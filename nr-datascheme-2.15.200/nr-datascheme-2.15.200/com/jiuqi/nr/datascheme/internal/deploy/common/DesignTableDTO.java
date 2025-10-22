/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesignTableDTO {
    private final DesignTableModelDefine tableModel;
    private final List<DesignColumnModelDefine> columnModels;
    private final List<DesignIndexModelDefine> indexModels;

    public DesignTableDTO(DesignTableModelDefine table, List<DesignColumnModelDefine> columns, List<DesignIndexModelDefine> indexes) {
        this.tableModel = table;
        this.columnModels = columns;
        this.indexModels = indexes;
    }

    public DesignTableModelDefine getTableModel() {
        return this.tableModel;
    }

    public List<DesignColumnModelDefine> getColumnModels() {
        return null == this.columnModels ? Collections.emptyList() : this.columnModels;
    }

    public List<DesignIndexModelDefine> getIndexModels() {
        return null == this.indexModels ? Collections.emptyList() : this.indexModels;
    }

    public Map<String, DesignColumnModelDefine> getColumnCodeMap() {
        HashMap<String, DesignColumnModelDefine> map = new HashMap<String, DesignColumnModelDefine>();
        for (DesignColumnModelDefine columnModel : this.getColumnModels()) {
            map.put(columnModel.getCode(), columnModel);
        }
        return map;
    }

    public Map<String, DesignIndexModelDefine> getIndexIdMap() {
        HashMap<String, DesignIndexModelDefine> map = new HashMap<String, DesignIndexModelDefine>();
        for (DesignIndexModelDefine indexModel : this.getIndexModels()) {
            map.put(indexModel.getID(), indexModel);
        }
        return map;
    }
}

