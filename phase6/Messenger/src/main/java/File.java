import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "File",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"FileID"})})
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FileID", nullable = false, unique = true)
    public int FileID;

    @Column(name = "content")
    public byte[] content;

    @Column(name = "fileName")
    public String fileName;

    private static byte[] convertToByte(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public static File addFile(Session session, String path) throws IOException{
        if (path == null)
            return null;
        File content = new File();
        content.setFileName(path.substring(path.lastIndexOf('\\') + 1));
        content.setContent(convertToByte(path));

        Main.Create(session, content);
        return content;
    }

    public static void printAllFileNames(Session session) {
        List<File> contentList = session.createQuery("FROM File ", File.class).list();
        for (File c : contentList) {
            System.out.println(c.getFileID() + " " + c.getFileName());
        }
    }
}