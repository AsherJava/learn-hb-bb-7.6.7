/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataSourceMetaInfo {
    private List<DataSourceAttrBean> attrBeans = new ArrayList<DataSourceAttrBean>();
    private int levelDepth;
    private boolean isParentSonMode;
    private String title;

    public List<String> getTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        for (DataSourceAttrBean attrBean : this.attrBeans) {
            titles.add(attrBean.getTitle());
        }
        return titles;
    }

    public boolean isHier() {
        return this.levelDepth != 0;
    }

    public List<DataSourceAttrBean> getAttrBeans() {
        return this.attrBeans;
    }

    public int getLevelDepth() {
        return this.levelDepth;
    }

    public void setLevelDepth(int levelDepth) {
        this.levelDepth = levelDepth;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isParentSonMode() {
        return this.isParentSonMode;
    }

    public void setParentSonMode(boolean isParentSonMode) {
        this.isParentSonMode = isParentSonMode;
    }

    public void toJson(JSONObject value) throws JSONException {
        value.put("levelDepth", this.levelDepth);
        value.put("isParentMode", this.isParentSonMode);
        if (this.title != null) {
            value.put("title", (Object)this.title);
        }
        if (this.attrBeans != null && this.attrBeans.size() != 0) {
            JSONArray titleJsArray = new JSONArray();
            for (DataSourceAttrBean attrBean : this.attrBeans) {
                JSONObject titleJsObj = new JSONObject();
                attrBean.toJson(titleJsObj);
                titleJsArray.put((Object)titleJsObj);
            }
            value.put("attrBeans", (Object)titleJsArray);
        }
    }

    public void fromJson(JSONObject value) throws JSONException {
        this.levelDepth = value.getInt("levelDepth");
        this.isParentSonMode = value.getBoolean("isParentMode");
        if (!value.isNull("title")) {
            this.title = value.getString("title");
        }
        if (!value.isNull("attrBeans")) {
            JSONArray titleJsArray = value.getJSONArray("attrBeans");
            for (int i = 0; i < titleJsArray.length(); ++i) {
                JSONObject beanJsObj = titleJsArray.getJSONObject(i);
                DataSourceAttrBean bean = new DataSourceAttrBean();
                bean.fromJson(beanJsObj);
                this.attrBeans.add(bean);
            }
        }
    }
}

