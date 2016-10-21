/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author b53t457
 */
public class World {

    //Enter N for size of world
    int n;
    Node[][] world;
    Random random = new Random();
    Scanner sc = new Scanner(System.in);
    boolean goldy = false;
    boolean wumpy = false;
    int min;
    int max;

    public void startworld(int n) {
        this.n=n;
        world = new Node[n + 2][n + 2];
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {

                world[i][j] = new Node();
            }
        }
        min = 0;
        max = n+1;
        for(int i = 0; i<world.length; i++)
        {
            world[min][i].wall = true;
            world[min][i].beenThere = true;
            world[max][i].wall = true;
            world[max][i].beenThere = true;
            world[i][min].wall = true;
            world[i][min].beenThere = true;
            world[i][max].wall = true;
            world[i][max].beenThere = true;
        }
    }
    public void buildworld(int n) {
        
        
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                int bder = random.nextInt(100);
                if(i==1&&j==1){
                    world[i][j].player = true;
                }
                else if (bder < 10 && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false && world[i][j].player==false) {
                    world[i][j].hole = true;
                    world[i-1][j].breeze = true;
                    world[i][j+1].breeze = true;
                    world[i+1][j].breeze = true;
                    world[i][j-1].breeze = true;
                } else if (bder >= 10 && bder < 20 && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false && world[i][j].player==false) {
                    world[i][j].wumpus = true;
                    wumpy = true;
                    world[i][j-1].stench = true;
                    world[i][j+1].stench = true;
                    world[i+1][j].stench = true;
                    world[i-1][j].stench = true;
                } else if (bder >= 20 && bder < 30 && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false && world[i][j].player==false) {
                    world[i][j].wall = true;
                } else if (bder >= 30 && bder < 40 && goldy == false && world[i][j].gold == false && world[i][j].hole == false && world[i][j].wumpus == false && world[i][j].wall == false && world[i][j].player==false) {
                    world[i][j].gold = true;
                    goldy = true;
                    world[i][j].glitter = true;
                }
            }
        }
        if(goldy == false || wumpy == false)
        {
            buildworld(n);
        }
       printworld();
    }
    public void printworld()
    {
         //System.out.println("World is built");
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
                else if(world[i][j].player== true)
                {
                    System.out.print("[P]");
                }
                else if(world[i][j].breeze== true)
                {
                    System.out.print("[B]");
                }
                else if(world[i][j].stench== true)
                {
                    System.out.print("[S]");
                }
                else
                {
                    System.out.print("[X]");
                }
               
            }
            System.out.println();
        }
        System.out.println("gold = G, hole =H, wall = O, wumpus = W, and nothing = X");
    }
     public Node travel(int x, int y) {
         if (world[x][y].wall == true)
         {
             return null;
         }
         else{
            return world[x][y];
         }
    }
     public boolean shoot(int x, int y, char cha)
     {
         if(cha == 'E' || cha == 'e')
         {
             for (int i = x; i<max ; i++)
             {
                 if(world[i][y].wall == true)
                 {
                     return false;
                 }
                 else if(world[i][y].wumpus == true)
                 {
                     return true;
                 }
             }
         }
         else if(cha == 'W' || cha == 'w')
         {
             for (int i = x; i>min ; i--)
             {
                 if(world[i][y].wall == true)
                 {
                     return false;
                 }
                 else if(world[i][y].wumpus == true)
                 {
                     return true;
                 }
             }
         }
         else if(cha == 'S' || cha == 's')
         {
             for (int i = y; i<max ; i++)
             {
                 if(world[x][i].wall == true)
                 {
                     return false;
                 }
                 else if(world[x][i].wumpus == true)
                 {
                     return true;
                 }
             }
         }
         else if(cha == 'N' || cha == 'n')
         {
             for (int i = y; i>min ; i--)
             {
                 if(world[x][i].wall == true)
                 {
                     return false;
                 }
                 else if(world[x][i].wumpus == true)
                 {
                     return true;
                 }
             }
         }
         return false;
     }
     
}