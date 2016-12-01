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
public class Driver {

    public static void main(String[] args) {
        char[][] map = ReadIn.read("L-track.txt");
        World w = new World(map);
        World copyW = w;
        //w.print();
        //Agent(world, CrashType, Name
        //CrashType = 0 : restart at beginning
        //CrashType = 1 : restart at nearest place on track
        Agent a0 = new Agent(w, 0, "completeRestart");
        Agent a1 = new Agent (copyW, 1, "nearestRestart", a0);
        a1.run();
    }
    
}
