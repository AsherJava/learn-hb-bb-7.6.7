/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;

public class DesignTaskGroupData {
    private String key;
    private String title;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void init(DesignTaskGroupDefine designTaskGroupDefines) {
        this.title = designTaskGroupDefines.getTitle();
        this.key = designTaskGroupDefines.getKey();
    }
}

