package parser.middleware;

import parser.ast.CompilationUnitNode;
import parser.ast.FunctionNode;
import parser.ast.QuartzNode;

public abstract class Middleware 
{
    private Middleware next;

    public Middleware linkWith(Middleware next)
    {
        this.next = next;
        return next;
    }

    public abstract QuartzNode transform(QuartzNode rootNode);

    protected QuartzNode transformNext(QuartzNode rootNode)
    {
        if (next == null) {
            return rootNode;
        }

        return next.transform(rootNode);
    }

    public QuartzNode[] getChildren(QuartzNode node) {
        if (node instanceof CompilationUnitNode) {
            return (QuartzNode[])((CompilationUnitNode) node).functionNodeStream().toArray();
        }

        if (node instanceof FunctionNode) {
            return (QuartzNode[])((FunctionNode) node).getStatements().toArray();
        }

        return new QuartzNode[0];
    }
}
