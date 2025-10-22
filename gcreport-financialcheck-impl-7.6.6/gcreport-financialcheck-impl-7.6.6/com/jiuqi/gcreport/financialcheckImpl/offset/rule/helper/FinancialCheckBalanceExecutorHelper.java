/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialCheckBalanceExecutorHelper
extends FinancialCheckExecutorImpl {
    private static Logger logger = LoggerFactory.getLogger(FinancialCheckBalanceExecutorHelper.class);

    public FinancialCheckBalanceExecutorHelper(AbstractUnionRule rule, GcCalcArgmentsDTO calcArgs) {
        super(rule, calcArgs);
    }

    @Override
    public void calMerge(GcCalcEnvContext env) {
        if (Objects.isNull(this.financialCheckRuleDTO)) {
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u7684\u89c4\u5219\u4e0d\u80fd\u4e3a\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)this.conSystemId)) {
            throw new BusinessRuntimeException("\u64cd\u4f5c\u5931\u8d25\uff0c\u627e\u4e0d\u5230\u5f53\u524d\u4efb\u52a1\u7684\u5408\u5e76\u4f53\u7cfb\uff0c\u8bf7\u5148\u5728\u5408\u5e76\u4f53\u7cfb\u7ef4\u62a4\u4e0e\u5f53\u524d\u4efb\u52a1\u7684\u5173\u8054\u5173\u7cfb\u3002");
        }
        boolean isChecked = this.financialCheckRuleDTO.isChecked();
        Set<String> allBoundSubjects = this.ruleConverter.getAllBoundSubjects(this.financialCheckRuleDTO);
        if (CollectionUtils.isEmpty(allBoundSubjects)) {
            return;
        }
        this.financialCheckOffsetService.deleteOffsetItemAndRel(this.financialCheckRuleDTO.getId(), this.financialCheckRuleDTO.getDelCheckedOffsetFlag(), this.calcArgs);
        List<Object> relatedItems = this.queryRelatedItem(allBoundSubjects, isChecked);
        relatedItems = relatedItems.stream().filter(item -> !StringUtils.isEmpty((String)item.getGcSubjectCode())).collect(Collectors.toList());
        ArrayList<GcOffsetRelatedItemEO> allUnOffsetData = new ArrayList<GcOffsetRelatedItemEO>();
        ArrayList<GcOffsetRelatedItemEO> noRuleData = new ArrayList<GcOffsetRelatedItemEO>();
        relatedItems.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getUnionRuleId())) {
                noRuleData.add((GcOffsetRelatedItemEO)item);
            } else {
                allUnOffsetData.add((GcOffsetRelatedItemEO)item);
            }
        });
        List<GcOffsetRelatedItemEO> ruleMatchedItems = this.financialCheckOffsetService.batchMatchRule(noRuleData, this.conSystemId);
        if (!CollectionUtils.isEmpty(ruleMatchedItems)) {
            allUnOffsetData.addAll(ruleMatchedItems);
        }
        List<GcOffsetRelatedItemEO> unOffsetData = allUnOffsetData.stream().filter(item -> this.financialCheckRuleDTO.getId().equals(item.getUnionRuleId())).collect(Collectors.toList());
        List<FinancialCheckRuleExecutorImpl.OffsetResult> offsetResults = this.autoCanOffsetItems(unOffsetData);
        boolean offsetResultsIsEmpty = CollectionUtils.isEmpty(offsetResults);
        logger.info("\u751f\u6210\u62b5\u9500\u5206\u5f55\u7684\u6570\u636e\u7ec4\u6570\u4e3a\uff1a" + (offsetResultsIsEmpty ? 0 : offsetResults.size()) + "\u7ec4," + this.calcArgsStr);
        if (CollectionUtils.isEmpty(offsetResults)) {
            return;
        }
        for (FinancialCheckRuleExecutorImpl.OffsetResult offsetResult2 : offsetResults) {
            this.financialCheckOffsetService.saveOffsetData(offsetResult2, false);
        }
        AtomicBoolean preCalcFlag = env.getCalcArgments().getPreCalcFlag();
        if (preCalcFlag.get()) {
            offsetResults.forEach(offsetResult -> {
                GcOffSetVchrDTO offsetVchr = offsetResult.getOffsetVchr();
                if (offsetVchr == null || CollectionUtils.isEmpty((Collection)offsetVchr.getItems())) {
                    return;
                }
                env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(offsetVchr.getItems());
            });
            throw new BusinessRuntimeException("\u5408\u5e76\u8ba1\u7b97\u9884\u6267\u884c\u901a\u8fc7\u629b\u5f02\u5e38\u7684\u65b9\u5f0f\u6765\u8fdb\u884c\u4e0d\u63d0\u4ea4\u4e8b\u52a1\u64cd\u4f5c");
        }
    }

    public List<FinancialCheckRuleExecutorImpl.OffsetResult> autoCanOffsetItems(List<GcOffsetRelatedItemEO> unOffsetData) {
        this.calcArgsStr = MessageFormat.format("\u672c\u6b21\u8ba1\u7b97\u7684\u53c2\u6570\u4e3a: [ \u62a5\u8868\u65b9\u6848:{0}, \u5408\u5e76\u5355\u4f4d:{1}, \u5e01\u79cd:{2}, \u65f6\u671f\uff1a{3}, \u4efb\u52a1:{4} ], \u89c4\u5219\u540d\u79f0\u4e3a\uff1a{5}", this.calcArgs.getSchemeId(), this.calcArgs.getOrgId(), this.calcArgs.getCurrency(), this.calcArgs.getPeriodStr(), this.calcArgs.getTaskId(), this.financialCheckRuleDTO.getTitle());
        boolean vchrBalancesIsEmpty = CollectionUtils.isEmpty(unOffsetData);
        logger.info("\u67e5\u8be2\u5230\u7684\u4f59\u989d\u6570\u636e\u6761\u6570\u4e3a\uff1a" + (vchrBalancesIsEmpty ? 0 : unOffsetData.size()) + "\u6761\uff0c\u5177\u4f53\u6570\u636e\u4e3a\uff1a" + (vchrBalancesIsEmpty ? "[]" : unOffsetData.toString()) + "," + this.calcArgsStr);
        this.calcAging(unOffsetData);
        List<GcFcRuleUnOffsetDataDTO> fcRuleUnOffsetDatas = FinancialCheckRuleExecutorImpl.convertOffset2RuleUnOffsetData(unOffsetData);
        List<GcFcRuleUnOffsetDataDTO> unOffsetDataS = this.coreCalc(fcRuleUnOffsetDatas);
        FinancialCheckRuleDTO ruleClone = this.ruleConverter.convert2ConSubject(this.financialCheckRuleDTO);
        FinancialCheckRuleExecutorImpl ruleExecutor = FinancialCheckRuleExecutorImpl.newInstance(this.calcArgs, ruleClone, this.conSystemId);
        return ruleExecutor.reltxnCanOffsetItems(unOffsetDataS);
    }

    public List<GcFcRuleUnOffsetDataDTO> coreCalc(List<GcFcRuleUnOffsetDataDTO> relatedItemEOS) {
        relatedItemEOS = this.sum(relatedItemEOS);
        this.decorateRuleInfo(relatedItemEOS);
        boolean unOffsetDataIsEmpty = CollectionUtils.isEmpty(relatedItemEOS);
        logger.info("\u5173\u8054\u8f6c\u5408\u5e76\u540e\u7684\u6570\u636e\u6761\u6570\u4e3a\uff1a" + (unOffsetDataIsEmpty ? 0 : relatedItemEOS.size()) + "\u6761\uff0c\u5177\u4f53\u6570\u636e\u4e3a\uff1a" + (unOffsetDataIsEmpty ? "[]" : relatedItemEOS) + "," + this.calcArgsStr);
        return relatedItemEOS;
    }

    private void calcAging(List<GcOffsetRelatedItemEO> unOffsetDataDTOS) {
        logger.info("\u8ba1\u7b97\u8d26\u9f84\u7ed3\u675f");
    }

    private List<GcOffsetRelatedItemEO> queryRelatedItem(Set<String> boundSubjects, boolean isChecked) {
        RelationToMergeArgDTO relationToMergeArgDTO = new RelationToMergeArgDTO();
        relationToMergeArgDTO.setAcctPeriod(this.calcArgs.getAcctPeriod());
        relationToMergeArgDTO.setAcctYear(this.calcArgs.getAcctYear());
        relationToMergeArgDTO.setCurrency(this.calcArgs.getCurrency());
        relationToMergeArgDTO.setOrgCode(this.calcArgs.getOrgId());
        relationToMergeArgDTO.setOrgType(this.calcArgs.getOrgType());
        relationToMergeArgDTO.setPeriodScheme(this.calcArgs.getPeriodStr());
        relationToMergeArgDTO.setPeriodType(this.calcArgs.getPeriodType());
        relationToMergeArgDTO.setBoundSubjects(boundSubjects);
        relationToMergeArgDTO.setChecked(isChecked);
        relationToMergeArgDTO.setSystemId(this.conSystemId);
        List<GcOffsetRelatedItemEO> result = this.financialCheckOffsetService.listByOffsetCondition(relationToMergeArgDTO);
        return result;
    }
}

