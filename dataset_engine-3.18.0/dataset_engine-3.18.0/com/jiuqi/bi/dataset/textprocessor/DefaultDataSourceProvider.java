/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.textprocessor;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.DataSetStorageManager;
import com.jiuqi.bi.dataset.textprocessor.IDataSourceProvider;

public class DefaultDataSourceProvider
implements IDataSourceProvider {
    @Override
    public DSModel findDatasetModel(String dsName) throws BIDataSetNotFoundException, BIDataSetException {
        DataSetStorageManager m = DataSetStorageManager.getInstance();
        try {
            DSModel model = m.findModel(dsName, null);
            if (model == null) {
                throw new BIDataSetNotFoundException(dsName);
            }
            return model;
        }
        catch (DataSetStorageException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
    }

    @Override
    public BIDataSet open(DSContext context, DSModel model) throws BIDataSetException {
        IDataSetManager dsMgr = DataSetManagerFactory.create();
        try {
            return dsMgr.open(context, model);
        }
        catch (DataSetTypeNotFoundException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
    }
}

