/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.thread.annotation.ThreadCacheable
 */
package com.jiuqi.budget.components;

import com.jiuqi.budget.autoconfigure.DimensionConst;
import com.jiuqi.budget.thread.annotation.ThreadCacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service(value="BudDimJudgeService")
@Component
public class DimJudgeService {
    @ThreadCacheable
    public boolean isOrg(String str) {
        return DimensionConst.isOrg(str);
    }
}

