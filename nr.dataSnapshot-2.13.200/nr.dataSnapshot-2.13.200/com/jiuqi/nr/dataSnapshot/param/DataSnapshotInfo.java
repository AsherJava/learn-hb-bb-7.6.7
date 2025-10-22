/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class DataSnapshotInfo
implements Serializable {
    private Map<String, DimensionValue> dimensionSet;
    private String snapshotId;
    private String title;
    private String describe;
    private Date creatTime;
    private String creatUserName;
    private String formSchemeKey;
    private Boolean autoCreate;

    public Boolean getAutoCreate() {
        return this.autoCreate;
    }

    public void setAutoCreate(Boolean autoCreate) {
        this.autoCreate = autoCreate;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getSnapshotId() {
        return this.snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
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

    public Date getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getCreatUserName() {
        return this.creatUserName;
    }

    public void setCreatUserName(String creatUserName) {
        this.creatUserName = creatUserName;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

