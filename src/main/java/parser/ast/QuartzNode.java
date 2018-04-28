package parser.ast;

import java.util.List;

public abstract class QuartzNode
{
    private static int depth = 0;

    @Override
    public String toString() 
    {
        String self = "";
        depth++;
        String space = new String(new char[2*this.depth]).replace('\0', ' ');
        String children= "";
        depth--;

        return self+children;
    }

}
