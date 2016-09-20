import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class DrawPanel extends JPanel{
    
    List<Point> points = new ArrayList<Point>();
    
    DrawPanel(List<Point> inPoints){
        points = inPoints;
    }
    
    //prints out the points and their connections
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        for(int i =0; i< points.size(); i++){
            g.drawOval(points.get(i).xCoor*50, points.get(i).yCoor*50, 5, 5);
            for(int j = 0; j < points.get(i).connectedPoints.size(); j++){
                g.drawLine(points.get(i).xCoor*50, points.get(i).yCoor*50, points.get(i).connectedPoints.get(j).xCoor*50, points.get(i).connectedPoints.get(j).yCoor*50);
            }
        }
    }
}