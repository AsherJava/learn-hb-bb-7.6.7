/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimeTableDTO {
    private final TableModelDefine tableModel;
    private final List<ColumnModelDefine> columnModels;
    private final List<IndexModelDefine> indexModels;

    public RuntimeTableDTO(TableModelDefine table, List<ColumnModelDefine> columns, List<IndexModelDefine> indexes) {
        this.tableModel = table;
        this.columnModels = columns;
        this.indexModels = indexes;
    }

    public TableModelDefine getTableModel() {
        return this.tableModel;
    }

    public List<ColumnModelDefine> getColumnModels() {
        return null == this.columnModels ? Collections.emptyList() : this.columnModels;
    }

    public List<IndexModelDefine> getIndexModels() {
        return null == this.indexModels ? Collections.emptyList() : this.indexModels;
    }

    public Map<String, ColumnModelDefine> getColumnCodeMap() {
        HashMap<String, ColumnModelDefine> map = new HashMap<String, ColumnModelDefine>();
        for (ColumnModelDefine columnModel : this.getColumnModels()) {
            map.put(columnModel.getCode(), columnModel);
        }
        return map;
    }

    public Map<String, IndexModelDefine> getIndexIdMap() {
        HashMap<String, IndexModelDefine> map = new HashMap<String, IndexModelDefine>();
        for (IndexModelDefine indexModel : this.getIndexModels()) {
            map.put(indexModel.getID(), indexModel);
        }
        return map;
    }
}

