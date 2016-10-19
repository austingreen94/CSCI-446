
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.Random;

/**
 *
 * @author b53t457
 */
public class World {

    //Enter N for size of world
    int n = 5;
    node[][] world = new node[n + 2][n + 2];
    Random random = new Random();
    boolean goldy = false;
    boolean wumpy = false;
    int min = 0;
    int max = n+1;

    public void startworld() {
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {

                world[i][j] = new node();
            }
        }
    }
    public void buildworld() {
        for(int i = 0; i<world.length; i++)
        {
            world[min][i].wall = true;
            world[max][i].wall = true;
            world[i][min].wall = true;
            world[i][max].wall = true;
        }
        
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                int bder = random.nextInt(100);
                if (bder < 10 && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false) {
                    world[i][j].hole = true;
                } else if (bder >= 10 && bder < 20 && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false) {
                    world[i][j].wumpus = true;
                    wumpy = true;
                } else if (bder >= 20 && bder < 30 && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false) {
                    world[i][j].wall = true;
                } else if (bder >= 30 && bder < 40 && goldy == false && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false) {
                    world[i][j].gold = true;
                    goldy = true;
                }
            }
        }
        if(goldy == false || wumpy == false)
        {
            buildworld();
        }
        System.out.println("World is built");
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                if(world[i][j].gold== true)
                {
                    System.out.print("[G]");
                }
                else if(world[i][j].hole== true)
                {
                    System.out.print("[H]");
                }
                else if(world[i][j].wumpus== true)
                {
                    System.out.print("[W]");
                }
                else if(world[i][j].wall== true)
                {
                    System.out.print("[O]");
                }
                else
                {
                    System.out.print("[X]");
                }
                
                //System.out.print("[" +(world[i][j].gold) +", " + (world[i][j].hole) +", " + (world[i][j].wall) +", " + (world[i][j].wumpus) + "]");
            }
            System.out.println();
        }
    }
}
