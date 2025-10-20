/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.invest.investbillcarryover.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElimFunction
extends AdvanceFunction
implements IGcFunction {
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public ElimFunction() {
        this.parameters().add(new Parameter("ruleTitle", 6, "\u89c4\u5219\u540d\u79f0", false));
        this.parameters().add(new Parameter("subjectCode", 6, "\u79d1\u76ee\u4ee3\u7801", false));
    }

    public String name() {
        return "ELIM";
    }

    public String title() {
        return "\u53d6\u6307\u5b9a\u89c4\u5219\uff0c\u79d1\u76ee\u5bf9\u5e94\u7684\u62b5\u9500\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        GcReportExceutorContext exeContext = (GcReportExceutorContext)qContext.getExeContext();
        DimensionValueSet dimensionValueSet = qContext.getMasterKeys();
        String periodStr = dimensionValueSet.getValue("DATATIME").toString();
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(exeContext.getTaskId(), periodStr);
        String orgType = this.getOrgType(exeContext.getTaskId(), periodStr);
        Set<String> ruleIdSet = this.getRuleId(parameters, systemId);
        List<GcOffSetVchrItemAdjustEO> offsetDatas = this.listOffsets(periodStr, systemId, ruleIdSet, orgType);
        return this.getSubjectRelOffsetVal(parameters, offsetDatas);
    }

    private String getOrgType(String taskId, String periodStr) {
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(taskId, periodStr);
        String orgType = consolidatedTaskVO.getCorporateEntity();
        if (StringUtils.isEmpty((CharSequence)orgType)) {
            TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
            orgType = taskDefine.getDw();
        }
        return orgType;
    }

    private double getSubjectRelOffsetVal(List<IASTNode> parameters, List<GcOffSetVchrItemAdjustEO> offsetDatas) throws SyntaxException {
        Object subjectCode = this.getParamValue(parameters, 1);
        if (CollectionUtils.isEmpty(offsetDatas = offsetDatas.stream().filter(offset -> subjectCode.equals(offset.getSubjectCode())).collect(Collectors.toList()))) {
            return 0.0;
        }
        double offsetSumValue = 0.0;
        Integer subjectOrient = ((GcOffSetVchrItemAdjustEO)offsetDatas.get(0)).getSubjectOrient();
        for (GcOffSetVchrItemAdjustEO gcOffSetVchrItemAdjustEO : offsetDatas) {
            if (gcOffSetVchrItemAdjustEO.getOrient() == OrientEnum.D.getValue()) {
                if (subjectOrient == OrientEnum.D.getValue()) {
                    offsetSumValue = NumberUtils.sum((double)offsetSumValue, (double)gcOffSetVchrItemAdjustEO.getOffSetDebit());
                    continue;
                }
                offsetSumValue = NumberUtils.sub((double)offsetSumValue, (double)gcOffSetVchrItemAdjustEO.getOffSetDebit());
                continue;
            }
            if (subjectOrient == OrientEnum.C.getValue()) {
                offsetSumValue = NumberUtils.sum((double)offsetSumValue, (double)gcOffSetVchrItemAdjustEO.getOffSetCredit());
                continue;
            }
            offsetSumValue = NumberUtils.sub((double)offsetSumValue, (double)gcOffSetVchrItemAdjustEO.getOffSetCredit());
        }
        return offsetSumValue;
    }

    private List<GcOffSetVchrItemAdjustEO> listOffsets(String periodStr, String systemId, Set<String> ruleIdSet, String orgType) {
        String[] columnNamesInDB = new String[]{"systemId", "DATATIME", "RULEID", "MD_GCORGTYPE"};
        List<String> orgTypes = Arrays.asList(GCOrgTypeEnum.NONE.getCode(), orgType);
        Object[] values = new Object[]{systemId, periodStr, ruleIdSet, orgTypes};
        List offsetDatas = ((GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class)).listOffsetRecordsByWhere(columnNamesInDB, values);
        return offsetDatas;
    }

    private Set<String> getRuleId(List<IASTNode> parameters, String systemId) throws SyntaxException {
        String ruleTitle = (String)this.getParamValue(parameters, 0);
        if (StringUtils.isEmpty((CharSequence)ruleTitle)) {
            throw new RuntimeException("ELIM\u51fd\u6570\u4e2d\u7b2c\u4e00\u4e2a\u53c2\u6570'\u79d1\u76ee\u540d\u79f0'\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String[] ruleTitleArr = ruleTitle.split(",");
        HashSet<String> ruleTitleSet = new HashSet<String>(Arrays.asList(ruleTitleArr));
        List<String> ruleTypes = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode(), RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        List rules = ((UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class)).selectAllRuleListByReportSystemAndRuleTypes(systemId, ruleTypes);
        Set<String> ruleIdSet = rules.stream().filter(item -> ruleTitleSet.contains(item.getTitle())).map(item -> item.getId()).collect(Collectors.toSet());
        return ruleIdSet;
    }

    private Object getParamValue(List<IASTNode> parameters, int index) throws SyntaxException {
        if (parameters.size() > index) {
            return parameters.get(index).evaluate(null);
        }
        return null;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u83b7\u53d6\u6307\u5b9a\u89c4\u5219\uff0c\u79d1\u76ee\u7684\u62b5\u9500\u6570\u636e").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("ruleTitle").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u89c4\u5219\u540d\u79f0").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u79d1\u76ee\u4ee3\u7801").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u79d1\u76ee\u4ee3\u7801").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u6570\u503c\u7c7b\u578b").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u6210\u672c\u6cd5\u8c03\u6574\u4e3a\u6743\u76ca\u6cd5\u7684\u5206\u5f55\u4e2d\u79d1\u76ee\u4e3a1501\u7684\u62b5\u9500\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ELIM(\"\u6210\u672c\u6cd5\u8c03\u6574\u4e3a\u6743\u76ca\u6cd5\",\"1501\")')").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append(DataType.toString((int)3));
        return buffer.toString();
    }
}

