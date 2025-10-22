/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.customExcelBatchImport.bean;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.customExcelBatchImport.bean.ErrorInfo;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value="CustomExcelCheckResultInfo", description="\u81ea\u5b9a\u4e49excel\u5bfc\u5165\u6587\u4ef6\u6821\u9a8c\u7ed3\u679c")
public class CustomExcelCheckResultInfo {
    @ApiModelProperty(value="\u8bc6\u522b\u5230\u7684\u533a\u57df", name="region")
    private DataRegionDefine region;
    @ApiModelProperty(value="\u7ef4\u5ea6\u6307\u6807\u884c", name="dimFieldIndex")
    private int dimFieldIndex;
    @ApiModelProperty(value="\u8bc6\u522b\u5230\u7684\u7ef4\u5ea6\u6307\u6807\u5217\u8868", name="dimFields")
    private Map<String, List<FieldDefine>> dimFields;
    @ApiModelProperty(value="\u8bc6\u522b\u5230\u7684\u666e\u901a\u6307\u6807\u5217\u8868", name="fields")
    private List<FieldDefine> fields;
    @ApiModelProperty(value="\u6587\u4ef6\u4e2d\u8bc6\u522b\u7684\u6307\u6807\u5217\u8868\uff08\u5305\u542b\u7ef4\u5ea6\u6307\u6807\uff09", name="fileFields")
    private List<FieldDefine> fileFields;
    @ApiModelProperty(value="\u6587\u4ef6\u4e2d\u8bc6\u522b\u7684\u6307\u6807\u5bf9\u5e94\u5217\u6570\uff08\u5305\u542b\u7ef4\u5ea6\u6307\u6807\uff09", name="fileFieldIndex")
    private List<Integer> fileFieldIndex;
    @ApiModelProperty(value="\u6839\u636e\u533a\u57df\u67e5\u8be2\u5230\u7684\u65f6\u671f\u6307\u6807\u4fe1\u606f", name="periodFiledInfo")
    private FieldDefine periodFiledInfo;
    @ApiModelProperty(value="\u6839\u636e\u533a\u57df\u67e5\u8be2\u5230\u7684\u8c03\u6574\u671f\u6307\u6807\u4fe1\u606f", name="adjustiledInfo")
    private FieldDefine adjustFiledInfo;
    @ApiModelProperty(value="\u6821\u9a8c\u4e2d\u7684\u9519\u8bef\u4fe1\u606f", name="errorInfos")
    private List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
    @ApiModelProperty(value="excel\u4e2d\u8bc6\u522b\u5230sheet\u9875\u7b7e\u7684\u4f4d\u7f6e", name="sheetIndex")
    private Integer sheetIndex = -1;

    public DataRegionDefine getRegion() {
        return this.region;
    }

    public void setRegion(DataRegionDefine region) {
        this.region = region;
    }

    public int getDimFieldIndex() {
        return this.dimFieldIndex;
    }

    public void setDimFieldIndex(int dimFieldIndex) {
        this.dimFieldIndex = dimFieldIndex;
    }

    public List<FieldDefine> getFields() {
        return this.fields;
    }

    public void setFields(List<FieldDefine> fields) {
        this.fields = fields;
    }

    public List<ErrorInfo> getErrorInfos() {
        return this.errorInfos;
    }

    public void setErrorInfos(List<ErrorInfo> errorInfos) {
        this.errorInfos = errorInfos;
    }

    public Map<String, List<FieldDefine>> getDimFields() {
        return this.dimFields;
    }

    public void setDimFields(Map<String, List<FieldDefine>> dimFields) {
        this.dimFields = dimFields;
    }

    public List<FieldDefine> getFileFields() {
        return this.fileFields;
    }

    public void setFileFields(List<FieldDefine> fileFields) {
        this.fileFields = fileFields;
    }

    public List<Integer> getFileFieldIndex() {
        return this.fileFieldIndex;
    }

    public void setFileFieldIndex(List<Integer> fileFieldIndex) {
        this.fileFieldIndex = fileFieldIndex;
    }

    public FieldDefine getPeriodFiledInfo() {
        return this.periodFiledInfo;
    }

    public void setPeriodFiledInfo(FieldDefine periodFiledInfo) {
        this.periodFiledInfo = periodFiledInfo;
    }

    public FieldDefine getAdjustFiledInfo() {
        return this.adjustFiledInfo;
    }

    public void setAdjustFiledInfo(FieldDefine adjustFiledInfo) {
        this.adjustFiledInfo = adjustFiledInfo;
    }

    public Integer getSheetIndex() {
        return this.sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }
}

