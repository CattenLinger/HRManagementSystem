package net.catten.hrsys.dao;

import net.catten.hrsys.data.orgnization.OrgRule;
import net.catten.hrsys.data.orgnization.OrgType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
public class IOrgRuleDAOTest {
    private IOrgRuleDAO ruleDAO;
    private IOrgTypeDAO typeDAO;

    @Autowired
    public void setRuleDAO(IOrgRuleDAO ruleDAO){
        this.ruleDAO = ruleDAO;
    }

    @Autowired
    public void setTypeDAO(IOrgTypeDAO typeDAO) {
        this.typeDAO = typeDAO;
    }

    @Test
    public void TestInsertRuleDAO(){
        OrgRule rule = new OrgRule();
        ruleDAO.save(rule);
        assertNotNull(rule.getId());
    }

    @Test
    public void TestGetAllOrgRule(){
        String[] names = new String[]{
                "UNIVERSITY", "BRANCH", "COLLEGE", "DEPARTMENT", "SPECIFICS", "CLASS"
        };

        List<OrgRule> orgRuleList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            OrgType orgType = new OrgType();
            orgType.setName(names[i]);
            typeDAO.save(orgType);

            for(int j = i + 1; j < names.length; j++){
                OrgType orgType2 = new OrgType();
                orgType2.setName(names[j]);
                typeDAO.save(orgType2);

                OrgRule orgRule = new OrgRule();
                orgRule.setParentType(orgType);
                orgRule.setChildType(orgType2);
                orgRule.setMaxChildrenCount(20);
                orgRuleList.add(orgRule);
            }
        }

        for(OrgRule rule : orgRuleList) {
            ruleDAO.save(rule);
        }

        List<OrgRule> newOrgRuleList = ruleDAO.listAll();
        assertFalse(newOrgRuleList.size() < 1);
    }
}