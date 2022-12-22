import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;


public class UserPage extends JFrame {
	
	private JPanel panel = new JPanel();
	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JTextField name, mail, newFriendName;
	private JButton loginScreen, post, newFriendButton, enterGroup;
	private JTextArea postArea, otherPosts;
	private JLabel RecentP, suggested; 
	private JLabel  SuggestedName;
	private User user;
	private ArrayList <JLabel> suggestedFr = new ArrayList<>();
	private JList grouplist;
	private DefaultListModel model;
	private AllObjects all;
	
	public UserPage(User user1, AllObjects all) {
		
		user = user1;
		this.all = all;
		
		panel.setLayout(new BorderLayout());
		
		//northPanel
		
		name = new JTextField(user.getName(),10);
		mail = new JTextField(user.getEmail(),12);
		
		
		loginScreen = new JButton("Back to Login Screen");
		newFriendButton = new JButton("Add a new friend");
		
		ButtonListener listener1 = new ButtonListener();
		
		loginScreen.addActionListener(listener1);

		northPanel.add(name);
		northPanel.add(mail);
		northPanel.add(loginScreen);
		
		panel.add(northPanel, BorderLayout.NORTH);
		
		//centerPanel
		
		centerPanel.setLayout(new FlowLayout());
		
		newFriendName = new JTextField(12);
		
		
		newFriendButton.addActionListener(listener1);
	
		//creating a JList with the groups
		grouplist = new JList();
		model = new DefaultListModel();
		for(Group group : all.listofGroups())
			model.addElement(group.getName());
		grouplist.setModel(model);
		
		enterGroup = new JButton("Enter in a group");
		enterGroup.addActionListener(listener1);
		
		//button to add a new post	
		post = new JButton("Post");
		post.addActionListener(listener1);
	
		//textArea to write the new Post
		postArea = new JTextArea(10, 20);
		postArea.setLineWrap(true); // changing line if filled
		postArea.setWrapStyleWord(true); //wrapping the words to the next lines if the current line is filled
		
		RecentP = new JLabel("Recent Posts by Friends");
		otherPosts = new JTextArea(10, 20);
		otherPosts.setLineWrap(true);// changing line if filled
		otherPosts.setWrapStyleWord(true);//wrapping the words to the next lines if the current line is filled
		
		suggested = new JLabel("Suggested Friends:");
		SuggestedName = new JLabel();
		
		//adding the Posts to the textArea which has all the posts of the user and his friends
		for(Post i : user.PostLists()){
			otherPosts.append(i.getUserName() +" "+i.getTimestamp()+": " + i.getText() + "\n");
		}
		
		
		centerPanel.add(newFriendButton);
		centerPanel.add(newFriendName);
		centerPanel.add(grouplist);
		centerPanel.add(enterGroup);
		centerPanel.add(postArea);
		centerPanel.add(post);
		centerPanel.add(RecentP);
		centerPanel.add(otherPosts);
		centerPanel.add(suggested);
		
		//adding the suggested friends as labels to a new list
		for(User u1 : user.SuggestedFriends()) {
			
			SuggestedName = new JLabel(u1.getName());
			suggestedFr.add(SuggestedName);
		}
		
		//adding the new labels to the centerpanel
		for(JLabel l1 : suggestedFr)
			centerPanel.add(l1);
		
		panel.add(centerPanel, BorderLayout.CENTER);
		
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(439, 540);
		this.setTitle("Σελίδα Χρήστη");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	
	class ButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String text = postArea.getText();
			
			//getting the time and date of the post
			DateTimeFormatter time = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			
			Post post1 = new Post(time.format(now), text, user);
			
			if(e.getSource().equals(post)) {
				user.addPost(post1);
				System.out.println("New Post has been added");
			}
			
			//event to go back to the logic screen
			else if(e.getSource().equals(loginScreen)) {
				
				//saving what you have done in the userpage in case the logic screen that was created before was closed
				try {
					FileOutputStream fileOut = new FileOutputStream("PamakBook.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(all);
					out.close();
					fileOut.close();		
				}
				catch(IOException i) {
					i.printStackTrace();
				}
				
				new InputUser(all);
			}
			
			//event to add a new friend
			else if(e.getSource().equals(newFriendButton)) {
				
				String newFriend = newFriendName.getText();
				User newFriendUser = null;
				
				//checking if the user exists in order to give him/her  his/hers value from the list 
				for(User u : all.listofUsers())
					if(u.getName().equals(newFriend))
						newFriendUser = u;
				
				//if the user(of the userpage) is not already friends with the one that was added in the textfield,add him to both sides
				if(!user.is_friends(newFriendUser))
					user.add_friend(newFriendUser);
				else
					//if they are already friends,show an appropiate message
					JOptionPane.showMessageDialog(null, "User "+newFriend+" is already friends with "+user.getName());
				
			}
			
			//event to add a user to a group
			else {
				
				String groupName = (String) grouplist.getSelectedValue();
				
				for(Group g : all.listofGroups()) {
					if(g.getName().equals(groupName)) 
						g.add_member(user);
				}
				
			}
				
		}
		
	}
}
