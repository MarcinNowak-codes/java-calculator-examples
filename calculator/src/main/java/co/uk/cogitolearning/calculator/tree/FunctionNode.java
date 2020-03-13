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

package co.uk.cogitolearning.calculator.tree;

import co.uk.cogitolearning.calculator.ParserException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * An ExpressionNode that handles mathematical functions.
 *
 * <p>Some pre-defined functions are handled, others can easily be added.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class FunctionNode implements ExpressionNode {
    /**
     * Function id for the sin function.
     */
    public static final int SIN = 1;
    /**
     * Function id for the cos function.
     */
    public static final int COS = 2;
    /**
     * Function id for the tan function.
     */
    public static final int TAN = 3;

    /**
     * Function id for the asin function.
     */
    public static final int ASIN = 4;
    /**
     * Function id for the acos function.
     */
    public static final int ACOS = 5;
    /**
     * Function id for the atan function.
     */
    public static final int ATAN = 6;

    /**
     * Function id for the sqrt function.
     */
    public static final int SQRT = 7;
    /**
     * Function id for the exp function.
     */
    public static final int EXP = 8;

    /**
     * Function id for the ln function.
     */
    public static final int LN = 9;
    /**
     * Function id for the log function.
     */
    public static final int LOG = 10;
    /**
     * Function id for the log2 function.
     */
    public static final int LOG2 = 11;

    /**
     * Consists a string with all the function names concatenated by a | symbol.
     */
    public static final String SIN_COS_TAN_ASIN_ACOS_ATAN_SQRT_EXP_LN_LOG_LOG_2 = "sin|cos|tan|asin|acos|atan|sqrt|exp|ln|log|log2";

    /**
     * The id of the function to apply to the argument.
     */
    private final int function;

    /**
     * The argument of the function.
     */
    private final ExpressionNode argument;

    /**
     * Converts a string to a function id.
     *
     * <p>If the function is not found this method throws an error.
     *
     * @param function the name of the function
     * @return the id of the function
     */
    public static int stringToFunction(final String function) {
        switch (function) {
            case "sin":
                return FunctionNode.SIN;
            case "cos":
                return FunctionNode.COS;
            case "tan":
                return FunctionNode.TAN;
            case "asin":
                return FunctionNode.ASIN;
            case "acos":
                return FunctionNode.ACOS;
            case "atan":
                return FunctionNode.ATAN;
            case "sqrt":
                return FunctionNode.SQRT;
            case "exp":
                return FunctionNode.EXP;
            case "ln":
                return FunctionNode.LN;
            case "log":
                return FunctionNode.LOG;
            case "log2":
                return FunctionNode.LOG2;
            default:
                throw new ParserException("Unexpected Function " + function + " found");
        }
    }

    @Override
    public void accept(final ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }

}
