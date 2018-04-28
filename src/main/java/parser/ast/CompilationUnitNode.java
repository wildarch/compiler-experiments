package parser.ast;

import java.util.List;

public class CompilationUnitNode extends QuartzNode {

    List<ImportNode> imports;
    List<FunctionNode> functions;

    public CompilationUnitNode(List<ImportNode> imports, List<FunctionNode> functions) {
        this.imports = imports;
        this.functions = functions;
    }

    @Override
    public String toString() {
        depth = 0;
        StringBuilder s = new StringBuilder();
        s.append("Imports:\n");
        for(ImportNode i : imports) {
            s.append(i.toString()).append('\n');
        }

        s.append("Functions:\n");
        for(FunctionNode f : functions) {
            s.append(f.toString()).append('\n');
        }
        return s.toString();
    }
}
