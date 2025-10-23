/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.RetriveActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserTask
implements IUserTask {
    public static final String P_ENABLE_FORCE_REPORT = "ENABLE_FORCE_REPORT";
    public static final String P_FORCE_REPORT_ROLE = "FORCE_REPORT_ROLE";
    public static final String P_ENABLE_RETRIVE_TO = "ENABLE_RETRIVE_TO";
    public static final String P_ENABLE_RETRIVE_FROM = "ENABLE_RETRIVE_FROM";
    public static final String P_ENABLE_APPLY_RETURN = "ENABLE_APPLY_RETURN";
    UserTaskTemplate template;
    String alias;
    List<IActorStrategy> actionExecutors;
    boolean enableSendTodo;
    List<IActorStrategy> todoReceivers;
    List<IUserActionPath> userActionPaths;
    Map<String, UserActionPath> userActionPathMap;
    Map<String, Object> properties;
    RetriveActionPath retriveActionPath;
    RetriveActionPath applyReturnAction;

    UserTask() {
    }

    public UserTask(UserTaskTemplate template, String alias, List<IActorStrategy> actionExecutors, boolean enableSendTodo, List<IActorStrategy> todoReceivers, List<IUserActionPath> userActionPaths) {
        this.template = template;
        this.alias = alias;
        this.actionExecutors = actionExecutors;
        this.enableSendTodo = enableSendTodo;
        this.todoReceivers = todoReceivers;
        this.userActionPaths = userActionPaths;
        this.userActionPathMap = userActionPaths.stream().collect(Collectors.toMap(t -> t.getUserAction().getCode(), t -> (UserActionPath)t));
    }

    public UserActionPath getActionPath(String actionCode) {
        return this.userActionPathMap.get(actionCode);
    }

    public String getCode() {
        return this.template.getCode();
    }

    public String getTitle() {
        return this.template.getTitle();
    }

    public String getAlias() {
        return this.alias;
    }

    public List<IActorStrategy> getActionExecutors() {
        return this.actionExecutors;
    }

    public boolean enableSendTodo() {
        return this.enableSendTodo;
    }

    public List<IActorStrategy> getTodoReceivers() {
        return this.todoReceivers;
    }

    public List<IUserActionPath> getActionPaths() {
        return this.userActionPaths;
    }

    public Object getProperties(String name) {
        if (name == null) {
            throw new IllegalArgumentException("'name' must not be null.");
        }
        if (this.properties == null) {
            return null;
        }
        return this.properties.get(name);
    }

    public RetriveActionPath getRetriveActionPath() {
        return this.retriveActionPath;
    }

    public RetriveActionPath getApplyReturnAction() {
        return this.applyReturnAction;
    }
}

