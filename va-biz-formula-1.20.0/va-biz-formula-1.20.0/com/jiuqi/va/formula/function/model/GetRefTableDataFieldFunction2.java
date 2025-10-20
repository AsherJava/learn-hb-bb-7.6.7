/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.domain.Utils;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.formula.provider.FilterNodeProvider;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class GetRefTableDataFieldFunction2
extends ModelFunction {
    private static final long serialVersionUID = 3276785022057417493L;

    public GetRefTableDataFieldFunction2() {
        this.parameters().add(new Parameter("value", 0, "\u53c2\u6570\u503c", false));
        this.parameters().add(new Parameter("mdValue", 0, "\u5f15\u7528\u6570\u636e\u67d0\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("dimension", 0, "\u9694\u79bb\u7ef4\u5ea6", true));
    }

    public String name() {
        return "GetRefTableDataField2";
    }

    public String title() {
        return "\u83b7\u53d6\u5f15\u7528\u57fa\u7840\u6570\u636e\u67d0\u5b57\u6bb5\u7684\u503c\uff08\u7ed3\u679c\u4e0d\u53bb\u91cd\uff09";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IFormulaContext cxt = (IFormulaContext)context;
        IASTNode node0 = parameters.get(0);
        TableFieldNode node1 = (TableFieldNode)parameters.get(1);
        String tableName = node1.getTableName();
        String fieldName = node1.getFieldName().toLowerCase();
        Object node0Data = node0.evaluate(context);
        int node0DataType = node0.getType(context);
        int node1DataType = parameters.get(1).getType(context);
        int refTableType = cxt.getRefTableType(tableName);
        if (node0Data == null) {
            return cxt.valueOf(null, node1DataType);
        }
        Map<String, Object> dimValues = this.buildDimValues(context, parameters, cxt);
        if (node0DataType == 6 || node0Data instanceof String) {
            Object findRefFieldValue = cxt.findRefFieldValue(refTableType, tableName, (String)node0Data, fieldName, dimValues);
            if (findRefFieldValue instanceof List) {
                return this.convertModelData(cxt, (List)findRefFieldValue, node1DataType);
            }
            if (findRefFieldValue != null) {
                if (2 == node1DataType && findRefFieldValue instanceof String) {
                    findRefFieldValue = Utils.parseDateTime(findRefFieldValue.toString());
                }
                return cxt.valueOf(findRefFieldValue, node1DataType);
            }
        } else if (node0DataType == 11 || node0Data instanceof ArrayData) {
            ArrayData arrayData = (ArrayData)node0Data;
            ArrayList returnValue = new ArrayList();
            arrayData.forEach(param -> {
                Object value = cxt.findRefFieldValue(refTableType, tableName, (String)param, fieldName, dimValues);
                ArrayList<Object> values = null;
                if (value instanceof List) {
                    values = (ArrayList<Object>)value;
                } else if (value != null) {
                    values = new ArrayList<Object>();
                    values.add(value);
                }
                if (values != null) {
                    values.forEach(o -> returnValue.add(cxt.valueOf(o, node1DataType)));
                }
            });
            return this.convertModelData(cxt, Arrays.asList(returnValue.toArray()), node1DataType);
        }
        return cxt.valueOf(null, node1DataType);
    }

    private Map<String, Object> buildDimValues(IContext context, List<IASTNode> parameters, IFormulaContext cxt) throws SyntaxException {
        int size = parameters.size();
        Map<String, Object> dimValues = cxt.getDimValues();
        if (parameters.size() > 2) {
            ArrayList<Object> values = new ArrayList<Object>();
            for (int i = 2; i < size; ++i) {
                IASTNode nodei = parameters.get(i);
                Object nodeiData = nodei.evaluate(context);
                if ((i & 1) == 0) {
                    if (ObjectUtils.isEmpty(nodeiData)) {
                        throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.isolation.key.not.empty"));
                    }
                    values.add(String.valueOf(nodeiData).toUpperCase());
                    continue;
                }
                values.add(nodeiData);
            }
            dimValues = new HashMap<String, Object>(dimValues);
            dimValues.putAll(Utils.makeMap(values.toArray()));
        }
        return dimValues;
    }

    private Object convertModelData(IFormulaContext cxt, List<Object> results, int node1DataType) {
        if (results.size() == 0) {
            return cxt.valueOf(null, node1DataType);
        }
        switch (node1DataType) {
            case 3: 
            case 10: {
                BigDecimal result = results.stream().map(o -> new BigDecimal(o.toString())).reduce(BigDecimal.ZERO, BigDecimal::add);
                return cxt.valueOf(result, node1DataType);
            }
            case 1: 
            case 6: 
            case 33: {
                return cxt.valueOf(results, node1DataType);
            }
        }
        return cxt.valueOf(null, node1DataType);
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u5f15\u7528\u57fa\u7840\u6570\u636e\u67d0\u5b57\u6bb5\u7684\u503c";
    }

    @Override
    public void toFilter(IContext context, List<IASTNode> params, StringBuilder buffer, String functionName, Object info) throws ToFilterException {
        buffer.append(this.name()).append('(');
        boolean flag = false;
        for (int i = 0; i < params.size(); ++i) {
            IASTNode p = params.get(i);
            if (flag) {
                buffer.append(',');
            } else {
                flag = true;
            }
            if (i == 1) {
                p.toString(buffer);
                continue;
            }
            FilterNodeProvider.get(p.getNodeType()).toFilter(context, p, buffer, info);
        }
        buffer.append(')');
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
        buffer.append("    ").append("value").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u53c2\u6570\u503c\uff0c\u7b2c\u4e00\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b,\u5fc5\u987b\u662f\u80fd\u591f\u5173\u8054\u5f15\u7528\u6570\u636e\u7684ID\u6216\u8005CODE\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("mdValue").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5f15\u7528\u6570\u636e\u67d0\u5b57\u6bb5\uff0c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u8868\u5fc5\u987b\u6709\u5bf9\u5e94\u7684\u5f15\u7528\u6570\u636e\u4fe1\u606f").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("dimension").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u53ef\u9009\u53c2\u6570\uff0c\u7528\u4e8e\u8986\u76d6\u6216\u65b0\u589e\u9694\u79bb\u7ef4\u5ea6\u6570\u636e\uff0c\u9694\u79bb\u7ef4\u5ea6\u4e0e\u9694\u79bb\u7ef4\u5ea6\u503c\u5fc5\u987b\u6210\u5bf9\u51fa\u73b0\uff0c\u53c2\u6570\u4e2a\u6570\u4e0d\u9650").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8be5\u51fd\u6570\u7684\u8fd4\u56de\u503c\u7c7b\u578b\u4e0e\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u5b57\u6bb5\u7684\u7c7b\u578b\u4fdd\u6301\u4e00\u81f4\u3002\u5f53\u7b2c\u4e00\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u4e3a\u5f15\u7528\u6570\u636e\u591a\u9009\u5b57\u6bb5\u65f6\uff0c").append("\u5982\u679c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b\u4e3aString\uff0cUUID\uff0cBoolean\u7c7b\u578b\uff0c\u5219\u8fd4\u56de\u503c\u4e3a\u7b2c\u4e00\u4e2a\u53c2\u6570\u9009\u62e9\u7684\u6bcf\u4e2a\u5f15\u7528\u6570\u636e\u5bf9\u5e94\u53c2\u6570\u4e8c\u5b57\u6bb5\u7684ArrayData\u3002").append("\u5982\u679c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b\u4e3a\u6574\u578b\u3001\u6570\u503c\u578b\uff0c\u5219\u8fd4\u56de\u503c\u4e3a\u7b2c\u4e00\u4e2a\u53c2\u6570\u9009\u62e9\u7684\u6bcf\u4e2a\u5f15\u7528\u6570\u636e\u5bf9\u5e94\u53c2\u6570\u4e8c\u5b57\u6bb5\u7684\u5408\u8ba1\u3002").append("\u5982\u679c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b\u4e3a\u4ee5\u4e0a\u6240\u5217\u7684\u5176\u4ed6\u7c7b\u578b\uff0c\u5219\u76f4\u63a5\u8fd4\u56deNULL.").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b1\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6FO_APPLOANBILL\u8868\u4e2dDEPTCODE\u5b57\u6bb5\u5173\u8054\u7684\u5f15\u7528\u6570\u636e\u8868MD_DEPARTMENT\u7684MANAGER\u5b57\u6bb5\u3002 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetRefTableDataField(FO_APPLOANBILL[DEPTCODE],MD_DEPARTMENT[MANAGER])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append(DataType.toString((int)0));
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b2\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u5f53\u524d\u51ed\u8bc1\u8d26\u7c3f\u4e0b\u4ee3\u7801\u4e3a1001\u7684\u79d1\u76ee\uff08\u79d1\u76ee\u57fa\u7840\u6570\u636e\u7684\u9694\u79bb\u7ef4\u5ea6\u662f\u7ec4\u7ec7\u673a\u6784\u548c\u8d26\u7c3f\uff09\u3002 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetRefTableDataField(\"1001\",MD_ACCTSUBJECT[OBJECTCODE],\"BOOKCODE\",FO_EXPENSEBILL[BOOKCODE])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("1001||ZZ01||ZB01");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8be5\u51fd\u6570\u7684\u8fd4\u56de\u503c\u7c7b\u578b\u4e0e\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u5b57\u6bb5\u7684\u7c7b\u578b\u4fdd\u6301\u4e00\u81f4\u3002\u5f53\u7b2c\u4e00\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u4e3a\u5f15\u7528\u6570\u636e\u591a\u9009\u5b57\u6bb5\u65f6\uff0c\u5982\u679c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b\u4e3aString\uff0cUUID\uff0cBoolean\u7c7b\u578b\uff0c\u5219\u8fd4\u56de\u503c\u4e3a\u7b2c\u4e00\u4e2a\u53c2\u6570\u9009\u62e9\u7684\u6bcf\u4e2a\u5f15\u7528\u6570\u636e\u5bf9\u5e94\u53c2\u6570\u4e8c\u5b57\u6bb5\u7684ArrayData\u3002\u5982\u679c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b\u4e3a\u6574\u578b\u3001\u6570\u503c\u578b\uff0c\u5219\u8fd4\u56de\u503c\u4e3a\u7b2c\u4e00\u4e2a\u53c2\u6570\u9009\u62e9\u7684\u6bcf\u4e2a\u5f15\u7528\u6570\u636e\u5bf9\u5e94\u53c2\u6570\u4e8c\u5b57\u6bb5\u7684\u5408\u8ba1\u3002\u5982\u679c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b\u4e3a\u4ee5\u4e0a\u6240\u5217\u7684\u5176\u4ed6\u7c7b\u578b\uff0c\u5219\u76f4\u63a5\u8fd4\u56deNULL\u3002");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("value");
        parameterDescription.setType(DataType.toString((int)6));
        parameterDescription.setDescription("\u53c2\u6570\u503c\uff0c\u7b2c\u4e00\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u5b57\u6bb5\u7c7b\u578b,\u5fc5\u987b\u662f\u80fd\u591f\u5173\u8054\u5f15\u7528\u6570\u636e\u7684ID\u6216\u8005CODE\u5b57\u6bb5");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("mdValue");
        parameterDescription1.setType(DataType.toString((int)0));
        parameterDescription1.setDescription("\u5f15\u7528\u6570\u636e\u67d0\u5b57\u6bb5\uff0c\u7b2c\u4e8c\u4e2a\u53c2\u6570\u5bf9\u5e94\u7684\u8868\u5fc5\u987b\u6709\u5bf9\u5e94\u7684\u5f15\u7528\u6570\u636e\u4fe1\u606f");
        parameterDescription1.setRequired(true);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("dimension");
        parameterDescription2.setType(DataType.toString((int)0));
        parameterDescription2.setDescription("\u53ef\u9009\u53c2\u6570\uff0c\u7528\u4e8e\u8986\u76d6\u6216\u65b0\u589e\u9694\u79bb\u7ef4\u5ea6\u6570\u636e\uff0c\u9694\u79bb\u7ef4\u5ea6\u4e0e\u9694\u79bb\u7ef4\u5ea6\u503c\u5fc5\u987b\u6210\u5bf9\u51fa\u73b0\uff0c\u53c2\u6570\u4e2a\u6570\u4e0d\u9650");
        parameterDescription2.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6FO_APPLOANBILL\u8868\u4e2dDEPTCODE\u5b57\u6bb5\u5173\u8054\u7684\u5f15\u7528\u6570\u636e\u8868MD_DEPARTMENT\u7684MANAGER\u5b57\u6bb5\u3002 ");
        formulaExample.setFormula("GetRefTableDataField(FO_APPLOANBILL[DEPTCODE],MD_DEPARTMENT[MANAGER])");
        formulaExample.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0));
        FormulaExample formulaExample1 = new FormulaExample();
        formulaExample1.setScenario("\u83b7\u53d6\u5f53\u524d\u51ed\u8bc1\u8d26\u7c3f\u4e0b\u4ee3\u7801\u4e3a1001\u7684\u79d1\u76ee\uff08\u79d1\u76ee\u57fa\u7840\u6570\u636e\u7684\u9694\u79bb\u7ef4\u5ea6\u662f\u7ec4\u7ec7\u673a\u6784\u548c\u8d26\u7c3f\uff09\u3002");
        formulaExample1.setFormula("GetRefTableDataField(\"1001\",MD_ACCTSUBJECT[OBJECTCODE],\"BOOKCODE\",FO_EXPENSEBILL[BOOKCODE])");
        formulaExample1.setReturnValue("1001||ZZ01||ZB01");
        formulaExamples.add(formulaExample);
        formulaExamples.add(formulaExample1);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    @Override
    protected void printParamDeclaration(StringBuilder buffer) {
        boolean flag = false;
        for (IParameter p : this.parameters()) {
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(DataType.toExpression((int)p.dataType())).append(' ').append(p.name());
        }
        if (this.isInfiniteParameter() && !this.parameters().isEmpty()) {
            buffer.append(", ...");
        }
    }

    @Override
    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        try {
            IFormulaContext cxt = (IFormulaContext)context;
            IASTNode node0 = parameters.get(0);
            TableFieldNode node1 = (TableFieldNode)parameters.get(1);
            String tableName = node1.getTableName();
            String fieldName = node1.getFieldName().toLowerCase();
            Object node0Data = node0.evaluate(context);
            int node0DataType = node0.getType(context);
            int node1DataType = parameters.get(1).getType(context);
            int refTableType = cxt.getRefTableType(tableName);
            if (node0Data == null) {
                buffer.append("null");
            }
            Map<String, Object> dimValues = this.buildDimValues(context, parameters, cxt);
            if (node0DataType == 6 || node0Data instanceof String) {
                Object findRefFieldValue = cxt.findRefFieldValue(refTableType, tableName, (String)node0Data, fieldName, dimValues);
                if (findRefFieldValue instanceof List) {
                    List values = (List)findRefFieldValue;
                    ArrayList newvalues = new ArrayList();
                    values.stream().forEach(o -> newvalues.add("'" + o + "'"));
                    buffer.append(" (").append(StringUtils.collectionToCommaDelimitedString(newvalues)).append(")");
                } else if (findRefFieldValue != null) {
                    String[] valueArray;
                    ArrayList<String> values = new ArrayList<String>();
                    for (String str : valueArray = findRefFieldValue.toString().split(",")) {
                        str = "'" + str + "'";
                        values.add(str);
                    }
                    if (values.size() <= 1) {
                        buffer.append(values.get(0));
                    } else {
                        buffer.append(" (").append(StringUtils.collectionToCommaDelimitedString(values)).append(")");
                    }
                }
            } else if (node0DataType == 11 || node0Data instanceof ArrayData) {
                ArrayData arrayData = (ArrayData)node0Data;
                HashSet returnValue = new HashSet();
                arrayData.forEach(param -> {
                    Object value = cxt.findRefFieldValue(refTableType, tableName, (String)param, fieldName, dimValues);
                    ArrayList<String> values = null;
                    if (value instanceof List) {
                        values = (ArrayList<String>)value;
                    } else if (value != null) {
                        String[] valueArray = value.toString().split(",");
                        values = new ArrayList<String>(Arrays.asList(valueArray));
                    }
                    if (values != null) {
                        values.forEach(o -> returnValue.add("'" + cxt.valueOf(o, node1DataType) + "'"));
                    }
                });
                buffer.append(" (").append(StringUtils.collectionToCommaDelimitedString(returnValue)).append(")");
            }
        }
        catch (SyntaxException e) {
            throw new InterpretException(String.format("GetRefTableDataField\u516c\u5f0f\u8f6csql\u5f02\u5e38\uff1a%s", e.getMessage()), (Throwable)e);
        }
    }
}

