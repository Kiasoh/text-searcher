import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();



    public static void main(String[] args) throws Exception {
        Session session = sessionFactory.openSession();
//        File.addFile("C:\\Users\\Lenovo\\Desktop\\default.jpg");


        try {
            User.signup(session,"user1","k","h","09330418759",
                    "bio","Kimia123","C:\\Users\\Lenovo\\Desktop\\default.jpg");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        try {
            User.signup(session,"user2","k","h","09330418759",
                    "bio","Kimia123","C:\\Users\\Lenovo\\Desktop\\default.jpg");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        User.seeALlUsers(session);
    }
}
