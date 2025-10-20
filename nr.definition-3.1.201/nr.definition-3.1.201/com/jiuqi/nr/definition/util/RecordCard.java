/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.nr.definition.util.TitleAndKey;
import java.io.Serializable;
import java.util.ArrayList;

public class RecordCard
implements Serializable {
    private String columu;
    private ArrayList<TitleAndKey> linkTitleKey;

    public String getColumu() {
        return this.columu;
    }

    public void setColumu(String columu) {
        this.columu = columu;
    }

    public ArrayList<TitleAndKey> getLinkTitleKey() {
        return this.linkTitleKey;
    }

    public void setLinkTitleKey(ArrayList<TitleAndKey> linkTitleKey) {
        this.linkTitleKey = linkTitleKey;
    }
}

