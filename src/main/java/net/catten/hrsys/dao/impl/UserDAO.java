package net.catten.hrsys.dao.impl;

import net.catten.hrsys.dao.IUserDAO;
import net.catten.hrsys.data.User;
import org.springframework.stereotype.Repository;

/**
 * Created by catten on 16/5/26.
 */
@Repository
public class UserDAO extends BaseDAO<User> implements IUserDAO{
}
