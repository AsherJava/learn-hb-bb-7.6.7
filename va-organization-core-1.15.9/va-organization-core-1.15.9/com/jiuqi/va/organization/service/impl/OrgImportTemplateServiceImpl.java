/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.DataFormat
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.usermodel.WorkbookFactory
 *  org.apache.poi.util.IOUtils
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.organization.service.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.organization.common.OrgAsyncTask;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.common.OrgDataCacheUtil;
import com.jiuqi.va.organization.common.OrgDataImportTemplate;
import com.jiuqi.va.organization.common.OrgExcelUtils;
import com.jiuqi.va.organization.dao.VaOrgImportTemplateDao;
import com.jiuqi.va.organization.domain.OrgDataImportProcess;
import com.jiuqi.va.organization.domain.OrgExcelColumn;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgImportTemplateService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrgImportTemplateServiceImpl
implements OrgImportTemplateService {
    private static Logger logger = LoggerFactory.getLogger(OrgImportTemplateServiceImpl.class);
    @Autowired
    private VaOrgImportTemplateDao orgImportTemplateDao;
    @Autowired
    private OrgAsyncTask orgAsyncTask;
    @Autowired
    private OrgCategoryService orgCategoryService;

    @Override
    public List<OrgImportTemplateDO> list(OrgImportTemplateDTO orgImportTemplateDTO) {
        return this.orgImportTemplateDao.list(orgImportTemplateDTO);
    }

    @Override
    public int add(OrgImportTemplateDTO orgImportTemplateDTO) {
        orgImportTemplateDTO.setId(UUID.randomUUID());
        orgImportTemplateDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        orgImportTemplateDTO.setTemplatedata(JSONUtil.toJSONString((Object)orgImportTemplateDTO.getTemplatedata()));
        return this.orgImportTemplateDao.add(orgImportTemplateDTO);
    }

    @Override
    public int update(OrgImportTemplateDTO orgImportTemplateDTO) {
        orgImportTemplateDTO.setTemplatedata(JSONUtil.toJSONString((Object)orgImportTemplateDTO.getTemplatedata()));
        return this.orgImportTemplateDao.update(orgImportTemplateDTO);
    }

    @Override
    public int delete(OrgImportTemplateDTO orgImportTemplateDTO) {
        return this.orgImportTemplateDao.delete(orgImportTemplateDTO);
    }

    @Override
    public void exportTemplate(OrgImportTemplateDTO param) {
        this.export(param, 0);
    }

    @Override
    public void exportData(OrgImportTemplateDTO param) {
        this.export(param, 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void export(OrgImportTemplateDTO param, int exportType) {
        OrgImportTemplateDO template = param.getTempImportTemplate();
        if (param.getId() != null) {
            OrgImportTemplateDTO templateParam = new OrgImportTemplateDTO();
            templateParam.setId(param.getId());
            template = this.orgImportTemplateDao.list(templateParam).get(0);
        }
        OrgCategoryDO categoryParam = new OrgCategoryDO();
        categoryParam.setName(template.getCode());
        categoryParam.setDeepClone(false);
        OrgCategoryDO define = this.orgCategoryService.get(categoryParam);
        String fileName = define.getTitle() + "_" + template.getName();
        Workbook workbook = null;
        try (OutputStream outputStream = RequestContextUtil.getOutputStream();){
            if (exportType == 0) {
                workbook = OrgExcelUtils.getTemplateExcel(template, null);
                fileName = fileName + "(" + OrgCoreI18nUtil.getMessage("org.consts.template", new Object[0]) + ")";
            } else if (exportType == 1) {
                workbook = OrgExcelUtils.getTemplateExcel(template, param);
            }
            RequestContextUtil.setResponseContentType((String)"application/vnd.ms-excel;charset=utf-8");
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName + ".xlsx", "UTF-8")));
            if (workbook != null) {
                workbook.write(outputStream);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (workbook != null) {
                try {
                    workbook.close();
                }
                catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                if (workbook instanceof SXSSFWorkbook) {
                    ((SXSSFWorkbook)workbook).dispose();
                }
            }
        }
    }

    @Override
    public void importCheck(OrgImportTemplateDTO template, MultipartFile multipartFile) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(FileCopyUtils.copyToByteArray(multipartFile.getInputStream()));
            Locale locale = LocaleContextHolder.getLocale();
            UserLoginDTO user = ShiroUtil.getUser();
            this.orgAsyncTask.execute(() -> {
                try {
                    LocaleContextHolder.setLocale(locale);
                    ShiroUtil.bindUser((UserLoginDTO)user);
                    this.importCheckAsync(template, inputStream);
                }
                finally {
                    ShiroUtil.unbindUser();
                }
            });
        }
        catch (Exception e) {
            String key = template.getResultKey();
            String info = JSONUtil.toJSONString((Object)R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.upload", new Object[0])));
            OrgDataCacheUtil.setImportDataResult(key, info);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void importCheckAsync(OrgImportTemplateDTO template, InputStream inputStream) {
        boolean complete = false;
        int state = 0;
        String key = template.getResultKey();
        OrgImportTemplateDO targetTemplate = template.getTempImportTemplate();
        if (targetTemplate == null) {
            targetTemplate = this.orgImportTemplateDao.list(template).get(0);
        }
        List templateDataList = JSONUtil.parseMapArray((String)targetTemplate.getTemplatedata().toString());
        Workbook workbook = null;
        try {
            int currIndex = 2;
            OrgDataImportProcess processInfo = new OrgDataImportProcess();
            processInfo.setRsKey(key);
            processInfo.setTotal(2 + templateDataList.size() * 3);
            state = 1;
            IOUtils.setByteArrayMaxOverride((int)Integer.MAX_VALUE);
            workbook = WorkbookFactory.create((InputStream)inputStream);
            processInfo.setCurrIndex(currIndex++);
            OrgDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
            HashMap<String, List<OrgDTO>> res = new HashMap<String, List<OrgDTO>>();
            Map templateData = null;
            String categoryname = null;
            List fields = null;
            Integer firstLine = null;
            List<OrgExcelColumn> excelCols = null;
            OrgDataImportTemplate importTemplate = null;
            for (int i = 0; i < templateDataList.size(); ++i) {
                templateData = (Map)templateDataList.get(i);
                categoryname = templateData.get(TemplateFields.categoryname.toString()).toString();
                fields = (List)templateData.get(TemplateFields.fields.toString());
                firstLine = (Integer)templateData.get(TemplateFields.firstline.toString());
                firstLine = firstLine == null ? 2 : firstLine;
                state = 2;
                excelCols = OrgExcelUtils.getExcelColumnsByTemplateColList(categoryname, fields);
                OrgExcelUtils.checkHeader(workbook.getSheetAt(i), excelCols, firstLine);
                state = 3;
                processInfo.setCurrIndex(currIndex++);
                OrgDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
                importTemplate = new OrgDataImportTemplate(workbook.getSheetAt(i), categoryname, (Date)template.getVersionDate(), excelCols, firstLine);
                importTemplate.executeImportCheck();
                res.put(categoryname, importTemplate.getImportDatas());
                processInfo.setCurrIndex(currIndex++);
                OrgDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
            }
            R importRes = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])).put("data", res);
            OrgDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)importRes));
            complete = true;
            state = 4;
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5931\u8d25", e);
        }
        finally {
            if (!complete) {
                String result = null;
                if (state == 0) {
                    result = JSONUtil.toJSONString((Object)R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.data.exception", new Object[0])));
                } else if (state == 1) {
                    result = JSONUtil.toJSONString((Object)R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.use.template", new Object[0])));
                } else if (state == 2) {
                    result = JSONUtil.toJSONString((Object)R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.column.not.same", new Object[0])));
                } else if (state == 3) {
                    result = JSONUtil.toJSONString((Object)R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import", new Object[0])));
                }
                OrgDataCacheUtil.setImportDataResult(key, result);
            }
            if (workbook != null) {
                try {
                    workbook.close();
                }
                catch (IOException iOException) {}
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    @Override
    public void importSave(OrgImportTemplateDTO template) {
        Locale locale = LocaleContextHolder.getLocale();
        UserLoginDTO user = ShiroUtil.getUser();
        this.orgAsyncTask.execute(() -> {
            try {
                LocaleContextHolder.setLocale(locale);
                ShiroUtil.bindUser((UserLoginDTO)user);
                this.importSaveAsync(template);
            }
            finally {
                ShiroUtil.unbindUser();
            }
        });
    }

    private void importSaveAsync(OrgImportTemplateDTO template) {
        String key = template.getResultKey();
        try {
            OrgImportTemplateDO targetTemplate = template.getTempImportTemplate();
            if (targetTemplate == null) {
                targetTemplate = this.orgImportTemplateDao.list(template).get(0);
            }
            List templateDataList = JSONUtil.parseMapArray((String)targetTemplate.getTemplatedata().toString());
            Map<String, List<OrgDTO>> importDataMap = template.getImportDataMap();
            int total = 0;
            for (int i = 0; i < templateDataList.size(); ++i) {
                Map templateData = (Map)templateDataList.get(i);
                String tablename = templateData.get(TemplateFields.categoryname.toString()).toString();
                total += importDataMap.get(tablename).size();
            }
            OrgDataImportProcess processInfo = new OrgDataImportProcess();
            processInfo.setRsKey(key);
            processInfo.setTotal(total + 30);
            processInfo.setCurrIndex(30);
            OrgDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
            HashMap<String, List<OrgDTO>> res = new HashMap<String, List<OrgDTO>>();
            Map templateData = null;
            String categoryname = null;
            List fields = null;
            List<OrgExcelColumn> excelCols = null;
            List<OrgDTO> importDatas = null;
            OrgDataImportTemplate importTemplate = null;
            for (int i = 0; i < templateDataList.size(); ++i) {
                templateData = (Map)templateDataList.get(i);
                categoryname = templateData.get(TemplateFields.categoryname.toString()).toString();
                fields = (List)templateData.get(TemplateFields.fields.toString());
                excelCols = OrgExcelUtils.getExcelColumnsByTemplateColList(categoryname, fields);
                importDatas = template.getImportDataMap().get(categoryname);
                importTemplate = new OrgDataImportTemplate(categoryname, (Date)template.getVersionDate(), excelCols, importDatas);
                importTemplate.setProcessInfo(processInfo);
                importTemplate.executeImportSave();
                res.put(categoryname, importTemplate.getImportResult());
            }
            R importRes = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])).put("data", res);
            OrgDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)importRes));
        }
        catch (Throwable e) {
            logger.error("\u5bfc\u5165\u5931\u8d25", e);
            String result = JSONUtil.toJSONString((Object)R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.save", new Object[0])));
            OrgDataCacheUtil.setImportDataResult(key, result);
        }
    }

    @Override
    public R getImportResult(OrgImportTemplateDTO template) {
        if (template == null || !StringUtils.hasText(template.getResultKey())) {
            return R.error((int)1, (String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing", new Object[0]));
        }
        String resString = OrgDataCacheUtil.getImportDataResult(template.getResultKey());
        if (!StringUtils.hasText(resString)) {
            return R.error((int)2, (String)OrgCoreI18nUtil.getMessage("org.error.template.import.no.result", new Object[0]));
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])).put("result", (Object)JSONUtil.parseObject((String)resString));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exportResultInfo(OrgImportTemplateDTO template) {
        UUID templateId = template.getId();
        OrgImportTemplateDTO param = new OrgImportTemplateDTO();
        param.setId(templateId);
        OrgImportTemplateDO targetTemplate = template.getTempImportTemplate();
        if (targetTemplate == null) {
            targetTemplate = this.orgImportTemplateDao.list(template).get(0);
        }
        List templateDataList = JSONUtil.parseMapArray((String)targetTemplate.getTemplatedata().toString());
        ArrayList<String> tableTitles = new ArrayList<String>();
        for (int i = 0; i < templateDataList.size(); ++i) {
            Map templateData = (Map)templateDataList.get(i);
            String tablename = templateData.get(TemplateFields.categoryname.toString()).toString();
            tableTitles.add(OrgExcelUtils.getDataModalDefine(tablename).getTitle());
        }
        List tablesResultInfo = (List)template.getTemplatedata();
        List<Object> tablesField = template.getImportFields();
        XSSFWorkbook workbook = new XSSFWorkbook();
        try (OutputStream outputStream = RequestContextUtil.getOutputStream();){
            for (int tableCount = 0; tableCount < tablesResultInfo.size(); ++tableCount) {
                int i;
                List tableFields = (List)tablesField.get(tableCount);
                List resultInfos = (List)tablesResultInfo.get(tableCount);
                Sheet sheet = workbook.createSheet((String)tableTitles.get(tableCount));
                int defaultWidth = 2560;
                Row row = sheet.createRow(0);
                row.setHeightInPoints(20.0f);
                Font font = workbook.createFont();
                font.setFontHeightInPoints((short)12);
                CellStyle headStyle = workbook.createCellStyle();
                headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SEA_GREEN.getIndex());
                headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                headStyle.setAlignment(HorizontalAlignment.LEFT);
                headStyle.setFont(font);
                headStyle.setFont(font);
                Font redFont = workbook.createFont();
                redFont.setColor((short)10);
                CellStyle indexStyle = workbook.createCellStyle();
                indexStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                indexStyle.setAlignment(HorizontalAlignment.CENTER);
                CellStyle redIndexStyle = workbook.createCellStyle();
                redIndexStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                redIndexStyle.setAlignment(HorizontalAlignment.CENTER);
                redIndexStyle.setFont(redFont);
                CellStyle dateStyle = null;
                String dateformat = "yyyy/MM/dd";
                dateStyle = workbook.createCellStyle();
                DataFormat dateFormatter = workbook.createDataFormat();
                dateStyle.setDataFormat(dateFormatter.getFormat(dateformat));
                CellStyle redDateStyle = null;
                redDateStyle = workbook.createCellStyle();
                redDateStyle.setDataFormat(dateFormatter.getFormat(dateformat));
                redDateStyle.setFont(redFont);
                CellStyle timeStyle = null;
                timeStyle = workbook.createCellStyle();
                DataFormat timeFormatter = workbook.createDataFormat();
                timeStyle.setDataFormat(timeFormatter.getFormat("yyyy/MM/dd hh:MM:ss"));
                CellStyle redTimeStyle = null;
                redTimeStyle = workbook.createCellStyle();
                redTimeStyle.setDataFormat(timeFormatter.getFormat("yyyy/MM/dd hh:MM:ss"));
                redTimeStyle.setFont(redFont);
                CellStyle redStyle = workbook.createCellStyle();
                redStyle.setFont(redFont);
                HashMap<String, String> indexField = new HashMap<String, String>();
                indexField.put("title", OrgCoreI18nUtil.getMessage("org.consts.serialNumber", new Object[0]));
                indexField.put("name", "resIndex");
                tableFields.add(0, indexField);
                for (i = 0; i < tableFields.size(); ++i) {
                    Map field = (Map)tableFields.get(i);
                    sheet.setColumnWidth(i, defaultWidth);
                    Cell cell = row.createCell(i);
                    cell.setCellValue(field.get("title") != null ? field.get("title").toString() : "");
                    cell.setCellStyle(headStyle);
                }
                if (resultInfos == null || resultInfos.isEmpty()) continue;
                for (i = 0; i < resultInfos.size(); ++i) {
                    LinkedHashMap resultInfo = (LinkedHashMap)resultInfos.get(i);
                    Map oldValueMap = (Map)resultInfo.get("oldValueMap");
                    if (oldValueMap != null) {
                        resultInfo.putAll(oldValueMap);
                    }
                    row = sheet.createRow(i + 1);
                    boolean isRed = (Integer)resultInfo.get("importstate") != 0;
                    for (int j = 0; j < tableFields.size(); ++j) {
                        Object value;
                        Map field = (Map)tableFields.get(j);
                        Cell cell = row.createCell(j);
                        if (isRed) {
                            cell.setCellStyle(redStyle);
                        }
                        if (field.get("name") != null && field.get("name").toString().equals("resIndex")) {
                            cell.setCellValue((double)(i + 1));
                            cell.setCellStyle(isRed ? redIndexStyle : indexStyle);
                            continue;
                        }
                        if (field.get("name") != null && resultInfo.get(field.get("name").toString().toLowerCase()) != null) {
                            cell.setCellValue(resultInfo.get(field.get("name").toString().toLowerCase()).toString());
                        } else {
                            cell.setCellValue("");
                        }
                        if ("DATE".equals(field.get("type"))) {
                            value = resultInfo.get(field.get("name").toString().toLowerCase());
                            if (value instanceof Long) {
                                cell.setCellStyle(isRed ? redDateStyle : dateStyle);
                                cell.setCellValue(new Date((Long)value));
                                continue;
                            }
                            cell.setCellValue((String)value);
                            continue;
                        }
                        if (!"TIMESTAMP".equals(field.get("type"))) continue;
                        value = resultInfo.get(field.get("name").toString().toLowerCase());
                        if (value instanceof Long) {
                            cell.setCellStyle(isRed ? redTimeStyle : timeStyle);
                            cell.setCellValue(new Date((Long)value));
                            continue;
                        }
                        cell.setCellValue((String)value);
                    }
                }
            }
            RequestContextUtil.setResponseContentType((String)"application/vnd.ms-excel;charset=utf-8");
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode("result.xlsx", "UTF-8")));
            workbook.write(outputStream);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            try {
                workbook.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static enum TemplateFields {
        tablename,
        title,
        firstline,
        dateformat,
        column,
        fields,
        name,
        type,
        reftable,
        reffield,
        checkval,
        importstate,
        importmemo,
        categoryname;

    }
}

