/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.configuration.facade;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.UUID;

public interface ProfileDefine {
    public UUID getId();

    public void setId(UUID var1);

    public UUID getTaskKey();

    public void setTaskKey(UUID var1);

    public UUID getFormSchemeKey();

    public void setFormSchemeKey(UUID var1);

    public UUID getUserKey();

    public void setUserKey(UUID var1);

    public DimensionValueSet getEntityKey();

    public void setEntityKey(DimensionValueSet var1);

    public UUID getFormKey();

    public void setFormKey(UUID var1);

    public String getCode();

    public void setCode(String var1);

    public String getFileName();

    public void setFileName(String var1);

    public String getProfileContent();

    public void setProfileContent(String var1);
}

