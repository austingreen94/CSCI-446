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
public class Node {
    double value;
    double reward;
    int pX;
    int pY;
    int vX;
    int vY;
    int actionY;
    int actionX;
    
    public Node(int a, int b, int c, int d){
        pY = a;
        pX = b;
        vY = c;
        vX = d;
    }
    
}
