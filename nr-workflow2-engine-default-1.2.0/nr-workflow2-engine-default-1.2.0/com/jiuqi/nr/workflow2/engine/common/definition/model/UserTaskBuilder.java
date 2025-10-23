/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatus;
import com.jiuqi.nr.workflow2.engine.common.definition.model.RetriveActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionBuilder;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPathBuilder;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserTaskBuilder {
    private String code;
    private String alias;
    private List<IActorStrategy> actionExecutors;
    private boolean enableSendTodo;
    private List<IActorStrategy> todoReceivers;
    private List<UserActionPathBuilder> userActionPaths;
    private Map<String, Object> properties;
    private RetriveActionPath retriveActionPath;
    private RetriveActionPath applyReturnAction;
    public UserTask userTask = new UserTask();
    private boolean hasBuild = false;

    public UserTaskBuilder(String userTaskCode) {
        this.code = userTaskCode;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setEnableSendTodo(boolean enable) {
        this.enableSendTodo = enable;
    }

    public void addUserActionPaths(UserActionBuilder action, UserTaskBuilder destUserTask, ProcessStatus destStatus) {
        if (this.userActionPaths == null) {
            this.userActionPaths = new ArrayList<UserActionPathBuilder>();
        }
        this.userActionPaths.add(new UserActionPathBuilder(action, destUserTask, destStatus));
    }

    public void addActionExecutors(IActorStrategy actionExecutor) {
        if (this.actionExecutors == null) {
            this.actionExecutors = new ArrayList<IActorStrategy>();
        }
        this.actionExecutors.add(actionExecutor);
    }

    public void todoReceiversSameToActionExecutors(boolean same) {
        this.todoReceivers = same ? this.actionExecutors : new ArrayList<IActorStrategy>();
    }

    public void AddTodoReceivers(IActorStrategy todoReceiver) {
        if (this.todoReceivers == null) {
            this.todoReceivers = new ArrayList<IActorStrategy>();
        }
        this.todoReceivers.add(todoReceiver);
    }

    public void setProperty(String name, Object value) {
        if (this.properties == null) {
            this.properties = new HashMap<String, Object>();
        }
        this.properties.put(name, value);
    }

    public void setRetriveAction(RetriveActionPath retriveActionPath) {
        this.retriveActionPath = retriveActionPath;
    }

    public void setApplyReturnAction(RetriveActionPath applyReturnAction) {
        this.applyReturnAction = applyReturnAction;
    }

    public UserTask build() {
        if (!this.hasBuild) {
            this.doBuild();
            this.hasBuild = true;
        }
        return this.userTask;
    }

    public UserTask tryBuild() {
        this.userTask.template = UserTaskTemplate.get(this.code);
        if (this.userTask.template == null) {
            return null;
        }
        this.userTask.alias = StringUtils.isEmpty(this.alias) ? this.userTask.getTitle() : this.alias;
        return this.userTask;
    }

    private void doBuild() {
        this.userTask.template = UserTaskTemplate.get(this.code);
        if (this.userTask.template == null) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "UserTask template must not be empty.");
        }
        this.userTask.alias = StringUtils.isEmpty(this.alias) ? this.userTask.getTitle() : this.alias;
        boolean isStartNode = this.code.equals("tsk_start");
        if (!isStartNode && (this.actionExecutors == null || this.actionExecutors.isEmpty())) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "UserTask action executors must not be empty.");
        }
        this.userTask.actionExecutors = this.actionExecutors == null || this.actionExecutors.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(this.actionExecutors);
        if (!isStartNode && (this.userActionPaths == null || this.userActionPaths.isEmpty())) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "UserTask useraction must not be empty.");
        }
        ArrayList<UserActionPath> actions = new ArrayList<UserActionPath>();
        for (UserActionPathBuilder builder : this.userActionPaths) {
            actions.add(builder.build());
        }
        this.userTask.userActionPaths = Collections.unmodifiableList(actions);
        this.userTask.userActionPathMap = actions.stream().collect(Collectors.toMap(t -> t.getUserAction().getCode(), t -> t));
        this.userTask.enableSendTodo = this.enableSendTodo;
        if (this.enableSendTodo) {
            if (!isStartNode && (this.todoReceivers == null || this.todoReceivers.isEmpty())) {
                throw new ProcessDefinitionException("jiuqi.nr.default", null, "UserTask todo receivers must not be empty.");
            }
            this.userTask.todoReceivers = this.todoReceivers == null || this.todoReceivers.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(this.todoReceivers);
        } else {
            this.userTask.todoReceivers = Collections.emptyList();
        }
        if (this.properties != null) {
            this.userTask.properties = new HashMap<String, Object>();
            this.userTask.properties.putAll(this.properties);
        }
        this.userTask.retriveActionPath = this.retriveActionPath;
        this.userTask.applyReturnAction = this.applyReturnAction;
    }
}

