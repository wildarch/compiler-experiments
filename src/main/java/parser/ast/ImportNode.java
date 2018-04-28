package parser.ast;

public class ImportNode extends QuartzNode {
    private String identifier;

    public ImportNode(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "import: "+this.identifier;
    }
}
