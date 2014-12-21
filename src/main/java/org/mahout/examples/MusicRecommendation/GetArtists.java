package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetArtists {
	public static void main(String[] args) throws IOException {
		String input = "";
		for(int i = 0; i<args.length; i++) {
			if(args[i].equals("-i")) {
				if(!args[i+1].equals("") && (args[i+1] != null)) {
					input = args[i+1];
					break;
				}
			}
		}
		main(input);
	}
	
	public static void main(String input) throws IOException {
		File artistfile;
		BufferedReader reader;
		String line;
		String[] linearr;
		
		
		artistfile = new File(input);
		reader = new BufferedReader(new FileReader(artistfile));
		while((line = reader.readLine()) != null) {
			linearr = line.split("\t");
			if(linearr.length > 2){
				System.out.println(linearr[2]);
			}
		}
		reader.close();
	}
}
