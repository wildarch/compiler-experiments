package parser.ast;

public class VariableDefinitionNode extends QuartzNode {
    private String typeIdentifier;
    private String identifier;

    public VariableDefinitionNode(String typeIdentifier, String identifier) {
        this.typeIdentifier = typeIdentifier;
        this.identifier = identifier;
    }

    public String getTypeIdentifier() {
       return this.typeIdentifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
