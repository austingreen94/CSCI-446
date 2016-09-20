
import javax.swing.JFrame;

/**
 *
 * @author KayZeus
 * @author Jordan Parr
 * @author Austin Green
 */
public class Main {

    public static void main(String[] args) {
        GraphGen Graph = new GraphGen();
        Graph.createNodes();
        Graph.setEdges();
        
        //Generates Jframe to a certain size
        //JPanel is made which stores all the drawing of the objects
        DrawPanel panel = new DrawPanel(Graph.finishedPoints);
        JFrame application = new JFrame();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize (Graph.squareDimension*50+30, Graph.squareDimension*50+50);
        application.setVisible(true);
    }
}
