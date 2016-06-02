package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IOrgRuleDAO;
import net.catten.hrsys.data.orgnization.OrgRule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by catten on 16/6/2.
 */
@Repository
public class OrgRuleDAO extends BaseDAO<OrgRule> implements IOrgRuleDAO {
    @Override
    public List<OrgRule> listAll() {
        return getSession().createQuery("from OrgRule").list();
    }
}
