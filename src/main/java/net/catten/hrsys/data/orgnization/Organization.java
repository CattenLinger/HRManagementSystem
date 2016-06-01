package net.catten.hrsys.data.orgnization;

import javax.persistence.*;

/**
 * Created by catten on 16/3/15.
 */
@Entity
@Table(name = "organization")
public class Organization {
    private Integer id; //Id of the organization
    private String name; //Name of the organization
    private Organization parentOrg; //The partent organization
    private OrgType orgType; //What type the org is.
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

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
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
}
