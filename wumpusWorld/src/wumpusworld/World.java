
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
    node[][] world = new node[n+2][n+2];
    Random random = new Random();
    public void startworld()
    {
        for(int i = 0; i<n+2; i++)
        {
            for(int j = 0; j<n+2; j++)
            {
                
                world[i][j] = new node();
            }
        }
    }
    public void hole()
    {
        int holex = random.nextInt(n)+1;
        int holey = random.nextInt(n)+1;
        
        //world[holex][holey].gold = true;
        if(world[holex][holey].gold == false && world[holex][holey].wumpus == false && world[holex][holey].wall == false)
        {
        System.out.println("hole location is : "+holex + " " + holey);
        world[holex][holey].hole = true;
        //System.out.println(world[holex][holey].hole);
        }
        else
        {
            System.out.println("hole cannot be placed at : " + holex + " " + holey);
            hole();
        }
    }
    public void wumpus()
    {
                int wump_x = random.nextInt(n)+1;
        int wump_y = random.nextInt(n)+1;
        
        if(world[wump_x][wump_y].gold == false && world[wump_x][wump_y].hole == false && world[wump_x][wump_y].wall == false)
        {
            System.out.println("wumpus location is : "+wump_x + " " + wump_y);
            world[wump_x][wump_y].wumpus = true;
            //System.out.println(world[wump_x][wump_y].wumpus);
        }    
        else
        {
            System.out.println("wumpus cannot be placed at : " +wump_x + " " + wump_y);
            wumpus();
        }
    }
    public void wall()
    {
                int obsx = random.nextInt(n)+1;
        int obsy = random.nextInt(n)+1;
        
        if(world[obsx][obsy].gold == false && world[obsx][obsy].wumpus == false && world[obsx][obsy].hole == false)
        {
            System.out.println("wall location is : "+ obsx + " " + obsy);
            world[obsx][obsy].wall = true;
            //System.out.println(world[obsx][obsy].wall);
        }  
        else
        {
            System.out.println("wall cannot be placed at : " + obsx + " " + obsy);
            wall();
        }
    }
    public void gold()
    {
        int goldx = random.nextInt(n)+1;
        int goldy = random.nextInt(n)+1;
        
        if(world[goldx][goldy].hole == false && world[goldx][goldy].wumpus == false && world[goldx][goldy].wall == false)
        {
        System.out.println("gold location is : " +goldx + " " + goldy);    
        world[goldx][goldy].gold = true;
        //System.out.println(world[goldx][goldy].gold);
        }
        else
        {
            System.out.println("gold cannot be placed at : " +goldx + " " + goldy);
            gold();
        }
    }
    
    public void buildworld()
    {
        hole();
        wumpus();
        wall();
        gold();

            System.out.println("World is built");
        }
}
