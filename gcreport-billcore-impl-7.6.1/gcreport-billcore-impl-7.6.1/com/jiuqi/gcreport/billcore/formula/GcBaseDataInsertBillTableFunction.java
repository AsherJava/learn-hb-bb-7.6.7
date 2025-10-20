/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.intf.ModelFunction
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.billcore.formula;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcBaseDataInsertBillTableFunction
extends ModelFunction {
    private static final Logger logger = LoggerFactory.getLogger(GcBaseDataInsertBillTableFunction.class);
    private static final long serialVersionUID = 1L;
    public static final String FN_PARENTID = "PARENTID";
    public static final String FN_TREEID = "TREEID";
    public static final String FN_LEAF = "LEAF";
    public static final String FN_LEVEL = "TREELEVEL";
    @Resource
    private BaseDataClient baseDataClient;

    public GcBaseDataInsertBillTableFunction() {
        this.parameters().add(new Parameter("billTable", 6, "\u5355\u636e\u5b50\u8868\u540d", false));
        this.parameters().add(new Parameter("fieldName", 6, "\u63d2\u5165\u5b57\u6bb5\u540d", false));
        this.parameters().add(new Parameter("baseDataTable", 6, "\u57fa\u7840\u6570\u636e\u8868\u540d", false));
        this.parameters().add(new Parameter("isTreeShow", 1, "\u662f\u5426\u4ee5\u6811\u578b\u5c55\u793a", false));
        this.parameters().add(new Parameter("filterValue", 6, "\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u503c", true));
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
        buffer.append("    ").append("billTable").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u5355\u636e\u5b50\u8868\u540d").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("fieldName").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u8981\u63d2\u5165\u7684\u5b57\u6bb5\u540d\uff0c\u9700\u8981\u6811\u5f62\u5c55\u793a\u7684\u8be5\u5b57\u6bb5\u5fc5\u987b\u662f\u6811\u5f62\u5b50\u8868\u5206\u7ec4\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("baseDataTable").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u57fa\u7840\u6570\u636e\u8868\u540d").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("isTreeShow").append("\uff1a").append(DataType.toString((int)1)).append("\uff1b\u662f\u5426\u4ee5\u6811\u5f62\u5c55\u793a\uff0c\u8981\u6c42\u57fa\u7840\u6570\u636e\u4e3a\u6811\u5f62\u7ed3\u6784\uff0c\u5305\u542b\u6811\u5f62\u8868\u8981\u6c42\u5b57\u6bb5\uff08PARENTID\uff0cTREEID\uff0cLEAF\uff09\u6216\u5b50\u8868\u6811\u5f62\u7f29\u8fdb\u5b57\u6bb5\uff08TREELEVEL\uff09").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u503c").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u57fa\u7840\u6570\u636e\u8fc7\u6ee4\u503c\uff0c\u683c\u5f0f\u53c2\u7167\uff1a").append(" assetcategory=01   \u53ef\u4f20\u591a\u4e2a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u65e0").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append("\u5c06\u57fa\u7840\u6570\u636eMD_EAM_ASSETCLASS\u63d2\u5165EAM_ASSETCLASS_ITEM\u8868ASSETCLASS\u5b57\u6bb5\u4e2d\uff0c\u7b5b\u9009\u6761\u4ef6\u4e3aassetcategory=01  ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append("GcBaseDataInsertBillTable(\"EAM_ASSETCLASS_ITEM\",\"ASSETCLASS\",\"MD_EAM_ASSETCLASS\",true,\"assetcategory=01\")").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public String addDescribe() {
        return "\u5c06\u57fa\u7840\u6570\u636e\u63d2\u5165\u5355\u636e\u8868\u683c\uff08\u5408\u5e76\u62a5\u8868\uff09";
    }

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

    public boolean isInfiniteParameter() {
        return true;
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

    public String name() {
        return "GcBaseDataInsertBillTable";
    }

    public String title() {
        return "\u57fa\u7840\u6570\u636e\u63d2\u5165\u5355\u636e\u8868\uff08\u5408\u5e76\u62a5\u8868\uff09";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> list) throws SyntaxException {
        String tableName = list.get(0).evaluate(context).toString();
        String fieldName = list.get(1).evaluate(context).toString();
        String baseDataTable = list.get(2).evaluate(context).toString();
        Boolean isTreeShow = (Boolean)list.get(3).evaluate(context);
        BillModel model = (BillModel)((ModelDataContext)context).model;
        DataTable billTable = model.getTable(tableName);
        NamedContainer fields = billTable.getFields();
        if (!model.editing()) {
            throw new RuntimeException(this.title() + "\u975e\u7f16\u8f91\u72b6\u6001\u4e0d\u53ef\u6267\u884c");
        }
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(baseDataTable);
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        basedataDTO.setLeafFlag(Boolean.valueOf(true));
        basedataDTO.setUnitcode(model.getMaster().getString("UNITCODE"));
        int size = list.size();
        HashMap<String, String> baseDataMap = new HashMap<String, String>();
        if (size > 4) {
            for (int i = 4; i < size; ++i) {
                IASTNode nodei = list.get(i);
                String param = nodei.evaluate(context).toString();
                int m = param.indexOf("=");
                if (m <= -1) continue;
                String baseDataColumnName = param.substring(0, m).toLowerCase();
                String baseDataValue = param.substring(m + 1);
                baseDataMap.put(baseDataColumnName, baseDataValue);
            }
        }
        basedataDTO.putAll(baseDataMap);
        List baseDataDOS = this.baseDataClient.list(basedataDTO).getRows();
        HashMap<String, UUID> treeMapping = new HashMap<String, UUID>();
        HashSet<String> fieldSet = new HashSet<String>();
        if (isTreeShow.booleanValue()) {
            if (fields.find(FN_PARENTID) != null) {
                fieldSet.add(FN_PARENTID);
            }
            if (fields.find(FN_TREEID) != null) {
                fieldSet.add(FN_TREEID);
            }
            if (fields.find(FN_LEAF) != null) {
                fieldSet.add(FN_LEAF);
            }
            if (fields.find(FN_LEVEL) != null) {
                fieldSet.add(FN_LEVEL);
            }
        }
        for (BaseDataDO baseDataDO : baseDataDOS) {
            String code = baseDataDO.getCode();
            DataRow row = billTable.appendRow();
            row.setValue(fieldName, (Object)baseDataDO.getObjectcode());
            if (!isTreeShow.booleanValue()) continue;
            UUID treeid = UUID.randomUUID();
            treeMapping.put(code, treeid);
            if (fieldSet.contains(FN_LEAF)) {
                row.setValue(FN_LEAF, baseDataDO.get((Object)"leafFlag"));
            }
            if (fieldSet.contains(FN_TREEID)) {
                row.setValue(FN_TREEID, (Object)treeid);
            }
            if (fieldSet.contains(FN_PARENTID)) {
                String parentcode = baseDataDO.getParentcode();
                UUID parentid = "-".equals(parentcode) ? UUID.fromString("00000000-0000-0000-0000-000000000000") : (UUID)treeMapping.get(parentcode);
                row.setValue(FN_PARENTID, (Object)parentid);
            }
            if (!fieldSet.contains(FN_LEVEL)) continue;
            long i = baseDataDO.getParents().chars().filter(ch -> ch == 47).count();
            row.setValue(FN_LEVEL, (Object)(i + 1L));
        }
        return null;
    }
}

