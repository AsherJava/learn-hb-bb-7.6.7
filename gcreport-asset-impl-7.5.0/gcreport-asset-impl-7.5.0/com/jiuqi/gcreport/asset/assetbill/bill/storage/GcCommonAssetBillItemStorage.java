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
package com.jiuqi.gcreport.asset.assetbill.bill.storage;

import com.jiuqi.gcreport.billcore.util.GcStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class GcCommonAssetBillItemStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcCommonAssetBillItemStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_COMMONASSETBILLITEM");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_COMMONASSETBILLITEM");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u5e38\u89c4\u8d44\u4ea7\u5355\u636e\u5b50\u8868");
        List columns = GcStorageUtil.getBillSubTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u91c7\u8d2d\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("OPPUNITCODE").columnTitle("\u9500\u552e\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAMONTH").columnTitle("\u6298\u65e7\u6708\u4efd").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAAMT").columnTitle("\u6298\u65e7\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("MEMO").columnTitle("\u63cf\u8ff0\u4fe1\u606f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCATYPE").columnTitle("\u6298\u65e7\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("OGATYPE").columnTitle("\u6cb9\u6c14\u8d44\u4ea7\u6298\u65e7\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.FIXED).mapping("MD_OGATYPE.CODE");
        dataModelDO.addIndex("IDX_COMBASSET_OPPUNIT").columnList(new String[]{"OPPUNITCODE"});
        return dataModelDO;
    }
}

