/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.hyperlink;

import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public interface IHyperlinkContext {
    public JSONObject getTarget();

    public List<String> getMeasures();

    public Map<String, Object> getRestrictions();

    public String toMessage();
}

