/*
    Copyright (C) 2015 Senare

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    contact : aperfectbot@gmail.com
    
 */
package ao.apbot.domain;

import java.util.List;

import com.google.common.collect.Lists;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ElementCollection;
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

    @ElementCollection
    @CollectionTable(name = "bot_admin", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "admin_id")
    private List<Integer> admins;

    public Bot() {
        this.admins = Lists.newArrayList();
    }

    public Bot(String name, String user) {
        this.name = name;
        this.user = user;
        this.admins = Lists.newArrayList();
    }

    public Bot(String name, String user, String password, Template template, int owner) {
        this.name = name;
        this.user = user;
        this.password = password;
        this.template = template;
        this.active = true;

        this.owner = owner;
        this.admins = Lists.newArrayList();
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
