package parser.ast;

import org.parboiled.support.Var;

import java.util.List;

public class ForNode extends QuartzNode {
    private VariableDefinitionNode iterationVariable;
    private RangeNode range;
    private Var<List<QuartzNode>> innerStatements;

    public ForNode(VariableDefinitionNode iterationVariable, RangeNode range, Var<List<QuartzNode>> innerStatements) {
        this.iterationVariable = iterationVariable;
        this.range = range;
        this.innerStatements = innerStatements;
    }
}
