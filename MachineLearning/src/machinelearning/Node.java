/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import java.util.ArrayList;

/**
 *
 * @author Jordan
 */
public class Node {
    //array of data points (size determined later)
    public String[] data;
    //arraylist of all the edge this node has with others
    public ArrayList<String> edges = new ArrayList<String>();
    public int attribute;
    Node[] children;
    String leafClass;
    double decisionPoint = 0;
    
    public Node(){
        
    }
    public Node(String[] inData){
        data = inData;
    }
    
    //prints out all the elements in this array of data on a single line
    public void print(){
        for(int i=0; i<data.length; i++){
            System.out.print(data[i]+",");
        }
        System.out.println();
    }
    
}
