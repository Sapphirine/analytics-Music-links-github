package org.mahout.examples.MusicRecommendation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class CombineRecommendations
{
	public static void main(String[] args) {
		String input = "";
		int numrecommendations = 1;
		for(int i = 0; i<args.length; i++) {
			if(args[i].equals("-i")) {
				input = args[i+1];
				i++;
				break;
			}else if(args[i].equals("-n")) {
				numrecommendations = Integer.parseInt(args[i+1]);
				i++;
				break;
			}
		}
		main(input, numrecommendations);
		
	}
	
	public static void main(String inpath, int numrecommendations)
	{
		//define
		ArrayList<item> list =new ArrayList<item>();
		String temp=null;
		String[] temps;
		item tempitem=new item();
		String fileNo=null;
		//HashMap<String, Double> trackmap;
		//Double rating;
		//int[] count={1,2,3,4,5,6,7,8,9,10};
		
		//read cluster
		//trackmap = new HashMap<String, Double>();
		//for(int j=0;j<1;j++)
		for(File recomfile : new File(inpath).listFiles())
		{
			//fileNo=Integer.toString(j);
			try
			{
				//File myfile=new File(inpath+fileNo+".csv");
				FileReader filereader=new FileReader(recomfile);
				BufferedReader reader=new BufferedReader(filereader);
				for(int i=0;(temp=reader.readLine())!=null;i++)
				{
					tempitem=new item();
					temps=temp.split("\\s+");
					
					/* hashmap code for efficiency
					if((rating = trackmap.get(temps[1])) != null){
						trackmap.put(temps[1], rating + Double.parseDouble(temps[2]));
					} else {
						trackmap.put(temps[1], Double.parseDouble(temps[2]));
					}
					*/
					
					tempitem.trackid=temps[0];
					tempitem.rating=(Float.parseFloat(temps[1]));
					list.add(tempitem);
				}
				reader.close();
			}
			catch(Exception ex)
			{
				System.out.println(temp);
				ex.printStackTrace();
			}
		}
		
		//combine
		
		for(int i=1;i<list.size();i++)
		{
			for(int j=0;j<i;j++)
			{
				if(list.get(i).trackid.equals(list.get(j).trackid))
				{
					list.get(j).rating+=list.get(i).rating;
					list.remove(i);
					i=i-1;
					break;
				}
			}
		}
		
		//sort
		Comparator<item> comparator=new Comparator<item>()
		{
			public int compare(item x,item y)
			{
				if(x.rating>=y.rating)
				{
					return -1;
				}
				return 1;
			}
		};
		Collections.sort(list,comparator);
		
		//write
		try
		{
			FileWriter writer=new FileWriter(inpath+"final_recommendations.csv");
			for(int i=0;(i<list.size()) && (i<numrecommendations);i++)
			{
				writer.write(list.get(i).trackid+"\t"+list.get(i).rating+"\n");
			}
			writer.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return;
	}
}