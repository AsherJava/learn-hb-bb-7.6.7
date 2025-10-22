/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.compatible;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.analysisreport.compatible.ReportGeneratorParam;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.helper.GenerateContext;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.support.IExprParser;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Deprecated
@Component
public class CompatibleParseHelper {
    private Logger logger = LoggerFactory.getLogger(CompatibleParseHelper.class);
    public static final String VIEW_KEY = "viewKey";
    public static final String CALENDAR_CODE = "calendarCode";
    public static final String KEY = "key";
    public static final String CODE = "code";
    public static final String TITLE = "title";
    public static final String REENTRANTLOCK = "reentrantLock";
    public static final String REPORTDEFINE = "reportDefine";
    public static final String CONTEXT = "context";
    public static final String REPORTGENERATORPARAM = "reportGeneratorParam";
    public static final String VARIABLE_VALUE_MAP = "variableValueMap";
    public static final String GENERATECONTEXT = "generateContext";
    @Autowired
    private IAnalysisReportEntityService analysisReportEntityService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public HashMap<String, Object> buildCommonInfo(ReportVariableParseVO reportVariableParseVO, AnalysisReportDefine reportDefine) {
        HashMap<String, Object> extInfo = new HashMap<String, Object>();
        try {
            ReportGeneratorParam reportGeneratorParam = this.buildReportGeneratorParam(reportVariableParseVO);
            GenerateContext generateContext = this.buildGenerateContext(reportGeneratorParam);
            generateContext.setPrintData(reportDefine.getPrintData());
            extInfo.put(GENERATECONTEXT, generateContext);
            extInfo.put(VARIABLE_VALUE_MAP, generateContext.getVariableValueMap());
            extInfo.put(REPORTGENERATORPARAM, reportGeneratorParam);
            extInfo.put(REPORTDEFINE, reportDefine);
            String genDataType = reportVariableParseVO.getExt().get("GEN_DATA_TYPE").toString();
            extInfo.put("GEN_DATA_TYPE", genDataType);
        }
        catch (Exception e) {
            extInfo = new HashMap();
            this.logger.error(e.getMessage(), e);
        }
        return extInfo;
    }

    public void parse(Elements elements, IExprParser exprParser, HashMap<String, Object> extInfo) {
        try {
            ReentrantLock reentrantLock = LockCacheUtil.getCacheLock(NpContextHolder.getContext());
            for (Element element : elements) {
                String varKey = element.attr("var-expr");
                if (StringUtil.isNullOrEmpty((String)varKey)) continue;
                HashMap<String, Object> commonInfo = new HashMap<String, Object>();
                commonInfo.putAll(extInfo);
                commonInfo.put(REENTRANTLOCK, reentrantLock);
                commonInfo.put("var-expr", varKey);
                commonInfo.put(CONTEXT, NpContextHolder.getContext());
                exprParser.parse(element, commonInfo);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public ReportGeneratorParam buildReportGeneratorParam(ReportVariableParseVO reportVariableParseVO) {
        ReportGeneratorParam reportGeneratorParam = new ReportGeneratorParam();
        reportGeneratorParam.setCalendarCode(reportVariableParseVO.getReportBaseVO().getPeriodStr());
        ArrayList<ReportGeneratorParam.UnitDim> unitDims = new ArrayList<ReportGeneratorParam.UnitDim>();
        for (ReportBaseVO.UnitDim chooseUnit : reportVariableParseVO.getReportBaseVO().getChooseUnits()) {
            ReportGeneratorParam.UnitDim unitDim = new ReportGeneratorParam.UnitDim();
            unitDim.setTitle(chooseUnit.getTitle());
            unitDim.setCode(chooseUnit.getCode());
            unitDim.setViewKey(chooseUnit.getViewKey());
            unitDim.setKey(chooseUnit.getCode());
            unitDims.add(unitDim);
        }
        reportGeneratorParam.setContents(reportVariableParseVO.getContent());
        reportGeneratorParam.setChooseUnits(unitDims);
        reportGeneratorParam.setKey(reportVariableParseVO.getReportBaseVO().getKey());
        ReportGeneratorParam.PeriodDim periodDim = new ReportGeneratorParam.PeriodDim();
        ReportBaseVO.PeriodDim period = reportVariableParseVO.getReportBaseVO().getPeriod();
        periodDim.setViewKey(period.getViewKey());
        periodDim.setCalendarCode(period.getCalendarCode());
        ArrayList<ReportGeneratorParam.PeriodDim> periodDims = new ArrayList<ReportGeneratorParam.PeriodDim>();
        periodDims.add(periodDim);
        reportGeneratorParam.setPeriods(periodDims);
        reportGeneratorParam.setContents(reportVariableParseVO.getContent());
        return reportGeneratorParam;
    }

    private GenerateContext buildGenerateContext(ReportGeneratorParam reportGeneratorParam) {
        GenerateContext generateContext = new GenerateContext();
        generateContext.setFullContent(reportGeneratorParam.getContents());
        generateContext.setPeriodCode(reportGeneratorParam.getCalendarCode());
        if (!CollectionUtils.isEmpty(reportGeneratorParam.getPeriods())) {
            for (ReportGeneratorParam.PeriodDim periodDim : reportGeneratorParam.getPeriods()) {
                String viewKey = periodDim.getViewKey();
                String calendarCode = periodDim.getCalendarCode();
                ReportBaseVO.PeriodDim reportBasePeriod = new ReportBaseVO.PeriodDim();
                reportBasePeriod.setCalendarCode(calendarCode);
                reportBasePeriod.setViewKey(viewKey);
                generateContext.setPeriodDim(reportBasePeriod);
                HashMap<String, String> period = new HashMap<String, String>();
                period.put(VIEW_KEY, viewKey);
                period.put(CALENDAR_CODE, calendarCode);
                generateContext.getPeriodMapList().add(period);
            }
        }
        for (ReportGeneratorParam.UnitDim unitDim : reportGeneratorParam.getChooseUnits()) {
            if (unitDim.getViewKey() == null) continue;
            String entityViewKey = unitDim.getViewKey();
            String title = null;
            if (!StringUtils.isEmpty((CharSequence)unitDim.getTitle())) {
                title = unitDim.getTitle();
                HashMap<String, String> unitMap = new HashMap<String, String>();
                unitMap.put(KEY, unitDim.getKey());
                unitMap.put(CODE, unitDim.getCode());
                unitMap.put(TITLE, title);
                unitMap.put(VIEW_KEY, entityViewKey);
                generateContext.getUnitMapList().add(unitMap);
                continue;
            }
            String entityKey = unitDim.getKey();
            String code = unitDim.getCode();
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            Map<String, String> unitMap = this.analysisReportEntityService.queryEntityData(entityViewKey, entityKey, code, executorContext);
            generateContext.getUnitMapList().add(unitMap);
        }
        return generateContext;
    }
}

