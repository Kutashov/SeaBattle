package classes;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.BattleField;
import gui.MainMenu;

/**
 * 
 * @author AlexandrKutashov
 * 
 */

public class Player {

	private static String nickname;
    private static Status gameStatus;
    private static boolean myTurn;
    private static List<Ship> ships = new ArrayList<Ship>();
    private static BattleField myBattleField;
    private static BattleField enemyBattleField;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private final static int port = 8033;
    private static boolean enemyReadiness;
    private static boolean myReadiness;
    private static Point lastAttacked;
    private static Socket conn;
	
    public Player() {
        nickname = "Username" + new Random().nextInt(999);
        gameStatus = Status.FREE;
    }

    public static boolean isPlaying() {
        if (gameStatus.equals(Status.FREE)) {
        	return false;
        } else {
        	return true;
        }
    }


    public static void setNickname(String nick) {
        nickname = nick;
    }

    public static String getNickname() {
        return nickname;
    }
    
    public static void sendMessage(String title, String body) throws IOException {
    	if (out != null) {
    		out.writeObject(new Message(title, body));
            out.flush();
    	}
        
    }
    
    public static void sendMessage(String body) throws IOException {
    	
    	if (out != null) {
    		out.writeObject(new Message(body));
            out.flush();
    	}  
    }
    
    public static void sendMessage(Message message) throws IOException {
    	
		if (out != null) {
    		out.writeObject(message);
            out.flush();
    	}  
	}
    
    public static void establishConnection (String IP) throws IOException {
    	conn = new Socket(IP, port);
        out = new ObjectOutputStream(conn.getOutputStream());
        in = new ObjectInputStream(conn.getInputStream());
     
    }

    public static void startGame() throws IOException {
    	
    	gameStatus = Status.GAME;	
    }


	public static Message getMessage() throws IOException, ClassNotFoundException {
		if (in != null) {
			return (Message) in.readObject();
    	}
		return null;
	}

	public static void breakConnection() throws IOException {
        out = null;
        in = null;     
	}

	public static void refreshFields() {
		  
		myBattleField.refreshField();
		enemyBattleField.refreshField();
	}
	
	public static int getPort () {
		return port;
	}

	public static ObjectOutputStream getOut() {
		return out;
	}

	public static void setOut(ObjectOutputStream out) {
		Player.out = out;
	}

	public static ObjectInputStream getIn() {
		return in;
	}

	public static void setIn(ObjectInputStream in) {
		Player.in = in;
	}

	public static void setShips () {
		
		gameStatus = Status.PREPARATION;
		refreshFields();
		
	}

	public static void randomShips () {
		
		fillShipes();
		myBattleField.refreshField();
		
		Random random = new Random();
		for (Ship ship : ships) {
			
			while (true) {
				final int i = random.nextInt(10);
				final int j = random.nextInt(10);
				int n = random.nextInt(4);
				if (n == 0) {
					if (isAvailableToSet(i, j, i + 1 - ship.getDeck(), j)) {
						int k = i + 1 - ship.getDeck();
						for (; k <= i; ++k) {
							ship.addPosition(k, j);
							myBattleField.setButtonStatus(k, j, Status.DECK);
						}
						break;
					} else {
						++n;
					}
				} 
				if (n == 1) {
					if (isAvailableToSet(i, j, i - 1 + ship.getDeck(), j)) {
						int k = i - 1 + ship.getDeck();
						for (; k >= i; --k) {
							ship.addPosition(k, j);
							myBattleField.setButtonStatus(k, j, Status.DECK);
						}
						break;
					} else {
						++n;
					}
				} 
				if (n == 2) {
					if (isAvailableToSet(i, j, i , j + 1 - ship.getDeck())) {
						int k = j + 1 - ship.getDeck();
						for (; k <= j; ++k) {
							ship.addPosition(i, k);
							myBattleField.setButtonStatus(i, k, Status.DECK);
						}
						break;
					} else {
						++n;
					}
				} 
				if (n == 3) {
					if (isAvailableToSet(i, j, i, j - 1 + ship.getDeck())) {
						int k = j - 1 + ship.getDeck();
						for (; k >= j; --k) {
							ship.addPosition(i, k);
							myBattleField.setButtonStatus(i, k, Status.DECK);
						}
						break;
					}
				} 
				
			}	
		}
		
	}
	
