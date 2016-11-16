package project3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Austin
 */
public class ID3 {
    // Nodes used for constructing the decision tree
    Node root = new Node();
    Node curNode = new Node();
    // Hardcoded string attributes for house votes data
    // Will move into ReadIn class and call in as ID3 parameter
    String[] houseVoteAttributes = {"Name", "Infants", "Water", "Budget",
    "Physician", "Salvador", "Religious", "Satellite", "Nicaraguan", "Missle",
    "Immigration", "Synfuels", "Education", "Superfund", "Crime", "Exports", "ExportSA"};
    // class choices for house votes data
    String[] hVClasses = {"democrat", "republican"};
    // Ignore for now
    String[][] curDataset;
    
    public ID3(String[][] dataset){
        curDataset = dataset;
    }
    
    // Method to calculate the entropy of a dataset and return the value
    public double calculateEntropy(String[][] dataset){
        double entropy = 0;

        // Calculates all data section with a certain class; Loops through all
        // the class choices
        int[] positives = new int[hVClasses.length];
        for(int i = 0; i < dataset.length; i++){
            for(int j = 0; j < hVClasses.length; j++){
                if(dataset[i][0].equals(hVClasses[j])){
                    positives[j]++;
                }
            }
        }

        // Calculates a probability for each of the classes in the dataset parameter
        double[] prob = new double[hVClasses.length];
        for(int i = 0; i < prob.length; i++){
            prob[i] = (double) positives[i] / dataset.length;
        }
        
        // Calculates entropy given each class probability
        for(int i = 0; i < prob.length; i++){
            if(prob[i] == 0){
                entropy += 0;
            } else{
                entropy += -prob[i] * (Math.log(prob[i]) / Math.log(2));
            }
        }
        
        return entropy;
    }
    
    // Method to calculate gain; Uses above calculateEntropy method
    public double calculateGain(String[][] dataset, String attribute){
        int attrNum = 0;
        double gain = 0;
        // Searches through houseVoteAttributes list and sets attrNum to given
        // column number in the dataset
        for(int i = 0; i < houseVoteAttributes.length; i++){
            if(houseVoteAttributes[i].equals(attribute)){
                attrNum = i;
                break;
            }
        }
        // Test print
        System.out.println("Attribute Column Number: " + attrNum);
        // Loops through the dataset and evaluates the number of choices for the
        // given attribute; ('n' or 'y' in example)
        List<String> attrChoices = new ArrayList<>();
        attrChoices.add(dataset[0][attrNum]);
        for(int i = 1; i < dataset.length; i++){
            boolean found = false;
            for(int j = 0; j < attrChoices.size(); j++){
                if(attrChoices.get(j).equals(dataset[i][attrNum])){
                    found = true;
                }
            }
            if(!found){
                attrChoices.add(dataset[i][attrNum]);
            }
        }
        System.out.println("Size of attribute choice list: " + attrChoices.size());
        
        // Adds initial dataset entropy to gain variable
        gain += calculateEntropy(dataset);
        
        // Loops through attrChoice list and creates subsets 
        for(int i = 0; i < attrChoices.size(); i++){
            String curChoice = attrChoices.get(i);
            // New subset based on given attribute choice
            List<String[]> subset = new ArrayList<>();
            for(int j = 0; j < dataset.length; j++){
                if(dataset[j][attrNum].equals(curChoice)){
                    subset.add(dataset[j]);
                }
            }
            // Converts subset list in subset 2d array
            String[][] subset2 = new String[subset.size()][17];
            for(int j = 0; j < subset.size(); j++){
                subset2[j] = subset.get(j);
            }
            
            // Calculates probability of subset 
            double probability = (double) subset.size() / dataset.length;

            // Adds to gain variable based on subset2's entropy
            gain += -probability * calculateEntropy(subset2);
        }
        // Test print of gain
        System.out.println("Gain: " + gain + " for attribute: " + attribute);
        return gain;
    }
}
