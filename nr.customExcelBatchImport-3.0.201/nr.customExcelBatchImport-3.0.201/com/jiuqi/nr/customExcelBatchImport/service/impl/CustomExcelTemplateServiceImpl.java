/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.customExcelBatchImport.service.impl;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelRegionTitleService;
import com.jiuqi.nr.customExcelBatchImport.service.ICustomExcelTemplateService;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomExcelTemplateServiceImpl
implements ICustomExcelTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(CustomExcelTemplateServiceImpl.class);
    @Resource
    private ICustomExcelRegionTitleService customExcelRegionTitleService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;

    @Override
    public Workbook createTemplate(DataRegionDefine region) {
        XSSFWorkbook workbook = null;
        List<FieldDefine> regionFieldDefineList = this.customExcelRegionTitleService.getRegionFieldDefineList(region);
        Map<String, List<FieldDefine>> dimFieldDefineMap = this.customExcelRegionTitleService.getDimFieldDefineMap(region, null);
        if (regionFieldDefineList == null || regionFieldDefineList.size() == 0) {
            return workbook;
        }
        workbook = new XSSFWorkbook();
        String sheetTitle = this.customExcelRegionTitleService.getTitleByRegion(region);
        XSSFSheet sheet = workbook.createSheet(sheetTitle);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("\u5b8b\u4f53");
        font.setBold(true);
        style.setFont(font);
        XSSFCell cell1 = null;
        XSSFCell cell2 = null;
        XSSFRow row1 = sheet.createRow(0);
        XSSFRow row2 = sheet.createRow(1);
        int fieldColumn = regionFieldDefineList.size();
        int columIndex = 0;
        for (String dim : dimFieldDefineMap.keySet()) {
            if (dimFieldDefineMap.get(dim) == null || dim.equals("DATATIME")) continue;
            cell1 = row1.createCell(columIndex);
            cell2 = row2.createCell(columIndex);
            List<FieldDefine> dimFieldDefines = dimFieldDefineMap.get(dim);
            String dimTitle = dimFieldDefines.get(0).getTitle();
            cell1.setCellValue(dimTitle);
            cell2.setCellValue(dim);
            cell1.setCellStyle(style);
            cell2.setCellStyle(style);
            ++columIndex;
        }
        for (int i = 0; i < fieldColumn; ++i) {
            if (regionFieldDefineList.get(i) == null) continue;
            cell1 = row1.createCell(columIndex);
            cell2 = row2.createCell(columIndex);
            FieldDefine dataFieldDefine = regionFieldDefineList.get(i);
            List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFieldDefine.getKey()});
            String dataFieldCode = ((DataFieldDeployInfo)deployInfos.get(0)).getTableName() + "[" + regionFieldDefineList.get(i).getCode() + "]";
            cell1.setCellValue(dataFieldDefine.getTitle());
            cell2.setCellValue(dataFieldCode);
            cell1.setCellStyle(style);
            cell2.setCellStyle(style);
            ++columIndex;
        }
        return workbook;
    }
}

