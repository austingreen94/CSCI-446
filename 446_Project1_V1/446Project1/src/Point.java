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
    // 0 = UNCOLORED, 1 = RED, 2 = GREEN, 3 = BLUE, 4 = YELLOW
    int color = 0;
    int legalMoves=0;
    List<Point> conflicts = new ArrayList<Point>();
    
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
