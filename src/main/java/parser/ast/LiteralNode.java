package parser.ast;

public class LiteralNode<T> extends QuartzNode {
    private T value;

    public LiteralNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
