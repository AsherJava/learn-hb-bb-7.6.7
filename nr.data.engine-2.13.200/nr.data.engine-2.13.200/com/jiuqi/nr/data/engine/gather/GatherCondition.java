/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class GatherCondition {
    private String formSchemeKey;
    private EntityViewDefine unitView;
    private String periodCode;
    private boolean isRecursive;
    private GatherDirection gatherDirection;
    private List<GatherTableDefine> gatherTables;
    private DimensionValueSet sourceDimensions;
    private DimensionValueSet targetDimension;
    private BigDecimal precisionValue;
    private List<GatherTableDefine> successGatherTables;
    private NotGatherEntityValue notGatherEntityValue;
    private Boolean containTargetKey = false;
    private Boolean sumAfterParentUpload = false;
    private String taskKey;
    private String dataSchemeKey;
    private GatherEntityValue gatherEntityValue;
    private Map<Integer, Map<String, String>> bizKeyOrderMappings;
    private Map<String, String> gatherSingleDims = new LinkedHashMap<String, String>();
    private boolean BZHZBGather = false;
    private String gatherDimName;

    public EntityViewDefine getUnitView() {
        return this.unitView;
    }

    public void setUnitView(EntityViewDefine unitView) {
        this.unitView = unitView;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public boolean isRecursive() {
        return this.isRecursive;
    }

    public void setRecursive(boolean isRecursive) {
        this.isRecursive = isRecursive;
    }

    public GatherDirection getGatherDirection() {
        return this.gatherDirection;
    }

    public void setGatherDirection(GatherDirection gatherDirection) {
        this.gatherDirection = gatherDirection;
    }

    public List<GatherTableDefine> getGatherTables() {
        return this.gatherTables;
    }

    public void setGatherTables(List<GatherTableDefine> gatherTables) {
        this.gatherTables = gatherTables;
    }

    public DimensionValueSet getSourceDimensions() {
        return this.sourceDimensions;
    }

    public void setSourceDimensions(DimensionValueSet sourceDimensions) {
        this.sourceDimensions = sourceDimensions;
    }

    public DimensionValueSet getTargetDimension() {
        return this.targetDimension;
    }

    public void setTargetDimension(DimensionValueSet targetDimension) {
        this.targetDimension = targetDimension;
    }

    public BigDecimal getPrecisionValue() {
        return this.precisionValue;
    }

    public void setPrecisionValue(BigDecimal precisionValue) {
        this.precisionValue = precisionValue;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<GatherTableDefine> getSuccessGatherTables() {
        return this.successGatherTables;
    }

    void setSuccessGatherTables(List<GatherTableDefine> successGatherTables) {
        this.successGatherTables = successGatherTables;
    }

    public NotGatherEntityValue getNotGatherEntityValue() {
        return this.notGatherEntityValue;
    }

    public void setNotGatherEntityValue(NotGatherEntityValue notGatherEntityValue) {
        this.notGatherEntityValue = notGatherEntityValue;
    }

    public Boolean getContainTargetKey() {
        return this.containTargetKey;
    }

    public void setContainTargetKey(Boolean containTargetKey) {
        this.containTargetKey = containTargetKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Boolean getSumAfterParentUpload() {
        return this.sumAfterParentUpload;
    }

    public void setSumAfterParentUpload(Boolean sumAfterParentUpload) {
        this.sumAfterParentUpload = sumAfterParentUpload;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public GatherEntityValue getGatherEntityValue() {
        return this.gatherEntityValue;
    }

    public void setGatherEntityValue(GatherEntityValue gatherEntityValue) {
        this.gatherEntityValue = gatherEntityValue;
    }

    public Map<Integer, Map<String, String>> getBizKeyOrderMappings() {
        if (CollectionUtils.isEmpty(this.bizKeyOrderMappings)) {
            this.bizKeyOrderMappings = new HashMap<Integer, Map<String, String>>();
        }
        return this.bizKeyOrderMappings;
    }

    public void setBizKeyOrderMappings(Map<Integer, Map<String, String>> bizKeyOrderMappings) {
        this.bizKeyOrderMappings = bizKeyOrderMappings;
    }

    public Map<String, String> getGatherSingleDims() {
        return this.gatherSingleDims;
    }

    public void setGatherSingleDims(Map<String, String> gatherSingleDims) {
        this.gatherSingleDims = gatherSingleDims;
    }

    public boolean isBZHZBGather() {
        return this.BZHZBGather;
    }

    public void setBZHZBGather(boolean BZHZBGather) {
        this.BZHZBGather = BZHZBGather;
    }

    public String getGatherDimName() {
        return this.gatherDimName;
    }

    public void setGatherDimName(String gatherDimName) {
        this.gatherDimName = gatherDimName;
    }
}

