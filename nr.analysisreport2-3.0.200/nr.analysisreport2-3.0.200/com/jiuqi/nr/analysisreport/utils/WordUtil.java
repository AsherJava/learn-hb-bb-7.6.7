/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.itextpdf.io.image.ImageData
 *  com.itextpdf.io.image.ImageDataFactory
 *  com.itextpdf.kernel.colors.Color
 *  com.itextpdf.kernel.colors.DeviceRgb
 *  com.itextpdf.kernel.font.PdfFont
 *  com.itextpdf.kernel.font.PdfFontFactory
 *  com.itextpdf.kernel.geom.Rectangle
 *  com.itextpdf.kernel.pdf.PdfDocument
 *  com.itextpdf.kernel.pdf.PdfPage
 *  com.itextpdf.kernel.pdf.PdfReader
 *  com.itextpdf.kernel.pdf.PdfWriter
 *  com.itextpdf.kernel.pdf.canvas.PdfCanvas
 *  com.itextpdf.kernel.pdf.extgstate.PdfExtGState
 *  com.itextpdf.layout.Canvas
 *  com.itextpdf.layout.properties.TextAlignment
 *  com.itextpdf.layout.properties.VerticalAlignment
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.authz2.service.SecurityLevelService
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.util.Base64
 *  io.netty.util.internal.StringUtil
 *  org.apache.commons.lang3.StringUtils
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.Node
 *  org.jsoup.nodes.TextNode
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.analysisreport.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.authz2.service.SecurityLevelService;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportChapterDefine;
import com.jiuqi.nr.analysisreport.chapter.service.IChapterService;
import com.jiuqi.nr.analysisreport.common.Consts;
import com.jiuqi.nr.analysisreport.facade.AnalyBigdataDefine;
import com.jiuqi.nr.analysisreport.facade.AnalysisReportDefine;
import com.jiuqi.nr.analysisreport.facade.ExportSetting;
import com.jiuqi.nr.analysisreport.helper.AnalysisHelper;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.GenerateContext;
import com.jiuqi.nr.analysisreport.internal.service.AnalyBigDataService;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.support.ReportExprParseSupport;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFHeaderFooterPolicy;
import com.jiuqi.nr.analysisreport.utils.DocumentSize;
import com.jiuqi.nr.analysisreport.utils.HeadFootNoUtil;
import com.jiuqi.nr.analysisreport.utils.IntegerParser;
import com.jiuqi.nr.analysisreport.utils.OfficeUtil;
import com.jiuqi.nr.analysisreport.utils.PdfSetHeaderFooterNoUtil;
import com.jiuqi.nr.analysisreport.utils.TableOfContents;
import com.jiuqi.nr.analysisreport.utils.WordStyle;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.analysisreport.vo.print.PrintStyle;
import com.jiuqi.nr.analysisreport.vo.print.ReportPrintSettingVO;
import com.jiuqi.nr.analysisreport.vo.wordtable.WordTableCell;
import com.jiuqi.nr.analysisreport.vo.wordtable.WordTableContext;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.JsonUtil;
import io.netty.util.internal.StringUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPBdr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSettings;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBrType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJcTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordUtil {
    private static Logger logger = LoggerFactory.getLogger(WordUtil.class);
    private HashMap<String, EntityViewDefine> masterViews;
    private AnalysisReportDefine reportDefine;
    private boolean isPDF;
    private Double[] DPI = new Double[2];
    private Integer recordPageBeforText = 0;
    private boolean currentParagraphIsDelete = false;
    private static final float DEF_LINE_HEIGHT = 1.5f;
    private String securityTitle = "";
    private static final String ERROR_GETCHARTIMAGE = "ERROR_GETCHARTIMAGE";
    private String textAlignRight = "text-align: right;";
    private String textAlignCenter = "text-align: center;";
    private String textAlign = "text-align";
    private String marginRight = "margin:0 0 0 calc(100% - 530px);";
    private String marginCenter = "margin:0 auto;";
    private String style = "style";
    private ExecutorContext executorContext;
    private Integer tableIndex = 0;
    private List<TableOfContents> tocConts = new ArrayList<TableOfContents>();
    private AnalysisHelper analysisHelper;
    private AnalyBigDataService analyBigDataService = (AnalyBigDataService)BeanUtil.getBean(AnalyBigDataService.class);
    private IDataDefinitionRuntimeController npRuntimeController;
    private IAnalysisReportEntityService analysisReportEntityService;
    private ISecretLevelService secretLevelService;
    private IChapterService chapterService;
    private SecurityLevelService securityLevelService;
    private ReportExprParseSupport reportExprParseSupport;
    private ReentrantLock reentrantLock;
    private String matcher_border_width = "(medium|initial)";
    private String matcher_border_style = "(none|solid|double|dashed|dotted)";

    public WordUtil() {
        this.analysisHelper = (AnalysisHelper)BeanUtil.getBean(AnalysisHelper.class);
        this.npRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.analysisReportEntityService = (IAnalysisReportEntityService)BeanUtil.getBean(IAnalysisReportEntityService.class);
        this.secretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        this.securityLevelService = (SecurityLevelService)BeanUtil.getBean(SecurityLevelService.class);
        this.reportExprParseSupport = (ReportExprParseSupport)BeanUtil.getBean(ReportExprParseSupport.class);
        this.chapterService = (IChapterService)BeanUtil.getBean(IChapterService.class);
        this.reentrantLock = new ReentrantLock();
    }

    private String getContent(String arcKey, String versionKey) throws Exception {
        boolean isMultiVer = !StringUtils.isEmpty((CharSequence)versionKey);
        String fullContent = "";
        if (StringUtils.isEmpty((CharSequence)arcKey)) {
            if (!isMultiVer) {
                fullContent = this.reportDefine.getData();
            } else {
                AnalyBigdataDefine analyBigdataDefine = this.analyBigDataService.getBykey(versionKey);
                fullContent = analyBigdataDefine.getBigData();
            }
        } else if (!isMultiVer) {
            fullContent = this.chapterService.getChapterById(arcKey).getArcData();
        } else {
            AnalyBigdataDefine arcBigData = this.analyBigDataService.getArcBigData(versionKey, arcKey);
            fullContent = arcBigData.getBigData();
        }
        if (fullContent == null) {
            return "";
        }
        fullContent = isMultiVer ? fullContent : new String(com.jiuqi.util.Base64.base64ToByteArray((String)fullContent));
        return fullContent;
    }

    public HashMap<String, EntityViewDefine> getMasterViews(ReportGeneratorVO reportGeneratorVO) {
        if (this.masterViews == null) {
            this.masterViews = new HashMap();
            for (ReportBaseVO.UnitDim unitDim : reportGeneratorVO.getChooseUnits()) {
                if (StringUtils.isEmpty((CharSequence)unitDim.getViewKey())) continue;
                String entityViewKey = unitDim.getViewKey();
                EntityViewDefine entityViewDefine = this.analysisReportEntityService.buildEntityViewDefine(entityViewKey);
                this.masterViews.put(entityViewKey, entityViewDefine);
            }
        }
        return this.masterViews;
    }

    private void formulaVarToMap(ReportGeneratorVO reportGeneratorVO, String genDataType, GenerateContext generateContext) throws Exception {
        ReportVariableParseVO reportVariableParseVO = this.buildReportVariableParseVo(reportGeneratorVO);
        this.reportExprParseSupport.support(reportVariableParseVO, this.reportDefine);
    }

    private ReportVariableParseVO buildReportVariableParseVo(ReportGeneratorVO reportGeneratorVO) {
        ReportVariableParseVO reportVariableParseVO = new ReportVariableParseVO();
        ReportBaseVO reportBaseVO = new ReportBaseVO();
        reportBaseVO.setKey(reportGeneratorVO.getKey());
        reportBaseVO.setChooseUnits(reportGeneratorVO.getChooseUnits());
        reportBaseVO.setPeriod(reportGeneratorVO.getPeriod());
        reportVariableParseVO.setReportBaseVO(reportBaseVO);
        reportVariableParseVO.setExt(reportGeneratorVO.getExt());
        reportVariableParseVO.setContent(reportGeneratorVO.getContents());
        return reportVariableParseVO;
    }

    public String getTempAllContent(String modelKey, String versionKey) throws Exception {
        List<ReportChapterDefine> chapterDefinesList = this.chapterService.queryChapterByModelId(modelKey);
        StringBuilder customContent = new StringBuilder();
        if (StringUtils.isNotEmpty((CharSequence)versionKey)) {
            if (!CollectionUtils.isEmpty(chapterDefinesList)) {
                List<AnalyBigdataDefine> list = this.analyBigDataService.list(versionKey);
                for (AnalyBigdataDefine analyBigdataDefine : list) {
                    customContent.append(analyBigdataDefine.getBigData());
                }
            } else {
                AnalyBigdataDefine bigdata = this.analyBigDataService.getBykey(versionKey);
                customContent.append(bigdata.getBigData());
            }
        } else if (!CollectionUtils.isEmpty(chapterDefinesList)) {
            for (ReportChapterDefine reportChapterDefine : chapterDefinesList) {
                if (!StringUtils.isNotEmpty((CharSequence)reportChapterDefine.getArcData())) continue;
                customContent.append(new String(com.jiuqi.util.Base64.base64ToByteArray((String)reportChapterDefine.getArcData())));
            }
        } else {
            AnalysisReportDefine reportDefine = this.analysisHelper.getListByKey(modelKey);
            customContent.append(new String(com.jiuqi.util.Base64.base64ToByteArray((String)reportDefine.getData())));
        }
        return customContent.toString();
    }

    public CustomXWPFDocument createWord(ReportGeneratorVO reportGeneratorVO, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        this.tableIndex = 0;
        this.tocConts.clear();
        if (StringUtils.isEmpty((CharSequence)reportGeneratorVO.getContents())) {
            String tempAllContent = this.getTempAllContent(reportGeneratorVO.getKey(), reportGeneratorVO.getVersionKey());
            reportGeneratorVO.setContents(tempAllContent);
        }
        this.setDPI(reportGeneratorVO.getxDPI(), reportGeneratorVO.getyDPI());
        reportGeneratorVO.getExt().put("GEN_DATA_TYPE", "GEN_WORD_DATA");
        this.parseVariable(reportGeneratorVO);
        String printData = this.reportDefine.getPrintData();
        ObjectMapper objectMapper = new ObjectMapper();
        ReportPrintSettingVO reportPrintSettingVO = (ReportPrintSettingVO)objectMapper.readValue(printData, ReportPrintSettingVO.class);
        CustomXWPFDocument docxDocument = this.loadCustomDocxDocument();
        reportPrintSettingVO.fomatPrintStyle();
        this.setDocHeadFootNo(docxDocument, reportPrintSettingVO);
        PrintStyle printStyle = reportPrintSettingVO.getTemplate();
        this.setDocxDocmentStyle(docxDocument, printStyle);
        Document doc = AnaUtils.parseBodyFragment(reportGeneratorVO.getContents());
        Element body = doc.body();
        this.removeEmptyElement(body);
        ExportSetting exportSetting = new ExportSetting();
        exportSetting.setOriginNumber(reportPrintSettingVO.getPageNumber().getOriginNumber());
        exportSetting.setPrintStyle(reportPrintSettingVO.getTemplate());
        Elements es = body.getAllElements();
        boolean tag = false;
        double index = 0.0;
        double progress = 0.01;
        for (Element e : es) {
            try {
                double d;
                if (!tag) {
                    tag = true;
                    continue;
                }
                if (e.parent() == body && e.tagName().equals("table")) {
                    this.fillTable(docxDocument, e, exportSetting);
                } else if (e.parent() == body && !e.tagName().equals("table")) {
                    if ("p,h1,h2,h3,h4,div".indexOf(e.tagName()) >= 0) {
                        this.createXWPFParagraph(docxDocument, e, exportSetting);
                    } else {
                        Elements childNodes = e.children();
                        for (Element child : childNodes) {
                            if ("table".equals(child.tagName())) {
                                this.fillTable(docxDocument, child, exportSetting);
                                continue;
                            }
                            this.createXWPFParagraph(docxDocument, child, exportSetting);
                        }
                    }
                }
                if (asyncTaskMonitor == null) continue;
                index += 1.0;
                if (!(d / ((double)es.size() * 1.15) - progress > 0.01)) continue;
                progress = index / ((double)es.size() * 1.15);
                progress = progress > 0.9 ? 0.9 : progress;
                asyncTaskMonitor.progressAndMessage(progress + 0.05, "\u6b63\u5728\u5bfc\u51fa\u5206\u6790\u62a5\u544a");
            }
            catch (Exception ex) {
                AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u5bfc\u51fa\u5f02\u5e38: " + e.outerHtml(), ex);
            }
        }
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.progressAndMessage(0.95, "\u6b63\u5728\u751f\u6210\u76ee\u5f55");
        }
        if (this.tocConts.size() > 0) {
            Integer initPageNum = exportSetting.getInitPageNum();
            Map<String, Integer> pageNums = exportSetting.getPageNums();
            if (this.isPDF) {
                CustomXWPFDocument cloneDocument = new CustomXWPFDocument();
                OfficeUtil.getCTSectPr(cloneDocument);
                this.setDocxDocmentStyle(cloneDocument, printStyle);
                TableOfContents tocCont = TableOfContents.CreateTableOfContents(cloneDocument, true, this.isPDF);
                tocCont.createTOC(pageNums, docxDocument.getParagraphs(), null);
                int tocPageNum = TableOfContents.getPDFPagenum(cloneDocument);
                initPageNum = initPageNum + tocPageNum;
                this.recordPageBeforText = -initPageNum.intValue();
            }
            for (String bookmarkid : pageNums.keySet()) {
                if (!pageNums.containsKey(bookmarkid)) continue;
                pageNums.put(bookmarkid, pageNums.get(bookmarkid) - initPageNum);
            }
            for (TableOfContents tocCont : this.tocConts) {
                tocCont.CreateTOC(pageNums);
            }
        }
        if (!this.isPDF && printStyle.getWatermark() != null) {
            this.createWaterMark(docxDocument, printStyle.getWatermark());
        }
        if (asyncTaskMonitor != null) {
            asyncTaskMonitor.finish("\u5206\u6790\u62a5\u544a\u5bfc\u51fa\u5b8c\u6210\u3002", (Object)JsonUtil.objectToJson((Object)new BatchReturnInfo()));
        }
        return docxDocument;
    }

    private void createWaterMark(CustomXWPFDocument docxDocument, PrintStyle.Watermark watermark) {
        try {
            if ("picture".equals(watermark.getType())) {
                new CustomXWPFHeaderFooterPolicy(docxDocument).createWatermark(watermark);
            } else if ("word".equals(watermark.getType())) {
                String content = StringUtils.isEmpty((CharSequence)watermark.getContent()) ? "" : watermark.getContent();
                content = this.getWatermarkContent(content);
                watermark.setContent(content);
                new CustomXWPFHeaderFooterPolicy(docxDocument).createWatermark(watermark);
            }
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u6c34\u5370\u8bbe\u7f6e\u5931\u8d25: " + e.toString(), e);
            logger.error("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", (Object)("\u5206\u6790\u62a5\u544a\u6c34\u5370\u8bbe\u7f6e\u5931\u8d25: " + e.getMessage()), (Object)e);
        }
    }

    private void setDocHeadFootNo(CustomXWPFDocument docxDocument, ReportPrintSettingVO reportPrintSettingVO) {
        CTSettings settings = docxDocument.getCTSettings(docxDocument);
        settings.addNewEvenAndOddHeaders();
        CTSectPr sectPr = OfficeUtil.getCTSectPr(docxDocument);
        CTPageNumber numType = sectPr.addNewPgNumType();
        numType.setFmt(STNumberFormat.DECIMAL);
        Map<String, String> noSettings = OfficeUtil.createPageNo(reportPrintSettingVO);
        numType.setStart(new BigInteger(reportPrintSettingVO.getPageNumber().getOriginNumber()));
        CustomXWPFHeaderFooterPolicy headerFooterPolicy = new CustomXWPFHeaderFooterPolicy(docxDocument, sectPr);
        if (!this.isPDF) {
            Map<String, String> headerSettings = OfficeUtil.createDefaultHeadFoot(reportPrintSettingVO.getPageHeader(), true);
            Map<String, String> footerSettings = OfficeUtil.createDefaultHeadFoot(reportPrintSettingVO.getPageFooter(), false);
            HeadFootNoUtil.createHeadFootNoPolicy(headerFooterPolicy, headerSettings, footerSettings, noSettings);
        }
    }

    private void setDocxDocmentStyle(CustomXWPFDocument docxDocument, PrintStyle printStyle) {
        this.setDocTitleStyle(docxDocument, printStyle.getLineHeight());
        this.setDocMargin(docxDocument, printStyle);
        this.setDocPaperStyle(docxDocument, printStyle);
    }

    private void setDocTitleStyle(CustomXWPFDocument docxDocument, String lineHeight) {
        float titleLingHeight = Float.parseFloat(lineHeight);
        try {
            OfficeUtil.addCustomHeadingStyle(docxDocument, "Normal", 0, titleLingHeight);
            OfficeUtil.addCustomHeadingStyle(docxDocument, "heading 1", 1, titleLingHeight);
            OfficeUtil.addCustomHeadingStyle(docxDocument, "heading 2", 2, titleLingHeight);
            OfficeUtil.addCustomHeadingStyle(docxDocument, "heading 3", 3, titleLingHeight);
            OfficeUtil.addCustomHeadingStyle(docxDocument, "heading 4", 4, titleLingHeight);
        }
        catch (IOException | XmlException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDocMargin(CustomXWPFDocument docxDocument, PrintStyle printStyle) {
        this.setDocumentMargin(docxDocument, printStyle.getMarginTop(), printStyle.getMarginLeft(), printStyle.getMarginBottom(), printStyle.getMarginLeft());
    }

    private void setDocPaperStyle(CustomXWPFDocument docxDocument, PrintStyle printStyle) {
        STPageOrientation.Enum orientationType;
        DocumentSize dSize = StringUtil.isNullOrEmpty((String)printStyle.getPaperType()) ? null : DocumentSize.getDocumentSize(printStyle.getPaperType());
        STPageOrientation.Enum enum_ = orientationType = printStyle.getOrientation() != null && printStyle.getOrientation() == 1 ? STPageOrientation.LANDSCAPE : STPageOrientation.PORTRAIT;
        if (!StringUtil.isNullOrEmpty((String)printStyle.getPaperType()) && dSize != null) {
            this.setDocumentSize(docxDocument, dSize.getWidth(), dSize.getHeight(), orientationType);
        } else if (!StringUtil.isNullOrEmpty((String)printStyle.getPaperWidth()) && !StringUtil.isNullOrEmpty((String)printStyle.getPaperHeight())) {
            this.setDocumentSize(docxDocument, Long.valueOf(printStyle.getPaperWidth()), Long.valueOf(printStyle.getPaperHeight()), orientationType);
        } else {
            this.setDocumentSize(docxDocument, DocumentSize.A4.getWidth(), DocumentSize.A4.getHeight(), orientationType);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CustomXWPFDocument loadCustomDocxDocument() {
        CustomXWPFDocument docxDocument = null;
        InputStream stream = null;
        try {
            String templatePath = ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "template" + File.separator + "template.docx";
            PathUtils.validatePathManipulation((String)templatePath);
            if (!new File(templatePath).exists()) {
                throw new FileNotFoundException();
            }
            stream = this.getClass().getResourceAsStream(templatePath);
            docxDocument = new CustomXWPFDocument(stream);
        }
        catch (Exception e) {
            docxDocument = new CustomXWPFDocument();
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return docxDocument;
    }

    public String createHtml(ReportGeneratorVO reportGeneratorVO, String ... contents) throws Exception {
        this.tableIndex = 0;
        reportGeneratorVO.getExt().put("GEN_DATA_TYPE", "GEN_HTML_DATA");
        this.parseVariable(reportGeneratorVO);
        Document doc = AnaUtils.parseBodyFragment(reportGeneratorVO.getContents());
        Element body = doc.body();
        body.prepend(this.createSecurityP());
        String showHtml = doc.body().children().toString();
        if (showHtml.contains("ANALYSISREPORT_LINE_BREAKS")) {
            showHtml = showHtml.replace("ANALYSISREPORT_LINE_BREAKS", "<br>");
        }
        return showHtml;
    }

    private void parseVariable(ReportGeneratorVO reportGeneratorVO) throws Exception {
        this.prepareParseVariable(reportGeneratorVO);
        this.doParseVariable(reportGeneratorVO);
    }

    private void doParseVariable(ReportGeneratorVO reportGeneratorVO) throws InterruptedException {
        ReportVariableParseVO reportVariableParseVO = this.buildReportVariableParseVo(reportGeneratorVO);
        this.reportExprParseSupport.support(reportVariableParseVO, this.reportDefine);
        reportGeneratorVO.setContents(reportVariableParseVO.getContent());
    }

    private void prepareParseVariable(ReportGeneratorVO reportGeneratorVO) throws Exception {
        this.reportDefine = this.analysisHelper.getListByKey(reportGeneratorVO.getKey());
        if (StringUtils.isEmpty((CharSequence)reportGeneratorVO.getContents())) {
            String contents = this.getContent(reportGeneratorVO.getArcKey(), reportGeneratorVO.getVersionKey());
            if (contents == null) {
                throw new Exception("\u6a21\u677f\u5185\u5bb9\u4e3a\u7a7a\uff01\u8bf7\u586b\u5199\u6a21\u677f\u5185\u5bb9");
            }
            reportGeneratorVO.setContents(contents);
        }
        reportGeneratorVO.setContents(this.formatReport(reportGeneratorVO.getContents()));
    }

    private String formatReport(String contents) {
        Document doc = AnaUtils.parseBodyFragment(contents);
        Element body = doc.body();
        Elements allElements = body.children();
        for (Element ele : allElements) {
            if (!ele.tagName().equals("p") || ele.childrenSize() <= 1 || !ele.children().hasClass("quickFormVar") && !ele.children().hasClass("quickFormVar_Local")) continue;
            List nodes = ele.childNodes();
            for (int i = 0; i < nodes.size(); ++i) {
                Node node = (Node)nodes.get(i);
                if (node.nodeName().equals("br") && i > 0 && (((Node)nodes.get(i - 1)).nodeName().equals("img") || nodes.get(i - 1) instanceof TextNode)) continue;
                String outHtml = null;
                outHtml = StringUtils.isNotEmpty((CharSequence)ele.attr("style")) && ele.attr("style").contains(this.textAlign) ? "<p style=\"" + ele.attr("style") + "\">" + node.outerHtml() + "</p>" : "<p >" + node.outerHtml() + "</p>";
                ele.before(outHtml);
            }
            ele.remove();
        }
        return body.html();
    }

    private void removeEmptyElement(Element body) {
        if (body == null || body.getAllElements() == null) {
            return;
        }
        Elements elements = body.getAllElements();
        for (Element element : elements) {
            if (!this.isEmptyElement(element)) continue;
            switch (element.tagName()) {
                case "p": {
                    boolean isTableSibling;
                    Element nextElementSibling = element.nextElementSibling();
                    Element previousElementSibling = element.previousElementSibling();
                    boolean bl = isTableSibling = nextElementSibling != null && previousElementSibling != null && nextElementSibling.tagName().equals("table") && previousElementSibling.tagName().equals("table");
                    if (isTableSibling) break;
                    element.remove();
                    break;
                }
                case "span": {
                    Element parent = element.parent();
                    if (parent.childNodeSize() != 1 || !parent.tagName().equals("p")) break;
                    parent.remove();
                    break;
                }
            }
        }
    }

    private boolean isEmptyElement(Element element) {
        return StringUtils.isEmpty((CharSequence)element.text()) && element.childrenSize() == 0;
    }

    private String createSecurityP() {
        String str = "";
        if (this.securityLevelService.isSecurityLevelEnabled()) {
            this.securityTitle = this.secretLevelService.getSecretLevelItem(this.reportDefine.getSecurityLevel()).getTitle();
            str = "<p><span style=color:red>" + this.securityTitle + "</span></p>";
        }
        return str;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createPDF(XWPFDocument word, ByteArrayOutputStream response) {
        String path = Consts.JVM_TEMP + File.separator + UUID.randomUUID().toString() + ".pdf";
        try (FileOutputStream out = new FileOutputStream(path);){
            byte[] pdfDoc = WordUtil.convertToPdf(word);
            ((OutputStream)out).write(pdfDoc);
            ObjectMapper objectMapper = new ObjectMapper();
            ReportPrintSettingVO reportPrintSettingVO = (ReportPrintSettingVO)objectMapper.readValue(this.reportDefine.getPrintData(), ReportPrintSettingVO.class);
            PrintStyle printStyle = reportPrintSettingVO.getTemplate();
            PrintStyle.Watermark watermark = printStyle.getWatermark();
            PdfSetHeaderFooterNoUtil.createPage(path, response, reportPrintSettingVO, this.recordPageBeforText);
            if (watermark != null && !"none".equals(watermark.getType())) {
                this.applyWatermark(response, watermark);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u751f\u6210PDF\u6587\u4ef6\u5931\u8d25: " + e.toString(), e);
        }
        finally {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void applyWatermark(ByteArrayOutputStream tempOut, PrintStyle.Watermark watermark) throws IOException {
        PdfDocument pdfDoc = null;
        try {
            PdfReader reader = new PdfReader((InputStream)new ByteArrayInputStream(tempOut.toByteArray()));
            ByteArrayOutputStream newOut = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter((OutputStream)newOut);
            pdfDoc = new PdfDocument(reader, writer);
            if ("picture".equals(watermark.getType())) {
                this.applyPictureWatermark(watermark, pdfDoc);
            } else if ("word".equals(watermark.getType())) {
                this.applyTextWatermark(watermark, pdfDoc);
            }
            pdfDoc.close();
            tempOut.reset();
            tempOut.write(newOut.toByteArray());
        }
        catch (Exception e) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u6c34\u5370\u8bbe\u7f6e\u5931\u8d25: " + e.toString(), e);
        }
        finally {
            if (pdfDoc != null && !pdfDoc.isClosed()) {
                pdfDoc.close();
            }
        }
    }

    private void applyPictureWatermark(PrintStyle.Watermark watermark, PdfDocument pdfDoc) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(watermark.getContent().replaceFirst("data:image/.*?;base64,", ""));
        ImageData imageData = ImageDataFactory.create((byte[])imageBytes);
        float opacity = watermark.getErosion() != false ? 0.3f : 0.7f;
        String zoomSetting = watermark.getZoom();
        boolean isAutoZoom = "auto".equals(zoomSetting);
        PdfExtGState gs = new PdfExtGState();
        gs.setFillOpacity(opacity);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); ++i) {
            PdfPage page = pdfDoc.getPage(i);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas canvas = new PdfCanvas(page);
            float scale = isAutoZoom ? pageSize.getWidth() / imageData.getWidth() : Float.parseFloat(zoomSetting.replace("%", "")) / 100.0f;
            float watermarkWidth = imageData.getWidth() * scale;
            float watermarkHeight = imageData.getHeight() * scale;
            float posX = isAutoZoom ? 0.0f : (pageSize.getWidth() - watermarkWidth) / 2.0f;
            float posY = (pageSize.getHeight() - watermarkHeight) / 2.0f;
            canvas.saveState();
            canvas.setExtGState(gs);
            canvas.addImageFittedIntoRectangle(imageData, new Rectangle(posX, posY, watermarkWidth, watermarkHeight), false);
            canvas.restoreState();
        }
    }

    private void applyTextWatermark(PrintStyle.Watermark watermark, PdfDocument pdfDoc) throws Exception {
        PdfFont font;
        String content = this.getWatermarkContent(watermark.getContent());
        String fontFamily = watermark.getFontfamily();
        float fontSize = Float.parseFloat(watermark.getFontsize());
        float angle = "level".equals(watermark.getFormat()) ? 0.0f : 45.0f;
        float opacity = watermark.getTranslucent() != false ? 0.3f : 1.0f;
        try {
            font = PdfFontFactory.createFont((String)fontFamily, (String)"Identity-H", (PdfDocument)pdfDoc);
        }
        catch (Exception e) {
            font = PdfFontFactory.createFont((String)"STSongStd-Light", (String)"UniGB-UCS2-H", (PdfDocument)pdfDoc);
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5b57\u4f53\u52a0\u8f7d\u5931\u8d25: " + fontFamily, e);
        }
        DeviceRgb color = null;
        if (watermark.getColor() != null && watermark.getColor().matches("#[0-9A-Fa-f]{6}")) {
            String hex = watermark.getColor().substring(1);
            int r = Integer.parseInt(hex.substring(0, 2), 16);
            int g = Integer.parseInt(hex.substring(2, 4), 16);
            int b = Integer.parseInt(hex.substring(4, 6), 16);
            color = new DeviceRgb(r, g, b);
        }
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); ++i) {
            PdfPage page = pdfDoc.getPage(i);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            PdfExtGState gs = new PdfExtGState();
            gs.setFillOpacity(opacity);
            pdfCanvas.setExtGState(gs);
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            ((Canvas)canvas.setFont(font)).setFontSize(fontSize);
            if (color != null) {
                canvas.setFontColor((com.itextpdf.kernel.colors.Color)color);
            }
            canvas.showTextAligned(content, pageSize.getWidth() / 2.0f, pageSize.getHeight() / 2.0f, TextAlignment.CENTER, VerticalAlignment.MIDDLE, angle);
            canvas.close();
        }
    }

    /*
     * Exception decompiling
     */
    private static byte[] convertToPdf(XWPFDocument document) throws IOException {
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

    public static void setParagraphWrap(XWPFParagraph paragraph, boolean allow) {
        if (allow) {
            CTP ctp = paragraph.getCTP();
            CTPPr ctpPr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
            CTOnOff ctOnOff = ctpPr.isSetWordWrap() ? ctpPr.getWordWrap() : ctpPr.addNewWordWrap();
            ctOnOff.setVal(STOnOff1.OFF);
        }
    }

    private void applyParagraphStyle(Element e, XWPFParagraph paragraph) {
        String[] tokens;
        String style = e.attr("style");
        if (style == null) {
            return;
        }
        String lineSpace = null;
        String tempLineSpace = null;
        for (String token : tokens = style.split(";")) {
            if (token.contains("text-indent") || token.contains("margin-left")) {
                CTInd ind = paragraph.getCTP().addNewPPr().addNewInd();
                String propertyName = token.split(":")[0].trim();
                String value = token.split(":")[1].trim();
                List<Object> integers = new ArrayList<BigInteger>();
                if (value.endsWith("em")) {
                    String emValue = value.substring(0, value.length() - 2);
                    integers.add(new BigInteger(emValue).multiply(new BigInteger("100")));
                    integers.add(new BigInteger(emValue).multiply(new BigInteger("210")));
                } else if (value.endsWith("px")) {
                    String pxValue = value.substring(0, value.length() - 2);
                    integers = this.calculatePixelToUnits(pxValue, e);
                }
                if (CollectionUtils.isEmpty(integers)) continue;
                if (propertyName.contains("text-indent")) {
                    if (integers.get(0) != null) {
                        ind.setFirstLineChars((BigInteger)integers.get(0));
                    }
                    if (integers.get(1) == null) continue;
                    ind.setFirstLine(integers.get(1));
                    continue;
                }
                if (!propertyName.contains("margin-left")) continue;
                if (integers.get(0) != null) {
                    ind.setLeftChars((BigInteger)integers.get(0));
                }
                if (integers.get(1) == null) continue;
                ind.setLeft(integers.get(1));
                continue;
            }
            if (token.indexOf("temp-line-height") > -1) {
                tempLineSpace = token.split(":")[1].trim();
                continue;
            }
            if (token.indexOf("line-height") <= -1) continue;
            lineSpace = token.split(":")[1].trim();
        }
        if (StringUtils.isNotEmpty(tempLineSpace)) {
            OfficeUtil.setSpaceing(paragraph, tempLineSpace.contains("normal") ? "1.5em" : tempLineSpace);
        } else if (StringUtils.isNotEmpty(lineSpace)) {
            OfficeUtil.setSpaceing(paragraph, lineSpace.contains("normal") ? "1.5em" : lineSpace);
        }
    }

    private List<BigInteger> calculatePixelToUnits(String pxValue, Element e) {
        ArrayList<BigInteger> integers = new ArrayList<BigInteger>();
        Boolean isDivide7 = IntegerParser.parseInt(pxValue) % 7 == 0;
        BigInteger firstLineChars = null;
        BigInteger firstLine = null;
        if (isDivide7.booleanValue()) {
            BigDecimal divideNum = null;
            divideNum = "h1".equals(e.tagName()) ? new BigDecimal(21) : new BigDecimal(14);
            firstLineChars = new BigDecimal(pxValue).divide(divideNum).multiply(new BigDecimal(100)).toBigInteger();
            firstLine = new BigDecimal(pxValue).divide(divideNum).multiply(new BigDecimal(210)).toBigInteger();
        } else {
            firstLine = IntegerParser.parseInt(pxValue) % 19 == 0 ? new BigDecimal(pxValue).divide(new BigDecimal(38)).multiply(new BigDecimal(567)).toBigInteger() : new BigDecimal(Units.pixelToEMU(IntegerParser.parseInt(pxValue))).divide(new BigDecimal(360000), 1, 3).multiply(new BigDecimal(567)).toBigInteger();
        }
        integers.add(firstLineChars);
        integers.add(firstLine);
        return integers;
    }

    private void createXWPFParagraph(CustomXWPFDocument docxDocument, Element e, ExportSetting exportSetting) throws IOException {
        XWPFParagraph paragraph = docxDocument.createParagraph();
        ParagraphAlignment aligen = ParagraphAlignment.LEFT;
        WordUtil.setParagraphWrap(paragraph, exportSetting.getPrintStyle().getAllowWordWrap());
        try {
            aligen = OfficeUtil.getAlignment(e);
            this.applyParagraphStyle(e, paragraph);
        }
        catch (Exception exce) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u6bb5\u843d\u5bf9\u9f50\u65b9\u5f0f\u83b7\u53d6\u5f02\u5e38: " + e.toString(), exce);
        }
        paragraph.setSpacingAfter(0);
        paragraph.setSpacingBefore(0);
        String tagName = e.tagName();
        int indexParagraph = exportSetting.getIndexParagraph();
        if ("h1,h2,h3,h4".contains(tagName)) {
            if (!StringUtil.isNullOrEmpty((String)e.text().trim()) && this.tocConts.size() > 0) {
                TableOfContents.Create_TOC_Bookmak_Begin(paragraph, indexParagraph);
            }
            this.getParagraphElement(docxDocument, paragraph, e, true, exportSetting);
            String style = tagName.replace("h", "");
            paragraph.setStyle("100" + style);
            if (this.tocConts.size() > 0) {
                if (!StringUtil.isNullOrEmpty((String)e.text().trim())) {
                    TableOfContents.Create_TOC_Bookmak_End(paragraph, indexParagraph);
                }
                exportSetting.getPageNums().put("_Toc" + TableOfContents.BookmarkID(indexParagraph++), TableOfContents.getPDFPagenum(docxDocument));
                exportSetting.setIndexParagraph(indexParagraph);
            }
        } else if (e.tagName().equals("p") || e.tagName().equals("div")) {
            this.getParagraphElement(docxDocument, paragraph, e, false, exportSetting);
            if (!this.currentParagraphIsDelete) {
                this.setParagraphStyle(docxDocument, paragraph, e);
            }
        }
        if (!this.currentParagraphIsDelete && paragraph != null) {
            paragraph.setAlignment(aligen);
        }
        this.currentParagraphIsDelete = false;
    }

    private boolean isParagraphDisconnected(XWPFParagraph paragraph) {
        try {
            paragraph.getCTP().getPPr();
            return false;
        }
        catch (XmlValueDisconnectedException e) {
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private void setParagraphStyle(CustomXWPFDocument docxDocument, XWPFParagraph paragraph, Element e) {
        String style = e.attr("style");
        if (style.contains("border-bottom")) {
            this.setBorderBottom(paragraph, null);
        }
    }

    private void setBorderBottom(XWPFParagraph paragraph, String value) {
        CTP ctp = paragraph.getCTP();
        CTPPr pPr = ctp.getPPr() == null ? ctp.addNewPPr() : ctp.getPPr();
        CTPBdr pBdr = pPr.getPBdr() == null ? pPr.addNewPBdr() : pPr.getPBdr();
        CTBorder boderBottom = pBdr.addNewBottom();
        boderBottom.setVal(STBorder.SINGLE);
        boderBottom.setSz(new BigInteger("6"));
        boderBottom.setSpace(new BigInteger("1"));
        CTSpacing spacing = pPr.isSetSpacing() ? pPr.getSpacing() : pPr.addNewSpacing();
        spacing.setLineRule(STLineSpacingRule.EXACT);
        spacing.setLine(BigInteger.valueOf(300L));
    }

    private void setDocumentSize(CustomXWPFDocument document, long width, long height, STPageOrientation.Enum stValue) {
        CTPageSz pageSize;
        CTSectPr section = OfficeUtil.getCTSectPr(document);
        CTPageSz cTPageSz = pageSize = section.isSetPgSz() ? section.getPgSz() : section.addNewPgSz();
        if (stValue == STPageOrientation.LANDSCAPE) {
            pageSize.setH(IntegerParser.parseBigInt(width));
            pageSize.setW(IntegerParser.parseBigInt(height));
        } else {
            pageSize.setH(IntegerParser.parseBigInt(height));
            pageSize.setW(IntegerParser.parseBigInt(width));
        }
    }

    private void setDocumentMargin(CustomXWPFDocument document, String top, String right, String bottom, String left) {
        CTSectPr sectPr = OfficeUtil.getCTSectPr(document);
        CTPageMar ctpagemar = sectPr.addNewPgMar();
        if (!StringUtil.isNullOrEmpty((String)left)) {
            ctpagemar.setLeft(new BigInteger(left));
        }
        if (!StringUtil.isNullOrEmpty((String)top)) {
            ctpagemar.setTop(new BigInteger(top));
        }
        if (!StringUtil.isNullOrEmpty((String)right)) {
            ctpagemar.setRight(new BigInteger(right));
        }
        if (!StringUtil.isNullOrEmpty((String)bottom)) {
            ctpagemar.setBottom(new BigInteger(bottom));
        }
        ctpagemar.setHeader(new BigInteger("0"));
        ctpagemar.setFooter(new BigInteger("720"));
        ctpagemar.setGutter(new BigInteger("0"));
    }

    private void getParagraphElement(CustomXWPFDocument doc, XWPFParagraph paragraph, Element e, boolean blod, ExportSetting exportSetting) throws IOException {
        for (Node child : e.childNodes()) {
            String text;
            if (child instanceof Element) {
                if (child.nodeName().equals("img")) {
                    this.fillImg((Element)child, doc, exportSetting, paragraph);
                } else if (((Element)child).hasClass("V:LINE")) {
                    this.setRedLine(doc, paragraph);
                } else if (((Element)child).hasClass("pageFlag")) {
                    paragraph.createRun().addBreak(BreakType.PAGE);
                } else {
                    this.getParagraphElement(doc, paragraph, (Element)child, blod, exportSetting);
                }
            }
            if (!(child instanceof TextNode) || "".equals(((TextNode)child).text())) continue;
            if (((Element)child.parent()).hasClass("catalogVar")) {
                DocumentSize dSize;
                TableOfContents tocCont = TableOfContents.CreateTableOfContents(doc, false, this.isPDF);
                tocCont.addStyle(1, "heading 1");
                tocCont.addStyle(2, "heading 2");
                tocCont.addStyle(3, "heading 3");
                tocCont.addStyle(4, "heading 4");
                PrintStyle printStyle = exportSetting.getPrintStyle();
                STPageOrientation.Enum orientationType = printStyle.getOrientation() != null && printStyle.getOrientation() == 1 ? STPageOrientation.LANDSCAPE : STPageOrientation.PORTRAIT;
                String paperType = printStyle.getPaperType();
                String paperWidth = printStyle.getPaperWidth();
                String paperHeight = printStyle.getPaperHeight();
                if (this.tocConts.size() <= 0) {
                    dSize = DocumentSize.getDocumentSize(paperType);
                    if (!"".equals(paperType) && dSize != null) {
                        this.setDocumentSize(doc, dSize.getWidth(), dSize.getHeight(), orientationType);
                    } else if (!"".equals(printStyle.getPaperWidth()) && !"".equals(printStyle.getPaperHeight())) {
                        this.setDocumentSize(doc, Long.valueOf(paperWidth), Long.valueOf(paperHeight), orientationType);
                    } else {
                        this.setDocumentSize(doc, DocumentSize.A4.getWidth(), DocumentSize.A4.getHeight(), orientationType);
                    }
                    tocCont.Create_TOC_Section(exportSetting.getOriginNumber());
                } else {
                    doc.createParagraph().getCTP().addNewR().addNewBr().setType(STBrType.PAGE);
                }
                tocCont.setTabPos((printStyle.getOrientation() != null && printStyle.getOrientation() == 1 ? Math.round(Double.valueOf(paperHeight) / 100.0 * 567.0) - (long)IntegerParser.parseInt(printStyle.getMarginTop()) - (long)IntegerParser.parseInt(printStyle.getMarginBottom()) : Math.round(Double.valueOf(paperWidth) / 100.0 * 567.0) - (long)IntegerParser.parseInt(printStyle.getMarginLeft()) - (long)IntegerParser.parseInt(printStyle.getMarginLeft())) + "");
                tocCont.InitTOC();
                if (this.tocConts.size() <= 0) {
                    dSize = DocumentSize.getDocumentSize(paperType);
                    if (!"".equals(paperType) && dSize != null) {
                        this.setDocumentSize(doc, dSize.getWidth(), dSize.getHeight(), orientationType);
                    } else if (!"".equals(paperWidth) && !"".equals(paperHeight)) {
                        this.setDocumentSize(doc, Long.valueOf(paperWidth), Long.valueOf(paperHeight), orientationType);
                    } else {
                        this.setDocumentSize(doc, DocumentSize.A4.getWidth(), DocumentSize.A4.getHeight(), orientationType);
                    }
                    tocCont.Create_TOC_Section(exportSetting.getOriginNumber());
                    exportSetting.setInitPageNum(TableOfContents.getPDFPagenum(doc) - 1);
                } else {
                    doc.createParagraph().getCTP().addNewR().addNewBr().setType(STBrType.PAGE);
                }
                this.tocConts.add(tocCont);
                continue;
            }
            if (this.currentParagraphIsDelete) continue;
            if (((Element)child.parentNode()).hasClass("BrokenLine")) {
                this.handleBreakLine(doc, paragraph, (TextNode)child);
            }
            if (!StringUtil.isNullOrEmpty((String)(text = ((TextNode)child).text()))) {
                text = text.replace("&nbsp;", " ");
            }
            this.setWordStyle(paragraph, child, text, blod);
        }
    }

    public void fillImg(Element child, CustomXWPFDocument doc, ExportSetting exportSetting, XWPFParagraph paragraph) {
        try {
            Element pTag = child.closest("p");
            if (pTag != null && pTag.attr("style").indexOf("line-height") > -1) {
                String[] tokens;
                String style = pTag.attr("style");
                for (String token : tokens = style.split(";")) {
                    if (token.indexOf("line-height") <= -1 || token.indexOf("px") <= -1) continue;
                    OfficeUtil.setSpaceing(paragraph, "1em");
                    break;
                }
            }
            Base64.Decoder decoder = Base64.getDecoder();
            String imgSrc = child.attr("src");
            if (StringUtils.isEmpty((CharSequence)imgSrc)) {
                return;
            }
            byte[] byteArray = decoder.decode(imgSrc.replaceAll("data:image/.*?;base64,(.*)", "$1"));
            int width = 0;
            int height = 0;
            String tempWidth = child.attr("tempwidth");
            String tempHeight = child.attr("tempheight");
            if (StringUtils.isNotEmpty((CharSequence)tempWidth) && StringUtils.isNotEmpty((CharSequence)tempHeight)) {
                width = this.convertMillimeterToEmu(tempWidth);
                height = this.convertMillimeterToEmu(tempHeight);
            } else {
                BigInteger imageWidth;
                BigInteger paperWidth;
                int[] imageSize = this.getImageSize(byteArray, child);
                Double xdpi = this.getXDPI();
                width = this.convertPxToEmu(imageSize[0], xdpi);
                height = this.convertPxToEmu(imageSize[1], xdpi);
                if (this.isPDF && (paperWidth = new BigInteger((int)Math.floor(Double.parseDouble(Integer.parseInt(exportSetting.getPrintStyle().getPaperWidth()) / 567 + "") * 100.0 / 25.4 * this.getXDPI()) + "").add(new BigInteger((int)Math.floor(Double.parseDouble(Integer.parseInt(exportSetting.getPrintStyle().getMarginLeft()) / 567 + "") * 100.0 / 25.4 * this.getXDPI()) + ""))).compareTo(imageWidth = new BigInteger(imageSize[0] + "")) < 0) {
                    try {
                        imgSrc = OfficeUtil.CropImage(imgSrc, paperWidth.intValue(), imageSize[1]);
                    }
                    catch (IOException e) {
                        logger.info(e.getMessage(), e);
                    }
                    byteArray = decoder.decode(imgSrc.replaceAll("data:image/.*?;base64,(.*)", "$1"));
                    imageSize = this.getImageSize(byteArray, child);
                    height = this.convertPxToEmu(new BigInteger(imageSize[1] + "").multiply(paperWidth).divide(imageWidth).intValue(), xdpi);
                    width = this.convertPxToEmu(imageWidth.intValue(), xdpi);
                }
            }
            if (byteArray == null) {
                return;
            }
            String blipId = doc.addPictureData(byteArray, org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG);
            doc.createPictureUnitCm(blipId, doc.getNextPicNameNumber(org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_PNG), width, height, paragraph);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public int convertMillimeterToEmu(String value) {
        return Integer.parseInt(value) * 360000 / 10;
    }

    public int convertPxToEmu(int value, Double dpi) {
        return new BigDecimal(value).multiply(new BigDecimal(914400)).divide(new BigDecimal(dpi), RoundingMode.HALF_DOWN).intValue();
    }

    private void handleBreakLine(CustomXWPFDocument doc, XWPFParagraph paragraph, TextNode child) {
        CTP ctp = paragraph.getCTP();
        CTPPr ppr = ctp.getPPr();
        if (ppr == null) {
            ppr = ctp.addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setLineRule(STLineSpacingRule.EXACT);
        spacing.setLine(BigInteger.valueOf(300L));
        child.text("\u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500 \u2500");
        this.currentParagraphIsDelete = true;
    }

    private void setRedLine(CustomXWPFDocument doc, XWPFParagraph paragraph) {
        BufferedImage bufferedImage = new BufferedImage(100, 100, 1);
        Graphics paint = bufferedImage.getGraphics();
        paint.setColor(Color.RED);
        paint.fillRect(0, 0, 100, 199);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)bufferedImage, "png", out);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        ByteArrayInputStream img = new ByteArrayInputStream(out.toByteArray());
        CTP pp1 = doc.getDocument().getBody().addNewP();
        CTPPr ppr = pp1.getPPr();
        if (ppr == null) {
            ppr = pp1.addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0L));
        spacing.setBefore(BigInteger.valueOf(0L));
        spacing.setLineRule(STLineSpacingRule.EXACT);
        spacing.setLine(BigInteger.valueOf(200L));
        XWPFParagraph imgpara = new XWPFParagraph(pp1, doc);
        imgpara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = imgpara.createRun();
        try {
            run.addPicture((InputStream)img, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(Integer.valueOf(10066) / 20), Units.toEMU(1.8));
            doc.removeBodyElement(doc.getPosOfParagraph(paragraph));
            this.currentParagraphIsDelete = true;
        }
        catch (InvalidFormatException invalidFormatException) {
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int[] getImageSize(byte[] imageByte, Element imageNode) {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(imageByte);
        int width = 100;
        int height = 100;
        try {
            BufferedImage buf = ImageIO.read(byteInputStream);
            width = buf.getWidth();
            height = buf.getHeight();
            String style = imageNode.attr("style");
            if (!StringUtil.isNullOrEmpty((String)style)) {
                for (String token : style.split(";")) {
                    if (Pattern.matches("width:.*", token = token.replace(" ", ""))) {
                        width = IntegerParser.parseInt(token.replaceAll("width:([0-9\\.]*).*", "$1"));
                        continue;
                    }
                    if (!Pattern.matches("height:.*", token.replaceAll("\\s*", ""))) continue;
                    height = IntegerParser.parseInt(token.replaceAll("height:([0-9\\.]*).*", "$1"));
                }
            }
        }
        catch (Exception exec) {
            AnalysisReportLogHelper.log("\u751f\u6210\u5206\u6790\u62a5\u544a", "\u89e3\u6790\u56fe\u7247\u9ad8\u5ea6\u5bbd\u5ea6\u5f02\u5e38\uff1a" + imageNode.outerHtml(), exec);
        }
        finally {
            if (byteInputStream != null) {
                try {
                    byteInputStream.close();
                }
                catch (IOException iOException) {}
            }
        }
        return new int[]{width, height};
    }

    private void setWordStyle(XWPFParagraph paragraph, Node child, String text, boolean blod) {
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        try {
            WordStyle wStyle = new WordStyle(child);
            wStyle.setWordStyle(run);
        }
        catch (Exception exce) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u6bb5\u843d\u6837\u5f0f\u83b7\u53d6\u5f02\u5e38: " + child.outerHtml(), exce);
        }
    }

    private void fillTable(CustomXWPFDocument docxDocument, Element eTable, ExportSetting exportSetting) throws IOException {
        try {
            Element tbody;
            Element element = tbody = eTable.children().size() > 0 ? eTable.child(0) : null;
            if (tbody == null) {
                return;
            }
            WordTableContext wordTableContext = this.caculateTableAttribute(eTable);
            XWPFTable table = docxDocument.createTable(wordTableContext.getRowLength(), wordTableContext.getColLength());
            wordTableContext.setTable(table);
            wordTableContext.setDocument(docxDocument);
            wordTableContext.setExportSetting(exportSetting);
            this.applyTableStyle(table, eTable, wordTableContext);
            this.proccessTable(wordTableContext, tbody);
            WordUtil wordUtil = this;
            Integer n = wordUtil.tableIndex;
            Integer n2 = wordUtil.tableIndex = Integer.valueOf(wordUtil.tableIndex + 1);
            docxDocument.setTable(n, table);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public WordTableContext caculateTableAttribute(Element eTable) {
        String colLengthAttr = eTable.attr("colLength");
        String longestRowAttr = eTable.attr("longestRow");
        Element tbody = eTable.child(0);
        if (StringUtils.isNotEmpty((CharSequence)colLengthAttr) && StringUtils.isNotEmpty((CharSequence)longestRowAttr)) {
            for (Element tr : tbody.children()) {
                if (!tr.attr("style").contains("display: none;")) continue;
                tr.remove();
            }
            return new WordTableContext(tbody.children().size(), Integer.valueOf(colLengthAttr), Integer.valueOf(longestRowAttr));
        }
        int[] tableAttribute = new int[3];
        for (int i = 0; i < tbody.children().size(); ++i) {
            Element tr = tbody.child(i);
            if (tr.attr("style").contains("display: none;")) {
                tr.remove();
                continue;
            }
            if (tr.children().size() > tableAttribute[0]) {
                tableAttribute[0] = tr.children().size();
                tableAttribute[1] = i;
            }
            int maxCellNum = 0;
            for (int j = 0; j < tr.children().size(); ++j) {
                Element td = tr.child(j);
                if (!"".equals(td.html()) && td.outerHtml().indexOf("display: none;") > -1) {
                    td.remove();
                    --j;
                    continue;
                }
                String colspan = tr.child(j).attr("colspan");
                maxCellNum += StringUtils.isNotEmpty((CharSequence)colspan) ? Integer.valueOf(colspan) : 1;
            }
            tableAttribute[2] = Math.max(tableAttribute[2], maxCellNum);
        }
        return new WordTableContext(tbody.children().size(), tableAttribute[2], tableAttribute[1]);
    }

    private BigInteger calculateEffectiveWidth(XWPFDocument docxDocument) {
        int pageWidth = Integer.parseInt(docxDocument.getDocument().getBody().getSectPr().getPgSz().getW().toString());
        int marLeft = Integer.parseInt(docxDocument.getDocument().getBody().getSectPr().getPgMar().getLeft().toString());
        int marRight = Integer.parseInt(docxDocument.getDocument().getBody().getSectPr().getPgMar().getRight().toString());
        BigInteger effectiveWidth = new BigInteger(String.valueOf(pageWidth - marLeft - marRight));
        return effectiveWidth;
    }

    private void applyTableStyle(XWPFTable table, Element eTable, WordTableContext wordTableContext) {
        String style;
        String tableWidth = eTable.attr("width").replace("-", "");
        if ("".equals(tableWidth)) {
            tableWidth = "100%";
        }
        BigInteger effectiveWidth = this.calculateEffectiveWidth(wordTableContext.getDocument());
        if (tableWidth.indexOf("%") >= 0) {
            table.setWidthType(TableWidthType.PCT);
            if (IntegerParser.parseInt(tableWidth.replace("%", "")) > 100) {
                tableWidth = "100%";
            }
            table.setWidth(tableWidth);
            effectiveWidth = effectiveWidth.multiply(new BigInteger(tableWidth.replace("%", "")).divide(new BigInteger("100")));
        } else {
            table.setWidthType(TableWidthType.DXA);
            table.setWidth(IntegerParser.parseInt(tableWidth) * 15);
            effectiveWidth = new BigInteger(IntegerParser.parseInt(tableWidth) * 15 + "");
        }
        CTTblGrid tblGrid = table.getCTTbl().addNewTblGrid();
        Element element = eTable.child(0).child(wordTableContext.getLongestRow());
        int colLength = wordTableContext.getColLength();
        for (int i = 0; i < colLength; ++i) {
            CTTblGridCol gridCol = tblGrid.addNewGridCol();
            if (i >= element.childrenSize()) {
                gridCol.setW(new BigInteger("100"));
                continue;
            }
            Element child = element.child(i);
            if (child.outerHtml().contains("display: none;")) {
                ++colLength;
                continue;
            }
            String width = this.dealColWidth(child);
            if (width.contains("%")) {
                gridCol.setW(effectiveWidth.multiply(new BigInteger(width.replace("%", ""))).divide(new BigInteger("100")));
                continue;
            }
            gridCol.setW(!"".equals(width) ? new BigInteger(width) : new BigInteger("0"));
        }
        String align = eTable.attr("align");
        if (StringUtil.isNullOrEmpty((String)align) && StringUtils.isNotEmpty((CharSequence)(style = eTable.attr("style")))) {
            Boolean marginLeft = style.contains("margin-left:auto");
            Boolean marginRight = style.contains("margin-right:auto");
            if (marginLeft.booleanValue() && marginRight.booleanValue()) {
                align = "center";
            } else if (marginLeft.booleanValue() && !marginRight.booleanValue()) {
                align = "right";
            }
        }
        STJcTable.Enum tableAlign = STJcTable.LEFT;
        if ("center".equals(align)) {
            tableAlign = STJcTable.CENTER;
        } else if ("right".equals(align)) {
            tableAlign = STJcTable.RIGHT;
        }
        CTTblPr tablePr = table.getCTTbl().addNewTblPr();
        tablePr.addNewJc().setVal(tableAlign);
        tablePr.addNewTblLayout().setType(STTblLayoutType.FIXED);
    }

    public String dealColWidth(Element td) {
        String tdwidth = td.attr("width");
        if (StringUtils.isEmpty((CharSequence)tdwidth)) {
            String[] tokens;
            for (String token : tokens = td.attr("style").split(";")) {
                if (token.indexOf("width") < 0 || token.indexOf("border") > -1) continue;
                tdwidth = token.split(":")[1];
                break;
            }
        }
        if (StringUtils.isNotEmpty((CharSequence)tdwidth)) {
            tdwidth = tdwidth.indexOf("%") > -1 ? tdwidth.trim() : IntegerParser.parseInt(tdwidth) * 15 + "";
        }
        return tdwidth;
    }

    private void mergeCells(WordTableCell wordTableCell, WordTableContext wordTableContext, int colspan, int rowspan, boolean[][] matrix) {
        for (int row = wordTableCell.getRowNum(); row < wordTableCell.getRowNum() + rowspan; ++row) {
            List deleteCells = wordTableContext.gethMergeCells().getOrDefault(row, new ArrayList());
            for (int col = wordTableCell.getColNum(); col < wordTableCell.getColNum() + colspan; ++col) {
                try {
                    XWPFTableCell mergeCell = wordTableContext.getTable().getRow(row).getCell(col);
                    if (rowspan > 1 && col == wordTableCell.getColNum()) {
                        if (StringUtils.isNotEmpty((CharSequence)wordTableCell.getWidth())) {
                            this.setCellWidth(wordTableCell.getWidth(), mergeCell);
                        }
                    } else if (col != wordTableCell.getColNum() || row != wordTableCell.getRowNum()) {
                        this.setCellWidth("0", mergeCell);
                    }
                    CTTcPr tcPr = OfficeUtil.getCellCTTcPr(mergeCell);
                    if (rowspan > 1) {
                        if (row == wordTableCell.getRowNum()) {
                            tcPr.addNewVMerge().setVal(STMerge.RESTART);
                        } else {
                            tcPr.addNewVMerge();
                        }
                    }
                    if (colspan > 1) {
                        if (col == wordTableCell.getColNum()) {
                            tcPr.addNewGridSpan().setVal(new BigInteger(String.valueOf(colspan)));
                        } else {
                            deleteCells.add(0, col);
                        }
                    }
                    matrix[row][col] = true;
                    this.setCellStyle(wordTableCell.getTd(), mergeCell);
                    continue;
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            wordTableContext.gethMergeCells().put(row, deleteCells);
        }
    }

    public void proccessTable(WordTableContext wordTableContext, Element tbody) throws IOException {
        Object object;
        Integer rowNum = 0;
        Integer colNum = 0;
        boolean[][] matrix = new boolean[wordTableContext.getRowLength()][wordTableContext.getColLength()];
        XWPFTable table = wordTableContext.getTable();
        for (int i = 0; i < tbody.children().size(); ++i) {
            Element tr = tbody.child(i);
            this.dealRowHeight(i, tr, table);
            colNum = 0;
            while (matrix.length > rowNum && matrix[rowNum].length > colNum + 1 && matrix[rowNum][colNum]) {
                Integer n = colNum;
                colNum = colNum + 1;
                object = colNum;
            }
            if (matrix.length < rowNum || matrix[rowNum].length < colNum) continue;
            for (int j = 0; j < tr.children().size(); ++j) {
                Element td = tr.child(j);
                if (td.attr("style").contains("display: none;")) continue;
                XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
                WordTableCell wordTableCell = new WordTableCell(td, cell, rowNum, colNum, this.dealColWidth(td));
                this.applyCellStyle(wordTableCell);
                this.getTableElement(wordTableContext.getDocument(), cell.getParagraphArray(0), wordTableCell.getTd(), wordTableContext.getExportSetting());
                int colspan = IntegerParser.parseInt("".equals(td.attr("colspan")) ? "1" : td.attr("colspan"));
                int rowspan = IntegerParser.parseInt("".equals(td.attr("rowspan")) ? "1" : td.attr("rowspan"));
                if (colspan > 1 || rowspan > 1) {
                    this.mergeCells(wordTableCell, wordTableContext, colspan, rowspan, matrix);
                    colNum = colNum + colspan;
                } else {
                    matrix[rowNum.intValue()][colNum.intValue()] = true;
                }
                while (matrix.length > rowNum && matrix[rowNum].length > colNum + 1 && matrix[rowNum][colNum]) {
                    Integer n = colNum;
                    Integer n2 = colNum = Integer.valueOf(colNum + 1);
                }
            }
            Integer j = rowNum;
            rowNum = rowNum + 1;
            object = rowNum;
        }
        for (int row : wordTableContext.gethMergeCells().keySet()) {
            List<Integer> deleteCells = wordTableContext.gethMergeCells().get(row);
            Collections.sort(deleteCells, Collections.reverseOrder());
            object = deleteCells.iterator();
            while (object.hasNext()) {
                int col = object.next();
                table.getRow(row).removeCell(col);
            }
        }
    }

    public void dealRowHeight(int i, Element tr, XWPFTable table) {
        String tdHeightStr = this.getStyleAttribute(tr.attr("style"), "height");
        if (StringUtils.isNotEmpty((CharSequence)tdHeightStr)) {
            int tdheight = new BigDecimal(tdHeightStr.replace("px", "")).multiply(new BigDecimal(15)).intValue();
            table.getRow(i).setHeight(tdheight);
        }
    }

    public void applyCellStyle(WordTableCell wordTableCell) {
        XWPFTableCell cell = wordTableCell.getCell();
        List<XWPFParagraph> paragraphList = cell.getParagraphs();
        XWPFParagraph pIO = CollectionUtils.isEmpty(paragraphList) ? cell.addParagraph() : paragraphList.get(0);
        pIO.setSpacingAfter(0);
        pIO.setSpacingBefore(0);
        Element td = wordTableCell.getTd();
        String tdstyle = td.attr("style");
        String colorInBackground = "auto";
        String[] backgroundtokens = tdstyle.split(";");
        boolean backgroundFound = false;
        boolean indentFound = false;
        try {
            for (String backgroundtoken : backgroundtokens) {
                if (backgroundFound && indentFound) break;
                if (!backgroundFound && backgroundtoken.contains("background")) {
                    String[] rgb;
                    String typeStyle = backgroundtoken.split(":")[1].trim();
                    if (typeStyle.contains("#")) {
                        colorInBackground = typeStyle.replace("#", "").toUpperCase();
                        backgroundFound = true;
                        continue;
                    }
                    if (!typeStyle.startsWith("rgb") || (rgb = typeStyle.replaceAll("rgb\\(([0-9, ]+)\\).*", "$1").split(",")).length != 3) continue;
                    colorInBackground = WordStyle.convertRGBToHex(Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()), Integer.parseInt(rgb[2].trim()));
                    backgroundFound = true;
                    continue;
                }
                if (indentFound || !backgroundtoken.contains("analysisreport_text_indent:%sem;")) continue;
                String indentValue = backgroundtoken.split(":")[1].trim();
                pIO.setIndentationLeftChars(Integer.valueOf(indentValue) * 100);
                indentFound = true;
            }
            this.setCellStyle(td, cell);
        }
        catch (Exception exce) {
            AnalysisReportLogHelper.log("\u5206\u6790\u62a5\u544a\u5bfc\u51fa", "\u5206\u6790\u62a5\u544a\u8868\u683c\u5b57\u4f53\u989c\u8272\u83b7\u53d6\u5f02\u5e38: " + td.outerHtml(), exce);
        }
        if (!"FFFFFF".equals(colorInBackground) && !"auto".equals(colorInBackground)) {
            cell.setColor(colorInBackground);
        } else {
            cell.setColor("auto");
        }
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        if (StringUtils.isNotEmpty((CharSequence)wordTableCell.getWidth())) {
            this.setCellWidth(wordTableCell.getWidth(), cell);
        } else {
            cell.setWidthType(TableWidthType.AUTO);
        }
        pIO.setAlignment(OfficeUtil.getTableAlignment(td));
    }

    public void setCellWidth(String tdWidth, XWPFTableCell cell) {
        CTTcPr tcPr = OfficeUtil.getCellCTTcPr(cell);
        CTTblWidth tcW = OfficeUtil.getCellTblWidth(tcPr);
        if (tdWidth.contains("%")) {
            tdWidth = tdWidth.trim();
            tcW.setW(new BigInteger(String.valueOf(IntegerParser.parseInt(tdWidth.replace("%", "")) * 50)));
            tcW.setType(STTblWidth.PCT);
        } else {
            tcW.setW(new BigInteger(tdWidth));
            tcW.setType(STTblWidth.DXA);
        }
    }

    public String getStyleAttribute(String style, String attributeName) {
        String[] styles;
        if (StringUtils.isEmpty((CharSequence)style) || StringUtils.isEmpty((CharSequence)attributeName)) {
            return null;
        }
        for (String styleStr : styles = style.split(";")) {
            String[] split;
            String trimmedStyleStr = styleStr.trim();
            if (StringUtils.isEmpty((CharSequence)trimmedStyleStr) || (split = trimmedStyleStr.split(":", 2)).length != 2 || !attributeName.equals(split[0].trim())) continue;
            return split[1].trim().toString();
        }
        return null;
    }

    private void getTableElement(CustomXWPFDocument doc, XWPFParagraph paragraph, Element e, ExportSetting exportSetting) throws IOException {
        this.appendLineBreak(paragraph, e);
        if (e.children().size() > 0) {
            for (Node child : e.childNodes()) {
                if (child instanceof Element) {
                    this.getTableElement(doc, paragraph, (Element)child, exportSetting);
                }
                if (!(child instanceof TextNode) || "".equals(((TextNode)child).text())) continue;
                XWPFRun run = paragraph.createRun();
                String showText = this.replaceSpaces(((TextNode)child).outerHtml());
                run.setText(showText);
                WordStyle wStyle = new WordStyle(child.childNodes() != null && child.childNodes().size() > 0 ? child.childNode(0) : child, "table");
                wStyle.setWordStyle(run);
            }
        } else if (e.hasClass("biChartVar")) {
            this.fillImg(e, doc, exportSetting, paragraph);
        } else if (e.hasClass("catalogVar")) {
            doc.createParagraph().createRun().addBreak(BreakType.PAGE);
            XWPFParagraph paragraphMeau = doc.createParagraph();
            XWPFRun runMeau = paragraphMeau.createRun();
            runMeau.setText("\u76ee\u5f55");
            paragraphMeau.setAlignment(ParagraphAlignment.CENTER);
            runMeau.setBold(true);
            runMeau.setColor("000000");
            runMeau.setFontFamily("\u5b8b\u4f53");
            runMeau.setFontSize(16);
            runMeau.addBreak();
            BufferedImage bufferedImage = new BufferedImage(100, 100, 1);
            Graphics paint = bufferedImage.getGraphics();
            paint.setColor(Color.BLACK);
            paint.fillRect(0, 0, 100, 199);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write((RenderedImage)bufferedImage, "png", out);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            ByteArrayInputStream img = new ByteArrayInputStream(out.toByteArray());
            XWPFParagraph imgpara = doc.createParagraph();
            imgpara.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = imgpara.createRun();
            try {
                run.addPicture((InputStream)img, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(500.0), Units.toEMU(2.5));
            }
            catch (InvalidFormatException invalidFormatException) {
                // empty catch block
            }
            run.addBreak();
            XWPFParagraph paragraphMeau1 = doc.createParagraph();
            XWPFRun runText = paragraphMeau1.createRun();
            runText.setText("catalogVar");
            paragraphMeau1.setPageBreak(true);
        } else {
            XWPFRun run = paragraph.createRun();
            if (e.tagName().equals("br")) {
                run.addBreak();
            } else {
                if ("td".equals(e.tagName()) && e.childNodeSize() == 0) {
                    e.html("&nbsp;");
                }
                String showText = this.replaceSpaces(e.html());
                run.setText(showText);
                WordStyle wStyle = new WordStyle((Node)(e.childNodes() != null && e.childNodes().size() > 0 ? e.childNode(0) : e), "table");
                wStyle.setWordStyle(run);
            }
        }
    }

    public String replaceSpaces(String text) {
        if (StringUtils.isEmpty((CharSequence)text)) {
            return text;
        }
        return text.replace(" ", "").replace("&nbsp;", " ");
    }

    public void appendLineBreak(XWPFParagraph paragraph, Element e) {
        try {
            if (!"p".equals(e.tagName())) {
                return;
            }
            if (e.children().size() == 1 && "br".equals(((Element)e.children().get(0)).tagName())) {
                return;
            }
            Element element = e.previousElementSibling();
            if (element == null) {
                return;
            }
            if (!"p".equals(element.tagName())) {
                return;
            }
            if (this.hasBr(element).booleanValue()) {
                return;
            }
            paragraph.createRun().addBreak();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public Boolean hasBr(Element e) {
        while (e.children().size() > 0) {
            if (e.children().size() <= 0) continue;
            e = e.child(e.childrenSize() - 1);
        }
        return "br".equals(e.tagName());
    }

    private void setCellStyle(Element td, XWPFTableCell cell) {
        String style = td.attr("style");
        CTTcPr tcPr = OfficeUtil.getCellCTTcPr(cell);
        CTTcBorders tblBorders = tcPr.isSetTcBorders() ? tcPr.getTcBorders() : tcPr.addNewTcBorders();
        String top_width = "1";
        String top_color = "000000";
        String bottom_width = "1";
        String bottom_color = "000000";
        String left_width = "1";
        String left_color = "000000";
        String right_width = "1";
        String right_color = "000000";
        STBorder.Enum top_style = STBorder.THICK;
        STBorder.Enum bottom_style = STBorder.THICK;
        STBorder.Enum left_style = STBorder.THICK;
        STBorder.Enum right_style = STBorder.THICK;
        for (String token : style.split(";")) {
            try {
                String key = token.split(":")[0].trim();
                String val = token.split(":")[1].trim();
                val = val.replaceAll(this.matcher_border_width, "0px").replaceAll("(transparent)", "none").replaceAll("(currentcolor|windowtext)", "#000000");
                List<String> border_width = AnaUtils.findRex(val, "[0-9]px");
                List<String> border_style = AnaUtils.findRex(val, this.matcher_border_style);
                List<String> border_color = AnaUtils.findRex(val, "(windowtext|currentcolor|transparent|#[a-fA-F0-9]{6}|rgb\\(.*?\\)|white|black|red|yellow|blue|green|orange|gray)");
                for (int i = 0; i < border_width.size(); ++i) {
                    String width = border_width.get(i);
                    if (StringUtil.isNullOrEmpty((String)width)) continue;
                    BigDecimal bwidth = new BigDecimal(width.replace("px", ""));
                    bwidth = bwidth.compareTo(new BigDecimal("0")) < 1 ? new BigDecimal("0") : (bwidth.compareTo(new BigDecimal("1")) == -1 ? new BigDecimal("8") : bwidth.multiply(new BigDecimal("8")));
                    border_width.set(i, bwidth.divide(new BigDecimal("1"), 0, 3).toString());
                }
                switch (key) {
                    case "border": {
                        if (border_width != null && border_width.size() > 0 && !StringUtil.isNullOrEmpty((String)border_width.get(0))) {
                            top_width = border_width.get(0);
                            bottom_width = border_width.get(0);
                            left_width = border_width.get(0);
                            right_width = border_width.get(0);
                        }
                        if (border_color != null && border_color.size() > 0 && !StringUtil.isNullOrEmpty((String)border_color.get(0))) {
                            top_color = border_color.get(0);
                            bottom_color = border_color.get(0);
                            left_color = border_color.get(0);
                            right_color = border_color.get(0);
                        }
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        top_style = this.getBorderStyle(border_style.get(0));
                        bottom_style = this.getBorderStyle(border_style.get(0));
                        left_style = this.getBorderStyle(border_style.get(0));
                        right_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-left": {
                        if (border_width != null && border_width.size() > 0 && !StringUtil.isNullOrEmpty((String)border_width.get(0))) {
                            left_width = border_width.get(0);
                        }
                        if (border_color != null && border_color.size() > 0 && !StringUtil.isNullOrEmpty((String)border_color.get(0))) {
                            left_color = border_color.get(0);
                        }
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        left_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-right": {
                        if (border_width != null && border_width.size() > 0 && !StringUtil.isNullOrEmpty((String)border_width.get(0))) {
                            right_width = border_width.get(0);
                        }
                        if (border_color != null && border_color.size() > 0 && !StringUtil.isNullOrEmpty((String)border_color.get(0))) {
                            right_color = border_color.get(0);
                        }
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        right_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-top": {
                        if (border_width != null && border_width.size() > 0 && !StringUtil.isNullOrEmpty((String)border_width.get(0))) {
                            top_width = border_width.get(0);
                        }
                        if (border_color != null && border_color.size() > 0 && !StringUtil.isNullOrEmpty((String)border_color.get(0))) {
                            top_color = border_color.get(0);
                        }
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        top_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-bottom": {
                        if (border_width != null && border_width.size() > 0 && !StringUtil.isNullOrEmpty((String)border_width.get(0))) {
                            bottom_width = border_width.get(0);
                        }
                        if (border_color != null && border_color.size() > 0 && !StringUtil.isNullOrEmpty((String)border_color.get(0))) {
                            bottom_color = border_color.get(0);
                        }
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        bottom_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-width": {
                        if (border_width == null || border_width.size() <= 0 || StringUtil.isNullOrEmpty((String)border_width.get(0))) break;
                        if (border_width.size() == 1) {
                            border_width.add(border_width.get(0));
                            border_width.add(border_width.get(0));
                            border_width.add(border_width.get(0));
                        } else if (border_width.size() == 2) {
                            border_width.add(border_width.get(0));
                            border_width.add(border_width.get(1));
                        } else if (border_width.size() == 3) {
                            border_width.add(border_width.get(1));
                        }
                        top_width = border_width.get(0);
                        bottom_width = border_width.get(1);
                        left_width = border_width.get(2);
                        right_width = border_width.get(3);
                        break;
                    }
                    case "border-style": {
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        if (border_style.size() == 1) {
                            border_style.add(border_style.get(0));
                            border_style.add(border_style.get(0));
                            border_style.add(border_style.get(0));
                        } else if (border_style.size() == 2) {
                            border_style.add(border_style.get(0));
                            border_style.add(border_style.get(1));
                        } else if (border_style.size() == 3) {
                            border_style.add(border_style.get(1));
                        }
                        top_style = this.getBorderStyle(border_style.get(0));
                        bottom_style = this.getBorderStyle(border_style.get(1));
                        left_style = this.getBorderStyle(border_style.get(2));
                        right_style = this.getBorderStyle(border_style.get(3));
                        break;
                    }
                    case "border-color": {
                        if (border_color == null || border_color.size() <= 0 || StringUtil.isNullOrEmpty((String)border_color.get(0))) break;
                        if (border_color.size() == 1) {
                            border_color.add(border_color.get(0));
                            border_color.add(border_color.get(0));
                            border_color.add(border_color.get(0));
                        } else if (border_color.size() == 2) {
                            border_color.add(border_color.get(0));
                            border_color.add(border_color.get(1));
                        } else if (border_color.size() == 3) {
                            border_color.add(border_color.get(1));
                        }
                        top_color = border_color.get(0);
                        bottom_color = border_color.get(1);
                        left_color = border_color.get(2);
                        right_color = border_color.get(3);
                        break;
                    }
                    case "border-left-width": {
                        if (border_width == null || border_width.size() <= 0 || StringUtil.isNullOrEmpty((String)border_width.get(0))) break;
                        left_width = border_width.get(0);
                        break;
                    }
                    case "border-right-width": {
                        if (border_width == null || border_width.size() <= 0 || StringUtil.isNullOrEmpty((String)border_width.get(0))) break;
                        right_width = border_width.get(0);
                        break;
                    }
                    case "border-top-width": {
                        if (border_width == null || border_width.size() <= 0 || StringUtil.isNullOrEmpty((String)border_width.get(0))) break;
                        top_width = border_width.get(0);
                        break;
                    }
                    case "border-bottom-width": {
                        if (border_width == null || border_width.size() <= 0 || StringUtil.isNullOrEmpty((String)border_width.get(0))) break;
                        bottom_width = border_width.get(0);
                        break;
                    }
                    case "border-left-style": {
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        left_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-right-style": {
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        right_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-top-style": {
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        top_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-bottom-style": {
                        if (border_style == null || border_style.size() <= 0 || StringUtil.isNullOrEmpty((String)border_style.get(0))) break;
                        bottom_style = this.getBorderStyle(border_style.get(0));
                        break;
                    }
                    case "border-left-color": {
                        if (border_color == null || border_color.size() <= 0 || StringUtil.isNullOrEmpty((String)border_color.get(0))) break;
                        left_color = border_color.get(0);
                        break;
                    }
                    case "border-right-color": {
                        if (border_color == null || border_color.size() <= 0 || StringUtil.isNullOrEmpty((String)border_color.get(0))) break;
                        right_color = border_color.get(0);
                        break;
                    }
                    case "border-top-color": {
                        if (border_color == null || border_color.size() <= 0 || StringUtil.isNullOrEmpty((String)border_color.get(0))) break;
                        top_color = border_color.get(0);
                        break;
                    }
                    case "border-bottom-color": {
                        if (border_color == null || border_color.size() <= 0 || StringUtil.isNullOrEmpty((String)border_color.get(0))) break;
                        bottom_color = border_color.get(0);
                        break;
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.setBorder(tblBorders, top_width, top_style, top_color, "top");
        this.setBorder(tblBorders, bottom_width, bottom_style, bottom_color, "bottom");
        this.setBorder(tblBorders, left_width, left_style, left_color, "left");
        this.setBorder(tblBorders, right_width, right_style, right_color, "right");
    }

    private void setBorder(CTTcBorders tblBorders, String width, STBorder.Enum style, String color, String position) {
        CTBorder border = null;
        switch (position) {
            case "top": {
                border = tblBorders.isSetTop() ? tblBorders.getTop() : tblBorders.addNewTop();
                break;
            }
            case "bottom": {
                border = tblBorders.isSetBottom() ? tblBorders.getBottom() : tblBorders.addNewBottom();
                break;
            }
            case "left": {
                border = tblBorders.isSetLeft() ? tblBorders.getLeft() : tblBorders.addNewLeft();
                break;
            }
            case "right": {
                border = tblBorders.isSetRight() ? tblBorders.getRight() : tblBorders.addNewRight();
                break;
            }
            default: {
                CTBorder cTBorder = border = tblBorders.isSetTop() ? tblBorders.getTop() : tblBorders.addNewTop();
            }
        }
        if (style == null || StringUtil.isNullOrEmpty((String)width) || "0".equals(width) || StringUtil.isNullOrEmpty((String)color) || style == STBorder.NIL) {
            border.setVal(STBorder.NIL);
        } else {
            border.setSz(new BigInteger(width));
            border.setVal(style);
            border.setColor(this.getBorderColor(color));
        }
    }

    private String getBorderColor(String style) {
        if (Pattern.matches("rgb(.*)", style)) {
            style = style.replace("rgb(", "");
            String[] rbg = (style = style.replace(")", "")).split(",");
            if (rbg.length == 3) {
                return WordStyle.convertRGBToHex(IntegerParser.parseInt(rbg[0].trim()), IntegerParser.parseInt(rbg[1].trim()), IntegerParser.parseInt(rbg[2].trim()));
            }
            return "000000";
        }
        if (Pattern.matches("#.*", style)) {
            return style.substring(1);
        }
        if (Pattern.matches("(white|green|yellow|red|blue|gray|orange|windowtext)", style)) {
            if (style.indexOf("white") >= 0) {
                return "FFFFFF";
            }
            if (style.indexOf("green") >= 0) {
                return "008000";
            }
            if (style.indexOf("yellow") >= 0) {
                return "FFFF00";
            }
            if (style.indexOf("red") >= 0) {
                return "FF0000";
            }
            if (style.indexOf("blue") >= 0) {
                return "0000FF";
            }
            if (style.indexOf("gray") >= 0) {
                return "808080";
            }
            if (style.indexOf("orange") >= 0) {
                return "FFA500";
            }
            return "000000";
        }
        return "000000";
    }

    private STBorder.Enum getBorderStyle(String typeStyle) {
        if (typeStyle.indexOf("solid") >= 0) {
            return STBorder.THICK;
        }
        if (typeStyle.indexOf("double") >= 0) {
            return STBorder.DOUBLE;
        }
        if (typeStyle.indexOf("dashed") >= 0) {
            return STBorder.DASHED;
        }
        if (typeStyle.indexOf("dotted") >= 0) {
            return STBorder.DOTTED;
        }
        if (typeStyle.indexOf("none") >= 0) {
            return STBorder.NIL;
        }
        return STBorder.THICK;
    }

    public boolean isPDF() {
        return this.isPDF;
    }

    public void setPDF(boolean isPDF) {
        this.isPDF = isPDF;
    }

    public Double[] getDPI() {
        return this.DPI;
    }

    public Double getXDPI() {
        return this.DPI[0];
    }

    public Double getYDPI() {
        return this.DPI[1];
    }

    public void setDPI(double x, double y) {
        this.DPI[0] = x;
        this.DPI[1] = y;
    }

    public void setDPI(Double[] DPI) {
        this.DPI = DPI;
    }

    private String getWatermarkContent(String content) {
        switch (content) {
            case "Secrecy": {
                return "\u4fdd\u5bc6";
            }
            case "Department top secret department": {
                return "\u90e8\u95e8\u7edd\u5bc6";
            }
            case "Circulation": {
                return "\u4f20\u9605";
            }
            case "Company top secret": {
                return "\u516c\u53f8\u7edd\u5bc6";
            }
            case "Urgent": {
                return "\u7d27\u6025";
            }
            case "No copying": {
                return "\u7981\u6b62\u590d\u5236";
            }
            case "Sample": {
                return "\u6837\u672c";
            }
            case "Examples": {
                return "\u6837\u4f8b";
            }
            case "Original": {
                return "\u539f\u672c";
            }
            case "Element": {
                return "\u5143\u4ef6";
            }
        }
        return content;
    }
}

