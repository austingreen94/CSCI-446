import java.util.Random;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KayZeus
 * @author Jordan Parr
 * @author Austin Green
 * 
 */
public class GraphGen {
    public Point[] pointArray;
    //Point point;
    
    public int squareDimension = 0;
    public int numPoints = 0;
    
    List<Point> finishedPoints = new ArrayList<Point>();
   
    public GraphGen(){
        
    }
    
    public void setNumPoints(int amountPoints){
        // 10 is test number
        numPoints = amountPoints; //Not sure how number will be entered by user. Ask Prof.
        
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
        setNumPoints(10);
        setDimension();
        pointArray = new Point[getNumPoints()];
        Point newPoint;
        boolean identicalCoords=false;
        for (int index = 0; index < numPoints; index++){
            do{
                identicalCoords=false;
                newPoint = new Point(getDimension(), index);
                for (int j = 0; j < index; j++){
                    if(newPoint.xCoor == pointArray[j].xCoor && newPoint.yCoor == pointArray[j].yCoor){
                        identicalCoords=true;
                    }
                }
            }while(identicalCoords);
            pointArray[index] = newPoint;            
        }
        
        //test print of coordinates
        System.out.println("Point Coord");
        for(int p = 0; p < numPoints; p++){
            System.out.println(pointArray[p].xCoor + " " + pointArray[p].yCoor);
        }
    }
    
    //Edge Creation:
    
    public void setEdges(){
        while(finishedPoints.size() < numPoints) {
            findNeighbor(pickPoint());
        }
    }
    
    // Method to pick a random Point within the graph
    public int pickPoint(){
        int randomPoint;
        do{
            Random rn = new Random();
            randomPoint = rn.nextInt(getNumPoints());
        }while(finishedPoints.contains(pointArray[randomPoint]));
        System.out.println("Random Point: " + pointArray[randomPoint].index);
        return randomPoint;               
    }

    public void findNeighbor(int randomPoint){
        Point chosenPoint = pointArray[randomPoint];
        double minDistance = Double.MAX_VALUE;
        Point closestPoint = pointArray[randomPoint];
        for(int i = 0; i < pointArray.length; i++){
            if(i == randomPoint){
                
            }else if(!checkConnection(pointArray[i], chosenPoint) && !overlap(randomPoint, i)){
                // Calculate distance    
                double x = Math.pow((chosenPoint.xCoor - pointArray[i].xCoor), 2);
                double y = Math.pow((chosenPoint.yCoor - pointArray[i].yCoor), 2);
                double distance = Math.sqrt(x + y);
                System.out.println("Distance Between Points:\n" + distance);
                    
                if(distance < minDistance){
                    minDistance = distance;
                    closestPoint = pointArray[i];
                }
            }
         }
        if(closestPoint.index == randomPoint){
            // Same Point, gets here if no other point in the whole set was found
            System.out.println("Same Point");
            finishedPoints.add(chosenPoint);
        } else {
            // Add edge between current Point and closest Point
            System.out.println("Lowest Distance = " + minDistance);
            System.out.println("Closest Node = " + closestPoint.xCoor + " " + closestPoint.yCoor);
            chosenPoint.connectedPoints.add(closestPoint);
            closestPoint.connectedPoints.add(chosenPoint);
        }
        // Prints out adjacency list for each Point's connections
        System.out.println("\nEdges\n");
        for(int i = 0; i < pointArray.length; i++){
            pointArray[i].printPoint();
            System.out.print(": ");
            for(int j = 0; j < pointArray[i].connectedPoints.size(); j++){
                pointArray[i].connectedPoints.get(j).printPoint();
                System.out.print(", ");
            }
            System.out.println();
        }
        
    }
    
    
    // Method to check if an edge between two Points already exists
    public boolean checkConnection(Point check, Point randomPoint){
        for(int i = 0; i < randomPoint.connectedPoints.size(); i++){
            if(randomPoint.connectedPoints.get(i).index == check.index){
                System.out.println("found theres already a connection between "+randomPoint.xCoor+","+randomPoint.yCoor+" and "+check.xCoor+","+check.yCoor);
                return true;
            }
        }
        return false;//default setting
    }
    public boolean overlap(int p1, int p2){
        int x1 = pointArray[p1].xCoor;
        int y1 = pointArray[p1].yCoor;
        int x2 = pointArray[p2].xCoor;
        int y2 = pointArray[p2].yCoor;
        for (int i = 0; i < pointArray.length; i++){
            if (i == p1 || i == p2){
                // do nothing
            }else{
                int x3 = pointArray[i].xCoor;
                int y3 = pointArray[i].yCoor;
                int x4;
                int y4;
                for (int j = 0; j<pointArray[i].connectedPoints.size(); j++){
                    x4 = pointArray[i].connectedPoints.get(j).xCoor;
                    y4 = pointArray[i].connectedPoints.get(j).yCoor;
                    if (pointArray[i].connectedPoints.get(j).index == p1 || pointArray[i].connectedPoints.get(j).index == p2){
                        // do nothing
                    }else if(Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}