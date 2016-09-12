
import java.util.Arrays;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KayZeus
 */
public class GraphGen {
    public Points[] pointArray;
    public Edges[] edgeArray;
    Points point;
    Edges edge;
    
    public int squareDimension = 0;
    public int numPoints = 0;
    double distance;
   
    public GraphGen(){

    }
    
    public void setNumPoints(){
        numPoints = 4; //Not sure how number will be entered by user. Ask Prof.
        
    }
    
    public int getNumPoints(){
        return numPoints;
    }
    
    public void setDimension(){
        squareDimension = 10;
    }
    
    public int getDimension(){
        return squareDimension;
    }
    
    public void createNodes(){
        setNumPoints();
        setDimension();
        pointArray = new Points[getNumPoints()];
        for (int index = 0; index < numPoints; index++){
            point = new Points(getDimension());
            pointArray[index] = point;            
        }
        
        //test print of coordinates
        System.out.println("Point Coord\n");
        for(int p = 0; p < numPoints; p++){
        System.out.println(pointArray[p].xCoor + " " + pointArray[p].yCoor);
        }
    }
    
    //Edge Creation:
    
    public void setEdges(){
        for (int i = 0; i < getNumPoints(); i ++){
            findNeighbor(pickPoint());
        }
    }
    
    public int pickPoint(){
        int randomPoint;
        Random rn = new Random();
        randomPoint = rn.nextInt(getNumPoints() - 0) + 0;
        System.out.println(randomPoint);
        return randomPoint;               
    }
    
    public void findNeighbor(int randomPoint){
        boolean edgePlace = false;
        int count = 0;
        while (!edgePlace && count < getNumPoints()){
            if(count == randomPoint){
                //can't be its own neighbor
            }
            else{
                if(checkConnection()){
                    if((pointArray[randomPoint].xCoor- pointArray[count].xCoor) == 0){
                        distance = (pointArray[randomPoint].yCoor - 
                        pointArray[count].yCoor);
                    }
                    else{
                        distance = (pointArray[randomPoint].yCoor - 
                        pointArray[count].yCoor)/(pointArray[randomPoint].xCoor
                        - pointArray[count].xCoor);
                    }
                    count++;
                    System.out.println("Distance Between Points:\n" + distance);
                
            }
            }
            
            if(!overlap()){
            edgePlace = true;
        }
        }
        
    }
    
    public boolean checkConnection(){
        return true;//default setting
    }
    public boolean overlap(){
        return false;//default setting
    }
}
