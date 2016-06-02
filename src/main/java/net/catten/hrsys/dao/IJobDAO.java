package net.catten.hrsys.dao;

import net.catten.hrsys.data.person.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by catten on 16/6/2.
 */
public interface IJobDAO extends JpaRepository<Job,Integer> {

}
