package net.catten.hrsys.data.person;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.catten.hrsys.data.orgnization.Organization;

import javax.persistence.*;

/**
 * Created by catten on 16/6/1.
 */
@Entity
@Table(name = "job")
public class Job {
    private Integer id;
    private final StringProperty name;
    private final StringProperty commit;

    public Job() {
        name = new SimpleStringProperty();
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
        this.name.setValue(name);
    }

    public String getCommit() {
        return commit.get();
    }

    public void setCommit(String commit) {
        this.commit.set(commit);
    }

    @Transient
    public StringProperty nameProperty() {
        return name;
    }

    @Transient
    public StringProperty commitProperty() {
        return commit;
    }
}
