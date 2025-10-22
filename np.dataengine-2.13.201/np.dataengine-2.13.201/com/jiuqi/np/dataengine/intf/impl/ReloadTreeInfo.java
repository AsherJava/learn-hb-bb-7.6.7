/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.np.dataengine.intf.impl.ReloadTreeFieldInfo;
import java.util.ArrayList;
import java.util.List;

public class ReloadTreeInfo {
    private List<ReloadTreeFieldInfo> reloadTreeFieldInfos;
    private String filterCondition;
    private boolean showDetailEntitys = true;
    private boolean notShowPreEntitys = false;

    public List<ReloadTreeFieldInfo> getReloadTreeFieldInfos() {
        return this.reloadTreeFieldInfos;
    }

    public void setReloadTreeFieldInfos(List<ReloadTreeFieldInfo> reloadTreeFieldInfos) {
        this.reloadTreeFieldInfos = reloadTreeFieldInfos;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public boolean isShowDetailEntitys() {
        return this.showDetailEntitys;
    }

    public void setShowDetailEntitys(boolean showDetailEntitys) {
        this.showDetailEntitys = showDetailEntitys;
    }

    public boolean isNotShowPreEntitys() {
        return this.notShowPreEntitys;
    }

    public void setNotShowPreEntitys(boolean notShowPreEntitys) {
        this.notShowPreEntitys = notShowPreEntitys;
    }

    public boolean isEmpty() {
        if (null != this.reloadTreeFieldInfos && this.reloadTreeFieldInfos.size() > 0) {
            ArrayList<ReloadTreeFieldInfo> removeList = new ArrayList<ReloadTreeFieldInfo>();
            for (ReloadTreeFieldInfo reloadTreeFieldInfo : this.reloadTreeFieldInfos) {
                List<Integer> showLevels = reloadTreeFieldInfo.getShowLevels();
                if (null != showLevels && showLevels.size() != 0) continue;
                removeList.add(reloadTreeFieldInfo);
            }
            this.reloadTreeFieldInfos.removeAll(removeList);
            if (this.reloadTreeFieldInfos.size() > 0) {
                return false;
            }
        }
        return true;
    }
}

