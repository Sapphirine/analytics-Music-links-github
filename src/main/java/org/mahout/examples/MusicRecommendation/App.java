package org.mahout.examples.MusicRecommendation;

import java.io.IOException;

import org.apache.commons.cli2.validation.InvalidArgumentException;
import org.apache.mahout.cf.taste.common.TasteException;

public class App {
	public static void main(String[] args) throws Exception {
		String id, num;
		int number = 10; id = ""; num = "";
		
		//Node.js application is running this
		if((num = System.getenv("numberRecommendations")) != null && (id = System.getenv("recommendationUserID")) != null) {
			number = Integer.parseInt(num);
			String home = "/usr/local/workspace/MusicRecommendation/";
			Recommendations.main(id, home+"data/user_cluster_frequencies.csv", home+"data/clusters/", home+"data/recommendation/", number);
			CombineRecommendations.main(home+"/data/recommendation/", number);
			TrackIDToName.main(home+"tracks/track.txt", home+"artists/artists.txt", home+"data/recommendation/final_recommendations.csv", home+"data/recommendation/", true);
			return;
			
		} else {
			//Normal call to this recommender application
			
			for (int i = 0; i<args.length; i++) {
				if(args[i].equals("-n")) {
					number = Integer.parseInt(args[i+1]);
					i++;
					continue;
				}else if(args[i].equals("-id")) {
					id = args[i+1];
					i++;
					continue;
				}
			}
			String home = "/usr/local/workspace/MusicRecommendation/";
			System.err.println("hello");
			Recommendations.main(id, home+"data/user_cluster_frequencies.csv", home+"data/clusters/", home+"data/recommendation/", number);
			CombineRecommendations.main(home+"/data/recommendation/", number);
			TrackIDToName.main(home+"tracks/track.txt", home+"artists/artists.txt", home+"data/recommendation/final_recommendations.csv", home+"data/recommendation/", false);
		}
		
	}
		
}
