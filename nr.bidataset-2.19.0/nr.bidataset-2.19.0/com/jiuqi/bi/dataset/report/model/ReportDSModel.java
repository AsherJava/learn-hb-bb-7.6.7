/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.report.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class ReportDSModel
extends DSModel {
    private ReportDsModelDefine reportDsModelDefine;
    private ReportDSModelBuilder modelBuilder;
    private Map<String, ParameterModel> dimParamMap = new HashMap<String, ParameterModel>();
    private TaskDefine taskDefine;
    private Set<String> schemeDims = new HashSet<String>();
    private IEntityDefine unitEntityDefnie;
    private List<String> publicDimFields = new ArrayList<String>();
    private List<Integer> sortFieldIndexes = new ArrayList<Integer>();
    private int unitKeyIndex = -1;
    private int unitOrderIndex = -1;
    private int unitParentIndex = -1;
    private int gaterDimType;
    private String gaterDimName;
    private String gatherSchemeKey;
    private String taskCurrentPeriod;

    public String getType() {
        return "ReportDataSet";
    }

    public void saveExtToJSON(JSONObject json) throws Exception {
        if (this.reportDsModelDefine == null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        String reportDsModelDefineStr = mapper.writeValueAsString((Object)this.reportDsModelDefine);
        json.put("reportDsModelDefine", (Object)new JSONObject(reportDsModelDefineStr));
        json.put("desc", (Object)this.getDescr());
    }

    public void loadExtFromJSON(JSONObject json) throws Exception {
        this.setDescr(json.optString("desc", ""));
        String reportDsModelDefineStr = json.optString("reportDsModelDefine");
        if (StringUtils.isNotEmpty((CharSequence)reportDsModelDefineStr) && !"NULL".equalsIgnoreCase(reportDsModelDefineStr)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            this.reportDsModelDefine = (ReportDsModelDefine)mapper.readValue(reportDsModelDefineStr, ReportDsModelDefine.class);
        }
    }

    public ReportDsModelDefine getReportDsModelDefine() {
        return this.reportDsModelDefine;
    }

    public void setReportDsModelDefine(ReportDsModelDefine reportDsModelDefine) {
        this.reportDsModelDefine = reportDsModelDefine;
    }

    public ReportDSModelBuilder getModelBuilder() {
        return this.modelBuilder;
    }

    public void setModelBuilder(ReportDSModelBuilder modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public List<String> getPublicDimFields() {
        return this.publicDimFields;
    }

    public Map<String, ParameterModel> getDimParamMap() {
        return this.dimParamMap;
    }

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public Set<String> getSchemeDims() {
        return this.schemeDims;
    }

    public int getUnitKeyIndex() {
        return this.unitKeyIndex;
    }

    public int getUnitOrderIndex() {
        return this.unitOrderIndex;
    }

    public int getUnitParentIndex() {
        return this.unitParentIndex;
    }

    public void setUnitKeyIndex(int unitKeyIndex) {
        this.unitKeyIndex = unitKeyIndex;
    }

    public void setUnitOrderIndex(int unitOrderIndex) {
        this.unitOrderIndex = unitOrderIndex;
    }

    public void setUnitParentIndex(int unitParentIndex) {
        this.unitParentIndex = unitParentIndex;
    }

    public List<Integer> getSortFieldIndexes() {
        return this.sortFieldIndexes;
    }

    public boolean isShowDetail() {
        return this.reportDsModelDefine.isShowDetail() && StringUtils.isNotEmpty((CharSequence)this.reportDsModelDefine.getGatherSchemeCode());
    }

    public boolean needUnitTreeBuilder() {
        return this.unitKeyIndex >= 0 && this.unitOrderIndex >= 0;
    }

    public IEntityDefine getUnitEntityDefnie() {
        return this.unitEntityDefnie;
    }

    public void setUnitEntityDefnie(IEntityDefine unitEntityDefnie) {
        this.unitEntityDefnie = unitEntityDefnie;
    }

    public String getUnitEntityId() {
        return this.unitEntityDefnie.getId();
    }

    public int getGaterDimType() {
        return this.gaterDimType;
    }

    public String getGaterDimName() {
        return this.gaterDimName;
    }

    public void setGaterDimType(int gaterDimType) {
        this.gaterDimType = gaterDimType;
    }

    public void setGaterDimName(String gaterDimName) {
        this.gaterDimName = gaterDimName;
    }

    public String getGatherSchemeKey() {
        return this.gatherSchemeKey;
    }

    public void setGatherSchemeKey(String gatherSchemeKey) {
        this.gatherSchemeKey = gatherSchemeKey;
    }

    public String getTaskCurrentPeriod() {
        return this.taskCurrentPeriod;
    }

    public void setTaskCurrentPeriod(String taskCurrentPeriod) {
        this.taskCurrentPeriod = taskCurrentPeriod;
    }
}

