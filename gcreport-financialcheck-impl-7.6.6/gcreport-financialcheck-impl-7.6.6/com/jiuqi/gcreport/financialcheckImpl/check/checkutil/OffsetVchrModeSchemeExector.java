/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.AmtCheck;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProvider;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.GroupedCheckDataProvider;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

public class OffsetVchrModeSchemeExector {
    private FinancialCheckSchemeEO scheme;
    private FinancialCheckDataProvider allDataProvider;
    private MatchingResult matchResult;
    private GcRelatedItemCommandService relatedItemCommandService;
    private GcRelatedItemQueryService relatedItemQueryService;
    private AmtCheck amtCheck;
    private Map<String, GroupedCheckDataProvider> providerGroups;
    private GroupedCheckDataProvider curDataProvider;
    private List<GcRelatedItemEO> sameGcNumberCheckedItems;
    private FinancialCheckSchemeService schemeService;
    private final int bothNum = 2;

    public static OffsetVchrModeSchemeExector newInstance(FinancialCheckSchemeEO scheme, FinancialCheckDataProvider provider) {
        return new OffsetVchrModeSchemeExector(scheme, provider);
    }

    private OffsetVchrModeSchemeExector(FinancialCheckSchemeEO scheme, FinancialCheckDataProvider provider) {
        this.scheme = scheme;
        this.allDataProvider = provider;
        this.matchResult = new MatchingResult();
        this.relatedItemCommandService = (GcRelatedItemCommandService)SpringContextUtils.getBean(GcRelatedItemCommandService.class);
        this.relatedItemQueryService = (GcRelatedItemQueryService)SpringContextUtils.getBean(GcRelatedItemQueryService.class);
        this.schemeService = (FinancialCheckSchemeService)SpringContextUtils.getBean(FinancialCheckSchemeService.class);
    }

    public MatchingResult executeAuto() {
        if (CollectionUtils.isEmpty(this.allDataProvider.getVoucherItems())) {
            return this.matchResult;
        }
        this.groupData();
        this.amtCheck = new AmtCheck(this.scheme);
        this.providerGroups.forEach((key, value) -> {
            this.curDataProvider = value;
            try {
                GcRelatedItemEO firstItem = value.getVoucherItems().get(0);
                this.sameGcNumberCheckedItems = new ArrayList<GcRelatedItemEO>();
                String unitId = firstItem.getUnitId();
                String oppUnitId = firstItem.getOppUnitId();
                if (!"SystemDefault".equals(firstItem.getGcNumber())) {
                    List sameGcNumberItems = this.relatedItemQueryService.queryByGcNumberAndUnit(firstItem.getGcNumber(), unitId, oppUnitId, firstItem.getAcctYear());
                    Set ids = this.curDataProvider.getVoucherItems().stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
                    HashSet<String> distinctCurr = new HashSet<String>();
                    HashSet<String> distinctCheckRuleId = new HashSet<String>();
                    HashSet<String> sameGcNumberUnCheckIds = new HashSet<String>();
                    for (GcRelatedItemEO item : sameGcNumberItems) {
                        distinctCurr.add(item.getOriginalCurr());
                        if (!StringUtils.isEmpty((String)item.getCheckRuleId())) {
                            distinctCheckRuleId.add(item.getCheckRuleId());
                        }
                        if (StringUtils.isEmpty((String)item.getCheckId())) {
                            sameGcNumberUnCheckIds.add(item.getId());
                            continue;
                        }
                        this.sameGcNumberCheckedItems.add(item);
                    }
                    if (!ids.equals(sameGcNumberUnCheckIds)) {
                        throw new BusinessRuntimeException("\u5bf9\u8d26\u5931\u8d25, \u540c\u4e00\u534f\u540c\u7801\u7684\u6570\u636e\u672a\u5168\u90e8\u53c2\u4e0e\u5bf9\u8d26");
                    }
                    if (distinctCurr.size() > 1) {
                        throw new BusinessRuntimeException("\u5bf9\u8d26\u5931\u8d25, \u540c\u4e00\u534f\u540c\u7801\u7684\u6570\u636e\u7684\u6e90\u5e01\u5e01\u79cd\u4e0d\u80fd\u4e0d\u4e00\u81f4");
                    }
                    if (distinctCheckRuleId.size() > 1) {
                        throw new BusinessRuntimeException("\u5bf9\u8d26\u5931\u8d25, \u540c\u4e00\u534f\u540c\u7801\u7684\u6570\u636e\u7684\u5bf9\u8d26\u65b9\u6848\u4e0d\u80fd\u4e0d\u4e00\u81f4");
                    }
                    if (!CollectionUtils.isEmpty(this.sameGcNumberCheckedItems) && this.sameGcNumberCheckedItems.stream().map(GcRelatedItemEO::getCheckId).distinct().count() > 1L) {
                        throw new BusinessRuntimeException("\u5bf9\u8d26\u5931\u8d25, \u540c\u4e00\u534f\u540c\u7801\u5b58\u5728\u591a\u7ec4\u5df2\u5bf9\u8d26\u6570\u636e");
                    }
                }
                ArrayList<GcRelatedItemEO> allItemInGroup = new ArrayList<GcRelatedItemEO>(value.getVoucherItems());
                if (!CollectionUtils.isEmpty(this.sameGcNumberCheckedItems)) {
                    allItemInGroup.addAll(this.sameGcNumberCheckedItems);
                }
                Integer maxDataPeriod = allItemInGroup.stream().map(GcRelatedItemEO::getAcctPeriod).max(Integer::compareTo).get();
                int unitOpenAccountPeriod = UnitStateUtils.getUnitOpenAccountPeriod(unitId, oppUnitId, this.curDataProvider.getAcctYear(), maxDataPeriod, this.curDataProvider.getAcctPeriod());
                if (unitOpenAccountPeriod == -1) {
                    throw new BusinessRuntimeException(String.format("\u5355\u4f4d{}\u4e0e{\u7684\u5171\u540c\u4e0a\u7ea7\u5728 {}\u5e74 {}\u6708-{}\u6708\u5df2\u5173\u8d26", unitId, oppUnitId, this.curDataProvider.getAcctYear(), maxDataPeriod, this.curDataProvider.getAcctPeriod()));
                }
                this.curDataProvider.setAcctPeriod(unitOpenAccountPeriod);
                this.doCheck();
            }
            catch (BusinessRuntimeException e) {
                this.matchResult.addUnmatchingVchrItems(this.curDataProvider.getVoucherItems());
            }
        });
        return this.matchResult;
    }

