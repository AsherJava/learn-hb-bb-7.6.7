/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.ms.System.Collections.ArrayList
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.CellLocation;
import com.spire.ms.System.Collections.ArrayList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u4e00\u4e2a\u533a\u57df\u5185\u5173\u8054\u6279\u6ce8\u7684\u5355\u5143\u683c\u4fe1\u606f", description="\u4e00\u4e2a\u533a\u57df\u5185\u5173\u8054\u6279\u6ce8\u7684\u5355\u5143\u683c\u4fe1\u606f")
public class RegionAnnotationResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u5173\u8054\u6279\u6ce8\u7684\u5355\u5143\u683c\u4fe1\u606f", name="cells")
    private List<CellLocation> cells = new ArrayList();

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<CellLocation> getCells() {
        return this.cells;
    }

    public void setCells(List<CellLocation> cells) {
        this.cells = cells;
    }
}

