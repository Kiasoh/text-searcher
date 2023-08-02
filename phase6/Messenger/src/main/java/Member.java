import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="Member",
        uniqueConstraints={@UniqueConstraint(columnNames={"memberID"})})
public class Member {
    @Id
    private int memberID;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "UserName")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat", referencedColumnName = "ChatID")
    private Chat chat;

    private boolean isAdmin;
    private int lastSeenMessage;
}