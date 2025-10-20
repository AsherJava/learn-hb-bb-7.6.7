/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nvwa.framework.parameter.storage;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.storage.IParameterStorageProvider;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterBean;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterFolderBean;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceTreeNode;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParameterStorageManager {
    private static ParameterStorageManager instance = new ParameterStorageManager();
    private List<IParameterStorageProvider> providers = new ArrayList<IParameterStorageProvider>();
    private Comparator<IParameterStorageProvider> comparator = null;
    private static final String REMOTEPARAMETERPREFIX = "com.jiuqi.bi.dataset.remote.";

    private ParameterStorageManager() {
    }

    public static ParameterStorageManager getInstance() {
        return instance;
    }

    public void registerStorageProvider(IParameterStorageProvider provider) {
        if (StringUtils.isEmpty((String)provider.getStorageType())) {
            return;
        }
        for (IParameterStorageProvider p : this.providers) {
            if (!provider.getStorageType().equals(p.getStorageType())) continue;
            return;
        }
        this.providers.add(provider);
    }

    public void removeStorageProvider(String storageType) {
        for (IParameterStorageProvider provider : this.providers) {
            if (!storageType.equals(provider.getStorageType())) continue;
            this.providers.remove(provider);
            return;
        }
    }

    public void setComparator(Comparator<IParameterStorageProvider> comparator) {
        this.comparator = comparator;
    }

    public List<IParameterStorageProvider> getAllProvider() {
        if (this.comparator == null) {
            return new ArrayList<IParameterStorageProvider>(this.providers);
        }
        ArrayList<IParameterStorageProvider> list = new ArrayList<IParameterStorageProvider>();
        list.addAll(this.providers);
        Collections.sort(list, this.comparator);
        return list;
    }

    public IParameterStorageProvider getStorageProvider(String storageType) {
        if (storageType == null) {
            throw new IllegalArgumentException("\u53c2\u6570\u5b58\u50a8\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        for (IParameterStorageProvider provider : this.providers) {
            if (!provider.getStorageType().equals(storageType)) continue;
            return provider;
        }
        return null;
    }

    public List<ParameterResourceTreeNode> getChildren(String parentId, String storageType) throws ParameterStorageException {
        if (this.isStorageTypeEmpty(storageType)) {
            for (IParameterStorageProvider provider : this.providers) {
                if (this.isRemoteDataSet(provider)) continue;
                List<ParameterFolderBean> folders = provider.getFolder(parentId);
                List<ParameterBean> parameterBeans = provider.getParameterBeans(parentId);
                ArrayList<ParameterResourceTreeNode> list = new ArrayList<ParameterResourceTreeNode>();
                if (folders != null) {
                    list.addAll(folders);
                }
                if (parameterBeans != null) {
                    list.addAll(parameterBeans);
                }
                if (list.isEmpty()) continue;
                return list;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            List<ParameterFolderBean> folders = provider.getFolder(parentId);
            List<ParameterBean> parameterBeans = provider.getParameterBeans(parentId);
            ArrayList<ParameterResourceTreeNode> list = new ArrayList<ParameterResourceTreeNode>();
            if (folders != null) {
                list.addAll(folders);
            }
            if (parameterBeans != null) {
                list.addAll(parameterBeans);
            }
            return list;
        }
        return new ArrayList<ParameterResourceTreeNode>();
    }

    public ParameterModel findModel(ParameterResourceIdentify identify, String storageType) throws ParameterStorageException {
        if (this.isStorageTypeEmpty(storageType)) {
            for (IParameterStorageProvider provider : this.providers) {
                ParameterModel model;
                if (this.isRemoteDataSet(provider) || (model = provider.findModel(identify)) == null) continue;
                return model;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            if (provider == null) {
                throw new ParameterStorageException("\u53c2\u6570\u7c7b\u578b\u3010" + storageType + "\u3011\u4e0d\u5b58\u5728");
            }
            return provider.findModel(identify);
        }
        return null;
    }

    public List<ParameterModel> findModels(List<String> parameterNames, String storageType) throws ParameterStorageException {
        return this.findModels(parameterNames, null, null, storageType);
    }

    public List<ParameterModel> findModels(List<String> parameterNames, String ownerName, String ownerType, String storageType) throws ParameterStorageException {
        if (this.isStorageTypeEmpty(storageType)) {
            for (IParameterStorageProvider provider : this.providers) {
                List<ParameterModel> rs;
                if (this.isRemoteDataSet(provider) || (rs = provider.findModels(parameterNames, ownerName, ownerType)) == null || rs.size() != parameterNames.size()) continue;
                return rs;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            if (provider == null) {
                throw new ParameterStorageException("\u53c2\u6570\u7c7b\u578b\u3010" + storageType + "\u3011\u4e0d\u5b58\u5728");
            }
            return provider.findModels(parameterNames, ownerName, ownerType);
        }
        return null;
    }

    public ParameterModel findModel(String parameterName) throws ParameterStorageException {
        return this.findModel(new ParameterResourceIdentify(parameterName), null);
    }

    public ParameterBean getParameterBean(ParameterResourceIdentify identify, String storageType) throws ParameterStorageException {
        if (this.isStorageTypeEmpty(storageType)) {
            for (IParameterStorageProvider provider : this.providers) {
                ParameterBean bean;
                if (this.isRemoteDataSet(provider) || (bean = provider.getParameterBean(identify)) == null) continue;
                return bean;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            return provider.getParameterBean(identify);
        }
        return null;
    }

    public List<ParameterBean> getParameterBeans(String owner_name, String owner_Type, String storageType) throws ParameterStorageException {
        if (this.isStorageTypeEmpty(storageType)) {
            for (IParameterStorageProvider provider : this.providers) {
                List<ParameterBean> beans;
                if (this.isRemoteDataSet(provider) || (beans = provider.getParameterBeans(owner_name, owner_Type)) == null || beans.isEmpty()) continue;
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

    private boolean isStorageTypeEmpty(String storageType) {
        return storageType == null || storageType.length() == 0 || storageType.equals("SqlDataSet");
    }

    public List<String> getPath(ParameterResourceIdentify identify, String storageType) throws ParameterStorageException {
        if (this.isStorageTypeEmpty(storageType)) {
            for (IParameterStorageProvider provider : this.providers) {
                List<String> path;
                if (this.isRemoteDataSet(provider) || (path = provider.getPath(identify)) == null || path.isEmpty()) continue;
                return path;
            }
        } else {
            IParameterStorageProvider provider = this.getStorageProvider(storageType);
            return provider.getPath(identify);
        }
        return null;
    }
}

