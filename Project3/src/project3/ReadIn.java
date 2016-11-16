package project3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author Austin
 */
public class ReadIn {
    Random ran = new Random();
    // 2d String array to hold house votes data
    String[][] houseVotesDataset = new String[10][17];
    // Sample of house votes text file with 10 examples
    String houseFile = "houseTest.txt";
    
    public ReadIn(){
    
    }
    
    // Method to read in houseTest.txt into the 2d array
    public void readHouseVotes(){
        try{
            FileInputStream stream = new FileInputStream("houseTest.txt");
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            int row = 0;
            while((line = br.readLine()) != null){
                // Split lines by commas
                String[] splitString = line.split(",");
                
                // If attribute value is '?' then randomly pick a value
                for(int i = 0; i < splitString.length; i++){
                    if(splitString[i].equals("?")){
                        int choice = ran.nextInt(2);
                        if(choice == 0){
                            houseVotesDataset[row][i] = "n";
                        }else{
                            houseVotesDataset[row][i] = "y";
                        }
                    } else{
                        houseVotesDataset[row][i] = splitString[i];
                    }
                }
                row++;
            }
            in.close();
        } catch(Exception e){
            System.out.println(e);
        }
        
        // Print of the dataset 2d array for testing
        for(int i = 0; i < houseVotesDataset.length; i++){
            for(int j = 0; j < houseVotesDataset[i].length; j++){
                System.out.print(houseVotesDataset[i][j] + " ");
            }
            System.out.println();
        }
    }
}
