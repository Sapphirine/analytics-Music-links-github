var userhash = { };
var songinfo = ""
var recommendations = "";
var next_anonymous = 1; 
var HOME = __dirname+"/../";
var maxbuffer = (1024 * 50000); //50 MB buffer between web view and processes

var exec = require('child_process').exec, child;

//Song functions
var init_song_info = function(res) {
    child = exec('java -cp '+process.env.CLASSPATH+':'+HOME+'target/classes/ org.mahout.examples.MusicRecommendation.GetSongInfo -t '+HOME+'tracks/track.txt -a '+HOME+'artists/artists.txt',
                {maxBuffer: maxbuffer}, function (error, stdout, stderr) {
            //console.log(stdout);
            if(error != null) {
                console.log("ERROR: "+error);
            }
            if(stdout != null) {
                songinfo = stdout;
            }
    });
};
exports.init_song_info = init_song_info;

var get_song_info = function() {
    return songinfo;
};
exports.get_song_info = get_song_info;

//Recommender
var recommend = function(req) {
   var id = req.data.id;
   var num = req.data.number;
   child = exec('java -cp '+process.env.CLASSPATH+':'+HOME+'target/classes org.mahout.examples.MusicRecommendation.App',
                {maxBuffer:maxbuffer, env:{numberRecommendations:num, recommendationUserID:id}, timeout:30000}, function ( error, stdout, stderr) {

           if(error != null) {
                console.log("ERROR: "+error);
           }
            if(stdout != null) {
                req.io.emit('recommend_done', {});
            }
           recommendations = stdout;
    });
};
exports.recommend = recommend;

var get_recommendations = function() {
    return recommendations;
};
exports.get_recommendations = get_recommendations;

//Add music preferences
var add_preference = function(req) {
    var trackname = req.data.track;
    var userid = req.data.trackid;
    var longitude = req.data.longitude;
    var latitude = req.data.latitude;
    var entry = userid+"\t"+trackname+"\t"+longitude+"\t"+latitude+"\n";
    var cc = HOME+"data/cluster_centers.txt";
    var cd = HOME+"data/clusters/";
    var f = HOME+"data/user_cluster_frequencies.csv";
    var t = HOME+"tracks/track.txt";
    child = exec('java -cp '+process.env.CLASSPATH+':'+HOME+'target/classes org.mahout.examples.MusicRecommendation.AddMusic -cc '+cc+' -cd '+cd+
                    ' -f '+f+' -t '+t, {maxBuffer:maxbuffer, env:{newmusicentry:entry}, timeout:30000}, function(error, stdout, stderr) {

            if(error != null) {
                console.log("ERROR: "+error);
            }
            console.log(stdout);
            console.log("Added new Preference");
    });
};
exports.add_preference = add_preference;



