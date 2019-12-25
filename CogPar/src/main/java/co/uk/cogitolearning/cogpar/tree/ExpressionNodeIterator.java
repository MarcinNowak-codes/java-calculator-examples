package co.uk.cogitolearning.cogpar.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExpressionNodeIterator implements Iterator<ExpressionNode> {
    private final List<ExpressionNode> list = new ArrayList<>();

    public ExpressionNodeIterator(ExpressionNode expressionNode) {
        IteratorVisitor visitor = new IteratorVisitor(list);
        expressionNode.accept(visitor);
    }

    @Override
    public boolean hasNext() {
        return !list.isEmpty();
    }

    @Override
    public ExpressionNode next() {
        return list.remove(0);
    }

    private static class IteratorVisitor implements ExpressionNodeVisitor<Void> {
        private List<ExpressionNode> list;

        IteratorVisitor(List<ExpressionNode> list) {
            this.list = list;
        }

        public Void visit(VariableNode node) {
            list.add(node);
            return null;
        }

        public Void visit(ConstantNode node) {
            list.add(node);
            return null;
        }

        public Void visit(ExponentiationNode node) {
            list.add(node);
            node.base.accept(this);
            node.exponent.accept(this);
            return null;
        }

        public Void visit(FunctionNode node) {
            list.add(node);
            node.argument.accept(this);
            return null;
        }

        @Override
        public Void visit(AdditionNode node) {
            list.add(node);
            node.addendLeft.accept(this);
            node.addendRight.accept(this);
            return null;
        }

        @Override
        public Void visit(SubtractionNode node) {
            list.add(node);
            node.minuend.accept(this);
            node.subtrahend.accept(this);
            return null;
        }

        @Override
        public Void visit(MultiplicationNode node) {
            list.add(node);
            node.multiplicand.accept(this);
            node.multiplier.accept(this);
            return null;
        }

        @Override
        public Void visit(DivNode node) {
            list.add(node);
            node.numerator.accept(this);
            node.denominator.accept(this);
            return null;
        }
    }
}
