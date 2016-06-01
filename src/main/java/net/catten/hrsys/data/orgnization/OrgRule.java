package net.catten.hrsys.data.orgnization;

import javax.persistence.*;

/**
 * Created by catten on 16/6/1.
 */
@Entity
@Table(name = "org_rules")
public class OrgRule {
    private Integer id; //id of the organization
    private OrgType parentType; //
    private OrgType childType; //
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
    public OrgType getParentType() {
        return parentType;
    }

    public void setParentType(OrgType parentOrg) {
        this.parentType = parentOrg;
    }

    @ManyToOne
    public OrgType getChildType() {
        return childType;
    }

    public void setChildType(OrgType childOrg) {
        this.childType = childOrg;
    }

    public Integer getMaxChildrenCount() {
        return maxChildrenCount;
    }

    public void setMaxChildrenCount(Integer maxChildrenCount) {
        this.maxChildrenCount = maxChildrenCount;
    }
}
