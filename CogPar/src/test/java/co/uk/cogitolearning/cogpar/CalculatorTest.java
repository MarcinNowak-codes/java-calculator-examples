package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.parser.Parser;
import co.uk.cogitolearning.cogpar.tree.*;
import org.junit.Assert;
import org.junit.Test;


public class CalculatorTest {
    @Test
    public void shouldCalculateSimpleOperation() {
        // (2+3)*4
        ConstantExpressionNode two = new ConstantExpressionNode(2);
        ConstantExpressionNode three = new ConstantExpressionNode(3);
        ConstantExpressionNode four = new ConstantExpressionNode(4);

        SequenceExpressionNode sum = new AdditionExpressionNode(two, true);
        sum.add(three, true);
        SequenceExpressionNode multi = new MultiplicationExpressionNode(sum, true);
        multi.add(four, true);


        Assert.assertEquals(20.0, Calculator.calculate(multi), 0.1);
    }

    @Test
    public void shouldCalculateExtendedCalculation() {
        ExpressionNode root;

        // String exprstr = "6*(3+sin(pi/2))^5"; //=6144
        ConstantExpressionNode five = new ConstantExpressionNode(6);

        DivExpressionNode halfPi = new DivExpressionNode();
        VariableExpressionNode pi = new VariableExpressionNode("pi");
        halfPi.add(pi, true);
        halfPi.add(new ConstantExpressionNode(2), true);

        FunctionExpressionNode sin = new FunctionExpressionNode(FunctionExpressionNode.SIN, halfPi);
        AdditionExpressionNode braces = new AdditionExpressionNode(new ConstantExpressionNode(3), true);
        braces.add(sin, true);
        ExponentiationExpressionNode exponent = new ExponentiationExpressionNode(braces, new ConstantExpressionNode(5));
        MultiplicationExpressionNode multiplication = new MultiplicationExpressionNode(five, true);
        multiplication.add(exponent, true);

        root = multiplication;

        Algorithms.setVariable(root, "pi", Math.PI);

        Assert.assertEquals(6144.0, Calculator.calculate(root), 0.1);
    }

    @Test
    public void shouldParse() {
        // Given
        Parser parser = new Parser();
        // When
        String exprstr = "2*(1+sin(0.5 * pi))^2"; //=8  with pi/2 is problem because DivExpressionNode was introduced. Parser has to be fixed.
        ExpressionNode expr = parser.parse(exprstr);
        Algorithms.setVariable(expr, "pi", Math.PI);

        // Then
        Assert.assertEquals(8.0, Calculator.calculate(expr), 0.1);
    }

}
