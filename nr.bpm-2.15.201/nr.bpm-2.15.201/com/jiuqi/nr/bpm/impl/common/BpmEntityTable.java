/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.definition.facade.TableDefine;

public class BpmEntityTable {
    private TableDefine parentEntity;
    private TableDefine childEntity;

    public TableDefine getParentEntity() {
        return this.parentEntity;
    }

    public void setParentEntity(TableDefine parentEntity) {
        this.parentEntity = parentEntity;
    }

    public TableDefine getChildEntity() {
        return this.childEntity;
    }

    public void setChildEntity(TableDefine childEntity) {
        this.childEntity = childEntity;
    }

    public boolean isEmpty() {
        return this.childEntity == null && this.parentEntity == null;
    }
}

