/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.List;
import java.util.Map;

public class DataDockingDataRowVO {
    private Map<String, Object> dimension;
    private DataRegionDefine dataRegionDefine;
    private FormDefine formDefine;
    private List<String> fieldCodes;
    private List<String> fieldValues;
    private Integer rowNum;

    public Map<String, Object> getDimension() {
        return this.dimension;
    }

    public void setDimension(Map<String, Object> dimension) {
        this.dimension = dimension;
    }

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public void setDataRegionDefine(DataRegionDefine dataRegionDefine) {
        this.dataRegionDefine = dataRegionDefine;
    }

    public List<String> getFieldCodes() {
        return this.fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }

    public List<String> getFieldValues() {
        return this.fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    public Integer getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }
}

