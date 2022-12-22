import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList <User> users = new ArrayList<User>(); //List of all users
		
		//Creating User Objects
		User u1 = new User("Makis", "iis1998@uom.edu.gr");
		User u2 = new User("Petros", "ics1924@uom.edu.gr");
		User u3 = new User("Maria", "iis2012@uom.edu.gr");
		User u4 = new User("Gianna", "iis19133@uom.edu.gr");
		User u5 = new User("Nikos", "dai1758@uom.edu.gr");
		User u6 = new User("Babis", "ics19104@uom.edu.gr");
		User u7 = new User("Stella", "dai1827@uom.edu.gr");
		User u8 = new User("Eleni", "ics2086@gmail.com");
		System.out.println();
		
		//Adding Friends
		u1.add_friend(u2);
		u1.add_friend(u5);
		u5.add_friend(u6);
		u3.add_friend(u4);
		u3.add_friend(u2);
		u4.add_friend(u6);
		u5.add_friend(u3);
		u1.add_friend(u6);
		u5.add_friend(u2);
		u7.add_friend(u1);
		
		//Creating the groups
		Group g1 = new Group("WebGurus","A group for web passionates");//Group object
		ClosedGroup g2 = new ClosedGroup("ExamSolutions","Solutions to common exam questions");//ClosedGroup object
		System.out.println();
		ArrayList<Group> groups = new ArrayList<>();
		groups.add(g1);
		groups.add(g2);
		
		//Adding users to groups
		g1.add_member(u4);
		g1.add_member(u3);
		g1.add_member(u2);
		g2.addFirstMember(u4);
		g2.add_member(u5);
		g2.add_member(u6);
		g2.add_member(u5);
		System.out.println();
		
		//adding all the users to a list to pass them to the GUI
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		users.add(u5);
		users.add(u6);
		users.add(u7);
		
		//class to add all the users and all the groups
		AllObjects allObjects = new AllObjects(users,groups);
		
		//creating the file
		try {
			FileOutputStream fileOut = new FileOutputStream("PamakBook.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(allObjects);
			System.out.println("File created.");
			out.close();
			fileOut.close();		
		}
		catch(IOException i) {
			i.printStackTrace();
		}
	
		new InputUser(allObjects);
	}
}
