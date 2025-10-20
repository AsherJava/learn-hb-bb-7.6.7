/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.storage;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.storage.DataSetBean;
import com.jiuqi.bi.dataset.storage.DataSetFolderBean;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.IDataSetStorageProvider;
import com.jiuqi.bi.dataset.storage.StorageContext;

public interface IDataSetStorageProviderWithContext
extends IDataSetStorageProvider {
    public DataSetFolderBean[] getFolder(String var1, StorageContext var2) throws DataSetStorageException;

    public DataSetBean[] getDataSet(String var1, StorageContext var2) throws DataSetStorageException;

    public String[] getPath(String var1, StorageContext var2) throws DataSetStorageException;

    public DataSetBean getDataSetBean(String var1, StorageContext var2) throws DataSetStorageException;

    public DSModel findModel(String var1, StorageContext var2) throws DataSetStorageException;
}

