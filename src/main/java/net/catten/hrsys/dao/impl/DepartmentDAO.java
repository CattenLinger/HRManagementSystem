package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IBaseDAO;
import net.catten.hrsys.dao.IDepartmentDAO;
import net.catten.hrsys.data.Department;
import org.springframework.stereotype.Repository;

/**
 * Created by catten on 16/5/27.
 */
@Repository
public class DepartmentDAO extends BaseDAO<Department> implements IDepartmentDAO{
}
