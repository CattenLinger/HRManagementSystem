package net.catten.hrsys.dao;

import net.catten.hrsys.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

/**
 * Created by catten on 16/5/26.
 */
public interface IUserDAO extends JpaRepository<User,Integer> {

}
