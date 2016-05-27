package net.catten.hrsys.dao;

import net.catten.hrsys.data.Department;
import net.catten.hrsys.data.Staff;
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

/**
 * Created by catten on 16/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        //如果测试文件有依赖，则按照这个顺序加载，如果直接使用ContextConfiguration则只加载一个
        @ContextConfiguration("classpath:net/catten/hrsys/applicationContext.xml")
})
//测试里事务默认回滚，但是建议加上
@Transactional
public class IStaffDAOTest {
    private IStaffDAO staffDAO;

    @Autowired
    public void setStaffDAO(IStaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Test
    public void InsertStaffTest() {
        int i = 0;
        for (Gender gender : Gender.values()) {
            Staff staff = new Staff();
            staff.setName("Staff" + i);
            staff.setDepartment(null);
            staff.setGender(gender);
            staff.setCommit("Test object " + i);
            staffDAO.save(staff);
            i++;
            assertNotNull(staff.getId());
        }
    }

    @Test
    public void InsertStaffWithDepartment(){
        Department department = new Department();
        department.setName("Department S");
        department.setCommit("Department inside staff");

        int i = 0;
        for (Gender gender : Gender.values()) {
            Staff staff = new Staff();
            staff.setName("Staff" + i);
            staff.setDepartment(null);
            staff.setGender(gender);
            staff.setCommit("Test object " + i);
            staff.setDepartment(department);
            staffDAO.save(staff);
            assertNotNull(staff.getId());
            i++;
        }
    }
}