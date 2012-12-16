package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
 * 
 */

public class Log extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993393251000586109L;
	private static JTextArea textAreaLog;
	private static JTextField textFieldMessage;
	private static JScrollPane scrollPane;
    
	
    public Log() {
    	textAreaLog = new JTextArea();
        textAreaLog.setFont(new Font("Times New Roman", 0, 13));
        textAreaLog.setEditable(false);
        scrollPane  = new JScrollPane();
       
        textAreaLog.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent arg0) {
			
				
				
			}
			
			@Override
			public void componentResized(ComponentEvent arg0) {
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			}
			
			@Override
			public void componentMoved(ComponentEvent arg0) {
				
			}
			
			@Override
			public void componentHidden(ComponentEvent arg0) {
				
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

    public static void sendMessage(String msg) {
        textAreaLog.append(getTime()+ msg + "\n");
        
    }

    public static void sendMessage(Message message) {
        sendMessage(message.getBody());

    }

    private static String getTime() {
        return String.valueOf(new Time(System.currentTimeMillis())).substring(0, 5) + " ";
    }

    
}
