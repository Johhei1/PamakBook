import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class InputUser extends JFrame {

	private JPanel panel = new JPanel();
	private JTextField userName, mail;
	private JButton UserPage, PossibleInfections, AddingNewUser, Save;
	private ArrayList<User> users;
	private AllObjects all;
	
	
	public InputUser(AllObjects allObject) {
		
	
		all = allObject;
	
		//taking the information from the file
		try {
			FileInputStream fileIn = new FileInputStream("PamakBook.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			all = (AllObjects) in.readObject();
			System.out.println("The file is read");
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		panel.setLayout(new FlowLayout());
		
		
		userName = new JTextField("user name");
		mail = new JTextField("user email");
		UserPage = new JButton("Enter User Page");
		PossibleInfections = new JButton("Show Potential Infections");
		AddingNewUser = new JButton("New User");
		Save = new JButton("Save PamakBook");
		
		
		ButtonListener listener = new ButtonListener();
		
		//button to enter a user's page
		UserPage.addActionListener(listener);
		//button to see the possible infected friends of the user
		PossibleInfections.addActionListener(listener);
		//button to add a new user
		AddingNewUser.addActionListener(listener);
		//button to save the pamakbook to a file
		Save.addActionListener(listener);
			
		panel.add(AddingNewUser);
		panel.add(userName);
		panel.add(mail);
		panel.add(UserPage);
		panel.add(PossibleInfections);
		panel.add(Save);
		
		
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(275, 275);
		this.setTitle("Είσοδος χρήστη");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	class ButtonListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent e) {
			
			boolean exists = false;
			User user1 = null;
			String Username = userName.getText();
			
			if(e.getSource().equals(UserPage)) {
				
				for(User user : all.listofUsers()) { //checks if the user actually exists
					if(user.getName().equals(Username)) {
						exists = true;
						user1 = user;
					}
				}
				
				if(exists) 
					new UserPage(user1, all); //if the user exists,you enter his/her page
				else 
					JOptionPane.showMessageDialog(null, "User "+Username+" Not Found"); // you show a message that the user does not exist

			}
			//event to open the window with the potential infections of the user
			else if(e.getSource().equals(PossibleInfections)) { 
				
				for(User user : all.listofUsers()) {
					if(user.getName().equals(Username)) {
						exists = true;
						user1 = user;
					}
				}
				
				if(exists) 
					new InfectedFriend(user1, all);
				else 
					JOptionPane.showMessageDialog(null, "User "+Username+" Not Found");
			}
			//event to add a new user
			else if(e.getSource().equals(AddingNewUser)) {
				
				String newName = userName.getText();
				String newMail = mail.getText();
				boolean check = false;
				
				//checking if the new user is already in the database
				for(User user : all.listofUsers()) 
					if(newName.equals(user.getName()))
						check = true;
				
				
				if(check) 
					JOptionPane.showMessageDialog(null, "User "+newName+" already in the list.");
				else {
					//if he is not already registered,he/she is created here
					User newUser = new User(newName, newMail);
					all.addUser(newUser);
					System.out.println("New user added");
				}
			}
			//event to save the PamakBook
			else if(e.getSource().equals(Save)) {
				
				try {
					FileOutputStream fileOut = new FileOutputStream("PamakBook.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(all);
					System.out.println("File saved.");
					out.close();
					fileOut.close();		
				}
				catch(IOException i) {
					i.printStackTrace();
				}
				

			}
			
		}
		
	}
}
