/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class ClbrBillExtendFieldDTO {
    private Map<String, Object> extendedFields = new HashMap<String, Object>();

    public Map<String, Object> getExtendedFields() {
        return this.extendedFields;
    }

    public void setExtendedFields(Map<String, Object> extendedFields) {
        this.extendedFields = extendedFields;
    }

    public void putExtendedFields(String key, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return;
        }
        if (this.extendedFields.containsKey(key)) {
            return;
        }
        this.extendedFields.put(key, value);
    }
}

