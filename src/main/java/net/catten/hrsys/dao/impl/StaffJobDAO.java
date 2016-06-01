package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IJobDAO;
import net.catten.hrsys.dao.IStaffJobDAO;
import net.catten.hrsys.data.person.StaffJob;
import org.springframework.stereotype.Repository;

/**
 * Created by catten on 16/6/2.
 */
@Repository
public class StaffJobDAO extends BaseDAO<StaffJob> implements IStaffJobDAO {
}
