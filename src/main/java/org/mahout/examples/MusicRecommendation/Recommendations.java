package org.mahout.examples.MusicRecommendation;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;







//import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.util.Arrays;

public class Recommendations
{
    public static void main( String[] args ) throws Exception
    {
    	String userid = "";
    	String frequenciesfolder = "";
    	String clusterfolder = "";
    	String outputfolder = "";
    	int numrecommendations = 1;
    	for(int i = 0; i < args.length; i++) {
    		if(args[i].equals("-id")) {
    			if(args[i+1] != null || args[i+1] != "") {
    				userid = args[i+1];
    				i++;
    				continue;
    			}
    		} else if(args[i].equals("-f")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				frequenciesfolder = args[i+1];
    				i++;
    				continue;
    			}
    		} else if(args[i].equals("-o")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				outputfolder = args[i+1];
    				i++;
    				continue;
    			}
    		}else if(args[i].equals("-i")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				clusterfolder = args[i+1];
    				i++;
    				continue;
    			}
    		}else if(args[i].equals("-n")) {
    			if(args[i+1] != null || args[i+1] != ""){
    				numrecommendations = Integer.parseInt(args[i+1]);
    				i++;
    				continue;
    			}
    		}
    		else {
    			throw new IllegalArgumentException(args[i]);
    		}
    	}
    	
    	main(userid, frequenciesfolder, clusterfolder, outputfolder, numrecommendations);
    	
    }
    
    public static void main(String userid, String frequenciesfolder, String clusterfolder, String outputfolder, int numrecommendations) throws Exception{
    	String line = "";
    	String[] clusters = null;
    	String filepath = null;
    	int found_id = 0;
    	try {
    		// Read in the cluster information for desired user, the input file is under  your workspace
    		BufferedReader br = new BufferedReader(new FileReader(frequenciesfolder));
    		while ( (line = br.readLine() ) != null){
    			clusters = line.split("\t");    //Assume delimiter is ","
    			if(clusters[0].equals(userid)){
    				clusters = Arrays.copyOfRange(clusters, 1,clusters.length);
    				found_id = 1;
    				break;
    			}
    		}
    		br.close();
    	}
    	catch (IOException e){
    		e.printStackTrace();
    	}
    	
    	if(found_id == 0) {
    		throw new Exception(userid);
    	}
    	
    	//Deleting old recommendations
    	File outputdir = new File(outputfolder);
    	for(File file : outputdir.listFiles()) {
			if(file.isFile()){
				file.delete();
			}
		}
    	
    	for (int i=0;i<clusters.length;i++) {
    		if (Integer.parseInt(clusters[i]) != 0){
    			// The folder name where you store the cluster data, the files' name are [cluster-number].csv
    			//String filename = Integer.toString(i+1);
    			String filename = Integer.toString(i);
    			File fileout = new File(outputfolder);
    			fileout.mkdir();
    			filepath = clusterfolder + filename +".csv";
    			DataModel model = new FileDataModel(new File(filepath));
    			UserSimilarity similarity = new LogLikelihoodSimilarity(model);
    	        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
    	        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
    	        // recommend 10 tracks for user
    	        List<RecommendedItem> recommendations = recommender.recommend(Integer.parseInt(userid), numrecommendations);
    	        try{
    	        	// The output file is stored under a folder called "recommendation" in your workspace
    	        	BufferedWriter writer = new BufferedWriter(new FileWriter(outputfolder + "recom" + filename + ".csv"));
    		        for (RecommendedItem recommendation : recommendations) {
    		        	writer.append(recommendation.getItemID()+ " " + recommendation.getValue() + "\n");
    		        }
    		        writer.flush();
    		        writer.close();
    	        }
    	        catch (IOException e){
    	        	e.printStackTrace();
    	        }
    		}
    	}
    }
}