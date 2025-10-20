/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.EntityLinkageDefine;
import java.util.Date;

public interface DesignEntityLinkageDefine
extends EntityLinkageDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setMasterEntityKey(String var1);

    public void setSlaveEntityKey(String var1);

    public void setLinkageCondition(String var1);
}

