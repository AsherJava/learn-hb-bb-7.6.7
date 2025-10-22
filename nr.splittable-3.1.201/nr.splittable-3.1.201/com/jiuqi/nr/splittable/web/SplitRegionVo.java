/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.splittable.web;

import io.swagger.annotations.ApiModelProperty;

public class SplitRegionVo {
    @ApiModelProperty(value="\u5b50\u533a\u57df\u5217\u8868\u4e2d\u7684regionKey")
    private String regionKey;

    public SplitRegionVo() {
    }

    public SplitRegionVo(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }
}

