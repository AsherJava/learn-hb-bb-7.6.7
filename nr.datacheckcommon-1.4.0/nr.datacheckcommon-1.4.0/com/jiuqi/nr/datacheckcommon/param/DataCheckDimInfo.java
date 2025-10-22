/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheckcommon.param;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataCheckDimInfo {
    private Map<String, Map<String, Boolean>> dimNameEntityIdExistCurrencyAttributeMap;
    private LinkedHashMap<String, LinkedHashMap<String, String>> dimRange;
    private Map<String, String> dimNameTitleMap;
    private Map<String, Boolean> dimNameIsShowMap;

    public Map<String, Map<String, Boolean>> getDimNameEntityIdExistCurrencyAttributeMap() {
        return this.dimNameEntityIdExistCurrencyAttributeMap;
    }

    public void setDimNameEntityIdExistCurrencyAttributeMap(Map<String, Map<String, Boolean>> dimNameEntityIdExistCurrencyAttributeMap) {
        this.dimNameEntityIdExistCurrencyAttributeMap = dimNameEntityIdExistCurrencyAttributeMap;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getDimRange() {
        return this.dimRange;
    }

    public void setDimRange(LinkedHashMap<String, LinkedHashMap<String, String>> dimRange) {
        this.dimRange = dimRange;
    }

    public Map<String, String> getDimNameTitleMap() {
        return this.dimNameTitleMap;
    }

    public void setDimNameTitleMap(Map<String, String> dimNameTitleMap) {
        this.dimNameTitleMap = dimNameTitleMap;
    }

    public Map<String, Boolean> getDimNameIsShowMap() {
        return this.dimNameIsShowMap;
    }

    public void setDimNameIsShowMap(Map<String, Boolean> dimNameIsShowMap) {
        this.dimNameIsShowMap = dimNameIsShowMap;
    }
}

