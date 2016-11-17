package machinelearning;
import java.util.ArrayList;
/**
 *
 * @author Jordan
 */
public class Driver {

    public static void main(String[] args) {
        //ArrayList<Node> allNodes = ReadIn.read("glass.data.txt");
        //breast-cancer-wisconsin.data
        //houseTest
        ArrayList<Node> allNodes = ReadIn.read("breast-cancer-wisconsin.data.txt");
        //print all nodes created
        for(int i = 0; i < allNodes.size(); i++){
            allNodes.get(i).print();
        }
        KNN k = new KNN(allNodes);
        k.run();
    }
}
