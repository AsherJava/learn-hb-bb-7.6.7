/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.reminder.actor.CanUploadOrSubmitStrategy;
import com.jiuqi.nr.reminder.infer.ParticipantHelper;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParticipantHelperImpl
implements ParticipantHelper {
    private static final Logger log = LoggerFactory.getLogger(ParticipantHelperImpl.class);
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private final CanUploadOrSubmitStrategy actorStrategy;

    public ParticipantHelperImpl(CanUploadOrSubmitStrategy actorStrategy) {
        this.actorStrategy = actorStrategy;
    }

    @Override
    public List<String> collectCommitUserId(String viewKey, String formSchemeKey, List<String> entityIds, List<IEntityRow> allRows) {
        return entityIds.stream().flatMap(e -> this.actorStrategy.getActors(viewKey, (String)e, formSchemeKey, null, null, allRows).stream()).collect(Collectors.toList());
    }

    @Override
    public List<String> collectUserIdOnlyRole(List<String> roleIds) {
        return this.actorStrategy.getActorsOnlyRoleIds(roleIds);
    }

    @Override
    public List<String> collectUserId(String viewKey, String formSchemeKey, List<String> entityIds, List<String> roleIds, List<IEntityRow> allRows) {
        return entityIds.stream().flatMap(e -> this.actorStrategy.getActors(viewKey, (String)e, formSchemeKey, null, null, roleIds, allRows).stream()).collect(Collectors.toList());
    }

    @Override
    public List<String> collectCommitUserId(String viewKey, String formSchemeKey, List<String> entityIds, CreateReminderCommand command, List<IEntityRow> allRows) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            return entityIds.stream().flatMap(e -> this.actorStrategy.getActors(viewKey, (String)e, formSchemeKey, null, null, allRows).stream()).collect(Collectors.toList());
        }
        ArrayList<String> list = new ArrayList<String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(command.getFormSchemeId());
            String dw = formScheme.getDw();
            String entityName = this.entityMetaService.getDimensionName(dw);
            for (String entityId : entityIds) {
                DimensionValueSet dimm = new DimensionValueSet();
                dimm.setValue(entityName, (Object)entityId);
                dimm.setValue("DATATIME", (Object)command.getPeriod());
                if (WorkFlowType.ENTITY.name().equals(command.getWorkFlowType())) {
                    list.addAll(this.dataFlowService.getTaskActors(formScheme, dimm, null, null));
                    continue;
                }
                if (WorkFlowType.GROUP.name().equals(command.getWorkFlowType())) {
                    if ("1".equals(command.getGroupRange())) {
                        List allFormGroups = this.runTimeAuthViewController.getAllFormGroupsInFormScheme(command.getFormSchemeId());
                        Iterator iterator = allFormGroups.iterator();
                        while (iterator.hasNext()) {
                            FormGroupDefine formGroup = (FormGroupDefine)iterator.next();
                            list.addAll(this.dataFlowService.getTaskActors(formScheme, dimm, formGroup.getKey(), formGroup.getKey()));
                        }
                        continue;
                    }
                    for (String string : command.getFormKeys()) {
                        list.addAll(this.dataFlowService.getTaskActors(formScheme, dimm, string, string));
                    }
                    continue;
                }
                if (!WorkFlowType.FORM.name().equals(command.getWorkFlowType())) continue;
                if ("1".equals(command.getFormRange())) {
                    List allForms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(command.getFormSchemeId());
                    for (FormDefine form : allForms) {
                        list.addAll(this.dataFlowService.getTaskActors(formScheme, dimm, form.getKey(), form.getKey()));
                    }
                    continue;
                }
                for (String string : command.getFormKeys()) {
                    list.addAll(this.dataFlowService.getTaskActors(formScheme, dimm, string, string));
                }
            }
        }
        catch (Exception e2) {
            log.error(e2.getMessage(), e2);
        }
        LinkedHashSet hashListSet = new LinkedHashSet(list);
        list = new ArrayList(hashListSet);
        return list;
    }
}

