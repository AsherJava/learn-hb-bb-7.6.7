/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.fmdm.IFMDMData;
import org.springframework.util.Assert;

public abstract class AbstractFMDMDataDO
implements IFMDMData {
    private String entityKey;

    public AbstractFMDMDataDO(String entityKey) {
        this.entityKey = entityKey;
    }

    @Override
    public String getFMDMKey() {
        return this.entityKey;
    }

    @Override
    public abstract DimensionValueSet getMasterKey();

    @Override
    public abstract AbstractData getValue(String var1);

    @Override
    public Object getAsObject(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getValue(code).getAsObject();
    }

    @Override
    public AbstractData getEntityValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getValue(code);
    }

    @Override
    public AbstractData getDataValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getValue(code);
    }

    @Override
    public Object getEntityAsObject(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getAsObject(code);
    }

    @Override
    public Object getDataAsObject(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getAsObject(code);
    }

    @Override
    public AbstractData getInfoValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getValue(code);
    }

    @Override
    public Object getInfoAsObject(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        return this.getAsObject(code);
    }
}

