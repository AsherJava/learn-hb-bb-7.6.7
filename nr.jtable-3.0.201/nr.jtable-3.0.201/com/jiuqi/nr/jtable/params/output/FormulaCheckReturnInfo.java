/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="FormulaCheckReturnInfo", description="\u516c\u5f0f\u5ba1\u6838\u7ed3\u679c\u96c6")
public class FormulaCheckReturnInfo
implements Serializable {
    private static final long serialVersionUID = -5828152656608330777L;
    @ApiModelProperty(value="\u5ba1\u6838\u7ed3\u679c\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u5ba1\u6838\u7ed3\u679c\u603b\u6570", name="totalCount")
    private int totalCount;
    @ApiModelProperty(value="\u63d0\u793a\u578b\u5ba1\u6838\u7ed3\u679c\u6570\u91cf", name="hintCount")
    private int hintCount;
    @ApiModelProperty(value="\u8b66\u544a\u578b\u5ba1\u6838\u7ed3\u679c\u6570\u91cf", name="warnCount")
    private int warnCount;
    @ApiModelProperty(value="\u9519\u8bef\u578b\u5ba1\u6838\u7ed3\u679c\u6570\u91cf", name="errorCount")
    private int errorCount;
    @ApiModelProperty(value="\u7ef4\u5ea6\u5217\u8868", name="dimensionList")
    private List<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();
    @ApiModelProperty(value="\u5ba1\u6838\u7ed3\u679c\u5217\u8868", name="results")
    private List<FormulaCheckResultInfo> results = new ArrayList<FormulaCheckResultInfo>();
    @ApiModelProperty(value="\u4e0a\u62a5\u72b6\u6001Map", name="results")
    private Map<Integer, Map<String, String>> uploadStateMap = new HashMap<Integer, Map<String, String>>();
    private List<String> ckdDescList = new ArrayList<String>();

    public List<String> getCkdDescList() {
        return this.ckdDescList;
    }

    public void setCkdDescList(List<String> ckdDescList) {
        this.ckdDescList = ckdDescList;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Map<String, DimensionValue>> getDimensionList() {
        return this.dimensionList;
    }

    public void setDimensionList(List<Map<String, DimensionValue>> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<FormulaCheckResultInfo> getResults() {
        return this.results;
    }

    public void setResults(List<FormulaCheckResultInfo> results) {
        this.results = results;
    }

    public int getHintCount() {
        return this.hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }

    public int getWarnCount() {
        return this.warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public int getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public Map<Integer, Map<String, String>> getUploadStateMap() {
        return this.uploadStateMap;
    }

    public void setUploadStateMap(Map<Integer, Map<String, String>> uploadStateMap) {
        this.uploadStateMap = uploadStateMap;
    }

    public void incrWranCount() {
        ++this.warnCount;
    }

    public void incrHintCount() {
        ++this.hintCount;
    }

    public void incrErrorCount() {
        ++this.errorCount;
    }
}

