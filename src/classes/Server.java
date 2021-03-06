package classes;

import gui.Log;
import gui.MainMenu;

import java.awt.Point;
import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * 
 * @author AlexandrKutashov
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
				if (message.getTitle().equals(Message.TITLE_GAME)) {
					if (message.getBody().equals(Message.BODY_GAME_LETS_START)) {
						if (Player.isPlaying()) {
							out.writeObject(new Message(Message.BODY_GAME_BUSY));
							out.flush();

						} else if (JOptionPane.showConfirmDialog(
								MainMenu.getMainFrame(), "Let's start?",
								"Confirmation", 0) == 0) {
							out.writeObject(new Message(Message.BODY_GAME_OK));
							out.flush();
							Player.setOut(out);
							Player.setShips();
							MainMenu.extendedFrame();

						}
					} else if (message.getBody()
							.equals(Message.BODY_GAME_READY)) {
						Player.setEnemyReadiness(true);

						if (Player.isEnemyReadiness() && Player.isMyReadiness()) {

							Player.startGame();
							Player.setMyTurn(true);
							MainMenu.getRandomButton().setVisible(false);
							MainMenu.getReadyButton().setVisible(false);
							MainMenu.getLblYourTurn().setVisible(true);
							MainMenu.getYourTurnLabel().setVisible(true);
						}
					} else {
						Log.sendMessage(message);

					}
				} else if (message.getTitle().equals(Message.TITLE_SHOOT)) {

					String[] coord = new String[2];
					coord = message.getBody().split(Message.BODY_SPLITTER);
					final Integer i = Integer.parseInt(coord[0]);
					final Integer j = Integer.parseInt(coord[1]);

					if (Player.getMyButtonStatus(i, j).equals(Status.EMPTY)) {
						Player.setMyButtonStatus(i, j, Status.MISSED);
						Player.sendMessage(Message.TITLE_RESPONSE,
								Message.BODY_RESPONSE_MISSED);
						Player.setMyTurn(true);

					} else if (Player.getMyButtonStatus(i, j).equals(
							Status.DECK)) {
						Player.setMyButtonStatus(i, j, Status.KILLED);
						if (!Player.checkIsKilled(i, j)) {
							Player.sendMessage(Message.TITLE_RESPONSE,
									Message.BODY_RESPONSE_INJURED);
						} else {
							if (!Player.isLose()) {
								Player.sendMessage(Message.TITLE_RESPONSE,
										Message.BODY_RESPONSE_KILLED);
							} else {
								Player.sendMessage(Message.TITLE_RESPONSE,
										Message.BODY_RESPONSE_WIN);
								JOptionPane.showMessageDialog(
										MainMenu.getMainFrame(),
										"You losed :(", "What a pity!", 2);
								Player.stopGame();
								MainMenu.runServer();
								break;
							}

						}
					} else {
						Player.sendMessage(Message.TITLE_RESPONSE,
								Message.BODY_RESPONSE_SHOOT_WAS);
					}
				} else if (message.getTitle().equals(Message.TITLE_RESPONSE)) {

					Point point = Player.getLastAttacked();
					if (message.getBody().equals(Message.BODY_RESPONSE_MISSED)) {
						Player.setEnemyButtonStatus(point.x, point.y,
								Status.MISSED);
						Player.setMyTurn(false);
					} else if (message.getBody().equals(
							Message.BODY_RESPONSE_INJURED)) {
						Player.setEnemyButtonStatus(point.x, point.y,
								Status.KILLED);
					} else if (message.getBody().equals(
							Message.BODY_RESPONSE_KILLED)) {
						Player.setEnemyButtonStatus(point.x, point.y,
								Status.KILLED);
						Player.removeEnemyShip(point);
					} else if (message.getBody().equals(
							Message.BODY_RESPONSE_WIN)) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"You win! :)", "Congratulations!", 2);
						Player.stopGame();
						MainMenu.runServer();
						break;
					}
					Player.setGameStatus(Status.GAME);
				}

			} while (true);
			conn.close();
			join();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO problem happened",
					"Port listener error", 0);
			MainMenu.runServer();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unknown answer from target",
					"Port listener error", 0);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}
