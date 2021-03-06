package parser.ast;

public class IdentifierNode extends QuartzNode {

    private String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
