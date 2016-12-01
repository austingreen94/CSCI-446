package racetrack;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Agent {
    World world;
    //y,x coords
    int[] position = new int[2];
    //y,x velocities
    int[] velocity = new int[2];
    //y,x accelerations
    int[] acceleration = new int[2];
    int crashType;
    Agent parallelA = null;
    String name;
    int[] initP = new int[2];
    
    public Agent(World w, int crash, String n){
        world = w;
        crashType = crash;
        name =n;
    }
    public Agent(World w, int crash, String n, Agent a){
        world = w;
        crashType = crash;
        name = n;
        parallelA = a;
    }
    
    public void run(){
        startPos();
        if(parallelA != null){
            parallelA.startPos();
        }
        System.out.println("Initial World:");
        world.print();
        int tickNum = 1;
        while(true){
            System.out.println("Tick Number: "+tickNum);
            tick();
            if(parallelA != null){
                parallelA.tick();
            }
            tickNum++;
        }
    }
    public void startPos(){
        initP = world.getStart();
        position[0] = initP[0];
        position[1] = initP[1];
        world.track[position[0]][position[1]] = 'A';
    }
    public void tick(){
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        changeAcc();
        velocity[0] += acceleration[0];
        velocity[1] += acceleration[1];
        limitVelocity();
        changePos();
        
        //print stats every tick
        System.out.println(name);
        System.out.println("AccX: "+acceleration[1] + " AccY: "+acceleration[0]);
        System.out.println("VelX: "+velocity[1] + " VelY: "+velocity[0]);
        world.print();
    }
    public void limitVelocity(){
        if(velocity[0] > 5){
            velocity[0] = 5;
        }
        else if(velocity[0] < -5){
            velocity[0] = -5;
        }
        if(velocity[1] > 5){
            velocity[1] = 5;
        }
        else if(velocity[1] < -5){
            velocity[1] = -5;
        }
    }
    public void changePos(){
        world.track[position[0]][position[1]] = 'X';
        position[0] += velocity[0];
        position[1] += velocity[1];
        crash();
        if(world.track[position[0]][position[1]] == 'F'){
            System.out.println("FINISH LINE!!");
            System.out.println(name + " Won!!");
            System.exit(0);
        }
        world.track[position[0]][position[1]] = 'A';
    }
    public void crash(){
        if(position[0] >= world.track.length || position[1] >= world.track[0].length || position[0] < 0 || position[1] < 0 || world.track[position[0]][position[1]] == '#'){
            System.out.println("CRASH OF: "+name);
            System.out.println("AccX: "+acceleration[1] + " AccY: "+acceleration[0]);
            System.out.println("VelX: "+velocity[1] + " VelY: "+velocity[0]);
            System.out.println("Crash at " + position[1] + " "+ position[0]);
            if(crashType == 0){
                crashHardRestart();
            } else{
                crashSoftRestart();
            }
        }
    }
    public void crashHardRestart(){
        velocity[0] = 0;
        velocity[1] = 0;
        position[0] = initP[0];
        position[1] = initP[1];
    }
    public void crashSoftRestart(){
        position[0] = position[0]-velocity[0];
        position[1] = position[1]-velocity[1];
        velocity[0] = 0;
        velocity[1] = 0;
    }
    public void changeAcc(){
        //allow acceleration 80% of the time
        if(Math.random()< .8){
            acceleration[0] = (int)(Math.random()*3)-1;
            acceleration[1] = (int)(Math.random()*3)-1;
        }
        else{
            acceleration[0] = 0;
            acceleration[1] = 0;
            System.out.println("Failed to accelerate");
        }
        
    }
}
