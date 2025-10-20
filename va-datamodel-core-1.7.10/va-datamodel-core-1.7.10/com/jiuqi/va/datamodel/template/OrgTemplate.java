/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.StorageFieldConsts
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplate
 */
package com.jiuqi.va.datamodel.template;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.domain.common.StorageFieldConsts;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component(value="vaDataModelOrgTemplate")
public class OrgTemplate
implements DataModelTemplate {
    public DataModelType.BizType getBizType() {
        return DataModelType.BizType.BASEDATA;
    }

    public String getName() {
        return "org";
    }

    public int getSubBizType() {
        return 0;
    }

    public String getTitle() {
        return DataModelCoreI18nUtil.getMessage("datamodel.attribute.biztype.organization", new Object[0]);
    }

    public List<DataModelColumn> getTemplateFields() {
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("ID").columnTitle(StorageFieldConsts.getFtID()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).pkey(Boolean.valueOf(true)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VER").columnTitle(StorageFieldConsts.getFtVer()).columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).nullable(Boolean.valueOf(false)).defaultVal("0").columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CODE").columnTitle(StorageFieldConsts.getFtCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("ORGCODE").columnTitle(DataModelCoreI18nUtil.getMessage("datamodel.consts.pubField.orgcode", new Object[0])).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("NAME").columnTitle(StorageFieldConsts.getFtName()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{200}).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("SHORTNAME").columnTitle(StorageFieldConsts.getFtShortName()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{100}).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("VALIDTIME").columnTitle(StorageFieldConsts.getFtValidTime()).columnType(DataModelType.ColumnType.DATE).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("INVALIDTIME").columnTitle(StorageFieldConsts.getFtInvalidTime()).columnType(DataModelType.ColumnType.DATE).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("PARENTCODE").columnTitle(StorageFieldConsts.getFtParentCode()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("STOPFLAG").columnTitle(StorageFieldConsts.getFtStopFlag()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).nullable(Boolean.valueOf(false)).defaultVal("0").mappingType(Integer.valueOf(0)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("RECOVERYFLAG").columnTitle(StorageFieldConsts.getFtRecoveryFlag()).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).nullable(Boolean.valueOf(false)).defaultVal("0").mappingType(Integer.valueOf(0)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("ORDINAL").columnTitle(StorageFieldConsts.getFtOrdinal()).columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATEUSER").columnTitle(StorageFieldConsts.getFtCreateUser()).columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36}).mapping("AUTH_USER.ID").mappingType(Integer.valueOf(3)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("CREATETIME").columnTitle(StorageFieldConsts.getFtCreateTime()).columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        columns.add(new DataModelColumn().columnName("PARENTS").columnTitle(StorageFieldConsts.getFtParents()).columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{610}).nullable(Boolean.valueOf(false)).columnAttr(DataModelType.ColumnAttr.SYSTEM));
        return columns;
    }

    public List<DataModelIndex> getTemplateIndexs(String tableName) {
        ArrayList<DataModelIndex> indexList = new ArrayList<DataModelIndex>();
        DataModelIndex index0 = new DataModelIndex().columnList(new String[]{"CODE", "VALIDTIME", "INVALIDTIME"}).unique(Boolean.valueOf(true));
        if (tableName != null) {
            index0.setIndexName(tableName + "_CEVEIE");
        } else {
            index0.setRandomIndexName();
        }
        DataModelIndex index1 = new DataModelIndex().columnList(new String[]{"VER"});
        if (tableName != null) {
            index1.setIndexName(tableName + "_VER");
        } else {
            index1.setRandomIndexName();
        }
        DataModelIndex index2 = new DataModelIndex().columnList(new String[]{"CODE"});
        if (tableName != null) {
            index2.setIndexName(tableName + "_CODE");
        } else {
            index2.setRandomIndexName();
        }
        DataModelIndex index3 = new DataModelIndex().columnList(new String[]{"PARENTCODE"});
        if (tableName != null) {
            index3.setIndexName(tableName + "_PARENTCODE");
        } else {
            index3.setRandomIndexName();
        }
        indexList.add(index0);
        indexList.add(index1);
        indexList.add(index2);
        indexList.add(index3);
        return indexList;
    }
}

