/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.kernel.geom.PageSize
 *  com.itextpdf.kernel.pdf.PdfDocument
 *  com.itextpdf.kernel.pdf.PdfWriter
 *  com.itextpdf.kernel.pdf.WriterProperties
 *  com.itextpdf.layout.Document
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.query.sql.web;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.query.print.QueryPrintAction;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.util.VAQueryI18nUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryPrintController {
    private static final String QUERY_SQL_BASE_API = "/api/datacenter/v1/userDefined/sql";
    private static final Logger logger = LoggerFactory.getLogger(QueryPrintController.class);
    @Autowired
    private QueryPrintAction queryPrintAction;

    @PostMapping(value={"/api/datacenter/v1/userDefined/sql/print/{scheme}"})
    public void execPreviewSql(@PathVariable(value="scheme") String schemeCode, @RequestBody QueryParamVO queryParamVO) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
            try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter((OutputStream)byteArrayOutputStream, new WriterProperties().setFullCompressionMode(true)));
                 Document document = new Document(pdfDoc, PageSize.A4);){
                this.queryPrintAction.executeTemplatePrint(document, queryParamVO, schemeCode);
            }
            RequestContextUtil.setResponseContentType((String)"application/pdf");
            byteArrayOutputStream.writeTo(RequestContextUtil.getOutputStream());
        }
        catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            RequestContextUtil.setResponseContentType((String)"application/json;charset=UTF-8");
            String errorMessage = VAQueryI18nUtil.getMessage("va.query.printError") + ": " + exception.getMessage();
            PrintWriter writer = new PrintWriter(RequestContextUtil.getOutputStream(), true);
            writer.write(errorMessage);
            writer.close();
        }
    }
}

