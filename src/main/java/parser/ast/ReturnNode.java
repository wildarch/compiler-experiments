package parser.ast;

public class ReturnNode extends QuartzNode {
    QuartzNode returnValue;

    public ReturnNode(QuartzNode returnValue) {
        this.returnValue = returnValue;
    }
}
