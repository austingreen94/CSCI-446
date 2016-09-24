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
    
    double xCoor;
    double yCoor;
    int index;
    ArrayList<Point> connectedPoints = new ArrayList<Point>();
    // 0 = UNCOLORED, 1 = RED, 2 = GREEN, 3 = BLUE, 4 = YELLOW
    int color = 0;
    int legalMoves=0;
    ArrayList<Point> conflicts = new ArrayList<Point>();
    
    public Point( int index){
        Random rn = new Random();
        xCoor = rn.nextDouble();
        yCoor = rn.nextDouble();
        this.index = index;
    }
  
    public void printPoint(){
        System.out.print("(" + xCoor + ", " + yCoor + ")");
    }
}
