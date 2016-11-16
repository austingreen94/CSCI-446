package project3;

/**
 *
 * @author Austin
 */
public class Project3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReadIn in = new ReadIn();
        // Read in houseTest.txt file
        in.readHouseVotes();
        // Test for the calculateGain method on a given String attribute
        ID3 start = new ID3(in.houseVotesDataset);
        start.calculateGain(start.curDataset, "Budget");
    }
    
}
