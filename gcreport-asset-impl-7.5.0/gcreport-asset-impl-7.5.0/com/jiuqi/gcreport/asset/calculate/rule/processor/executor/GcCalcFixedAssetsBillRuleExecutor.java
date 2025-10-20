/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
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
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.asset.calculate.rule.processor.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.assetbill.dao.CombinedAssetBillDao;
import com.jiuqi.gcreport.asset.assetbill.dao.CommonAssetBillDao;
import com.jiuqi.gcreport.asset.assetbill.dto.GcAssetBillGroupDTO;
import com.jiuqi.gcreport.asset.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
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
import com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import java.util.ArrayList;
import java.util.Arrays;
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
public class GcCalcFixedAssetsBillRuleExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcFixedAssetsBillRuleExecutor.class);
    @Autowired
    private CombinedAssetBillDao combinedAssetBillDao;
    @Autowired
    private CommonAssetBillDao commonAssetBillDao;
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Autowired
    private GcCalcService gcCalcService;

    @Transactional(propagation=Propagation.NESTED, rollbackFor={Exception.class})
    public void execute(@NotNull AbstractUnionRule rule, GcCalcEnvContext env) {
        this.doExecute(env, rule);
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    private void doExecute(GcCalcEnvContext env, @NotNull AbstractUnionRule rule) {
        ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(0));
        FixedAssetsRuleDTO assetsRuleDTO = (FixedAssetsRuleDTO)rule;
        if (!this.checkRule(assetsRuleDTO)) {
            env.addResultItem(GcI18nUtil.getMessage((String)"gc.calculate.bill.assets.rule.function.error", (Object[])new Object[]{assetsRuleDTO.getLocalizedName()}));
            ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).setSuccessFlag(Boolean.FALSE.booleanValue());
            return;
        }
        ArrayList<GcAssetBillGroupDTO> fixedAssetGroups = new ArrayList<GcAssetBillGroupDTO>();
        fixedAssetGroups.addAll(this.getCombinedFixedAssetGroups(env));
        fixedAssetGroups.addAll(this.getCommonFixedAssetGroups(env));
        List<GcOffSetVchrDTO> allDTOs = Collections.synchronizedList(new ArrayList());
        try {
            List<GcAssetBillGroupDTO> acceptDatas;
            GcFormulaThreadContext.enableCache();
            List<GcAssetBillGroupDTO> list = acceptDatas = StringUtils.isEmpty((String)rule.getRuleCondition()) ? fixedAssetGroups : this.filterGcAssetBillGroupDTOS(rule, env, fixedAssetGroups);
            if (CollectionUtils.isEmpty(acceptDatas)) {
                this.deleteHistoryAdjustOffSetItems(env, assetsRuleDTO);
                return;
            }
            List<GcAssetBillGroupDTO> list2 = acceptDatas = assetsRuleDTO.getScrappedFlag() != false ? this.filterNotDisposalItem(acceptDatas, env.getCalcArgments().getPeriodStr()) : this.filterDisposalItem(acceptDatas, env.getCalcArgments().getPeriodStr());
            if (!CollectionUtils.isEmpty(acceptDatas)) {
                for (GcAssetBillGroupDTO acceptData : acceptDatas) {
                    int i = 0;
                    do {
                        ArrayList<DefaultTableEntity> items = new ArrayList<DefaultTableEntity>();
                        if (CollectionUtils.isEmpty((Collection)acceptData.getItems())) {
                            Object oppUnit = acceptData.getMaster().getFieldValue("OPPUNITCODE");
                            if (oppUnit == null || StringUtils.isEmpty((String)oppUnit.toString())) {
                                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[{}]\uff0c\u8d44\u4ea7\u6570\u636e[ID:{}]\u6ca1\u6709\u5b57\u8868\uff0c\u5e76\u4e14\u4e3b\u8868\u4e2d\u4e0d\u5b58\u5728\u5bf9\u65b9\u5355\u4f4d\u3002", (Object)rule.getLocalizedName(), (Object)acceptData.getMaster().getId());
                                continue;
                            }
                        } else {
                            items.add((DefaultTableEntity)acceptData.getItems().get(i++));
                        }
                        acceptData.getMaster().addFieldValue("sn", (Object)env.getCalcArgments().getSn());
                        GcOffSetVchrDTO itemDTO = this.executeSingleAcceptData(assetsRuleDTO, env, new GcAssetBillGroupDTO(acceptData.getMaster(), items));
                        if (itemDTO == null) continue;
                        allDTOs.add(itemDTO);
                        if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
                        env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(itemDTO.getItems());
                    } while (acceptData.getItems() != null && i < acceptData.getItems().size());
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.assets.rule.error", (Object[])new Object[]{assetsRuleDTO.getLocalizedName()}) + e.getMessage(), (Throwable)e);
        }
        finally {
            GcFormulaThreadContext.releaseCache();
        }
        this.deleteHistoryAdjustOffSetItems(env, assetsRuleDTO);
        if (!CollectionUtils.isEmpty(allDTOs)) {
            allDTOs.forEach(offsetDTO -> offsetDTO.setConsFormulaCalcType("autoFlag"));
            this.offSetItemAdjustService.batchSave(allDTOs);
            allDTOs.forEach(offSetVchrDTO -> {
                List items = offSetVchrDTO.getItems();
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addCreateOffsetItemCountValue(Integer.valueOf(CollectionUtils.isEmpty((Collection)items) ? 0 : items.size()));
                if (CollectionUtils.isEmpty((Collection)items)) {
                    return;
                }
            });
        }
    }

    private boolean checkRule(FixedAssetsRuleDTO assetsRuleDTO) {
        boolean hasDepreFunction = false;
        boolean hasDetailDepreFunction = false;
        for (FixedAssetsRuleDTO.Item item : assetsRuleDTO.getDebitItemList()) {
            if (hasDepreFunction && hasDetailDepreFunction) {
                return false;
            }
            if (StringUtils.isEmpty((String)item.getFetchFormula())) continue;
            if (!hasDetailDepreFunction && item.getFetchFormula().contains("DetailCommonAssetDepre")) {
                hasDetailDepreFunction = true;
                continue;
            }
            if (hasDepreFunction || !item.getFetchFormula().contains("CommonAssetDepre") || item.getFetchFormula().contains("DetailCommonAssetDepre")) continue;
            hasDepreFunction = true;
        }
        for (FixedAssetsRuleDTO.Item item : assetsRuleDTO.getCreditItemList()) {
            if (hasDepreFunction && hasDetailDepreFunction) {
                return false;
            }
            if (StringUtils.isEmpty((String)item.getFetchFormula())) continue;
            if (!hasDetailDepreFunction && item.getFetchFormula().contains("DetailCommonAssetDepre")) {
                hasDetailDepreFunction = true;
                continue;
            }
            if (hasDepreFunction || !item.getFetchFormula().contains("CommonAssetDepre") || item.getFetchFormula().contains("DetailCommonAssetDepre")) continue;
            hasDepreFunction = true;
        }
        return !hasDepreFunction || !hasDetailDepreFunction;
    }

    private List<GcAssetBillGroupDTO> getCommonFixedAssetGroups(GcCalcEnvContext env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        if (calcArgments.getPreCalcFlag().get()) {
            String billCode = (String)calcArgments.getExtendInfo().get("BILLCODE");
            DefaultTableEntity assetBillEntity = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_COMMONASSETBILL");
            if (assetBillEntity == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(new GcAssetBillGroupDTO(assetBillEntity, null));
        }
        String hbOrgId = env.getCalcArgments().getOrgId();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgTool.getOrgByCode(hbOrgId);
        List<DefaultTableEntity> assetDatas = this.commonAssetBillDao.getCommonFixedAssetDatas(env, hbOrg);
        if (assetDatas == null || assetDatas.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList<GcAssetBillGroupDTO> groups = new ArrayList<GcAssetBillGroupDTO>();
        for (DefaultTableEntity item : assetDatas) {
            GcAssetBillGroupDTO gcInvestmentGroup = new GcAssetBillGroupDTO(item, null);
            groups.add(gcInvestmentGroup);
        }
        return groups;
    }

    private List<GcAssetBillGroupDTO> filterNotDisposalItem(List<GcAssetBillGroupDTO> acceptDatas, String periodStr) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        Date calcDate = yp.formatYP().getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calcDate);
        int currentYear = calendar.get(1);
        return acceptDatas.stream().filter(data -> {
            Object disposalDateObj = data.getMaster().getFieldValue("DISPOSALDATE");
            if (disposalDateObj == null) {
                return false;
            }
            Date disposalDate = (Date)disposalDateObj;
            if (calcDate.compareTo(disposalDate) < 0) {
                return false;
            }
            calendar.setTime(disposalDate);
            return calendar.get(1) == currentYear;
        }).collect(Collectors.toList());
    }

    private List<GcAssetBillGroupDTO> filterDisposalItem(List<GcAssetBillGroupDTO> acceptDatas, String periodStr) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        Date calcDate = yp.formatYP().getEndDate();
        return acceptDatas.stream().filter(data -> {
            Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)data.getMaster(), (String)"PURCHASEDATE");
            if (purchaseDate != null && calcDate.compareTo(purchaseDate) < 0) {
                return false;
            }
            Date disposalDate = InvestBillTool.getDateValue((DefaultTableEntity)data.getMaster(), (String)"DISPOSALDATE");
            if (disposalDate == null) {
                return true;
            }
            return calcDate.compareTo(disposalDate) < 0;
        }).collect(Collectors.toList());
    }

    private GcOffSetVchrDTO executeSingleAcceptData(FixedAssetsRuleDTO rule, GcCalcEnvContext env, GcAssetBillGroupDTO acceptData) {
        boolean isCreateGcOffSetVchrDTO;
        GcOffSetVchrItemDTO phsRecord;
        String subjectCode;
        GcOffSetVchrItemDTO offSetVchrItem;
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        DimensionValueSet dset = DimensionUtils.generateDimSet((Object)acceptData.getMaster().getFieldValue("UNITCODE"), (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        GcOffSetVchrDTO offSetVchrDTO = new GcOffSetVchrDTO(env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(rule.getId()));
        ArrayList<GcOffSetVchrItemDTO> records = new ArrayList<GcOffSetVchrItemDTO>();
        ArrayList<FixedAssetsRuleDTO.Item> debitPhsFormulaList = new ArrayList<FixedAssetsRuleDTO.Item>();
        ArrayList<FixedAssetsRuleDTO.Item> creditPhsFormulaList = new ArrayList<FixedAssetsRuleDTO.Item>();
        for (FixedAssetsRuleDTO.Item item : rule.getDebitItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                debitPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(offSetVchrDTO.getMrecid(), calcArgments, rule);
            try {
                subjectCode = this.getSubjectCode(rule, item, env, dset, acceptData);
            }
            catch (BusinessRuntimeException e) {
                this.logger.info(e.getMessage());
                continue;
            }
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, item, subjectCode, dset, OrientEnum.D, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit())) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[{}]\uff0c\u8d44\u4ea7\u6570\u636e[ID:{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u501f\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula());
                if (!env.getCalcArgments().getPreCalcFlag().get()) continue;
            }
            debitSum = NumberUtils.sum((Double)debitSum, (Double)offSetVchrItem.getOffSetDebit());
            records.add(offSetVchrItem);
        }
        for (FixedAssetsRuleDTO.Item item : rule.getCreditItemList()) {
            if (!StringUtils.isEmpty((String)item.getFetchFormula()) && item.getFetchFormula().contains("PHS")) {
                creditPhsFormulaList.add(item);
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(offSetVchrDTO.getMrecid(), calcArgments, rule);
            try {
                subjectCode = this.getSubjectCode(rule, item, env, dset, acceptData);
            }
            catch (BusinessRuntimeException e) {
                this.logger.info(e.getMessage());
                continue;
            }
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, item, subjectCode, dset, OrientEnum.C, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit())) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[{}]\uff0c\u8d44\u4ea7\u6570\u636e[ID:{}]\u5728\u89c4\u5219\u6761\u76ee[{}]\u4e2d\u53d6\u6570\u8d37\u65b9\u62b5\u9500\u91d1\u989d\u4e3a0\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula());
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
        this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[{}]\uff0c\u8d44\u4ea7\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u5bb9\u5dee\u7ea6\u675f\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", (Object)rule.getLocalizedName(), (Object)acceptData.getMaster().getId());
        return null;
    }

    private GcOffSetVchrItemDTO phsRecord(FixedAssetsRuleDTO rule, GcCalcEnvContext env, GcAssetBillGroupDTO acceptData, GcCalcArgmentsDTO calcArgments, DimensionValueSet dset, String mrecid, List<FixedAssetsRuleDTO.Item> debitPhsFormulaList, List<FixedAssetsRuleDTO.Item> creditPhsFormulaList, double diffValue) {
        GcOffSetVchrItemDTO offSetVchrItem;
        String subjectCode;
        for (FixedAssetsRuleDTO.Item fetchFormula : debitPhsFormulaList) {
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(-diffValue));
            try {
                subjectCode = this.getSubjectCode(rule, fetchFormula, env, dset, acceptData);
            }
            catch (BusinessRuntimeException e) {
                this.logger.info(e.getMessage());
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, fetchFormula, subjectCode, dset, OrientEnum.D, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetDebit()) && !env.getCalcArgments().getPreCalcFlag().get()) continue;
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        for (FixedAssetsRuleDTO.Item fetchFormula : creditPhsFormulaList) {
            env.getCalcContextExpandVariableCenter().setPhsValue(Double.valueOf(diffValue));
            try {
                subjectCode = this.getSubjectCode(rule, fetchFormula, env, dset, acceptData);
            }
            catch (BusinessRuntimeException e) {
                this.logger.info(e.getMessage());
                continue;
            }
            offSetVchrItem = this.createNewOffSetVchrItem(mrecid, calcArgments, rule);
            this.fillOffSetVchrItem(offSetVchrItem, acceptData, fetchFormula, subjectCode, dset, OrientEnum.C, env);
            if (NumberUtils.isZreo((Double)offSetVchrItem.getOffSetCredit()) && !env.getCalcArgments().getPreCalcFlag().get()) continue;
            offSetVchrItem.setMemo(GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.memo"));
            return offSetVchrItem;
        }
        return null;
    }

    private List<GcAssetBillGroupDTO> filterGcAssetBillGroupDTOS(@NotNull AbstractUnionRule rule, GcCalcEnvContext env, List<GcAssetBillGroupDTO> fixedAssetGroups) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        DimensionValueSet dset = DimensionUtils.generateDimSet(null, (Object)calcArgments.getPeriodStr(), (Object)calcArgments.getCurrency(), (Object)calcArgments.getOrgType(), (String)calcArgments.getSelectAdjustCode(), (String)calcArgments.getTaskId());
        List<GcAssetBillGroupDTO> acceptDatas = fixedAssetGroups.stream().filter(data -> {
            boolean isAccept = false;
            try {
                dset.setValue("MD_ORG", data.getMaster().getFieldValue("UNITCODE"));
                isAccept = this.billFormulaEvalService.checkAssetBillData(env, dset, rule.getRuleCondition(), (GcAssetBillGroupDTO)((Object)data));
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.assets.rule.condition.error", (Object[])new Object[]{rule.getLocalizedName(), rule.getRuleCondition()}) + e.getMessage());
            }
            if (!isAccept) {
                this.logger.info("\u5408\u5e76\u8ba1\u7b97\u8d44\u4ea7\u89c4\u5219[{}]\uff0c\u8d44\u4ea7\u6570\u636e[ID:{}]\u4e0d\u6ee1\u8db3\u9002\u7528\u6761\u4ef6[{}]\uff0c\u4e0d\u751f\u6210\u62b5\u9500\u5206\u5f55\u3002", rule.getLocalizedName(), data.getMaster().getId(), rule.getRuleCondition());
            }
            return isAccept;
        }).collect(Collectors.toList());
        return acceptDatas;
    }

    private void deleteHistoryAdjustOffSetItems(GcCalcEnvContext env, @NotNull FixedAssetsRuleDTO rule) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        this.gcCalcService.deleteAutoOffsetEntrysByRule(rule.getId(), calcArgments);
    }

    private List<GcAssetBillGroupDTO> getCombinedFixedAssetGroups(GcCalcEnvContext env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        if (calcArgments.getPreCalcFlag().get()) {
            String billCode = (String)calcArgments.getExtendInfo().get("BILLCODE");
            DefaultTableEntity assetBillEntity = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_COMBINEDASSETBILL");
            if (assetBillEntity == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(new GcAssetBillGroupDTO(assetBillEntity, null));
        }
        String hbOrgId = env.getCalcArgments().getOrgId();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgTool.getOrgByCode(hbOrgId);
        List<DefaultTableEntity> assetItemDatas = this.combinedAssetBillDao.getFixedAssetItemsDatas(env, hbOrg);
        if (assetItemDatas == null || assetItemDatas.size() == 0) {
            return Collections.emptyList();
        }
        Map<String, List<DefaultTableEntity>> assetItemMap = assetItemDatas.stream().collect(Collectors.groupingBy(o -> StringUtils.toViewString((Object)o.getFieldValue("MASTERID"))));
        List<DefaultTableEntity> assetDatas = this.combinedAssetBillDao.getFixedAssetsDatas(env, assetItemMap.keySet());
        if (assetDatas == null || assetDatas.size() == 0) {
            return Collections.emptyList();
        }
        Map<String, DefaultTableEntity> assetMap = assetDatas.stream().collect(Collectors.toMap(DefaultTableEntity::getId, eo -> eo));
        Map<String, List<DefaultTableEntity>> finalInvestmentItemMap = assetItemMap;
        ArrayList<GcAssetBillGroupDTO> groups = new ArrayList<GcAssetBillGroupDTO>();
        assetMap.entrySet().stream().forEach(entry -> {
            String id = (String)entry.getKey();
            DefaultTableEntity master = (DefaultTableEntity)entry.getValue();
            List items = null;
            if (finalInvestmentItemMap != null) {
                items = (List)finalInvestmentItemMap.get(id);
            }
            GcAssetBillGroupDTO gcInvestmentGroup = new GcAssetBillGroupDTO(master, items);
            groups.add(gcInvestmentGroup);
        });
        return groups;
    }

    private GcOffSetVchrItemDTO createNewOffSetVchrItem(String mrecid, GcCalcArgmentsDTO calcArgments, FixedAssetsRuleDTO rule) {
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
        initOffSetVchrItem.setOffSetSrcType(OffSetSrcTypeEnum.CONSOLIDATE);
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

    private void fillOffSetVchrItem(GcOffSetVchrItemDTO offSetVchrItemDTO, GcAssetBillGroupDTO assetBillGroupDTO, FixedAssetsRuleDTO.Item item, String subjectCode, DimensionValueSet dimensionValueSet, OrientEnum orientEnum, GcCalcEnvContext env) {
        String fetchFormula = item.getFetchFormula();
        String unitId = StringUtils.toViewString((Object)assetBillGroupDTO.getMaster().getFieldValue("UNITCODE"));
        String oppUnitId = StringUtils.toViewString((Object)(CollectionUtils.isEmpty((Collection)assetBillGroupDTO.getItems()) ? assetBillGroupDTO.getMaster().getFieldValue("OPPUNITCODE") : ((DefaultTableEntity)assetBillGroupDTO.getItems().get(0)).getFieldValue("OPPUNITCODE")));
        offSetVchrItemDTO.setSortOrder(Double.valueOf(item.getSort() == null ? 0.0 : (double)item.getSort().intValue()));
        offSetVchrItemDTO.setUnitId(unitId);
        offSetVchrItemDTO.setOppUnitId(oppUnitId);
        offSetVchrItemDTO.setSrcOffsetGroupId(assetBillGroupDTO.getMaster().getId());
        offSetVchrItemDTO.setSubjectCode(subjectCode);
        this.initDimensions(dimensionValueSet, offSetVchrItemDTO, assetBillGroupDTO, item, env, unitId, oppUnitId);
        Object assetTitle = assetBillGroupDTO.getMaster().getFieldValue("ASSETTITLE");
        if (assetTitle != null) {
            offSetVchrItemDTO.addFieldValue("ASSETTITLE", assetTitle);
        }
        if (StringUtils.isEmpty((String)fetchFormula)) {
            return;
        }
        Assert.isFalse((boolean)"PHS".equalsIgnoreCase(fetchFormula), (String)GcI18nUtil.getMessage((String)"gc.calculate.bill.phs.error"), (Object[])new Object[0]);
        double result = this.billFormulaEvalService.evaluateAssetBillData(env, dimensionValueSet, fetchFormula, assetBillGroupDTO);
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

    private void initDimensions(DimensionValueSet dimensionValueSet, GcOffSetVchrItemDTO offSetVchrItemDTO, GcAssetBillGroupDTO assetBillGroupDTO, FixedAssetsRuleDTO.Item item, GcCalcEnvContext env, String unitId, String oppUnitId) {
        Map dimensions = item.getDimensions();
        if (dimensions == null) {
            return;
        }
        for (String dimKey : dimensions.keySet()) {
            if (dimKey.contains("customizeFormula")) continue;
            String dimValueSource = (String)dimensions.get(dimKey);
            if ("UNITCODE".equals(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, unitId, dimKey));
                continue;
            }
            if ("OPPUNITCODE".equals(dimValueSource)) {
                offSetVchrItemDTO.addFieldValue(dimKey, (Object)this.getDimValueByDimCodeAndOrg(env, oppUnitId, dimKey));
                continue;
            }
            if ("ACCOUNT".equals(dimValueSource)) {
                Object fieldValue = null;
                if (!CollectionUtils.isEmpty((Collection)assetBillGroupDTO.getItems())) {
                    fieldValue = ((DefaultTableEntity)assetBillGroupDTO.getItems().get(0)).getFieldValue(dimKey);
                }
                if (fieldValue != null) {
                    offSetVchrItemDTO.addFieldValue(dimKey, fieldValue);
                    continue;
                }
                offSetVchrItemDTO.addFieldValue(dimKey, assetBillGroupDTO.getMaster().getFieldValue(dimKey));
                continue;
            }
            if (!"customizeFormula".equals(dimValueSource)) continue;
            if (StringUtils.isEmpty((String)((String)dimensions.get(dimKey + "_customizeFormula")))) {
                offSetVchrItemDTO.addFieldValue(dimKey, null);
                continue;
            }
            AbstractData data = this.billFormulaEvalService.getAssetBillData(env, dimensionValueSet, (String)dimensions.get(dimKey + "_customizeFormula"), assetBillGroupDTO);
            offSetVchrItemDTO.addFieldValue(dimKey, data.getAsObject());
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

    private String getSubjectCode(FixedAssetsRuleDTO rule, FixedAssetsRuleDTO.Item item, GcCalcEnvContext env, DimensionValueSet dset, GcAssetBillGroupDTO acceptData) {
        String subjectCode = null;
        FixedAssetsTypeEnum type = item.getType();
        if (type == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.assets.rule.item.type.empty", (Object[])new Object[]{rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula()}));
        }
        switch (type) {
            case DEPRECIATION_SUBJECT: {
                subjectCode = StringUtils.toViewString((Object)acceptData.getMaster().getFieldValue("DPCASUBJECT"));
                break;
            }
            case DISPOSE_SUBJECT: {
                subjectCode = StringUtils.toViewString((Object)acceptData.getMaster().getFieldValue("DSPESUBJECTCODE"));
                break;
            }
            case ASSET_SUBJECT: {
                subjectCode = StringUtils.toViewString((Object)acceptData.getMaster().getFieldValue("ASSETTYPE"));
                if (!StringUtils.isEmpty((String)subjectCode) || CollectionUtils.isEmpty((Collection)acceptData.getItems())) break;
                subjectCode = StringUtils.toViewString((Object)((DefaultTableEntity)acceptData.getItems().get(0)).getFieldValue("ASSETTYPE"));
                break;
            }
            case FORMULA: {
                if (StringUtils.isEmpty((String)item.getSubjectCode())) break;
                subjectCode = this.billFormulaEvalService.evaluateAssetBillDataGetSubject(env, dset, item.getSubjectCode(), acceptData);
                break;
            }
            case CUSTOMIZE: {
                subjectCode = item.getSubjectCode();
            }
        }
        if (StringUtils.isEmpty(subjectCode)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.assets.rule.item.subject.empty", (Object[])new Object[]{rule.getLocalizedName(), acceptData.getMaster().getId(), item.getFetchFormula()}));
        }
        return subjectCode;
    }
}

