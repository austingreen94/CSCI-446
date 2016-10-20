/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Jordan
 */
public class Agent {
    World askWorld;
    World myWorld = new World();
    int score = 0;
    int curX = 1;
    int curY = 1;
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
        frontier.add(new Point(1,2));
        frontier.add(new Point(2,1));
        
        while(!myWorld.world[curX][curY].glitter){
            
            attemptMove();
            
        }
        System.out.println("You found the gold!");
        askWorld.print();
        return score;
    }
    
    public void attemptMove(){
        
        int x = curX+1;
        int y = curY;
        
        Node newMove = askWorld.travel(x,y);
        if(newMove == null){
            //bump
            myWorld.world[x][y].wall = true;
        }else{
            myWorld.world[x][y] = newMove;
            myWorld.world[curX][curY].player = false;
            askWorld.world[curX][curY].player = false;
            myWorld.world[x][y].player = true;
            askWorld.world[x][y].player = true;
            curY = y;
            curX = x;
        }
    }
    
    
}
