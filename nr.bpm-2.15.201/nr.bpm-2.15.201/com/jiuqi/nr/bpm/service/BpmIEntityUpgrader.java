/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 */
package com.jiuqi.nr.bpm.service;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.definition.common.IEntityUpgrader;

public interface BpmIEntityUpgrader
extends IEntityUpgrader {
    public String queryParentEntityKey(String var1, EntityViewDefine var2, BusinessKeyInfo var3);

    public String[] queryParentEntityKeys(String var1, EntityViewDefine var2, BusinessKeyInfo var3);

    public String queryEntityView(BusinessKeyInfo var1);

    public String queryEntityView(BusinessKey var1);
}

