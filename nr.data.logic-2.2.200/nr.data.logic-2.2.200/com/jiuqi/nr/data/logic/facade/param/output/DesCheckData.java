/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DesCheckData
implements Serializable {
    private static final long serialVersionUID = 302215400171928643L;
    @ApiModelProperty(value="\u5355\u4f4dCode", name="unitCode")
    private String unitCode;
    @ApiModelProperty(value="\u5355\u4f4d\u540d\u79f0", name="unitTitle")
    private String unitTitle;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }
}

