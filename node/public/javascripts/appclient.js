var socket = io.connect();
var myapp = (function(){

    var submit = function() {
        socket.emit('recommend', {number:jQuery('#number').val(), id:jQuery('#recomuserid').val()});
    };

    socket.on('recommend_done', function() {
            jQuery('#recommendations').load('/recommendations');
    });


    var submit_track = function() {
        console.log("submit track");
        console.log(jQuery('#track').val());
        console.log(jQuery('#trackuserid').val());
        socket.emit('add_preference', {track:jQuery('#track').val(), trackid:jQuery('#trackuserid').val()});
    };

    socket.on('preference_added', function() {
        jQuery('#addin').load('/addin');
    });

    return {
        init: function() {
            //console.log("Client-side app starting up")
            jQuery("#submit").click(submit);
            jQuery("#submittrack").click(submit_track);

        }
    }
})();
jQuery(myapp.init);
