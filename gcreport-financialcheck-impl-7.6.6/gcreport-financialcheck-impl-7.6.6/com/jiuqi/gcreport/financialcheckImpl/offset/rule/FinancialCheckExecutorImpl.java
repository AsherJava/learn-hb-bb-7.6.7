/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.FinancialCheckOffsetService;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.GcRelatedItemOffsetDataQueryService;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.AgingExecutorGather;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.MemoryConversionExecutor;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRuleConverter;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FinancialCheckExecutorImpl {
    private static Logger logger = LoggerFactory.getLogger(FinancialCheckExecutorImpl.class);
    protected GcRelatedItemOffsetDataQueryService offsetDataQueryService = (GcRelatedItemOffsetDataQueryService)SpringContextUtils.getBean(GcRelatedItemOffsetDataQueryService.class);
    protected ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
    protected String conSystemId;
    protected FinancialCheckRuleDTO financialCheckRuleDTO;
    protected GcCalcArgmentsDTO calcArgs;
    protected GcOrgCenterService conTool;
    protected GcOrgCenterService finalChkTool;
    protected AgingExecutorGather agingExecutorGather;
    protected MemoryConversionExecutor memoryConversionExecutor;
    protected FinancialCheckRuleConverter ruleConverter;
    protected FinancialCheckOffsetService financialCheckOffsetService;
    protected String calcArgsStr;

    public FinancialCheckExecutorImpl(AbstractUnionRule rule, GcCalcArgmentsDTO calcArgs) {
        this.financialCheckRuleDTO = (FinancialCheckRuleDTO)rule;
        this.calcArgs = calcArgs;
        this.agingExecutorGather = (AgingExecutorGather)SpringContextUtils.getBean(AgingExecutorGather.class);
        this.conSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdByTaskIdAndPeriodStr(calcArgs.getTaskId(), calcArgs.getPeriodStr());
        YearPeriodObject yp = new YearPeriodObject(null, calcArgs.getPeriodStr());
        this.conTool = GcOrgPublicTool.getInstance((String)calcArgs.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        this.finalChkTool = GcOrgPublicTool.getInstance((String)"MD_ORG_CORPORATE", (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        this.memoryConversionExecutor = (MemoryConversionExecutor)SpringContextUtils.getBean(MemoryConversionExecutor.class);
        this.ruleConverter = (FinancialCheckRuleConverter)SpringContextUtils.getBean(FinancialCheckRuleConverter.class);
        this.financialCheckOffsetService = (FinancialCheckOffsetService)SpringContextUtils.getBean(FinancialCheckOffsetService.class);
    }

    public abstract void calMerge(GcCalcEnvContext var1);

    protected List<GcFcRuleUnOffsetDataDTO> sum(List<GcFcRuleUnOffsetDataDTO> records) {
        List<GcFcRuleUnOffsetDataDTO> unOffsetDataS = this.memoryConversionExecutor.sum(records);
        logger.info("\u6c47\u603b\u540e\u7684\u6570\u636e\u6761\u6570\u4e3a\uff1a" + unOffsetDataS.size() + "\u6761\uff0c\u5177\u4f53\u6570\u636e\u4e3a\uff1a" + (unOffsetDataS.isEmpty() ? "[]" : unOffsetDataS.toString()) + "," + this.calcArgsStr);
        return unOffsetDataS;
    }

    protected void decorateRuleInfo(List<GcFcRuleUnOffsetDataDTO> records) {
        for (GcFcRuleUnOffsetDataDTO record : records) {
            List offsetRelEOS = (List)record.getFieldValue("VCHROFFSETRELS");
            for (RelatedItemGcOffsetRelDTO offsetRelEO : offsetRelEOS) {
                offsetRelEO.setSystemId(this.financialCheckRuleDTO.getReportSystem());
                offsetRelEO.setCheckState(this.financialCheckRuleDTO.isChecked() ? CheckStateEnum.CHECKED.toString() : CheckStateEnum.UNCHECKED.toString());
                offsetRelEO.setUnionRuleId(this.financialCheckRuleDTO.getId());
                offsetRelEO.setDataTime(this.calcArgs.getPeriodStr());
                offsetRelEO.setConversionRate(record.getConversionRate());
                offsetRelEO.setConversionCurr(this.calcArgs.getCurrency());
            }
        }
    }
}