	public static boolean isAvailableToSet (int x0, int y0, int x1, int y1) {
		
		if (!(x0 >= 0 && x0 < 10)) {
			return false;
		}
		if (!(x1 >= 0 && x1 < 10)) {
			return false;
		}
		if (!(y0 >= 0 && y0 < 10)) {
			return false;
		}
		if (!(y1 >= 0 && y1 < 10)) {
			return false;
		}
		if (x1 - x0 < 0) {
			int temp = x1;
			x1 = x0;
			x0 = temp;
		}
		if (y1 - y0 < 0) {
			int temp = y1;
			y1 = y0;
			y0 = temp;
		}
		for (int i = x0; i <= x1; ++i) {
			for (int j = y0; j <= y1; ++j) {
				if (myBattleField.getButtonStatus(i, j).equals(Status.DECK) 
						|| myBattleField.getButtonStatus(i, j).equals(Status.UNDEFINED)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i-1, j-1).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i-1, j).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i, j-1).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i-1, j+1).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i+1, j-1).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i+1, j).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i, j+1).equals(Status.DECK)) {
					return false;
				}
				if (myBattleField.getButtonStatus(i+1, j+1).equals(Status.DECK)) {
					return false;
				}
			}
			
		}
		return true;
	}

	public static void stopGame () throws IOException {
		
		gameStatus = Status.FREE;
		MainMenu.defaultFrame();
		breakConnection();
	}

	private static void fillShipes () {
		
		if (!ships.isEmpty()) {
			ships.clear();
		}
		
		for (int i = 4; i >= 1; --i) {
			for (int j = 1; j <= 5 - i; ++j) {
				ships.add(new Ship(i));
			}
		}
		
		
	}

	public static void setMyBattleField(BattleField myBattleField) {
		Player.myBattleField = myBattleField;
	}

	public static void setEnemyBattleField(BattleField enemyBattleField) {
		Player.enemyBattleField = enemyBattleField;
	}

	public static boolean isEnemyReadiness() {
		return enemyReadiness;
	}

	public static void setEnemyReadiness(boolean enemyReadiness) {
		Player.enemyReadiness = enemyReadiness;
	}

	public static boolean isMyReadiness() {
		return myReadiness;
	}

	public static void setMyReadiness(boolean myReadiness) {
		Player.myReadiness = myReadiness;
	}

	public static boolean isMyTurn() {
		return myTurn;
	}

	public static void setMyTurn(boolean myTurn) {
		
		Player.myTurn = myTurn;
		MainMenu.setYourTurn(myTurn);
	}

	public static Status getGameStatus() {
		return gameStatus;
	}

	public static void setGameStatus(Status gameStatus) {
		Player.gameStatus = gameStatus;
	}
    
	public static Status getMyButtonStatus (int i, int j) {
		return myBattleField.getButtonStatus(i, j);
	}

	public static void setMyButtonStatus(Integer i, Integer j, Status stat) {
		myBattleField.setButtonStatus(i, j, stat);
		
	}
	
	public static Status getEnemyButtonStatus (int i, int j) {
		return enemyBattleField.getButtonStatus(i, j);
	}

	public static void setEnemyButtonStatus(Integer i, Integer j, Status stat) {
		enemyBattleField.setButtonStatus(i, j, stat);
		
	}

	public static boolean checkIsKilled(Integer i, Integer j) {
		
		Point curPoint = new Point(i, j);
		
		for (Ship ship : ships) {
			for (Point point : ship.getPosition()) {
				if (point.equals(curPoint)) {
					if (ship.isKilled()) {
						ships.remove(ship);
						myBattleField.remove(ship);
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	public static boolean isLose() {
		
		return ships.isEmpty();
	}
	

	public static Point getLastAttacked() {
		return lastAttacked;
	}
	

	public static void setLastAttacked(Point lastattacked) {
		Player.lastAttacked = lastattacked;
	}

	
	public static void removeEnemyShip(Point point) {

		List<Point> points = formEnemyShip(point);
		for (Point pt : points) {
			setEnemyButtonStatus(pt.x, pt.y, Status.KILLED);
			if (getEnemyButtonStatus(pt.x+1, pt.y).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x+1, pt.y, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x-1, pt.y).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x-1, pt.y, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x, pt.y+1).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x, pt.y+1, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x, pt.y-1).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x, pt.y-1, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x+1, pt.y+1).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x+1, pt.y+1, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x-1, pt.y-1).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x-1, pt.y-1, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x-1, pt.y+1).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x-1, pt.y+1, Status.MISSED);
			}
			if (getEnemyButtonStatus(pt.x+1, pt.y-1).equals(Status.EMPTY)) {
				setEnemyButtonStatus(pt.x+1, pt.y-1, Status.MISSED);
			}
		}
		
	}
	
	private static List<Point> formEnemyShip (Point point) {
		
		List<Point> points = new ArrayList<Point>();
		points.add(point);
		boolean was = true;
		while (was) {
			was = false;
			for (Point pt : points) { 
				if (getEnemyButtonStatus(pt.x+1, pt.y).equals(Status.KILLED) && !points.contains(new Point(pt.x+1, pt.y))) {
					points.add(new Point(pt.x+1, pt.y));
					was = true;
					break;
				}
				if (getEnemyButtonStatus(pt.x-1, pt.y).equals(Status.KILLED) && !points.contains(new Point(pt.x-1, pt.y))) {
					points.add(new Point(pt.x-1, pt.y));
					was = true;
					break;
				}
				if (getEnemyButtonStatus(pt.x, pt.y+1).equals(Status.KILLED) && !points.contains(new Point(pt.x, pt.y+1))) {
					points.add(new Point(pt.x, pt.y+1));
					was = true;
					break;
				}
				if (getEnemyButtonStatus(pt.x, pt.y-1).equals(Status.KILLED) && !points.contains(new Point(pt.x, pt.y-1))) {
					points.add(new Point(pt.x, pt.y-1));
					was = true;
					break;
				}
			}
			
		}
		
		return points;
		
	}

	public static boolean areShipsSetted () {
		
		return !ships.isEmpty();
	}
}
