package racetrack;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Agent {

    char agentTrail;
    char agentNum;
    
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
    
    ArrayList<int[]> alreadyTested = new ArrayList<int[]>();
    Table table;

    public Agent(World w, int crash, String n) {
        world = w;
        crashType = crash;
        name = n;
        table = new Table(world.track.length, world.track[0].length);
        agentTrail ='X';
        agentNum ='1';
    }

    public Agent(World w, int crash, String n, Agent a) {
        world = w;
        crashType = crash;
        name = n;
        parallelA = a;
        table = new Table(world.track.length, world.track[0].length);
        agentTrail ='Y';
        agentNum ='2';
    }

    public void run() {
        //VI training runs
        world.initializeTable(table);
        if (parallelA != null) {
            parallelA.world.initializeTable(parallelA.table);
        }
        for(int i=0; i<100; i++){
            training(0);
            alreadyTested.clear();
            if (parallelA != null) {
                parallelA.training(0);
                parallelA.alreadyTested.clear();
            }
        }
        //test run
        startPos();
        if (parallelA != null) {
            parallelA.startPos();
        }
        System.out.println("VI Test Run:");
        int tickNum = 1;
        boolean loop =true;
        while (loop) {
            System.out.println("Tick Number: " + tickNum);
            loop = tick();
            if (parallelA != null) {
                loop = loop && parallelA.tick();
            }
            world.print();
            tickNum++;
        }
        
        //train qlearning
        world.initializeTable(table);
        if (parallelA != null) {
            parallelA.world.initializeTable(parallelA.table);
        }
        for(int i=0; i<100; i++){
            training(1);
            alreadyTested.clear();
            if (parallelA != null) {
                parallelA.training(1);
                parallelA.alreadyTested.clear();
            }
        }
        
        //test run
        startPos();
        if (parallelA != null) {
            parallelA.startPos();
        }
        System.out.println("Q Test Run:");
        tickNum = 1;
        loop =true;
        while (loop) {
            System.out.println("Tick Number: " + tickNum);
            loop = tick();
            if (parallelA != null) {
                loop = loop && parallelA.tick();
            }
            world.print();
            tickNum++;
        }
    }
    
    public void training(int trainType){
        //gets an arraylist of all finish line coords
        ArrayList<int[]> s = world.getChar('F');
        alreadyTested.addAll(s);
        int num =0;
        while(s.size()>0){
            //moves new starting positions
            s = newStartPos(s);
            alreadyTested.addAll(s);
            for (int i = 0; i < s.size(); i++) {
                //changes world for visualization printing
                String str =Integer.toString(num);
                world.track[s.get(i)[0]][s.get(i)[1]] = str.charAt(str.length()-1);
            }
            //trains with these new positions
            train(s,trainType);
            num++;
            //System.out.println("training world visualization:");
            //world.print();
        }
        world.clearTrack();
    }
    
    //update new list of locations to train, starting at finish line and moving back by d=1 every time
    public ArrayList<int[]> newStartPos(ArrayList<int[]> lastStart) {
        ArrayList<int[]> newStartPos = new ArrayList<int[]>();
        int d = 1;
        
        //find all points in a d=1 size square from every point in the old list
        for (int i = 0; i < lastStart.size(); i++) {
            int test = 0;
            for (int j = 0; j <= d; j++) {
                for (int k = -1; k <= 1; k += 2) {
                    for (int k2 = -1; k2 <= 1; k2 += 2) {
                        int[] pos = new int[2];
                        pos[0] = lastStart.get(i)[0] + (d * k);
                        pos[1] = lastStart.get(i)[1] + (j * k2);
                        
                        //only add coords if theres no crash between these points(trying to cut corners)
                        test = world.crash(lastStart.get(i), pos);
                        //only add coords if it is not already in the list & isnt too close to any initial coords
                        if (test == 0 && !contains(newStartPos, pos) && !contains(alreadyTested, pos) && !tooClose(lastStart, pos, d)) {
                            newStartPos.add(pos);
                        }
                        
                        //does same with opposite coords
                        int[] pos2 = new int[2];
                        pos2[0] = lastStart.get(i)[0] + (j * k2);
                        pos2[1] = lastStart.get(i)[1] + (d * k);
                        test = world.crash(lastStart.get(i), pos2);
                        if (test == 0 && !contains(newStartPos, pos2) && !contains(alreadyTested, pos2) && !tooClose(lastStart, pos2, d)) {
                            newStartPos.add(pos2);
                        }
                    }
                }
            }
        }

        return newStartPos;
    }
    
    //used from newStartPos. Finds if some point is already in this arraylist
    public boolean contains(ArrayList<int[]> list, int[] point){
        for(int i=0; i< list.size(); i++){
            if(list.get(i)[0] == point[0] && list.get(i)[1] == point[1]){
                return true;
            }
        }
        return false;
    }
    
    //used from newStartPos. tests to see if some point is closer than it should be to any point in the old list
    public boolean tooClose(ArrayList<int[]> list, int[] point, int d){
        for(int i=0; i< list.size(); i++){
            if(Math.hypot((double)(list.get(i)[0] - point[0]), (double)(list.get(i)[1] - point[1])) <d ){
                return true;
            }
        }
        return false;
    }
    
    //trains with all these individual states
    public void train(ArrayList<int[]> positions, int trainType){
        for(int i=0; i< positions.size(); i++){
            trainPoint(positions.get(i), trainType);
        }
    }
    
    //performs VI or Qlearning
    public void trainPoint(int[] p, int trainType){
        for(int i=0; i< table.states[0][0].length; i++){
            for(int j=0; j< table.states[0][0][0].length; j++){
                if(trainType ==0){
                    trainStateVI(table.states[p[0]][p[1]][i][j]);
                }else{
                    trainStateQ(table.states[p[0]][p[1]][i][j]);
                }
                
            }
        }
    }
    
    //VI function
    public void trainStateVI(Node node){
        //initialize many variables
        double discount = .80;
        //-5 is the value of a crash (default is worst case senario
        double maxVal = -5;
        double newVal = -5;
        double newAccVal = 0;
        double failAccVal = 0;
        int[] p1 = new int[2];
        p1[0]= node.pY;
        p1[1]= node.pX;
        int[] p2 = new int[2];
        int[] p3 = new int[2];
        int actY =0;
        int actX =0;
        for(int aY=-1; aY<=1; aY++){
            for(int aX=-1; aX<=1; aX++){
                p2[0] = node.pY + node.vY+aY;
                p2[1] = node.pX + node.vX+aX;
                p3[0] = node.pY + node.vY;
                p3[1] = node.pX + node.vX;
                
                //do not calculate if the new velocity goes over 5 or under -5
                //Also dont calculate a v=0 and a=0 state (will stay put and no point)
                if((node.vY+aY) >= -5 && (node.vY+aY) <= 5 && (node.vX+aX) >= -5 && (node.vX+aX) <=5 && (p2[0] != p1[0] || p2[1] != p1[1])){
                    //if there is no crash then calculate the value normally
                    if(world.crash(p1, p2) == 0 && world.crash(p1, p3) == 0 ){
                        //System.out.println("["+(node.pY + node.vY+aY)+"]["+(node.pX + node.vX+aX)+"]["+(node.vY+aY+5)+"]["+(node.vX+aX+5)+"]");
                        newAccVal= table.states[node.pY + node.vY+aY][node.pX + node.vX+aX][node.vY+aY+5][node.vX+aX+5].value;
                        failAccVal = table.states[node.pY + node.vY][node.pX + node.vX][node.vY+5][node.vX+5].value;
                    }
                    //if a crash into the finish line then set to a value of 100(value of win state)
                    else if(world.crash(p1, p2) == 2 && world.crash(p1, p3) == 2 ){
                        newAccVal= 100;
                        failAccVal= 100;
                    }
                    //calculate VI value of this state
                    newVal = discount*(.8*newAccVal + .2* failAccVal);
                    //update the max value on this state
                    if(newVal> maxVal ){
                        maxVal = newVal;
                        actY = aY;
                        actX = aX;
                    }
                }
            }
        }
        //set new value of state to max value calculated
        node.value = maxVal;
        node.actionX = actX;
        node.actionY = actY;
    }
    
    //VI function
    public void trainStateQ(Node node){
        //initialize many variables
        double reward = 0;
        double learningRate =1;
        double discount = .95;
        double maxVal = -5;
        double newVal =-5;
        double newAccVal =0;
        double failAccVal =0;
        int[] p1 = new int[2];
        p1[0]= node.pY;
        p1[1]= node.pX;
        int[] p2 = new int[2];
        int[] p3 = new int[2];
        int actY =0;
        int actX =0;
        for(int aY=-1; aY<=1; aY++){
            for(int aX=-1; aX<=1; aX++){
                p2[0] = node.pY + node.vY+aY;
                p2[1] = node.pX + node.vX+aX;
                p3[0] = node.pY + node.vY;
                p3[1] = node.pX + node.vX;
                
                //do not calculate if the new velocity goes over 5 or under -5
                //Also dont calculate a v=0 and a=0 state (will stay put and no point)
                if((node.vY+aY) >= -5 && (node.vY+aY) <= 5 && (node.vX+aX) >= -5 && (node.vX+aX) <=5 && (p2[0] != p1[0] || p2[1] != p1[1])){
                    //if there is no crash then calculate the value normally
                    if(world.crash(p1, p2) == 0 && world.crash(p1, p3) == 0 ){
                        //System.out.println("["+(node.pY + node.vY+aY)+"]["+(node.pX + node.vX+aX)+"]["+(node.vY+aY+5)+"]["+(node.vX+aX+5)+"]");
                        newAccVal= table.states[node.pY + node.vY+aY][node.pX + node.vX+aX][node.vY+aY+5][node.vX+aX+5].value;
                        failAccVal = table.states[node.pY + node.vY][node.pX + node.vX][node.vY+5][node.vX+5].value;
                        reward = table.states[node.pY + node.vY+aY][node.pX + node.vX+aX][node.vY+aY+5][node.vX+aX+5].reward;
                    }
                    //if a crash into the finish line then set to a value of 100(value of win state)
                    else if(world.crash(p1, p2) == 2 && world.crash(p1, p3) == 2 ){
                        newAccVal= 100;
                        failAccVal= 100;//fail to accelerate should also bring past finish line since crash type
                        //already was tested and didnt crash into a #
                        reward = 100;
                    }
                    //calculate VI value of this state
                    newVal = discount*(.8*newAccVal + .2* failAccVal);
                    //update the max value on this state
                    if(newVal> maxVal ){
                        maxVal = newVal;
                        actY = aY;
                        actX = aX;
                    }
                }
            }
        }
        //set new value of state to max value calculated using q Learning
        node.value = node.value + learningRate *(reward + maxVal - node.value);
        node.actionX = actX;
        node.actionY = actY;
    }

    //finds a single spot to be the starting spot
    public void startPos() {
        ArrayList<int[]> s = world.getChar('S');
        int rand = (int) (Math.random() * s.size());
        initP = s.get(rand);
        position[0] = initP[0];
        position[1] = initP[1];
        world.track[position[0]][position[1]] = 'A';
    }

    //a single tick of a move
    public boolean tick() {

        //wait for .1 seconds to be easier to see
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
        // change acceleration and update velocity
        changeAcc();
        velocity[0] += acceleration[0];
        velocity[1] += acceleration[1];
        limitVelocity();
        // loop changed to false if finish line crossed
        boolean loop = changePos();

        //print stats every tick
        System.out.println(name);
        System.out.println("AccX: " + acceleration[1] + " AccY: " + acceleration[0]);
        System.out.println("VelX: " + velocity[1] + " VelY: " + velocity[0]);
//        world.print();
        return loop;
    }

    //limits velocity
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

    //changes position of agent, returns true if finish line crossed
    public boolean changePos() {
        world.track[position[0]][position[1]] = agentTrail;
        position[0] += velocity[0];
        position[1] += velocity[1];

        int[] initP = new int[2];
        initP[0] = position[0] - velocity[0];
        initP[1] = position[1] - velocity[1];
        //0=no crash, 1= # crash, 2= F crash(ie win)
        int c = world.crash(initP, position);
        if (c == 2) {
            System.out.println("FINISH LINE!!");
            System.out.println(name + " Won!!");
            return false;
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
        //update agent location
        world.track[position[0]][position[1]] = agentNum;
        return true;
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
//            acceleration[0] = (int) (Math.random() * 3) - 1;
//            acceleration[1] = (int) (Math.random() * 3) - 1;
            acceleration[0] = table.states[position[0]][position[1]][velocity[0]+5][velocity[1]+5].actionY;
            acceleration[1] = table.states[position[0]][position[1]][velocity[0]+5][velocity[1]+5].actionX;
            //System.out.println(table.states[position[0]][position[1]][velocity[0]][velocity[1]].value);
        } else {
            acceleration[0] = 0;
            acceleration[1] = 0;
            System.out.println("Failed to accelerate");
        }

    }
}
