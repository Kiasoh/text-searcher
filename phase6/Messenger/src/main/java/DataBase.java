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

    public void signup(String userName, String firstName, String lastName, String phoneNumber, String bio, String pass) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?);");
        stmt.setString(1,userName);
        stmt.setString(2,firstName);
        stmt.setString(3,lastName);
        stmt.setString(4,phoneNumber);
        stmt.setString(5,bio);
        stmt.setString(6,pass);
        stmt.execute();
    }

    public ResultSet logIn(String userName, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select login(?, ?);");
        stmt.setString(1,userName);
        stmt.setString(2,password);
        return stmt.executeQuery();
    }

    public void deleteAccount(String userName, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select DeleteUser(?, ?);");
        stmt.setString(1,userName);
        stmt.setString(2,password);
        stmt.executeQuery();
    }

    public void logOut(String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Delete From LoginLogs Where UserName = ?;");
        stmt.setString(1,userName);
        stmt.execute();
    }

    public void changeBio(String userName, String bio) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Update Users set Bio = ? where UserName = ?;");
        stmt.setString(1,bio);
        stmt.setString(2,userName);
        stmt.execute();
    }
    public void sendMessage (String destination , String userName, String filePath , String message ) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("Insert into Messages(\"content\", textMessage, Sender, Destination, sendAt) Values(?, ?, ?, ?, CURRENT_TIMESTAMP);");
        stmt.setString(1,destination);
        stmt.setString(2,message);
        stmt.setString(3,userName);
        stmt.setString(4,destination);
        stmt.execute();
    }
}
