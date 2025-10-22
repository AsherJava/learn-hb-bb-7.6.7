/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 */
package com.jiuqi.nr.entity.ext.auth.basedata;

import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.util.Set;

public interface IBaseDataRelationService {
    public Set<String> getUserIds(BaseDataDTO var1);

    public Set<String> getObjectCodesByUser(BaseDataAuthFindDTO var1);

    public Set<String> getObjectCodesByCodes(BaseDataDTO var1, Set<String> var2);
}

