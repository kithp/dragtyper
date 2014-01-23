import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DragTyperGui extends JPanel
{

	private JTextArea textArea;
	private JTextField dragField;
	private KeyboardPanel keyboardPanel;



	public DragTyperGui(){
		textArea = new JTextArea();
		dragField = new JTextField();
		keyboardPanel = new KeyboardPanel("keyboard4.png", textArea);
		


		this.setLayout( new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.SOUTH);	
        this.add(keyboardPanel, BorderLayout.CENTER);	
        this.add(dragField, BorderLayout.NORTH);	
		keyboardPanel.requestFocusInWindow();
	}

}
