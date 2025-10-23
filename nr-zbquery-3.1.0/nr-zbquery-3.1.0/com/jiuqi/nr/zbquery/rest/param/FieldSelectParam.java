/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.rest.param;

import com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx;
import com.jiuqi.nr.zbquery.model.HiddenDimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FieldSelectParam {
    private List<SelectedFieldDefineEx> zbs;
    private Map<String, String> extendedDatas;
    private List<HiddenDimension> hiddenDimensions = new ArrayList<HiddenDimension>();

    public List<SelectedFieldDefineEx> getZbs() {
        return this.zbs;
    }

    public void setZbs(List<SelectedFieldDefineEx> zbs) {
        this.zbs = zbs;
    }

    public Map<String, String> getExtendedDatas() {
        return this.extendedDatas;
    }

    public void setExtendedDatas(Map<String, String> extendedDatas) {
        this.extendedDatas = extendedDatas;
    }

    public List<HiddenDimension> getHiddenDimensions() {
        return this.hiddenDimensions;
    }

    public void setHiddenDimensions(List<HiddenDimension> hiddenDimensions) {
        this.hiddenDimensions = hiddenDimensions;
    }
}

