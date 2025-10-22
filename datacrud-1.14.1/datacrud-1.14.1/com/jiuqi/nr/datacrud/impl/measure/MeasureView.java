/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datacrud.impl.measure;

import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class MeasureView {
    private String key;
    private String title;
    private String code;
    private String name;
    private String groupCode;
    private String groupName;

    public MeasureView() {
        this.groupCode = "RENMINBI";
        this.groupName = "\u4eba\u6c11\u5e01";
    }

    public MeasureView(TableModelDefine tableModelDefine) {
        this.key = tableModelDefine.getID();
        this.title = tableModelDefine.getTitle();
        this.code = tableModelDefine.getCode();
        this.name = tableModelDefine.getName();
        this.groupCode = "RENMINBI";
        this.groupName = "\u4eba\u6c11\u5e01";
    }

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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

