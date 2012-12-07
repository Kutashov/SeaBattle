package classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import javax.swing.Timer;
import gui.BattleField;

/**
 * 
 * @author AlexandrKutashov
 * @version 0.6
 * 
 */

public class Player {

	private static String nickname;
    private static String gameWith;
    private boolean myTurn;
    protected static Timer timeout;
    private static BattleField myBattleField;
    private static BattleField enemyBattleField;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private final static int port = 8033;
	
    public Player() {
        gameWith = "";
        Random random = new Random();
        nickname = "Username" + random.nextInt(999);
        timeout = null;
        myBattleField = new BattleField();
        enemyBattleField = new BattleField();
    }

    public static boolean isPlaying() {
        return !gameWith.equals("");
    }

    public void setEnemy(String IP) {
        gameWith = IP;
    }

    public void setNickname(String nick) {
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
    	Socket conn = new Socket(IP, port);
        out = new ObjectOutputStream(conn.getOutputStream());
        in = new ObjectInputStream(conn.getInputStream());
        gameWith = IP;
    }

    public void startGame() throws IOException {
    	
        
    }


	public static Message getMessage() throws IOException, ClassNotFoundException {
		if (in != null) {
			return (Message) in.readObject();
    	}
		return null;
	}

	public static void breakConnection() {
        out = null;
        in = null;
        gameWith = "";
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

    

}
