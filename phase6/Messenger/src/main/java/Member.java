import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberID", nullable = false, unique = true)
    private int memberID;

    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "UserName")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat", referencedColumnName = "ChatID")
    private Chat chat;

    private boolean isAdmin;
    @Setter
    private int lastSeenMessage;

    public static void joinChat(Session session, int chatID, String username, boolean isAdmin) {
        //conditions
//      if (!userExists(username) || isInChat(username, chatID))
//            return;
        Member member = new Member();
        member.user = session.get(User.class, username);
        member.chat = session.get(Chat.class, chatID);
        member.isAdmin = isAdmin;
        member.lastSeenMessage = 0;
        Main.Create(session, member);
    }

    public static List<Chat> getChatsOfOneUser(Session session, String userName) {
        Query query = session.createQuery("select m.chat FROM Member m where m.user.UserName =:username", Chat.class);
        query.setParameter("username", userName);
        return query.getResultList();
    }

    public static Set<String> usersHaveRelationshipWithOneUser(Session session, String username){
        Set<String> result = new HashSet<>();
        List<Chat> chats = getChatsOfOneUser(session, username);
        Query query = session.createQuery("select m.user from Member m where m.chat.ChatID =:chatID", User.class);
        for (Chat chat : chats) {
            query.setParameter("chatID", chat.getChatID());
            List<User> users = query.getResultList();
            for (User user : users) {
                String foundUsername = user.getUserName();
                if (!foundUsername.equals(username))
                    result.add(foundUsername);
            }
        }
        return result;
    }

    public static void setLastSeenMessage(Session session, String username, int chatID, int messageID) {
//        if (!isInChat(username, chatID) || !userExists(username))
//            return;
        Query query = session.createQuery("select m from Member m where m.user.UserName =: username and m.chat.ChatID =: chatID", Member.class);
        query.setParameter("username", username);
        query.setParameter("chatID", chatID);
        Member member = (Member) query.getSingleResult();
        member.setLastSeenMessage(messageID);
        Main.Update(session, member);
    }
}