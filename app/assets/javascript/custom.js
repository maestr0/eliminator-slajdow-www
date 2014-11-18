$("#addSuggestion").click(function () {
    $.ajax({
        beforeSend: function (xhrObj) {
            xhrObj.setRequestHeader("Content-Type", "application/json");
            xhrObj.setRequestHeader("Accept", "html/text");
        },
        type: "POST",
        url: "/api/suggestions",
        data: JSON.stringify({"email": $('#suggestionModal #email').val(), "comment": $('#suggestionModal #comment').val(), "pageUrl": "N/A", "ua": navigator.userAgent, "galleryUrl": $('#suggestionModal #gallery_url').val()}),
        success: function (data) {
            $('#suggestionModal').modal('hide');
            $(".suggestionsPanel ul").prepend(data);
            $('#suggestionModal input, #suggestionModal textarea').val("");
            ga('send', 'event', 'button', 'click', 'submitSuggestion');
        },
        dataType: "html"
    }).fail(function (a, b, c) {
        $('#suggestionModal .modal-body .alert').remove();
        $('#suggestionModal .modal-body form').after('<div class="alert alert-danger" role="alert">' + a.responseText + '</div>')
    });
})

$(".alert-dismissible button.close").click(function () {
    ga('send', 'event', 'close-alert', 'click', 'close-donation-alert');
});

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


$('#suggestionModal').on('shown.bs.modal', function (e) {
    $('#suggestionModal .modal-body .alert').remove();
    ga('send', 'event', 'button', 'click', 'showModalSuggestion');
})

$('#issueModal').on('shown.bs.modal', function (e) {
    $('#issueModal #issue_es_version').val($("body").attr("es-version-data"));
    $('#issueModal .modal-body .alert').remove();
    ga('send', 'event', 'button', 'click', 'showModalIssue');
})