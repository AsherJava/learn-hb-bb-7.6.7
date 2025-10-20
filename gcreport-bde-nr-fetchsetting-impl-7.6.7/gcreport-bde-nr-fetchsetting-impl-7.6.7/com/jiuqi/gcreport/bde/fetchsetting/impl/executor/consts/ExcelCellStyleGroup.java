/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts;

import com.jiuqi.common.expimp.util.ExpImpUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelCellStyleGroup {
    private final CellStyle headStringStyle;
    private final CellStyle headStringBlueBackStyle;
    private final CellStyle contentLeftStringBackGreyStyle;
    private final CellStyle contentStringStyle;
    private final CellStyle contentStringCenterStyle;
    private final Workbook workbook;

    public ExcelCellStyleGroup(Workbook workbook) {
        this.workbook = workbook;
        this.headStringStyle = this.buildHeadStringStyle();
        this.headStringBlueBackStyle = this.buildHeadStringBlueBackStyle();
        this.contentLeftStringBackGreyStyle = this.buildContentLeftStringBackGreyStyle();
        this.contentStringStyle = this.buildContentStringStyle();
        this.contentStringCenterStyle = this.buildContentStringCenterStyle();
    }

    private CellStyle buildHeadStringStyle() {
        CellStyle cellStyle = ExpImpUtils.buildDefaultHeadCellStyle((Workbook)this.workbook);
        cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat("@"));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    private CellStyle buildHeadStringBlueBackStyle() {
        CellStyle cellStyle = ExpImpUtils.buildDefaultHeadCellStyle((Workbook)this.workbook);
        cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat("@"));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());
        return cellStyle;
    }

    private CellStyle buildContentLeftStringBackGreyStyle() {
        CellStyle cellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)this.workbook);
        cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat("@"));
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
        return cellStyle;
    }

    private CellStyle buildContentStringStyle() {
        CellStyle cellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)this.workbook);
        cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat("@"));
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        return cellStyle;
    }

    private CellStyle buildContentStringCenterStyle() {
        CellStyle cellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)this.workbook);
        cellStyle.setDataFormat(this.workbook.createDataFormat().getFormat("@"));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        return cellStyle;
    }

    public CellStyle getHeadStringStyle() {
        return this.headStringStyle;
    }

    public CellStyle getHeadStringBlueBackStyle() {
        return this.headStringBlueBackStyle;
    }

    public CellStyle getContentLeftStringBackGreyStyle() {
        return this.contentLeftStringBackGreyStyle;
    }

    public CellStyle getContentStringStyle() {
        return this.contentStringStyle;
    }

    public CellStyle getContentStringCenterStyle() {
        return this.contentStringCenterStyle;
    }
}

