/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.nr.common.constant.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="ResultErrorInfo", description="\u9519\u8bef\u4fe1\u606f")
public class ResultErrorInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u9519\u8bef\u4ee3\u7801", name="errorCode")
    private ErrorCode errorCode;
    @ApiModelProperty(value="\u9519\u8bef\u4fe1\u606f", name="errorInfo")
    private String errorInfo;

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}

