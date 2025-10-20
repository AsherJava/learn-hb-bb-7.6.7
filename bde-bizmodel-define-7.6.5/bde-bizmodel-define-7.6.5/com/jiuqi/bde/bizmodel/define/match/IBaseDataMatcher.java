/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.define.match;

import com.jiuqi.bde.bizmodel.define.match.BaseDataMatchCondi;
import com.jiuqi.bde.bizmodel.define.match.FilterRule;
import java.util.Map;

public interface IBaseDataMatcher {
    public String getMatchCode();

    public boolean match(BaseDataMatchCondi var1);

    public String getMatchSql(BaseDataMatchCondi var1, String var2);

    public String getNoMatchSql(BaseDataMatchCondi var1, String var2);

    default public boolean match(BaseDataMatchCondi condi, Map<FilterRule, Map<String, Boolean>> memories) {
        return this.match(condi);
    }
}

