/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.builder.ReportBuilder
 *  com.jiuqi.bi.quickreport.builder.define.DimensionGroupDefine
 *  com.jiuqi.bi.quickreport.builder.define.FieldDefine
 *  com.jiuqi.bi.quickreport.builder.define.FieldObject
 *  com.jiuqi.bi.quickreport.builder.define.GridDefine
 *  com.jiuqi.bi.quickreport.builder.define.GroupDefine
 *  com.jiuqi.bi.quickreport.builder.define.MeasureFieldDefine
 *  com.jiuqi.bi.quickreport.builder.define.MeasureGroupDefine
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.IReportExporter
 *  com.jiuqi.bi.quickreport.engine.IReportListener
 *  com.jiuqi.bi.quickreport.engine.ReportEngineFactory
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.NullParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.builder.ReportBuilder;
import com.jiuqi.bi.quickreport.builder.define.DimensionGroupDefine;
import com.jiuqi.bi.quickreport.builder.define.FieldDefine;
import com.jiuqi.bi.quickreport.builder.define.FieldObject;
import com.jiuqi.bi.quickreport.builder.define.GridDefine;
import com.jiuqi.bi.quickreport.builder.define.GroupDefine;
import com.jiuqi.bi.quickreport.builder.define.MeasureFieldDefine;
import com.jiuqi.bi.quickreport.builder.define.MeasureGroupDefine;
import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.IReportExporter;
import com.jiuqi.bi.quickreport.engine.IReportListener;
import com.jiuqi.bi.quickreport.engine.ReportEngineFactory;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.zbquery.common.ZBQueryConst;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.engine.ZBQueryEngine;
import com.jiuqi.nr.zbquery.engine.ZBQueryResult;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModel;
import com.jiuqi.nr.zbquery.engine.dataset.QueryDSModelBuilder;
import com.jiuqi.nr.zbquery.engine.grid.GridDataProcessor;
import com.jiuqi.nr.zbquery.engine.report.ExportReportListener;
import com.jiuqi.nr.zbquery.engine.report.GridDefineBuilder;
import com.jiuqi.nr.zbquery.model.ConditionField;
import com.jiuqi.nr.zbquery.model.ConditionType;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.DefaultValueMode;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.vo.ExportConfigVO;
import com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO;
import com.jiuqi.nr.zbquery.rest.vo.QueryResultVO;
import com.jiuqi.nr.zbquery.service.DrillPierceService;
import com.jiuqi.nr.zbquery.util.ParameterBuilder;
import com.jiuqi.nr.zbquery.util.QueryModelFinder;
import com.jiuqi.nr.zbquery.util.QueryResultVOUtil;
import com.jiuqi.nr.zbquery.util.QuerySystemOptionUtils;
import com.jiuqi.nr.zbquery.util.XlsxSimpleExporter;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import com.jiuqi.nr.zbquery.util.ZBQueryLogHelper;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.NullParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery"})
public class ZBQueryController {
    @Autowired
    private DrillPierceService drillPierceService;

    @PostMapping(value={"/query"})
    public QueryResultVO query(@RequestBody QueryConfigVO queryConfig) throws JQException {
        return this.query(queryConfig, false);
    }

    @PostMapping(value={"/fetch"})
    public QueryResultVO fetch(@RequestBody QueryConfigVO queryConfig) throws JQException {
        return this.query(queryConfig, true);
    }

