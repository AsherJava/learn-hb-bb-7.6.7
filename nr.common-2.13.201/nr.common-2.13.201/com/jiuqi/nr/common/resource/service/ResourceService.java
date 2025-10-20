/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.service;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.NrResourceCategory;
import com.jiuqi.nr.common.resource.bean.AuthzAction;
import java.util.List;

public interface ResourceService {
    public NrResourceCategory getResourceCategory(String var1);

    public List<NrResource> getRootResources(String var1, String var2, Object var3);

    public List<NrResource> getRootResources(String var1, String var2, int var3);

    public List<NrResource> getChildResources(String var1, String var2, String var3);

    public List<NrResource> getChildResources(String var1, String var2, String var3, Object var4);

    public List<Authority> linkage(String var1, AuthzAction var2);

    public List<NrResource> getResourcesAuthority(String var1, List<NrResource> var2, String var3, Object var4);

    public List<NrResource> getResourcesAuthorityById(String var1, List<String> var2, String var3, Boolean var4);
}

