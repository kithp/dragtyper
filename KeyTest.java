import java.awt.Color;
import java.awt.Robot;
import java.awt.*;

public class KeyTest {
  public static void main(String[] args) throws Exception{
    Robot robot = new Robot();

	while(true){
		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point coord = pointer.getLocation();	
		Color color = robot.getPixelColor(coord.x, coord.y);

		System.out.println("Red   = " + color.getRed());
		System.out.println("Green = " + color.getGreen());
		System.out.println("Blue  = " + color.getBlue());
		Thread.sleep(50);
	}
  }
}
