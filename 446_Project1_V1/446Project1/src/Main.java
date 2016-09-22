
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
        JFrame application = new JFrame("Original");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize (Graph.squareDimension*50+30, Graph.squareDimension*50+50);
        application.setVisible(true);
        
        long i=0;
        while(i<100000){
            i++;
            System.out.println(i);
        }
        
        MinConflicts algorithms = new MinConflicts(Graph.finishedPoints);
        // Max number of runs = 10000 & 4 colors being used
        int minConflictSuccess = algorithms.minConflicts(10000, 4);
        if(minConflictSuccess != -1){
            System.out.println("Number of Decisions of Min_Conflicts = " + minConflictSuccess);
        } else {
            System.out.println("Min_Conflicts Unsuccessful");
        }
        // Panel after Min_Conflicts algorithm has ran
        JFrame app2 = new JFrame("minConflicts");
        app2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app2.add(panel);
        app2.setSize (Graph.squareDimension*50+30, Graph.squareDimension*50+50);
        app2.setVisible(true);
        // Reset graph
        i=0;
        while(i<100000){
            i++;
            System.out.println(i);
        }
        algorithms.resetGraph();
    }
}
