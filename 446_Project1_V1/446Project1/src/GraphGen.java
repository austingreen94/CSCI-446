import java.util.Random;

/**
 *
 * @author KayZeus
 * @author Jordan Parr
 * @author Austin Green
 * 
 */
public class GraphGen {
    public Point[] pointArray;
    Point point;
    
    public int squareDimension = 0;
    public int numPoints = 0;
   
    public GraphGen(){

    }
    
    public void setNumPoints(int amountPoints){
        // 4 is test number
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
        for (int index = 0; index < numPoints; index++){
            point = new Point(getDimension(), index);
            pointArray[index] = point;            
        }
        
        //test print of coordinates
        System.out.println("Point Coord");
        for(int p = 0; p < numPoints; p++){
        System.out.println(pointArray[p].xCoor + " " + pointArray[p].yCoor);
        }
    }
    
    //Edge Creation:
    
    public void setEdges(){
        for (int i = 0; i < 100; i ++){
            findNeighbor(pickPoint());
        }
    }
    
    // Method to pick a random Point within the graph
    public int pickPoint(){
        int randomPoint;
        Random rn = new Random();
        randomPoint = rn.nextInt(getNumPoints());
        System.out.println("Random Point: " + pointArray[randomPoint].index);
        return randomPoint;               
    }

    public void findNeighbor(int randomPoint){
        Point chosenPoint = pointArray[randomPoint];
        double minDistance = Double.MAX_VALUE;
        Point closestPoint = pointArray[randomPoint];
        for(int i = 0; i < pointArray.length; i++){
        
            if(i == randomPoint){
                System.out.println("Same Point");
                break;
            }
            else if(!checkConnection(pointArray[i], chosenPoint) && !overlap()){
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
        if(closestPoint.index == pointArray[randomPoint].index){
            // Same Point
        } else {
            // Add edge between current Point and closest Point
            System.out.println("Lowest Distance = " + minDistance);
            System.out.println("Closest Node = " + closestPoint.xCoor + " " + closestPoint.yCoor);
            chosenPoint.connectedPoints.add(closestPoint);
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
                return true;
            }
        }
        return false;//default setting
    }
    public boolean overlap(){
        return false;//default setting
    }
}
