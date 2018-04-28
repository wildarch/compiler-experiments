package parser.parser;

import parser.dto.QuartzNode;

import org.parboiled.Rule;
import org.parboiled.BaseParser;
import org.parboiled.support.Var;
import org.parboiled.support.StringVar;
import org.parboiled.annotations.SkipNode;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.BuildParseTree;

/*
 * FORMULA <- TRUE \ FALSE \ VAR \ CONJUNCTION \ DISJUNCTION \ DIAMOND \ BOX \ LEAST_FP \ GREATEST_FP
 *
 * CONJUNCTION <- FORMULA . OR . FORMULA
 * DISJUNCTION <- FORMULA . AND . FORMULA
 * DIAMOND <-  '<' . TRANSITION . '>' . FORMULA
 * BOX <- '[' . TRANSITION . ']' . FORMULA
 * LEAST_FP <- 'mu' . VAR . '.' . FORMULA
 * GREATEST_FP <- 'nu' . VAR . '.' . FORMULA
 *
 * VAR = [A-Z]
 * TRANSITION = [a-z]+
 *
 * TRUE <- 'true'
 * FALSE <- 'false'
 * OR <- '||'
 * AND <- '&&'
 */

@BuildParseTree
public class QuartzParser extends BaseParser<QuartzNode>
{
    public Rule Formula() {
        return Sequence(
            Spacing(),
            Expression(),
            Spacing(),
            EOI // End of Input
        );
    }
    @SkipNode
    Rule Expression() {
        return FirstOf(
            Disjunction(),
            Conjunction(),
            Diamond(),
            Box(),
            LeastFixedPoint(),
            GreatestFixedPoint(),
            True(), 
            False(),
            Predicate(),
            Var(),
            Sequence(Open(), Spacing(), Expression(), Spacing(), Close())
        );
    }

    @SuppressNode
    Rule Spacing() {
        return ZeroOrMore(FirstOf(
            // Whitespace
            OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

            // Comment
            Sequence(
                "%",
                ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
                FirstOf("\r\n", '\r', '\n', EOI)
            )
        ));
    }

    @SkipNode
    Rule Term() {
        return FirstOf(
            True(), 
            False(),
            Diamond(), Box(),
            Sequence(Open(), Spacing(), Expression(), Spacing(), Close()),
            Predicate(),
            Var()
        );
    }

    Rule Conjunction() {
        return Sequence(
            Term(),
            Spacing(),
            And(),
            Spacing(),
            Expression(),
            push(new QuartzNode('&', pop(1), pop()))
        );
    }
    Rule Disjunction() {
        return Sequence(
            Term(),
            Spacing(),
            Or(),
            Spacing(),
            Expression(),
            push(new QuartzNode('|', pop(1), pop()))
        );
    }
    Rule Diamond() {
        StringVar transition = new StringVar();
        return Sequence(
            DiamondOpen(),
            Transition(transition),
            DiamondClose(),
            Term(),
            Spacing(),
            push(new QuartzNode('<', transition.get(), pop()))
        );
    }
    Rule Box() {
        StringVar transition = new StringVar();
        return Sequence(
            BoxOpen(),
            Transition(transition),
            BoxClose(),
            Term(),
            Spacing(),
            push(new QuartzNode('[', transition.get(), pop()))
        );
    }
    Rule GreatestFixedPoint() {
        return Sequence(
            Mu(),
            Spacing(),
            Var(),
            Dot(),
            Spacing(),
            Expression(),
            push(new QuartzNode('m', pop(1).getVal(), pop()))
        );
    }

    Rule LeastFixedPoint() {
        return Sequence(
            Nu(),
            Spacing(),
            Var(),
            Dot(),
            Spacing(),
            Expression(),
            push(new QuartzNode('n', pop(1).getVal(), pop()))
        );
    }

    Rule Transition(StringVar transition) {
        return OneOrMore(Sequence(FirstOf(CharRange('a','z'), CharRange('0','9'), Ch('_') ,Ch('-')), transition.append(matchedChar())));
    }

    Rule Predicate() {
        Var<Character> predicate = new Var<Character>();
        return Sequence(
            CharRange('a','z'), predicate.set(matchedChar()),
            push(new QuartzNode('p', predicate.get()))
        );
    }

    @SuppressNode
    Rule DiamondOpen() {
        return Ch('<');
    }
    @SuppressNode
    Rule DiamondClose() {
        return Ch('>');
    }
    @SuppressNode
    Rule BoxOpen() {
        return Ch('[');
    }
    @SuppressNode
    Rule BoxClose() {
        return Ch(']');
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
    Rule Dot() {
        return Ch('.');
    }

    Rule Mu() {
        return String("mu");
    }
    Rule Nu() {
        return String("nu");
    }
    @SuppressNode
    Rule And() {
        return String("&&");
    }
    @SuppressNode
    Rule Or() {
        return String("||");
    }

    Rule True() {
        return Sequence(
            String("true"),
            push(new QuartzNode('t', 't'))
        );
    }
    Rule False() {
        return Sequence(
            String("false"),
            push(new QuartzNode('f', 'f'))
        );
    }
    Rule Var() {
        Var<Character> var = new Var<Character>();
        return Sequence(
            CharRange('A','Z'), var.set(matchedChar()),
            push(new QuartzNode('v', var.get()))
        );
    }
}
