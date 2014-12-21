package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class GetSongInfo {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String tracks = "";
		String artists = "";
		for(int i = 0; i<args.length; i++) {
			if(args[i].equals("-t")) {
				if(!args[i+1].equals("") && (args[i+1] != null)) {
					tracks = args[i+1];
					i++;
					continue;
				}
			}else if(args[i].equals("-a")) {
				if(!args[i+1].equals("") && (args[i+1] != null)) {
					artists = args[i+1];
					i++;
					continue;
				}
			}
		}
		main(tracks, artists);
	}
	
	public static void main(String tracks, String artists) throws IOException, FileNotFoundException{
		HashMap<String,String> artistmap, trackmap;
		String line;
		Collection<String> c;
		Iterator<String> it;
		
		artistmap = TrackIDToName.getArtistMap(artists);
		trackmap = TrackIDToName.getTrackMap(tracks, artistmap);
		
		c = trackmap.values();
		it = c.iterator();
		while(it.hasNext()) {
			line = it.next();
			System.out.println(line);
		}
	}
}
