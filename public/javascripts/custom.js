  $("#addSuggestion").click(function(){
    ga('send', 'event', 'button', 'click', 'addSuggestion');
      $.ajax({
        beforeSend: function(xhrObj){
              xhrObj.setRequestHeader("Content-Type","application/json");
              xhrObj.setRequestHeader("Accept","html/text");
          },
        type: "POST",
        url: "/api/suggestions",
        data: JSON.stringify({"email": $('#suggestionModal #email').val(), "comment": $('#suggestionModal #comment').val(), "pageUrl": $('#suggestionModal #adress_url').val(), "galleryUrl": $('#suggestionModal #gallery_url').val()}),
        success: function( data ) {
                             $('#suggestionModal').modal('hide');
                             $(".suggestionsPanel ul").prepend(data);
                             $('#suggestionModal input, #suggestionModal textarea').val("");
                             ga('send', 'event', 'button', 'click', 'submitSuggestion');
                       },
        dataType: "html"
      }).fail(function(a,b,c) {
                $('#suggestionModal .modal-body .alert').remove();
                $('#suggestionModal .modal-body form').after('<div class="alert alert-danger" role="alert">' + a.responseText + '</div>')
          });
})





$('#issueModal #issue_es_version').val($("body").attr("es-version-data"));
    $("#addIssue").click(function(){
        ga('send', 'event', 'button', 'click', 'addIssue');

      $.ajax({
        beforeSend: function(xhrObj){
              xhrObj.setRequestHeader("Content-Type","application/json");
              xhrObj.setRequestHeader("Accept","html/text");
          },
        type: "POST",
        url: "/api/issues",
        data: JSON.stringify({"email": $('#issueModal #issue_email').val(), "comment": $('#issueModal #issue_comment').val(), "esVersion": $('#issue_es_version').val(), "galleryUrl": $('#issueModal #issue_gallery_url').val()}),
        success: function( data ) {
            $('#issueModal').modal('hide');
            $(".reportIssuePanel ul").prepend(data);
            $('#issueModal input, #issueModal textarea').val("");
            ga('send', 'event', 'button', 'click', 'submitIssue');
            },
        dataType: "html"
      }).fail(function(a,b,c) {
                $('#issueModal .modal-body .alert').remove();
                $('#issueModal .modal-body form').after('<div class="alert alert-danger" role="alert">' + a.responseText + '</div>')
          });
      })
