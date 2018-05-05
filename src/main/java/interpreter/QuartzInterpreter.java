package interpreter;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import parser.ast.*;
import parser.middleware.ExpressionSimplifier;
import parser.middleware.Middleware;
import parser.parser.QuartzParser;
import quartzBase.types.Integer;
import quartzBase.types.QuartzType;

import java.util.Optional;
import java.util.stream.Stream;

public class QuartzInterpreter {
    private CompilationUnitNode rootNode;

    public QuartzInterpreter(String program) {
        QuartzParser parser = Parboiled.createParser(QuartzParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.File()).run(program);

        CompilationUnitNode rootNode = (CompilationUnitNode) result.parseTreeRoot.getValue();

        /*
        Middleware transformer = new ExpressionSimplifier();
        this.rootNode = (CompilationUnitNode)transformer.transform(rootNode);
        */
        this.rootNode = rootNode;
    }

    private Optional<FunctionNode> findFunction(String ident) {
        Stream<FunctionNode> correctFunctions = rootNode.functionNodeStream().filter(f -> f.getIdentifier().equals(ident));

        return correctFunctions.findFirst();
    }

    private QuartzType evaluateExpression(StackFrame frame, ExpressionNode expression, QuartzType type) throws Exception {

        if (expression.isSimple()) {
            if (expression.getLeftHandSide() instanceof LiteralNode) {
                Integer lhs = new Integer(true, 64);
                lhs.setValue((int)((LiteralNode)expression.getLeftHandSide()).getValue());
                return lhs;
            }
            if (expression.getLeftHandSide() instanceof IdentifierNode) {
                return frame.getVariable(((IdentifierNode)expression.getLeftHandSide()).getIdentifier()).get();

            }
        } else {
            switch(expression.getOperator().getOperator()) {
                case "+":
                    QuartzType lhs;
                    if (expression.getLeftHandSide() instanceof ExpressionNode) {
                        lhs = this.evaluateExpression(frame, (ExpressionNode)expression.getLeftHandSide(), type);
                    } else {
                        lhs = frame.getVariable(((IdentifierNode)expression.getLeftHandSide()).getIdentifier()).get();
                    }

                    QuartzType rhs;
                    if (expression.getRightHandSide() instanceof ExpressionNode) {
                        rhs = this.evaluateExpression(frame, (ExpressionNode)expression.getRightHandSide(), type);
                    } else {
                        rhs = frame.getVariable(((IdentifierNode)expression.getRightHandSide()).getIdentifier()).get();
                    }

                    return lhs.apply("+", rhs);
                default:
                    throw new Exception("Unsupported operator: "+expression.getOperator().getOperator());
            }
        }

        throw new Exception("Could not evauluate expression.");
    }

    // TODO, split this function in subfunctions.
    public void run() throws Exception {
        CallStack callStack = new CallStack();
        FunctionNode main = findFunction("main").get();
        callStack.enter(main, null);
        while(!callStack.isEmpty()) {
            StackFrame currentFrame = callStack.get();
            FunctionNode func = currentFrame.getFunction();
            for(QuartzNode statement : func.getStatements()) {
                // Restore instruction pointer until statement after function return
                if(callStack.get().getOrigin() != null) {
                    if(statement == callStack.get().getOrigin())
                        callStack.get().setOrigin(null);
                    continue;
                }
                if(statement instanceof FunctionCallNode) {
                    FunctionCallNode call = (FunctionCallNode) statement;
                    String targetIdent = call.getTargetIdentifier();
                    Optional<FunctionNode> target = findFunction(targetIdent);
                    if(target.isPresent()) {
                        callStack.enter(target.get(), call);
                        break;
                    }
                    else {
                        // TODO this is awful, remove it
                        if(targetIdent.equals("io.println")) {
                            System.out.println(call.getArguments().get(0));
                        }
                    }
                }
                else if(statement instanceof ReturnNode) {
                    ReturnNode returnNode = (ReturnNode) statement;
                    // TODO do something with return value
                    callStack.exit();
                    break;
                }
                else if (statement instanceof VariableDefinitionNode) {
                    VariableDefinitionNode node = (VariableDefinitionNode) statement;
                    currentFrame.defineVariable(node.getIdentifier(), node.getTypeIdentifier());
                }
                else if (statement instanceof VariableAssignmentNode) {
                    VariableAssignmentNode node = (VariableAssignmentNode) statement;

                    Variable var = currentFrame.getVariable(node.getVariableIdentifier());
                    // TODO, evaluate the expression.
                    QuartzType value = this.evaluateExpression(currentFrame, node.getExpression(), var.get());

                    currentFrame.setVariable(node.getVariableIdentifier(), value);
                }
                else {
                    throw new RuntimeException("Unknown statement type: " + statement.getClass().getCanonicalName());
                }
            }
        }
    }
}
