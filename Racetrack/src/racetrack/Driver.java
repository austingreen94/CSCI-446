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
        //Agent(world, CrashType, Name, [parallel agent])
        //CrashType = 0 : restart at beginning
        //CrashType = 1 : restart at nearest place on track
        Agent a0 = new Agent(w, 1, "completeRestart");
        //Agent a1 = new Agent (w, 1, "nearestRestart", a0);
        a0.run();
    }
    
}