    private void doCheck() {
        this.doBilateralCheck();
        if (!CollectionUtils.isEmpty(this.curDataProvider.getVoucherItems())) {
            FinancialCheckSchemeVO schemeVO = this.schemeService.queryCheckScheme(this.scheme.getId());
            String unilateralCondition = schemeVO.getUnilateralCondition();
            if (!StringUtils.isEmpty((String)unilateralCondition) && this.curDataProvider.getVoucherItems().stream().anyMatch(item -> !this.schemeService.filterBySchemeCondition(unilateralCondition, (GcRelatedItemEO)item))) {
                return;
            }
            List unilateralSubSettings = schemeVO.getUnilateralSubSettings();
            if (CollectionUtils.isEmpty(unilateralSubSettings)) {
                return;
            }
            HashSet allUnilateralSubjects = new HashSet();
            unilateralSubSettings.forEach(x -> {
                if (!CollectionUtils.isEmpty(x.getSubjects())) {
                    allUnilateralSubjects.addAll(x.getSubjects());
                }
            });
            if (CollectionUtils.isEmpty(allUnilateralSubjects)) {
                return;
            }
            Set allChildrenContainSelfByCodes = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", allUnilateralSubjects);
            List items = this.curDataProvider.getVoucherItems().stream().filter(item -> allChildrenContainSelfByCodes.contains(item.getSubjectCode())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(items)) {
                return;
            }
            this.doUnilateralCheck();
        }
    }

    private void doBilateralCheck() {
        List<GcRelatedItemEO> uncheckItems = this.curDataProvider.getVoucherItems();
        if (CollectionUtils.isEmpty(uncheckItems)) {
            return;
        }
        if (!CollectionUtils.isEmpty(this.sameGcNumberCheckedItems) ? Stream.concat(this.sameGcNumberCheckedItems.stream(), uncheckItems.stream()).map(GcRelatedItemEO::getUnitId).distinct().count() < 2L : this.curDataProvider.getVoucherItemGroupByLocalOrg().size() < 2) {
            return;
        }
        this.doManyCheck(new ArrayList<GcRelatedItemEO>(uncheckItems));
    }

