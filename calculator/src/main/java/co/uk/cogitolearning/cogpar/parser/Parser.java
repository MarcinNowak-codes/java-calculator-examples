package co.uk.cogitolearning.cogpar.parser;

import co.uk.cogitolearning.cogpar.ParserException;
import co.uk.cogitolearning.cogpar.lexer.Token;
import co.uk.cogitolearning.cogpar.tree.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Parser {

    public ExpressionNode parse(List<Token> tokens) {
        //TODO: Replace recursion with iteration
        Stack<ExpressionNode> nodes = new Stack<>();

        while (!tokens.isEmpty()) {
            Token token = tokens.remove(0);
            switch (token.tokenId) {
                case Token.NUMBER:
                    nodes.push(new ConstantNode(Double.parseDouble(token.sequence)));
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
                    int function = FunctionNode.stringToFunction(token.sequence);
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
                    nodes.push(new VariableNode(token.sequence));
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported: " + token.tokenId);
            }

        }
        return nodes.pop();
    }

    private ExpressionNode lookaheadMinus(List<Token> tokens) {
        assert !tokens.isEmpty();

        if (tokens.get(0).tokenId == Token.NUMBER)
            return parse(new LinkedList<>(Collections.singletonList(tokens.remove(0))));
        return parse(tokens);
    }

    private List<Token> tokensInBracket(List<Token> tokens) {
        int brackets = 0;
        List<Token> internals = new LinkedList<>();
        while (!tokens.isEmpty()) {
            Token token = tokens.remove(0);
            if (token.tokenId == Token.CLOSE_BRACKET && brackets == 0)
                return internals;

            if (token.tokenId == Token.CLOSE_BRACKET && brackets > 0)
                brackets--;

            if (token.tokenId == Token.OPEN_BRACKET)
                brackets++;

            internals.add(token);
        }
        throw new ParserException("Closing bracket is missing");
    }
}
