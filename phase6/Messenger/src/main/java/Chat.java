import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Chat
{
    @Id
    public int ChatID;
    public String Title;
    public java.sql.Timestamp CreatedAt;
    public int ProfilePhotoID;
    public String Type;
}