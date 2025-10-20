/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.EnumLinkageSettingDefine;
import java.util.Date;
import java.util.List;

public interface DesignEnumLinkageSettingDefine
extends EnumLinkageSettingDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setMasterLink(String var1);

    public void setSlaveLinks(List<String> var1);

    public void setShowLinks(List<String> var1);
}

