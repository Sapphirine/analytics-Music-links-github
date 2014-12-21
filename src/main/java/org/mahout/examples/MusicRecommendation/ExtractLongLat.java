package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ExtractLongLat {
	public static void main(String args[]) throws IOException
	{
		File input, output;
		String arg, in, out, line;
		String[] arrline;
		StringBuffer templine;
		BufferedReader tweets;
		BufferedWriter locations;
		
		in = "";
		out = "";
	
		for(int i = 0; i<args.length; i++) {
			arg = args[i];
			System.out.println(arg);
			if(arg.equals("-i")) {
				if((args[i+1] != null) && !(args[i+1].equals(""))){
					in = args[i+1];
				}
			}else if(arg.equals("-o")) {
				if((args[i+1] != null) && !(args[i+1].equals(""))){
					out = args[i+1];
				}
			}
		}
		
		input = new File(in);
		output = new File(out);
		System.out.println(in);
		tweets = new BufferedReader(new FileReader(input));
		locations = new BufferedWriter(new FileWriter(output));
		
		while((line = tweets.readLine()) != null){
			arrline = line.split("\t");
			templine = new StringBuffer(arrline[2]);
			templine.append(",");
			templine.append(arrline[3]);
			templine.append("\n");
			locations.write(templine.toString());
		}
		
		tweets.close();
		locations.close();
		
	}
}
