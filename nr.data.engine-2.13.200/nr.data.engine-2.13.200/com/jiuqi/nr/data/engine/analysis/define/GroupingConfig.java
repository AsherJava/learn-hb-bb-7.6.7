/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.define;

import java.util.ArrayList;
import java.util.List;

public class GroupingConfig {
    private String groupingKey;
    private String levelString;
    private List<String> groupingKeyEvalExps = new ArrayList<String>();
    private boolean discardDetail;

    public String getGroupingKey() {
        return this.groupingKey;
    }

    public String getLevelString() {
        return this.levelString;
    }

    public List<String> getGroupingKeyEvalExps() {
        return this.groupingKeyEvalExps;
    }

    public boolean isDiscardDetail() {
        return this.discardDetail;
    }

    public void setGroupingKey(String groupingKey) {
        this.groupingKey = groupingKey;
    }

    public void setLevelString(String levelString) {
        this.levelString = levelString;
    }

    public void setDiscardDetail(boolean discardDetail) {
        this.discardDetail = discardDetail;
    }

    public String toString() {
        return "GroupingConfig [groupingKey=" + this.groupingKey + ", levelString=" + this.levelString + ", groupingKeyEvalExps=" + this.groupingKeyEvalExps + ", discardDetail=" + this.discardDetail + "]";
    }
}

