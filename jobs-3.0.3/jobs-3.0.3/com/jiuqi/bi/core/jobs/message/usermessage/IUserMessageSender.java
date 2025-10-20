/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.message.usermessage;

import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import java.util.List;

public interface IUserMessageSender {
    public void send(String var1, String var2, String var3, String var4, String var5, List<JobExecResultBean> var6) throws Exception;
}

