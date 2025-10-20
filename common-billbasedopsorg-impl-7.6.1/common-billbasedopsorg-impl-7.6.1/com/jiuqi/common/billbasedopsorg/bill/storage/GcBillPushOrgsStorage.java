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

public class GcBillPushOrgsStorage {
    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = GcBillPushOrgsStorage.getCreateDataMode(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("GC_BILLPUSHORGS");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(GcBillPushOrgUtil.mergeDataModel(origalDataModel, dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BILL);
        dataModelDO.setName("GC_BILLPUSHORGS");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u6279\u91cf\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u4e3b\u8868");
        VaDataModelTemplateService templateService = (VaDataModelTemplateService)SpringBeanUtils.getBean(VaDataModelTemplateService.class);
        DataModelTemplate template = templateService.getSubTemplate(DataModelType.BizType.BILL.name(), Integer.valueOf(1));
        List templateFields = template.getTemplateFields();
        dataModelDO.setColumns(templateFields);
        dataModelDO.addColumn("ALLOWPUSHORG").columnTitle("\u5141\u8bb8\u63a8\u9001\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{10}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SYNCORGTYPE").columnTitle("\u540c\u6b65\u5355\u4f4d\u7c7b\u578b").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SYNCPERIOD").columnTitle("\u540c\u6b65\u65f6\u671f").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{20}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SYNCPARENTCODE").columnTitle("\u540c\u6b65\u7684\u7236\u7ea7\u5355\u4f4d").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SYNCLOGID").columnTitle("\u540c\u6b65\u65e5\u5fd7id").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{60}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SYNCLOG").columnTitle("\u540c\u6b65\u65e5\u5fd7").columnType(DataModelType.ColumnType.NVARCHAR).lengths(new Integer[]{1000}).columnAttr(DataModelType.ColumnAttr.EXTEND);
        return dataModelDO;
    }
}

