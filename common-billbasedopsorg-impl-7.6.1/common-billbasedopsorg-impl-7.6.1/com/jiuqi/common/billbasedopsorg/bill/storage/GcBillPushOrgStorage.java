/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.va.datamodel.service.VaDataModelTemplateService
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.extend.DataModelTemplate
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.common.billbasedopsorg.bill.storage;

import com.jiuqi.common.billbasedopsorg.utils.GcBillPushOrgUtil;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.va.datamodel.service.VaDataModelTemplateService;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplate;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class GcBillPushOrgStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcBillPushOrgStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_BILLPUSHORG");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcBillPushOrgUtil.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_BILLPUSHORG");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u4e3b\u8868");
        VaDataModelTemplateService templateService = (VaDataModelTemplateService)SpringBeanUtils.getBean(VaDataModelTemplateService.class);
        DataModelTemplate template = templateService.getSubTemplate(DataModelType.BizType.BILL.name(), Integer.valueOf(1));
        List templateFields = template.getTemplateFields();
        dataModelDO.setColumns(templateFields);
        dataModelDO.addColumn("ORG_CODE").columnTitle("\u5355\u4f4d\u4ee3\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ORG_NAME").columnTitle("\u5355\u4f4d\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ORG_PARENTCODE").columnTitle("\u7236\u7ea7\u5355\u4f4d\u4ee3\u7801").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ORG_PARENTNAME").columnTitle("\u7236\u7ea7\u5355\u4f4d\u540d\u79f0").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{200}).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ALLOWPUSHORG").columnTitle("\u5141\u8bb8\u63a8\u9001\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        return dataModelDO;
    }
}

