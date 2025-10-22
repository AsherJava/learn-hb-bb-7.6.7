/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.deepoove.poi.xwpf.NiceXWPFDocument
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDimensionDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO$ChooseUnit
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO
 *  com.jiuqi.gcreport.analysisreport.dto.req.ReqSaveAnalysisReportDataDocPageDTO
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 *  com.jiuqi.nr.analysisreport.rest.SaveAnalysisController
 *  com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.utils.WordUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$PeriodDim
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$UnitDim
 *  com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil
 *  com.jiuqi.nr.convert.pdf.utils.ConvertUtil$FILE_TYPE_TO_PDF
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService
 *  com.jiuqi.nr.designer.web.rest.vo.ReturnObject
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  feign.Util
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 *  org.springframework.http.MediaType
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.analysisreport.utils;

import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDimensionDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportParseContextDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO;
import com.jiuqi.gcreport.analysisreport.dto.req.ReqSaveAnalysisReportDataDocPageDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportDataEO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportTemplateService;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.rest.SaveAnalysisController;
import com.jiuqi.nr.analysisreport.utils.CustomXWPFDocument;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.utils.WordUtil;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.convert.pdf.utils.ConvertUtil;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.designer.web.rest.vo.ReturnObject;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import feign.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.LambdaMetafactory;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class AnalysisReportUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisReportUtils.class);
    public static final String ROOT_LOCATION = System.getProperty("java.io.tmpdir");

    public static String buildDataDimensionsLikeConditionIngoreOrgDim(List<AnalysisReportDimensionDTO> dimensions) {
        if (CollectionUtils.isEmpty(dimensions)) {
            return null;
        }
        dimensions = dimensions.stream().sorted(Comparator.comparing(AnalysisReportDimensionDTO::getViewKey)).collect(Collectors.toList());
        StringBuilder dimConditionBuilder = new StringBuilder();
        for (int i = 0; i < dimensions.size(); ++i) {
            AnalysisReportDimensionDTO dimensionDTO = (AnalysisReportDimensionDTO)dimensions.get(i);
            if (StringUtils.isEmpty((String)dimensionDTO.getCode()) || dimensionDTO.getViewKey().contains("@ORG")) continue;
            dimConditionBuilder.append("%");
            dimConditionBuilder.append(dimensionDTO.getViewKey()).append("@").append(dimensionDTO.getCode());
            if (i < dimensions.size() - 1) {
                dimConditionBuilder.append("|");
            }
            dimConditionBuilder.append("%");
        }
        return dimConditionBuilder.toString();
    }

    public static String buildDataDimensionsValueIngoreOrgDim(List<AnalysisReportDimensionDTO> dimensions) {
        if (CollectionUtils.isEmpty(dimensions)) {
            return null;
        }
        dimensions = dimensions.stream().sorted(Comparator.comparing(AnalysisReportDimensionDTO::getViewKey)).collect(Collectors.toList());
        StringBuilder dimConditionBuilder = new StringBuilder();
        for (int i = 0; i < dimensions.size(); ++i) {
            AnalysisReportDimensionDTO dimensionDTO = (AnalysisReportDimensionDTO)dimensions.get(i);
            if (StringUtils.isEmpty((String)dimensionDTO.getCode()) || dimensionDTO.getViewKey().contains("@ORG")) continue;
            dimConditionBuilder.append(dimensionDTO.getViewKey()).append("@").append(dimensionDTO.getCode());
            if (i >= dimensions.size() - 1) continue;
            dimConditionBuilder.append("|");
        }
        return dimConditionBuilder.toString();
    }

    public static ObjectInfo generatorMergeDocFileByRealTimeSubTemplate(HttpServletRequest request, AnalysisReportParseContextDTO context, String templateId, List<AnalysisTempAndRefOrgDTO> analysisTempAndRefOrgDTOS) throws Exception {
        if (CollectionUtils.isEmpty(analysisTempAndRefOrgDTOS)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5173\u8054\u7684\u62a5\u8868\u5206\u6790\u62a5\u544a\u6a21\u677f\u4fe1\u606f\u3002");
        }
        CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
        SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), cacheObjectResourceRemote);
        String templateRootPath = AnalysisReportUtils.buildTempFileRootPathByTemplate(templateId);
        File rootFile = new File(templateRootPath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        LOGGER.debug("\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a\u5b9e\u65f6\u7248\u672c\u751f\u6210\u8def\u5f84\u4e3a\uff1a" + templateRootPath);
        ZipSecureFile.setMinInflateRatio(-1.0);
        ArrayList<String> refTemplateDocumentFilePaths = new ArrayList<String>();
        for (int i = 0; i < analysisTempAndRefOrgDTOS.size(); ++i) {
            AnalysisTempAndRefOrgDTO analysisTempAndRefOrgDTO = analysisTempAndRefOrgDTOS.get(i);
            ReportGeneratorVO contextDTO = AnalysisReportUtils.buildNrWordEnvJsonNode(context, analysisTempAndRefOrgDTO);
            String lockCacheKey = LockCacheUtil.buildLockCacheKey((HttpServletRequest)request, (ReportGeneratorVO)contextDTO);
            NpContextHolder.getContext().getExtension("arvl").put("arvl", (Serializable)((Object)lockCacheKey));
            CustomXWPFDocument document = new WordUtil().createWord(contextDTO, (AsyncTaskMonitor)asyncTaskMonitor);
            String xwpfDocumentSubFilePath = templateRootPath + "partDoc" + i + ".doc";
            File subFile = new File(xwpfDocumentSubFilePath);
            boolean isNew = subFile.createNewFile();
            if (!isNew) {
                LOGGER.error("\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a\u5b50\u6a21\u677f\u7684word\u6587\u6863\uff0c\u8def\u5f84\u3010" + xwpfDocumentSubFilePath + "\u3011\u751f\u6210\u5931\u8d25");
            }
            FileOutputStream os = new FileOutputStream(subFile);
            document.write(os);
            os.close();
            refTemplateDocumentFilePaths.add(xwpfDocumentSubFilePath);
        }
        if (CollectionUtils.isEmpty(refTemplateDocumentFilePaths)) {
            return null;
        }
        ObjectInfo wordOssFile = AnalysisReportUtils.buildMergeDocByRefSubDocs(templateId, templateRootPath, refTemplateDocumentFilePaths);
        return wordOssFile;
    }

    private static ReportGeneratorVO buildNrWordEnvJsonNode(AnalysisReportParseContextDTO context, AnalysisTempAndRefOrgDTO analysisTempAndRefOrgDTO) throws JsonProcessingException {
        String taskKey = "";
        AnalysisTemp analysisTemp = analysisTempAndRefOrgDTO.getAnalysisTemp();
        String html = new String(Base64.getDecoder().decode(ConverterUtils.getAsString((Object)analysisTemp.getData(), (String)"")));
        if (!StringUtils.isEmpty((String)html)) {
            Document doc = Jsoup.parseBodyFragment((String)html);
            HashSet<String> formSchemeKeys = new HashSet<String>();
            Elements elements = doc.select("[var-formscheme-key]");
            for (Element element : elements) {
                formSchemeKeys.add(element.attr("var-formscheme-key"));
            }
            List taskKeys = formSchemeKeys.stream().filter(Objects::nonNull).map(formSchemeKey -> {
                IRuntimeFormSchemeService schemeService = (IRuntimeFormSchemeService)SpringContextUtils.getBean(IRuntimeFormSchemeService.class);
                FormSchemeDefine formScheme = schemeService.getFormScheme(formSchemeKey);
                return formScheme;
            }).filter(Objects::nonNull).map(FormSchemeDefine::getTaskKey).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskKeys)) {
                taskKey = (String)taskKeys.get(0);
            }
        }
        AnalysisReportParseContextDTO nrParseContextDTO = new AnalysisReportParseContextDTO();
        BeanUtils.copyProperties(context, nrParseContextDTO);
        AnalysisReportRefOrgDTO refOrgDTO = analysisTempAndRefOrgDTO.getRefOrgDTO();
        Optional<AnalysisReportParseContextDTO.ChooseUnit> orgViewDim = nrParseContextDTO.getChooseUnits().stream().filter(dimension -> dimension.getViewKey().contains("@ORG")).findFirst();
        String unitViewKey = null;
        if (orgViewDim.isPresent()) {
            unitViewKey = orgViewDim.get().getViewKey();
        }
        String finalUnitViewKey = unitViewKey;
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        List chooseUnits = nrParseContextDTO.getChooseUnits().stream().filter(chooseUnit -> {
            IEntityDefine iEntityDefine;
            if (chooseUnit.getViewKey().equals(finalUnitViewKey) && refOrgDTO != null && !StringUtils.isEmpty((String)refOrgDTO.getOrgId())) {
                chooseUnit.setCode(refOrgDTO.getOrgId());
                chooseUnit.setKey(refOrgDTO.getOrgId());
                chooseUnit.setTitle(refOrgDTO.getOrgTitle());
            }
            return (iEntityDefine = entityMetaService.queryEntity(chooseUnit.getViewKey())) != null;
        }).collect(Collectors.toList());
        ReportGeneratorVO reportGeneratorVO = new ReportGeneratorVO();
        List chooseUnitDims = chooseUnits.stream().map(unit -> {
            ReportBaseVO.UnitDim unitDim = new ReportBaseVO.UnitDim();
            BeanUtils.copyProperties(unit, unitDim);
            return unitDim;
        }).collect(Collectors.toList());
        ReportBaseVO.PeriodDim periodDim = new ReportBaseVO.PeriodDim();
        BeanUtils.copyProperties(nrParseContextDTO.getPeriods().get(0), periodDim);
        BeanUtils.copyProperties(nrParseContextDTO, reportGeneratorVO);
        reportGeneratorVO.setChooseUnits(chooseUnitDims);
        reportGeneratorVO.setKey(analysisTemp.getKey());
        reportGeneratorVO.setCurTimeStamp("");
        reportGeneratorVO.setVersionKey("");
        reportGeneratorVO.setPeriod(periodDim);
        return reportGeneratorVO;
    }

    public static ObjectInfo generatorRealTimeMergeDocFileBySubOssFileKeys(String templateId, List<String> ossFileKeys) throws Exception {
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        if (CollectionUtils.isEmpty(ossFileKeys)) {
            return null;
        }
        String rootPatemplateRootPath = AnalysisReportUtils.buildTempFileRootPathByTemplate(templateId);
        File rootFile = new File(rootPatemplateRootPath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        LOGGER.debug("\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a\u5b9e\u65f6\u7248\u672c\u751f\u6210\u4e34\u65f6\u672c\u5730\u8def\u5f84\u4e3a\uff1a" + rootPatemplateRootPath);
        ZipSecureFile.setMinInflateRatio(-1.0);
        ArrayList<String> refTemplateDocumentFilePaths = new ArrayList<String>();
        for (int i = 0; i < ossFileKeys.size(); ++i) {
            String docFileKey = ossFileKeys.get(i);
            CommonFileDTO file = commonFileService.queryOssFileByFileKey("ONLINE_OFFICE", docFileKey);
            if (file == null) {
                LOGGER.debug("\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a\u5b9e\u65f6\u7248\u672c\u751f\u6210\u6587\u4ef6\u65f6\uff0c\u5b50\u7ae0\u8282\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u5b50\u7ae0\u8282ossFileKey:" + docFileKey);
                continue;
            }
            String xwpfDocumentSubFilePath = rootPatemplateRootPath + "partDoc" + i + ".doc";
            File subFile = new File(xwpfDocumentSubFilePath);
            boolean isNew = subFile.createNewFile();
            if (!isNew) {
                LOGGER.error("\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a\u5b9e\u65f6\u7248\u672c\u6587\u4ef6\uff0c\u8def\u5f84\u3010" + xwpfDocumentSubFilePath + "\u3011\u751f\u6210\u5931\u8d25");
            }
            FileOutputStream os = new FileOutputStream(subFile);
            IOUtils.write(file.getBytes(), (OutputStream)os);
            os.close();
            refTemplateDocumentFilePaths.add(xwpfDocumentSubFilePath);
        }
        if (CollectionUtils.isEmpty(refTemplateDocumentFilePaths)) {
            return null;
        }
        ObjectInfo wordOssFile = AnalysisReportUtils.buildMergeDocByRefSubDocs(templateId, rootPatemplateRootPath, refTemplateDocumentFilePaths);
        return wordOssFile;
    }

    private static ObjectInfo buildMergeDocByRefSubDocs(String templateId, String templateRootPath, List<String> refTemplateDocumentFilePaths) throws Exception {
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        AnalysisReportTemplateService templateService = (AnalysisReportTemplateService)SpringContextUtils.getBean(AnalysisReportTemplateService.class);
        AnalysisReportEO analysisReportEO = templateService.queryTemplateByTemplateId(templateId);
        Objects.requireNonNull(analysisReportEO, "\u5f53\u524d\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0d\u5b58\u5728\u3002");
        File mainTempDocFile = new File(templateRootPath + "mergeTemp.doc");
        try (FileOutputStream out = new FileOutputStream(mainTempDocFile);){
            NiceXWPFDocument mainDoc = new NiceXWPFDocument((InputStream)new FileInputStream(refTemplateDocumentFilePaths.get(0)));
            for (int i = 1; i < refTemplateDocumentFilePaths.size(); ++i) {
                NiceXWPFDocument subDoc = new NiceXWPFDocument((InputStream)new FileInputStream(refTemplateDocumentFilePaths.get(i)));
                mainDoc = mainDoc.merge(subDoc);
                subDoc.close();
            }
            mainDoc.write((OutputStream)out);
            mainDoc.close();
        }
        catch (Exception e) {
            throw new Exception(e);
        }
        byte[] bytes = FileCopyUtils.copyToByteArray(mainTempDocFile);
        VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("analysisReportMultipartFile", analysisReportEO.getTitle().concat("\u6a21\u677f\u5206\u6790\u62a5\u544a\u5b9e\u65f6\u7248\u672c.doc"), "multipart/form-data; charset=ISO-8859-1", bytes);
        String fileKey = UUIDUtils.newUUIDStr();
        ObjectInfo wordOssFile = commonFileService.uploadFileToOss("ONLINE_OFFICE", (MultipartFile)multipartFile, fileKey);
        FileUtils.deleteDirectory(new File(templateRootPath));
        LOGGER.info("\u751f\u6210\u5206\u6790\u62a5\u544a\u6587\u6863\uff1a\u6a21\u677fID[".concat(templateId).concat("], \u672c\u5730\u8def\u5f84[").concat(templateRootPath).concat("], OSS\u5b58\u50a8KEY[").concat(fileKey).concat("]"));
        return wordOssFile;
    }

    private static String buildTempFileRootPathByTemplate(String templateId) {
        return ROOT_LOCATION + File.separator + "gcreport" + File.separator + "analysisReport" + File.separator + "tmp" + File.separator + templateId + "_" + LocalDate.now() + "_" + UUIDUtils.newUUIDStr() + File.separator;
    }

    public static String generatorWordOpenFileUrl(String docFileKey) {
        String wordUrl = "/api/gcreport/v1/common/onlineoffice/download/{docFileKey}".replace("{docFileKey}", docFileKey);
        LOGGER.info("\u751f\u6210\u5206\u6790\u62a5\u544a\u6587\u6863\u53ef\u8bbf\u95eeURL\uff1aOSS\u5b58\u50a8KEY[".concat(docFileKey).concat("], OSS\u5730\u5740\uff1a").concat(wordUrl));
        String token = ShiroUtil.getToken();
        wordUrl = wordUrl.concat("?JTOKENID=").concat(token);
        return wordUrl;
    }

    public static String generatorWordSaveFilePageUrl(ReqSaveAnalysisReportDataDocPageDTO paramsSaveAnalysisReportDataDocPageDTO) throws UnsupportedEncodingException {
        String token = ShiroUtil.getToken();
        String saveFilePageUrl = "/api/gcreport/v1/analysisreport/datamanage/saveAnalysisReportDataDocPage".concat("?JTOKENID=").concat(token).concat("&params=").concat(URLEncoder.encode(JsonUtils.writeValueAsString((Object)paramsSaveAnalysisReportDataDocPageDTO), Util.UTF_8.name()));
        LOGGER.info("\u751f\u6210\u5df2\u5b58\u7248\u672c\u5206\u6790\u62a5\u544a\u6570\u636e[ID:" + paramsSaveAnalysisReportDataDocPageDTO.getDataId() + "]\u5bf9\u5e94\u7684\u5206\u6587\u6863\u4fdd\u5b58URL\u5730\u5740:" + saveFilePageUrl);
        return saveFilePageUrl;
    }

    public static ObjectInfo uploadFileToOss(String docFileKey, String docFilename, byte[] fileBytes) {
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        VaParamSyncMultipartFile multipartFile = new VaParamSyncMultipartFile("multipartFile", docFilename, "multipart/form-data; charset=ISO-8859-1", fileBytes);
        commonFileService.deleteOssFile("ONLINE_OFFICE", docFileKey);
        ObjectInfo objectInfo = commonFileService.uploadFileToOss("ONLINE_OFFICE", (MultipartFile)multipartFile, docFileKey);
        return objectInfo;
    }

    public static void downloadOssFile(HttpServletRequest request, HttpServletResponse response, String docFileKey) {
        if (StringUtils.isEmpty((String)docFileKey)) {
            throw new BusinessRuntimeException("\u65e0\u5206\u6790\u62a5\u544a\u6587\u6863");
        }
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        commonFileService.downloadOssFile("ONLINE_OFFICE", request, response, docFileKey);
    }

    public static void downloadOssFileToPdf(HttpServletRequest request, HttpServletResponse response, String docFileKey) throws Exception {
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        CommonFileDTO fileDTO = commonFileService.queryOssFileByFileKey("ONLINE_OFFICE", docFileKey);
        if (fileDTO == null) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u4e0d\u5b58\u5728\u3002");
        }
        InputStream docInputStream = fileDTO.getInputStream();
        byte[] bytesPdf = ConvertUtil.convertToPdf((InputStream)docInputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOC);
        String mimeType = Optional.ofNullable(URLConnection.guessContentTypeFromName(docFileKey)).orElse(MediaType.ALL.getType());
        response.setHeader("Content-Type", mimeType + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + docFileKey);
        docInputStream.close();
        response.setContentLength(bytesPdf.length);
        response.getOutputStream().write(bytesPdf);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /*
     * Unable to fully structure code
     */
    public static void downloadZipFile(HttpServletRequest request, HttpServletResponse response, AnalysisReportDataExportExecutorParamDTO exportParamDTO, List<AnalysisReportDataEO> dataEOS) {
        commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        files = new ArrayList<E>();
        fileNames = new ArrayList<E>();
        fileType = null;
        var8_8 = exportParamDTO.getCurrType();
        var9_9 = -1;
        switch (var8_8.hashCode()) {
            case 3655434: {
                if (!var8_8.equals("word")) break;
                var9_9 = 0;
                break;
            }
            case 110834: {
                if (!var8_8.equals("pdf")) break;
                var9_9 = 1;
            }
        }
        switch (var9_9) {
            case 0: {
                fileType = ".doc";
                break;
            }
            case 1: {
                fileType = ".pdf";
                break;
            }
        }
        finalFileType = fileType;
        dataEOS.stream().forEach((Consumer<AnalysisReportDataEO>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$downloadZipFile$4(com.jiuqi.common.file.service.CommonFileService java.util.List java.util.List java.lang.String com.jiuqi.gcreport.analysisreport.entity.AnalysisReportDataEO ), (Lcom/jiuqi/gcreport/analysisreport/entity/AnalysisReportDataEO;)V)((CommonFileService)commonFileService, files, fileNames, (String)finalFileType));
        if (CollectionUtils.isEmpty(files)) {
            throw new BusinessRuntimeException("\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u5bfc\u51fa\u3002");
        }
        zipName = "\u5206\u6790\u62a5\u544a\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6" + DateUtils.nowDateStr();
        try {
            outputStream = response.getOutputStream();
            var11_13 = null;
            try {
                zos = new ZipOutputStream((OutputStream)outputStream, Charset.forName("GBK"));
                var13_17 = null;
                try {
                    response.setHeader("content-type", "application/octet-stream");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String(zipName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
                    response.setCharacterEncoding("UTF-8");
                    buf = new byte[1024];
                    for (i = 0; i < files.size(); ++i) {
                        zipEntry = new ZipEntry((String)fileNames.get(i));
                        zos.putNextEntry(zipEntry);
                        try {
                            inputStream = ((CommonFileDTO)files.get(i)).getInputStream();
                            var18_25 = null;
                            try {
                                var19_26 = exportParamDTO.getCurrType();
                                var20_29 = -1;
                                switch (var19_26.hashCode()) {
                                    case 3655434: {
                                        if (!var19_26.equals("word")) break;
                                        var20_29 = 0;
                                        break;
                                    }
                                    case 110834: {
                                        if (!var19_26.equals("pdf")) break;
                                        var20_29 = 1;
                                    }
                                }
                                switch (var20_29) {
                                    case 0: {
                                        while ((len = inputStream.read(buf)) != -1) {
                                            zos.write(buf, 0, len);
                                        }
                                        break;
                                    }
                                    case 1: {
                                        bytesPdf = ConvertUtil.convertToPdf((InputStream)inputStream, (ConvertUtil.FILE_TYPE_TO_PDF)ConvertUtil.FILE_TYPE_TO_PDF.DOC);
                                        zos.write(bytesPdf, 0, bytesPdf.length);
                                        ** break;
lbl66:
                                        // 1 sources

                                        break;
                                    }
                                    ** default:
lbl68:
                                    // 1 sources

                                    break;
                                }
                            }
                            catch (Throwable var19_28) {
                                var18_25 = var19_28;
                                throw var19_28;
                            }
                            finally {
                                if (inputStream != null) {
                                    if (var18_25 != null) {
                                        try {
                                            inputStream.close();
                                        }
                                        catch (Throwable var19_27) {
                                            var18_25.addSuppressed(var19_27);
                                        }
                                    } else {
                                        inputStream.close();
                                    }
                                }
                            }
                        }
                        catch (IOException e) {
                            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                        }
                        zos.closeEntry();
                    }
                }
                catch (Throwable var14_20) {
                    var13_17 = var14_20;
                    throw var14_20;
                }
                finally {
                    if (zos != null) {
                        if (var13_17 != null) {
                            try {
                                zos.close();
                            }
                            catch (Throwable var14_19) {
                                var13_17.addSuppressed(var14_19);
                            }
                        } else {
                            zos.close();
                        }
                    }
                }
            }
            catch (Throwable var12_16) {
                var11_13 = var12_16;
                throw var12_16;
            }
            finally {
                if (outputStream != null) {
                    if (var11_13 != null) {
                        try {
                            outputStream.close();
                        }
                        catch (Throwable var12_15) {
                            var11_13.addSuppressed(var12_15);
                        }
                    } else {
                        outputStream.close();
                    }
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    public static void deleteFileToOss(Set<String> docFileKeys) {
        if (CollectionUtils.isEmpty(docFileKeys)) {
            return;
        }
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        docFileKeys.forEach(docFileKey -> commonFileService.deleteOssFile("ONLINE_OFFICE", docFileKey));
    }

    public static ObjectInfo copyAsUploadOssFile(String srcFileKey, String targetFileKey) {
        if (StringUtils.isEmpty((String)srcFileKey)) {
            return null;
        }
        CommonFileService commonFileService = (CommonFileService)SpringContextUtils.getBean(CommonFileService.class);
        CommonFileDTO commonFileDTO = commonFileService.queryOssFileByFileKey("ONLINE_OFFICE", srcFileKey);
        if (commonFileDTO == null) {
            return null;
        }
        ObjectInfo objectInfo = commonFileService.uploadFileToOss("ONLINE_OFFICE", (MultipartFile)commonFileDTO, targetFileKey);
        return objectInfo;
    }

    public static String getMergeTemplateUnResolvedContent(String templateId) {
        Objects.requireNonNull(templateId, "\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        AnalysisReportTemplateService templateService = (AnalysisReportTemplateService)SpringContextUtils.getBean(AnalysisReportTemplateService.class);
        List<AnalysisTempAndRefOrgDTO> analysisTempAndRefOrgDTOS = templateService.queryAnalysisTempsByTemplateId(templateId);
        StringBuilder mergeTemplateUnResolvedContentBuilder = new StringBuilder();
        mergeTemplateUnResolvedContentBuilder.append("<html><head></head><body> \n");
        analysisTempAndRefOrgDTOS.stream().forEach(analysisTempAndRefOrgDTO -> {
            String unResolvedTemplateContent = analysisTempAndRefOrgDTO.getAnalysisTemp().getData();
            unResolvedTemplateContent = unResolvedTemplateContent.replace("<html>", "").replace("</html>", "").replace("<body>", "").replace("</body>", "").replace("<head>", "").replace("</head>", "");
            mergeTemplateUnResolvedContentBuilder.append(unResolvedTemplateContent).append("<br/>");
        });
        mergeTemplateUnResolvedContentBuilder.append("\n </body></html>");
        String mergeTemplateContent = mergeTemplateUnResolvedContentBuilder.toString();
        return mergeTemplateContent;
    }

    public static String getMergeTemplateResolvedContent(HttpServletRequest request, HttpServletResponse response, String templateId, AnalysisReportParseContextDTO parseContextDTO) throws Exception {
        Objects.requireNonNull(templateId, "\u5206\u6790\u62a5\u544a\u6a21\u677f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        SaveAnalysisController saveAnalysisController = (SaveAnalysisController)SpringContextUtils.getBean(SaveAnalysisController.class);
        AnalysisReportTemplateService templateService = (AnalysisReportTemplateService)SpringContextUtils.getBean(AnalysisReportTemplateService.class);
        List<AnalysisTempAndRefOrgDTO> analysisTempAndRefOrgDTOS = templateService.queryAnalysisTempsByTemplateId(templateId);
        StringBuilder mergeTemplateResolvedContentBuilder = new StringBuilder();
        mergeTemplateResolvedContentBuilder.append("<html><head></head><body> \n");
        analysisTempAndRefOrgDTOS.stream().forEach(analysisTempAndRefOrgDTO -> {
            try {
                parseContextDTO.setKey(analysisTempAndRefOrgDTO.getAnalysisTemp().getKey());
                ReportGeneratorVO reportGeneratorVO = new ReportGeneratorVO();
                BeanUtils.copyProperties(parseContextDTO, reportGeneratorVO);
                ReturnObject returnObject = saveAnalysisController.analysisReportGenerator(reportGeneratorVO, response, request);
                AnalysisTemp resolvedAnalysisTemp = (AnalysisTemp)returnObject.getObj();
                String resolvedTemplateContent = resolvedAnalysisTemp.getData();
                resolvedTemplateContent = resolvedTemplateContent.replace("<html>", "").replace("</html>", "").replace("<body>", "").replace("</body>", "").replace("<head>", "").replace("</head>", "");
                mergeTemplateResolvedContentBuilder.append(resolvedTemplateContent).append("<br/>");
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                mergeTemplateResolvedContentBuilder.append("<span>\u6a21\u677f\u89e3\u6790\u5931\u8d25\uff0c\u8be6\u60c5\uff1a").append(e.getMessage()).append("</span>").append("</span>").append("<br/>");
            }
        });
        mergeTemplateResolvedContentBuilder.append("\n </body></html>");
        return mergeTemplateResolvedContentBuilder.toString();
    }

    public static List<AnalysisReportDTO> findAllLeafNodesByParentId(String parentId, AnalysisReportDTO root) {
        ArrayList<AnalysisReportDTO> result = new ArrayList<AnalysisReportDTO>();
        List children = root.getChildren();
        if (children != null && !children.isEmpty()) {
            for (AnalysisReportDTO child : children) {
                List<AnalysisReportDTO> childLeafNodes;
                if (child.getParentId() == null || !child.getParentId().equals(parentId) || (childLeafNodes = AnalysisReportUtils.findAllLeafNodesByParentId(child.getId(), child)).isEmpty()) continue;
                result.addAll(childLeafNodes);
            }
        } else if (root.getParentId() != null && root.getParentId().equals(parentId)) {
            result.add(root);
        }
        return result;
    }

    public static AnalysisReportDTO findNodeById(String id, AnalysisReportDTO root) {
        if (root.getId().equals(id)) {
            return root;
        }
        List children = root.getChildren();
        if (children != null && !children.isEmpty()) {
            for (AnalysisReportDTO child : children) {
                AnalysisReportDTO result = AnalysisReportUtils.findNodeById(id, child);
                if (result == null) continue;
                return result;
            }
        }
        return null;
    }

    private static /* synthetic */ void lambda$downloadZipFile$4(CommonFileService commonFileService, List files, List fileNames, String finalFileType, AnalysisReportDataEO dataEO) {
        String docFileKey = dataEO.getDocFileKey();
        if (StringUtils.isEmpty((String)docFileKey)) {
            return;
        }
        CommonFileDTO file = commonFileService.queryOssFileByFileKey("ONLINE_OFFICE", docFileKey);
        if (file == null) {
            return;
        }
        files.add(file);
        fileNames.add(dataEO.getTemplateTitle() + "_" + dataEO.getVersionName() + finalFileType);
    }
}

