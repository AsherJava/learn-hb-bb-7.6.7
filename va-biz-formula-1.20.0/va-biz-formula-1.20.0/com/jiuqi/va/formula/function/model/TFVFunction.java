/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class TFVFunction
extends ModelFunction {
    @Autowired
    private CommonDao dao;
    private static final long serialVersionUID = 1L;

    public TFVFunction() {
        this.parameters().add(new Parameter("Table", 0, "\u8868\u540d", false));
        this.parameters().add(new Parameter("Field", 0, "\u5b57\u6bb5\u540d", false));
        this.parameters().add(new Parameter("Function", 0, "\u51fd\u6570\u540d", true));
        this.parameters().add(new Parameter("Where", 0, "\u6761\u4ef6", true));
        this.parameters().add(new Parameter("Values", 0, "\u53c2\u6570\u503c", true));
    }

    @Override
    public String addDescribe() {
        return "\u8868\u793a\u83b7\u53d6Table\u8868\u7684Field\u5b57\u6bb5\u7684\u503c\uff0c\u6761\u4ef6\u4e3aWhere\uff0c\u8fd4\u56deField\u5b57\u6bb5\u7c7b\u578b";
    }

    public String name() {
        return "TFV";
    }

    public String title() {
        return "TFV\u516c\u5f0f";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public boolean isInfiniteParameter() {
        return true;
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

    public void toDeclaration(StringBuilder buffer) {
        try {
            int retType = this.getResultType(null, null);
            buffer.append(DataType.toExpression((int)retType)).append(' ').append(this.name()).append('(');
        }
        catch (SyntaxException e) {
            throw new SyntaxRuntimeException((Throwable)e);
        }
        this.printParamDeclaration(buffer);
        buffer.append(");");
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
        buffer.append("    ").append("Table").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u8868\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Field").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u5b57\u6bb5\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Function").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u51fd\u6570\uff0c\u586b\u5199\u4ec5\u9650\u4e8e\u5f53\u524d\u6570\u636e\u5e93\u652f\u6301\u7684\u51fd\u6570\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Where").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u6761\u4ef6\uff0c\u8bed\u6cd5\u53c2\u7167\u4e0b\u65b9\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR).append("    ").append("      name = #{param.arg0, jdbcType=VARCHAR}\uff0c\u9488\u5bf9\u53d8\u5316\u5185\u5bb9\uff0carg0\u8868\u793a\u7b2c1\u4e2a\u53c2\u6570\uff0c\u540e\u9762\u4f9d\u6b21\u7c7b\u63a8\uff0c\u4f8b\u5982param.arg1\u8868\u793a\u7b2c\u4e8c\u4e2a\u53c2\u6570\uff0cjdbcType\u540e\u65b9\u4e3a\u53c2\u6570\u7c7b\u578b\uff0cVARCHAR\u8868\u793a\u5b57\u7b26\u4e32\u7c7b\u578b\uff0c\u5e38\u7528\u7c7b\u578b\u4f8b\u5982\uff1aINTEGER\u6574\u6570\uff0cNUMERIC\u6570\u5b57\uff0cVARCHAR\u5b57\u7b26\uff0cDATE\u65e5\u671f\uff0cTIMESTAMP\u00a0\u65e5\u671f\u65f6\u95f4\uff0cBOOLEAN\u5e03\u5c14\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Values").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u53c2\u6570\u503c\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b").append("\u8fd4\u56deField\u5b57\u6bb5\u7c7b\u578b\uff08\u67e5\u8be2\u7ed3\u679c\u6709\u591a\u6761\u65f6\uff0c\u8fd4\u56de\u6570\u7ec4\uff09").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u62a5\u9500\u5355\u804c\u5458\u4e3ajiuqi\u5355\u636e\u72b6\u6001\u4e3a\u5ba1\u6279\u901a\u8fc7(BILLSTATE=130)\u7684\u5355\u636e\u91d1\u989d\u5408\u8ba1  ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("TFV(\"FO_EXPENSEBILL\",\"BILLMONEY\",\"SUM\",\"STAFFCODE = #{param.arg0, jdbcType=VARCHAR} AND BILLSTATE = #{param.arg1, jdbcType=INTEGER}\", \"jiuqi\", 130)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("123456.78");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0) + "\uff1b\u8fd4\u56deField\u5b57\u6bb5\u7c7b\u578b\uff08\u67e5\u8be2\u7ed3\u679c\u6709\u591a\u6761\u65f6\uff0c\u8fd4\u56de\u6570\u7ec4\uff09");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("Table", DataType.toString((int)0), "\u8868\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("Field", DataType.toString((int)0), "\u5b57\u6bb5\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", true);
        ParameterDescription parameterDescription2 = new ParameterDescription("Function", DataType.toString((int)0), "\u51fd\u6570\uff0c\u586b\u5199\u4ec5\u9650\u4e8e\u5f53\u524d\u6570\u636e\u5e93\u652f\u6301\u7684\u51fd\u6570\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", false);
        ParameterDescription parameterDescription3 = new ParameterDescription("Where", DataType.toString((int)0), "\u6761\u4ef6\uff0c\u8bed\u6cd5\u53c2\u7167\u4e0b\u65b9\u8bf4\u660e\uff1a      name = #{param.arg0, jdbcType=VARCHAR}\uff0c\u9488\u5bf9\u53d8\u5316\u5185\u5bb9\uff0carg0\u8868\u793a\u7b2c1\u4e2a\u53c2\u6570\uff0c\u540e\u9762\u4f9d\u6b21\u7c7b\u63a8\uff0c\u4f8b\u5982param.arg1\u8868\u793a\u7b2c\u4e8c\u4e2a\u53c2\u6570\uff0cjdbcType\u540e\u65b9\u4e3a\u53c2\u6570\u7c7b\u578b\uff0cVARCHAR\u8868\u793a\u5b57\u7b26\u4e32\u7c7b\u578b\uff0c\u5e38\u7528\u7c7b\u578b\u4f8b\u5982\uff1aINTEGER\u6574\u6570\uff0cNUMERIC\u6570\u5b57\uff0cVARCHAR\u5b57\u7b26\uff0cDATE\u65e5\u671f\uff0cTIMESTAMP \u65e5\u671f\u65f6\u95f4\uff0cBOOLEAN\u5e03\u5c14\u3002", false);
        ParameterDescription parameterDescription4 = new ParameterDescription("Values", DataType.toString((int)0), "\u53c2\u6570\u503c\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        parameterDescriptions.add(parameterDescription3);
        parameterDescriptions.add(parameterDescription4);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u53d6\u62a5\u9500\u5355\u804c\u5458\u4e3ajiuqi\u5355\u636e\u72b6\u6001\u4e3a\u5ba1\u6279\u901a\u8fc7(BILLSTATE=130)\u7684\u5355\u636e\u91d1\u989d\u5408\u8ba1  ", "TFV(\"FO_EXPENSEBILL\",\"BILLMONEY\",\"SUM\",\"STAFFCODE = #{param.arg0, jdbcType=VARCHAR} AND BILLSTATE = #{param.arg1, jdbcType=INTEGER}\", \"jiuqi\", 130)", "123456.78", "\u83b7\u53d6\u5355\u636e\u8868\uff08FO_EXPENSEBILL\uff09\u4e2d\u804c\u5458\uff08STAFFCODE\uff09\u4e3ajiuqi\u3001\u5355\u636e\u72b6\u6001\uff08BILLSTATE\uff09\u4e3a\u5ba1\u6279\u901a\u8fc7\uff08130\uff09\u7684\u5355\u636e\u7684\u91d1\u989d\uff08BILLMONEY\uff09\u5408\u8ba1\uff08SUM\uff09\u3002");
        FormulaExample formulaExample1 = new FormulaExample("\u8fc7\u6ee4\u9884\u7b97\u79d1\u76ee\u7684\u53ef\u9009\u8303\u56f4", "[YSKMBM] in TFV(\"MD_YSKM\",\"CODE\",\"\",\"OBJECTCODE in (select YSKM from MD_YSXZCY where STAFF = #{param.arg0, jdbcType=VARCHAR})\",FO_APARBILL[STAFFCODE])", "[01,02]", "\u8fc7\u6ee4\u9884\u7b97\u79d1\u76ee\u7684\u53ef\u9009\u8303\u56f4\uff0c\u53ef\u9009\u8303\u56f4\u662f\u9884\u7b97\u79d1\u76ee\u7684\u9884\u7b97\u79d1\u76ee\u7f16\u7801\uff08YSKMBM\uff09\u4e3aifs\u9884\u7b97\u79d1\u76ee\uff08MD_YSKM\uff09\u7684\u6761\u76ee\uff0cifs\u9884\u7b97\u79d1\u76ee\uff08MD_YSKM\uff09\u7684\u53d6\u503c\u7ed3\u679c\u662f\u9884\u7b97\u5c0f\u7ec4\u6210\u5458\uff08MD_YSXZCY\uff09\u7684\u804c\u5458\u4e3a\u5f53\u524d\u5355\u636e\u804c\u5458\uff08FO_APARBILL[STAFFCODE]\uff09\u7684\u6761\u76ee\u4e0a\u7684\u9884\u7b97\u79d1\u76ee\uff08YSKM\uff09\u3002");
        FormulaExample formulaExample2 = new FormulaExample("\u83b7\u53d6\u5236\u5355\u4eba\u624b\u673a\u53f7", "TFV(\"NP_USER\",\"TELEPHONE\",\"\",\"ID= #{param.arg0, jdbcType=VARCHAR} \",FO_APARBILL[CREATEUSER])", "17800001000", "\u83b7\u53d6\u5355\u636e\uff08FO_APARBILL\uff09\u5236\u5355\u4eba\uff08CREATEUSER\uff09\u7528\u6237\u8868\uff08NP_USER\uff09\u7684\u624b\u673a\u53f7\u7801\uff08TELEPHONE\uff09\u3002");
        formulaExamples.add(formulaExample);
        formulaExamples.add(formulaExample1);
        formulaExamples.add(formulaExample2);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        IASTNode node1 = parameters.get(1);
        IASTNode node2 = parameters.get(2);
        IASTNode node3 = parameters.get(3);
        int size = parameters.size();
        HashMap<String, Object> param = new HashMap<String, Object>();
        if (size > 4) {
            for (int i = 4; i < size; ++i) {
                IASTNode nodei = parameters.get(i);
                Object nodeiData = nodei.evaluate(context);
                param.put("arg" + (i - 4), nodeiData);
            }
        }
        Object node0Data = node0.evaluate(context);
        Object node1Data = node1.evaluate(context);
        Object node2Data = node2.evaluate(context);
        Object node3Data = node3.evaluate(context);
        String table = (String)node0Data;
        String field = (String)node1Data;
        String function = (String)node2Data;
        String where = (String)node3Data;
        if (!StringUtils.hasText(table) || !StringUtils.hasText(field)) {
            return null;
        }
        SQL sql = new SQL();
        if (StringUtils.hasText(function)) {
            sql.SELECT(function + "(" + field + ") as " + field + "");
        } else {
            sql.SELECT(field);
        }
        sql.FROM(table);
        if (StringUtils.hasText(where)) {
            sql.WHERE(where);
        }
        SqlDTO sqlDTO = new SqlDTO(this.getTenantName(context), sql.toString());
        sqlDTO.setParam(param);
        List result = this.dao.listMap(sqlDTO);
        if (result == null || result.size() == 0) {
            return null;
        }
        if (result.size() == 1) {
            Map map = (Map)result.get(0);
            if (map == null) {
                return null;
            }
            Object value = map.values().toArray()[0];
            if (value instanceof Date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime((Date)value);
                return calendar;
            }
            return value;
        }
        List resultList = result.stream().map(o -> o == null ? null : o.values().toArray()[0]).collect(Collectors.toList());
        Optional<Object> optional = resultList.stream().filter(o -> !ObjectUtils.isEmpty(o)).findFirst();
        if (!optional.isPresent()) {
            return new ArrayData(6, resultList);
        }
        Object object = optional.get();
        if (object instanceof String || object instanceof UUID) {
            return new ArrayData(6, resultList);
        }
        if (object instanceof BigDecimal) {
            return new ArrayData(10, resultList);
        }
        if (object instanceof Number) {
            return new ArrayData(3, resultList);
        }
        if (object instanceof Boolean) {
            return new ArrayData(1, resultList);
        }
        if (object instanceof Date) {
            List calendars = resultList.stream().map(o -> {
                if (o == null) {
                    return null;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime((Date)o);
                return calendar;
            }).collect(Collectors.toList());
            return new ArrayData(2, calendars);
        }
        return new ArrayData(6, resultList);
    }

    public String getTenantName(IContext context) {
        String tenantName = ShiroUtil.getTenantName();
        if (tenantName != null) {
            return tenantName;
        }
        if (context != null && context instanceof IFormulaContext && ((IFormulaContext)context).getTenantName() != null) {
            return ((IFormulaContext)context).getTenantName();
        }
        return "";
    }

    @Override
    public void toSQL(IContext context, List<IASTNode> params, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        try {
            IASTNode node0 = params.get(0);
            IASTNode node1 = params.get(1);
            IASTNode node2 = params.get(2);
            IASTNode node3 = params.get(3);
            String table = (String)node0.evaluate(context);
            String field = node1.interpret(context, Language.SQL, (Object)info).replace("'", "");
            String function = node2.interpret(context, Language.SQL, (Object)info).replace("'", "");
            String where = node3.interpret(context, Language.SQL, (Object)info).replace("'", "");
            if (!StringUtils.hasText(table) || !StringUtils.hasText(field)) {
                return;
            }
            SQL sql = new SQL();
            if (StringUtils.hasText(function)) {
                sql.SELECT(function + "(" + field + ") as " + field + "");
            } else {
                sql.SELECT(field);
            }
            sql.FROM(table);
            if (StringUtils.hasText(where)) {
                int size = params.size();
                StringBuilder whereSql = new StringBuilder(16);
                if (size > 4) {
                    String[] wheres = where.split("#\\{(.+?)\\}");
                    for (int i = 4; i < size; ++i) {
                        IASTNode nodei = params.get(i);
                        whereSql.append(wheres[i - 4]).append(nodei.interpret(context, Language.SQL, (Object)info));
                    }
                    if (wheres.length > size - 4) {
                        whereSql.append(wheres[wheres.length - 1]);
                    }
                    sql.WHERE(whereSql.toString());
                } else {
                    sql.WHERE(where);
                }
            }
            buffer.append("(").append(sql.toString()).append(")");
        }
        catch (SyntaxException e) {
            throw new InterpretException(BizFormualI18nUtil.getMessage("va.bizformula.tfv.execute.exception", new Object[]{e.getMessage()}), (Throwable)e);
        }
    }

    @Override
    public boolean autoOptimize() {
        return true;
    }
}

