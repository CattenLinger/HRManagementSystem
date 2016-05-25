package net.catten.hrsys.data;

import net.catten.hrsys.util.AttendStatus;

import java.util.Date;

/**
 * Created by catten on 16/3/15.
 */
public class AttendRecord {
    private Integer id;
    private Date timePoint;
    private Staff staff;
    private AttendStatus state;
    private String commit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public AttendStatus getState() {
        return state;
    }

    public void setState(AttendStatus state) {
        this.state = state;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
