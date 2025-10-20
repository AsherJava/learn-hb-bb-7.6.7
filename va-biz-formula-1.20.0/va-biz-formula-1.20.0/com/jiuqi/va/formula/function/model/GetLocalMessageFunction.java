/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetLocalMessageFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;

    public GetLocalMessageFunction() {
        this.parameters().add(new Parameter("chinese", 6, "\u4e2d\u6587", false));
        this.parameters().add(new Parameter("english", 6, "\u82f1\u6587", false));
        this.parameters().add(new Parameter("other", 6, "\u5176\u4ed6\u8bed\u8a00", true));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    @Override
    public String addDescribe() {
        return "\u6839\u636e\u4e0a\u4e0b\u6587\u8bed\u8a00\u73af\u5883\u8fd4\u56de\u5bf9\u5e94\u7684\u591a\u8bed\u8a00\u6570\u636e";
    }

    public String name() {
        return "GetLocalMessage";
    }

    public String title() {
        return "\u8fd4\u56de\u5bf9\u5e94\u7684\u591a\u8bed\u8a00\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int size = parameters.size();
        if (size % 2 != 0) {
            throw new SyntaxException(BizFormualI18nUtil.getMessage("va.bizformula.param.count.not.same"));
        }
        String language = LocaleContextHolder.getLocale().toLanguageTag();
        if ("zh-cn".equalsIgnoreCase(language)) {
            IASTNode node0 = parameters.get(0);
            return node0.evaluate(context);
        }
        if ("en-us".equalsIgnoreCase(language)) {
            IASTNode node0 = parameters.get(1);
            return node0.evaluate(context);
        }
        if (parameters.size() == 2) {
            return null;
        }
        for (int i = 2; i < parameters.size(); i += 2) {
            IASTNode node0 = parameters.get(i);
            String node1 = (String)node0.evaluate(context);
            if (!language.equalsIgnoreCase(node1)) continue;
            IASTNode iastNode = parameters.get(i + 1);
            return iastNode.evaluate(context);
        }
        return null;
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8fd4\u56de\u4e0e\u5f53\u524d\u73af\u5883\u5bf9\u5e94\u7684\u591a\u8bed\u8a00\u6570\u636e");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("chinese");
        parameterDescription.setType(DataType.toString((int)6));
        parameterDescription.setDescription("\u4e2d\u6587");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("english");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u82f1\u6587");
        parameterDescription1.setRequired(true);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("other");
        parameterDescription2.setType(DataType.toString((int)6));
        parameterDescription2.setDescription("\u5176\u4ed6\u8bed\u8a00\uff0c\u6807\u8bc6\u4e0e\u5bf9\u5e94\u7684\u8bed\u8a00\u9700\u6210\u5bf9\u51fa\u73b0\uff0c\u5176\u4ed6\u8bed\u8a00\u6709\uff1a\u4fc4\u8bed:ru-ru\u3001\n\u6cd5\u8bed:fr-fr\u3001\n\u897f\u73ed\u7259\u8bed:es-es\u3001\n\u5fb7\u8bed:de-de\u3001\n\u65e5\u8bed:ja-jp\u3001\n\u97e9\u8bed:ko-kr\u3001\n\u610f\u5927\u5229\u8bed:it-it\u3001\n\u632a\u5a01\u8bed:no-no\u3001\n\u571f\u8033\u5176\u8bed:tr-tr\u3001\n\u65af\u6d1b\u6587\u5c3c\u4e9a\u8bed:sl-sl\u3001\n\u745e\u5178\u8bed:sv-se\u3001\n\u82ac\u5170\u8bed:fi-fi\u3001\n\u4e39\u9ea6\u8bed:da-dk\u3001\n\u8377\u5170\u8bed:nl-nl\u3001\n\u8461\u8404\u7259\u8bed:pt-pt\u3001\n\u5e0c\u814a\u8bed:el-gr\u3001\n\u5308\u7259\u5229\u8bed:hu-hu");
        parameterDescription2.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u5355\u636e\u5b57\u7b26\u5b57\u6bb5\u8ba1\u7b97\u503c\u516c\u5f0f\u9700\u8ddf\u968f\u591a\u8bed\u8a00\u73af\u5883\u81ea\u52a8\u53d8\u5316");
        formulaExample.setFormula("GetLocalMessage(\"\u4e8b\u7531\", \"memo\", \"ja-jp\", \"\u30e1\u30e2\")");
        formulaExample.setReturnValue("\u4e8b\u7531");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

