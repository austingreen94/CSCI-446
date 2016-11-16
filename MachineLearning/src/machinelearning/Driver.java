package machinelearning;
import java.util.ArrayList;
/**
 *
 * @author Jordan
 */
public class Driver {

    public static void main(String[] args) {
        // Read in houseTest.txt file
        ArrayList<Node> allNodes = ReadIn.read("houseTest.txt");
        for(int i = 0; i < allNodes.size(); i++){
            allNodes.get(i).print();
        }
        KNN k = new KNN();
        // Test for the calculateGain method on a given String attribute
        //ID3 start = new ID3(in.dataset);
        //start.calculateGain(start.curDataset, "Budget");
    }
}
