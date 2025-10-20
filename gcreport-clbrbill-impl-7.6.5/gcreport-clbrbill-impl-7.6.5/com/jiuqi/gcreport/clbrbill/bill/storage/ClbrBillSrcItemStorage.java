/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.va.datamodel.service.VaDataModelTemplateService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplate
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.clbrbill.bill.storage;

import com.jiuqi.gcreport.clbrbill.utils.ClbrBillUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class ClbrBillSrcItemStorage {
    private ClbrBillSrcItemStorage() {
    }

    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = ClbrBillSrcItemStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_CLBRSRCBILLITEM");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(ClbrBillUtils.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_CLBRSRCBILLITEM");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u5171\u4eab\u5355\u636e\u4fe1\u606f\u5b50\u8868");
        VaDataModelTemplateService templateService = (VaDataModelTemplateService)SpringBeanUtils.getBean(VaDataModelTemplateService.class);
        DataModelTemplate template = templateService.getSubTemplate(DataModelType.BizType.BILL.name(), Integer.valueOf(2));
        List templateFields = template.getTemplateFields();
        templateFields.add(new DataModelColumn().columnName("SYSCODE").columnTitle("\u5171\u4eab\u6570\u636e\u6e20\u9053").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_SYSCODE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("SRCBILLTYPE").columnTitle("\u5171\u4eab\u5355\u636e\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_BILLTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("SRCBILLDEFINE").columnTitle("\u5171\u4eab\u5355\u636e\u5b9a\u4e49").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("SRCBILLNAME").columnTitle("\u5171\u4eab\u5355\u636e\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("SRCBILLCODE").columnTitle("\u5171\u4eab\u5355\u636e\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("SRCBILLSTATE").columnTitle("\u5171\u4eab\u5355\u636e\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("CLBRBILLTYPE").columnTitle("\u534f\u540c\u89d2\u8272").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_CLBRBILLTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("BILLDEFINE").columnTitle("\u534f\u540c\u5355\u636e\u5b9a\u4e49").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_CLBRBILLDEFINE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("VCHRACCOUNTPERIOD").columnTitle("\u51ed\u8bc1\u5165\u8d26\u8d26\u671f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("VCHRNUM").columnTitle("\u51ed\u8bc1\u53f7\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("VCHRCREATETIME").columnTitle("\u51ed\u8bc1\u751f\u6210\u5165\u8d26\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("AMT").columnTitle("\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        dataModelDO.setColumns(templateFields);
        return dataModelDO;
    }
}

