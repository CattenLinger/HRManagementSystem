package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.Organization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class IOrganizationDAOTest {
    private IOrganizationDAO organizationDAO;

    @Autowired
    public void setDepartmentDAO(IOrganizationDAO departmentDAO){
        this.organizationDAO = departmentDAO;
    }

    @Test
    public void InsertDepartmentTest(){
        Organization organization = new Organization();
        organizationDAO.save(organization);
        assertNotNull(organization.getId());
    }

    @Test
    public void PagingOrganizationTest(){
        int dataAmount = 100;
        for(int i = 0; i < dataAmount; i++){
            Organization org = new Organization();
            org.setName("Org " + dataAmount);
            organizationDAO.save(org);
        }

        Pageable pageable = new PageRequest(1,5);
        Page<Organization> page = organizationDAO.findAll(pageable);
        assertEquals(dataAmount,page.getTotalElements());
        assertEquals((dataAmount / 5),page.getTotalPages());
    }

}