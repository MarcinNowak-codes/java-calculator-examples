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
                new Token(Token.NUMBER, "1", 0),
                new Token(Token.PLUS, "+", 1),
                new Token(Token.NUMBER, "2", 2));
    }

    @Test
    public void tokenizeAdd3() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("1+2+3");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(Token.NUMBER, "1", 0),
                new Token(Token.PLUS, "+", 1),
                new Token(Token.NUMBER, "2", 2),
                new Token(Token.PLUS, "+", 3),
                new Token(Token.NUMBER, "3", 4));
    }

    @Test
    public void tokenizeSub3() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("4-2-1");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(Token.NUMBER, "4", 0),
                new Token(Token.MINUS, "-", 1),
                new Token(Token.NUMBER, "2", 2),
                new Token(Token.MINUS, "-", 3),
                new Token(Token.NUMBER, "1", 4));
    }

    @Test
    public void tokenizeExtended() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("6*(3+sin(3.1415/2))^5");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(Token.NUMBER, "6", 0),
                new Token(Token.MULT, "*", 1),
                new Token(Token.OPEN_BRACKET, "(", 2),
                new Token(Token.NUMBER, "3", 3),
                new Token(Token.PLUS, "+", 4),
                new Token(Token.FUNCTION, "sin", 5),
                new Token(Token.OPEN_BRACKET, "(", 8),
                new Token(Token.NUMBER, "3.1415", 9),
                new Token(Token.DIV, "/", 15),
                new Token(Token.NUMBER, "2", 16),
                new Token(Token.CLOSE_BRACKET, ")", 17),
                new Token(Token.CLOSE_BRACKET, ")", 18),
                new Token(Token.RAISED, "^", 19),
                new Token(Token.NUMBER, "5", 20)
        );
    }

    @Test
    public void tokenizeExtendedVariable() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("6*(3+sin(pi/2))^5");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(Token.NUMBER, "6", 0),
                new Token(Token.MULT, "*", 1),
                new Token(Token.OPEN_BRACKET, "(", 2),
                new Token(Token.NUMBER, "3", 3),
                new Token(Token.PLUS, "+", 4),
                new Token(Token.FUNCTION, "sin", 5),
                new Token(Token.OPEN_BRACKET, "(", 8),
                new Token(Token.VARIABLE, "pi", 9),
                new Token(Token.DIV, "/", 11),
                new Token(Token.NUMBER, "2", 12),
                new Token(Token.CLOSE_BRACKET, ")", 13),
                new Token(Token.CLOSE_BRACKET, ")", 14),
                new Token(Token.RAISED, "^", 15),
                new Token(Token.NUMBER, "5", 16)
        );
    }

}
