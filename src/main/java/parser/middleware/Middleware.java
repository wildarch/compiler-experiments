package parser.middleware;

import parser.dto.QuartzNode;

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
}
