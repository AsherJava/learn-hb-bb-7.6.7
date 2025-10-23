/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatus;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatusTemplate;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessDefinition
implements IProcessDefinition {
    public static final ProcessDefinition NOT_EXISTS_PROCESSDEFINTION = new ProcessDefinition();
    String id;
    String engineName;
    String title;
    String description;
    List<IUserTask> userTasks;
    List<IProcessStatus> statuses;
    Map<String, IProcessStatus> statusMap;
    private Map<String, UserTask> userTaskMap;
    private Map<String, IProcessStatus> entityStatusByForm;
    private Map<String, IProcessStatus> entityStatusByFormGroup;

    ProcessDefinition() {
    }

    public ProcessDefinition(String id, String title, List<UserTask> userTasks) {
        this.id = id;
        this.title = title;
        this.userTasks = Collections.unmodifiableList(userTasks);
        this.statuses = Collections.unmodifiableList(this.findAllStatus(userTasks));
        this.userTaskMap = userTasks.stream().collect(Collectors.toMap(IUserTask::getCode, o -> o));
        this.statusMap = this.statuses.stream().collect(Collectors.toMap(IProcessStatus::getCode, o -> o));
        this.buildEntityStatus();
    }

    private List<IProcessStatus> findAllStatus(List<UserTask> userTasks) {
        ArrayList<IProcessStatus> statuses = new ArrayList<IProcessStatus>();
        HashSet<String> statusCodes = new HashSet<String>(8);
        for (IUserTask iUserTask : userTasks) {
            for (IUserActionPath actionPath : iUserTask.getActionPaths()) {
                if (statusCodes.contains(actionPath.getDestStatus().getCode())) continue;
                statuses.add(actionPath.getDestStatus());
                statusCodes.add(actionPath.getDestStatus().getCode());
            }
        }
        return statuses;
    }

    private void buildEntityStatus() {
        IProcessStatus confirmStatus;
        IProcessStatus reportStatus;
        String entityStatusAlas;
        this.entityStatusByForm = new HashMap<String, IProcessStatus>(4);
        this.entityStatusByFormGroup = new HashMap<String, IProcessStatus>(4);
        IProcessStatus submitStatus = this.statusMap.get("submited");
        if (submitStatus != null) {
            entityStatusAlas = submitStatus.getAlias();
            if (entityStatusAlas == null) {
                entityStatusAlas = submitStatus.getTitle();
            }
            if (entityStatusAlas.startsWith("\u5df2")) {
                entityStatusAlas = entityStatusAlas.substring(1);
            }
            ProcessStatus statusBaseForm = new ProcessStatus(ProcessStatusTemplate.PARTFORMSUBMITED, "\u5206\u8868" + entityStatusAlas);
            this.entityStatusByForm.put(statusBaseForm.getCode(), statusBaseForm);
            ProcessStatus statusBaseGroup = new ProcessStatus(ProcessStatusTemplate.PARTFORMSUBMITED, "\u5206\u7ec4" + entityStatusAlas);
            this.entityStatusByFormGroup.put(statusBaseGroup.getCode(), statusBaseGroup);
        }
        if ((reportStatus = this.statusMap.get("reported")) != null) {
            entityStatusAlas = reportStatus.getAlias();
            if (entityStatusAlas == null) {
                entityStatusAlas = reportStatus.getTitle();
            }
            if (entityStatusAlas.startsWith("\u5df2")) {
                entityStatusAlas = entityStatusAlas.substring(1);
            }
            ProcessStatus statusBaseForm = new ProcessStatus(ProcessStatusTemplate.PARTFORMREPORTED, "\u5206\u8868" + entityStatusAlas);
            this.entityStatusByForm.put(statusBaseForm.getCode(), statusBaseForm);
            ProcessStatus statusBaseGroup = new ProcessStatus(ProcessStatusTemplate.PARTFORMREPORTED, "\u5206\u7ec4" + entityStatusAlas);
            this.entityStatusByFormGroup.put(statusBaseGroup.getCode(), statusBaseGroup);
        }
        if ((confirmStatus = this.statusMap.get("confirmed")) != null) {
            entityStatusAlas = confirmStatus.getAlias();
            if (entityStatusAlas == null) {
                entityStatusAlas = confirmStatus.getTitle();
            }
            if (entityStatusAlas.startsWith("\u5df2")) {
                entityStatusAlas = entityStatusAlas.substring(1);
            }
            ProcessStatus statusBaseForm = new ProcessStatus(ProcessStatusTemplate.PARTFORMCONFIRMED, "\u5206\u8868" + entityStatusAlas);
            this.entityStatusByForm.put(statusBaseForm.getCode(), statusBaseForm);
            ProcessStatus statusBaseGroup = new ProcessStatus(ProcessStatusTemplate.PARTFORMCONFIRMED, "\u5206\u7ec4" + entityStatusAlas);
            this.entityStatusByFormGroup.put(statusBaseGroup.getCode(), statusBaseGroup);
        }
    }

    public UserTask getUserTask(String userTaskCode) {
        return this.userTaskMap.get(userTaskCode);
    }

    public UserTask getStartUserTask() {
        return this.userTaskMap.get("tsk_start");
    }

    public IProcessStatus getStatus(String statusCode) {
        return this.statusMap.get(statusCode);
    }

    public IProcessStatus getEntityStatus(String statusCode, WorkflowObjectType workflowObjectType) {
        IProcessStatus status = this.statusMap.get(statusCode);
        if (status == null) {
            switch (workflowObjectType) {
                case FORM: {
                    if (this.entityStatusByForm == null) break;
                    status = this.entityStatusByForm.get(statusCode);
                    break;
                }
                case FORM_GROUP: {
                    if (this.entityStatusByFormGroup == null) break;
                    status = this.entityStatusByFormGroup.get(statusCode);
                    break;
                }
            }
        }
        return status;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getProcessEngineId() {
        return this.engineName;
    }

    public List<IUserTask> getUserTasks() {
        return this.userTasks;
    }

    public List<IProcessStatus> getStatus() {
        return this.statuses;
    }
}

