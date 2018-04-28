package parser.ast;

import java.util.List;

public class FunctionNode extends QuartzNode {
    private String identifier;
    private String returnTypeIdentifier;
    private List<QuartzNode> statements;

    public FunctionNode(String identifier, String returnTypeIdentifier, List<QuartzNode> statements) {
        this.identifier = identifier;
        this.returnTypeIdentifier = returnTypeIdentifier;
        this.statements = statements;
    }
}
