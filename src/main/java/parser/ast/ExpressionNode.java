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

    public boolean isSimple() {
        return null == this.operator;
    }

    public QuartzNode getLeftHandSide() {
        return this.leftHandSide;
    }

    public void setLeftHandSide(QuartzNode leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    public OperatorNode getOperator() {
        return this.operator;
    }

    public void setOperator(OperatorNode operator) {
        this.operator = operator;
    }

    public QuartzNode getRightHandSide() {
        return this.rightHandSide;
    }

    public void setRightHandSide(QuartzNode rightHandSide) {
        this.rightHandSide = rightHandSide;
    }
}
