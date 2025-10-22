/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.itextpdf.kernel.colors.ColorConstants
 *  com.itextpdf.kernel.font.PdfFont
 *  com.itextpdf.kernel.font.PdfFontFactory
 *  com.itextpdf.kernel.geom.Rectangle
 *  com.itextpdf.kernel.pdf.PdfDocument
 *  com.itextpdf.kernel.pdf.PdfPage
 *  com.itextpdf.kernel.pdf.PdfReader
 *  com.itextpdf.kernel.pdf.PdfWriter
 *  com.itextpdf.kernel.pdf.WriterProperties
 *  com.itextpdf.kernel.pdf.canvas.PdfCanvas
 *  com.itextpdf.kernel.pdf.canvas.draw.DottedLine
 *  com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer
 *  com.itextpdf.kernel.utils.PdfMerger
 *  com.itextpdf.layout.Canvas
 *  com.itextpdf.layout.Document
 *  com.itextpdf.layout.element.IBlockElement
 *  com.itextpdf.layout.element.ILeafElement
 *  com.itextpdf.layout.element.Paragraph
 *  com.itextpdf.layout.element.Tab
 *  com.itextpdf.layout.element.TabStop
 *  com.itextpdf.layout.element.Text
 *  com.itextpdf.layout.properties.TabAlignment
 *  com.itextpdf.layout.properties.TextAlignment
 *  com.itextpdf.layout.properties.VerticalAlignment
 *  com.jiuqi.bi.office.excel.spire.SpireHelper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy
 *  com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase
 *  com.jiuqi.nr.definition.print.service.IPrintTemplateService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.util.FileUtil
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.xg.print.SimplePaintInteractor
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.IPaintInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.SimpleProcessMonitor
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.spire.pdf.FileFormat
 *  com.spire.pdf.PdfDocument
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.Nullable
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.google.gson.Gson;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.jiuqi.bi.office.excel.spire.SpireHelper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.bean.ExportPdfType;
import com.jiuqi.nr.dataentry.export.IDataEntryExportService;
import com.jiuqi.nr.dataentry.internal.service.ExportExcelNameServiceImpl;
import com.jiuqi.nr.dataentry.paramInfo.PrintFilterInfo;
import com.jiuqi.nr.dataentry.print.common.interactor.PrintIPaginateInteractor;
import com.jiuqi.nr.dataentry.print.common.other.Log4jPrintStream;
import com.jiuqi.nr.dataentry.print.common.other.PDFPrintUtil2;
import com.jiuqi.nr.dataentry.print.common.param.PrintParam;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IExternalPdfExportor;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.FileCreatUtil;
import com.jiuqi.nr.dataservice.core.log.DataServiceLogHelper;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.common.define.DefaultPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.define.IPageNumberGenerateStrategy;
import com.jiuqi.nr.definition.facade.print.common.param.IPrintParamBase;
import com.jiuqi.nr.definition.print.service.IPrintTemplateService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.util.FileUtil;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.xg.print.SimplePaintInteractor;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.IPaintInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.SimpleProcessMonitor;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="EXPORT_PDF")
public class ExportPdfServiceImpl
implements IDataEntryExportService {
    private static final Logger logger = LoggerFactory.getLogger(ExportPdfServiceImpl.class);
    private static final String MODULEPDF = "PDF\u5bfc\u51fa";
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IPrintRunTimeController printRunTimeController;
    @Autowired
    private IPrintTemplateService printTemplateService;
    @Autowired
    private ExportExcelNameServiceImpl exportExcelNameService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Resource
    private IRunTimeViewController controller;
    @Resource
    private ISecretLevelService iSecretLevelService;
    @Autowired(required=false)
    private IExternalPdfExportor iExternalPdfExportor;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    private static final int MAX_LINES_PER_PAGE = 40;
    private static final float LEFT_MARGIN = 50.0f;
    private static final float RIGHT_MARGIN = 50.0f;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ExportData export(ExportParam param, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        String[] splits;
        DataServiceLogHelper logHelper = this.dataServiceLoggerFactory.getLogger("PDF\u5bfc\u51fa\u670d\u52a1", OperLevel.USER_OPER);
        FormSchemeDefine formScheme = this.controller.getFormScheme(param.getContext().getFormSchemeKey());
        IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(formScheme.getDw());
        LogDimensionCollection logDimension = null;
        try {
            logDimension = new LogDimensionCollection();
            logDimension.setDw(formScheme.getDw(), new String[]{((DimensionValue)param.getContext().getDimensionSet().get(queryEntity.getDimensionName())).getValue()});
            logDimension.setPeriod(formScheme.getDateTime(), ((DimensionValue)param.getContext().getDimensionSet().get("DATATIME")).getValue());
        }
        catch (Exception e1) {
            logger.error("\u6784\u5efa\u4e1a\u52a1\u65e5\u5fd7\u7ef4\u5ea6\u51fa\u9519");
        }
        logHelper.info(formScheme.getTaskKey(), logDimension, "PDF\u5bfc\u51fa\u5f00\u59cb", "\u5bfc\u51faPDF\u6587\u4ef6");
        String pdfFileName = this.exportExcelNameService.compileNameInfoWithSetting(param.getContext().getFormKey(), param.getContext(), "EXCEL_NAME", false, param.getContext().getUnitViewKey(), param.getRuleSettings());
        JtableContext jtableContext = param.getContext();
        String fileName = "";
        param.setBackground(false);
        boolean isOneForm = false;
        String formKeys = param.getFormKeys();
        if (!StringUtils.isEmpty((String)formKeys) && (splits = formKeys.split(";")).length <= 1) {
            formKeys = formKeys.replace(";", "");
            jtableContext.setFormKey(formKeys);
            isOneForm = true;
        }
        if (!param.isAllForm() && isOneForm && !param.isPrintCatalog()) {
            FormData formDefine = this.jtableParamService.getReport(jtableContext.getFormKey(), param.getContext().getFormSchemeKey());
            param.setSheetName(DataEntryUtil.getFormTitle(formDefine));
            if (formDefine.getFormType().equals("FORM_TYPE_ANALYSISREPORT")) {
                logHelper.info(formScheme.getTaskKey(), logDimension, "PDF\u5bfc\u51fa\u5b8c\u6210", "\u5bfc\u51faPDF\u6587\u4ef6");
                return new ExportData(true);
            }
            byte[] bytes = this.exportPdf(param, null);
            if (param.getType().equals("EXPORT_OFD")) {
                SpireHelper.loadSpireLicence();
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();){
                    PdfDocument pdfDoc = new PdfDocument();
                    pdfDoc.loadFromBytes(bytes);
                    pdfDoc.saveToStream((OutputStream)byteArrayOutputStream, FileFormat.OFD);
                    bytes = byteArrayOutputStream.toByteArray();
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            logHelper.info(formScheme.getTaskKey(), logDimension, "PDF\u5bfc\u51fa\u5b8c\u6210", "\u5bfc\u51faPDF\u6587\u4ef6");
            if (bytes == null) {
                return new ExportData(true);
            }
            return new ExportData(pdfFileName, bytes);
        }
        boolean havaData = false;
        String fileLocation = BatchExportConsts.EXPORTDIR + BatchExportConsts.SEPARATOR + "concatPDF" + BatchExportConsts.SEPARATOR;
        String filePathUuid = UUID.randomUUID().toString() + BatchExportConsts.SEPARATOR;
        ArrayList pdfList = new ArrayList();
        DimensionValue DATA_SNAPSHOT_ID = null;
        if (jtableContext.getDimensionSet().containsKey("DATASNAPSHOTID")) {
            DATA_SNAPSHOT_ID = (DimensionValue)jtableContext.getDimensionSet().get("DATASNAPSHOTID");
        }
        List<FormData> forms = DataEntryUtil.getAllForms(this.dataEntryParamService, param, formKeys);
        if (DATA_SNAPSHOT_ID != null) {
            jtableContext.getDimensionSet().put("DATASNAPSHOTID", DATA_SNAPSHOT_ID);
        }
        ArrayList<FormData> formAfterFormat = new ArrayList<FormData>();
        PrintFilterInfo printFilterInfo = param.getPrintFilterInfo();
        for (FormData formData : forms) {
            boolean notPrint = false;
            if (param.getExportPdfType() == ExportPdfType.PRINT_PDF && printFilterInfo != null) {
                String taskKey = jtableContext.getTaskKey();
                if (printFilterInfo.getTaskFilterFormList() != null && printFilterInfo.getTaskFilterFormList().size() > 0) {
                    if (printFilterInfo.getTaskFilterFormList().get(taskKey) != null && printFilterInfo.getTaskFilterFormList().get(taskKey).size() > 0 && printFilterInfo.getTaskFilterFormList().get(taskKey).contains(formData.getCode())) {
                        notPrint = true;
                    }
                } else if (printFilterInfo.getTaskNotFilterFormList() != null && printFilterInfo.getTaskNotFilterFormList().size() > 0 && printFilterInfo.getTaskNotFilterFormList().get(taskKey) != null && printFilterInfo.getTaskNotFilterFormList().get(taskKey).size() > 0 && !printFilterInfo.getTaskNotFilterFormList().get(taskKey).contains(formData.getCode())) {
                    notPrint = true;
                }
            }
            if (formData.getFormType().equals("FORM_TYPE_ANALYSISREPORT") || notPrint) continue;
            formAfterFormat.add(formData);
        }
        String formKey = jtableContext.getFormKey();
        boolean label = param.isLabel();
        List<String> tabs = param.getTabs();
        LinkedHashMap<String, Integer> catalogs = new LinkedHashMap<String, Integer>();
        ArrayList<String> pdfLocations = new ArrayList<String>();
        int currentPage = 1;
        if (formAfterFormat.size() > 0) {
            Object formDefine;
            Object writerProperties;
            double begin = 0.01;
            double size = 0.0;
            if (null != asyncTaskMonitor) {
                size = 0.7 / (double)formAfterFormat.size();
            }
            DefaultPageNumberGenerateStrategy strategy = new DefaultPageNumberGenerateStrategy();
            com.itextpdf.kernel.pdf.PdfDocument mergedDoc = null;
            PdfWriter writer = null;
            String outFile = "";
            outFile = param.isPrintCatalog() ? fileLocation + filePathUuid + pdfFileName + "temp.pdf" : fileLocation + filePathUuid + pdfFileName + ".pdf";
            try {
                File ifNotExists = FileUtil.createIfNotExists((String)outFile);
                writerProperties = new WriterProperties();
                writerProperties.useSmartMode();
                writer = new PdfWriter(outFile, (WriterProperties)writerProperties);
                mergedDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                PdfMerger pdfMerger = new PdfMerger(mergedDoc);
                for (int x = 0; x < formAfterFormat.size(); ++x) {
                    FormData form = (FormData)formAfterFormat.get(x);
                    jtableContext.setFormKey(form.getKey());
                    formDefine = this.jtableParamService.getReport(jtableContext.getFormKey(), null);
                    param.setSheetName(DataEntryUtil.getFormTitle((FormData)formDefine));
                    if (form.getKey().equals(formKey)) {
                        param.setTabs(tabs);
                        param.setLabel(label);
                    } else {
                        param.setTabs(null);
                        param.setLabel(false);
                    }
                    byte[] bytes = this.exportPdf(param, (IPageNumberGenerateStrategy)strategy);
                    if (bytes == null) continue;
                    havaData = true;
                    try (ByteArrayInputStream formPdfStream = new ByteArrayInputStream(bytes);){
                        PdfReader reader = new PdfReader((InputStream)formPdfStream);
                        Object object = null;
                        try (com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(reader);){
                            if (param.isPrintCatalog()) {
                                catalogs.put(form.getTitle(), currentPage);
                                currentPage += pdfDocument.getNumberOfPages();
                                FileCreatUtil.createFileWithPatch(form.getKey(), fileLocation + filePathUuid + "formTemp" + BatchExportConsts.SEPARATOR, bytes);
                                pdfLocations.add(fileLocation + filePathUuid + "formTemp" + BatchExportConsts.SEPARATOR + form.getKey());
                            } else {
                                pdfMerger.merge(pdfDocument, 1, pdfDocument.getNumberOfPages());
                                mergedDoc.flushCopiedObjects(pdfDocument);
                            }
                        }
                        catch (Throwable throwable) {
                            object = throwable;
                            throw throwable;
                        }
                        finally {
                            if (reader != null) {
                                if (object != null) {
                                    try {
                                        reader.close();
                                    }
                                    catch (Throwable throwable) {
                                        ((Throwable)object).addSuppressed(throwable);
                                    }
                                } else {
                                    reader.close();
                                }
                            }
                        }
                    }
                    if (null == asyncTaskMonitor) continue;
                    asyncTaskMonitor.progressAndMessage(begin + (double)(x + 1) * size, "");
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            finally {
                if (mergedDoc != null) {
                    mergedDoc.close();
                }
                if (writer != null) {
                    try {
                        writer.close();
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
            if (catalogs.size() > 0) {
                PdfWriter catwriter = new PdfWriter((OutputStream)new FileOutputStream(fileLocation + filePathUuid + pdfFileName + "catlog.pdf"));
                writerProperties = null;
                try (com.itextpdf.kernel.pdf.PdfDocument cataloguePdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(catwriter);){
                    Document document = new Document(cataloguePdfDoc);
                    formDefine = null;
                    try {
                        PdfFont font = this.getPdfFont();
                        document.add((IBlockElement)new Paragraph(new Text("\u76ee\u5f55")).setTextAlignment(TextAlignment.CENTER));
                        Paragraph title = (Paragraph)((Paragraph)((Paragraph)((Paragraph)((Paragraph)new Paragraph(new Text("\u76ee\u5f55")).setTextAlignment(TextAlignment.CENTER)).setFontSize(20.0f)).setBold()).setFont(font)).setMarginBottom(20.0f);
                        document.add((IBlockElement)title);
                        int pagesNum = cataloguePdfDoc.getNumberOfPages();
                        LinkedHashMap adjustedCatalogs = new LinkedHashMap();
                        catalogs.forEach((key, value) -> adjustedCatalogs.put(key, value + pagesNum));
                        for (Map.Entry entry : adjustedCatalogs.entrySet()) {
                            Paragraph p = new Paragraph();
                            p.setFont(font);
                            p.addTabStops(new TabStop[]{new TabStop(540.0f, TabAlignment.RIGHT, (ILineDrawer)new DottedLine())});
                            p.add((String)entry.getKey());
                            p.add((ILeafElement)new Tab());
                            p.add(((Integer)entry.getValue()).toString());
                            document.add((IBlockElement)p);
                        }
                        for (int i = 1; i <= pagesNum; ++i) {
                            Paragraph paragraph = new Paragraph("-" + i + "-");
                            paragraph.setFixedPosition(cataloguePdfDoc.getNumberOfPages(), cataloguePdfDoc.getPage(i).getPageSize().getWidth() / 2.0f, 6.0f, 50.0f);
                            paragraph.setFont(font);
                            document.add((IBlockElement)paragraph);
                        }
                    }
                    catch (Throwable throwable) {
                        formDefine = throwable;
                        throw throwable;
                    }
                    finally {
                        if (document != null) {
                            if (formDefine != null) {
                                try {
                                    document.close();
                                }
                                catch (Throwable throwable) {
                                    ((Throwable)formDefine).addSuppressed(throwable);
                                }
                            } else {
                                document.close();
                            }
                        }
                    }
                }
                catch (Throwable throwable) {
                    writerProperties = throwable;
                    throw throwable;
                }
                finally {
                    if (catwriter != null) {
                        if (writerProperties != null) {
                            try {
                                catwriter.close();
                            }
                            catch (Throwable throwable) {
                                ((Throwable)writerProperties).addSuppressed(throwable);
                            }
                        } else {
                            catwriter.close();
                        }
                    }
                }
                String pdfWithCatLogOutFile = fileLocation + filePathUuid + pdfFileName + ".pdf";
                try (PdfReader catReader = new PdfReader(fileLocation + filePathUuid + pdfFileName + "catlog.pdf");
                     PdfWriter writerWithCatlog = new PdfWriter(pdfWithCatLogOutFile);
                     com.itextpdf.kernel.pdf.PdfDocument outputDoc = new com.itextpdf.kernel.pdf.PdfDocument(writerWithCatlog);){
                    PdfMerger outputMerge = new PdfMerger(outputDoc);
                    com.itextpdf.kernel.pdf.PdfDocument catlogDoc = new com.itextpdf.kernel.pdf.PdfDocument(catReader);
                    Object object = null;
                    try {
                        outputMerge.merge(catlogDoc, 1, catlogDoc.getNumberOfPages());
                        outputDoc.flushCopiedObjects(catlogDoc);
                    }
                    catch (Throwable i) {
                        object = i;
                        throw i;
                    }
                    finally {
                        if (catlogDoc != null) {
                            if (object != null) {
                                try {
                                    catlogDoc.close();
                                }
                                catch (Throwable i) {
                                    ((Throwable)object).addSuppressed(i);
                                }
                            } else {
                                catlogDoc.close();
                            }
                        }
                    }
                    int page = 1;
                    for (String pdfLocation : pdfLocations) {
                        Throwable throwable;
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        try (PdfReader formPdfReader = new PdfReader(pdfLocation);){
                            throwable = null;
                            try (PdfWriter tempWriter = new PdfWriter((OutputStream)byteArrayOutputStream);
                                 com.itextpdf.kernel.pdf.PdfDocument formPdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(formPdfReader, tempWriter);){
                                int numberOfPages = formPdfDoc.getNumberOfPages();
                                for (int i = 0; i < numberOfPages; ++i) {
                                    PdfPage formPage = formPdfDoc.getPage(i + 1);
                                    Rectangle pageSize = formPage.getPageSize();
                                    float x = pageSize.getWidth() / 2.0f;
                                    float y = pageSize.getBottom() + 20.0f;
                                    PdfCanvas pdfCanvas = new PdfCanvas(formPage);
                                    try (Canvas canvas = new Canvas(pdfCanvas, pageSize);){
                                        String pageNumberText = String.format("- %d -", i + 1 + page);
                                        Paragraph p = (Paragraph)((Paragraph)((Paragraph)((Paragraph)((Paragraph)new Paragraph(pageNumberText).setFont(this.getPdfFont())).setFontSize(10.0f)).setFontColor(ColorConstants.BLACK)).setTextAlignment(TextAlignment.CENTER)).setVerticalAlignment(VerticalAlignment.MIDDLE);
                                        canvas.showTextAligned(p, x, y, i + 1, TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0.0f);
                                        continue;
                                    }
                                }
                                page += numberOfPages;
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                        }
                        PdfReader modifiedReader = new PdfReader((InputStream)new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                        var47_95 = null;
                        try {
                            com.itextpdf.kernel.pdf.PdfDocument modifiedPdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(modifiedReader);
                            throwable = null;
                            try {
                                outputMerge.merge(modifiedPdfDoc, 1, modifiedPdfDoc.getNumberOfPages());
                                outputDoc.flushCopiedObjects(modifiedPdfDoc);
                            }
                            catch (Throwable throwable3) {
                                throwable = throwable3;
                                throw throwable3;
                            }
                            finally {
                                if (modifiedPdfDoc == null) continue;
                                if (throwable != null) {
                                    try {
                                        modifiedPdfDoc.close();
                                    }
                                    catch (Throwable throwable4) {
                                        throwable.addSuppressed(throwable4);
                                    }
                                    continue;
                                }
                                modifiedPdfDoc.close();
                            }
                        }
                        catch (Throwable throwable5) {
                            var47_95 = throwable5;
                            throw throwable5;
                        }
                        finally {
                            if (modifiedReader == null) continue;
                            if (var47_95 != null) {
                                try {
                                    modifiedReader.close();
                                }
                                catch (Throwable throwable6) {
                                    var47_95.addSuppressed(throwable6);
                                }
                                continue;
                            }
                            modifiedReader.close();
                        }
                    }
                }
            }
        }
        if (havaData) {
            ExportData exportData = new ExportData(pdfFileName, null);
            if (param.getType().equals("EXPORT_OFD")) {
                SpireHelper.loadSpireLicence();
                PdfDocument pdfDoc = new PdfDocument();
                pdfDoc.loadFromFile(fileLocation + filePathUuid + pdfFileName + ".pdf");
                pdfDoc.saveToFile(fileLocation + filePathUuid + pdfFileName + ".ofd", FileFormat.OFD);
                exportData.setFileLocation(fileLocation + filePathUuid + pdfFileName + ".ofd");
            } else {
                exportData.setFileLocation(fileLocation + filePathUuid + pdfFileName + ".pdf");
            }
            return exportData;
        }
        return new ExportData(true);
    }

    @Nullable
    private PdfFont getPdfFont() {
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont((String)"STSongStd-Light", (String)"UniGB-UCS2-H");
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return font;
    }

    public byte[] exportPdf(ExportParam param, IPageNumberGenerateStrategy strategy) {
        JtableContext jtableContext = param.getContext();
        if (param.getExportPdfType() == ExportPdfType.PRINT_PDF && this.iExternalPdfExportor != null) {
            try {
                logger.info("\u8c03\u7528\u7b7e\u7ae0\u6253\u5370\u63a5\u53e3\u83b7\u53d6\u7b7e\u7ae0\u6587\u4ef6\u3002");
                byte[] bytes = this.iExternalPdfExportor.export(param);
                if (bytes != null) {
                    return bytes;
                }
            }
            catch (Exception e) {
                logger.error("\u8c03\u7528\u7b7e\u7ae0\u6253\u5370\u63a5\u53e3\u5f02\u5e38\uff01\uff01\uff01");
                logger.error("\u8c03\u7528\u7b7e\u7ae0\u6253\u5370\u63a5\u53e3\u53c2\u6570:" + new Gson().toJson((Object)param));
                throw new RuntimeException(e);
            }
        }
        if (null == param.getPrintSchemeKey() || "".equals(param.getPrintSchemeKey())) {
            List printSchemes = null;
            try {
                printSchemes = this.printRunTimeController.getAllPrintSchemeByFormScheme(jtableContext.getFormSchemeKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (null == printSchemes || printSchemes.size() == 0) {
                logger.error("=\u6ca1\u6709\u83b7\u53d6\u5230\u6253\u5370\u65b9\u6848=:\u6253\u5370\u65b9\u6848:" + jtableContext.getFormSchemeKey());
            } else {
                param.setPrintSchemeKey(((PrintTemplateSchemeDefine)printSchemes.get(0)).getKey());
            }
        }
        ITemplateDocument documentTemplateObject = null;
        try {
            if (param.getPrintSchemeKey() != null) {
                PrintTemplateDefine printTemPlate = this.printRunTimeController.queryPrintTemplateDefineBySchemeAndForm(param.getPrintSchemeKey(), jtableContext.getFormKey());
                if (null == printTemPlate) {
                    documentTemplateObject = this.printTemplateService.loadNewRuntimeTempDoc(param.getPrintSchemeKey(), jtableContext.getFormKey());
                } else {
                    ITemplateObjectFactory factory = GraphicalFactoryManager.getTemplateObjectFactory((String)"REPORT_PRINT_NATURE");
                    documentTemplateObject = (ITemplateDocument)SerializeUtil.deserialize((String)new String(printTemPlate.getTemplateData()), (ITemplateObjectFactory)factory);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null == documentTemplateObject) {
            try {
                documentTemplateObject = this.printTemplateService.loadNewRuntimeTempDoc(param.getPrintSchemeKey(), jtableContext.getFormKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        documentTemplateObject.setNature("REPORT_PRINT_NATURE");
        Iterator iterator = documentTemplateObject.getPage(0).iterator();
        if (param.isOnlyStyle()) {
            PDFPrintUtil2.printPDF((ITemplateDocument)documentTemplateObject, (OutputStream)out);
        } else {
            IPrintParamBase iPrintParamBase = this.getPrintParamPdf(param);
            PrintIPaginateInteractor interactor = new PrintIPaginateInteractor(iPrintParamBase);
            interactor.setPageNumberGenerateStrategy(strategy);
            PDFPrintUtil2.printPDF(documentTemplateObject, (IPaginateInteractor)interactor, (IPaintInteractor)new SimplePaintInteractor(), out, (IProcessMonitor)new SimpleProcessMonitor(new PrintStream(new Log4jPrintStream(logger)), new PrintStream(new Log4jPrintStream(logger)), new PrintStream(new Log4jPrintStream(logger))));
            if (iPrintParamBase.isEmptyTable()) {
                return null;
            }
        }
        return out.toByteArray();
    }

    /*
     * Exception decompiling
     */
    private byte[] concatPDFs(List<ExportData> pdfList) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private IPrintParamBase getPrintParamPdf(ExportParam param) {
        JtableContext jtableContext = param.getContext();
        PrintParam pdf = new PrintParam();
        pdf.setContext(jtableContext);
        pdf.setTabs(param.getTabs());
        pdf.setLabel(param.isLabel());
        pdf.setExportEmptyTable(param.isExportEmptyTable());
        pdf.setExportZero(param.isExportZero());
        boolean secretLevelEnable = this.iSecretLevelService.secretLevelEnable(param.getContext().getTaskKey());
        if (secretLevelEnable) {
            String secretLevelTitle = this.iSecretLevelService.getSecretLevel(jtableContext).getSecretLevelItem().getTitle();
            pdf.setSecretLevelTitle(secretLevelTitle);
        }
        if (param.isPrintCatalog()) {
            pdf.setPrintPageNum(false);
        } else {
            pdf.setPrintPageNum(true);
        }
        return pdf;
    }
}

