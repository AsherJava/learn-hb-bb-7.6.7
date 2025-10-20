/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.calculate.util.ListMatch
 *  com.jiuqi.gcreport.calculate.util.ListMatch$CallBack
 *  com.jiuqi.gcreport.calculate.util.ListMatch$CompareResult
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.calculate.util.ListMatch;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortOffsetHelper {
    private List<InputDataEO> inputItems;
    private Map<String, InputDataEO> inputItemGroupById;
    private Map<String, Set<String>> offSetVchrIdsGroupByInputDataId;
    private List<GcOffSetVchrItemDTO> origOffsetItems1;
    private List<GcOffSetVchrItemDTO> origOffsetItems2;
    private List<GcOffSetVchrItemDTO> origOffsetItems;
    private AbstractUnionRule rule;
    private BiFunction<Collection<GcOffSetVchrItemDTO>, Collection<GcOffSetVchrItemDTO>, List<GcOffSetVchrItemDTO>> balanceCreator;
    private Predicate<List<InputDataEO>> checkAfterMatched;
    private BiConsumer<Collection<InputDataEO>, List<GcOffSetVchrItemDTO>> matchResultCunsumer;
    private int maxOffsetNum;
    private boolean unilateralOffsetFlag;
    private Match match;

    public static SortOffsetHelper newInstance(List<InputDataEO> inputItems, List<GcOffSetVchrItemDTO> offsetItems1, List<GcOffSetVchrItemDTO> offsetItems2, AbstractUnionRule rule, Predicate<List<InputDataEO>> checkAfterMatched, BiConsumer<Collection<InputDataEO>, List<GcOffSetVchrItemDTO>> matchResultCunsumer) {
        return new SortOffsetHelper(inputItems, offsetItems1, offsetItems2, rule, checkAfterMatched, matchResultCunsumer);
    }

    void setBalanceCreator(BiFunction<Collection<GcOffSetVchrItemDTO>, Collection<GcOffSetVchrItemDTO>, List<GcOffSetVchrItemDTO>> balanceCreator) {
        this.balanceCreator = balanceCreator;
    }

    SortOffsetHelper setMaxOffsetNum(int maxOffsetNum) {
        if (maxOffsetNum > 1) {
            this.maxOffsetNum = maxOffsetNum;
        }
        return this;
    }

    SortOffsetHelper setUnilateralOffsetItems(List<GcOffSetVchrItemDTO> offsetItems) {
        this.origOffsetItems = offsetItems;
        this.unilateralOffsetFlag = true;
        return this;
    }

    private SortOffsetHelper(List<InputDataEO> inputItems, List<GcOffSetVchrItemDTO> offsetItems1, List<GcOffSetVchrItemDTO> offsetItems2, AbstractUnionRule rule, Predicate<List<InputDataEO>> checkAfterMatched, BiConsumer<Collection<InputDataEO>, List<GcOffSetVchrItemDTO>> matchResultCunsumer) {
        this.inputItems = inputItems;
        this.origOffsetItems1 = offsetItems1;
        this.origOffsetItems2 = offsetItems2;
        this.rule = rule;
        this.checkAfterMatched = checkAfterMatched;
        this.matchResultCunsumer = matchResultCunsumer;
        this.maxOffsetNum = 1;
        this.match = new Match();
        this.offSetVchrIdsGroupByInputDataId = new HashMap<String, Set<String>>();
    }

    public int offset() {
        this.inputItemGroupById = this.inputItems.stream().collect(Collectors.toMap(InputDataEO::getId, i -> i));
        for (String inputDataId : this.inputItemGroupById.keySet()) {
            Set origOffset1IdsByInputDataId = this.origOffsetItems1.stream().filter(gcOffSetVchrItemDTO -> inputDataId.equals(gcOffSetVchrItemDTO.getSrcId())).map(GcOffSetVchrItemDTO::getId).collect(Collectors.toSet());
            Set origOffset2IdsByInputDataId = this.origOffsetItems2.stream().filter(gcOffSetVchrItemDTO -> inputDataId.equals(gcOffSetVchrItemDTO.getSrcId())).map(GcOffSetVchrItemDTO::getId).collect(Collectors.toSet());
            HashSet OffSetVchrIds = new HashSet();
            OffSetVchrIds.addAll(origOffset1IdsByInputDataId);
            OffSetVchrIds.addAll(origOffset2IdsByInputDataId);
            if (OffSetVchrIds.size() < 2) continue;
            this.offSetVchrIdsGroupByInputDataId.put(inputDataId, OffSetVchrIds);
        }
        ArrayList<GcOffSetVchrItemDTO> offsetItems1 = new ArrayList<GcOffSetVchrItemDTO>(this.origOffsetItems1);
        ArrayList<GcOffSetVchrItemDTO> offsetItems2 = new ArrayList<GcOffSetVchrItemDTO>(this.origOffsetItems2);
        return new ListMatch(offsetItems1, offsetItems2, (ListMatch.CallBack)this.match).match();
    }

    private class Match
    implements ListMatch.CallBack<GcOffSetVchrItemDTO> {
        private Match() {
        }

        public int getMaxCount() {
            return SortOffsetHelper.this.unilateralOffsetFlag ? 1 : SortOffsetHelper.this.maxOffsetNum;
        }

        public double getValue(GcOffSetVchrItemDTO o) {
            double amt = o.getDebit() == 0.0 ? o.getCredit() : o.getDebit();
            if (SortOffsetHelper.this.unilateralOffsetFlag) {
                return amt > 0.0 ? amt : -amt;
            }
            return amt;
        }

        public ListMatch.CompareResult<GcOffSetVchrItemDTO> equals(double v1, double v2, Collection<GcOffSetVchrItemDTO> o1, Collection<GcOffSetVchrItemDTO> o2) {
            if (SortOffsetHelper.this.unilateralOffsetFlag) {
                v1 -= v2;
                v2 = 0.0;
            }
            ArrayList<GcOffSetVchrItemDTO> offSetItems = new ArrayList<GcOffSetVchrItemDTO>();
            offSetItems.addAll(o1);
            offSetItems.addAll(o2);
            ListMatch.CompareResult compareResult = new ListMatch.CompareResult();
            if (!GcCalcAmtCheckUtil.checkAmt((AbstractUnionRule)SortOffsetHelper.this.rule, (BigDecimal)BigDecimal.valueOf(v1), (BigDecimal)BigDecimal.valueOf(v2), offSetItems)) {
                if (SortOffsetHelper.this.balanceCreator != null) {
                    List balanceOffSetVchrItem = (List)SortOffsetHelper.this.balanceCreator.apply(o1, o2);
                    if (CollectionUtils.isEmpty((Collection)balanceOffSetVchrItem)) {
                        return compareResult;
                    }
                    compareResult.setNewData(balanceOffSetVchrItem);
                } else {
                    return compareResult;
                }
            }
            ArrayList matchingInputItems = new ArrayList();
            o1.forEach(offsetItem -> matchingInputItems.add(SortOffsetHelper.this.inputItemGroupById.get(offsetItem.getSrcId())));
            o2.forEach(offsetItem -> matchingInputItems.add(SortOffsetHelper.this.inputItemGroupById.get(offsetItem.getSrcId())));
            if (!SortOffsetHelper.this.offSetVchrIdsGroupByInputDataId.isEmpty()) {
                Set matchingInputDataIds = matchingInputItems.stream().map(InputDataEO::getId).collect(Collectors.toSet());
                HashSet offSetVchrIds = new HashSet();
                offSetVchrIds.addAll(o1.stream().map(GcOffSetVchrItemDTO::getId).collect(Collectors.toSet()));
                offSetVchrIds.addAll(o2.stream().map(GcOffSetVchrItemDTO::getId).collect(Collectors.toSet()));
                for (String inputDataId : matchingInputDataIds) {
                    if (CollectionUtils.isEmpty((Collection)((Collection)SortOffsetHelper.this.offSetVchrIdsGroupByInputDataId.get(inputDataId))) || offSetVchrIds.containsAll((Collection)SortOffsetHelper.this.offSetVchrIdsGroupByInputDataId.get(inputDataId))) continue;
                    return compareResult;
                }
            }
            if (SortOffsetHelper.this.checkAfterMatched.test(matchingInputItems)) {
                compareResult.setEqual(true);
            }
            return compareResult;
        }

        public void accept(Collection<GcOffSetVchrItemDTO> set1, Collection<GcOffSetVchrItemDTO> set2, List<GcOffSetVchrItemDTO> balanceItems) {
            String offsetGroupId = UUIDUtils.newUUIDStr();
            HashMap offsetedInputItems = new HashMap(16);
            Stream.concat(set1.stream(), set2.stream()).forEach(offsetItem -> {
                offsetItem.setSrcOffsetGroupId(offsetGroupId);
                if (SortOffsetHelper.this.unilateralOffsetFlag) {
                    SortOffsetHelper.this.origOffsetItems.remove(offsetItem);
                } else {
                    SortOffsetHelper.this.origOffsetItems1.remove(offsetItem);
                    SortOffsetHelper.this.origOffsetItems2.remove(offsetItem);
                }
                offsetedInputItems.put(offsetItem.getSrcId(), SortOffsetHelper.this.inputItemGroupById.get(offsetItem.getSrcId()));
                SortOffsetHelper.this.offSetVchrIdsGroupByInputDataId.remove(offsetItem.getSrcId());
            });
            SortOffsetHelper.this.inputItems.removeAll(offsetedInputItems.values());
            ArrayList<GcOffSetVchrItemDTO> offsetItems = new ArrayList<GcOffSetVchrItemDTO>(set1);
            offsetItems.addAll(set2);
            if (!CollectionUtils.isEmpty(balanceItems)) {
                balanceItems.forEach(balanceItem -> balanceItem.setSrcOffsetGroupId(offsetGroupId));
                offsetItems.addAll(balanceItems);
            }
            SortOffsetHelper.this.matchResultCunsumer.accept(offsetedInputItems.values(), offsetItems);
        }
    }
}

