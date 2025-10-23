/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event;

import com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory;
import com.jiuqi.nr.workflow2.engine.core.exception.EventNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.EventRegisterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionEventFactoryImpl
implements IActionEventFactory {
    private final List<IActionEventDefinition> srotedEventDefinitions = new ArrayList<IActionEventDefinition>();
    private final Map<String, InternalActionEventDefinition> eventDefinitions = new HashMap<String, InternalActionEventDefinition>();
    private final Map<String, IActionEventExecutorFactory> executorFactories = new HashMap<String, IActionEventExecutorFactory>();

    public ActionEventFactoryImpl(List<ActionEventRegisteration> registerations) {
        registerations.sort((r1, r2) -> r1.getOrder() - r2.getOrder());
        for (ActionEventRegisteration registeration : registerations) {
            this.validateRegisteration(registeration);
            if (this.eventDefinitions.containsKey(registeration.getId())) {
                throw new EventRegisterException(registeration.getId(), "action event id '" + registeration.getId() + "' already exists.");
            }
            InternalActionEventDefinition actionEventDefinition = new InternalActionEventDefinition(registeration);
            this.srotedEventDefinitions.add(actionEventDefinition);
            this.eventDefinitions.put(actionEventDefinition.getId(), actionEventDefinition);
            this.executorFactories.put(actionEventDefinition.getId(), registeration.getExecutorFactory());
        }
    }

    private void validateRegisteration(ActionEventRegisteration registeration) {
        if (registeration.getId() == null || registeration.getId().trim().length() == 0) {
            throw new EventRegisterException("", "action event id must not be empty: " + registeration.getTitle());
        }
        if (registeration.getTitle() == null || registeration.getTitle().trim().length() == 0) {
            throw new EventRegisterException(registeration.getId(), "action event title must not be empty: " + registeration.getId());
        }
        if (registeration.getExecutorFactory() == null) {
            throw new EventRegisterException(registeration.getId(), "action event excutor factory must not be null: " + registeration.getId());
        }
    }

    @Override
    public IActionEventDefinition queryActionEventDefinition(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("args name con not be empty.");
        }
        return this.eventDefinitions.get(id);
    }

    @Override
    public short getActionEventOrder(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("args name con not be empty.");
        }
        InternalActionEventDefinition eventDefinition = this.eventDefinitions.get(id);
        if (eventDefinition == null) {
            throw new EventNotFoundException(id);
        }
        return eventDefinition.order;
    }

    @Override
    public IActionEventExecutorFactory queryActionEventExecutorFactory(String id) {
        if (id == null || id.trim().length() == 0) {
            throw new IllegalArgumentException("args name con not be empty.");
        }
        if (!this.eventDefinitions.containsKey(id)) {
            throw new EventNotFoundException(id);
        }
        return this.executorFactories.get(id);
    }

    private static class InternalActionEventDefinition
    implements IActionEventDefinition {
        final String id;
        final String title;
        final String decription;
        final IActionEventDefinition.ExecutionTiming executionTiming;
        final IActionEventDefinition.Level level;
        final short order;

        public InternalActionEventDefinition(ActionEventRegisteration registeration) {
            this.id = registeration.getId();
            this.title = registeration.getTitle();
            this.decription = registeration.getDescription();
            this.executionTiming = registeration.getExecutionTiming();
            this.order = registeration.getOrder();
            this.level = registeration.getLevel();
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public String getDescription() {
            return this.decription;
        }

        @Override
        public IActionEventDefinition.ExecutionTiming getExecutionTiming() {
            return this.executionTiming;
        }

        @Override
        public IActionEventDefinition.Level getLevel() {
            return this.level;
        }
    }
}

