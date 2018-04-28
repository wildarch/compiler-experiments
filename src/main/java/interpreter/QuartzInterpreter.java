package interpreter;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import parser.ast.*;
import parser.parser.QuartzParser;

import java.util.Optional;

public class QuartzInterpreter {
    private CompilationUnitNode rootNode;

    public QuartzInterpreter(String program) {
        QuartzParser parser = Parboiled.createParser(QuartzParser.class);
        ParsingResult<?> result = new ReportingParseRunner(parser.File()).run(program);
        rootNode = (CompilationUnitNode) result.parseTreeRoot.getValue();
    }

    private Optional<FunctionNode> findFunction(String ident) {
        return rootNode.functionNodeStream().filter(f -> f.getIdentifier().equals(ident)).findFirst();
    }

    public void run() {
        CallStack callStack = new CallStack();
        FunctionNode main = findFunction("main").get();
        callStack.enter(main, null);
        while(!callStack.isEmpty()) {
            FunctionNode func = callStack.get().getFunction();
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
                    // TOOD do something with return value
                    callStack.exit();
                    break;
                }
                else {
                    throw new RuntimeException("Unknown statement type: " + statement.getClass().getCanonicalName());
                }
            }
        }
    }
}
