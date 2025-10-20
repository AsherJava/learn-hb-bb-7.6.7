/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.metadata.LogicIndexField
 */
package com.jiuqi.nr.common.temptable;

import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.nr.common.temptable.IndexField;
import java.util.ArrayList;
import java.util.List;

public class IndexMeta {
    private boolean unique;
    private List<IndexField> indexFields = new ArrayList<IndexField>();

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public List<IndexField> getIndexFields() {
        return this.indexFields;
    }

    public void addIndexField(IndexField field) {
        this.indexFields.add(field);
    }

    public void setIndexFields(List<IndexField> indexFields) {
        this.indexFields = indexFields;
    }

    public LogicIndex toLogicIndex() {
        LogicIndex logicIndex = new LogicIndex();
        logicIndex.setUnique(this.unique);
        for (IndexField indexField : this.indexFields) {
            LogicIndexField logicIndexField = new LogicIndexField();
            logicIndexField.setFieldName(indexField.getFieldName());
            logicIndexField.setSortType(indexField.getSortType());
            logicIndex.addIndexField(logicIndexField);
        }
        return logicIndex;
    }
}

