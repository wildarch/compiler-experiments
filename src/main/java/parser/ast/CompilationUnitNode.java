package parser.ast;

import java.util.List;

public class CompilationUnitNode extends QuartzNode {
    List<ImportNode> imports;
    List<FunctionNode> functions;

    public CompilationUnitNode(List<ImportNode> imports, List<FunctionNode> functions) {
        this.imports = imports;
        this.functions = functions;
    }
}
