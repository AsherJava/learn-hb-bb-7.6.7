/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.scroll;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.scroll.DatasetReader;
import com.jiuqi.bi.dataset.scroll.JdbcDatasetReader;
import com.jiuqi.bi.dataset.scroll.MemoryDatasetReader;
import com.jiuqi.bi.dataset.sql.SQLModel;

public class DatasetFactory {
    public static DatasetReader createDatasetReader(DSModel model) {
        if (model instanceof SQLModel) {
            return new JdbcDatasetReader(model);
        }
        return new MemoryDatasetReader(model);
    }
}

