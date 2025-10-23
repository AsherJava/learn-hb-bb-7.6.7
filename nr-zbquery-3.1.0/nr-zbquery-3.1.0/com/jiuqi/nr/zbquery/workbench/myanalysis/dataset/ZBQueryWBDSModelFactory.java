/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModelFactory;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSModel;

public class ZBQueryWBDSModelFactory
extends ZBQueryDSModelFactory {
    @Override
    public DSModel createDataSetModel() {
        return new ZBQueryWBDSModel();
    }

    @Override
    public String getType() {
        return "com.jiuqi.nr.dataset.zbquery.workbench";
    }
}

