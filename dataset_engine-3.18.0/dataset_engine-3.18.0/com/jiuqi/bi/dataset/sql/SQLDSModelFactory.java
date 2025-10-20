/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactory;
import com.jiuqi.bi.dataset.sql.SQLModel;

public class SQLDSModelFactory
extends DSModelFactory {
    @Override
    public DSModel createDataSetModel() {
        return new SQLModel();
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.dataset.sq";
    }
}

