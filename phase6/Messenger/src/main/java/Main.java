import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DataBase dataBase = new DataBase();
// Create a new statement on the connection

// Execute the query, and store the results in the ResultSet instance
//        ResultSet rs = (new DataBase()).logIn("kimik","13");
//        dataBase.signup("boz","kimia","hosseini","09330418759","boz","123");
            dataBase.changeBio("boz", "new boz");
//        dataBase.deleteAccount("kimik","123");
        //        while (rs.next()) {
//            // Now that `rs` points to a valid row (rs.next() is true), we can use the `getString`
//            // and `getLong` methods to return each column value of the row as a string and long
//            // respectively, and print it to the console
//
//            System.out.println(rs.getBoolean("login"));
//        }
    }
}
