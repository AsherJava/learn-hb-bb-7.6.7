/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import java.util.List;

public class CorporateSubBaseDataRowData
extends EntityResultSet {
    private CorporateEntityData corporateEntityData;

    public CorporateSubBaseDataRowData(int total, CorporateEntityData corporateEntityData) {
        super(total);
        this.corporateEntityData = corporateEntityData;
    }

    public List<String> getAllKeys() {
        return null;
    }

    protected Object getColumnObject(int index, String columnCode) {
        return this.corporateEntityData.getKey();
    }

    protected String getKey(int index) {
        return this.corporateEntityData.getKey();
    }

    protected String getCode(int index) {
        return this.corporateEntityData.getCode();
    }

    protected String getTitle(int index) {
        return this.corporateEntityData.getTitle();
    }

    protected String getParent(int index) {
        return null;
    }

    protected Object getOrder(int index) {
        return null;
    }

    protected String[] getParents(int index) {
        return new String[0];
    }

    public int append(EntityResultSet rs) {
        return 0;
    }
}

