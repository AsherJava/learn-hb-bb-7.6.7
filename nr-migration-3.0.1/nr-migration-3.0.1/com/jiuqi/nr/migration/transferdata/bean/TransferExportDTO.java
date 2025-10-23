/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.ExportParam;
import com.jiuqi.nr.migration.transferdata.bean.OptionConfig;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferExportDTO
implements Serializable {
    private String taskId;
    private String formSchemeId;
    private String mappingSchemeId;
    private Map<String, DimensionValue> dimensionValueSet = new HashMap<String, DimensionValue>();
    private OptionConfig option;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getMappingSchemeId() {
        return this.mappingSchemeId;
    }

    public void setMappingSchemeId(String mappingSchemeId) {
        this.mappingSchemeId = mappingSchemeId;
    }

    public Map<String, DimensionValue> getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(Map<String, DimensionValue> dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public OptionConfig getOption() {
        return this.option;
    }

    public void setOption(OptionConfig option) {
        this.option = option;
    }

    public ExportParam toTransferExportVo() {
        ExportParam vo = new ExportParam();
        vo.setTaskId(this.taskId);
        vo.setFormSchemeId(this.formSchemeId);
        vo.setMappingSchemeId(this.mappingSchemeId);
        List<DimInfo> dimInfoList = TransferUtils.toDimInfoList(this.dimensionValueSet);
        vo.setCreateXM(this.option.isCreateXM());
        vo.setSynCheckErrorDes(this.option.isSynCheckErrorDesc());
        vo.setSynRange(this.option.getSynRange());
        vo.setSynCommitMemo(this.option.isSynCommitMemo());
        return vo;
    }
}

