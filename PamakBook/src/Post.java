import java.io.Serializable;

public class Post implements Serializable {
	
	private String timestamp;
	private String text;
	private User user;
	
	public Post(String timestamp, String text, User user) {
	
		this.timestamp = timestamp;
		this.text = text;
		this.user = user;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getText() {
		return text;
	}

	public String getUserName() {
		return user.getName();
	}

}
