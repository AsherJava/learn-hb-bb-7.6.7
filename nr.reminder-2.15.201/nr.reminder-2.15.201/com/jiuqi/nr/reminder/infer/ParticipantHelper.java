/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.reminder.infer;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.reminder.internal.CreateReminderCommand;
import java.util.List;

public interface ParticipantHelper {
    public List<String> collectCommitUserId(String var1, String var2, List<String> var3, List<IEntityRow> var4);

    public List<String> collectUserId(String var1, String var2, List<String> var3, List<String> var4, List<IEntityRow> var5);

    public List<String> collectCommitUserId(String var1, String var2, List<String> var3, CreateReminderCommand var4, List<IEntityRow> var5);

    public List<String> collectUserIdOnlyRole(List<String> var1);
}

