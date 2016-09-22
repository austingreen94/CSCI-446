
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/**
 *
 * @author Austin
 */
public class MinConflicts {
    
    int currentColor = 1;
    List<Point> points = new ArrayList<Point>();
    Random ran;
    
    MinConflicts(List<Point> inPoints){
        points = inPoints;
        ran = new Random();
    }
    
    // Method coloring a point with 3 color choices
    public int color3Point(int curColor, Point curPoint){
        curPoint.color = curColor;
        if(curColor == 3){
            curColor = 1;
        } else {
            curColor++;
        }
        return curColor;
    }
    
    // Method coloring a point with 4 color choices
    public int color4Point(int curColor, Point curPoint){
        curPoint.color = curColor;
        if(curColor == 4){
            curColor = 1;
        } else {
            curColor++;
        }
        return curColor;
    }
    
    // Method to color entire graph for initiation of minConflicts algorithm
    public void colorEntireGraph(int amountColors){
        if(amountColors == 3){
            for(int i = 0; i < points.size(); i++){
                currentColor = color3Point(currentColor, points.get(i));
            }
        } else {
            for(int i = 0; i < points.size(); i++){
                currentColor = color4Point(currentColor, points.get(i));
            }
        }
    }
    
    // Method to reset each Point's color to uncolored
    public void resetGraph(){
        for(int i = 0; i < points.size(); i++){
            points.get(i).color = 0;
        }
    }
    
    // Method to check amount of conflicts for minConflicts algorithm
    public List<Point> checkConflicts(){
        List<Point> conflictList = new ArrayList<Point>();
        for(int i = 0; i <  points.size(); i++){
            for(int j = 0; j < points.get(i).connectedPoints.size(); j++){
                if(points.get(i).color == points.get(i).connectedPoints.get(j).color){
                    conflictList.add(points.get(i));
                }
            }
        }
        return conflictList;
    }
    
    // Method to check potential number of conflicts given a color change
    public int changeColorCheck(int var, int colorToCheck, List<Point> conflictList){
        int currentColor = conflictList.get(var).color;
        conflictList.get(var).color = colorToCheck;
        List<Point> conflicts = checkConflicts();
        int conflictsInt = conflicts.size() / 2;
        conflictList.get(var).color = currentColor;
        return conflictsInt;
    }
    
    // Min_Conflicts algorithm
    public int minConflicts(int max_steps,  int amountOfColors){
        int numDecisions = points.size();
        colorEntireGraph(amountOfColors);
        List<Point> conflictList = new ArrayList<Point>();
        for(int i = 0; i < max_steps; i++){
            conflictList = checkConflicts();
            int conflictCount = conflictList.size() / 2;
            System.out.println("Number of Conflicts: " + conflictCount);
            if(conflictList.isEmpty()){
                return numDecisions;
            } else {
                int var = ran.nextInt(conflictList.size());
                //System.out.println("Var: " + var);
                int bestColor = conflictList.get(var).color;
                int potentialLowConflicts = Integer.MAX_VALUE;
                for(int j = 0; j < amountOfColors; j++){
                    int check = changeColorCheck(var, (j + 1), conflictList);
                    numDecisions++;
                    if(check < potentialLowConflicts){
                        potentialLowConflicts = check;
                        bestColor = (j + 1);
                    }
                }
                conflictList.get(var).color = bestColor;
                numDecisions++;
                conflictList = checkConflicts();
                conflictCount = conflictList.size();
                
            }
        }
        return -1;
    }
    
}

