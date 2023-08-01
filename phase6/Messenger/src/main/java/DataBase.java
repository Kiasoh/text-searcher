import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DataBase {
    final String url =
            "jdbc:postgresql://localhost:5432/MyDatabase?user=postgres&password=ADMIN";
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

    public void sendMessage (int destination , String userName, String filePath , String message ) throws SQLException, IOException {
        if(!isInChat(userName, destination).getBoolean("isInChat"))
            System.out.println("U ARE NOT IN THIS CHAT");
        PreparedStatement stmt = conn.prepareStatement("Insert into Messages(\"content\", textMessage, Sender, Destination, sendAt) Values(?, ?, ?, ?, CURRENT_TIMESTAMP);");
        stmt.setBytes(1,convertToByte(filePath));
        stmt.setString(2,message);
        stmt.setString(3,userName);
        stmt.setInt(4,destination);
        stmt.execute();
    }
    public void editMessage (int messageID , String newText) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Update Messages set textMessage = ? where MessageID = ?;");
        stmt.setString(1 , newText);
        stmt.setInt(2 , messageID);
        stmt.execute();
    }
    public int getAllMessagesFromOneUser (String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT MessageID FROM messages WHERE Sender = ?;" , ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_UPDATABLE);
        stmt.setString( 1 , userName);
        ResultSet s = stmt.executeQuery();
        s.last();
        return s.getRow();

    private byte[] convertToByte(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    private ResultSet isInChat(String userName, int chatID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("? in (select \"user\" from \"Member\" where \"Member\".chat = ?) as isInChat");
        stmt.setString(1,userName);
        stmt.setInt(2,chatID);
        return stmt.executeQuery();
    }
}
