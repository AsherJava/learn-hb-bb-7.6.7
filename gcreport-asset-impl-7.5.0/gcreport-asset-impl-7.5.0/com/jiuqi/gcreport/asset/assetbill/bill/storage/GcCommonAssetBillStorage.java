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

public class GcCommonAssetBillStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcCommonAssetBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_COMMONASSETBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_COMMONASSETBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u5e38\u89c4\u8d44\u4ea7\u5355\u636e\u8868");
        List columns = GcStorageUtil.getBillMasterTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u91c7\u8d2d\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("OPPUNITCODE").columnTitle("\u9500\u552e\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ASSETTYPE").columnTitle("\u8d44\u4ea7\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_ASSETTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ASSETTITLE").columnTitle("\u8d44\u4ea7\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ASSETAMT").columnTitle("\u8d44\u4ea7\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SALECOST").columnTitle("\u9500\u552e\u6210\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("GROSSPROFITRATE").columnTitle("\u9500\u552e\u65b9\u6bdb\u5229\u7387").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("UNREALIZEDGAINLOSS").columnTitle("\u672a\u5b9e\u73b0\u635f\u76ca\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("RMVALUERATE").columnTitle("\u6b8b\u503c\u7387").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAMETHOD").columnTitle("\u6298\u65e7\u65b9\u5f0f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_DPCAMETHOD.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCARATE").columnTitle("\u7efc\u5408\u6298\u65e7\u7387").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAYEAR").columnTitle("\u6298\u65e7\u5e74\u9650(\u6708)").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{18, 0}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("DPCASUBJECT").columnTitle("\u6298\u65e7\u79d1\u76ee").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_DPCASUBJECT.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("USESTATE").columnTitle("\u4f7f\u7528\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_USESTATE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISPOSALDATE").columnTitle("\u5904\u7f6e\u65f6\u95f4").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DSPEPRFLS").columnTitle("\u5904\u7f6e\u635f\u76ca").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DSPESUBJECTCODE").columnTitle("\u5904\u7f6e\u79d1\u76ee").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_DSPESUBJECT.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("PURCHASEDATE").columnTitle("\u91c7\u8d2d\u65f6\u95f4").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ASSETSLOSS").columnTitle("\u8d44\u4ea7\u51cf\u503c\u635f\u5931").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ASSETSTATE").columnTitle("\u8f6c\u56fa\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_ASSETSTATE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BUTTONACTION").columnTitle("\u6309\u94ae\u52a8\u4f5c:NEW\u3001EDIT\u3001DISPOSAL").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITDPCAAMT").columnTitle("\u521d\u59cb\u6298\u65e7\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITDPCAYEAR").columnTitle("\u7cfb\u7edf\u6298\u65e7\u5f00\u59cb\u5e74").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CURRENCYCODE").columnTitle("\u5e01\u79cd").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).mapping("MD_CURRENCY.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addIndex("IDX_ASSETBILL_BILLCODE").columnList(new String[]{"BILLCODE"}).unique(Boolean.valueOf(true));
        return dataModelDO;
    }
}

