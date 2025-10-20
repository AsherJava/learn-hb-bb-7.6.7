/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.model.hierarchy;

import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DSHierarchy
implements Cloneable {
    private String name;
    private String title;
    private DSHierarchyType type;
    private List<String> levels = new ArrayList<String>();
    private String parentFieldName;
    private String codePattern;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DSHierarchyType getType() {
        return this.type;
    }

    public void setType(DSHierarchyType type) {
        this.type = type;
    }

    public List<String> getLevels() {
        return this.levels;
    }

    public String getKeyFieldName() {
        return this.levels != null && this.levels.size() == 1 ? this.levels.get(0) : null;
    }

    public String getParentFieldName() {
        return this.parentFieldName;
    }

    public void setParentFieldName(String parentFieldName) {
        this.parentFieldName = parentFieldName;
    }

    public String getCodePattern() {
        return this.codePattern;
    }

    public void setCodePattern(String codePattern) {
        this.codePattern = codePattern;
    }

    public DSHierarchy clone() {
        try {
            DSHierarchy cloned = (DSHierarchy)super.clone();
            cloned.levels = new ArrayList<String>(this.levels);
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void fromJson(JSONObject json) throws JSONException {
        JSONArray lvs;
        this.name = json.optString("name");
        this.title = json.optString("title");
        if (!json.isNull("type")) {
            this.type = DSHierarchyType.valueOf(json.getInt("type"));
        }
        if ((lvs = json.optJSONArray("levels")) != null) {
            for (int i = 0; i < lvs.length(); ++i) {
                this.levels.add(lvs.getString(i));
            }
        }
        this.parentFieldName = json.optString("parent");
        this.codePattern = json.optString("codePattern");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        if (this.name != null) {
            json.put("name", (Object)this.name);
        }
        if (this.title != null) {
            json.put("title", (Object)this.title);
        }
        if (this.type != null) {
            json.put("type", this.type.value());
        }
        if (this.levels != null && !this.levels.isEmpty()) {
            json.put("levels", this.levels);
        }
        if (this.parentFieldName != null) {
            json.put("parent", (Object)this.parentFieldName);
        }
        if (this.codePattern != null) {
            json.put("codePattern", (Object)this.codePattern);
        }
        return json;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[").append(this.name).append(",");
        buf.append(this.title).append(",").append(this.type.title()).append("]");
        return buf.toString();
    }
}

