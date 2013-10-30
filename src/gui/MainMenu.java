package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import classes.Message;
import classes.Player;
import classes.ResourceUtil;
import classes.Server;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

/**
 * 
 * @author AlexandrKutashov
 * 
 */

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 2032726310837517393L;
	private JPanel contentPane;
	private static ServerSocket server;
	private static MainMenu mainFrame;
	protected Log log;
	protected static Server serverSocket;
	protected final Player player;
	private static JLabel yourTurnLabel;
	private static JLabel lblYourTurn;
	private static JButton randomButton;
	private static JButton readyButton;
	private static final int DEFAULT_WIDTH = 350;
	private static final int EXTENDED_WIDTH = 390;

	public static void main(String[] args) throws IOException {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			UIManager.getLookAndFeelDefaults();
		}

		mainFrame = new MainMenu();
		runServer();

	}

	public MainMenu() throws IOException {

		setIconImage(ResourceUtil.getIcon(ResourceUtil.RESOURCE_GAME_ICON)
				.getImage());
		setResizable(false);

		setBounds(100, 100, 825, DEFAULT_WIDTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		player = new Player();
		setTitle("Sea Battle: " + Player.getNickname());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnStart = new JMenu("Start");
		menuBar.add(mnStart);

		JMenuItem mntmChangeNickname = new JMenuItem("Change nickname");
		mntmChangeNickname.setIcon(ResourceUtil
				.getIcon(ResourceUtil.RESOURCE_ADDRESSBOOK));
		mntmChangeNickname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame changeNick = new ChangenickFrame();
				changeNick.setVisible(true);
			}
		});
		mnStart.add(mntmChangeNickname);

		JMenuItem mntmConnect = new JMenuItem("Connect");
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame connFrame = new ConnectionFrame();
				connFrame.setVisible(true);
			}
		});
		mntmConnect.setIcon(ResourceUtil.getIcon(ResourceUtil.RESOURCE_RADAR));
		mnStart.add(mntmConnect);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmFeedback = new JMenuItem("Feedback");
		mntmFeedback.setIcon(ResourceUtil
				.getIcon(ResourceUtil.RESOURCE_FEEDBACK_ICON));
		mntmFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(
						MainMenu.getMainFrame(),
						"Made by Kutashov Alexandr\nIf you find any bugs, please mail me to kutashov.alexandr@yandex.ru",
						"About", 1);
			}
		});
		mnAbout.add(mntmFeedback);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		log = new Log();

		BattleField battleField = new BattleField(true);
		Player.setMyBattleField(battleField);
		BattleField battleField_1 = new BattleField(false);
		Player.setEnemyBattleField(battleField_1);

		lblYourTurn = new JLabel("Your turn: ");
		lblYourTurn.setVisible(false);
		lblYourTurn.setFont(new Font("Times New Roman", Font.PLAIN, 24));

		yourTurnLabel = new JLabel();
		yourTurnLabel.setLabelFor(lblYourTurn);
		yourTurnLabel.setPreferredSize(new Dimension(24, 24));

		randomButton = new JButton("Random me!");
		randomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Player.randomShips();
			}
		});
		randomButton.setVisible(false);

		readyButton = new JButton("I'm ready!");
		readyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Player.areShipsSetted()) {
					try {
						Player.sendMessage(Message.BODY_GAME_READY);
						Player.setMyReadiness(true);

						if (Player.isEnemyReadiness() && Player.isMyReadiness()) {

							Player.startGame();
							MainMenu.getRandomButton().setVisible(false);
							MainMenu.getReadyButton().setVisible(false);
							MainMenu.getLblYourTurn().setVisible(true);
							MainMenu.getYourTurnLabel().setVisible(true);
							Player.setMyTurn(false);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		readyButton.setVisible(false);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																battleField,
																GroupLayout.PREFERRED_SIZE,
																274,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				randomButton,
																				GroupLayout.PREFERRED_SIZE,
																				105,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(33)
																		.addComponent(
																				readyButton,
																				GroupLayout.PREFERRED_SIZE,
																				106,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				lblYourTurn,
																				GroupLayout.PREFERRED_SIZE,
																				111,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				yourTurnLabel,
																				GroupLayout.PREFERRED_SIZE,
																				81,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																log,
																GroupLayout.PREFERRED_SIZE,
																232,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(battleField_1,
												GroupLayout.PREFERRED_SIZE,
												272, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																log,
																GroupLayout.PREFERRED_SIZE,
																256,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.LEADING,
																				false)
																		.addComponent(
																				battleField,
																				GroupLayout.PREFERRED_SIZE,
																				256,
																				Short.MAX_VALUE)
																		.addComponent(
																				battleField_1,
																				0,
																				0,
																				Short.MAX_VALUE)))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				lblYourTurn,
																				GroupLayout.DEFAULT_SIZE,
																				40,
																				Short.MAX_VALUE)
																		.addComponent(
																				randomButton,
																				GroupLayout.PREFERRED_SIZE,
																				42,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				readyButton,
																				GroupLayout.PREFERRED_SIZE,
																				42,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																yourTurnLabel,
																GroupLayout.PREFERRED_SIZE,
																40,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
		server = new ServerSocket(Player.getPort());
		server.setSoTimeout(5000);
	}

	public static void runServer() {

		try {
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
			JOptionPane
					.showMessageDialog(
							MainMenu.mainFrame,
							"Creating port listener problem\nApplication will be closed",
							"Critical error", 0);
			System.exit(-1);
		}
	}

	public static void setServer(ObjectInputStream in, ObjectOutputStream out) {

		serverSocket = new Server(in, out);
	}

	public static Component getMainFrame() {

		return mainFrame;
	}

	public static void setYourTurn(boolean flag) {

		if (flag) {
			yourTurnLabel.setBackground(Color.GREEN);
			yourTurnLabel.setOpaque(true);

		} else {
			yourTurnLabel.setBackground(Color.RED);
			yourTurnLabel.setOpaque(true);
		}
	}

	public static JButton getRandomButton() {
		return randomButton;
	}

	public static JButton getReadyButton() {
		return readyButton;
	}

	public static JLabel getYourTurnLabel() {
		return yourTurnLabel;
	}

	public static JLabel getLblYourTurn() {
		return lblYourTurn;
	}

	public static void defaultFrame() {
		mainFrame.setBounds(100, 100, 825, DEFAULT_WIDTH);
		yourTurnLabel.setVisible(false);
		lblYourTurn.setVisible(false);
	}

	public static void extendedFrame() {
		mainFrame.setBounds(100, 100, 825, EXTENDED_WIDTH);
		randomButton.setVisible(true);
		readyButton.setVisible(true);
	}
}
