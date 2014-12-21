package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TrackIDToName {
	
	public static void main(String[] args) throws IOException {
		
		String tracks = "";
		String artists = "";
		String input = "";
		String outpath = "";
	
		for(int i = 0; i < args.length; i++) {
    		if(args[i].equals("-i")) {
    			if(args[i+1] != null || args[i+1] != "") {
    				input = args[i+1];
    				i++;
    				continue;
    			}
    		} else if(args[i].equals("-t")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				tracks = args[i+1];
    				i++;
    				continue;
    			}
    		}else if(args[i].equals("-a")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				artists = args[i+1];
    				i++;
    				continue;
    			}
    		}else if(args[i].equals("-o")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				outpath = args[i+1];
    				i++;
    				continue;
    			}
    		}
		}
		main(tracks, artists, input, outpath, false);
	}
	
	public static void main(String t, String a, String input, String outpath, boolean print) throws IOException{
		File filein;
		//File tracks;
		//File artists;
		File out;
		BufferedReader stream;
		BufferedWriter streamout;
		String line;
		String trackid;
		//String artist;
		//String artistname;
		//StringBuffer trackinfo;
		String[] linelist;
		HashMap<String, String> trackmap;
		HashMap<String, String> artistmap;
		
		filein = new File(input);
		//tracks = new File(t);
		//artists = new File(a);
		out = new File(outpath+"final_recommendations_tracks_and_artists.txt");
		artistmap = getArtistMap(a);
		trackmap = getTrackMap(t, artistmap);
		
		stream = new BufferedReader(new FileReader(filein));
		
		out.createNewFile();
		streamout = new BufferedWriter(new FileWriter(out));
		while((line=stream.readLine()) != null) {
			linelist = line.split("\\s+");
			trackid = linelist[0];
			streamout.write(trackmap.get(trackid));
			if(print) {
				System.out.println(trackmap.get(trackid));
			}
		}
		stream.close();
		streamout.close();
		
	}
		
	public static HashMap<String, String> getArtistMap(String artists) throws IOException {
		HashMap<String, String> artistmap = new HashMap<String,String>();
		BufferedReader stream = new BufferedReader(new FileReader(artists));
		String line, artist;
		String[] linelist;

		while((line=stream.readLine()) != null) {
			linelist = line.split("\t");
			artist = linelist[0];
			if(artistmap.get(artist) == null) {
				if(linelist.length > 2){
				artistmap.put(artist, linelist[2]);
				}else {
					artistmap.put(artist, "Artist Name Not Available");
				}
			}
		}
		stream.close();
		return artistmap;
	}
	
	public static HashMap<String, String> getTrackMap(String tracks, HashMap<String, String> artistmap) throws IOException {
		HashMap<String, String> trackmap = new HashMap<String, String>();
		BufferedReader stream = new BufferedReader(new FileReader(tracks));
		String line, trackid, artist, artistname;
		StringBuffer trackinfo;
		String[] linelist;
		
		while((line = stream.readLine()) != null) {
			linelist = line.split("\t");
			trackid = linelist[0];
			artist = linelist[2];
			artistname = artistmap.get(artist);
			//trackinfo = new StringBuffer("Track: ");
			trackinfo = new StringBuffer(linelist[1]);
			//trackinfo.append(linelist[1]);
			trackinfo.append("\tBy: ");
			trackinfo.append(artistname);
			//trackinfo.append("\n");
			if(trackmap.get(trackid) == null) {
				trackmap.put(trackid, trackinfo.toString());
			}
		}
		stream.close();
		return trackmap;
	}
		

}
