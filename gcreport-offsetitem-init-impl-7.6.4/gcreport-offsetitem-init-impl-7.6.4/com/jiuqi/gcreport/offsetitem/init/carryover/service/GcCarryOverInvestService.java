/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service;

import com.jiuqi.gcreport.offsetitem.init.carryover.enums.GcCarryOverInvestTypeEnum;
import java.util.Map;
import java.util.Set;

public interface GcCarryOverInvestService {
    public Map<String, String> getSrcId2IdMap(Set<String> var1, GcCarryOverInvestTypeEnum var2, int var3);
}

