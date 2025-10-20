/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Divide
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.operator.LessThan
 *  com.jiuqi.bi.syntax.operator.LessThanOrEqual
 *  com.jiuqi.bi.syntax.operator.Like
 *  com.jiuqi.bi.syntax.operator.Minus
 *  com.jiuqi.bi.syntax.operator.MoreThan
 *  com.jiuqi.bi.syntax.operator.MoreThanOrEqual
 *  com.jiuqi.bi.syntax.operator.Multiply
 *  com.jiuqi.bi.syntax.operator.Negative
 *  com.jiuqi.bi.syntax.operator.Not
 *  com.jiuqi.bi.syntax.operator.NotEqual
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.operator.Plus
 *  com.jiuqi.bi.syntax.operator.Positive
 *  com.jiuqi.bi.syntax.operator.Power
 */
package com.jiuqi.va.formula.provider;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Divide;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.operator.LessThan;
import com.jiuqi.bi.syntax.operator.LessThanOrEqual;
import com.jiuqi.bi.syntax.operator.Like;
import com.jiuqi.bi.syntax.operator.Minus;
import com.jiuqi.bi.syntax.operator.MoreThan;
import com.jiuqi.bi.syntax.operator.MoreThanOrEqual;
import com.jiuqi.bi.syntax.operator.Multiply;
import com.jiuqi.bi.syntax.operator.Negative;
import com.jiuqi.bi.syntax.operator.Not;
import com.jiuqi.bi.syntax.operator.NotEqual;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.operator.Plus;
import com.jiuqi.bi.syntax.operator.Positive;
import com.jiuqi.bi.syntax.operator.Power;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.tojs.AndNodeToJS;
import com.jiuqi.va.formula.tojs.BinaryOperatorToJS;
import com.jiuqi.va.formula.tojs.DataNodeToJS;
import com.jiuqi.va.formula.tojs.DynamicDataNodeToJS;
import com.jiuqi.va.formula.tojs.EqualNodeToJS;
import com.jiuqi.va.formula.tojs.ExpressionToJS;
import com.jiuqi.va.formula.tojs.FunctionNodeToJS;
import com.jiuqi.va.formula.tojs.IfThenElseNodeToJS;
import com.jiuqi.va.formula.tojs.InNodeToJS;
import com.jiuqi.va.formula.tojs.LikeNodeToJS;
import com.jiuqi.va.formula.tojs.NotEqualNodeToJS;
import com.jiuqi.va.formula.tojs.NotNodeToJS;
import com.jiuqi.va.formula.tojs.OperatorToJS;
import com.jiuqi.va.formula.tojs.OrNodeToJS;
import com.jiuqi.va.formula.tojs.PowerNodelToJS;
import com.jiuqi.va.formula.tojs.UnaryOperatorToJS;
import java.util.HashMap;
import java.util.Map;

public class JavaScriptNodeProvider {
    private static final Map<ASTNodeType, ToJavaScript> fristMap = new HashMap<ASTNodeType, ToJavaScript>(){
        private static final long serialVersionUID = 1L;
        {
            this.put(ASTNodeType.OPERATOR, new OperatorToJS());
            this.put(ASTNodeType.DATA, new DataNodeToJS());
            this.put(ASTNodeType.DYNAMICDATA, new DynamicDataNodeToJS());
            this.put(ASTNodeType.FUNCTION, new FunctionNodeToJS());
            this.put(ASTNodeType.EXPRESSION, new ExpressionToJS());
        }
    };
    private static final Map<ASTNodeType, Map<Class<?>, ToJavaScript>> secondMap = new HashMap();

    public static ToJavaScript get(ASTNodeType nodeType) throws ToJavaScriptException {
        ToJavaScript javaScript = fristMap.get(nodeType);
        if (javaScript == null) {
            throw new ToJavaScriptException();
        }
        return javaScript;
    }

    public static ToJavaScript get(ASTNodeType nodeType, Class<?> T) throws ToJavaScriptException {
        ToJavaScript javaScript = secondMap.get(nodeType).get(T);
        if (javaScript == null) {
            throw new ToJavaScriptException();
        }
        return javaScript;
    }

    static {
        secondMap.put(ASTNodeType.OPERATOR, new HashMap<Class<?>, ToJavaScript>(){
            private static final long serialVersionUID = 1L;
            {
                this.put(Negative.class, new UnaryOperatorToJS());
                this.put(Positive.class, new UnaryOperatorToJS());
                this.put(Not.class, new NotNodeToJS());
                this.put(Equal.class, new EqualNodeToJS());
                this.put(NotEqual.class, new NotEqualNodeToJS());
                this.put(And.class, new AndNodeToJS());
                this.put(Or.class, new OrNodeToJS());
                this.put(Plus.class, new BinaryOperatorToJS());
                this.put(Minus.class, new BinaryOperatorToJS());
                this.put(Multiply.class, new BinaryOperatorToJS());
                this.put(Divide.class, new BinaryOperatorToJS());
                this.put(LessThan.class, new BinaryOperatorToJS());
                this.put(LessThanOrEqual.class, new BinaryOperatorToJS());
                this.put(MoreThan.class, new BinaryOperatorToJS());
                this.put(MoreThanOrEqual.class, new BinaryOperatorToJS());
                this.put(In.class, new InNodeToJS());
                this.put(Like.class, new LikeNodeToJS());
                this.put(Power.class, new PowerNodelToJS());
                this.put(IfThenElse.class, new IfThenElseNodeToJS());
            }
        });
        secondMap.put(ASTNodeType.DATA, new HashMap<Class<?>, ToJavaScript>(){
            private static final long serialVersionUID = 1L;
            {
                this.put(DataNode.class, new DataNodeToJS());
            }
        });
    }
}

