package net.catten.hrsys.data.person;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private final StringProperty name;
    private Gender gender;
    private final StringProperty contactInfo;
    private final StringProperty commit;

    public Staff() {
        name = new SimpleStringProperty();
        contactInfo = new SimpleStringProperty();
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

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCommit() {
        return commit.get();
    }

    public void setCommit(String commit) {
        this.commit.setValue(commit);
    }

    public String getContactInfo() {
        return contactInfo.get();
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo.get();
    }

    @Transient
    public StringProperty nameProperty() {
        return name;
    }

    @Transient
    public StringProperty contactInfoProperty() {
        return contactInfo;
    }

    @Transient
    public StringProperty commitProperty() {
        return commit;
    }
}
