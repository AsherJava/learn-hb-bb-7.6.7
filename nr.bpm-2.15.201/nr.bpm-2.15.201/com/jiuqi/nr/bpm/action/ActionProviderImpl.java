/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.action;

import com.jiuqi.nr.bpm.action.ActionBase;
import com.jiuqi.nr.bpm.action.ActionProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ActionProviderImpl
implements ActionProvider {
    @Autowired
    private List<ActionBase> allaction;
    private Map<String, ActionBase> atcionIDMap;
    private Map<String, ActionBase> atcionCodeMap;
    @Autowired
    private ApplicationContext applicationContext;

    private void init() {
        this.allaction = new ArrayList<ActionBase>();
        this.atcionIDMap = new HashMap<String, ActionBase>();
        this.atcionCodeMap = new HashMap<String, ActionBase>();
        Map<String, ActionBase> actions = this.applicationContext.getBeansOfType(ActionBase.class);
        for (ActionBase action : actions.values()) {
            this.allaction.add(action);
            this.atcionIDMap.put(action.getActionID(), action);
            this.atcionCodeMap.put(action.getActionCode(), action);
        }
    }

    @Override
    public ActionBase getActionBaseByID(String actionID) {
        this.init();
        return this.atcionIDMap.get(actionID);
    }

    @Override
    public ActionBase getActionBaseByCode(String actionCode) {
        this.init();
        return this.atcionCodeMap.get(actionCode);
    }

    @Override
    public List<ActionBase> getAllActionBase() {
        this.init();
        return this.allaction;
    }
}

