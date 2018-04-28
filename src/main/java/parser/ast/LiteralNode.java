package parser.ast;

public class LiteralNode extends QuartzNode {
    private int intValue;
    private String stringValue;

    public LiteralNode(int value) {
        this.intValue = value;
    }

    public LiteralNode(String value) {
       this.stringValue = value;
    }
}
