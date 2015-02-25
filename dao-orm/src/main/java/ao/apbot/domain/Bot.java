package ao.apbot.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ao.apbot.Template;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "user" }))
public class Bot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    private String name;
    private String user;
    private String password;

    @Enumerated(EnumType.STRING)
    private Template template;

    private boolean active = false;

    private int owner;

    public Bot() {
    }

    public Bot(String name, String user) {
        this.name = name;
        this.user = user;
    }

    public Bot(String name, String user, String password, Template template, int owner) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.template = template;
        this.active = true;

        this.owner = owner;
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

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOwner() {
        return owner;
    }
}
