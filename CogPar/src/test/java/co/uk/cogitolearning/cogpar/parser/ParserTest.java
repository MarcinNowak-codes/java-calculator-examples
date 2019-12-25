package co.uk.cogitolearning.cogpar.parser;

import co.uk.cogitolearning.cogpar.lexer.Token;
import co.uk.cogitolearning.cogpar.tree.AdditionNode;
import co.uk.cogitolearning.cogpar.tree.ConstantNode;
import co.uk.cogitolearning.cogpar.tree.ExpressionNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    public void shouldParse() {
        // Given
        Parser parser = new Parser();

        // When
        ExpressionNode tree = parser.parse(new LinkedList<>(Arrays.asList(
                new Token(7, "1", 0),
                new Token(1, "+", 1),
                new Token(7, "2", 2))));
        // Then
        AdditionNode node;
        node = new AdditionNode(new ConstantNode(1.0), true);
        node.add(new ConstantNode(2.0), true);

        assertThat(tree).containsExactly(
                node,
                new ConstantNode(1.0),
                new ConstantNode(2.0));
    }

}
