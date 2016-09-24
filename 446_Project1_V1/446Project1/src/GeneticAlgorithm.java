
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
    
    // Create a population of random Graphs
    public void populationCreator(int numberOfGraphs, int numberOfColors, GraphGen graph){
        for(int i = 0; i < numberOfGraphs; i++){  
            for(int j = 0; j < graph.finishedPoints.size(); j++){
                population[i][j] = ran.nextInt(numberOfColors) + 1;
            }
        }
    }
    
    // Checks the conflicts of each graph in the population and saves to geneticConflicts
    // variable in GraphGen class
    public void eachGraphConflicts(GraphGen graph, int popSize){
        for(int i = 0; i < popSize; i++){
            conflicts[i]=0;
        }
        for(int i = 0; i < popSize; i++){
            //System.out.print("index:"+i+" ");
            for(int j = 0; j < graph.finishedPoints.size(); j++){
                //System.out.print(population[i][j]);
                for(int k = 0; k < graph.finishedPoints.get(j).connectedPoints.size(); k++){
                    int point1Color = population[i][j];
                    int save = 0;
                    for(int l=0;l<graph.finishedPoints.size();l++){
                        if(graph.finishedPoints.get(l)==graph.finishedPoints.get(j).connectedPoints.get(k)){
                            save =l;
                        }
                    }
                    int point2Color = population[i][save];
                    if(point1Color == point2Color){
                        conflicts[i]++;
                    }
                }
            }
            //System.out.println(" "+conflicts[i]);
        }
    }
    
    // Boolean method that returns true if one the graphs in the population 
    // has zero conflicts
    public boolean zeroConflicts(GraphGen g, int popSize){
        for(int i = 0; i < popSize; i++){
            if(conflicts[i] == 0){
                return true;
            }
        }
        return false;
    }
    
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
        population[pop][mutatedPoint] = mutatedColor;
    }
    
    public void geneticAlgorithm(GraphGen graph){
        int popSize=1000;
        population = new int[popSize][graph.finishedPoints.size()];
        conflicts= new int[popSize];
        populationCreator(popSize, 4, graph); // Create Population
        eachGraphConflicts(graph, popSize); // Save number of conflicts for each graph in population
        int loops = 0;
        int temp[][];
        int mate=0;
        while(!zeroConflicts(graph, popSize) && loops < 100000){ // Run until there is a perfect individual or enough time has elapsed
            
                boolean chance = new Random().nextInt(50)==0;
                boolean chance2 = new Random().nextInt(5)==0;
                int y = ran.nextInt(popSize);
                mate=ran.nextInt(popSize);
                int dead=0;
                int dead2=1;
                for(int j=0; j<popSize; j++){
                    if(conflicts[j]>conflicts[dead]){
                        dead = j;
                    }else if(conflicts[j]>conflicts[dead2]){
                        dead2 = j;
                    }
                }
                dead = ran.nextInt(popSize);
                dead2 = ran.nextInt(popSize);
                if(chance2){
                    for(int j=0; j<popSize; j++){
                        if(conflicts[j]<conflicts[mate]){
                            mate = j;
                        }
                    }
                }
                System.out.println("--------------------------------------------"+conflicts[mate]);
                
                temp = reproduce(mate, y, graph);
                for(int j=0;j<graph.numPoints;j++){
                    population[dead][j]=temp[0][j];
                    population[dead2][j]=temp[1][j];
                }
                if (chance){
                    mutate(dead, 4, graph);
                }
            eachGraphConflicts(graph, popSize);
            //sortedPopulation = sortPopulationConflicts(population);
            System.out.println("--------------------------------------------"+loops);
            loops++;
        }
        for(int i = 0; i < popSize; i++){
            if(conflicts[i] == 0){
                System.out.println("Best Individual "+ i);
                mate =i;
            }
        }
        if(loops == 100000){
            System.out.println(loops);
        } 
        for(int j=0;j<graph.numPoints;j++){
            graph.finishedPoints.get(j).color=population[mate][j];
            System.out.print(population[mate][j]);
        }
        System.out.println(" "+conflicts[mate]+" "+mate+" "+ " End"); // Testing
    }
}
