import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ListPopup extends JFrame{

	private String[] data; 
	private JList list;
	private JTextArea area; //where to write to

	public ListPopup(String[] v, JTextArea a){
		if (v.length < 1)
			return;

		area = a;
		data = v;
		list = new JList(data);
		list.setSelectedIndex(0);
		MouseAdapter mouseListener = new MouseAdapter() {
		  public void mouseClicked(MouseEvent mouseEvent) {
			JList list = (JList) mouseEvent.getSource();
			if ((mouseEvent.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
			  int index = list.getSelectedIndex();
			  if (index >= 0) {
				if (area != null)
					area.setText(area.getText() + " " + list.getSelectedValue());
				System.out.println("right-clicked on : " + list.getSelectedValue() );
				//try to close the window
				//list.getTopLevelAncenstor().dispose();
				JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(list);
				parent.dispose();
				//list.getParent().close();
			  }
			}
			if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			  int index = list.getSelectedIndex();
			  if (index >= 0) {
				if (area != null)
					area.setText(area.getText() + " " + list.getSelectedValue());
				System.out.println("left-clicked on : " + list.getSelectedValue() );
				//try to close the window
				//list.getTopLevelAncenstor().dispose();
				JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(list);
				parent.dispose();
				//list.getParent().close();
			  }
			}
		  }
			public void mouseWheelMoved(MouseWheelEvent mouseEvent) {
				JList list = (JList) mouseEvent.getSource();
				String message = "none";

  			    int index = list.getSelectedIndex();
				int notches = mouseEvent.getWheelRotation();
				if (notches < 0 & index > 0) {
					list.setSelectedIndex(--index);
					message = "Mouse wheel moved UP "
							+ -notches + " notch(es)";
				} else if (notches > 0 & index < data.length-1){

					list.setSelectedIndex(++index);
					message = "Mouse wheel moved DOWN "
						+ notches + " notch(es)";
				}
				System.out.println(message);
				}

		};
		list.addMouseListener(mouseListener);
		list.addMouseWheelListener(mouseListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.add(list);
	}


	public static void main(String[] args){

		String[] data = { "Value 1", "Value 2", "Value 3", "Value 4", "Value 5" };
		JFrame frame = new ListPopup(data, null);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("adf");
	}
}
