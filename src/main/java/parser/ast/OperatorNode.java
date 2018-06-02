package parser.ast;

public class OperatorNode extends QuartzNode
{
    private String operator;

    public OperatorNode(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
