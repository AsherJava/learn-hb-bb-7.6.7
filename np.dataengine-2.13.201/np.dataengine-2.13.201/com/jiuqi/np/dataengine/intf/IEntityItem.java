/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import java.util.Date;

public interface IEntityItem {
    public String getRowCaption();

    public void setRowCaption(String var1);

    public String getEntityKeyData();

    public String getParentEntityKey();

    public void setParentEntityKey(String var1);

    public Object getEntityOrder();

    public void setEntityOrder(Object var1);

    public String getIconValue();

    public void setIconValue(String var1);

    public Date getVersionBeginDate();

    public Date getVersionEndDate();

    public String getTitle();

    public String getCode();

    public String getBizKey();

    public boolean isStoped();

    public String getParents();

    public void setParents(String var1);

    public String getResId();
}

