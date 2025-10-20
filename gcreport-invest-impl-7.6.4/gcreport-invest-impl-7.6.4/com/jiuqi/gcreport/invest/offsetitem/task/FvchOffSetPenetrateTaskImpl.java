/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcPenetrateDetailVO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.offsetitem.task.AbstractOffSetPenetrateTaskImpl;
import com.jiuqi.gcreport.invest.offsetitem.task.FormulaParser;
import com.jiuqi.gcreport.invest.offsetitem.task.FormulaParserResult;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO;
import com.jiuqi.gcreport.offsetitem.vo.GcPenetrateDetailVO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FvchOffSetPenetrateTaskImpl
extends AbstractOffSetPenetrateTaskImpl {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    private Logger logger = LoggerFactory.getLogger(FvchOffSetPenetrateTaskImpl.class);

    public String getTaskType() {
        return "fvchBill";
    }

    @Override
    protected void setPenetrateDetail(Map<String, List<String>> subject2FormulaMap, GcOffSetVchrItemAdjustEO offsetEO, GcOffSetPenetrateVO offSetPenetrateVO, GcOrgCenterService tool, String orgType) {
        String subjectCode = offSetPenetrateVO.getSubjectCode();
        if (!subject2FormulaMap.containsKey(subjectCode)) {
            return;
        }
        ArrayList<GcPenetrateDetailVO> penetrateDetail = new ArrayList<GcPenetrateDetailVO>();
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(offsetEO.getDefaultPeriod(), offsetEO.getTaskId());
            String schemeKey = schemePeriodLinkDefine.getSchemeKey();
            DefaultTableEntity fvchItemData = this.getFvchItemData(offsetEO);
            DimensionValueSet dset = this.getDimensionValueSet(offsetEO, orgType);
            GcReportExceutorContext context = this.getContext(offsetEO, schemeKey);
            GcCalcEnvContextImpl env = this.getEnv(offsetEO, orgType, tool, schemeKey);
            String fetchFormula = this.matchFetchFormulaOfSubject(subject2FormulaMap, offsetEO, dset, fvchItemData, env);
            offSetPenetrateVO.setFetchFormula(fetchFormula);
            Set<FormulaParserResult> formulaParserResultSet = FormulaParser.parseFormula((ExecutorContext)context, fetchFormula);
            for (FormulaParserResult formulaParserResult : formulaParserResultSet) {
                Object calcValue;
                GcPenetrateDetailVO penetrateDetailVO = new GcPenetrateDetailVO();
                penetrateDetailVO.setCalcProject(formulaParserResult.getNodeFormula());
                penetrateDetailVO.setProjectName(formulaParserResult.getNodeTitle(schemeKey, offsetEO.getDefaultPeriod()));
                penetrateDetailVO.setProjectSource(formulaParserResult.getOperateSource(schemeKey, offsetEO.getDefaultPeriod()));
                if ("PHS()".equalsIgnoreCase(formulaParserResult.getNodeFormula())) {
                    calcValue = StringUtils.isEmpty((String)offSetPenetrateVO.getCreditStr()) ? offSetPenetrateVO.getDebitStr() : offSetPenetrateVO.getCreditStr();
                    penetrateDetailVO.setCalcValue((String)calcValue);
                } else {
                    calcValue = this.billFormulaEvalService.evaluateFvchBillData((GcCalcEnvContext)env, dset, formulaParserResult.getNodeFormula(), fvchItemData);
                    penetrateDetailVO.setCalcValue(NumberUtils.doubleToString((Double)calcValue));
                }
                penetrateDetail.add(penetrateDetailVO);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        offSetPenetrateVO.setPenetrateDetail(penetrateDetail);
    }

    private GcReportExceutorContext getContext(GcOffSetVchrItemAdjustEO offsetEO, String schemeKey) throws ParseException {
        GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
        context.registerFunctionProvider((IFunctionProvider)new GcReportBillFunctionProvider());
        context.setDefaultGroupName("GC_FVCHBILL");
        context.setTaskId(offsetEO.getTaskId());
        context.setSchemeId(schemeKey);
        return context;
    }

    private DefaultTableEntity getFvchItemData(GcOffSetVchrItemAdjustEO offsetEO) {
        String srcOffsetGroupId = offsetEO.getSrcOffsetGroupId();
        DefaultTableEntity fvchItemBill = InvestBillTool.getEntityById((String)srcOffsetGroupId, (String)"GC_FVCH_FIXEDITEM");
        if (fvchItemBill == null) {
            fvchItemBill = InvestBillTool.getEntityById((String)srcOffsetGroupId, (String)"GC_FVCH_OTHERITEM");
            fvchItemBill.getFields().put("fvchItemType", "GC_FVCH_OTHERITEM");
        } else {
            fvchItemBill.getFields().put("fvchItemType", "GC_FVCH_FIXEDITEM");
        }
        return fvchItemBill;
    }

    private String matchFetchFormulaOfSubject(Map<String, List<String>> subject2FormulaMap, GcOffSetVchrItemAdjustEO offsetEO, DimensionValueSet dset, DefaultTableEntity fvchItemData, GcCalcEnvContextImpl env) {
        List<String> fetchFormulaList = subject2FormulaMap.get(offsetEO.getSubjectCode());
        String fetchFormula = fetchFormulaList.get(0);
        if (fetchFormulaList.size() > 1) {
            for (String formulaItem : fetchFormulaList) {
                double calcValue = this.billFormulaEvalService.evaluateFvchBillData((GcCalcEnvContext)env, dset, formulaItem, fvchItemData);
                double offsetValue = offsetEO.getOrient() == OrientEnum.D.getValue() ? offsetEO.getOffSetDebit().doubleValue() : offsetEO.getOffSetCredit().doubleValue();
                if (!NumberUtils.compareDouble((double)offsetValue, (double)calcValue)) continue;
                fetchFormula = formulaItem;
                break;
            }
        }
        return fetchFormula;
    }
}

