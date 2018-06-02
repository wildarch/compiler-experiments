package parser.ast;

public class RangeNode extends QuartzNode {
    private ExpressionNode start;
    private ExpressionNode end;

    public RangeNode(ExpressionNode start, ExpressionNode end) {
        this.start = start;
        this.end = end;
    }
}
