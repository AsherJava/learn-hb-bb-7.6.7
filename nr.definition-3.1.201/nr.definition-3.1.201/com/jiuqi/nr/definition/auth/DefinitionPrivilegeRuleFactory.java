/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.extension.PrivilegeRule
 */
package com.jiuqi.nr.definition.auth;

import com.jiuqi.np.authz2.privilege.extension.PrivilegeRule;

public interface DefinitionPrivilegeRuleFactory {
    public Integer getOrder();

    public PrivilegeRule createPrivilegeRule();
}

