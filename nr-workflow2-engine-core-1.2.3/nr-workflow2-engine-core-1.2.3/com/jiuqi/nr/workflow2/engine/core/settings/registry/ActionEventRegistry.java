/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.registry;

import com.jiuqi.nr.workflow2.engine.core.exception.ActionEventRegisterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionEventRegistry {
    private static final Map<String, List<String>> actionEventRelation = new HashMap<String, List<String>>();

    public static void registerActionEvent(String actionCode, String eventId) {
        if (actionEventRelation.containsKey(actionCode)) {
            actionEventRelation.get(actionCode).add(eventId);
        } else {
            ArrayList<String> eventIds = new ArrayList<String>();
            eventIds.add(eventId);
            actionEventRelation.put(actionCode, eventIds);
        }
    }

    public static List<String> getEvents(String actionCode) throws ActionEventRegisterException {
        if (actionEventRelation.containsKey(actionCode)) {
            return actionEventRelation.get(actionCode);
        }
        throw new ActionEventRegisterException("\u52a8\u4f5c" + actionCode + "\u4e0a\u672a\u7ed1\u5b9a\u4efb\u4f55\u4e8b\u4ef6 \u52a8\u4f5c\u4e8b\u4ef6\u83b7\u53d6\u5931\u8d25\uff01");
    }
}

