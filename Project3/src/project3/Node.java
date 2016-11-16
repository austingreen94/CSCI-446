package project3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Austin
 */
public class Node {
    String attrQues;
    List<Node> children = new ArrayList<>();
    List<String> edges = new ArrayList<>();
    
    public Node(){
        attrQues = "";
    }
}
