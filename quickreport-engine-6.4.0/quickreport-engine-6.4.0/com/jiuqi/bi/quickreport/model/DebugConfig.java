/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class DebugConfig
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private boolean enabled;
    private String dumpDir;
    private static final String DEBUG_ENABLE = "enabled";
    private static final String DEBUG_DUMPDIR = "dumpDir";

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public String toString() {
        if (this.enabled) {
            return "\u8f93\u51fa\u76ee\u5f55\uff1a" + this.dumpDir;
        }
        return "\u5df2\u7981\u7528";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject info = new JSONObject();
        info.put(DEBUG_ENABLE, this.enabled);
        info.put(DEBUG_DUMPDIR, (Object)this.dumpDir);
        return info;
    }

    public void fromJSON(JSONObject info) throws JSONException {
        if (info == null) {
            return;
        }
        this.enabled = info.optBoolean(DEBUG_ENABLE, false);
        this.dumpDir = info.optString(DEBUG_DUMPDIR, null);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDumpDir() {
        return this.dumpDir;
    }

    public void setDumpDir(String dumpDir) {
        this.dumpDir = dumpDir;
    }
}

