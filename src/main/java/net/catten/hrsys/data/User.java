package net.catten.hrsys.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.catten.hrsys.data.person.Staff;

import javax.persistence.*;

/**
 * Created by catten on 16/3/15.
 */
@Entity
@Table(name = "system_users")
public class User {
    private Integer id;
    private final StringProperty username;
    private final StringProperty password;
    private Staff owner;
    private final StringProperty commit;

    public User() {
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        commit = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    @ManyToOne
    public Staff getOwner() {
        return owner;
    }

    public void setOwner(Staff owner) {
        this.owner = owner;
    }

    public String getCommit() {
        return commit.get();
    }

    public void setCommit(String commit) {
        this.commit.setValue(commit);
    }
}
