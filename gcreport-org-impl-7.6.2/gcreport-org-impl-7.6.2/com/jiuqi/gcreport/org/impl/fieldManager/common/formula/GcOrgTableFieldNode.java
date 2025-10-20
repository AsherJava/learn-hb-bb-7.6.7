/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.filter.bill.formula.OrgFormulaContext
 *  com.jiuqi.va.formula.intf.TableFieldNode
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.org.impl.fieldManager.common.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.fieldManager.common.formula.GcOrgFormulaContext;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Optional;

public class GcOrgTableFieldNode
extends DynamicNode
implements TableFieldNode {
    private static final long serialVersionUID = 5681006905930338710L;
    public final String tableName;
    public final String fieldName;
    public DataModelType.ColumnType columnType;

    public GcOrgTableFieldNode(IContext context, Token token, String tableName, String fieldName) {
        super(token);
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.setColumnType((OrgFormulaContext)Convert.cast((Object)context, OrgFormulaContext.class));
    }

    private void setColumnType(OrgFormulaContext orgFormulaContext) {
        Optional<DataModelColumn> result;
        DataModelDO modelDO = (DataModelDO)orgFormulaContext.get(this.tableName);
        if (modelDO == null) {
            modelDO = this.findTabelDefine(this.tableName);
            if (modelDO == null) {
                throw new RuntimeException("\u672a\u77e5\u7684\u8868\u5b9a\u4e49\uff1a" + this.tableName);
            }
            orgFormulaContext.put(this.tableName, (Object)modelDO);
        }
        if ((result = modelDO.getColumns().stream().filter(o -> o.getColumnName().equals(this.fieldName)).findFirst()).equals(Optional.empty())) {
            throw new RuntimeException(String.format("\u672a\u77e5\u7684\u8868\u5b57\u6bb5\uff1a%s[%s]", this.tableName, this.fieldName));
        }
        this.columnType = result.get().getColumnType();
    }

    public int getType(IContext context) throws SyntaxException {
        switch (this.columnType) {
            case UUID: 
            case NVARCHAR: {
                return 6;
            }
            case INTEGER: {
                return 3;
            }
            case NUMERIC: {
                return 3;
            }
            case DATE: {
                return 2;
            }
            case TIMESTAMP: {
                return 2;
            }
            case CLOB: {
                return 11;
            }
        }
        return -1;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        GcOrgFormulaContext orgContext = (GcOrgFormulaContext)context;
        int dataType = this.getType((IContext)orgContext);
        if (this.tableName.equalsIgnoreCase("MD_ORG")) {
            String code = orgContext.getOrg().getCode();
            OrgToJsonVO orgByCode = GcOrgBaseTool.getInstance().getOrgByCode(code);
            orgContext.setBaseOrg(new OrgDO(orgByCode.getDatas()));
        }
        if (orgContext.getDataMap().containsKey(this.tableName + "_" + this.fieldName)) {
            Object value = orgContext.getDataMap().get(this.tableName + "_" + this.fieldName);
            return orgContext.valueOf(value, dataType);
        }
        throw new SyntaxException("\u65e0\u6cd5\u8bc6\u522b\u7684\u5217:" + this.fieldName);
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("%s[%s]", this.tableName, this.fieldName));
    }

    private DataModelDO findTabelDefine(String tableName) {
        DataModelDTO param = new DataModelDTO();
        param.setName(tableName);
        return ((DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class)).get(param);
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append(String.format("%s.%s", this.tableName, this.fieldName));
    }
}

