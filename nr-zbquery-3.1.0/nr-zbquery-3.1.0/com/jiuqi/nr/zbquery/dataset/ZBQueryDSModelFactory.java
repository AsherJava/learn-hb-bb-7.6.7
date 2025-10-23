/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelFactory
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModel;

public class ZBQueryDSModelFactory
extends DSModelFactory {
    public DSModel createDataSetModel() {
        return new ZBQueryDSModel();
    }

    public String getType() {
        return "com.jiuqi.nr.dataset.zbquery";
    }
}

