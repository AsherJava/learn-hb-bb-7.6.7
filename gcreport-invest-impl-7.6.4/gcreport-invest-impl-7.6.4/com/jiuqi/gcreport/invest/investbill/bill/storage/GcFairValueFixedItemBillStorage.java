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

public class GcFairValueFixedItemBillStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcFairValueFixedItemBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_FVCH_FIXEDITEM");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_FVCH_FIXEDITEM");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u516c\u5141\u4ef7\u503c\u56fa\u5b9a/\u65e0\u5f62\u8d44\u4ea7\u8868");
        List columns = GcStorageUtil.getBillSubTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u6295\u8d44\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INVESTEDUNIT").columnTitle("\u88ab\u6295\u8d44\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ASSETTYPE").columnTitle("\u8d44\u4ea7\u7c7b\u522b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_ASSETTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ASSETTITLE").columnTitle("\u8d44\u4ea7\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BOOKVALUE").columnTitle("\u8d26\u9762\u4ef7\u503c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FAIRVALUE").columnTitle("\u516c\u5141\u4ef7\u503c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DIFFAMT").columnTitle("\u5dee\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BIZDATE").columnTitle("\u589e\u52a0\u65e5\u671f").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("REMAININGTERM").columnTitle("\u5e76\u8d2d\u65e5\u5269\u4f59\u4f7f\u7528\u671f\u9650\uff08\u6708\uff09").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("RMVALUERATE").columnTitle("\u6b8b\u503c\u7387\uff08%\uff09").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAAMTIZSUBJECTCODE").columnTitle("\u6298\u65e7/\u644a\u9500\u79d1\u76ee").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_DPCAAMTIZSUBJECT.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAAMTIZ").columnTitle("\u6708\u6298\u65e7/\u644a\u9500\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DPCAAMTIZDEADLINE").columnTitle("\u6298\u65e7/\u644a\u9500\u622a\u6b62\u65e5").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISPOSEDATE").columnTitle("\u5904\u7f6e\u65f6\u95f4").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DSPEPRFLS").columnTitle("\u5904\u7f6e\u635f\u76ca").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DSPESUBJECTCODE").columnTitle("\u5904\u7f6e\u79d1\u76ee").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_DSPESUBJECT.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCTYPE").columnTitle("\u5f55\u5165\u7c7b\u578b").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTYEAR").columnTitle("\u5e74\u5ea6").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCID").columnTitle("\u6765\u6e90ID").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.FIXED);
        return dataModelDO;
    }
}

