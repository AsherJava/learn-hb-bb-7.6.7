/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.efdc.pojo;

import com.jiuqi.nr.efdc.bean.EntityDataObject;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;

public class EntityData
extends EntityDataObject {
    public static EntityDataObject buildData(IEntityRow row) {
        EntityDataObject dataObj = new EntityDataObject();
        if (row != null) {
            dataObj.setSuccess(true);
            dataObj.setKey(row.getEntityKeyData());
            dataObj.setTitle(row.getTitle());
            dataObj.setCode(row.getCode());
            dataObj.setPath(row.getParentsEntityKeyDataPath());
            dataObj.setParentId(row.getParentEntityKey());
            dataObj.setIconValue(row.getIconValue());
            IFieldsInfo fields = row.getFieldsInfo();
            int fieldCount = fields.getFieldCount();
            for (int idx = 0; idx < fieldCount; ++idx) {
                IEntityAttribute fieldByIndex = fields.getFieldByIndex(idx);
                IEntityAttribute fieldDefine = fields.getFieldDefine(fieldByIndex.getCode());
                AbstractData value = row.getValue(fieldDefine.getCode());
                dataObj.setFieldValue(fieldDefine.getCode().toLowerCase(), value.getAsObject());
            }
        }
        return dataObj;
    }
}

