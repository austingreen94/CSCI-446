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
            if(myWorld.world[curPoint.x][curPoint.y].wumpus || myWorld.world[curPoint.x][curPoint.y].hole){
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
        
        
        return frontier.get(0);
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
            curPoint = moveTo;
            myWorld.world[curPoint.x][curPoint.y].beenThere = true;
            if(frontier.contains(curPoint)){
                frontier.remove(curPoint);
                //checking if we have ever been to or tried to go to all points around current point
                if(myWorld.world[curPoint.x-1][curPoint.y].beenThere == false){
                    addToFrontier(new Point(curPoint.x-1,curPoint.y));
                }
                else if(myWorld.world[curPoint.x+1][curPoint.y].beenThere == false){
                    addToFrontier(new Point(curPoint.x+1,curPoint.y));
                }
                else if(myWorld.world[curPoint.x][curPoint.y-1].beenThere == false){
                    addToFrontier(new Point(curPoint.x,curPoint.y-1));
                }
                else if(myWorld.world[curPoint.x][curPoint.y+1].beenThere == false){
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
    
    
}
