package net.catten.hrsys.data.person;

import net.catten.hrsys.util.Gender;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by catten on 16/3/15.
 */
@Entity
@Table(name = "staff")
public class Staff {
    private Integer id;
    private String name;
    private Gender gender;
    private String contactInfo;
    private String commit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
