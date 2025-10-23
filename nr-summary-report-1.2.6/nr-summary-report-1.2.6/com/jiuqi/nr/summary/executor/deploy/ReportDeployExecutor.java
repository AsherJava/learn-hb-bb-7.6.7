/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DataCellExecutor;
import com.jiuqi.nr.summary.executor.deploy.DataSchemeExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.executor.deploy.comparator.ComparatorHolder;
import com.jiuqi.nr.summary.internal.dto.DesignSummaryReportDTO;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryDataCellService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import com.jiuqi.nr.summary.model.report.SummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.utils.ParamComparator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

class ReportDeployExecutor
extends AbstractExecutor {
    private final AbstractExecutor dataCellExecutor;
    private final AbstractExecutor dataSchemeExecutor;
    private final IDesignSummarySolutionService designSolutionService;
    private final IDesignDataSchemeService designDataSchemeService;
    private final IDesignSummaryReportService designReportService;
    private final IRuntimeSummaryReportService runtimeReportService;
    private final IRuntimeSummarySolutionService runtimeSolutionService;
    private final DataSourceTransactionManager transactionManager;

    public ReportDeployExecutor(DeployContext context, AsyncTaskMonitor monitor, IDesignSummarySolutionService designSolutionService, DataSourceTransactionManager transactionManager, IDesignDataSchemeService iDesignDataSchemeService, IDataSchemeDeployService dataSchemeDeployService, IDesignSummaryReportService designReportService, IDesignSummaryDataCellService designDataCellService, IRuntimeSummaryReportService runtimeReportService, IRuntimeSummaryDataCellService runtimeDataCellService) {
        super(context, monitor);
        this.designSolutionService = designSolutionService;
        this.designDataSchemeService = iDesignDataSchemeService;
        this.designReportService = designReportService;
        this.runtimeReportService = runtimeReportService;
        this.transactionManager = transactionManager;
        this.dataSchemeExecutor = new DataSchemeExecutor(context, monitor, transactionManager, designReportService, runtimeReportService, iDesignDataSchemeService, dataSchemeDeployService);
        this.dataCellExecutor = new DataCellExecutor(context, monitor, designDataCellService, runtimeDataCellService);
        this.runtimeSolutionService = (IRuntimeSummarySolutionService)SpringBeanUtils.getBean(IRuntimeSummarySolutionService.class);
    }

    @Override
    public boolean execute() {
        String solutionKey = this.context.getSolutionModel().getKey();
        List<SummaryReport> designReports = this.designSolutionService.getNotEmptySummaryReportsBySolution(solutionKey);
        List<SummaryReport> runtimeReports = this.runtimeSolutionService.getSummaryReportDefinesBySolu(solutionKey);
        ParamComparator<SummaryReport, SummaryReport> reportComparator = ComparatorHolder.getSummaryReportComparator();
        reportComparator.processDiff(designReports, runtimeReports);
        List<SummaryReport> deleteList = reportComparator.getDeleteList();
        List<SummaryReport> modifyList = reportComparator.getModifyList();
        List<SummaryReport> insertList = reportComparator.getInsertList();
        int size = deleteList.size() + modifyList.size() + insertList.size();
        double step = 0.8f / (float)size;
        this.context.setTableStep(step);
        if (!CollectionUtils.isEmpty(deleteList)) {
            ArrayList deleteTitles = new ArrayList();
            ArrayList deleteKeys = new ArrayList();
            deleteList.forEach(dataGroup -> {
                deleteTitles.add(dataGroup.getTitle());
                deleteKeys.add(dataGroup.getKey());
            });
            this.designDataSchemeService.deleteDataGroups(deleteKeys);
            this.step(step * (double)deleteKeys.size(), "\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u5220\u9664\u6570\u636e\u8868\u5206\u7ec4\u3010%s\u3011", String.join((CharSequence)",", deleteTitles));
        }
        this.processList(modifyList);
        this.processList(insertList);
        return true;
    }

    private void processList(List<SummaryReport> summaryReports) {
        for (SummaryReport report : summaryReports) {
            this.context.resetDataTableMap();
            this.deployPerReport(report);
            this.step(this.context.getTableStep(), "\u6c47\u603b\u8868\u3010%s\u3011\u5904\u7406\u5b8c\u6bd5", report.getTitle());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void deployPerReport(SummaryReport report) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = this.transactionManager.getTransaction((TransactionDefinition)transactionDefinition);
        try {
            this.context.setCurrReport(report);
            SummaryReportModel designReportModel = this.designSolutionService.getSummaryReportModel(report.getKey());
            this.context.setDesignReportModel(designReportModel);
            if (!this.dataSchemeExecutor.execute()) {
                this.transactionManager.rollback(status);
                return;
            }
            this.deploySummaryReport(report.getKey());
            this.dataCellExecutor.execute();
            this.transactionManager.commit(status);
        }
        catch (Exception e) {
            this.transactionManager.rollback(status);
            this.error("\u6c47\u603b\u8868\u3010%s\u3011\u5904\u7406\u5931\u8d25", report.getTitle());
        }
        finally {
            this.context.setDesignReportModel(null);
            this.context.setRunTimeReportModel(null);
            this.context.setCurrReport(null);
        }
    }

    public void deploySummaryReport(String reportKey) throws SummaryCommonException {
        this.runtimeReportService.deleteSummaryReportByKey(reportKey);
        DesignSummaryReportDTO reportDTO = this.designReportService.getSummaryReportByKey(reportKey, true);
        this.runtimeReportService.insertSummaryReport(reportDTO);
        this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6c47\u603b\u65b9\u6848:\u540c\u6b65\u6c47\u603b\u8868\u3010%s\u3011\u5230\u8fd0\u884c\u671f", reportDTO.getTitle());
    }
}

