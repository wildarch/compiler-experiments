package interpreter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.parboiled.support.ParsingResult;
import parser.App;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit test for simple Quartz Interpreter.
 */
public class QuartzInterpreterTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public QuartzInterpreterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(QuartzInterpreterTest.class);
    }

    public void runFile(String fileName) throws IOException, URISyntaxException {
        URI uri = getClass().getResource("/parser/" + fileName).toURI();
        String content = new String(Files.readAllBytes(Paths.get(uri)));
        QuartzInterpreter interpreter = new QuartzInterpreter(content);
        interpreter.run();
    }

    public void testHello() throws IOException, URISyntaxException {
        runFile("hello.q");
    }

    public void testCall() throws IOException, URISyntaxException {
        runFile("call.q");
    }
}
