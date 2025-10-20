/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcService
 *  com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule$Item
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.util.GcCalcAmtCheckUtil;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.InvestmentUnitEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcInvestBillRuleExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcInvestBillRuleExecutor.class);
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Autowired
    private GcCalcService gcCalcService;

    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public void execute(@NotNull AbstractInvestmentRule rule, GcCalcEnvContext env, List<GcInvestBillGroupDTO> datas) {
        this.doExecute(env, rule, datas);
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    private void doExecute(GcCalcEnvContext env, @NotNull AbstractInvestmentRule rule, List<GcInvestBillGroupDTO> datas) {
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        DimensionValueSet dset = DimensionUtils.generateDimSet(null, (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        List<GcInvestBillGroupDTO> acceptDatas = !StringUtils.isEmpty((String)rule.getRuleCondition()) ? datas.stream().filter(data -> {
            boolean isAccept = false;
            try {
                dset.setValue("MD_ORG", data.getMaster().getFieldValue("INVESTEDUNIT"));
                isAccept = this.billFormulaEvalService.checkInvestBillData(env, dset, rule.getRuleCondition(), (GcInvestBillGroupDTO)data);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.condition.error", (Object[])new Object[]{rule.getLocalizedName(), rule.getRuleCondition()}) + e.getMessage());
            }
            if (!isAccept) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u9002\u7528\u6761\u4ef6[{}]\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), data.getMaster().getId(), rule.getRuleCondition());
            }
            return isAccept;
        }).collect(Collectors.toList()) : datas;
        List<GcOffSetVchrDTO> allDTOs = Collections.synchronizedList(new ArrayList());
        try {
            GcFormulaThreadContext.enableCache();
            YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
            Date calcDate = yp.formatYP().getEndDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calcDate);
            int currentMonth = calendar.get(2);
            if (!CollectionUtils.isEmpty(acceptDatas)) {
                for (GcInvestBillGroupDTO acceptData : acceptDatas) {
                    GcOffSetVchrDTO itemDTO;
                    if (this.isDispose(acceptData, currentMonth, calendar) || (itemDTO = this.executeSingleAcceptData(rule, env, acceptData)) == null) continue;
                    allDTOs.add(itemDTO);
                    if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
                    env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(itemDTO.getItems());
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.error", (Object[])new Object[]{rule.getLocalizedName()}) + e.getMessage(), (Throwable)e);
        }
        finally {
            GcFormulaThreadContext.releaseCache();
        }
        this.deleteHistoryAdjustOffSetItems(env, rule);
        if (!CollectionUtils.isEmpty(allDTOs)) {
            allDTOs.forEach(offsetDTO -> offsetDTO.setConsFormulaCalcType("autoFlag"));
            this.offSetItemAdjustService.batchSave(allDTOs);
            allDTOs.forEach(gcOffSetVchrDTO -> {
                List items = gcOffSetVchrDTO.getItems();
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(CollectionUtils.isEmpty((Collection)items) ? 0 : items.size()));
            });
        }
    }

    private boolean allowCalcByChangeScenario(AbstractInvestmentRule rule, int currentMonth, GcInvestBillGroupDTO acceptData) {
        List changeRatioOfCurrYear = rule.getChangeRatioOfCurrYear();
        List changeRatioOfCurrMonth = rule.getChangeRatioOfCurrMonth();
        List<DefaultTableEntity> currYearItems = acceptData.getCurrYearItems();
        boolean currYearAllowCalcFlag = false;
        boolean currMonthAllowCalcFlag = false;
        currYearAllowCalcFlag = CollectionUtils.isEmpty((Collection)changeRatioOfCurrYear) ? true : currYearItems.stream().anyMatch(item -> changeRatioOfCurrYear.contains(item.getFieldValue("CHANGESCENARIO")));
        currMonthAllowCalcFlag = CollectionUtils.isEmpty((Collection)changeRatioOfCurrMonth) ? true : currYearItems.stream().anyMatch(item -> {
            Date changeDate = (Date)item.getFieldValue("CHANGEDATE");
            String changeScenario = (String)item.getFieldValue("CHANGESCENARIO");
            return currentMonth == DateUtils.getDateFieldValue((Date)changeDate, (int)2) && changeRatioOfCurrMonth.contains(changeScenario);
        });
        return currYearAllowCalcFlag && currMonthAllowCalcFlag;
    }

    private boolean isDispose(GcInvestBillGroupDTO acceptData, int currentMonth, Calendar calendar) {
        Date disposeDate = (Date)acceptData.getMaster().getFieldValue("DISPOSEDATE");
        if (disposeDate == null) {
            return false;
        }
        calendar.setTime(disposeDate);
        int disposeMonth = calendar.get(2);
        return disposeMonth <= currentMonth;
    }

    private void deleteHistoryAdjustOffSetItems(GcCalcEnvContext env, @NotNull AbstractInvestmentRule rule) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), calcArgments);
    }

    private GcOffSetVchrDTO executeSingleAcceptData(AbstractInvestmentRule rule, GcCalcEnvContext env, GcInvestBillGroupDTO acceptData) {
        boolean isCreateGcOffSetVchrDTO;
        GcOffSetVchrItemDTO phsRecord;
        GcOffSetVchrItemDTO offSetVchrItem;
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)acceptData.getMaster().getFieldValue("INVESTEDUNIT"), (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO(env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(rule.getId()));
        ArrayList<GcOffSetVchrItemDTO> records = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<AbstractInvestmentRule.Item> debitPhsFormulaList = new ArrayList<AbstractInvestmentRule.Item>();
        ArrayList<AbstractInvestmentRule.Item> creditPhsFormulaList = new ArrayList<AbstractInvestmentRule.Item>();
        for (AbstractInvestmentRule.Item item : rule.getDebitItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                debitPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(offSetVchrDTO.getMrecid(), calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, item, dset, OrientEnum.D, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u501f\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula());
                if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
            }
            debitSum = NumberUtils.sum((Double)debitSum, (Double)offSetVchrItem.getOffSetDebit());
            records.add(offSetVchrItem);
        }
        for (AbstractInvestmentRule.Item item : rule.getCreditItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                creditPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(offSetVchrDTO.getMrecid(), calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, item, dset, OrientEnum.C, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u8d37\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula());
                if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
            }
            creditSum = NumberUtils.sum((Double)creditSum, (Double)offSetVchrItem.getOffSetCredit());
            records.add(offSetVchrItem);
        }
        double diffValue = NumberUtils.sub((Double)debitSum, (Double)creditSum);
        if (!NumberUtils.isZreo((Double)diffValue) && null != (phsRecord = this.phsRecord(rule, env, acceptData, calcArgments, dset, offSetVchrDTO.getMrecid(), debitPhsFormulaList, creditPhsFormulaList, diffValue))) {
            records.add(phsRecord);
        }
        if ((isCreateGcOffSetVchrDTO = GcCalcAmtCheckUtil.checkAndDistributionAmt((AbstractUnionRule)rule, records)) || env.getCalcArgments().getPreCalcFlag().get()) {
            offSetVchrDTO.setItems(records);
            return offSetVchrDTO;
        }
        this.logger.info("\u5408\u5e76\u8ba1\u7b97\u6295\u8d44\u89c4\u5219[{}]\uff0c\u6295\u8d44\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u5bb9\u5dee\u7ea6\u675f\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", (Object)rule.getLocalizedName(), (Object)acceptData.getMaster().getId());
        return null;
    }

    private GcOffSetVchrItemDTO phsRecord(AbstractInvestmentRule rule, GcCalcEnvContext env, GcInvestBillGroupDTO investBillGroupDTO, GcCalcArgmentsDTO calcArgments, DimensionValueSet dset, String mrecid, List<AbstractInvestmentRule.Item> debitPhsFormulaList, List<AbstractInvestmentRule.Item> creditPhsFormulaList, double diffValue) {
        GcOffSetVchrItemDTO offSetVchrItem;
        for (AbstractInvestmentRule.Item fetchFormula : debitPhsFormulaList) {
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(-diffValue));
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, investBillGroupDTO, fetchFormula, dset, OrientEnum.D, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit()) && !env.getCalcArgments().getPreCalcFlag().get()) continue;
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        for (AbstractInvestmentRule.Item fetchFormula : creditPhsFormulaList) {
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(diffValue));
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, investBillGroupDTO, fetchFormula, dset, OrientEnum.C, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit()) && !env.getCalcArgments().getPreCalcFlag().get()) continue;
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        return null;
    }

    private void fillOffSetVchrItem(GcOffSetVchrItemDTO offSetVchrItemDTO, GcInvestBillGroupDTO investmentBillGroupDTO, AbstractInvestmentRule.Item item, DimensionValueSet dimensionValueSet, OrientEnum orientEnum, GcCalcEnvContext env) {
        String defaultDimOrgId;
        String oppUnitId;
        String unitId;
        String subjectCode = item.getSubjectCode();
        String fetchFormula = item.getFetchFormula();
        String investUnit = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("UNITCODE"));
        String investedUnit = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("INVESTEDUNIT"));
        if (InvestmentUnitEnum.INVESTMENT_UNIT.equals((Object)item.getInvestmentUnit())) {
            unitId = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("UNITCODE"));
            oppUnitId = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("INVESTEDUNIT"));
            defaultDimOrgId = investUnit;
        } else {
            unitId = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("INVESTEDUNIT"));
            oppUnitId = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("UNITCODE"));
            defaultDimOrgId = investedUnit;
        }
        this.initDimensions(dimensionValueSet, offSetVchrItemDTO, investmentBillGroupDTO, item, env, investUnit, investedUnit, defaultDimOrgId);
        offSetVchrItemDTO.setSortOrder(Double.valueOf(item.getSort() == null ? 0.0 : (double)item.getSort().intValue()));
        offSetVchrItemDTO.setUnitId(unitId);
        offSetVchrItemDTO.setOppUnitId(oppUnitId);
        String srcId = ConverterUtils.getAsString((Object)investmentBillGroupDTO.getMaster().getFieldValue("SRCID"));
        if (StringUtils.isEmpty((String)srcId)) {
            offSetVchrItemDTO.setSrcOffsetGroupId(investmentBillGroupDTO.getMaster().getId());
        } else {
            offSetVchrItemDTO.setSrcOffsetGroupId(srcId);
        }
        offSetVchrItemDTO.setSubjectCode(subjectCode);
        if (StringUtils.isEmpty((String)fetchFormula)) {
            return;
        }
        Assert.isFalse((boolean)"PHS".equalsIgnoreCase(fetchFormula), (String)GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.error"), (Object[])new Object[0]);
        double result = this.billFormulaEvalService.evaluateInvestBillData(env, dimensionValueSet, fetchFormula, investmentBillGroupDTO, null);
        if (orientEnum.equals((Object)OrientEnum.D)) {
            offSetVchrItemDTO.setOffSetDebit(Double.valueOf(result));
            offSetVchrItemDTO.setDebit(Double.valueOf(result));
            offSetVchrItemDTO.setOrient(OrientEnum.D);
        } else {
            offSetVchrItemDTO.setOffSetCredit(Double.valueOf(result));
            offSetVchrItemDTO.setCredit(Double.valueOf(result));
            offSetVchrItemDTO.setOrient(OrientEnum.C);
        }
    }

    private void initDimensions(DimensionValueSet dimensionValueSet, GcOffSetVchrItemDTO offSetVchrItemDTO, GcInvestBillGroupDTO investmentBillGroupDTO, AbstractInvestmentRule.Item item, GcCalcEnvContext env, String investUnit, String investedUnit, String defaultDimOrgId) {
        Map dimensions = item.getDimensions();
        if (dimensions == null) {
            return;
        }
        for (String dimKey : dimensions.keySet()) {
            String dimValueSource = (String)dimensions.get(dimKey);
            if (dimKey.contains("customizeFormula")) continue;
            if (InvestmentUnitEnum.INVESTMENT_UNIT.getCode().equalsIgnoreCase(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, investUnit, dimKey));
                continue;
            }
            if (InvestmentUnitEnum.INVESTED_UNIT.getCode().equalsIgnoreCase(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, investedUnit, dimKey));
                continue;
            }
            if ("ACCOUNT".equals(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, investmentBillGroupDTO.getMaster().getFieldValue(dimKey));
                continue;
            }
            if ("customizeFormula".equals(dimValueSource)) {
                if (StringUtils.isEmpty((String)((String)dimensions.get(dimKey + "_customizeFormula")))) {
                    offSetVchrItemDTO.addFieldValue(dimKey, null);
                    continue;
                }
                AbstractData data = this.billFormulaEvalService.getInvestBillData(env, dimensionValueSet, (String)dimensions.get(dimKey + "_customizeFormula"), investmentBillGroupDTO);
                offSetVchrItemDTO.addFieldValue(dimKey, data.getAsObject());
                continue;
            }
            offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, defaultDimOrgId, dimKey));
        }
    }

    private String getDimValueByDimCodeAndOrg(GcCalcEnvContext env, String dimOrgId, String dimCode) {
        if (dimOrgId == null) {
            return null;
        }
        GcOrgCenterService orgCenterService = env.getCalcContextExpandVariableCenter().getOrgCenterService();
        GcOrgCacheVO dimOrg = orgCenterService.getOrgByCode(dimOrgId);
        if (dimOrg == null) {
            return null;
        }
        Object dimValue = dimOrg.getBaseFieldValue(dimCode);
        if (dimValue == null) {
            return null;
        }
        return dimValue.toString();
    }

    private GcOffSetVchrItemDTO createNewOffSetVchrItem(String mrecid, GcCalcArgmentsDTO calcArgments, AbstractInvestmentRule rule) {
        GcOffSetVchrItemDTO initOffSetVchrItem = new GcOffSetVchrItemDTO();
        initOffSetVchrItem.setmRecid(mrecid);
        initOffSetVchrItem.setDefaultPeriod(calcArgments.getPeriodStr());
        initOffSetVchrItem.setAcctPeriod(calcArgments.getAcctPeriod());
        initOffSetVchrItem.setAcctYear(calcArgments.getAcctYear());
        initOffSetVchrItem.setTaskId(calcArgments.getTaskId());
        initOffSetVchrItem.setSchemeId(calcArgments.getSchemeId());
        initOffSetVchrItem.setId(UUID.randomUUID().toString());
        initOffSetVchrItem.setElmMode(Integer.valueOf(OffsetElmModeEnum.AUTO_ITEM.getValue()));
        initOffSetVchrItem.setCreateTime(Calendar.getInstance().getTime());
        initOffSetVchrItem.setGcBusinessTypeCode(rule.getBusinessTypeCode());
        initOffSetVchrItem.setRuleId(rule.getId());
        initOffSetVchrItem.setOffSetSrcType(rule.getEquityLawAdjustFlag() != false ? OffSetSrcTypeEnum.EQUITY_METHOD_ADJ : OffSetSrcTypeEnum.CONSOLIDATE);
        initOffSetVchrItem.setOffSetCurr(calcArgments.getCurrency());
        initOffSetVchrItem.setDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setOffSetDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setOffSetCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setBfOffSetDebit(Double.valueOf(0.0));
        initOffSetVchrItem.setBfOffSetCredit(Double.valueOf(0.0));
        initOffSetVchrItem.setDiffd(Double.valueOf(0.0));
        initOffSetVchrItem.setDiffc(Double.valueOf(0.0));
        initOffSetVchrItem.setOrgType(GCOrgTypeEnum.NONE.getCode());
        initOffSetVchrItem.setSelectAdjustCode(calcArgments.getSelectAdjustCode());
        return initOffSetVchrItem;
    }
}

