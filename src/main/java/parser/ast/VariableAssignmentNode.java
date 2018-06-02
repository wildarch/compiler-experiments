package parser.ast;

// TODO, change this to a TypeDeclaration node.
public class VariableAssignmentNode extends QuartzNode {
    String variableIdentifier;
    ExpressionNode expression;

    public VariableAssignmentNode(String variableIdentifier, ExpressionNode expression) {
        this.variableIdentifier = variableIdentifier;
        this.expression = expression;
    }

    public String getVariableIdentifier() {
        return this.variableIdentifier;
    }

    public ExpressionNode getExpression() {
        return this.expression;
    }
}
