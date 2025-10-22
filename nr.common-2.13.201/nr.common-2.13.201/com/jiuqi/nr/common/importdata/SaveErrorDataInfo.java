/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.common.importdata;

import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="SaveErrorDataInfo", description="\u4fdd\u5b58\u7ed3\u679c\u9519\u8bef\u4fe1\u606f")
public class SaveErrorDataInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u884cid", name="rowId")
    private String rowId;
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey")
    private String fieldKey;
    @ApiModelProperty(value="\u6307\u6807Code", name="fieldCode")
    private String fieldCode;
    @ApiModelProperty(value="\u6307\u6807\u6807\u9898", name="fieldTitle")
    private String fieldTitle;
    @ApiModelProperty(value="\u6570\u636e\u7d22\u5f15", name="dataIndex")
    private Integer dataIndex;
    @ApiModelProperty(value="\u9519\u8bef\u4fe1\u606f", name="dataError")
    private ResultErrorInfo dataError;

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public Integer getDataIndex() {
        return this.dataIndex;
    }

    public void setDataIndex(Integer dataIndex) {
        this.dataIndex = dataIndex;
    }

    public ResultErrorInfo getDataError() {
        if (null == this.dataError) {
            this.dataError = new ResultErrorInfo();
        }
        return this.dataError;
    }

    public void setDataError(ResultErrorInfo dataError) {
        this.dataError = dataError;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
}

