
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "File")
public class Content {
    @Id
    public int FileID;
    public byte[] content;
    public String fileName;

    private static byte[] convertToByte(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public static Integer addFile(String path) throws IOException, SQLException {
        if (path == null)
            return null;
        Content content = new Content();
        content.setFileName(path.substring(path.lastIndexOf('\\') + 1));
        content.setContent(convertToByte(path));
        Session session = Main.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.persist(content);
        session.flush();
        session.close();
        return content.getFileID();
    }
}