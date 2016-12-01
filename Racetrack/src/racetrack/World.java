/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racetrack;

import java.util.ArrayList;

/**
 *
 * @author Jordan
 */
public class World {
    char[][] track;
    public World(char[][] inTrack){
        track = inTrack;
    }
    public void print(){
        for(int i = 0; i< track.length; i++){
            for(int j = 0; j< track[0].length; j++){
                System.out.print(track[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public int[] getStart(){
        ArrayList<int[]> starts = new ArrayList<int[]>();
        for(int i = 0; i< track.length; i++){
            for(int j = 0; j< track[0].length; j++){
                if(track[i][j] =='S'){
                    int[] s = new int[2];
                    s[0]=i;
                    s[1]=j;
                    starts.add(s);
                }
            }
        }
        int rand = (int)(Math.random()*starts.size());
        return starts.get(rand);
    }
}
