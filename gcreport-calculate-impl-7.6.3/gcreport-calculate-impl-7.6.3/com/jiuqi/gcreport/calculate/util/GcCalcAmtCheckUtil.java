/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 */
package com.jiuqi.gcreport.calculate.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.service.GcCalAmtCheckService;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class GcCalcAmtCheckUtil {
    private static final double ERRORRANGE = 0.001;
    private static final int SCALE = 2;
    private static Logger logger = LoggerFactory.getLogger(GcCalcAmtCheckUtil.class);

    public static boolean distributionAmt(AbstractUnionRule rule, Collection<GcOffSetVchrItemDTO> offsetItems) {
        if (CollectionUtils.isEmpty(offsetItems)) {
            return true;
        }
        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;
        for (GcOffSetVchrItemDTO record : offsetItems) {
            debitSum = NumberUtils.add((BigDecimal)debitSum, (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(record.getDebit())});
            creditSum = NumberUtils.add((BigDecimal)creditSum, (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(record.getCredit())});
        }
        GcCalcAmtCheckUtil.distributionAmt(rule, offsetItems, debitSum, creditSum);
        return Math.abs(NumberUtils.sub((BigDecimal)debitSum, (BigDecimal)creditSum).doubleValue()) <= 0.001;
    }

    public static boolean checkAndDistributionAmt(AbstractUnionRule rule, Collection<GcOffSetVchrItemDTO> offsetItems) {
        if (CollectionUtils.isEmpty(offsetItems)) {
            return false;
        }
        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;
        for (GcOffSetVchrItemDTO record : offsetItems) {
            debitSum = NumberUtils.add((BigDecimal)debitSum, (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(record.getDebit())});
            creditSum = NumberUtils.add((BigDecimal)creditSum, (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(record.getCredit())});
        }
        if (!GcCalcAmtCheckUtil.checkAmt(rule, debitSum, creditSum, offsetItems)) {
            return false;
        }
        GcCalcAmtCheckUtil.distributionAmt(rule, offsetItems, debitSum, creditSum);
        return true;
    }

    public static boolean checkAmt(AbstractUnionRule rule, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum) {
        if (Math.abs(NumberUtils.subDouble((Object)debitUncheckedAmtSum, (Object)creditUncheckedAmtSum)) <= 0.001) {
            return true;
        }
        if (!rule.getEnableToleranceFlag().booleanValue()) {
            return false;
        }
        return GcCalcAmtCheckUtil.rateToleranceRangeChcek(rule, debitUncheckedAmtSum, creditUncheckedAmtSum, null);
    }

    public static boolean checkAmt(AbstractUnionRule rule, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum, Collection<GcOffSetVchrItemDTO> offsetItems) {
        if (CollectionUtils.isEmpty(offsetItems)) {
            return false;
        }
        if (Math.abs(NumberUtils.subDouble((Object)debitUncheckedAmtSum, (Object)creditUncheckedAmtSum)) <= 0.001) {
            return true;
        }
        if (!rule.getEnableToleranceFlag().booleanValue()) {
            return false;
        }
        return GcCalcAmtCheckUtil.rateToleranceRangeChcek(rule, debitUncheckedAmtSum, creditUncheckedAmtSum, offsetItems);
    }

    private static boolean rateToleranceRangeChcek(AbstractUnionRule rule, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum, Collection<GcOffSetVchrItemDTO> offsetItems) {
        if (rule.getToleranceRange() == null) {
            Object[] args = new String[]{rule.getLocalizedName()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.amtcheck.amttolerancerangemsg", (Object[])args));
        }
        double toleranceRange = rule.getToleranceRange();
        if (toleranceRange < 0.0) {
            return true;
        }
        toleranceRange /= 100.0;
        BigDecimal diffAmt = BigDecimal.valueOf(Math.abs(NumberUtils.subDouble((Object)debitUncheckedAmtSum, (Object)creditUncheckedAmtSum)));
        ToleranceTypeEnum toleranceType = rule.getToleranceType();
        if (toleranceType == null) {
            Object[] args = new String[]{rule.getLocalizedName()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.amtcheck.amttolerancerangemsg", (Object[])args));
        }
        switch (toleranceType) {
            case BY_MONEY: {
                return NumberUtils.subDouble((Object)diffAmt, (Object)rule.getToleranceRange()) <= 0.001;
            }
            case BY_PROPORTION: {
                BigDecimal offsetAmt = GcCalcAmtCheckUtil.getOffsetAmt(rule, debitUncheckedAmtSum, creditUncheckedAmtSum, offsetItems);
                return NumberUtils.subDouble((Object)diffAmt, (Object)Math.abs(NumberUtils.mul((BigDecimal)offsetAmt, (double)toleranceRange).doubleValue())) <= 0.001;
            }
        }
        return false;
    }

    private static BigDecimal getOffsetAmt(AbstractUnionRule rule, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum, Collection<GcOffSetVchrItemDTO> offsetItems) {
        OffsetTypeEnum offsetType = rule.getOffsetType();
        if (offsetType == null) {
            Object[] args = new String[]{rule.getLocalizedName()};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.amtcheck.nooffsettypemsg", (Object[])args));
        }
        switch (offsetType) {
            case MORE_MONEY: {
                return debitUncheckedAmtSum.compareTo(creditUncheckedAmtSum) > 0 ? debitUncheckedAmtSum : creditUncheckedAmtSum;
            }
            case LESS_MONEY: {
                return debitUncheckedAmtSum.compareTo(creditUncheckedAmtSum) < 0 ? debitUncheckedAmtSum : creditUncheckedAmtSum;
            }
            case DEBIT_MONEY: {
                return debitUncheckedAmtSum;
            }
            case CREDIT_MONEY: {
                return creditUncheckedAmtSum;
            }
            case CUSTOMIZE: {
                return GcCalcAmtCheckUtil.customizeMatchOrient(rule, offsetItems, debitUncheckedAmtSum, creditUncheckedAmtSum);
            }
        }
        return BigDecimal.ZERO;
    }

    private static BigDecimal customizeMatchOrient(AbstractUnionRule rule, Collection<GcOffSetVchrItemDTO> offsetItems, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum) {
        GcCalAmtCheckService calInputDataAmtCheckService = (GcCalAmtCheckService)SpringContextUtils.getBean(GcCalAmtCheckService.class);
        return calInputDataAmtCheckService.customizeMatchOrient(rule, offsetItems, debitUncheckedAmtSum, creditUncheckedAmtSum);
    }

    private static void distributionAmt(AbstractUnionRule rule, Collection<GcOffSetVchrItemDTO> offsetItems, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum) {
        GcCalcAmtCheckUtil.distributionAmtNoDiff(offsetItems);
        if (Math.abs(NumberUtils.sub((BigDecimal)debitUncheckedAmtSum, (BigDecimal)creditUncheckedAmtSum).doubleValue()) <= 0.001) {
            return;
        }
        GcCalcAmtCheckUtil.distributionAmtHasDiff(rule, offsetItems, debitUncheckedAmtSum, creditUncheckedAmtSum);
    }

    private static void distributionAmtNoDiff(Collection<GcOffSetVchrItemDTO> offsetItems) {
        if (CollectionUtils.isEmpty(offsetItems)) {
            return;
        }
        offsetItems.forEach(item -> {
            item.setOffSetDebit(item.getDebit());
            item.setOffSetCredit(item.getCredit());
            item.setDiffd(Double.valueOf(0.0));
            item.setDiffc(Double.valueOf(0.0));
        });
    }

    private static void distributionAmtHasDiff(AbstractUnionRule rule, Collection<GcOffSetVchrItemDTO> offsetItems, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum) {
        BigDecimal diffAmt;
        OrientEnum diffOrient;
        BigDecimal offsetAmt = GcCalcAmtCheckUtil.getOffsetAmt(rule, debitUncheckedAmtSum, creditUncheckedAmtSum, offsetItems);
        if (debitUncheckedAmtSum.compareTo(offsetAmt) == 0) {
            diffOrient = OrientEnum.C;
            diffAmt = NumberUtils.sub((BigDecimal)creditUncheckedAmtSum, (BigDecimal)offsetAmt);
        } else {
            diffOrient = OrientEnum.D;
            diffAmt = NumberUtils.sub((BigDecimal)debitUncheckedAmtSum, (BigDecimal)offsetAmt);
        }
        if (rule instanceof FlexibleRuleDTO && ((FlexibleRuleDTO)rule).getProportionOffsetDiffFlag() != false || rule instanceof FloatLineRuleDTO && ((FloatLineRuleDTO)rule).getProportionOffsetDiffFlag() != false || rule instanceof FinancialCheckRuleDTO && ((FinancialCheckRuleDTO)rule).getProportionOffsetDiffFlag().booleanValue()) {
            GcCalcAmtCheckUtil.distributionAmtAccordingRate(offsetItems, diffOrient, diffAmt.setScale(2, 4));
        } else {
            OffsetTypeEnum offsetType = rule.getOffsetType();
            boolean isDiffSubjectCodeFlag = GcCalcAmtCheckUtil.isDiffSubjectCode(rule);
            if (!OffsetTypeEnum.CUSTOMIZE.equals((Object)offsetType) && !isDiffSubjectCodeFlag) {
                List<GcOffSetVchrItemDTO> offSetVchrItems = GcCalcAmtCheckUtil.getRecordsGroupByDcOrderByAmtDesc(offsetItems, diffOrient);
                if (OffsetTypeEnum.LESS_MONEY.equals((Object)offsetType)) {
                    GcCalcAmtCheckUtil.distributionAmtToOffsetItems(offSetVchrItems, diffOrient, diffAmt.setScale(2, 4));
                } else if (OffsetTypeEnum.MORE_MONEY.equals((Object)offsetType)) {
                    GcCalcAmtCheckUtil.distributionMoreMoneyAmtToOffsetItems(offSetVchrItems, diffOrient, diffAmt.setScale(2, 4));
                } else {
                    GcCalcAmtCheckUtil.distributionDebitOrCreditAmtToOffsetItems(offSetVchrItems, diffOrient, diffAmt.setScale(2, 4), offsetType, debitUncheckedAmtSum, creditUncheckedAmtSum);
                }
            } else {
                GcOffSetVchrItemDTO diffOffSetVchrItem = GcCalcAmtCheckUtil.getRecordByDc(offsetItems, diffOrient);
                GcCalcAmtCheckUtil.distributionAmtToOne(diffOffSetVchrItem, diffOrient, diffAmt.setScale(2, 4));
                if (rule instanceof FlexibleRuleDTO) {
                    String diffSubjectCode = ((FlexibleRuleDTO)rule).getDiffSubjectCode();
                    if (StringUtils.isEmpty((String)diffSubjectCode)) {
                        return;
                    }
                    GcCalcAmtCheckUtil.addDiffOffsetVcherItems(diffOffSetVchrItem, diffOrient, diffAmt.setScale(2, 4), diffSubjectCode, offsetItems);
                }
            }
        }
    }

    private static boolean isDiffSubjectCode(AbstractUnionRule rule) {
        String diffSubjectCode;
        return rule instanceof FlexibleRuleDTO && !StringUtils.isEmpty((String)(diffSubjectCode = ((FlexibleRuleDTO)rule).getDiffSubjectCode()));
    }

    private static void distributionAmtToOne(GcOffSetVchrItemDTO record, OrientEnum diffOrient, BigDecimal diffAmt) {
        OrientEnum amtOrient = record.getOrient();
        BigDecimal bigDecimal = diffAmt = amtOrient == diffOrient ? diffAmt : diffAmt.negate();
        if (amtOrient == OrientEnum.D) {
            record.setDiffd(Double.valueOf(diffAmt.doubleValue()));
            Double offsetAmt = BigDecimal.valueOf(record.getDebit()).subtract(diffAmt).doubleValue();
            record.setOffSetDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((Double)offsetAmt)));
        } else {
            record.setDiffc(Double.valueOf(diffAmt.doubleValue()));
            Double offsetAmt = BigDecimal.valueOf(record.getCredit()).subtract(diffAmt).doubleValue();
            record.setOffSetCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((Double)offsetAmt)));
        }
    }

    private static void distributionAmtToOffsetItems(List<GcOffSetVchrItemDTO> records, OrientEnum diffOrient, BigDecimal diffAmt) {
        if (records.size() == 1) {
            GcCalcAmtCheckUtil.distributionAmtToOne(records.get(0), diffOrient, diffAmt);
            return;
        }
        OrientEnum amtOrient = records.get(0).getOrient();
        BigDecimal distributionDiffAmt = diffAmt = amtOrient == diffOrient ? diffAmt : diffAmt.negate();
        for (int i = 0; i < records.size(); ++i) {
            BigDecimal balanceDiffAmt;
            Double offsetAmt;
            if (i == records.size() - 1) {
                GcCalcAmtCheckUtil.distributionAmtToOne(records.get(i), diffOrient, distributionDiffAmt.abs());
                break;
            }
            GcOffSetVchrItemDTO record = records.get(i);
            if (amtOrient == OrientEnum.D) {
                if (diffAmt.doubleValue() <= record.getDebit()) {
                    record.setDiffd(Double.valueOf(diffAmt.doubleValue()));
                    offsetAmt = BigDecimal.valueOf(record.getDebit()).subtract(diffAmt).doubleValue();
                    record.setOffSetDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((Double)offsetAmt)));
                    break;
                }
                balanceDiffAmt = distributionDiffAmt.abs();
                distributionDiffAmt = BigDecimal.valueOf(record.getDebit()).subtract(balanceDiffAmt);
                if (record.getDebit() > 0.0) {
                    if (distributionDiffAmt.doubleValue() >= 0.0) {
                        record.setDiffd(Double.valueOf(balanceDiffAmt.doubleValue()));
                        record.setOffSetDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)distributionDiffAmt.doubleValue())));
                        break;
                    }
                    record.setDiffd(record.getDebit());
                    record.setOffSetDebit(Double.valueOf(0.0));
                    continue;
                }
                record.setDiffd(Double.valueOf(balanceDiffAmt.doubleValue()));
                record.setOffSetDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)distributionDiffAmt.doubleValue())));
                break;
            }
            if (diffAmt.doubleValue() <= record.getCredit()) {
                record.setDiffc(Double.valueOf(diffAmt.doubleValue()));
                offsetAmt = BigDecimal.valueOf(record.getCredit()).subtract(diffAmt).doubleValue();
                record.setOffSetCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((Double)offsetAmt)));
                break;
            }
            balanceDiffAmt = distributionDiffAmt.abs();
            distributionDiffAmt = BigDecimal.valueOf(record.getCredit()).subtract(balanceDiffAmt);
            if (record.getCredit() > 0.0) {
                if (distributionDiffAmt.doubleValue() >= 0.0) {
                    record.setDiffc(Double.valueOf(balanceDiffAmt.doubleValue()));
                    record.setOffSetCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)distributionDiffAmt.doubleValue())));
                    break;
                }
                record.setDiffc(record.getCredit());
                record.setOffSetCredit(Double.valueOf(0.0));
                continue;
            }
            record.setDiffc(Double.valueOf(balanceDiffAmt.doubleValue()));
            record.setOffSetCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)distributionDiffAmt.doubleValue())));
            break;
        }
    }

    private static void distributionMoreMoneyAmtToOffsetItems(List<GcOffSetVchrItemDTO> offsetItems, OrientEnum diffOrient, BigDecimal diffAmt) {
        ArrayList<GcOffSetVchrItemDTO> amtOrderByDesc = new ArrayList<GcOffSetVchrItemDTO>(offsetItems);
        Collections.reverse(amtOrderByDesc);
        GcCalcAmtCheckUtil.distributionAmtToOne((GcOffSetVchrItemDTO)amtOrderByDesc.get(0), diffOrient, diffAmt);
    }

    private static void distributionDebitOrCreditAmtToOffsetItems(List<GcOffSetVchrItemDTO> records, OrientEnum diffOrient, BigDecimal diffAmt, OffsetTypeEnum OffsetType, BigDecimal debitUncheckedAmtSum, BigDecimal creditUncheckedAmtSum) {
        if (debitUncheckedAmtSum.compareTo(creditUncheckedAmtSum) > 0) {
            if (OffsetTypeEnum.DEBIT_MONEY.equals((Object)OffsetType)) {
                GcCalcAmtCheckUtil.distributionMoreMoneyAmtToOffsetItems(records, diffOrient, diffAmt);
            } else {
                GcCalcAmtCheckUtil.distributionAmtToOffsetItems(records, diffOrient, diffAmt);
            }
        } else if (OffsetTypeEnum.DEBIT_MONEY.equals((Object)OffsetType)) {
            GcCalcAmtCheckUtil.distributionAmtToOffsetItems(records, diffOrient, diffAmt);
        } else {
            GcCalcAmtCheckUtil.distributionMoreMoneyAmtToOffsetItems(records, diffOrient, diffAmt);
        }
    }

    private static void addDiffOffsetVcherItems(GcOffSetVchrItemDTO offSetVchrItem, OrientEnum diffOrient, BigDecimal diffAmt, String diffSubjectCode, Collection<GcOffSetVchrItemDTO> offsetItems) {
        GcOffSetVchrItemDTO diffOffsetVchrItem = new GcOffSetVchrItemDTO();
        BeanUtils.copyProperties(offSetVchrItem, diffOffsetVchrItem);
        diffOffsetVchrItem.setId(UUIDUtils.newUUIDStr());
        diffOffsetVchrItem.setOffSetCredit(Double.valueOf(0.0));
        diffOffsetVchrItem.setOffSetDebit(Double.valueOf(0.0));
        diffOffsetVchrItem.setDebit(Double.valueOf(0.0));
        diffOffsetVchrItem.setCredit(Double.valueOf(0.0));
        diffOffsetVchrItem.setBfOffSetCredit(Double.valueOf(0.0));
        diffOffsetVchrItem.setBfOffSetDebit(Double.valueOf(0.0));
        diffOffsetVchrItem.setDiffd(Double.valueOf(0.0));
        diffOffsetVchrItem.setDiffc(Double.valueOf(0.0));
        OrientEnum amtOrient = diffOffsetVchrItem.getOrient();
        BigDecimal bigDecimal = diffAmt = amtOrient == diffOrient ? diffAmt : diffAmt.negate();
        if (amtOrient == OrientEnum.D) {
            diffOffsetVchrItem.setSubjectCode(diffSubjectCode);
            diffOffsetVchrItem.setOffSetDebit(Double.valueOf(diffAmt.negate().doubleValue()));
        } else {
            diffOffsetVchrItem.setSubjectCode(diffSubjectCode);
            diffOffsetVchrItem.setOffSetCredit(Double.valueOf(diffAmt.negate().doubleValue()));
        }
        diffOffsetVchrItem.setMemo(null);
        diffOffsetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.MANUAL_OFFSET_INPUT);
        diffOffsetVchrItem.setMemo("\u6279\u91cf\u624b\u52a8\u5dee\u989d");
        offSetVchrItem.setOffSetCredit(offSetVchrItem.getCredit());
        offSetVchrItem.setOffSetDebit(offSetVchrItem.getDebit());
        offSetVchrItem.setDiffc(Double.valueOf(0.0));
        offSetVchrItem.setDiffd(Double.valueOf(0.0));
        GcCalcAmtCheckUtil.setDimValues(diffOffsetVchrItem, offSetVchrItem);
        offsetItems.add(diffOffsetVchrItem);
    }

    private static List<GcOffSetVchrItemDTO> getRecordsGroupByDcOrderByAmtDesc(Collection<GcOffSetVchrItemDTO> offsetItems, OrientEnum orient) {
        OrientEnum orientEnum;
        ArrayList<GcOffSetVchrItemDTO> offSetVchrItemByOrient = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> offSetVchrItemByOrientInvert = new ArrayList<GcOffSetVchrItemDTO>();
        for (GcOffSetVchrItemDTO item : offsetItems) {
            if (orient.equals((Object)item.getOrient())) {
                offSetVchrItemByOrient.add(item);
                continue;
            }
            offSetVchrItemByOrientInvert.add(item);
        }
        OrientEnum orientEnum2 = orientEnum = OrientEnum.D.equals((Object)orient) ? OrientEnum.D : OrientEnum.C;
        if (!CollectionUtils.isEmpty(offSetVchrItemByOrient)) {
            return offSetVchrItemByOrient.stream().sorted(GcCalcAmtCheckUtil.offSetVchrItemGroupComparator(orientEnum).reversed()).collect(Collectors.toList());
        }
        return offSetVchrItemByOrientInvert.stream().sorted(GcCalcAmtCheckUtil.offSetVchrItemGroupComparator(orientEnum).reversed()).collect(Collectors.toList());
    }

    private static Comparator<GcOffSetVchrItemDTO> offSetVchrItemGroupComparator(OrientEnum orient) {
        if (OrientEnum.D.equals((Object)orient)) {
            return Comparator.comparing(GcOffSetVchrItemDTO::getDebit);
        }
        return Comparator.comparing(GcOffSetVchrItemDTO::getCredit);
    }

    private static GcOffSetVchrItemDTO getRecordByDc(Collection<GcOffSetVchrItemDTO> offsetItems, OrientEnum orient) {
        for (GcOffSetVchrItemDTO record : offsetItems) {
            if (record.getOrient() != orient) continue;
            return record;
        }
        return offsetItems.iterator().next();
    }

    private static void distributionAmtAccordingRate(Collection<GcOffSetVchrItemDTO> offsetItems, OrientEnum diffOrient, BigDecimal diffAmtSum) {
        Collection<GcOffSetVchrItemDTO> items = GcCalcAmtCheckUtil.getRecordsByDc(offsetItems, diffOrient);
        BigDecimal amtSum = BigDecimal.ZERO;
        for (GcOffSetVchrItemDTO item : items) {
            Double amt = item.getDebit() == 0.0 ? item.getCredit() : item.getDebit();
            amtSum = amtSum.add(BigDecimal.valueOf(amt));
        }
        BigDecimal distributionedDiffAmt = BigDecimal.ZERO;
        int index = 1;
        for (GcOffSetVchrItemDTO item : items) {
            BigDecimal diffAmt;
            if (index++ < items.size()) {
                if (amtSum.compareTo(BigDecimal.ZERO) == 0) {
                    diffAmt = diffAmtSum.divide(BigDecimal.valueOf(items.size()), 2, RoundingMode.HALF_UP);
                } else {
                    Double amt = item.getDebit() == 0.0 ? item.getCredit() : item.getDebit();
                    diffAmt = BigDecimal.valueOf(amt).multiply(diffAmtSum).divide(amtSum, 2, RoundingMode.HALF_UP);
                }
                distributionedDiffAmt = distributionedDiffAmt.add(diffAmt);
            } else {
                diffAmt = diffAmtSum.subtract(distributionedDiffAmt);
            }
            GcCalcAmtCheckUtil.distributionAmtToOne(item, diffOrient, diffAmt);
        }
    }

    private static Collection<GcOffSetVchrItemDTO> getRecordsByDc(Collection<GcOffSetVchrItemDTO> offsetItems, OrientEnum orient) {
        List<GcOffSetVchrItemDTO> matchedList = offsetItems.stream().filter(record -> orient.equals((Object)record.getOrient())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(matchedList)) {
            matchedList = new ArrayList<GcOffSetVchrItemDTO>(offsetItems);
        }
        return matchedList;
    }

    private static void setDimValues(GcOffSetVchrItemDTO diffOffsetVchrItem, GcOffSetVchrItemDTO offsetVchrSoutceItem) {
        ManagementDimensionCacheService managementDimensionCacheService = (ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class);
        assert (managementDimensionCacheService != null);
        List managementDims = managementDimensionCacheService.getOptionalManagementDims();
        if (CollectionUtils.isEmpty(managementDims)) {
            return;
        }
        managementDims.forEach(managementDim -> GcCalcAmtCheckUtil.setOffsetVchrItemFieldValue(diffOffsetVchrItem, offsetVchrSoutceItem, managementDim.getCode().toUpperCase()));
    }

    private static void setOffsetVchrItemFieldValue(GcOffSetVchrItemDTO diffOffsetVchrItem, GcOffSetVchrItemDTO offsetVchrSoutceItem, String fieldName) {
        diffOffsetVchrItem.addFieldValue(fieldName, offsetVchrSoutceItem.getFieldValue(fieldName));
        try {
            Optional<PropertyDescriptor> matchedProperty = Stream.of(Introspector.getBeanInfo(GcOffSetVchrItemDTO.class).getPropertyDescriptors()).filter(property -> fieldName.equals(property.getName().toUpperCase())).findAny();
            if (matchedProperty.isPresent()) {
                matchedProperty.get().getWriteMethod().invoke(diffOffsetVchrItem, offsetVchrSoutceItem.getFieldValue(fieldName));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

