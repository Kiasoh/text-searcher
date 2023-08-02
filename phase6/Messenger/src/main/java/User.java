//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.sql.PreparedStatement;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String UserName;
//    private String FirstName;
//    private String LastName;
//    private String PhoneNumber;
//    private String Bio;
//    private String password;
//    private int ProfilePhotoID;

//    public void signup(String userName, String firstName, String lastName, String phoneNumber, String bio, String pass, String path) throws Exception {
//        User user = new User(userName, firstName, lastName, phoneNumber, bio, pass, path);
//
//        if (userExists(userName))
//            throw new Exception("Duplicate username");
//        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?);");
//        stmt.setString(1, userName);
//        stmt.setString(2, firstName);
//        stmt.setString(3, lastName);
//        stmt.setString(4, Validations.PhoneNumberValidation(phoneNumber));
//        stmt.setString(5, bio);
//        stmt.setString(6, Validations.passwordValidation(pass));
//        stmt.setInt(7, addFile(path));
//        stmt.execute();
//    }
//}