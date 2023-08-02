import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="memberID", nullable=false, unique=true)
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

    public static void joinChat (Session session, int chatID , String username, boolean isAdmin) {
        //conditions
//      if (!userExists(username) || isInChat(username, chatID))
//            return;
        Member member = new Member();
        member.user = session.get(User.class , username);
        member.chat = session.get(Chat.class , chatID);
        member.isAdmin = isAdmin;
        member.lastSeenMessage = 0;
        Main.Create(session , member);
    }

}