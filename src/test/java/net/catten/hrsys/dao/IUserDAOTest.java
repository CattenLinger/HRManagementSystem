package net.catten.hrsys.dao;

import net.catten.hrsys.data.person.Staff;
import net.catten.hrsys.data.User;
import net.catten.hrsys.util.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        //如果测试文件有依赖，则按照这个顺序加载，如果直接使用ContextConfiguration则只加载一个
        @ContextConfiguration("classpath:net/catten/hrsys/applicationContext.xml")
})
//测试里事务默认回滚，但是建议加上
@Transactional
public class IUserDAOTest {
    private IUserDAO userDAO;

    @Autowired
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Test
    public void DAOInsertUser(){
        User user = new User();
        user.setUsername("daoUserTest");
        user.setPassword("000000");
        user.setCommit("For UserDAO Test");
        userDAO.save(user);
        assertNotNull(user);
    }

    @Test
    public void DAOInsertUserWithStaff(){
        Staff staff = new Staff();
        staff.setName("Inside User");
        staff.setGender(Gender.both);
        staff.setCommit("Inside User");
        User user = new User();
        user.setUsername("daoUserTestWithStaff");
        user.setPassword("000000");
        user.setCommit("For UserDAO Test with a staff");
        user.setOwner(staff);
        userDAO.save(user);
        assertNotNull(user);
    }
}