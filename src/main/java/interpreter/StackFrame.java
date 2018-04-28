package interpreter;

import parser.ast.FunctionCallNode;
import parser.ast.FunctionNode;
import parser.ast.QuartzNode;

public class StackFrame {
    // TODO add list of local variables
    private FunctionNode function;
    private FunctionCallNode origin = null;

    public StackFrame(FunctionNode function) {
        this.function = function;
    }

    public FunctionNode getFunction() {
        return function;
    }

    public void setOrigin(FunctionCallNode origin) {
        this.origin = origin;
    }

    public FunctionCallNode getOrigin() {
        return origin;
    }
}
