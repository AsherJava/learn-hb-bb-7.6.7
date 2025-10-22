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

public class ClbrBillStorage {
    private static final String MD_ORG = "MD_ORG.CODE";
    private static final String MD_STAFF = "MD_STAFF.CODE";

    private ClbrBillStorage() {
    }

    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = ClbrBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_CLBRBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(ClbrBillUtils.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_CLBRBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u534f\u540c\u5355\u4e3b\u8868");
        VaDataModelTemplateService templateService = (VaDataModelTemplateService)SpringBeanUtils.getBean(VaDataModelTemplateService.class);
        DataModelTemplate template = templateService.getSubTemplate(DataModelType.BizType.BILL.name(), Integer.valueOf(1));
        List templateFields = template.getTemplateFields();
        templateFields.add(new DataModelColumn().columnName("CLBRSTATE").columnTitle("\u534f\u540c\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_CLBRSTATE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("BUSINESSTYPE").columnTitle("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{100}).mapping("MD_CLBRBUSINESSTYPE.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("ISTRIPARTITE").columnTitle("\u662f\u5426\u4e09\u65b9\u534f\u540c").columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).mappingType(Integer.valueOf(0)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("INITIATEORG").columnTitle("\u53d1\u8d77\u65b9\u516c\u53f8").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping(MD_ORG).mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("RECEIVEORG").columnTitle("\u63a5\u6536\u65b9\u516c\u53f8").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping(MD_ORG).mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("THIRDORG").columnTitle("\u7b2c\u4e09\u65b9\u516c\u53f8").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping(MD_ORG).mappingType(Integer.valueOf(4)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("INITIATEUSER").columnTitle("\u53d1\u8d77\u65b9\u7528\u6237").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping(MD_STAFF).mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("RECEIVEUSER").columnTitle("\u63a5\u6536\u65b9\u7528\u6237").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping(MD_STAFF).mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("THIRDUSER").columnTitle("\u7b2c\u4e09\u65b9\u7528\u6237").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping(MD_STAFF).mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("INITIATEAMT").columnTitle("\u53d1\u8d77\u65b9\u539f\u5e01\u4ef7\u7a0e\u5408\u8ba1").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("RECEIVEAMT").columnTitle("\u63a5\u6536\u65b9\u539f\u5e01\u4ef7\u7a0e\u5408\u8ba1").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("THIRDAMT").columnTitle("\u7b2c\u4e09\u65b9\u539f\u5e01\u4ef7\u7a0e\u5408\u8ba1").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("INITIATEQUOTEAMT").columnTitle("\u53d1\u8d77\u65b9\u5df2\u5f15\u7528\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("RECEIVEQUOTEAMT").columnTitle("\u63a5\u6536\u65b9\u5df2\u5f15\u7528\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("THIRDQUOTEAMT").columnTitle("\u7b2c\u4e09\u65b9\u5df2\u5f15\u7528\u91d1\u989d").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 6}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("CURRENCY").columnTitle("\u5e01\u79cd").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_CURRENCY.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("DIGEST").columnTitle("\u6458\u8981\u8bf4\u660e").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("CONTRACTCODE").columnTitle("\u5408\u540c\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("CONTRACTNAME").columnTitle("\u5408\u540c\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("PACKAGECODE").columnTitle("\u5408\u5e76\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("ABANDONUSER").columnTitle("\u4f5c\u5e9f\u4eba").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED));
        templateFields.add(new DataModelColumn().columnName("ABANDONTIME").columnTitle("\u4f5c\u5e9f\u65f6\u95f4").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.FIXED));
        dataModelDO.setColumns(templateFields);
        return dataModelDO;
    }
}

