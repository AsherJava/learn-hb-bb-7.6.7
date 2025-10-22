/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="\u5355\u5143\u683c\u5b9a\u4f4d", description="\u5355\u5143\u683c\u5b9a\u4f4d")
public class CellLocation
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u6570\u636e\u94fe\u63a5", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u6d6e\u52a8\u884cid", name="rowId")
    private String rowId;

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
}

