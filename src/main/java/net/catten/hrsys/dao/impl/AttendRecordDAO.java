package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IAttendRecordDAO;
import net.catten.hrsys.data.AttendRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

/**
 * Created by catten on 16/5/27.
 */
@Repository
public class AttendRecordDAO extends BaseDAO<AttendRecord> implements IAttendRecordDAO{

}
