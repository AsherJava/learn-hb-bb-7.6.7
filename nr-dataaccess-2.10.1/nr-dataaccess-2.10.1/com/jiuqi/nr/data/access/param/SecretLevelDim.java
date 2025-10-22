/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.SecretLevel;

public class SecretLevelDim {
    private DimensionValueSet dimensionValueSet;
    private SecretLevel secretLevel;

    public SecretLevelDim() {
    }

    public SecretLevelDim(DimensionValueSet dimensionValueSet, SecretLevel secretLevel) {
        this.dimensionValueSet = dimensionValueSet;
        this.secretLevel = secretLevel;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public SecretLevel getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(SecretLevel secretLevel) {
        this.secretLevel = secretLevel;
    }
}

