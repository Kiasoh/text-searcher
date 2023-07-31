import org.postgresql.ds.PGSimpleDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
    final String url =
            "jdbc:postgresql://localhost:5432/Messanger?user=postgres&password=26561343";
    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    Connection conn;

    public DataBase() throws SQLException {
        dataSource.setUrl(url);
        conn = dataSource.getConnection();
    }

    public ResultSet signup(String userName, String firstName, String lastName, String phoneNumber, String bio, String pass) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?);");
        stmt.setString(1,userName);
        stmt.setString(2,firstName);
        stmt.setString(3,lastName);
        stmt.setString(4,phoneNumber);
        stmt.setString(5,bio);
        stmt.setString(6,pass);
        return stmt.executeQuery();
    }

    public ResultSet logIn(String userName, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select login(?, ?);");
        stmt.setString(1,userName);
        stmt.setString(2,password);
        return stmt.executeQuery();
    }
}
