/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.param.impl;

import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.param.IEntityDeleteParam;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import java.util.List;

public class EntityDeleteParam
extends EntityQueryParam
implements IEntityDeleteParam {
    private List<EntityDataRow> deleteRows;

    @Override
    public List<EntityDataRow> getDeleteRows() {
        return this.deleteRows;
    }

    public void setDeleteRows(List<EntityDataRow> deleteRows) {
        this.deleteRows = deleteRows;
    }
}

