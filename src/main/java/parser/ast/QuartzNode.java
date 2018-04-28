package parser.ast;

import java.util.stream.IntStream;

public abstract class QuartzNode {
    static final int INDENT_WIDTH = 4;
    static int depth = 0;

    protected String indent() {
        StringBuilder s = new StringBuilder();
        IntStream.range(0, depth * INDENT_WIDTH).forEach(i -> s.append(' '));
        return s.toString();
    }


}
