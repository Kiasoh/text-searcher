import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    public String user;
    public int chat;
    public boolean isAdmin;
    public int lastSeenMessage;
}