    private void groupData() {
        this.providerGroups = new HashMap<String, GroupedCheckDataProvider>(16);
        this.allDataProvider.getVoucherItems().forEach(voucherItem -> {
            String groupKey = this.getGroupKey((GcRelatedItemEO)voucherItem);
            GroupedCheckDataProvider belongToProvider = this.providerGroups.computeIfAbsent(groupKey, key -> GroupedCheckDataProvider.iniByOther(this.allDataProvider));
            belongToProvider.addVoucherItem((GcRelatedItemEO)voucherItem);
        });
    }

    private String getGroupKey(GcRelatedItemEO voucherItem) {
        StringBuilder builder = new StringBuilder();
        if (voucherItem.getUnitId().compareTo(voucherItem.getOppUnitId()) <= 0) {
            builder.append(voucherItem.getUnitId()).append(voucherItem.getOppUnitId());
        } else {
            builder.append(voucherItem.getOppUnitId()).append(voucherItem.getUnitId());
        }
        builder.append(voucherItem.getGcNumber());
        return DigestUtils.md5DigestAsHex(builder.toString().getBytes(Charset.defaultCharset()));
    }

    private void doManyCheck(List<GcRelatedItemEO> records) {
        if (this.amtCheck.checkAndDistributionAmt(records)) {
            try {
                MatchingVchr checkedVchr = new MatchingVchr(this.scheme, new ArrayList<GcRelatedItemEO>(records));
                this.setAndUpdateCheckInfo(checkedVchr);
                records.forEach(record -> this.curDataProvider.removeVoucherItem((GcRelatedItemEO)record));
                this.matchResult.getMatchingVchrs().add(checkedVchr);
                records.clear();
            }
            catch (BusinessRuntimeException businessRuntimeException) {
                // empty catch block
            }
        }
    }

    private void doUnilateralCheck() {
        List<GcRelatedItemEO> uncheckedData = this.curDataProvider.getVoucherItems();
        for (GcRelatedItemEO record : uncheckedData) {
            record.setChkCurr(record.getOriginalCurr());
            if (record.getAmtOrient() == 1) {
                record.setChkAmtD(Double.valueOf(record.getAmountAsDebit().doubleValue()));
                record.setChkAmtC(Double.valueOf(0.0));
                continue;
            }
            record.setChkAmtD(Double.valueOf(0.0));
            record.setChkAmtC(Double.valueOf(record.getAmountAsDebit().negate().doubleValue()));
        }
        try {
            MatchingVchr checkedVchr = new MatchingVchr(this.scheme, uncheckedData);
            this.setAndUpdateCheckInfo(checkedVchr);
            this.matchResult.getMatchingVchrs().add(checkedVchr);
        }
        catch (BusinessRuntimeException e) {
            this.matchResult.addUnmatchingVchrItems(uncheckedData);
        }
    }

    private void setAndUpdateCheckInfo(MatchingVchr matchingVchr) {
        String checkId = UUIDOrderUtils.newUUIDStr();
        if (!CollectionUtils.isEmpty(this.sameGcNumberCheckedItems)) {
            checkId = this.sameGcNumberCheckedItems.get(0).getCheckId();
        }
        this.setCheckInfo(matchingVchr.getVchrItems(), checkId);
        if (CollectionUtils.isEmpty(matchingVchr.getVchrItems())) {
            return;
        }
        this.relatedItemCommandService.doCheck(matchingVchr.getVchrItems(), true);
    }

    private void setCheckInfo(List<GcRelatedItemEO> voucherItems, String checkId) {
        if (CollectionUtils.isEmpty(voucherItems)) {
            return;
        }
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String loginUserName = loginUser == null ? "system" : loginUser.getName();
        Date checkTime = Date.from(Instant.now());
        for (GcRelatedItemEO record : voucherItems) {
            record.setChkCurr(record.getOriginalCurr());
            record.setChkState(CheckStateEnum.CHECKED.name());
            record.setCheckId(checkId);
            record.setCheckTime(checkTime);
            record.setCheckYear(Integer.valueOf(this.curDataProvider.getAcctYear()));
            record.setCheckPeriod(Integer.valueOf(this.curDataProvider.getAcctPeriod()));
            record.setChecker(loginUserName);
            record.setCheckMode(CheckModeEnum.OFFSETVCHR.getCode());
            record.setCheckType("\u81ea\u52a8\u6838\u5bf9");
        }
    }
}

