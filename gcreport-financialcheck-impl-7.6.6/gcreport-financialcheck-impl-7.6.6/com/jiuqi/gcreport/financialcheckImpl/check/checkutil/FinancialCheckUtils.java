/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheck;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProvider;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProviderImpl;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FinancialCheckUtils {
    public static CheckResult autoCheck(FinancialCheckDataProvider provider) {
        FinancialCheckDataProviderImpl providerImpl = (FinancialCheckDataProviderImpl)provider;
        List<GcRelatedItemEO> voucherItems = providerImpl.getVoucherItems();
        providerImpl.setVoucherItems(voucherItems);
        FinancialCheck check = FinancialCheck.newRealtimeInstance();
        MatchingResult matchingResult = check.doAutoCheck(providerImpl);
        if (Objects.nonNull(providerImpl.getCheckProgress())) {
            providerImpl.getCheckProgress().setProgressValueAndRefresh(0.95);
        }
        return FinancialCheckUtils.getCheckResult(matchingResult);
    }

    private static CheckResult getCheckResult(MatchingResult matchingResult) {
        CheckResult result = new CheckResult();
        ArrayList checkedIds = new ArrayList();
        int checkedGroupCount = matchingResult.getMatchingVchrs() == null ? 0 : matchingResult.getMatchingVchrs().size();
        int checkedItemCount = 0;
        if (!CollectionUtils.isEmpty((Collection)matchingResult.getMatchingVchrs())) {
            for (MatchingVchr matchingVchr : matchingResult.getMatchingVchrs()) {
                List vchrItems = matchingVchr.getVchrItems();
                checkedItemCount += vchrItems.size();
                checkedIds.addAll(vchrItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
            }
        }
        result.setCheckedGroupCount(checkedGroupCount);
        result.setCheckedItemCount(checkedItemCount);
        result.setCheckedItemIds(checkedIds);
        return result;
    }

    public static String inspectCheck(FinancialCheckDataProvider provider) {
        FinancialCheck check = FinancialCheck.newRealtimeInstance();
        return null;
    }
}

