package net.catten.hrsys.data.person;

import net.catten.hrsys.data.orgnization.Organization;

import javax.persistence.*;

/**
 * Created by catten on 16/6/1.
 */
@Entity
@Table(name = "job")
public class Job {
    private Integer id;
    private Organization organization;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization orgCode) {
        this.organization = orgCode;
    }
}
