package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.OrgType;
import net.catten.hrsys.data.orgnization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by catten on 16/5/27.
 */
public interface IOrganizationDAO extends JpaRepository<Organization,Integer> {

}
