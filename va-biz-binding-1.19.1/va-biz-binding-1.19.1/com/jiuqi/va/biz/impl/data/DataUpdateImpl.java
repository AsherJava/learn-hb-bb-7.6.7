/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.intf.data.DataUpdate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataUpdateImpl
implements DataUpdate {
    private List<Map<String, Object>> insert = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> update = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> delete = new ArrayList<Map<String, Object>>();

    @Override
    public List<Map<String, Object>> getInsert() {
        return this.insert;
    }

    public void setInsert(List<Map<String, Object>> insert) {
        this.insert = insert;
    }

    @Override
    public List<Map<String, Object>> getUpdate() {
        return this.update;
    }

    public void setUpdate(List<Map<String, Object>> update) {
        this.update = update;
    }

    @Override
    public List<Map<String, Object>> getDelete() {
        return this.delete;
    }

    public void setDelete(List<Map<String, Object>> delete) {
        this.delete = delete;
    }
}

