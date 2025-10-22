/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="\u67e5\u8be2\u4e00\u4e2a\u5355\u5143\u683c\u7684\u6279\u6ce8\u4fe1\u606f", description="\u67e5\u8be2\u4e00\u4e2a\u5355\u5143\u683c\u7684\u6279\u6ce8\u4fe1\u606f")
public class CellAnnotationQueryCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u6570\u636e\u94fe\u63a5", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u6d6e\u52a8\u884cid", name="rowId")
    private String rowId;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

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

