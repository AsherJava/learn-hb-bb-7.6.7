/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeTaskFlowController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.setting.utils;

import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.utils.SettingEntityUpgraderImpl;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeTaskFlowController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SettingUtil
extends SettingEntityUpgraderImpl {
    private static final Logger logger = LogFactory.getLogger(SettingUtil.class);
    private static final String DEFAULT_KEY = "00000000-0000-0000-0000-000000000000";
    @Resource
    private UserService<User> userService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IQueryUploadStateService queryUploadStateServiceImpl;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Resource
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IRunTimeTaskFlowController runTimeTaskFlowController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public static ContextUser getCurrentUser() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        return user;
    }

    public static String getCurrentUserId() {
        ContextUser user = SettingUtil.getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public String queryFlowDefineKey(String formSchemekey) {
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine workflowSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemekey);
        if (workflowSetting != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowSetting.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null) {
            return workFlowDefine.getCode();
        }
        return DEFAULT_KEY;
    }

    public WorkFlowType queryStartType(String formSchemeKey) {
        WorkFlowType workFlowType = WorkFlowType.ENTITY;
        try {
            FormSchemeDefine formScheme = this.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                workFlowType = formScheme.getFlowsSetting().getWordFlowType();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return workFlowType;
    }

    public WorkflowStatus queryFlowType(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            FlowsType flowType = formScheme.getFlowsSetting().getFlowsType();
            if (FlowsType.DEFAULT.equals((Object)flowType) || FlowsType.WORKFLOW.equals((Object)flowType)) {
                WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                if (workflowDefine != null && !DEFAULT_KEY.equals(workflowDefine.getWorkflowId())) {
                    return WorkflowStatus.WORKFLOW;
                }
                return WorkflowStatus.DEFAULT;
            }
            if (FlowsType.NOSTARTUP.equals((Object)formScheme.getFlowsSetting().getFlowsType())) {
                return WorkflowStatus.NOSTARTUP;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public List<TaskDefine> queryTasks() {
        return this.runTimeTaskFlowController.getEnableFlowTaskDefines();
    }

    public TaskDefine queryTasks(String taskKey) {
        try {
            return this.runTimeViewController.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public List<FormSchemeDefine> queryFormSchemeByTaskKey(String taskKey) {
        try {
            return this.runTimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public List<FormGroupDefine> queryFormGroupByFormSchemeKey(String formSchemeKey) {
        try {
            return this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public List<FormDefine> queryFormByFormSchemeKey(String formSchemeKey) {
        try {
            return this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public List<FormDefine> queryFormByGroupKey(String groupKey) {
        try {
            return this.runTimeViewController.getAllFormsInGroup(groupKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230!");
        }
        return formScheme;
    }

    public FormDefine getFormDefine(String formKey) {
        try {
            return this.runTimeViewController.queryFormById(formKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public FormGroupDefine getFormGroupDefine(String formGroupKey) {
        try {
            return this.runTimeViewController.queryFormGroup(formGroupKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public Map<String, String> queryFormOrGroupTitle(String formSchemeKey, String formOrGroupKey) {
        HashMap<String, String> codeAndTitle;
        block4: {
            codeAndTitle = new HashMap<String, String>();
            try {
                WorkFlowType startType = this.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    FormDefine formDefine = this.getFormDefine(formOrGroupKey);
                    codeAndTitle.put(formDefine.getFormCode(), formDefine.getTitle());
                    break block4;
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    FormGroupDefine formGroupDefine = this.getFormGroupDefine(formOrGroupKey);
                    codeAndTitle.put(formGroupDefine.getCode(), formGroupDefine.getTitle());
                    break block4;
                }
                return null;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return codeAndTitle;
    }

    public List<String> queryFormOrGroupByFormSchemeKey(String formSchemeKey) {
        List<String> ids;
        block5: {
            ids = new ArrayList<String>();
            try {
                WorkFlowType startType = this.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                    ids = formDefines.stream().map(e -> e.getKey()).collect(Collectors.toList());
                    break block5;
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    List groups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
                    ids = groups.stream().map(e -> e.getKey()).collect(Collectors.toList());
                    break block5;
                }
                String defaultFormId = this.nrParameterUtils.getDefaultFormId(formSchemeKey);
                if (defaultFormId != null) {
                    ids.add(defaultFormId);
                }
                return ids;
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), (Throwable)e2);
            }
        }
        return ids;
    }

    public Set<String> queryFormOrGroupKeyByFormSchemeKey(String formSchemeKey) {
        Set<String> ids;
        block5: {
            ids = new HashSet<String>();
            try {
                WorkFlowType startType = this.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                    ids = formDefines.stream().map(e -> e.getKey()).collect(Collectors.toSet());
                    break block5;
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    List groups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
                    ids = groups.stream().map(e -> e.getKey()).collect(Collectors.toSet());
                    break block5;
                }
                String defaultFormId = this.nrParameterUtils.getDefaultFormId(formSchemeKey);
                if (defaultFormId != null) {
                    ids.add(defaultFormId);
                }
                return ids;
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), (Throwable)e2);
            }
        }
        return ids;
    }

    public EntityViewDefine getEntityView(String entityKey) {
        return this.dimensionUtil.getEntityView(entityKey);
    }

    public String getMainDimName(EntityViewDefine viewDefine) {
        return this.dimensionUtil.getDimensionName(viewDefine);
    }

    public String getDwMainDimName(String formSchemeKey) {
        return this.dimensionUtil.getDwMainDimName(formSchemeKey);
    }

    public String getEntityDimName(String entityId) {
        return this.dimensionUtil.getDimensionName(entityId);
    }

    public Map<String, Set<String>> queryAlreadyStart(String formSchemeKey, Set<String> unitIds, Set<String> reportIds, String perid, String adjust) {
        HashMap<String, Set<String>> startFlowMap = new HashMap<String, Set<String>>();
        List<UploadStateNew> alreadyStartFlows = this.queryAlreadyStartFlow(unitIds, reportIds, formSchemeKey, perid, adjust);
        StringBuffer sb = null;
        for (UploadStateNew record : alreadyStartFlows) {
            sb = new StringBuffer();
            DimensionValueSet dimensionValue = record.getEntities();
            DimensionSet dimensionSet = dimensionValue.getDimensionSet();
            sb.append("'");
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String dimension = dimensionSet.get(i);
                if (dimension.equals("FORMID")) continue;
                sb.append(dimensionValue.getValue(dimension).toString()).append(";");
            }
            sb.append("'");
            HashSet<String> flowSet = (HashSet<String>)startFlowMap.get(sb.toString());
            if (flowSet == null) {
                flowSet = new HashSet<String>();
                flowSet.add(record.getFormId());
                startFlowMap.put(sb.toString(), flowSet);
                continue;
            }
            flowSet.add(record.getFormId());
            startFlowMap.put(sb.toString(), flowSet);
        }
        return startFlowMap;
    }

    private List<UploadStateNew> queryAlreadyStartFlow(Set<String> unitIds, Set<String> reportIds, String formShcemKey, String perid, String adjust) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(this.getDwMainDimName(formShcemKey), unitIds.stream().collect(Collectors.toList()));
        dimensionValueSet.setValue("DATATIME", (Object)perid);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formShcemKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        String dataScheme = taskDefine.getDataScheme();
        boolean enableAdjust = this.runtimeDataSchemeService.enableAdjustPeriod(dataScheme);
        if (enableAdjust) {
            dimensionValueSet.setValue("ADJUST", (Object)adjust);
        }
        FormSchemeDefine formSchem = this.getFormScheme(formShcemKey);
        if (reportIds == null || reportIds.size() == 0) {
            reportIds = new HashSet<String>();
        }
        List<UploadStateNew> uploadState = this.queryUploadStateServiceImpl.queryUploadStates(formSchem.getKey(), dimensionValueSet, new ArrayList<String>(reportIds), new ArrayList<String>(reportIds));
        return uploadState;
    }

    public String getDefaultFormId(String formSchemKey) {
        return this.nrParameterUtils.getDefaultFormId(formSchemKey);
    }

    public TableModelDefine getTableByFormSchemeKey(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
            return tableModel;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException("\u672a\u627e\u5230\u8868\u5b9a\u4e49!");
        }
    }

    public TableModelDefine getTableByEntityId(String entityId) {
        try {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
            return tableModel;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException("\u672a\u627e\u5230\u8868\u5b9a\u4e49!");
        }
    }

    public DimensionValueSet buildDimsionValueSet(String mainDim, List<String> objs, String period) {
        DimensionValueSet dimSet = new DimensionValueSet();
        dimSet.setValue("DATATIME", (Object)period);
        dimSet.setValue(mainDim, objs);
        return dimSet;
    }

    public List<DimensionValueSet> buildDimsionValueSetList(String mainDim, Set<String> objs, String period) {
        ArrayList<DimensionValueSet> dimSets = new ArrayList<DimensionValueSet>();
        if (objs.size() > 0) {
            for (String obj : objs) {
                DimensionValueSet dimSet = new DimensionValueSet();
                dimSet.setValue("DATATIME", (Object)period);
                dimSet.setValue(mainDim, (Object)obj);
                dimSets.add(dimSet);
            }
        }
        return dimSets;
    }

    public WorkFlowDefine getWorkFlowDefine(String workflowId) {
        return this.customWorkFolwService.getRunWorkFlowDefineByID(workflowId, 1);
    }

    public List<WorkFlowDefine> getWorkFlowDefine() {
        return this.customWorkFolwService.getWorkflowByState();
    }

    public WorkFlowAction getWorkFlowAction(String formSchemeKey, String taskId, String action) {
        WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowDefineByFormSchemeKey != null) {
            WorkFlowDefine workFlowDefineByID = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
            return this.customWorkFolwService.getWorkflowActionByCode(taskId, action, workFlowDefineByID.getLinkid());
        }
        return new WorkFlowAction();
    }

    public List<WorkFlowLine> getWorkFlowLinesByEndNode(String taskId, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwService.getWorkflowLineByEndNode(taskId, workFlowDefine.getLinkid());
        }
        return new ArrayList<WorkFlowLine>();
    }

    public List<WorkFlowLine> getWorkflowLinesByPreTask(String taskId, String actionId, String linkId) {
        return this.customWorkFolwService.getWorkflowLinesByPreTask(taskId, actionId, linkId);
    }

    public List<WorkFlowLine> getWorkflowLinesByPreTask(String taskId, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwService.getWorkflowLinesByPreTask(taskId, workFlowDefine.getLinkid());
        }
        return new ArrayList<WorkFlowLine>();
    }

    public List<WorkFlowLine> getWorkflowLineByBNodeAndEndNode(List<String> nodeList, String taskId, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwService.getWorkflowLineByBNodeAndEndNode(nodeList, taskId, workFlowDefine.getLinkid());
        }
        return new ArrayList<WorkFlowLine>();
    }

    public List<WorkFlowLine> getWorkflowLinesByLinkid(String linkId) {
        return this.customWorkFolwService.getWorkflowLinesByLinkid(linkId);
    }

    public WorkFlowNodeSet getWorkFlowNodeSet(String nodeId, String formSchemeKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwService.getWorkFlowNodeSetByID(nodeId, workFlowDefine.getLinkid());
        }
        return null;
    }
}

