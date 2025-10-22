/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.user.UserDO
 */
package com.jiuqi.nr.entity.ext.auth.basedata;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.user.UserDO;
import java.util.Set;

@Deprecated
public interface IBaseDataUserRelationService {
    public Set<String> getUserIds(BaseDataDO var1);

    default public Set<String> getObjectCodes(UserDO user, String tableName) {
        return null;
    }
}

