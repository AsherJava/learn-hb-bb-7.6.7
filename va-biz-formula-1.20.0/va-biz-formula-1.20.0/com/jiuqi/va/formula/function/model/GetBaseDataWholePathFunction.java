/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.formula.common.exception.FormulaException;
import com.jiuqi.va.formula.common.utils.FormulaCommonUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class GetBaseDataWholePathFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public GetBaseDataWholePathFunction() {
        this.parameters().add(new Parameter("code", 6, "\u57fa\u7840\u6570\u636ecode", false));
        this.parameters().add(new Parameter("table", 6, "\u57fa\u7840\u6570\u636e\u8868\u540d", false));
        this.parameters().add(new Parameter("separator", 6, "\u5206\u9694\u7b26", false));
        this.parameters().add(new Parameter("fieldName", 6, "\u76ee\u6807\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("dimension", 0, "\u9694\u79bb\u7ef4\u5ea6", true));
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u6307\u5b9a\u8868\u4e2d\u6307\u5b9a\u57fa\u7840\u6570\u636e\u7684\u5168\u8def\u5f84\u503c\uff0c\u6309\u6307\u5b9a\u5206\u9694\u7b26\u5206\u9694";
    }

    public String name() {
        return "GetBaseDataWholePath";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u57fa\u7840\u6570\u636e\u5168\u8def\u5f84\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String code = (String)parameters.get(0).evaluate(context);
        String tableName = (String)parameters.get(1).evaluate(context);
        String sep = (String)parameters.get(2).evaluate(context);
        String pathFieldName = (String)parameters.get(3).evaluate(context);
        if (ObjectUtils.isEmpty(code) || ObjectUtils.isEmpty(tableName) || ObjectUtils.isEmpty(sep) || ObjectUtils.isEmpty(pathFieldName)) {
            throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.param.not.empty"));
        }
        HashMap<String, Object> dimValue = null;
        int paramterSize = parameters.size();
        if (paramterSize > 4) {
            if (paramterSize % 2 != 0) {
                throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.isolation.dimension.pair"));
            }
            dimValue = new HashMap<String, Object>();
            for (int i = 4; i < paramterSize; i += 2) {
                String key = (String)parameters.get(i).evaluate(context);
                Object value = parameters.get(i + 1).evaluate(context);
                if (!StringUtils.hasText(key)) {
                    throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.isolation.key.not.empty"));
                }
                dimValue.put(key.toLowerCase(), value);
            }
        }
        pathFieldName = pathFieldName.toLowerCase();
        BaseDataDO data = FormulaCommonUtil.getBasedataByCode(tableName, code, dimValue);
        if (data == null) {
            throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.not.find.target.data"));
        }
        if (pathFieldName.equalsIgnoreCase("CODE")) {
            return data.getParents().replace("/", sep);
        }
        String[] codes = data.getParents().split("/");
        if (codes.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < codes.length - 1; ++index) {
                BaseDataDO pData = FormulaCommonUtil.getBasedataByCode(tableName, codes[index], dimValue);
                if (pData == null) {
                    throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.get.basedata.empty", new Object[]{codes[index]}));
                }
                stringBuilder.append(pData.getOrDefault((Object)pathFieldName, (Object)"")).append(sep);
            }
            stringBuilder.append(data.getOrDefault((Object)pathFieldName, (Object)""));
            return stringBuilder.toString();
        }
        return (String)data.get((Object)pathFieldName);
    }

    @Override
    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("code\uff1a").append(DataType.toString((int)6)).append("\uff1b \u57fa\u7840\u6570\u636ecode\uff0c\u5fc5\u9700").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("table\uff1a").append(DataType.toString((int)6)).append("\uff1b \u57fa\u7840\u6570\u636e\u8868\u540d\uff0c\u5fc5\u9700").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("separator\uff1a").append(DataType.toString((int)6)).append("\uff1b \u5206\u9694\u7b26\uff0c\u5fc5\u9700").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("fieldName\uff1a").append(DataType.toString((int)6)).append("\uff1b \u6307\u5b9a\u8fd4\u56de\u57fa\u7840\u6570\u636e\u7684\u5b57\u6bb5\uff0c\u5fc5\u9700").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("dimension\uff1a").append(DataType.toString((int)0)).append("\uff1b\u9694\u79bb\u7ef4\u5ea6\uff0c\u53ef\u9009\u53c2\u6570\uff0c\u7528\u4e8e\u8986\u76d6\u6216\u65b0\u589e\u9694\u79bb\u7ef4\u5ea6\u6570\u636e\uff0c\u9694\u79bb\u7ef4\u5ea6\u4e0e\u9694\u79bb\u7ef4\u5ea6\u503c\u5fc5\u987b\u6210\u5bf9\u51fa\u73b0\uff0c\u53c2\u6570\u4e2a\u6570\u4e0d\u9650").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u8868table\u4e2d\u7f16\u7801\u4e3acode\u7684\uff0c\u9694\u79bb\u7ef4\u5ea6\u4e3adimension\u7684\u6570\u636e\uff0c\u4ee5\u5206\u9694\u7b26separator\u5206\u5272\u7684\uff0c\u5bf9\u5e94\u5b57\u6bb5fieldName\u7684\u5168\u8def\u5f84\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u67e5\u627e\u7ec4\u7ec7\u673a\u6784\u4e3a001\u7f16\u7801\u4e3a0101\u7684\u90e8\u95e8\u7684\u5168\u8def\u5f84\uff0c\u6307\u5b9a\u8fd4\u56de\u90e8\u95e8\u540d\u79f0\uff0c\u4ee5-\u5206\u9694").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetBaseDataWholePath(\"0021\", \"MD_DEPARTMENT\", \"-\", \"name\", \"UNITCODE\", \"001\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u90e8\u95e801-\u90e8\u95e80101");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8868table\u4e2d\u7f16\u7801\u4e3acode\u7684\uff0c\u9694\u79bb\u7ef4\u5ea6\u4e3adimension\u7684\u6570\u636e\uff0c\u4ee5\u5206\u9694\u7b26separator\u5206\u5272\u7684\uff0c\u5bf9\u5e94\u5b57\u6bb5fieldName\u7684\u5168\u8def\u5f84\u503c");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("code");
        parameterDescription.setType(DataType.toString((int)6));
        parameterDescription.setDescription("\u57fa\u7840\u6570\u636ecode\uff0c\u5fc5\u9700");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("table");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription(" \u57fa\u7840\u6570\u636e\u8868\u540d\uff0c\u5fc5\u9700");
        parameterDescription1.setRequired(true);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("separator");
        parameterDescription2.setType(DataType.toString((int)6));
        parameterDescription2.setDescription(" \u5206\u9694\u7b26\uff0c\u5fc5\u9700");
        parameterDescription2.setRequired(true);
        ParameterDescription parameterDescription3 = new ParameterDescription();
        parameterDescription3.setName("fieldName");
        parameterDescription3.setType(DataType.toString((int)6));
        parameterDescription3.setDescription(" \u6307\u5b9a\u8fd4\u56de\u57fa\u7840\u6570\u636e\u7684\u5b57\u6bb5\uff0c\u5fc5\u9700");
        parameterDescription3.setRequired(true);
        ParameterDescription parameterDescription4 = new ParameterDescription();
        parameterDescription4.setName("dimension");
        parameterDescription4.setType(DataType.toString((int)0));
        parameterDescription4.setDescription(" \u9694\u79bb\u7ef4\u5ea6\uff0c\u53ef\u9009\u53c2\u6570\uff0c\u7528\u4e8e\u8986\u76d6\u6216\u65b0\u589e\u9694\u79bb\u7ef4\u5ea6\u6570\u636e\uff0c\u9694\u79bb\u7ef4\u5ea6\u4e0e\u9694\u79bb\u7ef4\u5ea6\u503c\u5fc5\u987b\u6210\u5bf9\u51fa\u73b0\uff0c\u53c2\u6570\u4e2a\u6570\u4e0d\u9650");
        parameterDescription4.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        parameterDescriptions.add(parameterDescription3);
        parameterDescriptions.add(parameterDescription4);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u67e5\u627e\u7ec4\u7ec7\u673a\u6784\u4e3a001\u7f16\u7801\u4e3a0101\u7684\u90e8\u95e8\u7684\u5168\u8def\u5f84\uff0c\u6307\u5b9a\u8fd4\u56de\u90e8\u95e8\u540d\u79f0\uff0c\u4ee5-\u5206\u9694");
        formulaExample.setFormula("GetBaseDataWholePath(\"0021\", \"MD_DEPARTMENT\", \"-\", \"name\", \"UNITCODE\", \"001\")");
        formulaExample.setReturnValue("\u90e8\u95e801-\u90e8\u95e80101");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

