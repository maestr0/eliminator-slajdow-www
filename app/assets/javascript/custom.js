$("button.report-problem ").click(function () {
    window.location = "/problem/nowy";
});

("#ua").val(navigator.userAgent);

$("#addIssue").click(function () {
    $.ajax({
        beforeSend: function (xhrObj) {
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "html/text");
        },
        type: "POST",
        url: "/api/issues",
        data: JSON.stringify({"email": $('#issueModal #issue_email').val(), "comment": $('#issueModal #issue_comment').val(), "esVersion": $('#issue_es_version').val(), "ua": navigator.userAgent, "galleryUrl": $('#issueModal #issue_gallery_url').val()}),
        success: function (data) {
            $('#issueModal').modal('hide');
            $(".reportIssuePanel ul").prepend(data);
            $('#issueModal input, #issueModal textarea').val("");
            ga('send', 'event', 'button', 'click', 'submitIssue');
        },
        dataType: "html"
    }).fail(function (a, b, c) {
        $('#issueModal .modal-body .alert').remove();
        $('#issueModal .modal-body form').after('<div class="alert alert-danger" role="alert">' + a.responseText + '</div>')
    });
})