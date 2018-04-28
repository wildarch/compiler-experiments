package parser.dto;

public class QuartzNode
{
    private static int depth = 0;

    private char operator;
    private String transition;
    private char val;
    private char surroundingBinder; //m or n, or u for unbounded 

    QuartzNode left;
    QuartzNode right;

    public QuartzNode(char operator, String transition, QuartzNode left)
    {
        this.left = left;
        this.right = null;
        this.operator = operator;
        this.transition = transition;
    }

    public QuartzNode(char operator, char val, QuartzNode left)
    {
        this.left = left;
        this.right = null;
        this.operator = operator;
        this.val = val;
    }

    public QuartzNode(char operator, char val)
    {
        this.left = null;
        this.right = null;
        this.operator = operator;
        this.val = val;
    }

    public QuartzNode(char operator, QuartzNode left)
    {
        this.left = left;
        this.right = null;
        this.operator = operator;
    }

    public QuartzNode(char operator, QuartzNode left, QuartzNode right)
    {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public boolean hasLeft()
    {
        return null != this.left;
    }
    public boolean hasRight()
    {
        return null != this.right;
    }
    public QuartzNode left()
    {
        return this.left;
    }
    public QuartzNode right()
    {
        return this.right;
    }
    public void setLeft(QuartzNode left)
    {
        this.left = left;
    }
    public void setRight(QuartzNode right)
    {
        this.right = right;
    }

    public char getOperator()
    {
        return this.operator;
    }

    public void setOperator(char operator)
    {
        this.operator = operator;
    }

    public char getVal()
    {
        return this.val;
    }
    
    public String getTransition() {
        return this.transition;
    }
    
    public void setBinder(char binder)
    {
        this.surroundingBinder = binder;
    }

    public char getBinder()
    {
        return this.surroundingBinder;
    }

    @Override
    public String toString() 
    {
        String self = "Op:"+String.valueOf(operator)+(" Val:"+String.valueOf(val))+(null!=transition?" Transition:"+transition:"");
        depth++;
        String space = new String(new char[2*this.depth]).replace('\0', ' ');
        String left = (null!=left()?"\n"+space+left():"");
        String right = (null!=right()?"\n"+space+right():"");
        depth--;

        return self+left+right; 
    }

}
