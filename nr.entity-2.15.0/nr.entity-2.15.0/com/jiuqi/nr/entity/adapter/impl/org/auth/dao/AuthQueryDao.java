/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 */
package com.jiuqi.nr.entity.adapter.impl.org.auth.dao;

import com.jiuqi.nr.entity.adapter.impl.org.auth.AuthItem;
import com.jiuqi.nr.entity.adapter.impl.org.auth.Authority;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;

public interface AuthQueryDao {
    public Set<String> queryUserByOrgCode(String var1);

    public List<OrgAuthDO> queryRule(String var1);

    public Set<String> queryUserByOrgCodesInRole(String var1, Set<String> var2);

    public EnumMap<Authority, List<String>> queryDetail(String var1, String var2, String var3);

    public Set<String> queryRoleDetailAuth(String var1, String var2, String var3);

    public List<AuthItem> listAuthItem(OrgDataOption.AuthType var1, String var2, String var3);
}

