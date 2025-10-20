/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
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
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.intf.ToFilter;
import com.jiuqi.va.formula.tofilter.BinaryOperatorToFilter;
import com.jiuqi.va.formula.tofilter.DataNodeToFilter;
import com.jiuqi.va.formula.tofilter.DynamicDataNodeToFilter;
import com.jiuqi.va.formula.tofilter.EqualNodeToFilter;
import com.jiuqi.va.formula.tofilter.ExpressionNodeToFilter;
import com.jiuqi.va.formula.tofilter.FunctionNodeToFilter;
import com.jiuqi.va.formula.tofilter.IfThenElseNodeToFilter;
import com.jiuqi.va.formula.tofilter.NotNodeToFilter;
import com.jiuqi.va.formula.tofilter.OperatorToFilter;
import com.jiuqi.va.formula.tofilter.UnaryOperatorToFilter;
import java.util.HashMap;
import java.util.Map;

public class FilterNodeProvider {
    private static final Map<ASTNodeType, ToFilter> fristMap = new HashMap<ASTNodeType, ToFilter>(){
        {
            this.put(ASTNodeType.OPERATOR, new OperatorToFilter());
            this.put(ASTNodeType.DATA, new DataNodeToFilter());
            this.put(ASTNodeType.EXPRESSION, new ExpressionNodeToFilter());
            this.put(ASTNodeType.DYNAMICDATA, new DynamicDataNodeToFilter());
            this.put(ASTNodeType.FUNCTION, new FunctionNodeToFilter());
        }
    };
    private static final Map<ASTNodeType, Map<Class<?>, ToFilter>> secondMap = new HashMap();

    public static ToFilter get(ASTNodeType nodeType) throws ToFilterException {
        ToFilter toFilter = fristMap.get(nodeType);
        if (toFilter == null) {
            throw new ToFilterException();
        }
        return toFilter;
    }

    public static ToFilter get(ASTNodeType nodeType, Class<?> T) throws ToFilterException {
        ToFilter toFilter = secondMap.get(nodeType).get(T);
        if (toFilter == null) {
            throw new ToFilterException();
        }
        return toFilter;
    }

    static {
        secondMap.put(ASTNodeType.OPERATOR, new HashMap<Class<?>, ToFilter>(){
            {
                this.put(Negative.class, new UnaryOperatorToFilter());
                this.put(Positive.class, new UnaryOperatorToFilter());
                this.put(Not.class, new NotNodeToFilter());
                this.put(Equal.class, new EqualNodeToFilter());
                this.put(NotEqual.class, new BinaryOperatorToFilter());
                this.put(And.class, new BinaryOperatorToFilter());
                this.put(Or.class, new BinaryOperatorToFilter());
                this.put(Plus.class, new BinaryOperatorToFilter());
                this.put(Minus.class, new BinaryOperatorToFilter());
                this.put(Multiply.class, new BinaryOperatorToFilter());
                this.put(Divide.class, new BinaryOperatorToFilter());
                this.put(LessThan.class, new BinaryOperatorToFilter());
                this.put(LessThanOrEqual.class, new BinaryOperatorToFilter());
                this.put(MoreThan.class, new BinaryOperatorToFilter());
                this.put(MoreThanOrEqual.class, new BinaryOperatorToFilter());
                this.put(In.class, new BinaryOperatorToFilter());
                this.put(Like.class, new BinaryOperatorToFilter());
                this.put(Power.class, new BinaryOperatorToFilter());
                this.put(IfThenElse.class, new IfThenElseNodeToFilter());
            }
        });
    }
}

