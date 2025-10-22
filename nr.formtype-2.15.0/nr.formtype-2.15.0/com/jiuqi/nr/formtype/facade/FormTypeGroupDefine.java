/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.facade;

import java.io.Serializable;
import java.util.Date;

public interface FormTypeGroupDefine
extends Serializable {
    public String getId();

    public void setId(String var1);

    public String getCode();

    public void setCode(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getGroupId();

    public void setGroupId(String var1);

    public String getDesc();

    public void setDesc(String var1);

    public String getOrder();

    public void setOrder(String var1);

    public Date getUpdateTime();

    public void setUpdateTime(Date var1);
}

