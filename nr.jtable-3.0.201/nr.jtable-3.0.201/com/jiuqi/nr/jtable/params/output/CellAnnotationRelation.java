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

@ApiModel(value="\u4e00\u4e2a\u6279\u6ce8\u5173\u8054\u7684\u5355\u5143\u683c", description="\u4e00\u4e2a\u6279\u6ce8\u5173\u8054\u7684\u5355\u5143\u683c")
public class CellAnnotationRelation
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u6240\u5c5e\u8868", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u6570\u636e\u94fe\u63a5", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u6d6e\u52a8\u884cid", name="rowId")
    private String rowId;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u5c55\u793a\u4fe1\u606f", name="show")
    private String show;

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getShow() {
        return this.show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

