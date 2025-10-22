/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl
 *  com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;

public class BSEntityRowImpl
extends EntityRowImpl {
    private String key;
    private String code;
    private String title;
    private boolean isLeaf;
    private boolean hasChildren;

    public BSEntityRowImpl(ReadonlyTableImpl table, DimensionValueSet rowKeys, int rowIndex) {
        super(table, rowKeys, rowIndex);
    }

    public BSEntityRowImpl(String key, String code, String title) {
        this(null, null, 0);
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public String getEntityKeyData() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAsString(String code) throws RuntimeException {
        return null;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public boolean hasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}

