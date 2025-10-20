/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.storage;

import com.jiuqi.bi.dataset.storage.DataSetBean;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import java.util.List;

public interface IDataSetStorageFilter {
    public List<DataSetBean> filterDataSet(String var1) throws DataSetStorageException;
}

