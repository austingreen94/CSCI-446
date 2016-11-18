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
        //initiallize the data set to be returned
        ArrayList<Node> dataset = new ArrayList<Node>();
        try{
            //attempts to read the file
            FileInputStream stream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            int row = 0;
            
            //reads next line so long as there is one
            while((line = br.readLine()) != null && !line.equals("")){
                // Split lines by commas
                String[] splitString = line.split(",");
                
                //if "n"s or "y"s are here then assign those as 0s and 1s
                for(int i=0; i<splitString.length; i++){
                    if(splitString[i].equals("y")){
                        splitString[i] = "1";
                    }
                    else if(splitString[i].equals("n")){
                        splitString[i] = "0";
                    }
                }
                // add node to data set
                dataset.add(new Node(splitString));
            }
            in.close();
        } catch(Exception e){
            System.out.println(e);
        }
        
        //Perform search for "?"s and input a random value in there
        for(int i =0; i< dataset.size(); i++){
            int cols = dataset.get(i).data.length;
            for(int j =0; j< cols; j++){
                //if a ? is found then get rid of it!
                if(dataset.get(i).data[j].equals("?")){
                    System.out.println("? found at Node "+i+", Attribute "+j);
                    
                    //get all possible attribute types in this particular column
                    ArrayList<String> attributes = new ArrayList<String>();
                    for(int k =0; k< dataset.size(); k++){
                        if(!attributes.contains(dataset.get(k).data[j]) && !dataset.get(k).data[j].equals("?")){
                            attributes.add(dataset.get(k).data[j]);
                        }
                    }
                    
                    //randomly select one of the attributes to insert here
                    int choice = (int)(Math.random()*attributes.size());
                    dataset.get(i).data[j] = attributes.get(choice);
                    System.out.println("    Choice#:" + choice + " Choice:" + attributes.get(choice));
                }
            }
        }
        
        //return finished dataset
        return dataset;
    }
}