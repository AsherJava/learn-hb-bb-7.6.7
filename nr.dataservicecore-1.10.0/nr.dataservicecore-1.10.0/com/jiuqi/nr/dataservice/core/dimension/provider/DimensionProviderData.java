/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionProviderData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String currentValue;
    private List<String> choosedValues;
    private List<String> excludeValues;
    private String dataSchemeKey;
    private String filter;
    private AuthorityType authorityType = AuthorityType.Read;
    private Map<String, Serializable> params;

    public DimensionProviderData() {
    }

    public DimensionProviderData(List<String> choosedValues, String dataSchemeKey) {
        this.choosedValues = choosedValues;
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void putOtherPamra(String key, Serializable value) {
        if (this.params == null) {
            this.params = new HashMap<String, Serializable>();
        }
        this.params.put(key, value);
    }

    Object getOtherParam(String key) {
        if (this.params == null) {
            return null;
        }
        return this.params.get(key);
    }

    public Map<String, Serializable> getOtherParams() {
        return this.params;
    }

    public List<String> getChoosedValues() {
        return this.choosedValues;
    }

    public void setChoosedValues(List<String> choosedValues) {
        this.choosedValues = choosedValues;
    }

    public List<String> getExcludeValues() {
        return this.excludeValues;
    }

    public void setExcludeValues(List<String> excludeValues) {
        this.excludeValues = excludeValues;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public AuthorityType getAuthorityType() {
        return this.authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public String toString() {
        return "DimensionProviderData{currentValue=" + this.currentValue + ", choosedValues=" + this.choosedValues + ", excludeValues=" + this.excludeValues + ", dataSchemeKey=" + this.dataSchemeKey + ", filter=" + this.filter + ", authorityType=" + this.authorityType + ", params=" + this.params + '}';
    }
}

