/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 */
package com.jiuqi.nr.designer.web.facade.cycle;

import com.jiuqi.nr.definition.formulamapping.facade.Data;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.designer.web.facade.cycle.CycleData;

public class CycleTree
extends TreeObj {
    private CycleData data;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = (CycleData)data;
    }
}

