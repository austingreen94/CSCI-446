package racetrack;

import java.util.ArrayList;
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

    public Agent(World w, int crash, String n) {
        world = w;
        crashType = crash;
        name = n;
    }

    public Agent(World w, int crash, String n, Agent a) {
        world = w;
        crashType = crash;
        name = n;
        parallelA = a;
    }

    public void run() {
        //training runs
        ArrayList<int[]> s = world.getChar('F');
        s.clear();
        int[] testing = new int[2];
        testing[0]=5;
        testing[1] =5;
        s.add(testing);
        s = newStartPos(s);
        System.out.println("size:" +s.size());
        for (int i = 0; i < s.size(); i++) {
            world.track[s.get(i)[0]][s.get(i)[1]] = 'T';
        }
        //final test run
        startPos();
//        position[0] = 3;
//        position[1] = 34;
//        velocity[0] = -5;
//        velocity[1] = 0;
        if (parallelA != null) {
            parallelA.startPos();
        }
        System.out.println("Initial World:");
        world.print();
        int tickNum = 1;
        while (true) {
            System.out.println("Tick Number: " + tickNum);
            tick();
            if (parallelA != null) {
                parallelA.tick();
            }
            tickNum++;
        }
    }

    public ArrayList<int[]> newStartPos(ArrayList<int[]> lastStart) {
        ArrayList<int[]> newStartPos = new ArrayList<int[]>();
        int d = 3;
        for (int i = 0; i < lastStart.size(); i++) {
            
            int test = 0;
            for (int j = 0; j <= d; j++) {
                for (int k = -1; k <= 1; k += 2) {
                    for (int k2 = -1; k2 <= 1; k2 += 2) {
                        int[] pos = new int[2];
                        pos[0] = lastStart.get(i)[0] + (3 * k);
                        pos[1] = lastStart.get(i)[1] + (j * k2);
                        System.out.println((pos[0]-lastStart.get(i)[0]) +" " +(pos[1]-lastStart.get(i)[1]));
                        //test = world.crash(pos, lastStart.get(i));
                        //if (test == 0 ) {
                            newStartPos.add(pos);
                        //}

                        pos[0] = lastStart.get(i)[0] + (j * k2);
                        pos[1] = lastStart.get(i)[1] + (3 * k);
                        //test = world.crash(pos, lastStart.get(i));
                        //if (test == 0 ) {
                            newStartPos.add(pos);
                        //}
                    }
                }

            }
            //int[] pos = lastStart.get(i);

            //addPositions();
//             removePositions();
        }

        return newStartPos;
    }

    public void startPos() {
        ArrayList<int[]> s = world.getChar('S');
        int rand = (int) (Math.random() * s.size());
        initP = s.get(rand);
        position[0] = initP[0];
        position[1] = initP[1];
        world.track[position[0]][position[1]] = 'A';
    }

    public void tick() {

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
        System.out.println("AccX: " + acceleration[1] + " AccY: " + acceleration[0]);
        System.out.println("VelX: " + velocity[1] + " VelY: " + velocity[0]);
        world.print();
    }

    public void limitVelocity() {
        if (velocity[0] > 5) {
            velocity[0] = 5;
        } else if (velocity[0] < -5) {
            velocity[0] = -5;
        }
        if (velocity[1] > 5) {
            velocity[1] = 5;
        } else if (velocity[1] < -5) {
            velocity[1] = -5;
        }
    }

    public void changePos() {
        world.track[position[0]][position[1]] = 'X';
        position[0] += velocity[0];
        position[1] += velocity[1];

        //0=no crash, 1= # crash, 2= F crash(ie win)
        int[] initP = new int[2];
        initP[0] = position[0] - velocity[0];
        initP[1] = position[1] - velocity[1];
        int c = world.crash(initP, position);
        if (c == 2) {
            System.out.println("FINISH LINE!!");
            System.out.println(name + " Won!!");
            world.print();
            System.exit(0);
        } else if (c == 1) {
            System.out.println("CRASH OF: " + name);
            System.out.println("AccX: " + acceleration[1] + " AccY: " + acceleration[0]);
            System.out.println("VelX: " + velocity[1] + " VelY: " + velocity[0]);
            if (crashType == 0) {
                crashHardRestart();
            } else {
                crashSoftRestart();
            }
        }
        world.track[position[0]][position[1]] = 'A';
    }

    public void crashHardRestart() {
        velocity[0] = 0;
        velocity[1] = 0;
        position[0] = initP[0];
        position[1] = initP[1];
    }

    public void crashSoftRestart() {
        position[0] = position[0] - velocity[0];
        position[1] = position[1] - velocity[1];
        velocity[0] = 0;
        velocity[1] = 0;
    }

    public void changeAcc() {
        //allow acceleration 80% of the time
        if (Math.random() < .8) {
            acceleration[0] = (int) (Math.random() * 3) - 1;
            acceleration[1] = (int) (Math.random() * 3) - 1;
        } else {
            acceleration[0] = 0;
            acceleration[1] = 0;
            System.out.println("Failed to accelerate");
        }

    }
}
