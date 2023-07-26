//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ExecuteTest {
//
////    @BeforeAll
////    public void haha() {
////
////    }
//    boolean compareResults(ArrayList<ScoreHolder> result1 , ArrayList<ScoreHolder> result2 )
//    {
//        for (ScoreHolder doc: result1) {
//            boolean flag = false;
//            Set<ScoreHolder> ha = new HashSet<>(result2);
//            if (ScoreHolder.contains(ha, doc).getClass() != NullScoreHolder.class) {
//                flag = true;
//            }
//            if (!flag)
//                return false;
//        }
//        return true;
//    }
//    boolean compareResults_WithOrderInMind(ArrayList<ScoreHolder> result1 , ArrayList<ScoreHolder> result2 )
//    {
//        if (result1.size()!=result2.size()) return false;
//        for (int i = 0; i < result1.size(); i++) {
//            if (!result1.get(i).equals(result2.get(i)))
//                return false;
//        }
//        return true;
//    }
//    @Test
//    public void run_OneEssential() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
//        expectedResult.add(new ScoreHolder("Clean Agile.txt",0 ,0 ,0));
//        expectedResult.add(new ScoreHolder("Clean Architecture, A Craftsman's Guide to Software Structure and Design.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("PeopleWare.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("Modern Java In Action.txt",0 ,0,0));
//        var actualResult =new ArrayList<>(execute.run("kiarash")) ;
////        assertTrue();
//        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
//    }
//    @Test
//    public void run_OneOptional() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
//        expectedResult.add(new ScoreHolder("Clean Agile.txt",0 ,0 ,0));
//        expectedResult.add(new ScoreHolder("Clean Architecture, A Craftsman's Guide to Software Structure and Design.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("PeopleWare.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("Modern Java In Action.txt",0 ,0,0));
//        var actualResult =new ArrayList<>(execute.run("+kiarash")) ;
////        assertTrue();
//        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
//    }
//    @Test
//    public void run_OneNegetive() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
//        expectedResult.add(new ScoreHolder("SoftSkills.txt",0 ,0 ,0));
//        expectedResult.add(new ScoreHolder("The Clean Coder.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("Crusual Conversations.txt",0 ,0 , 0));
//        var actualResult =new ArrayList<>(execute.run("-data")) ;
////        assertTrue();
//        assertEquals(true , compareResults(expectedResult ,actualResult) );
//    }
//    @Test
//    public void run_OneOptionalOneEssential() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
////        expectedResult.add(new Document("Clean Agile.txt",0 ,0 ,0));
////        expectedResult.add(new Document("Clean Architecture, A Craftsman's Guide to Software Structure and Design.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("PeopleWare.txt",0 ,0 , 0));
////        expectedResult.add(new Document("Modern Java In Action.txt",0 ,0,0));
//        var actualResult =new ArrayList<>(execute.run("dog +kiarash")) ;
////        assertTrue();
//        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
//    }
//    @Test
//    public void run_OneOptionalOneNegetive() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
//        expectedResult.add(new ScoreHolder("Clean Agile.txt",0 ,0 ,0));
//        expectedResult.add(new ScoreHolder("Clean Architecture, A Craftsman's Guide to Software Structure and Design.txt",0 ,0 , 0));
////        expectedResult.add(new Document("PeopleWare.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("Modern Java In Action.txt",0 ,0,0));
//        var actualResult =new ArrayList<>(execute.run("-dog +kiarash")) ;
////        assertTrue();
//        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
//    }
//    @Test
//    public void run_OneEssentialOneNegetive() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
//        expectedResult.add(new ScoreHolder("Clean Agile.txt",0 ,0 ,0));
//        expectedResult.add(new ScoreHolder("Clean Architecture, A Craftsman's Guide to Software Structure and Design.txt",0 ,0 , 0));
////        expectedResult.add(new Document("PeopleWare.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("Modern Java In Action.txt",0 ,0,0));
//        var actualResult =new ArrayList<>(execute.run("kiarash -dog")) ;
////        assertTrue();
//        assertEquals(true , compareResults(expectedResult ,actualResult) );
//    }
//    @Test
//    public void run_OneOfEach() throws IOException {
//        Execute execute;
//        FileScanner fileReader = new TxtFileReader("./books/");
//        Chainsaw chainsaw = new Chainsaw(2,10);
//        ReadPrinciple readPrinciple = ReadPrinciple.builder().useNGram(false).splitMarks("\\n\\r\\s-")
//                .normalization(new Stemmer()).chainsaw(chainsaw).build();
//        execute = new Execute(readPrinciple, fileReader);
//        var expectedResult = new ArrayList<ScoreHolder>();
////        expectedResult.add(new Document("Clean Agile.txt",0 ,0 ,0));
//        expectedResult.add(new ScoreHolder("Clean Architecture, A Craftsman's Guide to Software Structure and Design.txt",0 ,0 , 0));
////        expectedResult.add(new Document("PeopleWare.txt",0 ,0 , 0));
//        expectedResult.add(new ScoreHolder("Modern Java In Action.txt",0 ,0,0));
//        var actualResult =new ArrayList<>(execute.run("kiarash +scope -dog")) ;
////        assertTrue();
//        assertEquals(true , compareResults(expectedResult ,actualResult) );
//    }
//
//
//}