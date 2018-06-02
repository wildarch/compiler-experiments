package parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.parboiled.errors.ParseError;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;
import parser.ast.QuartzNode;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    private void runFile(String fileName) throws IOException, URISyntaxException {
        URI uri = getClass().getResource("/parser/" + fileName).toURI();
        String content = new String(Files.readAllBytes(Paths.get(uri)));

        ParsingResult<?> result = App.parseString(content);

        // TODO, implement understandable toString() for the ParseErrors
        assertEquals(
                "The parser returned errors: "+result.parseErrors.stream().map(ParseError::getErrorMessage).reduce("", (acc, e) -> acc+e),
                0, result.parseErrors.size());

        assertNotNull("The parser did not return a result.", result.parseTreeRoot);

        QuartzNode constraintRoot = (QuartzNode) result.parseTreeRoot.getValue();

        System.out.println(constraintRoot);
    }

    public void testHello() throws IOException, URISyntaxException {
        runFile("hello.q");
    }

    public void testCall() throws IOException, URISyntaxException {
        runFile("call.q");
    }

    public void testArithmetic() throws IOException, URISyntaxException {
        runFile("arithmetic.q");
    }
}
