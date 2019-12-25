package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.parser.Parser;
import co.uk.cogitolearning.cogpar.tree.*;
import org.junit.Assert;
import org.junit.Test;


public class CalculatorTest {
    @Test
    public void shouldCalculateSimpleOperation() {
        // (2+3)*4
        ConstantNode two = new ConstantNode(2);
        ConstantNode three = new ConstantNode(3);
        ConstantNode four = new ConstantNode(4);

        AdditionNode sum = new AdditionNode(two, true);
        sum.add(three, true);
        MultiplicationNode multi = new MultiplicationNode(sum, true);
        multi.add(four, true);


        Assert.assertEquals(20.0, Calculator.calculate(multi), 0.1);
    }

    @Test
    public void shouldCalculateExtendedCalculation() {
        ExpressionNode root;

        // String exprstr = "6*(3+sin(pi/2))^5"; //=6144
        ConstantNode five = new ConstantNode(6);

        DivNode halfPi = new DivNode();
        VariableNode pi = new VariableNode("pi");
        halfPi.add(pi, true);
        halfPi.add(new ConstantNode(2), true);

        FunctionNode sin = new FunctionNode(FunctionNode.SIN, halfPi);
        AdditionNode braces = new AdditionNode(new ConstantNode(3), true);
        braces.add(sin, true);
        ExponentiationNode exponent = new ExponentiationNode(braces, new ConstantNode(5));
        MultiplicationNode multiplication = new MultiplicationNode(five, true);
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
        String expresion = "2*(1+sin(pi/2))^2";
        ExpressionNode expr = parser.parse(expresion);
        Algorithms.setVariable(expr, "pi", Math.PI);

        // Then
        Assert.assertEquals(8.0, Calculator.calculate(expr), 0.1);
    }

    @Test
    public void shouldParseSub() {
        // Given
        Parser parser = new Parser();
        // When
        String expresion = "3-1";
        ExpressionNode expr = parser.parse(expresion);
        Algorithms.setVariable(expr, "pi", Math.PI);

        // Then
        Assert.assertEquals(3 - 1, Calculator.calculate(expr), 0.1);
    }

}
