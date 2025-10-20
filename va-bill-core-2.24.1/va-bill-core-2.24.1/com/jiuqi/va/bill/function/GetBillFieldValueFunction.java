/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.data.DataFieldImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GetBillFieldValueFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(GetBillFieldValueFunction.class);
    @Autowired
    BillDefineService billDefineService;

    public GetBillFieldValueFunction() {
        this.parameters().add(new Parameter("definecode", 6, "\u5355\u636e\u5b9a\u4e49", false));
        this.parameters().add(new Parameter("billcode", 6, "\u5355\u636e\u7f16\u53f7", false));
        this.parameters().add(new Parameter("tablefield", 6, "\u9700\u8981\u67e5\u8be2\u7684\u6570\u636e\u8868\u5b57\u6bb5\u6807\u8bc6", false));
    }

    public String addDescribe() {
        return "\u83b7\u53d6\u5355\u636e\u67d0\u5b57\u6bb5\u7684\u503c\u3002";
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        ModelDataContext cxt = (ModelDataContext)context;
        Object defineCodeObj = parameters.get(0).evaluate(context);
        Object billCodeObj = parameters.get(1).evaluate(context);
        IASTNode iastNode = parameters.get(2);
        if (defineCodeObj == null || billCodeObj == null || iastNode == null) {
            return cxt.valueOf(null, 0);
        }
        String defineCode = defineCodeObj.toString();
        String billCode = billCodeObj.toString();
        if (!StringUtils.hasText(defineCode) || !StringUtils.hasText(billCode)) {
            return cxt.valueOf(null, 0);
        }
        String param = iastNode.toString();
        int start = param.indexOf("[");
        int end = param.indexOf("]");
        String tableName = param.substring(1, start);
        String fieldName = param.substring(start + 1, end);
        try {
            BillDefineService billDefineService = (BillDefineService)ApplicationContextRegister.getBean(BillDefineService.class);
            BillContextImpl billContextImpl = new BillContextImpl();
            billContextImpl.setDisableVerify(true);
            BillModelImpl model = (BillModelImpl)billDefineService.createModel((BillContext)billContextImpl, defineCode);
            model.loadByCode(billCode);
            DataTableImpl table = (DataTableImpl)model.getData().getTables().find(tableName);
            if (table == null || table.getRows() == null) {
                return cxt.valueOf(null, 0);
            }
            DataFieldImpl field = (DataFieldImpl)table.getFields().find(fieldName);
            if (field == null) {
                return cxt.valueOf(null, 0);
            }
            boolean multFalg = false;
            if (field.getDefine().getRefTableType() != 0 && field.getDefine().getRefTableType() != 3 && field.getDefine().getValueType() == ValueType.IDENTIFY || field.getDefine().getRefTableType() == 3 && field.getDefine().isMultiChoiceStore()) {
                multFalg = true;
            }
            ArrayList<Object> values = new ArrayList<Object>();
            ListContainer rows = table.getRows();
            for (int i = 0; i < rows.size(); ++i) {
                DataRowImpl row = (DataRowImpl)rows.get(i);
                if (multFalg) {
                    values.addAll(row.getMultiValue(fieldName));
                    continue;
                }
                values.add(row.getValue(fieldName));
            }
            if (values.size() == 0) {
                return cxt.valueOf(null, 0);
            }
            if (values.size() == 1) {
                return cxt.valueOf(values.get(0), 0);
            }
            return cxt.valueOf(values, 0);
        }
        catch (Exception e) {
            LOG.error("getBillFieldValue\u516c\u5f0f\u6267\u884c\u5f02\u5e38", e);
            return cxt.valueOf(null, 0);
        }
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String name() {
        return "getBillFieldValue";
    }

    public String title() {
        return "\u83b7\u53d6\u5355\u636e\u67d0\u5b57\u6bb5\u7684\u503c";
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
        buffer.append("    ").append("definecode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5355\u636e\u5b9a\u4e49\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("billcode").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5355\u636e\u7f16\u53f7").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("tablefield").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u6570\u636e\u8868\u5b57\u6bb5\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u4efb\u610f\u7c7b\u578b").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u62a5\u9500\u5355\u4e3b\u8868\u9879\u76ee\u8d1f\u8d23\u4eba\u4f5c\u4e3a\u5de5\u4f5c\u6d41\u53c2\u4e0e\u8005 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("getBillFieldValue(FO_REVERSEBILL[SRCDEFINECODE],FO_REVERSEBILL[SRCBILLCODE],'FO_EXPNESEBILL[STAFFCODE]')").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append(DataType.toString((int)0));
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("definecode", DataType.toString((int)6), "\u5355\u636e\u5b9a\u4e49\u6807\u8bc6", Boolean.valueOf(true));
        ParameterDescription parameterDescription1 = new ParameterDescription("billcode", DataType.toString((int)6), "\u5355\u636e\u7f16\u53f7", Boolean.valueOf(true));
        ParameterDescription parameterDescription2 = new ParameterDescription("tablefield", DataType.toString((int)6), "\u6570\u636e\u8868\u5b57\u6bb5\u6807\u8bc6", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u62a5\u9500\u5355\u4e3b\u8868\u9879\u76ee\u8d1f\u8d23\u4eba\u4f5c\u4e3a\u5de5\u4f5c\u6d41\u53c2\u4e0e\u8005\u3002", "getBillFieldValue(FO_REVERSEBILL[SRCDEFINECODE],FO_REVERSEBILL[SRCBILLCODE],'FO_EXPNESEBILL[STAFFCODE]')", DataType.toString((int)0));
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

