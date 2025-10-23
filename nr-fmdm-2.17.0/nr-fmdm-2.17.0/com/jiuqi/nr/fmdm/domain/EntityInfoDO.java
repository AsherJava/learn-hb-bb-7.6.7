/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 */
package com.jiuqi.nr.fmdm.domain;

import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.fmdm.domain.ZbDataDO;
import java.util.Map;
import org.springframework.util.Assert;

public class EntityInfoDO
extends ZbDataDO {
    private IDataRow entityInfoRow;
    private Map<String, Integer> entityInfoIndex;

    public EntityInfoDO(IDataRow entityInfoRow, Map<String, Integer> entityInfoIndex, String entityKeyData) {
        super(null, null, entityKeyData);
        this.entityInfoIndex = entityInfoIndex;
        this.entityInfoRow = entityInfoRow;
    }

    public EntityInfoDO(ZbDataDO zbDataDO) {
        super(zbDataDO.getDataRow(), zbDataDO.getCodeToIndex(), zbDataDO.getEntityRow(), zbDataDO.getFMDMKey());
    }

    @Override
    public AbstractData getValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        Integer rowIndex = this.getRowIndex(code);
        if (rowIndex == null) {
            return super.getValue(code);
        }
        return this.getValueByIndex(code, rowIndex, this.entityInfoRow);
    }

    @Override
    public AbstractData getInfoValue(String code) {
        Assert.notNull((Object)code, "parameter 'code' can not be empty.");
        if (this.entityInfoRow == null) {
            return super.getInfoValue(code);
        }
        Integer rowIndex = this.getRowIndex(code);
        return this.getDataValueByIndex(code, rowIndex, this.entityInfoRow);
    }

    @Override
    public Object getInfoAsObject(String code) {
        return this.getInfoValue(code).getAsObject();
    }

    private Integer getRowIndex(String code) {
        if (this.entityInfoIndex == null) {
            return null;
        }
        return this.entityInfoIndex.get(code);
    }

    public Map<String, Integer> getEntityInfoIndex() {
        return this.entityInfoIndex;
    }

    public void setEntityInfoIndex(Map<String, Integer> entityInfoIndex) {
        this.entityInfoIndex = entityInfoIndex;
    }

    public void setEntityInfoRow(IDataRow entityInfoRow) {
        this.entityInfoRow = entityInfoRow;
    }
}

