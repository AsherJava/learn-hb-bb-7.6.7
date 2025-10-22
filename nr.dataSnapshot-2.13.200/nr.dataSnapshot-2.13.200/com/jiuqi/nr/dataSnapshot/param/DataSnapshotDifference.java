/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.dataSnapshot.param.IDataRegionCompareDifference;
import java.util.List;
import java.util.Map;

public class DataSnapshotDifference {
    private String formKey;
    private List<String> dataSourceNames;
    private Map<String, IDataRegionCompareDifference> differenceDatas;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<String> getDataSourceNames() {
        return this.dataSourceNames;
    }

    public void setDataSourceNames(List<String> dataSourceNames) {
        this.dataSourceNames = dataSourceNames;
    }

    public Map<String, IDataRegionCompareDifference> getDifferenceDatas() {
        return this.differenceDatas;
    }

    public void setDifferenceDatas(Map<String, IDataRegionCompareDifference> differenceDatas) {
        this.differenceDatas = differenceDatas;
    }
}

