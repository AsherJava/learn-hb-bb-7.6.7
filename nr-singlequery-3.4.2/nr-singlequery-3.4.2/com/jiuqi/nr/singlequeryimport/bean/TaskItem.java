/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;

public class TaskItem {
    private String key;
    private String title;
    private String aliasName;

    public String getKey() {
        return this.key;
    }

    public TaskItem(FormSchemeDefine formSchemeDefine) {
        this.key = formSchemeDefine.getTaskKey();
        this.title = formSchemeDefine.getTitle();
    }

    public TaskItem(TaskLinkDefine taskLinkDefine) {
        this.key = taskLinkDefine.getKey();
        this.title = taskLinkDefine.getTitle() + "@" + taskLinkDefine.getLinkAlias();
        this.aliasName = taskLinkDefine.getLinkAlias();
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

    public String getAliasName() {
        return this.aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}

