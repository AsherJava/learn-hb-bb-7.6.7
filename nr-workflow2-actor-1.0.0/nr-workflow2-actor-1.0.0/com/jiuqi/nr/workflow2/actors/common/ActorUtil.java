/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 */
package com.jiuqi.nr.workflow2.actors.common;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;

public class ActorUtil {
    public static boolean actorIsFromContext(IActor actor) {
        IActor actorFromContext = IActor.fromContext();
        return actorFromContext.getUserId().equals(actor.getUserId()) && actorFromContext.getIdentityId().equals(actor.getIdentityId());
    }

    public static void assertActorIsFromContext(IActor actor) {
        if (!ActorUtil.actorIsFromContext(actor)) {
            throw new ProcessRuntimeException(null, "\u5f53\u524d\u53c2\u4e0e\u8005\u7b56\u7565\u4ec5\u9002\u7528\u4e8e\u5bf9\u5f53\u524d\u4e0a\u4e0b\u6587\u7528\u6237\u7684\u5224\u65ad\uff0c\u4e0d\u652f\u6301\u5bf9\u4efb\u610f\u53c2\u4e0e\u8005\u7684\u5224\u65ad\u3002");
        }
    }

    public static String getOrgnizationCodeFromContxt() {
        NpContext ctx = NpContextHolder.getContext();
        if (ctx.getIdentity() != null) {
            return ctx.getIdentity().getOrgCode();
        }
        return null;
    }
}

