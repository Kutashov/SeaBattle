package classes;

import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceUtil {

	public static ImageIcon getIcon(String name) {
		return new ImageIcon(ResourceUtil.class.getResource("/resources/" + name + ".png"));
	}
	
	public static URL getIconURL(String name) {
		return ResourceUtil.class.getResource("/resources/" + name + ".png");
	}
}
