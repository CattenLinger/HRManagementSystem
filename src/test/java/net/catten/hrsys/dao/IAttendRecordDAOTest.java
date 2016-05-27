package net.catten.hrsys.dao;

import net.catten.hrsys.dao.IAttendRecordDAO;
import net.catten.hrsys.data.AttendRecord;
import net.catten.hrsys.data.Staff;
import net.catten.hrsys.util.AttendStatus;
import net.catten.hrsys.util.Gender;
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
 * Created by catten on 16/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        //如果测试文件有依赖，则按照这个顺序加载，如果直接使用ContextConfiguration则只加载一个
        @ContextConfiguration("classpath:net/catten/hrsys/applicationContext.xml")
})
//测试里事务默认回滚，但是建议加上
@Transactional
public class IAttendRecordDAOTest {
    private IAttendRecordDAO attendRecordDAO;

    @Autowired
    public void setAttendRecordDAO(IAttendRecordDAO attendRecordDAO){
        this.attendRecordDAO = attendRecordDAO;
    }

    @Test
    public void InsertAttendRecord(){
        int i = 0;
        Date date = new Date();
        for(AttendStatus attendStatus : AttendStatus.values()){
            AttendRecord attendRecord = new AttendRecord();
            attendRecord.setTimePoint(date);
            attendRecord.setCommit("Test attend record "+i);
            attendRecord.setState(attendStatus);
            attendRecordDAO.save(attendRecord);
            i++;
            assertNotNull(attendRecord.getId());
        }
    }

    @Test
    public void InsertAttendRecordWithStaff(){
        int i = 0;

        Date date = new Date();

        Staff staff = new Staff();
        staff.setDepartment(null);
        staff.setName("staff a");
        staff.setGender(Gender.female);
        staff.setCommit("Staff inside attend record");

        for(AttendStatus attendStatus : AttendStatus.values()){
            AttendRecord attendRecord = new AttendRecord();
            attendRecord.setTimePoint(date);
            attendRecord.setCommit("Test attend record "+i);
            attendRecord.setState(attendStatus);
            attendRecord.setStaff(staff);
            attendRecordDAO.save(attendRecord);
            i++;
            assertNotNull(attendRecord.getId());
        }
    }
}