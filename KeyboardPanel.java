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
	private Character currentCharacter;
	private Map<Color, Character> colorCharMap;  
	private DragTyper dragTyper;
	private JTextArea writeArea;  //where to draw the result
	private String currentDragSet;


    public void keyTyped(KeyEvent e) {
        System.out.println("KEY TYPED: " + e.getKeyChar());

		//associate the color currently under the mouse with the letter that was typed
		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point coord = pointer.getLocation();	
		Color color = robot.getPixelColor(coord.x, coord.y);
		colorCharMap.put(color, e.getKeyChar());

		//when the space bar is pressed, save the current color/key mapping to a file
		if (e.getKeyChar() == KeyEvent.VK_SPACE){
			System.out.println("saving keymap");

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
    	//this is necessary to be able to handle mouse events in the panel
		this.requestFocus();
    }

	void processMouse(MouseEvent e){
		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point coord = pointer.getLocation();	
		Color color = robot.getPixelColor(coord.x, coord.y);
		Character c = colorCharMap.get(color);

		//don't do anything if the color isn't mapped
		if (c == null)
			return;

		//ignore spaces and repeat characters
		//on an empty dragset, just add the current character
		if ((c != currentCharacter && c != ' ') | currentDragSet.equals("")){
			//System.out.println("Red   = " + color.getRed());
			//System.out.println("Green = " + color.getGreen());
			//System.out.println("Blue  = " + color.getBlue());
			currentCharacter = c;
			currentDragSet += c;
		}
	}
    public void mouseDragged(MouseEvent e) {
		processMouse(e);
    }

    public void mousePressed(MouseEvent e) {
	   processMouse(e);
       //System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
    }

    public void mouseReleased(MouseEvent e) {
		//if the right mouse button is released
		//deleted a character and return
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK){
			String text = writeArea.getText();
			if (text != null && text.length() > 1)
				writeArea.setText(text.substring(0, text.length()-1));
			return;
		}
	    System.out.println("drag set: " + currentDragSet);
        //System.out.println("Mouse released; # of clicks: " + e.getClickCount());
		if (currentDragSet.length() == 0)
			return;
		try{	
			//if its a single character, write it out wihtout a space
			if (currentDragSet.length() == 1){
				writeArea.setText(writeArea.getText() + currentDragSet);
			}
			//if it's more than one character, look up it's matches
			else{  
				ArrayList<String> matches = dragTyper.getMatches(currentDragSet);
				String[] choices = matches.toArray(new String[matches.size()]);
				callPopup(choices);
			}
		} catch(Exception ex){
			System.out.println("error in dragtyper");
			ex.printStackTrace();
		} finally{
			currentDragSet = "";
		}
    }

    public void mouseEntered(MouseEvent e) {
       //System.out.println("Mouse entered");
    }

    public void mouseExited(MouseEvent e) {
       //System.out.println("Mouse exited");
    }

    public void mouseClicked(MouseEvent e) {
       //System.out.println("Mouse clicked (# of clicks: "
       //             + e.getClickCount() + ")");
    }


	public void callPopup(String[] data){
		JFrame frame = new ListPopup(data, writeArea);
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Point location = MouseInfo.getPointerInfo().getLocation(); 
		int x = (int) location.getX();
		int y = (int) location.getY();
		frame.setLocation(x, y);
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


		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		//colors to keys
		loadDefaultMap();


		//try loading the picture of the keyboard
        try {                
        	image = ImageIO.read(new File(imageFile));
        } catch (IOException ex) {
	     	System.out.println("exception");
	     	ex.printStackTrace();
             // handle exception...
        }

		//try setting up the robot that handles mouse position
		try{ 
			robot = new Robot();
		}catch(AWTException e){
			System.out.println("awte");
			e.printStackTrace();
		}
    }



	//draw the picture of the keyboard
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

}
