/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.BaseDataAction
 *  com.jiuqi.va.feign.util.LogUtil
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
package com.jiuqi.va.basedata.service.impl;

import com.jiuqi.va.basedata.common.BaseDataAsyncTask;
import com.jiuqi.va.basedata.common.BaseDataCacheUtil;
import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.BaseDataImportTemplate;
import com.jiuqi.va.basedata.common.BasedataExcelUtils;
import com.jiuqi.va.basedata.dao.VaBaseDataImportTemplateDao;
import com.jiuqi.va.basedata.domain.BaseDataExcleColumn;
import com.jiuqi.va.basedata.domain.BaseDataImportProcess;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDO;
import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDTO;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataImportTemplateService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.BaseDataAction;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Primary
@Service(value="vaBaseDataImportTemplateServiceImpl")
public class BaseDataImportTemplateServiceImpl
implements BaseDataImportTemplateService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataImportTemplateServiceImpl.class);
    @Autowired
    private VaBaseDataImportTemplateDao baseDataImportTemplateDao;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private BaseDataAsyncTask baseDataAsyncTask;
    @Autowired
    private BaseDataParamService baseDataParamService;

    @Override
    public boolean exist(BaseDataImportTemplateDTO template) {
        List<BaseDataImportTemplateDO> res = this.baseDataImportTemplateDao.list(template);
        return res != null && !res.isEmpty();
    }

    @Override
    public int add(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        baseDataImportTemplateDTO.setId(UUID.randomUUID());
        if (baseDataImportTemplateDTO.getFixed() == null) {
            baseDataImportTemplateDTO.setFixed(0);
        }
        baseDataImportTemplateDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        baseDataImportTemplateDTO.setTemplatedata(JSONUtil.toJSONString((Object)baseDataImportTemplateDTO.getTemplatedata()));
        return this.baseDataImportTemplateDao.add(baseDataImportTemplateDTO);
    }

    @Override
    public int update(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        baseDataImportTemplateDTO.setTemplatedata(JSONUtil.toJSONString((Object)baseDataImportTemplateDTO.getTemplatedata()));
        return this.baseDataImportTemplateDao.update(baseDataImportTemplateDTO);
    }

    @Override
    public int delete(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        return this.baseDataImportTemplateDao.delete(baseDataImportTemplateDTO);
    }

    @Override
    public List<BaseDataImportTemplateDO> list(BaseDataImportTemplateDTO baseDataImportTemplateDTO) {
        return this.baseDataImportTemplateDao.list(baseDataImportTemplateDTO);
    }

    @Override
    public void exportTemplate(BaseDataImportTemplateDTO param) {
        this.export(param, 0);
    }

    @Override
    public void exportData(BaseDataImportTemplateDTO param) {
        this.export(param, 1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void export(BaseDataImportTemplateDTO param, int exportType) {
        if (param.getId() != null) {
            BaseDataImportTemplateDO template = this.baseDataImportTemplateDao.list(param).get(0);
            BeanUtils.copyProperties((Object)template, (Object)param);
        }
        BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
        defineParam.setName(param.getCode());
        defineParam.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO define = this.baseDataDefineService.get(defineParam);
        BaseDataContext.bindRealDataFlag((Boolean)false);
        String fileName = (define.getTitle() + "_" + param.getName()).replace("/", "_").replace("\\", "_");
        List templateDataList = JSONUtil.parseMapArray((String)param.getTemplatedata().toString());
        Workbook workbook = null;
        try (OutputStream outputStream = RequestContextUtil.getOutputStream();){
            BaseDataBatchOptDTO batchOptDTO = new BaseDataBatchOptDTO();
            BaseDataDTO basedataParam = new BaseDataDTO();
            basedataParam.setTableName((String)((Map)templateDataList.get(0)).get(TemplateFields.tablename.toString()));
            basedataParam.put("_exportExcel", (Object)true);
            basedataParam.setUnitcode(param.getUnitcode());
            basedataParam.put("rootCode", (Object)param.getRootCode());
            basedataParam.put("showStopped", (Object)param.getShowStopped());
            basedataParam.put("templateDataList", (Object)templateDataList);
            HashMap<String, List<BaseDataDO>> dataListMap = null;
            if (exportType == 1) {
                dataListMap = new HashMap<String, List<BaseDataDO>>();
                basedataParam.put("dataListMap", dataListMap);
            }
            batchOptDTO.setQueryParam(basedataParam);
            this.baseDataParamService.loadBatchOptExtendParam(batchOptDTO, BaseDataAction.CHECK);
            String[] tableNames = new String[templateDataList.size()];
            HashMap<String, List<BaseDataExcleColumn>> columnListMap = new HashMap<String, List<BaseDataExcleColumn>>();
            for (int i = 0; i < templateDataList.size(); ++i) {
                Map templateData = (Map)templateDataList.get(i);
                tableNames[i] = (String)templateData.get(TemplateFields.tablename.toString());
                List fields = (List)templateData.get(TemplateFields.fields.toString());
                columnListMap.put(tableNames[i], BasedataExcelUtils.getExcelColumnsByTemplateColList(tableNames[i], fields));
            }
            if (exportType == 0) {
                workbook = BasedataExcelUtils.getTemplateExcel(tableNames, columnListMap);
                fileName = fileName + "(" + BaseDataCoreI18nUtil.getMessage("basedata.consts.template", new Object[0]) + ")";
            } else if (exportType == 1) {
                workbook = BasedataExcelUtils.getDataExcel(tableNames, columnListMap, dataListMap, param);
                fileName = fileName + '_' + RequestContextUtil.getParameter((String)"timestamp");
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
            BaseDataContext.unbindRealDataFlag();
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
    public void importCheck(BaseDataImportTemplateDTO template, MultipartFile multipartFile) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(FileCopyUtils.copyToByteArray(multipartFile.getInputStream()));
            Locale locale = LocaleContextHolder.getLocale();
            UserLoginDTO user = ShiroUtil.getUser();
            this.baseDataAsyncTask.execute(() -> {
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
            String info = JSONUtil.toJSONString((Object)R.error((String)"\u6587\u4ef6\u4e0a\u4f20\u5f02\u5e38"));
            BaseDataCacheUtil.setImportDataResult(key, info);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void importCheckAsync(BaseDataImportTemplateDTO template, InputStream inputStream) {
        boolean complete = false;
        int state = 0;
        String key = template.getResultKey();
        BaseDataImportTemplateDO targetTemplate = template.getTempImportTemplate();
        if (targetTemplate == null) {
            targetTemplate = this.baseDataImportTemplateDao.list(template).get(0);
        }
        List templateDataList = JSONUtil.parseMapArray((String)targetTemplate.getTemplatedata().toString());
        String mainTableName = (String)((Map)templateDataList.get(0)).get(TemplateFields.tablename.toString());
        BaseDataBatchOptDTO batchOptDTO = new BaseDataBatchOptDTO();
        BaseDataDTO basedataParam = new BaseDataDTO();
        basedataParam.setTableName(mainTableName);
        basedataParam.setUnitcode(template.getUnitcode());
        basedataParam.put("_UpdateExcelCheck", (Object)true);
        basedataParam.put("templateDataList", (Object)templateDataList);
        batchOptDTO.setQueryParam(basedataParam);
        this.baseDataParamService.loadBatchOptExtendParam(batchOptDTO, BaseDataAction.CHECK);
        Workbook workbook = null;
        try {
            int currIndex = 2;
            BaseDataImportProcess processInfo = new BaseDataImportProcess();
            processInfo.setRsKey(key);
            processInfo.setTotal(2 + templateDataList.size() * 3);
            state = 1;
            IOUtils.setByteArrayMaxOverride((int)Integer.MAX_VALUE);
            workbook = WorkbookFactory.create((InputStream)inputStream);
            processInfo.setCurrIndex(currIndex++);
            BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
            HashMap<String, List<BaseDataDTO>> res = new HashMap<String, List<BaseDataDTO>>();
            Map templateData = null;
            String tablename = null;
            List fields = null;
            Integer firstLine = null;
            List<BaseDataExcleColumn> excelCols = null;
            BaseDataImportTemplate importTemplate = null;
            for (int i = 0; i < templateDataList.size(); ++i) {
                templateData = (Map)templateDataList.get(i);
                tablename = templateData.get(TemplateFields.tablename.toString()).toString();
                fields = (List)templateData.get(TemplateFields.fields.toString());
                firstLine = (Integer)templateData.get(TemplateFields.firstline.toString());
                firstLine = firstLine == null ? 2 : firstLine;
                state = 2;
                excelCols = BasedataExcelUtils.getExcelColumnsByTemplateColList(tablename, fields);
                BasedataExcelUtils.checkHeader(workbook.getSheetAt(i), excelCols, firstLine);
                state = 3;
                processInfo.setCurrIndex(currIndex++);
                BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
                importTemplate = new BaseDataImportTemplate(workbook.getSheetAt(i), tablename, excelCols, firstLine, template.getUnitcode());
                processInfo.setCurrIndex(currIndex++);
                BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
                importTemplate.executeImportCheck();
                res.put(tablename, importTemplate.getImportDatas());
                processInfo.setCurrIndex(currIndex++);
                BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
            }
            basedataParam.put("checkDataList", res);
            this.baseDataParamService.loadBatchOptExtendParam(batchOptDTO, BaseDataAction.CHECK);
            R importRes = R.ok();
            importRes.put("data", res);
            BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)importRes));
            complete = true;
            state = 4;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (!complete) {
                String result = null;
                if (state == 0) {
                    result = JSONUtil.toJSONString((Object)R.error((String)"\u6a21\u677f\u6570\u636e\u5f02\u5e38"));
                } else if (state == 1) {
                    result = JSONUtil.toJSONString((Object)R.error((String)"\u8bf7\u4f7f\u7528\u5bfc\u5165\u6a21\u677f\u6587\u4ef6\u8fdb\u884c\u6570\u636e\u5bfc\u5165"));
                } else if (state == 2) {
                    result = JSONUtil.toJSONString((Object)R.error((String)"\u5bfc\u5165\u6587\u4ef6\u8868\u5934\u540d\u79f0\u548c\u6a21\u677f\u4e0d\u4e00\u81f4"));
                } else if (state == 3) {
                    result = JSONUtil.toJSONString((Object)R.error((String)"\u5bfc\u5165\u5931\u8d25"));
                }
                BaseDataCacheUtil.setImportDataResult(key, result);
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
    public void importSave(BaseDataImportTemplateDTO template) {
        Locale locale = LocaleContextHolder.getLocale();
        UserLoginDTO user = ShiroUtil.getUser();
        this.baseDataAsyncTask.execute(() -> {
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

    private void importSaveAsync(BaseDataImportTemplateDTO template) {
        String key = template.getResultKey();
        try {
            BaseDataImportTemplateDO targetTemplate = template.getTempImportTemplate();
            if (targetTemplate == null) {
                targetTemplate = this.baseDataImportTemplateDao.list(template).get(0);
            }
            List templateDataList = JSONUtil.parseMapArray((String)targetTemplate.getTemplatedata().toString());
            Map<String, List<BaseDataDTO>> importDataMap = template.getImportDataMap();
            String mainTableName = null;
            String tablename = null;
            int total = 0;
            for (int i = 0; i < templateDataList.size(); ++i) {
                Map templateData = (Map)templateDataList.get(i);
                tablename = templateData.get(TemplateFields.tablename.toString()).toString();
                if (i == 0) {
                    mainTableName = tablename;
                }
                total += importDataMap.get(tablename).size();
            }
            BaseDataImportProcess processInfo = new BaseDataImportProcess();
            processInfo.setRsKey(key);
            processInfo.setTotal(total + 30);
            processInfo.setCurrIndex(30);
            BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)processInfo));
            BaseDataBatchOptDTO batchOptDTO = new BaseDataBatchOptDTO();
            BaseDataDTO basedataParam = new BaseDataDTO();
            basedataParam.setTableName(mainTableName);
            basedataParam.setUnitcode(template.getUnitcode());
            basedataParam.put("_UpdateExcelSave", (Object)true);
            basedataParam.put("templateDataList", (Object)templateDataList);
            basedataParam.put("importDataMap", importDataMap);
            batchOptDTO.setQueryParam(basedataParam);
            this.baseDataParamService.loadBatchOptExtendParam(batchOptDTO, BaseDataAction.CHECK);
            HashMap<String, List<BaseDataDTO>> res = new HashMap<String, List<BaseDataDTO>>();
            Map templateData = null;
            List fields = null;
            List<BaseDataExcleColumn> excelCols = null;
            List<BaseDataDTO> importDatas = null;
            BaseDataImportTemplate importTemplate = null;
            for (int i = 0; i < templateDataList.size(); ++i) {
                templateData = (Map)templateDataList.get(i);
                tablename = templateData.get(TemplateFields.tablename.toString()).toString();
                importDatas = importDataMap.get(tablename);
                if (importDatas == null || importDatas.isEmpty()) continue;
                fields = (List)templateData.get(TemplateFields.fields.toString());
                excelCols = BasedataExcelUtils.getExcelColumnsByTemplateColList(tablename, fields);
                importTemplate = new BaseDataImportTemplate(tablename, excelCols, importDatas, template.getUnitcode());
                importTemplate.setProcessInfo(processInfo);
                importTemplate.executeImportSave();
                res.put(tablename, importTemplate.getImportResult());
                LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u8868\u683c\u5bfc\u5165", (String)tablename, (String)"", (String)("\u5bfc\u5165\u4e86" + importTemplate.getImportResult().size() + "\u6761\u6570\u636e"));
            }
            basedataParam.put("checkDataList", res);
            this.baseDataParamService.loadBatchOptExtendParam(batchOptDTO, BaseDataAction.CHECK);
            R importRes = R.ok().put("data", res);
            BaseDataCacheUtil.setImportDataResult(key, JSONUtil.toJSONString((Object)importRes));
        }
        catch (Exception e) {
            String result = JSONUtil.toJSONString((Object)R.error((String)e.getMessage()));
            BaseDataCacheUtil.setImportDataResult(key, result);
        }
    }

    @Override
    public R getImportResult(BaseDataImportTemplateDTO template) {
        if (template == null || !StringUtils.hasText(template.getResultKey())) {
            return R.error((int)1, (String)"\u7f3a\u5c11\u53c2\u6570");
        }
        String resString = BaseDataCacheUtil.getImportDataResult(template.getResultKey());
        if (!StringUtils.hasText(resString)) {
            return R.error((int)2, (String)"\u672a\u627e\u5230\u5bfc\u5165\u7ed3\u679c");
        }
        return R.ok().put("result", (Object)JSONUtil.parseObject((String)resString));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R exportResultInfo(BaseDataImportTemplateDTO template) {
        UUID templateId = template.getId();
        BaseDataImportTemplateDTO param = new BaseDataImportTemplateDTO();
        param.setId(templateId);
        BaseDataImportTemplateDO targetTemplate = template.getTempImportTemplate();
        if (targetTemplate == null) {
            targetTemplate = this.baseDataImportTemplateDao.list(template).get(0);
        }
        List templateDataList = JSONUtil.parseMapArray((String)targetTemplate.getTemplatedata().toString());
        ArrayList<String> tableTitles = new ArrayList<String>();
        for (int i = 0; i < templateDataList.size(); ++i) {
            Map templateData = (Map)templateDataList.get(i);
            String tablename = templateData.get(TemplateFields.tablename.toString()).toString();
            tableTitles.add(BasedataExcelUtils.getDataModalDefine(tablename).getTitle());
        }
        List tablesResultInfo = (List)template.getTemplatedata();
        List<Object> tablesField = template.getImportFields();
        XSSFWorkbook workbook = new XSSFWorkbook();
        try (OutputStream outputStream = RequestContextUtil.getOutputStream();){
            for (int tableCount = 0; tableCount < tablesResultInfo.size(); ++tableCount) {
                int i;
                List tableFields = (List)tablesField.get(tableCount);
                List resultInfos = (List)tablesResultInfo.get(tableCount);
                Sheet sheet = workbook.createSheet(((String)tableTitles.get(tableCount)).replace("/", "_").replace("\\", "_"));
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
                indexField.put("title", "\u5e8f\u53f7");
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
            RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode("\u5bfc\u5165\uff08\u4e0a\u4f20\uff09\u7ed3\u679c\u4fe1\u606f.xlsx", "UTF-8")));
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
        return R.ok();
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
        importmemo;

    }
}

