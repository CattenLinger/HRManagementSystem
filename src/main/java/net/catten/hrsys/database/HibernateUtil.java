package net.catten.hrsys.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by catten on 16/3/15.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try{
            return new Configuration().configure("net/catten/hibernate.cfg.xml").buildSessionFactory();
        }catch (Throwable throwable){
            System.err.println("Session Factory Create Failed. " + throwable);
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
