/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.io.params.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import java.util.List;

public class Datablocks {
    @JsonProperty(value="float")
    private boolean isFloat;
    @JsonProperty
    private List<ExportFieldDefine> fields;
    @JsonProperty
    private List<Object> datas;
    @JsonProperty
    private List<String> LinkDatasFilesName;
    private int regionTop;
    private int totalCount;

    public int getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<String> getLinkDatasFilesName() {
        return this.LinkDatasFilesName;
    }

    public void setLinkDatasFilesName(List<String> linkDatasFilesName) {
        this.LinkDatasFilesName = linkDatasFilesName;
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public void setIsFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public List<ExportFieldDefine> getFields() {
        return this.fields;
    }

    public void setFields(List<ExportFieldDefine> fields) {
        this.fields = fields;
    }

    public List<Object> getDatas() {
        return this.datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}

