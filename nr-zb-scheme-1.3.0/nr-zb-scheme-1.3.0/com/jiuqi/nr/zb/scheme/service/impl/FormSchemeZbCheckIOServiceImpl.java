/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.zb.scheme.common.Consts;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.internal.excel.ExcelSheetWriter;
import com.jiuqi.nr.zb.scheme.internal.excel.ExcelWriter;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.ExcelRowWrapperImpl;
import com.jiuqi.nr.zb.scheme.internal.excel.impl.FormSchemeZbChekRowWriterImpl;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckIOService;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckQueryService;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckExportParam;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FormSchemeZbCheckIOServiceImpl
implements IFormSchemeZbCheckIOService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormSchemeZbCheckQueryService formSchemeZbCheckQueryService;

    @Override
    public void exportExcel(ZbCheckExportParam exportParam, Workbook workbook) {
        Assert.notNull((Object)exportParam, "exportParam is null");
        Assert.notNull((Object)workbook, "workbook is null");
        ExcelWriter excelWriter = new ExcelWriter(workbook);
        ExcelSheetWriter excelSheetWriter = excelWriter.createSheet("\u6307\u6807\u68c0\u67e5\u7ed3\u679c");
        excelSheetWriter.registerWriter(ZbCheckItemDTO.class, new FormSchemeZbChekRowWriterImpl(this.buildCellStyle(excelWriter.getWorkbook())));
        excelSheetWriter.addHeader(Consts.EXCEL_ZBCHECK_ATTRS);
        this.formSchemeZbCheckQueryService.queryForExport(exportParam).stream().map(ExcelRowWrapperImpl::new).forEach(excelSheetWriter::addRow);
        excelSheetWriter.write();
        Sheet sheet = excelSheetWriter.getSheet();
        List<String> headers = excelSheetWriter.getAllHeaders();
        for (int i = 0; i < headers.size(); ++i) {
            sheet.getRow(0).getCell(i).setCellStyle(this.buildHeadCellStyle(excelWriter.getWorkbook()));
            sheet.autoSizeColumn(i);
        }
    }

    private CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private CellStyle buildCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        cellStyle.setFont(font);
        return cellStyle;
    }
}

