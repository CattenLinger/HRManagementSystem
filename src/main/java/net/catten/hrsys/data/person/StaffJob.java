package net.catten.hrsys.data.person;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.catten.hrsys.data.orgnization.Organization;

import javax.persistence.*;

/**
 * Created by catten on 16/6/1.
 */
@Entity
@Table(name = "staff_job")
public class StaffJob {
    private Integer id;
    private Staff staff;
    private Job job;
    private Organization organization;
    private StringProperty commit;

    public StaffJob(){
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

    @ManyToOne
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @ManyToOne
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @ManyToOne
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getCommit() {
        return commit.get();
    }

    public void setCommit(String commit) {
        this.commit.set(commit);
    }

    @Transient
    public StringProperty commitProperty() {
        return commit;
    }
}
