/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.billcode.BillCodeRuleDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.billcode.service;

import com.jiuqi.va.domain.billcode.BillCodeRuleDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public interface IBillCodeRuleService {
    public BillCodeRuleDTO getRuleByUniqueCode(String var1) throws Exception;

    public BillCodeRuleDTO addRule(BillCodeRuleDTO var1) throws Exception;

    public BillCodeRuleDTO editRule(BillCodeRuleDTO var1) throws Exception;

    public PageVO<MetaTreeInfoDTO> gatherMetaTree(TenantDO var1);

    public boolean hasRuleByConstant(String var1);

    public List<BillCodeRuleDTO> getRuleAll() throws Exception;

    public String getUniqueCodeByBillCode(String var1) throws Exception;

    public BillCodeRuleDTO getRuleByUniqueCodeUnCheck(String var1, boolean var2) throws Exception;
}

