import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Users")
public class Users
{
    @Id
    public String UserName;
    public String FirstName;
    public String LastName;
    public String PhoneNumber;
    public String Bio;
}