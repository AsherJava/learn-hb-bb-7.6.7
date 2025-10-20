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

public class DcAgeUnclearedBillStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = DcAgeUnclearedBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("DC_BILL_AGEUNCLEARED");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(DcStorageUtil.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("DC_BILL_AGEUNCLEARED");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u4e3b\u8868");
        List<DataModelColumn> columns = DcStorageUtil.getBillMasterTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u7ec4\u7ec7\u673a\u6784").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_ORG.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTYEAR").columnTitle("\u5e74").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTPERIOD").columnTitle("\u671f\u95f4").mapping("EM_ACCTPERIOD.VAL").mappingType(Integer.valueOf(2)).columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("VALIDFLAG").columnTitle("\u662f\u5426\u6709\u6548").defaultVal("0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LOCKSTATE").columnTitle("\u6570\u636e\u9501\u5b9a\u72b6\u6001").defaultVal("1").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addIndex("IDX_DCBILL_UNCLEARED_U_Y_P").columnList(new String[]{"UNITCODE", "ACCTYEAR", "ACCTPERIOD"});
        return dataModelDO;
    }
}

