/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 */
package com.jiuqi.nr.common.resource;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.nr.common.resource.NrResource;

public interface NrResourceGroup
extends Identifiable,
NrResource {
    public boolean isAuthorisable();
}

