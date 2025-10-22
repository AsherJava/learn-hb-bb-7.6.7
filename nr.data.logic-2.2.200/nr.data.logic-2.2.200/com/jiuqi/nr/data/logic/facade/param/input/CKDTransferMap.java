/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import java.util.HashMap;
import java.util.Map;

public class CKDTransferMap {
    private Map<String, String> mainDimValueMap = new HashMap<String, String>();
    private Map<String, String> bizKeyOrderMap = new HashMap<String, String>();

    public Map<String, String> getMainDimValueMap() {
        return this.mainDimValueMap;
    }

    public void setMainDimValueMap(Map<String, String> mainDimValueMap) {
        this.mainDimValueMap = mainDimValueMap;
    }

    public Map<String, String> getBizKeyOrderMap() {
        return this.bizKeyOrderMap;
    }

    public void setBizKeyOrderMap(Map<String, String> bizKeyOrderMap) {
        this.bizKeyOrderMap = bizKeyOrderMap;
    }
}

