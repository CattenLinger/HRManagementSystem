package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.OrgRule;

import java.util.List;

/**
 * Created by catten on 16/6/2.
 */
public interface IOrgRuleDAO extends IBaseDAO<OrgRule> {
    List<OrgRule> listAll();
}
