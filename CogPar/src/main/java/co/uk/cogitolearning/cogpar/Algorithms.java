package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.tree.ExpressionNode;
import lombok.experimental.UtilityClass;

import java.util.Iterator;

@UtilityClass
class Algorithms {
    static void setVariable(ExpressionNode root, String name, double value) {
        VariableSetterVisitor visitor = new VariableSetterVisitor(name, value);
        for (ExpressionNode expressionNode : root)
            expressionNode.accept(visitor);
    }
}
