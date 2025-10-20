/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.itextpdf.kernel.font.PdfFont
 *  com.itextpdf.kernel.font.PdfFontFactory
 *  com.itextpdf.kernel.font.PdfFontFactory$EmbeddingStrategy
 *  com.itextpdf.kernel.geom.PageSize
 *  com.itextpdf.kernel.pdf.PdfDocument
 *  com.itextpdf.kernel.pdf.PdfWriter
 *  com.itextpdf.layout.Document
 *  com.itextpdf.layout.borders.Border
 *  com.itextpdf.layout.element.Cell
 *  com.itextpdf.layout.element.IBlockElement
 *  com.itextpdf.layout.element.Paragraph
 *  com.itextpdf.layout.element.Table
 *  com.itextpdf.layout.properties.HorizontalAlignment
 *  com.itextpdf.layout.properties.TextAlignment
 *  com.itextpdf.layout.properties.UnitValue
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.mongodb.GCBlobContainerManager
 *  com.jiuqi.gcreport.common.pdf.ConstantProperties
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.impl.help.OrgDataQueryService
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  javax.annotation.Resource
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.mongodb.GCBlobContainerManager;
import com.jiuqi.gcreport.common.pdf.ConstantProperties;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.EfdcCheckReportDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogEO;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.EFDCDataCheckExportService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.extend.DataCheckPdfService;
import com.jiuqi.gcreport.efdcdatacheck.impl.service.impl.EFDCDataCheckImpl;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EFDCDataCheckExportImpl
implements EFDCDataCheckExportService {
    private final Logger logger = LoggerFactory.getLogger(EFDCDataCheckExportImpl.class);
    @Autowired
    private ConstantProperties pro;
    @Autowired
    private GCBlobContainerManager gcBlobContainerMongo;
    @Resource
    private EfdcCheckReportDAO checkReportLogDAO;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private OrgDataQueryService orgDataQueryService;
    @Autowired
    private DataCheckPdfService pdfService;
    public static final String DIRECTORY = "efdcCheckfiles";

    private PdfFont createPdfFont() throws IOException {
        String fontPath = new ClassPathResource("/pdf/simsun.ttf").getPath();
        return PdfFontFactory.createFont((String)fontPath, (String)"Identity-H", (PdfFontFactory.EmbeddingStrategy)PdfFontFactory.EmbeddingStrategy.PREFER_NOT_EMBEDDED);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportPdf(GcFormOperationInfo formOperationInfo, HttpServletResponse response) throws Exception {
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = response.getOutputStream();
        PdfWriter writer = new PdfWriter((OutputStream)outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        try (Document document = new Document(pdfDocument, PageSize.A4);){
            this.exportPdf(document, formOperationInfo);
            response.flushBuffer();
        }
    }

    @Override
    public StringBuffer planTaskCheckResultExcel(String asynTaskKey, EFDCDataCheckImpl checkResultInfo, GcBatchEfdcCheckInfo gcBatchEfdcCheckInfo) throws Exception {
        this.batchCheckCreateExcel(gcBatchEfdcCheckInfo, asynTaskKey, checkResultInfo);
        return checkResultInfo.getLog();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void batchCheckCreateExcel(GcBatchEfdcCheckInfo gcBatchEfdcCheckInfo, String asynTaskKey, EFDCDataCheckImpl checkResultInfo) {
        TaskDefine taskDefine;
        GcBatchEfdcQueryParam efdcQueryParam = new GcBatchEfdcQueryParam();
        efdcQueryParam.setAsynTaskID(asynTaskKey);
        List<EfdcCheckResultVO> resultVOs = checkResultInfo.queryResultByAsynTaskId(efdcQueryParam);
        HSSFWorkbook workbook = new HSSFWorkbook();
        checkResultInfo.createExcel(workbook, resultVOs, gcBatchEfdcCheckInfo.isGroupByReport());
        ArrayList<String> rootUnitResults = new ArrayList<String>();
        HashMap<String, String> orgId2ParentsMap = new HashMap<String, String>();
        String firstRootUnitTitle = this.getRootUnit(gcBatchEfdcCheckInfo, rootUnitResults, orgId2ParentsMap);
        if (rootUnitResults.size() > 1) {
            firstRootUnitTitle = firstRootUnitTitle + "\u7b49";
        }
        String taskTile = "";
        if (!StringUtils.isEmpty((String)gcBatchEfdcCheckInfo.getTaskKey()) && null != (taskDefine = this.runTimeViewController.queryTaskDefine(gcBatchEfdcCheckInfo.getTaskKey()))) {
            taskTile = taskDefine.getTitle() + "_";
        }
        String groupMode = gcBatchEfdcCheckInfo.isGroupByReport() ? "_\u6309\u62a5\u8868" : "_\u6309\u5355\u4f4d";
        String defaultFileName = taskTile + gcBatchEfdcCheckInfo.getPeriodTitle() + "_" + firstRootUnitTitle + groupMode + "_" + asynTaskKey;
        String fileName = StringUtils.isEmpty((String)gcBatchEfdcCheckInfo.getFileName()) ? defaultFileName + ".xls" : gcBatchEfdcCheckInfo.getFileName() + "_" + asynTaskKey + ".xls";
        try {
            File tempFile = new File(this.pro.getTempFilePath() + fileName);
            workbook.write(tempFile);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            checkResultInfo.getLog().append(e.getMessage());
        }
        finally {
            try {
                workbook.close();
            }
            catch (IOException e) {
                this.logger.error(e.getMessage(), e);
                checkResultInfo.getLog().append(e.getMessage());
            }
        }
        try {
            String fileKey = this.uploadFileToOss(fileName);
            String username = gcBatchEfdcCheckInfo.getUserName();
            if (StringUtils.isEmpty((String)username)) {
                ContextUser user = NpContextHolder.getContext().getUser();
                username = user == null ? "" : user.getName();
            }
            String adjust = "";
            if (gcBatchEfdcCheckInfo.getDimensionSet().get("ADJUST") != null) {
                adjust = String.valueOf(((DimensionValue)gcBatchEfdcCheckInfo.getDimensionSet().get("ADJUST")).getValue());
            }
            if (CollectionUtils.isEmpty(rootUnitResults)) {
                return;
            }
            EfdcCheckReportLogEO eo = new EfdcCheckReportLogEO();
            eo.setId(fileKey);
            DimensionValue dataTime = (DimensionValue)gcBatchEfdcCheckInfo.getDimensionSet().get("DATATIME");
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)gcBatchEfdcCheckInfo.getFormSchemeKey(), (String)dataTime.getValue());
            eo.setAcctPeriod(yp.getPeriod());
            eo.setAcctYear(yp.getYear());
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            eo.setCreateDate(Calendar.getInstance().getTime());
            eo.setCreateUser(username);
            eo.setFileName(fileName);
            eo.setFilePath(this.pro.getTempFilePath());
            eo.setDefaultPeriod(dataTime.getValue());
            eo.setTaskId(gcBatchEfdcCheckInfo.getTaskKey());
            eo.setSchemeId(gcBatchEfdcCheckInfo.getFormSchemeKey());
            eo.setAdjust(adjust);
            this.checkReportLogDAO.save(eo);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            checkResultInfo.getLog().append(e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void exportPdf(Document document, GcFormOperationInfo formOperationInfo) throws IOException {
        NpContext context = NpContextHolder.getContext();
        try {
            EFDCDataCheckImpl checkResultInfo = new EFDCDataCheckImpl();
            EfdcCheckResultGroupVO resultGroupVO = checkResultInfo.processEfdcDataCheckResultGroup(context, formOperationInfo);
            NpContextHolder.setContext((NpContext)context);
            String unitId = (String)formOperationInfo.getDimensionValueSet().getValue("MD_ORG");
            String orgType = (String)formOperationInfo.getDimensionValueSet().getValue("MD_GCORGTYPE");
            String period = (String)formOperationInfo.getDimensionValueSet().getValue("DATATIME");
            YearPeriodObject yp = new YearPeriodObject(null, period);
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            ContextUser user = context.getUser();
            List<String> formIds = this.header(document, user, resultGroupVO.getPeriodTitle(), resultGroupVO.getMsg(), checkResultInfo, true, formOperationInfo.getFormSchemeKey(), formOperationInfo.getTaskKey());
            ArrayList<EfdcCheckResultVO> resultVOs = new ArrayList<EfdcCheckResultVO>();
            for (List checkResultVOs : resultGroupVO.getEfdcCheckResultVOs()) {
                for (EfdcCheckResultVO efdcCheckResultVO : checkResultVOs) {
                    resultVOs.add(efdcCheckResultVO);
                }
            }
            this.everyZbInfoGroupUnit(document, resultVOs, formIds, checkResultInfo, tool);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String uploadFileToOss(String fileName) throws IOException {
        File file = new File(this.pro.getTempFilePath() + fileName);
        String fileKey = UUIDOrderUtils.newUUIDStr();
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(file);
            VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("multipartFile", fileKey, "multipart/form-data; charset=ISO-8859-1", bytes);
            this.commonFileService.uploadFileToOss((MultipartFile)multipartFile, fileKey);
        }
        finally {
            file.delete();
        }
        return fileKey;
    }

    private List<String> header(Document document, ContextUser user, String periodTitle, String msg, EFDCDataCheckImpl checkResultInfo, boolean showDiffRate, String formSchemeKey, String taskId) throws Exception {
        FormSchemeDefine formSchemeDefine;
        TaskDefine taskDefine;
        String userTitle = "";
        if (null != user) {
            userTitle = StringUtils.isEmpty((String)user.getFullname()) ? user.getName() : user.getFullname();
        }
        if (this.runTimeViewController == null) {
            this.runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        }
        String taskTile = "";
        if (!StringUtils.isEmpty((String)taskId) && null != (taskDefine = this.runTimeViewController.queryTaskDefine(taskId))) {
            taskTile = taskDefine.getTitle();
        }
        String schemeTitle = "";
        if (!StringUtils.isEmpty((String)formSchemeKey) && null != (formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey))) {
            schemeTitle = formSchemeDefine.getTitle();
        }
        Calendar calendar = Calendar.getInstance();
        String today = calendar.get(1) + "\u5e74" + (calendar.get(2) + 1) + "\u6708" + calendar.get(5) + "\u65e5";
        PdfFont pdfFont = this.createPdfFont();
        Paragraph paragraph = (Paragraph)((Paragraph)((Paragraph)((Paragraph)((Paragraph)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcReport")).setFont(pdfFont)).setFontSize(18.0f)).setBold()).setTextAlignment(TextAlignment.CENTER)).setMarginBottom(22.0f);
        document.add((IBlockElement)paragraph);
        float[] widths = new float[]{24.0f, 8.0f};
        Table table2 = new Table(UnitValue.createPercentArray((float[])widths));
        table2.setWidth(430.0f);
        table2.setFixedLayout();
        Cell cell21 = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcTask", (Object[])new Object[]{taskTile}))).setFont(pdfFont)).setFontSize(9.0f)).setBorder(Border.NO_BORDER);
        Cell cell22 = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph("")).setFont(pdfFont)).setFontSize(9.0f)).setBorder(Border.NO_BORDER);
        table2.addCell(cell21);
        table2.addCell(cell22);
        table2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table2.setMarginBottom(10.0f);
        document.add((IBlockElement)table2);
        table2 = new Table(UnitValue.createPercentArray((float[])widths));
        table2.setWidth(430.0f);
        table2.setFixedLayout();
        cell21 = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcPeriod", (Object[])new Object[]{periodTitle}))).setFont(pdfFont)).setFontSize(9.0f)).setBorder(Border.NO_BORDER);
        cell22 = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcReportScheme", (Object[])new Object[]{schemeTitle}))).setFont(pdfFont)).setFontSize(9.0f)).setBorder(Border.NO_BORDER);
        table2.addCell(cell21);
        table2.addCell(cell22);
        table2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table2.setMarginBottom(10.0f);
        document.add((IBlockElement)table2);
        table2 = new Table(UnitValue.createPercentArray((float[])widths));
        table2.setWidth(430.0f);
        table2.setFixedLayout();
        cell21 = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.auditUser", (Object[])new Object[]{userTitle}))).setFont(pdfFont)).setFontSize(9.0f)).setBorder(Border.NO_BORDER);
        cell22 = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportDate", (Object[])new Object[]{today}))).setFont(pdfFont)).setFontSize(9.0f)).setBorder(Border.NO_BORDER);
        table2.addCell(cell21);
        table2.addCell(cell22);
        table2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table2.setMarginBottom(10.0f);
        document.add((IBlockElement)table2);
        paragraph = (Paragraph)((Paragraph)((Paragraph)((Paragraph)new Paragraph("          " + msg).setFont(pdfFont)).setFontSize(9.0f)).setMarginBottom(6.0f)).setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add((IBlockElement)paragraph);
        paragraph = (Paragraph)((Paragraph)((Paragraph)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.diffInfo")).setFont(pdfFont)).setFontSize(9.0f)).setMarginBottom(6.0f);
        document.add((IBlockElement)paragraph);
        ArrayList<String> formIds = new ArrayList<String>();
        this.everyFormCountInfo(document, checkResultInfo, formIds, showDiffRate, formSchemeKey);
        return formIds;
    }

    private void everyFormCountInfo(Document document, EFDCDataCheckImpl checkResultInfo, List<String> formIds, boolean showNoPassCol, String formSchemeKey) throws Exception {
        float[] widths = new float[]{14.0f, 6.0f, 6.0f, 6.0f};
        int colCount = widths.length;
        Table table = new Table(UnitValue.createPercentArray((float[])widths));
        table.setWidth(430.0f);
        table.setFixedLayout();
        PdfFont pdfFont = this.createPdfFont();
        Cell[] headerCells = new Cell[colCount];
        headerCells[0] = (Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportName"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER);
        headerCells[1] = (Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.zbTotalNum"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER);
        headerCells[2] = (Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.fail"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER);
        headerCells[3] = (Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.passRate"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER);
        for (Cell cell : headerCells) {
            table.addCell(cell);
        }
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add((IBlockElement)table);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        Map<String, Integer> form2CheckZbCount = checkResultInfo.getForm2CheckZbAllOrgCount();
        Map<String, Integer> form2FailZbCount = checkResultInfo.getForm2FailZbAllOrgCount();
        List<String> form2CheckZbCountList = new LinkedList<String>(form2CheckZbCount.keySet());
        form2CheckZbCountList = form2CheckZbCountList.stream().filter(formId -> (Integer)form2CheckZbCount.get(formId) != 0).collect(Collectors.toList());
        this.sortForm2CheckZbCountList(form2CheckZbCountList, form2CheckZbCount, form2FailZbCount);
        for (String formId2 : form2CheckZbCountList) {
            formIds.add(formId2);
            FormDefine formDefine = runTimeViewController.queryEntityForm(formId2);
            int checkZbCount = form2CheckZbCount.get(formId2);
            int failZbCount = null == form2FailZbCount.get(formId2) ? 0 : form2FailZbCount.get(formId2);
            int successCount = checkZbCount - failZbCount;
            String rate = "0";
            if (successCount != 0 && checkZbCount != 0) {
                rate = NumberUtils.round((double)((double)successCount * 100.0 / (double)checkZbCount)) + "%";
            }
            Table dataTable = new Table(UnitValue.createPercentArray((float[])widths));
            dataTable.setWidth(430.0f);
            dataTable.setFixedLayout();
            Cell cell1 = (Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(formDefine.getTitle())).setFont(pdfFont)).setFontSize(9.0f)).setBorderRight(Border.NO_BORDER)).setBorderTop(Border.NO_BORDER);
            Cell cell2 = (Cell)((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(String.valueOf(checkZbCount))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.RIGHT)).setBorderRight(Border.NO_BORDER)).setBorderTop(Border.NO_BORDER);
            Cell cell3 = (Cell)((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(String.valueOf(failZbCount))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.RIGHT)).setBorderRight(Border.NO_BORDER)).setBorderTop(Border.NO_BORDER);
            Cell cell4 = (Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(rate)).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(Border.NO_BORDER);
            dataTable.addCell(cell1);
            dataTable.addCell(cell2);
            dataTable.addCell(cell3);
            dataTable.addCell(cell4);
            dataTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add((IBlockElement)dataTable);
        }
    }

    private void sortForm2CheckZbCountList(List<String> form2CheckZbCountList, Map<String, Integer> form2CheckZbCount, Map<String, Integer> form2FailZbCount) {
        Collections.sort(form2CheckZbCountList, (o1, o2) -> {
            int checkZbCount = (Integer)form2CheckZbCount.get(o1);
            int failZbCount = (Integer)form2FailZbCount.get(o1);
            int successCount = checkZbCount - failZbCount;
            double rate1 = 0.0;
            if (successCount != 0 && checkZbCount != 0) {
                rate1 = NumberUtils.round((double)((double)successCount * 100.0 / (double)checkZbCount));
            }
            checkZbCount = (Integer)form2CheckZbCount.get(o2);
            failZbCount = (Integer)form2FailZbCount.get(o2);
            successCount = checkZbCount - failZbCount;
            double rate2 = 0.0;
            if (successCount != 0 && checkZbCount != 0) {
                rate2 = NumberUtils.round((double)((double)successCount * 100.0 / (double)checkZbCount));
            }
            if (rate2 - rate1 > 0.0) {
                return -1;
            }
            if (rate2 - rate1 == 0.0) {
                return 0;
            }
            return 1;
        });
    }

    private void everyZbInfoGroupUnit(Document document, List<EfdcCheckResultVO> resultVOs, List<String> formIds, EFDCDataCheckImpl checkResultInfo, GcOrgCenterService tool) throws IOException {
        GcOrgCacheVO org = tool.getOrgByCode(resultVOs.get(0).getOrgId());
        PdfFont pdfFont = this.createPdfFont();
        Paragraph paragraph = (Paragraph)((Paragraph)((Paragraph)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unit", (Object[])new Object[]{org.getTitle()})).setFont(pdfFont)).setFontSize(9.0f)).setMarginBottom(10.0f);
        document.add((IBlockElement)paragraph);
        this.everyUnitCountInfoHeader(document, pdfFont);
        Map<String, Integer> form2CheckZbCount = checkResultInfo.getForm2CheckZbCount();
        this.sortFormEveryZbInfoGroupUnit(formIds, resultVOs, form2CheckZbCount);
        int index = 1;
        for (String formkey : formIds) {
            int failCount = (int)resultVOs.stream().filter(vo -> vo.getFormKey().equals(formkey)).count();
            if (failCount == 0) continue;
            FormDefine formDefine = this.runTimeViewController.queryFormById(formkey);
            int checkZbCount = form2CheckZbCount.get(formkey);
            String failRate = failCount != 0 && checkZbCount != 0 ? NumberUtils.round((double)((double)failCount * 100.0 / (double)checkZbCount)) + "%" : "0";
            float[] widths = new float[]{4.0f, 14.0f, 7.0f, 7.0f};
            Table table = new Table(UnitValue.createPercentArray((float[])widths));
            table.setWidth(430.0f);
            table.setFixedLayout();
            table.addCell((Cell)((Cell)((Cell)new Cell().add((IBlockElement)((Paragraph)new Paragraph(String.valueOf(index++)).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderTop(Border.NO_BORDER)).setBorderRight(Border.NO_BORDER));
            table.addCell((Cell)((Cell)new Cell().add((IBlockElement)((Paragraph)new Paragraph(formDefine.getTitle()).setFont(pdfFont)).setFontSize(9.0f)).setBorderTop(Border.NO_BORDER)).setBorderRight(Border.NO_BORDER));
            table.addCell((Cell)((Cell)new Cell().add((IBlockElement)((Paragraph)new Paragraph(String.valueOf(failCount)).setFont(pdfFont)).setFontSize(9.0f)).setBorderTop(Border.NO_BORDER)).setBorderRight(Border.NO_BORDER));
            table.addCell((Cell)((Cell)new Cell().add((IBlockElement)((Paragraph)new Paragraph(failRate).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.RIGHT)).setBorderTop(Border.NO_BORDER));
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add((IBlockElement)table);
        }
    }

    private void sortFormEveryZbInfoGroupUnit(List<String> formIds, List<EfdcCheckResultVO> resultVOs, Map<String, Integer> form2CheckZbCount) {
        Collections.sort(formIds, (o1, o2) -> {
            int count1 = resultVOs.stream().filter(vo -> vo.getFormKey().equals(o1)).collect(Collectors.toList()).size();
            int checkZbCount1 = (Integer)form2CheckZbCount.get(o1);
            double rate1 = 0.0;
            if (count1 != 0 && checkZbCount1 != 0) {
                rate1 = NumberUtils.round((double)((double)count1 * 100.0 / (double)checkZbCount1));
            }
            int count2 = resultVOs.stream().filter(vo -> vo.getFormKey().equals(o2)).collect(Collectors.toList()).size();
            int checkZbCount2 = (Integer)form2CheckZbCount.get(o2);
            double rate2 = 0.0;
            if (count2 != 0 && checkZbCount2 != 0) {
                rate2 = NumberUtils.round((double)((double)count2 * 100.0 / (double)checkZbCount2));
            }
            if (rate2 - rate1 > 0.0) {
                return 1;
            }
            if (rate2 - rate1 == 0.0) {
                return 0;
            }
            return -1;
        });
    }

    private void everyZbInfoGroupReport(Document document, List<EfdcCheckResultVO> resultVOs, List<String> formIds, EFDCDataCheckImpl checkResultInfo, Set<String> unitIdSet, Map<String, DimensionValue> dimensionValueMap) throws IOException {
        String orgType = dimensionValueMap.get("MD_GCORGTYPE").getValue();
        String dataTime = dimensionValueMap.get("DATATIME").getValue();
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcBaseDataCenterTool baseDataTool = GcBaseDataCenterTool.getInstance();
        Map<String, List<EfdcCheckResultVO>> form2ResultVOs = resultVOs.stream().collect(Collectors.groupingBy(EfdcCheckResultVO::getFormKey));
        Map<String, Integer> form2CheckZbCount = checkResultInfo.getForm2CheckZbCount();
        PdfFont pdfFont = this.createPdfFont();
        ArrayList<String> orgCodeList = new ArrayList<String>(unitIdSet);
        for (String formId : formIds) {
            List<EfdcCheckResultVO> resultVOs2 = form2ResultVOs.get(formId);
            if (resultVOs2 == null) continue;
            FormDefine formDefine = this.runTimeViewController.queryFormById(formId);
            document.add((IBlockElement)((Paragraph)((Paragraph)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.reportColumn", (Object[])new Object[]{formDefine.getTitle()})).setFont(pdfFont)).setFontSize(9.0f)).setMarginBottom(10.0f));
            float[] widths = new float[]{3.0f, 14.0f, 4.0f, 7.0f, 7.0f};
            Table headerTable = new Table(UnitValue.createPercentArray((float[])widths));
            headerTable.setWidth(430.0f);
            headerTable.setFixedLayout();
            headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.index"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
            headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph("\u5355\u4f4d")).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
            headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph("\u5e01\u79cd")).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
            headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.diffZbNumber"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
            headerTable.addCell((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.differenceRate"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER));
            headerTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add((IBlockElement)headerTable);
            Map<String, List<EfdcCheckResultVO>> org2ResultVOs = resultVOs2.stream().collect(Collectors.groupingBy(EfdcCheckResultVO::getOrgId));
            orgCodeList.sort(Comparator.comparingInt(o -> org2ResultVOs.getOrDefault(o, Collections.emptyList()).size()).reversed());
            int checkZbCount = form2CheckZbCount.getOrDefault(formId, 0);
            if (checkZbCount == 0) continue;
            int index = 1;
            for (String orgCode : orgCodeList) {
                List<EfdcCheckResultVO> resultVOs3;
                GcOrgCacheVO org = tool.getOrgByCode(orgCode);
                if (org == null || CollectionUtils.isEmpty(resultVOs3 = org2ResultVOs.get(orgCode))) continue;
                Map<String, List<EfdcCheckResultVO>> resultVOs4 = resultVOs3.stream().collect(Collectors.groupingBy(EfdcCheckResultVO::getCurrency));
                for (Map.Entry<String, List<EfdcCheckResultVO>> entry : resultVOs4.entrySet()) {
                    String currencyId = entry.getKey();
                    List<EfdcCheckResultVO> efdcCheckResultVOS = entry.getValue();
                    BaseDataVO baseDataVO = baseDataTool.queryBaseDataVoByCode("MD_CURRENCY", currencyId);
                    int failCheckZbCount = efdcCheckResultVOS.size();
                    String rate = checkZbCount != 0 && failCheckZbCount != 0 ? NumberUtils.round((double)((double)failCheckZbCount * 100.0 / (double)checkZbCount)) + "%" : "0";
                    Table dataTable = new Table(UnitValue.createPercentArray((float[])widths));
                    dataTable.setWidth(430.0f);
                    dataTable.setFixedLayout();
                    dataTable.addCell(this.createDataCell(String.valueOf(index++), TextAlignment.CENTER, true, pdfFont));
                    dataTable.addCell(this.createDataCell(org.getTitle(), TextAlignment.LEFT, true, pdfFont));
                    dataTable.addCell(this.createDataCell(baseDataVO.getTitle(), TextAlignment.LEFT, true, pdfFont));
                    dataTable.addCell(this.createDataCell(String.valueOf(failCheckZbCount), TextAlignment.RIGHT, true, pdfFont));
                    dataTable.addCell(this.createDataCell(rate, TextAlignment.RIGHT, false, pdfFont));
                    dataTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    document.add((IBlockElement)dataTable);
                }
            }
        }
    }

    private Cell createDataCell(String text, TextAlignment alignment, boolean noRightBorder, PdfFont pdfFont) {
        Cell cell = (Cell)((Cell)new Cell().add((IBlockElement)((Paragraph)new Paragraph(text).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(alignment)).setBorderTop(Border.NO_BORDER);
        if (noRightBorder) {
            cell.setBorderRight(Border.NO_BORDER);
        }
        return cell;
    }

    private void groupByReportPDF(NpContext context, GcBatchEfdcCheckInfo batchCheckInfo, String asynTaskKey, EFDCDataCheckImpl checkResultInfo, String taskTile) {
        ArrayList<String> rootUnitResults = new ArrayList<String>();
        HashMap<String, String> orgId2ParentsMap = new HashMap<String, String>();
        ContextUser user = context.getUser();
        String firstRootUnitTitle = this.getRootUnit(batchCheckInfo, rootUnitResults, orgId2ParentsMap);
        if (rootUnitResults.size() > 1) {
            firstRootUnitTitle = firstRootUnitTitle + "\u7b49";
        }
        String defaultFileName = taskTile + batchCheckInfo.getPeriodTitle() + "_" + firstRootUnitTitle + "_\u6309\u62a5\u8868_" + asynTaskKey;
        String fileName = StringUtils.isEmpty((String)batchCheckInfo.getFileName()) ? defaultFileName + ".pdf" : batchCheckInfo.getFileName() + "_" + asynTaskKey + ".pdf";
        try (FileOutputStream fos = new FileOutputStream(this.pro.getTempFilePath() + fileName);){
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter((OutputStream)fos));
            Document document = new Document(pdfDoc, PageSize.A4);
            int checkZbCount = checkResultInfo.getCheckZbCount();
            int failZbCount = checkResultInfo.getFailZbCount();
            int successCount = checkZbCount - failZbCount;
            String rate = "0";
            if (successCount != 0 && checkZbCount != 0) {
                rate = NumberUtils.round((double)((double)successCount * 100.0 / (double)checkZbCount)) + "%";
            }
            int formCount = checkResultInfo.getForm2CheckZbCount().size();
            Set unitIdSet = batchCheckInfo.getOrgIds();
            unitIdSet.removeAll(checkResultInfo.getErrorUnitSet());
            String msg = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.efdcResultInfo", (Object[])new Object[]{unitIdSet.size(), formCount, checkZbCount, successCount, rate});
            List<String> formIds = this.header(document, user, batchCheckInfo.getPeriodTitle(), msg, checkResultInfo, false, batchCheckInfo.getFormSchemeKey(), batchCheckInfo.getTaskKey());
            GcBatchEfdcQueryParam efdcQueryParam = new GcBatchEfdcQueryParam();
            efdcQueryParam.setAsynTaskID(asynTaskKey);
            List<EfdcCheckResultVO> resultVOs = checkResultInfo.queryResultByAsynTaskId(efdcQueryParam);
            this.everyZbInfoGroupReport(document, resultVOs, formIds, checkResultInfo, unitIdSet, batchCheckInfo.getDimensionSet());
            document.close();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createReportError"), (Throwable)e);
        }
        try {
            String fileKey = this.uploadFileToOss(fileName);
            String username = batchCheckInfo.getUserName();
            if (StringUtils.isEmpty((String)username)) {
                String string = username = user == null ? "" : user.getName();
            }
            if (CollectionUtils.isEmpty(rootUnitResults)) {
                return;
            }
            EfdcCheckReportLogEO eo = new EfdcCheckReportLogEO();
            DimensionValue dataTime = (DimensionValue)batchCheckInfo.getDimensionSet().get("DATATIME");
            String adjust = "";
            if (batchCheckInfo.getDimensionSet().get("ADJUST") != null) {
                adjust = String.valueOf(((DimensionValue)batchCheckInfo.getDimensionSet().get("ADJUST")).getValue());
            }
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)batchCheckInfo.getFormSchemeKey(), (String)dataTime.getValue());
            eo.setId(fileKey);
            eo.setAcctPeriod(yp.getPeriod());
            eo.setAcctYear(yp.getYear());
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            eo.setCreateDate(Calendar.getInstance().getTime());
            eo.setCreateUser(username);
            eo.setFileName(fileName);
            eo.setFilePath(this.pro.getTempFilePath());
            eo.setDefaultPeriod(dataTime.getValue());
            eo.setTaskId(batchCheckInfo.getTaskKey());
            eo.setSchemeId(batchCheckInfo.getFormSchemeKey());
            eo.setAdjust(adjust);
            this.checkReportLogDAO.save(eo);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private void groupByUnitPDF(NpContext context, GcBatchEfdcCheckInfo batchCheckInfo, String asynTaskKey, EFDCDataCheckImpl checkResultInfo, String taskTile) {
        ArrayList<String> rootUnitResults = new ArrayList<String>();
        HashMap<String, String> orgId2ParentsMap = new HashMap<String, String>();
        ContextUser user = context.getUser();
        String firstRootUnitTitle = this.getRootUnit(batchCheckInfo, rootUnitResults, orgId2ParentsMap);
        if (rootUnitResults.size() > 1) {
            firstRootUnitTitle = firstRootUnitTitle + "\u7b49";
        }
        String defaultFileName = taskTile + batchCheckInfo.getPeriodTitle() + "_" + firstRootUnitTitle + "_\u6309\u5355\u4f4d_" + asynTaskKey;
        String fileName = StringUtils.isEmpty((String)batchCheckInfo.getFileName()) ? defaultFileName + ".pdf" : batchCheckInfo.getFileName() + "_" + asynTaskKey + ".pdf";
        try (FileOutputStream fos = new FileOutputStream(this.pro.getTempFilePath() + fileName);){
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter((OutputStream)fos));
            Document document = new Document(pdfDoc, PageSize.A4);
            Map<String, Integer> unit2Count = new EFDCDataCheckImpl().queryCountGroupByOrgIdAndCurrency(asynTaskKey);
            Set unitIdSet = batchCheckInfo.getOrgIds();
            unitIdSet.removeAll(checkResultInfo.getErrorUnitSet());
            String msg = this.pdfService.getDataCheckPdfHeaderMsg(asynTaskKey, batchCheckInfo, checkResultInfo);
            this.header(document, user, batchCheckInfo.getPeriodTitle(), msg, checkResultInfo, false, batchCheckInfo.getFormSchemeKey(), batchCheckInfo.getTaskKey());
            this.everyUnitCountInfo(document, unit2Count, unitIdSet, checkResultInfo, batchCheckInfo);
            document.close();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.createReportError"), (Throwable)e);
        }
        try {
            String fileKey = this.uploadFileToOss(fileName);
            String username = batchCheckInfo.getUserName();
            if (StringUtils.isEmpty((String)username)) {
                String string = username = user == null ? "" : user.getName();
            }
            if (CollectionUtils.isEmpty(rootUnitResults)) {
                return;
            }
            EfdcCheckReportLogEO eo = new EfdcCheckReportLogEO();
            DimensionValue dataTime = (DimensionValue)batchCheckInfo.getDimensionSet().get("DATATIME");
            String adjust = "";
            if (batchCheckInfo.getDimensionSet().get("ADJUST") != null) {
                adjust = String.valueOf(((DimensionValue)batchCheckInfo.getDimensionSet().get("ADJUST")).getValue());
            }
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((String)batchCheckInfo.getFormSchemeKey(), (String)dataTime.getValue());
            eo.setId(fileKey);
            eo.setAcctPeriod(yp.getPeriod());
            eo.setAcctYear(yp.getYear());
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            eo.setCreateDate(Calendar.getInstance().getTime());
            eo.setCreateUser(username);
            eo.setFileName(fileName);
            eo.setFilePath(this.pro.getTempFilePath());
            eo.setDefaultPeriod(dataTime.getValue());
            eo.setTaskId(batchCheckInfo.getTaskKey());
            eo.setSchemeId(batchCheckInfo.getFormSchemeKey());
            eo.setAdjust(adjust);
            this.checkReportLogDAO.save(eo);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private void everyUnitCountInfo(Document document, Map<String, Integer> unit2Count, Set<String> unitIdSet, EFDCDataCheckImpl checkResultInfo, GcBatchEfdcCheckInfo batchCheckInfo) throws IOException {
        Map<String, Integer> form2CheckZbCount = checkResultInfo.getForm2CheckZbCount();
        PdfFont pdfFont = this.createPdfFont();
        DimensionValue dimensionValue = (DimensionValue)batchCheckInfo.getDimensionSet().get("MD_GCORGTYPE");
        String orgType = dimensionValue.getValue();
        DimensionValue dataTime = (DimensionValue)batchCheckInfo.getDimensionSet().get("DATATIME");
        YearPeriodObject yp = new YearPeriodObject(null, dataTime.getValue());
        GcBaseDataCenterTool baseDataTool = GcBaseDataCenterTool.getInstance();
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCategoryname(orgType);
        orgDTO.setStopflag(Integer.valueOf(0));
        orgDTO.setRecoveryflag(Integer.valueOf(0));
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        ArrayList<String> orgCodeList = new ArrayList<String>();
        for (String orgCode : unitIdSet) {
            if (orgCodeList.contains(orgCode)) continue;
            orgCodeList.add(orgCode);
            orgDTO.setCode(orgCode);
            OrgDO org = this.orgDataQueryService.get(orgDTO);
            if (org == null) continue;
            List currencyids = (List)org.get((Object)"currencyids");
            for (String currency : currencyids) {
                List<String> formIds;
                int unitFailZbCount = this.getUnitFailZbCount(unit2Count, orgCode, currency, formIds = this.sortEveryUnitCountInfo(form2CheckZbCount, unit2Count, orgCode, currency));
                if (unitFailZbCount == 0) continue;
                BaseDataVO currencyBaseData = baseDataTool.queryBaseDataVoByCode("MD_CURRENCY", currency);
                String currencyTitle = currencyBaseData.getTitle();
                document.add((IBlockElement)((Paragraph)((Paragraph)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.unit", (Object[])new Object[]{org.getName()}) + "\uff0c\u7a3d\u6838\u5e01\u79cd\uff1a" + currencyTitle).setFont(pdfFont)).setFontSize(9.0f)).setMarginBottom(10.0f));
                this.everyUnitCountInfoHeader(document, pdfFont);
                int index = 1;
                for (String formkey : formIds) {
                    int failCount = unit2Count.getOrDefault(orgCode + currency + formkey, 0);
                    int checkZbCount = form2CheckZbCount.get(formkey);
                    if (failCount == 0) continue;
                    FormDefine formDefine = this.runTimeViewController.queryFormById(formkey);
                    String failRate = failCount != 0 && checkZbCount != 0 ? NumberUtils.round((double)((double)failCount * 100.0 / (double)checkZbCount)) + "%" : "0";
                    float[] widths = new float[]{4.0f, 14.0f, 7.0f, 7.0f};
                    Table dataTable = new Table(UnitValue.createPercentArray((float[])widths));
                    dataTable.setWidth(430.0f);
                    dataTable.setFixedLayout();
                    dataTable.addCell(this.createDataCell(String.valueOf(index++), TextAlignment.CENTER, true, pdfFont));
                    dataTable.addCell(this.createDataCell(formDefine.getTitle(), TextAlignment.LEFT, true, pdfFont));
                    dataTable.addCell(this.createDataCell(String.valueOf(failCount), TextAlignment.RIGHT, true, pdfFont));
                    dataTable.addCell(this.createDataCell(failRate, TextAlignment.RIGHT, false, pdfFont));
                    dataTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    document.add((IBlockElement)dataTable);
                }
            }
        }
    }

    private int getUnitFailZbCount(Map<String, Integer> unit2Count, String orgCode, String currency, List<String> formIds) {
        int count = 0;
        for (String formkey : formIds) {
            count += unit2Count.getOrDefault(orgCode + currency + formkey, 0).intValue();
        }
        return count;
    }

    private List<String> sortEveryUnitCountInfo(Map<String, Integer> form2CheckZbCount, Map<String, Integer> unit2Count, String orgCode, String currency) {
        List<String> formIds = new ArrayList<String>(form2CheckZbCount.keySet());
        formIds = formIds.stream().sorted((o1, o2) -> {
            int checkZbCount1 = (Integer)form2CheckZbCount.get(o1);
            int failZbCount1 = unit2Count.get(orgCode + o1) == null ? 0 : (Integer)unit2Count.get(orgCode + currency + o1);
            double rate1 = 0.0;
            if (failZbCount1 != 0 && checkZbCount1 != 0) {
                rate1 = NumberUtils.round((double)((double)failZbCount1 * 100.0 / (double)checkZbCount1));
            }
            int checkZbCount2 = (Integer)form2CheckZbCount.get(o2);
            int failZbCount2 = unit2Count.get(orgCode + currency + o2) == null ? 0 : (Integer)unit2Count.get(orgCode + currency + o2);
            double rate2 = 0.0;
            if (failZbCount2 != 0 && checkZbCount2 != 0) {
                rate2 = NumberUtils.round((double)((double)failZbCount2 * 100.0 / (double)checkZbCount2));
            }
            if (rate2 - rate1 > 0.0) {
                return 1;
            }
            if (rate2 - rate1 == 0.0) {
                return 0;
            }
            return -1;
        }).collect(Collectors.toList());
        return formIds;
    }

    private Table everyUnitCountInfoHeader(Document document, PdfFont pdfFont) {
        float[] widths = new float[]{4.0f, 14.0f, 7.0f, 7.0f};
        Table headerTable = new Table(UnitValue.createPercentArray((float[])widths));
        headerTable.setWidth(430.0f);
        headerTable.setFixedLayout();
        headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.index"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
        headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.report"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
        headerTable.addCell((Cell)((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.diffZbNumber"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER)).setBorderRight(Border.NO_BORDER));
        headerTable.addCell((Cell)((Cell)((Cell)new Cell().add((IBlockElement)new Paragraph(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.differenceRate"))).setFont(pdfFont)).setFontSize(9.0f)).setTextAlignment(TextAlignment.CENTER));
        headerTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add((IBlockElement)headerTable);
        return headerTable;
    }

    @Override
    public StringBuffer planTaskCheckResultPdf(NpContext context, String asynTaskKey, EFDCDataCheckImpl checkResultInfo, GcBatchEfdcCheckInfo batchCheckInfo) throws Exception {
        TaskDefine taskDefine;
        String taskTile = "";
        if (!StringUtils.isEmpty((String)batchCheckInfo.getTaskKey()) && null != (taskDefine = this.runTimeViewController.queryTaskDefine(batchCheckInfo.getTaskKey()))) {
            taskTile = taskDefine.getTitle() + "_";
        }
        if (batchCheckInfo.isGroupByReport()) {
            this.groupByReportPDF(context, batchCheckInfo, asynTaskKey, checkResultInfo, taskTile);
        } else {
            this.groupByUnitPDF(context, batchCheckInfo, asynTaskKey, checkResultInfo, taskTile);
        }
        return checkResultInfo.getLog();
    }

    private String getRootUnit(GcBatchEfdcCheckInfo batchCheckInfo, List<String> rootUnitResults, Map<String, String> orgId2ParentsMap) {
        String orgType = ((DimensionValue)batchCheckInfo.getDimensionSet().get("MD_GCORGTYPE")).getValue();
        String dataTime = ((DimensionValue)batchCheckInfo.getDimensionSet().get("DATATIME")).getValue();
        YearPeriodObject yp = new YearPeriodObject(null, dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List orgs = tool.listAllOrgByParentId(null);
        Set orignOrgIds = batchCheckInfo.getOrignOrgIds();
        for (Object org : orgs) {
            orgId2ParentsMap.put(org.getId(), org.getParentStr());
        }
        ArrayList<String> parents = new ArrayList<String>();
        for (Object orgId : orignOrgIds) {
            parents.add(orgId2ParentsMap.get(orgId));
        }
        Collections.sort(parents);
        String topParent = null;
        for (String parent : parents) {
            if (null == topParent) {
                topParent = parent;
                continue;
            }
            if (parent.startsWith(topParent)) continue;
            String orgId = this.orgId(topParent);
            rootUnitResults.add(orgId);
            topParent = parent;
        }
        rootUnitResults.add(this.orgId(topParent));
        String unitTitle = "";
        if (rootUnitResults.size() > 0) {
            String orgId = rootUnitResults.get(0);
            GcOrgCacheVO organization = tool.getOrgByID(orgId);
            unitTitle = organization.getTitle();
        }
        return unitTitle;
    }

    private String orgId(String topParent) {
        return topParent.substring(topParent.lastIndexOf(47) + 1);
    }
}

