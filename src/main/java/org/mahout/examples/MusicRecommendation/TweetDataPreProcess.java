package org.mahout.examples.MusicRecommendation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;

public class TweetDataPreProcess {
	
	public static HashMap<String, File> parser(String[] args) {
		String arg;
		HashMap<String,File> argmap;
		File tempfile;
		
		argmap = new HashMap<String,File>(2);
		for(int i=0; i<args.length; i++) {
			arg = args[i];
			
			if(arg.equals("-i") || arg.equals("-o")) {
				if(!args[i+1].equals(null) && !args[i+1].equals(" ")){
					tempfile = new File(args[i+1]);
					if(arg.equals("-i") && !tempfile.exists()) {
						System.err.println("Invalid input path: "+args[i+1]);
						throw new IllegalArgumentException();
					}else if(arg.equals("-o") && tempfile.exists()) {
						System.err.println("Output path: '"+args[i+1]+"' already exists.");
						throw new IllegalArgumentException();
					}
				}else {
					System.err.println("Invalid argument for parameter: "+arg);
					throw new IllegalArgumentException();
				}
			} else if(arg.equals("uid") || arg.equals("tid")){
				if(!args[i+1].equals(null)) {
					try {
						 Integer.parseInt(args[i+1]);
					}catch(NumberFormatException e) {
						System.err.println("Argument for parameter: '"+arg+"' must be an integer");
						throw new IllegalArgumentException();
					}
				}
			}
			
			if(arg.equals("-i")) {
				argmap.put("input", new File(args[i+1]));
				i++;
			}else if(arg.equals("-o")){
				argmap.put("output", new File(args[i+1]));
				i++;
			}else {
				System.err.println("Invalid parameter entry: "+arg);
				throw new InvalidParameterException();
			}
		}
		return argmap;
	}
	
	public static void main(String[] args) throws IOException, FileNotFoundException
	{
		HashMap<String,File> argmap;
		File input;
		File output;
		BufferedReader streamin;
		BufferedWriter streamout;
		String line;
		String[] linelist;
		
		argmap = parser(args);
		input = argmap.get("input");
		output = argmap.get("output");
		output.createNewFile();
		
		streamin = new BufferedReader(new FileReader(input));
		streamout = new BufferedWriter(new FileWriter(output));
		streamin.readLine();
		while((line = streamin.readLine()) != null){
			linelist = line.split("\\s+");
			line = linelist[2]+"\t"+linelist[4]+"\t"+"5"+"\t"+linelist[8]+"\t"+linelist[9]+"\n";
			streamout.write(line);
		}
		
		streamin.close();
		streamout.close();
		
	}
	
}
