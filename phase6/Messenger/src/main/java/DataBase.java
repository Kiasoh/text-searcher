import org.postgresql.ds.PGSimpleDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DataBase {
    final String url =
            "jdbc:postgresql://localhost:5432/Messanger?user=postgres&password=26561343";
    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    Connection conn;

    public DataBase() throws SQLException {
        dataSource.setUrl(url);
        conn = dataSource.getConnection();
    }

    public void seeAllUsers() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from Users");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()){
            System.out.println("username: " + resultSet.getString("UserName")
            + "\nfirst name: " + resultSet.getString("FirstName")
            + "\nlast name: " + resultSet.getString("LastName")
            + "\nphone number: " + resultSet.getString("PhoneNumber")
            + "\nbio: " + resultSet.getString("Bio") + "\n**********");
        }
    }

    private boolean userExists(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select exists(select UserName from Users where UserName = ?) as exist;");
        stmt.setString(1, username);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        return resultSet.getBoolean("exist");
    }

    public void signup(String userName, String firstName, String lastName, String phoneNumber, String bio, String pass, String path) throws Exception {
        if(userExists(userName))
            throw new Exception("Duplicate username");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?);");
        stmt.setString(1,userName);
        stmt.setString(2,firstName);
        stmt.setString(3,lastName);
        stmt.setString(4,Validations.PhoneNumberValidation(phoneNumber));
        stmt.setString(5,bio);
        stmt.setString(6,Validations.passwordValidation(pass));
        stmt.setInt(7,addFile(path));
        stmt.execute();
    }

    private Integer addFile(String path) throws IOException, SQLException {
        if(path == null)
            return null;
        String fileName = path.substring(path.lastIndexOf('\\')+1);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"File\"(\"content\", fileName) values (?,?) RETURNING FileID as FileID;");
        stmt.setBytes(1,convertToByte(path));
        stmt.setString(2,fileName);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        return resultSet.getInt("FileID");
    }

    private ResultSet logIn(String userName, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select exists(select UserName from Users where UserName = ? and \"password\" = ?) as exist;");
        stmt.setString(1,userName);
        stmt.setString(2,password);
        return stmt.executeQuery();
    }

    public void printLogin(String userName, String password) throws SQLException {
        ResultSet resultSet = logIn(userName, password);
        resultSet.next();
        if(resultSet.getBoolean("exist"))
            System.out.println("Welcome");
        else
            System.out.println("Invalid info");
    }

    public void deleteAccount(String userName, String password) throws Exception {
        if(!userExists(userName))
            throw new Exception("invalid username");
        PreparedStatement stmt = conn.prepareStatement("delete from \"Member\" where \"user\" = ?;");
        stmt.setString(1,userName);
        stmt.execute();

        stmt = conn.prepareStatement("delete from Users where UserName = ? and \"password\" = ?;");
        stmt.setString(1,userName);
        stmt.setString(2,password);
        stmt.execute();
    }

    public void changeBio(String userName, String bio) throws Exception {
        if(!userExists(userName))
            throw new Exception("invalid username");
        PreparedStatement stmt = conn.prepareStatement("Update Users set Bio = ? where UserName = ?;");
        stmt.setString(1,bio);
        stmt.setString(2,userName);
        stmt.execute();
    }

    public void addPVChat(String username1, String username2, String profilePath) throws SQLException, IOException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Chat(Title, ProfilePhotoID, \"Type\", CreatedAt) VALUES (null , ?, 'pv', CURRENT_TIMESTAMP) RETURNING ChatID as ChatID;");
        stmt.setObject(1,addFile(profilePath));
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        int chatID = resultSet.getInt("ChatID");
        joinChat(chatID, username1, true);
        joinChat(chatID, username2, true);
    }

    public void addChat(String title, String profilePath, String type) throws SQLException, IOException {
        if(type.equals("PV"))
            return;
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Chat(Title, ProfilePhotoID, \"Type\", CreatedAt) VALUES (?, ?, ?, CURRENT_TIMESTAMP);");
        stmt.setString(1,title);
        stmt.setObject(2,addFile(profilePath));
        stmt.setString(3,type);
        stmt.execute();
    }

    public void joinChat(int chatID, String username, boolean isAdmin) throws SQLException {
        if(!userExists(username) || isInChat(username, chatID))
            return;
        PreparedStatement stmt = conn.prepareStatement("Insert into \"Member\" Values (?, ?, ?, ?);");
        stmt.setString(1,username);
        stmt.setInt(2,chatID);
        stmt.setBoolean(3,isAdmin);
        stmt.setInt(4,0);
        stmt.execute();
    }

    public void seeAllChats() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from Chat");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()){
            System.out.println("Title: " + resultSet.getString("Title")
                    + "\nType: " + resultSet.getString("Type")
                    + "\ncreated at: " + resultSet.getString("CreatedAt") + "\n**********");
        }
    }
    public void sendMessage (int destination , String userName, String filePath , String message ) throws SQLException, IOException {
        if(!isInChat(userName, destination))
            System.out.println("U ARE NOT IN THIS CHAT");
        PreparedStatement stmt = conn.prepareStatement("Insert into Messages(\"File\", textMessage, Sender, Destination, sendAt) Values(?, ?, ?, ?, CURRENT_TIMESTAMP);");
        stmt.setObject(1,addFile(filePath));
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

    public void deleteMessage (int messageID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("delete from Messages where MessageID = ?;");
        stmt.setInt(1 , messageID);
        stmt.execute();
    }
    public int getNumMessagesFromOneUser (String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT MessageID FROM messages WHERE Sender = ?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, userName);
        ResultSet s = stmt.executeQuery();
        s.last();
        return s.getRow();
    }

    public ResultSet getAllMessagesFromOneUser (String userName) throws SQLException, IOException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM messages m , \"File\" f WHERE (Sender = ? and f.FileID = m.\"File\");");
        stmt.setString(1, userName);
        ResultSet resultSet = stmt.executeQuery();
        File directory = new File("./Contents/");
        directory.mkdir();
        FileOutputStream fileOutputStream;
        while (resultSet.next()){
            byte[] bytes = resultSet.getBytes("content");
            File file = new File("./Contents/" + resultSet.getString("fileName"));
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        }
        return stmt.executeQuery();
    }

    public void printMessages (String username) throws SQLException, IOException {
        ResultSet resultSet = getAllMessagesFromOneUser(username);
        while (resultSet.next()){
            System.out.println("Sender: " + resultSet.getString("Sender") + "\nchatID:" +
                    resultSet.getString("Destination") +
                    "\nsend at: " + resultSet.getString("sendAt") + "\ntext: " +
                    resultSet.getString("textMessage") +"\nand see the content in Content directory"
            + "***********\n\n");
        }
    }
    public ResultSet getChatsOfOneUser (String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT chat FROM \"Member\" WHERE \"user\" = ?;");
        stmt.setString(1, userName);
        return stmt.executeQuery();
    }

    public int numUsersHasRelationshipWithOneUser (String username) throws SQLException {
        Set<String > reuslt = new HashSet<>();
        ResultSet chats = getChatsOfOneUser(username);
        PreparedStatement stmt = conn.prepareStatement("SELECT \"user\" FROM \"Member\" WHERE chat = ?;");
        while (chats.next()){
            stmt.setInt(1,chats.getInt("chat"));
            ResultSet users = stmt.executeQuery();
            while (users.next()){
                String relationship = users.getString("user");
                if(!relationship.equals(username))
                    reuslt.add(relationship);
            }
        }
        return reuslt.size();
    }

    public double getAvgNumberMessagesOfOneUser(String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select count(MessageID) FROM Messages WHERE Sender = ?;");
        stmt.setString(1, userName);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        int numTargets = resultSet.getInt("count");
        stmt =  conn.prepareStatement("select count(MessageID) FROM Messages;");
        resultSet = stmt.executeQuery();
        resultSet.next();
        int numTotal = resultSet.getInt("count");
        return numTargets*1.0 / numTotal;
    }

    public void setLastSeenMessage(String username, int chatID, int messageID) throws SQLException {
        if(!isInChat(username,chatID) || !userExists(username))
            return;
        PreparedStatement stmt = conn.prepareStatement("update \"Member\" set lastSeenMessage = ? where \"user\" = ? and chat = ?");
        stmt.setInt(1, messageID);
        stmt.setString(2, username);
        stmt.setInt(3, chatID);
        stmt.execute();
    }

    public ArrayList<String> getViewersOfOneMessage(int messageID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select Destination from Messages where MessageID = ?");
        stmt.setInt(1, messageID);
        ResultSet resultSet = stmt.executeQuery();
        int chatID = 0;
        if(resultSet.next())//;
             chatID= resultSet.getInt("Destination");
        stmt = conn.prepareStatement("select \"user\" from \"Member\" where chat = ? and lastSeenMessage >= ?");
        stmt.setInt(1, chatID);
        stmt.setInt(2, messageID);
        ResultSet resultSet2 = stmt.executeQuery();
        ArrayList<String > viewers = new ArrayList<>();
        while (resultSet2.next()){
            viewers.add(resultSet2.getString("user"));
        }
        return viewers;
    }

    private byte[] convertToByte(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    private boolean isInChat(String userName, int chatID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select ? in (select \"user\" from \"Member\" where \"Member\".chat = ?) as isInChat");
        stmt.setString(1,userName);
        stmt.setInt(2,chatID);
        ResultSet resultSet = stmt.executeQuery();
        resultSet.next();
        return resultSet.getBoolean("isInChat");
    }
}
