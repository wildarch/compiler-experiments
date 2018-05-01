package parser.ast;

public class VariableDefinitionNode extends QuartzNode {
    private String typeIdentifier;
    private String identifier;

    public VariableDefinitionNode(String typeIdentifier, String identifier) {
        this.typeIdentifier = typeIdentifier;
        this.identifier = identifier;
    }
}
