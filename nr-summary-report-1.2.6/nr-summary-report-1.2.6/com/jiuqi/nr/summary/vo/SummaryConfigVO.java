/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.SummaryConfigItem;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

public class SummaryConfigVO {
    private String key;
    private String menuId;
    private List<SummaryConfigItem> items;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public List<SummaryConfigItem> getItems() {
        return this.items;
    }

    public void setItems(List<SummaryConfigItem> items) {
        this.items = items;
    }

    public String itemToString() {
        if (!CollectionUtils.isEmpty(this.items)) {
            JSONArray jsonArray = new JSONArray();
            this.items.forEach(item -> jsonArray.put((Object)item.toString()));
            return jsonArray.toString();
        }
        return null;
    }

    public void stringToItem(String itemStr) {
        JSONArray jsonArray = new JSONArray(itemStr);
        ArrayList<SummaryConfigItem> items = new ArrayList<SummaryConfigItem>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            SummaryConfigItem configItem = new SummaryConfigItem();
            configItem.fromJson(new JSONObject(jsonArray.getString(i)));
            items.add(configItem);
        }
        this.setItems(items);
    }
}

