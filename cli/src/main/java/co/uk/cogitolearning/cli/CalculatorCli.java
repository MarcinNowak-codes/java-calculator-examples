package co.uk.cogitolearning.cli;

import co.uk.cogitolearning.calculator.Calculator;
import co.uk.cogitolearning.calculator.EvaluationException;
import co.uk.cogitolearning.calculator.ParserException;

/**
 * Command line interface for calculator
 */
public class CalculatorCli {
    /**
     * The main method to test the functionality of the parser
     */
    public static void main(String[] args) {

        String expresion = "2*(1+sin(0.5 * pi))^2"; //=8  with pi/2 is problem because DivExpressionNode was introduced. Parser has to be fixed.
        if (args.length > 0) expresion = args[0];

        try {
            System.out.println("The value of the expression is " + Calculator.calculate(expresion));
        } catch (ParserException | EvaluationException e) {
            System.err.println(e.getMessage());
        }
    }

}
