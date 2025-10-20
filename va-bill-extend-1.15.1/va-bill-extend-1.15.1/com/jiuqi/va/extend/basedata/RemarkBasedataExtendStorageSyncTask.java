/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.basedata.OrgMngType
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.common.StorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.extend.basedata;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.basedata.OrgMngType;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.common.StorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class RemarkBasedataExtendStorageSyncTask
implements StorageSyncTask {
    public String getVersion() {
        return "20220712-0000";
    }

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        this.creatBaseDataDefine(tenantName);
        RemarkBasedataExtendStorageSyncTask.init(tenantName);
    }

    public static void init(String tenantName) {
        DataModelClient client = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        DataModelDO origalDataModel = RemarkBasedataExtendStorageSyncTask.getCreateJTM(tenantName);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("MD_FILLREMARK");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client.get(dataModelDTO);
        origalDataModel.setColumns(StorageUtil.mergeDataModel((DataModelDO)origalDataModel, (DataModelDO)dataModelDO));
        client.push(origalDataModel);
    }

    public static DataModelDO getCreateJTM(String tenantName) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setName("MD_FILLREMARK");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("\u586b\u62a5\u8bf4\u660e");
        BaseDataStorageUtil.createBasedataPubField((DataModelDO)dataModelDO);
        ArrayList<DataModelColumn> columns = new ArrayList<DataModelColumn>();
        columns.add(new DataModelColumn().columnName("REMARK").columnTitle("\u586b\u62a5\u8bf4\u660e").columnType(DataModelType.ColumnType.CLOB).columnAttr(DataModelType.ColumnAttr.FIXED));
        dataModelDO.getColumns().addAll(columns);
        return dataModelDO;
    }

    public void creatBaseDataDefine(String tenantName) {
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName(tenantName);
        baseDataDefineDTO.setName("MD_FILLREMARK");
        BaseDataDefineDO oldDefine = client.get(baseDataDefineDTO);
        baseDataDefineDTO.setStructtype(Integer.valueOf(0));
        baseDataDefineDTO.setTitle("\u586b\u62a5\u8bf4\u660e");
        baseDataDefineDTO.setGroupname("public");
        baseDataDefineDTO.setSharetype(Integer.valueOf(OrgMngType.SHARE.getCode()));
        baseDataDefineDTO.setSolidifyflag(Integer.valueOf(1));
        baseDataDefineDTO.setVersionflag(Integer.valueOf(0));
        baseDataDefineDTO.setReadonly(Integer.valueOf(1));
        if (oldDefine == null) {
            baseDataDefineDTO.setId(UUID.randomUUID());
            baseDataDefineDTO.setDefaultShowColumns(this.getDefaultShowColumns());
            client.add(baseDataDefineDTO);
        } else {
            BaseDataDefineDTO defineDTO = new BaseDataDefineDTO();
            defineDTO.setTenantName(tenantName);
            defineDTO.setName("MD_FILLREMARK");
            defineDTO.setModifytime(new Date());
            defineDTO.setReadonly(Integer.valueOf(1));
            defineDTO.setDefine(oldDefine.getDefine());
            HashMap<String, Boolean> extInfo = new HashMap<String, Boolean>();
            extInfo.put("onlyEditBasicInfo", true);
            defineDTO.setExtInfo(extInfo);
            defineDTO.setDefaultShowColumns(this.getDefaultShowColumns());
            client.upate(defineDTO);
        }
    }

    private List<Map<String, Object>> getDefaultShowColumns() {
        ArrayList<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
        fields.add(BaseDataStorageUtil.getColumnMap((String)"CODE", (String)"\u4ee3\u7801", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"NAME", (String)"\u540d\u79f0", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)true, (Boolean)false, (Boolean)true, (Boolean)false));
        fields.add(BaseDataStorageUtil.getColumnMap((String)"REMARK", (String)"\u586b\u62a5\u8bf4\u660e", (String)DataModelType.ColumnType.NVARCHAR.name(), (Boolean)false, (Boolean)false, (Boolean)false, (Boolean)false));
        return fields;
    }
}

