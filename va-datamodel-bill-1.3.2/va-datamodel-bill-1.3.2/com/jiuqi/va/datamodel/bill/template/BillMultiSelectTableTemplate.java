/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.va.datamodel.bill.template;

import com.jiuqi.va.datamodel.bill.biztype.BillSubBizType;
import com.jiuqi.va.datamodel.bill.common.BillMessageSourceUtil;
import com.jiuqi.va.datamodel.bill.common.BillTemplateFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelBillExtendTemplate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillMultiSelectTableTemplate
implements DataModelBillExtendTemplate {
    public String getName() {
        return "billMultipleSubTable";
    }

    public String getTitle() {
        return BillMessageSourceUtil.getMessage("datamodel.attribute.biztype.multiSelect", new Object[0]);
    }

    @Override
    public int getSubBizType() {
        return BillSubBizType.MULTIPLESUB.getIndex();
    }

    @Override
    public int getOrdinal() {
        return 99;
    }

    public boolean isShowSubBizType() {
        return false;
    }

    public List<DataModelColumn> getTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle(BillTemplateFieldConsts.getFtID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("GROUPID").columnTitle(BillTemplateFieldConsts.getFtGroupID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("MASTERID").columnTitle(BillTemplateFieldConsts.getFtMasterID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BINDINGID").columnTitle(BillTemplateFieldConsts.getFtBindingID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BINDINGVALUE").columnTitle(BillTemplateFieldConsts.getFtBindingValue()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("ORDERNUM").columnTitle(BillTemplateFieldConsts.getFtOrdinal()).columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        return columns;
    }

    public List<DataModelIndex> getTemplateIndexs(String tableName) {
        ArrayList<DataModelIndex> indexList = new ArrayList<DataModelIndex>();
        DataModelIndex index1 = new DataModelIndex().columnList(new String[]{"GROUPID", "MASTERID", "BINDINGID"});
        if (tableName != null) {
            index1.setIndexName(tableName + "_GDMDBD");
        } else {
            index1.setRandomIndexName();
        }
        indexList.add(index1);
        return indexList;
    }
}

