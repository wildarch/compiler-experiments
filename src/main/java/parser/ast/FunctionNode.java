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

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(indent());
        s.append("fun ").append(identifier).append(" : ").append(returnTypeIdentifier).append('\n');

        depth++;
        for(QuartzNode n : statements) {
            s.append(n.toString()).append('\n');
        }
        depth--;

        return s.toString();
    }
}
