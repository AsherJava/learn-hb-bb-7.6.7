/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.enums.OffsetStateEnum
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.financial.cache.FinancialCheckRuleCacheService;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckOffsetItemCreateHelper;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.enums.OffsetStateEnum;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FinancialCheckRuleExecutorImpl {
    private FinancialCheckRuleDTO fcRule;
    private final ConsolidatedSubjectService conSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
    private Logger logger = LoggerFactory.getLogger(FinancialCheckRuleExecutorImpl.class);
    private List<String> groupFieldList;
    GcCalcArgmentsDTO calcArgs;
    protected String reportSystemId;

    private FinancialCheckRuleExecutorImpl() {
    }

    public static FinancialCheckRuleExecutorImpl newInstance(GcCalcArgmentsDTO calcArgs, FinancialCheckRuleDTO rule, String conSystemId) {
        FinancialCheckRuleExecutorImpl ruleExecutor = new FinancialCheckRuleExecutorImpl();
        ruleExecutor.calcArgs = calcArgs;
        ruleExecutor.reportSystemId = conSystemId;
        assert (SpringContextUtils.getBean(FinancialCheckRuleCacheService.class) != null);
        ruleExecutor.fcRule = ((FinancialCheckRuleCacheService)SpringContextUtils.getBean(FinancialCheckRuleCacheService.class)).getRule(rule);
        return ruleExecutor;
    }

    private void initGroupInfo() {
        this.groupFieldList = this.fcRule.getOffsetGroupingField();
        if (CollectionUtils.isEmpty(this.groupFieldList)) {
            this.groupFieldList = new ArrayList<String>();
        }
        if (!this.groupFieldList.contains("UNITID")) {
            this.groupFieldList.add("UNITID");
        }
        if (!this.groupFieldList.contains("OPPUNITID")) {
            this.groupFieldList.add("OPPUNITID");
        }
        this.groupFieldList = this.groupFieldList.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    public List<OffsetResult> reltxnCanOffsetItems(List<GcFcRuleUnOffsetDataDTO> uncheckedList) {
        if (CollectionUtils.isEmpty(uncheckedList)) {
            return Collections.emptyList();
        }
        List flexibleFetchConfigs = this.fcRule.getFetchConfigList();
        if (CollectionUtils.isEmpty(flexibleFetchConfigs)) {
            return Collections.emptyList();
        }
        assert (this.conSubjectService != null);
        Set srcDebitSubjectCodes = this.conSubjectService.listAllChildrenCodesContainsSelf(this.fcRule.getSrcDebitSubjectCodeList(), this.fcRule.getReportSystem());
        for (GcFcRuleUnOffsetDataDTO unOffsetData : uncheckedList) {
            boolean isChecked;
            String subjectCode = unOffsetData.getSubjectCode();
            ConsolidatedSubjectEO consolidatedSubject = this.conSubjectService.getSubjectByCode(this.reportSystemId, subjectCode);
            if (Objects.isNull(consolidatedSubject)) {
                throw new BusinessRuntimeException("\u6839\u636e\u79d1\u76ee\u4ee3\u7801" + subjectCode + "\u67e5\u627e\u4e0d\u5230\u5408\u5e76\u79d1\u76ee");
            }
            if (!Objects.equals(consolidatedSubject.getOrient(), unOffsetData.getDc())) {
                unOffsetData.setAmt(Double.valueOf(BigDecimal.valueOf(unOffsetData.getAmt()).negate().doubleValue()));
            }
            if (srcDebitSubjectCodes.contains(subjectCode)) {
                unOffsetData.setDc(OrientEnum.D.getValue());
            } else {
                unOffsetData.setDc(OrientEnum.C.getValue());
            }
            if (!(isChecked = this.fcRule.isChecked()) || !ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) continue;
            unOffsetData.setAmt(Double.valueOf(BigDecimal.valueOf(unOffsetData.getAmt()).negate().doubleValue()));
        }
        this.initGroupInfo();
        Map<String, List<GcFcRuleUnOffsetDataDTO>> group = this.groupByOffsetCondition(uncheckedList);
        ArrayList<OffsetResult> OffsetResults = new ArrayList<OffsetResult>();
        group.forEach((condGroupKey, sameGroupList) -> {
            String offsetGroupId = UUIDUtils.newUUIDStr();
            for (int i = 0; i < flexibleFetchConfigs.size(); ++i) {
                FinancialCheckFetchConfig flexibleFetchConfig = (FinancialCheckFetchConfig)flexibleFetchConfigs.get(i);
                OffsetResult offsetResult = this.tryOffsetByFetchConfig((List<GcFcRuleUnOffsetDataDTO>)sameGroupList, flexibleFetchConfig, offsetGroupId);
                if (offsetResult.matched) {
                    offsetResult.offsetVchr.getItems().forEach(offSetVchrItem -> offSetVchrItem.setOffSetSrcType(this.fcRule.isChecked() ? OffSetSrcTypeEnum.FINANCIAL_CHECK : OffSetSrcTypeEnum.FINANCIAL_CHECK_NOCHECK));
                    OffsetResults.add(offsetResult);
                    if (i != 0 || !Boolean.TRUE.equals(this.fcRule.getReconciliationOffsetFlag())) continue;
                    sameGroupList = offsetResult.getOffsetedInputItems();
                    continue;
                }
                if (i != 0 || !Boolean.TRUE.equals(this.fcRule.getReconciliationOffsetFlag())) continue;
                return;
            }
        });
        return OffsetResults;
    }

    protected String getRecordKey(GcFcRuleUnOffsetDataDTO unOffsetData) {
        StringBuilder keyBuf = new StringBuilder();
        keyBuf.append(this.getUnitComb(unOffsetData.getUnitId(), unOffsetData.getOppUnitId()));
        this.groupFieldList.forEach(groupField -> {
            String fieldCode = groupField.toUpperCase();
            if (!"UNITID".equals(fieldCode) && !"OPPUNITID".equals(fieldCode)) {
                keyBuf.append(unOffsetData.getFieldValue(fieldCode));
            }
        });
        return keyBuf.toString();
    }

    private Map<String, List<GcFcRuleUnOffsetDataDTO>> groupByOffsetCondition(List<GcFcRuleUnOffsetDataDTO> items) {
        HashMap<String, List<GcFcRuleUnOffsetDataDTO>> group = new HashMap<String, List<GcFcRuleUnOffsetDataDTO>>(16);
        items.forEach(item -> {
            String key = this.getRecordKey((GcFcRuleUnOffsetDataDTO)item);
            List sameGroupList = group.computeIfAbsent(key, k -> new ArrayList());
            sameGroupList.add(item);
        });
        return group;
    }

    String getUnitComb(String unitId1, String unitId2) {
        StringBuilder buf = new StringBuilder();
        if (unitId1.compareTo(unitId2) <= 0) {
            return buf.append(unitId1).append(",").append(unitId2).toString();
        }
        return buf.append(unitId2).append(",").append(unitId1).toString();
    }

    private OffsetResult tryOffsetByFetchConfig(List<GcFcRuleUnOffsetDataDTO> unOffsetDataS, FinancialCheckFetchConfig fetchConfig, String offsetGroupId) {
        boolean canOffset;
        OffsetResult offsetResult = new OffsetResult();
        if (StringUtils.hasText(this.checkByOptions(unOffsetDataS))) {
            return offsetResult;
        }
        FinancialCheckOffsetItemCreateHelper offsetItemCreateHelper = FinancialCheckOffsetItemCreateHelper.newInstance(this.fcRule, this.calcArgs);
        FinancialCheckOffsetItemCreateHelper.CreateResult createResult = offsetItemCreateHelper.createOffsetItemByFetchConfig(unOffsetDataS, fetchConfig, offsetGroupId);
        boolean bl = canOffset = !CollectionUtils.isEmpty(createResult.getOffsetItems()) && (createResult.isNoDiff() || GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)this.fcRule, createResult.getOffsetItems()));
        if (canOffset) {
            offsetResult.offsetedInputItems = this.setOffsetInfo(createResult.getUnOffsetDataS(), offsetGroupId);
            GcOffSetVchrDTO offsetVchr = new GcOffSetVchrDTO();
            ArrayList<GcOffSetVchrItemDTO> items = new ArrayList<GcOffSetVchrItemDTO>(createResult.getOffsetItems());
            offsetVchr.setItems(items);
            offsetResult.offsetVchr = offsetVchr;
            offsetResult.matched = true;
            return offsetResult;
        }
        return offsetResult;
    }

    protected String checkByOptions(Collection<GcFcRuleUnOffsetDataDTO> list) {
        if (CollectionUtils.isEmpty(this.fcRule.getFetchConfigList())) {
            Object[] args = new String[]{this.fcRule.getTitle()};
            return GcI18nUtil.getMessage((String)"gc.calculate.flexiblerule.rulenotfetchsettingsmsg", (Object[])args);
        }
        if (CollectionUtils.isEmpty(list)) {
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notoffsetdatamsg");
        }
        boolean sameOrient = this.isSameOrient(list);
        if (sameOrient && !Boolean.TRUE.equals(this.fcRule.getUnilateralOffsetFlag())) {
            Object[] args = new String[]{this.fcRule.getLocalizedName()};
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notsupportedunilateraloffset", (Object[])args);
        }
        return "";
    }

    private boolean isSameOrient(Collection<GcFcRuleUnOffsetDataDTO> list) {
        if (CollectionUtils.isEmpty(list) || list.size() == 1) {
            return true;
        }
        Iterator<GcFcRuleUnOffsetDataDTO> it = list.iterator();
        int firstItemDc = it.next().getDc();
        while (it.hasNext()) {
            GcFcRuleUnOffsetDataDTO inputItem = it.next();
            if (firstItemDc == inputItem.getDc()) continue;
            return false;
        }
        return true;
    }

    private List<GcFcRuleUnOffsetDataDTO> setOffsetInfo(Collection<GcFcRuleUnOffsetDataDTO> inputItemsList, String offsetGroupId) {
        if (CollectionUtils.isEmpty(inputItemsList)) {
            return Collections.emptyList();
        }
        List unOffsetItem = inputItemsList.stream().filter(inputItem -> OffsetStateEnum.NOTOFFSET.getValue().equals(inputItem.getOffsetState())).collect(Collectors.toList());
        ArrayList<GcFcRuleUnOffsetDataDTO> offsetedInputDataList = new ArrayList<GcFcRuleUnOffsetDataDTO>();
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String loginUserName = loginUser == null ? "system" : loginUser.getName();
        offsetedInputDataList.addAll(unOffsetItem);
        for (GcFcRuleUnOffsetDataDTO item : unOffsetItem) {
            item.setOffsetTime(new Date());
            item.setOffsetGroupId(offsetGroupId);
            item.setOffsetState(OffsetStateEnum.OFFSET.getValue());
            item.setOffsetPerson(loginUserName);
        }
        return offsetedInputDataList;
    }

    public static List<GcFcRuleUnOffsetDataDTO> convertOffset2RuleUnOffsetData(List<GcOffsetRelatedItemEO> uncheckedList) {
        ArrayList<GcFcRuleUnOffsetDataDTO> offsetRelatedItemList = new ArrayList<GcFcRuleUnOffsetDataDTO>();
        List dimensionS = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsByTableName("GC_RELATED_ITEM");
        for (GcOffsetRelatedItemEO uncheckedItem : uncheckedList) {
            GcFcRuleUnOffsetDataDTO unOffsetDataDTO = new GcFcRuleUnOffsetDataDTO();
            BeanUtils.copyProperties(uncheckedItem, unOffsetDataDTO);
            unOffsetDataDTO.setChkState(uncheckedItem.getCheckState());
            if (!CollectionUtils.isEmpty(dimensionS)) {
                List dimensionCodes = dimensionS.stream().map(DimensionEO::getCode).collect(Collectors.toList());
                for (String dimensionCode : dimensionCodes) {
                    unOffsetDataDTO.addFieldValue(dimensionCode, uncheckedItem.getFieldValue(dimensionCode.toUpperCase()));
                }
            }
            unOffsetDataDTO.setOffsetState(OffsetStateEnum.NOTOFFSET.getValue());
            Map fields = uncheckedItem.getFields();
            if (fields.containsKey("AGINGRANGE") && ((Map)fields.get("AGINGRANGE")).size() > 0) {
                unOffsetDataDTO.addFieldValue("AGINGRANGE", fields.get("AGINGRANGE"));
            }
            if (!NumberUtils.isZreo((Double)uncheckedItem.getDebitConversionValue())) {
                unOffsetDataDTO.setDc(OrientEnum.D.getValue());
            } else {
                unOffsetDataDTO.setDc(OrientEnum.C.getValue());
            }
            unOffsetDataDTO.setAmt(uncheckedItem.getAmt());
            unOffsetDataDTO.setOffsetCurrency(uncheckedItem.getConversionCurr());
            unOffsetDataDTO.setConversionRate(uncheckedItem.getConversionRate());
            unOffsetDataDTO.setUnitId(uncheckedItem.getGcUnitId());
            unOffsetDataDTO.setOppUnitId(uncheckedItem.getGcOppUnitId());
            unOffsetDataDTO.setSubjectCode(uncheckedItem.getGcSubjectCode());
            if (Objects.isNull(uncheckedItem.getFieldValue("VCHROFFSETRELS"))) {
                RelatedItemGcOffsetRelDTO relatedItemOffsetRelDTO = new RelatedItemGcOffsetRelDTO();
                relatedItemOffsetRelDTO.setId(UUIDOrderUtils.newUUIDStr());
                relatedItemOffsetRelDTO.setRelatedItemId(uncheckedItem.getRelatedItemId());
                relatedItemOffsetRelDTO.setChkState(uncheckedItem.getCheckState());
                ArrayList<RelatedItemGcOffsetRelDTO> vchrOffsetRelEOS = new ArrayList<RelatedItemGcOffsetRelDTO>(Arrays.asList(relatedItemOffsetRelDTO));
                unOffsetDataDTO.addFieldValue("VCHROFFSETRELS", vchrOffsetRelEOS);
            } else {
                unOffsetDataDTO.addFieldValue("VCHROFFSETRELS", uncheckedItem.getFieldValue("VCHROFFSETRELS"));
            }
            offsetRelatedItemList.add(unOffsetDataDTO);
        }
        return offsetRelatedItemList;
    }

    public static List<GcFcRuleUnOffsetDataDTO> convertOrigin2RuleUnOffsetData(List<GcRelatedItemEO> uncheckedList) {
        ArrayList<GcFcRuleUnOffsetDataDTO> offsetRelatedItemList = new ArrayList<GcFcRuleUnOffsetDataDTO>();
        List dimensionS = ((DimensionService)SpringBeanUtils.getBean(DimensionService.class)).findDimFieldsByTableName("GC_RELATED_ITEM");
        for (GcRelatedItemEO uncheckedItem : uncheckedList) {
            GcFcRuleUnOffsetDataDTO unOffsetDataDTO = new GcFcRuleUnOffsetDataDTO();
            BeanUtils.copyProperties(uncheckedItem, unOffsetDataDTO);
            if (!CollectionUtils.isEmpty(dimensionS)) {
                List dimensionCodes = dimensionS.stream().map(DimensionEO::getCode).collect(Collectors.toList());
                for (String dimensionCode : dimensionCodes) {
                    unOffsetDataDTO.addFieldValue(dimensionCode, uncheckedItem.getFieldValue(dimensionCode.toUpperCase()));
                }
            }
            unOffsetDataDTO.setOffsetState(OffsetStateEnum.NOTOFFSET.getValue());
            if (CheckStateEnum.CHECKED.getCode().equals(unOffsetDataDTO.getChkState())) {
                if (uncheckedItem.getChkAmtD() != 0.0) {
                    unOffsetDataDTO.setDc(OrientEnum.D.getValue());
                    unOffsetDataDTO.setAmt(uncheckedItem.getChkAmtD());
                } else {
                    unOffsetDataDTO.setDc(OrientEnum.C.getValue());
                    unOffsetDataDTO.setAmt(uncheckedItem.getChkAmtC());
                }
                unOffsetDataDTO.setOffsetCurrency(uncheckedItem.getChkCurr());
            } else if (uncheckedItem.getDebit() != 0.0) {
                unOffsetDataDTO.setDc(OrientEnum.D.getValue());
                unOffsetDataDTO.setAmt(uncheckedItem.getDebitOrig());
                unOffsetDataDTO.setOffsetCurrency(uncheckedItem.getOriginalCurr());
            } else {
                unOffsetDataDTO.setDc(OrientEnum.C.getValue());
                unOffsetDataDTO.setAmt(uncheckedItem.getCreditOrig());
                unOffsetDataDTO.setOffsetCurrency(uncheckedItem.getOriginalCurr());
            }
            if (Objects.isNull(uncheckedItem.getFieldValue("VCHROFFSETRELS"))) {
                RelatedItemGcOffsetRelDTO relatedItemOffsetRelDTO = new RelatedItemGcOffsetRelDTO();
                relatedItemOffsetRelDTO.setId(UUIDOrderUtils.newUUIDStr());
                relatedItemOffsetRelDTO.setRelatedItemId(uncheckedItem.getId());
                relatedItemOffsetRelDTO.setUpdateTime(uncheckedItem.getUpdateTime());
                relatedItemOffsetRelDTO.setRecordTimestamp(uncheckedItem.getRecordTimestamp());
                relatedItemOffsetRelDTO.setChkState(uncheckedItem.getChkState());
                ArrayList<RelatedItemGcOffsetRelDTO> vchrOffsetRelEOS = new ArrayList<RelatedItemGcOffsetRelDTO>(Arrays.asList(relatedItemOffsetRelDTO));
                unOffsetDataDTO.addFieldValue("VCHROFFSETRELS", vchrOffsetRelEOS);
            } else {
                unOffsetDataDTO.addFieldValue("VCHROFFSETRELS", uncheckedItem.getFieldValue("VCHROFFSETRELS"));
            }
            offsetRelatedItemList.add(unOffsetDataDTO);
        }
        return offsetRelatedItemList;
    }

    public static class OffsetResult {
        private boolean matched;
        private List<GcFcRuleUnOffsetDataDTO> offsetedInputItems;
        private GcOffSetVchrDTO offsetVchr;

        public GcOffSetVchrDTO getOffsetVchr() {
            return this.offsetVchr;
        }

        public List<GcFcRuleUnOffsetDataDTO> getOffsetedInputItems() {
            return this.offsetedInputItems;
        }
    }
}

