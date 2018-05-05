package parser.middleware;

import parser.ast.ExpressionNode;
import parser.ast.QuartzNode;

// Removes simple subexpressions.
public class ExpressionSimplifier extends Middleware
{
    @Override
    public QuartzNode transform(QuartzNode rootNode) {
        QuartzNode[] children = this.getChildren(rootNode);

        if (rootNode instanceof ExpressionNode) {
            ExpressionNode node = (ExpressionNode)rootNode;

            if (null == node.getOperator() && node.getLeftHandSide() instanceof ExpressionNode) {
                ExpressionNode childNode = (ExpressionNode)node.getLeftHandSide();

                node.setLeftHandSide(childNode.getLeftHandSide());
                node.setOperator(childNode.getOperator());
                node.setRightHandSide(childNode.getRightHandSide());
            }
        }

        for (QuartzNode child : children) {
            transform(child);
        }

        return rootNode;
    }
}
