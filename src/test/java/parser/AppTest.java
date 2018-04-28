package parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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

    public void test() throws IOException, URISyntaxException {
        URI uri = getClass().getResource("/parser/hello.q").toURI();
        String content = new String(Files.readAllBytes(Paths.get(uri)));

        ParsingResult<?> result = App.parseString(content);

        assertNotNull(result.parseTreeRoot);

        QuartzNode constraintRoot = (QuartzNode) result.parseTreeRoot.getValue();

        System.out.println(constraintRoot);
    }
}
