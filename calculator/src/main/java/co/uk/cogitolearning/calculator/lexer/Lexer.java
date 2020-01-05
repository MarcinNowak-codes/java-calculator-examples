/*
 * This software and all files contained in it are distrubted under the MIT license.
 *
 * Copyright (c) 2013 Cogito Learning Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package co.uk.cogitolearning.calculator.lexer;

import co.uk.cogitolearning.calculator.ParserException;
import co.uk.cogitolearning.calculator.tree.FunctionNode;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for reading an input string and separating it into tokens that can be
 * fed into Parser.
 * <p>
 * The user can add regular expressions that will be matched against the front
 * of the string. Regular expressions should not contain beginning-of-string or
 * end-of-string anchors or any capturing groups as these will be added by the
 * tokenizer itslef.
 */
public class Lexer {
    /**
     * Internal class holding the information about a token type.
     */
    private static class TokenInfo {
        /**
         * the regular expression to match against
         */
        final Pattern regex;
        /**
         * the token id that the regular expression is linked to
         */
        final int token;

        /**
         * Construct TokenInfo with its values
         */
        TokenInfo(Pattern regex, int token) {
            this.regex = regex;
            this.token = token;
        }
    }

    /**
     * a list of TokenInfo objects
     * <p>
     * Each token type corresponds to one entry in the list
     */
    private LinkedList<TokenInfo> tokenInfos;

    /**
     * the list of tokens produced when tokenizing the input
     */
    private LinkedList<Token> tokens;

    /**
     * a tokenizer that can handle mathematical expressions
     */
    private static Lexer instance = null;

    /**
     * Default constructor
     */
    private Lexer() {
        tokenInfos = new LinkedList<>();
        tokens = new LinkedList<>();
    }

    /**
     * A static method that returns a tokenizer for mathematical expressions
     * <p>
     * Not thread safe singleton design pattern
     *
     * @return a tokenizer that can handle mathematical expressions
     */
    public static synchronized Lexer getInstance() {
        if (instance == null)
            instance = create();
        return instance;
    }

    /**
     * A static method that actually creates a tokenizer for mathematical expressions
     *
     * @return a tokenizer that can handle mathematical expressions
     */
    private static Lexer create() {
        Lexer lexer = new Lexer();

        lexer.add("[+]", Token.PLUS);
        lexer.add("[-]", Token.MINUS);
        lexer.add("[*]", Token.MULT);
        lexer.add("[/]", Token.DIV);
        lexer.add("\\^", Token.RAISED);

        String funcs = FunctionNode.SIN_COS_TAN_ASIN_ACOS_ATAN_SQRT_EXP_LN_LOG_LOG_2;
        lexer.add("(" + funcs + ")(?!\\w)", Token.FUNCTION);

        lexer.add("\\(", Token.OPEN_BRACKET);
        lexer.add("\\)", Token.CLOSE_BRACKET);
        lexer.add("(?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?", Token.NUMBER);
        lexer.add("[a-zA-Z]\\w*", Token.VARIABLE);

        return lexer;
    }

    /**
     * Add a regular expression and a token id to the internal list of recognized tokens
     *
     * @param regex the regular expression to match against
     * @param token the token id that the regular expression is linked to
     */
    public void add(String regex, int token) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), token));
    }

    /**
     * Tokenize an input string.
     * <p>
     * The reult of tokenizing can be accessed via getTokens
     *
     * @param str the string to tokenize
     */
    public void tokenize(String str) {
        String s = str.trim();
        int totalLength = s.length();
        tokens.clear();
        while (!s.equals("")) {
            int remaining = s.length();
            boolean match = false;
            for (TokenInfo info : tokenInfos) {
                Matcher m = info.regex.matcher(s);
                if (m.find()) {
                    match = true;
                    String tok = m.group().trim();
                    s = m.replaceFirst("").trim();
                    tokens.add(new Token(info.token, tok, totalLength - remaining));
                    break;
                }
            }
            if (!match)
                throw new ParserException("Unexpected character in input: " + s);
        }
    }

    /**
     * Get the tokens generated in the last call to tokenize.
     *
     * @return a list of tokens to be fed to Parser
     */
    public List<Token> getTokens() {
        return tokens;
    }

}
