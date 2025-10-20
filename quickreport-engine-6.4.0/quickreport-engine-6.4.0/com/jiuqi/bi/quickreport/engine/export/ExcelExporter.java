/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.jiuqi.bi.office.excel.watermark.IWatermarkInjector
 *  com.jiuqi.bi.syntax.excel.ExcelException
 *  com.jiuqi.bi.syntax.excel.ExcelExportor
 *  com.jiuqi.bi.syntax.excel.ISheetDescriptor
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.export;

import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.bi.office.excel.watermark.IWatermarkInjector;
import com.jiuqi.bi.syntax.excel.ExcelException;
import com.jiuqi.bi.syntax.excel.ExcelExportor;
import com.jiuqi.bi.syntax.excel.ISheetDescriptor;
import com.jiuqi.bi.util.StringUtils;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Iterator;

public final class ExcelExporter {
    public static final String EXCEL_XLS = "xls";
    public static final String EXCEL_XLSX = "xlsx";
    private final Iterator<ISheetDescriptor> sheetIterator;
    private final String ext;
    private IWatermarkInjector injector;

    public ExcelExporter(Iterator<ISheetDescriptor> sheetIterator, String ext) {
        this.sheetIterator = sheetIterator;
        this.ext = StringUtils.isEmpty((String)ext) ? EXCEL_XLSX : ext;
    }

    public void setWatermarkInjector(IWatermarkInjector injector) {
        this.injector = injector;
    }

    public void export(OutputStream outStream) throws ExcelException {
        this.export(outStream, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void export(OutputStream outStream, int options) throws ExcelException {
        ExcelExportor exporter = new ExcelExportor(this.sheetIterator, this.isXlsx());
        exporter.setWatermarkInjector(this.injector);
        if (this.isExcel()) {
            exporter.export(outStream, options);
            return;
        }
        try {
            Path fPath = File.createTempFile("excel_", ".tmp").toPath();
            try (OutputStream fos = Files.newOutputStream(fPath, new OpenOption[0]);){
                exporter.export(fos, options);
                try (InputStream fis = Files.newInputStream(fPath, new OpenOption[0]);){
                    new SpireHelper().excel2file(fis, outStream, this.ext);
                }
            }
            finally {
                Files.delete(fPath);
            }
        }
        catch (Exception e) {
            throw new ExcelException("\u5bfc\u51fa\u6587\u4ef6\u5f02\u5e38", (Throwable)e);
        }
    }

    private boolean isExcel() {
        return StringUtils.equalsIgnoreCase((String)this.ext, (String)EXCEL_XLSX) || StringUtils.equalsIgnoreCase((String)this.ext, (String)EXCEL_XLS);
    }

    private boolean isXlsx() {
        return StringUtils.equalsIgnoreCase((String)this.ext, (String)EXCEL_XLSX);
    }
}

