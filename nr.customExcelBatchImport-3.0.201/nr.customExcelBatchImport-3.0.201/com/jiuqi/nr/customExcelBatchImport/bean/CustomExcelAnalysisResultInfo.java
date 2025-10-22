/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.customExcelBatchImport.bean;

import com.jiuqi.nr.customExcelBatchImport.bean.CustomExcelCheckResultInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="CustomExcelAnalysisResultInfo", description="\u81ea\u5b9a\u4e49excel\u5bfc\u5165\u6587\u4ef6\u89e3\u6790\u7ed3\u679c")
public class CustomExcelAnalysisResultInfo {
    @ApiModelProperty(value="\u81ea\u5b9a\u4e49excel\u5bfc\u5165\u6587\u4ef6\u6821\u9a8c\u7ed3\u679c", name="checkResult")
    private CustomExcelCheckResultInfo checkResult;
    @ApiModelProperty(value="\u89e3\u6790\u51fa\u6765\u7684\u6570\u636e\u884c", name="dataRows")
    private List<List<Object>> dataRows;
    @ApiModelProperty(value="\u89e3\u6790\u51fa\u6765\u7684\u201c\u6587\u4ef6\u540d\u79f0-sheet\u540d\u79f0\u201d", name="sheetNames")
    private final Map<String, String> sheetNames = new HashMap<String, String>();

    public CustomExcelCheckResultInfo getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(CustomExcelCheckResultInfo checkResult) {
        this.checkResult = checkResult;
    }

    public List<List<Object>> getDataRows() {
        return this.dataRows;
    }

    public void setDataRows(List<List<Object>> dataRows) {
        this.dataRows = dataRows;
    }

    public Map<String, String> getSheetNames() {
        return this.sheetNames;
    }
}

