/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillResult", description="\u901a\u7528\u8fd4\u56de\u7ed3\u679c")
public class DataFillResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u662f\u5426\u6210\u529f", name="success")
    private boolean success;
    @ApiModelProperty(value="\u63d0\u793a\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u6570\u636e\u9519\u8bef\u4fe1\u606f", name="errors")
    private List<DataFillSaveErrorDataInfo> errors;
    @ApiModelProperty(value="\u9519\u8bef\u6807\u8bc6\u6587\u6863\u8def\u5f84", name="errorMarkExcelPath")
    private String errorMarkExcelPath;

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

    public List<DataFillSaveErrorDataInfo> getErrors() {
        return this.errors;
    }

    public void setErrors(List<DataFillSaveErrorDataInfo> errors) {
        this.errors = errors;
    }

    public void setErrorMarkExcelPath(String errorMarkExcelPath) {
        this.errorMarkExcelPath = errorMarkExcelPath;
    }

    public String getErrorMarkExcelPath() {
        return this.errorMarkExcelPath;
    }
}

