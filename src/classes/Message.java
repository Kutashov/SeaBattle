package classes;

import java.io.Serializable;


/**
 * 
 * @author AlexandrKutashov
 * 
 */

public class Message implements Serializable {
	private static final long serialVersionUID = 0x76d5372f1fb2b09eL;
    private final String body;
    private final String title;
    
    public Message(String title, String body) {
        this.body = body;
        this.title = title;
    }
    
    public Message(String body) {
        this.body = body;
        this.title = "Game";
    }


    public String toString() {
        return "Message: (" + title +") " + body;
    }

    public String getBody() {
        return body;
    }
    
    public String getTitle () {
    	return title;
    }

    
}
