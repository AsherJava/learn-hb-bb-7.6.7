/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 */
package com.jiuqi.bde.fetch.impl.result.enums;

import com.jiuqi.bde.bizmodel.execute.config.FetchResultConfig;
import com.jiuqi.bde.fetch.impl.result.entity.TableIndexVO;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import java.util.ArrayList;
import java.util.List;

public enum FetchResultTableEnum {
    BDE_RESULT_FIXED("BDE_RESULT_FIXED", "\u56fa\u5b9a\u7ed3\u679c\u8868"){

        @Override
        public List<DesignColumnModelDefine> getFieldList(String tableId) {
            ArrayList<DesignColumnModelDefine> designColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefineImpl requestTaskId = new DesignColumnModelDefineImpl();
            requestTaskId.setCode("REQUESTTASKID");
            requestTaskId.setName("REQUESTTASKID");
            requestTaskId.setColumnType(ColumnModelType.STRING);
            requestTaskId.setPrecision(16);
            requestTaskId.setTableID(tableId);
            requestTaskId.setID(UUIDUtils.newUUIDStr());
            requestTaskId.setKind(ColumnModelKind.DEFAULT);
            requestTaskId.setNullAble(false);
            requestTaskId.setTitle("\u8bf7\u6c42\u4efb\u52a1ID");
            designColumnModelDefines.add((DesignColumnModelDefine)requestTaskId);
            DesignColumnModelDefineImpl formId = new DesignColumnModelDefineImpl();
            formId.setCode("FORMID");
            formId.setName("FORMID");
            formId.setColumnType(ColumnModelType.STRING);
            formId.setPrecision(36);
            formId.setTableID(tableId);
            formId.setID(UUIDUtils.newUUIDStr());
            formId.setKind(ColumnModelKind.DEFAULT);
            formId.setNullAble(false);
            formId.setTitle("\u8868ID");
            designColumnModelDefines.add((DesignColumnModelDefine)formId);
            DesignColumnModelDefineImpl regionId = new DesignColumnModelDefineImpl();
            regionId.setCode("REGIONID");
            regionId.setName("REGIONID");
            regionId.setColumnType(ColumnModelType.STRING);
            regionId.setPrecision(36);
            regionId.setTableID(tableId);
            regionId.setID(UUIDUtils.newUUIDStr());
            regionId.setKind(ColumnModelKind.DEFAULT);
            regionId.setNullAble(false);
            regionId.setTitle("\u533a\u57dfID");
            designColumnModelDefines.add((DesignColumnModelDefine)regionId);
            DesignColumnModelDefineImpl fieldDefineId = new DesignColumnModelDefineImpl();
            fieldDefineId.setCode("FIELDDEFINEID");
            fieldDefineId.setName("FIELDDEFINEID");
            fieldDefineId.setColumnType(ColumnModelType.STRING);
            fieldDefineId.setPrecision(36);
            fieldDefineId.setTableID(tableId);
            fieldDefineId.setID(UUIDUtils.newUUIDStr());
            fieldDefineId.setKind(ColumnModelKind.DEFAULT);
            fieldDefineId.setNullAble(false);
            fieldDefineId.setTitle("\u6307\u6807ID");
            designColumnModelDefines.add((DesignColumnModelDefine)fieldDefineId);
            DesignColumnModelDefineImpl fetchSettingId = new DesignColumnModelDefineImpl();
            fetchSettingId.setCode("FETCHSETTINGID");
            fetchSettingId.setName("FETCHSETTINGID");
            fetchSettingId.setColumnType(ColumnModelType.STRING);
            fetchSettingId.setPrecision(20);
            fetchSettingId.setTableID(tableId);
            fetchSettingId.setID(UUIDUtils.newUUIDStr());
            fetchSettingId.setKind(ColumnModelKind.DEFAULT);
            fetchSettingId.setNullAble(false);
            fetchSettingId.setTitle("\u53d6\u6570\u8bbe\u7f6eID");
            designColumnModelDefines.add((DesignColumnModelDefine)fetchSettingId);
            DesignColumnModelDefineImpl sign = new DesignColumnModelDefineImpl();
            sign.setCode("SIGN");
            sign.setName("SIGN");
            sign.setColumnType(ColumnModelType.INTEGER);
            sign.setPrecision(3);
            sign.setTableID(tableId);
            sign.setID(UUIDUtils.newUUIDStr());
            sign.setKind(ColumnModelKind.DEFAULT);
            sign.setNullAble(false);
            sign.setTitle("\u65b9\u5411");
            designColumnModelDefines.add((DesignColumnModelDefine)sign);
            DesignColumnModelDefineImpl zbValue = new DesignColumnModelDefineImpl();
            zbValue.setCode("ZBVALUE");
            zbValue.setName("ZBVALUE");
            zbValue.setColumnType(ColumnModelType.STRING);
            zbValue.setPrecision(350);
            zbValue.setDecimal(2);
            zbValue.setTableID(tableId);
            zbValue.setID(UUIDUtils.newUUIDStr());
            zbValue.setKind(ColumnModelKind.DEFAULT);
            zbValue.setNullAble(true);
            zbValue.setTitle("\u6307\u6807\u503c");
            designColumnModelDefines.add((DesignColumnModelDefine)zbValue);
            DesignColumnModelDefineImpl zbValueStr = new DesignColumnModelDefineImpl();
            zbValueStr.setCode("ZBVALUETYPE");
            zbValueStr.setName("ZBVALUETYPE");
            zbValueStr.setColumnType(ColumnModelType.STRING);
            zbValueStr.setPrecision(20);
            zbValueStr.setTableID(tableId);
            zbValueStr.setID(UUIDUtils.newUUIDStr());
            zbValueStr.setKind(ColumnModelKind.DEFAULT);
            zbValueStr.setNullAble(true);
            zbValueStr.setTitle("\u6307\u6807\u503c\uff08\u5b57\u7b26\u578b\uff09");
            designColumnModelDefines.add((DesignColumnModelDefine)zbValueStr);
            return designColumnModelDefines;
        }

        @Override
        public List<TableIndexVO> getIndexList() {
            ArrayList<TableIndexVO> tableIndexVOS = new ArrayList<TableIndexVO>();
            TableIndexVO fixedRtidFid = new TableIndexVO();
            fixedRtidFid.setIndexName("IDX_RESULT_FIXED_R_FID");
            fixedRtidFid.setIndexType(IndexModelType.NORMAL);
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("REQUESTTASKID");
            fields.add("FORMID");
            fixedRtidFid.setFields(fields);
            tableIndexVOS.add(fixedRtidFid);
            return tableIndexVOS;
        }
    }
    ,
    BDE_RESULT_FLOATDEFINE("BDE_RESULT_FLOATDEFINE", "\u6d6e\u52a8\u6307\u6807\u8868"){

        @Override
        public List<DesignColumnModelDefine> getFieldList(String tableId) {
            ArrayList<DesignColumnModelDefine> designColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefineImpl requestTaskId = new DesignColumnModelDefineImpl();
            requestTaskId.setCode("REQUESTTASKID");
            requestTaskId.setName("REQUESTTASKID");
            requestTaskId.setColumnType(ColumnModelType.STRING);
            requestTaskId.setPrecision(16);
            requestTaskId.setTableID(tableId);
            requestTaskId.setID(UUIDUtils.newUUIDStr());
            requestTaskId.setKind(ColumnModelKind.DEFAULT);
            requestTaskId.setNullAble(false);
            requestTaskId.setTitle("\u8bf7\u6c42\u4efb\u52a1ID");
            designColumnModelDefines.add((DesignColumnModelDefine)requestTaskId);
            DesignColumnModelDefineImpl formId = new DesignColumnModelDefineImpl();
            formId.setCode("FORMID");
            formId.setName("FORMID");
            formId.setColumnType(ColumnModelType.STRING);
            formId.setPrecision(36);
            formId.setTableID(tableId);
            formId.setID(UUIDUtils.newUUIDStr());
            formId.setKind(ColumnModelKind.DEFAULT);
            formId.setNullAble(false);
            formId.setTitle("\u8868ID");
            designColumnModelDefines.add((DesignColumnModelDefine)formId);
            DesignColumnModelDefineImpl regionId = new DesignColumnModelDefineImpl();
            regionId.setCode("REGIONID");
            regionId.setName("REGIONID");
            regionId.setColumnType(ColumnModelType.STRING);
            regionId.setPrecision(36);
            regionId.setTableID(tableId);
            regionId.setID(UUIDUtils.newUUIDStr());
            regionId.setKind(ColumnModelKind.DEFAULT);
            regionId.setNullAble(false);
            regionId.setTitle("\u533a\u57dfID");
            designColumnModelDefines.add((DesignColumnModelDefine)regionId);
            DesignColumnModelDefineImpl requestRegionid = new DesignColumnModelDefineImpl();
            requestRegionid.setCode("REQUESTREGIONID");
            requestRegionid.setName("REQUESTREGIONID");
            requestRegionid.setColumnType(ColumnModelType.STRING);
            requestRegionid.setPrecision(32);
            requestRegionid.setTableID(tableId);
            requestRegionid.setID(UUIDUtils.newUUIDStr());
            requestRegionid.setKind(ColumnModelKind.DEFAULT);
            requestRegionid.setNullAble(false);
            requestRegionid.setTitle("\u8bf7\u6c42\u533a\u57dfID");
            designColumnModelDefines.add((DesignColumnModelDefine)requestRegionid);
            DesignColumnModelDefineImpl fieldDefineOrder = new DesignColumnModelDefineImpl();
            fieldDefineOrder.setCode("FIELDDEFINEORDER");
            fieldDefineOrder.setName("FIELDDEFINEORDER");
            fieldDefineOrder.setColumnType(ColumnModelType.INTEGER);
            fieldDefineOrder.setPrecision(11);
            fieldDefineOrder.setTableID(tableId);
            fieldDefineOrder.setID(UUIDUtils.newUUIDStr());
            fieldDefineOrder.setKind(ColumnModelKind.DEFAULT);
            fieldDefineOrder.setNullAble(false);
            fieldDefineOrder.setTitle("\u5217\u6392\u5e8f");
            designColumnModelDefines.add((DesignColumnModelDefine)fieldDefineOrder);
            DesignColumnModelDefineImpl fieldDefineid = new DesignColumnModelDefineImpl();
            fieldDefineid.setCode("FIELDDEFINEID");
            fieldDefineid.setName("FIELDDEFINEID");
            fieldDefineid.setColumnType(ColumnModelType.STRING);
            fieldDefineid.setPrecision(36);
            fieldDefineid.setTableID(tableId);
            fieldDefineid.setID(UUIDUtils.newUUIDStr());
            fieldDefineid.setKind(ColumnModelKind.DEFAULT);
            fieldDefineid.setNullAble(true);
            fieldDefineid.setTitle("\u6307\u6807ID");
            designColumnModelDefines.add((DesignColumnModelDefine)fieldDefineid);
            DesignColumnModelDefineImpl fieldDefineType = new DesignColumnModelDefineImpl();
            fieldDefineType.setCode("FIELDDEFINETYPE");
            fieldDefineType.setName("FIELDDEFINETYPE");
            fieldDefineType.setColumnType(ColumnModelType.STRING);
            fieldDefineType.setPrecision(36);
            fieldDefineType.setTableID(tableId);
            fieldDefineType.setID(UUIDUtils.newUUIDStr());
            fieldDefineType.setKind(ColumnModelKind.DEFAULT);
            fieldDefineType.setNullAble(false);
            fieldDefineType.setTitle("\u6307\u6807\u7c7b\u578b");
            designColumnModelDefines.add((DesignColumnModelDefine)fieldDefineType);
            DesignColumnModelDefineImpl fieldDefineName = new DesignColumnModelDefineImpl();
            fieldDefineName.setCode("FIELDDEFINENAME");
            fieldDefineName.setName("FIELDDEFINENAME");
            fieldDefineName.setColumnType(ColumnModelType.STRING);
            fieldDefineName.setPrecision(36);
            fieldDefineName.setTableID(tableId);
            fieldDefineName.setID(UUIDUtils.newUUIDStr());
            fieldDefineName.setKind(ColumnModelKind.DEFAULT);
            fieldDefineName.setNullAble(false);
            fieldDefineName.setTitle("\u6307\u6807\u540d\u79f0");
            designColumnModelDefines.add((DesignColumnModelDefine)fieldDefineName);
            return designColumnModelDefines;
        }

        @Override
        public List<TableIndexVO> getIndexList() {
            ArrayList<TableIndexVO> tableIndexVOS = new ArrayList<TableIndexVO>();
            TableIndexVO fdefRtidFid = new TableIndexVO();
            fdefRtidFid.setIndexName("IDX_RESULT_FDEFG_RID");
            fdefRtidFid.setIndexType(IndexModelType.NORMAL);
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("REQUESTREGIONID");
            fdefRtidFid.setFields(fields);
            tableIndexVOS.add(fdefRtidFid);
            TableIndexVO definIndex = new TableIndexVO();
            definIndex.setIndexName("IDX_RESULT_DEFG_RQ_RG_FID");
            definIndex.setIndexType(IndexModelType.NORMAL);
            ArrayList<String> fields1 = new ArrayList<String>();
            fields1.add("REQUESTTASKID");
            fields1.add("FORMID");
            fields1.add("REGIONID");
            definIndex.setFields(fields1);
            tableIndexVOS.add(definIndex);
            return tableIndexVOS;
        }
    }
    ,
    BDE_RESULT_FLOATROW("BDE_RESULT_FLOATROW", "\u6d6e\u52a8\u884c\u7ed3\u679c\u8868"){

        @Override
        public List<DesignColumnModelDefine> getFieldList(String tableId) {
            ArrayList<DesignColumnModelDefine> designColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefineImpl requestRegionid = new DesignColumnModelDefineImpl();
            requestRegionid.setCode("REQUESTREGIONID");
            requestRegionid.setName("REQUESTREGIONID");
            requestRegionid.setColumnType(ColumnModelType.STRING);
            requestRegionid.setPrecision(32);
            requestRegionid.setTableID(tableId);
            requestRegionid.setID(UUIDUtils.newUUIDStr());
            requestRegionid.setKind(ColumnModelKind.DEFAULT);
            requestRegionid.setNullAble(false);
            requestRegionid.setTitle("\u8bf7\u6c42\u533a\u57dfID");
            designColumnModelDefines.add((DesignColumnModelDefine)requestRegionid);
            DesignColumnModelDefineImpl floatOrder = new DesignColumnModelDefineImpl();
            floatOrder.setCode("FLOATORDER");
            floatOrder.setName("FLOATORDER");
            floatOrder.setColumnType(ColumnModelType.INTEGER);
            floatOrder.setPrecision(11);
            floatOrder.setTableID(tableId);
            floatOrder.setID(UUIDUtils.newUUIDStr());
            floatOrder.setKind(ColumnModelKind.DEFAULT);
            floatOrder.setNullAble(false);
            floatOrder.setTitle("\u884c\u6392\u5e8f");
            designColumnModelDefines.add((DesignColumnModelDefine)floatOrder);
            for (int i = 0; i < FetchResultConfig.fetchResultTableFieldNum; ++i) {
                DesignColumnModelDefineImpl zbValue = new DesignColumnModelDefineImpl();
                zbValue.setCode(String.format("ZBVALUE_%1$d", i));
                zbValue.setName(String.format("ZBVALUE_%1$d", i));
                zbValue.setColumnType(ColumnModelType.STRING);
                zbValue.setPrecision(350);
                zbValue.setTableID(tableId);
                zbValue.setID(UUIDUtils.newUUIDStr());
                zbValue.setKind(ColumnModelKind.DEFAULT);
                zbValue.setNullAble(true);
                zbValue.setTitle(String.format("\u6307\u6807\u503c%1$d", i));
                designColumnModelDefines.add((DesignColumnModelDefine)zbValue);
            }
            return designColumnModelDefines;
        }

        @Override
        public List<TableIndexVO> getIndexList() {
            ArrayList<TableIndexVO> tableIndexVOS = new ArrayList<TableIndexVO>();
            TableIndexVO frowRtidFid = new TableIndexVO();
            frowRtidFid.setIndexName("PK_RESULT_FROW_RID");
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("REQUESTREGIONID");
            fields.add("FLOATORDER");
            frowRtidFid.setFields(fields);
            tableIndexVOS.add(frowRtidFid);
            return tableIndexVOS;
        }
    }
    ,
    BDE_RESULT_FLOATCOL("BDE_RESULT_FLOATCOL", "\u6d6e\u52a8\u5217\u7ed3\u679c\u8868"){

        @Override
        public List<DesignColumnModelDefine> getFieldList(String tableId) {
            ArrayList<DesignColumnModelDefine> designColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
            DesignColumnModelDefineImpl requestRegionid = new DesignColumnModelDefineImpl();
            requestRegionid.setCode("REQUESTREGIONID");
            requestRegionid.setName("REQUESTREGIONID");
            requestRegionid.setColumnType(ColumnModelType.STRING);
            requestRegionid.setPrecision(32);
            requestRegionid.setTableID(tableId);
            requestRegionid.setID(UUIDUtils.newUUIDStr());
            requestRegionid.setKind(ColumnModelKind.DEFAULT);
            requestRegionid.setNullAble(false);
            requestRegionid.setTitle("\u8bf7\u6c42\u533a\u57dfID");
            designColumnModelDefines.add((DesignColumnModelDefine)requestRegionid);
            DesignColumnModelDefineImpl floatOrder = new DesignColumnModelDefineImpl();
            floatOrder.setCode("FLOATORDER");
            floatOrder.setName("FLOATORDER");
            floatOrder.setColumnType(ColumnModelType.INTEGER);
            floatOrder.setPrecision(11);
            floatOrder.setTableID(tableId);
            floatOrder.setID(UUIDUtils.newUUIDStr());
            floatOrder.setKind(ColumnModelKind.DEFAULT);
            floatOrder.setNullAble(false);
            floatOrder.setTitle("\u884c\u6392\u5e8f");
            designColumnModelDefines.add((DesignColumnModelDefine)floatOrder);
            DesignColumnModelDefineImpl fieldDefineid = new DesignColumnModelDefineImpl();
            fieldDefineid.setCode("FIELDDEFINEID");
            fieldDefineid.setName("FIELDDEFINEID");
            fieldDefineid.setColumnType(ColumnModelType.STRING);
            fieldDefineid.setPrecision(36);
            fieldDefineid.setTableID(tableId);
            fieldDefineid.setID(UUIDUtils.newUUIDStr());
            fieldDefineid.setKind(ColumnModelKind.DEFAULT);
            fieldDefineid.setNullAble(false);
            fieldDefineid.setTitle("\u6307\u6807ID");
            designColumnModelDefines.add((DesignColumnModelDefine)fieldDefineid);
            DesignColumnModelDefineImpl fetchSettingId = new DesignColumnModelDefineImpl();
            fetchSettingId.setCode("FETCHSETTINGID");
            fetchSettingId.setName("FETCHSETTINGID");
            fetchSettingId.setColumnType(ColumnModelType.STRING);
            fetchSettingId.setPrecision(20);
            fetchSettingId.setTableID(tableId);
            fetchSettingId.setID(UUIDUtils.newUUIDStr());
            fetchSettingId.setKind(ColumnModelKind.DEFAULT);
            fetchSettingId.setNullAble(true);
            fetchSettingId.setTitle("\u53d6\u6570\u8bbe\u7f6eID");
            designColumnModelDefines.add((DesignColumnModelDefine)fetchSettingId);
            DesignColumnModelDefineImpl sign = new DesignColumnModelDefineImpl();
            sign.setCode("SIGN");
            sign.setName("SIGN");
            sign.setColumnType(ColumnModelType.STRING);
            sign.setPrecision(3);
            sign.setTableID(tableId);
            sign.setID(UUIDUtils.newUUIDStr());
            sign.setKind(ColumnModelKind.DEFAULT);
            sign.setNullAble(false);
            sign.setTitle("\u65b9\u5411");
            designColumnModelDefines.add((DesignColumnModelDefine)sign);
            DesignColumnModelDefineImpl zbValue = new DesignColumnModelDefineImpl();
            zbValue.setCode("ZBVALUE");
            zbValue.setName("ZBVALUE");
            zbValue.setColumnType(ColumnModelType.STRING);
            zbValue.setPrecision(350);
            zbValue.setTableID(tableId);
            zbValue.setID(UUIDUtils.newUUIDStr());
            zbValue.setKind(ColumnModelKind.DEFAULT);
            zbValue.setNullAble(true);
            zbValue.setTitle("\u6307\u6807\u503c");
            designColumnModelDefines.add((DesignColumnModelDefine)zbValue);
            DesignColumnModelDefineImpl zbValueStr = new DesignColumnModelDefineImpl();
            zbValueStr.setTableID(tableId);
            zbValueStr.setCode("ZBVALUETYPE");
            zbValueStr.setName("ZBVALUETYPE");
            zbValueStr.setColumnType(ColumnModelType.STRING);
            zbValueStr.setPrecision(20);
            zbValueStr.setID(UUIDUtils.newUUIDStr());
            zbValueStr.setKind(ColumnModelKind.DEFAULT);
            zbValueStr.setNullAble(true);
            zbValueStr.setTitle("\u6307\u6807\u7c7b\u578b\uff08\u5b57\u7b26\u578b\uff09");
            designColumnModelDefines.add((DesignColumnModelDefine)zbValueStr);
            return designColumnModelDefines;
        }

        @Override
        public List<TableIndexVO> getIndexList() {
            ArrayList<TableIndexVO> tableIndexVOS = new ArrayList<TableIndexVO>();
            TableIndexVO fcolRtidFid = new TableIndexVO();
            fcolRtidFid.setIndexName("IDX_RESULT_FCOL_RID");
            fcolRtidFid.setIndexType(IndexModelType.NORMAL);
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("REQUESTREGIONID");
            fields.add("FLOATORDER");
            fcolRtidFid.setFields(fields);
            tableIndexVOS.add(fcolRtidFid);
            return tableIndexVOS;
        }
    };

    private final String tableName;
    private final String tableTitle;

    private FetchResultTableEnum(String tableName, String tableTitle) {
        this.tableName = tableName;
        this.tableTitle = tableTitle;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public static FetchResultTableEnum getEnumByCode(String tableName) {
        for (FetchResultTableEnum fetchResultTableEnum : FetchResultTableEnum.values()) {
            if (!fetchResultTableEnum.getTableName().equals(tableName)) continue;
            return fetchResultTableEnum;
        }
        return null;
    }

    public abstract List<DesignColumnModelDefine> getFieldList(String var1);

    public abstract List<TableIndexVO> getIndexList();
}

