package co.uk.cogitolearning.cogpar.parser;

import co.uk.cogitolearning.cogpar.lexer.Token;
import co.uk.cogitolearning.cogpar.tree.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    public void shouldParseAdd2() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(Token.NUMBER, "1", 0),
                new Token(Token.PLUS, "+", 1),
                new Token(Token.NUMBER, "2", 2))));

        // Then
        AdditionNode node = new AdditionNode(new ConstantNode(1.0), true);
        node.add(new ConstantNode(2.0), true);

        assertThat(tree).isEqualTo(node);
    }

    @Ignore
    @Test
    public void shouldParseAdd2New() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(Token.NUMBER, "1", 0),
                new Token(Token.PLUS, "+", 1),
                new Token(Token.NUMBER, "2", 2))));

        // Then
        AdditionNodeNew node = new AdditionNodeNew(new ConstantNode(1.0), new ConstantNode(2.0));

        assertThat(tree).isEqualTo(node);
    }


    @Test
    public void shouldParseAdd3() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(Token.NUMBER, "1", 0),
                new Token(Token.PLUS, "+", 1),
                new Token(Token.NUMBER, "2", 2),
                new Token(Token.PLUS, "+", 3),
                new Token(Token.NUMBER, "3", 4)
        )));

        // Then
        AdditionNode node = new AdditionNode(new ConstantNode(1.0), true);
        node.add(new ConstantNode(2.0), true);
        node.add(new ConstantNode(3.0), true);

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseSub2() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(Token.NUMBER, "3", 0),
                new Token(Token.MINUS, "-", 1),
                new Token(Token.NUMBER, "1", 2))));

        // Then
        SubtractionNode node = new SubtractionNode(new ConstantNode(3.0), true);
        node.add(new ConstantNode(1.0), false);

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseSub3() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(Token.NUMBER, "6", 0),
                new Token(Token.MINUS, "-", 1),
                new Token(Token.NUMBER, "2", 2),
                new Token(Token.MINUS, "-", 3),
                new Token(Token.NUMBER, "3", 4)
        )));

        // Then
        SubtractionNode node = new SubtractionNode(new ConstantNode(6.0), true);
        node.add(new ConstantNode(2.0), false);
        node.add(new ConstantNode(3.0), false);

        assertThat(tree).isEqualTo(node);
    }

    @Test
    public void shouldParseSubExtended() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(7, "6", 0),
                new Token(2, "*", 1),
                new Token(5, "(", 2),
                new Token(7, "3", 3),
                new Token(1, "+", 4),
                new Token(4, "sin", 5),
                new Token(5, "(", 8),
                new Token(8, "pi", 9),
                new Token(9, "/", 11),
                new Token(7, "2", 12),
                new Token(6, ")", 13),
                new Token(6, ")", 14),
                new Token(3, "^", 15),
                new Token(7, "5", 16)
        )));

        // Then
        SubtractionNode node = new SubtractionNode(new ConstantNode(6.0), true);
        node.add(new ConstantNode(2.0), false);
        node.add(new ConstantNode(3.0), false);

        assertThat(tree).isEqualTo(node);
    }
}
