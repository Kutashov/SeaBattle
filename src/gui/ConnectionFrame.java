package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import classes.Message;
import classes.Player;
import classes.ResourceUtil;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author AlexandrKutashov
 * 
 */

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = -6341383271068388199L;
	private JPanel contentPane;
	private JTextField textFieldIP;

	public ConnectionFrame() {

		setResizable(false);
		setTitle("Connection manager");
		setIconImage(ResourceUtil.getIcon(ResourceUtil.RESOURCE_RADAR)
				.getImage());
		
		setBounds(100, 100, 275, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnConnect = new JButton("Connect");

		JLabel lblEnterIpAdress = new JLabel("Enter IP adress:");
		lblEnterIpAdress.setFont(new Font("Times New Roman", 0, 15));

		textFieldIP = new JTextField();
		textFieldIP.setRequestFocusEnabled(true);
		textFieldIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldIP.getText().equals("")) {
					try {
						Player.establishConnection(textFieldIP.getText());
						Player.sendMessage(new Message(
								Message.BODY_GAME_LETS_START));

						Message answer = Player.getMessage();
						if (answer.getBody().equals(Message.BODY_GAME_BUSY)) {
							dispose();
							JOptionPane.showMessageDialog(
									MainMenu.getMainFrame(),
									"Your partner is playing at this moment. Try again later",
									"Game message", 0);
						} else if (!answer.getBody().equals(
								Message.BODY_GAME_OK)) {
							dispose();
							Player.breakConnection();
							JOptionPane.showMessageDialog(
									MainMenu.getMainFrame(), "Error",
									"Something bad", 0);
						} else {
							dispose();
							JOptionPane.showMessageDialog(
									MainMenu.getMainFrame(), "Game started",
									"User approved your suggestion", 0);
							MainMenu.setServer(Player.getIn(), Player.getOut());
							Player.setShips();
							MainMenu.extendedFrame();

						}

					} catch (UnknownHostException e1) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"User was not found", "Game request error", 0);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"IO problem happend", "Game request error", 0);
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"Unknown answer from target",
								"Game request error", 0);
					}
				}
			}
		});
		textFieldIP.setToolTipText("Enter IP adress you want connect to");
		textFieldIP.setColumns(10);

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldIP.getText().equals("")) {
					try {
						Player.establishConnection(textFieldIP.getText());
						Player.sendMessage(new Message(
								Message.BODY_GAME_LETS_START));

						Message answer = Player.getMessage();
						if (answer.getBody().equals(Message.BODY_GAME_BUSY)) {
							dispose();
							JOptionPane.showMessageDialog(
									MainMenu.getMainFrame(),
									"Your partner is playing at this moment. Try again later",
									"Game message", 0);
						} else if (!answer.getBody().equals(
								Message.BODY_GAME_OK)) {
							dispose();
							Player.breakConnection();
							JOptionPane.showMessageDialog(
									MainMenu.getMainFrame(), "Error",
									"Something bad", 0);
						} else {
							dispose();
							JOptionPane.showMessageDialog(
									MainMenu.getMainFrame(), "Game started",
									"User approved your suggestion", 0);
							MainMenu.setServer(Player.getIn(), Player.getOut());
							Player.setShips();
							MainMenu.extendedFrame();

						}

					} catch (UnknownHostException e1) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"User was not found", "Game request error", 0);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"IO problem happend", "Game request error", 0);
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(MainMenu.getMainFrame(),
								"Unknown answer from target",
								"Game request error", 0);
					}
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																btnConnect,
																GroupLayout.PREFERRED_SIZE,
																101,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				lblEnterIpAdress,
																				GroupLayout.PREFERRED_SIZE,
																				105,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(10)
																		.addComponent(
																				textFieldIP,
																				GroupLayout.PREFERRED_SIZE,
																				128,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(37, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textFieldIP,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblEnterIpAdress,
																GroupLayout.PREFERRED_SIZE,
																29,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(btnConnect).addGap(74)));

		contentPane.setLayout(gl_contentPane);
	}
}
