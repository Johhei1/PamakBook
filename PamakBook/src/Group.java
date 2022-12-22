import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

	protected String name;
	private String description;
	protected ArrayList <User> members = new ArrayList<User>();
	
	public Group(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	//Method to check if the user is a member of the group
	public boolean is_member(User user) {
		for(User i : members) {
			if(user.equals(i))
				return true;
		}
		return false;
	}
	
	//Method to add a user in the group
	public void add_member(User user) {
		if(!this.is_member(user)) {
			members.add(user);
			user.addGroup(this);
			System.out.println(user.getName()+" has been successfully enrolled in group "+name);
		}
	}
	
	public String getName() {
		return name;
	}

	//Method to print the users of the group
	public void printMembers() {
		
		int sum = 0;
		
		System.out.println("Members of group "+name);
		System.out.println("*******************************");
		
		for(User i : members) {
			sum++;
			System.out.println(sum+": "+i.getName()); 
		}
		System.out.println("-----------------------------");
		System.out.println("*******************************");
	}
}
