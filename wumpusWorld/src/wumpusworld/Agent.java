/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan
 */
public class Agent {
    World askWorld;
    World myWorld = new World();
    int score = 0;
    Point curPoint = new Point(1,1);
    char direction = 'E';
    int amtArrows;
    ArrayList<Point> frontier = new ArrayList<Point>();
    
    public Agent(World inWorld){
        askWorld = inWorld;
        myWorld.startworld(askWorld.n);
    }
    
    //main method run to find gold. returns score
            
    public int runGame(){
        //initial starting position
        myWorld.world[1][1] = askWorld.world[1][1];
        myWorld.world[1][1].beenThere = true;
        frontier.add(new Point(1,2));
        frontier.add(new Point(2,1));
        Point frontierTarget = null;
        ArrayList<Point> routeTo = new ArrayList<Point>();
        
        while(!myWorld.world[curPoint.x][curPoint.y].glitter){
            if (routeTo.isEmpty()){
                frontierTarget = pickFrontier();
                routeTo = findRouteTo(new ArrayList<Point>(), frontierTarget);
                routeTo = shortenRoute(routeTo);
                routeTo.remove(0);
                for(int i = 0; i < routeTo.size(); i++){
                    System.out.print(" -> "+routeTo.get(i).x+","+routeTo.get(i).y);
                }
                System.out.println();
            }
            
            attemptMove(routeTo.get(0));
            routeTo.remove(0);
            myWorld.printworld();
            System.out.println("curPoint: " + curPoint);
            System.out.println();
            if(askWorld.world[curPoint.x][curPoint.y].wumpus || askWorld.world[curPoint.x][curPoint.y].hole){
                System.out.println("DEAD!!");
                System.exit(0);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                System.out.println("\tsleep error");
                Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println("You found the gold!");
        askWorld.printworld();
        return score;
    }
    
    public ArrayList<Point> findRouteTo(ArrayList<Point> curRoute, Point dest){
        ArrayList<Point> ans = null;
        //initial case where we are at the curPoint (ie where the player is)
        if(curRoute.isEmpty()){
            curRoute.add(curPoint);
            return(findRouteTo(curRoute, dest));
        }
        //exit case when the last point in the list is the dest
        else if(curRoute.get(curRoute.size()-1).equals(dest)){
            //curRoute.remove(0);
            return curRoute;
        }
        //go deeper case
        else{
            Point tester = curRoute.get(curRoute.size()-1);
            if((myWorld.world[tester.x+1][tester.y].beenThere && !myWorld.world[tester.x+1][tester.y].wall && !curRoute.contains(new Point(tester.x+1,tester.y))) || dest.equals(new Point(tester.x+1,tester.y))){
                curRoute.add(new Point(tester.x+1,tester.y));
                ans = findRouteTo(curRoute, dest);
                //failed at this attempt, take out this last add
                if(ans == null){
                    curRoute.remove(curRoute.size()-1);
                }
                //got an answer!
                else if(ans.get(ans.size()-1).equals(dest)){
                    return ans;
                }
            }
            if((myWorld.world[tester.x][tester.y+1].beenThere && !myWorld.world[tester.x][tester.y+1].wall && !curRoute.contains(new Point(tester.x,tester.y+1))) || dest.equals(new Point(tester.x,tester.y+1))){
                curRoute.add(new Point(tester.x,tester.y+1));
                ans = findRouteTo(curRoute, dest);
                //failed at this attempt, take out this last add
                if(ans == null){
                    curRoute.remove(curRoute.size()-1);
                }
                //got an answer!
                else if(ans.get(ans.size()-1).equals(dest)){
                    return ans;
                }
            }
            if((myWorld.world[tester.x][tester.y-1].beenThere && !myWorld.world[tester.x][tester.y-1].wall && !curRoute.contains(new Point(tester.x,tester.y-1))) || dest.equals(new Point(tester.x,tester.y-1))){
                curRoute.add(new Point(tester.x,tester.y-1));
                ans = findRouteTo(curRoute, dest);
                //failed at this attempt, take out this last add
                if(ans == null){
                    curRoute.remove(curRoute.size()-1);
                }
                //got an answer!
                else if(ans.get(ans.size()-1).equals(dest)){
                    return ans;
                }
            }
            if((myWorld.world[tester.x-1][tester.y].beenThere && !myWorld.world[tester.x-1][tester.y].wall && !curRoute.contains(new Point(tester.x-1,tester.y))) || dest.equals(new Point(tester.x-1,tester.y))){
                curRoute.add(new Point(tester.x-1,tester.y));
                ans = findRouteTo(curRoute, dest);
                //failed at this attempt, take out this last add
                if(ans == null){
                    curRoute.remove(curRoute.size()-1);
                }
                //got an answer!
                else if(ans.get(ans.size()-1).equals(dest)){
                    return ans;
                }
            }
        }
        // failed at all directions here
        return null;
    }
    
    public ArrayList<Point> shortenRoute(ArrayList<Point> r){
        //ArrayList<Point> newR = r;
        for(int i = 0; i<r.size(); i++){
            for(int j = r.size()-1; j>i+1; j--){
                //shorten by checking if pieces are one apart & deleteing middlemen
                if(Math.abs(r.get(i).x-r.get(j).x) == 1 && r.get(i).y == r.get(j).y){
                    // delete i+1 to j-1
                    for(int k =i+1; k<j; k++){
                        r.remove(i+1);
                    }
                    j=i;
                }
                else if(Math.abs(r.get(i).y-r.get(j).y) == 1 && r.get(i).x == r.get(j).x){
                    // delete i+1 to j-1
                    for(int k =i+1; k<j; k++){
                        r.remove(i+1);
                    }
                    j=i;
                }
            }
        }
        return r;
    }
    public Point pickFrontier(){
        //Point choice = null;
        ArrayList<Point> safe = new ArrayList<Point>();
        ArrayList<Point> danger = new ArrayList<Point>();
        for(int i = 0; i<frontier.size(); i++){
            if(safeFrontier(frontier.get(i))){
                safe.add(frontier.get(i));
            }else{
                danger.add(frontier.get(i));
            }
        }
        if(!safe.isEmpty()){safe = sortFrontier(safe);}
        else{System.out.println("YOLO");}
        if(!danger.isEmpty()){danger = sortFrontier(danger);}
        for(int i=0; i< danger.size(); i++){
            safe.add(danger.get(i));
        }
        frontier = safe;
        
        return frontier.get(0);
    }
    
    public boolean safeFrontier(Point p){
        boolean isSafe = false;
        if(!myWorld.world[p.x-1][p.y].breeze && !myWorld.world[p.x-1][p.y].stench && !myWorld.world[p.x-1][p.y].wall && myWorld.world[p.x-1][p.y].beenThere){
            isSafe = true;
        }
        else if(!myWorld.world[p.x+1][p.y].breeze && !myWorld.world[p.x+1][p.y].stench && !myWorld.world[p.x+1][p.y].wall && myWorld.world[p.x+1][p.y].beenThere){
            isSafe = true;
        }
        else if(!myWorld.world[p.x][p.y-1].breeze && !myWorld.world[p.x][p.y-1].stench && !myWorld.world[p.x][p.y-1].wall && myWorld.world[p.x][p.y-1].beenThere){
            isSafe = true;
        }
        else if(!myWorld.world[p.x][p.y+1].breeze && !myWorld.world[p.x][p.y+1].stench && !myWorld.world[p.x][p.y+1].wall && myWorld.world[p.x][p.y+1].beenThere){
            isSafe = true;
        }
        return isSafe;
    }
    
    public ArrayList<Point> sortFrontier (ArrayList<Point> list){
        ArrayList<Point> sorted = new ArrayList<Point>();
        sorted.add(list.get(0));
        for(int i =1; i< list.size(); i++){
            for(int j = 0; j < sorted.size(); j++){
                if(distance(list.get(i)) < distance(sorted.get(j))){
                    sorted.add(j, list.get(i));
                    j=sorted.size();
                }
                else if(j == sorted.size()-1){
                    sorted.add(list.get(i));
                    j=sorted.size();
                }
            }
        }
        return sorted;
    }
    
    public int distance(Point p){
        return Math.abs(p.x-curPoint.x) + Math.abs(p.y-curPoint.y);
    }
    
    public void attemptMove(Point moveTo){
        
        Node newMove = askWorld.travel(moveTo.x,moveTo.y);
        if(newMove == null){
            //bump
            myWorld.world[moveTo.x][moveTo.y].wall = true;
            myWorld.world[moveTo.x][moveTo.y].beenThere = true;
            frontier.remove(moveTo);
        }else{
            myWorld.world[moveTo.x][moveTo.y] = newMove;
            myWorld.world[curPoint.x][curPoint.y].player = false;
            askWorld.world[curPoint.x][curPoint.y].player = false;
            myWorld.world[moveTo.x][moveTo.y].player = true;
            askWorld.world[moveTo.x][moveTo.y].player = true;
            if(curPoint.x<moveTo.x)
            {
                direction = 'E';
                shoot();
            }
            else if(curPoint.x>moveTo.x)
            {
                direction = 'W';
                shoot();
            }
            else if(curPoint.y<moveTo.y)
            {
                direction = 'S';
                shoot();
            }
            else if(curPoint.y<moveTo.y)
            {
                direction = 'N';
                shoot();
            }
            curPoint = moveTo;
            myWorld.world[curPoint.x][curPoint.y].beenThere = true;
            if(frontier.contains(curPoint)){
                frontier.remove(curPoint);
                //checking if we have ever been to or tried to go to all points around current point
                if(myWorld.world[curPoint.x-1][curPoint.y].beenThere == false){
                    addToFrontier(new Point(curPoint.x-1,curPoint.y));
                }
                if(myWorld.world[curPoint.x+1][curPoint.y].beenThere == false){
                    addToFrontier(new Point(curPoint.x+1,curPoint.y));
                }
                if(myWorld.world[curPoint.x][curPoint.y-1].beenThere == false){
                    addToFrontier(new Point(curPoint.x,curPoint.y-1));
                }
                if(myWorld.world[curPoint.x][curPoint.y+1].beenThere == false){
                    addToFrontier(new Point(curPoint.x,curPoint.y+1));
                }
            }
        }
    }
    
    public void addToFrontier(Point toAdd){
        boolean canAdd = true;
        for(int i =0; i< frontier.size(); i++){
            if(frontier.get(i).equals(toAdd)){
                canAdd = false;
                break;
            }
        }
        if(canAdd){
            frontier.add(toAdd);
        }
    }
    
    public void shoot()
    {
        if(myWorld.world[curPoint.x][curPoint.y].stench && (myWorld.world[curPoint.x+1][curPoint.y+1].stench && myWorld.world[curPoint.x+1][curPoint.y+1].beenThere))
        {
            
            if(myWorld.world[curPoint.x][curPoint.y+1].beenThere)
            {
                myWorld.world[curPoint.x+1][curPoint.y].wumpus = true;
                direction='E'; //N
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
            else if(myWorld.world[curPoint.x+1][curPoint.y].beenThere)
            {
                myWorld.world[curPoint.x][curPoint.y+1].wumpus = true;
                direction='S'; //E
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
            
        }
        if(myWorld.world[curPoint.x][curPoint.y].stench && (myWorld.world[curPoint.x-1][curPoint.y+1].stench && myWorld.world[curPoint.x-1][curPoint.y+1].beenThere))
        {

            if(myWorld.world[curPoint.x-1][curPoint.y].beenThere)
            {
                myWorld.world[curPoint.x][curPoint.y+1].wumpus = true;
                direction='S'; //E
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
            else if(myWorld.world[curPoint.x][curPoint.y+1].beenThere)
            {
                myWorld.world[curPoint.x-1][curPoint.y].wumpus = true;
                direction='W'; //S
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
        }
        if(myWorld.world[curPoint.x][curPoint.y].stench && (myWorld.world[curPoint.x+1][curPoint.y-1].stench && myWorld.world[curPoint.x+1][curPoint.y-1].beenThere))
        {

            if(myWorld.world[curPoint.x][curPoint.y-1].beenThere)
            {
                myWorld.world[curPoint.x+1][curPoint.y].wumpus = true;
                direction='E'; //N
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
            else if(myWorld.world[curPoint.x+1][curPoint.y].beenThere)
            {
                myWorld.world[curPoint.x][curPoint.y-1].wumpus = true;
                direction='N'; //W
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
        }
        if(myWorld.world[curPoint.x][curPoint.y].stench && (myWorld.world[curPoint.x-1][curPoint.y-1].stench && myWorld.world[curPoint.x-1][curPoint.y-1].beenThere))
        {

            if(myWorld.world[curPoint.x][curPoint.y-1].beenThere)
            {
                myWorld.world[curPoint.x-1][curPoint.y].wumpus = true;
                direction='W'; //S
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
            else if(myWorld.world[curPoint.x-1][curPoint.y].beenThere)
            {
                myWorld.world[curPoint.x][curPoint.y-1].wumpus = true;
                direction='N'; //W
                System.out.println("Shooting " + direction);
                askWorld.shoot(curPoint.x, curPoint.y, direction);
            }
        }
    }
    
    
}
