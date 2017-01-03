$(".deleteButton").click(function (e) {
    e.preventDefault();
    var href = $(this).attr("href");
    var row = $(this).parent().parent().parent();
    $.get(href, function () {
        row.remove();
    });
});