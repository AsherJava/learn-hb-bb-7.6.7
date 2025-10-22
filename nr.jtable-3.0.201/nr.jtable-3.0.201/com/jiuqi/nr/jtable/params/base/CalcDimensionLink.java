/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="CalcDimensionLink", description="\u6d6e\u52a8\u533a\u57df\u8fd0\u7b97\u5355\u5143\u683c")
public class CalcDimensionLink {
    @ApiModelProperty(value="\u94fe\u63a5key", name="linkKey")
    private String linkKey;
    @ApiModelProperty(value="\u6d6e\u52a8\u884c\u6570\u636eID", name="rowId")
    private String rowId;

    public CalcDimensionLink(String linkKey, String rowId) {
        this.linkKey = linkKey;
        this.rowId = rowId;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
}

