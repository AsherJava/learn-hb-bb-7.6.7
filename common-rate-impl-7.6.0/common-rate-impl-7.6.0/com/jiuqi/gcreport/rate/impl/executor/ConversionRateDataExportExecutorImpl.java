/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.rate.impl.executor;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.gcreport.rate.impl.executor.ConversionRateDataExportParam;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.gcreport.rate.impl.service.RateExportImportService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionRateDataExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private RateExportImportService service;
    @Autowired
    private CommonRateSchemeService commonRateSchemeService;

    public String getName() {
        return "ConversionRateDataExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ConversionRateDataExportParam exportParam = (ConversionRateDataExportParam)JsonUtils.readValue((String)context.getParam(), ConversionRateDataExportParam.class);
        Boolean isRoot = exportParam.getRootFlag();
        String periodStrStart = isRoot != false ? "" : exportParam.getPeriodStrStart();
        String periodStrEnd = isRoot != false ? "" : exportParam.getPeriodStrEnd();
        String sourceCurrencyCode = isRoot != false ? "" : exportParam.getSourceCurrencyCode();
        String targetCurrencyCode = isRoot != false ? "" : exportParam.getTargetCurrencyCode();
        Map<String, String> titleMap = this.service.getRateExcelColumnTitleMap();
        ArrayList<String> columnKeys = new ArrayList<String>(titleMap.keySet());
        List<Object> rateSchemeVOS = new ArrayList();
        if (isRoot.booleanValue()) {
            rateSchemeVOS = this.commonRateSchemeService.listAllRateScheme();
        } else {
            String rateSchemeCode = exportParam.getRateSchemeCode();
            CommonRateSchemeVO vo = this.commonRateSchemeService.queryRateScheme(rateSchemeCode);
            rateSchemeVOS.add(vo);
        }
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        for (int i = 0; i < rateSchemeVOS.size(); ++i) {
            ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
            Object[] headDatas = new Object[columnKeys.size()];
            for (int j = 0; j < columnKeys.size(); ++j) {
                String key = (String)columnKeys.get(j);
                headDatas[j] = titleMap.get(key);
            }
            rowDatas.add(headDatas);
            CommonRateSchemeVO vo = (CommonRateSchemeVO)rateSchemeVOS.get(i);
            if (!context.isTemplateExportFlag()) {
                List<Map<String, Object>> exportDatas = this.service.rateExport(vo.getCode(), vo.getPeriodType(), periodStrStart, periodStrEnd, sourceCurrencyCode, targetCurrencyCode);
                exportDatas.stream().forEach(exportData -> {
                    Object[] rowData = new Object[columnKeys.size()];
                    for (int j = 0; j < columnKeys.size(); ++j) {
                        String key = (String)columnKeys.get(j);
                        rowData[j] = exportData.get(key);
                    }
                    rowDatas.add(rowData);
                });
            }
            ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(i), vo.getName());
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            Map contentCellStyleCache = exportExcelSheet.getContentCellStyleCache();
            short format = workbook.createDataFormat().getFormat("@");
            List<BaseDataDO> rateTypeInfosCache = CommonRateUtils.getAllRateTypes();
            for (int columnIndex = 0; columnIndex < headDatas.length; ++columnIndex) {
                String headTitle = ConverterUtils.getAsString((Object)headDatas[columnIndex], (String)"");
                BaseDataDO rateTypeEnum = CommonRateUtils.findByTitle(headTitle, rateTypeInfosCache);
                CellStyle contentCellStyle = this.buildDefaultContentCellStyle(workbook);
                if (rateTypeEnum != null) {
                    contentCellStyle.setAlignment(HorizontalAlignment.RIGHT);
                } else {
                    contentCellStyle.setAlignment(HorizontalAlignment.LEFT);
                }
                contentCellStyle.setDataFormat(format);
                contentCellStyleCache.put(columnIndex, contentCellStyle);
            }
            exportExcelSheets.add(exportExcelSheet);
        }
        return exportExcelSheets;
    }
}

