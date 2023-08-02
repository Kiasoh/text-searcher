import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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

    public static void getAllMessagesFromOneUser(Session session, String userName) throws IOException {
        Query query = session.createQuery("select m FROM Messages m left outer join File f on m.Sender.UserName = :username " +
                "and f.FileID = m.attachedFile.FileID", Messages.class);
        query.setParameter("username", userName);
        List<Messages> result = query.getResultList();
        java.io.File directory = new java.io.File("./Contents/");
        directory.mkdir();
        FileOutputStream fileOutputStream;
        for (Messages messages : result){
            printMessage(messages);
            File content = messages.getAttachedFile();
            if(content == null)
                continue;
            byte[] bytes = content.content;
            java.io.File file = new java.io.File("./Contents/" + content.getFileName());
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        }
    }

    private static void printMessage(Messages messages){
        System.out.println("Sender: " + messages.getSender().getUserName() +
                "\nchatID:" + messages.getDestination().getChatID() +
                    "\nsend at: " + messages.getSendAt() + "\ntext: " +
                    messages.getTextMessage());
        if(messages.getAttachedFile() != null){
            System.out.println("file name : " + messages.getAttachedFile().getFileName() + "\nand see the content in Content directory");
        }
        System.out.println("***********\n\n");
    }

    public static double getAvgNumberMessagesOfOneUser(Session session, String userName) throws SQLException {
        Query query = session.createQuery("select count(*) FROM Messages where Sender.UserName = :username", Messages.class);
        query.setParameter("username",userName);
        Long numTargets =  (Long) query.getSingleResult();
        query = session.createQuery("select count(*) FROM Messages", Messages.class);
        Long numTotal = (Long) query.getSingleResult();
        return numTargets * 1.0 / numTotal;
    }
}