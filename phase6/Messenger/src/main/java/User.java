import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.persister.collection.mutation.RowMutationOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
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

    public static void seeALlUsers(Session session){
        List<User> users = session.createQuery("FROM User ", User.class).list();
        for (User user : users) {
            System.out.println("username: " + user.getUserName()
                    + "\nfirst name: " + user.getFirstName()
                    + "\nlast name: " +user.getLastName()
                    + "\nphone number: " + user.getPhoneNumber()
                    + "\nbio: " + user.getBio() + "\n**********");
        }
    }
    public static boolean userExists(Session session, String username) throws SQLException {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        Predicate condition = criteriaBuilder.equal(root.get("UserName"), username);
        criteriaQuery.where(condition);
        return session.createQuery(criteriaQuery).getSingleResult() > 0;
    }

    public static void signup(Session session, String userName, String firstName, String lastName, String phoneNumber, String bio, String pass, String path) throws Exception {
        if(userExists(session, userName))
            throw new Exception("Duplicate username");
        User user = new User(userName, firstName, lastName, phoneNumber, bio, pass, File.addFile(path));
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    public static void changeBio(Session session, String userName, String bio) throws Exception {
        if(!userExists(session, userName))
            throw new Exception("No user found!");
        User user =session.get(User.class , userName);
        user.Bio = bio;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}