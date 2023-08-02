
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name="File",
        uniqueConstraints={@UniqueConstraint(columnNames={"FileID"})})
public class File {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="FileID", nullable=false, unique=true)
    public int FileID;

    @Column(name="content")
    public byte[] content;

    @Column(name="fileName")
    public String fileName;

    private static byte[] convertToByte(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public static Integer addFile(String path) throws IOException, SQLException {
        if (path == null)
            return null;
        Session session = Main.sessionFactory.openSession();
        Transaction transaction = null;

        File content = new File();
        content.setFileName(path.substring(path.lastIndexOf('\\') + 1));
        content.setContent(convertToByte(path));

        try{
            transaction = session.beginTransaction();
            session.persist(content);
            x(session);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
        return content.getFileID();
    }

    public static void x(Session session){
        List<File> contentList = session.createQuery("FROM File ", File.class).list();

        // Display the retrieved employees
        for (File c : contentList) {
            System.out.println(c.getFileID() + " " + c.getFileName());
        }
    }
}