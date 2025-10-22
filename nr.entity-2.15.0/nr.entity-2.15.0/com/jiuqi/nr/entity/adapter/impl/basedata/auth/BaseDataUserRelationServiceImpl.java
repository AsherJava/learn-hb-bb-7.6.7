/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.AbstractBaseDataUserRelationService;

@Deprecated
public class BaseDataUserRelationServiceImpl
extends AbstractBaseDataUserRelationService {
    @Override
    public String getUserField(String tableName) {
        return "informant_user";
    }
}

