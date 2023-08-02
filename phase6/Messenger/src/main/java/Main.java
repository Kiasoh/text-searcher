import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static <T> void Create(Session session , T target) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(target);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    public static <T> void Update(Session session , T oldMan , T newKid) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            oldMan = newKid;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    public static <T> void Delete (Session session , T target) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(target);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }


    public static void main(String[] args) throws Exception {
        Session session = sessionFactory.openSession();
////        File.addFile("C:\\Users\\Lenovo\\Desktop\\default.jpg");
//
//
//        try {
//            User.signup(session,"user1","k","h","09330418759",
//                    "bio","Kimia123","C:\\Users\\Lenovo\\Desktop\\default.jpg");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        try {
//            User.signup(session,"user2","k","h","09330418759",
//                    "bio","Kimia123","C:\\Users\\Lenovo\\Desktop\\default.jpg");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        User.seeALlUsers(session);

//        User.login(session, "user1", "Kimia123");
    }
}
