/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.facade;

import com.jiuqi.nr.configuration.common.ConfigContentType;

public interface BusinessConfigurationDefine {
    public String getKey();

    public void setKey(String var1);

    public String getTaskKey();

    public void setTaskKey(String var1);

    public String getFormSchemeKey();

    public void setFormSchemeKey(String var1);

    public String getCode();

    public void setCode(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getDescription();

    public void setDescription(String var1);

    public String getCategory();

    public void setCategory(String var1);

    public ConfigContentType getContentType();

    public void setContentType(ConfigContentType var1);

    public String getConfigurationContent();

    public void setConfigurationContent(String var1);

    public String getFileName();

    public void setFileName(String var1);
}

