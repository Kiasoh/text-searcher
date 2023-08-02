import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name="Chat",
        uniqueConstraints={@UniqueConstraint(columnNames={"ChatID"})})
public class Chat
{
    @Id
    public int ChatID;
    public String Title;
    public java.sql.Timestamp CreatedAt;

    @OneToOne
    @JoinColumn(name = "ProfilePhotoID", referencedColumnName = "FileID")
    public File ProfilePhotoID;

    public String Type;
}