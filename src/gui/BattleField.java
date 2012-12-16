package gui;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import classes.Player;
import classes.ResourceUtil;
import classes.Ship;
import classes.Status;

/**
 * 
 * @author AlexandrKutashov
 * 
 */



public class BattleField extends JPanel {
	
	private static final int size = 10;
	private static final long serialVersionUID = 7394375309988115458L;
	@SuppressWarnings("unused")
	private final boolean isMyField;
	private static final char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
	protected JButton [][] buttons;
	protected Status [][] status;

	public BattleField(final boolean isMyField) {
	  
		this.isMyField = isMyField;
	    buttons = new JButton [size][size];
	    status = new Status [size][size];
	
	    setLayout(new GridLayout(size, size));
	
	    for (int i = 0; i < size; ++i)
	      for (int j = 0; j < size; ++j) {
	        status[i][j] = Status.EMPTY;
	        buttons[i][j] = new JButton(ResourceUtil.getIcon("key-empty"));
	        buttons[i][j].setToolTipText(String.valueOf(letters[j]) + (i + 1));
	        buttons[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	
	        final int I = i;
	        final int J = j;
	        
	        buttons[i][j].addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0)
	        	{
	        		if (!isMyField && Player.getGameStatus().equals(Status.GAME) && Player.isMyTurn()) {
	        			
	        			try {
							Player.sendMessage("Shoot", I + " " + J);
							Player.setLastAttacked(new Point(I, J));
							Player.setGameStatus(Status.WAITING);
						
						} catch (IOException e) {
							
							e.printStackTrace();
						} 
	        			
	        		}
	        		
	        	}
	        });
	        add(buttons[i][j]);
	      }
	}
	
	
	public void refreshField () {
		
		for (int i = 0; i < size; i++) {
		      for (int j = 0; j < size; j++) { 
		    	  status[i][j] = Status.EMPTY;
		    	  buttons[i][j].setIcon(ResourceUtil.getIcon("key-empty"));
		      }
		}
	}
	
	public Status getButtonStatus (int i, int j) {
	
		if (i < size && i >= 0 && j >= 0 && j < size) {
			
			return status[i][j];
		} else {
			return Status.UNDEFINED;
		}
		
	}
	
	public void setButtonStatus (int i, int j, Status stat) {
		
		if (i < size && i >= 0 && j >= 0 && j < size) {
			status[i][j] = stat;
			
			switch (stat) {
				case DECK: buttons[i][j].setIcon(ResourceUtil.getIcon("key-star-empty"));
						break;
				case KILLED: buttons[i][j].setIcon(ResourceUtil.getIcon("key-star-full"));
						break;
				case MISSED: buttons[i][j].setIcon(ResourceUtil.getIcon("key-period"));
						break;
				default:
						break;
			}	
		}
		
	}


	public void remove(Ship ship) {

		for (Point point : ship.getPosition()) {
			setButtonStatus(point.x, point.y, Status.KILLED);
			if (getButtonStatus(point.x+1, point.y).equals(Status.EMPTY)) {
				setButtonStatus(point.x+1, point.y, Status.MISSED);
			}
			if (getButtonStatus(point.x-1, point.y).equals(Status.EMPTY)) {
				setButtonStatus(point.x-1, point.y, Status.MISSED);
			}
			if (getButtonStatus(point.x, point.y+1).equals(Status.EMPTY)) {
				setButtonStatus(point.x, point.y+1, Status.MISSED);
			}
			if (getButtonStatus(point.x, point.y-1).equals(Status.EMPTY)) {
				setButtonStatus(point.x, point.y-1, Status.MISSED);
			}
			if (getButtonStatus(point.x+1, point.y+1).equals(Status.EMPTY)) {
				setButtonStatus(point.x+1, point.y+1, Status.MISSED);
			}
			if (getButtonStatus(point.x-1, point.y-1).equals(Status.EMPTY)) {
				setButtonStatus(point.x-1, point.y-1, Status.MISSED);
			}
			if (getButtonStatus(point.x-1, point.y+1).equals(Status.EMPTY)) {
				setButtonStatus(point.x-1, point.y+1, Status.MISSED);
			}
			if (getButtonStatus(point.x+1, point.y-1).equals(Status.EMPTY)) {
				setButtonStatus(point.x+1, point.y-1, Status.MISSED);
			}
		}
	}
	
	
}


