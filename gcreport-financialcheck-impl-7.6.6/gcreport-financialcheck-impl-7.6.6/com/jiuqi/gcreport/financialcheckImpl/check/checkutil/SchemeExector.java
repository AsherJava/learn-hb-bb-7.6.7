/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
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
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.ListMatch;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingResult;
import com.jiuqi.gcreport.financialcheckcore.check.dto.MatchingVchr;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

public class SchemeExector {
    private FinancialCheckSchemeEO scheme;
    private FinancialCheckDataProvider allDataProvider;
    private MatchingResult matchResult;
    private GcRelatedItemCommandService relatedItemCommandService;
    private boolean manualCheck;
    private boolean inspectCheck = false;
    private AmtCheck amtCheck;
    private Map<String, GroupedCheckDataProvider> providerGroups;
    private GroupedCheckDataProvider curDataProvider;
    private CheckModeEnum realCheckMode;
    private final int bothNum = 2;

    public static SchemeExector newInstance(FinancialCheckSchemeEO scheme, FinancialCheckDataProvider provider, CheckModeEnum realCheckMode) {
        return new SchemeExector(scheme, provider, realCheckMode);
    }

    private SchemeExector(FinancialCheckSchemeEO scheme, FinancialCheckDataProvider provider, CheckModeEnum realCheckMode) {
        this.scheme = scheme;
        this.allDataProvider = provider;
        this.matchResult = new MatchingResult();
        this.relatedItemCommandService = (GcRelatedItemCommandService)SpringContextUtils.getBean(GcRelatedItemCommandService.class);
        this.realCheckMode = realCheckMode;
    }

    public MatchingResult executeAuto() {
        if (CollectionUtils.isEmpty(this.allDataProvider.getVoucherItems())) {
            return this.matchResult;
        }
        this.manualCheck = false;
        this.groupData();
        this.amtCheck = new AmtCheck(this.scheme);
        Iterator<GroupedCheckDataProvider> iterator = this.providerGroups.values().iterator();
        while (iterator.hasNext()) {
            GroupedCheckDataProvider provider;
            this.curDataProvider = provider = iterator.next();
            try {
                this.disposeMinCheckPeriod();
                this.doCheck();
            }
            catch (BusinessRuntimeException e) {
                this.matchResult.addUnmatchingVchrItems(this.curDataProvider.getVoucherItems());
            }
        }
        return this.matchResult;
    }

    private void disposeMinCheckPeriod() {
        String oppUnitId;
        List<GcRelatedItemEO> voucherItems = this.curDataProvider.getVoucherItems();
        GcRelatedItemEO firstItem = voucherItems.get(0);
        Integer minDataPeriod = voucherItems.stream().map(GcRelatedItemEO::getAcctPeriod).min(Integer::compareTo).get();
        String unitId = firstItem.getUnitId();
        int unitOpenAccountPeriod = UnitStateUtils.getUnitOpenAccountPeriod(unitId, oppUnitId = firstItem.getOppUnitId(), firstItem.getAcctYear(), minDataPeriod, this.allDataProvider.getAcctPeriod());
        if (unitOpenAccountPeriod == -1) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d{}\u4e0e{\u7684\u5171\u540c\u4e0a\u7ea7\u5728 {}\u5e74 {}\u6708-{}\u6708\u5df2\u5173\u8d26", unitId, oppUnitId, this.curDataProvider.getAcctYear(), minDataPeriod, this.curDataProvider.getAcctPeriod()));
        }
        this.curDataProvider.setMinCheckPeriod(unitOpenAccountPeriod);
    }

