package net.catten.hrsys.data.person;

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
}
