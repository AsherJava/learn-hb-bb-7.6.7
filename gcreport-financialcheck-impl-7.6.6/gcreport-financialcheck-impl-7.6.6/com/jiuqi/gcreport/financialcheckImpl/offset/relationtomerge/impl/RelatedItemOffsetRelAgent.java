/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatedItemOffsetRelAgent {
    public static Map<String, RelatedItemGcOffsetRelDTO> listRelatedItemId2DTOMap(FinancialCheckRuleExecutorImpl.OffsetResult offsetResult) {
        List<GcFcRuleUnOffsetDataDTO> unOffsetDataS = offsetResult.getOffsetedInputItems();
        if (CollectionUtils.isEmpty(unOffsetDataS)) {
            return Collections.EMPTY_MAP;
        }
        HashMap<String, RelatedItemGcOffsetRelDTO> relatedItemId2DTOMap = new HashMap<String, RelatedItemGcOffsetRelDTO>();
        unOffsetDataS.forEach(item -> {
            List vchrOffsetrRls = (List)item.getFieldValue("VCHROFFSETRELS");
            vchrOffsetrRls.forEach(vchrOffsetRel -> relatedItemId2DTOMap.put(vchrOffsetRel.getRelatedItemId(), (RelatedItemGcOffsetRelDTO)((Object)vchrOffsetRel)));
        });
        return relatedItemId2DTOMap;
    }

    public static List<GcOffsetRelatedItemEO> decorateOffsetGroupId(FinancialCheckRuleExecutorImpl.OffsetResult offsetResult) {
        List<GcFcRuleUnOffsetDataDTO> unOffsetDataS = offsetResult.getOffsetedInputItems();
        ArrayList<GcOffsetRelatedItemEO> vchrOffsetRelEOS = new ArrayList<GcOffsetRelatedItemEO>();
        unOffsetDataS.forEach(item -> {
            List vchrOffsetrRls = (List)item.getFieldValue("VCHROFFSETRELS");
            vchrOffsetrRls.forEach(vchrOffsetRel -> vchrOffsetRel.setOffsetGroupId(item.getOffsetGroupId()));
            vchrOffsetRelEOS.addAll(vchrOffsetrRls);
        });
        return vchrOffsetRelEOS;
    }
}

