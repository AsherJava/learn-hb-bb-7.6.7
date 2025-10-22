/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.authz.dao;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.authz.IUserEntity;
import com.jiuqi.nr.authz.UserQueryParam;
import java.util.List;

public interface IUserQueryDao<T extends IUserEntity> {
    public List<T> queryUser(UserQueryParam var1) throws JQException;

    public long queryUserCount(UserQueryParam var1);

    public List<T> queryUserByIds(String ... var1);
}

