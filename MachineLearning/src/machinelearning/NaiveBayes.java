/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package machinelearning;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jordan
 */
public class NaiveBayes {

    Random rn = new Random();
    ArrayList<Node> dataSet = new ArrayList<Node>();
    ArrayList<String>[] attributes;
    ArrayList<Node> trainSet = new ArrayList<Node>();
    ArrayList<Node> testSet = new ArrayList<Node>();
    double[] classNum;
    int classCol = 0;
    int ignoredCol = 0;
    int totalCorrect = 0;
    int totalWrong = 0;

    // given the dataSet and then also generates the overall attribute array of arraylists
    public NaiveBayes(ArrayList<Node> newSet, int inClassCol, int inSkip) {
        dataSet = newSet;
        classCol = inClassCol;
        ignoredCol = inSkip;
        genAttributes();
    }

    //Generates an array of arraylists of the different types of attributes in all of the columns
    public void genAttributes() {
        int numCol = dataSet.get(0).data.length;

        //initiallizes all the arraylist from 0 to the amount of columns
        attributes = (ArrayList<String>[]) new ArrayList[numCol];
        for (int i = 0; i < numCol; i++) {
            attributes[i] = new ArrayList<String>();
        }

        //populates the array of arraylists with every type of attribute was present
        for (int i = 0; i < dataSet.size(); i++) {
            for (int j = 0; j < numCol; j++) {
                if (!attributes[j].contains(dataSet.get(i).data[j])) {
                    attributes[j].add(dataSet.get(i).data[j]);
                }
            }
        }
    }

    //prints the whole selected group(test/train/all)
    public void printGroup(ArrayList<Node> allNodes) {
        for (int i = 0; i < allNodes.size(); i++) {
            allNodes.get(i).print();
        }
        System.out.println();
    }

    //splits into 2 groups (train and test)
    public void splitGroups() {
        trainSet.clear();
        testSet.clear();
        trainSet.addAll(dataSet);
        while (testSet.size() < trainSet.size()) {
            int rand = (int) (Math.random() * trainSet.size());
            testSet.add(trainSet.get(rand));
            trainSet.remove(rand);
        }
    }

    //main running method
    public void run() {

        //prints all the attributes of each column (debugging purposes)
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("Attribute Col: " + i);
            for (int j = 0; j < attributes[i].size(); j++) {
                System.out.print(attributes[i].get(j) + ",");
            }
            System.out.println();
        }
        //perform the runs 5 times (for 5x2)
        for (int i = 0; i < 5; i++) {
            splitGroups();
            singleRun();
        }
        //print overall results
        System.out.println("total Correct:" + totalCorrect);
        System.out.println("total Wrong:" + totalWrong);
    }

    //A single Run of the 5x2
    public void singleRun() {
        percents();
    }

    public void percents() {
        classNum = new double[attributes[classCol].size()];
        double[][] likelihood = new double[attributes[classCol].size()][attributes.length];
        double[] perdict = new double[attributes.length];
        double[][] perc = new double[attributes[classCol].size()][attributes.length];
        //prints all the attributes of each column (debugging purposes)
        for (int i = 0; i < attributes[classCol].size(); i++) {
            //System.out.println("Attribute Col: "+i);
            classNum[i] = 0;
            for (int j = 0; j < trainSet.size(); j++) {
                //System.out.print(dataSet.get(j).data[i]);
                if (trainSet.get(j).data[classCol].equals(attributes[classCol].get(i))) {
                    classNum[i] += 1;
                    for (int k = 0; k < attributes.length; k++) {
                        if (k != classCol && k != ignoredCol) {
                            likelihood[i][k] += Double.parseDouble(trainSet.get(j).data[k]);
                            perdict[k] += Double.parseDouble(trainSet.get(j).data[k]);
                        }
                        //System.out.println(perdict[k] + " , " + i + " , " + j + " ,    " + likelihood[i][k]);
                    }

                }
            }
            //System.out.println();
            classNum[i] = classNum[i] / trainSet.size();
            //System.out.println(classNum[i]);
        }
        for (int k = 0; k < attributes.length; k++) {
            perdict[k] = perdict[k] / trainSet.size();
        }
        for (int i = 0; i < attributes[classCol].size(); i++) {
            for (int k = 0; k < attributes.length; k++) {
                if (k != classCol && k != ignoredCol) {
                    likelihood[i][k] = likelihood[i][k] / trainSet.size();
                }
            }
        }

        for (int i = 0; i < attributes[classCol].size(); i++) {
            for (int j = 0; j < attributes.length; j++) {
                perc[i][j] = likelihood[i][j] / perdict[j];
                //System.out.println(perdict[j] +" , " + i + " , "+ j + " ,    " + likelihood[i][j]);
            }
        }
        testrun(perc);
    }

    public void testrun(double[][] perc) {
        //String[] test = new String[attributes[classCol].size()];
        double[] totalperc = new double[attributes[classCol].size()];

        for (int l = 0; l < testSet.size(); l++) {
            int guess = 0;
            for (int i = 0; i < attributes[classCol].size(); i++) {
                totalperc[i] = 1;
                for (int k = 0; k < attributes.length; k++) {
                    if (k != classCol && k != ignoredCol) {
                        if (perc[i][k]*attributes[k].size() < Double.parseDouble(testSet.get(l).data[k])) {
                            //totalperc[i] = totalperc[i] * (1 - perc[i][k]);
                            if(perc[i][k]!=0&& perc[i][k]==perc[i][k]){
                            totalperc[i] = totalperc[i] * perc[i][k];
                            }
                        } else {
                            if(perc[i][k]!=0&& perc[i][k]==perc[i][k]){
                            totalperc[i] = totalperc[i] * (1 - perc[i][k]);
                            }
                            //totalperc[i] = totalperc[i] * perc[i][k];
                        }
                            //System.out.println(totalperc[i] + "   ,   " + perc[i][k]);   
                    }
                    //System.out.println(totalperc[i]);   
                }
                //System.out.println(totalperc[i]);    
                //totalperc[i] = totalperc[i] * ((double) classNum[i] / trainSet.size());
            }
            for (int i = 1; i < attributes[classCol].size(); i++) {
                if (totalperc[guess] < totalperc[i]) {
                    guess = i;
                }
            }
            if (attributes[classCol].get(guess).equals(testSet.get(l).data[classCol])) {
                totalCorrect++;
                System.out.print((testSet.get(l).data[classCol]));
                System.out.println("    Correct!");
            } else {
                totalWrong++;
                System.out.print((testSet.get(l).data[classCol]));
                System.out.println("    Wrong!");
            }
        }
    }

}
