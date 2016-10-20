/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

import java.util.Scanner;

/**
 *
 * @author manam
 */
public class WumpusWorld {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        World world = new World();
        System.out.println("insert size of world as a square");
        int n = sc.nextInt();
        world.startworld(n);
        world.buildworld(n);
    }
    
}
