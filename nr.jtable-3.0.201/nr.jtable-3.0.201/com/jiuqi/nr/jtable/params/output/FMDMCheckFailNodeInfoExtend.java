/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import io.swagger.annotations.ApiModelProperty;

public class FMDMCheckFailNodeInfoExtend
extends FMDMCheckFailNodeInfo {
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey")
    private String dataLinkKey;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }
}

