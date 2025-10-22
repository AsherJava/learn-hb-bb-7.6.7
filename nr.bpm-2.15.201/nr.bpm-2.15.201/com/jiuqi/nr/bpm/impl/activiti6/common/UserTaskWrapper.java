/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.bpmn.model.ExtensionElement
 *  org.activiti.bpmn.model.FlowElement
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.common.UserAction;
import com.jiuqi.nr.bpm.common.UserTask;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.springframework.util.Assert;

class UserTaskWrapper
implements UserTask {
    private final FlowElement flowElement;

    public UserTaskWrapper(FlowElement flowElement) {
        Assert.notNull((Object)flowElement, "'flowElement' must not be null.");
        this.flowElement = flowElement;
    }

    @Override
    public String getId() {
        return this.flowElement.getId();
    }

    @Override
    public String getName() {
        return this.flowElement.getName();
    }

    @Override
    public String getTodoTemplate() {
        List<ExtensionElement> elements = this.getExtensionElement("todoContent");
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(0).getAttributeValue(null, "value");
    }

    @Override
    public boolean isFormEditable() {
        List<ExtensionElement> elements = this.getExtensionElement("formEditable");
        if (elements.isEmpty()) {
            return true;
        }
        return Boolean.parseBoolean(elements.get(0).getAttributeValue(null, "value"));
    }

    @Override
    public List<UserAction> getActions() {
        List<ExtensionElement> elements = this.getExtensionElement("action");
        return elements.stream().map(o -> new UserActionWrapper((ExtensionElement)o)).collect(Collectors.toList());
    }

    @Override
    public boolean isNeedNotice() {
        List<ExtensionElement> elements = this.getExtensionElement("noticeChannel");
        return !elements.isEmpty();
    }

    @Override
    public Set<String> getNoticeChannles() {
        List<ExtensionElement> elements = this.getExtensionElement("noticeChannel");
        return elements.stream().map(o -> o.getAttributeValue(null, "value")).collect(Collectors.toSet());
    }

    @Override
    public List<ActorStrategyInstance> getActorStrategies() {
        List<ExtensionElement> elements = this.getExtensionElement("actorStrategy");
        return elements.stream().map(o -> new ActorStrategyInstanceWrapper((ExtensionElement)o)).collect(Collectors.toList());
    }

    @Override
    public boolean isRetrivable() {
        List<ExtensionElement> elements = this.getExtensionElement("retrivable");
        if (elements.isEmpty()) {
            return true;
        }
        return Boolean.parseBoolean(elements.get(0).getAttributeValue(null, "value"));
    }

    private List<ExtensionElement> getExtensionElement(String attributeName) {
        List elements = (List)this.flowElement.getExtensionElements().get(attributeName);
        if (elements == null) {
            return Collections.emptyList();
        }
        return elements;
    }

    private static class ActorStrategyInstanceWrapper
    implements ActorStrategyInstance {
        private final ExtensionElement extensionElement;

        public ActorStrategyInstanceWrapper(ExtensionElement extensionElement) {
            Assert.notNull((Object)extensionElement, "'extensionElement' must not be null.");
            this.extensionElement = extensionElement;
        }

        @Override
        public String getType() {
            return this.extensionElement.getAttributeValue(null, "type");
        }

        @Override
        public String getParameterJson() {
            return this.extensionElement.getAttributeValue(null, "param");
        }
    }

    private static class UserActionWrapper
    implements UserAction {
        private final ExtensionElement extensionElement;

        public UserActionWrapper(ExtensionElement extensionElement) {
            Assert.notNull((Object)extensionElement, "'extensionElement' must not be null.");
            this.extensionElement = extensionElement;
        }

        @Override
        public String getId() {
            return this.extensionElement.getAttributeValue(null, "code");
        }

        @Override
        public String getName() {
            return this.extensionElement.getAttributeValue(null, "name");
        }

        @Override
        public boolean isNeedComment() {
            return Boolean.parseBoolean(this.extensionElement.getAttributeValue(null, "needComment"));
        }
    }
}

