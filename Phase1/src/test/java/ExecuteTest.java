import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ExecuteTest {
    @Spy
    Execute execute;

    @BeforeEach
    public void setup() throws Exception {
        ArrayList<FileScanner> fileReaders = new ArrayList<>();
        ArrayList<Guard> guards = new ArrayList<>();

        Chainsaw chainsaw = new Chainsaw(3,5);
        Normalization normalization = new Stemmer();
        String splitMarks = "\\r\\n\\s-,.";
        boolean useNGram = false;

        ReadPrinciple readPrinciple = ReadPrinciple.builder()
                .useNGram(useNGram).splitMarks(splitMarks)
                .normalization(normalization).chainsaw(chainsaw)
                .build();

        Guard guard = new GateKeeper(readPrinciple);
        guards.add(guard);

        FileScanner fileReader = new TxtFileReader("./temp_tests/", guard);
        fileReaders.add(fileReader);
        execute = spy(Execute.class);
        when(execute.checkRunnable()).thenReturn(false);
        //use from code
        execute = new Execute(guards, fileReaders);
    }

    boolean compareResults(ArrayList<ScoreHolder> result1 , ArrayList<ScoreHolder> result2 ) {
        for (ScoreHolder doc: result1) {
            Set<ScoreHolder> ha = new HashSet<>(result2);
            if (ScoreHolder.contains(ha, doc).getClass() == NullScoreHolder.class) {
                return false;
            }
        }
        return true;
    }
    boolean compareResults_WithOrderInMind(ArrayList<ScoreHolder> result1 , ArrayList<ScoreHolder> result2 )
    {
        if (result1.size()!=result2.size()) return false;
        for (int i = 0; i < result1.size(); i++)
            if (!result1.get(i).equals(result2.get(i)))
                return false;
        return true;
    }

    @Test
    public void run_OneEssential() throws IOException {

        var expectedResult = new ArrayList<ScoreHolder>();
        expectedResult.add(new ScoreHolder( new Document("file1.txt" , 2)));
        expectedResult.add(new ScoreHolder(new Document("file2.txt" , 2) ,0 , 0));
        var actualResult =new ArrayList<>(execute.run("kimia")) ;
//        assertTrue();
        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
    }

    @Test
    public void run_OneOptional() throws IOException {
        var expectedResult = new ArrayList<ScoreHolder>();
        expectedResult.add(new ScoreHolder( new Document("file1.txt" , 2)));
        expectedResult.add(new ScoreHolder(new Document("file2.txt" , 2) ,0 , 0));
        var actualResult =new ArrayList<>(execute.run("+kimia")) ;
//        assertTrue();
        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
    }

    @Test
    public void run_OneNegetive() throws IOException {
        var expectedResult = new ArrayList<ScoreHolder>();
        expectedResult.add(new ScoreHolder(new Document("file1.txt",0) ,0 ,0));
        var actualResult =new ArrayList<>(execute.run("-boz1boz1")) ;
//        assertTrue();
        assertEquals(true , compareResults(expectedResult ,actualResult) );
    }

    @Test
    public void run_OneOptionalOneEssential() throws IOException {
        var expectedResult = new ArrayList<ScoreHolder>();
        expectedResult.add(new ScoreHolder(new Document("file1.txt" , 0) ,0 , 0));
        var actualResult =new ArrayList<>(execute.run("asd +kimia")) ;
//        assertTrue();
        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
    }

    @Test
    public void run_OneOptionalOneNegetive() throws IOException {
        var expectedResult = new ArrayList<ScoreHolder>();
        expectedResult.add(new ScoreHolder( new Document("file1.txt" , 2)));
        var actualResult =new ArrayList<>(execute.run("-boz1boz1 +boz1")) ;
//        assertTrue();
        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
    }

    @Test
    public void run_OneEssentialOneNegetive() throws IOException {
        var expectedResult = new ArrayList<ScoreHolder>();

        expectedResult.add(new ScoreHolder( new Document("file1.txt" , 2)));
        var actualResult =new ArrayList<>(execute.run("-boz1boz1 boz1")) ;
        assertEquals(true , compareResults(expectedResult ,actualResult) );
    }

    @Test
    public void run_OneOfEach() throws IOException {
        var expectedResult = new ArrayList<ScoreHolder>();
        expectedResult.add(new ScoreHolder( new Document("file1.txt" , 2)));
        var actualResult =new ArrayList<>(execute.run("kimia +boz1 -boz1boz1")) ;
//        assertTrue();
        assertEquals(true , compareResults_WithOrderInMind(expectedResult ,actualResult) );
    }
}