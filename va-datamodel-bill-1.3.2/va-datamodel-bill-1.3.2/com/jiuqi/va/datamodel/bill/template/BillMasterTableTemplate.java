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
public class BillMasterTableTemplate
implements DataModelBillExtendTemplate {
    public String getName() {
        return "billMasterTable";
    }

    public String getTitle() {
        return BillMessageSourceUtil.getMessage("datamodel.attribute.biztype.billMaster", new Object[0]);
    }

    @Override
    public int getSubBizType() {
        return BillSubBizType.BILLMaster.getIndex();
    }

    @Override
    public int getOrdinal() {
        return 0;
    }

    public List<DataModelColumn> getTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle(BillTemplateFieldConsts.getFtID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VER").columnTitle(BillTemplateFieldConsts.getFtVer()).columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 0}).nullable(Boolean.valueOf(false)).defaultVal("0").columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("DEFINECODE").columnTitle(BillTemplateFieldConsts.getFtDefineCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLCODE").columnTitle(BillTemplateFieldConsts.getFtBillCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLDATE").columnTitle(BillTemplateFieldConsts.getFtBillDate()).columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("BILLSTATE").columnTitle(BillTemplateFieldConsts.getFtBillState()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{5}).mapping("EM_BILLSTATE.VAL").mappingType(Integer.valueOf(2)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("UNITCODE").columnTitle(BillTemplateFieldConsts.getFtUnitCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_ORG.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATEUSER").columnTitle(BillTemplateFieldConsts.getFtCreateUser()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).mapping("AUTH_USER.ID").mappingType(Integer.valueOf(3)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATETIME").columnTitle(BillTemplateFieldConsts.getFtCreateTime()).columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("QRCODE").columnTitle(BillTemplateFieldConsts.getFtQRCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{100}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("QUOTECODE").columnTitle(BillTemplateFieldConsts.getFtQuoteCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{43}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("ATTACHNUM").columnTitle(BillTemplateFieldConsts.getFtAttachNum()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{5}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("IMAGETYPE").columnTitle(BillTemplateFieldConsts.getFtImageType()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{5}).mapping("EM_IMAGETYPE.VAL").mappingType(Integer.valueOf(2)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("IMAGESTATE").columnTitle(BillTemplateFieldConsts.getFtImageState()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{2}).mapping("EM_IMAGESTATE.VAL").mappingType(Integer.valueOf(2)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("IMAGENUM").columnTitle(BillTemplateFieldConsts.getFtImageNum()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{5}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("DISABLESENDMAILFLAG").columnTitle(BillTemplateFieldConsts.getFtDisablesendMailFlag()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.EXTEND).mappingType(Integer.valueOf(0)).defaultVal("0"));
        columns.add(new DataModelColumn().columnName("GOTOLASTREJECT").columnTitle(BillTemplateFieldConsts.getFtGotoLastReject()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.EXTEND).mappingType(Integer.valueOf(0)).defaultVal("0"));
        columns.add(new DataModelColumn().columnName("TEMPSTEP").columnTitle(BillTemplateFieldConsts.getFtTempStep()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{2}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("ABOLISHUSER").columnTitle(BillTemplateFieldConsts.getFtAbolishUser()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).mapping("AUTH_USER.ID").mappingType(Integer.valueOf(3)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        columns.add(new DataModelColumn().columnName("ABOLISHTIME").columnTitle(BillTemplateFieldConsts.getFtAabolishTime()).columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.EXTEND));
        return columns;
    }

    public List<DataModelIndex> getTemplateIndexs(String tableName) {
        ArrayList<DataModelIndex> indexList = new ArrayList<DataModelIndex>();
        DataModelIndex index1 = new DataModelIndex().columnList(new String[]{"BILLCODE"});
        if (tableName != null) {
            index1.setIndexName(tableName + "_BILLCODE");
        } else {
            index1.setRandomIndexName();
        }
        indexList.add(index1);
        return indexList;
    }
}

