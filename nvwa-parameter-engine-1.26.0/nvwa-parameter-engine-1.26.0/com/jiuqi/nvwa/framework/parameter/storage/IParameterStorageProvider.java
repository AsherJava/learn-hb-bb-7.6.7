/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.storage;

import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterBean;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterFolderBean;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import java.util.List;

public interface IParameterStorageProvider {
    public String getStorageType();

    public String getStorageTypeTitle();

    public List<ParameterFolderBean> getFolder(String var1) throws ParameterStorageException;

    public ParameterFolderBean getFolderBean(String var1) throws ParameterStorageException;

    public List<ParameterBean> getParameterBeans(String var1) throws ParameterStorageException;

    public List<String> getPath(ParameterResourceIdentify var1) throws ParameterStorageException;

    public ParameterBean getParameterBean(ParameterResourceIdentify var1) throws ParameterStorageException;

    public List<ParameterBean> getParameterBeans(String var1, String var2) throws ParameterStorageException;

    public List<ParameterBean> search(String var1, String var2, String var3) throws ParameterStorageException;

    public ParameterModel findModel(ParameterResourceIdentify var1) throws ParameterStorageException;

    public List<ParameterModel> findModels(List<String> var1, String var2, String var3) throws ParameterStorageException;
}

