package classes;

import java.io.Serializable;

/**
 * 
 * @author AlexandrKutashov
 * 
 */

public class Message implements Serializable {

	public static final String TITLE_GAME = "Game";
	public static final String TITLE_SHOOT = "Shoot";
	public static final String TITLE_RESPONSE = "Response";
	public static final String BODY_GAME_LETS_START = "Let's start?";
	public static final String BODY_GAME_BUSY = "Busy";
	public static final String BODY_GAME_OK = "Ok!";
	public static final String BODY_GAME_READY = "I'm ready";
	public static final String BODY_RESPONSE_MISSED = "Missed";
	public static final String BODY_RESPONSE_INJURED = "Injured";
	public static final String BODY_RESPONSE_KILLED = "Killed";
	public static final String BODY_RESPONSE_WIN = "You win";
	public static final String BODY_RESPONSE_SHOOT_WAS = "Was";
	public static final String BODY_SPLITTER = " ";

	private static final long serialVersionUID = 0x76d5372f1fb2b09eL;
	private final String body;
	private final String title;

	public Message(String title, String body) {
		this.body = body;
		this.title = title;
	}

	public Message(String body) {
		this.body = body;
		this.title = TITLE_GAME;
	}

	public String toString() {
		return "Message: (" + title + ") " + body;
	}

	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

}
