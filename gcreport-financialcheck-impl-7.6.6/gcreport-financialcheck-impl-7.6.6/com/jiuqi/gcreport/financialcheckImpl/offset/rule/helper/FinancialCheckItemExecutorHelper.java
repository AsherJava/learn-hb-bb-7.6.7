/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.helper.FcCheckData2OffsetDataHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.VoucherAgingExecutor;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.service.GcFinancialCheckFormulaEvalService;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.impl.GcRelatedOffsetVoucherItemServiceImpl;
import com.jiuqi.gcreport.financialcheckImpl.util.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.xlib.utils.StringUtil;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinancialCheckItemExecutorHelper
extends FinancialCheckExecutorImpl {
    private static Logger logger = LoggerFactory.getLogger(FinancialCheckItemExecutorHelper.class);
    private GcFinancialCheckFormulaEvalService inputDataFormulaEvalService = (GcFinancialCheckFormulaEvalService)SpringContextUtils.getBean(GcFinancialCheckFormulaEvalService.class);

    public FinancialCheckItemExecutorHelper(AbstractUnionRule rule, GcCalcArgmentsDTO calcArgs) {
        super(rule, calcArgs);
    }

    @Override
    public void calMerge(GcCalcEnvContext env) {
        List<Object> unOffsetDatas;
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
        if (isChecked) {
            unOffsetDatas = GcRelatedOffsetVoucherItemServiceImpl.convert2UnOffsetData(this.queryOffsetVoucherItems(allBoundSubjects));
            unOffsetDatas = unOffsetDatas.stream().filter(item -> {
                if (StringUtils.isEmpty((String)this.financialCheckRuleDTO.getRuleCondition())) {
                    return true;
                }
                Map fields = item.getFields();
                DimensionValueSet ds = DimensionUtils.generateDimSet(fields.get("MDCODE"), (Object)this.calcArgs.getPeriodStr(), (Object)this.calcArgs.getCurrency(), (Object)this.calcArgs.getOrgType(), (String)((String)item.getFieldValue("ADJUST")), (String)this.calcArgs.getTaskId());
                return this.inputDataFormulaEvalService.checkMxUnOffsetData(ds, this.financialCheckRuleDTO.getRuleCondition(), (GcRelatedItemEO)item, this.calcArgs.getTaskId(), this.calcArgs.getPeriodStr());
            }).collect(Collectors.toList());
        } else {
            List<GcRelatedItemEO> relatedItems = this.queryRelatedItem(allBoundSubjects, isChecked);
            relatedItems = FcCheckData2OffsetDataHelper.newInstance(env.getCalcArgments().getPeriodStr()).filterByApplicableCondition(relatedItems, (AbstractUnionRule)this.financialCheckRuleDTO, env.getCalcArgments());
            unOffsetDatas = FinancialCheckRuleExecutorImpl.convertOrigin2RuleUnOffsetData(relatedItems);
        }
        List<FinancialCheckRuleExecutorImpl.OffsetResult> offsetResults = this.autoCanOffsetItems(unOffsetDatas);
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

    public List<FinancialCheckRuleExecutorImpl.OffsetResult> autoCanOffsetItems(List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        this.calcArgsStr = MessageFormat.format("\u672c\u6b21\u8ba1\u7b97\u7684\u53c2\u6570\u4e3a: [ \u62a5\u8868\u65b9\u6848:{0}, \u5408\u5e76\u5355\u4f4d:{1}, \u5e01\u79cd:{2}, \u65f6\u671f\uff1a{3}, \u4efb\u52a1:{4} ], \u89c4\u5219\u540d\u79f0\u4e3a\uff1a{5}", this.calcArgs.getSchemeId(), this.calcArgs.getOrgId(), this.calcArgs.getCurrency(), this.calcArgs.getPeriodStr(), this.calcArgs.getTaskId(), this.financialCheckRuleDTO.getTitle());
        boolean vchrBalancesIsEmpty = CollectionUtils.isEmpty(unOffsetDatas);
        logger.info("\u67e5\u8be2\u5230\u7684\u4f59\u989d\u8868\u6570\u636e\u6761\u6570\u4e3a\uff1a" + (vchrBalancesIsEmpty ? 0 : unOffsetDatas.size()) + "\u6761\uff0c\u5177\u4f53\u6570\u636e\u4e3a\uff1a" + (vchrBalancesIsEmpty ? "[]" : unOffsetDatas.toString()) + "," + this.calcArgsStr);
        List<GcFcRuleUnOffsetDataDTO> unOffsetDataS = this.coreCalc(unOffsetDatas);
        FinancialCheckRuleDTO ruleClone = this.ruleConverter.convert2ConSubject(this.financialCheckRuleDTO);
        FinancialCheckRuleExecutorImpl ruleExecutor = FinancialCheckRuleExecutorImpl.newInstance(this.calcArgs, ruleClone, this.conSystemId);
        return ruleExecutor.reltxnCanOffsetItems(unOffsetDataS);
    }

    public List<GcFcRuleUnOffsetDataDTO> coreCalc(List<GcFcRuleUnOffsetDataDTO> relatedItemEOS) {
        relatedItemEOS = this.sum(relatedItemEOS);
        this.memoryConversion(relatedItemEOS);
        this.decorateRuleInfo(relatedItemEOS);
        this.convertSubjectUnit(relatedItemEOS);
        boolean datasIsEmpty = CollectionUtils.isEmpty(relatedItemEOS);
        logger.info("\u5173\u8054\u8f6c\u5408\u5e76\u540e\u7684\u6570\u636e\u6761\u6570\u4e3a\uff1a" + (datasIsEmpty ? 0 : relatedItemEOS.size()) + "\u6761\uff0c\u5177\u4f53\u6570\u636e\u4e3a\uff1a" + (datasIsEmpty ? "[]" : relatedItemEOS) + "," + this.calcArgsStr);
        return relatedItemEOS;
    }

    private void calcAging(List<GcFcRuleUnOffsetDataDTO> unOffsetDataDTOS) {
        ((VoucherAgingExecutor)SpringBeanUtils.getBean(VoucherAgingExecutor.class)).calcAging(unOffsetDataDTOS, this.calcArgs.getPeriodStr());
        logger.info("\u8ba1\u7b97\u8d26\u9f84\u7ed3\u675f");
    }

    private void memoryConversion(List<GcFcRuleUnOffsetDataDTO> records) {
        this.memoryConversionExecutor.memoryConversion(records, this.calcArgs);
        logger.info("\u6298\u7b97\u540e\u7684\u6570\u636e\u6761\u6570\u4e3a\uff1a" + records.size() + "\u6761\uff0c\u5177\u4f53\u6570\u636e\u4e3a\uff1a" + (records.isEmpty() ? "[]" : records) + "," + this.calcArgsStr);
    }

    private void convertSubjectUnit(List<GcFcRuleUnOffsetDataDTO> srcInputDataS) {
        Map<String, String> accountSubjectCode2ParentCodeMap = BaseDataUtils.getCode2ParentCodeMap("MD_ACCTSUBJECT");
        Set conSubjectSet = this.consolidatedSubjectService.listAllSubjectsBySystemId(this.conSystemId).stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
        srcInputDataS.forEach(inputData -> this.convertSubjectUnit((GcFcRuleUnOffsetDataDTO)inputData, accountSubjectCode2ParentCodeMap, conSubjectSet));
    }

    private GcFcRuleUnOffsetDataDTO convertSubjectUnit(GcFcRuleUnOffsetDataDTO inputDataEO, Map<String, String> accountSubjectCode2ParentCodeMap, Set<String> conSubjectSet) {
        String conSubjectCode = this.ruleConverter.getConsSubjectCode(inputDataEO.getSubjectCode(), accountSubjectCode2ParentCodeMap, conSubjectSet);
        if (Objects.isNull(conSubjectCode)) {
            throw new BusinessRuntimeException("\u96c6\u56e2\u79d1\u76ee\uff1a" + inputDataEO.getSubjectCode() + "\u627e\u4e0d\u5230\u76f8\u5e94\u7684\u5408\u5e76\u79d1\u76ee\u6620\u5c04");
        }
        inputDataEO.setSubjectCode(conSubjectCode);
        String localOrgCode = inputDataEO.getUnitId();
        String oppOrgCode = inputDataEO.getOppUnitId();
        inputDataEO.setOppUnitId(this.findConOrgCodeByFinalChkOrg(oppOrgCode));
        inputDataEO.setUnitId(this.findConOrgCodeByFinalChkOrg(localOrgCode));
        return inputDataEO;
    }

    private String findConOrgCodeByFinalChkOrg(String orgCode) {
        GcOrgCacheVO orgCacheVO = this.findConOrgByFinalChkOrg(orgCode);
        if (null == orgCacheVO) {
            return null;
        }
        return orgCacheVO.getCode();
    }

    private GcOrgCacheVO findConOrgByFinalChkOrg(String orgCode) {
        GcOrgCacheVO org = this.conTool.getOrgByCode(orgCode);
        if (Objects.nonNull(org)) {
            return org;
        }
        GcOrgCacheVO localOrg = this.finalChkTool.getOrgByCode(orgCode);
        if (Objects.isNull(localOrg)) {
            throw new BusinessRuntimeException("\u5355\u4f4d:" + orgCode + "\u5728\u6cd5\u4eba\u53e3\u5f84\u5355\u4f4d\u57fa\u7840\u6570\u636e\u4e2d\u67e5\u8be2\u4e0d\u5230");
        }
        String parentId = localOrg.getParentId();
        if (StringUtil.equals((String)"-", (String)parentId)) {
            throw new BusinessRuntimeException("\u5355\u4f4d:" + orgCode + "\u5728\u5408\u5e76\u5355\u4f4d\u57fa\u7840\u6570\u636e\u4e2d\u67e5\u8be2\u4e0d\u5230");
        }
        return this.findConOrgByFinalChkOrg(parentId);
    }

    private List<GcRelatedItemEO> queryRelatedItem(Set boundSubjects, boolean isChecked) {
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
        List<GcRelatedItemEO> result = this.offsetDataQueryService.relationToMergeExecute(relationToMergeArgDTO);
        return result;
    }

    private List<GcRelatedOffsetVoucherItemEO> queryOffsetVoucherItems(Set allBoundSubjects) {
        BalanceCondition balanceCondition = new BalanceCondition();
        balanceCondition.setOrgType(this.calcArgs.getOrgType());
        balanceCondition.setOrgId(this.calcArgs.getOrgId());
        balanceCondition.setAcctYear(this.calcArgs.getAcctYear());
        balanceCondition.setAcctPeriod(this.calcArgs.getAcctPeriod());
        balanceCondition.setPeriodType(this.calcArgs.getPeriodType());
        balanceCondition.setCurrency(this.calcArgs.getCurrency());
        balanceCondition.setBoundSubjects(allBoundSubjects);
        balanceCondition.setSystemId(this.conSystemId);
        balanceCondition.setPeriodStr(this.calcArgs.getPeriodStr());
        return this.offsetDataQueryService.queryOffsetVoucherItems(balanceCondition);
    }
}

