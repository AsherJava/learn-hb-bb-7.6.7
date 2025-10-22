/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth;

import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataUserRelationService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.Collections;
import java.util.Set;

@Deprecated
public abstract class AbstractBaseDataUserRelationService
implements IBaseDataUserRelationService {
    public abstract String getUserField(String var1);

    @Override
    public Set<String> getUserIds(BaseDataDO baseDataDO) {
        String userField = this.getUserField(baseDataDO.getTableName());
        if (userField == null) {
            return Collections.emptySet();
        }
        Object o = baseDataDO.get((Object)userField);
        if (o == null) {
            return Collections.emptySet();
        }
        return Collections.singleton(String.valueOf(o));
    }
}

