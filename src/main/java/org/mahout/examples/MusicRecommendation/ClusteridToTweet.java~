mport java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class project {
	public static void main(String args[]) throws IOException {
		int cluster_num=10; //need changed every time
		File filePath = new File(“Music-Links/testoutput/kmeans_10/center_10"); //
 		File filePath2 = new File("Music-Links/testoutput/kmeans_10/points_10); //
		File filePath3 = new File("Music-Links/data/tweet.txt");
		FileWriter out=new FileWriter("Music-Links/data/tweet_10.txt"); 
		BufferedReader br;
		String s = null;
		int i=0;
		int i2=0;
		String[] sArray=new String[2];
		String[] sArray1=new String[7];
		String[] sArray2=new String[6];
		String[] id = new String[cluster_num]; //store cluster_keys
		int[] id2 = new int[1048575];
		
		
		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((s = br.readLine()) != null) {
				sArray= s.split("L-");
				s= sArray[1].replaceAll("\"}","");
				id[i]=s; 
				i++;
			}
			
			br = new BufferedReader(new FileReader(filePath2));
			s = br.readLine(); //delete first three lines
			s = br.readLine();
			s = br.readLine();
     		while ((s = br.readLine()) != null && i2<1048574 ) {
     			sArray1 =s.split(":");
				s = sArray1[1];
				s = s.replaceAll(" ","");
				//System.out.println(s + "\r\n");  
				for (i=0; i<cluster_num; i++)
				{
					if (id[i].equals(s))
					{
						id2[i2]=i;
						break;
					}
				}
				//System.out.println(id2[i2] + "\r\n");  
				i2++;
			}
     				
     		i2=0;
     		br = new BufferedReader(new FileReader(filePath3));
     		while ((s = br.readLine()) != null  && i2<1048574) {
     			sArray2= s.split(",");
				s = sArray2[0]+","+sArray2[1]+","+sArray2[2]+","+id2[i2];
				out.write(s +"\r\n");
				i2++;
     		}
     		
     		out.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

