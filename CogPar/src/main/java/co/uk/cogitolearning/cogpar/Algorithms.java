package co.uk.cogitolearning.cogpar;

import co.uk.cogitolearning.cogpar.tree.ExpressionNode;
import lombok.experimental.UtilityClass;

import java.util.Iterator;

@UtilityClass
class Algorithms {
    static void setVariable(ExpressionNode root, String name, double value) {
        VariableSetterVisitor piVisitor = new VariableSetterVisitor(name, value);
        Iterator<ExpressionNode> it = root.iterator();
        while (it.hasNext())
            it.next().accept(piVisitor);
    }
}
