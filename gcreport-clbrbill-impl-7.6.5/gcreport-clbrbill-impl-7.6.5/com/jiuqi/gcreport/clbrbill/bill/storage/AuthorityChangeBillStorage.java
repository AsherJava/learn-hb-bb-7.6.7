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

public class AuthorityChangeBillStorage {
    private AuthorityChangeBillStorage() {
    }

    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = AuthorityChangeBillStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_AUTHORITYCHANGEBILL");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(ClbrBillUtils.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_AUTHORITYCHANGEBILL");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u6743\u9650\u53d8\u66f4\u5355\u4e3b\u8868");
        VaDataModelTemplateService templateService = (VaDataModelTemplateService)SpringBeanUtils.getBean(VaDataModelTemplateService.class);
        DataModelTemplate template = templateService.getSubTemplate(DataModelType.BizType.BILL.name(), Integer.valueOf(1));
        List templateFields = template.getTemplateFields();
        templateFields.add(new DataModelColumn().columnName("REPORTMODE").columnTitle("\u5171\u4eab\u51fa\u8868\u6a21\u5f0f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("SSFX").columnTitle("\u5171\u4eab\u4e2d\u5fc3").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("HELMSMAN").columnTitle("\u638c\u8235\u4eba").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("PHONE").columnTitle("\u638c\u8235\u4eba\u8054\u7cfb\u65b9\u5f0f").columnType(DataModelType.ColumnType.NUMERIC).lengths(new Integer[]{19, 0}).columnAttr(DataModelType.ColumnAttr.EXTEND));
        templateFields.add(new DataModelColumn().columnName("APPLICATIONCREATETIME").columnTitle("\u7533\u8bf7\u65e5\u671f").columnType(DataModelType.ColumnType.TIMESTAMP).columnAttr(DataModelType.ColumnAttr.EXTEND));
        dataModelDO.setColumns(templateFields);
        return dataModelDO;
    }
}

