import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        final String url =
                "jdbc:postgresql://localhost:5432/Messanger?user=postgres&password=26561343";
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);

        Connection conn = dataSource.getConnection();

// Create a new statement on the connection
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users");

// Execute the query, and store the results in the ResultSet instance
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            // Now that `rs` points to a valid row (rs.next() is true), we can use the `getString`
            // and `getLong` methods to return each column value of the row as a string and long
            // respectively, and print it to the console
            System.out.printf("id:%s f:%s", rs.getString("UserName"),
                    rs.getString("FirstName"));
            System.out.println();
        }
    }
}
