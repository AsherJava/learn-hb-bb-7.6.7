/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.workflow2.engine.core.actor;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;

public interface IActor {
    public String getUserId();

    public String getIdentityId();

    public static IActor fromContext() {
        return new ActorFromContext();
    }

    public static class ActorFromContext
    implements IActor {
        private final NpContext ctx = NpContextHolder.getContext();

        private ActorFromContext() {
            if (this.ctx == null) {
                throw new RuntimeException("can not found current context.");
            }
        }

        @Override
        public String getUserId() {
            return this.ctx.getUserId();
        }

        @Override
        public String getIdentityId() {
            return this.ctx.getIdentityId();
        }
    }
}

