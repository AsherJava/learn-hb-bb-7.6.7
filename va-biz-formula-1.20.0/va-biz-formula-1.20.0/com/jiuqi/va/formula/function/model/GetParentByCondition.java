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
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryParentType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  javax.annotation.Resource
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class GetParentByCondition
extends ModelFunction {
    private static final long serialVersionUID = 6345272061511863612L;
    @Resource
    private BaseDataClient baseDataClient;
    @Resource
    private BaseDataDefineClient baseDataDefineClient;

    public GetParentByCondition() {
        this.parameters().add(new Parameter("table", 6, "\u57fa\u7840\u6570\u636e\u8868\u540d", false));
        this.parameters().add(new Parameter("value", 0, "\u57fa\u7840\u6570\u636e\u503c", false));
        this.parameters().add(new Parameter("conditionField", 6, "\u6761\u4ef6\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("conditionValue", 6, "\u6761\u4ef6\u503c", false));
        this.parameters().add(new Parameter("auth", 1, "\u6743\u9650\u63a7\u5236", true));
    }

    @Override
    public String addDescribe() {
        return "\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u57fa\u7840\u6570\u636e\u4e0a\u7ea7\uff08\u6216\u672c\u8eab\uff09\u3002\u83b7\u53d6\u987a\u5e8f\u4e3a\u672c\u8eab\u3001\u4e0a\u7ea7\u3001\u4e0a\u7ea7\u7684\u4e0a\u7ea7...\u76f4\u5230\u627e\u5230\u6ee1\u8db3\u6761\u4ef6\u7684\u57fa\u7840\u6570\u636e\u9879\u3002";
    }

    public String name() {
        return "GetParentByCondition";
    }

    public String title() {
        return "\u6839\u636e\u6761\u4ef6\u83b7\u53d6\u57fa\u7840\u6570\u636e\u4e0a\u7ea7\uff08\u6216\u672c\u8eab\uff09";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        IASTNode mdTableNode = list.get(0);
        IASTNode mdValueNode = list.get(1);
        IASTNode conditionFieldNode = list.get(2);
        IASTNode conditionValueNode = list.get(3);
        Object mdValueObj = mdValueNode.evaluate(iContext);
        if (ObjectUtils.isEmpty(mdValueObj)) {
            return null;
        }
        String value = (String)mdValueObj;
        Object tableNameObj = mdTableNode.evaluate(iContext);
        String tableName = (String)tableNameObj;
        Object conditionFieldNodeObj = conditionFieldNode.evaluate(iContext);
        String conditionFieldCode = (String)conditionFieldNodeObj;
        Object conditionValueObj = conditionValueNode.evaluate(iContext);
        Boolean authNodeBool = true;
        if (list.size() == this.parameters().size()) {
            IASTNode authNode = list.get(4);
            Object authNodeObj = authNode.evaluate(iContext);
            authNodeBool = (Boolean)authNodeObj;
        }
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setPagination(Boolean.valueOf(false));
        if (value.contains("||")) {
            baseDataDTO.setObjectcode(value);
        } else {
            baseDataDTO.setCode(value);
        }
        if (!authNodeBool.booleanValue()) {
            baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        } else {
            baseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
        }
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO mdPageVo = this.baseDataClient.list(baseDataDTO);
        if (mdPageVo == null || mdPageVo.getRows() == null || mdPageVo.getRows().size() != 1) {
            return null;
        }
        List rows = mdPageVo.getRows();
        BaseDataDO curBaseData = (BaseDataDO)mdPageVo.getRows().get(0);
        baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setPagination(Boolean.valueOf(false));
        if (value.contains("||")) {
            baseDataDTO.setObjectcode(value);
        } else {
            baseDataDTO.setCode(value);
        }
        baseDataDTO.setUnitcode(curBaseData.getUnitcode());
        if (!authNodeBool.booleanValue()) {
            baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        } else {
            baseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
        }
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        baseDataDTO.setQueryParentType(BaseDataOption.QueryParentType.ALL_PARENT);
        mdPageVo = this.baseDataClient.list(baseDataDTO);
        if (mdPageVo == null || mdPageVo.getRows() == null) {
            return null;
        }
        rows.addAll(mdPageVo.getRows());
        BaseDataDO baseDataDO = rows.stream().filter(o -> this.match((BaseDataDO)o, conditionFieldCode, conditionValueObj)).findFirst().orElse(null);
        return baseDataDO == null ? null : baseDataDO.getObjectcode();
    }

    private boolean match(BaseDataDO baseDataDO, String conditionField, Object conditionValueObj) {
        if ("true".equals(conditionValueObj.toString().toLowerCase())) {
            conditionValueObj = 1;
        } else if ("false".equals(conditionValueObj.toString().toLowerCase())) {
            conditionValueObj = 0;
        }
        Object conditionValue = baseDataDO.get((Object)conditionField.toLowerCase());
        return conditionValue instanceof BigDecimal ? ((BigDecimal)conditionValue).intValue() == Integer.valueOf(conditionValueObj.toString()).intValue() : conditionValue != null && conditionValue.equals(conditionValueObj);
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
        buffer.append("    ").append("table\uff1a").append(DataType.toString((int)6)).append("\uff1b \u57fa\u7840\u6570\u636e\u8868\u540d\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("value\uff1a").append(DataType.toString((int)0)).append("\uff1b \u57fa\u7840\u6570\u636e\u7684ObjectCode\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("conditionField\uff1a").append(DataType.toString((int)6)).append("\uff1b \u6761\u4ef6\u5b57\u6bb5\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("conditionValue\uff1a").append(DataType.toString((int)6)).append("\uff1b \u6761\u4ef6\u503c\uff0c\u5fc5\u9700\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("auth\uff1a").append(DataType.toString((int)1)).append("\uff1b \u662f\u5426\u6743\u9650\u63a7\u5236\uff0c\u53ef\u9009\uff0c\u9ed8\u8ba4true\uff0c\u8868\u793a\u542f\u7528\u6743\u9650\u63a7\u5236\uff1bfalse\u4ee3\u8868\u5ffd\u7565\u6743\u9650\u67e5\u627e\u4e0a\u7ea7\uff0c\u53ef\u914d\u5408\u5b57\u6bb5\u5c5e\u6027\u4e2d\u7684\u5ffd\u7565\u6743\u9650\u4e00\u8d77\u4f7f\u7528").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u90e8\u95e8\u7684REALDEPT\u5b57\u6bb5\u4e3atrue\u7684\u4e0a\u7ea7\u90e8\u95e8").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetParentByCondition(\"MD_DEPARTMENT\", FO_APPLOANBILL[DEPTCODE], \"REALDEPT\", \"true\", true)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("DEPT00||A0001");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("table");
        parameterDescription.setType(DataType.toString((int)6));
        parameterDescription.setDescription("\u57fa\u7840\u6570\u636e\u8868\u540d");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("value");
        parameterDescription1.setType(DataType.toString((int)0));
        parameterDescription1.setDescription("\u57fa\u7840\u6570\u636e\u7684ObjectCode");
        parameterDescription1.setRequired(true);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("conditionField");
        parameterDescription2.setType(DataType.toString((int)6));
        parameterDescription2.setDescription("\u6761\u4ef6\u5b57\u6bb5");
        parameterDescription2.setRequired(true);
        ParameterDescription parameterDescription3 = new ParameterDescription();
        parameterDescription3.setName("conditionValue");
        parameterDescription3.setType(DataType.toString((int)6));
        parameterDescription3.setDescription("\u6761\u4ef6\u503c");
        parameterDescription3.setRequired(true);
        ParameterDescription parameterDescription4 = new ParameterDescription();
        parameterDescription4.setName("auth");
        parameterDescription4.setType(DataType.toString((int)1));
        parameterDescription4.setDescription("\u662f\u5426\u6743\u9650\u63a7\u5236\uff0c\u9ed8\u8ba4\u4e3atrue\uff0c\u8868\u793a\u542f\u7528\u6743\u9650\u63a7\u5236\uff1bfalse\u8868\u793a\u5ffd\u7565\u6743\u9650\uff0c\u7ed3\u5408\u5b57\u6bb5\u5c5e\u6027\u4e2d\u7684\u5ffd\u7565\u6743\u9650\u4e00\u8d77\u4f7f\u7528");
        parameterDescription4.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        parameterDescriptions.add(parameterDescription3);
        parameterDescriptions.add(parameterDescription4);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6\u90e8\u95e8\u7684REALDEPT\u5b57\u6bb5\u4e3atrue\u7684\u4e0a\u7ea7\u90e8\u95e8\u3002");
        formulaExample.setFormula("GetParentByCondition(\"MD_DEPARTMENT\", FO_APPLOANBILL[DEPTCODE], \"REALDEPT\", \"true\", true)");
        formulaExample.setReturnValue("DEPT00||A0001");
        formulaExample.setDefinition("\u83b7\u53d6\u90e8\u95e8\u57fa\u7840\u6570\u636e\uff08MD_DEPARTMENT\uff09\u4e2d\uff0c\u6570\u636e\u9879objectcode\u4e3a\u7533\u8bf7\u5355\uff08FO_APPLOANBILL\uff09\u7684\u90e8\u95e8\uff08DEPTCODE\uff09\uff0c\u4e14\u662f\u5426\u90e8\u95e8\uff08REALDEPT\uff09\u4e3atrue\u7684\u6761\u76ee\u6216\u5176\u4e0a\u7ea7\u6761\u76ee\uff0c\u54cd\u5e94\u6743\u9650\u63a7\u5236\u3002");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    private static class BoolString {
        private static final String TRUE = "true";
        private static final String FALSE = "false";

        private BoolString() {
        }
    }
}

