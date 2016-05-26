package net.catten.hrsys.data;

import javax.persistence.*;

/**
 * Created by catten on 16/3/15.
 */
@Entity
@Table(name = "system_users")
public class User {
    private Integer id;
    private String username;
    private String password;
    private Staff owner;
    private String commit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    public Staff getOwner() {
        return owner;
    }

    public void setOwner(Staff owner) {
        this.owner = owner;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
