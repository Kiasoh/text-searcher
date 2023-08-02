import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {
    @Id
    private int id;
    public String user;
    public int chat;
    public boolean isAdmin;
    public int lastSeenMessage;
}