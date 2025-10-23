/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.summary.executor.query.export;

import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.consts.SummaryConsts;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.executor.query.QueryPageConfig;
import com.jiuqi.nr.summary.executor.query.SummaryQueryExecutor;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.vo.ExportConfig;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class SummaryExportExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SummaryExportExecutor.class);
    private ExportConfig exportConfig;

    public SummaryExportExecutor(ExportConfig exportConfig) {
        this.exportConfig = exportConfig;
    }

    public void export(HttpServletResponse response) throws SummaryCommonException {
        ExportPrepareInfo prepareInfo = this.prepare();
        this.writeOS(response, prepareInfo);
    }

    private ExportPrepareInfo prepare() throws SummaryCommonException {
        ExportPrepareInfo prepareInfo = new ExportPrepareInfo();
        ArrayList<Integer> sizeList = new ArrayList<Integer>();
        IRuntimeSummarySolutionService runtimeSoluService = (IRuntimeSummarySolutionService)SpringBeanUtils.getBean(IRuntimeSummarySolutionService.class);
        SummaryParamService summaryParamService = (SummaryParamService)SpringBeanUtils.getBean(SummaryParamService.class);
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        prepareInfo.reportModels = new ArrayList<SummaryReportModel>();
        this.exportConfig.getReportKeys().forEach(key -> {
            SummaryReportModel reportModel = runtimeSoluService.getSummaryReportModel((String)key);
            prepareInfo.reportModels.add(reportModel);
        });
        prepareInfo.solutionModel = runtimeSoluService.getSummarySolutionModel(this.exportConfig.getSolutionKey());
        prepareInfo.mainTask = summaryParamService.getTaskDefine(prepareInfo.solutionModel.getMainTask());
        sizeList.add(prepareInfo.reportModels.size());
        prepareInfo.dimSets = new ArrayList<DimensionValueSet>();
        DimensionValueSet dimValueSet = new DimensionValueSet();
        String mainDimKey = prepareInfo.mainTask.getDw();
        String mainKey = entityMetaService.getEntityCode(mainDimKey);
        Map<String, String[]> conditions = this.exportConfig.getConditions();
        String[] masterDimValues = conditions.get(mainKey);
        if (OrgAdapterUtil.isOrg((String)mainDimKey)) {
            mainKey = "MD_ORG";
        }
        if (masterDimValues == null) {
            masterDimValues = conditions.get(mainKey);
        }
        sizeList.add(masterDimValues.length);
        dimValueSet.setValue(mainKey, (Object)masterDimValues[0]);
        String timeDimKey = prepareInfo.mainTask.getDateTime();
        String timeCode = BqlTimeDimUtils.getBqlTimeDimTable((String)timeDimKey);
        String[] timeValues = conditions.get(timeCode);
        Object timeValue = this.getDataTimeValue(timeValues, prepareInfo.mainTask.getPeriodType());
        if (ObjectUtils.isEmpty(timeValue)) {
            timeValues = conditions.get(timeDimKey);
            timeValue = this.getDataTimeValue(timeValues, prepareInfo.mainTask.getPeriodType());
        }
        sizeList.add(timeValues.length);
        dimValueSet.setValue("DATATIME", timeValue);
        String dims = prepareInfo.mainTask.getDims();
        if (StringUtils.hasLength(dims)) {
            String[] dimArr;
            for (String dimKey : dimArr = dims.split(";")) {
                String dimCode = entityMetaService.getEntityCode(dimKey);
                String[] values = conditions.get(dimCode);
                if (values == null) continue;
                Object value = values.length == 1 ? values[0] : Arrays.asList(values);
                dimValueSet.setValue(dimCode, value);
            }
        }
        prepareInfo.dimSets.add(dimValueSet);
        long oneCount = sizeList.stream().filter(size -> 1 == size).count();
        if (oneCount > 1L) {
            Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
            String excelFormat = environment.getProperty("jiuqi.nvwa.export.excel.format", "xlsx");
            if (StringUtils.hasLength(excelFormat) || !Arrays.asList(SummaryConsts.SUPPORT_FORMATS).contains(excelFormat)) {
                excelFormat = "xlsx";
            }
            prepareInfo.fileType = excelFormat.equals("xlsx") ? ExportType.XLSX : (excelFormat.equals("xls") ? ExportType.XLS : ExportType.ET);
        } else {
            prepareInfo.fileType = ExportType.ZIP;
        }
        return prepareInfo;
    }

    private Object getDataTimeValue(String[] values, PeriodType periodType) {
        if (values != null) {
            List list = Arrays.stream(values).map(value -> TimeDimUtils.getDataTimeByTimeDim((String)value, (String)String.valueOf((char)Character.toUpperCase(periodType.code())))).collect(Collectors.toList());
            if (list.size() == 1) {
                return list.get(0);
            }
            return list;
        }
        return null;
    }

    private void writeOS(HttpServletResponse response, ExportPrepareInfo prepareInfo) {
        if (prepareInfo.fileType == ExportType.XLSX) {
            this.writeToXlsx(response, prepareInfo);
        } else if (prepareInfo.fileType == ExportType.XLS) {
            this.writeToXls(response, prepareInfo);
        } else if (prepareInfo.fileType == ExportType.ET) {
            this.writeToEt(response, prepareInfo);
        } else {
            this.writeToZip(response, prepareInfo);
        }
    }

    private void writeToXlsx(HttpServletResponse response, ExportPrepareInfo prepareInfo) {
        try {
            SummaryReportModel reportModel = prepareInfo.reportModels.get(0);
            IReportEngine reportEngine = new SummaryQueryExecutor().getReportEngine(prepareInfo.solutionModel, reportModel, prepareInfo.dimSets.get(0), new QueryPageConfig());
            String attachName = reportModel.getTitle() + ".xlsx";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attachName, "UTF-8"));
            reportEngine.getConfig().put("excel.format", "xlsx");
            try (ServletOutputStream os = response.getOutputStream();){
                reportEngine.exportPrimarySheet((OutputStream)os);
                os.flush();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToXls(HttpServletResponse response, ExportPrepareInfo prepareInfo) {
    }

    private void writeToEt(HttpServletResponse response, ExportPrepareInfo prepareInfo) {
    }

    private void writeToZip(HttpServletResponse response, ExportPrepareInfo prepareInfo) {
    }

    static enum ExportType {
        XLS,
        XLSX,
        ET,
        ZIP;

    }

    static class ExportPrepareInfo {
        ExportType fileType;
        List<SummaryReportModel> reportModels;
        SummarySolutionModel solutionModel;
        TaskDefine mainTask;
        List<DimensionValueSet> dimSets;

        ExportPrepareInfo() {
        }
    }
}

