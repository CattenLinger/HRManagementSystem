package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IBaseDAO;
import net.catten.hrsys.dao.IOrgTypeDAO;
import net.catten.hrsys.data.orgnization.OrgType;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by catten on 16/6/2.
 */
@Repository
public class OrgTypeDAO extends BaseDAO<OrgType> implements IOrgTypeDAO {
    @Override
    public List<OrgType> listAll() {
        return getSession().createQuery("from OrgType").list();
    }
}
