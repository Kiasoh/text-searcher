import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();



    public static void main(String[] args) throws Exception {
        Content.addFile("C:\\Users\\Lenovo\\Desktop\\default.jpg");
    }
}
