/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme
 *  nr.single.map.data.exception.SingleDataException
 */
package com.jiuqi.nr.single.extension.entitycheck;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.RelationTaskAndFormScheme;
import java.util.List;
import nr.single.map.data.exception.SingleDataException;

public interface SingleQueryEntityCheckService {
    public List<RelationTaskAndFormScheme> getRelationTaskToFromSchemes(String var1, String var2, String var3) throws SingleDataException;
}

