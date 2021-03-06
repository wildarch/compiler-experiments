package parser.parser;

import parser.ast.*;

import org.parboiled.Rule;
import org.parboiled.BaseParser;
import org.parboiled.support.Var;
import org.parboiled.support.StringVar;
import org.parboiled.annotations.SkipNode;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.BuildParseTree;

import java.util.ArrayList;
import java.util.List;


@BuildParseTree
public class QuartzParser extends BaseParser<QuartzNode>
{
    public Rule File() {
        Var<List<ImportNode>> imports = new Var<>(new ArrayList<>());
        Var<List<FunctionNode>> functions = new Var<>(new ArrayList<>());
        return Sequence(
            Spacing(),
            ZeroOrMore(Import(imports)),
            Spacing(),
            OneOrMore(Function(functions)),
            EOI, // End of Input
            push(new CompilationUnitNode(imports.get(), functions.get()))
        );
    }
    @SkipNode
    Rule Import(Var<List<ImportNode>> imports) {
        StringVar identifier = new StringVar();
        return Sequence(String("import"), Spacing(), Identifier(identifier),
                imports.get().add((new ImportNode(identifier.get()))));
    }

    @SuppressNode
    Rule Identifier(StringVar name) {
        return OneOrMore(Sequence(FirstOf(CharRange('a','z'), CharRange('A', 'Z'), CharRange('0','9'), Ch('_')), name.append(matchedChar())));
    }

    @SuppressNode
    Rule QualifiedIdentifier(StringVar name) {
        return Sequence(Identifier(name), ZeroOrMore(Ch('.'), name.append('.'), Identifier(name)));
    }

    @SuppressNode
    Rule Spacing() {
        return ZeroOrMore(FirstOf(
            // Whitespace
            OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

            // Single line comment
            Sequence(
                "//",
                ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
                FirstOf("\r\n", '\r', '\n', EOI)
            )
        ));
    }

    @SkipNode
    Rule Function(Var<List<FunctionNode>> functions) {
        StringVar identifier = new StringVar();
        StringVar typeIdentifier = new StringVar();
        Var<List<QuartzNode>> statements = new Var<>(new ArrayList<>());
        return Sequence(
            String("fun"), Spacing(), Identifier(identifier), Spacing(), // Function identifier
            Open(), Spacing(), Parameters(), Spacing(), Close(), // Parameters
            Spacing(), Ch(':'), Spacing(), Type(typeIdentifier), Spacing(), // Type
            ScopeStart(), Spacing(), FunctionScope(statements), Spacing(), ScopeEnd(), Spacing(),// Function scope
            functions.get().add(new FunctionNode(identifier.get(), typeIdentifier.get(), statements.get()))
        );
    }

    Rule Parameters() {
        return ZeroOrMore(Sequence(Parameter(), Spacing(), Optional(Ch(','), Spacing())));
    }

    Rule Parameter() {
        StringVar identifier = new StringVar();
        StringVar typeIdentifier = new StringVar();
        return Sequence(Identifier(identifier), Spacing(), Ch(':'), Spacing(), Type(typeIdentifier));
    }

    Rule Arguments(Var<List<QuartzNode>> arguments) {
        return ZeroOrMore(Sequence(Argument(arguments), Spacing(), Optional(Ch(','), Spacing())));
    }

    Rule Argument(Var<List<QuartzNode>> arguments) {
        StringVar identifier = new StringVar();
        return Sequence(
                FirstOf(
                        Sequence(Identifier(identifier), arguments.get().add(new IdentifierNode(identifier.get()))),
                        Sequence(Literal(), arguments.get().add(pop()))
                ),
                Spacing()
        );
    }

    Rule FunctionScope(Var<List<QuartzNode>> statements) {
        return OneOrMore(Statement(statements));
    }

    // TODO, handle newlines properly.
    Rule Statement(Var<List<QuartzNode>> statements) {
        return Sequence(FirstOf(FunctionCall(statements), Return(statements)), Spacing());
    }

    Rule FunctionCall(Var<List<QuartzNode>> statements) {
        StringVar target = new StringVar();
        Var<List<QuartzNode>> arguments = new Var<>(new ArrayList<>());
        return Sequence(QualifiedIdentifier(target), Open(), Arguments(arguments), Close(), Spacing(),
                statements.get().add(new FunctionCallNode(target.get(), arguments.get())));
    }

    Rule Return(Var<List<QuartzNode>> statements) {
        return Sequence(String("return"), Spacing(), Expression(),
                statements.get().add(new ReturnNode(pop())));
    }

    // Todo, add more expression types.
    // Note to self, every expression type should push a value to the stack.
    Rule Expression() {
        return Literal();
    }

    // Todo, add more literal types.
    Rule Literal() {
        return FirstOf(IntegerLiteral(), StringLiteral());
    }

    Rule IntegerLiteral() {
        return Sequence(OneOrMore(CharRange('0', '9')),
                push(new LiteralNode(Integer.parseInt(match()))));
    }

    Rule StringLiteral() {
        return Sequence(
                Ch('"'),
                    ZeroOrMore(Sequence(TestNot(AnyOf("\"")), ANY)),
                    push(new LiteralNode(match())),
                Ch('"')
        );
    }

    @SuppressNode
    Rule Type(StringVar typeIdentifier) {
        return OneOrMore(Sequence(FirstOf(CharRange('a','z'), CharRange('0','9')), typeIdentifier.append(matchedChar())));
    }

    /*
    Rule Predicate() {
        Var<Character> predicate = new Var<Character>();
        return Sequence(
            CharRange('a','z'), predicate.set(matchedChar()),
            push(new QuartzNode('p', predicate.get()))
        );
    }
    */

    @SuppressNode
    Rule ScopeStart() {
        return Ch('{');
    }
    @SuppressNode
    Rule ScopeEnd() {
        return Ch('}');
    }

    @SuppressNode
    Rule Open() {
        return Ch('(');
    }
    @SuppressNode
    Rule Close() {
        return Ch(')');
    }

    @SuppressNode
    Rule And() {
        return String("&&");
    }
    @SuppressNode
    Rule Or() {
        return String("||");
    }
}
