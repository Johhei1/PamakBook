import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;


public class User implements Serializable {

	private String name;
	private String email;
	private ArrayList <User> friends = new ArrayList <User>(); //List for friends
	private ArrayList <Group> groups = new ArrayList <Group>();//List for groups
	private ArrayList <Post> posts = new ArrayList <Post>();//List for posts for a user

	public User(String name, String email) {
		if(!(email.substring(0, 3).contains("iis") ||
				 email.substring(0, 3).contains("ics") ||
				 email.substring(0, 3).contains("dai"))) {
			
		
			System.out.println("User "+name+" has not been created. Email format is not acceptable.");
		}
		
		else if(!(email.contains("@uom.edu.gr"))) {
			
			System.out.println("User "+name+" has not been created. Email format is not acceptable.");
			
		}
		
		else if(email.substring(3, email.indexOf('@')).length()<3 || 
					  email.substring(3, email.indexOf('@')).length()>5) {
						
						System.out.println("User "+name+" has not been created. Email format is not acceptable.");
					
		}
			
		else {
			this.name = name;
			this.email = email;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}

	
	public ArrayList<Post> getPosts() {
		return posts;
	}

	//Method to check if they are friends
	public boolean is_friends(User user) {
		for(User i : friends) {
			if(i.equals(user))
				return true;
		}
		return false;
	}
	
	//Method to add the user1 as friend
	public void add_friend(User user1) {
		if(!(this.equals(user1))) {
			if(!this.is_friends(user1)) {
				friends.add(user1);
				user1.add_friendback(this);
				System.out.println(name+" and "+user1.getName()+" are now friends!");
			}
		}
	}
	
	//here the user who called the method "add_friend" is added back as friend to the user1
	private void add_friendback(User user2) {
		if(!(this.equals(user2))) {
			if(!this.is_friends(user2)) {
				friends.add(user2);
			}
		}
	}
	
	//Method to make a list with the common friends between the one who called the method and the object user
	public ArrayList<User> sameFriends(User user) {
		
		ArrayList <User> commonFriends = new ArrayList<User>();
		
		for(User i : friends) {
			if(user.is_friends(i)) {
				commonFriends.add(i);
			}
		}
		return commonFriends;
	}

	//print the friends of the who called the method
	public void printFriends() {
		
		int sum = 0;
		
		System.out.println("Friends of "+name);
		System.out.println("************************");
		
		for(User i : friends) {
			sum++;
			System.out.println(sum+": "+i.getName());
		}
		System.out.println("-----------------------");
	}
	
	public void addGroup(Group group) {
		groups.add(group);
	}
	
	//Method to print the groups of the user who called the method
	public void printGroups() {
		
		int sum = 0;
		
		System.out.println("Groups that "+name+" has been enrolled in");
		System.out.println("**************************************");
		
		for(Group i : groups) {
			sum++;
			System.out.println(sum+": "+i.getName());
		}
		
		System.out.println("--------------------------------------");
		System.out.println("*******************************");
	}
	

	//find possible infected users who were close to the one who called the method	
	public ArrayList<User> infected() {
		
		ArrayList <User> infectedUsers = new ArrayList <User>();//List for possible infected users
		
		for(User i : friends) { //adding the friends of the caller of the method
			infectedUsers.add(i);
		}
		
		for(User i : friends) { //adding the friends of the caller of the method
			i.InfectedMaybe(this,infectedUsers);
		}
		
		Collection <User> noDups = new HashSet<User>(infectedUsers);
		infectedUsers.removeAll(infectedUsers);
		
		for(User i : noDups)
			infectedUsers.add(i);
		
		return infectedUsers;
	}
	
	private void InfectedMaybe(User u1,ArrayList <User>x) { // adding the friends of the friends 
	
		for(User i : friends) {
			if(!i.equals(u1)) 
				x.add(i);
		}
	}
	
	public void addPost(Post post) { //method to add a post
		this.posts.add(post);
	}
	
	public ArrayList<Post> PostLists() { //method to collect the posts of the user and his friends
		
		ArrayList <Post> ListofPosts = new ArrayList <Post>();
		
		ListofPosts = posts;
		
		for(User i : friends) {
			if(!i.equals(this)) 
				i.PostsofFriends(ListofPosts);
		}
		
		Collection <Post> noDups = new HashSet<Post>(ListofPosts);//turning the list into a hashset to remove the duplicates
		ListofPosts.removeAll(ListofPosts); //emptying the list
		
		for(Post i : noDups)
			ListofPosts.add(i); //filling the list with the elements of the hashset
		
		Comparator<Post> compareByTimestamp = new Comparator<Post>() { //making a comparator to find the recent posts and sort them

			@Override
			public int compare(Post p1, Post p2) {
				
				return p1.getTimestamp().compareTo(p2.getTimestamp());
			}
		
		};
		
		ListofPosts.sort(Collections.reverseOrder(compareByTimestamp)); //sorting the list of posts
		
		return ListofPosts;
	}
	
	private void PostsofFriends(ArrayList <Post>x) { //adding the posts of the friends 
		
		for(Post post : posts)
			x.add(post);
	}
	
	public ArrayList<User> SuggestedFriends() { //method to find the suggested the friends
		
		ArrayList <User> suggestedlist = new ArrayList<User>();
		
		//2 hashsets,1 with the friends of the user and 1 with the friends of the user's friends and his friends
		Collection <User> friendSet = new HashSet<>(friends);
		Collection <User> friendofFriendSet = new HashSet<>(this.infected());
		
		friendofFriendSet.removeAll(friendSet);//removing the friends of the user from the 2nd hashset
		
		Iterator<User> iter = friendofFriendSet.iterator();
		
		//adding the elements to the list
		while(iter.hasNext()) {
			User u = (User) iter.next();
			suggestedlist.add(u);
		}
		
		return suggestedlist;
	}

}