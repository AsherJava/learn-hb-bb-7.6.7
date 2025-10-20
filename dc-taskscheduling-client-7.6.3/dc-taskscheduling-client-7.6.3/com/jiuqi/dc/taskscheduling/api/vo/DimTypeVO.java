/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.dc.taskscheduling.api.vo;

import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;

public class DimTypeVO
implements IDimType {
    private String name;
    private String title;

    public DimTypeVO() {
    }

    public DimTypeVO(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

