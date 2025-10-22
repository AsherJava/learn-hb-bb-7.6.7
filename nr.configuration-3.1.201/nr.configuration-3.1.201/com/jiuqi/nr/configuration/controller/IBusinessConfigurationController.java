/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.controller;

import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import java.util.List;

public interface IBusinessConfigurationController {
    public BusinessConfigurationDefine createConfiguration();

    public void addConfiguration(BusinessConfigurationDefine var1);

    public List<BusinessConfigurationDefine> getConfigurationByTaskWithoutContent(String var1);

    public List<BusinessConfigurationDefine> getConfigurationByCategoryWithoutContent(String var1, String var2);

    public BusinessConfigurationDefine getConfiguration(String var1, String var2);

    public BusinessConfigurationDefine getConfiguration(String var1, String var2, String var3);

    public String getConfigurationContent(String var1, String var2, String var3);

    public void updateConfigurationContent(String var1, String var2, String var3);

    public void updateConfiguration(BusinessConfigurationDefine var1);

    public void deleteConfiguration(String var1);

    public void deleteConfiguration(String var1, String var2);
}

