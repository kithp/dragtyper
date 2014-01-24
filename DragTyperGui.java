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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DragTyperGui extends JPanel
{

	private JTextArea textArea;  //place the the text is written
	private KeyboardPanel keyboardPanel;  //place to interact with the keys
	private JButton copyButton;  //copy results to system clipboard


	public DragTyperGui(){
		textArea = new JTextArea();
		keyboardPanel = new KeyboardPanel("keyboard4.png", textArea);

		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		copyButton = new JButton("copy");		
		copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                System.out.println("copied to clipboard");

				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(textArea.getText());
				clipboard.setContents(strSel, null);
            }
        });


		this.setLayout( new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.NORTH);	
        this.add(keyboardPanel, BorderLayout.CENTER);	
        this.add(copyButton, BorderLayout.SOUTH);	
		keyboardPanel.requestFocusInWindow();
	}

}
