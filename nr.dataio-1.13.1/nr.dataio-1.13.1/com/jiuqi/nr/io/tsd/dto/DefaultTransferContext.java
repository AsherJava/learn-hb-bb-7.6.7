/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.data.common.service.DescRecorder
 *  com.jiuqi.nr.data.common.service.ExpSettings
 *  com.jiuqi.nr.data.common.service.ImpSettings
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.Result
 *  com.jiuqi.nr.data.common.service.StatisticalRecorder
 *  com.jiuqi.nr.data.common.service.TransferContext
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.data.common.service.DescRecorder;
import com.jiuqi.nr.data.common.service.ExpSettings;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.Result;
import com.jiuqi.nr.data.common.service.StatisticalRecorder;
import com.jiuqi.nr.data.common.service.TransferContext;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTransferContext
implements TransferContext {
    private DimensionCollection masterKeys;
    private String taskKey;
    private String formSchemeKey;
    private List<String> formKeys;
    private IProviderStore providerStore;
    private ParamsMapping paramsMapping;
    private ExpSettings exportSettings;
    private ImpSettings importSettings;
    private IProgressMonitor monitor;
    private List<String> nonexistentUnits;
    private final Map<String, Result> resultMap = new HashMap<String, Result>();
    private final Map<String, StatisticalRecorder> statisticalRecorderMap = new HashMap<String, StatisticalRecorder>();
    private final Map<String, DescRecorder> descRecorderMap = new HashMap<String, DescRecorder>();
    private String periodValue;
    private int allDwCount;
    private int failedDwCount;

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public int getAllDwCount() {
        return this.allDwCount;
    }

    public void setAllDwCount(int allDwCount) {
        this.allDwCount = allDwCount;
    }

    public int getFailedDwCount() {
        return this.failedDwCount;
    }

    public void setFailedDwCount(int failedDwCount) {
        this.failedDwCount = failedDwCount;
    }

    public DimensionCollection getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionCollection masterKeys) {
        this.masterKeys = masterKeys;
    }

    public List<String> getNonexistentUnits() {
        return this.nonexistentUnits;
    }

    public void setNonexistentUnits(List<String> nonexistentUnits) {
        this.nonexistentUnits = nonexistentUnits;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public void setParamsMapping(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }

    public ExpSettings getExportSettings() {
        return this.exportSettings;
    }

    public ImpSettings getImportSettings() {
        return this.importSettings;
    }

    public void setImportSettings(ImpSettings importSettings) {
        this.importSettings = importSettings;
    }

    public void setExportSettings(ExpSettings exportSettings) {
        this.exportSettings = exportSettings;
    }

    public Result getResult(String code) {
        return this.resultMap.get(code);
    }

    public void setResult(String code, Result result) {
        this.resultMap.put(code, result);
    }

    public IProgressMonitor getProgressMonitor() {
        return this.monitor;
    }

    public DescRecorder getDescRecorder(String code) {
        return this.descRecorderMap.get(code);
    }

    public void putDescRecorder(String code, DescRecorder descRecorder) {
        this.descRecorderMap.put(code, descRecorder);
    }

    public StatisticalRecorder getStatisticalRecord(String code) {
        return this.statisticalRecorderMap.get(code);
    }

    public void setMonitor(IProgressMonitor monitor) {
        this.monitor = monitor;
    }

    public void putStatisticalRecorder(String importType, StatisticalRecorder statisticalRecorder) {
        this.statisticalRecorderMap.put(importType, statisticalRecorder);
    }
}

