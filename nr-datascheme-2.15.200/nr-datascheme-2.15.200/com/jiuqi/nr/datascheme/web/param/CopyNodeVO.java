/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class CopyNodeVO {
    @ApiModelProperty(value="\u76ee\u6807\u8282\u70b9")
    private DataSchemeNodeDTO target;
    @ApiModelProperty(value="\u62f7\u8d1d\u6570\u636e")
    private List<DataSchemeNodeDTO> copyData;

    public DataSchemeNodeDTO getTarget() {
        return this.target;
    }

    public void setTarget(DataSchemeNodeDTO target) {
        this.target = target;
    }

    public List<DataSchemeNodeDTO> getCopyData() {
        return this.copyData;
    }

    public void setCopyData(List<DataSchemeNodeDTO> copyData) {
        this.copyData = copyData;
    }

    public String toString() {
        return "CopyNodeVO{target=" + this.target + ", copyData=" + this.copyData + '}';
    }
}

