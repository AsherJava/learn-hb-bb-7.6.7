/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class DFDAddRowConfirmResult {
    @ApiModelProperty(value="\u662f\u5426\u6210\u529f", name="success")
    private boolean success;
    @ApiModelProperty(value="\u63d0\u793a\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u9519\u8bef\u7684\u7ef4\u5ea6\u6570\u636e\u7ec4\u5408", name="errors")
    private List<List<DFDimensionValue>> errors;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<List<DFDimensionValue>> getErrors() {
        return this.errors;
    }

    public void setErrors(List<List<DFDimensionValue>> errors) {
        this.errors = errors;
    }
}

