import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author KayZeus
 * @author Jordan Parr
 * @author Austin Green
 */
public class Point {
    
    int xCoor;
    int yCoor;
    int index;
    List<Point> connectedPoints = new ArrayList<Point>();
    
    public Point(int squareDimension, int index){
        Random rn = new Random();
        xCoor = rn.nextInt(squareDimension + 1) + 0;
        yCoor = rn.nextInt(squareDimension + 1) + 0;
        this.index = index;
    }
  
    public void printPoint(){
        System.out.print("(" + xCoor + ", " + yCoor + ")");
    }
}
