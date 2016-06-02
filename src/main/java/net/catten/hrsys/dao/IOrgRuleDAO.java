package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.OrgRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by catten on 16/6/2.
 */
public interface IOrgRuleDAO extends CrudRepository<OrgRule,Integer> {

}
