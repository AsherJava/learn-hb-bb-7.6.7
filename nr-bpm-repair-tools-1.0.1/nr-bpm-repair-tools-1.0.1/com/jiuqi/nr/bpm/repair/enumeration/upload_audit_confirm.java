/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.repair.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum upload_audit_confirm {
    start("start", "tsk_upload"),
    act_upload("act_upload", "tsk_audit"),
    act_reject("act_reject", "tsk_upload"),
    act_confirm("act_confirm", "tsk_audit_after_confirm"),
    act_cancel_confirm("act_cancel_confirm", "tsk_audit");

    public final String actionCode;
    public final String nodeCode;

    private upload_audit_confirm(String actionCode, String nodeCode) {
        this.nodeCode = nodeCode;
        this.actionCode = actionCode;
    }

    public static List<Map<String, String>> getActionToNodeMapGroup() {
        ArrayList<Map<String, String>> actionToNodeMapGroup = new ArrayList<Map<String, String>>();
        for (upload_audit_confirm actionToNodeMap : upload_audit_confirm.values()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("PREVEVENT", actionToNodeMap.actionCode);
            map.put("CURNODE", actionToNodeMap.nodeCode);
            actionToNodeMapGroup.add(map);
        }
        return actionToNodeMapGroup;
    }

    public static Map<String, String> getActionToNodeMap() {
        HashMap<String, String> actionToNodeMap = new HashMap<String, String>();
        for (upload_audit_confirm item : upload_audit_confirm.values()) {
            actionToNodeMap.put(item.actionCode, item.nodeCode);
        }
        return actionToNodeMap;
    }
}

