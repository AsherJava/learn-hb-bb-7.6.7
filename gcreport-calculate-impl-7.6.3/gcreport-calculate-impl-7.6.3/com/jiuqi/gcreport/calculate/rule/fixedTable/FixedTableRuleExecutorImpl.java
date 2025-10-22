/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.gcreport.calculate.rule.fixedTable;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.formula.service.GcFormulaEvalService;
import com.jiuqi.gcreport.calculate.rule.fixedTable.AbstractFixedTableRuleExecutor;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class FixedTableRuleExecutorImpl
extends AbstractFixedTableRuleExecutor {
    public GcCalcEnvContext processEnv;
    private FixedTableRuleDTO rule;
    public GcOffSetAppOffsetService adjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
    public GcFormulaEvalService formulaEvalService = (GcFormulaEvalService)SpringContextUtils.getBean(GcFormulaEvalService.class);
    public GcCalcService gcCalcService = (GcCalcService)SpringContextUtils.getBean(GcCalcService.class);
    private Logger logger = LoggerFactory.getLogger(FixedTableRuleExecutorImpl.class);

    @Override
    public void calMerge(AbstractUnionRule rule, GcCalcEnvContext env) {
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        if (!(rule instanceof FixedTableRuleDTO)) {
            return;
        }
        this.rule = (FixedTableRuleDTO)rule;
        this.setProcessEnv(env);
        GcCalcArgmentsDTO arg = env.getCalcArgments();
        if (!this.isAllowCal().booleanValue()) {
            return;
        }
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), arg);
        this.calMerge();
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    private Boolean isAllowCal() {
        if (null == this.rule.getRuleCondition()) {
            return true;
        }
        GcCalcArgmentsDTO arg = this.processEnv.getCalcArgments();
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)arg.getOrgId(), (Object)arg.getPeriodStr(), (Object)arg.getCurrency(), (Object)arg.getOrgType(), (String)arg.getSelectAdjustCode(), (String)arg.getTaskId());
        return this.formulaEvalService.checkUnitData(arg, dset, this.rule.getRuleCondition(), arg.getSchemeId());
    }

    private void calMerge() {
        if (CollectionUtils.isEmpty((Collection)this.rule.getCreditItemList()) || CollectionUtils.isEmpty((Collection)this.rule.getDebitItemList())) {
            return;
        }
        this.doOffset();
    }

    private boolean doOffset() {
        ((GcCalcRuleExecuteStateDTO)this.processEnv.getRuleStateMap().get(this.rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        GcCalcArgmentsDTO calcArgments = this.processEnv.getCalcArgments();
        YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List<GcOffSetVchrItemDTO> offsetItems = this.getOffsetItems(tool);
        if (CollectionUtils.isEmpty(offsetItems)) {
            this.logger.debug("\u56fa\u8868\u89c4\u5219\u672a\u751f\u6210\u62b5\u9500\u5206\u5f55. \u5355\u4f4d\uff1a" + this.processEnv.getCalcArgments().getOrgId() + " \u65f6\u671f\uff1a" + this.processEnv.getCalcArgments().getPeriodStr());
        }
        if (GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)this.rule, offsetItems)) {
            GcOffSetVchrDTO offSetItemDTO = new GcOffSetVchrDTO();
            offSetItemDTO.setItems(offsetItems);
            this.adjustService.save(offSetItemDTO);
            ((GcCalcRuleExecuteStateDTO)this.processEnv.getRuleStateMap().get(this.rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(offsetItems.size()));
            if (this.processEnv.getCalcArgments().getPreCalcFlag().get()) {
                this.processEnv.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(offsetItems);
            }
            return true;
        }
        this.logger.debug("\u56fa\u8868\u89c4\u5219\u5408\u5e76\u8ba1\u7b97\u672a\u6838\u5bf9\u6210\u529f. \u5355\u4f4d\uff1a" + this.processEnv.getCalcArgments().getOrgId() + " \u65f6\u671f\uff1a" + this.processEnv.getCalcArgments().getPeriodStr());
        return false;
    }

    private List<GcOffSetVchrItemDTO> getOffsetItems(GcOrgCenterService tool) {
        GcOffSetVchrItemDTO offsetVchrItem;
        ArrayList<GcOffSetVchrItemDTO> mOffsets_d = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<GcOffSetVchrItemDTO> mOffsets_c = new ArrayList<GcOffSetVchrItemDTO>();
        List<Object> offsets = new ArrayList<GcOffSetVchrItemDTO>();
        String offsetGroupId = UUID.randomUUID().toString();
        FixedTableRuleDTO.Item phsRuleItem_d = this.getFixedTableRuleDTOItemWithPHS(this.rule.getDebitItemList());
        FixedTableRuleDTO.Item phsRuleItem_c = this.getFixedTableRuleDTOItemWithPHS(this.rule.getCreditItemList());
        List<FixedTableRuleDTO.Item> debitRuleList = this.rule.getDebitItemList().stream().filter(one -> !StringUtils.isEmpty((String)one.getFetchFormula()) && !one.getFetchFormula().contains("PHS")).collect(Collectors.toList());
        List<FixedTableRuleDTO.Item> creditRuleList = this.rule.getCreditItemList().stream().filter(one -> !StringUtils.isEmpty((String)one.getFetchFormula()) && !one.getFetchFormula().contains("PHS")).collect(Collectors.toList());
        HashMap<String, String> unitCode2TitleMap = new HashMap<String, String>();
        debitRuleList = this.transRuleList(debitRuleList, tool, unitCode2TitleMap);
        creditRuleList = this.transRuleList(creditRuleList, tool, unitCode2TitleMap);
        this.checkUnitNum(unitCode2TitleMap);
        for (FixedTableRuleDTO.Item item1 : debitRuleList) {
            offsetVchrItem = this.createOffsetItem(offsetGroupId, OrientEnum.D, item1, tool);
            mOffsets_d.add(offsetVchrItem);
        }
        for (FixedTableRuleDTO.Item item : creditRuleList) {
            offsetVchrItem = this.createOffsetItem(offsetGroupId, OrientEnum.C, item, tool);
            mOffsets_c.add(offsetVchrItem);
        }
        Collection<GcOffSetVchrItemDTO> offsets_d = this.reduceByUnit(OrientEnum.D, mOffsets_d).values();
        Collection<GcOffSetVchrItemDTO> offsets_c = this.reduceByUnit(OrientEnum.C, mOffsets_c).values();
        GcOffSetVchrItemDTO phsOffset = this.createOffsetItemWithPHS(offsets_d, offsets_c, phsRuleItem_d, phsRuleItem_c, offsetGroupId);
        offsets.addAll(offsets_d);
        offsets.addAll(offsets_c);
        if (phsOffset != null) {
            offsets.add(phsOffset);
        }
        if ((offsets = offsets.stream().filter(offset -> offset.getDebit() != 0.0 || offset.getCredit() != 0.0).collect(Collectors.toList())).size() < 2) {
            return new ArrayList<GcOffSetVchrItemDTO>();
        }
        this.otherAction(offsets, tool);
        return offsets;
    }

    private void checkUnitNum(Map<String, String> unitCode2TitleMap) {
        if (unitCode2TitleMap.size() > 2) {
            String appendString = "";
            for (Map.Entry<String, String> entry : unitCode2TitleMap.entrySet()) {
                appendString = appendString + entry.getKey() + "|" + entry.getValue() + "\uff1b";
            }
            throw new RuntimeException(String.format("\u3010%1s\u3011\u501f\u8d37\u65b9\u5355\u4f4d\u8d85\u8fc7\u4e24\u5bb6\uff0c\u8df3\u8fc7\u8be5\u89c4\u5219\u3002\u501f\u8d37\u65b9\u5355\u4f4d\uff1a%2s", this.rule.getLocalizedName(), appendString));
        }
    }

    private void otherAction(List<GcOffSetVchrItemDTO> offsets, GcOrgCenterService tool) {
        String oppUnitId;
        String unitId;
        String orgId;
        Map<String, List<GcOffSetVchrItemDTO>> unit2dtos = offsets.stream().collect(Collectors.groupingBy(GcOffSetVchrItemDTO::getUnitId));
        if (unit2dtos.size() == 1 && null == offsets.get(0).getOppUnitId()) {
            throw new BusinessRuntimeException("\u751f\u6210\u7684\u62b5\u9500\u5206\u5f55\u4ec5\u5305\u542b\u4e00\u4e2a\u5355\u4f4d\uff0c\u4e0d\u7b26\u5408\u89c4\u5219");
        }
        if (unit2dtos.size() == 2) {
            String[] units = new String[2];
            unit2dtos.keySet().toArray(units);
            unit2dtos.get(units[0]).forEach(offset -> offset.setOppUnitId(units[1]));
            unit2dtos.get(units[1]).forEach(offset -> offset.setOppUnitId(units[0]));
        }
        if (!tool.checkCommonUnit(orgId = this.processEnv.getCalcArgments().getOrgId(), unitId = offsets.get(0).getUnitId(), oppUnitId = offsets.get(0).getOppUnitId())) {
            offsets.clear();
        }
    }

    private GcOffSetVchrItemDTO createOffsetItemWithPHS(Collection<GcOffSetVchrItemDTO> offsets_d, Collection<GcOffSetVchrItemDTO> offsets_c, FixedTableRuleDTO.Item phsRuleItem_d, FixedTableRuleDTO.Item phsRuleItem_c, String offsetGroupId) {
        GcOffSetVchrItemDTO offsetItem = null;
        if (phsRuleItem_d != null) {
            double debit = this.calcPHSDiff(offsets_d, offsets_c, OrientEnum.D);
            offsetItem = this.createOffsetItem(offsetGroupId, OrientEnum.D, phsRuleItem_d, null);
            offsetItem.setDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)debit)));
            offsetItem.setCredit(Double.valueOf(0.0));
        }
        if (phsRuleItem_d == null && phsRuleItem_c != null) {
            double crebit = this.calcPHSDiff(offsets_d, offsets_c, OrientEnum.C);
            offsetItem = this.createOffsetItem(offsetGroupId, OrientEnum.C, phsRuleItem_c, null);
            offsetItem.setCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)crebit)));
            offsetItem.setDebit(Double.valueOf(0.0));
        }
        if (offsetItem != null) {
            offsetItem.setOffSetSrcType(OffSetSrcTypeEnum.PHS);
            offsetItem.setMemo("\u5e73\u8861\u6570");
        }
        return offsetItem;
    }

    private double calcPHSDiff(Collection<GcOffSetVchrItemDTO> offsets_d, Collection<GcOffSetVchrItemDTO> offsets_c, OrientEnum orient) {
        double m_debit_sum = offsets_d.stream().mapToDouble(GcOffSetVchrItemDTO::getDebit).sum();
        double m_crebit_sum = offsets_c.stream().mapToDouble(GcOffSetVchrItemDTO::getCredit).sum();
        BigDecimal debit_sum = new BigDecimal(m_debit_sum);
        BigDecimal crebit_sum = new BigDecimal(m_crebit_sum);
        if (orient == OrientEnum.D) {
            return crebit_sum.subtract(debit_sum).doubleValue();
        }
        if (orient == OrientEnum.C) {
            return debit_sum.subtract(crebit_sum).doubleValue();
        }
        return 0.0;
    }

    private FixedTableRuleDTO.Item getFixedTableRuleDTOItemWithPHS(List<FixedTableRuleDTO.Item> itemList) {
        Optional<FixedTableRuleDTO.Item> mPhsRuleItem = itemList.stream().filter(one -> !StringUtils.isEmpty((String)one.getFetchFormula()) && one.getFetchFormula().contains("PHS")).findFirst();
        if (mPhsRuleItem.isPresent()) {
            GcCalcArgmentsDTO calcArgments = this.processEnv.getCalcArgments();
            YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO org = tool.getOrgByCode(calcArgments.getOrgId());
            FixedTableRuleDTO.Item item = mPhsRuleItem.get();
            String baseunitid = this.getBaseUnitId(org.getBaseUnitId(), tool);
            Assert.isNotNull((Object)baseunitid, (String)String.format("%1s \u672a\u8bbe\u7f6e\u672c\u90e8\u5355\u4f4d", org.getTitle()), (Object[])new Object[0]);
            String diffunitid = org.getDiffUnitId();
            Assert.isNotNull((Object)baseunitid, (String)String.format("%1s \u672a\u8bbe\u7f6e\u5dee\u989d\u5355\u4f4d", org.getTitle()), (Object[])new Object[0]);
            if (!FetchRangeEnum.CUSTOMIZE.equals((Object)item.getFetchRange())) {
                FixedTableRuleDTO.Item mRuleItem = new FixedTableRuleDTO.Item();
                BeanUtils.copyProperties(item, mRuleItem);
                if (baseunitid != null) {
                    mRuleItem.setUnit(baseunitid);
                    mRuleItem.setOppUnitId(diffunitid);
                    return mRuleItem;
                }
            } else {
                FixedTableRuleDTO.Item mRuleItem = new FixedTableRuleDTO.Item();
                BeanUtils.copyProperties(item, mRuleItem);
                mRuleItem.setFetchUnit(item.getUnit());
            }
            return mPhsRuleItem.get();
        }
        return null;
    }

    private Map<String, GcOffSetVchrItemDTO> reduceByUnit(OrientEnum orient, List<GcOffSetVchrItemDTO> mOffsets) {
        HashMap<String, GcOffSetVchrItemDTO> key2offset = new HashMap<String, GcOffSetVchrItemDTO>();
        for (GcOffSetVchrItemDTO item : mOffsets) {
            if (FetchRangeEnum.ALL_DIRECT_SUBS.equals((Object)item.getFetchRange())) {
                String key = (String)item.getFieldValue("fixedTableRuleItemId");
                if (key2offset.get(key) == null) {
                    key2offset.put(key, item);
                    continue;
                }
                GcOffSetVchrItemDTO preItem = (GcOffSetVchrItemDTO)key2offset.get(key);
                if (orient == OrientEnum.D) {
                    preItem.setDebit(Double.valueOf(preItem.getDebit() + item.getDebit()));
                } else {
                    preItem.setCredit(Double.valueOf(preItem.getCredit() + item.getCredit()));
                }
                key2offset.put(key, preItem);
                continue;
            }
            key2offset.put(item.getId(), item);
        }
        return key2offset;
    }

    private String getBaseUnitId(String orgCode, GcOrgCenterService tool) {
        GcOrgCacheVO org = tool.getOrgByCode(orgCode);
        if (null == org) {
            return null;
        }
        if (org.isLeaf()) {
            return org.getId();
        }
        return this.getBaseUnitId(org.getBaseUnitId(), tool);
    }

    public List<FixedTableRuleDTO.Item> transRuleList(List<FixedTableRuleDTO.Item> originList, GcOrgCenterService tool, Map<String, String> unitCode2TitleMap) {
        GcOrgCacheVO org = tool.getOrgByCode(this.processEnv.getCalcArgments().getOrgId());
        String baseunitid = this.getBaseUnitId(org.getBaseUnitId(), tool);
        Assert.isNotNull((Object)baseunitid, (String)String.format("%1s \u672a\u8bbe\u7f6e\u672c\u90e8\u5355\u4f4d", org.getTitle()), (Object[])new Object[0]);
        String diffunitid = org.getDiffUnitId();
        Assert.isNotNull((Object)diffunitid, (String)String.format("%1s \u672a\u8bbe\u7f6e\u5dee\u989d\u5355\u4f4d", org.getTitle()), (Object[])new Object[0]);
        ArrayList<FixedTableRuleDTO.Item> transfedList = new ArrayList<FixedTableRuleDTO.Item>();
        for (FixedTableRuleDTO.Item item : originList) {
            FixedTableRuleDTO.Item mRuleItem;
            FetchRangeEnum fetchRange = item.getFetchRange();
            if (fetchRange == FetchRangeEnum.UNION || fetchRange == FetchRangeEnum.HEADQUARTERS) {
                mRuleItem = new FixedTableRuleDTO.Item();
                BeanUtils.copyProperties(item, mRuleItem);
                mRuleItem.setUnit(baseunitid);
                mRuleItem.setOppUnitId(diffunitid);
                if (fetchRange == FetchRangeEnum.UNION) {
                    mRuleItem.setFetchUnit(org.getId());
                } else {
                    mRuleItem.setFetchUnit(baseunitid);
                }
                transfedList.add(mRuleItem);
                GcOrgCacheVO baseUnitOrg = tool.getOrgByCode(baseunitid);
                unitCode2TitleMap.put(baseunitid, baseUnitOrg.getTitle());
                continue;
            }
            if (fetchRange == FetchRangeEnum.DIFFERENCE) {
                mRuleItem = new FixedTableRuleDTO.Item();
                BeanUtils.copyProperties(item, mRuleItem);
                mRuleItem.setFetchUnit(diffunitid);
                mRuleItem.setUnit(baseunitid + "," + diffunitid);
                mRuleItem.setUnit(diffunitid);
                mRuleItem.setOppUnitId(baseunitid);
                transfedList.add(mRuleItem);
                GcOrgCacheVO diffUnitOrg = tool.getOrgByCode(diffunitid);
                unitCode2TitleMap.put(diffunitid, diffUnitOrg.getTitle());
                continue;
            }
            if (fetchRange == FetchRangeEnum.ALL_DIRECT_SUBS || item.getFetchRange() == FetchRangeEnum.DIRECT_SUB_DETAIL) {
                GcOrgCacheVO baseUnitOrg = tool.getOrgByCode(baseunitid);
                unitCode2TitleMap.put(baseunitid, baseUnitOrg.getTitle());
                List childrenOrg = org.getChildren();
                String fixedTableRuleItemId = UUIDOrderUtils.newUUIDStr();
                for (GcOrgCacheVO child : childrenOrg) {
                    if (GcOrgKindEnum.DIFFERENCE.equals((Object)child.getOrgKind())) continue;
                    FixedTableRuleDTO.Item mRuleItem2 = new FixedTableRuleDTO.Item();
                    BeanUtils.copyProperties(item, mRuleItem2);
                    mRuleItem2.setUnit(baseunitid);
                    mRuleItem2.setOppUnitId(diffunitid);
                    mRuleItem2.setFetchUnit(child.getId());
                    mRuleItem2.setFixedTableRuleItemId(fixedTableRuleItemId);
                    transfedList.add(mRuleItem2);
                }
                continue;
            }
            if (item.getFetchRange() != FetchRangeEnum.CUSTOMIZE) continue;
            item.setFetchUnit(item.getUnit());
            transfedList.add(item);
            GcOrgCacheVO unitOrg = tool.getOrgByCode(item.getUnit());
            unitCode2TitleMap.put(item.getUnit(), unitOrg.getTitle());
        }
        return transfedList;
    }

    private GcOffSetVchrItemDTO createOffsetItem(String offsetGroupId, OrientEnum orient, FixedTableRuleDTO.Item item, GcOrgCenterService tool) {
        GcCalcArgmentsDTO calcArgments = this.processEnv.getCalcArgments();
        GcOffSetVchrItemDTO offsetVchrItem = new GcOffSetVchrItemDTO();
        offsetVchrItem.setFetchRange(item.getFetchRange());
        offsetVchrItem.setId(UUID.randomUUID().toString());
        offsetVchrItem.setRecver(Long.valueOf(0L));
        offsetVchrItem.setOrgType("NONE");
        offsetVchrItem.setAcctYear(calcArgments.getAcctYear());
        offsetVchrItem.setAcctPeriod(calcArgments.getAcctPeriod());
        offsetVchrItem.setDefaultPeriod(calcArgments.getPeriodStr());
        offsetVchrItem.setSrcOffsetGroupId(offsetGroupId);
        offsetVchrItem.setTaskId(calcArgments.getTaskId());
        offsetVchrItem.setSchemeId(calcArgments.getSchemeId());
        offsetVchrItem.setSubjectCode(item.getSubjectCode());
        offsetVchrItem.setSortOrder(Double.valueOf(0.0));
        offsetVchrItem.setOffSetCurr(calcArgments.getCurrency());
        offsetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        offsetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
        offsetVchrItem.setRuleId(this.getRule().getId());
        offsetVchrItem.setCreateTime(new Date());
        offsetVchrItem.setGcBusinessTypeCode(this.getRule().getBusinessTypeCode());
        if (StringUtils.isEmpty((String)item.getUnit()) && !"phs".equalsIgnoreCase(item.getUnit())) {
            throw new RuntimeException("\u5408\u5e76\u5355\u4f4d\u672a\u8bbe\u7f6e\u672c\u65b9\u5355\u4f4d\u6216\u5dee\u989d\u5355\u4f4d");
        }
        offsetVchrItem.setUnitId(item.getUnit());
        offsetVchrItem.setOppUnitId(item.getOppUnitId());
        offsetVchrItem.getFields().put("fixedTableRuleItemId", item.getFixedTableRuleItemId());
        String orgType = calcArgments.getOrgType();
        boolean directSubDetail = item.getFetchRange().equals((Object)FetchRangeEnum.DIRECT_SUB_DETAIL);
        String memo = null;
        if (tool != null) {
            GcOrgCacheVO fetchUnitOrg = tool.getOrgByCode(item.getFetchUnit());
            orgType = fetchUnitOrg.getOrgTypeId();
            if (directSubDetail) {
                memo = fetchUnitOrg.getCode() + "|" + fetchUnitOrg.getTitle();
            }
        } else if (directSubDetail) {
            memo = "\u5e73\u8861\u6570";
        }
        offsetVchrItem.setMemo(memo);
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)item.getFetchUnit(), (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)orgType, (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        if (StringUtils.isEmpty((String)item.getFetchFormula())) {
            offsetVchrItem.setDebit(Double.valueOf(0.0));
            offsetVchrItem.setCredit(Double.valueOf(0.0));
        } else {
            boolean isPhs;
            boolean bl = isPhs = !StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS");
            if (orient == OrientEnum.D && !isPhs) {
                double debit = this.formulaEvalService.evaluateUnitData(dset, item.getFetchFormula(), calcArgments.getSchemeId());
                offsetVchrItem.setDebit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)debit)));
                offsetVchrItem.setCredit(Double.valueOf(0.0));
            }
            if (orient == OrientEnum.C && !isPhs) {
                offsetVchrItem.setDebit(Double.valueOf(0.0));
                double credit = this.formulaEvalService.evaluateUnitData(dset, item.getFetchFormula(), calcArgments.getSchemeId());
                offsetVchrItem.setCredit(Double.valueOf(OffsetVchrItemNumberUtils.round((double)credit)));
            }
        }
        offsetVchrItem.setOffSetDebit(offsetVchrItem.getDebit());
        offsetVchrItem.setOffSetCredit(offsetVchrItem.getCredit());
        offsetVchrItem.setDiffd(Double.valueOf(0.0));
        offsetVchrItem.setDiffc(Double.valueOf(0.0));
        this.appendDimensions(offsetVchrItem, dset, item.getDimensions());
        offsetVchrItem.setSelectAdjustCode(calcArgments.getSelectAdjustCode());
        return offsetVchrItem;
    }

    private void appendDimensions(GcOffSetVchrItemDTO offSetVchrItemDTO, DimensionValueSet dset, Map<String, String> dimensions) {
        if (dimensions == null) {
            return;
        }
        for (String dimKey : dimensions.keySet()) {
            String dimValueSource = dimensions.get(dimKey);
            if (dimKey.contains("customizeFormula") || !"customizeFormula".equals(dimValueSource)) continue;
            if (StringUtils.isEmpty((String)dimensions.get(dimKey + "_customizeFormula"))) {
                offSetVchrItemDTO.addFieldValue(dimKey, null);
                continue;
            }
            AbstractData data = this.formulaEvalService.ordinaryFormulaEvaluate(dset, dimensions.get(dimKey + "_customizeFormula"), null);
            offSetVchrItemDTO.addFieldValue(dimKey, data.getAsObject());
        }
    }

    AbstractUnionRule getRule() {
        return this.rule;
    }

    public GcCalcEnvContext getProcessEnv() {
        return this.processEnv;
    }

    public void setProcessEnv(GcCalcEnvContext processEnv) {
        this.processEnv = processEnv;
    }
}

