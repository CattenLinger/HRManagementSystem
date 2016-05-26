package net.catten.hrsys.data;

import net.catten.hrsys.util.Gender;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
public class AttendRecordTest {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Test
    public void InsertAttendRecord(){
        Department department = new Department();
        department.setName("Department A");
        department.setCommit("Inside an user that inside an attend record");

        Staff staff = new Staff();
        staff.setName("Staff A");
        staff.setGender(Gender.female);
        staff.setDepartment(department);
        staff.setCommit("Inside an attend record");

        AttendRecord attendRecord = new AttendRecord();
        attendRecord.setTimePoint(new Date());
        attendRecord.setStaff(staff);
        sessionFactory.getCurrentSession().save(attendRecord);
        assertNotNull(attendRecord);
    }

}