import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class DrawPanel extends JPanel{
    
    List<Point> points = new ArrayList<Point>();
    
    DrawPanel(List<Point> inPoints){
        points = inPoints;
    }
    //sets points
    public void setPoints(List<Point> inPoints){
        points = inPoints;
    }
    //prints out the points and their connections
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        for(int i =0; i< points.size(); i++){
            switch (points.get(i).color) {
                case 1:
                    g.setColor(Color.RED);
                    break;
                case 2:
                    g.setColor(Color.GREEN);
                    break;
                case 3:
                    g.setColor(Color.BLUE);
                    break;
                case 4:
                    g.setColor(Color.YELLOW);
                    break;
                default:
                    g.setColor(Color.BLACK);
                    break;
            }
            g.fillOval(points.get(i).xCoor*50, points.get(i).yCoor*50, 10, 10);
            g.setColor(Color.BLACK);
            for(int j = 0; j < points.get(i).connectedPoints.size(); j++){
                g.drawLine(points.get(i).xCoor*50, points.get(i).yCoor*50, points.get(i).connectedPoints.get(j).xCoor*50, points.get(i).connectedPoints.get(j).yCoor*50);
            }
        }
    }
}