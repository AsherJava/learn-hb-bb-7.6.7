/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.dataquery;

import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import java.util.List;

public interface GcRelatedItemOffsetDataQueryService {
    public List<GcRelatedItemEO> relationToMergeExecute(RelationToMergeArgDTO var1);

    public List<GcRelatedOffsetVoucherItemEO> queryOffsetVoucherItems(BalanceCondition var1);
}

