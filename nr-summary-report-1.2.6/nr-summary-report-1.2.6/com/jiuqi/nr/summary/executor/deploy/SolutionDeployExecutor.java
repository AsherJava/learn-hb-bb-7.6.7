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
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.executor.deploy.FormulaDeployExecutor;
import com.jiuqi.nr.summary.executor.deploy.ReportDeployExecutor;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryFormulaService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.utils.SummaryLoggerHelper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

class SolutionDeployExecutor
extends AbstractExecutor {
    private final AbstractExecutor reportExecutor;
    private final AbstractExecutor formulaExecutor;

    public SolutionDeployExecutor(String solutionKey, AsyncTaskMonitor monitor, IDesignSummarySolutionService designSolutionService, DataSourceTransactionManager transactionManager, IDesignDataSchemeService iDesignDataSchemeService, IDataSchemeDeployService dataSchemeDeployService, IDesignSummaryReportService designReportService, IDesignSummaryDataCellService designDataCellService, IDesignSummaryFormulaService designFormulaService, IRuntimeSummaryReportService runtimeReportService, IRuntimeSummaryDataCellService runtimeDataCellService, IRuntimeSummaryFormulaService runtimeFormulaService) {
        super(new DeployContext(), monitor);
        this.initContext(solutionKey, designSolutionService);
        this.reportExecutor = new ReportDeployExecutor(this.context, monitor, designSolutionService, transactionManager, iDesignDataSchemeService, dataSchemeDeployService, designReportService, designDataCellService, runtimeReportService, runtimeDataCellService);
        this.formulaExecutor = new FormulaDeployExecutor(this.context, monitor, designFormulaService, runtimeFormulaService);
    }

    private void initContext(String solutionKey, IDesignSummarySolutionService designSolutionService) {
        SummarySolutionModel summarySolutionModel = designSolutionService.getSummarySolutionModel(solutionKey);
        this.context.setSolutionModel(summarySolutionModel);
    }

    @Override
    public boolean execute() {
        this.step(0.0, "\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u53d1\u5e03\u5f00\u59cb");
        SummarySolutionModel solutionModel = this.context.getSolutionModel();
        assert (solutionModel != null);
        try {
            this.reportExecutor.execute();
            this.formulaExecutor.execute();
            SummaryLoggerHelper.info("\u6c47\u603b\u4efb\u52a1\u53d1\u5e03", solutionModel.getTitle() + "\u53d1\u5e03\u6210\u529f");
            this.logFinished("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u53d1\u5e03\u7ed3\u675f");
        }
        catch (Exception e) {
            SummaryLoggerHelper.error("\u6c47\u603b\u4efb\u52a1\u53d1\u5e03", solutionModel.getTitle() + "\u53d1\u5e03\u5931\u8d25");
            this.logError(e);
        }
        return true;
    }
}

