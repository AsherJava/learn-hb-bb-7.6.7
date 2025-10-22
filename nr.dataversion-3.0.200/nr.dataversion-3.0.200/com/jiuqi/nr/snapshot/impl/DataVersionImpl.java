/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.impl;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.DataVersion;
import java.util.Date;

public class DataVersionImpl
implements DataVersion {
    private String versionId;
    private String title;
    private String describe;
    private Date creatTime;
    private String creatUser;
    private boolean autoCreated;
    private String formSchemeKey;
    private DimensionCombination dimensionValueSet;

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getVersionId() {
        return this.versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public Date getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String getCreatUser() {
        return this.creatUser;
    }

    public void setCreatUser(String creatUser) {
        this.creatUser = creatUser;
    }

    @Override
    public boolean isAutoCreated() {
        return this.autoCreated;
    }

    public void setAutoCreated(boolean autoCreated) {
        this.autoCreated = autoCreated;
    }

    @Override
    public DimensionCombination getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionCombination dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

