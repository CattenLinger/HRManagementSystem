package net.catten.hrsys.dao;

import net.catten.hrsys.data.person.StaffJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by catten on 16/6/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        //如果测试文件有依赖，则按照这个顺序加载，如果直接使用ContextConfiguration则只加载一个
        @ContextConfiguration("classpath:net/catten/hrsys/applicationContext.xml")
})
//测试里事务默认回滚，但是建议加上
@Transactional
public class IStaffJobDAOTest {
    private IStaffJobDAO staffJobDAO;

    @Autowired
    public void setStaffJobDAO(IStaffJobDAO staffJobDAO) {
        this.staffJobDAO = staffJobDAO;
    }

    @Test
    public void TestInsertStaffJob(){
        StaffJob staffJob = new StaffJob();
        staffJobDAO.save(staffJob);
        assertNotNull(staffJob.getId());
    }
}