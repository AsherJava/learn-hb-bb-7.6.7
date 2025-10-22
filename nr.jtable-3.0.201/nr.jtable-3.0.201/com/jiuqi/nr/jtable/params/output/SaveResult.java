/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.jtable.params.output.FMDMCheckFailNodeInfoExtend;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="SaveResult", description="\u6570\u636e\u4fdd\u5b58\u7ed3\u679c")
public class SaveResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u4fdd\u5b58\u7ed3\u679c\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u4fdd\u5b58\u7ed3\u679c\u9519\u8bef\u4fe1\u606f\u5217\u8868", name="errors")
    private List<SaveErrorDataInfo> errors = new ArrayList<SaveErrorDataInfo>();
    @ApiModelProperty(value="\u4fdd\u5b58\u524d\u540erowkey\u8f6c\u6362map", name="rowKeyMap")
    private Map<String, String> rowKeyMap = new HashMap<String, String>();
    @ApiModelProperty(value="\u4fdd\u5b58\u540e\u65b0\u589e\u884c\u7684\u6392\u5e8f\u503c", name="floatOrderMap")
    private Map<String, Object> floatOrderMap = new HashMap<String, Object>();
    @ApiModelProperty(value="\u4fdd\u5b58\u540e\u8fd0\u7b97\u7ed3\u679c", name="calculateReturnInfo")
    private ReturnInfo calculateReturnInfo;
    @ApiModelProperty(value="\u4fdd\u5b58\u540e\u5ba1\u6838\u7ed3\u679c", name="checkReturnInfo")
    private FormulaCheckReturnInfo checkReturnInfo;
    @ApiModelProperty(value="\u5c01\u9762\u4ee3\u7801\u4fdd\u5b58\u8fd4\u56de\u503c", name="unitCode")
    private String unitCode;
    @ApiModelProperty(value="\u5c01\u9762\u4ee3\u7801\u5ba1\u6838\u7ed3\u679c", name="fMDMCheckFailNodeInfoExtend")
    private List<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtend;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SaveErrorDataInfo> getErrors() {
        return this.errors;
    }

    public void setErrors(List<SaveErrorDataInfo> errors) {
        this.errors = errors;
    }

    public Map<String, String> getRowKeyMap() {
        return this.rowKeyMap;
    }

    public void setRowKeyMap(Map<String, String> rowKeyMap) {
        this.rowKeyMap = rowKeyMap;
    }

    public Map<String, Object> getFloatOrderMap() {
        return this.floatOrderMap;
    }

    public void setFloatOrderMap(Map<String, Object> floatOrderMap) {
        this.floatOrderMap = floatOrderMap;
    }

    public ReturnInfo getCalculateReturnInfo() {
        return this.calculateReturnInfo;
    }

    public void setCalculateReturnInfo(ReturnInfo calculateReturnInfo) {
        this.calculateReturnInfo = calculateReturnInfo;
    }

    public FormulaCheckReturnInfo getCheckReturnInfo() {
        return this.checkReturnInfo;
    }

    public void setCheckReturnInfo(FormulaCheckReturnInfo checkReturnInfo) {
        this.checkReturnInfo = checkReturnInfo;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public List<FMDMCheckFailNodeInfoExtend> getfMDMCheckFailNodeInfoExtend() {
        return this.fMDMCheckFailNodeInfoExtend;
    }

    public void setfMDMCheckFailNodeInfoExtend(List<FMDMCheckFailNodeInfoExtend> fMDMCheckFailNodeInfoExtend) {
        this.fMDMCheckFailNodeInfoExtend = fMDMCheckFailNodeInfoExtend;
    }
}

