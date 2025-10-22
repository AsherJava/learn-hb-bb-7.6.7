/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.util.GcStorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.invest.investbill.bill.storage;

import com.jiuqi.gcreport.billcore.util.GcStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class GcFairValueBillStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcFairValueBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_FVCHBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_FVCHBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u516c\u5141\u4ef7\u503c\u5355\u636e\u4e3b\u8868");
        List columns = GcStorageUtil.getBillMasterTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u6295\u8d44\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INVESTEDUNIT").columnTitle("\u88ab\u6295\u8d44\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTYEAR").columnTitle("\u5e74\u5ea6").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCID").columnTitle("\u6765\u6e90ID").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCTYPE").columnTitle("\u5f55\u5165\u7c7b\u578b").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        return dataModelDO;
    }
}

