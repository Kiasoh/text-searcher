import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "Chat",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ChatID"})})
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChatID", nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    private int ChatID;

    private String Title;

    private java.sql.Timestamp CreatedAt;

    @OneToOne
    @JoinColumn(name = "ProfilePhotoID", referencedColumnName = "FileID")
    private File ProfilePhotoID;

    private String Type;

    public static void addChat(Session session, String title, String profilePath, String type) throws SQLException, IOException {
        Chat chat = new Chat();
        chat.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chat.setProfilePhotoID(File.addFile(session, profilePath));
        chat.setTitle(title);
        chat.setType(type);
        Main.Create(session, chat);
    }

    public static void addPVChat(Session session, String username1, String username2, String profilePath) throws SQLException, IOException {
        Chat chat = new Chat();
        chat.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        chat.setProfilePhotoID(File.addFile(session, profilePath));
        chat.setType("pv");
        Main.Create(session, chat);
        Member.joinChat(session, chat.getChatID(), username1, true);
        Member.joinChat(session, chat.getChatID(), username2, true);
    }

    public static void seeAllChats(Session session) {
        List<Chat> chats = session.createQuery("FROM Chat ", Chat.class).list();
        for (Chat chat : chats) {
            printChat(chat);
        }
    }

    private static void printChat(Chat chat) {
        System.out.println("Title: " + chat.getTitle()
                + "\ntype: " + chat.getType()
                + "\ncreated at: " + chat.getCreatedAt() + "\n**********");
    }
}