/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition$ExecutionTiming
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserAction;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActionBuilder {
    private String code;
    private String alias;
    private List<IUserActionEvent> previousEvents;
    private List<IUserActionEvent> postEvents;
    private Map<String, Object> properties;

    public UserActionBuilder(String userActionCode) {
        this.code = userActionCode;
    }

    public void alias(String alias) {
        this.alias = alias;
    }

    public void addEvent(IUserActionEvent event, IActionEventDefinition.ExecutionTiming executionTiming) {
        switch (executionTiming) {
            case PRE_ACTION: {
                if (this.previousEvents == null) {
                    this.previousEvents = new ArrayList<IUserActionEvent>();
                }
                this.previousEvents.add(event);
                break;
            }
            case POST_ACTION: {
                if (this.postEvents == null) {
                    this.postEvents = new ArrayList<IUserActionEvent>();
                }
                this.postEvents.add(event);
                break;
            }
        }
    }

    public void setProperty(String name, Object value) {
        if (this.properties == null) {
            this.properties = new HashMap<String, Object>();
        }
        this.properties.put(name, value);
    }

    public UserAction build() {
        UserAction newAction = new UserAction();
        newAction.actionTemplate = UserActionTemplate.get(this.code);
        if (newAction.actionTemplate == null) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "UserAction actionTemplate must not be empty.");
        }
        newAction.alias = StringUtils.isEmpty(this.alias) ? newAction.getTitle() : this.alias;
        newAction.previousEvents = this.previousEvents == null ? Collections.emptyList() : Collections.unmodifiableList(this.previousEvents);
        newAction.postEvents = this.postEvents == null ? Collections.emptyList() : Collections.unmodifiableList(this.postEvents);
        newAction.properties = this.properties == null ? Collections.emptyMap() : Collections.unmodifiableMap(this.properties);
        return newAction;
    }

    public UserAction tryBuild() {
        UserAction newAction = new UserAction();
        newAction.actionTemplate = UserActionTemplate.get(this.code);
        if (newAction.actionTemplate == null) {
            return null;
        }
        newAction.alias = StringUtils.isEmpty(this.alias) ? newAction.getTitle() : this.alias;
        newAction.previousEvents = this.previousEvents == null ? Collections.emptyList() : Collections.unmodifiableList(this.previousEvents);
        newAction.postEvents = this.postEvents == null ? Collections.emptyList() : Collections.unmodifiableList(this.postEvents);
        newAction.properties = this.properties == null ? Collections.emptyMap() : Collections.unmodifiableMap(this.properties);
        return newAction;
    }
}

