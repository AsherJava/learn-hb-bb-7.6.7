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

public class GcTenantryBillStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcTenantryBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_TENANTRYBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_TENANTRYBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u627f\u79df\u65b9\u5355\u636e\u4e3b\u8868");
        List columns = GcStorageUtil.getBillMasterTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u627f\u79df\u65b9").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("OPPUNITCODE").columnTitle("\u51fa\u79df\u65b9").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("LEASEKIND").columnTitle("\u79df\u8d41\u6027\u8d28").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ASSETTYPE").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ASSETTITLE").columnTitle("\u8d44\u4ea7\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LEASEBEGINDATE").columnTitle("\u79df\u8d41\u5f00\u59cb\u65e5").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LEASEHOLD").columnTitle("\u79df\u8d41\u671f\u9650(\u6708)").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("COLLECTAMTPERIOD").columnTitle("\u6536\u6b3e\u5468\u671f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_PERIODTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("COLLECTAMT").columnTitle("\u6bcf\u671f\u4ed8\u6b3e\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LASTPERIODCOLLECTAMT").columnTitle("\u6700\u540e\u4e00\u671f\u6536\u6b3e\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITLEASEPAYAMT").columnTitle("\u521d\u59cb\u786e\u8ba4\u79df\u8d41\u4ed8\u6b3e\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITDIRECTAMT").columnTitle("\u521d\u59cb\u76f4\u63a5\u8d39\u7528").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITESTIMATEDEBT").columnTitle("\u521d\u59cb\u786e\u8ba4\u9884\u8ba1\u8d1f\u503a").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITUNFINAMT").columnTitle("\u521d\u59cb\u786e\u8ba4\u672a\u786e\u8ba4\u878d\u8d44\u8d39\u7528").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITUSEASSET").columnTitle("\u521d\u59cb\u786e\u8ba4\u4f7f\u7528\u6743\u8d44\u4ea7").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISCOUNTRATE").columnTitle("\u6298\u73b0\u7387(%)").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SUBJECTCODE").columnTitle("\u79df\u8d41\u6536\u5165\u79d1\u76ee").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("DPCALOSSSUBJECT").columnTitle("\u4f7f\u7528\u6743\u8d44\u4ea7\u6298\u65e7\u5bf9\u5e94\u635f\u76ca\u79d1\u76ee").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("RMVALUERATE").columnTitle("\u6b8b\u503c\u7387").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("MONTHLYDEPREAMT").columnTitle("\u6708\u6298\u65e7\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CURRENCYCODE").columnTitle("\u5e01\u79cd").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_CURRENCY.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("COLLECTBEGINDATE").columnTitle("\u6536\u6b3e\u5f00\u59cb\u65e5").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        return dataModelDO;
    }
}

