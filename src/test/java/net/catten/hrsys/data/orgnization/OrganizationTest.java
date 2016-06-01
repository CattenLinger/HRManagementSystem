package net.catten.hrsys.data.orgnization;

import net.catten.hrsys.data.orgnization.Organization;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by catten on 16/5/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        //如果测试文件有依赖，则按照这个顺序加载，如果直接使用ContextConfiguration则只加载一个
        @ContextConfiguration("classpath:net/catten/hrsys/applicationContext.xml")
})
//测试里事务默认回滚，但是建议加上
@Transactional
public class OrganizationTest {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    public void TestInsertDepartment() {
        Organization organization = new Organization();
        organization.setName("Test Department");
        organization.setCommit("For Test");
        sessionFactory.getCurrentSession().save(organization);
        assertNotNull(organization.getId());
    }

}