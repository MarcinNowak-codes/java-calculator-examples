package co.uk.cogitolearning.calculator.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

final class ExpressionNodeIterator implements Iterator<ExpressionNode> {
    private final List<ExpressionNode> list = new ArrayList<>();

    ExpressionNodeIterator(final ExpressionNode expressionNode) {
        IteratorVisitor visitor = new IteratorVisitor(list);
        expressionNode.accept(visitor);
    }

    @Override
    public boolean hasNext() {
        return !list.isEmpty();
    }

    @Override
    public ExpressionNode next() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Iteration beyond the end of the collection");
        }
        return list.remove(0);
    }

    private static class IteratorVisitor implements ExpressionNodeVisitor<Void> {
        private final List<ExpressionNode> list;

        IteratorVisitor(final List<ExpressionNode> list) {
            this.list = list;
        }

        @Override
        public Void visit(final VariableNode node) {
            list.add(node);
            return null;
        }

        @Override
        public Void visit(final ConstantNode node) {
            list.add(node);
            return null;
        }

        @Override
        public Void visit(final ExponentiationNode node) {
            list.add(node);
            node.getBase().accept(this);
            node.getExponent().accept(this);
            return null;
        }

        @Override
        public Void visit(final FunctionNode node) {
            list.add(node);
            node.getArgument().accept(this);
            return null;
        }

        @Override
        public Void visit(final AdditionNode node) {
            list.add(node);
            node.getAddendLeft().accept(this);
            node.getAddendRight().accept(this);
            return null;
        }

        @Override
        public Void visit(final SubtractionNode node) {
            list.add(node);
            node.getMinuend().accept(this);
            node.getSubtrahend().accept(this);
            return null;
        }

        @Override
        public Void visit(final MultiplicationNode node) {
            list.add(node);
            node.getMultiplicand().accept(this);
            node.getMultiplier().accept(this);
            return null;
        }

        @Override
        public Void visit(final DivNode node) {
            list.add(node);
            node.getNumerator().accept(this);
            node.getDenominator().accept(this);
            return null;
        }
    }
}
