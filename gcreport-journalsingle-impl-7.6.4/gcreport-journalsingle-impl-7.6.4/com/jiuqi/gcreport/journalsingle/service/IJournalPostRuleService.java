/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 */
package com.jiuqi.gcreport.journalsingle.service;

import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import java.util.List;

public interface IJournalPostRuleService {
    public List<String> getSubjectCodesByZbId(JournalDetailCondition var1);
}

