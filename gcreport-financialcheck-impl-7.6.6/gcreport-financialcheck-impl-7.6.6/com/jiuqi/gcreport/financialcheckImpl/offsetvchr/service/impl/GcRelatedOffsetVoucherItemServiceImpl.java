/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.gcreport.financialcheckcore.offset.enums.OffsetStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.dao.GcRelatedOffsetVoucherItemDao
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.utils.OffsetMethodEnum
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.dao.GcRelatedOffsetVoucherItemQueryDao;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.GcRelatedOffsetVoucherItemService;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckImpl.util.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.gcreport.financialcheckcore.offset.enums.OffsetStateEnum;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.dao.GcRelatedOffsetVoucherItemDao;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.utils.OffsetMethodEnum;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.offsetitem.enums.CheckStateEnum;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;

@Service
public class GcRelatedOffsetVoucherItemServiceImpl
implements GcRelatedOffsetVoucherItemService {
    @Autowired
    private GcRelatedOffsetVoucherItemDao offsetVoucherItemDao;
    @Autowired
    private GcRelatedOffsetVoucherItemQueryDao offsetVoucherItemQueryDao;
    @Autowired
    private GcRelatedItemQueryService itemQueryService;
    @Autowired
    private FinancialCheckSchemeService schemeService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private GcRelatedItemCommandService relatedItemCommandService;

    @Override
    @OuterTransaction
    public void addByCheckGroup(List<GcRelatedItemEO> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        String checkId = items.get(0).getCheckId();
        List sameCheckedGroupItems = this.itemQueryService.findByCheckIds(Arrays.asList(checkId));
        if (CollectionUtils.isEmpty((Collection)sameCheckedGroupItems)) {
            return;
        }
        Set schemeIds = items.stream().map(GcRelatedItemEO::getCheckRuleId).collect(Collectors.toSet());
        String schemeId = (String)schemeIds.iterator().next();
        if (schemeIds.size() > 1 || Objects.isNull(schemeId)) {
            throw new BusinessRuntimeException("\u751f\u6210\u62b5\u9500\u51ed\u8bc1\u7684\u5173\u8054\u4ea4\u6613\u5206\u5f55\u9700\u8981\u4e3a\u540c\u4e00\u5bf9\u8d26\u65b9\u6848");
        }
        FinancialCheckSchemeVO scheme = this.schemeService.queryCheckScheme(schemeId);
        List<GcRelatedOffsetVoucherItemEO> offsetVoucherItems = this.convertByRelatedItems(sameCheckedGroupItems, scheme);
        List srcItems = this.offsetVoucherItemDao.queryEntityByCheckGroupId(checkId);
        if (!CollectionUtils.isEmpty((Collection)srcItems)) {
            offsetVoucherItems = this.offsetVoucherDeductions(offsetVoucherItems, srcItems);
            Integer checkPeriod = items.get(0).getCheckPeriod();
            offsetVoucherItems.forEach(x -> x.setOffsetPeriod(checkPeriod));
        }
        this.offsetVoucherItemDao.addBatch(offsetVoucherItems);
    }

    private List<GcRelatedOffsetVoucherItemEO> offsetVoucherDeductions(List<GcRelatedOffsetVoucherItemEO> items, List<GcRelatedOffsetVoucherItemEO> srcItems) {
        Map<String, List<String>> subjectCode2assTypeListMap = BaseDataUtils.getSubjectCode2assTypeListMap();
        Set srcIds = srcItems.stream().map(GcRelatedOffsetVoucherItemEO::getSrcItemId).filter(Objects::nonNull).collect(Collectors.toSet());
        items = items.stream().filter(item -> !OffsetMethodEnum.NOOFFSET.getCode().equals(item.getOffsetMethod()) || !srcIds.contains(item.getSrcItemId())).collect(Collectors.toList());
        LinkedMultiValueMap<String, List> map = new LinkedMultiValueMap<String, List>();
        items.stream().forEach(item -> {
            List assTypeList = (List)MapUtils.getVal((Map)subjectCode2assTypeListMap, (Object)item.getOffsetSubject(), (Object)Collections.EMPTY_LIST);
            ArrayKey key = this.getCombinedKey((GcRelatedOffsetVoucherItemEO)item, assTypeList);
            map.add(key.toString(), (List)item);
        });
        LinkedMultiValueMap<String, List> srcMap = new LinkedMultiValueMap<String, List>();
        srcItems.stream().filter(item -> !OffsetMethodEnum.NOOFFSET.getCode().equals(item.getOffsetMethod())).forEach(item -> {
            List assTypeList = (List)MapUtils.getVal((Map)subjectCode2assTypeListMap, (Object)item.getOffsetSubject(), (Object)Collections.EMPTY_LIST);
            ArrayKey key = this.getCombinedKey((GcRelatedOffsetVoucherItemEO)item, assTypeList);
            srcMap.add(key.toString(), (List)item);
        });
        ArrayList<GcRelatedOffsetVoucherItemEO> finalItems = new ArrayList<GcRelatedOffsetVoucherItemEO>();
        srcMap.forEach((k, v) -> {
            if (!map.containsKey(k)) {
                v.forEach(item -> {
                    item.setId(UUIDUtils.newUUIDStr());
                    item.setCreditOffset(Double.valueOf(Objects.equals(item.getCreditOffset(), 0.0) ? 0.0 : -item.getCreditOffset().doubleValue()));
                    item.setDebitOffset(Double.valueOf(Objects.equals(item.getDebitOffset(), 0.0) ? 0.0 : -item.getDebitOffset().doubleValue()));
                });
                finalItems.addAll((Collection<GcRelatedOffsetVoucherItemEO>)v);
            }
        });
        map.forEach((k, v) -> {
            if (!srcMap.containsKey(k)) {
                finalItems.addAll((Collection<GcRelatedOffsetVoucherItemEO>)v);
                return;
            }
            List srcValues = (List)srcMap.get(k);
            Set srcItemIds = srcValues.stream().map(GcRelatedOffsetVoucherItemEO::getSrcItemId).filter(Objects::nonNull).collect(Collectors.toSet());
            v.sort((item1, item2) -> {
                if (!srcItemIds.contains(item1.getSrcItemId()) && srcItemIds.contains(item2.getSrcItemId())) {
                    return 1;
                }
                if (srcItemIds.contains(item1.getSrcItemId()) && !srcItemIds.contains(item2.getSrcItemId())) {
                    return -1;
                }
                return 0;
            });
            Iterator iterator = v.iterator();
            while (iterator.hasNext()) {
                GcRelatedOffsetVoucherItemEO next = (GcRelatedOffsetVoucherItemEO)iterator.next();
                List sameSrcIdItems = srcValues.stream().filter(x -> Objects.nonNull(next.getSrcItemId()) && next.getSrcItemId().equals(x.getSrcItemId())).collect(Collectors.toList());
                if (!iterator.hasNext()) {
                    sameSrcIdItems = srcValues;
                }
                if (!CollectionUtils.isEmpty((Collection)sameSrcIdItems)) {
                    BigDecimal debitSum = BigDecimal.ZERO;
                    BigDecimal creditSum = BigDecimal.ZERO;
                    for (GcRelatedOffsetVoucherItemEO item : sameSrcIdItems) {
                        debitSum = NumberUtils.sum((BigDecimal)debitSum, (double)item.getDebitOffset());
                        creditSum = NumberUtils.sum((BigDecimal)creditSum, (double)item.getCreditOffset());
                    }
                    BigDecimal srcSumAmt = NumberUtils.subtract((BigDecimal)debitSum, (BigDecimal[])new BigDecimal[]{creditSum});
                    BigDecimal amt = NumberUtils.subtract((BigDecimal)BigDecimal.valueOf(next.getDebitOffset()), (BigDecimal[])new BigDecimal[]{BigDecimal.valueOf(next.getCreditOffset())});
                    if (amt.compareTo(srcSumAmt) != 0) {
                        BigDecimal finalAmt = NumberUtils.subtract((BigDecimal)amt, (BigDecimal[])new BigDecimal[]{srcSumAmt});
                        if (Objects.equals(next.getCreditOffset(), 0.0)) {
                            next.setDebitOffset(Double.valueOf(finalAmt.doubleValue()));
                        } else {
                            next.setCreditOffset(Double.valueOf(finalAmt.negate().doubleValue()));
                        }
                        finalItems.add(next);
                    } else {
                        Set sameSrcIds = sameSrcIdItems.stream().map(GcRelatedOffsetVoucherItemEO::getSrcItemId).filter(Objects::nonNull).collect(Collectors.toSet());
                        if (!sameSrcIds.contains(next.getSrcItemId())) {
                            next.setDebitOffset(Double.valueOf(0.0));
                            next.setCreditOffset(Double.valueOf(0.0));
                            finalItems.add(next);
                        }
                    }
                    srcValues.removeAll(sameSrcIdItems);
                    continue;
                }
                finalItems.add(next);
            }
        });
        return finalItems;
    }

    private ArrayKey getCombinedKey(GcRelatedOffsetVoucherItemEO record, List<String> assTypeList) {
        ArrayList<Object> keys = new ArrayList<Object>(16);
        Collections.addAll(keys, record.getOffsetSubject());
        for (String assTypeFieldCode : assTypeList) {
            Object dimValue = record.getFieldValue(assTypeFieldCode);
            keys.add(dimValue);
        }
        return ArrayKey.of(keys);
    }

    public List<GcRelatedOffsetVoucherItemEO> convertByRelatedItems(List<GcRelatedItemEO> items, FinancialCheckSchemeVO scheme) {
        ArrayList<GcRelatedOffsetVoucherItemEO> offsetVoucherItems = new ArrayList<GcRelatedOffsetVoucherItemEO>();
        if (CollectionUtils.isEmpty(items)) {
            return offsetVoucherItems;
        }
        boolean isUnilateral = items.stream().map(GcRelatedItemEO::getUnitId).distinct().count() == 1L;
        Set<String> schemeSubjects = this.getSchemeSubjects(scheme, isUnilateral);
        List<GcRelatedOffsetVoucherItemEO> offsetVchrItems = items.stream().map(item -> this.convert((GcRelatedItemEO)item, schemeSubjects, OffsetMethodEnum.AUTO)).filter(list -> !list.isEmpty()).flatMap(Collection::stream).collect(Collectors.toList());
        offsetVchrItems.forEach(item -> item.setId(UUIDUtils.newUUIDStr()));
        offsetVoucherItems.addAll(offsetVchrItems);
        if (isUnilateral) {
            HashMap subjectMap = new HashMap();
            if (CheckModeEnum.UNILATERAL.getCode().equals(scheme.getCheckMode())) {
                List projects = scheme.getProjects();
                projects.forEach(project -> {
                    for (String subject : com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)project.getSubjects())) {
                        subjectMap.put(subject, project.getOppSubject());
                    }
                });
            } else if (CheckModeEnum.OFFSETVCHR.getCode().equals(scheme.getCheckMode())) {
                List settings = scheme.getUnilateralSubSettings();
                settings.forEach(setting -> {
                    for (String subject : com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)setting.getSubjects())) {
                        subjectMap.put(subject, setting.getOppSubject());
                    }
                });
            }
            offsetVchrItems.stream().filter(item -> !OffsetMethodEnum.NOOFFSET.getCode().equals(item.getOffsetMethod())).forEach(item -> {
                if (subjectMap.containsKey(item.getOffsetSubject())) {
                    String oppSubject = (String)subjectMap.get(item.getOffsetSubject());
                    if (Objects.isNull(oppSubject)) {
                        throw new BusinessRuntimeException(item.getOffsetSubject() + "\u7684\u751f\u6210\u5bf9\u65b9\u79d1\u76ee\u672a\u914d\u7f6e\uff0c\u8bf7\u68c0\u67e5\u5bf9\u8d26\u65b9\u6848");
                    }
                    GcRelatedOffsetVoucherItemEO oppItems = new GcRelatedOffsetVoucherItemEO();
                    BeanUtils.copyProperties(item, oppItems);
                    oppItems.resetFields(item.getFields());
                    oppItems.setId(UUIDUtils.newUUIDStr());
                    oppItems.setOffsetSubject(oppSubject);
                    oppItems.setSrcItemId(null);
                    oppItems.setCreditOffset(item.getDebitOffset());
                    oppItems.setDebitOffset(item.getCreditOffset());
                    oppItems.setCreditSrc(item.getDebitSrc());
                    oppItems.setDebitSrc(item.getCreditSrc());
                    oppItems.setUnitId(item.getOppUnitId());
                    oppItems.setOppUnitId(item.getUnitId());
                    offsetVoucherItems.add(oppItems);
                }
            });
        }
        if (!CheckModeEnum.OFFSETVCHR.getCode().equals(scheme.getCheckMode()) || isUnilateral) {
            this.distributionDiffAmt(offsetVoucherItems);
            return offsetVoucherItems;
        }
        ArrayList<GcRelatedOffsetVoucherItemEO> allOffsetVoucherItems = new ArrayList<GcRelatedOffsetVoucherItemEO>(4);
        Iterator iterator = scheme.getBilateralSubSettings().iterator();
        while (iterator.hasNext()) {
            FinancialCheckBilateralSubSettingDTO next = (FinancialCheckBilateralSubSettingDTO)iterator.next();
            if (!iterator.hasNext()) {
                this.distributionDiffAmt(offsetVoucherItems);
                allOffsetVoucherItems.addAll(offsetVoucherItems);
                continue;
            }
            List subjects = next.getSubjects();
            subjects.addAll(next.getDebtSubjects());
            Set groupSubjects = com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)subjects);
            List<GcRelatedOffsetVoucherItemEO> groupItems = offsetVoucherItems.stream().filter(x -> groupSubjects.contains(x.getOffsetSubject())).collect(Collectors.toList());
            this.distributionDiffAmt(groupItems);
            allOffsetVoucherItems.addAll(groupItems);
            offsetVoucherItems.removeAll(groupItems);
        }
        return allOffsetVoucherItems;
    }

    private Set<String> getSchemeSubjects(FinancialCheckSchemeVO scheme, boolean isUnilateral) {
        HashSet<String> schemeSubjects = new HashSet<String>();
        if (CheckModeEnum.OFFSETVCHR.getCode().equals(scheme.getCheckMode())) {
            if (isUnilateral) {
                List unilateralSubSettings = scheme.getUnilateralSubSettings();
                if (!CollectionUtils.isEmpty((Collection)unilateralSubSettings)) {
                    HashSet allUnilateralSubjects = new HashSet();
                    unilateralSubSettings.forEach(x -> {
                        if (!CollectionUtils.isEmpty((Collection)x.getSubjects())) {
                            allUnilateralSubjects.addAll(x.getSubjects());
                        }
                    });
                    if (!CollectionUtils.isEmpty(allUnilateralSubjects)) {
                        return com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", allUnilateralSubjects);
                    }
                }
            } else {
                List subSettings = scheme.getBilateralSubSettings();
                if (!CollectionUtils.isEmpty((Collection)subSettings)) {
                    HashSet allBilaterSubjects = new HashSet();
                    subSettings.forEach(x -> {
                        if (!CollectionUtils.isEmpty((Collection)x.getSubjects())) {
                            allBilaterSubjects.addAll(x.getSubjects());
                            allBilaterSubjects.addAll(x.getDebtSubjects());
                        }
                    });
                    if (!CollectionUtils.isEmpty(allBilaterSubjects)) {
                        return com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", allBilaterSubjects);
                    }
                }
            }
        } else {
            List projects = scheme.getProjects();
            projects.forEach(project -> {
                for (String subject : com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)project.getSubjects())) {
                    schemeSubjects.add(subject);
                }
            });
        }
        return schemeSubjects;
    }

    private void distributionDiffAmt(List<GcRelatedOffsetVoucherItemEO> offsetVoucherItems) {
        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;
        BigDecimal ZERO = BigDecimal.ZERO;
        for (GcRelatedOffsetVoucherItemEO item2 : offsetVoucherItems) {
            debitSum = NumberUtils.sum((BigDecimal)debitSum, (BigDecimal)this.safeToBigDecimal(item2.getDebitSrc()));
            creditSum = NumberUtils.sum((BigDecimal)creditSum, (BigDecimal)this.safeToBigDecimal(item2.getCreditSrc()));
        }
        if (!debitSum.equals(creditSum)) {
            boolean isDebitLess;
            boolean bl = isDebitLess = debitSum.compareTo(creditSum) < 0;
            if (isDebitLess && creditSum.compareTo(ZERO) == 0 || !isDebitLess && debitSum.compareTo(ZERO) == 0) {
                offsetVoucherItems.forEach(item -> this.setOffset((GcRelatedOffsetVoucherItemEO)item, !isDebitLess, 0.0));
                return;
            }
            BigDecimal diff = isDebitLess ? creditSum.subtract(debitSum) : debitSum.subtract(creditSum);
            offsetVoucherItems.sort(Collections.reverseOrder(this.createComparator(isDebitLess)));
            Iterator<GcRelatedOffsetVoucherItemEO> iterator = offsetVoucherItems.iterator();
            while (iterator.hasNext()) {
                GcRelatedOffsetVoucherItemEO item3 = iterator.next();
                if (diff.compareTo(BigDecimal.ZERO) == 0) break;
                BigDecimal srcValue = this.safeToBigDecimal(isDebitLess ? item3.getCreditSrc() : item3.getDebitSrc());
                if (srcValue.compareTo(ZERO) == 0) continue;
                if (srcValue.compareTo(ZERO) > 0) {
                    if (diff.compareTo(srcValue) >= 0) {
                        this.setOffset(item3, isDebitLess, ZERO.doubleValue());
                        diff = diff.subtract(srcValue);
                    } else {
                        BigDecimal offset = srcValue.subtract(diff).negate();
                        this.setOffset(item3, isDebitLess, offset.doubleValue());
                        diff = BigDecimal.ZERO;
                    }
                } else {
                    BigDecimal offset = srcValue.subtract(diff).negate();
                    this.setOffset(item3, isDebitLess, offset.doubleValue());
                    diff = BigDecimal.ZERO;
                }
                if (iterator.hasNext() || diff.compareTo(BigDecimal.ZERO) == 0) continue;
                double currentOffset = isDebitLess ? item3.getCreditOffset() : item3.getDebitOffset();
                this.setOffset(item3, isDebitLess, NumberUtils.sum((BigDecimal)BigDecimal.valueOf(currentOffset), (BigDecimal)diff).doubleValue());
                diff = BigDecimal.ZERO;
            }
        }
    }

    private void setOffset(GcRelatedOffsetVoucherItemEO item, boolean isDebitLess, double offset) {
        if (isDebitLess) {
            item.setCreditOffset(Double.valueOf(offset));
        } else {
            item.setDebitOffset(Double.valueOf(offset));
        }
    }

    private Comparator<GcRelatedOffsetVoucherItemEO> createComparator(boolean isDebitLess) {
        return (o1, o2) -> {
            BigDecimal v1 = this.safeToBigDecimal(isDebitLess ? o1.getCreditSrc() : o1.getDebitSrc());
            BigDecimal v2 = this.safeToBigDecimal(isDebitLess ? o2.getCreditSrc() : o2.getDebitSrc());
            if (v1.compareTo(BigDecimal.ZERO) == 0 && v2.compareTo(BigDecimal.ZERO) != 0) {
                return 1;
            }
            if (v1.compareTo(BigDecimal.ZERO) != 0 && v2.compareTo(BigDecimal.ZERO) == 0) {
                return -1;
            }
            return v1.compareTo(v2);
        };
    }

    private BigDecimal safeToBigDecimal(Double value) {
        return value == null ? BigDecimal.ZERO : BigDecimal.valueOf(value);
    }

    private List<GcRelatedOffsetVoucherItemEO> convert(GcRelatedItemEO item, Set<String> schemeSubjects, OffsetMethodEnum offsetMethod) {
        ArrayList<GcRelatedOffsetVoucherItemEO> offsetVoucherItemS = new ArrayList<GcRelatedOffsetVoucherItemEO>();
        boolean isCfItem = VchrSrcWayEnum.DATASYNC_CF.name().equals(item.getInputWay());
        ArrayList<String> offsetSubjects = new ArrayList<String>();
        if (isCfItem) {
            offsetSubjects.add(item.getCfItemCode());
        } else {
            offsetSubjects.add(item.getSubjectCode());
            if (StringUtils.hasText(item.getCfItemCode()) && !"#".equals(item.getCfItemCode())) {
                offsetSubjects.add(item.getCfItemCode());
            }
        }
        List realOffsetSubjects = offsetSubjects.stream().filter(schemeSubjects::contains).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(realOffsetSubjects)) {
            offsetVoucherItemS.add(this.convert(item, (String)offsetSubjects.get(0), OffsetMethodEnum.NOOFFSET, ""));
        } else {
            Iterator iterator = realOffsetSubjects.iterator();
            while (iterator.hasNext()) {
                String offsetSubject;
                GcRelatedOffsetVoucherItemEO offsetVoucherItem = this.convert(item, offsetSubject, offsetMethod, (offsetSubject = (String)iterator.next()).equals(item.getSubjectCode()) ? "" : "\u73b0\u6d41\u9879\u76ee\u81ea\u52a8\u751f\u6210");
                offsetVoucherItemS.add(offsetVoucherItem);
            }
        }
        return offsetVoucherItemS;
    }

    private GcRelatedOffsetVoucherItemEO convert(GcRelatedItemEO item, String offsetSubject, OffsetMethodEnum offsetMethod, String remark) {
        GcRelatedOffsetVoucherItemEO offsetVoucherItem = new GcRelatedOffsetVoucherItemEO();
        offsetVoucherItem.setOffsetMethod(offsetMethod.getCode());
        offsetVoucherItem.setUnitId(item.getUnitId());
        offsetVoucherItem.setOppUnitId(item.getOppUnitId());
        offsetVoucherItem.setCheckId(item.getCheckId());
        offsetVoucherItem.setOffsetCurrency(item.getChkCurr());
        offsetVoucherItem.setAcctPeriod(item.getAcctPeriod());
        offsetVoucherItem.setAcctYear(item.getAcctYear());
        offsetVoucherItem.setOffsetSubject(offsetSubject);
        offsetVoucherItem.setOffsetPeriod(item.getCheckPeriod());
        offsetVoucherItem.setSrcItemId(item.getId());
        offsetVoucherItem.setRemark(remark);
        offsetVoucherItem.setGcNumber(item.getGcNumber());
        if (OffsetMethodEnum.NOOFFSET.equals((Object)offsetMethod)) {
            offsetVoucherItem.setCreditSrc(Double.valueOf(0.0));
            offsetVoucherItem.setDebitSrc(Double.valueOf(0.0));
            offsetVoucherItem.setCreditOffset(Double.valueOf(0.0));
            offsetVoucherItem.setDebitOffset(Double.valueOf(0.0));
        } else {
            offsetVoucherItem.setCreditSrc(item.getCreditOrig());
            offsetVoucherItem.setDebitSrc(item.getDebitOrig());
            offsetVoucherItem.setCreditOffset(Double.valueOf(Objects.equals(item.getChkAmtC(), 0.0) ? item.getChkAmtC() : -item.getChkAmtC().doubleValue()));
            offsetVoucherItem.setDebitOffset(Double.valueOf(Objects.equals(item.getChkAmtD(), 0.0) ? item.getChkAmtD() : -item.getChkAmtD().doubleValue()));
        }
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        if (!CollectionUtils.isEmpty((Collection)dimensions)) {
            List dimensionCodes = dimensions.stream().map(DimensionEO::getCode).collect(Collectors.toList());
            for (String dimensionCode : dimensionCodes) {
                offsetVoucherItem.addFieldValue(dimensionCode, item.getFieldValue(dimensionCode.toUpperCase()));
            }
        }
        return offsetVoucherItem;
    }

    @Override
    @OuterTransaction
    public void deleteByCheckIdAndOffsetPeriod(Collection<String> checkIds, Integer offsetPeriod) {
        this.offsetVoucherItemDao.deleteByCheckIdsAndOffsetPeriod(checkIds, offsetPeriod);
    }

    @Override
    public List<GcRelatedOffsetVoucherItemEO> queryByOffsetCondition(BalanceCondition queryCondition) {
        return this.offsetVoucherItemQueryDao.queryByOffsetCondition(queryCondition);
    }

    public static List<GcFcRuleUnOffsetDataDTO> convert2UnOffsetData(List<GcRelatedOffsetVoucherItemEO> items) {
        ArrayList<GcFcRuleUnOffsetDataDTO> offsetRelatedItemList = new ArrayList<GcFcRuleUnOffsetDataDTO>();
        List dimensionS = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsByTableName("GC_RELATED_ITEM");
        for (GcRelatedOffsetVoucherItemEO item : items) {
            GcFcRuleUnOffsetDataDTO unOffsetDataDTO = new GcFcRuleUnOffsetDataDTO();
            BeanUtils.copyProperties(item, unOffsetDataDTO);
            unOffsetDataDTO.setSubjectCode(item.getOffsetSubject());
            unOffsetDataDTO.setAcctPeriod(item.getOffsetPeriod());
            unOffsetDataDTO.setOriginalCurr(item.getOffsetCurrency());
            if (!org.springframework.util.CollectionUtils.isEmpty(dimensionS)) {
                List dimensionCodes = dimensionS.stream().map(DimensionEO::getCode).collect(Collectors.toList());
                for (String dimensionCode : dimensionCodes) {
                    unOffsetDataDTO.addFieldValue(dimensionCode, item.getFieldValue(dimensionCode.toUpperCase()));
                }
            }
            unOffsetDataDTO.setOffsetState(OffsetStateEnum.NOTOFFSET.getValue());
            if (!NumberUtils.isZreo((Double)item.getDebitOffset())) {
                unOffsetDataDTO.setDc(OrientEnum.D.getValue());
                unOffsetDataDTO.setAmt(item.getDebitOffset());
            } else {
                unOffsetDataDTO.setDc(OrientEnum.C.getValue());
                unOffsetDataDTO.setAmt(item.getCreditOffset());
            }
            unOffsetDataDTO.setOffsetCurrency(item.getOffsetCurrency());
            unOffsetDataDTO.setConversionRate(Double.valueOf(1.0));
            if (Objects.isNull(item.getFieldValue("VCHROFFSETRELS"))) {
                RelatedItemGcOffsetRelDTO relatedItemOffsetRelDTO = new RelatedItemGcOffsetRelDTO();
                relatedItemOffsetRelDTO.setId(UUIDOrderUtils.newUUIDStr());
                relatedItemOffsetRelDTO.setRelatedItemId(item.getId());
                relatedItemOffsetRelDTO.setChkState(CheckStateEnum.CHECKED.getCode());
                ArrayList<RelatedItemGcOffsetRelDTO> vchrOffsetRelEOS = new ArrayList<RelatedItemGcOffsetRelDTO>(Arrays.asList(relatedItemOffsetRelDTO));
                unOffsetDataDTO.addFieldValue("VCHROFFSETRELS", vchrOffsetRelEOS);
            } else {
                unOffsetDataDTO.addFieldValue("VCHROFFSETRELS", item.getFieldValue("VCHROFFSETRELS"));
            }
            offsetRelatedItemList.add(unOffsetDataDTO);
        }
        return offsetRelatedItemList;
    }

    @Override
    public List<GcRelatedOffsetVoucherInfoVO> queryOffSetVoucherInfo(String checkGroupId) {
        Assert.isNotEmpty((String)checkGroupId, (String)"\u5bf9\u8d26\u5206\u7ec4Id\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        ArrayList offsetVoucherInfoS = new ArrayList();
        List offsetVchrItems = this.offsetVoucherItemDao.queryEntityByCheckGroupId(checkGroupId);
        List items = this.itemQueryService.findByCheckIds(Arrays.asList(checkGroupId));
        Map<String, List<String>> subjectDimsMap = BaseDataUtils.getSubjectCode2assTypeListMap();
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        Map<String, DimensionEO> dimsMap = dimensions.stream().collect(Collectors.toMap(DimensionEO::getCode, Function.identity()));
        return this.convert2Vo(offsetVchrItems, items, subjectDimsMap, dimsMap);
    }

    @Override
    public List<GcRelatedOffsetVoucherInfoVO> queryByClbrCode(String clbrCode) {
        ArrayList<GcRelatedOffsetVoucherInfoVO> gcRelatedOffsetVoucherInfoVOS = new ArrayList<GcRelatedOffsetVoucherInfoVO>();
        List gcRelatedItemEOS = this.itemQueryService.queryByGcNumber(clbrCode);
        Map<String, List<GcRelatedItemEO>> stateMap = gcRelatedItemEOS.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getChkState));
        List<GcRelatedItemEO> checkedItems = stateMap.get(CheckStateEnum.CHECKED.getCode());
        if (!CollectionUtils.isEmpty(checkedItems)) {
            Set checkIds = checkedItems.stream().map(GcRelatedItemEO::getCheckId).collect(Collectors.toSet());
            for (String checkId : checkIds) {
                gcRelatedOffsetVoucherInfoVOS.addAll(this.queryOffSetVoucherInfo(checkId));
            }
        }
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        List<GcRelatedItemEO> uncheckedItems = stateMap.get(CheckStateEnum.UNCHECKED.getCode());
        ArrayList<GcRelatedOffsetVoucherInfoVO> unCheckRelatedOffsetVoucherInfoVOS = new ArrayList<GcRelatedOffsetVoucherInfoVO>();
        if (!CollectionUtils.isEmpty(uncheckedItems)) {
            for (GcRelatedItemEO item : uncheckedItems) {
                GcRelatedOffsetVoucherInfoVO info = new GcRelatedOffsetVoucherInfoVO();
                GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(item.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)item.getAcctPeriod())));
                GcOrgCacheVO org = instance.getOrgByCode(item.getUnitId());
                info.setOrg(Objects.nonNull(org) ? org.getTitle() : item.getUnitId());
                info.setPeriod(item.getAcctYear() + "\u5e74" + item.getAcctPeriod() + "\u6708");
                info.setVchrNum(item.getVchrNum());
                GcBaseData subject = baseDataCenterTool.queryBasedataByCode("MD_ACCTSUBJECT", item.getSubjectCode());
                info.setSubjectCode(Objects.nonNull(subject) ? subject.getTitle() : item.getSubjectCode());
                info.setDebit(item.getDebitOrig());
                info.setCredit(item.getCreditOrig());
                info.setDigest(item.getDigest());
                info.setRecordTimestamp(item.getRecordTimestamp());
                info.setOffsetSubject(new HashMap());
                info.setUnitId(new HashMap());
                unCheckRelatedOffsetVoucherInfoVOS.add(info);
            }
        }
        gcRelatedOffsetVoucherInfoVOS.addAll(unCheckRelatedOffsetVoucherInfoVOS);
        return gcRelatedOffsetVoucherInfoVOS;
    }

    public List<GcRelatedOffsetVoucherInfoVO> convert2Vo(List<GcRelatedOffsetVoucherItemEO> offsetVchrItems, List<GcRelatedItemEO> items, Map<String, List<String>> subjectDimsMap, Map<String, DimensionEO> dimensionMap) {
        ArrayList<GcRelatedOffsetVoucherInfoVO> infos = new ArrayList<GcRelatedOffsetVoucherInfoVO>();
        if (CollectionUtils.isEmpty(offsetVchrItems) || CollectionUtils.isEmpty(items)) {
            return infos;
        }
        Map itemMap = items.stream().collect(Collectors.toMap(DefaultTableEntity::getId, Function.identity()));
        GcBaseDataCenterTool baseDataCenterTool = GcBaseDataCenterTool.getInstance();
        offsetVchrItems.forEach(offsetVchrItem -> {
            GcRelatedOffsetVoucherInfoVO info = new GcRelatedOffsetVoucherInfoVO();
            BeanUtils.copyProperties(offsetVchrItem, info);
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(offsetVchrItem.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)offsetVchrItem.getAcctPeriod())));
            GcOrgCacheVO unit = instance.getOrgByCode(offsetVchrItem.getUnitId());
            HashMap<String, String> unitMap = new HashMap<String, String>(2);
            unitMap.put("code", Objects.nonNull(unit) ? unit.getCode() : offsetVchrItem.getUnitId());
            unitMap.put("title", Objects.nonNull(unit) ? unit.getTitle() : offsetVchrItem.getUnitId());
            info.setUnitId(unitMap);
            info.setOffsetPeriodStr(info.getAcctYear() + "\u5e74" + info.getOffsetPeriod() + "\u6708");
            GcBaseData offsetSubject = baseDataCenterTool.queryBasedataByCode("MD_ACCTSUBJECT", offsetVchrItem.getOffsetSubject());
            HashMap<String, String> subjectMap = new HashMap<String, String>(2);
            subjectMap.put("code", Objects.nonNull(offsetSubject) ? offsetSubject.getCode() : offsetVchrItem.getOffsetSubject());
            subjectMap.put("title", Objects.nonNull(offsetSubject) ? offsetSubject.getTitle() : offsetVchrItem.getOffsetSubject());
            info.setOffsetSubject(subjectMap);
            info.setOffsetMethod(OffsetMethodEnum.codeOf((String)offsetVchrItem.getOffsetMethod()).getName());
            List dims = (List)subjectDimsMap.get(offsetVchrItem.getOffsetSubject());
            HashMap<String, Object> dimsMap = new HashMap<String, Object>();
            if (!CollectionUtils.isEmpty((Collection)dims)) {
                for (String dim : dims) {
                    if (Objects.isNull(offsetVchrItem.getFieldValue(dim)) || Objects.isNull(dimensionMap.get(dim))) continue;
                    DimensionEO dimension = (DimensionEO)dimensionMap.get(dim);
                    String dimValue = (String)offsetVchrItem.getFieldValue(dim);
                    if (!StringUtils.isEmpty(dimension.getReferField())) {
                        GcBaseData dimBaseData = baseDataCenterTool.queryBasedataByCode(dimension.getReferField(), dimValue);
                        if (!Objects.nonNull(dimBaseData)) continue;
                        HashMap<String, String> dimMap = new HashMap<String, String>(2);
                        dimMap.put("code", dimBaseData.getCode());
                        dimMap.put("title", dimBaseData.getTitle());
                        dimsMap.put(dim, dimMap);
                        continue;
                    }
                    dimsMap.put(dim, dimValue);
                }
            }
            info.setDims(dimsMap);
            GcRelatedItemEO item = (GcRelatedItemEO)itemMap.get(offsetVchrItem.getSrcItemId());
            if (Objects.nonNull(item)) {
                GcOrgCacheVO org = instance.getOrgByCode(item.getUnitId());
                info.setOrg(Objects.nonNull(org) ? org.getTitle() : item.getUnitId());
                info.setPeriod(item.getAcctYear() + "\u5e74" + item.getAcctPeriod() + "\u6708");
                info.setVchrNum(item.getVchrNum());
                if (VchrSrcWayEnum.DATASYNC_CF.name().equals(item.getInputWay())) {
                    info.setSubjectCode("");
                } else {
                    GcBaseData subject = baseDataCenterTool.queryBasedataByCode("MD_ACCTSUBJECT", item.getSubjectCode());
                    info.setSubjectCode(Objects.nonNull(subject) ? subject.getTitle() : item.getSubjectCode());
                }
                String cfItemCode = item.getCfItemCode();
                if (StringUtils.hasText(cfItemCode) && !"#".equals(cfItemCode)) {
                    GcBaseData cfItem = baseDataCenterTool.queryBasedataByCode("MD_ACCTSUBJECT", cfItemCode);
                    info.setCfItemCode(Objects.nonNull(cfItem) ? cfItem.getTitle() : cfItemCode);
                }
                info.setDebit(item.getDebitOrig());
                info.setCredit(item.getCreditOrig());
                info.setDigest(item.getDigest());
                info.setRecordTimestamp(item.getRecordTimestamp());
            }
            infos.add(info);
        });
        infos.sort(Comparator.comparing(item -> item.getVchrNum(), Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(item -> item.getSrcItemId(), Comparator.nullsLast(Comparator.naturalOrder())));
        String previousSrcId = null;
        for (GcRelatedOffsetVoucherInfoVO info : infos) {
            if (Objects.equals(info.getSrcItemId(), previousSrcId)) {
                info.setOrg("");
                info.setPeriod("");
                info.setVchrNum("");
                info.setSubjectCode("");
                info.setDebit(Double.valueOf(0.0));
                info.setCredit(Double.valueOf(0.0));
                info.setDigest("");
                info.setCfItemCode("");
                continue;
            }
            previousSrcId = info.getSrcItemId();
        }
        return infos;
    }

    @Override
    public List<GcRelatedOffsetVoucherInfoVO> queryManualOffsetResult(ManualCheckParam param) {
        Set itemIds = param.getItemIds();
        List items = this.itemQueryService.queryByIds((Collection)itemIds);
        if (items.stream().anyMatch(item -> CheckStateEnum.CHECKED.getCode().equals(item.getChkState()))) {
            throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u53d1\u751f\u53d8\u5316\uff0c\u8bf7\u5237\u65b0\u91cd\u8bd5");
        }
        Integer dataMaxPeriod = items.stream().map(GcRelatedItemEO::getAcctPeriod).max(Integer::compareTo).get();
        int maxPeriod = UnitStateUtils.getUnitOpenAccountPeriod(((GcRelatedItemEO)items.get(0)).getUnitId(), ((GcRelatedItemEO)items.get(0)).getOppUnitId(), param.getYear(), dataMaxPeriod, param.getPeriod());
        items.forEach(item -> {
            item.setChkAmtD(item.getDebitOrig());
            item.setChkAmtC(item.getCreditOrig());
            item.setCheckPeriod(Integer.valueOf(maxPeriod));
            item.setChkCurr(item.getOriginalCurr());
        });
        Set offsetSubjects = items.stream().map(GcRelatedItemEO::getSubjectCode).collect(Collectors.toSet());
        List<GcRelatedOffsetVoucherItemEO> offsetVchrItems = items.stream().map(item -> this.convert((GcRelatedItemEO)item, offsetSubjects, OffsetMethodEnum.ADJUST)).filter(list -> !list.isEmpty()).flatMap(Collection::stream).collect(Collectors.toList());
        this.distributionDiffAmt(offsetVchrItems);
        Map<String, List<String>> subjectDimsMap = BaseDataUtils.getSubjectCode2assTypeListMap();
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        Map<String, DimensionEO> dimsMap = dimensions.stream().collect(Collectors.toMap(DimensionEO::getCode, Function.identity()));
        return this.convert2Vo(offsetVchrItems, items, subjectDimsMap, dimsMap);
    }

    @Override
    @OuterTransaction
    public void saveRelatedOffsetVchrInfo(List<GcRelatedOffsetVoucherInfoVO> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        Map<String, Long> srcItemMap = items.stream().filter(item -> Objects.nonNull(item.getSrcItemId())).collect(Collectors.toMap(GcRelatedOffsetVoucherInfoVO::getSrcItemId, GcRelatedOffsetVoucherInfoVO::getRecordTimestamp, (a, b) -> a));
        if (srcItemMap.isEmpty()) {
            throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u6570\u636e\u53d1\u751f\u53d8\u5316,\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        Set<String> srcIds = srcItemMap.keySet();
        List srcItems = this.itemQueryService.queryByIds(srcIds);
        if (CollectionUtils.isEmpty((Collection)srcItems) || srcItems.size() != srcIds.size()) {
            throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u6570\u636e\u53d1\u751f\u53d8\u5316,\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        List dbCheckStates = srcItems.stream().map(GcRelatedItemEO::getChkState).distinct().collect(Collectors.toList());
        if (srcItems.stream().anyMatch(srcItem -> !Objects.equals(srcItemMap.get(srcItem.getId()), srcItem.getRecordTimestamp())) || dbCheckStates.size() > 1) {
            throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u6570\u636e\u53d1\u751f\u53d8\u5316,\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        String dbCheckState = (String)dbCheckStates.get(0);
        this.checkItems(items);
        if (CheckStateEnum.UNCHECKED.getCode().equals(dbCheckState)) {
            String checkId = UUIDOrderUtils.newUUIDStr();
            items.forEach(item -> item.setCheckId(checkId));
            List<GcRelatedOffsetVoucherItemEO> offsetVoucherItems = this.disposeOffsetVchrInfo(items);
            offsetVoucherItems.forEach(item -> item.setId(UUIDUtils.newUUIDStr()));
            this.offsetVoucherItemDao.addBatch(offsetVoucherItems);
            Integer maxPeriod = offsetVoucherItems.stream().map(GcRelatedOffsetVoucherItemEO::getOffsetPeriod).max(Integer::compareTo).get();
            this.setCheckInfo(srcItems, checkId, maxPeriod);
            this.relatedItemCommandService.doCheck(srcItems, false);
        } else {
            String checkId = items.get(0).getCheckId();
            List<GcRelatedOffsetVoucherItemEO> offsetVoucherItems = this.disposeOffsetVchrInfo(items);
            List dbOffsetVoucherItemS = this.offsetVoucherItemDao.queryEntityByCheckGroupId(checkId);
            Set targetItemIds = items.stream().filter(Objects::nonNull).map(GcRelatedOffsetVoucherInfoVO::getId).collect(Collectors.toSet());
            List needDeleteItems = dbOffsetVoucherItemS.stream().filter(x -> !targetItemIds.contains(x.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(needDeleteItems)) {
                this.offsetVoucherItemDao.deleteBatch(needDeleteItems);
            }
            ArrayList needUpdateItems = new ArrayList();
            ArrayList needAddItems = new ArrayList();
            offsetVoucherItems.forEach(item -> {
                if (StringUtils.hasText(item.getId())) {
                    if (OffsetMethodEnum.ADJUST.getCode().equals(item.getOffsetMethod())) {
                        needUpdateItems.add(item);
                    }
                } else {
                    item.setId(UUIDUtils.newUUIDStr());
                    needAddItems.add(item);
                }
            });
            if (!CollectionUtils.isEmpty(needUpdateItems)) {
                this.offsetVoucherItemDao.updateRelatedOffsetVoucherItemInfo(needUpdateItems);
            }
            if (!CollectionUtils.isEmpty(needAddItems)) {
                needAddItems.forEach(x -> x.setCheckId(checkId));
                this.offsetVoucherItemDao.addBatch(needAddItems);
            }
        }
    }

    private void checkItems(List<GcRelatedOffsetVoucherInfoVO> items) {
        HashSet<String> units = new HashSet<String>();
        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;
        for (GcRelatedOffsetVoucherInfoVO item : items) {
            debitSum = NumberUtils.sum((BigDecimal)debitSum, (double)item.getDebitOffset());
            creditSum = NumberUtils.sum((BigDecimal)creditSum, (double)item.getCreditOffset());
            units.add((String)item.getUnitId().get("code"));
            if (!StringUtils.hasText(item.getOppUnitId())) continue;
            units.add(item.getOppUnitId());
        }
        if (debitSum.compareTo(creditSum) != 0) {
            throw new BusinessRuntimeException("\u62b5\u9500\u501f\u65b9\u91d1\u989d\u4e0e\u62b5\u9500\u8d37\u65b9\u91d1\u989d\u4e0d\u4e00\u81f4");
        }
        long unitCount = items.stream().map(x -> x.getUnitId().get("code")).distinct().count();
        if (units.size() != 2 || unitCount != 2L) {
            throw new BusinessRuntimeException("\u53c2\u4e0e\u624b\u5de5\u5bf9\u8d26\u7684\u6570\u636e\u672c\u5bf9\u65b9\u5355\u4f4d\u53ea\u80fd\u6709\u4e24\u5bb6");
        }
        for (int i = 0; i < items.size(); ++i) {
            GcRelatedOffsetVoucherInfoVO offsetVoucherInfo = items.get(i);
            if (Objects.isNull(offsetVoucherInfo.getOffsetSubject())) {
                throw new BusinessRuntimeException(String.format("\u7b2c%s\u6761\u62b5\u9500\u51ed\u8bc1\u7684\u62b5\u9500\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a", i + 1));
            }
            if (Objects.isNull(offsetVoucherInfo.getUnitId())) {
                throw new BusinessRuntimeException(String.format("\u7b2c%s\u6761\u62b5\u9500\u51ed\u8bc1\u7684\u672c\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", i + 1));
            }
            if (Objects.isNull(offsetVoucherInfo.getCreditOffset()) && Objects.isNull(offsetVoucherInfo.getDebitOffset())) {
                throw new BusinessRuntimeException(String.format("\u7b2c%s\u6761\u62b5\u9500\u51ed\u8bc1\u7684\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u80fd\u90fd\u4e3a\u7a7a", i + 1));
            }
            if (Objects.equals(offsetVoucherInfo.getCreditOffset(), 0.0) || Objects.equals(offsetVoucherInfo.getDebitOffset(), 0.0)) continue;
            throw new BusinessRuntimeException(String.format("\u7b2c%s\u6761\u62b5\u9500\u51ed\u8bc1\u7684\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u80fd\u90fd\u6709\u503c", i + 1));
        }
    }

    private List<GcRelatedOffsetVoucherItemEO> disposeOffsetVchrInfo(List<GcRelatedOffsetVoucherInfoVO> items) {
        List units = Stream.concat(items.stream().map(GcRelatedOffsetVoucherInfoVO::getOppUnitId).filter(Objects::nonNull), items.stream().map(item -> (String)item.getUnitId().get("code")).filter(Objects::nonNull)).distinct().collect(Collectors.toList());
        if (units.size() > 2) {
            throw new BusinessRuntimeException("\u5bf9\u8d26\u5355\u4f4d\u4e0d\u80fd\u8d85\u8fc7\u4e24\u5bb6");
        }
        Integer offsetPeriod = items.stream().map(GcRelatedOffsetVoucherInfoVO::getOffsetPeriod).filter(Objects::nonNull).distinct().max(Integer::compareTo).get();
        Integer acctYear = items.stream().map(GcRelatedOffsetVoucherInfoVO::getAcctYear).filter(Objects::nonNull).distinct().findFirst().get();
        String offsetCurrency = items.stream().map(GcRelatedOffsetVoucherInfoVO::getOffsetCurrency).filter(Objects::nonNull).distinct().findFirst().get();
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        ArrayList<GcRelatedOffsetVoucherItemEO> offsetVchrItems = new ArrayList<GcRelatedOffsetVoucherItemEO>();
        items.forEach(item -> {
            GcRelatedOffsetVoucherItemEO offsetVchrItem = new GcRelatedOffsetVoucherItemEO();
            BeanUtils.copyProperties(item, offsetVchrItem);
            offsetVchrItem.setUnitId((String)item.getUnitId().get("code"));
            offsetVchrItem.setOffsetSubject((String)item.getOffsetSubject().get("code"));
            if (Objects.isNull(offsetVchrItem.getOffsetPeriod())) {
                offsetVchrItem.setOffsetPeriod(offsetPeriod);
            }
            if (Objects.isNull(offsetVchrItem.getAcctYear())) {
                offsetVchrItem.setAcctYear(acctYear);
            }
            if (Objects.isNull(offsetVchrItem.getAcctPeriod())) {
                offsetVchrItem.setAcctPeriod(offsetPeriod);
            }
            if (Objects.isNull(offsetVchrItem.getOffsetCurrency())) {
                offsetVchrItem.setOffsetCurrency(offsetCurrency);
            }
            if (Objects.isNull(offsetVchrItem.getOppUnitId())) {
                offsetVchrItem.setOppUnitId(offsetVchrItem.getUnitId().equals(units.get(0)) ? (String)units.get(1) : (String)units.get(0));
            }
            offsetVchrItem.setOffsetMethod(OffsetMethodEnum.nameOf((String)offsetVchrItem.getOffsetMethod()).getCode());
            Map dims = item.getDims();
            dimensions.forEach(dimension -> {
                if (dims.containsKey(dimension.getCode())) {
                    if (Objects.nonNull(dims.get(dimension.getCode())) && !StringUtils.isEmpty(dimension.getReferField())) {
                        offsetVchrItem.addFieldValue(dimension.getCode(), ((Map)dims.get(dimension.getCode())).get("code"));
                    } else {
                        offsetVchrItem.addFieldValue(dimension.getCode(), dims.get(dimension.getCode()));
                    }
                }
            });
            offsetVchrItems.add(offsetVchrItem);
        });
        return offsetVchrItems;
    }

    private void setCheckInfo(List<GcRelatedItemEO> voucherItems, String checkId, Integer maxPeriod) {
        if (org.springframework.util.CollectionUtils.isEmpty(voucherItems)) {
            return;
        }
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String loginUserName = loginUser == null ? "system" : loginUser.getName();
        Date checkTime = Date.from(Instant.now());
        for (GcRelatedItemEO record : voucherItems) {
            record.setChkCurr(record.getOriginalCurr());
            record.setChkState(com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum.CHECKED.name());
            record.setCheckId(checkId);
            record.setCheckTime(checkTime);
            record.setCheckYear(voucherItems.get(0).getAcctYear());
            record.setCheckPeriod(maxPeriod);
            record.setChecker(loginUserName);
            record.setCheckMode(CheckModeEnum.BILATERAL.getCode());
            record.setCheckType("\u624b\u5de5\u6838\u5bf9");
            record.setChkAmtC(record.getCreditOrig());
            record.setChkAmtD(record.getDebitOrig());
        }
    }

    @Override
    public List<GcRelatedOffsetVoucherItemEO> queryByIds(Set<String> ids) {
        return this.offsetVoucherItemDao.queryByIds(ids);
    }
}

