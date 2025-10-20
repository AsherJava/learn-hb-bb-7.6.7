/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.strategy.Strategy
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 */
package com.jiuqi.va.workflow.strategy;

import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DesignatedBusinessSubmitterStrategy
implements Strategy {
    public String getName() {
        return "designatedBusinessSubmitter";
    }

    public String getTitle() {
        return "\u6307\u5b9a\u4e1a\u52a1\u63d0\u4ea4\u4eba";
    }

    public String getOrder() {
        return "007";
    }

    public String getStrategyModule() {
        return "general";
    }

    public Set<String> execute(Object params) {
        LinkedHashSet<String> set = new LinkedHashSet<String>();
        Map paramsmap = (Map)params;
        ProcessDO processDO = (ProcessDO)paramsmap.get("processDO");
        if (processDO != null && StringUtils.hasText(processDO.getStartuser())) {
            set.add(processDO.getStartuser());
        }
        if (!CollectionUtils.isEmpty(set)) {
            return set;
        }
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            set.add(user.getId());
        }
        return set;
    }
}

