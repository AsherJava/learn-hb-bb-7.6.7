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
package com.jiuqi.gcreport.lease.leasebill.bill.storage;

import com.jiuqi.gcreport.billcore.util.GcStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class GcTenantryBillItemStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcTenantryBillItemStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_TENANTRYITEMBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_TENANTRYITEMBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u627f\u79df\u65b9\u5355\u636e\u5b50\u8868");
        List columns = GcStorageUtil.getBillSubTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("CHANGEMONTH").columnTitle("\u6708\u5ea6").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINNAMT").columnTitle("\u79df\u8d41\u8d1f\u503a-\u671f\u521d\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("UNCONFIRMFINAMT").columnTitle("\u79df\u8d41\u8d1f\u503a-\u672a\u786e\u8ba4\u878d\u8d44\u8d39\u7528").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LEASEPAYAMT").columnTitle("\u79df\u8d41\u8d1f\u503a-\u79df\u8d41\u4ed8\u6b3e\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDAMT").columnTitle("\u79df\u8d41\u8d1f\u503a-\u671f\u672b\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("USEFINBEGINAMT").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7-\u671f\u521d\u4f59\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("MONTHLYDEPREAMT").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7-\u672c\u6708\u6298\u65e7\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("YEARDEPREAMT").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7-\u672c\u5e74\u6298\u65e7\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CUMULATIVEDEPRE").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7-\u7d2f\u8ba1\u6298\u65e7").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("USEFINENDAMT").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7-\u671f\u672b\u4f59\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INPUTTYPE").columnTitle("\u5f55\u5165\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).mapping("MD_INPUTTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        return dataModelDO;
    }
}

