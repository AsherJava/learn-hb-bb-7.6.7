/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.join.api.common;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.join.api.common.JoinCacheUtil;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ListenerChangeStatus;
import com.jiuqi.va.join.api.domain.ListenerStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;

public interface JoinTemplate {
    public R send(String var1, String var2);

    public ReplyTo sendAndReceive(String var1, String var2);

    public R addDeclare(JoinDeclare var1);

    default public R addDeclareIfAbsent(JoinDeclare declare) {
        JoinDeclare oldData = JoinCacheUtil.getDelcare(declare.getName());
        if (oldData == null) {
            return this.addDeclare(declare);
        }
        return R.ok();
    }

    public R addListener(JoinListener var1);

    public R startListener(String var1);

    public R stopListener(String var1);

    default public R startAllListener(String joinName) {
        return this.changeAllListener(joinName, ListenerStatus.START);
    }

    default public R stopAllListener(String joinName) {
        return this.changeAllListener(joinName, ListenerStatus.STOP);
    }

    default public R changeAllListener(String joinName, ListenerStatus status) {
        try {
            ListenerChangeStatus changeStatus = new ListenerChangeStatus(joinName, status);
            this.send("CHANGE_JOIN_LISTENER_STATUS", JSONUtil.toJSONString((Object)changeStatus));
        }
        catch (Throwable e) {
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    default public boolean isRunning() {
        return true;
    }
}

