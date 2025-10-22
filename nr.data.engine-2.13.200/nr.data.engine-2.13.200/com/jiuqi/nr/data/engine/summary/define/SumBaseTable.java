/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.define;

import com.jiuqi.nr.data.engine.summary.define.SumBaseZB;
import java.util.ArrayList;
import java.util.List;

public class SumBaseTable {
    private String guid;
    private String name;
    private String title;
    private String folder;
    private String type;
    private String regionCodefield;
    private String timeField;
    private List<SumBaseZB> zbs = new ArrayList<SumBaseZB>();

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeField() {
        return this.timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    public String getRegionCodefield() {
        return this.regionCodefield;
    }

    public void setRegionCodefield(String regionCodefield) {
        this.regionCodefield = regionCodefield;
    }

    public List<SumBaseZB> getZbs() {
        return this.zbs;
    }
}

