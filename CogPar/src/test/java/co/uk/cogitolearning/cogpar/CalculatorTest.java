package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.tree.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class CalculatorTest {
    @Test
    public void shouldCalculateSimpleOperation() {
        // (2+3)*4
        ConstantNode two = new ConstantNode(2);
        ConstantNode three = new ConstantNode(3);
        ConstantNode four = new ConstantNode(4);

        AdditionNodeNew sum = new AdditionNodeNew(two, three);
        MultiplicationNodeNew multi = new MultiplicationNodeNew(sum, four);

        Assert.assertEquals(20.0, Calculator.calculateTree(multi), 0.1);
    }

    @Test
    public void shouldCalculateExtendedCalculation() {
        ExpressionNode root;

        // String exprstr = "6*(3+sin(pi/2))^5"; //=6144
        ConstantNode five = new ConstantNode(6);

        VariableNode pi = new VariableNode("pi");
        DivNodeNew halfPi = new DivNodeNew(pi, new ConstantNode(2));

        FunctionNode sin = new FunctionNode(FunctionNode.SIN, halfPi);
        AdditionNodeNew braces = new AdditionNodeNew(new ConstantNode(3), sin);
        ExponentiationNode exponent = new ExponentiationNode(braces, new ConstantNode(5));
        MultiplicationNodeNew multiplication = new MultiplicationNodeNew(five, exponent);

        root = multiplication;

        Algorithms.setVariable(root, "pi", Math.PI);

        Assert.assertEquals(6144.0, Calculator.calculateTree(root), 0.1);
    }

    @Test
    public void shouldParse() {
        Assert.assertEquals(8.0, Calculator.calculate("2*(1+sin(pi/2))^2"), 0.1);
    }

    @Test
    public void shouldParseSub() {
        Assert.assertEquals(3 - 1, Calculator.calculate("3-1"), 0.1);
    }

    @Ignore
    @Test
    public void shouldParseSub2() {
        Assert.assertEquals(4 - 1 - 2, Calculator.calculate("4-1-2"), 0.1);
    }

    @Ignore
    @Test
    public void shouldParseAdd2() {
        Assert.assertEquals(1 + 2 + 3, Calculator.calculate("1+2+3"), 0.1);
    }

}
