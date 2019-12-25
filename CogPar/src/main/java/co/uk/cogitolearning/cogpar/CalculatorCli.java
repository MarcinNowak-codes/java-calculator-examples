package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.parser.Parser;
import co.uk.cogitolearning.cogpar.tree.ExpressionNode;

/**
 * Command line interface for calculator
 */
public class CalculatorCli {
    /**
     * The main method to test the functionality of the parser
     */
    public static void main(String[] args) {

        String expresion = "2*(1+sin(0.5 * pi))^2"; //=8  with pi/2 is problem because DivExpressionNode was introduced. Parser has to be fixed.
//    String exprstr = "2*3";
        if (args.length > 0) expresion = args[0];

        Parser parser = new Parser();
        try {
            ExpressionNode expr = parser.parse(expresion);
            Algorithms.setVariable(expr, "pi", Math.PI);

            System.out.println("The value of the expression is " + Calculator.calculate(expr));

        } catch (ParserException e) {
            System.out.println(e.getMessage());
        } catch (EvaluationException e) {
            System.out.println(e.getMessage());
        }
    }
}
