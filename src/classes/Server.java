package classes;

import gui.Log;
import gui.MainMenu;

import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * 
 * @author AlexandrKutashov
 * @version 0.6
 * 
 */

public final class Server extends Thread {

	private Socket conn = null;
	private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
	
    public Server(Socket conn) {
        this.conn = conn;
        setDaemon(true);
        start();
    }

    public Server(ObjectInputStream in, ObjectOutputStream out) {
    	this.in = in;
    	this.out = out;
    	setDaemon(true);
        start();
	}

	public void run() {
        try {
        	if (in == null || out == null) {
        		in = new ObjectInputStream(conn.getInputStream());
                out = new ObjectOutputStream(conn.getOutputStream());
        	}
        	
            do {
                Message message = (Message) in.readObject();
                if(message.getBody().equals("Let's start?")) {
                    if(Player.isPlaying()) {
                        out.writeObject(new Message("Busy"));
                        out.flush();
                        continue;
                    }
                    if(JOptionPane.showConfirmDialog(MainMenu.getMainFrame(), "Let's start?", "Confirmation", 0) == 0) {
                        out.writeObject(new Message("Ok!"));
                        out.flush();
                        Player.setOut(out);
                        continue;
                    }
                } else {
					Log.sendMessage(message);
					
                }
                
            } while(true);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "IO problem happened", "Port listener error", 0);
        }
        catch(ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Unknown answer from target", "Port listener error", 0);
        }
    }

    
}
