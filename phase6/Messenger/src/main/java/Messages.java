import jakarta.persistence.*;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Messages",
        uniqueConstraints={@UniqueConstraint(columnNames={"MessageID"})})
public class Messages
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="MessageID", nullable=false, unique=true)
    private int MessageID;
    @OneToOne
    @JoinColumn(name = "File", referencedColumnName = "FileID")
    private File attachedFile;
    private String textMessage;

    @ManyToOne
    @JoinColumn(name = "Sender", referencedColumnName = "UserName")
    private User Sender;

    @ManyToOne
    @JoinColumn(name = "Destination", referencedColumnName = "ChatID")
    private Chat Destination;

    private java.sql.Timestamp sendAt;
    public static void sendMessage (Session session ,int destination, String userName, String filePath, String message ) throws SQLException, IOException {
        //condition
//        if (!isInChat(userName, destination))
//            System.out.println("U ARE NOT IN THIS CHAT");
        Messages messages = new Messages();
        messages.Destination = session.get(Chat.class ,destination);
        messages.sendAt = Timestamp.valueOf(LocalDateTime.now());
        messages.textMessage = message;
        messages.Sender = session.get(User.class , userName);
        messages.attachedFile = File.addFile(filePath);
        Main.Create(session , messages);
    }
    public static void editMessage(Session session, int messageID, String newText) {
        Messages messages = session.get(Messages.class , messageID);
        Main.Update(session , messages.textMessage , newText);
    }
    public static void deleteMessage (Session session , int messageID) {
        Main.Delete(session , session.get(Messages.class , messageID));
    }

    public static Long getNumMessagesFromOneUser(Session session, String userName) throws SQLException {
        Query query = session.createQuery("select count(*) FROM Messages where Sender.UserName = :username", Messages.class);
        query.setParameter("username",userName);
        return (Long) query.getSingleResult();
    }

}