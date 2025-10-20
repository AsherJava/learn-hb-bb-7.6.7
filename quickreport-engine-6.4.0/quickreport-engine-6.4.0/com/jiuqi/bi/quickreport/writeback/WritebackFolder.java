/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.QuickReportError;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class WritebackFolder
implements Cloneable,
Serializable {
    private static final long serialVersionUID = -3614096526626696276L;
    private String id;
    private String title;
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_ID, (Object)this.id);
        value.put(TAG_TITLE, (Object)this.title);
    }

    public void load(JSONObject value) throws JSONException {
        this.id = value.getString(TAG_ID);
        this.title = value.getString(TAG_TITLE);
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }
}

