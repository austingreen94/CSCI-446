
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Austin Green
 */

public  class GeneticAlgorithm {
    
    Random ran = new Random();
    List<Point> points = new ArrayList<Point>();
    
    public GeneticAlgorithm(){
        
    }
    
    // Method coloring a point with 3 color choices
    public int color3Point(int curColor, Point curPoint){
        curPoint.color = curColor;
        if(curColor == 3){
            curColor = 1;
        } else {
            curColor++;
        }
        return curColor;
    }
    
    // Method coloring a point with 4 color choices
    public int color4Point(int curColor, Point curPoint){
        curPoint.color = curColor;
        if(curColor == 4){
            curColor = 1;
        } else {
            curColor++;
        }
        return curColor;
    }
    
    public void randomColor(int amountOfColors, Point curPoint){
        curPoint.color = ran.nextInt(amountOfColors) + 1;
    }
    
    // Method to color entire graph for initiation of minConflicts algorithm
    public void colorEntireGraphRandomly(int amountColors, GraphGen graph){
        if(amountColors == 3){
            for(int i = 0; i < graph.numPoints; i++){
                randomColor(3, graph.finishedPoints.get(i));
            }
        } else {
            for(int i = 0; i < graph.numPoints; i++){
                randomColor(4, graph.finishedPoints.get(i));
            }
        }
    }
    
    // Create a population of random Graphs
    public List<GraphGen> populationCreator(int numberOfGraphs, int numberOfColors, GraphGen graph){
        List<GraphGen> population = new ArrayList<GraphGen>();
        for(int i = 0; i < numberOfGraphs; i++){   
            colorEntireGraphRandomly(numberOfColors, graph);
            population.add(graph);
        }
        return population;
    }
    
    // Checks the conflicts of each graph in the population and saves to geneticConflicts
    // variable in GraphGen class
    public void eachGraphConflicts(List<GraphGen> population){
        for(int i = 0; i < population.size(); i++){
            population.get(i).geneticConflicts = 0;
        }
        for(int i = 0; i < population.size(); i++){
            for(int j = 0; j < population.get(i).finishedPoints.size(); j++){
                for(int k = 0; k < population.get(i).finishedPoints.get(j).connectedPoints.size(); k++){
                    int point1Color = population.get(i).finishedPoints.get(j).color;
                    int point2Color = population.get(i).finishedPoints.get(j).connectedPoints.get(k).color;
                    if(point1Color == point2Color){
                        population.get(i).geneticConflicts++;
                    }
                }
            }
        }
    }
    
    // Boolean method that returns true if one the graphs in the population 
    // has zero conflicts
    public boolean zeroConflicts(List<GraphGen> population){
        for(int i = 0; i < population.size(); i++){
            if(population.get(i).geneticConflicts == 0){
                return true;
            }
        }
        return false;
    }
    
    // Method to sort Graphs in population by lowest conflicts
//    public List<GraphGen> sortPopulationConflicts(List<GraphGen> population){
//        List<GraphGen> sortedPopulation = new ArrayList<GraphGen>();
//        GraphGen first = population.remove(0);
//        sortedPopulation.add(first);
//        int count = 0;
//        while(!population.isEmpty()){
//            GraphGen current = population.remove(0);
//            while(current.geneticConflicts > sortedPopulation.get(count).geneticConflicts && count < sortedPopulation.size()){
//                count++;
//            } ;
//            sortedPopulation.add(count, current);
//            count = 0;
//        } 
//        return sortedPopulation;
//    }
    
    // Method to combine two graphs
    public GraphGen reproduce(GraphGen x, GraphGen y){
        GraphGen newGraph = new GraphGen(x.finishedPoints.size());
        int n = x.finishedPoints.size();
        int c = ran.nextInt(n);
        for(int i = 0; i < c; i++){
            newGraph.finishedPoints.add(x.finishedPoints.get(i));
        }
        for(int i = c; i < n; i++){
             newGraph.finishedPoints.add(y.finishedPoints.get(i));
        }
        return newGraph;
    }
    
//    public void mutate(GraphGen child, int numberOfColors){
//        int mutatedPoint = ran.nextInt(child.finishedPoints.size());
//        int mutatedColor = ran.nextInt(numberOfColors);
//        child.finishedPoints.get(mutatedPoint).color = mutatedColor;
//    }
    
    public void geneticAlgorithm(GraphGen graph){
        List<GraphGen> population = populationCreator(100, 3, graph); // Create Population
        eachGraphConflicts(population); // Save number of conflicts for each graph in population
        //System.out.println(population.size()); // Test
        //List<GraphGen> sortedPopulation = sortPopulationConflicts(population); // Sort graphs by lowest number of conflicts
        List<GraphGen> newPopulation = new ArrayList<GraphGen>();
        int loops = 0;
        while(!zeroConflicts(population) || loops < 10000){ // Run until there is a perfect individual or enough time has elapsed
            for(int i = 0; i < population.size(); i++){
                //System.out.println(i);
                //System.out.println(population.size());
                int x = ran.nextInt(population.size());
                int y = ran.nextInt(population.size());
                GraphGen gx = population.get(x);
                GraphGen gy = population.get(y);
                GraphGen child = reproduce(gx, gy);
                //System.out.println(loops);
                newPopulation.add(child);
            }
            //population = newPopulation.subList(0, newPopulation.size());
            population.clear();
            for(int i = 0; i < newPopulation.size(); i++){
                GraphGen newGraph = newPopulation.get(i);
                population.add(newGraph);
            }
            newPopulation.clear();
            //population = temp;
            //newPopulation.clear();
            eachGraphConflicts(population);
            //sortedPopulation = sortPopulationConflicts(population);
            loops++;
        }
        //GraphGen bestIndividual = sortedPopulation.get(0);
        for(int i = 0; i < population.size(); i++){
            if(population.get(i).geneticConflicts == 0){
                System.out.println("Best Individual");
            }
        }
        if(loops == 10000){
            System.out.println(loops);
        } 
        System.out.println("End"); // Testing
    }
}
