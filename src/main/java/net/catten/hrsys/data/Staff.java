package net.catten.hrsys.data;

import net.catten.hrsys.util.Gender;

import javax.persistence.*;

/**
 * Created by catten on 16/3/15.
 */
@Entity
@Table(name = "staff")
public class Staff {
    private Integer id;
    private String name;
    private Gender gender;
    private Department department;
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

    @ManyToOne
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
