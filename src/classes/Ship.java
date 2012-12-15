package classes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author AlexandrKutashov
 * 
 */

public class Ship {

	private int deck;
	private List<Point> position = new ArrayList<Point>();
	
	public Ship(int deck) {
		this.deck = deck;
	}

	public int getDeck() {
		return deck;
	}

	public void addPosition(int i, int j) {

		position.add(new Point(i, j));
	}
	
	public String toString () {
		String temp = "";
		if (!position.isEmpty()) {
			temp = " : " + position.toString();
		}	
		return "{" + deck + "}" + temp;
		
		
	}

	public List<Point> getPosition() {
		return position;
	}

	public boolean isKilled() {
		
		for (Point point : position) {
			if (Player.getMyButtonStatus(point.x, point.y).equals(Status.DECK)) {
				return false;
			}
		}
		return true;
	}

}
