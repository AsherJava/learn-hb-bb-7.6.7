/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.integritycheck.errdescheck.impl;

import com.jiuqi.nr.integritycheck.errdescheck.IErrDesCheckService;
import com.jiuqi.nr.integritycheck.errdescheck.param.GetRuleGroupContext;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultErrDesCheckServiceImpl
implements IErrDesCheckService {
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    @Override
    public String getRuleGroup(GetRuleGroupContext context) {
        return this.nvwaSystemOptionService.findValueById("@nr/check/explain-use-rule-group");
    }
}

