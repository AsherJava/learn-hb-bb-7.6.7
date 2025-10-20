/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.storage;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.storage.DataSetBean;
import com.jiuqi.bi.dataset.storage.DataSetFolderBean;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.IDataSetStorageProvider;
import com.jiuqi.bi.dataset.storage.IDataSetStorageProviderWithContext;
import com.jiuqi.bi.dataset.storage.StorageContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSetStorageManager {
    private static DataSetStorageManager instance = new DataSetStorageManager();
    private Map<String, IDataSetStorageProvider> map = new ConcurrentHashMap<String, IDataSetStorageProvider>();
    private Comparator<IDataSetStorageProvider> comparator = null;
    private static final String REMOTEDATASET_PREFIX = "com.jiuqi.bi.dataset.remote.";

    public static final DataSetStorageManager getInstance() {
        return instance;
    }

    private DataSetStorageManager() {
    }

    public void setComparator(Comparator<IDataSetStorageProvider> comparator) {
        this.comparator = comparator;
    }

    public void registDataSetStorageProvider(IDataSetStorageProvider provider) {
        this.map.put(provider.getStorageType(), provider);
    }

    public void removeDataSetStorageProvider(String storageType) {
        this.map.remove(storageType);
    }

    public IDataSetStorageProvider[] getAllProvider() {
        if (this.comparator == null) {
            return this.map.values().toArray(new IDataSetStorageProvider[0]);
        }
        ArrayList<IDataSetStorageProvider> list = new ArrayList<IDataSetStorageProvider>();
        list.addAll(this.map.values());
        Collections.sort(list, this.comparator);
        return list.toArray(new IDataSetStorageProvider[0]);
    }

    public IDataSetStorageProvider getStorageProvider(String storageType) {
        if (StringUtils.isEmpty((String)storageType)) {
            return this.map.values().iterator().next();
        }
        return this.map.get(storageType);
    }

    public Object[] getChildren(String parentId, String storageType) throws DataSetStorageException {
        return this.getChildren(parentId, storageType, null);
    }

    public Object[] getChildren(String parentId, String storageType, String userGuid) throws DataSetStorageException {
        DataSetFolderBean[] folders;
        IDataSetStorageProvider provider = this.getStorageProvider(storageType);
        if (provider == null) {
            throw new DataSetStorageException("\u65e0\u6cd5\u627e\u5230\u5b58\u50a8\u7c7b\u578b\uff1a" + storageType);
        }
        IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(provider);
        for (DataSetFolderBean folder : folders = providerWC != null ? providerWC.getFolder(parentId, this.getStorageContext(userGuid)) : provider.getFolder(parentId)) {
            folder.setStorageType(storageType);
        }
        DataSetBean[] datasets = providerWC != null ? providerWC.getDataSet(parentId, this.getStorageContext(userGuid)) : provider.getDataSet(parentId);
        Object[] dest = new Object[folders.length + datasets.length];
        System.arraycopy(folders, 0, dest, 0, folders.length);
        System.arraycopy(datasets, 0, dest, folders.length, datasets.length);
        return dest;
    }

    public DSModel findModel(String id, String datasetType) throws DataSetStorageException {
        return this.findModel(id, datasetType, null);
    }

    public DSModel findModel(String id, String datasetType, String userGuid) throws DataSetStorageException {
        if (StringUtils.isEmpty((String)datasetType)) {
            for (IDataSetStorageProvider p : this.getAllProvider()) {
                DSModel model;
                if (this.isRemoteDataSet(p)) continue;
                IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(p);
                DSModel dSModel = model = providerWC != null ? providerWC.findModel(id, this.getStorageContext(userGuid)) : p.findModel(id);
                if (model == null) continue;
                return model;
            }
            return null;
        }
        IDataSetStorageProvider provider = null;
        for (IDataSetStorageProvider p : this.getAllProvider()) {
            if (!p.support(datasetType)) continue;
            provider = p;
            break;
        }
        if (provider == null) {
            return null;
        }
        IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(provider);
        return providerWC != null ? providerWC.findModel(id, this.getStorageContext(userGuid)) : provider.findModel(id);
    }

    public DataSetBean getDataSetBean(String id, String datasetType) throws DataSetStorageException {
        return this.getDataSetBean(id, datasetType, null);
    }

    public DataSetBean getDataSetBean(String id, String datasetType, String userGuid) throws DataSetStorageException {
        if (StringUtils.isEmpty((String)datasetType)) {
            for (IDataSetStorageProvider p : this.getAllProvider()) {
                DataSetBean bean;
                if (this.isRemoteDataSet(p)) continue;
                IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(p);
                DataSetBean dataSetBean = bean = providerWC != null ? providerWC.getDataSetBean(id, this.getStorageContext(userGuid)) : p.getDataSetBean(id);
                if (bean == null) continue;
                return bean;
            }
            return null;
        }
        IDataSetStorageProvider provider = null;
        for (IDataSetStorageProvider p : this.getAllProvider()) {
            if (!p.support(datasetType)) continue;
            provider = p;
            break;
        }
        if (provider == null) {
            return null;
        }
        IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(provider);
        DataSetBean bean = providerWC != null ? providerWC.getDataSetBean(id, this.getStorageContext(userGuid)) : provider.getDataSetBean(id);
        return bean;
    }

    public String[] getPath(String id, String datasetType) throws DataSetStorageException {
        return this.getPath(id, datasetType, null);
    }

    public String[] getPath(String id, String datasetType, String userGuid) throws DataSetStorageException {
        String[] path = null;
        String storageType = null;
        IDataSetStorageProvider[] storageProviders = this.getAllProvider();
        if (StringUtils.isEmpty((String)datasetType)) {
            for (IDataSetStorageProvider p : storageProviders) {
                String[] sub;
                if (this.isRemoteDataSet(p)) continue;
                IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(p);
                String[] stringArray = sub = providerWC != null ? providerWC.getPath(id, this.getStorageContext(userGuid)) : p.getPath(id);
                if (sub == null) continue;
                path = sub;
                storageType = p.getStorageType();
                break;
            }
        } else {
            IDataSetStorageProvider provider = null;
            for (IDataSetStorageProvider p : storageProviders) {
                if (!p.support(datasetType)) continue;
                provider = p;
                break;
            }
            if (provider == null) {
                return null;
            }
            IDataSetStorageProviderWithContext providerWC = this.getProviderWithContext(provider);
            path = providerWC != null ? providerWC.getPath(id, this.getStorageContext(userGuid)) : provider.getPath(id);
            storageType = provider.getStorageType();
        }
        if (path == null) {
            return null;
        }
        if (storageProviders.length == 1) {
            return path;
        }
        String[] newPath = new String[path.length + 1];
        newPath[0] = "root$" + storageType;
        for (int i = 0; i < path.length; ++i) {
            newPath[i + 1] = path[i];
        }
        return newPath;
    }

    private StorageContext getStorageContext(String userGuid) {
        StorageContext sc = new StorageContext();
        sc.setUserguid(userGuid);
        return sc;
    }

    private IDataSetStorageProviderWithContext getProviderWithContext(IDataSetStorageProvider provider) {
        if (provider instanceof IDataSetStorageProviderWithContext) {
            return (IDataSetStorageProviderWithContext)provider;
        }
        return null;
    }

    public boolean isRemoteDataSet(IDataSetStorageProvider provider) {
        return provider.getStorageType().startsWith(REMOTEDATASET_PREFIX);
    }
}

