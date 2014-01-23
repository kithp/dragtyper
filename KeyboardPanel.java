import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Robot;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class KeyboardPanel extends JPanel
					implements MouseListener, MouseMotionListener, KeyListener 
{

    private BufferedImage image;
	private Robot robot;
	private Color currentColor;
	private Character currentCharacter;
	private Map<Color, Character> colorCharMap;
	private DragTyper dragTyper;
	private JTextArea writeArea;  //where to draw the result
	private String currentDragSet;


    public void keyTyped(KeyEvent e) {
        System.out.println("KEY TYPED: " + e.getKeyChar());

		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point coord = pointer.getLocation();	
		Color color = robot.getPixelColor(coord.x, coord.y);
		colorCharMap.put(color, e.getKeyChar());

		//test
		if (e.getKeyChar() == KeyEvent.VK_SPACE){
			System.out.println("adf");

			for (Map.Entry<Color, Character> entry : colorCharMap.entrySet()){
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}


			//save map to a file
			try{
				FileOutputStream fos = new FileOutputStream("default.cmap");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(colorCharMap);
				oos.close();
			}catch(Exception e1){
			}


		}
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
       this.requestFocus();
    }

    public void mouseDragged(MouseEvent e) {
		//System.out.println("Mouse dragged"+  e.getX() + ", " + e.getY());

		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point coord = pointer.getLocation();	
		Color color = robot.getPixelColor(coord.x, coord.y);
		Character c = colorCharMap.get(color);

		if (c != null && c != currentCharacter && c != ' '){
			System.out.println("Red   = " + color.getRed());
			System.out.println("Green = " + color.getGreen());
			System.out.println("Blue  = " + color.getBlue());
			currentColor = color;
			currentCharacter = c;
			currentDragSet += c;
		}
    }

    public void mousePressed(MouseEvent e) {
       System.out.println("Mouse pressed; # of clicks: "
                    + e.getClickCount());
    }

    public void mouseReleased(MouseEvent e) {
	    System.out.println("drag set: " + currentDragSet);
        System.out.println("Mouse released; # of clicks: "
                    + e.getClickCount());

		try{	
			//dragTyper.test1(currentDragSet);
			ArrayList<String> matches = dragTyper.getMatches(currentDragSet);
			String[] choices = matches.toArray(new String[matches.size()]);
			String input = (String) JOptionPane.showInputDialog(null, "Choose now...",
				"The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
				choices, // Array of choices
				choices[0]); // Initial choice
			System.out.println(input);
			writeArea.setText(writeArea.getText() + " " + input);
			
		} catch(Exception ex){
			System.out.println("error in dragtyper");
			//ex.printStackTrace();
		} finally{
			currentDragSet = "";
		}
    }

    public void mouseEntered(MouseEvent e) {
       System.out.println("Mouse entered");
    }

    public void mouseExited(MouseEvent e) {
       System.out.println("Mouse exited");
    }

    public void mouseClicked(MouseEvent e) {
       System.out.println("Mouse clicked (# of clicks: "
                    + e.getClickCount() + ")");
    }


	private void loadDefaultMap(){
		try{
			FileInputStream fis = new FileInputStream("default.cmap");
			ObjectInputStream ois = new ObjectInputStream(fis);
			colorCharMap = (HashMap<Color,Character>) ois.readObject();
			ois.close();
		}catch(Exception e){
			System.out.println("couldn't load map");
			e.printStackTrace();
		}
	}


    public KeyboardPanel(String imageFile, JTextArea area) {
		dragTyper = new DragTyper();
		writeArea = area;	

		colorCharMap = new HashMap<Color, Character>();
		currentDragSet = new String();

        try {                
        	image = ImageIO.read(new File(imageFile));
        } catch (IOException ex) {
	     	System.out.println("exception");
	     	ex.printStackTrace();
             // handle exception...
        }

		try{ 
			robot = new Robot();
		}catch(AWTException e){
			System.out.println("awte");
			e.printStackTrace();
		}

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		currentColor = new Color(0, 0, 0);

		loadDefaultMap();

    }











    //@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

}
