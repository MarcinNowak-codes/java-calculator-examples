package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.tree.*;

import java.util.ArrayList;
import java.util.Iterator;

public class ExpressionNodeIterator implements Iterator<ExpressionNode> {
    private final ArrayList<ExpressionNode> list = new ArrayList<>();

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
        private ArrayList<ExpressionNode> list;

        public IteratorVisitor(ArrayList<ExpressionNode> stack) {
            this.list = stack;
        }

        public Void visit(VariableNode node) {
            list.add(node);
            return null;
        }

        public Void visit(ConstantNode node) {
            list.add(node);
            return null;
        }

        public Void visit(AdditionNode node) {
            list.add(node);
            for (SequenceNode.Term t : node.terms)
                t.expression.accept(this);
            return null;
        }

        public Void visit(MultiplicationNode node) {
            list.add(node);
            for (SequenceNode.Term t : node.terms)
                t.expression.accept(this);
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
            node.getArgument().accept(this);
            return null;
        }

        public Void visit(DivNode node) {
            list.add(node);
            for (SequenceNode.Term t : node.terms)
                t.expression.accept(this);
            return null;
        }

        @Override
        public Void visit(SubtractionNode node) {
            list.add(node);
            for (SequenceNode.Term t : node.terms)
                t.expression.accept(this);
            return null;
        }
    }
}
