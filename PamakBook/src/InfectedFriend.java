import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class InfectedFriend extends JFrame {

	private JPanel panel = new JPanel();
	private JTextArea area;
	private JButton login;
	private User user;
	private AllObjects all;
	
	public InfectedFriend(User user1, AllObjects all) {
		
		user = user1;
		this.all = all;
		
		area = new JTextArea(10, 20);
		
		for(int x=0;x<70;x++)
			area.append("*");
		area.append("\n");
		
		area.append(user.getName()+" has been infected. The following users have to be tested\n");
		
		for(int x=0;x<70;x++)
			area.append("*");
		area.append("\n");
		
		for(User i : user.infected()){
			area.append(i.getName()+"\n");
		}
		
		login = new JButton("Back to Login Screen");
		ButtonListener listener = new ButtonListener();
		login.addActionListener(listener);
		
		panel.add(area);
		panel.add(login);
		
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(400, 400);
		this.setTitle("Πιθανή Μετάδοση ιού");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new InputUser(all);
		}
	}
}
