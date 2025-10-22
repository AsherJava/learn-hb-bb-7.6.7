/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.setting;

import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import java.util.ArrayList;
import java.util.List;

public class DataRegTotalInfo {
    private List<GradeTotalItem> gradeTotalItems;
    private List<Integer> gradeLevels;
    private List<Integer> precisions;

    public DataRegTotalInfo() {
        this.setGradeTotalItems(new ArrayList<GradeTotalItem>());
    }

    public DataRegTotalInfo(List<GradeTotalItem> gradeTotalItems) {
        this.setGradeTotalItems(gradeTotalItems);
    }

    public List<GradeTotalItem> getGradeTotalItems() {
        return this.gradeTotalItems;
    }

    public void setGradeTotalItems(List<GradeTotalItem> gradeTotalItems) {
        this.gradeTotalItems = gradeTotalItems;
    }

    public List<Integer> getGradeLevels() {
        return this.gradeLevels;
    }

    public void setGradeLevels(List<Integer> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }

    public boolean isMultiGradeLevels() {
        return this.gradeLevels != null && !this.gradeLevels.isEmpty();
    }

    public List<Integer> getPrecisions() {
        return this.precisions;
    }

    public void setPrecisions(List<Integer> precisions) {
        this.precisions = precisions;
    }
}

