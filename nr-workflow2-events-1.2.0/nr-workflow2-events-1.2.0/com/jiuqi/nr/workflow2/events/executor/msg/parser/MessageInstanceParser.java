/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfo;
import java.util.Map;

public interface MessageInstanceParser {
    public MessageInfo parseToMessageInfo(String var1, Map<String, String> var2);
}

