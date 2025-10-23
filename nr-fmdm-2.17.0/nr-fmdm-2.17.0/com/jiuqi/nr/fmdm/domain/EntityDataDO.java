/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fmdm.domain.AbstractFMDMDataDO;
import org.springframework.util.Assert;

public class EntityDataDO
extends AbstractFMDMDataDO {
    private IEntityRow entityRow;

    public EntityDataDO(IEntityRow entityRow) {
        super(entityRow.getEntityKeyData());
        this.entityRow = entityRow;
    }

    public EntityDataDO(IEntityRow entityRow, String entityKey) {
        super(entityKey);
        this.entityRow = entityRow;
    }

    @Override
    public DimensionValueSet getMasterKey() {
        return this.entityRow.getRowKeys();
    }

    @Override
    public AbstractData getValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (this.entityRow == null) {
            return AbstractData.empty();
        }
        return this.entityRow.getValue(code);
    }

    @Override
    public AbstractData getEntityValue(String code) {
        return this.getValue(code);
    }

    @Override
    public Object getEntityAsObject(String code) {
        return this.getEntityValue(code).getAsObject();
    }

    public void setEntityRow(IEntityRow entityRow) {
        this.entityRow = entityRow;
    }

    public IEntityRow getEntityRow() {
        return this.entityRow;
    }
}