    String executeInspect() {
        this.manualCheck = true;
        this.inspectCheck = true;
        this.groupData();
        if (this.providerGroups.size() > 1) {
            throw new BusinessRuntimeException("\u9009\u4e2d\u7684\u6570\u636e\u4e0d\u5c5e\u4e8e\u540c\u4e00\u7ec4");
        }
        this.amtCheck = new AmtCheck(this.scheme);
        Iterator<GroupedCheckDataProvider> iterator = this.providerGroups.values().iterator();
        while (iterator.hasNext()) {
            GroupedCheckDataProvider provider;
            this.curDataProvider = provider = iterator.next();
            this.doCheck();
        }
        return "success";
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
        if (CheckModeEnum.unilateralCheck((String)this.realCheckMode.getCode())) {
            return voucherItem.getId();
        }
        StringBuilder builder = new StringBuilder();
        if (CheckModeEnum.selfCheck((String)this.realCheckMode.getCode())) {
            builder.append(voucherItem.getUnitId()).append(voucherItem.getOppUnitId());
        } else if (voucherItem.getUnitId().compareTo(voucherItem.getOppUnitId()) <= 0) {
            builder.append(voucherItem.getUnitId()).append(voucherItem.getOppUnitId());
        } else {
            builder.append(voucherItem.getOppUnitId()).append(voucherItem.getUnitId());
        }
        if (CheckModeEnum.selfCheck((String)this.realCheckMode.getCode())) {
            builder.append(voucherItem.getSubjectCode());
        }
        if (!StringUtils.isEmpty((String)this.scheme.getCheckDimensions())) {
            String[] dims = this.scheme.getCheckDimensions().split(",");
            Arrays.stream(dims).forEach(dim -> builder.append(voucherItem.getFieldValue(dim)));
        }
        builder.append(voucherItem.getOriginalCurr());
        return DigestUtils.md5DigestAsHex(builder.toString().getBytes(Charset.defaultCharset()));
    }

    private void doCheck() {
        switch (this.realCheckMode) {
            case WRITEOFFCHECK: {
                this.doSelfCheck();
                break;
            }
            case BILATERAL: {
                this.doBilateralCheck();
                break;
            }
            case UNILATERAL: {
                this.doUnilateralCheck();
                break;
            }
            case SPECIALCHECK: {
                this.doSpecialCheck();
                break;
            }
            case GCNUMBER: {
                this.doCheckByGcNumber();
                break;
            }
            case GCNUMBERSPECIAL: {
                this.doCheckByGcNumberSpecial();
                break;
            }
        }
    }

    private void doSelfCheck() {
        if (this.manualCheck && this.curDataProvider.getVoucherItemGroupByLocalOrg().size() > 1) {
            if (this.inspectCheck) {
                throw new BusinessRuntimeException("\u5b58\u5728\u591a\u4e2a\u5355\u4f4d\u7684\u6570\u636e");
            }
            return;
        }
        this.curDataProvider.getVoucherItemGroupByLocalOrg().values().forEach(voucherItems -> {
            List<List<GcRelatedItemEO>> debitRecordOffsetGroup = this.groupByAmtSign((List<GcRelatedItemEO>)voucherItems);
            if (!this.manualCheck) {
                this.doMatch(debitRecordOffsetGroup.get(0), debitRecordOffsetGroup.get(1), true, (List<GcRelatedItemEO>)voucherItems);
            }
            this.doManyCheck((List<GcRelatedItemEO>)voucherItems, () -> {
                if (voucherItems.size() < 2) {
                    if (this.inspectCheck) {
                        throw new BusinessRuntimeException("\u53c2\u4e0e\u5bf9\u8d26\u7684\u5206\u5f55\u4e0d\u80fd\u5c11\u4e8e2\u6761");
                    }
                    return false;
                }
                return true;
            }, true);
            this.matchResult.addUnmatchingVchrItems(voucherItems);
        });
    }

    private List<List<GcRelatedItemEO>> groupByAmtSign(List<GcRelatedItemEO> voucherItems) {
        ArrayList<List<GcRelatedItemEO>> group = new ArrayList<List<GcRelatedItemEO>>();
        ArrayList positiveItems = new ArrayList();
        ArrayList negativeItems = new ArrayList();
        group.add(positiveItems);
        group.add(negativeItems);
        voucherItems.forEach(voucherItem -> {
            if (voucherItem.getAmountAsDebit().signum() > 0) {
                positiveItems.add(voucherItem);
            } else {
                negativeItems.add(voucherItem);
            }
        });
        return group;
    }

