package machinelearning;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Austin
 */
public class ReadIn {
    
    // 2d String array to hold house votes data
    //String[][] dataset = new String[10][17];
    
    // Sample of house votes text file with 10 examples
    
    public ReadIn(){
    
    }
    
    // Method to read in houseTest.txt into the 2d array
    public static ArrayList<Node> read(String file){
        ArrayList<Node> dataset = new ArrayList<Node>();
        try{
            FileInputStream stream = new FileInputStream(file);
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
                        Random ran = new Random();
                        int choice = ran.nextInt(2);
                        if(choice == 0){
                            //dataset[row][i] = "n";
                            splitString[i]="n";
                        }else{
                            //dataset[row][i] = "y";
                            splitString[i]="y";
                        }
                    } else{
                        //dataset[row][i] = splitString[i];
                    }
                }
                dataset.add(new Node(splitString));
                row++;
            }
            in.close();
        } catch(Exception e){
            System.out.println(e);
        }
        
        // Print of the dataset 2d array for testing
        
        return dataset;
    }
}