/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.inputdata.dto;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class GcBatchCopyOrgAndFormTableEnvDTO {
    private String taskId;
    private String schemeId;
    private boolean afterRealTimeOffset;
    private String srcPeriod;
    private String srcSelectAdjustCode;
    private GcOrgCacheVO org;
    private FormDefine formDefine;
    private TableModelDefine tableDefine;
    private DataRegionDefine dataRegionDefine;
    private DimensionValueSet dimensionValueSet;

    public boolean isAfterRealTimeOffset() {
        return this.afterRealTimeOffset;
    }

    public void setAfterRealTimeOffset(boolean afterRealTimeOffset) {
        this.afterRealTimeOffset = afterRealTimeOffset;
    }

    public String getSrcPeriod() {
        return this.srcPeriod;
    }

    public void setSrcPeriod(String srcPeriod) {
        this.srcPeriod = srcPeriod;
    }

    public String getSrcSelectAdjustCode() {
        return this.srcSelectAdjustCode;
    }

    public void setSrcSelectAdjustCode(String srcSelectAdjustCode) {
        this.srcSelectAdjustCode = srcSelectAdjustCode;
    }

    public GcOrgCacheVO getOrg() {
        return this.org;
    }

    public void setOrg(GcOrgCacheVO org) {
        this.org = org;
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    public TableModelDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(TableModelDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public void setDataRegionDefine(DataRegionDefine dataRegionDefine) {
        this.dataRegionDefine = dataRegionDefine;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}

