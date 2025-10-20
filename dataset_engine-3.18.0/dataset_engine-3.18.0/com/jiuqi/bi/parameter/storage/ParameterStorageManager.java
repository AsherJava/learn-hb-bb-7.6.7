/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.storage;

import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.storage.IParameterStorageProvider;
import com.jiuqi.bi.parameter.storage.ParameterBean;
import com.jiuqi.bi.parameter.storage.ParameterFolderBean;
import com.jiuqi.bi.parameter.storage.ParameterStorageException;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParameterStorageManager {
    private static ParameterStorageManager instance = new ParameterStorageManager();
    private Map<String, IParameterStorageProvider> map = new ConcurrentHashMap<String, IParameterStorageProvider>();
    private Comparator<IParameterStorageProvider> comparator = null;
    private static final String REMOTEPARAMETERPREFIX = "com.jiuqi.bi.dataset.remote.";

    public static final ParameterStorageManager getInstance() {
        return instance;
    }

    private ParameterStorageManager() {
    }

    public void setComparator(Comparator<IParameterStorageProvider> comparator) {
        this.comparator = comparator;
    }

    public void registParameterStorageProvider(IParameterStorageProvider provider) {
        this.map.put(provider.getStorageType(), provider);
    }

    public void removeParameterStorageProvider(String storageType) {
        this.map.remove(storageType);
    }

    public IParameterStorageProvider[] getAllProvider() {
        if (this.comparator == null) {
            return this.map.values().toArray(new IParameterStorageProvider[0]);
        }
        ArrayList<IParameterStorageProvider> list = new ArrayList<IParameterStorageProvider>();
        list.addAll(this.map.values());
        Collections.sort(list, this.comparator);
        return list.toArray(new IParameterStorageProvider[0]);
    }

    public IParameterStorageProvider getStorageProvider(String storageType) {
        if (StringUtils.isEmpty((String)storageType)) {
            return (IParameterStorageProvider)this.map.values().toArray()[0];
        }
        return this.map.get(storageType);
    }

    public Object[] getChildren(String parentId, String storageType) throws ParameterStorageException {
        if (StringUtils.isEmpty((String)storageType)) {
            for (IParameterStorageProvider provider : this.map.values()) {
                if (this.isRemoteDataSet(provider)) continue;
                ParameterFolderBean[] folders = provider.getFolder(parentId);
                ParameterBean[] parameterBeans = provider.getParameterBeans(parentId);
                if (!(folders != null && folders.length != 0 || parameterBeans != null && parameterBeans.length != 0)) continue;
                Object[] dest = new Object[folders.length + parameterBeans.length];
                System.arraycopy(folders, 0, dest, 0, folders.length);
                System.arraycopy(parameterBeans, 0, dest, folders.length, parameterBeans.length);
                return dest;
            }
        } else {
            ParameterFolderBean[] folders;
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            for (ParameterFolderBean folder : folders = provider.getFolder(parentId)) {
                folder.setStorageType(storageType);
            }
            ParameterBean[] parameterBeans = provider.getParameterBeans(parentId);
            Object[] dest = new Object[folders.length + parameterBeans.length];
            System.arraycopy(folders, 0, dest, 0, folders.length);
            System.arraycopy(parameterBeans, 0, dest, folders.length, parameterBeans.length);
            return dest;
        }
        return null;
    }

    public ParameterModel findModel(String id, String owner_Name, String owner_Type, String storageType) throws ParameterStorageException {
        if (StringUtils.isEmpty((String)storageType)) {
            for (IParameterStorageProvider provider : this.map.values()) {
                if (this.isRemoteDataSet(provider)) continue;
                try {
                    ParameterModel model = provider.findModel(id, owner_Name, owner_Type);
                    if (model == null) continue;
                    return model;
                }
                catch (ParameterStorageException e) {
                    e.printStackTrace();
                }
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            if (provider == null) {
                throw new ParameterStorageException("\u53c2\u6570\u7c7b\u578b\u3010" + storageType + "\u3011\u4e0d\u5b58\u5728");
            }
            return provider.findModel(id, owner_Name, owner_Type);
        }
        return null;
    }

    public ParameterModel[] findModels(String[] ids, String owner_Name, String owner_Type, String storageType) throws ParameterStorageException {
        if (StringUtils.isEmpty((String)storageType)) {
            for (IParameterStorageProvider provider : this.map.values()) {
                if (this.isRemoteDataSet(provider)) continue;
                try {
                    ParameterModel[] models = provider.findModels(ids, owner_Name, owner_Type);
                    if (models == null || models.length <= 0) continue;
                    return models;
                }
                catch (ParameterStorageException e) {
                    e.printStackTrace();
                }
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            if (provider == null) {
                throw new ParameterStorageException("\u53c2\u6570\u7c7b\u578b\u3010" + storageType + "\u3011\u4e0d\u5b58\u5728");
            }
            return provider.findModels(ids, owner_Name, owner_Type);
        }
        return new ParameterModel[0];
    }

    public ParameterBean getParameterBean(String id, String owner_Name, String owner_Type, String storageType) throws ParameterStorageException {
        if (StringUtils.isEmpty((String)storageType)) {
            for (IParameterStorageProvider provider : this.map.values()) {
                ParameterBean bean;
                if (this.isRemoteDataSet(provider) || (bean = provider.getParameterBean(id, owner_Name, owner_Type)) == null) continue;
                return bean;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            return provider.getParameterBean(id, owner_Name, owner_Type);
        }
        return null;
    }

    public String[] getPath(String id, String owner_Name, String owner_Type, String storageType) throws ParameterStorageException {
        if (StringUtils.isEmpty((String)storageType)) {
            for (IParameterStorageProvider provider : this.map.values()) {
                String[] path;
                if (this.isRemoteDataSet(provider) || (path = provider.getPath(id, owner_Name, owner_Type)) == null || path.length <= 0) continue;
                return path;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            return provider.getPath(id, owner_Name, owner_Type);
        }
        return null;
    }

    public ParameterBean[] getParameterBeans(String owner_name, String owner_Type, String storageType) throws ParameterStorageException {
        if (StringUtils.isEmpty((String)storageType)) {
            for (IParameterStorageProvider provider : this.map.values()) {
                ParameterBean[] beans;
                if (this.isRemoteDataSet(provider) || (beans = provider.getParameterBeans(owner_name, owner_Type)) == null || beans.length != 0) continue;
                return beans;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            return provider.getParameterBeans(owner_name, owner_Type);
        }
        return null;
    }

    private boolean isRemoteDataSet(IParameterStorageProvider provider) {
        return provider.getStorageType().startsWith(REMOTEPARAMETERPREFIX);
    }
}

