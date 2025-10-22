/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.event.GcCalcPrepareDatasEvent
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.inputdata.flexible.listener;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.event.GcCalcPrepareDatasEvent;
import com.jiuqi.gcreport.inputdata.flexible.service.GcCalcPrepareDataService;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GcCalcPrepareDatasEventListener
implements ApplicationListener<GcCalcPrepareDatasEvent> {
    @Autowired
    private GcCalcPrepareDataService prepareDataService;

    @Override
    public void onApplicationEvent(GcCalcPrepareDatasEvent event) {
        List rules = event.getRules();
        GcCalcEnvContext env = event.getEnv();
        boolean needPrepareDatas = false;
        for (AbstractUnionRule rule : rules) {
            if (!RuleTypeEnum.FLEXIBLE.getCode().equals(rule.getRuleType())) continue;
            needPrepareDatas = true;
            break;
        }
        if (needPrepareDatas) {
            this.prepareDataService.prepareDatas(env, rules);
        }
    }
}

