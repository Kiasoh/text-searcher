import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name="Chat",
        uniqueConstraints={@UniqueConstraint(columnNames={"ChatID"})})
public class Chat
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ChatID", nullable=false, unique=true)
    @Setter(AccessLevel.NONE)
    private int ChatID;
    private String Title;
    private java.sql.Timestamp CreatedAt;

    @OneToOne
    @JoinColumn(name = "ProfilePhotoID", referencedColumnName = "FileID")
    private File ProfilePhotoID;

    private String Type;

    public static int addChat (Session session , String title, String profilePath, String type) throws SQLException, IOException {
        Chat chat = new Chat();
        chat.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chat.setProfilePhotoID(File.addFile(profilePath));
        chat.setTitle(title);
        chat.setType(type);
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(chat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return chat.getChatID();
    }
    public static void joinChat (Session session, int chatID , String username, boolean isAdmin) {

    }
}