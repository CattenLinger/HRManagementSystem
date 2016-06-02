package net.catten.hrsys.dao;

import net.catten.hrsys.data.person.StaffJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by catten on 16/6/2.
 */
public interface IStaffJobDAO extends JpaRepository<StaffJob,Integer> {
}
