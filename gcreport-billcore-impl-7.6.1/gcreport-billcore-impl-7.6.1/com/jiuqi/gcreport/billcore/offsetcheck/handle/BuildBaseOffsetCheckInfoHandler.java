/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.billcore.offsetcheck.handle;

import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import java.util.Map;

public interface BuildBaseOffsetCheckInfoHandler {
    public String getTitle();

    public List<BillOffsetCheckInfoDTO> buildBaseOffsetCheckInfo(AbstractUnionRule var1, Map<String, ConsolidatedSubjectEO> var2, Map<String, Map<String, Object>> var3, Map<String, GcOffSetVchrItemDTO> var4);

    public List<BillOffsetCheckInfoDTO> buildBaseOffsetCheckInfo(AbstractUnionRule var1, List<Map<String, Object>> var2, Map<String, ConsolidatedSubjectEO> var3);

    public boolean isMatch(String var1);
}

