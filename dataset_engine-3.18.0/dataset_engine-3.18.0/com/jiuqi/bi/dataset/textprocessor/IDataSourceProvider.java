/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.textprocessor;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.model.DSModel;

public interface IDataSourceProvider {
    public DSModel findDatasetModel(String var1) throws BIDataSetNotFoundException, BIDataSetException;

    public BIDataSet open(DSContext var1, DSModel var2) throws BIDataSetException;
}

