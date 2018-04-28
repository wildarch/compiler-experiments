package parser.ast;

import java.util.List;

public class FunctionCallNode extends QuartzNode {
    String targetIdentifier;
    List<QuartzNode> arguments;

    public FunctionCallNode(String targetIdentifier, List<QuartzNode> arguments) {
        this.targetIdentifier = targetIdentifier;
        this.arguments = arguments;
    }
}
