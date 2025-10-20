/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.HashMap;
import java.util.Map;

public class ReportFunContext
implements IContext {
    private Map<String, IEntityTable> dimEntityTables = new HashMap<String, IEntityTable>();
    private Map<String, String> tableCodeToEntityIdMap = new HashMap<String, String>();

    public Map<String, IEntityTable> getDimEntityTables() {
        return this.dimEntityTables;
    }

    public Map<String, String> getTableCodeToEntityIdMap() {
        return this.tableCodeToEntityIdMap;
    }

    public void setTableCodeToEntityIdMap(Map<String, String> tableCodeToEntityIdMap) {
        this.tableCodeToEntityIdMap = tableCodeToEntityIdMap;
    }
}

