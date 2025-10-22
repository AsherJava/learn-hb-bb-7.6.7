/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.logic.internal.impl.ckd;

import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.spi.ICKDRuleProvider;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;

public class DefCkdRuleProvider
implements ICKDRuleProvider {
    @Autowired
    protected INvwaSystemOptionService systemOptionService;

    @Override
    public String getRuleGroupKey(CheckDesContext context) {
        return this.systemOptionService.get("nr-audit-group", "@nr/check/explain-use-rule-group");
    }
}

