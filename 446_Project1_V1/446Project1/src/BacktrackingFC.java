
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jordan
 */
public class BacktrackingFC {
    List<Point> points = new ArrayList<Point>();
    List<Point> alreadyTested = new ArrayList<Point>();
    int numDecisions =0;
    int numColors;
    
    BacktrackingFC(List<Point> inPoints){
        points = inPoints;
        
    }
    
    public int init(int in_numColors){
        numColors = in_numColors;
        initPointLegalMoves();
        int rand = (int) (Math.random()*points.size());
        points.get(rand).color = 1;
        numDecisions++;
        List<Point> answer = DFS(points.get(rand));
        if (answer == null){
            System.out.println("Failed Attempt");
        }
        return numDecisions;
    }
    
    //sets all nodes initial legal moves total to the amount of colors available
    public void initPointLegalMoves(){
        for(int i=0; i< points.size(); i++){
            points.get(i).legalMoves=numColors;
        }
    }
    
    //recursive DFS
    public List<Point> DFS(Point curNode){
        //adds curNode to the working tested list
        //reduces legal moves of neighbors if applicable
        //creates a fail condition if a noncolored node reaches 0 possiblities
        alreadyTested.add(curNode);
        boolean legalMovesFailed = false;
        for(int i=0;i<curNode.connectedPoints.size(); i++){
            if(curNode.connectedPoints.get(i).color != curNode.color){
                curNode.connectedPoints.get(i).legalMoves--;
            }
            if(curNode.connectedPoints.get(i).legalMoves<=0 && curNode.connectedPoints.get(i).color ==0){
                legalMovesFailed = true;
            }
        }
        
        //print current working list and length of it
        for (int i = 0; i< alreadyTested.size(); i++){
            System.out.print(alreadyTested.get(i).index + ":"+alreadyTested.get(i).color+" ");
        }
        System.out.println(alreadyTested.size());
        
        //exit if an answer is found
        if(!legalMovesFailed && alreadyTested.size()==points.size()){
            System.out.println("ANSWER");
            return alreadyTested;
        }
        
        // dont enter if fail condition was met
        if(!legalMovesFailed){
            //finds node with least moves that is not on the list already
            Point testNode = null;
            for(int i=0; i<points.size();i++){
                // only continue if this node isnt already in the list(ie already colored)
                if (!alreadyTested.contains(points.get(i))){
                    if(testNode==null || points.get(i).legalMoves<testNode.legalMoves){
                        testNode = points.get(i);
                    }
                }
            }
            
            //create a list of conflicts for colors (not color=0 as it is default)
            boolean conflicts[] = new boolean[numColors+1];
            for(int j = 0; j<conflicts.length; j++){
                conflicts[j]=false;
            }
            for(int j=0; j<testNode.connectedPoints.size(); j++){
                conflicts[testNode.connectedPoints.get(j).color]=true;
            }
            for(int j = 1; j<conflicts.length; j++){
                //can only be colored if there was no color conflict
                if(!conflicts[j]){
                    testNode.color=j;
                    numDecisions++;
                    List<Point> ans = DFS(testNode);
                    // checks to see if all nodes possible were in the returned answer (ie exit because an answer was found)
                    if(ans != null && ans.size()==points.size()){
                        return ans;
                    }
                }
            } 
        }
        
        //restores all nodes to before this branch began
        curNode.color=0;
        alreadyTested.remove(curNode);
        for(int i=0;i<curNode.connectedPoints.size(); i++){
            if(curNode.connectedPoints.get(i).color != curNode.color){
                curNode.connectedPoints.get(i).legalMoves++;
            }
        }
        return null;
    }
}
