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

public class GcInvestBillItemStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcInvestBillItemStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_INVESTBILLITEM");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_INVESTBILLITEM");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u6295\u8d44\u5355\u636e\u5b50\u8868");
        List columns = GcStorageUtil.getBillSubTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("CHANGESCENARIO").columnTitle("\u53d8\u52a8\u573a\u666f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_CHANGERATIO.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEDATE").columnTitle("\u53d8\u52a8\u65f6\u671f").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEAMT").columnTitle("\u53d8\u52a8\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGERATIO").columnTitle("\u80a1\u6743\u53d8\u52a8\u6bd4\u4f8b").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FEWSHAREHOLDERADD").columnTitle("\u5c11\u6570\u80a1\u4e1c\u65b0\u589e\u8d44\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISPOSINGPRICE").columnTitle("\u5904\u7f6e\u5bf9\u4ef7").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTYEAR").columnTitle("\u5e74\u5ea6").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTPERIOD").columnTitle("\u6708").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCID").columnTitle("\u6765\u6e90ID").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCTYPE").columnTitle("\u6765\u6e90\u7c7b\u578b").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INPUTDATE").columnTitle("\u5f55\u5165\u671f\u95f4").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("STATUS").columnTitle("\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).mapping("MD_SUBSTATUS.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("VCHRUNIQUECODE").columnTitle("\u51ed\u8bc1\u552f\u4e00\u6807\u8bc6").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("VCHRNUM").columnTitle("\u51ed\u8bc1\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{50}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ASSISTCOMB").columnTitle("\u8f85\u52a9\u9879").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{30}).columnAttr(DataModelType.ColumnAttr.FIXED);
        return dataModelDO;
    }
}

