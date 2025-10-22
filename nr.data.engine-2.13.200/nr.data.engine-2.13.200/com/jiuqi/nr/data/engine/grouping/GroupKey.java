/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.GradeLinkItem
 *  com.jiuqi.np.dataengine.setting.DataRegTotalInfo
 *  com.jiuqi.np.dataengine.setting.GradeTotalItem
 *  org.springframework.data.util.Pair
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.np.dataengine.common.GradeLinkItem;
import com.jiuqi.np.dataengine.setting.DataRegTotalInfo;
import com.jiuqi.np.dataengine.setting.GradeTotalItem;
import java.util.List;
import org.springframework.data.util.Pair;

public class GroupKey {
    private List<String> keys;
    private DataRegTotalInfo dataRegTotalInfo;

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public DataRegTotalInfo getDataRegTotalInfo() {
        return this.dataRegTotalInfo;
    }

    public void setDataRegTotalInfo(DataRegTotalInfo dataRegTotalInfo) {
        this.dataRegTotalInfo = dataRegTotalInfo;
    }

    public String getGroupKeyValue() {
        return (String)this.getLevelKey(Integer.MAX_VALUE).getFirst();
    }

    public Pair<String, GradeLinkItem> getLevelKey(int level) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.keys.size(); ++i) {
            Integer size = (Integer)this.dataRegTotalInfo.getPrecisions().get(i);
            String value = this.keys.get(i);
            if (size != null) {
                if (value.length() > size) {
                    value = value.substring(0, size);
                    result.append(value);
                } else if (value.length() < size) {
                    result.append(value);
                    for (int ignore = 0; ignore < size - value.length(); ++ignore) {
                        result.append('#');
                    }
                } else {
                    result.append(value);
                }
            } else {
                result.append(value);
            }
            if (result.length() < level) continue;
            result.setLength(level);
            GradeTotalItem gradeTotalItem = (GradeTotalItem)this.dataRegTotalInfo.getGradeTotalItems().get(i);
            GradeLinkItem gradeLink = gradeTotalItem.getGradeLink();
            return Pair.of((Object)result.toString(), (Object)gradeLink);
        }
        GradeTotalItem gradeTotalItem = (GradeTotalItem)this.dataRegTotalInfo.getGradeTotalItems().get(this.dataRegTotalInfo.getGradeTotalItems().size() - 1);
        GradeLinkItem gradeLink = gradeTotalItem.getGradeLink();
        return Pair.of((Object)result.toString(), (Object)gradeLink);
    }

    public int length() {
        int length = 0;
        for (int i = 0; i < this.keys.size(); ++i) {
            Integer size = (Integer)this.dataRegTotalInfo.getPrecisions().get(i);
            String value = this.keys.get(i);
            if (size != null) {
                length += size.intValue();
                continue;
            }
            length += value.length();
        }
        return length;
    }
}

