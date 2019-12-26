package co.uk.cogitolearning.cogpar;


import co.uk.cogitolearning.cogpar.tree.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static co.uk.cogitolearning.cogpar.tree.FunctionNode.*;

public class CalculationVisitor implements ExpressionNodeVisitor<Void> {
    private final Stack<Double> stack = new Stack<>();

    private final Map<String, Double> variable = new HashMap<>();

    private static double functionGetValue(int function, double argument) {
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
            default:
                throw new EvaluationException("Invalid function id " + function + "!");

        }
    }

    public Void visit(VariableNode node) {
        if (!variable.containsKey(node.name))
            throw new EvaluationException("Variable '" + node.name + "' was not initialized.");

        stack.push(variable.get(node.name));
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
        stack.push(functionGetValue(node.function, operand1));
        return null;
    }

    public Void visit(ConstantNode node) {
        stack.push(node.value);
        return null;
    }

    @Override
    public Void visit(AdditionNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();

        stack.push(operand1 + operand2);
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
    public Void visit(MultiplicationNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();
        stack.push(operand1 * operand2);
        return null;
    }

    @Override
    public Void visit(DivNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();
        stack.push(operand1 / operand2);
        return null;
    }


    public void addVariable(String name, Double value) {
        this.variable.put(name, value);
    }

    public double getValue() {
        return stack.pop();
    }
}
