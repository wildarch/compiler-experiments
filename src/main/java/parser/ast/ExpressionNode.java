package parser.ast;

public class ExpressionNode extends QuartzNode
{
    private QuartzNode leftHandSide;
    private OperatorNode operator;
    private QuartzNode rightHandSide;

    public ExpressionNode(QuartzNode leftHandSide, OperatorNode operator, QuartzNode rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.operator = operator;
        this.rightHandSide = rightHandSide;
    }

    public ExpressionNode(QuartzNode leftHandSide) {
        this.leftHandSide = leftHandSide;
        this.operator = null;
        this.rightHandSide = null;
    }
}
