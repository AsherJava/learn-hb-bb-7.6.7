/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskLinkService
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskLinkService;
import com.jiuqi.nr.singlequeryimport.bean.FormSchemeItem;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.FormulaCheckParam;
import com.jiuqi.nr.singlequeryimport.bean.TaskItem;
import com.jiuqi.nr.singlequeryimport.service.IFormulaEditorService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormulaEditorServiceImpl
implements IFormulaEditorService {
    private static final Logger logger = LoggerFactory.getLogger(FormulaEditorServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeTaskLinkService runtimeTaskLinkService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    @Override
    public List<TaskItem> getTaskItemList(String formSchemeKey) {
        ArrayList<TaskItem> taskInfos = new ArrayList<TaskItem>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        taskInfos.add(new TaskItem(formScheme));
        List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formSchemeKey);
        if (!CollectionUtils.isEmpty(taskLinkDefines)) {
            List linkInfos = taskLinkDefines.stream().filter(taskLinkDefine -> {
                boolean hasRelatedFormSchemeKey = StringUtils.hasLength(taskLinkDefine.getRelatedFormSchemeKey());
                if (hasRelatedFormSchemeKey) {
                    FormSchemeDefine relatedFormScheme = this.runTimeViewController.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
                    return relatedFormScheme != null;
                }
                return false;
            }).map(taskLinkDefine -> {
                TaskItem taskItem = new TaskItem((TaskLinkDefine)taskLinkDefine);
                FormSchemeDefine relatedFormScheme = this.runTimeViewController.getFormScheme(taskLinkDefine.getRelatedFormSchemeKey());
                TaskDefine relatedTask = this.runTimeViewController.queryTaskDefine(relatedFormScheme.getTaskKey());
                taskItem.setTitle(String.format("%s@%s", relatedTask.getTitle(), taskLinkDefine.getLinkAlias()));
                return taskItem;
            }).collect(Collectors.toList());
            taskInfos.addAll(linkInfos);
        }
        return taskInfos;
    }

    @Override
    public List<FormSchemeItem> getFormSchemeItemList(String taskItemKey) {
        TaskLinkDefine taskLinkDefine = this.runtimeTaskLinkService.queryTaskLinkByKey(taskItemKey);
        FormSchemeItem formSchemeItem = new FormSchemeItem(taskLinkDefine);
        formSchemeItem.setKey(taskLinkDefine.getRelatedFormSchemeKey());
        return Collections.singletonList(formSchemeItem);
    }

    @Override
    public void checkFormula(FormulaCheckParam formulaCheckParam) throws Exception {
        ExecutorContext executorContext = this.createExecutorContext(formulaCheckParam.getTaskKey(), formulaCheckParam.getFormSchemeKey());
        QueryContext qContext = new QueryContext(executorContext, null);
        ReportFormulaParser parser = executorContext.getCache().getFormulaParser(true);
        parser.parseEval(formulaCheckParam.getExpression(), (IContext)qContext);
    }

    private ExecutorContext createExecutorContext(String taskKey, String formSchemeKey) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        env.setDataScehmeKey(taskDefine.getDataScheme());
        executorContext.setEnv((IFmlExecEnvironment)env);
        return executorContext;
    }
}

