/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.bpm.Actor;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.exception.BpmException;
import org.springframework.util.Assert;

public final class Actor {
    private final String userId;
    private final String identityId;

    public static Actor fromNpContext() {
        NpContext context = NpContextHolder.getContext();
        String userId = context.getUserId();
        if (userId == null) {
            throw new BpmException("current user not found.");
        }
        return new Actor(userId, context.getIdentityId());
    }

    public Actor(String userId, String identityId) {
        Assert.notNull((Object)userId, "parameter 'userId' must not be null.");
        this.userId = userId;
        this.identityId = identityId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getIdentityId() {
        return this.identityId;
    }
}

