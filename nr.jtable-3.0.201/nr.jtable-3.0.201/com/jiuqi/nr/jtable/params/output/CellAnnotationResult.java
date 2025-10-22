/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.ms.System.Collections.ArrayList
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.CellAnnotationContent;
import com.spire.ms.System.Collections.ArrayList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u4e00\u4e2a\u5355\u5143\u683c\u6240\u6709\u6279\u6ce8", description="\u4e00\u4e2a\u5355\u5143\u683c\u6240\u6709\u6279\u6ce8")
public class CellAnnotationResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u6570\u636e\u94fe\u63a5", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u6d6e\u52a8\u884cid", name="rowId")
    private String rowId;
    @ApiModelProperty(value="\u5355\u5143\u683c\u4e0b\u6240\u6709\u6279\u6ce8", name="contents")
    private List<CellAnnotationContent> contents = new ArrayList();

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

    public List<CellAnnotationContent> getContents() {
        return this.contents;
    }

    public void setContents(List<CellAnnotationContent> contents) {
        this.contents = contents;
    }
}

