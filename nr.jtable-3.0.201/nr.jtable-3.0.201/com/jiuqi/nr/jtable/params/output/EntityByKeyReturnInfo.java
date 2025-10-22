/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.ArrayList;
import java.util.List;

public class EntityByKeyReturnInfo {
    private String message;
    private List<String> cells = new ArrayList<String>();
    private EntityData entity;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getCells() {
        return this.cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }

    public EntityData getEntity() {
        return this.entity;
    }

    public void setEntity(EntityData entity) {
        this.entity = entity;
    }
}

