package machinelearning;

import java.util.ArrayList;


/**
 *
 * @author Austin
 */
public class ID3 {
    ArrayList<Node> dataSet = new ArrayList<Node>();
    ArrayList<String>[] attributes;
    ArrayList<Node> trainSet = new ArrayList<Node>();
    ArrayList<Node> testSet = new ArrayList<Node>();
    int classCol = 10;
    int colIgn = 0;
    int totalCorrect =0;
    int totalWrong =0;
    Node root;
    ArrayList<Integer> unusedAttrCol = new ArrayList<Integer>();
    
    public ID3(ArrayList<Node> newSet){
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
        
        for(int i = 0; i < attributes.length; i++){
            if(i != colIgn && i != classCol){
                unusedAttrCol.add(i);
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
    
    // Method to calculate the entropy of a dataset and return the value
    public double calculateEntropy(ArrayList<Node> dataset){
        double entropy = 0;
        
        int[] positives = new int[attributes[classCol].size()];
        for(int i = 0; i < dataset.size(); i++){
            for(int j = 0; j < attributes[classCol].size(); j++){
                if(dataset.get(i).data[classCol].equals(attributes[classCol].get(j))){
                    positives[j]++;
                }
            }
        }
        
        // Calculates a probability for each of the classes in the dataset parameter
        double[] prob = new double[attributes[classCol].size()];
        for(int i = 0; i < prob.length; i++){
            prob[i] = (double) positives[i] / dataset.size();
        }
        
        // Calculates entropy given each class probability
        for(int i = 0; i < prob.length; i++){
            if(prob[i] <= 0){
                entropy += 0;
            } else{
                entropy += -prob[i] * (Math.log(prob[i]) / Math.log(2));
            }
        }
        return entropy;
    }
    
    // Method to calculate gain; Uses above calculateEntropy method
    public double calculateGain(ArrayList<Node> dataset, int attrCol){
        double gain = 0;
        
        // Adds initial dataset entropy to gain variable
        gain += calculateEntropy(dataset);
        
        // Ignore column
        if(attrCol == colIgn){
            return 0;
        }
        
        // create subset for given attribute column
        for(int i = 0; i < attributes[attrCol].size(); i++){
            ArrayList<Node> subset = new ArrayList<Node>();
            String curChoice = attributes[attrCol].get(i);
            
            for(int j = 0; j < dataset.size(); j++){
                if(dataset.get(j).data[attrCol].equals(curChoice)){
                    subset.add(dataset.get(j));
                }
            }
            
             // Calculates probability of subset 
            double probability = (double) subset.size() / dataset.size();

            // Adds to gain variable based on subset2's entropy
            gain += -probability * calculateEntropy(subset);
        }
        
        // Test print of gain
        System.out.println("Gain: " + gain + " for attribute: " + attrCol);
        return gain;
    }
    
    // Method to create decision tree, will be used recursively
    public Node createTree(ArrayList<Node> dataset, ArrayList<Integer> unused){
        ArrayList<Integer> unusedCopy = unused;
        Node curRoot = new Node();
        int count = 0;
        // All examples are of same class then return single root node
        // with class
        for(int i = 0; i < attributes[classCol].size(); i++){
            for(int j = 0; j < dataset.size(); j++){
                if(dataset.get(j).data[classCol].equals(attributes[classCol].get(i))){ 
                    count++;
                }
            }
            if(count == dataset.size()){
                curRoot.leafClass = attributes[classCol].get(i);
                return curRoot;
            } else {
                count = 0;
            }
        }
        // Dataset is empty
        if(unusedCopy.isEmpty()){
            return curRoot;
        }
        
        // Calcualte max gain of dataset parameter
        double maxGain = Double.MIN_VALUE;
        int maxCol = 0; 
        for(int i = 0; i < unusedCopy.size(); i++){
            double curGain = calculateGain(trainSet, unusedCopy.get(i));
            if(curGain > maxGain){
                maxGain = curGain;
                maxCol = unusedCopy.get(i);
            }
        }
        curRoot.attribute = maxCol;
        
        // Remove attribute for unused attribute arraylist
        for(int i = 0; i < unusedCopy.size(); i++){
            if(unusedCopy.get(i).equals(maxCol)){
                unusedCopy.remove(i);
                break;
            }
        }
        System.out.println("Root Attribute: " + curRoot.attribute);
        
        // Find the median value for the given attribute
        double[] medianArray = new double[dataset.size()];
        for(int i = 0; i < dataset.size(); i++){
            medianArray[i] = Double.parseDouble(dataset.get(i).data[maxCol]);
        }
        double median = medianArray[medianArray.length / 2];
        curRoot.decisionPoint = median;
        System.out.println("Median: " + curRoot.decisionPoint);
        
        
        // Recursive Attempt 1
        
        ArrayList<Node> subset = new ArrayList<Node>();
        ArrayList<Node> subset2 = new ArrayList<Node>();
        for(int i = 0; i < dataset.size(); i++){
            if(Double.parseDouble(dataset.get(i).data[maxCol]) < median){
                subset.add(dataset.get(i));
            } else {
                subset2.add(dataset.get(i));
            }
        }
        System.out.println("Less than Subset Size: " + subset.size());
        System.out.println("Greater than Subset Size: " + subset2.size());
        if(subset.isEmpty()){
            System.out.println("Leaf Node");
        } else {
            curRoot.children[0] = createTree(subset, unusedCopy);
        }
        if(subset2.isEmpty()){
            System.out.println("Leaf Node");
        } else {
            curRoot.children[1] = createTree(subset2, unusedCopy);
        }
        
//        if(subset.isEmpty()){
//            Node leafNode = new Node();
//            leafNode.leafClass = "1";
//            curRoot.children[0] = leafNode;
//            
//        } else {
//            curRoot.children[0] = createTree(subset);
//        }
//        
//        if(dataset.isEmpty()){
//            Node leafNode = new Node();
//            leafNode.leafClass = "0";
//            curRoot.children[1] = leafNode;
//        } else {
//            curRoot.children[1] = createTree(subset2);
//        }
        

        // Recursive attempt 2

//        for(int i = 0; i < curRoot.children.length; i++){
//            if(i == 0){
//                ArrayList<Node> subset = new ArrayList<Node>();
//                for(int j = 0; j < dataset.size(); j++){
//                    if(Double.parseDouble(dataset.get(j).data[maxCol]) < median){
//                        subset.add(dataset.get(j));
//                    }           
//                }
//                System.out.println("Subset Size: " + subset.size());
//                if(subset.isEmpty()){
//                    Node leafNode = new Node();
//                    leafNode.leafClass = "1";
//                    curRoot.children[0] = leafNode;
//                } else {
//                    curRoot.children[0] = createTree(subset);
//                }
//            } else {
//            
//            }
//       }
        return curRoot;
    }
    
    public void traverseTree(Node rootNode){
    
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
        
        splitGroups();
        root = createTree(trainSet, unusedAttrCol);
        traverseTree(root);
        System.out.println("End of ID3");
    }
}
