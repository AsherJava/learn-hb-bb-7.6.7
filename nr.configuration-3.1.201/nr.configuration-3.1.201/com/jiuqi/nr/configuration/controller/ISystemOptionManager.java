/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.controller;

import com.jiuqi.nr.configuration.facade.SystemOptionBase;
import com.jiuqi.nr.configuration.facade.SystemOptionDefine;
import java.util.HashMap;
import java.util.List;

@Deprecated
public interface ISystemOptionManager {
    public void registerSystemOption(SystemOptionBase var1);

    public Object getObject(String var1);

    public Object getObject(String var1, String var2);

    public Object getObjectFromDB(String var1, String var2, String var3);

    public Object getObject(String var1, String var2, String var3);

    public HashMap<String, Object> batchGetObjects(List<String> var1);

    public HashMap<String, Object> batchGetObjects(List<String> var1, String var2);

    public HashMap<String, Object> batchGetObjects(List<String> var1, String var2, String var3);

    public void setObject(String var1, Object var2);

    public void setObject(String var1, String var2, Object var3);

    public void setObject(String var1, String var2, String var3, Object var4);

    public void batchSetObjects(HashMap<String, Object> var1);

    public void setObject(HashMap<String, Object> var1, String var2);

    public void setObject(HashMap<String, Object> var1, String var2, String var3);

    public SystemOptionDefine getGlobalOptionByKey(String var1);

    public List<SystemOptionDefine> getGlobalOptions();

    public List<SystemOptionDefine> getOptionsByTask(String var1);

    public List<SystemOptionDefine> getOptionsByFormScheme(String var1, String var2);

    public List<SystemOptionDefine> getAllOptions();

    public List<SystemOptionDefine> getOptionsByGroup(String var1);

    public void deleteSystemOption(String var1);

    public void deleteSystemOption(String var1, String var2);

    public void deleteSystemOption(String var1, String var2, String var3);

    public void batchDeleteOptions(List<String> var1);

    public void batchDeleteOptions(List<String> var1, String var2);

    public void batchDeleteOptions(List<String> var1, String var2, String var3);

    public void batchResetOptions(List<String> var1);

    public List<SystemOptionDefine> getSystemOption(List<String> var1);

    public boolean adjustOptionsInheritProperty(String var1, String var2);

    public boolean adjustOptionsInheritProperty(String var1, String var2, String var3);
}

