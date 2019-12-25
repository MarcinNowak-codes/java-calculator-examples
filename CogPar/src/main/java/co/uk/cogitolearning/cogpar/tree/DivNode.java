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

package co.uk.cogitolearning.cogpar.tree;

import co.uk.cogitolearning.cogpar.ExpressionNodeIterator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;

/**
 * An ExpressionNode that handles divisions. The node can hold
 * an two number of factors that are divided.
 */
@ToString
@EqualsAndHashCode
public class DivNode implements ExpressionNode {
    public final ExpressionNode left;
    public final ExpressionNode right;

    /**
     * Constructor to create a multiplication with the first term already added.
     *
     * @param left
     * @param right
     */
    public DivNode(ExpressionNode left, ExpressionNode right) {

        this.left = left;
        this.right = right;
    }

    /**
     * Returns the type of the node, in this case ExpressionNode.MULTIPLICATION_NODE
     */
    public int getType() {
        return ExpressionNode.DIVISION_NODE;
    }

    @Override
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Iterator iterator() {
        return new ExpressionNodeIterator(this);
    }

}
