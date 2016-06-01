package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IJobDAO;
import net.catten.hrsys.data.person.Job;
import org.springframework.stereotype.Repository;

/**
 * Created by catten on 16/6/2.
 */
@Repository
public class JobDAO extends BaseDAO<Job> implements IJobDAO {
}
