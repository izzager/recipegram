/*
$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/api/test"
    }).then(function(data) {
        $('.greeting-id').append(data);
        console.log(data)
    });
});*/
$.get( "http://localhost:8080/api/steps/3", function( data ) {
    $('.greeting-id').html(data.id);
    $('.greeting-content').append(data.description);
    //alert( "Load was performed." );
    console.log(data)
});