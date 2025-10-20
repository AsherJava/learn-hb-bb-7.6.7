/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.storage;

import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.storage.ParameterBean;
import com.jiuqi.bi.parameter.storage.ParameterFolderBean;
import com.jiuqi.bi.parameter.storage.ParameterStorageException;
import java.util.List;

public interface IParameterStorageProvider {
    public String getStorageType();

    public String getStorageTypeTitle();

    public ParameterFolderBean[] getFolder(String var1) throws ParameterStorageException;

    public ParameterBean[] getParameterBeans(String var1) throws ParameterStorageException;

    public String[] getPath(String var1, String var2, String var3) throws ParameterStorageException;

    public ParameterBean getParameterBean(String var1, String var2, String var3) throws ParameterStorageException;

    public ParameterBean[] getParameterBeans(String var1, String var2) throws ParameterStorageException;

    public List<ParameterBean> searchParameterBeans(String var1, String var2, String var3) throws ParameterStorageException;

    public ParameterModel findModel(String var1, String var2, String var3) throws ParameterStorageException;

    public ParameterModel[] findModels(String[] var1, String var2, String var3) throws ParameterStorageException;
}

