package net.catten.hrsys.database;

import junit.framework.Assert;
import net.catten.hrsys.data.AttendRecord;
import net.catten.hrsys.data.Department;
import net.catten.hrsys.data.Staff;
import net.catten.hrsys.data.User;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by catten on 16/3/15.
 */
public class HibernateUtilTest {

    @org.junit.Test
    public void testGetSessionFactory() throws Exception {
        HibernateUtil.getSessionFactory();
    }

    @org.junit.Test
    public void testQuery() throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.getTransaction().begin();
        Department department = (Department) session.get(Department.class, 100100);
        Assert.assertEquals(department.getCommit(),"Do not delete cause for test method");
        Assert.assertEquals(department.getId(),100100);
        Assert.assertEquals(department.getName(),"testData");

        AttendRecord attendRecord = (AttendRecord) session.get(AttendRecord.class, 2);
        Assert.assertEquals(attendRecord.getCommit(),"illed_(:з」∠)_");
        Assert.assertEquals(attendRecord.getStaff().getId(),1);
        Assert.assertEquals(attendRecord.getState(),"o");

        Query query = session.createQuery("select s from Staff s where s.id=0");
        Staff staff = (Staff) query.uniqueResult();
        Assert.assertEquals(staff.getName(),"Snipy");
        Assert.assertEquals(staff.getGender(),"male");
        Assert.assertEquals(staff.getDepartment().getId(),0);
        Assert.assertEquals(staff.getCommit(),"??");

        Query query1 = session.createQuery("select s from User s where s.id = 259688");
        User user = (User) query1.uniqueResult();
        Assert.assertEquals(user.getUsername(),"jack");
        Assert.assertEquals(user.getPassword(),"0xFFFFFF");
        Assert.assertEquals(user.getCommit(),"");
        Assert.assertEquals(user.getOwner().getId(),4);
        session.getTransaction().commit();
    }


}