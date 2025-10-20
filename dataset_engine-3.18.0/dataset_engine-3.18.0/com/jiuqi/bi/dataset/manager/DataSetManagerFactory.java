/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.manager;

import com.jiuqi.bi.dataset.manager.CachePolicy;
import com.jiuqi.bi.dataset.manager.DataSetManager;
import com.jiuqi.bi.dataset.manager.IDataSetManager;

public class DataSetManagerFactory {
    public static IDataSetManager create(CachePolicy policy) {
        if (policy != null) {
            throw new IllegalArgumentException("\u65e0\u6b64\u7c7b\u578b\u7684\u7f13\u5b58\u673a\u5236\uff0c\u65e0\u6cd5\u521b\u5efa\u6570\u636e\u96c6\u7ba1\u7406\u5668");
        }
        return new DataSetManager();
    }

    public static IDataSetManager create() {
        return DataSetManagerFactory.create(null);
    }
}

