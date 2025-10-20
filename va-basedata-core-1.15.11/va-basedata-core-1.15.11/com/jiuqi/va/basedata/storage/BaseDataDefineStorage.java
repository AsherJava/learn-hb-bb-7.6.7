/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.StorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.storage;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.StorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;

public class BaseDataDefineStorage {
    private static DataModelClient dataModelClient = null;

    public static R init(BaseDataDefineDO defineDO) {
        return BaseDataDefineStorage.init(defineDO, null);
    }

    public static R init(BaseDataDefineDO defineDO, List<DataModelColumn> columns) {
        DataModelDO dataModelDO = BaseDataDefineStorage.getCreateJTM(defineDO, columns);
        DataModelClient client = BaseDataDefineStorage.getDataModelClient();
        return client.push(dataModelDO);
    }

    public static DataModelDO getCreateJTM(BaseDataDefineDO basedata) {
        return BaseDataDefineStorage.getCreateJTM(basedata, null);
    }

    public static DataModelDO getCreateJTM(BaseDataDefineDO basedata, List<DataModelColumn> columns) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setGroupcode("public");
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setName(basedata.getName());
        dataModelDO.setTenantName(basedata.getTenantName());
        dataModelDO.setTitle(basedata.getTitle());
        String key = "ignoreCheckTableNameLength";
        if (basedata.getExtInfo(key) != null) {
            dataModelDO.addExtInfo(key, basedata.getExtInfo(key));
        }
        DataModelTemplateEntity template = StorageUtil.getDataModelTemplate((String)"basedata", (String)basedata.getName());
        if (columns != null && !columns.isEmpty()) {
            dataModelDO.setColumns(columns);
        } else {
            dataModelDO.setColumns(template.getTemplateFields());
        }
        DataModelColumn codeCol = null;
        DataModelColumn objectcodeCol = null;
        for (DataModelColumn column : dataModelDO.getColumns()) {
            if ("CODE".equalsIgnoreCase(column.getColumnName())) {
                codeCol = column;
            }
            if (!"OBJECTCODE".equalsIgnoreCase(column.getColumnName())) continue;
            objectcodeCol = column;
        }
        if (objectcodeCol != null) {
            Integer shareType = basedata.getSharetype();
            if (shareType != null && shareType == 0 && codeCol != null) {
                objectcodeCol.setLengths(codeCol.getLengths());
            } else {
                objectcodeCol.setLengths(new Integer[]{200});
            }
        }
        dataModelDO.setIndexConsts(template.getTemplateIndexs());
        dataModelDO.addExtInfo("baseDataDefine", (Object)basedata);
        return dataModelDO;
    }

    public static DataModelDO getDataModel(DataModelDTO dataModelDTO) {
        DataModelClient client = BaseDataDefineStorage.getDataModelClient();
        return client.get(dataModelDTO);
    }

    private static DataModelClient getDataModelClient() {
        if (dataModelClient == null) {
            dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
        }
        return dataModelClient;
    }
}

