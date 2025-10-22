/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.EntityData
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.List;

public class SceneSubjectInfo {
    private String dimensionName;
    private String title;
    private List<EntityData> entitys;
    private String entityKey;

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EntityData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityData> entitys) {
        this.entitys = entitys;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }
}

