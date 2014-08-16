$('#suggestionModal').on('shown.bs.modal', function (e) {
  $("#addSuggestion").click(function(){


  $.ajax({
    beforeSend: function(xhrObj){
          xhrObj.setRequestHeader("Content-Type","application/json");
          xhrObj.setRequestHeader("Accept","application/json");
      },
    type: "POST",
    url: "/api/suggestions",
    data: JSON.stringify({"comment": $('#suggestionModal #comment').val(), "pageUrl": $('#suggestionModal #adress_url').val(), "galleryUrl": $('#suggestionModal #gallery_url').val()}),
    success: function( data ) {
                         $('#suggestionModal').modal('hide');
                   },
    dataType: "json"
  }).fail(function(a,b,c) {
            $('#suggestionModal .modal-body .alert').remove();
            $('#suggestionModal .modal-body form').after('<div class="alert alert-danger" role="alert">' + a.responseText + '</div>')
      });
  })
})



$('#issueModal').on('shown.bs.modal', function (e) {
    $('#issueModal #issue_es_version').val($("body").attr("es-version-data"));
    $("#addIssue").click(function(){


      $.ajax({
        beforeSend: function(xhrObj){
              xhrObj.setRequestHeader("Content-Type","application/json");
              xhrObj.setRequestHeader("Accept","application/json");
          },
        type: "POST",
        url: "/api/issues",
        data: JSON.stringify({"comment": $('#issueModal #comment').val(), "pageUrl": $('#issueModal #adress_url').val(), "galleryUrl": $('#issueModal #gallery_url').val()}),
        success: function( data ) {
            $('#issueModal').modal('hide');
                       },
        dataType: "json"
      }).fail(function(a,b,c) {
                $('#suggestionModal .modal-body .alert').remove();
                $('#suggestionModal .modal-body form').after('<div class="alert alert-danger" role="alert">' + a.responseText + '</div>')
          });
      })
})