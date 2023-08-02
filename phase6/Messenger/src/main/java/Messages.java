import jakarta.persistence.*;

@Entity
@Table(name="Messages",
        uniqueConstraints={@UniqueConstraint(columnNames={"MessageID"})})
public class Messages
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="MessageID", nullable=false, unique=true)
    private int MessageID;
    @OneToOne
    @JoinColumn(name = "File", referencedColumnName = "FileID")
    private File File;
    private String textMessage;

    @ManyToOne
    @JoinColumn(name = "Sender", referencedColumnName = "UserName")
    private User Sender;

    @ManyToOne
    @JoinColumn(name = "Destination", referencedColumnName = "ChatID")
    private Chat Destination;

    private java.sql.Timestamp sendAt;
}