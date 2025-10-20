/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetCurViewCodeFunction
extends ModelFunction {
    public String addDescribe() {
        return "\u8fd4\u56de\u5f53\u524d\u9875\u9762\u6807\u8bc6";
    }

    public String name() {
        return "GetCurViewCode";
    }

    public String title() {
        return "\u8fd4\u56de\u5f53\u524d\u9875\u9762\u6807\u8bc6\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return ((BillContextImpl)((ModelDataContext)iContext).model.getContext()).getContextValue("curView");
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = FunctionUtils.handleFormula((IFunction)this);
        formulaDescription.setDescription(this.addDescribe());
        return formulaDescription;
    }
}

