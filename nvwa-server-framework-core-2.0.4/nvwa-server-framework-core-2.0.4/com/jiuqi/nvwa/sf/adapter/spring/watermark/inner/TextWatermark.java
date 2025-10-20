/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark.inner;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class TextWatermark
extends Watermark {
    private int mode = 1;
    private String content;
    private Map<String, String> extendVarMap;

    @Override
    public int getMode() {
        return this.mode;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getExtendVarMap() {
        return this.extendVarMap;
    }

    public void setExtendVarMap(Map<String, String> extendVarMap) {
        this.extendVarMap = extendVarMap;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject json = super.toJson();
        json.put("content", (Object)this.content);
        json.put("mode", this.mode);
        json.put("extendVarMap", this.extendVarMap);
        return json;
    }

    @Override
    public void fromJson(JSONObject json) {
        super.fromJson(json);
        this.content = json.optString("content");
        this.mode = json.optInt("mode");
        if (json.has("extendVarMap")) {
            JSONObject extendVar = json.getJSONObject("extendVarMap");
            Iterator keyIterator = extendVar.keys();
            while (keyIterator.hasNext()) {
                String key = (String)keyIterator.next();
                Object value = extendVar.get(key);
                if (this.extendVarMap == null) {
                    this.extendVarMap = new HashMap<String, String>();
                }
                this.extendVarMap.put(key, value.toString());
            }
        }
    }
}