    private QueryResultVO query(@RequestBody QueryConfigVO queryConfig, boolean fetch) throws JQException {
        try {
            if (StringUtils.isEmpty((String)queryConfig.getCacheId())) {
                queryConfig.setCacheId(Guid.newGuid());
            }
            ZBQueryEngine queryEngine = new ZBQueryEngine(queryConfig.getCacheId(), queryConfig.getQueryModel());
            ZBQueryResult queryResult = null;
            if (fetch) {
                queryResult = queryEngine.fetch(queryConfig.getConditionValues(), queryConfig.getPageInfo());
            } else {
                queryResult = queryEngine.query(queryConfig.getConditionValues(), queryConfig.getPageInfo());
                ZBQueryLogHelper.info("\u67e5\u8be2\u6307\u6807\u6570\u636e", "\u6a21\u677f\u6807\u9898\uff1a" + queryConfig.getTitle());
            }
            QueryResultVOUtil queryResultVOUtil = new QueryResultVOUtil(queryResult, queryConfig);
            queryResultVOUtil.buildQueryResultVO();
            queryResultVOUtil.buildDrillInfo(this.drillPierceService);
            QueryResultVO queryResultVO = queryResultVOUtil.getResultVO();
            queryResultVO.setCacheId(queryConfig.getCacheId());
            return queryResultVO;
        }
        catch (Exception e) {
            int index;
            ZBQueryLogHelper.error("\u67e5\u8be2\u6307\u6807\u6570\u636e", "\u6a21\u677f\u6807\u9898\uff1a" + queryConfig.getTitle());
            String message = e.getMessage();
            if (StringUtils.isNotEmpty((String)message) && message.length() > 138 && (index = message.lastIndexOf(",", 138)) > 84) {
                message = message.substring(0, index);
                message = message + "......";
            }
            if (StringUtils.isNotEmpty((String)message)) {
                if (message.contains("BQL\u5f15\u64ce\u6267\u884c\u9519\u8bef")) {
                    message = ZBQueryI18nUtils.getMessage("zbquery.exception.queryErrorContactAdmin", new Object[0]);
                } else if ("\u5b58\u5728\u591a\u4e2a\u7ef4\u5ea6\uff0c\u65e0\u6cd5\u67e5\u8be2".equals(message)) {
                    message = ZBQueryI18nUtils.getMessage("zbquery.exception.cannotQuery005", new Object[0]);
                }
            }
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_100, message, (Throwable)e);
        }
    }

    @PostMapping(value={"/export"})
    @RequiresPermissions(value={"nr:zbquery:querymodel"})
    public void export(@RequestBody ExportConfigVO exportConfig, HttpServletResponse response) throws JQException {
        try {
            QueryDSModelBuilder dsModelBuilder = new QueryDSModelBuilder(exportConfig.getQueryModel(), exportConfig.getConditionValues());
            dsModelBuilder.build();
            QueryDSModel dsModel = dsModelBuilder.getDSModel();
            GridDefineBuilder gridDefineBuilder = new GridDefineBuilder(exportConfig.getQueryModel(), dsModelBuilder);
            gridDefineBuilder.build();
            GridDefine gridDefine = gridDefineBuilder.getGridDefine();
            ExportReportListener exportListener = new ExportReportListener(exportConfig.getCacheId(), dsModelBuilder, exportConfig.getConditionValues(), null);
            BIDataSet dataSet = exportListener.openDataSet(dsModel.getName());
            boolean simpleExport = this.needSimpleExport(dataSet, gridDefine);
            Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
            String excelFormat = environment.getProperty("jiuqi.nvwa.export.excel.format", "xlsx");
            if (StringUtils.isEmpty((String)excelFormat) || !Arrays.asList(ZBQueryConst.SUPPORT_FORMATS).contains(excelFormat)) {
                excelFormat = "xlsx";
            }
            String attachName = "\u8868\u683c." + excelFormat;
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("\u540e\u7aef\u5b9a\u4e49\u5bfc\u51fa\u6807\u9898", "UTF-8"));
            if (!simpleExport) {
                ReportBuilder reportBuilder = new ReportBuilder((DSModel)dsModel);
                QuickReport quickReport = reportBuilder.build(gridDefine);
                IReportEngine reportEngine = ReportEngineFactory.createEngine(null, (QuickReport)quickReport, (IParameterEnv)new NullParameterEnv(null));
                reportEngine.setLanguage(NpContextHolder.getContext().getLocale().getLanguage());
                reportEngine.setListener((IReportListener)exportListener);
                reportEngine.initParamEnv();
                reportEngine.open(610);
                GridData gridData = reportEngine.getPrimarySheet().getGridData();
                dsModelBuilder.getQueryModelBuilder().getModelFinder().getQueryModel().getOption().setDisplayRowCheck(false);
                new GridDataProcessor(gridData, dsModelBuilder.getQueryModelBuilder().getModelFinder()).process();
                reportEngine.getConfig().put("excel.format", excelFormat);
                try (ServletOutputStream os = response.getOutputStream();){
                    IReportExporter reportExporter = reportEngine.createExporter();
                    reportExporter.export((OutputStream)os, 2);
                    os.flush();
                }
            }
            try (ServletOutputStream os = response.getOutputStream();){
                XlsxSimpleExporter simpleExporter = new XlsxSimpleExporter((OutputStream)os, dsModelBuilder, gridDefine, dataSet, excelFormat);
                simpleExporter.export();
                os.flush();
            }
            ZBQueryLogHelper.info("\u5bfc\u51fa\u6307\u6807\u6570\u636e", "\u6a21\u677f\u6807\u9898\uff1a" + exportConfig.getTitle());
        }
        catch (Exception e) {
            ZBQueryLogHelper.error("\u5bfc\u51fa\u6307\u6807\u6570\u636e", "\u6a21\u677f\u6807\u9898\uff1a" + exportConfig.getTitle());
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(502);
            JSONObject resultdata = new JSONObject();
            resultdata.put("success", false);
            resultdata.put("data", (Object)e.getMessage());
            try {
                response.getWriter().write(resultdata.toString());
            }
            catch (Exception e1) {
                JQException jqe = new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_101, e1.getMessage(), (Throwable)e1);
                jqe.addSuppressed((Throwable)e);
                throw jqe;
            }
        }
    }

    private boolean needSimpleExport(BIDataSet dataSet, GridDefine gridDefine) {
        for (FieldObject field : gridDefine.getCols()) {
            if (field instanceof DimensionGroupDefine) {
                return false;
            }
            if (!(field instanceof MeasureGroupDefine) && !(field instanceof MeasureFieldDefine) || !gridDefine.getSetting().isMeasureVertical()) continue;
            return false;
        }
        int columnCount = 0;
        ArrayList gridFields = new ArrayList(gridDefine.getRows());
        gridFields.addAll(gridDefine.getCols());
        for (FieldObject gridField : gridFields) {
            if (gridField instanceof FieldDefine) {
                ++columnCount;
                continue;
            }
            columnCount += ((GroupDefine)gridField).getAllFieldChildren().size();
        }
        return dataSet.getRecordCount() * columnCount > QuerySystemOptionUtils.getSimpleExportValue() * 10000;
    }

    @PostMapping(value={"/export/excelname"})
    public String getExportExcelName(@RequestBody ExportConfigVO exportConfig) throws Exception {
        Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
        String excelFormat = environment.getProperty("jiuqi.nvwa.export.excel.format", "xlsx");
        if (StringUtils.isEmpty((String)excelFormat) || !Arrays.asList(ZBQueryConst.SUPPORT_FORMATS).contains(excelFormat)) {
            excelFormat = "xlsx";
        }
        if (!"\u6307\u6807\u67e5\u8be2".equals(exportConfig.getTitle())) {
            return exportConfig.getTitle() + "." + excelFormat;
        }
        List<ConditionField> conditions = exportConfig.getQueryModel().getConditions();
        ConditionValues conditionValues = exportConfig.getConditionValues();
        if (conditions == null || conditions.size() == 0 || conditionValues == null) {
            return "\u6307\u6807\u67e5\u8be2." + excelFormat;
        }
        ZBQueryModel queryModel = this.copyXXFormValueToCondition(exportConfig);
        QueryModelFinder finder = new QueryModelFinder(queryModel);
        List<ParameterModel> parameterModels = ParameterBuilder.build(queryModel);
        ParameterEnv parameterEnv = new ParameterEnv("admin", parameterModels);
        String periodTitle = "";
        String unitTitle = "";
        for (ConditionField conditionField : conditions) {
            String name = conditionField.isCalibreCondition() ? conditionField.getCalibreName() : conditionField.getFullName();
            ParameterResultset value = parameterEnv.getValue(name);
            if (value.isEmpty()) continue;
            QueryDimension queryDimension = finder.getQueryDimension(conditionField.getFullName());
            if (conditionField.getObjectType() == QueryObjectType.DIMENSION && queryDimension.getDimensionType() == QueryDimensionType.PERIOD) {
                periodTitle = this.getPeriodDimensionTitle(conditionField, value);
                continue;
            }
            if (conditionField.getObjectType() != QueryObjectType.DIMENSION || queryDimension.getDimensionType() != QueryDimensionType.MASTER) continue;
            unitTitle = this.getUnitDimensionTitle(conditionField, value);
        }
        if (StringUtils.isEmpty((String)periodTitle) && StringUtils.isEmpty((String)unitTitle)) {
            return exportConfig.getTitle() + "." + excelFormat;
        }
        return unitTitle + " " + periodTitle + "." + excelFormat;
    }

    private ZBQueryModel copyXXFormValueToCondition(ExportConfigVO exportConfig) {
        ConditionValues conditionValues = exportConfig.getConditionValues();
        List<ConditionField> conditions = exportConfig.getQueryModel().getConditions();
        for (ConditionField conditionField : conditions) {
            String[] values = conditionValues.getValues(conditionField.getFullName());
            if (conditionField.getObjectType() != QueryObjectType.DIMENSION || values == null || values.length <= 0) continue;
            if (conditionField.getConditionType() == ConditionType.RANGE) {
                conditionField.setDefaultValues(new String[]{values[0]});
                conditionField.setDefaultMaxValue(values[1]);
                continue;
            }
            conditionField.setDefaultValues(values);
        }
        return exportConfig.getQueryModel();
    }

    @GetMapping(value={"/export/exceltype"})
    public String getExportExcelType() {
        Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
        String excelFormat = environment.getProperty("jiuqi.nvwa.export.excel.format", "xlsx");
        if (StringUtils.isEmpty((String)excelFormat) || !Arrays.asList(ZBQueryConst.SUPPORT_FORMATS).contains(excelFormat)) {
            excelFormat = "xlsx";
        }
        return excelFormat;
    }

    @GetMapping(value={"/systemOptionConfig"})
    public String systemOptionConfig() {
        JSONObject config = new JSONObject();
        config.put("ENABLE_FAVORITES", QuerySystemOptionUtils.isEnableFavorites());
        config.put("ENABLE_QUICK_TOOLBAR", QuerySystemOptionUtils.isEnableQuickToolbar());
        return config.toString();
    }

    private String getPeriodDimensionTitle(ConditionField periodCondition, ParameterResultset periodValue) {
        String periodTitle = "";
        if (periodValue.isEmpty()) {
            return "";
        }
        switch (periodCondition.getConditionType()) {
            case SINGLE: {
                periodTitle = periodValue.get(0).getTitle();
                break;
            }
            case RANGE: {
                if (periodValue.size() > 1 && !periodValue.get(0).getTitle().equals(periodValue.get(1).getTitle())) {
                    periodTitle = periodValue.get(0).getTitle() + "-" + periodValue.get(1).getTitle();
                    break;
                }
                periodTitle = periodValue.get(0).getTitle();
                break;
            }
            case MULTIPLE: {
                periodTitle = periodValue.size() > 1 ? periodValue.get(0).getTitle() + "\u7b49" : periodValue.get(0).getTitle();
            }
        }
        return periodTitle;
    }

    private String getUnitDimensionTitle(ConditionField unitCondition, ParameterResultset unitValue) {
        String unitTitle = "";
        switch (unitCondition.getConditionType()) {
            case SINGLE: {
                unitTitle = "\u5355\u4f4d" + unitValue.get(0).getTitle();
                break;
            }
            case MULTIPLE: {
                DefaultValueMode defaultValueMode = unitCondition.getDefaultValueMode();
                if (defaultValueMode == DefaultValueMode.FIRST || unitValue.size() == 1) {
                    unitTitle = "\u5355\u4f4d" + unitValue.get(0).getTitle();
                    break;
                }
                if (defaultValueMode == DefaultValueMode.FIRST_ALLCHILD) {
                    unitTitle = "\u5355\u4f4d" + unitValue.get(0).getTitle() + "\u53ca\u6240\u6709\u4e0b\u7ea7";
                    break;
                }
                if (defaultValueMode == DefaultValueMode.FIRST_CHILD) {
                    unitTitle = "\u5355\u4f4d" + unitValue.get(0).getTitle() + "\u53ca\u76f4\u63a5\u4e0b\u7ea7";
                    break;
                }
                if (defaultValueMode != DefaultValueMode.APPOINT) break;
                unitTitle = "\u5355\u4f4d" + unitValue.get(0).getTitle() + "\u7b49";
            }
        }
        return unitTitle;
    }
}

