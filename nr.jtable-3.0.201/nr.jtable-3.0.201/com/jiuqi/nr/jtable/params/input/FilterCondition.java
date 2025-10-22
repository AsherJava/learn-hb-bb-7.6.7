/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="FilterCondition", description="\u8fc7\u6ee4\u5668\u6761\u4ef6")
public class FilterCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u64cd\u4f5c\u7b26", name="opCode")
    private String opCode;
    @ApiModelProperty(value="\u64cd\u4f5c\u503c", name="opValue")
    private String opValue;

    public String getOpCode() {
        return this.opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpValue() {
        return this.opValue;
    }

    public void setOpValue(String opValue) {
        this.opValue = opValue;
    }
}

