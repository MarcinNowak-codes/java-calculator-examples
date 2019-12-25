package co.uk.cogitolearning.cogpar;


import co.uk.cogitolearning.cogpar.tree.*;

import java.util.Stack;

import static co.uk.cogitolearning.cogpar.tree.FunctionNode.*;

public class CalculateVisitor implements ExpressionNodeVisitor<Void> {
    private Stack<Double> stack;

    public CalculateVisitor(Stack<Double> stack) {

        this.stack = stack;
    }

    static public double functionGetValue(int function, double argument) {
        switch (function) {
            case SIN:
                return Math.sin(argument);
            case COS:
                return Math.cos(argument);
            case TAN:
                return Math.tan(argument);
            case ASIN:
                return Math.asin(argument);
            case ACOS:
                return Math.acos(argument);
            case ATAN:
                return Math.atan(argument);
            case SQRT:
                return Math.sqrt(argument);
            case EXP:
                return Math.exp(argument);
            case LN:
                return Math.log(argument);
            case LOG:
                return Math.log(argument) * 0.43429448190325182765;
            case LOG2:
                return Math.log(argument) * 1.442695040888963407360;
        }

        throw new EvaluationException("Invalid function id " + function + "!");
    }

    public Void visit(VariableNode node) {
        stack.push(node.getValue());
        return null;
    }

    public Void visit(ExponentiationNode node) {
        double base = stack.pop();
        double exponent = stack.pop();
        stack.push(Math.pow(base, exponent));
        return null;
    }

    public Void visit(FunctionNode node) {
        double operand1 = stack.pop();
        stack.push(functionGetValue(node.getFunction(), operand1));
        return null;
    }

    public Void visit(AdditionNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();

        stack.push(operand1 + operand2);
        return null;
    }

    public Void visit(ConstantNode node) {
        stack.push(node.value);
        return null;
    }

    public Void visit(DivNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();
        stack.push(operand1 / operand2);
        return null;
    }

    @Override
    public Void visit(SubtractionNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();

        stack.push(operand1 - operand2);
        return null;
    }

    @Override
    public Void visit(AdditionNodeNew node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();

        stack.push(operand1 + operand2);
        return null;
    }

    public Void visit(MultiplicationNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();
        stack.push(operand1 * operand2);
        return null;
    }
}
