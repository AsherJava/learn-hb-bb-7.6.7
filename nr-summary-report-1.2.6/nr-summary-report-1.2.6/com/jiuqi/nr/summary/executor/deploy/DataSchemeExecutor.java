/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.jdbc.datasource.DataSourceTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.summary.executor.deploy.AbstractExecutor;
import com.jiuqi.nr.summary.executor.deploy.DataTableExecutor;
import com.jiuqi.nr.summary.executor.deploy.DeployContext;
import com.jiuqi.nr.summary.internal.service.IDesignSummaryReportService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryReportService;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.DimensionData;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

class DataSchemeExecutor
extends AbstractExecutor {
    private final DataTableExecutor dataTableExecutor;
    private final IDesignDataSchemeService designDataSchemeService;
    private final IDataSchemeDeployService dataSchemeDeployService;
    private final DataSourceTransactionManager transactionManager;

    public DataSchemeExecutor(DeployContext context, AsyncTaskMonitor monitor, DataSourceTransactionManager transactionManager, IDesignSummaryReportService designReportService, IRuntimeSummaryReportService runtimeReportService, IDesignDataSchemeService designDataSchemeService, IDataSchemeDeployService dataSchemeDeployService) {
        super(context, monitor);
        this.dataTableExecutor = new DataTableExecutor(context, monitor, designDataSchemeService, designReportService, runtimeReportService);
        this.transactionManager = transactionManager;
        this.designDataSchemeService = designDataSchemeService;
        this.dataSchemeDeployService = dataSchemeDeployService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean execute() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(Propagation.REQUIRES_NEW.value());
        TransactionStatus status = this.transactionManager.getTransaction((TransactionDefinition)transactionDefinition);
        try {
            this.dataSchemeProcess();
            this.dataTableGroupProcess();
            if (!this.dataTableExecutor.execute()) {
                this.transactionManager.rollback(status);
                boolean bl = false;
                return bl;
            }
            this.transactionManager.commit(status);
        }
        catch (Exception e) {
            this.transactionManager.rollback(status);
            this.error(e);
            boolean bl = false;
            return bl;
        }
        finally {
            this.context.setCurrDataGroup(null);
        }
        if (this.context.isRefreshDataScheme()) {
            this.deployDataScheme(this.context);
        }
        return true;
    }

    private void dataSchemeProcess() {
        SummarySolutionModel solutionModel = this.context.getSolutionModel();
        assert (solutionModel != null);
        DesignDataScheme designDataScheme = this.designDataSchemeService.getDataScheme(solutionModel.getKey());
        if (designDataScheme == null) {
            designDataScheme = this.doCreateDataScheme(solutionModel, designDataScheme);
        }
        this.context.setDataScheme((DataScheme)designDataScheme);
        this.context.setRefreshDataScheme(true);
    }

    private DesignDataScheme doCreateDataScheme(SummarySolutionModel solution, DesignDataScheme designDataScheme) {
        DesignDataScheme dataScheme = designDataScheme;
        if (dataScheme == null) {
            dataScheme = this.designDataSchemeService.initDataScheme();
            dataScheme.setKey(solution.getKey());
            dataScheme.setTitle(solution.getTitle());
            dataScheme.setCode(solution.getName());
            dataScheme.setOrder(OrderGenerator.newOrder());
            dataScheme.setDesc("\u81ea\u5b9a\u4e49\u6c47\u603b\u65b9\u6848\u3010" + solution.getTitle() + "\u3011");
            this.doCreateDataSchemeGroup();
            dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000001234");
            ArrayList<DesignDataDimension> dimList = new ArrayList<DesignDataDimension>();
            this.addMasterDimension(dataScheme, dimList);
            this.addPeriodDimension(dataScheme, dimList);
            this.addSceneDimension(dataScheme, dimList);
            this.designDataSchemeService.insertDataScheme(dataScheme, dimList);
            this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u521b\u5efa\u6570\u636e\u65b9\u6848\u3010%s\u3011", dataScheme.getTitle());
        }
        return dataScheme;
    }

    private void doCreateDataSchemeGroup() {
        DesignDataGroup dataGroup = this.designDataSchemeService.getDataGroup("00000000-0000-0000-0000-000000001234");
        if (dataGroup == null) {
            dataGroup = this.designDataSchemeService.initDataGroup();
            dataGroup.setKey("00000000-0000-0000-0000-000000001234");
            dataGroup.setTitle("\u81ea\u5b9a\u4e49\u6c47\u603b");
            dataGroup.setParentKey("00000000-0000-0000-0000-000000000000");
            dataGroup.setDataGroupKind(DataGroupKind.SCHEME_GROUP);
            dataGroup.setOrder(OrderGenerator.newOrder());
            dataGroup.setDesc("\u81ea\u5b9a\u4e49\u6c47\u603b\u5206\u7ec4");
            this.designDataSchemeService.insertDataGroup(dataGroup);
            this.logInfo("\u6c47\u603b\u65b9\u6848\u53d1\u5e03\u4efb\u52a1:\u5904\u7406\u6570\u636e\u65b9\u6848:\u521b\u5efa\u6570\u636e\u65b9\u6848\u5206\u7ec4\u3010%s\u3011", dataGroup.getTitle());
        }
    }

    private void addMasterDimension(DesignDataScheme dataScheme, List<DesignDataDimension> dimList) {
        DesignDataDimension orgDim = this.designDataSchemeService.initDataSchemeDimension();
        orgDim.setDataSchemeKey(dataScheme.getKey());
        orgDim.setDimKey(Objects.requireNonNull(this.context.getSolutionModel()).getTargetDimension());
        orgDim.setDimensionType(DimensionType.UNIT);
        orgDim.setOrder(OrderGenerator.newOrder());
        dimList.add(orgDim);
    }

    private void addPeriodDimension(DesignDataScheme dataScheme, List<DesignDataDimension> dimList) {
        DesignDataDimension periodDim = this.designDataSchemeService.initDataSchemeDimension();
        periodDim.setDataSchemeKey(dataScheme.getKey());
        IRunTimeViewController viewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        TaskDefine taskDefine = viewController.queryTaskDefine(Objects.requireNonNull(this.context.getSolutionModel()).getMainTask());
        periodDim.setDimKey(taskDefine.getDateTime());
        periodDim.setDimensionType(DimensionType.PERIOD);
        periodDim.setPeriodType(taskDefine.getPeriodType());
        periodDim.setOrder(OrderGenerator.newOrder());
        dimList.add(periodDim);
    }

    private void addSceneDimension(DesignDataScheme dataScheme, List<DesignDataDimension> dimList) {
        SummarySolutionModel solutionModel = this.context.getSolutionModel();
        List<DimensionData> dimDataRange = Objects.requireNonNull(solutionModel).getDimDataRange();
        if (!CollectionUtils.isEmpty(dimDataRange)) {
            dimDataRange.forEach(dimensionData -> {
                DesignDataDimension dim = this.designDataSchemeService.initDataSchemeDimension();
                dim.setDataSchemeKey(dataScheme.getKey());
                dim.setDimKey(dimensionData.getName());
                dim.setDimensionType(DimensionType.DIMENSION);
                dimList.add(dim);
            });
        }
    }

    private void dataTableGroupProcess() {
        DesignDataGroup designDataGroup = this.doCreateOrUpdateDataGroup(this.context.getDataScheme());
        this.context.setCurrDataGroup((DataGroup)designDataGroup);
    }

    private DesignDataGroup doCreateOrUpdateDataGroup(DataScheme dataScheme) {
        SummaryReportModel designReportModel = this.context.getDesignReportModel();
        DesignDataGroup designDataGroup = this.designDataSchemeService.getDataGroup(this.context.getCurrReport().getKey());
        boolean update = true;
        if (designDataGroup == null) {
            designDataGroup = this.designDataSchemeService.initDataGroup();
            update = false;
        }
        designDataGroup.setKey(this.context.getCurrReport().getKey());
        designDataGroup.setTitle(designReportModel.getTitle());
        designDataGroup.setDataSchemeKey(dataScheme.getKey());
        designDataGroup.setParentKey(dataScheme.getKey());
        designDataGroup.setDataGroupKind(DataGroupKind.TABLE_GROUP);
        designDataGroup.setOrder(OrderGenerator.newOrder());
        designDataGroup.setDesc(designReportModel.getTitle());
        designDataGroup.setUpdateTime(Instant.ofEpochMilli(this.context.getCurrReport().getModifyTime().getTime()));
        if (update) {
            this.designDataSchemeService.updateDataGroup(designDataGroup);
        } else {
            this.designDataSchemeService.insertDataGroup(designDataGroup);
        }
        return designDataGroup;
    }

    private String buildDataSchemeCode() {
        return "SR_" + Objects.requireNonNull(this.context.getSolutionModel()).getName();
    }

    private void deployDataScheme(DeployContext context) {
        String dataSchemeKey = context.getDataScheme().getKey();
        this.dataSchemeDeployService.deployDataScheme(dataSchemeKey, progressItem -> {}, null);
        context.setRefreshDataScheme(false);
    }
}

