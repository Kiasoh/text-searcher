import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int lastSeenMessage;
}