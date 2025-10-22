/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.ext.dwdm;

import com.jiuqi.nr.entity.ext.dwdm.DWDMOptionQuery;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractDWDMMenu
extends DWDMOptionQuery {
    public Set<String> getApplyTo() {
        if (this.enableOption()) {
            return null;
        }
        HashSet<String> org = new HashSet<String>(1);
        org.add(UUID.randomUUID().toString());
        return org;
    }
}

