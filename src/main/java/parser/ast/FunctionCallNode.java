package parser.ast;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionCallNode extends QuartzNode {
    String targetIdentifier;
    List<QuartzNode> arguments;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        String argumentsString = String.join(",", arguments.stream().map(Object::toString).collect(Collectors.toList()));

        s.append(indent());
        s.append(targetIdentifier)
                .append("(")
                .append(argumentsString)
                .append(")");

        return s.toString();
    }

    public FunctionCallNode(String targetIdentifier, List<QuartzNode> arguments) {
        this.targetIdentifier = targetIdentifier;
        this.arguments = arguments;
    }

    public String getTargetIdentifier() {
        return targetIdentifier;
    }

    public List<QuartzNode> getArguments() {
        return arguments;
    }
}
