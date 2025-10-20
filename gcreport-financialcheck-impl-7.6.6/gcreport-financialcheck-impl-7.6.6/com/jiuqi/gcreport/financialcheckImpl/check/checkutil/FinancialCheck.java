/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProvider;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProviderImpl;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.OffsetVchrModeSchemeExector;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.SchemeExector;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

class FinancialCheck {
    private FinancialCheckSchemeService schemeService = (FinancialCheckSchemeService)SpringContextUtils.getBean(FinancialCheckSchemeService.class);
    private MatchingResult matchResult = new MatchingResult();
    private FinancialCheckSchemeEO currentScheme;

    static FinancialCheck newRealtimeInstance() {
        return new FinancialCheck();
    }

    private FinancialCheck() {
    }

    MatchingResult doAutoCheck(FinancialCheckDataProvider provider) {
        if (CollectionUtils.isEmpty(provider.getVoucherItems())) {
            this.matchResult.addMatchingVchrs(Collections.emptyList());
            this.matchResult.addUnmatchingVchrItems(Collections.emptyList());
            return this.matchResult;
        }
        Map<String, List<GcRelatedItemEO>> uncheckedItemGroups = provider.getVoucherItems().stream().collect(Collectors.groupingBy(GcRelatedItemEO::getCheckRuleId));
        double stepProcess = 0.7 / (double)uncheckedItemGroups.size();
        uncheckedItemGroups.forEach((schemeId, items) -> {
            FinancialCheckSchemeEO scheme = this.schemeService.queryById((String)schemeId);
            if (Objects.isNull(scheme)) {
                this.matchResult.addUnmatchingVchrItems(items);
                return;
            }
            this.currentScheme = scheme;
            FinancialCheckDataProviderImpl matchedCurSchemeDataProvider = new FinancialCheckDataProviderImpl();
            matchedCurSchemeDataProvider.setAcctYear(provider.getAcctYear());
            matchedCurSchemeDataProvider.setAcctPeriod(provider.getAcctPeriod());
            matchedCurSchemeDataProvider.setVoucherItems((List<GcRelatedItemEO>)items);
            matchedCurSchemeDataProvider.setCheckPeriodBaseVoucherPeriod(provider.getCheckPeriodBaseVoucherPeriod());
            if (CheckModeEnum.OFFSETVCHR.getCode().equals(scheme.getCheckMode())) {
                OffsetVchrModeSchemeExector schemeExector = OffsetVchrModeSchemeExector.newInstance(this.currentScheme, matchedCurSchemeDataProvider);
                MatchingResult matchingResult = schemeExector.executeAuto();
                this.matchResult.addMatchingVchrs(matchingResult.getMatchingVchrs());
                this.matchResult.addUnmatchingVchrItems(matchingResult.getUnmatchingVchrItems());
            } else {
                this.doAutoCheckBySchemeCheckMode(matchedCurSchemeDataProvider);
            }
            if (provider.getCheckProgress() != null) {
                provider.getCheckProgress().addProgressValueAndRefresh(stepProcess);
            }
        });
        return this.matchResult;
    }

    private void doAutoCheckBySchemeCheckMode(FinancialCheckDataProviderImpl provider) {
        SchemeExector writeOffCheckschemeExector = SchemeExector.newInstance(this.currentScheme, provider, CheckModeEnum.WRITEOFFCHECK);
        MatchingResult writeOffCheckMatchingResult = writeOffCheckschemeExector.executeAuto();
        this.matchResult.addMatchingVchrs(writeOffCheckMatchingResult.getMatchingVchrs());
        if (CollectionUtils.isEmpty(writeOffCheckMatchingResult.getUnmatchingVchrItems())) {
            return;
        }
        provider.setVoucherItems(writeOffCheckMatchingResult.getUnmatchingVchrItems());
        if (CheckModeEnum.BILATERAL.getCode().equals(this.currentScheme.getCheckMode())) {
            SchemeExector bilateralSchemeExector = SchemeExector.newInstance(this.currentScheme, provider, CheckModeEnum.BILATERAL);
            MatchingResult bilateralMatchingResult = bilateralSchemeExector.executeAuto();
            this.matchResult.addMatchingVchrs(bilateralMatchingResult.getMatchingVchrs());
            if (CollectionUtils.isEmpty(bilateralMatchingResult.getUnmatchingVchrItems())) {
                return;
            }
            if (this.currentScheme.getSpecialCheck() == 0) {
                this.matchResult.addUnmatchingVchrItems(bilateralMatchingResult.getUnmatchingVchrItems());
            } else {
                provider.setVoucherItems(bilateralMatchingResult.getUnmatchingVchrItems());
                SchemeExector specialSchemeExector = SchemeExector.newInstance(this.currentScheme, provider, CheckModeEnum.SPECIALCHECK);
                MatchingResult specialMatchingResult = specialSchemeExector.executeAuto();
                this.matchResult.addMatchingVchrs(specialMatchingResult.getMatchingVchrs());
                this.matchResult.addUnmatchingVchrItems(specialMatchingResult.getUnmatchingVchrItems());
            }
        } else {
            SchemeExector unilateralSchemeExector = SchemeExector.newInstance(this.currentScheme, provider, CheckModeEnum.UNILATERAL);
            MatchingResult unilateralMatchingResult = unilateralSchemeExector.executeAuto();
            this.matchResult.addMatchingVchrs(unilateralMatchingResult.getMatchingVchrs());
            this.matchResult.addUnmatchingVchrItems(unilateralMatchingResult.getUnmatchingVchrItems());
        }
    }
}

