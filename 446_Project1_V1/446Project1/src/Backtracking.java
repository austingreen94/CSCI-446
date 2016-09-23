
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
public class Backtracking {
    
    List<Point> points = new ArrayList<Point>();
    List<Point> alreadyTested = new ArrayList<Point>();
    int numDecisions =0;
    int numColors;
    
    Backtracking(List<Point> inPoints){
        points = inPoints;
        
    }
    
    public int init(int in_numColors){
        numColors = in_numColors;
        int rand = (int) (Math.random()*points.size());
        points.get(rand).color = 1;
        numDecisions++;
        List<Point> answer = DFS(points.get(rand));
        if (answer == null){
            System.out.println("Failed Attempt");
        }
        return numDecisions;
    }
    public List<Point> DFS(Point curNode){
        alreadyTested.add(curNode);
        
        //print working list
        for (int i = 0; i< alreadyTested.size(); i++){
            System.out.print(alreadyTested.get(i).index + ":"+alreadyTested.get(i).color+" ");
        }
        System.out.println(alreadyTested.size());
        
        //exit condition for finding answer
        if(alreadyTested.size()==points.size()){
            System.out.println("ANSWER");
            return alreadyTested;
        }
        
        //test ever node that is a neighbor
        for(int i=0; i<curNode.connectedPoints.size();i++){
            Point testNode = curNode.connectedPoints.get(i);
            // only continue if this node isnt already in the list(ie already colored)
            if (!alreadyTested.contains(testNode)){
                boolean conflicts[] = new boolean[numColors+1];
                //create a list of conflicts for colors (not color=0 as it is default)
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
                        // checks to see if all nodes possible got in the returned answer
                        if(ans != null && ans.size()==points.size()){
                            return ans;
                        }
                    }
                } 
            }
        }
        curNode.color = 0;
        alreadyTested.remove(curNode);
        //return null if this branch failed
        return null;
    }
    
}
