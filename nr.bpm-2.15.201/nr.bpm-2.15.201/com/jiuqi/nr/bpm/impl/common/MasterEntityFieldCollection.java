/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.FieldType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.management.openmbean.KeyAlreadyExistsException;
import org.springframework.util.Assert;

public class MasterEntityFieldCollection {
    private Map<UUID, FieldInfo> masterEntityFieldsMap = new HashMap<UUID, FieldInfo>();
    private List<UUID> entityViewKeys = new ArrayList<UUID>();

    public void add(UUID entityViewKey, String fieldName, FieldType fieldType) {
        Assert.isTrue((boolean)StringUtils.isNotEmpty((String)fieldName));
        if (this.masterEntityFieldsMap.containsKey(entityViewKey)) {
            throw new KeyAlreadyExistsException();
        }
        this.masterEntityFieldsMap.put(entityViewKey, new FieldInfo(fieldName, fieldType));
        this.entityViewKeys.add(entityViewKey);
    }

    public List<UUID> getEntityViewKeys() {
        return this.entityViewKeys;
    }

    public String getFieldName(UUID entityViewKey) {
        FieldInfo fieldInfo = this.masterEntityFieldsMap.get(entityViewKey);
        return fieldInfo == null ? null : fieldInfo.fieldName;
    }

    public FieldType getFieldType(UUID entityViewKey) {
        FieldInfo fieldInfo = this.masterEntityFieldsMap.get(entityViewKey);
        return fieldInfo == null ? null : fieldInfo.fieldType;
    }

    public boolean containsMasterEntity(UUID entityViewKey) {
        return this.masterEntityFieldsMap.containsKey(entityViewKey);
    }

    private class FieldInfo {
        String fieldName;
        FieldType fieldType;

        public FieldInfo(String fieldName, FieldType fieldType) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }
    }
}

