package parser.ast;

public class IdentifierNode extends QuartzNode {

    private String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return String.format("Identifier[%s]", identifier);
    }
}
