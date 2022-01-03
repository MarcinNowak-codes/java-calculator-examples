/*
 * This software and all files contained in it are distrubted under the MIT license.
 *
 * Copyright (c) 2013 Cogito Learning Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package co.uk.cogitolearning.calculator;

import co.uk.cogitolearning.calculator.lexer.Lexer;
import co.uk.cogitolearning.calculator.parser.Parser;
import co.uk.cogitolearning.calculator.tree.*;

import java.util.*;

public final class Calculator {

    private final Map<String, Double> variables = new HashMap<>();

    static double calculateTree(final ExpressionNode expr, Map<String, Double> variable) {
        ArrayList<ExpressionNode> polishNotationList = new ArrayList<>();

        for (ExpressionNode node : expr) {
            polishNotationList.add(node);
        }
        return calculatePolishNotation(polishNotationList, variable);
    }

    private static double calculatePolishNotation(final ArrayList<ExpressionNode> list, Map<String, Double> variable) {
        // https://en.wikipedia.org/wiki/Polish_notation
        Collections.reverse(list); // Scan the given prefix expression from right to left

        final Stack<Double> stack = new Stack<>();

        for (ExpressionNode node : list) {
            var result = switch (node) {
                case AdditionNode no -> stack.pop() + (double) stack.pop();
                case ConstantNode no -> stack.push(no.value());
                case DivNode no -> stack.pop() / (double) stack.pop();
                case ExponentiationNode no -> Math.pow(stack.pop(), stack.pop());
                case FunctionNode no -> calculateFunction(no.functionId(), stack.pop());
                case MultiplicationNode no -> stack.pop() * (double) stack.pop();
                case SubtractionNode no -> stack.pop() - (double) stack.pop();
                case VariableNode no -> {
                    if (!variable.containsKey(no.name())) {
                        throw new EvaluationException("Variable '" + no.name() + "' was not initialized.");
                    }
                    yield variable.get(no.name());
                }
                default -> throw new IllegalStateException("Unexpected value: " + node);
            };
            stack.push(result);
        }

        return stack.pop();
    }

    public static double calculateFunction(final FunctionId functionId, final double argument) {
        return switch (functionId) {
            case SIN -> Math.sin(argument);
            case COS -> Math.cos(argument);
            case TAN -> Math.tan(argument);
            case ASIN -> Math.asin(argument);
            case ACOS -> Math.acos(argument);
            case ATAN -> Math.atan(argument);
            case SQRT -> Math.sqrt(argument);
            case EXP -> Math.exp(argument);
            case LN -> Math.log(argument);
            case LOG -> Math.log10(argument);
            case LOG2 -> Math.log10(argument) / Math.log10(2); // Change of base
        };
    }

    public double calculate(final String expresion) {
        Lexer lexer = Lexer.getInstance();
        lexer.tokenize(expresion);
        ExpressionNode expr = Parser.parse(lexer.getTokens());
        return calculateTree(expr, variables);
    }

    public Calculator withVariable(final String variable, final double value) {
        variables.put(variable, value);
        return this;
    }
}
