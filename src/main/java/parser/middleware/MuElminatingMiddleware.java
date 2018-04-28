package parser.middleware;

import parser.dto.QuartzNode;

/*
 * Replace least fixed point by greatest fixed point
 */
public class MuElminatingMiddleware extends Middleware
{
    public QuartzNode transform(QuartzNode rootNode)
    {
        rootNode = this.eliminateMu(rootNode);

        return this.transformNext(rootNode);
    }

    private QuartzNode eliminateMu(QuartzNode rootNode)
    {
        if (rootNode.hasLeft()) {
            rootNode.setLeft(this.eliminateMu(rootNode.left())); 
        }
        if (rootNode.hasRight()) {
            rootNode.setRight(this.eliminateMu(rootNode.right())); 
        }

        if ('m' == rootNode.getOperator()) {
            rootNode.setOperator('n');
            QuartzNode newRoot = new QuartzNode('!', rootNode);

            // A mu or nu only has a left node
            if (rootNode.hasLeft()) {
                rootNode.setLeft(new QuartzNode('!', rootNode.left()));
                this.negateVar(rootNode.left(), rootNode.getVal());
            }

            return newRoot;
        } else {
            return rootNode;
        }
    }

    private QuartzNode negateVar(QuartzNode node, char var)
    {
        if (node.hasLeft()) {
            node.setLeft(this.negateVar(node.left(), var)); 
        }
        if (node.hasRight()) {
            node.setRight(this.negateVar(node.right(), var)); 
        }

        if ('v' == node.getOperator() && var == node.getVal()) {
            return new QuartzNode('!', node);
        } else {
            return node;
        }
    }
}
