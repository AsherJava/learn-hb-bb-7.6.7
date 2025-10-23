/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import java.util.List;

public interface ReceiverItem {
    public String getStrategy();

    public List<String> getUsers();

    public List<String> getRoles();
}

