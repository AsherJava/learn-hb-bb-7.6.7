/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.dto;

import com.jiuqi.nr.definition.option.dto.InnerField;
import java.util.Map;

public class ReferEntity {
    private String entityId;
    private String code;
    private String ownEntityId;
    private String ownCode;
    private Map<String, InnerField> innerFieldMap;

    public ReferEntity(String entityId, String code, String ownEntityId, String ownCode) {
        this.entityId = entityId;
        this.code = code;
        this.ownEntityId = ownEntityId;
        this.ownCode = ownCode;
    }

    public String getOwnCode() {
        return this.ownCode;
    }

    public void setOwnCode(String ownCode) {
        this.ownCode = ownCode;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, InnerField> getInnerFieldMap() {
        return this.innerFieldMap;
    }

    public void setInnerFieldMap(Map<String, InnerField> innerFieldMap) {
        this.innerFieldMap = innerFieldMap;
    }

    public String getOwnEntityId() {
        return this.ownEntityId;
    }

    public void setOwnEntityId(String ownEntityId) {
        this.ownEntityId = ownEntityId;
    }
}

