package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class ClusterUserSearch 
{
	private String path;
	private String fileout;
	private String[] filelist;
	private int numfiles;
	
	public ClusterUserSearch(String folderPath, String out, int numFiles) throws IllegalArgumentException, IOException
	{
		//String system path to the folder holding the cluster results
		//The folder should have files in some sort of alphanumeric ordering because they will
		//be sorted by name
		//
		//String local output file name to be created in folderpath folder
		//Integer of the number of Files to search through in the directory.
		//Integer of the number of lines in each file that are headers
		//Integer corresponding to the column number of the userIDs starting from 0
		
		if(folderPath.lastIndexOf('/') != folderPath.length()-1) {
			folderPath = folderPath + "/";
		}
		
		int i = 0;
		String[] filelist = new String[numFiles];
		this.path = folderPath;
		this.numfiles = numFiles;
		this.fileout = out;
		File folder = new File(folderPath);
		
		if(!folder.exists()) {
			System.out.println("Bad directory path");
			throw new IllegalArgumentException();
		}
		
		for(File file : folder.listFiles()) {
			if(file.isFile()){
				filelist[i] = file.getName();
			}else
				i--;
			i++;
		}
		Arrays.sort(filelist);
		
		if(i != numFiles) {
			System.out.println("Incorrect number of files");
			throw new IllegalArgumentException();
		}
		
		this.filelist = filelist;
	}
	
	public static HashMap<String, String> parser(String[] args) {
		String arg;
		HashMap<String,String> argmap;
		File tempfile;
		
		argmap = new HashMap<String,String>();
		for(int i=0; i<args.length; i++) {
			arg = args[i];
			
			if(arg.equals("-i") || arg.equals("-o")) {
				if(!args[i+1].equals(null) && !args[i+1].equals(" ")){
					tempfile = new File(args[i+1]);
					if(arg.equals("-i") &&  !tempfile.exists()) {
						System.err.println("Invalid input path: "+args[i+1]);
						throw new IllegalArgumentException();
					}else if(arg.equals("-o") && tempfile.exists()) {
						System.err.println("Output path: '"+args[i+1]+"' already exists");
						throw new IllegalArgumentException();
					}
				}else {
					System.err.println("Invalid argument for parameter: "+arg);
					throw new IllegalArgumentException();
				}
			} else if(arg.equals("-numfiles") || arg.equals("-nf") || arg.equals("-nfiles")){
				if(!args[i+1].equals(null)) {
					try {
						 Integer.parseInt(args[i+1]);
					}catch(NumberFormatException e) {
						System.err.println("Argument for parameter: '"+arg+"' must be an integer");
						throw new IllegalArgumentException();
					}
				}
			} else {
				System.err.println("'"+arg+"' is not a valid parameter");
				throw new IllegalArgumentException();
			}
			
			if(arg.equals("-i")) {
				argmap.put("input", args[i+1]);
				i++;
			}else if(arg.equals("-o")){
				argmap.put("output", args[i+1]);
				i++;
			}else {
				argmap.put("numfiles", args[i+1]);
				i++;
			}
		}
		return argmap;
	}
	
	public int numLines(String file) throws IOException, FileNotFoundException
	{
		int num = 0;
		BufferedReader stream = new BufferedReader(new FileReader(file));
		
		while(stream.readLine() != null)
		{
			num++;
		}
		stream.close();
		return num;
	}
	
	public HashMap<String, Integer> userMap(String File) throws EOFException, FileNotFoundException, IOException
	{
		HashMap<String, Integer> map;
		String file;
		String line;
		String userid;
		int hashsize;
		Integer userval;
		BufferedReader stream;
				
		file = this.path+File;
		       
        if((hashsize = numLines(file)) != 0)
        	map = new HashMap<String,Integer>(hashsize/10000, (float)0.75);
        else {
        	System.out.println("File is empty");
        	throw new EOFException();
        }
        
        stream = new BufferedReader(new FileReader(file));
        
        while((line = stream.readLine())!= null) {
        	userid = line.split("\\s+")[0];
        	if((userval = map.get(userid)) != null)
        		map.put(userid, ++userval);
        	else
        		map.put(userid, 1);
        }
        stream.close();
        return map;
	}
	
	public void writeOut(HashMap<String,Integer> userlist, ArrayList<HashMap<String,Integer>> maplist) throws IOException
	{
		BufferedWriter streamout;
		StringBuilder writebuffer;
		Integer userfrequency;
		File output;
		
		output = new File(this.fileout);
		output.createNewFile();
		
        streamout = new BufferedWriter(new FileWriter(fileout));
        writebuffer = new StringBuilder("UserID");
        writebuffer.append("\t");
        for(int i=0; i<numfiles; i++){
        	writebuffer.append("Cluster");
        	writebuffer.append(i);
        	writebuffer.append("\t");
        }
        writebuffer.append("\n");
        streamout.write(writebuffer.toString());
        
        for(String user: userlist.keySet())
        {
        	writebuffer = new StringBuilder(user);
        	writebuffer.append("\t");
	        for(int j=0; j<numfiles; j++)
	        {
	        	if((userfrequency = maplist.get(j).get(user)) == null)
	        		userfrequency = 0;
	        	writebuffer.append(userfrequency);
	        	writebuffer.append("\t");
	        }
	        writebuffer.append("\n");
	        streamout.write(writebuffer.toString());
        }
        streamout.flush();
        streamout.close();
	}
	
    public void search() throws IOException, FileNotFoundException
    {
        String[] filelist = this.filelist;
        int numfiles = this.numfiles;
        ArrayList<HashMap<String,Integer>> maplist;
        HashMap<String,Integer> userlist;
        HashMap<String,Integer> map;
        
        maplist = new ArrayList<HashMap<String,Integer>>();
        userlist = new HashMap<String,Integer>();
        for(int i=0; i<numfiles; i++)
        {
        	map = userMap(filelist[i]);
	        maplist.add(i, map);
	        for(String key : map.keySet())
	        {
	        	if (!userlist.containsKey(key)) {
	        		userlist.put(key, null);
	        	}
	        }
        }
        
        writeOut(userlist, maplist);
    }
    
    public static void main(String[] args) throws IOException
    {
        HashMap<String,String> argmap;
        
        argmap = parser(args);
    	ClusterUserSearch s = new ClusterUserSearch(argmap.get("input"), 
    					argmap.get("output"), Integer.parseInt(argmap.get("numfiles")));
    	s.search();
    }
}
