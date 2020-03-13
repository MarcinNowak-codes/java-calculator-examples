package co.uk.cogitolearning.calculator;


import co.uk.cogitolearning.calculator.tree.AdditionNode;
import co.uk.cogitolearning.calculator.tree.ConstantNode;
import co.uk.cogitolearning.calculator.tree.DivNode;
import co.uk.cogitolearning.calculator.tree.ExponentiationNode;
import co.uk.cogitolearning.calculator.tree.ExpressionNodeVisitor;
import co.uk.cogitolearning.calculator.tree.FunctionNode;
import co.uk.cogitolearning.calculator.tree.MultiplicationNode;
import co.uk.cogitolearning.calculator.tree.SubtractionNode;
import co.uk.cogitolearning.calculator.tree.VariableNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static co.uk.cogitolearning.calculator.tree.FunctionNode.ACOS;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.ASIN;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.ATAN;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.COS;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.EXP;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.LN;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.LOG;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.LOG2;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.SIN;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.SQRT;
import static co.uk.cogitolearning.calculator.tree.FunctionNode.TAN;

public final class CalculationVisitor implements ExpressionNodeVisitor<Void> {
    private final Stack<Double> stack = new Stack<>();

    private final Map<String, Double> variable = new HashMap<>();

    @Override
    public Void visit(final VariableNode node) {
        if (!variable.containsKey(node.getName())) {
            throw new EvaluationException("Variable '" + node.getName() + "' was not initialized.");
        }

        stack.push(variable.get(node.getName()));
        return null;
    }

    @Override
    public Void visit(final ExponentiationNode node) {
        double base = stack.pop();
        double exponent = stack.pop();
        stack.push(Math.pow(base, exponent));
        return null;
    }

    @Override
    public Void visit(final FunctionNode node) {
        double operand1 = stack.pop();
        stack.push(functionGetValue(node.getFunction(), operand1));
        return null;
    }

    private static double functionGetValue(final int function, final double argument) {
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
                return Math.log10(argument);
            case LOG2:
                return Math.log10(argument) / Math.log10(2); // Change of base
            default:
                throw new EvaluationException("Invalid function id " + function + "!");

        }
    }

    @Override
    public Void visit(final ConstantNode node) {
        stack.push(node.getValue());
        return null;
    }

    @Override
    public Void visit(final AdditionNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();

        stack.push(operand1 + operand2);
        return null;
    }

    @Override
    public Void visit(final SubtractionNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();

        stack.push(operand1 - operand2);
        return null;
    }

    @Override
    public Void visit(final MultiplicationNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();
        stack.push(operand1 * operand2);
        return null;
    }

    @Override
    public Void visit(final DivNode node) {
        double operand1 = stack.pop();
        double operand2 = stack.pop();
        stack.push(operand1 / operand2);
        return null;
    }

    public void addVariable(final String name, final Double value) {
        this.variable.put(name, value);
    }

    public double getValue() {
        return stack.pop();
    }
}
