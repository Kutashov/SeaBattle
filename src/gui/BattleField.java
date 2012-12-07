package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author AlexandrKutashov
 * @version 0.6
 * 
 */

public class BattleField extends JPanel {
	
	private static final long serialVersionUID = 7394375309988115458L;
	enum  Status {EMPTY, INJURED, KILLED}
	private static final char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
	protected JButton [][] buttons;
	protected Status [][] status;

	public BattleField() {
	  
	    buttons = new JButton [10][10];
	    status = new Status [10][10];
	
	    setLayout(new GridLayout(10, 10));
	
	    for (int i = 0; i < 10; ++i)
	      for (int j = 0; j < 10; ++j) {
	        status[i][j] = Status.EMPTY;
	        buttons[i][j] = new JButton(new ImageIcon(MainMenu.class.getResource("/resources/key-empty.png")));
	        buttons[i][j].setToolTipText(String.valueOf(letters[j]) + (i + 1));
	
	        final int I = i;
	        final int J = j;
	        
	        buttons[i][j].addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0)
	        	{
	        		if (status[I][J].equals(Status.EMPTY)) {
	        			status[I][J] = Status.INJURED;
	        			buttons[I][J].setIcon(new ImageIcon(MainMenu.class.getResource("/resources/key-period.png")));
	        		}
	        	}
	        });
	        add(buttons[i][j]);
	      }
	}
	
	public void refreshField () {
		
		for (int i = 0; i < 10; i++) {
		      for (int j = 0; j < 10; j++) { 
		    	  status[i][j] = Status.EMPTY;
		    	  buttons[i][j].setIcon(new ImageIcon(MainMenu.class.getResource("/resources/key-empty.png")));
		      }
		}
	}
}
