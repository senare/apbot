package ao.apbot.domain;

import javax.persistence.*;

@Entity
@Table
public class Bot {

	@Id
	@GeneratedValue
	private Long id;

	private String chatId;
	private String name;
	private String user;
	private String password;

	public Bot() {
	}

	public Bot(String name, String user, String password) {
		this.name = name;
		this.user = user;
		this.password = password;
	}

	public Bot(String chatId, String name, String user, String password) {
		this.chatId = chatId;
		this.name = name;
		this.user = user;
		this.password = password;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
