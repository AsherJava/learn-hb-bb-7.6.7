/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.service.impl;

import com.jiuqi.nr.snapshot.message.DataInfo;
import com.jiuqi.nr.snapshot.service.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceImpl
implements DataSource {
    private String title;
    private Map<String, DataInfo> dataInfoMap = new HashMap<String, DataInfo>();

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setData(Map<String, DataInfo> dataInfoMap) {
        this.dataInfoMap = dataInfoMap;
    }

    @Override
    public DataInfo getData(String formKey) {
        return this.dataInfoMap.get(formKey);
    }
}

