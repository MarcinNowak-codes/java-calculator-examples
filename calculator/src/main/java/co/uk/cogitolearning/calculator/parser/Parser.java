package co.uk.cogitolearning.calculator.parser;

import co.uk.cogitolearning.calculator.ParserException;
import co.uk.cogitolearning.calculator.lexer.Token;
import co.uk.cogitolearning.calculator.tree.AdditionNode;
import co.uk.cogitolearning.calculator.tree.ConstantNode;
import co.uk.cogitolearning.calculator.tree.DivNode;
import co.uk.cogitolearning.calculator.tree.ExponentiationNode;
import co.uk.cogitolearning.calculator.tree.ExpressionNode;
import co.uk.cogitolearning.calculator.tree.FunctionNode;
import co.uk.cogitolearning.calculator.tree.MultiplicationNode;
import co.uk.cogitolearning.calculator.tree.SubtractionNode;
import co.uk.cogitolearning.calculator.tree.VariableNode;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

@UtilityClass
public class Parser {

    public static ExpressionNode parse(final List<Token> tokens) {
        //TODO: Replace recursion with iteration
        Stack<ExpressionNode> nodes = new Stack<>();

        while (!tokens.isEmpty()) {
            Token token = tokens.remove(0);
            switch (token.getTokenId()) {
                case Token.NUMBER:
                    nodes.push(new ConstantNode(Double.parseDouble(token.getSequence())));
                    break;
                case Token.PLUS:
                    ExpressionNode left = nodes.pop();
                    ExpressionNode right = parse(tokens);
                    nodes.push(new AdditionNode(left, right));
                    break;
                case Token.MINUS:
                    left = nodes.pop();
                    right = lookaheadMinus(tokens);
                    nodes.push(new SubtractionNode(left, right));
                    break;
                case Token.MULT:
                    left = nodes.pop();
                    right = parse(tokens);
                    nodes.push(new MultiplicationNode(left, right));
                    break;
                case Token.OPEN_BRACKET:
                    nodes.push(parse(tokensInBracket(tokens)));
                    break;
                case Token.CLOSE_BRACKET:
                    break;

                case Token.FUNCTION:
                    int function = FunctionNode.stringToFunction(token.getSequence());
                    nodes.push(new FunctionNode(function, parse(tokens)));
                    break;
                case Token.DIV:
                    left = nodes.pop();
                    right = parse(tokens);
                    nodes.push(new DivNode(left, right));
                    break;
                case Token.RAISED:
                    ExpressionNode expr = nodes.pop();
                    ExpressionNode exponent = parse(tokens);
                    nodes.push(new ExponentiationNode(expr, exponent));
                    break;
                case Token.VARIABLE:
                    nodes.push(new VariableNode(token.getSequence()));
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported: " + token.getTokenId());
            }

        }
        return nodes.pop();
    }

    private static ExpressionNode lookaheadMinus(final List<Token> tokens) {
        assert !tokens.isEmpty();

        if (tokens.get(0).getTokenId() == Token.NUMBER) {
            return parse(new LinkedList<>(Collections.singletonList(tokens.remove(0))));
        }
        return parse(tokens);
    }

    private static List<Token> tokensInBracket(final List<Token> tokens) {
        int brackets = 0;
        List<Token> internals = new LinkedList<>();
        while (!tokens.isEmpty()) {
            Token token = tokens.remove(0);
            if (token.getTokenId() == Token.CLOSE_BRACKET && brackets == 0) {
                return internals;
            }

            if (token.getTokenId() == Token.CLOSE_BRACKET && brackets > 0) {
                brackets--;
            }

            if (token.getTokenId() == Token.OPEN_BRACKET) {
                brackets++;
            }

            internals.add(token);
        }
        throw new ParserException("Closing bracket is missing");
    }
}
