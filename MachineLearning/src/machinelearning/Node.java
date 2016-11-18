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
    public String[] data;
    public ArrayList<String> edges = new ArrayList<String>();
    public int attribute;
    Node[] children;
    String leafClass;
    double decisionPoint = 0;
    
    public Node(){
        children = new Node[2];
    }
    public Node(String[] inData){
        data = inData;
    }
    
    public void print(){
        for(int i=0; i<data.length; i++){
            System.out.print(data[i]+",");
        }
        System.out.println();
    }
    
}
