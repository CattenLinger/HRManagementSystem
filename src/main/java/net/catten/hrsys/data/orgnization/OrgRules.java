package net.catten.hrsys.data.orgnization;

import javax.persistence.*;

/**
 * Created by catten on 16/6/1.
 */
@Entity
@Table(name = "org_rules")
public class OrgRules {
    private Integer id; //id of the organization
    private Organization parentOrg; //
    private Organization childOrg; //
    private Integer maxChildrenCount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public Organization getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Organization parentOrg) {
        this.parentOrg = parentOrg;
    }

    @ManyToOne
    public Organization getChildOrg() {
        return childOrg;
    }

    public void setChildOrg(Organization childOrg) {
        this.childOrg = childOrg;
    }

    public Integer getMaxChildrenCount() {
        return maxChildrenCount;
    }

    public void setMaxChildrenCount(Integer maxChildrenCount) {
        this.maxChildrenCount = maxChildrenCount;
    }
}
