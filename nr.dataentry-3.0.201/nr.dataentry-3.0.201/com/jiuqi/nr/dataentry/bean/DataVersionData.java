/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.snapshot.DataVersion
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.snapshot.DataVersion;
import java.io.Serializable;
import java.util.Map;

public class DataVersionData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String versionId;
    private String title;
    private String describe;
    private long creatTime;
    private String creatUser;
    private String creatUserId;
    private boolean autoCreated;
    private Map<String, DimensionValue> dimensionSet;

    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public long getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public String getCreatUser() {
        return this.creatUser;
    }

    public void setCreatUser(String creatUser) {
        this.creatUser = creatUser;
    }

    public String getCreatUserId() {
        return this.creatUserId;
    }

    public void setCreatUserId(String creatUserId) {
        this.creatUserId = creatUserId;
    }

    public boolean isAutoCreated() {
        return this.autoCreated;
    }

    public void setAutoCreated(boolean autoCreated) {
        this.autoCreated = autoCreated;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public void init(DataVersion dataVersion) {
        this.versionId = dataVersion.getVersionId();
        this.autoCreated = dataVersion.isAutoCreated();
        this.creatTime = dataVersion.getCreatTime().getTime();
        this.creatUserId = null;
        this.describe = dataVersion.getDescribe();
        this.title = dataVersion.getTitle();
    }
}

