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
        KNN k = new KNN(allHouse, 0, -1);
        k.run();
        NaiveBayes nb = new NaiveBayes(allHouse, 0 ,-1);
        nb.run();
        TAN tan = new TAN(allHouse, 0, -1);
        tan.run();
        KNN k2 = new KNN(allCancer, 10, 0);
        k2.run();
        NaiveBayes nb2 = new NaiveBayes(allCancer, 10 ,0);
        nb2.run();
        TAN tan2 = new TAN(allCancer, 10, 0);
        tan2.run();
        KNN k3 = new KNN(allGlass, 10, 0);
        k3.run();
        NaiveBayes nb3 = new NaiveBayes(allGlass, 10 ,0);
        nb3.run();
        TAN tan3 = new TAN(allGlass, 10, 0);
        tan3.run();
        KNN k4 = new KNN(allSoybean, 35, -1);
        k4.run();
        NaiveBayes nb4 = new NaiveBayes(allSoybean, 35 ,-1);
        nb4.run();
        TAN tan4 = new TAN(allSoybean, 35, -1);
        tan4.run();
        KNN k5 = new KNN(allIris, 4, -1);
        k5.run();
        NaiveBayes nb5 = new NaiveBayes(allIris, 4, -1);
        nb5.run();
        TAN tan5 = new TAN(allIris, 4, -1);
        tan5.run();

        //ID3 id = new ID3(allCancer);
        //id.run();
        
    }
}
