package parser;

import parser.ast.QuartzNode;
import parser.parser.QuartzParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.support.ParsingResult;
import org.parboiled.parserunners.ReportingParseRunner;

public class App {
    public static void main(String[] args) {
        // Debug, verbose
        Logger.init(false, true);

        String algorithm = "emersonlei"; //default algorithm
        String autFilename = "";

        // Read the required data
        Logger.debug("Parsing input files ...");

        String mcf = "";
        QuartzParser parser = Parboiled.createParser(QuartzParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.File()).run(mcf);

        if (result.hasErrors()) {
            System.out.println("Parsing mcf failed: ");
            for (ParseError error : result.parseErrors) {
                System.out.println(error.getErrorMessage());
                System.out.println("Start: " + error.getStartIndex() + " end: " + error.getEndIndex());
            }
            return;
        }

        QuartzNode constraintRoot = (QuartzNode) result.parseTreeRoot.getValue();

        Logger.debug("AST:");
        Logger.debug(constraintRoot);

        /*Middleware middleware = new DisjunctionEliminatingMiddleware();
        middleware.
            linkWith(new DiamondEliminatingMiddleware()).
            linkWith(new FalseEliminatingMiddleware()).
            linkWith(new MuElminatingMiddleware()).
            linkWith(new DoubleNegationEliminatingMiddleware());

        constraintRoot = middleware.transform(constraintRoot);*/


        return;
    }

    public static ParsingResult<?> parseString(String input) {
        QuartzParser parser = Parboiled.createParser(QuartzParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.File()).run(input);

        return result;
    }

    public static String readFile(String filename) throws IOException {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            File file = new File(filename);
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } finally {
            reader.close();
        }

        return stringBuilder.toString();
    }
}
