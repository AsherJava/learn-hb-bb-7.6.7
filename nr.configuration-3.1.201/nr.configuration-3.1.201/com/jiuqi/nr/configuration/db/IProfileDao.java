/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.db;

import com.jiuqi.nr.configuration.entity.ProfileEntity;
import java.util.List;

public interface IProfileDao {
    public void add(ProfileEntity var1) throws Exception;

    public void update(ProfileEntity var1) throws Exception;

    public void delete(String var1, String var2) throws Exception;

    public ProfileEntity queryUnique(String var1, String var2) throws Exception;

    public List<ProfileEntity> queryByUser(String var1) throws Exception;

    public List<ProfileEntity> queryByCode(String var1) throws Exception;
}

