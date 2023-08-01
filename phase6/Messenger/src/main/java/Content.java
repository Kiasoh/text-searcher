import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "File")
public class Content {
    @Id
    public int FileID;
    public byte[] content;
    public String fileName;
}