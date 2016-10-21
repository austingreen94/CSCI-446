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
        Point frontierTarget;
        
        while(!myWorld.world[curPoint.x][curPoint.y].glitter){
            frontierTarget = pickFrontier();
            
            attemptMove(frontierTarget);
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
