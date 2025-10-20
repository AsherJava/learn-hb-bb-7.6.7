/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetCurMessageContextFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;

    public boolean isInfiniteParameter() {
        return true;
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u5f53\u524d\u73af\u5883\u4e0b\u591a\u8bed\u8a00\u4fe1\u606f";
    }

    public String name() {
        return "GetCurMessageContext";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u73af\u5883\u4e0b\u591a\u8bed\u8a00\u4fe1\u606f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return LocaleContextHolder.getLocale().toLanguageTag();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setDescription(this.addDescribe());
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6));
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6\u5f53\u524d\u5ba2\u6237\u7aef\u591a\u8bed\u8a00\u6807\u8bc6");
        formulaExample.setFormula("GetCurMessageContext()");
        formulaExample.setReturnValue("zh-CN");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

