package co.uk.cogitolearning.calculator.lexer;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LexerTest {

    @Test
    void tokenizeAdd() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("1+2");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(TokenId.NUMBER, "1", 0),
                new Token(TokenId.PLUS, "+", 1),
                new Token(TokenId.NUMBER, "2", 2));
    }

    @Test
    void tokenizeAdd3() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("1+2+3");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(TokenId.NUMBER, "1", 0),
                new Token(TokenId.PLUS, "+", 1),
                new Token(TokenId.NUMBER, "2", 2),
                new Token(TokenId.PLUS, "+", 3),
                new Token(TokenId.NUMBER, "3", 4));
    }

    @Test
    void tokenizeSub3() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("4-2-1");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(TokenId.NUMBER, "4", 0),
                new Token(TokenId.MINUS, "-", 1),
                new Token(TokenId.NUMBER, "2", 2),
                new Token(TokenId.MINUS, "-", 3),
                new Token(TokenId.NUMBER, "1", 4));
    }

    @Test
    void tokenizeExtended() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("6*(3+sin(3.1415/2))^5");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(TokenId.NUMBER, "6", 0),
                new Token(TokenId.MULT, "*", 1),
                new Token(TokenId.OPEN_BRACKET, "(", 2),
                new Token(TokenId.NUMBER, "3", 3),
                new Token(TokenId.PLUS, "+", 4),
                new Token(TokenId.FUNCTION, "sin", 5),
                new Token(TokenId.OPEN_BRACKET, "(", 8),
                new Token(TokenId.NUMBER, "3.1415", 9),
                new Token(TokenId.DIV, "/", 15),
                new Token(TokenId.NUMBER, "2", 16),
                new Token(TokenId.CLOSE_BRACKET, ")", 17),
                new Token(TokenId.CLOSE_BRACKET, ")", 18),
                new Token(TokenId.RAISED, "^", 19),
                new Token(TokenId.NUMBER, "5", 20)
        );
    }

    @Test
    void tokenizeExtendedVariable() {
        // Given
        Lexer lexer = Lexer.getInstance();
        // When
        lexer.tokenize("6*(3+sin(pi/2))^5");
        // Then
        assertThat(lexer.getTokens()).containsExactly(
                new Token(TokenId.NUMBER, "6", 0),
                new Token(TokenId.MULT, "*", 1),
                new Token(TokenId.OPEN_BRACKET, "(", 2),
                new Token(TokenId.NUMBER, "3", 3),
                new Token(TokenId.PLUS, "+", 4),
                new Token(TokenId.FUNCTION, "sin", 5),
                new Token(TokenId.OPEN_BRACKET, "(", 8),
                new Token(TokenId.VARIABLE, "pi", 9),
                new Token(TokenId.DIV, "/", 11),
                new Token(TokenId.NUMBER, "2", 12),
                new Token(TokenId.CLOSE_BRACKET, ")", 13),
                new Token(TokenId.CLOSE_BRACKET, ")", 14),
                new Token(TokenId.RAISED, "^", 15),
                new Token(TokenId.NUMBER, "5", 16)
        );
    }

}
