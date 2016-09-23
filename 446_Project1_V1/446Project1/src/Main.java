
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author KayZeus
 * @author Jordan Parr
 * @author Austin Green
 */
public class Main {

    static GraphGen[] graphs = new GraphGen[10];
    
    public static void resetColors(){
        for(int i = 0; i < graphs.length; i++){
            for(int j = 0; j < graphs[i].finishedPoints.size(); j++){
                graphs[i].finishedPoints.get(j).color = 0;
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        for(int i = 1; i < 11; i++){
            GraphGen Graph = new GraphGen();
            Graph.setNumPoints(i * 10);
            Graph.createNodes();
            Graph.setEdges();
            graphs[i - 1] = Graph; 
        }
        
        //Generates Jframe to a certain size
        //JPanel is made which stores all the drawing of the objects
        DrawPanel panel = new DrawPanel(graphs[0].finishedPoints);
        JFrame application = new JFrame("Original");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize (graphs[0].squareDimension*50+30, graphs[0].squareDimension*50+50);
        application.setVisible(true);
        TimeUnit.SECONDS.sleep(1);
        
        
        MinConflicts minConflicts = new MinConflicts(graphs[0].finishedPoints);
        // Max number of runs = 10000 & 4 colors being used
        int minConflictSuccess = minConflicts.minConflicts(10000, 3);
        if(minConflictSuccess != -1){
            System.out.println("Number of Points: " + graphs[0].finishedPoints.size() + "\nNumber of Decisions of Min_Conflicts = " + minConflictSuccess + "\n");
        } else {
            System.out.println("Number of Points: " + graphs[0].finishedPoints.size() + "\nMin_Conflicts Unsuccessful\n");
        }
        // Panel after Min_Conflicts algorithm has ran
        JFrame app2 = new JFrame("minConflicts");
        app2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app2.add(panel);
        app2.setSize (graphs[0].squareDimension*50+30, graphs[0].squareDimension*50+50);
        app2.setVisible(true);
        // Reset graph
        TimeUnit.SECONDS.sleep(1);
        
        // 3 Colors MinConflicts
        System.out.println("3 Colors Min-Conflicts:");
        for(int i = 1; i < graphs.length; i++){
            MinConflicts algorithms2 = new MinConflicts(graphs[i].finishedPoints);
            int minConflictSuccess2 = algorithms2.minConflicts(10000, 3);
            if(minConflictSuccess2 != -1){
                System.out.println("Number of Points: " + graphs[i].finishedPoints.size() + "\nNumber of Decisions of Min_Conflicts = " + minConflictSuccess2 + "\n");
            } else {
                System.out.println("Number of Points: " + graphs[i].finishedPoints.size() + "\nMin_Conflicts Unsuccessful\n");
            }
            //resetColors();
        }
        resetColors();
        
        // 4 Colors MinConflicts
        System.out.println("3 Colors Min-Conflicts:");
        for(int i = 0; i < graphs.length; i++){
            MinConflicts algorithms2 = new MinConflicts(graphs[i].finishedPoints);
            int minConflictSuccess2 = algorithms2.minConflicts(10000, 4);
            if(minConflictSuccess2 != -1){
                System.out.println("Number of Points: " + graphs[i].finishedPoints.size() + "\nNumber of Decisions of Min_Conflicts = " + minConflictSuccess2 + "\n");
            } else {
                System.out.println("Number of Points: " + graphs[i].finishedPoints.size() + "\nMin_Conflicts Unsuccessful\n");
            }
            //resetColors();
        }
        resetColors();
        
        //initialize backtracking
        Backtracking back = new Backtracking(graphs[0].finishedPoints);
        int decisions = back.init(4); //4 color types
        System.out.println("Number of Decisions of Simple Backtracking = " + decisions);
        //make new frame for backtracking
        JFrame app3 = new JFrame("Backtracking");
        app3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app3.add(panel);
        app3.setSize (graphs[0].squareDimension*50+30, graphs[0].squareDimension*50+50);
        app3.setVisible(true);
        //reset graph
        TimeUnit.SECONDS.sleep(1);
        //minConflicts.resetGraph();
        for(int i = 1; i < graphs.length; i++){
            Backtracking back2 = new Backtracking(graphs[i].finishedPoints);
            decisions = back2.init(4); //4 color types
            System.out.println("Number of Decisions of Simple Backtracking = " + decisions);
            //back2.resetGraph();
        }
        resetColors();
        
        //initialize backtrackingFC
        BacktrackingFC backFC = new BacktrackingFC(graphs[0].finishedPoints);
        decisions = backFC.init(4); //4 color types
        System.out.println("Number of Decisions of Backtracking with Forward Checking= " + decisions);
        //make new frame for backtracking
        JFrame appBackFC = new JFrame("Backtracking with Forward Check");
        appBackFC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appBackFC.add(panel);
        appBackFC.setSize (graphs[0].squareDimension*50+30, graphs[0].squareDimension*50+50);
        appBackFC.setVisible(true);
        //reset graph
        TimeUnit.SECONDS.sleep(1);
        //minConflicts.resetGraph();
        for(int i = 1; i < graphs.length; i++){
            BacktrackingFC backFC2 = new BacktrackingFC(graphs[0].finishedPoints);
            decisions = backFC.init(4); //4 color types
            System.out.println("Number of Decisions of Backtracking with Forward Checking= " + decisions);
        }
        resetColors();
        
        
        //initialize backtrackingMAC
        BacktrackingMAC backMAC = new BacktrackingMAC(graphs[0].finishedPoints);
        decisions = backMAC.init(4); //4 color types
        System.out.println("Number of Decisions of Backtracking with Constant Propagation= " + decisions);
        //make new frame for backtracking
        JFrame appBackMAC = new JFrame("Backtracking with Constant Propagation (MAC)");
        appBackMAC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appBackMAC.add(panel);
        appBackMAC.setSize (graphs[0].squareDimension*50+30, graphs[0].squareDimension*50+50);
        appBackMAC.setVisible(true);
        //reset graph
        TimeUnit.SECONDS.sleep(1);
        //algorithms.resetGraph();
    
        //GeneticAlgorithm genetic = new GeneticAlgorithm();
       //genetic.geneticAlgorithm(graphs[0]);
    }
    
}