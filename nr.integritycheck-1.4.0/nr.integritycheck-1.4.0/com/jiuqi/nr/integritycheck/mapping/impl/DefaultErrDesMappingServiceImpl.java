/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.integritycheck.mapping.impl;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.integritycheck.mapping.IErrDesMappingService;
import com.jiuqi.nr.integritycheck.message.FormMappingMessage;
import com.jiuqi.nr.integritycheck.message.FormSchemeMappingMessage;
import com.jiuqi.nr.integritycheck.message.TaskMappingMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultErrDesMappingServiceImpl
implements IErrDesMappingService {
    private IRunTimeViewController runTimeViewController;
    private Map<String, TaskMappingMessage> taskCodeMappingCatch;
    private Map<String, FormSchemeMappingMessage> formSchemeMappingCatch;
    private Map<String, FormMappingMessage> formMappingCatch;

    public DefaultErrDesMappingServiceImpl(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
        this.taskCodeMappingCatch = new HashMap<String, TaskMappingMessage>();
        this.formSchemeMappingCatch = new HashMap<String, FormSchemeMappingMessage>();
        this.formMappingCatch = new HashMap<String, FormMappingMessage>();
    }

    @Override
    public TaskMappingMessage getTaskMapping(TaskMappingMessage param) {
        if (this.taskCodeMappingCatch.containsKey(param.getTaskCode())) {
            return this.taskCodeMappingCatch.get(param.getTaskCode());
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefineByCode(param.getTaskCode());
        if (null == taskDefine) {
            throw new RuntimeException("\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728");
        }
        TaskMappingMessage taskMappingMessage = new TaskMappingMessage(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle());
        this.taskCodeMappingCatch.put(param.getTaskCode(), taskMappingMessage);
        return taskMappingMessage;
    }

    @Override
    public FormSchemeMappingMessage getFormSchemeMapping(TaskMappingMessage taskParam, FormSchemeMappingMessage formSchemeParam) {
        if (this.formSchemeMappingCatch.containsKey(formSchemeParam.getFormSchemeCode())) {
            return this.formSchemeMappingCatch.get(formSchemeParam.getFormSchemeCode());
        }
        TaskMappingMessage taskMapping = this.getTaskMapping(taskParam);
        try {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskMapping.getTaskKey());
            if (null == formSchemeDefines || formSchemeDefines.isEmpty()) {
                throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\u4e0d\u5b58\u5728");
            }
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                if (!formSchemeDefine.getTitle().equals(formSchemeParam.getFormSchemeTitle())) continue;
                FormSchemeMappingMessage formSchemeMappingMessage = new FormSchemeMappingMessage(formSchemeDefine.getKey(), formSchemeDefine.getFormSchemeCode(), formSchemeDefine.getTitle());
                this.formSchemeMappingCatch.put(formSchemeParam.getFormSchemeCode(), formSchemeMappingMessage);
                return formSchemeMappingMessage;
            }
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\u4e0d\u5b58\u5728");
        }
        catch (Exception e) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u5b9a\u4e49\u4e0d\u5b58\u5728", e);
        }
    }

    @Override
    public Map<String, FormMappingMessage> getFormMapping(TaskMappingMessage taskParam, FormSchemeMappingMessage formSchemeParam, List<FormMappingMessage> formParams) {
        HashMap<String, FormMappingMessage> result = new HashMap<String, FormMappingMessage>();
        FormSchemeMappingMessage formSchemeMapping = this.getFormSchemeMapping(taskParam, formSchemeParam);
        try {
            for (FormMappingMessage formParam : formParams) {
                if (this.formMappingCatch.containsKey(formParam.getFormCode())) {
                    result.put(formParam.getFormKey(), this.formMappingCatch.get(formParam.getFormCode()));
                    continue;
                }
                FormDefine formDefine = this.runTimeViewController.queryFormByCodeInScheme(formSchemeMapping.getFormSchemeKey(), formParam.getFormCode());
                if (null == formDefine) {
                    throw new RuntimeException("\u8868\u5355\u5b9a\u4e49\u4e0d\u5b58\u5728");
                }
                FormMappingMessage formMappingMessage = new FormMappingMessage(formDefine.getKey(), formDefine.getFormCode(), formDefine.getTitle());
                this.formMappingCatch.put(formParam.getFormCode(), formMappingMessage);
                result.put(formParam.getFormKey(), formMappingMessage);
            }
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException("\u8868\u5355\u5b9a\u4e49\u4e0d\u5b58\u5728", e);
        }
    }
}

