/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

import com.jiuqi.nr.definition.option.core.AffectedMode;
import java.util.List;

public class RelationTaskOptionItem {
    private String fromKey;
    private AffectedMode affectedMode;
    private List<String> fromValues;
    private String tipsMsg;

    public String getFromKey() {
        return this.fromKey;
    }

    public void setFromKey(String fromKey) {
        this.fromKey = fromKey;
    }

    public AffectedMode getAffectedMode() {
        return this.affectedMode;
    }

    public void setAffectedMode(AffectedMode affectedMode) {
        this.affectedMode = affectedMode;
    }

    public List<String> getFromValues() {
        return this.fromValues;
    }

    public void setFromValues(List<String> fromValues) {
        this.fromValues = fromValues;
    }

    public String getTipsMsg() {
        return this.tipsMsg;
    }

    public void setTipsMsg(String tipsMsg) {
        this.tipsMsg = tipsMsg;
    }
}

