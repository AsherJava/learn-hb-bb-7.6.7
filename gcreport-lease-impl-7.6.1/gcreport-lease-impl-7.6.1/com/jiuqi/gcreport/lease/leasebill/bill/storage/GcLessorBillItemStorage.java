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

public class GcLessorBillItemStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcLessorBillItemStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_LESSORITEMBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_LESSORITEMBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u51fa\u79df\u65b9\u5355\u636e\u5b50\u8868");
        List columns = GcStorageUtil.getBillSubTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("CHANGEMONTH").columnTitle("\u6708\u5ea6").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINNAMT").columnTitle("\u5e94\u6536\u878d\u8d44\u79df\u8d41\u6b3e-\u671f\u521d\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("UNFINAMORTIZE").columnTitle("\u6536\u878d\u8d44\u79df\u8d41\u6b3e-\u672a\u5b9e\u73b0\u878d\u8d44\u6536\u76ca\u644a\u9500").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LEASEPAYAMT").columnTitle("\u5e94\u6536\u878d\u8d44\u79df\u8d41\u6b3e-\u79df\u8d41\u6536\u6b3e\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDAMT").columnTitle("\u5e94\u6536\u878d\u8d44\u79df\u8d41\u6b3e-\u671f\u672b\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("REASEFINBEGINAMT").columnTitle("\u79df\u8d41\u8d44\u4ea7-\u671f\u521d\u4f59\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("MONTHLYDEPREAMT").columnTitle("\u79df\u8d41\u8d44\u4ea7-\u672c\u6708\u6298\u65e7\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("YEARDEPREAMT").columnTitle("\u79df\u8d41\u8d44\u4ea7-\u672c\u5e74\u6298\u65e7\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CUMULATIVEDEPRE").columnTitle("\u79df\u8d41\u8d44\u4ea7-\u7d2f\u8ba1\u6298\u65e7").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("REASEFINENDAMT").columnTitle("\u79df\u8d41\u8d44\u4ea7-\u671f\u672b\u4f59\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INPUTTYPE").columnTitle("\u5f55\u5165\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).mapping("MD_INPUTTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        return dataModelDO;
    }
}

