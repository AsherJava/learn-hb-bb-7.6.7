/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.cloud.impl;

import com.jiuqi.nvwa.sf.adapter.spring.cloud.ISFExtendValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SFExtendValueManage {
    @Autowired(required=false)
    private List<ISFExtendValue> extendValueList;

    public Map<String, String> getExtendValueMap() {
        HashMap<String, String> extendValues = new HashMap<String, String>();
        if (this.extendValueList != null && !this.extendValueList.isEmpty()) {
            for (ISFExtendValue extendValue : this.extendValueList) {
                extendValues.put(extendValue.getKey(), extendValue.getValue());
            }
        }
        return extendValues;
    }
}

