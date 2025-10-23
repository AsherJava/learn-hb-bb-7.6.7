/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckItemVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultcheckItemParamVO {
    private List<MultcheckItemVO> items;
    private List<IEntityDefine> dimsForReport;
    private boolean hasDimsForReport;
    private Map<String, String> schemeDims;
    private Map<String, Map<String, String>> itemsDims;
    private boolean adjust;
    private String taskKey;
    private List<String> supportDimensionTypes;

    public List<MultcheckItemVO> getItems() {
        return this.items;
    }

    public void setItems(List<MultcheckItemVO> items) {
        this.items = items;
    }

    public List<IEntityDefine> getDimsForReport() {
        return this.dimsForReport;
    }

    public void setDimsForReport(List<IEntityDefine> dimsForReport) {
        this.dimsForReport = dimsForReport;
    }

    public void addDim(IEntityDefine entityDefine) {
        if (this.dimsForReport == null) {
            this.dimsForReport = new ArrayList<IEntityDefine>();
        }
        this.dimsForReport.add(entityDefine);
    }

    public boolean isHasDimsForReport() {
        return this.hasDimsForReport;
    }

    public void setHasDimsForReport(boolean hasDimsForReport) {
        this.hasDimsForReport = hasDimsForReport;
    }

    public Map<String, String> getSchemeDims() {
        return this.schemeDims;
    }

    public void setSchemeDims(Map<String, String> schemeDims) {
        this.schemeDims = schemeDims;
    }

    public Map<String, Map<String, String>> getItemsDims() {
        return this.itemsDims;
    }

    public void setItemsDims(Map<String, Map<String, String>> itemsDims) {
        this.itemsDims = itemsDims;
    }

    public boolean isAdjust() {
        return this.adjust;
    }

    public void setAdjust(boolean adjust) {
        this.adjust = adjust;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getSupportDimensionTypes() {
        return this.supportDimensionTypes;
    }

    public void setSupportDimensionTypes(List<String> supportDimensionTypes) {
        this.supportDimensionTypes = supportDimensionTypes;
    }
}

