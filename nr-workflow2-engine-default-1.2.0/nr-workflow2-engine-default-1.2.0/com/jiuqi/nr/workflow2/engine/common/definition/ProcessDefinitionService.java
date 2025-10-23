/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.exception.UserActionNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.exception.UserTaskNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 */
package com.jiuqi.nr.workflow2.engine.common.definition;

import com.jiuqi.nr.workflow2.engine.common.definition.ProcessDefinitionProvider;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionBuilder;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskBuilder;
import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.UserActionNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.UserTaskNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ProcessDefinitionService
implements IProcessDefinitionService {
    private final ProcessDefinitionProvider provider;

    public ProcessDefinitionService(ProcessDefinitionProvider provider) {
        this.provider = provider;
    }

    public IProcessDefinition queryProcess(String processId) {
        return this.provider.getProcessDefintion(processId);
    }

    public ProcessDefinition getProcess(String processId) {
        ProcessDefinition processDefinition = this.provider.getProcessDefintion(processId);
        if (processDefinition == null) {
            throw new ProcessNotFoundException(this.provider.engineName(), processId);
        }
        return processDefinition;
    }

    public List<IUserTask> queryAllUserTasks(String processId) {
        ProcessDefinition processDefinition = this.getProcess(processId);
        return processDefinition.getUserTasks();
    }

    private UserTask internalQueryUserTask(String processId, String userTaskCode) {
        ProcessDefinition processDefinition = this.getProcess(processId);
        return processDefinition.getUserTask(userTaskCode);
    }

    public IUserTask queryUserTask(String processId, String userTaskCode) {
        return this.internalQueryUserTask(processId, userTaskCode);
    }

    public IUserTask getUserTaskOrDefault(String processId, String userTaskCode) {
        UserTask userTask = this.internalQueryUserTask(processId, userTaskCode);
        if (userTask == null) {
            UserTaskBuilder builder = new UserTaskBuilder(userTaskCode);
            return builder.tryBuild();
        }
        return userTask;
    }

    public IUserTask getUserTask(String processId, String userTaskCode) {
        IUserTask userTask = this.queryUserTask(processId, userTaskCode);
        if (userTask == null) {
            throw new UserTaskNotFoundException(this.provider.engineName(), processId, userTaskCode);
        }
        return userTask;
    }

    public List<IUserAction> queryAllActions(String processId, String userTaskCode) {
        UserTask userTask = this.internalQueryUserTask(processId, userTaskCode);
        if (userTask == null) {
            throw new UserTaskNotFoundException(this.provider.engineName(), processId, userTaskCode);
        }
        return this.getUserActionsByUserTask(userTask);
    }

    public IUserAction queryAction(String processId, String userTaskCode, String actionCode) {
        List<IUserAction> userActions = this.queryAllActions(processId, userTaskCode);
        for (IUserAction userAction : userActions) {
            if (!userAction.getCode().equals(actionCode)) continue;
            return userAction;
        }
        return null;
    }

    public IUserAction getActionOrDefault(String processId, String userTaskCode, String actionCode) {
        UserTask userTask = this.internalQueryUserTask(processId, userTaskCode);
        if (userTask != null) {
            List<IUserAction> userActions = this.getUserActionsByUserTask(userTask);
            for (IUserAction userAction : userActions) {
                if (!userAction.getCode().equals(actionCode)) continue;
                return userAction;
            }
        }
        UserActionBuilder actionBuilder = new UserActionBuilder(actionCode);
        return actionBuilder.tryBuild();
    }

    public IUserAction getAction(String processId, String userTaskCode, String actionCode) {
        IUserAction userAction = this.queryAction(processId, userTaskCode, actionCode);
        if (userAction == null) {
            throw new UserActionNotFoundException(this.provider.engineName(), processId, userTaskCode, actionCode);
        }
        return userAction;
    }

    public IUserActionPath getActionPath(String processId, String userTaskCode, String actionCode) {
        IUserTask userTask = this.getUserTask(processId, userTaskCode);
        for (IUserActionPath actionPath : userTask.getActionPaths()) {
            if (!actionPath.getUserAction().getCode().equals(actionCode)) continue;
            return actionPath;
        }
        throw new UserActionNotFoundException(this.provider.engineName(), processId, userTaskCode, actionCode);
    }

    public List<IUserActionEvent> queryAllPreviousEvents(String processId, String userTaskCode, String actionCode) {
        IUserAction userAction = this.queryAction(processId, userTaskCode, actionCode);
        if (userAction == null) {
            throw new UserActionNotFoundException(this.provider.engineName(), processId, userTaskCode, actionCode);
        }
        return userAction.getPreviousEvents();
    }

    public List<IUserActionEvent> queryAllPostEvents(String processId, String userTaskCode, String actionCode) {
        IUserAction userAction = this.queryAction(processId, userTaskCode, actionCode);
        if (userAction == null) {
            throw new UserActionNotFoundException(this.provider.engineName(), processId, userTaskCode, actionCode);
        }
        return userAction.getPostEvents();
    }

    public List<IProcessStatus> queryAllStatus(String processId) {
        ProcessDefinition processDefinition = this.getProcess(processId);
        return processDefinition.getStatus();
    }

    public List<IProcessStatus> queryUserTaskStatus(String processId, String userTaskCode) {
        if (StringUtils.isEmpty(userTaskCode)) {
            return Collections.emptyList();
        }
        ProcessDefinition processDefinition = this.getProcess(processId);
        ArrayList<IProcessStatus> statusInUserTask = new ArrayList<IProcessStatus>(2);
        for (IUserTask userTask : processDefinition.getUserTasks()) {
            for (IUserActionPath actionPath : userTask.getActionPaths()) {
                if (!actionPath.getDestUserTask().getCode().equals(userTaskCode)) continue;
                statusInUserTask.add(actionPath.getDestStatus());
            }
        }
        return statusInUserTask;
    }

    public IProcessStatus queryStatus(String processId, String statusCode) {
        List<IProcessStatus> statusList = this.queryAllStatus(processId);
        for (IProcessStatus status : statusList) {
            if (!status.getCode().equals(statusCode)) continue;
            return status;
        }
        return null;
    }

    public void onProcessChanged(String processDefinitionId) {
        this.provider.onProcessChanged(processDefinitionId);
    }

    private List<IUserAction> getUserActionsByUserTask(UserTask userTask) {
        ArrayList<IUserAction> userActions = new ArrayList<IUserAction>();
        for (IUserActionPath actionPath : userTask.getActionPaths()) {
            userActions.add(actionPath.getUserAction());
        }
        if (userTask.getRetriveActionPath() != null) {
            userActions.add(userTask.getRetriveActionPath().getUserAction());
        }
        if (userTask.getApplyReturnAction() != null) {
            userActions.add(userTask.getApplyReturnAction().getUserAction());
        }
        return userActions;
    }
}

