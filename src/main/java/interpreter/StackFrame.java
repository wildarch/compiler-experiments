package interpreter;

import parser.ast.FunctionNode;
import parser.ast.FunctionCallNode;
import quartzBase.types.Integer;
import quartzBase.types.QuartzType;

import java.util.HashMap;

public class StackFrame {
    // TODO add list of local variables
    private FunctionNode function;
    private FunctionCallNode origin = null;
    private HashMap<String, Variable> variables;

    public StackFrame(FunctionNode function) {
        this.function = function;
        this.variables = new HashMap<>();
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

    // TODO, allow defining more general variables.
    public void defineVariable(String identifier, String typeIdentifier) {
        switch (typeIdentifier) {
            case "i64":
                this.variables.put(identifier, new Variable(identifier, new Integer(true, 64)));
                break;
        }
    }

    // TODO, allow defining setting other types.
    public void setVariable(String identifier, QuartzType value) throws Exception {
        Variable var = this.variables.get(identifier);

        if (var.get().getClass() != value.getClass()) {
            throw new Exception("Invalid assignment of type "+value.getClass()+" to a variable of type "+var.get().getClass());
        }

        var.set(value);
    }

    public Variable getVariable(String identifier) {
        return this.variables.get(identifier);
    }
}
