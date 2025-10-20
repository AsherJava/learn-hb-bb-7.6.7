/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.service;

import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDTO;
import com.jiuqi.va.biz.domain.commrule.BizCommonRuleVO;
import java.util.List;

public interface BizCommonRuleService {
    public List<Integer> checkName(BizCommonRuleVO var1);

    public String saveRule(BizCommonRuleVO var1);

    public List<BizCommonRuleDTO> list(BizCommonRuleVO var1);

    public String delete(BizCommonRuleVO var1);
}

