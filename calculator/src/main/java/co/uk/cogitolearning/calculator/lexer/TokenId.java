package co.uk.cogitolearning.calculator.lexer;

public enum TokenId {
    /**
     * Token id for the epsilon terminal.
     */
    EPSILON,
    /**
     * Token id for plus.
     */
    PLUS,
    /**
     * Token id for multiplication.
     */
    MULT,
    /**
     * Token id for the exponentiation symbol.
     */
    RAISED,
    /**
     * Token id for function names.
     */
    FUNCTION,
    /**
     * Token id for opening brackets.
     */
    OPEN_BRACKET,
    /**
     * Token id for closing brackets.
     */
    CLOSE_BRACKET,
    /**
     * Token id for numbers.
     */
    NUMBER,
    /**
     * Token id for variable names.
     */
    VARIABLE,
    /**
     * Token id for division.
     */
    DIV,
    /**
     * Token id for minus.
     */
    MINUS;
}
