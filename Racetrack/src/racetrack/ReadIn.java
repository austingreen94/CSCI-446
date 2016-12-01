package racetrack;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class ReadIn {
    
    // 2d String array to hold house votes data
    //String[][] dataset = new String[10][17];
    
    // Sample of house votes text file with 10 examples
    
    public ReadIn(){
    
    }
    
    // Method to read in 
    public static char[][] read(String file){
        
        try{
            //attempts to read the file
            FileInputStream stream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            line = br.readLine();
            String[] split = line.split(",");
            int rows = Integer.parseInt(split[0]);
            int cols = Integer.parseInt(split[1]);
            System.out.println(rows+" "+cols);
            char[][] map = new char[rows][cols];
            int i=0;
            //reads next line so long as there is one
            while((line = br.readLine()) != null && !line.equals("")){
                char[] row = line.toCharArray();
                map[i] = row;
                i++;
            }
            in.close();
            return map;
        } catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
}