/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider
 *  com.jiuqi.nr.subdatabase.facade.SubDataBase
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DataBaseTableProvider
implements IDataBaseTableProvider {
    @Resource
    private SubDataBaseInfoProvider subDataBaseInfoProvider;
    @Resource
    private SplitTableHelper splitTableHelper;
    @Resource
    public IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    public IRunTimeViewController runTimeViewController;
    @Resource
    public IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    protected TargetFromProviderFactoryImpl wrapper;

    @Override
    public TableModelDefine getOriginTableModelDefine(String tableModelKey, String taskId, String period) {
        TableModelDefine tableModelDefine = this.wrapper.dataModelService.getTableModelDefineById(tableModelKey);
        SubDataBase curDataBase = this.subDataBaseInfoProvider.getCurDataBase();
        if (curDataBase != null) {
            String splitTableName = this.splitTableHelper.getCurrentSplitTableName(this.buildQueryContext(taskId, period), tableModelDefine.getName());
            tableModelDefine = this.wrapper.dataModelService.getTableModelDefineByName(splitTableName);
        }
        return tableModelDefine;
    }

    @Override
    public TableModelDefine getSumTableModelDefine(OriTableModelInfo oriTableModelInfo, SummaryScheme summaryScheme, String period) {
        ExecutorContext context = this.buildQueryContext(summaryScheme.getTask(), period);
        context.getVariableManager().add(new Variable("batchGatherSchemeCode", "batchGatherSchemeCode", 6, (Object)summaryScheme.getCode()));
        String sumTableName = this.splitTableHelper.getCurrentSplitTableName(context, oriTableModelInfo.getOriTableModel().getName());
        return this.wrapper.dataModelService.getTableModelDefineByName(sumTableName);
    }

    private ExecutorContext buildQueryContext(String taskId, String period) {
        FormSchemeDefine formSchemeDefine = this.queryFormSchemeDefine(taskId, period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        assert (formSchemeDefine != null);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeDefine.getKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    public FormSchemeDefine queryFormSchemeDefine(String taskId, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskId);
            if (schemePeriodLinkDefine != null) {
                return this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

