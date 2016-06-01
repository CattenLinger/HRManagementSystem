package net.catten.hrsys.data.orgnization;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

/**
 * Created by catten on 16/6/1.
 */
@Entity
@Table(name = "org_type")
public class OrgType {
    private Integer id;
    private String name;

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
}
