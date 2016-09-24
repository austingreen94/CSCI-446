
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
    //List<Point> points = new ArrayList<Point>();
    int population[][];
    int conflicts[];
    
    public GeneticAlgorithm(){
        
    }
    
    // Method coloring a point with 3 color choices
    //KILL
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
    //KILL
    public int color4Point(int curColor, Point curPoint){
        curPoint.color = curColor;
        if(curColor == 4){
            curColor = 1;
        } else {
            curColor++;
        }
        return curColor;
    }
    
    //KILL
    public Point randomColor(int amountOfColors, Point curPoint){
        curPoint.color = ran.nextInt(amountOfColors) + 1;
        System.out.print(curPoint.color);
        return curPoint;
    }
    
    // Method to color entire graph for initiation of minConflicts algorithm
    //KILL
    public GraphGen colorEntireGraphRandomly(int amountColors, GraphGen graph){
        GraphGen graphNew = new GraphGen(graph.numPoints);
        graphNew.finishedPoints = (ArrayList)graph.finishedPoints.clone();
        
        for(int i = 0; i < graphNew.numPoints; i++){
            graphNew.finishedPoints.get(i).color = ran.nextInt(amountColors) + 1;
            
        }
        System.out.println();
        return graphNew;
    }
    
    // Create a population of random Graphs
    public void populationCreator(int numberOfGraphs, int numberOfColors, GraphGen graph){
        for(int i = 0; i < numberOfGraphs; i++){  
            for(int j = 0; j < graph.finishedPoints.size(); j++){
                population[i][j] = ran.nextInt(numberOfColors) + 1;
            }
            System.out.println();
        }
    }
    
    // Checks the conflicts of each graph in the population and saves to geneticConflicts
    // variable in GraphGen class
    public void eachGraphConflicts(GraphGen graph, int popSize){
        for(int i = 0; i < popSize; i++){
            conflicts[i]=0;
        }
        for(int i = 0; i < popSize; i++){
            System.out.print("index:"+i+" ");
            for(int j = 0; j < graph.finishedPoints.size(); j++){
                System.out.print(population[i][j]);
                for(int k = 0; k < graph.finishedPoints.get(j).connectedPoints.size(); k++){
                    int point1Color = population[i][j];
                    int point2Color = population[i][k];
                    if(point1Color == point2Color){
                        conflicts[i]++;
                    }
                }
            }
            System.out.println(" "+conflicts[i]);
        }
    }
    
    // Boolean method that returns true if one the graphs in the population 
    // has zero conflicts
    public boolean zeroConflicts(GraphGen g, int popSize){
        for(int i = 0; i < popSize; i++){
            if(conflicts[i] == 0){
                return true;
            }
//            System.out.println("index:"+i+" conflicts:"+population.get(i).geneticConflicts);
//            if(minConflictsGuy == 5000 || population.get(i).geneticConflicts < population.get(minConflictsGuy).geneticConflicts){
//                minConflictsGuy = i;
//            }
//            for(int j = 0; j < population.get(i).numPoints; j++){
//                System.out.print(population.get(i).finishedPoints.get(j).color);
//
//            }
//            System.out.println();
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
    public int[][] reproduce(int x, int y, GraphGen g){
        //GraphGen newGraph = new GraphGen(x.finishedPoints.size());
        
        int n = g.finishedPoints.size();
        int temp[][] = new int[2][n];
        int c = ran.nextInt(n);
        for(int i = 0; i < c; i++){
            temp[0][i] = population[x][i];
            temp[1][i] = population[y][i];
        }
        for(int i = c; i < n; i++){
            temp[1][i]= population[x][i];
            temp[0][i]= population[y][i];
        }
        return temp;
    }
    
    public void mutate(int pop, int numberOfColors, GraphGen g){
        int mutatedPoint = ran.nextInt(g.numPoints);
        int mutatedColor = ran.nextInt(numberOfColors)+1;
        int mutatedColor2 = ran.nextInt(numberOfColors)+1;
        population[pop][mutatedPoint] = mutatedColor;
        for(int i=0; i<g.finishedPoints.get(mutatedPoint).connectedPoints.size();i++){
            population[pop][i] = mutatedColor2;
        }
    }
    
    public void geneticAlgorithm(GraphGen graph){
        int popSize=1000;
        population = new int[popSize][graph.finishedPoints.size()];
        conflicts= new int[popSize];
        populationCreator(popSize, 4, graph); // Create Population
        eachGraphConflicts(graph, popSize); // Save number of conflicts for each graph in population
        //System.out.println(population.size()); // Test
        //List<GraphGen> sortedPopulation = sortPopulationConflicts(population); // Sort graphs by lowest number of conflicts
        int newPopulation[][] = new int[popSize][graph.finishedPoints.size()];
        int loops = 0;
        int temp[][];
        while(!zeroConflicts(graph, popSize) && loops < 100000){ // Run until there is a perfect individual or enough time has elapsed
            for(int i = 0; i < popSize; i++){
                boolean chance = new Random().nextInt(10)==0;
                //int mutant = ran.nextInt(popSize);
                //mutate(mutant, 4, graph);
                //int x = ran.nextInt(popSize);
                int y = ran.nextInt(popSize);
                int mate=0;
                //int mate2=1;
                int dead=0;
                int dead2=1;
                for(int j=0; j<popSize; j++){
                    if(conflicts[j]>conflicts[dead]){
                        dead = j;
                    }else if(conflicts[j]>conflicts[dead2]){
                        dead2 = j;
                    }
                }
                //dead = ran.nextInt(popSize);
                //dead2 = ran.nextInt(popSize);
                for(int j=0; j<popSize; j++){
                    if(conflicts[j]<conflicts[mate]){
                        mate = j;
                    }
                }
                //GraphGen gx = population.get(x);
                //GraphGen gy = population.get(y);
                temp = reproduce(mate, y, graph);
                for(int j=0;j<graph.numPoints;j++){
                    population[dead][j]=temp[0][j];
                    population[dead2][j]=temp[1][j];
                }
                if (chance){
                    mutate(dead, 4, graph);
                }
                
                //System.out.println(loops);
                
            }
            //population = newPopulation.subList(0, newPopulation.size());
            //population.clear();
            //for(int i = 0; i < newPopulation.size(); i++){
            //    GraphGen newGraph = newPopulation.get(i);
            //    population.add(newGraph);
            //}
            
//            for(int i = 0; i < popSize; i++){
//                for(int j = 0; j < graph.finishedPoints.size(); j++){
//                    population[i][j] = newPopulation[i][j];
//                }
//            }
//            for(int i = 0; i < popSize; i++){
//                for(int j = 0; j < graph.finishedPoints.size(); j++){
//                    newPopulation[i][j]=0;
//                }
//            }
                        
            //population = temp;
            //newPopulation.clear();
            eachGraphConflicts(graph, popSize);
            //sortedPopulation = sortPopulationConflicts(population);
            System.out.println("--------------------------------------------"+loops);
            loops++;
        }
        //GraphGen bestIndividual = sortedPopulation.get(0);
        for(int i = 0; i < popSize; i++){
            if(conflicts[i] == 0){
                System.out.println("Best Individual");
            }
        }
        if(loops == 100000){
            System.out.println(loops);
        } 
        System.out.println("End"); // Testing
    }
}
