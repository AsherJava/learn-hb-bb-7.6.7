/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class CopyDataParam {
    private DimensionCollection masterKey;
    private String sourceEntityId;
    private String targetEntityId;
    private String sourceTaskKey;
    private String targetTaskKey;
    private String sourceFormSchemeKey;
    private String targetFormSchemeKey;
    private List<String> formKeys;

    public CopyDataParam(DimensionCollection masterKey, String sourceEntityId, String targetEntityId, String sourceTaskKey, String targetTaskKey, String sourceFormSchemeKey, String targetFormSchemeKey, List<String> formKeys) {
        this.masterKey = masterKey;
        this.sourceEntityId = sourceEntityId;
        this.targetEntityId = targetEntityId;
        this.sourceTaskKey = sourceTaskKey;
        this.targetTaskKey = targetTaskKey;
        this.sourceFormSchemeKey = sourceFormSchemeKey;
        this.targetFormSchemeKey = targetFormSchemeKey;
        this.formKeys = formKeys;
    }

    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public String getSourceEntityId() {
        return this.sourceEntityId;
    }

    public void setSourceEntityId(String sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public String getTargetEntityId() {
        return this.targetEntityId;
    }

    public void setTargetEntityId(String targetEntityId) {
        this.targetEntityId = targetEntityId;
    }

    public String getSourceTaskKey() {
        return this.sourceTaskKey;
    }

    public void setSourceTaskKey(String sourceTaskKey) {
        this.sourceTaskKey = sourceTaskKey;
    }

    public String getTargetTaskKey() {
        return this.targetTaskKey;
    }

    public void setTargetTaskKey(String targetTaskKey) {
        this.targetTaskKey = targetTaskKey;
    }

    public String getSourceFormSchemeKey() {
        return this.sourceFormSchemeKey;
    }

    public void setSourceFormSchemeKey(String sourceFormSchemeKey) {
        this.sourceFormSchemeKey = sourceFormSchemeKey;
    }

    public String getTargetFormSchemeKey() {
        return this.targetFormSchemeKey;
    }

    public void setTargetFormSchemeKey(String targetFormSchemeKey) {
        this.targetFormSchemeKey = targetFormSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

