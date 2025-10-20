/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.common.resource.authority;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.resource.authority.RoleCheckType;
import java.util.List;

public interface IAuthMgr {
    public void checkPermissionUserIds(List<String> var1, RoleCheckType var2) throws JQException;
}

