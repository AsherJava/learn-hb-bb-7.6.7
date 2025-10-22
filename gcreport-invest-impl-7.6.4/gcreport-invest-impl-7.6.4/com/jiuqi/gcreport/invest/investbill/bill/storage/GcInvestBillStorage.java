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

public class GcInvestBillStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcInvestBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_INVESTBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcStorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_INVESTBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u6295\u8d44\u5355\u636e\u4e3b\u8868");
        List columns = GcStorageUtil.getBillMasterTableTemplateFields();
        dataModelDO.setColumns(columns);
        dataModelDO.addColumn("UNITCODE").columnTitle("\u6295\u8d44\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("INVESTEDUNIT").columnTitle("\u88ab\u6295\u8d44\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).mapping("MD_ORG_CORPORATE.CODE").mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("UNITRELATIONSOURCE").columnTitle("\u6295\u8d44\u6765\u6e90\u65b9\u5f0f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_UNITRELATIONSOURCE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCOUNTINGMETHOD").columnTitle("\u6838\u7b97\u65b9\u6cd5").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_ACCOUNTINGMETHOD.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDEQUITYRATIO").columnTitle("\u671f\u672b\u80a1\u6743\u6bd4\u4f8b(%)").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINBOOKBALANCE").columnTitle("\u671f\u521d\u8d26\u9762\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDBOOKBALANCE").columnTitle("\u671f\u672b\u8d26\u9762\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGININVSTDEVALUEPREP").columnTitle("\u671f\u521d\u6295\u8d44\u51cf\u503c\u51c6\u5907").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDINVSTDEVALUEPREP").columnTitle("\u671f\u672b\u6295\u8d44\u51cf\u503c\u51c6\u5907").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDGOODWILL").columnTitle("\u5546\u8a89\u539f\u503c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINGOODWILLDEVALUE").columnTitle("\u671f\u521d\u5546\u8a89\u51cf\u503c\u51c6\u5907").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDGOODWILLDEVALUE").columnTitle("\u671f\u672b\u5546\u8a89\u51cf\u503c\u51c6\u5907").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CASHDIVIDENDS").columnTitle("\u5206\u6d3e\u73b0\u91d1\u80a1\u5229").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISPOSALINCOME").columnTitle("\u56e0\u5904\u7f6e\u4ea7\u751f\u7684\u5904\u7f6e\u6536\u76ca").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ACCTYEAR").columnTitle("\u5e74\u5ea6").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("PERIOD").columnTitle("\u6708\u5ea6").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("VALIDPERIOD").columnTitle("\u6708").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INVALIDPERIOD").columnTitle("\u5931\u6548\u6708").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("OFFSETINITFLAG").columnTitle("\u6295\u8d44\u62b5\u9500\u521d\u59cb").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FAIRVALUEADJUSTFLAG").columnTitle("\u516c\u5141\u4ef7\u503c\u8c03\u6574").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FAIRVALUEOFFSETFLAG").columnTitle("\u516c\u5141\u4ef7\u503c\u8c03\u6574\u62b5\u9500\u521d\u59cb").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("MERGETYPE").columnTitle("\u5408\u5e76\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).mapping("MD_MERGETYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCTYPE").columnTitle("\u5f55\u5165\u7c7b\u578b").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{4}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("UPDATETIME").columnTitle("\u66f4\u65b0\u65f6\u95f4").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SRCID").columnTitle("\u6765\u6e90ID").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{36}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SHOWCHANGEAREA").columnTitle("\u663e\u793a\u6295\u8d44\u6d6e\u52a8\u533a\u57df").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INVESTBILLSTATE").columnTitle("\u6295\u8d44\u5355\u636e\u72b6\u6001:NEW\u65b0\u589e\u3001EDIT\u4fee\u6539\u3001CHANGE\u53d8\u52a8").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINEQUITYRATIO").columnTitle("\u671f\u521d\u80a1\u6743\u6bd4\u4f8b(%)").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INITIALINVESTMENTCOST").columnTitle("\u521d\u59cb\u6295\u8d44\u6210\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEEQUITYRATIO").columnTitle("\u672c\u5e74\u53d8\u52a8\u80a1\u6743\u6bd4\u4f8b(%)").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEBOOKBALANCE").columnTitle("\u672c\u5e74\u53d8\u52a8\u8d26\u9762\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEINVSTDEVALUEPREP").columnTitle("\u672c\u5e74\u53d8\u52a8\u6295\u8d44\u51cf\u503c\u51c6\u5907").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEGOODWILLDEVALUE").columnTitle("\u672c\u5e74\u53d8\u52a8\u5546\u8a89\u51cf\u503c\u51c6\u5907").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEINVESTMENTCOST").columnTitle("\u672c\u5e74\u53d8\u52a8\u6295\u8d44\u6210\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINPROFITLOSSADJUSTMENT").columnTitle("\u671f\u521d\u635f\u76ca\u8c03\u6574").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEPROFITLOSSADJUSTMENT").columnTitle("\u672c\u5e74\u53d8\u52a8\u635f\u76ca\u8c03\u6574").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDPROFITLOSSADJUSTMENT").columnTitle("\u671f\u672b\u635f\u76ca\u8c03\u6574").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINOTHERCOMPREHENSIVEINCOME").columnTitle("\u671f\u521d\u5176\u4ed6\u7efc\u5408\u6536\u76ca").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEOTHERCOMPREHENSIVEINCOME").columnTitle("\u672c\u5e74\u53d8\u52a8\u5176\u4ed6\u7efc\u5408\u6536\u76ca").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDOTHERCOMPREHENSIVEINCOME").columnTitle("\u671f\u672b\u5176\u4ed6\u7efc\u5408\u6536\u76ca").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINOTHERCAPITALRESERVE").columnTitle("\u671f\u521d\u5176\u4ed6\u8d44\u672c\u516c\u79ef").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEOTHERCAPITALRESERVE").columnTitle("\u672c\u5e74\u53d8\u52a8\u5176\u4ed6\u8d44\u672c\u516c\u79ef").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDOTHERCAPITALRESERVE").columnTitle("\u671f\u672b\u5176\u4ed6\u8d44\u672c\u516c\u79ef").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGINFAIRVALUE").columnTitle("\u671f\u521d\u516c\u5141\u4ef7\u503c\u53d8\u52a8").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CHANGEFAIRVALUE").columnTitle("\u672c\u5e74\u53d8\u52a8\u516c\u5141\u4ef7\u503c\u53d8\u52a8").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDFAIRVALUE").columnTitle("\u671f\u672b\u516c\u5141\u4ef7\u503c\u53d8\u52a8").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BEGININVESTMENTCOST").columnTitle("\u671f\u521d\u6295\u8d44\u6210\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ENDINVESTMENTCOST").columnTitle("\u671f\u672b\u6295\u8d44\u6210\u672c").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 2}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISPOSEDATE").columnTitle("\u5904\u7f6e\u65f6\u95f4").columnType(DataModelType.ColumnType.DATE).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DISPOSEFLAG").columnTitle("\u5904\u7f6e\u72b6\u6001").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("COMPREEQUITYRATIO").columnTitle("\u5bf9\u6295\u8d44\u5355\u4f4d\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("INVESTEDCOMPREEQUITYRATIO").columnTitle("\u5bf9\u88ab\u6295\u8d44\u5355\u4f4d\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CURRENCYCODE").columnTitle("\u5e01\u79cd").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).mapping("MD_CURRENCY.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("CONVERTBOOKBALANCE").columnTitle("\u6298\u7b97\u540e\u6295\u8d44\u8d26\u9762\u4f59\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{23, 6}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("STATUS").columnTitle("\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).mapping("MD_SUBSTATUS.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addIndex("IDX_INVESTBILL_BILLCODE").columnList(new String[]{"BILLCODE"}).unique(Boolean.valueOf(true));
        return dataModelDO;
    }
}

