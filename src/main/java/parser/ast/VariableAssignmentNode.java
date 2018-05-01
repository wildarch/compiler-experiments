package parser.ast;

public class VariableAssignmentNode extends QuartzNode {
    String variableIdentifier;
    QuartzNode expression;

    public VariableAssignmentNode(String variableIdentifier, QuartzNode expression) {
        this.variableIdentifier = variableIdentifier;
        this.expression = expression;
    }
}
