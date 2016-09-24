
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
    int maxDecisions;
    
    BacktrackingFC(int max, List<Point> inPoints){
        maxDecisions = max;
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
        }else{
            //print current working list and length of it
            for (int i = 0; i< alreadyTested.size(); i++){
                System.out.print(alreadyTested.get(i).index + ":"+alreadyTested.get(i).color+" ");
            }
            System.out.println(alreadyTested.size());
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
        //exit condition for too many tries
        if(numDecisions>=maxDecisions){
            //System.out.println("Failed");
            return null;
        }
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
            System.out.println("\nANSWER:");
            return alreadyTested;
        }
        
        // dont enter if fail condition was met
        if(!legalMovesFailed){
            List<Point> testNodeList = new ArrayList<Point>();
            //finds node with least moves that is not on the list already
            Point testNode = null;
            //testNodeList.addAll(points);
            //testNodeList.removeAll(alreadyTested);
            for(int j = 1; j<=numColors; j++){
                for(int i=0; i<points.size();i++){
                // only continue if this node isnt already in the list(ie already colored)
                
                    if (!alreadyTested.contains(points.get(i)) && points.get(i).legalMoves==j){
                        testNodeList.add(points.get(i));                 
//                    if(testNode==null || points.get(i).legalMoves<testNode.legalMoves){
//                        testNode = points.get(i);
//                    }
                    }
                }
            }
            //print testNodeList
            System.out.print("testNodeList (PointIndex:legalMovesLeft) - ");
            for(int j=0; j<testNodeList.size(); j++){
                System.out.print(testNodeList.get(j).index + ":"+testNodeList.get(j).legalMoves+" ");
            }
            System.out.println(testNodeList.size());
            
            for(int k=0; k<testNodeList.size(); k++){
                testNode = testNodeList.get(k);

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
        }
        
        //restores all nodes to before this branch began
        
        alreadyTested.remove(curNode);
        for(int i=0;i<curNode.connectedPoints.size(); i++){
            if(curNode.connectedPoints.get(i).color != curNode.color){
                curNode.connectedPoints.get(i).legalMoves++;
            }
        }
        curNode.color=0;
        return null;
    }
}
