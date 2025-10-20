/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.IFormulaContext
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.extend.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlItemInsertFunction
extends ModelFunction {
    private static final long serialVersionUID = -1L;
    @Autowired
    private CommonDao dao;

    public SqlItemInsertFunction() {
        this.parameters().add(new Parameter("Sql", 0, "\u516c\u5f0f\u67e5\u8be2sql", false));
        this.parameters().add(new Parameter("Fields", 6, "\u67e5\u8be2\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("flagBool", 1, "\u5168\u5220\u5168\u63d2", false));
    }

    public String addDescribe() {
        return "\u7f16\u5199SQL\u67e5\u8be2\u6570\u636e,\u56de\u5199\u5230\u5bf9\u5e94\u7684\u5b50\u8868";
    }

    public boolean enableDebug() {
        return true;
    }

    public String name() {
        return "SqlItemInsert";
    }

    public String title() {
        return "SQL\u5b50\u8868\u63d2\u5165";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        BillModelImpl model = (BillModelImpl)cxt.model;
        IASTNode node0 = parameters.get(0);
        String sql = (String)node0.evaluate(context);
        SqlDTO sqlDTO = new SqlDTO(this.getTenantName(context), sql);
        List resulList = this.dao.listUpperKeyMap(sqlDTO);
        if (resulList == null || resulList.size() == 0) {
            return true;
        }
        String fieldsStr = parameters.get(1).toString().replace("\"", "").trim();
        String tableName = fieldsStr.substring(0, fieldsStr.indexOf("@"));
        String fieldNames = fieldsStr.substring(fieldsStr.indexOf("@") + 1);
        Set fieldNameSet = Arrays.stream(fieldNames.split(",")).collect(Collectors.toSet());
        String bool = parameters.get(2).toString().replace("\"", "").trim();
        DataTable dataTable = (DataTable)model.getData().getTables().find(tableName);
        if ("TRUE".equalsIgnoreCase(bool)) {
            int size = dataTable.getRows().size();
            dataTable.deleteRow(0, size);
        }
        for (Map map : resulList) {
            DataRow dataRow = dataTable.appendRow();
            for (String name : fieldNameSet) {
                dataRow.setValue(name, map.get(name));
            }
        }
        return true;
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

    public String toDescription() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Sql\uff1a").append(DataType.toString((int)0)).append("\uff1b\u67e5\u8be2sql\uff0c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("Fields\uff1a").append(DataType.toString((int)6)).append("\uff1b\u56de\u5199\u5b57\u6bb5\uff0c").append("\u8868\u540d\u4e0e\u5b57\u6bb5\u7528@\u9694\u5f00\uff0c\u5b57\u6bb5\u5b58\u5728\u591a\u4e2a\u65f6\u7528,\u9694\u5f00\uff0c\u5982\uff1a\u8868\u540d@\u5b57\u6bb51,\u5b57\u6bb52, ...").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("flagBool\uff1a").append(DataType.toString((int)1)).append("\uff1b\u5168\u5220\u5168\u63d2\uff0c").append("\u4e3aTRUE\u65f6\u4f1a\u6e05\u7a7a\u5b50\u8868\u6240\u6709\u884c\uff0c\u4e3aFALSE\u8ffd\u52a0\u884c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u6ce8\u610f\u4e8b\u9879\uff1a\u56de\u663e\u5b57\u6bb5\u5fc5\u987b\u548cSql\u7684SELECT\u7684\u5b57\u6bb5\u540d\u6216\u8005AS\u7684\u522b\u540d\u5bf9\u5e94\uff0c\u5982\u679cWHERE\u540e\u6709\u65e5\u671f\u7c7b\u578b\u7684\u5b57\u6bb5\uff0c\u5219Sql\u4e2d\u8981\u4f7f\u7528TO_DATE()\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a\u65e0\u8fd4\u56de\u503c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u81ea\u5199SQL\u6309\u5355\u636e\u4e3b\u8868\u7684BILLCODE\u67e5\u8be2FO_APPLOANBILL_ITEM\u8868\uff0c\u5c06\u67e5\u8be2\u7ed3\u679cBILLMONEY\u5b57\u6bb5\u56de\u663e\u5230\u5b50\u8868\u7684BILLMONEY\u5b57\u6bb5\uff0c\u5c06TESTMONEY\u5b57\u6bb5\u56de\u663e\u5230\u5b50\u8868ORGNREQUESTMONEY\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SqlItemInsert(\"SELECT BILLMONEY,TESTMONEY AS ORGNREQUESTMONEY FROM FO_APPLOANBILL_ITEM WHERE BILLCODE='\"+FO_APPLOANBILL[APPLOANCODE]+\"'\" , \"FO_APPLOANBILL_ITEM@BILLMONEY,ORGNREQUESTMONEY\",FALSE)").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue("\u65e0\u8fd4\u56de\u503c;");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("Sql", DataType.toString((int)0), "\u67e5\u8be2sql", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("Fields", DataType.toString((int)6), "\u56de\u5199\u5b57\u6bb5", Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription("flagBool", DataType.toString((int)1), "\u5168\u5220\u5168\u63d2", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<String> notes = new ArrayList<String>();
        notes.add("\u56de\u663e\u5b57\u6bb5\u5fc5\u987b\u548cSql\u7684SELECT\u7684\u5b57\u6bb5\u540d\u6216\u8005AS\u7684\u522b\u540d\u5bf9\u5e94\uff0c\u5982\u679cWHERE\u540e\u6709\u65e5\u671f\u7c7b\u578b\u7684\u5b57\u6bb5\uff0c\u5219Sql\u4e2d\u8981\u4f7f\u7528TO_DATE()\u51fd\u6570");
        formulaDescription.setNotes(notes);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u81ea\u5199SQL\u6309\u5355\u636e\u4e3b\u8868\u7684BILLCODE\u67e5\u8be2FO_APPLOANBILL_ITEM\u8868\uff0c\u5c06\u67e5\u8be2\u7ed3\u679cBILLMONEY\u5b57\u6bb5\u56de\u663e\u5230\u5b50\u8868\u7684BILLMONEY\u5b57\u6bb5\uff0c\u5c06TESTMONEY\u5b57\u6bb5\u56de\u663e\u5230\u5b50\u8868ORGNREQUESTMONEY\u5b57\u6bb5", "SqlItemInsert(\"SELECT BILLMONEY,TESTMONEY AS ORGNREQUESTMONEY FROM FO_APPLOANBILL_ITEM WHERE BILLCODE='\"+FO_APPLOANBILL[APPLOANCODE]+\"'\" , \"FO_APPLOANBILL_ITEM@BILLMONEY,ORGNREQUESTMONEY\",FALSE)", "");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

