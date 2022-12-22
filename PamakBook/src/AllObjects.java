import java.io.Serializable;
import java.util.ArrayList;

//new class in order to be used to save all the info that is needed to the file
public class AllObjects implements Serializable {

	private ArrayList<User> allUsers = new ArrayList<>();
	private ArrayList<Group> allgroups = new ArrayList<>();
	
	public AllObjects(ArrayList<User> users, ArrayList<Group> groups) {
		allUsers = users;
		allgroups = groups;
	}
	
	public void addUser(User user) { //adding a new User to the Database
		
		allUsers.add(user);
		
	}
	
	public void addGroup(Group group) {
		allgroups.add(group);
	}
	
	
	public ArrayList<User> listofUsers(){
		return allUsers;
	}
	
	public ArrayList<Group> listofGroups(){
		return allgroups;
	}
	
	
}
