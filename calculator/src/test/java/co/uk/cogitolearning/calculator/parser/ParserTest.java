package co.uk.cogitolearning.calculator.parser;

import co.uk.cogitolearning.calculator.lexer.Token;
import co.uk.cogitolearning.calculator.lexer.TokenId;
import co.uk.cogitolearning.calculator.tree.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    public void shouldParseAdd2() {
        // Given

        // When
        ExpressionNode tree = Parser.parse(new LinkedList<>(Arrays.asList(
                new Token(TokenId.NUMBER, "1", 0),
                new Token(TokenId.PLUS, "+", 1),
                new Token(TokenId.NUMBER, "2", 2))));

        // Then
        AdditionNode node = new AdditionNode(new ConstantNode(1.0), new ConstantNode(2.0));

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseAdd3() {
        // Given

        // When
        ExpressionNode tree = Parser.parse(new LinkedList<>(Arrays.asList(
                new Token(TokenId.NUMBER, "1", 0),
                new Token(TokenId.PLUS, "+", 1),
                new Token(TokenId.NUMBER, "2", 2),
                new Token(TokenId.PLUS, "+", 3),
                new Token(TokenId.NUMBER, "3", 4)
        )));

        // Then
        AdditionNode right = new AdditionNode(new ConstantNode(2.0), new ConstantNode(3.0));
        AdditionNode node = new AdditionNode(new ConstantNode(1.0), right);

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseSub2() {
        // Given

        // When
        ExpressionNode tree = Parser.parse(new LinkedList<>(Arrays.asList(
                new Token(TokenId.NUMBER, "3", 0),
                new Token(TokenId.MINUS, "-", 1),
                new Token(TokenId.NUMBER, "1", 2))));

        // Then
        SubtractionNode node = new SubtractionNode(new ConstantNode(3.0), new ConstantNode(1.0));

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseSub3() {
        // Given

        // When
        ExpressionNode tree = Parser.parse(new LinkedList<>(Arrays.asList(
                new Token(TokenId.NUMBER, "6", 0),
                new Token(TokenId.MINUS, "-", 1),
                new Token(TokenId.NUMBER, "2", 2),
                new Token(TokenId.MINUS, "-", 3),
                new Token(TokenId.NUMBER, "3", 4)
        )));

        // Then
        SubtractionNode left = new SubtractionNode(new ConstantNode(6.0), new ConstantNode(2.0));
        SubtractionNode node = new SubtractionNode(left, new ConstantNode(3.0));

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseExtended() {
        // Given

        // When
        // 6*(3+sin(3.1415/2))^5
        ExpressionNode tree = Parser.parse(new LinkedList<>(Arrays.asList(
                new Token(TokenId.NUMBER, "6", 0),
                new Token(TokenId.MULT, "*", 1),
                new Token(TokenId.OPEN_BRACKET, "(", 2),
                new Token(TokenId.NUMBER, "3", 3),
                new Token(TokenId.PLUS, "+", 4),
                new Token(TokenId.FUNCTION, "sin", 5),
                new Token(TokenId.OPEN_BRACKET, "(", 8),
                new Token(TokenId.NUMBER, Double.toString(Math.PI), 9),
                new Token(TokenId.DIV, "/", 15),
                new Token(TokenId.NUMBER, "2", 16),
                new Token(TokenId.CLOSE_BRACKET, ")", 17),
                new Token(TokenId.CLOSE_BRACKET, ")", 18),
                new Token(TokenId.RAISED, "^", 19),
                new Token(TokenId.NUMBER, "5", 20)
        )));

        // Then
        MultiplicationNode expected;
        DivNode div = new DivNode(new ConstantNode(Math.PI), new ConstantNode(2.0));
        FunctionNode sin = new FunctionNode(FunctionNode.SIN, div);
        AdditionNode base = new AdditionNode(new ConstantNode(3.0), sin);
        ExponentiationNode exp = new ExponentiationNode(base, new ConstantNode(5.0));

        expected = new MultiplicationNode(new ConstantNode(6.0), exp);

        assertThat(tree).isEqualTo(expected);
    }
}
