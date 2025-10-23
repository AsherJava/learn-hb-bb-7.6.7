/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.facade;

import java.io.Serializable;
import java.util.Date;

public interface SubDataBase
extends Serializable {
    public void setTitle(String var1);

    public String getTitle();

    public void setCode(String var1);

    public String getCode();

    public void setDataScheme(String var1);

    public String getDataScheme();

    public Date getCreateTime();

    public String getOrgCateGoryName();

    public void serOrgCateGoryName(String var1);

    public void setDefaultDBOrgCateGoryName(String var1);

    public String getDefaultDBOrgCateGoryName();

    public Boolean getDSDeployStatus();

    public void setDSDeployStatus(Boolean var1);

    public Date getSDSyncTime();

    public void setSDSyncTime(Date var1);

    public Integer getSyncOrder();

    public void setSyncOrder(Integer var1);
}

