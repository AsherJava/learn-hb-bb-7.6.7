/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityUpgraderImpl;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil
extends DeEntityUpgraderImpl {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private DataModelService dataModelService;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    public IDataAccessFormService dataAccessFormService;
    @Resource
    private WorkflowSettingService settingService;

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            return formScheme;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public TaskDefine getTaskDefine(String taskKey) {
        try {
            return this.runTimeViewController.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public FormDefine getFormDefine(String formKey) {
        try {
            return this.runTimeViewController.queryFormById(formKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public FormGroupDefine getFormGroupDefine(String formGroupKey) {
        try {
            return this.runTimeViewController.queryFormGroup(formGroupKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<FormGroupDefine> getFormGroupDefineByFormKey(String formKey) {
        try {
            return this.runTimeViewController.getFormGroupsByFormKey(formKey);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FormDefine> getFormDefineByGroupKey(String groupKey) {
        try {
            return this.runTimeViewController.getAllFormsInGroup(groupKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<FormSchemeDefine> getFormSchemeDefineByTaskKey(String taskKey) {
        try {
            return this.runTimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<FormDefine> getFormBySchemeKey(String formSchemeKey) {
        try {
            List allForm = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            return allForm;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public BatchNoOperate getFormByKey(String formSchemeKey, String formKey) {
        BatchNoOperate batchNoOperate = new BatchNoOperate();
        try {
            WorkFlowType queryStartType = this.workflow.queryStartType(formSchemeKey);
            if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                FormDefine form = this.runTimeViewController.queryFormById(formKey);
                batchNoOperate.setId(form.getKey());
                batchNoOperate.setCode(form.getFormCode());
                batchNoOperate.setName(form.getTitle());
                return batchNoOperate;
            }
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                FormGroupDefine formGroup = this.runTimeViewController.queryFormGroup(formKey);
                batchNoOperate.setId(formGroup.getKey());
                batchNoOperate.setCode(formGroup.getCode());
                batchNoOperate.setName(formGroup.getTitle());
                return batchNoOperate;
            }
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getFormOrGroupName(String formSchemeKey, String formKey) {
        try {
            WorkFlowType queryStartType = this.workflow.queryStartType(formSchemeKey);
            if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                FormDefine form = this.runTimeViewController.queryFormById(formKey);
                return form.getTitle();
            }
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                FormGroupDefine formGroup = this.runTimeViewController.queryFormGroup(formKey);
                return formGroup.getTitle();
            }
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getFormOrGroupNames(String formSchemeKey, Set<String> formOrGroupKeys) {
        block6: {
            StringBuffer sb = new StringBuffer();
            try {
                WorkFlowType queryStartType = this.workflow.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                    List formTitles = this.runTimeViewController.queryFormsById(new LinkedList<String>(formOrGroupKeys)).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getTitle).collect(Collectors.toList());
                    sb.append(String.join((CharSequence)"\u3001", formTitles));
                    if (StringUtils.isNotEmpty((String)sb.toString())) {
                        return sb.toString();
                    }
                    break block6;
                }
                if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                    List formGroupTitles = new LinkedList<String>(formOrGroupKeys).stream().map(key -> this.runTimeViewController.queryFormGroup(key)).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IMetaGroup::getTitle).collect(Collectors.toList());
                    sb.append(String.join((CharSequence)"\u3001", formGroupTitles));
                    if (StringUtils.isNotEmpty((String)sb.toString())) {
                        return sb.toString();
                    }
                    break block6;
                }
                return null;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public String getFormOrGroupCodes(String formSchemeKey, Set<String> formKeys) {
        block8: {
            StringBuffer sb = new StringBuffer();
            try {
                WorkFlowType queryStartType = this.workflow.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                    for (String formKey : formKeys) {
                        FormDefine form = this.runTimeViewController.queryFormById(formKey);
                        if (form == null) continue;
                        sb.append(form.getKey()).append("\u3001");
                    }
                    if (StringUtils.isNotEmpty((String)sb.toString())) {
                        return sb.substring(0, sb.length() - 1);
                    }
                    break block8;
                }
                if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                    for (String formKey : formKeys) {
                        FormGroupDefine formGroup = this.runTimeViewController.queryFormGroup(formKey);
                        if (formGroup == null) continue;
                        sb.append(formGroup.getKey()).append("\u3001");
                    }
                    if (StringUtils.isNotEmpty((String)sb.toString())) {
                        return sb.substring(0, sb.length() - 1);
                    }
                    break block8;
                }
                return null;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public List<String> getFormOrGroupNameList(String formSchemeKey, List<String> formKeys) {
        ArrayList<String> formOrGroupNames = new ArrayList<String>();
        try {
            WorkFlowType queryStartType = this.workflow.queryStartType(formSchemeKey);
            if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                for (String formKey : formKeys) {
                    FormDefine form = this.runTimeViewController.queryFormById(formKey);
                    formOrGroupNames.add(form.getTitle());
                }
                return formOrGroupNames;
            }
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                for (String formKey : formKeys) {
                    FormGroupDefine formGroup = this.runTimeViewController.queryFormGroup(formKey);
                    formOrGroupNames.add(formGroup.getTitle());
                }
                return formOrGroupNames;
            }
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<String> getFormKeyBySchemeKey(String formSchemeKey) {
        ArrayList<String> formKeyList = new ArrayList<String>();
        try {
            List allForm = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            for (FormDefine formDefine : allForm) {
                formKeyList.add(formDefine.getKey());
            }
            return formKeyList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<FormGroupDefine> getGroupBySchemeKey(String formSchemeKey) {
        try {
            List allFormGroups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
            return allFormGroups;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<String> getGroupKeyBySchemeKey(String formSchemeKey) {
        ArrayList<String> formGroupKeyList = new ArrayList<String>();
        try {
            List allFormGroups = this.runTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
            for (FormGroupDefine formGroupDefine : allFormGroups) {
                formGroupKeyList.add(formGroupDefine.getKey());
            }
            return formGroupKeyList;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ContextUser getCurrentUser() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        return user;
    }

    public String getCurrentUserId() {
        ContextUser user = this.getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public boolean checkFormIdIsPrimaryKey(String formSchemekey) {
        FormSchemeDefine formScheme = this.getFormScheme(formSchemekey);
        String stateTableCode = TableConstant.getSysUploadStateTableName(formScheme);
        try {
            TableModelDefine stateTable = this.dataModelService.getTableModelDefineByCode(stateTableCode);
            String bizKeys = stateTable.getBizKeys();
            String[] split = bizKeys.split(";");
            Set set = Arrays.stream(split).collect(Collectors.toSet());
            ColumnModelDefine formField = this.dataModelService.getColumnModelDefineByCode(stateTable.getID(), "FORMID");
            if (set.contains(formField.getID())) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public String formId(String formSchemeKey) {
        return this.nrParameterUtils.getDefaultFormId(formSchemeKey);
    }

    public boolean isDefaultWorkflow(String formSchemeKey) {
        return this.workflow.isDefaultWorkflow(formSchemeKey);
    }

    public WorkFlowType workflowType(String formSchemeKey) {
        return this.workflow.queryStartType(formSchemeKey);
    }

    public List<WorkFlowLine> getWorkflowLines(String userTaskId, FormSchemeDefine formSchemeDefine) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(formSchemeDefine.getKey());
        if (refSetting != null) {
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            return this.customWorkFolwService.getWorkflowLineByEndNode(userTaskId, workFlowDefine.getLinkid());
        }
        return new ArrayList<WorkFlowLine>();
    }

    public ActionParam actionParam(String formSchemeKey, String actionCode) {
        return this.actionMethod.actionParam(formSchemeKey, actionCode);
    }
}

