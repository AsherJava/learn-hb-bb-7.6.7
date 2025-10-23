/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.executor.deploy.IDeployExecutor;
import com.jiuqi.nr.summary.executor.deploy.SolutionDeployExecutor;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class SummaryDeployExecutorFactory {
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private IDesignSummarySolutionService designSolutionService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDesignSummaryReportService designReportService;
    @Autowired
    private IDesignSummaryDataCellService designDataCellService;
    @Autowired
    private IDesignSummaryFormulaService designFormulaService;
    @Autowired
    private IRuntimeSummaryReportService runtimeReportService;
    @Autowired
    private IRuntimeSummaryDataCellService runtimeDataCellService;
    @Autowired
    private IRuntimeSummaryFormulaService runtimeFormulaService;
    @Autowired(required=false)
    private DataSourceTransactionManager transactionManager;

    public IDeployExecutor getDeployExecutor(String solutionKey, AsyncTaskMonitor monitor) {
        return new SolutionDeployExecutor(solutionKey, monitor, this.designSolutionService, this.transactionManager, this.designDataSchemeService, this.dataSchemeDeployService, this.designReportService, this.designDataCellService, this.designFormulaService, this.runtimeReportService, this.runtimeDataCellService, this.runtimeFormulaService);
    }
}

