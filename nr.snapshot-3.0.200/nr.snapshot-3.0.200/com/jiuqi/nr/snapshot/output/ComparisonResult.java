/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.output;

import com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ComparisonResult
implements Serializable {
    private static final long serialVersionUID = -6429158460426494481L;
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

