package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.sql.Time;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


import classes.Message;
import classes.Player;

/**
 * 
 * @author AlexandrKutashov
 * @version 0.6
 * 
 */

public class Log extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993393251000586109L;
	private static JTextArea textAreaLog;
	private static JTextField textFieldMessage;
    
	
    public Log() {
    	textAreaLog = new JTextArea();
        textAreaLog.setFont(new Font("Times New Roman", 0, 13));
        textAreaLog.setEditable(false);
       
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setRequestFocusEnabled(true);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				arg0.getAdjustable().setValue(arg0.getAdjustable().getMaximum());
				
			}
        });
        
        
        
        textFieldMessage = new JTextField();
        textFieldMessage.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (!textFieldMessage.getText().equals("")) {
              try {
				sendMessage(Player.getNickname() + ": " + textFieldMessage.getText());
				Player.sendMessage(Player.getNickname() + ": " + textFieldMessage.getText());
              } catch (IOException e1) {
				e1.printStackTrace();
              } 
              
              textFieldMessage.setText("");
            }
          }
        });
        textFieldMessage.setToolTipText("Enter new message here");
        textFieldMessage.setColumns(10);
        scrollPane.setViewportView(textAreaLog);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        		.addComponent(textFieldMessage, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        			.addGap(18)
        			.addComponent(textFieldMessage, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
        );
        setLayout(groupLayout);
    }

    public static void sendMessage(String msg) throws IOException {
        textAreaLog.append(getTime()+ msg + "\n");
        
    }

    public static void sendMessage(Message message) {
        textAreaLog.append(getTime() + message.getBody() + "\n");

    }

    private static String getTime() {
        return String.valueOf(new Time(System.currentTimeMillis())).substring(0, 5) + " ";
    }

    
}
