package gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import classes.Player;
import classes.ResourceUtil;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ChangenickFrame extends JFrame {

	private static final long serialVersionUID = -9212474436929699540L;
	private JPanel contentPane;
	private JTextField textFieldoldNickname;
	private JLabel lblChangeTo;
	private JTextField textFieldnewNickname;

	public ChangenickFrame() {

		setResizable(false);
		setIconImage(ResourceUtil.getIcon(ResourceUtil.RESOURCE_ADDRESSBOOK)
				.getImage());
		setTitle("Change nickname");
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblYourNickname = new JLabel("Your nickname");
		lblYourNickname.setFont(new Font("Times New Roman", 0, 15));

		textFieldoldNickname = new JTextField();
		textFieldoldNickname.setColumns(10);
		textFieldoldNickname.setText(Player.getNickname());
		textFieldoldNickname.setEditable(false);

		lblChangeTo = new JLabel("Change to");
		lblChangeTo.setFont(new Font("Times New Roman", 0, 15));
		textFieldnewNickname = new JTextField();

		textFieldnewNickname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((!textFieldnewNickname.getText().equals(""))
						&& (!textFieldnewNickname.getText().equals(
								Player.getNickname()))) {

					try {
						Log.sendMessage(Player.getNickname()
								+ " has changed his nickname to "
								+ textFieldnewNickname.getText());
						Player.sendMessage(Player.getNickname()
								+ " has changed his nickname to "
								+ textFieldnewNickname.getText());
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					Player.setNickname(textFieldnewNickname.getText());
					((Frame) MainMenu.getMainFrame()).setTitle("Sea Battle: "
							+ Player.getNickname());
					dispose();

				}
			}
		});
		textFieldnewNickname.setToolTipText("Enter your new nickname here");
		textFieldnewNickname.setColumns(10);

		JButton btnChange = new JButton("Change");
		btnChange.setToolTipText("Click to change your nickname");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((!textFieldnewNickname.getText().equals(""))
						&& (!textFieldnewNickname.getText().equals(
								Player.getNickname()))) {

					try {
						Log.sendMessage(Player.getNickname()
								+ " has changed his nickname to "
								+ textFieldnewNickname.getText());
						Player.sendMessage(Player.getNickname()
								+ " has changed his nickname to "
								+ textFieldnewNickname.getText());
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					Player.setNickname(textFieldnewNickname.getText());
					((Frame) MainMenu.getMainFrame()).setTitle("Sea Battle: "
							+ Player.getNickname());
					dispose();

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
																Alignment.LEADING)
														.addComponent(
																lblYourNickname)
														.addComponent(
																lblChangeTo,
																GroupLayout.PREFERRED_SIZE,
																85,
																GroupLayout.PREFERRED_SIZE))
										.addGap(47)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				btnChange,
																				GroupLayout.PREFERRED_SIZE,
																				103,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap())
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.LEADING)
																		.addGroup(
																				gl_contentPane
																						.createSequentialGroup()
																						.addComponent(
																								textFieldoldNickname,
																								GroupLayout.DEFAULT_SIZE,
																								112,
																								Short.MAX_VALUE)
																						.addContainerGap())
																		.addGroup(
																				Alignment.TRAILING,
																				gl_contentPane
																						.createSequentialGroup()
																						.addComponent(
																								textFieldnewNickname,
																								GroupLayout.DEFAULT_SIZE,
																								127,
																								Short.MAX_VALUE)
																						.addContainerGap())))));
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
																Alignment.BASELINE)
														.addComponent(
																lblYourNickname)
														.addComponent(
																textFieldoldNickname,
																GroupLayout.PREFERRED_SIZE,
																23,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblChangeTo,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textFieldnewNickname,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(btnChange)
										.addContainerGap(66, Short.MAX_VALUE)));

		contentPane.setLayout(gl_contentPane);
	}
}
