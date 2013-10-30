package classes;

import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceUtil {

	public static final String RESOURCE_EMPTY_FIELD = "key-empty";
	public static final String RESOURCE_DECK_FIELD = "key-star-empty";
	public static final String RESOURCE_KILLED_FIELD = "key-star-full";
	public static final String RESOURCE_MISSED_FIELD = "key-period";
	public static final String RESOURCE_RADAR = "radar";
	public static final String RESOURCE_ADDRESSBOOK = "addressbook";
	public static final String RESOURCE_GAME_ICON = "star-medal-gold-green";
	public static final String RESOURCE_FEEDBACK_ICON = "facebook-alternative";

	public static ImageIcon getIcon(String name) {
		return new ImageIcon(ResourceUtil.class.getResource("/resources/"
				+ name + ".png"));
	}

	public static URL getIconURL(String name) {
		return ResourceUtil.class.getResource("/resources/" + name + ".png");
	}
}
