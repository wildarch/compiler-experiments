package interpreter;

import parser.ast.FunctionCallNode;
import parser.ast.FunctionNode;
import parser.ast.QuartzNode;

import java.util.Stack;

public class CallStack {
    Stack<StackFrame> frames = new Stack<>();

    public CallStack() {

    }

    public void enter(FunctionNode func, FunctionCallNode origin) {
        if(origin != null)
            frames.peek().setOrigin(origin);
        frames.push(new StackFrame(func));
    }

    public StackFrame get() {
        return frames.peek();
    }

    public void exit() {
        frames.pop();
    }

    public boolean isEmpty() {
        return frames.isEmpty();
    }
}
