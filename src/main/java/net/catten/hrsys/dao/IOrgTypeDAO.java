package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.OrgType;

import java.util.List;

/**
 * Created by catten on 16/6/2.
 */
public interface IOrgTypeDAO extends IBaseDAO<OrgType>{
    List<OrgType> listAll();
}
