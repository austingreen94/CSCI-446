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
public class TAN {

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
    public TAN(ArrayList<Node> newSet, int inClassCol, int inSkip) {
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
//        for (int i = 0; i < attributes.length; i++) {
//            System.out.println("Attribute Col: " + i);
//            for (int j = 0; j < attributes[i].size(); j++) {
//                System.out.print(attributes[i].get(j) + ",");
//            }
//            System.out.println();
//        }
        //perform the runs 5 times (for 5x2)
        for (int i = 0; i < 5; i++) {
            splitGroups();
            System.out.println("RUN NUMBER " + i);
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
        //Generate new version of data storage based on how many classes there are
        // and how many attributes there are and how many types each attribute has
        int[][][] totals = new int[attributes[classCol].size() + 1][attributes.length + 1][1000];
        for (int j = 0; j < attributes.length; j++) {
            for (int k = 0; k < trainSet.size(); k++) {
                totals[attributes[classCol].indexOf(trainSet.get(k).data[classCol])][j][attributes[j].indexOf(trainSet.get(k).data[j])]++;

            }

        }

        double[] runningTotal = new double[attributes.length];
        double[] bestTotal = new double[attributes.length / 2];
        int bestClass = 0;
        double bestPercent = 0;
        double bestGuess = 1;
        //runs once for every node in testSet
        for (int k = 0; k < testSet.size(); k++) {
            bestClass = 0;
            bestPercent = 0;
            //creating new node
            Node tester = testSet.get(k);
            System.out.println("Answer = " + tester.data[classCol]);
            //runs for every type of class
            for (int i = 0; i < attributes[classCol].size(); i++) {
                //compiling running total percentages
                for (int j = 0; j < attributes.length; j++) {
                    if (j != classCol && j != ignoredCol) {
                        runningTotal[j] = (((double) totals[i][j][attributes[j].indexOf(tester.data[j])]) / totals[i][classCol][i]);
                    }
                }
                //Prunes running total down to best total
                //to not overfit tree
                for (int o = 0; o < bestTotal.length; o++) {
                    if (o != classCol && o != ignoredCol) {
                        double newBest = 0;
                        double bestBest = 0;
                        int bestIndex = 0;
                        for (int j = 0; j < attributes.length; j++) {
                            if (j != classCol && j != ignoredCol) {
                                if (bestBest < (double) totals[i][j][attributes[j].indexOf(tester.data[j])] / attributes[j].size() && runningTotal[j] != 0) {
                                    bestBest = (double) totals[i][j][attributes[j].indexOf(tester.data[j])] / attributes[j].size();
                                    newBest = runningTotal[j];
                                    bestIndex = j;
                                }
                            }
                        }
                        if (newBest != 0) {
                            bestTotal[o] = newBest;
                            //System.out.println(newBest + "  ,  " + bestIndex);
                            runningTotal[bestIndex] = 0;
                        }
                    }
                }
                bestGuess = 1;
                //Multiply running total to create current percentage for i
                //using product rule with bayes rule
                for (int j = 0; j < bestTotal.length; j++) {
                    if (j != classCol && j != ignoredCol) {
                        bestGuess = bestGuess * bestTotal[j];

                    }

                }
                //multiplying by prior ratio of this class type
                bestGuess = bestGuess * ((double) totals[i][classCol][i] / trainSet.size());
                //selects best class choice by highest percentage
                if (bestGuess > bestPercent) {
                    bestPercent = bestGuess;
                    bestClass = i;
                }
                System.out.println("Current class [i] = " + bestGuess + "  ,  current best percentage " + bestPercent);
            }
            System.out.println("GUESS = " + attributes[classCol].get(bestClass));
            if (tester.data[classCol].equals(attributes[classCol].get(bestClass))) {
                totalCorrect++;
            } else {
                System.out.println("Wrong!\n");
                totalWrong++;
            }
        }
    }

}
