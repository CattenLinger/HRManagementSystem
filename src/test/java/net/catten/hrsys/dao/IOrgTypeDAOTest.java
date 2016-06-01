package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.OrgType;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
public class IOrgTypeDAOTest {
    private IOrgTypeDAO orgTypeDAO;

    @Autowired
    public void setOrgTypeDAO(IOrgTypeDAO orgTypeDAO) {
        this.orgTypeDAO = orgTypeDAO;
    }

    @Test
    public void TestInsertOrgTypeDAO() {
        OrgType orgType = new OrgType();
        orgTypeDAO.save(orgType);
        assertNotNull(orgType.getId());
    }

    @Test
    public void TestListAllOrgType() {
        String[] names = new String[]{
                "UNIVERSITY", "BRANCH", "COLLEGE", "DEPARTMENT", "SPECIFICS", "CLASS"
        };

        for (String name : names) {
            OrgType orgType = new OrgType();
            orgType.setName(name);
            orgTypeDAO.save(orgType);
        }

        List<OrgType> orgTypeList = orgTypeDAO.listAll();
        Map<String, OrgType> stringOrgTypeMap = new HashMap<>();
        for (OrgType orgType : orgTypeList) {
            stringOrgTypeMap.put(orgType.getName(), orgType);
        }
        for (String name : names) {
            assertNotNull(stringOrgTypeMap.get(name));
        }
        assertEquals(names.length, orgTypeList.size());

    }
}