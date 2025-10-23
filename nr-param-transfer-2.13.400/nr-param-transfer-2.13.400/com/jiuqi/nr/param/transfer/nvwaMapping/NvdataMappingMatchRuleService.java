/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean
 *  com.jiuqi.nr.mapping2.util.NvMappingMatchRule
 */
package com.jiuqi.nr.param.transfer.nvwaMapping;

import com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean;
import com.jiuqi.nr.mapping2.util.NvMappingMatchRule;
import com.jiuqi.nr.param.transfer.nvwaMapping.NvdataMappingMatchContext;
import java.util.List;
import java.util.Map;

public interface NvdataMappingMatchRuleService {
    public NvMappingMatchRule getNvMappingMatchRule();

    public void saveMappingMatchRule(Map<String, List<TransferResourceDataBean>> var1, NvdataMappingMatchContext var2) throws Exception;
}

