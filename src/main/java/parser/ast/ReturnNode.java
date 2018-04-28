package parser.ast;

public class ReturnNode extends QuartzNode {
    QuartzNode returnValue;

    public ReturnNode(QuartzNode returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(indent()).append("return ").append(returnValue.toString());
        return s.toString();
    }
}
