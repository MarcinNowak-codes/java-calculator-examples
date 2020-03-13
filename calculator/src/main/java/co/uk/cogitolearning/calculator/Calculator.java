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
import co.uk.cogitolearning.calculator.tree.ExpressionNode;

import java.util.ArrayList;
import java.util.Collections;

public final class Calculator {

    private final CalculationVisitor visitor = new CalculationVisitor();

    static double calculateTree(final ExpressionNode expr, final CalculationVisitor visitor) {
        ArrayList<ExpressionNode> polishNotationList = new ArrayList<>();

        for (ExpressionNode node : expr) {
            polishNotationList.add(node);
        }
        return calculatePolishNotation(polishNotationList, visitor);
    }

    private static double calculatePolishNotation(final ArrayList<ExpressionNode> list, final CalculationVisitor visitor) {
        // https://en.wikipedia.org/wiki/Polish_notation
        Collections.reverse(list); // Scan the given prefix expression from right to left

        for (ExpressionNode node : list) {
            node.accept(visitor);
        }

        return visitor.getValue();
    }

    public double calculate(final String expresion) {
        Lexer lexer = Lexer.getInstance();
        lexer.tokenize(expresion);
        ExpressionNode expr = Parser.parse(lexer.getTokens());
        return calculateTree(expr, visitor);
    }

    public Calculator withVariable(final String variable, final double value) {
        visitor.addVariable(variable, value);
        return this;
    }
}
