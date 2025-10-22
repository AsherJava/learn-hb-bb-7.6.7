/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.extend.BaseDataAuthRuleExtend
 */
package com.jiuqi.nr.entity.ext.auth.basedata;

import com.jiuqi.va.extend.BaseDataAuthRuleExtend;
import java.util.Date;
import java.util.Set;

public interface IBaseDataAuthRuleExtend
extends BaseDataAuthRuleExtend {
    public Set<String> getGrantedIdentities(String var1, String var2, String var3, Date var4, String var5);

    default public String getExtRulePrefix() {
        return "RULEX_";
    }
}

