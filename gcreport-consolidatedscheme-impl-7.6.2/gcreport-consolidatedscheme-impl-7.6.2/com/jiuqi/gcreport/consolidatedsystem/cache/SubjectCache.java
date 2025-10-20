/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.cache;

import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface SubjectCache {
    public void clearCache(String var1);

    public void clearCache();

    public List<ConsolidatedSubjectEO> listSubjectsBySystemId(@NotNull String var1);

    public ConsolidatedSubjectEO getSubjectByCode(String var1, String var2);
}

