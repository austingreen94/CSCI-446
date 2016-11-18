package machinelearning;
import java.util.ArrayList;
/**
 *
 * @author Jordan
 */
public class Driver {

    public static void main(String[] args) {
        //file name   |    class location   |   attribute to be skipped
        //breast-cancer-wisconsin.data.txt, class=10, skip = 0
        //soybean-small.data.txt, 35, -1 (none)
        //glass.data.txt, 10, 0
        //house-votes-84.data.txt, 0, -1
        //iris.data.txt, 4, -1
        ArrayList<Node> allHouse = ReadIn.read("house-votes-84.data.txt");
        ArrayList<Node> allGlass = ReadIn.read("glass.data.txt");
        ArrayList<Node> allCancer = ReadIn.read("breast-cancer-wisconsin.data.txt");
        ArrayList<Node> allSoybean = ReadIn.read("soybean-small.data.txt");
        ArrayList<Node> allIris = ReadIn.read("iris.data.txt");
        ArrayList<Node> houseTest = ReadIn.read("houseTest.txt");
        
        //print all nodes created
        //for(int i = 0; i < allHouse.size(); i++){
        //    allHouse.get(i).print();
        //}
        //initializes a KNN algorithm with this set of data, class col location, and skipped col location
        //KNN k = new KNN(allHouse, 0, -1);
        //k.run();
        //KNN k2 = new KNN(allCancer, 10, 0);
        //k2.run();
        //NaiveBayes nb = new NaiveBayes(allHouse, 0 ,-1);
        NaiveBayes nb = new NaiveBayes(allSoybean, 35 ,-1);
        nb.run();
    }
}
