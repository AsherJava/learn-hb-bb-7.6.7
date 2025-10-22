/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 */
package com.jiuqi.nr.customExcelBatchImport.bean;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

@ApiModel(value="ErrorInfo", description="\u81ea\u5b9a\u4e49\u5bfc\u5165\u9519\u8bef\u4fe1\u606f")
public class ErrorInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String errorMsg;
    private String FileName;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String fileName) {
        this.FileName = fileName;
    }
}

