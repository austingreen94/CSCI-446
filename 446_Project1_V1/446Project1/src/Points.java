
import java.util.Arrays;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KayZeus
 */
public class Points {
    
    int xCoor;
    int yCoor;
    int numNodes = 0;
    int index = 0;
    
    public Points(int squareDimension){
        Random rn = new Random();
        xCoor = rn.nextInt(squareDimension + 1) + 0;
        yCoor = rn.nextInt(squareDimension + 1) + 0;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }
  
}
