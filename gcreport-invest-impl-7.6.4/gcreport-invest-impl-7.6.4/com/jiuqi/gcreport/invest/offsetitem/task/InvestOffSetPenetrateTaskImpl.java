/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.conversion.function.RateValueFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcPenetrateDetailVO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.conversion.function.RateValueFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.invest.offsetitem.task.AbstractOffSetPenetrateTaskImpl;
import com.jiuqi.gcreport.invest.offsetitem.task.FormulaParser;
import com.jiuqi.gcreport.invest.offsetitem.task.FormulaParserResult;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetPenetrateVO;
import com.jiuqi.gcreport.offsetitem.vo.GcPenetrateDetailVO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
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
import org.springframework.util.CollectionUtils;

@Component
public class InvestOffSetPenetrateTaskImpl
extends AbstractOffSetPenetrateTaskImpl {
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private GcBillFormulaEvalService billFormulaEvalService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private RateValueFunction rateValueFunction;
    private Logger logger = LoggerFactory.getLogger(InvestOffSetPenetrateTaskImpl.class);

    public String getTaskType() {
        return "investBill";
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
            DimensionValueSet dset = this.getDimensionValueSet(offsetEO, orgType);
            GcInvestBillGroupDTO investBillGroupDTO = this.getGcInvestBillGroupDTO(offsetEO);
            GcReportExceutorContext context = this.getContext(offsetEO, investBillGroupDTO, schemeKey);
            GcCalcEnvContextImpl env = this.getEnv(offsetEO, orgType, tool, schemeKey);
            String fetchFormula = this.matchFetchFormulaOfSubject(subject2FormulaMap, offsetEO, offSetPenetrateVO, dset, investBillGroupDTO, env);
            offSetPenetrateVO.setFetchFormula(fetchFormula);
            Set<FormulaParserResult> formulaParserResultSet = FormulaParser.parseFormula((ExecutorContext)context, fetchFormula);
            for (FormulaParserResult formulaParserResult : formulaParserResultSet) {
                String nodeFormula = formulaParserResult.getNodeFormula();
                GcPenetrateDetailVO penetrateDetailVO = new GcPenetrateDetailVO();
                penetrateDetailVO.setCalcProject(nodeFormula);
                penetrateDetailVO.setProjectName(formulaParserResult.getNodeTitle(schemeKey, offsetEO.getDefaultPeriod()));
                penetrateDetailVO.setProjectSource(formulaParserResult.getOperateSource(schemeKey, offsetEO.getDefaultPeriod()));
                if ("PHS()".equalsIgnoreCase(formulaParserResult.getNodeFormula())) {
                    String calcValue = StringUtils.isEmpty((String)offSetPenetrateVO.getCreditStr()) ? offSetPenetrateVO.getDebitStr() : offSetPenetrateVO.getCreditStr();
                    penetrateDetailVO.setCalcValue(calcValue);
                } else {
                    int decimal = 2;
                    if (nodeFormula.contains(this.rateValueFunction.name())) {
                        decimal = CommonRateUtils.getRateValueFieldFractionDigits();
                    }
                    AbstractData data = this.billFormulaEvalService.getInvestBillData((GcCalcEnvContext)env, dset, formulaParserResult.getNodeFormula(), investBillGroupDTO);
                    if (data.dataType == 3) {
                        penetrateDetailVO.setCalcValue(NumberUtils.doubleToString((double)GcAbstractData.getDoubleValue((AbstractData)data), (int)decimal));
                    } else {
                        penetrateDetailVO.setCalcValue(GcAbstractData.getStringValue((AbstractData)data));
                    }
                }
                penetrateDetail.add(penetrateDetailVO);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        offSetPenetrateVO.setPenetrateDetail(penetrateDetail);
    }

    private String matchFetchFormulaOfSubject(Map<String, List<String>> subject2FormulaMap, GcOffSetVchrItemAdjustEO offsetEO, GcOffSetPenetrateVO offSetPenetrateVO, DimensionValueSet dset, GcInvestBillGroupDTO investBillGroupDTO, GcCalcEnvContextImpl env) {
        List<String> fetchFormulaList = subject2FormulaMap.get(offsetEO.getSubjectCode());
        String fetchFormula = fetchFormulaList.get(0);
        if (fetchFormulaList.size() > 1) {
            for (String formulaItem : fetchFormulaList) {
                double calcValue = this.billFormulaEvalService.evaluateInvestBillData((GcCalcEnvContext)env, dset, formulaItem, investBillGroupDTO, null);
                double offsetValue = offsetEO.getOrient() == OrientEnum.D.getValue() ? offsetEO.getOffSetDebit().doubleValue() : offsetEO.getOffSetCredit().doubleValue();
                if (!NumberUtils.compareDouble((double)offsetValue, (double)calcValue)) continue;
                fetchFormula = formulaItem;
                break;
            }
        }
        return fetchFormula;
    }

    private GcReportExceutorContext getContext(GcOffSetVchrItemAdjustEO offsetEO, GcInvestBillGroupDTO investBillGroupDTO, String schemeKey) throws ParseException {
        GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
        context.setData(investBillGroupDTO.getMaster());
        context.setItems(investBillGroupDTO.getItems());
        context.registerFunctionProvider((IFunctionProvider)new GcReportBillFunctionProvider());
        context.setDefaultGroupName("GC_INVESTBILL");
        context.setTaskId(offsetEO.getTaskId());
        context.setSchemeId(schemeKey);
        return context;
    }

    private GcInvestBillGroupDTO getGcInvestBillGroupDTO(GcOffSetVchrItemAdjustEO offsetEO) {
        Map<String, Object> investBillData = this.investBillDao.getByUnitAndYear(offsetEO.getUnitId(), offsetEO.getOppUnitId(), ConverterUtils.getAsIntValue((Object)offsetEO.getDefaultPeriod().substring(0, 4)));
        if (CollectionUtils.isEmpty(investBillData)) {
            investBillData = this.investBillDao.getByUnitAndYear(offsetEO.getOppUnitId(), offsetEO.getUnitId(), ConverterUtils.getAsIntValue((Object)offsetEO.getDefaultPeriod().substring(0, 4)));
        }
        DefaultTableEntity master = new DefaultTableEntity();
        master.resetFields(investBillData);
        List<DefaultTableEntity> investbillItemDatas = this.investBillDao.getInvestmentItemsByMastId((String)investBillData.get("ID"));
        return new GcInvestBillGroupDTO(master, investbillItemDatas, true);
    }
}

