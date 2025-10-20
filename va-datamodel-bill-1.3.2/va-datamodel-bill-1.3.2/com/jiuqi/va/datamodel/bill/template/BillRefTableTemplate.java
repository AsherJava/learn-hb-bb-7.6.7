/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplate
 */
package com.jiuqi.va.datamodel.bill.template;

import com.jiuqi.va.datamodel.bill.common.BillMessageSourceUtil;
import com.jiuqi.va.datamodel.bill.common.BillTemplateFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillRefTableTemplate
implements DataModelTemplate {
    public DataModelType.BizType getBizType() {
        return DataModelType.BizType.OTHER;
    }

    public String getName() {
        return "billRefVerificationRelationshipTable";
    }

    public String getTitle() {
        return BillMessageSourceUtil.getMessage("datamodel.attribute.billref.relationship", new Object[0]);
    }

    public List<DataModelColumn> getTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle(BillTemplateFieldConsts.getFtID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)));
        columns.add(new DataModelColumn().columnName("MASTERID").columnTitle(BillTemplateFieldConsts.getFtBillRefMasterId()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{60}));
        columns.add(new DataModelColumn().columnName("BILLCODE").columnTitle(BillTemplateFieldConsts.getFtTargetBillCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}));
        columns.add(new DataModelColumn().columnName("BILLDEFINE").columnTitle(BillTemplateFieldConsts.getFtTargetDefineCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}));
        columns.add(new DataModelColumn().columnName("BILLITEMID").columnTitle(BillTemplateFieldConsts.getFtBillItemId()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}));
        columns.add(new DataModelColumn().columnName("SRCBILLCODE").columnTitle(BillTemplateFieldConsts.getFtSrcBillCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}));
        columns.add(new DataModelColumn().columnName("SRCBILLDEFINE").columnTitle(BillTemplateFieldConsts.getFtSrcBillDefine()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}));
        columns.add(new DataModelColumn().columnName("SRCBILLITEMID").columnTitle(BillTemplateFieldConsts.getFtSrcBillItemId()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}));
        columns.add(new DataModelColumn().columnName("CURRMONEY").columnTitle(BillTemplateFieldConsts.getFtCurrmoney()).columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{18, 2}));
        columns.add(new DataModelColumn().columnName("TEMPLATEID").columnTitle(BillTemplateFieldConsts.getFtTemplateId()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}));
        columns.add(new DataModelColumn().columnName("STATUS").columnTitle(BillTemplateFieldConsts.getFtStatus()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}));
        columns.add(new DataModelColumn().columnName("CREATETIME").columnTitle(BillTemplateFieldConsts.getFtCreateTime()).columnType(DataModelType.ColumnType.TIMESTAMP));
        return columns;
    }

    public List<DataModelIndex> getTemplateIndexs(String tableName) {
        ArrayList<DataModelIndex> indexList = new ArrayList<DataModelIndex>();
        DataModelIndex index_template = new DataModelIndex().columnList(new String[]{"TEMPLATEID"});
        DataModelIndex index_masterId = new DataModelIndex().columnList(new String[]{"MASTERID"});
        DataModelIndex index_srcBillCode = new DataModelIndex().columnList(new String[]{"SRCBILLCODE"});
        if (tableName != null) {
            index_template.setIndexName(tableName + "_TEMPLATEID");
            index_masterId.setIndexName(tableName + "_MASTERID");
            index_srcBillCode.setIndexName(tableName + "_SRCBILLCODE");
        } else {
            index_template.setRandomIndexName();
            index_masterId.setRandomIndexName();
            index_srcBillCode.setRandomIndexName();
        }
        indexList.add(index_template);
        indexList.add(index_masterId);
        indexList.add(index_srcBillCode);
        return indexList;
    }
}

