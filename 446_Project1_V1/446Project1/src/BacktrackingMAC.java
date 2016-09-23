
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
public class BacktrackingMAC {
    List<Point> points = new ArrayList<Point>();
    List<Point> alreadyTested = new ArrayList<Point>();
    int numDecisions =0;
    int numColors;
    
    BacktrackingMAC(List<Point> inPoints){
        points = inPoints;
    }
    
    public void initConflicts(){
        for(int i=0; i< points.size(); i++){
            points.get(i).legalMoves=numColors;
            points.get(i).conflicts.clear();
        }
    }
    
    public int init(int in_numColors){
        numColors = in_numColors;
        initConflicts();
        int rand = (int) (Math.random()*points.size());
        points.get(rand).color = 1;
        numDecisions++;
        List<Point> answer = DFS(points.get(rand),1);
        if (answer == null){
            System.out.println("Failed Attempt");
        }
        return numDecisions;
    }
    
    public List<Point> DFS(Point curNode, int curColor){
        //adds curNode to the working tested list
        //reduces legal moves of neighbors if applicable
        //creates a fail condition if a noncolored node reaches 0 possiblities
        alreadyTested.add(curNode);
        boolean legalMovesFailed = false;
        for(int i=0;i<curNode.connectedPoints.size(); i++){
            if(curNode.connectedPoints.get(i).color != curNode.color){
                curNode.connectedPoints.get(i).legalMoves--;
            }
            if(curNode.connectedPoints.get(i).color == 0){
                curNode.connectedPoints.get(i).conflicts.add(curNode);
            }
            if(curNode.connectedPoints.get(i).legalMoves<=0 && curNode.connectedPoints.get(i).color ==0){
                legalMovesFailed = true;
            }
        }
        
        //print working list
        for (int i = 0; i< alreadyTested.size(); i++){
            System.out.print(alreadyTested.get(i).index + ":"+alreadyTested.get(i).color+" ");
        }
        System.out.println(alreadyTested.size());
        
        //exit condition for finding answer
        if(alreadyTested.size()==points.size()){
            for(int i=0; i<alreadyTested.size();i++){
                if(alreadyTested.get(i).color> numColors){
                    alreadyTested.get(i).color = 1;
                }
            }
            System.out.println("ANSWER");
            return alreadyTested;
        }
        
        if(!legalMovesFailed){
            for(int c=curColor; c<=numColors; c++){
                //finds some node that works with the current color
                Point testNode = null;
                boolean colorFail = false;
                for(int i=0; i<points.size();i++){
                    // only continue if this node isnt already in the list(ie not already colored)
                    if (!alreadyTested.contains(points.get(i))){
                        colorFail=false;
                        for(int j=0; j<points.get(i).connectedPoints.size(); j++){
                            if(points.get(i).connectedPoints.get(j).color == c){
                                colorFail = true;
                                j=points.get(i).connectedPoints.size();
                            }
                        }
                        if(!colorFail){
                            testNode = points.get(i);
                            testNode.color=c;
                            numDecisions++;
                            List<Point> ans = DFS(testNode, c);
                            // checks to see if all nodes possible were in the returned answer (ie exit because an answer was found)
                            if(ans != null && ans.size()==points.size()){
                                return ans;
                            }
                        }
                    }
                }

                //if test node found continue recursion
                
                //else find any other node with another color
//                else{
//                    curColor++;
//                    System.out.println(curColor);
//                    if(curColor<=numColors){
//                        for(int i=0; i<points.size();i++){
//                            // only continue if this node isnt already in the list(ie not already colored)
//                            if (!alreadyTested.contains(points.get(i)) && points.get(i).color==0){
//
//                                testNode = points.get(i);
//                                testNode.color=curColor;
//                                numDecisions++;
//                                List<Point> ans = DFS(testNode, curColor);
//                                // checks to see if all nodes possible were in the returned answer (ie exit because an answer was found)
//                                if(ans != null && ans.size()==points.size()){
//                                    return ans;
//
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
        
        curNode.color = 0;
        //restores all nodes to before this branch began
        alreadyTested.remove(curNode);
        for(int i=0;i<curNode.connectedPoints.size(); i++){
            if(curNode.connectedPoints.get(i).color != curNode.color){
                curNode.connectedPoints.get(i).legalMoves++;
            }
            curNode.connectedPoints.get(i).conflicts.remove(curNode);
        }
        List<Point> workingList=new ArrayList<Point>();
        workingList.add(curNode);
        fixProblems(findProblem(curNode, workingList));
        return null;
    }
    
    //creates oldest last list of nodes that need to be changed.
    public List<Point> findProblem(Point curPoint, List<Point> workingList){
        Point nextConflict=null;
        for(int i=curPoint.conflicts.size()-1; i>=0; i--){
            if(workingList == null || !workingList.contains(curPoint.conflicts.get(i))){
                nextConflict = curPoint.conflicts.get(i);
                i=-1;
            }
        }
        if(nextConflict!=null){
            workingList.add(nextConflict);
            return findProblem(nextConflict ,workingList);
        }else{
            return workingList;
        }
    }
    
    //
    public void fixProblems(List<Point> problems){
        System.out.print("Fix:::  ");
        for(int i=problems.size()-1; i>=0; i--){
            System.out.print(problems.get(i).index +":"+problems.get(i).color+" ");
            problems.get(i).color++;
            if(problems.get(i).color> numColors){
//                boolean conflicts[] = new boolean[numColors+1];
//                for(int j = 0; j<conflicts.length; j++){
//                    conflicts[j]=false;
//                }
//                for(int j=0; j<problems.get(i).connectedPoints.size(); j++){
//                    conflicts[problems.get(i).connectedPoints.get(j).color]=true;
//                }
//                for(int j = 1; j<conflicts.length; j++){
//                    //can only be colored if there was no color conflict
//                    if(!conflicts[j]){
//                        problems.get(i).color=j;
//                    }else{
//                        System.out.print("issue");
//                    }
//                }
            }
        }
        System.out.println();
    }
}
