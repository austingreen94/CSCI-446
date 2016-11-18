package machinelearning;
import java.util.ArrayList;
/**
 *
 * @author Jordan
 */
public class KNN {
    ArrayList<Node> dataSet = new ArrayList<Node>();
    ArrayList<String>[] attributes;
    ArrayList<Node> trainSet = new ArrayList<Node>();
    ArrayList<Node> testSet = new ArrayList<Node>();
    int classCol = 10;
    int totalCorrect =0;
    int totalWrong =0;
    // given the dataSet and then also generates the overall attribute array of arraylists
    public KNN(ArrayList<Node> newSet){
        dataSet = newSet;
        genAttributes();
    }
    
    //Generates an array of arraylists of the different types of attributes in all of the columns
    public void genAttributes(){
        int numCol =dataSet.get(0).data.length;
        
        //initiallizes all the arraylist from 0 to the amount of columns
        attributes = (ArrayList<String>[])new ArrayList[numCol];
        for(int i =0; i< numCol; i++){
            attributes[i] = new ArrayList<String>();
        }
        
        //populates the array of arraylists with every type of attribute was present
        for(int i =0; i< dataSet.size(); i++){
            for(int j = 0; j<numCol; j++){
                if(!attributes[j].contains(dataSet.get(i).data[j])){
                    attributes[j].add(dataSet.get(i).data[j]);
                }
            }
        }
    }
    
    //prints the whole selected group(test/train/all)
    public void printGroup(ArrayList<Node> allNodes){
        for(int i = 0; i < allNodes.size(); i++){
            allNodes.get(i).print();
        }
        System.out.println();
    }
    
    //splits into 2 groups (train and test)
    public void splitGroups(){
        trainSet.clear();
        testSet.clear();
        trainSet.addAll(dataSet);
        while(testSet.size() < trainSet.size()){
            int rand = (int)(Math.random() * trainSet.size());
            testSet.add(trainSet.get(rand));
            trainSet.remove(rand);
        }
    }
    
    //main running method
    public void run(){
        
        //prints all the attributes of each column (debugging purposes)
        for(int i = 0; i<attributes.length; i++){
            System.out.println("Attribute Col: "+i);
            for(int j=0; j<attributes[i].size(); j++){
                System.out.print(attributes[i].get(j)+",");
            }
            System.out.println();
        }
        
        //perform the runs 5 times (for 5x2)
        for(int i = 0; i<5; i++){
            splitGroups();
            singleRun();
        }
        //print overall results
        System.out.println("total Correct:"+ totalCorrect);
        System.out.println("total Wrong:"+ totalWrong);
    }
    
    //A single Run of the 5x2
    public void singleRun(){
        //choose a k nearest
        int kNear = 5;
        for(int i =0; i< testSet.size(); i++){
            //initialize 2 arrays, one holds indexs, the other the corresponding distances to this 'i'th test set node
            int[] nearests = new int[kNear];
            double[] nearestsDist = new double[kNear];
            for(int k =0;k<kNear; k++){
                nearests[k]=0;
                nearestsDist[k] = Double.MAX_VALUE;
            }
            
            //for each test node, find the nearest k nodes in the training data
            for(int j =0; j<trainSet.size(); j++){
                //computes distances between 'j'th node in the training data and the 'i'th node in the test data
                double newDist = distance(testSet.get(i),trainSet.get(j));
                int largest = 0;
                //finds index of the largest value currently that might be overwritten
                for(int k=1; k<kNear; k++){
                    if(nearestsDist[largest]<nearestsDist[k]){
                        largest= k;
                    }
                }
                
                //replaces the new found distance between this 'j'th node in the training data and the largest distance if the prior is smaller
                if(newDist<nearestsDist[largest]){
                    nearests[largest]=j;
                    nearestsDist[largest]=newDist;
                }
            }
            //prints out the test node data and the few closest
            System.out.println("Test:");
            testSet.get(i).print();
            System.out.println("Closest 3:");
            trainSet.get(nearests[0]).print();
            trainSet.get(nearests[1]).print();
            trainSet.get(nearests[2]).print();
            
            //develops a new array the length of the amount of different attribute types in the class section
            int[] classTypes = new int[attributes[classCol].size()];
            //tallies all the different attributes
            for(int k =0; k< kNear; k++){
                classTypes[attributes[classCol].indexOf(trainSet.get(nearests[k]).data[classCol])]++;
            }
            //chooses the most common class that came up as the guess
            int guess = 0;
            for(int k =1; k< classTypes.length; k++){
                if(classTypes[guess]< classTypes[k]){
                    guess = k;
                }
            }
            //prints and tallies whether the class guess was correct
            System.out.println("Class Guess is "+attributes[classCol].get(guess));
            if(attributes[classCol].get(guess).equals(testSet.get(i).data[classCol])){
                totalCorrect++;
                System.out.println("Correct!");
            }else{
                totalWrong++;
                System.out.println("Wrong!");
            }
        }
    }
    
    //finds the 'distance' between 2 nodes
    //longer description was available in the design document
    public double distance (Node n1, Node n2){
        int j=1;
        int k=2;
        int start = 3;
        //moves j or k if they overlap with the classCol this is never part of distance calculation 
        //(since we cannot know the class of the test node
        if(j == classCol){
            j=3;
            start++;
        }
        else if(k == classCol){
            k=3;
            start++;
        }
        
        // calculates dist as the hypotenuse between these 2 points
        double n1_0 = Double.parseDouble(n1.data[j]);
        double n1_1 = Double.parseDouble(n1.data[k]);
        double n2_0 = Double.parseDouble(n2.data[j]);
        double n2_1 = Double.parseDouble(n2.data[k]);
        double dist = Math.hypot(n1_0-n2_0, n1_1-n2_1);
        
        //loops the hypotenuse calculation using the new triangle created
        //from the last distance and the length between the next 2 data points
        for(int i =start; i<n1.data.length; i++){
            if(i != classCol){
                double n1_i = Double.parseDouble(n1.data[i]);
                double n2_i = Double.parseDouble(n2.data[i]);
                dist = Math.hypot(dist, n1_i-n2_i);
            }
        }
        //returns the dist when all calculations are done
        return dist;
    }
    
}
