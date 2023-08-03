import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    private String UserName;

    private String FirstName;

    private String LastName;

    private String PhoneNumber;

    private String Bio;

    private String password;

    @OneToOne
    @JoinColumn(name = "ProfilePhotoID", referencedColumnName = "FileID")
    private File ProfilePhotoID;

    public static void seeALlUsers(Session session) throws Exception {
        List<User> users = session.createQuery("FROM User ", User.class).list();
        for (User user : users) {
            printUser(user);
        }
    }

    public static boolean userExists(Session session, String username) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        Predicate condition = criteriaBuilder.equal(root.get("UserName"), username);
        criteriaQuery.where(condition);
        return session.createQuery(criteriaQuery).getSingleResult() > 0;
    }

    public static void signup(Session session, String userName, String firstName, String lastName, String phoneNumber, String bio, String pass, String path) throws Exception {
        if (userExists(session, userName))
            throw new Exception("Duplicate username");
        User user = new User(userName, firstName, lastName, phoneNumber, bio, pass, File.addFile(session, path));
        Main.Create(session, user);
    }

    public static void changeBio(Session session, String userName, String bio) throws Exception {
        if (!userExists(session, userName))
            throw new Exception("No user found!");
        User user = session.get(User.class, userName);
        user.Bio = bio;
        Main.Update(session, user);
    }

    public static User login(Session session, String userName, String password) {
        Query query = session.createQuery("FROM User where UserName = :username and password = :pass", User.class);
        query.setParameter("username", userName);
        query.setParameter("pass", password);
        List<User> users = query.getResultList();
        if (users.isEmpty())
            return null;
        return users.get(0);
    }

    public static void printUser(User user) throws Exception {
        if (user == null)
            throw new Exception("Invalid info");
        System.out.println("username: " + user.getUserName()
                + "\nfirst name: " + user.getFirstName()
                + "\nlast name: " + user.getLastName()
                + "\nphone number: " + user.getPhoneNumber()
                + "\nbio: " + user.getBio() + "\n**********");
    }

    public static void deleteUser(Session session, String userName, String password) throws Exception {
        User user = login(session, userName, password);
        if (user == null)
            throw new Exception("Invalid info");
        Main.Delete(session, user);
    }
}