import java.util.ArrayList;

public class ClosedGroup extends Group  {
	
	public ClosedGroup(String name, String description) {
		super(name,description);
	}
	
	//Method to add the first user to the group
	public void addFirstMember(User user) {
		members.add(user);
		user.addGroup(this);
		System.out.println(user.getName()+" has been successfully enrolled in group "+name);
	}
	
	//Method to add a new member to the closed group
	public void add_member(User user) {
		
		boolean canEnter= false;
		
		if(!(this.is_member(user))) {
			for(User i : members) {
				if(user.is_friends(i))
					canEnter = true;
			}
		}
			
			if(canEnter) {
				members.add(user);
				user.addGroup(this);
				System.out.println(user.getName()+" has been successfully enrolled in group "+name);
			}
			else 
				System.out.println("FAILED: "+user.getName()+" cannot be enrolled in group "+name);
			
		
	}
	
	public ArrayList<User> memberList(){
		
		return members;
	}
	
}
