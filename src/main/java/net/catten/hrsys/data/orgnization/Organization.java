package net.catten.hrsys.data.orgnization;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * Created by catten on 16/3/15.
 */
@Entity
@Table(name = "organization")
public class Organization {
    private Integer id; //Id of the organization
    private final StringProperty name; //Name of the organization
    private Organization parentOrg; //The parent organization
    private OrgType orgType; //What type the org is.
    private final StringProperty commit;

    public Organization(){
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
        this.name.set(name);
    }

    public String getCommit() {
        return commit.get();
    }

    public void setCommit(String commit) {
        this.commit.setValue(commit);
    }

    @ManyToOne
    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    @ManyToOne
    public Organization getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Organization parentOrg) {
        this.parentOrg = parentOrg;
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
