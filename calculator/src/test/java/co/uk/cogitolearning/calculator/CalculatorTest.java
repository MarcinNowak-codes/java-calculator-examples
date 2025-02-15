package co.uk.cogitolearning.calculator;

import co.uk.cogitolearning.calculator.tree.AdditionNode;
import co.uk.cogitolearning.calculator.tree.ConstantNode;
import co.uk.cogitolearning.calculator.tree.DivNode;
import co.uk.cogitolearning.calculator.tree.ExponentiationNode;
import co.uk.cogitolearning.calculator.tree.FunctionId;
import co.uk.cogitolearning.calculator.tree.FunctionNode;
import co.uk.cogitolearning.calculator.tree.MultiplicationNode;
import co.uk.cogitolearning.calculator.tree.VariableNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    @Test
    void shouldCalculateSumOperation() {
        // (2+3)
        ConstantNode two = new ConstantNode(2);
        ConstantNode three = new ConstantNode(3);

        AdditionNode sum = new AdditionNode(two, three);

        assertEquals(5.0, Calculator.calculateTree(sum, Map.of()), 0.1);
    }

    @Test
    void shouldCalculateMultiplicationOperation() {
        // (2*3)
        ConstantNode two = new ConstantNode(2);
        ConstantNode three = new ConstantNode(3);

        MultiplicationNode multi = new MultiplicationNode(two, three);

        assertEquals(6.0, Calculator.calculateTree(multi, Map.of()), 0.1);
    }

    @Test
    void shouldCalculateSimpleOperation() {
        // (2+3)*4
        ConstantNode two = new ConstantNode(2);
        ConstantNode three = new ConstantNode(3);
        ConstantNode four = new ConstantNode(4);

        AdditionNode sum = new AdditionNode(two, three);
        MultiplicationNode multi = new MultiplicationNode(sum, four);

        assertEquals(20.0, Calculator.calculateTree(multi, Map.of()), 0.1);
    }

    @Test
    void shouldCalculateExtendedCalculation() {

        // String exprstr = "6*(3+sin(pi/2))^5"; //=6144
        ConstantNode five = new ConstantNode(6);

        VariableNode pi = new VariableNode("pi");
        DivNode halfPi = new DivNode(pi, new ConstantNode(2));

        FunctionNode sin = new FunctionNode(FunctionId.SIN, halfPi);
        AdditionNode braces = new AdditionNode(new ConstantNode(3), sin);
        ExponentiationNode exponent = new ExponentiationNode(braces, new ConstantNode(5));
        MultiplicationNode root = new MultiplicationNode(five, exponent);

        assertEquals(6144.0, Calculator.calculateTree(root, Map.of("pi", Math.PI)), 0.1);
    }

    @ParameterizedTest
    @CsvSource({
            //         expression,            expected
            "   2*(1+sin(pi/2))^2,                 8.0",
            "                 3-1,                 2.0",
            "               4-1-2,                 1.0",
            "         4-sin(pi/2),                 3.0",
            "       4-1-sin(pi/2),                 2.0",
            "                4-pi,  0.8584073464102069",
            "              5-1-pi,  0.8584073464102069",
            "             log2(2),                 1.0",
            "             log(10),                 1.0",
            "               1+2+3,                 6.0",
    })
    void shouldParse(String expression, double expected) {
        // given
        Calculator calculator = new Calculator()
                .withVariable("pi", Math.PI);
        // when
        double value = calculator.calculate(expression);
        // then
        assertEquals(expected, value, 0.00001);
    }

    @Disabled
    @Test
    void shouldParseRump() {
        // https://books.google.pl/books?id=fZsXBgAAQBAJ&pg=PA179&lpg=PA179&dq=floating+point+error+ibm+format&source=bl&ots=MI5qlHzp51&sig=ACfU3U1lp8FThhWkCmk3erU9ShJGavLl3Q&hl=en&sa=X&ved=2ahUKEwjdm8XH4JLoAhUwSBUIHUxsCiUQ6AEwEHoECAoQAQ#v=onepage&q&f=false
        // x=77617, y=33096
        // given
        Calculator calculator = new Calculator()
                .withVariable("x", 77617)
                .withVariable("y", 33096);
        // when
        double value = calculator.calculate("333.75y^6+x^2(11x^2*y^2-y^6-121y^4-2)+5.5y^8+x/2y");

        // then
        assertEquals(-0.827, value, 0.1);
    }

}
