
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author KayZeus
 * @author Jordan Parr
 * @author Austin Green
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
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
        TimeUnit.SECONDS.sleep(1);
        
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
        TimeUnit.SECONDS.sleep(1);
        algorithms.resetGraph();
        
        //initialize backtracking
        Backtracking back = new Backtracking(Graph.finishedPoints);
        int decisions = back.init(4); //4 color types
        System.out.println("Number of Decisions of Simple Backtracking = " + decisions);
        //make new frame for backtracking
        JFrame app3 = new JFrame("Backtracking");
        app3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app3.add(panel);
        app3.setSize (Graph.squareDimension*50+30, Graph.squareDimension*50+50);
        app3.setVisible(true);
        //reset graph
        TimeUnit.SECONDS.sleep(1);
        algorithms.resetGraph();
        
        //initialize backtrackingFC
        BacktrackingFC backFC = new BacktrackingFC(Graph.finishedPoints);
        decisions = backFC.init(4); //4 color types
        System.out.println("Number of Decisions of Backtracking with Forward Checking= " + decisions);
        //make new frame for backtracking
        JFrame appBackFC = new JFrame("Backtracking with Forward Check");
        appBackFC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appBackFC.add(panel);
        appBackFC.setSize (Graph.squareDimension*50+30, Graph.squareDimension*50+50);
        appBackFC.setVisible(true);
        //reset graph
        TimeUnit.SECONDS.sleep(1);
        algorithms.resetGraph();
        
        //initialize backtrackingMAC
        BacktrackingMAC backMAC = new BacktrackingMAC(Graph.finishedPoints);
        decisions = backMAC.init(4); //4 color types
        System.out.println("Number of Decisions of Backtracking with Constant Propagation= " + decisions);
        //make new frame for backtracking
        JFrame appBackMAC = new JFrame("Backtracking with Constant Propagation (MAC)");
        appBackMAC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appBackMAC.add(panel);
        appBackMAC.setSize (Graph.squareDimension*50+30, Graph.squareDimension*50+50);
        appBackMAC.setVisible(true);
        //reset graph
        TimeUnit.SECONDS.sleep(1);
        //algorithms.resetGraph();
    }
}