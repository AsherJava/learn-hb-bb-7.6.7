/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.bean;

import com.jiuqi.nr.data.engine.bean.CompareDifferenceItem;
import com.jiuqi.nr.data.engine.util.Consts;
import java.util.List;
import java.util.Map;

public class NaturalKeyCompareDifference {
    private String naturalKey;
    private Consts.NaturalKeyCompareType type;
    private List<String> naturalNames;
    private List<String> naturalKeys;
    private List<Map<String, String>> naturalFields;
    private List<CompareDifferenceItem> updateItems;
    private String initialFloatId;
    private String compareFloatId;

    public Consts.NaturalKeyCompareType getType() {
        return this.type;
    }

    public void setType(Consts.NaturalKeyCompareType type) {
        this.type = type;
    }

    public List<String> getNaturalNames() {
        return this.naturalNames;
    }

    public void setNaturalNames(List<String> naturalNames) {
        this.naturalNames = naturalNames;
    }

    public List<String> getNaturalKeys() {
        return this.naturalKeys;
    }

    public void setNaturalKeys(List<String> naturalKeys) {
        this.naturalKeys = naturalKeys;
    }

    public List<CompareDifferenceItem> getUpdateItems() {
        return this.updateItems;
    }

    public void setUpdateItems(List<CompareDifferenceItem> updateItems) {
        this.updateItems = updateItems;
    }

    public String getInitialFloatId() {
        return this.initialFloatId;
    }

    public void setInitialFloatId(String initialFloatId) {
        this.initialFloatId = initialFloatId;
    }

    public String getCompareFloatId() {
        return this.compareFloatId;
    }

    public void setCompareFloatId(String compareFloatId) {
        this.compareFloatId = compareFloatId;
    }

    public List<Map<String, String>> getNaturalFields() {
        return this.naturalFields;
    }

    public void setNaturalFields(List<Map<String, String>> naturalFields) {
        this.naturalFields = naturalFields;
    }

    public String getNaturalKey() {
        return this.naturalKey;
    }

    public void setNaturalKey(String naturalKey) {
        this.naturalKey = naturalKey;
    }
}