    private void doManyCheck(List<GcRelatedItemEO> records, DoManyCheckCallBack callBack, boolean selfCheckFlag) {
        if (!callBack.verifyBeforeManyCheck()) {
            return;
        }
        boolean amtMatch = this.amtCheck.checkAndDistributionAmt(records);
        if (amtMatch) {
            try {
                MatchingVchr checkedVchr = new MatchingVchr(this.scheme, new ArrayList<GcRelatedItemEO>(records));
                if (!this.inspectCheck) {
                    this.setAndUpdateCheckInfo(checkedVchr);
                }
                if (!selfCheckFlag) {
                    records.forEach(record -> this.curDataProvider.removeVoucherItem((GcRelatedItemEO)record));
                }
                this.matchResult.getMatchingVchrs().add(checkedVchr);
                records.clear();
            }
            catch (BusinessRuntimeException businessRuntimeException) {}
        } else if (this.inspectCheck) {
            throw new BusinessRuntimeException("\u6838\u5bf9\u91d1\u989d\u5931\u8d25");
        }
    }

    private void doBilateralCheck() {
        ArrayList<GcRelatedItemEO> group;
        if (this.curDataProvider.getVoucherItemGroupByLocalOrg().size() != 2) {
            throw new BusinessRuntimeException("\u540c\u4e00\u7ec4\u53c2\u4e0e\u5bf9\u8d26\u7684\u6570\u636e\u7684\u53ea\u80fd\u4e3a\u4e24\u4e2a\u5355\u4f4d");
        }
        Iterator it = this.curDataProvider.getVoucherItemGroupByLocalOrg().values().iterator();
        List list1 = (List)it.next();
        Map<Integer, List<GcRelatedItemEO>> list1Map = list1.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getBusinessRole));
        List list2 = (List)it.next();
        Map<Integer, List<GcRelatedItemEO>> list2Map = list2.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getBusinessRole));
        if (!CollectionUtils.isEmpty((Collection)list1Map.get(1)) && !CollectionUtils.isEmpty((Collection)list2Map.get(-1))) {
            group = new ArrayList<GcRelatedItemEO>((Collection)list1Map.get(1));
            group.addAll((Collection)list2Map.get(-1));
            if (!this.manualCheck) {
                this.doMatch(new ArrayList<GcRelatedItemEO>((Collection)list1Map.get(1)), new ArrayList<GcRelatedItemEO>((Collection)list2Map.get(-1)), false, group);
            }
            if (!CollectionUtils.isEmpty(group)) {
                this.doManyCheck(group, () -> this.groupDataIsSpecialCheck(group), false);
            }
        }
        if (!CollectionUtils.isEmpty((Collection)list1Map.get(-1)) && !CollectionUtils.isEmpty((Collection)list2Map.get(1))) {
            group = new ArrayList(list1Map.get(-1));
            group.addAll((Collection)list2Map.get(1));
            if (!this.manualCheck) {
                this.doMatch(new ArrayList<GcRelatedItemEO>((Collection)list1Map.get(-1)), new ArrayList<GcRelatedItemEO>((Collection)list2Map.get(1)), false, group);
            }
            if (!CollectionUtils.isEmpty(group)) {
                this.doManyCheck(group, () -> this.groupDataIsSpecialCheck(group), false);
            }
        }
        this.matchResult.addUnmatchingVchrItems(this.curDataProvider.getVoucherItems());
    }

    private void doCheckByGcNumber() {
        ArrayList<GcRelatedItemEO> group;
        if (this.curDataProvider.getVoucherItemGroupByLocalOrg().size() != 2) {
            throw new BusinessRuntimeException("\u540c\u4e00\u7ec4\u53c2\u4e0e\u5bf9\u8d26\u7684\u6570\u636e\u7684\u53ea\u80fd\u4e3a\u4e24\u4e2a\u5355\u4f4d");
        }
        Iterator it = this.curDataProvider.getVoucherItemGroupByLocalOrg().values().iterator();
        List list1 = (List)it.next();
        Map<Integer, List<GcRelatedItemEO>> list1Map = list1.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getBusinessRole));
        List list2 = (List)it.next();
        Map<Integer, List<GcRelatedItemEO>> list2Map = list2.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getBusinessRole));
        if (!CollectionUtils.isEmpty((Collection)list1Map.get(1)) && !CollectionUtils.isEmpty((Collection)list2Map.get(-1))) {
            group = new ArrayList<GcRelatedItemEO>((Collection)list1Map.get(1));
            group.addAll((Collection)list2Map.get(-1));
            this.doManyCheck(group, () -> this.groupDataIsSpecialCheck(group), false);
        }
        if (!CollectionUtils.isEmpty((Collection)list1Map.get(-1)) && !CollectionUtils.isEmpty((Collection)list2Map.get(1))) {
            group = new ArrayList(list1Map.get(-1));
            group.addAll((Collection)list2Map.get(1));
            this.doManyCheck(group, () -> this.groupDataIsSpecialCheck(group), false);
        }
        this.matchResult.addUnmatchingVchrItems(this.curDataProvider.getVoucherItems());
    }

    private void doCheckByGcNumberSpecial() {
        List<GcRelatedItemEO> uncheckItems = this.curDataProvider.getVoucherItems();
        if (CollectionUtils.isEmpty(uncheckItems)) {
            return;
        }
        this.doManyCheck(new ArrayList<GcRelatedItemEO>(uncheckItems), () -> true, false);
    }

    private boolean groupDataIsSpecialCheck(List<GcRelatedItemEO> group) {
        int unitCount;
        return this.scheme.getSpecialCheck() != 0 || (unitCount = group.stream().collect(Collectors.groupingBy(item -> item.getUnitId() + item.getOppUnitId())).size()) != 1;
    }

    private void doSpecialCheck() {
        this.doSpecialCheckWithGroup(uncheckItems -> uncheckItems.stream().collect(Collectors.groupingBy(item -> item.getUnitId() + item.getOppUnitId())));
        this.doSpecialCheckWithGroup(uncheckItems -> uncheckItems.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getSubjectCode)));
        this.matchResult.addUnmatchingVchrItems(this.curDataProvider.getVoucherItems());
    }

    private void doSpecialCheckWithGroup(SpecialCheckGroupCallBack specialCheckGroupCallBack) {
        List<GcRelatedItemEO> uncheckItems = this.curDataProvider.getVoucherItems();
        if (CollectionUtils.isEmpty(uncheckItems)) {
            return;
        }
        Map<String, List<GcRelatedItemEO>> uncheckItemsGroup = specialCheckGroupCallBack.callBack(uncheckItems);
        for (List<GcRelatedItemEO> items : uncheckItemsGroup.values()) {
            List<List<GcRelatedItemEO>> debitRecordOffsetGroup = this.groupByAmtSign(items);
            if (!this.manualCheck) {
                this.doMatch(debitRecordOffsetGroup.get(0), debitRecordOffsetGroup.get(1), false, items);
            }
            this.doManyCheck(items, () -> true, false);
        }
    }

    private void doUnilateralCheck() {
        this.doUnilateralCheck(this.curDataProvider.getVoucherItems());
    }

    private void doUnilateralCheck(List<GcRelatedItemEO> uncheckedData) {
        if (this.manualCheck && uncheckedData.size() > 1) {
            if (this.inspectCheck) {
                throw new BusinessRuntimeException("\u5355\u8fb9\u5bf9\u8d26\u9009\u62e9\u7684\u6570\u636e\u4e0d\u80fd\u4e3a\u591a\u6761");
            }
            return;
        }
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
            if (!this.inspectCheck) {
                this.setAndUpdateCheckInfo(checkedVchr);
            }
            this.matchResult.getMatchingVchrs().add(checkedVchr);
        }
        catch (BusinessRuntimeException e) {
            this.matchResult.addUnmatchingVchrItems(uncheckedData);
        }
    }

    private void doMatch(List<GcRelatedItemEO> localVoucherItems, List<GcRelatedItemEO> oppVoucherItems, final boolean selfCheckFlag, final List<GcRelatedItemEO> origVoucherItems) {
        int maxCheckNum = 3;
        final Set localVoucherItemIds = localVoucherItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        new ListMatch<GcRelatedItemEO>(localVoucherItems, oppVoucherItems, new ListMatch.CallBack<GcRelatedItemEO>(){

            @Override
            public int getMaxCount() {
                return selfCheckFlag ? 1 : 3;
            }

            @Override
            public double getValue(GcRelatedItemEO voucherItem) {
                if (localVoucherItemIds.contains(voucherItem.getId())) {
                    return voucherItem.getAmountAsDebit().doubleValue();
                }
                return voucherItem.getAmountAsDebit().negate().doubleValue();
            }

            @Override
            public boolean equals(double localValue, double oppValue, Collection<GcRelatedItemEO> localVoucherItem, Collection<GcRelatedItemEO> oppVoucherItem) {
                return SchemeExector.this.amtCheck.checkAndDistributionAmt(localVoucherItem, oppVoucherItem);
            }

            @Override
            public void accept(Collection<GcRelatedItemEO> localVoucherItems, Collection<GcRelatedItemEO> oppVoucherItems) {
                ArrayList matchedInputItems = new ArrayList();
                localVoucherItems.forEach(voucherItem -> {
                    if (!CollectionUtils.isEmpty(origVoucherItems)) {
                        origVoucherItems.remove(voucherItem);
                    }
                    SchemeExector.this.curDataProvider.removeVoucherItem((GcRelatedItemEO)voucherItem);
                    matchedInputItems.add(voucherItem);
                });
                oppVoucherItems.forEach(voucherItem -> {
                    if (!CollectionUtils.isEmpty(origVoucherItems)) {
                        origVoucherItems.remove(voucherItem);
                    }
                    SchemeExector.this.curDataProvider.removeVoucherItem((GcRelatedItemEO)voucherItem);
                    matchedInputItems.add(voucherItem);
                });
                MatchingVchr matchingVchr = new MatchingVchr(SchemeExector.this.scheme, matchedInputItems);
                try {
                    SchemeExector.this.setAndUpdateCheckInfo(matchingVchr);
                    SchemeExector.this.matchResult.getMatchingVchrs().add(matchingVchr);
                }
                catch (BusinessRuntimeException businessRuntimeException) {
                    // empty catch block
                }
            }
        }).match();
    }

    private void setAndUpdateCheckInfo(MatchingVchr matchingVchr) {
        String checkId = UUIDOrderUtils.newUUIDStr();
        if (this.curDataProvider.getCheckPeriodBaseVoucherPeriod()) {
            ArrayList allItemInGroup = new ArrayList();
            if (!CollectionUtils.isEmpty(matchingVchr.getVchrItems())) {
                allItemInGroup.addAll(matchingVchr.getVchrItems());
            }
            if (!CollectionUtils.isEmpty(allItemInGroup)) {
                Integer maxPeriod = allItemInGroup.stream().map(GcRelatedItemEO::getAcctPeriod).max(Integer::compareTo).get();
                this.curDataProvider.setAcctPeriod(maxPeriod);
            }
        }
        if (this.curDataProvider.getMinCheckPeriod() > this.curDataProvider.getAcctPeriod()) {
            this.curDataProvider.setAcctPeriod(this.curDataProvider.getMinCheckPeriod());
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
            record.setCheckMode(this.scheme.getCheckMode());
            record.setCheckType(Boolean.TRUE.equals(this.manualCheck) ? "\u624b\u5de5\u6838\u5bf9" : "\u81ea\u52a8\u6838\u5bf9");
        }
    }

    @FunctionalInterface
    private static interface SpecialCheckGroupCallBack {
        public Map<String, List<GcRelatedItemEO>> callBack(List<GcRelatedItemEO> var1);
    }

    @FunctionalInterface
    private static interface DoManyCheckCallBack {
        public boolean verifyBeforeManyCheck();
    }
}

