/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.nr.entity.component.dwdm.impl;

import com.jiuqi.nr.entity.component.dwdm.EntityDWDMService;
import com.jiuqi.nr.entity.component.dwdm.OrgAttributeDTO;
import com.jiuqi.nr.entity.component.dwdm.OrgIDCAttributeDTO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityDWDMServiceImpl
implements EntityDWDMService {
    @Autowired
    private DataModelClient dataModelClient;

    @Override
    public OrgIDCAttributeDTO getDWDMAttributes(String categoryName) {
        OrgIDCAttributeDTO orgIDCAttributeDTO = new OrgIDCAttributeDTO();
        DataModelDTO param = new DataModelDTO();
        param.setName(categoryName);
        DataModelDO dataModelDO = this.dataModelClient.get(param);
        List columns = dataModelDO.getColumns();
        Map extInfo = dataModelDO.getExtInfo();
        if (extInfo != null) {
            Object value = extInfo.get("DWDM_FIELD");
            orgIDCAttributeDTO.setDwdm(value == null ? null : value.toString());
        }
        ArrayList<OrgAttributeDTO> attributes = new ArrayList<OrgAttributeDTO>(columns.size());
        for (DataModelColumn column : columns) {
            if (column.getMappingType() != null || column.getColumnType() != DataModelType.ColumnType.NVARCHAR) continue;
            OrgAttributeDTO attributeDTO = new OrgAttributeDTO();
            attributeDTO.setKey(column.getColumnName());
            attributeDTO.setTitle(column.getColumnTitle());
            attributes.add(attributeDTO);
        }
        orgIDCAttributeDTO.setAttributes(attributes);
        return orgIDCAttributeDTO;
    }

    @Override
    public void saveDWDMAttribute(String categoryName, String attributeName) {
        DataModelDTO param = new DataModelDTO();
        param.setName(categoryName);
        DataModelDO dataModelDO = this.dataModelClient.get(param);
        dataModelDO.addExtInfo("DWDM_FIELD", (Object)attributeName);
        this.dataModelClient.push(dataModelDO);
    }
}

