/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.authz.dao;

import com.jiuqi.np.user.User;
import com.jiuqi.nr.authz.IUserEntity;
import com.jiuqi.nr.authz.bean.UserEntity;
import com.jiuqi.nr.authz.dao.BaseUserQueryDaoImpl;
import com.jiuqi.nr.authz.dao.IUserQueryDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository(value="com.jiuqi.nr.authz.common.dao.UserEntityDao")
public class UserEntityDao
extends BaseUserQueryDaoImpl<IUserEntity>
implements IUserQueryDao<IUserEntity> {
    @Override
    protected RowMapper<IUserEntity> getRowMapper() {
        return (rs, rowNum) -> new UserEntity(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
    }

    @Override
    protected String[] getColumn() {
        return new String[]{"id", "name", "nickname", "org_code", "create_time"};
    }

    @Override
    protected IUserEntity buildEntity(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getNickname(), user.getOrgCode());
    }
}

