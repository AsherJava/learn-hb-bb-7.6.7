/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.plan.service;

import com.jiuqi.nr.reminder.plan.CbPlanDTO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParticipantService {
    public Set<String> collectUserId(CbPlanDTO var1, String var2, String var3, Map<String, Set<String>> var4);

    public Set<String> collectUserId(CbPlanDTO var1, List<String> var2, String var3, String var4, Map<String, Set<String>> var5);
}

