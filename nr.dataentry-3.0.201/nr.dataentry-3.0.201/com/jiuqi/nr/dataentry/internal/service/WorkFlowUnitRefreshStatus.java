/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeIconAndColor
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.xlib.runtime.Assert
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeIconAndColor;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.dataentry.bean.DWorkflowConfig;
import com.jiuqi.nr.dataentry.paramInfo.FormUploadState;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.xlib.runtime.Assert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowUnitRefreshStatus
implements IRefreshStatus<FormUploadState> {
    private final IWorkflowService workflowService;
    private final IRunTimeViewController runTimeViewController;
    private final TreeIconAndColor treeIconAndColor;
    private final IBatchQueryUploadStateService batchQueryUploadStateService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public WorkFlowUnitRefreshStatus(TreeIconAndColor treeIconAndColor, IWorkflowService workflowService, IRunTimeViewController runTimeViewController, IBatchQueryUploadStateService batchQueryUploadStateService) {
        this.workflowService = workflowService;
        this.runTimeViewController = runTimeViewController;
        this.treeIconAndColor = treeIconAndColor;
        this.batchQueryUploadStateService = batchQueryUploadStateService;
    }

    @Override
    public boolean getEnable(String taskKey, String formSchemeKey) {
        if (!this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return false;
        }
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKey is must not be null!");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        return workFlowType != WorkFlowType.ENTITY;
    }

    @Override
    public String getName() {
        return "formUpload";
    }

    @Override
    public Consts.RefreshStatusType getType() {
        return Consts.RefreshStatusType.UNIT;
    }

    @Override
    public FormUploadState getStatus(JtableContext context) {
        DWorkflowConfig workflowConfig = this.workflowService.getWorkflowConfig(context.getFormSchemeKey());
        WorkFlowType flowsType = workflowConfig.getFlowsType();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        if (flowsType != WorkFlowType.ENTITY) {
            List fromKeys = new ArrayList();
            List<Object> groupKeys = new ArrayList();
            HashMap<String, String> formStates = new HashMap<String, String>();
            if (flowsType == WorkFlowType.GROUP) {
                List formGroupDefines = this.runTimeViewController.queryRootGroupsByFormScheme(context.getFormSchemeKey());
                groupKeys = formGroupDefines.stream().map(i -> i.getKey()).collect(Collectors.toList());
            } else {
                fromKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(context.getFormSchemeKey());
            }
            Map dimensionSet = context.getDimensionSet();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
            boolean icon = this.treeIconAndColor.isIcon();
            Map stateDisMap = icon ? this.treeIconAndColor.getBase64IconMap() : this.treeIconAndColor.getColorMap();
            try {
                Map stateMap = this.batchQueryUploadStateService.queryUploadStates(dimensionValueSet, fromKeys, groupKeys, formScheme);
                for (DimensionValueSet valueSet : stateMap.keySet()) {
                    ActionStateBean actionStateBean = (ActionStateBean)stateMap.get(valueSet);
                    Object value = valueSet.getValue("FORMID");
                    formStates.put((String)value, actionStateBean.getCode());
                }
            }
            catch (Exception e) {
                this.logger.error("\u540e\u53f0\u4e0a\u62a5\u6d41\u7a0b\u6269\u5c55\u5c5e\u6027\u67e5\u8be2\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
            }
            FormUploadState formUploadState = new FormUploadState();
            formUploadState.setFormsState(formStates);
            formUploadState.setIsIcon(icon);
            formUploadState.setStateDisMap(stateDisMap);
            return formUploadState;
        }
        return null;
    }
}

