/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.common.service;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.data.common.service.DescRecorder;
import com.jiuqi.nr.data.common.service.ExpSettings;
import com.jiuqi.nr.data.common.service.ImpSettings;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.Result;
import com.jiuqi.nr.data.common.service.StatisticalRecorder;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public interface TransferContext {
    public DimensionCollection getMasterKeys();

    public List<String> getNonexistentUnits();

    public String getTaskKey();

    public String getFormSchemeKey();

    public List<String> getFormKeys();

    public IProviderStore getProviderStore();

    public ParamsMapping getParamsMapping();

    public ExpSettings getExportSettings();

    public ImpSettings getImportSettings();

    public Result getResult(String var1);

    public void setResult(String var1, Result var2);

    public IProgressMonitor getProgressMonitor();

    public DescRecorder getDescRecorder(String var1);

    public StatisticalRecorder getStatisticalRecord(String var1);
}

