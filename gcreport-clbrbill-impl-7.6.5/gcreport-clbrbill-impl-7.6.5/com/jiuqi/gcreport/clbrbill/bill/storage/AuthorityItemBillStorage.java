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

public class AuthorityItemBillStorage {
    private AuthorityItemBillStorage() {
    }

    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = AuthorityItemBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_AUTHORITYITEMBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(ClbrBillUtils.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_AUTHORITYITEMBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u6743\u9650\u7ba1\u7406\u5b50\u8868");
        VaDataModelTemplateService templateService = (VaDataModelTemplateService)SpringBeanUtils.getBean(VaDataModelTemplateService.class);
        DataModelTemplate template = templateService.getSubTemplate(DataModelType.BizType.BILL.name(), Integer.valueOf(2));
        List templateFields = template.getTemplateFields();
        templateFields.add(new DataModelColumn().columnName("ORGCODE").columnTitle("\u7528\u6237\u6240\u5c5e\u673a\u6784").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("MANAGEORGCODES").columnTitle("\u7528\u6237\u517c\u7ba1\u673a\u6784").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("STATIONSTATE").columnTitle("\u5728\u5c97\u72b6\u6001").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("STATIONROLE").columnTitle("\u5c97\u4f4d\u89d2\u8272").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("RESPONSIBILITY").columnTitle("\u5c97\u4f4d\u804c\u8d23").columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("CHANGETYPE").columnTitle("\u53d8\u66f4\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("STAFF").columnTitle("\u804c\u5458").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).mapping("MD_STAFF.CODE").mappingType(Integer.valueOf(1)).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("USERLINKID").columnTitle("\u5173\u8054\u7528\u6237id").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("MANAGEBILLCODE").columnTitle("\u7ba1\u7406\u5355\u636e\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("ITEMCODE").columnTitle("\u53d8\u66f4\u524d\u5b50\u8868\u5355\u636e\u7f16\u53f7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("MANAGEBILLID").columnTitle("\u7ba1\u7406\u5355\u636eid").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        dataModelDO.setColumns(templateFields);
        return dataModelDO;
    }
}

