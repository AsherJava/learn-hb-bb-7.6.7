/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridData
 *  com.jiuqi.np.office.excel.WorksheetReader
 */
package com.jiuqi.nr.convert.pdf.utils;

import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.office.excel.WorksheetReader;
import com.jiuqi.nr.convert.pdf.converter.AddTitleForPDF;
import com.jiuqi.nr.convert.pdf.converter.Converter;
import com.jiuqi.nr.convert.pdf.converter.DocToPDFConverter;
import com.jiuqi.nr.convert.pdf.converter.DocxToPDFConverter;
import com.jiuqi.nr.convert.pdf.converter.OdtToPDF;
import com.jiuqi.nr.convert.pdf.converter.OfdToPDFConverter;
import com.jiuqi.nr.convert.pdf.converter.PptxToPDFConverter;
import com.jiuqi.nr.convert.pdf.converter.TxtToPDFConverter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
    public static boolean shouldShowMessages = true;

    public static byte[] convertToPdf(InputStream inputStream, FILE_TYPE_TO_PDF type) {
        return ConvertUtil.convertToPdf(inputStream, type, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] convertToPdf(InputStream inputStream, FILE_TYPE_TO_PDF type, String fileTitle) {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream(0xA00000);){
            Converter converter = ConvertUtil.getConverter(inputStream, outStream, type);
            if (converter == null) return null;
            if (fileTitle == null) {
                converter.convert();
            } else {
                converter.convert(fileTitle);
            }
            byte[] byArray = outStream.toByteArray();
            return byArray;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static GridData convertToGridData(Workbook workbook, Sheet sheet) {
        WorksheetReader worksheetReader = new WorksheetReader(workbook, sheet);
        return worksheetReader.getGridData();
    }

    private static Converter getConverter(InputStream inStream, ByteArrayOutputStream outStream, FILE_TYPE_TO_PDF type) {
        Converter converter = null;
        switch (type) {
            case DOC: {
                converter = new DocToPDFConverter(inStream, outStream, shouldShowMessages, true);
                break;
            }
            case DOCX: {
                converter = new DocxToPDFConverter(inStream, outStream, shouldShowMessages, true);
                break;
            }
            case PPTX: {
                converter = new PptxToPDFConverter(inStream, outStream, shouldShowMessages, true);
                break;
            }
            case ODT: {
                converter = new OdtToPDF(inStream, outStream, shouldShowMessages, true);
                break;
            }
            case PDF: {
                converter = new AddTitleForPDF(inStream, outStream, shouldShowMessages, true);
                break;
            }
            case TXT: {
                converter = new TxtToPDFConverter(inStream, outStream, shouldShowMessages, true);
                break;
            }
            case OFD: {
                converter = new OfdToPDFConverter(inStream, outStream, shouldShowMessages, true);
                break;
            }
            default: {
                converter = null;
            }
        }
        return converter;
    }

    public static enum FILE_TYPE_TO_PDF {
        DOC,
        DOCX,
        PPTX,
        ODT,
        PDF,
        TXT,
        OFD;

    }
}

