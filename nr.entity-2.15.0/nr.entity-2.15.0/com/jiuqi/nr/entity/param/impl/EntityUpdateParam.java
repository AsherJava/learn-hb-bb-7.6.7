/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.param.impl;

import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.param.IEntityUpdateParam;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import java.util.List;

public class EntityUpdateParam
extends EntityQueryParam
implements IEntityUpdateParam {
    private List<EntityDataRow> modifyRows;
    private boolean batchUpdateModel;

    @Override
    public List<EntityDataRow> getModifyRows() {
        return this.modifyRows;
    }

    public void setModifyRows(List<EntityDataRow> modifyRows) {
        this.modifyRows = modifyRows;
    }

    @Override
    public boolean isBatchUpdateModel() {
        return this.batchUpdateModel;
    }

    public void setBatchUpdateModel(boolean batchUpdateModel) {
        this.batchUpdateModel = batchUpdateModel;
    }
}

