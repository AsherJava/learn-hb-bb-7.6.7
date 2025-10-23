/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.access.api.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.SecretLevelDim;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="BatchSecretLevelInfo", description="\u5bc6\u7ea7\u5c5e\u6027")
public class BatchSecretLevelInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u5f53\u524d\u73af\u5883", name="jtableContext")
    private DimensionValueSet dimensionValueSet;
    @ApiModelProperty(value="\u5bc6\u7ea7\u5217\u8868", name="sercetLevels")
    private List<SecretLevelDim> sercetLevels;

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public List<SecretLevelDim> getSercetLevels() {
        return this.sercetLevels;
    }

    public void setSercetLevels(List<SecretLevelDim> sercetLevels) {
        this.sercetLevels = sercetLevels;
    }
}

