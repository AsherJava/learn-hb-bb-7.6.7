/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.bill.storage;

import com.jiuqi.dc.bill.util.DcStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class DcAgeUnclearedBillItemStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = DcAgeUnclearedBillItemStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("DC_BILL_AGEUNCLEAREDITEM");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(DcStorageUtil.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("DC_BILL_AGEUNCLEAREDITEM");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u5b50\u8868");
        List<DataModelColumn> columns = DcStorageUtil.getBillSubTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("DIGEST").columnTitle("\u6458\u8981").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{1000}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SUBJECTCODE").columnTitle("\u79d1\u76ee\u4ee3\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CURRENCYCODE").columnTitle("\u5e01\u522b\u4ee3\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ORGNBALANCE").columnTitle("\u539f\u5e01\u4f59\u989d").defaultVal("0.00").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BALANCE").columnTitle("\u672c\u5e01\u4f59\u989d").defaultVal("0.00").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BIZDATE").columnTitle("\u4e1a\u52a1\u65e5\u671f").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("EXPIREDATE").columnTitle("\u5230\u671f\u65e5").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addIndex("IDX_BILL_UNCLEAREDITEM_MID").columnList(new String[]{"MASTERID"});
        return dataModelDO;
    }
}

