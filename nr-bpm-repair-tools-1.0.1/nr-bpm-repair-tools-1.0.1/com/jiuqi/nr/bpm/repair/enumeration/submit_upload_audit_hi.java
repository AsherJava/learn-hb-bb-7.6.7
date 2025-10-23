/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.repair.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum submit_upload_audit_hi {
    act_submit("act_submit", "tsk_submit"),
    act_return("act_return", "tsk_upload"),
    act_upload("act_upload", "tsk_upload"),
    act_reject("act_reject", "tsk_audit"),
    act_retrieve("act_retrieve", "tsk_audit");

    public final String actionCode;
    public final String nodeCode;

    private submit_upload_audit_hi(String actionCode, String nodeCode) {
        this.nodeCode = nodeCode;
        this.actionCode = actionCode;
    }

    public static List<Map<String, String>> getActionToNodeMapGroup() {
        ArrayList<Map<String, String>> actionToNodeMapGroup = new ArrayList<Map<String, String>>();
        for (submit_upload_audit_hi actionToNodeMap : submit_upload_audit_hi.values()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("CUREVENT", actionToNodeMap.actionCode);
            map.put("CURNODE", actionToNodeMap.nodeCode);
            actionToNodeMapGroup.add(map);
        }
        return actionToNodeMapGroup;
    }

    public static Map<String, String> getActionToNodeMap() {
        HashMap<String, String> actionToNodeMap = new HashMap<String, String>();
        for (submit_upload_audit_hi item : submit_upload_audit_hi.values()) {
            actionToNodeMap.put(item.actionCode, item.nodeCode);
        }
        return actionToNodeMap;
    }
}

