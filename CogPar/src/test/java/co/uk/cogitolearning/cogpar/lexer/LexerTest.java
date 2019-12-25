package co.uk.cogitolearning.cogpar.lexer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerTest {

    @Test
    public void tokenizeAdd() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("1+2");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(7, "1", 0),
                new Token(1, "+", 1),
                new Token(7, "2", 2));
    }

    @Test
    public void tokenizeAdd3() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("1+2+3");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(7, "1", 0),
                new Token(1, "+", 1),
                new Token(7, "2", 2),
                new Token(1, "+", 3),
                new Token(7, "3", 4));
    }

    @Test
    public void tokenizeSub3() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("4-2-1");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(7, "4", 0),
                new Token(10, "-", 1),
                new Token(7, "2", 2),
                new Token(10, "-", 3),
                new Token(7, "1", 4));
    }
}
