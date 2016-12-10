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
    char[][] cleanTrack;
    public World(char[][] inTrack, char[][] inTrack2){
        track = inTrack;
        cleanTrack = inTrack2;
    }
    
    //prints out world
    public void print(){
        for(int i = 0; i< track.length; i++){
            for(int j = 0; j< track[0].length; j++){
                System.out.print(track[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //clears the world and remakes it as the clean version
    public void clearTrack(){
        for(int i = 0; i< track.length; i++){
            for(int j = 0; j< track[0].length; j++){
                track[i][j] = cleanTrack[i][j];
            }
        }
    }
    
    //give an array list of all characters on the board asked for
    public ArrayList<int[]> getChar(char get){
        ArrayList<int[]> starts = new ArrayList<int[]>();
        for(int i = 0; i< cleanTrack.length; i++){
            for(int j = 0; j< cleanTrack[0].length; j++){
                if(cleanTrack[i][j] ==get){
                    int[] s = new int[2];
                    s[0]=i;
                    s[1]=j;
                    starts.add(s);
                }
            }
        }
        return starts;
    }
    
    //0=no crash, 1= # crash, 2= F crash(ie win)
    public int crash(int[] p1, int[] p2){
        int[] initP = new int[2];
        initP[0] = p1[0];
        initP[1] = p1[1];
        int[] position = new int[2];
        position[0] = p2[0];
        position[1] = p2[1];
        while(initP[0] != position[0] || initP[1] != position[1]){
            //make one tile movement
            if(initP[0] < position[0]){
                initP[0]++;
            }else if(initP[0] > position[0]){
                initP[0]--;
            }
            if(initP[1] < position[1]){
                initP[1]++;
            }else if(initP[1] > position[1]){
                initP[1]--;
            }
            
            //if over length or lands on # it is a crash
            if(initP[0] >= track.length || initP[1] >= track[0].length || initP[0] < 0 || initP[1] < 0 || track[initP[0]][initP[1]] == '#'){
                return 1;
            }
            //if lands on an F its a win
            else if(track[initP[0]][initP[1]] == 'F'){
                
                return 2;
            }
        }
        // if all else fails then it never crashed. returns 0
        return 0;
    }
}
