/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.bean;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ZBQueryExt {
    private String desc;
    private Map<String, String> extendedDatas;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getExtendedDatas() {
        return this.extendedDatas;
    }

    public void setExtendedDatas(Map<String, String> extendedDatas) {
        this.extendedDatas = extendedDatas;
    }

    public String toJSONString() {
        if (this.desc == null && this.extendedDatas == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("desc", (Object)this.desc);
        JSONObject extendJsonObject = new JSONObject();
        for (String extendKey : this.extendedDatas.keySet()) {
            extendJsonObject.put(extendKey, (Object)this.extendedDatas.get(extendKey));
        }
        jsonObject.put("extendedDatas", (Object)extendJsonObject);
        return jsonObject.toString();
    }

    public static ZBQueryExt parseFromString(String extDataStr) {
        ZBQueryExt zbQueryExt = new ZBQueryExt();
        JSONObject jsonObject = new JSONObject(extDataStr);
        String desc = jsonObject.optString("desc");
        zbQueryExt.setDesc(desc);
        JSONObject extendJsonObject = jsonObject.optJSONObject("extendedDatas");
        HashMap<String, String> extendDatas = new HashMap<String, String>();
        if (extendJsonObject != null) {
            for (String key : extendJsonObject.keySet()) {
                extendDatas.put(key, extendJsonObject.optString(key));
            }
        }
        zbQueryExt.setExtendedDatas(extendDatas);
        return zbQueryExt;
    }
}

