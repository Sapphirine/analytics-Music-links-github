package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FormatFileName {
	public static void main(String[] args) throws IOException {
		String input = "";
		int numclusters = 10;
		for(int i = 0; i<args.length; i++) {
			if(args[i].equals("-i")){
				input = args[i+1];
				i++; 
				continue;
			}else if(args[i].equals("-n")) {
				numclusters = Integer.parseInt(args[i+1]);
				i++;
				continue;
			}
		}
		main(input, numclusters);
	}
	 public static void main(String inputfolder, int numclusters) throws IOException{
		 //input name of folder with clusters in them.
		 File folder=new File(inputfolder);
			String[] filelist = new String[numclusters];
			BufferedReader reader;
			BufferedWriter writer;
			StringBuffer text;
			int j=0;
			for (File file : folder.listFiles()) {
				text = new StringBuffer("");
				if (file.isFile()) {
					filelist[j] = file.getName();
					reader = new BufferedReader(new FileReader(file));
					String line;
					String[] linearr;
					while((line = reader.readLine()) != null) {
						linearr = line.split(",");
						for(int i = 0; i< linearr.length-1; i++) {
							if(i == 0) {
								text.append(linearr[i]);
							}else{
							text.append(","+linearr[i]);
							}
						}
						text.append("\n");
					}
					reader.close();
					writer = new BufferedWriter(new FileWriter(file));
					writer.write(text.toString());
					writer.close();
					String temp=filelist[j].replace("\r",  "");
					file.renameTo(new File(inputfolder+temp));
				}else
					j--;
				j++;
			}
	 }
}