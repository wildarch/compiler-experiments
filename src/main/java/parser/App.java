package parser;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import parser.parser.QuartzParser;

public class App {
    public static ParsingResult<?> parseString(String input) {
        QuartzParser parser = Parboiled.createParser(QuartzParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.File()).run(input);

        return result;
    }
}
