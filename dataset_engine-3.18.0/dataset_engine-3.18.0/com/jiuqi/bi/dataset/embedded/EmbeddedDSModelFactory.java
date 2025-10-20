/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.embedded;

import com.jiuqi.bi.dataset.embedded.EmbeddedDSModel;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;

public class EmbeddedDSModelFactory
extends DSModelFactory {
    @Override
    public DSModel createDataSetModel() {
        return new EmbeddedDSModel();
    }

    @Override
    public String getType() {
        return "embedded";
    }
}

