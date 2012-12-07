package gui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import classes.Player;
import classes.Server;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author AlexandrKutashov
 * @version 0.6
 * 
 */

public class MainMenu extends JFrame {
	
	private static final long serialVersionUID = 2032726310837517393L;
	private JPanel contentPane;
	private static MainMenu mainFrame;
	protected Log log;
	protected static Server serverSocket;
	protected final Player player;

	public static void main(String[] args) throws IOException {
	  
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			UIManager.getLookAndFeelDefaults();
		}
	
		mainFrame = new MainMenu();
		mainFrame.setVisible(true);
		mainFrame.runServer();
  }

  public MainMenu() throws IOException {
	  
	  	setResizable(false);
	  	setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/resources/star-medal-gold-green.png")));
	
	  	setDefaultCloseOperation(3);
	  	setBounds(100, 100, 825, 345);
	
	  	player = new Player();
	  	setTitle("Sea Battle: " + Player.getNickname());
	
	  	JMenuBar menuBar = new JMenuBar();
	  	setJMenuBar(menuBar);
	
	  	JMenu mnStart = new JMenu("Start");
	  	menuBar.add(mnStart);
	
	  	JMenuItem mntmChangeNickname = new JMenuItem("Change nickname");
	  	mntmChangeNickname.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/addressbook.png")));
	  	mntmChangeNickname.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			JFrame changeNick = new ChangenickFrame(player, MainMenu.mainFrame);
			  	changeNick.setVisible(true);
	      	}
	  	});
	  	mnStart.add(mntmChangeNickname);
	
	  	JMenuItem mntmConnect = new JMenuItem("Connect");
	  	mntmConnect.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	    	  	JFrame connFrame = new ConnectionFrame(player);
	    	  	connFrame.setVisible(true);
	
	    	  	if (Player.isPlaying()) {
	    	  		try {
	    	  			player.startGame();
	    	  		} catch (Error e1) {
	    			  	JOptionPane.showMessageDialog(MainMenu.mainFrame, "Game closed by timeout", "Error", 1);
	      
	    		  	} catch (IOException e1) {
	    			  	JOptionPane.showMessageDialog(MainMenu.mainFrame, "Connection error. Game will be closed", "Error", 1);
	            
	    		  	} finally {
	    		  		Player.refreshFields();
	    		  	}
	    	  	}
	  		}
	  	});
	  	mntmConnect.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/radar.png")));
	  	mnStart.add(mntmConnect);
	
	  	JMenu mnGame = new JMenu("Game");
	  	menuBar.add(mnGame);
	
	  	JMenuItem mntmSave = new JMenuItem("Save");
	  	mntmSave.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/floppydisc.png")));
	  	mnGame.add(mntmSave);
	
	  	JMenuItem mntmLoad = new JMenuItem("Load");
	  	mntmLoad.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/controllButton-Play.png")));
	  	mnGame.add(mntmLoad);
	
	  	JMenu mnAbout = new JMenu("About");
	  	menuBar.add(mnAbout);
	
	  	JMenuItem mntmFeedback = new JMenuItem("Feedback");
	  	mntmFeedback.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/facebook-alternative.png")));
	  	mntmFeedback.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent arg0) {
	  			JOptionPane.showMessageDialog(MainMenu.mainFrame, "Made by Kutashov Alexandr\nIf you find any bugs, please mail me to kutashov.alexandr@yandex.ru", "About", 1);
	      	}
	  	});
	  	mnAbout.add(mntmFeedback);
	  	contentPane = new JPanel();
	  	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	  	setContentPane(contentPane);
	
	  	log = new Log();
	    
	    
	  	BattleField battleField = new BattleField();
	    
	  	BattleField battleField_1 = new BattleField();
	    
	
	  	GroupLayout gl_contentPane = new GroupLayout(contentPane);
	  	gl_contentPane.setHorizontalGroup(
	  		gl_contentPane.createParallelGroup(Alignment.TRAILING)
	  			.addGroup(gl_contentPane.createSequentialGroup()
	    		.addContainerGap()
	    		.addComponent(battleField, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
	    		.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	    		.addComponent(log, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
	    		.addPreferredGap(ComponentPlacement.UNRELATED)
	    		.addComponent(battleField_1, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
	    		.addContainerGap())
	    );
	    gl_contentPane.setVerticalGroup(
	    	gl_contentPane.createParallelGroup(Alignment.LEADING)
	    		.addGroup(gl_contentPane.createSequentialGroup()
	    			.addContainerGap()
	    			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
	    				.addComponent(log, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
	    				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
	    					.addComponent(battleField, GroupLayout.PREFERRED_SIZE, 256, Short.MAX_VALUE)
	    					.addComponent(battleField_1, 0, 0, Short.MAX_VALUE)))
	    			.addContainerGap(20, Short.MAX_VALUE))
	    );
	    contentPane.setLayout(gl_contentPane);
	  }
	
	public void runServer() {
		  
		try {
			ServerSocket server = new ServerSocket(Player.getPort());
			server.setSoTimeout(5000);
			Socket sock = null;
			while (serverSocket == null) {
				try {
					sock = server.accept();
				} catch (SocketTimeoutException e) {
					continue;
				}
				serverSocket = new Server(sock);
				break;
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(MainMenu.mainFrame, "Creating port listener problem\nApplication will be closed", "Critical error", 0);
			serverSocket = null;
		}
	}

	public static void setServer(ObjectInputStream in, ObjectOutputStream out) {
		
		serverSocket = new Server(in, out);
	}

	public static Component getMainFrame() {
		
		return mainFrame;
	}

	
}
