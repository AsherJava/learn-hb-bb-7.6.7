/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService
 *  com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO
 *  com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService;
import com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetConfigVO;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCarryOverSubjectExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private GcCarryOverConfigService configService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "GcCarryOverSubjectExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u7ed3\u8f6c\u4e3a\u5e74\u521d\u672a\u5206\u914d\u5229\u6da6\u79d1\u76ee");
        String[] titles = new String[]{"\u5e8f\u53f7", "\u79d1\u76ee\u4ee3\u7801", "\u79d1\u76ee\u540d\u79f0"};
        exportExcelSheet.getRowDatas().add(titles);
        exportExcelSheets.add(exportExcelSheet);
        Map paramsVO = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String carryOverSchemeId = (String)paramsVO.get("carryOverSchemeId");
        Assert.isNotEmpty((String)carryOverSchemeId, (String)"\u5e74\u7ed3\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String configOptionDataStr = this.configService.getConfigOptionById(carryOverSchemeId);
        CarryOverOffsetConfigVO optionData = (CarryOverOffsetConfigVO)JsonUtils.readValue((String)configOptionDataStr, CarryOverOffsetConfigVO.class);
        CarryOverConfigOptionBaseVO optionBaseData = (CarryOverConfigOptionBaseVO)JsonUtils.readValue((String)configOptionDataStr, CarryOverConfigOptionBaseVO.class);
        if (optionData == null) {
            return exportExcelSheets;
        }
        List carryOverSubjectCodes = optionData.getCarryOverSubjectVos().stream().map(CarryOverOffsetSubjectVO::getCode).collect(Collectors.toList());
        List baseDatas = this.subjectService.listAllSubjectsBySystemId(optionBaseData.getBoundSystemId());
        if (CollectionUtils.isEmpty(baseDatas = baseDatas.stream().filter(data -> carryOverSubjectCodes.contains(data.getCode())).collect(Collectors.toList()))) {
            return exportExcelSheets;
        }
        int rowIndex = 1;
        for (ConsolidatedSubjectEO baseData : baseDatas) {
            Object[] rowData = new Object[]{rowIndex++, baseData.getCode(), baseData.getTitle()};
            exportExcelSheet.getRowDatas().add(rowData);
        }
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        String cellStyleKey = rowNum == 0 ? "headString" : "contentString";
        cell.setCellStyle(this.getCellStyleMap(workbook).get(cellStyleKey));
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        sheet.setColumnWidth(0, 1280);
        sheet.setColumnWidth(1, 5120);
        sheet.setColumnWidth(2, 12800);
    }
}

