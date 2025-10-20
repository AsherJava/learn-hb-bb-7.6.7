/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RulerFields {
    private Map<String, Map<String, Boolean>> fields = new HashMap<String, Map<String, Boolean>>();

    public static RulerFields build() {
        return new RulerFields();
    }

    public RulerFields anyTables() {
        this.fields = null;
        return this;
    }

    public RulerFields noTables() {
        this.fields = new HashMap<String, Map<String, Boolean>>();
        return this;
    }

    public RulerFields anyFields(String tableName, boolean currentRow) {
        return this.field(tableName, "*", currentRow);
    }

    public RulerFields noFields(String tableName, boolean currentRow) {
        this.fields.put(tableName, currentRow ? Collections.emptyMap() : null);
        return this;
    }

    public RulerFields field(String tableName, String fieldName, boolean currentRow) {
        Map<String, Boolean> map = this.fields.get(tableName);
        if (map == null) {
            map = new HashMap<String, Boolean>();
            this.fields.put(tableName, map);
        }
        map.put(fieldName, currentRow);
        return this;
    }

    public RulerFields fields(String tableName, List<String> fieldNames, boolean currentRow) {
        Map<String, Boolean> map = this.fields.get(tableName);
        if (map == null) {
            map = new HashMap<String, Boolean>();
            this.fields.put(tableName, map);
        }
        Map<String, Boolean> map0 = map;
        fieldNames.forEach(o -> map0.put((String)o, currentRow));
        return this;
    }

    public Map<String, Map<String, Boolean>> fields() {
        return this.fields;
    }
}

