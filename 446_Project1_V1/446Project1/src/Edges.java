/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KayZeus
 */
public class Edges {
    //Edge will need to know what point it is currently looking at and where it
    //is trying to connect
    int pointIndexA;
    int pointIndexB;    
    
    public Edges(int pointIndexA, int pointIndexB){
        this.pointIndexA = pointIndexA;
        this.pointIndexB = pointIndexB;
    }
    
//    public void setEdge(int pointIndexA, int pointIndexB, int edgeIndex){
//        edge = new Edges(pointIndexA, pointIndexB);
//        edgeArray[edgeIndex] = edge; 
//    }  
    
}
