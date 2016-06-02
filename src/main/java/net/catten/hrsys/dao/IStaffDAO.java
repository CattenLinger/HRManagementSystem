package net.catten.hrsys.dao;

import net.catten.hrsys.data.person.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by catten on 16/5/27.
 */
public interface IStaffDAO extends JpaRepository<Staff,Integer> {

}
