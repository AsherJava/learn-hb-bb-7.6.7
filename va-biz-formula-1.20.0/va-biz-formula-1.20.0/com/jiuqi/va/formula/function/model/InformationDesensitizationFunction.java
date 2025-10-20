/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.desensitization.SensitiveType;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.desensitization.VaDesensitizationFactory;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InformationDesensitizationFunction
extends ModelFunction {
    public InformationDesensitizationFunction() {
        this.parameters().add(new Parameter("information", 6, "\u8131\u654f\u4fe1\u606f"));
        this.parameters().add(new Parameter("type", 6, "phone:\u624b\u673a\u53f7,id:\u8eab\u4efd\u8bc1,bank:\u94f6\u884c\u5361\u53f7,email:\u90ae\u7bb1"));
    }

    @Override
    public String addDescribe() {
        return "\u5c06\u5b57\u6bb5\u4fe1\u606f\u8fdb\u884c\u8131\u654f\u5904\u7406";
    }

    public String name() {
        return "InformationDesensitization";
    }

    public String title() {
        return "\u8eab\u4efd\u8bc1\u53f7\u624b\u673a\u53f7\u94f6\u884c\u8d26\u53f7\u8131\u654f";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String value = (String)list.get(0).evaluate(iContext);
        String type = (String)list.get(1).evaluate(iContext);
        if (value == null || value.isEmpty()) {
            return "";
        }
        if ("phone".equals(type)) {
            return VaDesensitizationFactory.getDesensitization(SensitiveType.PHONE).desensitize(value);
        }
        if ("id".equals(type)) {
            return VaDesensitizationFactory.getDesensitization(SensitiveType.ID_CARD).desensitize(value);
        }
        if ("bank".equals(type)) {
            return VaDesensitizationFactory.getDesensitization(SensitiveType.BANK_CARD).desensitize(value);
        }
        if ("email".equals(type)) {
            return VaDesensitizationFactory.getDesensitization(SensitiveType.EMAIL).desensitize(value);
        }
        return value;
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = FunctionUtils.handleFormula((IFunction)this);
        formulaDescription.setDescription(this.addDescribe());
        return formulaDescription;
    }
}

