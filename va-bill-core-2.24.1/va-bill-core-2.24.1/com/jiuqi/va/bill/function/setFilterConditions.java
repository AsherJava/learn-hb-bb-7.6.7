/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.formula.common.exception.ToFilterException
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class setFilterConditions
extends ModelFunction {
    @Autowired
    private BaseDataClient baseDataClient;
    private static final long serialVersionUID = 1L;

    public setFilterConditions() {
        this.parameters().add(new Parameter("value", 0, "\u9700\u8981\u8fdb\u884c\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u7684\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("condition1", 6, "\u8fc7\u6ee4\u6761\u4ef6\u96c6\u5408", false));
        this.parameters().add(new Parameter("condition2", 6, "\u5404\u6761\u4ef6\u95f4\u7684\u5173\u7cfb", false));
    }

    public String addDescribe() {
        return "\u6839\u636e\u591a\u4e2a\u6761\u4ef6\u8fc7\u6ee4\u51fa\u7b26\u5408\u6761\u4ef6\u7684\u57fa\u7840\u6570\u636e\u3010\u5e9f\u5f03\u3011";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String name() {
        return "setFilterConditions";
    }

    public String title() {
        return "\u591a\u6761\u4ef6\u8fc7\u6ee4\u57fa\u7840\u6570\u636e\u3010\u5e9f\u5f03\u3011";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        String code = (String)cxt.get("CODE");
        BaseDataDTO param = new BaseDataDTO();
        String conditionStr = (String)parameters.get(1).evaluate(context);
        String tableName = conditionStr.split("&")[0].split("\\[")[0];
        try {
            param.setTableName(tableName);
            param.setExpression(this.buildBformula(cxt, this.getBasedataFormula(context, parameters)));
            param.setCode(code);
            param.put("vaBizFormula", (Object)true);
            PageVO res = this.baseDataClient.list(param);
            return res.getTotal() > 0;
        }
        catch (ToFilterException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildBformula(ModelDataContext context, String basedataFormula) {
        if (!StringUtils.hasText(basedataFormula) || !basedataFormula.contains("this.GetText")) {
            return basedataFormula;
        }
        String[] nodes = basedataFormula.split(" \\+ ");
        if (nodes.length == 1) {
            return basedataFormula;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < nodes.length; ++i) {
            String node = nodes[i];
            if (!node.contains("this.GetText")) {
                res.append(node);
                continue;
            }
            String[] letNodes = node.split("\"");
            DataRowImpl row = (DataRowImpl)context.getParams().get(letNodes[1]);
            nodes[i] = row.getString(letNodes[3]);
            nodes[i] = nodes[i] == null ? "" : nodes[i];
            res.append(nodes[i]);
        }
        return res.toString().replace("\\", "");
    }

    public void toFilter(IContext context, List<IASTNode> parameters, StringBuilder filterBuffer, String functionName, Object info) throws ToFilterException {
        filterBuffer.append(this.getBasedataFormula(context, parameters));
    }

    String getBasedataFormula(IContext context, List<IASTNode> parameters) throws ToFilterException {
        StringBuffer buffer = new StringBuffer();
        buffer.delete(0, buffer.length());
        try {
            String conditionStr = (String)parameters.get(1).evaluate(context);
            String logicType = (String)parameters.get(2).evaluate(context);
            if (ObjectUtils.isEmpty(logicType)) {
                throw new ToFilterException("\u7f3a\u5c11\u903b\u8f91\u5173\u952e\u5b57condition2");
            }
            if (!logicType.equalsIgnoreCase("AND") && !logicType.equalsIgnoreCase("OR")) {
                throw new ToFilterException("\u975e\u6cd5\u7684\u903b\u8f91\u5173\u952e\u5b57[" + logicType + "]");
            }
            String[] conditions = conditionStr.split("&");
            ArrayList<String[]> list = new ArrayList<String[]>();
            for (int i = 0; i < conditions.length; ++i) {
                list.add(conditions[i].split("#"));
            }
            String blank = " ";
            for (String[] c : list) {
                String vfield;
                String vtable;
                String field = "[" + c[0].split("\\[")[1];
                String key = c[1];
                String[] vals = c[2].split("@");
                if (key.equalsIgnoreCase("IN") || key.equalsIgnoreCase("NOTIN")) {
                    if (key.equalsIgnoreCase("NOTIN")) {
                        buffer.append("NOT (");
                    }
                    buffer.append(field).append(blank);
                    buffer.append("IN").append(blank);
                    if (vals.length > 1) {
                        buffer.append("ToArray");
                    }
                    buffer.append("(");
                    for (String v : vals) {
                        if (v.contains("[")) {
                            vtable = v.split("\\[")[0];
                            vfield = v.split("\\[")[1].split("\\]")[0];
                            buffer.append("\" + this.GetText(\"" + vtable + "\", \"" + vfield + "\") + \"");
                        } else {
                            buffer.append("'");
                            buffer.append(v);
                            buffer.append("'");
                        }
                        buffer.append(",");
                    }
                    buffer.deleteCharAt(buffer.length() - 1);
                    buffer.append(")");
                    if (key.equalsIgnoreCase("NOTIN")) {
                        buffer.append(")");
                    }
                } else if (key.equalsIgnoreCase("STARTWITH") || key.equalsIgnoreCase("NOTSTARTWITH")) {
                    for (String v : vals) {
                        if (vals.length > 1) {
                            buffer.append("(");
                        }
                        if (key.equalsIgnoreCase("NOTSTARTWITH")) {
                            buffer.append("NOT (");
                        }
                        buffer.append(field).append(blank);
                        buffer.append("LIKE").append(blank);
                        if (v.contains("[")) {
                            buffer.append("(");
                            vtable = v.split("\\[")[0];
                            vfield = v.split("\\[")[1].split("\\]")[0];
                            buffer.append("\" + this.GetText(\"" + vtable + "\", \"" + vfield + "\") + \"");
                            buffer.append("+\\\"%\\\")");
                        } else {
                            buffer.append("'");
                            buffer.append(v);
                            buffer.append("%'");
                        }
                        if (key.equalsIgnoreCase("NOTSTARTWITH")) {
                            buffer.append(")");
                        }
                        if (vals.length > 1) {
                            buffer.append(")");
                        }
                        buffer.append(blank).append("or").append(blank);
                    }
                    buffer.delete(buffer.length() - 4, buffer.length());
                } else {
                    throw new ToFilterException("\u975e\u6cd5\u7684\u6761\u4ef6\u5173\u952e\u5b57[" + key + "]");
                }
                buffer.append(blank);
                buffer.append(logicType.toLowerCase()).append(blank);
            }
            buffer.delete(buffer.length() - logicType.length() - 2, buffer.length());
        }
        catch (Exception e) {
            throw new ToFilterException(e.getMessage());
        }
        return buffer.toString();
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("value\uff1a").append(DataType.toString((int)0)).append("\uff1b \u8868\u793a\u5355\u636e\u4e3b\u8868\u6216\u8005\u5b50\u8868\u4e2d\u9700\u8981\u8fdb\u884c\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u7684\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("condition1\uff1a").append(DataType.toString((int)6)).append("\uff1b \u8fc7\u6ee4\u6761\u4ef6\u96c6\u5408\uff0c\u591a\u4e2a\u8fc7\u6ee4\u6761\u4ef6\u7528&\u53f7\u5206\u9694\uff0c\u6bcf\u4e2a\u8fc7\u6ee4\u6761\u4ef6\u7684\u683c\u5f0f\u4e3a\uff1a\u57fa\u7840\u6570\u636e\u5b57\u6bb5#\u64cd\u4f5c\u7b26#\u8fc7\u6ee4\u503c\u3002\u64cd\u4f5c\u7b26\u53ea\u80fd\u5728\u4ee5\u4e0b\u7b26\u53f7\u4e2d\u9009\u53d6\uff08IN,NOTIN,STARTWITH,NOTSTARTWITH\uff09,\u4e14\u53ea\u80fd\u4e3a\u4e00\u4e2a\u3002\u5176\u4e2d\u8fc7\u6ee4\u503c\u53ef\u4ee5\u4e3a\u591a\u4e2a\uff0c\u6bcf\u4e2a\u8fc7\u6ee4\u503c\u4e4b\u95f4\u7528@\u7b26\u53f7\u76f8\u8fde\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("condition2\uff1a").append(DataType.toString((int)6)).append("\uff1b \u8868\u793a\u5404\u6761\u4ef6\u95f4\u7684\u5173\u7cfb\uff0c\u53ea\u80fd\u9009\u62e9AND\u6216\u8005OR\uff0c\u5f53\u9009\u62e9AND\u65f6\uff0c\u8868\u793a\u5fc5\u987b\u6240\u6709\u6761\u4ef6\u90fd\u6ee1\u8db3\uff0c\u9009\u62e9OR\uff0c\u8868\u793a\u53ea\u9700\u8981\u6ee1\u8db3\u4e00\u4e2a\u6761\u4ef6\u5373\u53ef\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u7b26\u5408\u6761\u4ef6\u7684\u57fa\u7840\u6570\u636e\u96c6\u5408").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6TABLE\u8868\u4e2d\u5173\u8054\u4e86\u57fa\u7840\u6570\u636eMD_TABLE\u7684field\u53ea\u80fd\u9009\u62e9MD_TABLE\u4e2d\u7684CODE\u572801\u548c02\u8303\u56f4\u5185\uff0c\u5e76\u4e14NAME\u4e0d\u7b49\u4e8e\u571f\u5730\u7684\u57fa\u7840\u6570\u636e\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SetFilterConditions(TABLE[FIELD], \u201cMD_TABLE[CODE]#IN#01@02&MD_TABLE[NAME]#NOTIN#\u571f\u5730\u201d, \u201cAND\u201d)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u7b26\u5408\u6761\u4ef6\u7684\u57fa\u7840\u6570\u636e\u96c6\u5408");
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0) + "\uff1b\u7b26\u5408\u6761\u4ef6\u7684\u57fa\u7840\u6570\u636e\u96c6\u5408\u3002");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("value", DataType.toString((int)0), "\u8868\u793a\u5355\u636e\u4e3b\u8868\u6216\u8005\u5b50\u8868\u4e2d\u9700\u8981\u8fdb\u884c\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u7684\u5b57\u6bb5", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("condition1", DataType.toString((int)6), " \u8fc7\u6ee4\u6761\u4ef6\u96c6\u5408\uff0c\u591a\u4e2a\u8fc7\u6ee4\u6761\u4ef6\u7528&\u53f7\u5206\u9694\uff0c\u6bcf\u4e2a\u8fc7\u6ee4\u6761\u4ef6\u7684\u683c\u5f0f\u4e3a\uff1a\u57fa\u7840\u6570\u636e\u5b57\u6bb5#\u64cd\u4f5c\u7b26#\u8fc7\u6ee4\u503c\u3002\u64cd\u4f5c\u7b26\u53ea\u80fd\u5728\u4ee5\u4e0b\u7b26\u53f7\u4e2d\u9009\u53d6\uff08IN,NOTIN,STARTWITH,NOTSTARTWITH\uff09,\u4e14\u53ea\u80fd\u4e3a\u4e00\u4e2a\u3002\u5176\u4e2d\u8fc7\u6ee4\u503c\u53ef\u4ee5\u4e3a\u591a\u4e2a\uff0c\u6bcf\u4e2a\u8fc7\u6ee4\u503c\u4e4b\u95f4\u7528@\u7b26\u53f7\u76f8\u8fde\u3002", Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription("condition1", DataType.toString((int)6), " \u8868\u793a\u5404\u6761\u4ef6\u95f4\u7684\u5173\u7cfb\uff0c\u53ea\u80fd\u9009\u62e9AND\u6216\u8005OR\uff0c\u5f53\u9009\u62e9AND\u65f6\uff0c\u8868\u793a\u5fc5\u987b\u6240\u6709\u6761\u4ef6\u90fd\u6ee1\u8db3\uff0c\u9009\u62e9OR\uff0c\u8868\u793a\u53ea\u9700\u8981\u6ee1\u8db3\u4e00\u4e2a\u6761\u4ef6\u5373\u53ef\u3002", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6TABLE\u8868\u4e2d\u5173\u8054\u4e86\u57fa\u7840\u6570\u636eMD_TABLE\u7684field\u53ea\u80fd\u9009\u62e9MD_TABLE\u4e2d\u7684CODE\u572801\u548c02\u8303\u56f4\u5185\uff0c\u5e76\u4e14NAME\u4e0d\u7b49\u4e8e\u571f\u5730\u7684\u57fa\u7840\u6570\u636e\u3002", "SetFilterConditions(TABLE[FIELD], \u201cMD_TABLE[CODE]#IN#01@02&MD_TABLE[NAME]#NOTIN#\u571f\u5730\u201d, \u201cAND\u201d)", "\u7b26\u5408\u6761\u4ef6\u7684\u57fa\u7840\u6570\u636e\u96c6\u5408");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

