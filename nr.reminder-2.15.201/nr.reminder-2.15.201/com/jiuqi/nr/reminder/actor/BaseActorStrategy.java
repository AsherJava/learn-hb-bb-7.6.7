/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 */
package com.jiuqi.nr.reminder.actor;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import java.util.Date;
import java.util.List;

public abstract class BaseActorStrategy {
    protected final IEntityAuthorityService entityAuthority;
    protected final DefinitionAuthorityProvider definitionAuthorityProvider;
    protected final EntityIdentityService entityIdentityService;

    public BaseActorStrategy(IEntityAuthorityService entityAuthority, DefinitionAuthorityProvider definitionAuthorityProvider, EntityIdentityService entityIdentityService) {
        this.entityAuthority = entityAuthority;
        this.definitionAuthorityProvider = definitionAuthorityProvider;
        this.entityIdentityService = entityIdentityService;
    }

    public abstract List<String> getActors(String var1, String var2, String var3, Date var4, Date var5, List<IEntityRow> var6);
}

