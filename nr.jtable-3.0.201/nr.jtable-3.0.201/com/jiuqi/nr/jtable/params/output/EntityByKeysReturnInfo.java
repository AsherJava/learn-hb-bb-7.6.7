/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.EntityData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityByKeysReturnInfo {
    private String message;
    private List<String> cells = new ArrayList<String>();
    private Map<String, EntityData> entitys = new HashMap<String, EntityData>();

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

    public Map<String, EntityData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(Map<String, EntityData> entitys) {
        this.entitys = entitys;
    }
}

