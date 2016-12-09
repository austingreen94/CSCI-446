/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racetrack;

/**
 *
 * @author Jordan
 */
public class Table {
    //y, x, Vy, Vx coords
    //actualV -> V[actualV - 5]
    //V[0] -> actualV = -5, V[5] -> actualV = 0, V[10] -> actualV = +5
    public Node[][][][] states;
    
    public Table(int inY, int inX){
        states = new Node[inY][inX][11][11];
    }
    
    
}
