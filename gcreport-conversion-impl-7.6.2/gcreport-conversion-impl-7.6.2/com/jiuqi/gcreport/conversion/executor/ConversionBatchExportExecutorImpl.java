/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.consts.ExcelConsts$ContentCellStyle
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.gcreport.conversion.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.consts.ExcelConsts;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.conversion.action.param.GcConversionActionParam;
import com.jiuqi.gcreport.conversion.common.GcConversionWorkPaperEnv;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.service.GCConversionWorkPaperService;
import com.jiuqi.gcreport.conversion.service.impl.GCConversionWorkPaperServiceImpl;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ConversionBatchExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private GCConversionWorkPaperService gcConversionWorkPaperService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    private static final Logger LOGGER = LoggerFactory.getLogger(GCConversionWorkPaperServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private CellStyle titleStringStyle = null;
    private CellStyle titleHeadCellStyle;

    public String getName() {
        return "ConversionBatchExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        this.titleHeadCellStyle = this.buildDefaultHeadCellStyle(workbook);
        JSONObject actionParams = new JSONObject(context.getParam());
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        if (actionParams.getBoolean("isConversionWorkPaperPage")) {
            GcConversionWorkPaperEnv gcConversionWorkPaperEnv = this.gcConversionWorkPaperService.getGcConversionWorkPaperEnv(actionParams.getJSONObject("data").toString(), context);
            try {
                JSONObject conversionJson = this.gcConversionWorkPaperService.getConversionWorkPaperDatas(gcConversionWorkPaperEnv, null);
                ExportExcelSheet exportExcelSheet = this.getExportExcelSheet(0, workbook, context, conversionJson, actionParams);
                exportExcelSheets.add(exportExcelSheet);
            }
            catch (Exception e) {
                ExportExcelSheet exportExcelSheet = this.getExportExcelSheetException(context, e.getMessage());
                exportExcelSheets.add(exportExcelSheet);
            }
        } else {
            ExportExcelSheet headerExportExcelSheet;
            ExportExcelSheet exportExcelSheet;
            Map<String, List<GcConversionWorkPaperEnv>> gcConversionWorkPaperEnvList;
            try {
                gcConversionWorkPaperEnvList = this.gcConversionWorkPaperService.getGcBatchConversionWorkPaperEnv(actionParams.getJSONObject("data").toString(), context);
            }
            catch (Exception e) {
                LOGGER.error("\u5916\u5e01\u6298\u7b97\u7a3d\u6838\u5bfc\u51fa\u9519\u8bef", e);
                ExportExcelSheet exportExcelSheet2 = new ExportExcelSheet(Integer.valueOf(0), "\u5916\u5e01\u7a3d\u6838\u62a5\u544a", Integer.valueOf(1));
                Object[] titles = new Object[]{e.getMessage()};
                exportExcelSheet2.getRowDatas().add(titles);
                exportExcelSheets.add(exportExcelSheet2);
                return exportExcelSheets;
            }
            context.getProgressData().setProgressValueAndRefresh(0.03);
            int orgCount = 0;
            JSONArray orgConversionJsonArrays = new JSONArray();
            ArrayList<ExportExcelSheet> exportExcelSheetList = new ArrayList<ExportExcelSheet>();
            int zbCount = 0;
            HashMap<String, List<ConversionSystemItemEO>> tableKeyAndItemMap = new HashMap<String, List<ConversionSystemItemEO>>();
            for (Map.Entry<String, List<GcConversionWorkPaperEnv>> entry : gcConversionWorkPaperEnvList.entrySet()) {
                ++orgCount;
                ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
                JSONObject orgConversionJsonObjects = new JSONObject();
                JSONArray orgConversionFromOrgDatas = new JSONArray();
                int differenceCount = 0;
                int floatCountOfOrg = 0;
                exportExcelSheet = this.getBatchExportExcelSheet(orgCount, workbook, context, entry.getKey());
                String errorMessage = "";
                for (GcConversionWorkPaperEnv gcConversionWorkPaperEnv : entry.getValue()) {
                    JSONObject orgConversionJsonObject = new JSONObject();
                    try {
                        FormDefine formDefine = this.runTimeViewController.queryFormById(gcConversionWorkPaperEnv.getFormId());
                        orgConversionJsonObject.put("formTitle", (Object)formDefine.getTitle());
                        JSONObject conversionJson = this.gcConversionWorkPaperService.getConversionWorkPaperDatas(gcConversionWorkPaperEnv, tableKeyAndItemMap);
                        rowDatas.addAll(this.getBatchExportExcelRowDatas(context, conversionJson, entry.getKey(), actionParams));
                        orgConversionJsonObject.put("differenceCount", conversionJson.getInt("differenceCount"));
                        orgConversionFromOrgDatas.put((Object)orgConversionJsonObject);
                        differenceCount += conversionJson.getInt("differenceCount");
                        zbCount += conversionJson.getJSONArray("conversionDatas").length();
                    }
                    catch (Exception e) {
                        LOGGER.error("\u5916\u5e01\u6298\u7b97\u7a3d\u6838\u5bfc\u51fa\u9519\u8bef:" + e.getMessage(), e);
                        errorMessage = "\u5916\u5e01\u6298\u7b97\u7a3d\u6838\u5bfc\u51fa\u9519\u8bef:" + e.getMessage();
                        ++floatCountOfOrg;
                        orgConversionJsonObject.put("differenceCount", 0);
                        orgConversionFromOrgDatas.put((Object)orgConversionJsonObject);
                    }
                }
                if (floatCountOfOrg == entry.getValue().size()) {
                    exportExcelSheet = this.getBatchExportExcelSheetException(orgCount, context, entry.getKey(), errorMessage);
                }
                orgConversionJsonObjects.put("orgId", (Object)entry.getKey());
                orgConversionJsonObjects.put("differenceCount", differenceCount);
                orgConversionJsonObjects.put("orgConversionFromOrgDatas", (Object)orgConversionFromOrgDatas);
                orgConversionJsonArrays.put((Object)orgConversionJsonObjects);
                exportExcelSheet.getRowDatas().addAll(rowDatas);
                exportExcelSheetList.add(exportExcelSheet);
            }
            try {
                headerExportExcelSheet = this.getBatchHeaderExportExcelSheet(workbook, context, orgConversionJsonArrays, zbCount);
            }
            catch (Exception e) {
                LOGGER.error("\u5916\u5e01\u6298\u7b97\u7a3d\u6838\u5bfc\u51fa\u9519\u8bef\u3002", e);
                exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u5916\u5e01\u7a3d\u6838\u62a5\u544a", Integer.valueOf(1));
                Object[] titles = new Object[]{"\u5916\u5e01\u6298\u7b97\u7a3d\u6838\u5bfc\u51fa\u9519\u8bef\u3002"};
                exportExcelSheet.getRowDatas().add(titles);
                exportExcelSheets.add(exportExcelSheet);
                return exportExcelSheets;
            }
            exportExcelSheets.add(headerExportExcelSheet);
            exportExcelSheets.addAll(exportExcelSheetList);
        }
        return exportExcelSheets;
    }

    private double getStepProgress(Map<String, List<GcConversionWorkPaperEnv>> gcConversionWorkPaperEnvList) {
        int count = 0;
        Iterator<Map.Entry<String, List<GcConversionWorkPaperEnv>>> iterator = gcConversionWorkPaperEnvList.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, List<GcConversionWorkPaperEnv>> entry = iterator.next();
            count = gcConversionWorkPaperEnvList.entrySet().size() * entry.getValue().size();
        }
        double stepProgress = count > 0 ? new BigDecimal(0.95f / (float)count).setScale(5, 1).doubleValue() : 0.95;
        return stepProgress;
    }

    private CellStyle getRightCellStyle(Workbook workbook) {
        CellStyle headCell = this.buildDefaultHeadCellStyle(workbook);
        headCell.setAlignment(HorizontalAlignment.RIGHT);
        return headCell;
    }

    private CellStyle getLeftCellStyle(Workbook workbook) {
        CellStyle headCell = this.buildDefaultHeadCellStyle(workbook);
        headCell.setAlignment(HorizontalAlignment.LEFT);
        return headCell;
    }

    private CellStyle getCenterCellStyle(Workbook workbook) {
        CellStyle headCell = this.buildDefaultHeadCellStyle(workbook);
        headCell.setAlignment(HorizontalAlignment.CENTER);
        return headCell;
    }

    private ExportExcelSheet getBatchHeaderExportExcelSheet(Workbook workbook, ExportContext context, JSONArray conversionJson, int zbCount) throws Exception {
        String periodData;
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        DimensionValue adjustPeriodValue = (DimensionValue)dimensionSet.get("ADJUST");
        NpContext npContext = NpContextHolder.getContext();
        ContextUser user = npContext.getUser();
        Date nowDate = new Date();
        DateFormat format = DateFormat.getDateTimeInstance();
        String dateStr = format.format(nowDate);
        ArrayList<Object> rowDatas = new ArrayList<Object>();
        Object[] titles = new Object[6];
        for (int i = 0; i < titles.length; ++i) {
            titles[i] = "\u5916\u5e01\u7a3d\u6838\u62a5\u544a";
        }
        String string = periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        if (periodData != null && adjustPeriodValue != null && !adjustPeriodValue.getValue().equals("0")) {
            periodData = periodData + "\u8c03\u6574\u671f" + adjustPeriodValue.getValue();
        }
        Object[] titles2 = new Object[]{"\u4efb\u52a1\uff1a", this.iRunTimeViewController.queryTaskDefine(envContext.getTaskKey()).getTitle(), "\u62a5\u8868\u65b9\u6848\uff1a", this.iRunTimeViewController.getFormScheme(envContext.getFormSchemeKey()).getTitle(), "\u65f6\u671f\uff1a", periodData};
        Object[] titles3 = new Object[]{"\u7a3d\u6838\u4eba\uff1a", user.getName(), "\u7a3d\u6838\u65e5\u671f\uff1a", dateStr, dateStr, dateStr};
        Object[] titles5 = new Object[]{"\u5e8f\u53f7", "\u5355\u4f4d", "\u5408\u8ba1\u6570\u91cf", "\u62a5\u8868", "\u5dee\u5f02\u6307\u6807\u6570\u91cf", ""};
        ArrayList<Object[]> titleList = new ArrayList<Object[]>();
        int differenceCount = 0;
        int fromCount = 0;
        for (int count = 0; count < conversionJson.length(); ++count) {
            JSONObject conversionObject = conversionJson.getJSONObject(count);
            GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), conversionObject.optString("orgId"));
            JSONArray conversionArray = conversionObject.getJSONArray("orgConversionFromOrgDatas");
            fromCount = conversionArray.length();
            for (int i = 0; i < conversionArray.length(); ++i) {
                JSONObject conversionData = conversionArray.getJSONObject(i);
                Object[] titleFor = new Object[]{count + 1, gcOrgCacheVO.getTitle() + "|" + gcOrgCacheVO.getCode(), NumberUtils.doubleToString((double)Integer.parseInt(conversionObject.get("differenceCount").toString()), (int)0), conversionData.optString("formTitle"), NumberUtils.doubleToString((double)Integer.parseInt(conversionData.get("differenceCount").toString()), (int)0), ""};
                titleList.add(titleFor);
            }
            differenceCount += conversionObject.getInt("differenceCount");
        }
        Object[] titles4 = new Object[6];
        titles4[0] = "\u7a3d\u6838\u7ed3\u679c\uff1a";
        for (int i = 1; i < titles4.length; ++i) {
            titles4[i] = zbCount == 0 ? "\u672c\u6b21\u5171\u7a3d\u6838" + conversionJson.length() + "\u5355\u4f4d\uff0c" + fromCount + "\u5f20\u8868\uff0c" + zbCount + "\u4e2a\u6307\u6807\uff0c\u7a3d\u6838\u901a\u8fc7" + (zbCount - differenceCount) + "\u4e2a\u6307\u6807\uff0c\u7a3d\u6838\u901a\u8fc7\u7387100%" : "\u672c\u6b21\u5171\u7a3d\u6838" + conversionJson.length() + "\u5355\u4f4d\uff0c" + fromCount + "\u5f20\u8868\uff0c" + zbCount + "\u4e2a\u6307\u6807\uff0c\u7a3d\u6838\u901a\u8fc7" + (zbCount - differenceCount) + "\u4e2a\u6307\u6807\uff0c\u7a3d\u6838\u901a\u8fc7\u7387" + new BigDecimal(zbCount - differenceCount).divide(new BigDecimal(zbCount), 4, 1).multiply(new BigDecimal(100)).setScale(2, 1) + "%";
        }
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(0), "\u7a3d\u6838\u62a5\u544a", Integer.valueOf(5));
        for (int count = 0; count < 6; ++count) {
            mastSheet.getHeadCellStyleCache().put(count, this.getLeftCellStyle(workbook));
        }
        rowDatas.add(titles);
        rowDatas.add(titles2);
        rowDatas.add(titles3);
        rowDatas.add(titles4);
        rowDatas.add(titles5);
        rowDatas.addAll(titleList);
        mastSheet.getRowDatas().addAll(rowDatas);
        return mastSheet;
    }

    private List<Object[]> getBatchExportExcelRowDatas(ExportContext context, JSONObject conversionJson, String orgId, JSONObject actionParams) {
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        JSONArray jsonArray = conversionJson.getJSONArray("conversionDatas");
        String periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), orgId);
        JSONObject jsonObject = this.gcConversionWorkPaperService.getExcelTableData(gcOrgCacheVO);
        JSONObject jsonObjectBefore = jsonObject.getJSONObject("currencyBeforeTable");
        JSONArray jsonObjectAfter = jsonObject.getJSONArray("currencyAfterTable");
        if (!jsonObjectAfter.isEmpty()) {
            for (int j = 0; j < jsonArray.length(); ++j) {
                Object[] titleData = new Object[3 + jsonObjectAfter.length() * 3];
                JSONObject jsonObjectAfterData = jsonArray.getJSONObject(j);
                titleData[0] = jsonObjectAfterData.optString("zbName");
                titleData[1] = jsonObjectAfterData.optString("conversion" + jsonObjectBefore.get("currencyCode"));
                titleData[2] = jsonObjectAfterData.optString("rateType");
                if ("differenceZb".equals(actionParams.getJSONObject("data").getString("exportType")) && !Boolean.valueOf(jsonObjectAfterData.optString("isDifference")).booleanValue()) continue;
                for (int i = 1; i <= jsonObjectAfter.length(); ++i) {
                    titleData[3 * i] = jsonObjectAfterData.get("rate" + jsonObjectAfter.getJSONObject(i - 1).get("currencyCode"));
                    titleData[3 * i + 1] = jsonObjectAfterData.get("conversionAfter" + jsonObjectAfter.getJSONObject(i - 1).get("currencyCode"));
                    titleData[3 * i + 2] = jsonObjectAfterData.get("conversion" + jsonObjectAfter.getJSONObject(i - 1).get("currencyCode"));
                }
                rowDatas.add(titleData);
            }
        }
        return rowDatas;
    }

    private ExportExcelSheet getBatchExportExcelSheetException(int sheetNo, ExportContext context, String orgId, String message) {
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        String periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), orgId);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        String sheetTitle = gcOrgCacheVO.getTitle() + "|" + gcOrgCacheVO.getCode();
        sheetTitle = sheetTitle.replace("[", "(");
        sheetTitle = sheetTitle.replace("]", ")");
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), sheetTitle, Integer.valueOf(1));
        Object[] titleData = new Object[]{message};
        rowDatas.add(titleData);
        mastSheet.getRowDatas().addAll(rowDatas);
        return mastSheet;
    }

    private ExportExcelSheet getBatchExportExcelSheet(int sheetNo, Workbook workbook, ExportContext context, String orgId) {
        ExportExcelSheet mastSheet;
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        String periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), orgId);
        JSONObject jsonObject = this.gcConversionWorkPaperService.getExcelTableData(gcOrgCacheVO);
        JSONObject jsonObjectBefore = jsonObject.getJSONObject("currencyBeforeTable");
        JSONArray jsonObjectAfter = jsonObject.getJSONArray("currencyAfterTable");
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        String sheetTitle = gcOrgCacheVO.getTitle() + "|" + gcOrgCacheVO.getCode();
        sheetTitle = sheetTitle.replace("[", "(");
        sheetTitle = sheetTitle.replace("]", ")");
        if (jsonObjectAfter.isEmpty()) {
            mastSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), sheetTitle, Integer.valueOf(1));
            Object[] titleData = new Object[]{"\u5f53\u524d\u5355\u4f4d\u62a5\u8868\u5e01\u79cd\u53ea\u6709\u4e00\u4e2a\uff0c\u65e0\u9700\u7a3d\u6838\u3002"};
            rowDatas.add(titleData);
        } else {
            mastSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), sheetTitle, Integer.valueOf(2));
            Object[] titles = new Object[3 + jsonObjectAfter.length() * 3];
            titles[0] = jsonObjectBefore.get("zbName");
            titles[1] = jsonObjectBefore.get("currencyTitle");
            titles[2] = "\u6c47\u7387\u7c7b\u578b";
            for (int i = 1; i <= jsonObjectAfter.length(); ++i) {
                titles[3 * i] = jsonObjectAfter.getJSONObject(i - 1).get("currencyTitle");
                titles[3 * i + 1] = jsonObjectAfter.getJSONObject(i - 1).get("currencyTitle");
                titles[3 * i + 2] = jsonObjectAfter.getJSONObject(i - 1).get("currencyTitle");
                mastSheet.getHeadCellStyleCache().put(3 * i, this.getRightCellStyle(workbook));
                mastSheet.getHeadCellStyleCache().put(3 * i + 1, this.getRightCellStyle(workbook));
                mastSheet.getHeadCellStyleCache().put(3 * i + 2, this.getRightCellStyle(workbook));
            }
            Object[] titles2 = new Object[3 + jsonObjectAfter.length() * 3];
            titles2[0] = jsonObjectBefore.get("zbName");
            titles2[1] = jsonObjectBefore.get("currencyTitle");
            titles2[2] = "\u6c47\u7387\u7c7b\u578b";
            for (int i = 1; i <= jsonObjectAfter.length(); ++i) {
                titles2[3 * i] = "\u6c47\u7387";
                titles2[3 * i + 1] = "\u6298\u7b97\u540e\u91d1\u989d";
                titles2[3 * i + 2] = "\u62a5\u8868\u91d1\u989d";
            }
            rowDatas.add(titles);
            rowDatas.add(titles2);
        }
        mastSheet.getRowDatas().addAll(rowDatas);
        return mastSheet;
    }

    private ExportExcelSheet getExportExcelSheetException(ExportContext context, String message) {
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        String periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), actionParam.getOrgIds().get(0));
        String sheetTitle = gcOrgCacheVO.getTitle() + "|" + gcOrgCacheVO.getCode();
        sheetTitle = sheetTitle.replace("[", "(");
        sheetTitle = sheetTitle.replace("]", ")");
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(0), sheetTitle, Integer.valueOf(1));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] titleData = new Object[]{message};
        rowDatas.add(titleData);
        mastSheet.getRowDatas().addAll(rowDatas);
        return mastSheet;
    }

    private ExportExcelSheet getExportExcelSheet(int sheetNo, Workbook workbook, ExportContext context, JSONObject conversionJson, JSONObject actionParams) {
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        String periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), actionParam.getOrgIds().get(0));
        String sheetTitle = gcOrgCacheVO.getTitle() + "|" + gcOrgCacheVO.getCode();
        sheetTitle = sheetTitle.replace("[", "(");
        sheetTitle = sheetTitle.replace("]", ")");
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(sheetNo), sheetTitle, Integer.valueOf(2));
        mastSheet.getHeadCellStyleCache().put(0, this.getLeftCellStyle(workbook));
        mastSheet.getHeadCellStyleCache().put(1, this.getRightCellStyle(workbook));
        mastSheet.getHeadCellStyleCache().put(2, this.getLeftCellStyle(workbook));
        JSONObject jsonObject = this.gcConversionWorkPaperService.getExcelTableData(gcOrgCacheVO);
        JSONObject jsonObjectBefore = jsonObject.getJSONObject("currencyBeforeTable");
        JSONArray jsonObjectAfter = jsonObject.getJSONArray("currencyAfterTable");
        Object[] titles = new Object[3 + jsonObjectAfter.length() * 3];
        titles[0] = jsonObjectBefore.get("zbName");
        titles[1] = jsonObjectBefore.get("currencyTitle");
        titles[2] = "\u6c47\u7387\u7c7b\u578b";
        for (int i = 1; i <= jsonObjectAfter.length(); ++i) {
            titles[3 * i] = jsonObjectAfter.getJSONObject(i - 1).get("currencyTitle");
            titles[3 * i + 1] = jsonObjectAfter.getJSONObject(i - 1).get("currencyTitle");
            titles[3 * i + 2] = jsonObjectAfter.getJSONObject(i - 1).get("currencyTitle");
        }
        Object[] titles2 = new Object[3 + jsonObjectAfter.length() * 3];
        titles2[0] = jsonObjectBefore.get("zbName");
        titles2[1] = jsonObjectBefore.get("currencyTitle");
        titles2[2] = "\u6c47\u7387\u7c7b\u578b";
        for (int i = 1; i <= jsonObjectAfter.length(); ++i) {
            titles2[3 * i] = "\u6c47\u7387";
            titles2[3 * i + 1] = "\u6298\u7b97\u540e\u91d1\u989d";
            titles2[3 * i + 2] = "\u62a5\u8868\u91d1\u989d";
        }
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        rowDatas.add(titles);
        rowDatas.add(titles2);
        if (jsonObjectAfter.length() < 1) {
            mastSheet.getRowDatas().addAll(rowDatas);
            return mastSheet;
        }
        JSONArray jsonArray = conversionJson.getJSONArray("conversionDatas");
        boolean isDifferenceDataHandle = actionParams.getBoolean("isDifferenceDataHandle");
        for (int j = 0; j < jsonArray.length(); ++j) {
            JSONObject jsonObjectAfterData = jsonArray.getJSONObject(j);
            boolean isDifference = jsonObjectAfterData.optBoolean("isDifference");
            if (isDifferenceDataHandle && !isDifference) continue;
            Object[] titleData = new Object[3 + jsonObjectAfter.length() * 3];
            titleData[0] = jsonObjectAfterData.optString("zbName");
            titleData[1] = jsonObjectAfterData.optString("conversion" + jsonObjectBefore.get("currencyCode"));
            titleData[2] = jsonObjectAfterData.optString("rateType");
            for (int i = 1; i <= jsonObjectAfter.length(); ++i) {
                titleData[3 * i] = jsonObjectAfterData.optString("rate" + jsonObjectAfter.getJSONObject(i - 1).get("currencyCode"));
                titleData[3 * i + 1] = jsonObjectAfterData.optString("conversionAfter" + jsonObjectAfter.getJSONObject(i - 1).get("currencyCode"));
                titleData[3 * i + 2] = jsonObjectAfterData.optString("conversion" + jsonObjectAfter.getJSONObject(i - 1).get("currencyCode"));
            }
            rowDatas.add(titleData);
        }
        mastSheet.getRowDatas().addAll(rowDatas);
        return mastSheet;
    }

    private ExportExcelSheet getHeaderExportExcelSheet(Workbook workbook, ExportContext context, JSONObject conversionJson) throws Exception {
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)new JSONObject(context.getParam()).getJSONObject("data").toString(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        NpContext npContext = NpContextHolder.getContext();
        ContextUser user = npContext.getUser();
        Date nowDate = new Date();
        DateFormat format = DateFormat.getDateTimeInstance();
        String dateStr = format.format(nowDate);
        ExportExcelSheet mastSheet = new ExportExcelSheet(Integer.valueOf(0), "\u7a3d\u6838\u62a5\u544a", Integer.valueOf(5));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        for (int count = 0; count < 6; ++count) {
            mastSheet.getHeadCellStyleCache().put(count, this.getLeftCellStyle(workbook));
        }
        Object[] titles = new Object[6];
        for (int i = 0; i < titles.length; ++i) {
            titles[i] = "\u5916\u5e01\u7a3d\u6838\u62a5\u544a";
        }
        String periodData = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        Object[] titles2 = new Object[]{"\u4efb\u52a1\uff1a", this.iRunTimeViewController.queryTaskDefine(envContext.getTaskKey()).getTaskCode(), "\u62a5\u8868\u65b9\u6848\uff1a", this.iRunTimeViewController.getFormScheme(envContext.getFormSchemeKey()).getFormSchemeCode(), "\u65f6\u671f\uff1a", periodData};
        Object[] titles3 = new Object[]{"\u7a3d\u6838\u4eba\uff1a", user.getName(), "\u7a3d\u6838\u65e5\u671f\uff1a", dateStr, "", ""};
        Object[] titles4 = new Object[6];
        titles4[0] = "\u7a3d\u6838\u7ed3\u679c\uff1a";
        for (int i = 1; i < titles4.length; ++i) {
            int zbCount = conversionJson.getJSONArray("conversionDatas").length();
            int differenceCount = conversionJson.getInt("differenceCount");
            titles4[i] = "\u672c\u6b21\u5171\u7a3d\u6838" + actionParam.getOrgIds().size() + "\u5355\u4f4d\uff0c" + actionParam.getFormIds().size() + "\u5f20\u8868\uff0c" + zbCount + "\u4e2a\u6307\u6807\uff0c\u7a3d\u6838\u901a\u8fc7" + (zbCount - differenceCount) + "\u4e2a\u6307\u6807\uff0c\u7a3d\u6838\u901a\u8fc7\u7387" + new BigDecimal(zbCount - differenceCount).divide(new BigDecimal(zbCount), 2, 4).multiply(new BigDecimal(100)).setScale(0, 4) + "%";
        }
        GcOrgCacheVO gcOrgCacheVO = this.getOrgCode(periodData, actionParam.getOrgVersionType(), actionParam.getOrgIds().get(0));
        FormDefine formDefine = this.runTimeViewController.queryFormById(actionParam.getFormIds().get(0));
        Object[] titles5 = new Object[]{"\u5e8f\u53f7", "\u5355\u4f4d", "\u5dee\u5f02\u6307\u6807\u6570\u91cf", "\u62a5\u8868", "\u5dee\u5f02\u6307\u6807\u6570\u91cf", ""};
        Object[] titles6 = new Object[]{"1", gcOrgCacheVO.getTitle(), conversionJson.get("differenceCount"), formDefine.getTitle(), conversionJson.get("differenceCount"), ""};
        rowDatas.add(titles);
        rowDatas.add(titles2);
        rowDatas.add(titles3);
        rowDatas.add(titles4);
        rowDatas.add(titles5);
        rowDatas.add(titles6);
        mastSheet.getRowDatas().addAll(rowDatas);
        return mastSheet;
    }

    private GcOrgCacheVO getOrgCode(String periodStr, String orgVersionType, String orgId) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)orgVersionType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        return instance.getOrgByCode(orgId);
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        int numberOfCells = row.getPhysicalNumberOfCells();
        if ("\u7a3d\u6838\u62a5\u544a".equals(excelSheet.getSheetName())) {
            CellStyle titleHeadStringStyle;
            if (rowNum == 0) {
                titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
                titleHeadStringStyle.setAlignment(HorizontalAlignment.CENTER);
                cell.setCellStyle(titleHeadStringStyle);
            }
            if (rowNum > 4) {
                if (numberOfCells == 3 || numberOfCells == 5) {
                    this.titleHeadCellStyle.setAlignment(HorizontalAlignment.RIGHT);
                } else {
                    this.titleHeadCellStyle.setAlignment(HorizontalAlignment.LEFT);
                }
                this.titleHeadCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                cell.setCellStyle(this.titleHeadCellStyle);
            }
            if (rowNum == 1 && (numberOfCells == 2 || numberOfCells == 4 || numberOfCells == 6)) {
                titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
                titleHeadStringStyle.setAlignment(HorizontalAlignment.LEFT);
                titleHeadStringStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                cell.setCellStyle(titleHeadStringStyle);
            }
            if (rowNum == 2 && (numberOfCells == 2 || numberOfCells > 3)) {
                titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
                titleHeadStringStyle.setAlignment(HorizontalAlignment.LEFT);
                titleHeadStringStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                cell.setCellStyle(titleHeadStringStyle);
            }
            if (rowNum == 3 && numberOfCells > 1) {
                titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
                titleHeadStringStyle.setAlignment(HorizontalAlignment.LEFT);
                titleHeadStringStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                cell.setCellStyle(titleHeadStringStyle);
            }
            int columnMaxIndex = excelSheet.getColumnMaxIndex();
            int rowMaxIndex = excelSheet.getRowDatas().size();
            int offSet = 5;
            if (rowNum + 1 == rowMaxIndex && numberOfCells == columnMaxIndex + 1) {
                LinkedHashMap<Integer, List<String>> index2HeadsMap = new LinkedHashMap<Integer, List<String>>();
                for (int columnIndex = 0; columnIndex <= 2; ++columnIndex) {
                    index2HeadsMap.put(columnIndex, new ArrayList());
                    for (int headRowNum = offSet; headRowNum < rowMaxIndex; ++headRowNum) {
                        String headValue = String.valueOf(((Object[])excelSheet.getRowDatas().get(headRowNum))[columnIndex]);
                        ((List)index2HeadsMap.get(columnIndex)).add(headValue);
                    }
                }
                List<CellRangeAddress> headCellRangeAddresses = this.buildAutoMergeHeadCellRangeAddresses(index2HeadsMap, offSet);
                if (!CollectionUtils.isEmpty(headCellRangeAddresses)) {
                    excelSheet.getCellRangeAddresses().addAll(headCellRangeAddresses);
                }
            }
        } else {
            if (rowNum == 0 && numberOfCells > 3) {
                CellStyle titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
                titleHeadStringStyle.setAlignment(HorizontalAlignment.CENTER);
                cell.setCellStyle(titleHeadStringStyle);
            }
            if (rowNum > 1 && (numberOfCells == 2 || numberOfCells > 3)) {
                if (rowNum == 2 && numberOfCells == 2) {
                    this.titleStringStyle = workbook.createCellStyle();
                    this.buildDefaultContentCellStyleRest(workbook);
                }
                cell.setCellStyle(this.titleStringStyle);
            }
        }
    }

    protected CellStyle buildDefaultContentCellStyleRest(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)13);
        font.setItalic(false);
        font.setStrikeout(false);
        font.setColor((short)0);
        font.setTypeOffset((short)0);
        font.setUnderline((byte)0);
        font.setCharSet(0);
        font.setBold(false);
        this.titleStringStyle.setFont(font);
        this.titleStringStyle.setDataFormat((short)0);
        this.titleStringStyle.setHidden(false);
        this.titleStringStyle.setLocked(false);
        this.titleStringStyle.setQuotePrefixed(false);
        this.titleStringStyle.setAlignment(ExcelConsts.ContentCellStyle.horizontalAlignment);
        this.titleStringStyle.setWrapText(false);
        this.titleStringStyle.setVerticalAlignment(ExcelConsts.ContentCellStyle.verticalAlignment);
        this.titleStringStyle.setRotation((short)0);
        this.titleStringStyle.setIndention((short)0);
        this.titleStringStyle.setBorderLeft(ExcelConsts.ContentCellStyle.borderLeft);
        this.titleStringStyle.setBorderRight(ExcelConsts.ContentCellStyle.borderRight);
        this.titleStringStyle.setBorderTop(ExcelConsts.ContentCellStyle.borderTop);
        this.titleStringStyle.setBorderBottom(ExcelConsts.ContentCellStyle.borderBottom);
        this.titleStringStyle.setLeftBorderColor((short)0);
        this.titleStringStyle.setRightBorderColor((short)0);
        this.titleStringStyle.setTopBorderColor((short)0);
        this.titleStringStyle.setBottomBorderColor((short)0);
        this.titleStringStyle.setFillPattern(ExcelConsts.ContentCellStyle.fillPatternType);
        this.titleStringStyle.setFillBackgroundColor((short)9);
        this.titleStringStyle.setFillForegroundColor((short)9);
        this.titleStringStyle.setShrinkToFit(false);
        this.titleStringStyle.setAlignment(HorizontalAlignment.RIGHT);
        return this.titleStringStyle;
    }

    private List<CellRangeAddress> buildAutoMergeHeadCellRangeAddresses(Map<Integer, List<String>> index2HeadsMap, int offSet) {
        if (CollectionUtils.isEmpty(index2HeadsMap)) {
            return Collections.emptyList();
        }
        ArrayList<CellRangeAddress> cellRangeList = new ArrayList<CellRangeAddress>();
        HashSet<String> alreadyRangeSet = new HashSet<String>();
        ArrayList<List<String>> headList = new ArrayList<List<String>>(index2HeadsMap.values());
        for (int colIndex = 0; colIndex < headList.size(); ++colIndex) {
            List headNameList = (List)headList.get(colIndex);
            for (int rowIndex = 0; rowIndex < headNameList.size(); ++rowIndex) {
                if (alreadyRangeSet.contains(colIndex + "-" + rowIndex)) continue;
                alreadyRangeSet.add(colIndex + "-" + rowIndex);
                String headName = (String)headNameList.get(rowIndex);
                int lastColIndex = colIndex;
                int lastRowIndex = rowIndex;
                int colIndex2 = colIndex + 1;
                while (colIndex2 < headList.size() && ((String)((List)headList.get(colIndex2)).get(rowIndex)).equals(headName)) {
                    alreadyRangeSet.add(colIndex2 + "-" + rowIndex);
                    lastColIndex = colIndex2++;
                }
                HashSet<String> tempAlreadyRangeSet = new HashSet<String>();
                int rowIndex2 = rowIndex + 1;
                block3: while (rowIndex2 < headNameList.size()) {
                    for (int colIndex3 = colIndex; colIndex3 <= lastColIndex; ++colIndex3) {
                        if (colIndex == 2) {
                            if (!((String)((List)headList.get(colIndex3 - 1)).get(rowIndex2 - 1)).equals(((List)headList.get(colIndex3 - 1)).get(rowIndex2))) break block3;
                            tempAlreadyRangeSet.add(colIndex3 + "-" + rowIndex2);
                            continue;
                        }
                        if (!((String)((List)headList.get(colIndex3)).get(rowIndex2)).equals(headName)) break block3;
                        tempAlreadyRangeSet.add(colIndex3 + "-" + rowIndex2);
                    }
                    lastRowIndex = rowIndex2++;
                    alreadyRangeSet.addAll(tempAlreadyRangeSet);
                }
                if (rowIndex == lastRowIndex && colIndex == lastColIndex) continue;
                cellRangeList.add(new CellRangeAddress(rowIndex + offSet, lastRowIndex + offSet, colIndex, lastColIndex));
            }
        }
        return cellRangeList;
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        if ("\u7a3d\u6838\u62a5\u544a".equals(excelSheet.getSheetName())) {
            sheet.setColumnWidth(1, 12800);
            sheet.setColumnWidth(3, 10240);
            sheet.setColumnWidth(5, 7680);
        } else {
            sheet.setColumnWidth(0, 12800);
            sheet.setColumnWidth(1, 5120);
            sheet.setColumnWidth(2, 5120);
            int columnMaxIndex = excelSheet.getColumnMaxIndex();
            for (int columnIndex = 3; columnIndex <= columnMaxIndex; ++columnIndex) {
                if (columnIndex % 3 != 1 && columnIndex % 3 != 2) continue;
                sheet.setColumnWidth(columnIndex, 5120);
            }
        }
    }
}

