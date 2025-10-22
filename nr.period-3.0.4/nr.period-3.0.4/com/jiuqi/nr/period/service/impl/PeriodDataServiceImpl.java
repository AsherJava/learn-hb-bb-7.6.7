/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodType
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.period.service.impl;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.language.LanguageType;
import com.jiuqi.nr.period.common.rest.ErrorPosInfo;
import com.jiuqi.nr.period.common.rest.ImportRow;
import com.jiuqi.nr.period.common.rest.PeriodDataObject;
import com.jiuqi.nr.period.common.rest.PeriodObject;
import com.jiuqi.nr.period.common.rest.SimpleTitleObj;
import com.jiuqi.nr.period.common.rest.TitleSettingData;
import com.jiuqi.nr.period.common.rest.VailFormObject;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodSqlUtil;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.dao.PeriodLanguageDao;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodLanguage;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.I18nPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodLanguageImpl;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.util.TitleState;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeriodDataServiceImpl
implements PeriodDataService {
    private Logger logger = LogFactory.getLogger(PeriodDataServiceImpl.class);
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private PeriodLanguageDao periodLanguageDao;
    private static final String XLS = "XLS";
    private static final String XLSX = "XLSX";
    private static final String CODE = "\u7f16\u7801";
    private static final String TITLE = "\u6807\u9898";
    private static final String START = "\u5f00\u59cb\u65e5\u671f(yyyy-MM-dd)";
    private static final String END = "\u7ed3\u675f\u65e5\u671f(yyyy-MM-dd)";
    private static final String SIMPLETITLE = "\u7b80\u79f0";
    private static final String ENTITY_CODE = "\u65f6\u671f\u6807\u8bc6";
    private static final String ENTITY_TITLE = "\u65f6\u671f\u6807\u9898";

    @Override
    public List<IPeriodRow> queryPeriodDataByPeriodCode(String code) throws Exception {
        List<PeriodDataDefineImpl> periodDataDefines = this.periodDataDao.queryCustomPeriodData(code);
        ArrayList<IPeriodRow> periodDataObjects = new ArrayList<IPeriodRow>();
        periodDataDefines.stream().forEach(t -> periodDataObjects.add((IPeriodRow)t));
        return periodDataObjects;
    }

    @Override
    public IPeriodRow queryPeriodDataByPeriodCodeAndDataCode(String periodCode, String dateCode) throws Exception {
        List<PeriodDataDefineImpl> periodDataDefines = this.periodDataDao.queryCustomPeriodData(periodCode);
        PeriodDataDefineImpl periodData = null;
        for (PeriodDataDefineImpl data : periodDataDefines) {
            if (!dateCode.equals(data.getCode())) continue;
            periodData = data;
        }
        return periodData;
    }

    @Override
    public void insertCustomPeriod(IPeriodRow periodDataObject, String tableCode) throws Exception {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        periodDataDefine.setCode(periodDataObject.getCode());
        periodDataDefine.setTitle(periodDataObject.getTitle());
        periodDataDefine.setStartDate(periodDataObject.getStartDate());
        periodDataDefine.setAlias(periodDataObject.getAlias());
        periodDataDefine.setEndDate(periodDataObject.getEndDate());
        if (null != periodDataObject.getStartDate()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(periodDataDefine.getStartDate());
            periodDataDefine.setYear(calendar.get(1));
            periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(calendar.get(2) + 1));
            periodDataDefine.setMonth(calendar.get(2) + 1);
            periodDataDefine.setDay(calendar.get(5));
            periodDataDefine.setDays(PeriodUtils.getNumberOfDaysWeek(periodDataDefine));
            periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        } else {
            periodDataDefine.setYear(0);
            periodDataDefine.setQuarter(0);
            periodDataDefine.setMonth(0);
            periodDataDefine.setDay(0);
            periodDataDefine.setDays(0);
            periodDataDefine.setTimeKey("0");
        }
        if (periodDataObject.getKey() != null) {
            periodDataDefine.setKey(periodDataObject.getKey());
        }
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setSimpleTitle(periodDataObject.getSimpleTitle());
        this.periodDataDao.insertCustomPeriodData(tableCode, periodDataDefine);
    }

    @Override
    public void updateCustomPeriod(IPeriodRow periodDataObject, String tableCode) throws Exception {
        PeriodDataDefineImpl periodDataDefine = new PeriodDataDefineImpl();
        periodDataDefine.setKey(periodDataObject.getKey());
        periodDataDefine.setTitle(periodDataObject.getTitle());
        periodDataDefine.setCode(periodDataObject.getCode());
        periodDataDefine.setAlias(periodDataObject.getAlias());
        Date startDate = periodDataObject.getStartDate();
        Date endDate = periodDataObject.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        periodDataDefine.setStartDate(startDate);
        periodDataDefine.setEndDate(endDate);
        periodDataDefine.setYear(calendar.get(1));
        periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(calendar.get(2) + 1));
        periodDataDefine.setMonth(calendar.get(2) + 1);
        periodDataDefine.setDay(calendar.get(5));
        periodDataDefine.setDays(PeriodUtils.getNumberOfDaysWeek(periodDataDefine));
        periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        periodDataDefine.setUpdateTime(new Date());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setSimpleTitle(periodDataObject.getSimpleTitle());
        this.periodDataDao.updateCustomPeriodData(tableCode, periodDataDefine);
    }

    public void updatePeriod13Data(IPeriodRow periodDataObject, String tableCode) throws Exception {
        PeriodDataDefineImpl periodDataDefine = new PeriodDataDefineImpl();
        periodDataDefine.setKey(periodDataObject.getKey());
        periodDataDefine.setTitle(periodDataObject.getTitle());
        periodDataDefine.setCode(periodDataObject.getCode());
        periodDataDefine.setAlias(periodDataObject.getAlias());
        Date startDate = periodDataObject.getStartDate();
        Date endDate = periodDataObject.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        periodDataDefine.setStartDate(startDate);
        periodDataDefine.setEndDate(endDate);
        periodDataDefine.setYear(periodDataObject.getYear());
        periodDataDefine.setQuarter(periodDataObject.getQuarter());
        periodDataDefine.setMonth(periodDataObject.getMonth());
        periodDataDefine.setDay(periodDataObject.getDay());
        periodDataDefine.setDays(periodDataObject.getDays());
        periodDataDefine.setTimeKey(periodDataObject.getTimeKey());
        periodDataDefine.setUpdateTime(new Date());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setSimpleTitle(periodDataObject.getSimpleTitle());
        this.periodDataDao.updateCustomPeriodData(tableCode, periodDataDefine);
    }

    @Override
    public void deleteCustomPeriodData(IPeriodRow periodDataObject, String tableCode) throws Exception {
        this.periodDataDao.deleteCustomPeriodData(tableCode, periodDataObject.getKey());
        this.periodLanguageDao.deleteByEntityAndCode(tableCode, periodDataObject.getCode());
    }

    @Override
    public void deleteCustomPeriodDatas(List<String> ids, String tableCode) throws Exception {
        this.periodDataDao.deleteCustomPeriodDatas(tableCode, ids);
    }

    @Override
    public PeriodObject autoCreateCustomDataCode(String code) throws Exception {
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(code);
        PeriodObject periodObject = new PeriodObject();
        String coding = "";
        ArrayList<Integer> yearList = new ArrayList<Integer>();
        ArrayList<Integer> numsList = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("^[1-9][0-9]{3}B[0-9]{4}$");
        for (PeriodDataDefineImpl periodDataDefine : dataListByCode) {
            if (!pattern.matcher(periodDataDefine.getCode()).find()) continue;
            try {
                yearList.add(Integer.parseInt(periodDataDefine.getCode().substring(0, 4)));
                numsList.add(Integer.parseInt(periodDataDefine.getCode().substring(5)));
            }
            catch (Exception exception) {}
        }
        if (yearList.size() == 0 || numsList.size() == 0) {
            coding = LocalDate.now().getYear() + "" + PeriodUtils.getPeriodType(PeriodType.CUSTOM) + PeriodUtils.doFormat(1);
        } else {
            Integer maxYear = (Integer)yearList.stream().max(Integer::compareTo).get();
            Integer maxNum = (Integer)numsList.stream().max(Integer::compareTo).get() + 1;
            coding = maxYear + PeriodUtils.getPeriodType(PeriodType.CUSTOM) + PeriodUtils.doFormat(maxNum);
        }
        periodObject.setCode(coding);
        IPeriodRow periodDataDefine = PeriodUtils.maxRow(dataListByCode);
        if (periodDataDefine != null && periodDataDefine.getEndDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(periodDataDefine.getEndDate());
            calendar.add(5, 1);
            Date date = calendar.getTime();
            periodObject.setStartdate(PeriodUtils.getPeriodFromDate(PeriodType.DAY.type(), date));
        } else {
            Date date = new Date();
            periodObject.setStartdate(PeriodUtils.getPeriodFromDate(PeriodType.DAY.type(), date));
        }
        return periodObject;
    }

    @Override
    public void handleValidForm(VailFormObject vailFormObject) throws Exception {
        if (NrPeriodConst.PERIOD_CODE.equals(vailFormObject.getValidType())) {
            List<PeriodDefineImpl> periodList = this.periodDao.getPeriodList();
            if ("insert".equalsIgnoreCase(vailFormObject.getOperator())) {
                for (PeriodDefineImpl periodDefine : periodList) {
                    if (!PeriodUtils.removePerfix(periodDefine.getCode()).equals(vailFormObject.getTableCode().toUpperCase())) continue;
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_111);
                }
            } else {
                int count = 0;
                for (PeriodDefineImpl periodDefine : periodList) {
                    if (!PeriodUtils.removePerfix(periodDefine.getCode()).equals(vailFormObject.getTableCode().toUpperCase())) continue;
                    ++count;
                }
                if (count != 1) {
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_111);
                }
            }
        } else {
            String code = vailFormObject.getTableCode();
            IPeriodEntity iPeriodEntity = this.periodDao.queryPeriodByKey(vailFormObject.getPeriodKey());
            if (iPeriodEntity.getPeriodType() != PeriodType.CUSTOM) {
                return;
            }
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(vailFormObject.getPeriodKey());
            if ("insert".equalsIgnoreCase(vailFormObject.getOperator())) {
                for (PeriodDataDefineImpl periodDataDefine : dataListByCode) {
                    if (!periodDataDefine.getCode().equals(code.toUpperCase())) continue;
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_111);
                }
            } else {
                int count = 0;
                for (PeriodDataDefineImpl periodDataDefine : dataListByCode) {
                    if (!periodDataDefine.getCode().equals(code.toUpperCase())) continue;
                    ++count;
                }
                if (count != 1) {
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_111);
                }
            }
            ArrayList<String> defaultCode = new ArrayList<String>(Arrays.asList(NrPeriodConst.CREATE_PERIOD_DATA_TABLE));
            if (code.length() == 9 && defaultCode.contains(code.substring(4, 5).toUpperCase())) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_124);
            }
        }
    }

    @Override
    public void handleValidDate(PeriodDataObject periodDataObject) throws Exception {
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(periodDataObject.getPeriodKey());
        for (PeriodDataDefineImpl periodDataDefine : dataListByCode) {
            Date endDateOfPeriod;
            Date startDateOfPeriod;
            if (periodDataDefine.getStartDate() == null || periodDataDefine.getEndDate() == null || periodDataDefine.getKey().equals(periodDataObject.getKey())) continue;
            if (StringUtils.isNotEmpty(periodDataObject.getStartdate()) && !(startDateOfPeriod = PeriodUtils.getStartDateOfPeriod(periodDataObject.getStartdate(), true)).before(periodDataDefine.getStartDate()) && !startDateOfPeriod.after(periodDataDefine.getEndDate())) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_110);
            }
            if (!StringUtils.isNotEmpty(periodDataObject.getEnddate()) || (endDateOfPeriod = PeriodUtils.getStartDateOfPeriod(periodDataObject.getEnddate(), true)).before(periodDataDefine.getStartDate()) || endDateOfPeriod.after(periodDataDefine.getEndDate())) continue;
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_113);
        }
    }

    @Override
    public void checkTitle(IPeriodEntity iPeriodEntity, PeriodDataDefineImpl pdf, String language) throws Exception {
        if (this.isCN(language)) {
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(iPeriodEntity.getCode());
            for (PeriodDataDefineImpl periodDataDefine : dataListByCode) {
                if (pdf.getCode().equals(periodDataDefine.getCode()) || !pdf.getTitle().equals(periodDataDefine.getTitle())) continue;
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_132);
            }
        } else {
            if (StringUtils.isEmpty(pdf.getKey())) {
                List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(iPeriodEntity.getCode());
                for (PeriodDataDefineImpl periodDataDefine : dataListByCode) {
                    if (pdf.getCode().equals(periodDataDefine.getCode()) || !pdf.getTitle().equals(periodDataDefine.getTitle())) continue;
                    throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_133);
                }
            }
            List<IPeriodLanguage> iPeriodLanguages = this.periodLanguageDao.queryLanguageByEntityAndType(iPeriodEntity.getCode(), language);
            for (IPeriodLanguage iPeriodLanguage : iPeriodLanguages) {
                if (!StringUtils.isNotEmpty(iPeriodLanguage.getCode()) || iPeriodLanguage.getCode().equals(pdf.getCode()) || !iPeriodLanguage.getTitle().equals(pdf.getTitle())) continue;
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_132);
            }
        }
    }

    @Override
    public List<IPeriodRow> queryPeriodDataByPeriodCodeLanguage(String periodCode, String language) throws Exception {
        return this.setLanguage(this.queryPeriodDataByPeriodCode(periodCode), periodCode, language);
    }

    @Override
    public IPeriodRow queryPeriodDataByPeriodCodeAndDataCodeLanguage(String periodCode, String dateCode, String language) throws Exception {
        return this.setLanguage(this.queryPeriodDataByPeriodCodeAndDataCode(periodCode, dateCode), periodCode, language);
    }

    @Override
    public void insertCustomPeriodLanguage(IPeriodRow periodDataRow, String periodCode, String language) throws Exception {
        this.insertCustomPeriod(periodDataRow, periodCode);
        if (!this.isCN(language)) {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodCode);
            periodLanguage.setCode(periodDataRow.getCode());
            periodLanguage.setTitle(periodDataRow.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        }
    }

    @Override
    public void updateCustomPeriodLanguage(IPeriodRow periodDataRow, String periodCode, String language) throws Exception {
        if (!this.isCN(language)) {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodCode);
            periodLanguage.setCode(periodDataRow.getCode());
            periodLanguage.setTitle(periodDataRow.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        } else {
            this.updateCustomPeriod(periodDataRow, periodCode);
        }
    }

    @Override
    public void updatePeriod13DataLanguage(IPeriodRow periodDataRow, String periodCode, String language) throws Exception {
        if (!this.isCN(language)) {
            PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
            periodLanguage.setEntity(periodCode);
            periodLanguage.setCode(periodDataRow.getCode());
            periodLanguage.setTitle(periodDataRow.getTitle());
            periodLanguage.setType(language);
            this.periodLanguageDao.insertOrUpdate(periodLanguage);
        } else {
            this.updatePeriod13Data(periodDataRow, periodCode);
        }
    }

    @Override
    public void updateSimpleTitle(SimpleTitleObj simpleTitleObj) throws Exception {
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(simpleTitleObj.getPeriodKey());
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(simpleTitleObj.getPeriodKey());
        HashMap dbMap = new HashMap();
        for (PeriodDataDefineImpl define : dataListByCode) {
            Integer qi = Integer.parseInt(define.getCode().substring(5));
            if (dbMap.get(qi) == null) {
                dbMap.put(qi, new ArrayList());
                ((List)dbMap.get(qi)).add(define);
                continue;
            }
            ((List)dbMap.get(qi)).add(define);
        }
        List<TitleSettingData> data = simpleTitleObj.getData();
        ArrayList updateDatas = new ArrayList();
        for (TitleSettingData datum : data) {
            List updates = (List)dbMap.get(datum.getIndex());
            if (null == updates || updates.isEmpty()) continue;
            if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
                for (PeriodDataDefineImpl update : updates) {
                    if (StringUtils.isEmpty(datum.getTitle())) {
                        update.setSimpleTitle(PeriodUtils.autoMonthSimpleTitle(PeriodType.MONTH, update.getMonth()));
                        continue;
                    }
                    update.setSimpleTitle(datum.getTitle());
                }
            } else {
                for (PeriodDataDefineImpl update : updates) {
                    update.setSimpleTitle(datum.getTitle());
                }
            }
            updateDatas.addAll(updates);
        }
        if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
            for (PeriodDataDefineImpl updateData : updateDatas) {
                this.updatePeriod13DataLanguage(updateData, periodEntity.getCode(), NpContextHolder.getContext().getLocale().getLanguage());
            }
        } else {
            for (PeriodDataDefineImpl updateData : updateDatas) {
                this.updateCustomPeriodLanguage(updateData, periodEntity.getCode(), NpContextHolder.getContext().getLocale().getLanguage());
            }
        }
        this.periodDao.updateDataType(periodEntity, periodEntity.getDataType() | TitleState.SIMPLE_TITLE.getValue());
    }

    @Override
    public void exportData(String entity, HttpServletResponse res) throws Exception {
        try {
            IPeriodEntity iPeriodEntity = this.periodDao.queryPeriodByCode(entity);
            if (null == iPeriodEntity) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_105);
            }
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(iPeriodEntity.getCode());
            List<IPeriodLanguage> iPeriodLanguages = this.periodLanguageDao.queryLanguageByEntity(iPeriodEntity.getCode());
            String fileName = iPeriodEntity.getTitle();
            fileName = HtmlUtils.cleanHeaderValue((String)URLEncoder.encode(fileName, "UTF-8"));
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            ServletOutputStream out = res.getOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            String sheetTitle = iPeriodEntity.getCode();
            XSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0, sheetTitle);
            sheet.setDefaultColumnWidth(20);
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setFont(font);
            style.setWrapText(true);
            List entityTitles = iPeriodLanguages.stream().filter(e -> StringUtils.isEmpty(e.getCode())).collect(Collectors.toList());
            HashMap<String, String> entityLanguage = new HashMap<String, String>();
            for (IPeriodLanguage entityTitle : entityTitles) {
                if (!StringUtils.isNotEmpty(entityTitle.getTitle())) continue;
                entityLanguage.put(entityTitle.getType(), entityTitle.getTitle());
            }
            XSSFRow row0 = sheet.createRow(0);
            XSSFRow row1 = sheet.createRow(1);
            XSSFCell cell11 = row0.createCell(0);
            cell11.setCellStyle(style);
            cell11.setCellValue(new XSSFRichTextString(ENTITY_CODE).toString());
            XSSFCell cell21 = row1.createCell(0);
            cell21.setCellStyle(style);
            cell21.setCellValue(new XSSFRichTextString(iPeriodEntity.getCode()).toString());
            XSSFCell cell12 = row0.createCell(1);
            cell12.setCellStyle(style);
            cell12.setCellValue(new XSSFRichTextString(ENTITY_TITLE).toString());
            XSSFCell cell22 = row1.createCell(1);
            cell22.setCellStyle(style);
            cell22.setCellValue(new XSSFRichTextString(iPeriodEntity.getTitle()).toString());
            int idx = 0;
            for (String type : entityLanguage.keySet()) {
                XSSFCell cella = row0.createCell((short)idx + 2);
                cella.setCellStyle(style);
                cella.setCellValue(new XSSFRichTextString(type).toString());
                XSSFCell cellb = row1.createCell((short)idx + 2);
                cellb.setCellStyle(style);
                cellb.setCellValue(new XSSFRichTextString((String)entityLanguage.get(type)).toString());
                ++idx;
            }
            ArrayList<String> headers = new ArrayList<String>();
            headers.add(CODE);
            headers.add(TITLE);
            headers.add(START);
            headers.add(END);
            headers.add(SIMPLETITLE);
            List entityDataTitles = iPeriodLanguages.stream().filter(e -> StringUtils.isNotEmpty(e.getCode())).collect(Collectors.toList());
            HashSet<String> typeSet = new HashSet<String>();
            HashMap<String, String> entityDataLanguage = new HashMap<String, String>();
            for (IPeriodLanguage entityTitle : entityDataTitles) {
                if (!StringUtils.isNotEmpty(entityTitle.getTitle())) continue;
                entityDataLanguage.put(entityTitle.getCode() + entityTitle.getType(), entityTitle.getTitle());
                typeSet.add(entityTitle.getType());
            }
            for (String s : typeSet) {
                headers.add(s);
            }
            XSSFRow row = sheet.createRow(2);
            for (int i = 0; i < headers.size(); ++i) {
                XSSFCell cell = row.createCell((short)i);
                cell.setCellStyle(style);
                XSSFRichTextString text = new XSSFRichTextString((String)headers.get(i));
                cell.setCellValue(text.toString());
            }
            if (dataListByCode != null && dataListByCode.size() > 0) {
                for (int r = 0; r < dataListByCode.size(); ++r) {
                    row = sheet.createRow(r + 3);
                    PeriodDataDefineImpl resultData = dataListByCode.get(r);
                    String cellValue = null;
                    cellValue = resultData.getCode() != null ? resultData.getCode() : "";
                    XSSFCell cellR0 = row.createCell(0);
                    cellR0.setCellValue(cellValue.toString());
                    cellR0.setCellType(CellType.STRING);
                    cellValue = resultData.getTitle() != null ? resultData.getTitle() : "";
                    XSSFCell cellR1 = row.createCell(1);
                    cellR1.setCellValue(cellValue.toString());
                    cellR1.setCellType(CellType.STRING);
                    if (resultData.getStartDate() != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(resultData.getStartDate());
                        calendar.set(14, 0);
                        cellValue = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
                    } else {
                        cellValue = "";
                    }
                    XSSFCell cellR2 = row.createCell(2);
                    cellR2.setCellValue(cellValue.toString());
                    cellR2.setCellType(CellType.STRING);
                    if (resultData.getEndDate() != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(resultData.getEndDate());
                        calendar.set(14, 0);
                        cellValue = calendar.get(1) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
                    } else {
                        cellValue = "";
                    }
                    XSSFCell cellR3 = row.createCell(3);
                    cellR3.setCellValue(cellValue.toString());
                    cellR3.setCellType(CellType.STRING);
                    cellValue = resultData.getSimpleTitle() != null ? resultData.getSimpleTitle() : PeriodUtils.getDefaultShowTitle(iPeriodEntity.getPeriodType(), resultData);
                    XSSFCell cellR4 = row.createCell(4);
                    cellR4.setCellValue(cellValue.toString());
                    cellR4.setCellType(CellType.STRING);
                    int idx2 = 0;
                    for (String lType : typeSet) {
                        cellValue = (String)entityDataLanguage.get(resultData.getCode() + lType);
                        if (StringUtils.isEmpty(cellValue)) continue;
                        XSSFCell cellRi = row.createCell(idx2 + 4);
                        cellRi.setCellValue(cellValue.toString());
                        cellRi.setCellType(CellType.STRING);
                        ++idx2;
                    }
                }
            }
            workbook.write((OutputStream)out);
            out.flush();
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e2) {
                    this.logger.error(e2.getMessage(), (Throwable)e2);
                }
            }
        }
        catch (Exception e3) {
            this.logger.error(e3.getMessage(), (Throwable)e3);
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_125, (Throwable)e3);
        }
    }

    @Override
    public List<ErrorPosInfo> importData(String entity, MultipartFile file) throws Exception {
        this.checkFile(file);
        ArrayList<ErrorPosInfo> errorInfo = new ArrayList<ErrorPosInfo>();
        try {
            Workbook workBook = this.getWorkBook(file);
            ArrayList<String> defaultCode = new ArrayList<String>(Arrays.asList(NrPeriodConst.CREATE_PERIOD_DATA_TABLE));
            IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(entity);
            if (null == periodEntity) {
                throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122);
            }
            if (!PeriodType.CUSTOM.equals((Object)periodEntity.getPeriodType())) {
                this.importDefaultPeriodData(workBook, errorInfo, entity);
            } else {
                this.importCustomPeriodData(workBook, errorInfo, entity);
            }
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_122, (Throwable)e);
        }
        return errorInfo;
    }

    private void importDefaultPeriodData(Workbook workBook, List<ErrorPosInfo> errorInfo, String entity) throws Exception {
        ArrayList<ImportRow> importData = new ArrayList<ImportRow>();
        Sheet sheet = workBook.getSheetAt(0);
        if (sheet == null) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_129);
        }
        Row row0 = sheet.getRow(0);
        if (!this.checkHeader0(row0)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_131);
        }
        Row row2 = sheet.getRow(2);
        if (!this.checkHeader3(row2)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_131);
        }
        Row row1 = sheet.getRow(1);
        String entityCode = row1.getCell(0).getStringCellValue();
        if (!entityCode.equals(entity)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_130);
        }
        String entityTitle = row1.getCell(1).getStringCellValue();
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByKey(entity);
        PeriodDefineImpl updateEntity = null;
        if (!periodEntity.getTitle().equals(entityTitle)) {
            updateEntity = new PeriodDefineImpl();
            BeanUtils.copyProperties(periodEntity, updateEntity);
            updateEntity.setTitle(entityTitle);
            updateEntity.setUpdateTime(new Date());
        }
        ArrayList<PeriodLanguageImpl> entityLanguageData = new ArrayList<PeriodLanguageImpl>();
        if (row0.getLastCellNum() > 2) {
            for (int col = 2; col < row0.getLastCellNum(); ++col) {
                if (StringUtils.isEmpty(row0.getCell(col).getStringCellValue())) continue;
                PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
                periodLanguage.setEntity(entityCode);
                periodLanguage.setCode(null);
                periodLanguage.setTitle(row1.getCell(col).getStringCellValue());
                periodLanguage.setType(row0.getCell(col).getStringCellValue());
                entityLanguageData.add(periodLanguage);
            }
        }
        int rowCount = sheet.getLastRowNum();
        int headerColCount = sheet.getRow(2).getLastCellNum();
        ArrayList<String> headerList = new ArrayList<String>();
        for (int c = 0; c < headerColCount; ++c) {
            headerList.add(PeriodDataServiceImpl.getCellValue(sheet.getRow(2).getCell(c)));
        }
        for (int r = 3; r <= rowCount; ++r) {
            Row row = sheet.getRow(r);
            int colCount = row.getLastCellNum();
            ImportRow periodData = new ImportRow();
            for (int c = 0; c < colCount; ++c) {
                if (c <= 4) {
                    String cellValue = (String)headerList.get(c);
                    String cValue = null;
                    periodData.setRow(r);
                    periodData.setCol(c);
                    switch (cellValue) {
                        case "\u7f16\u7801": {
                            cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                            if (!StringUtils.isNotEmpty(cValue)) break;
                            cValue = cValue.toUpperCase();
                            periodData.setCode(cValue);
                            break;
                        }
                        case "\u6807\u9898": {
                            cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                            if (!StringUtils.isNotEmpty(cValue)) break;
                            periodData.setTitle(cValue);
                            break;
                        }
                        case "\u7b80\u79f0": {
                            cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                            if (!StringUtils.isNotEmpty(cValue)) break;
                            periodData.setSimpleTitle(cValue);
                            break;
                        }
                    }
                    continue;
                }
                if (StringUtils.isEmpty(row.getCell(c).getStringCellValue())) continue;
                PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
                periodLanguage.setEntity(entityCode);
                periodLanguage.setCode(periodData.getCode());
                periodLanguage.setTitle(row.getCell(c).getStringCellValue());
                periodLanguage.setType(row2.getCell(c).getStringCellValue());
                entityLanguageData.add(periodLanguage);
            }
            importData.add(periodData);
        }
        if (errorInfo.size() == 0) {
            List<IPeriodRow> iPeriodRows = this.queryPeriodDataByPeriodCode(entity);
            List filterData = importData.stream().filter(e -> StringUtils.isNotEmpty(e.getCode()) && StringUtils.isNotEmpty(e.getTitle())).collect(Collectors.toList());
            Set codeSet = iPeriodRows.stream().map(e -> e.getCode()).collect(Collectors.toSet());
            Iterator iterator = filterData.iterator();
            while (iterator.hasNext()) {
                ImportRow next = (ImportRow)iterator.next();
                if (codeSet.contains(next.getCode())) continue;
                iterator.remove();
            }
            this.checkCode(importData, errorInfo);
            this.checkTitle(importData, errorInfo);
            if (errorInfo.size() == 0) {
                Map<String, IPeriodRow> map = iPeriodRows.stream().collect(Collectors.toMap(e -> e.getCode(), e -> e));
                int dataType = periodEntity.getDataType();
                boolean isPeriod13 = PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType());
                for (ImportRow filterDatum : filterData) {
                    IPeriodRow iPeriodRow = map.get(filterDatum.getCode());
                    PeriodDataDefineImpl ipe = new PeriodDataDefineImpl();
                    BeanUtils.copyProperties(iPeriodRow, ipe);
                    ipe.setTitle(filterDatum.getTitle());
                    if (isPeriod13) {
                        if (StringUtils.isEmpty(filterDatum.getSimpleTitle())) {
                            String defaultTitle = PeriodUtils.autoMonthSimpleTitle(periodEntity.getPeriodType(), iPeriodRow.getMonth());
                            ipe.setSimpleTitle(defaultTitle);
                        } else {
                            ipe.setSimpleTitle(filterDatum.getSimpleTitle());
                        }
                        dataType = dataType | TitleState.TITLE.getValue() | TitleState.SIMPLE_TITLE.getValue();
                    } else {
                        if (StringUtils.isEmpty(filterDatum.getSimpleTitle()) || PeriodUtils.getDefaultShowTitle(periodEntity.getPeriodType(), iPeriodRow).equals(filterDatum.getSimpleTitle())) {
                            ipe.setSimpleTitle(null);
                        } else {
                            ipe.setSimpleTitle(filterDatum.getSimpleTitle());
                        }
                        if (!iPeriodRow.getTitle().equals(ipe.getTitle())) {
                            dataType |= TitleState.TITLE.getValue();
                        }
                        if (StringUtils.isNotEmpty(ipe.getSimpleTitle())) {
                            dataType |= TitleState.SIMPLE_TITLE.getValue();
                        }
                    }
                    this.periodDataDao.updateCustomPeriodData(entity, ipe);
                }
                if (null != updateEntity) {
                    this.periodDao.updateDate(updateEntity);
                }
                this.periodDao.updateDataType(periodEntity, dataType);
                this.periodLanguageDao.deleteByEntity(entity);
                if (!entityLanguageData.isEmpty()) {
                    this.periodLanguageDao.insertDates((IPeriodLanguage[])entityLanguageData.stream().toArray(PeriodLanguageImpl[]::new));
                }
            }
        }
    }

    private List<ErrorPosInfo> importCustomPeriodData(Workbook workBook, List<ErrorPosInfo> errorInfo, String entity) throws Exception {
        ArrayList<String> defaultCode = new ArrayList<String>(Arrays.asList(NrPeriodConst.CREATE_PERIOD_DATA_TABLE));
        ArrayList<ImportRow> importData = new ArrayList<ImportRow>();
        Sheet sheet = workBook.getSheetAt(0);
        if (sheet == null) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_129);
        }
        Row row0 = sheet.getRow(0);
        if (!this.checkHeader0(row0)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_131);
        }
        Row row2 = sheet.getRow(2);
        if (!this.checkHeader3(row2)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_131);
        }
        Row row1 = sheet.getRow(1);
        String entityCode = row1.getCell(0).getStringCellValue();
        if (!entityCode.equals(entity)) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_130);
        }
        String entityTitle = row1.getCell(1).getStringCellValue();
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByKey(entity);
        PeriodDefineImpl updateEntity = null;
        if (!periodEntity.getTitle().equals(entityTitle)) {
            updateEntity = new PeriodDefineImpl();
            BeanUtils.copyProperties(periodEntity, updateEntity);
            updateEntity.setTitle(entityTitle);
            updateEntity.setUpdateTime(new Date());
        }
        ArrayList<IPeriodLanguage> entityLanguageData = new ArrayList<IPeriodLanguage>();
        if (row0.getLastCellNum() > 2) {
            for (int col = 2; col < row0.getLastCellNum(); ++col) {
                if (StringUtils.isEmpty(row0.getCell(col).getStringCellValue())) continue;
                PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
                periodLanguage.setEntity(entityCode);
                periodLanguage.setCode(null);
                periodLanguage.setTitle(row1.getCell(col).getStringCellValue());
                periodLanguage.setType(row0.getCell(col).getStringCellValue());
                entityLanguageData.add(periodLanguage);
            }
        }
        int rowCount = sheet.getLastRowNum();
        int headerColCount = sheet.getRow(2).getLastCellNum();
        ArrayList<String> headerList = new ArrayList<String>();
        for (int c = 0; c < headerColCount; ++c) {
            headerList.add(PeriodDataServiceImpl.getCellValue(sheet.getRow(2).getCell(c)));
        }
        for (int r = 3; r <= rowCount; ++r) {
            Row row = sheet.getRow(r);
            int colCount = row.getLastCellNum();
            ImportRow periodData = new ImportRow();
            for (int c = 0; c < colCount; ++c) {
                if (c <= 4) {
                    String cellValue = (String)headerList.get(c);
                    String cValue = null;
                    periodData.setRow(r);
                    periodData.setCol(c);
                    switch (cellValue) {
                        case "\u7f16\u7801": {
                            cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                            if (StringUtils.isNotEmpty(cValue)) {
                                cValue = cValue.toUpperCase();
                                Pattern pattern = Pattern.compile("^\\w{0,19}$");
                                if (pattern.matcher(cValue).matches()) {
                                    if (cValue.length() == 9 && defaultCode.contains(cValue.substring(4, 5).toUpperCase())) {
                                        errorInfo.add(new ErrorPosInfo(r, 1, "\u4e0d\u5b9a\u671f\u65f6\u671f\u7f16\u7801\u4e0d\u80fd\u4e0e\u56fa\u5b9a\u671f\u4e00\u81f4", cValue));
                                        break;
                                    }
                                    periodData.setCode(cValue);
                                    break;
                                }
                                errorInfo.add(new ErrorPosInfo(r, 1, "\u65f6\u671f\u7f16\u7801\u53ea\u80fd\u662f\u6570\u5b57\u5b57\u6bcd\u4e0b\u5212\u7ebf\u76840-19\u4f4d\u7ec4\u5408", cValue));
                                break;
                            }
                            errorInfo.add(new ErrorPosInfo(r, 1, "\u65f6\u671f\u7f16\u7801\u4e3a\u7a7a", cValue));
                            break;
                        }
                        case "\u6807\u9898": {
                            cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                            if (StringUtils.isNotEmpty(cValue)) {
                                periodData.setTitle(cValue);
                                break;
                            }
                            errorInfo.add(new ErrorPosInfo(r, 2, "\u65f6\u671f\u6570\u636e\u6807\u9898\u4e0d\u5141\u8bb8\u4e3a\u7a7a", cValue));
                            break;
                        }
                        case "\u7b80\u79f0": {
                            cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                            if (!StringUtils.isNotEmpty(cValue)) break;
                            periodData.setSimpleTitle(cValue);
                            break;
                        }
                        case "\u5f00\u59cb\u65e5\u671f(yyyy-MM-dd)": {
                            Calendar calendar;
                            int dayOfMonth;
                            int month;
                            int year;
                            String dom;
                            String m;
                            String[] split;
                            Date parse;
                            Calendar calendar2;
                            BigDecimal bd;
                            if (null != row.getCell(c)) {
                                if (row.getCell(c).getCellType().equals((Object)CellType.NUMERIC)) {
                                    try {
                                        bd = new BigDecimal(row.getCell(c).getNumericCellValue());
                                        int days = bd.intValue();
                                        calendar2 = Calendar.getInstance();
                                        calendar2.set(1900, 0, 1);
                                        calendar2.add(5, days - 2);
                                        calendar2.set(11, 0);
                                        calendar2.set(12, 0);
                                        calendar2.set(13, 0);
                                        calendar2.set(14, 0);
                                        parse = calendar2.getTime();
                                        if (null == parse) {
                                            throw new Exception("");
                                        }
                                        periodData.setStart(parse);
                                        cValue = calendar2.get(1) + "/" + (calendar2.get(2) + 1) + "/" + calendar2.get(5);
                                        periodData.setStartValue(cValue);
                                    }
                                    catch (Exception e) {
                                        errorInfo.add(new ErrorPosInfo(r, 3, "\u5f00\u59cb\u65f6\u95f4\u683c\u5f0f\u4e0d\u6b63\u786e", cValue));
                                    }
                                    break;
                                }
                                cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                                periodData.setStartValue(cValue);
                                if (StringUtils.isNotEmpty(cValue)) {
                                    split = cValue.split("-");
                                    if (split.length != 3) {
                                        errorInfo.add(new ErrorPosInfo(r, 3, "\u5f00\u59cb\u65f6\u95f4\u683c\u5f0f\u4e0d\u6b63\u786e", cValue));
                                        break;
                                    }
                                    try {
                                        String y = split[0];
                                        m = split[1];
                                        dom = split[2];
                                        year = Integer.parseInt(y);
                                        month = Integer.parseInt(m);
                                        dayOfMonth = Integer.parseInt(dom);
                                        if (year <= 0 || year >= 3000) {
                                            throw new Exception("");
                                        }
                                        if (month <= 0 || month >= 13) {
                                            throw new Exception("");
                                        }
                                        if (dayOfMonth <= 0) {
                                            throw new Exception("");
                                        }
                                        switch (month) {
                                            case 1: 
                                            case 3: 
                                            case 5: 
                                            case 7: 
                                            case 8: 
                                            case 10: 
                                            case 12: {
                                                if (dayOfMonth < 32) break;
                                                throw new Exception("");
                                            }
                                            case 4: 
                                            case 6: 
                                            case 9: 
                                            case 11: {
                                                if (dayOfMonth < 31) break;
                                                throw new Exception("");
                                            }
                                            case 2: {
                                                if (!(this.isLeapYear(year) ? dayOfMonth >= 30 : dayOfMonth >= 29)) break;
                                                throw new Exception("");
                                            }
                                        }
                                        calendar = Calendar.getInstance();
                                        calendar.set(year, month - 1, dayOfMonth, 0, 0, 0);
                                        calendar.set(14, 0);
                                        periodData.setStart(calendar.getTime());
                                    }
                                    catch (Exception e) {
                                        errorInfo.add(new ErrorPosInfo(r, 3, "\u5f00\u59cb\u65f6\u95f4\u683c\u5f0f\u4e0d\u6b63\u786e", cValue));
                                    }
                                    break;
                                }
                                periodData.setStart(null);
                                break;
                            }
                            periodData.setStartValue(null);
                            periodData.setStart(null);
                            break;
                        }
                        case "\u7ed3\u675f\u65e5\u671f(yyyy-MM-dd)": {
                            Calendar calendar;
                            int dayOfMonth;
                            int month;
                            int year;
                            String dom;
                            String m;
                            String[] split;
                            Date parse;
                            Calendar calendar2;
                            BigDecimal bd;
                            if (null != row.getCell(c)) {
                                if (row.getCell(c).getCellType().equals((Object)CellType.NUMERIC)) {
                                    try {
                                        bd = new BigDecimal(row.getCell(c).getNumericCellValue());
                                        int days = bd.intValue();
                                        calendar2 = Calendar.getInstance();
                                        calendar2.set(1900, 0, 1);
                                        calendar2.add(5, days - 2);
                                        calendar2.set(11, 0);
                                        calendar2.set(12, 0);
                                        calendar2.set(13, 0);
                                        calendar2.set(14, 0);
                                        parse = calendar2.getTime();
                                        if (null == parse) {
                                            throw new Exception("");
                                        }
                                        periodData.setEnd(parse);
                                        cValue = calendar2.get(1) + "/" + (calendar2.get(2) + 1) + "/" + calendar2.get(5);
                                        periodData.setEndValue(cValue);
                                    }
                                    catch (Exception e) {
                                        errorInfo.add(new ErrorPosInfo(r, 4, "\u7ed3\u675f\u65f6\u95f4\u683c\u5f0f\u4e0d\u6b63\u786e", cValue));
                                    }
                                    break;
                                }
                                cValue = PeriodDataServiceImpl.getCellValue(row.getCell(c));
                                periodData.setEndValue(cValue);
                                if (StringUtils.isNotEmpty(cValue)) {
                                    split = cValue.split("-");
                                    if (split.length != 3) {
                                        errorInfo.add(new ErrorPosInfo(r, 4, "\u7ed3\u675f\u65f6\u95f4\u683c\u5f0f\u4e0d\u6b63\u786e", cValue));
                                        break;
                                    }
                                    try {
                                        String y = split[0];
                                        m = split[1];
                                        dom = split[2];
                                        year = Integer.parseInt(y);
                                        month = Integer.parseInt(m);
                                        dayOfMonth = Integer.parseInt(dom);
                                        if (year <= 0 || year >= 3000) {
                                            throw new Exception("");
                                        }
                                        if (month <= 0 || month >= 13) {
                                            throw new Exception("");
                                        }
                                        if (dayOfMonth <= 0) {
                                            throw new Exception("");
                                        }
                                        switch (month) {
                                            case 1: 
                                            case 3: 
                                            case 5: 
                                            case 7: 
                                            case 8: 
                                            case 10: 
                                            case 12: {
                                                if (dayOfMonth < 32) break;
                                                throw new Exception("");
                                            }
                                            case 4: 
                                            case 6: 
                                            case 9: 
                                            case 11: {
                                                if (dayOfMonth < 31) break;
                                                throw new Exception("");
                                            }
                                            case 2: {
                                                if (!(this.isLeapYear(year) ? dayOfMonth >= 30 : dayOfMonth >= 29)) break;
                                                throw new Exception("");
                                            }
                                        }
                                        calendar = Calendar.getInstance();
                                        calendar.set(year, month - 1, dayOfMonth, 0, 0, 0);
                                        calendar.set(14, 0);
                                        periodData.setEnd(calendar.getTime());
                                    }
                                    catch (Exception e) {
                                        errorInfo.add(new ErrorPosInfo(r, 4, "\u7ed3\u675f\u65f6\u95f4\u683c\u5f0f\u4e0d\u6b63\u786e", cValue));
                                    }
                                    break;
                                }
                                periodData.setEnd(null);
                                break;
                            }
                            periodData.setEndValue(null);
                            periodData.setEnd(null);
                            break;
                        }
                    }
                    continue;
                }
                if (StringUtils.isEmpty(row.getCell(c).getStringCellValue())) continue;
                PeriodLanguageImpl periodLanguage = new PeriodLanguageImpl();
                periodLanguage.setEntity(entityCode);
                periodLanguage.setCode(periodData.getCode());
                periodLanguage.setTitle(row.getCell(c).getStringCellValue());
                periodLanguage.setType(row2.getCell(c).getStringCellValue());
                entityLanguageData.add(periodLanguage);
            }
            importData.add(periodData);
        }
        if (errorInfo.size() != 0) {
            return errorInfo;
        }
        if (!this.checkCode(importData, errorInfo)) {
            return errorInfo;
        }
        if (!this.checkTitle(importData, errorInfo)) {
            return errorInfo;
        }
        if (!this.checkTime(importData, errorInfo)) {
            return errorInfo;
        }
        this.saveData(importData, entity, entityLanguageData, updateEntity);
        return errorInfo;
    }

    public void saveData(List<ImportRow> importData, String entity, List<IPeriodLanguage> insertLanguageData, PeriodDefineImpl periodEntity) throws Exception {
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(entity);
        List<String> collect = dataListByCode.stream().map(e -> e.getKey()).collect(Collectors.toList());
        this.periodDataDao.deleteCustomPeriodDatas(entity, collect);
        IPeriodEntity dataTypeEntity = this.periodDao.queryPeriodByKey(entity);
        int dataType = dataTypeEntity.getDataType();
        for (ImportRow importDatum : importData) {
            PeriodDataDefineImpl importRowData = this.createImportRowData(importDatum);
            this.periodDataDao.insertCustomPeriodData(entity, importRowData);
            dataType |= TitleState.TITLE.getValue();
            if (!StringUtils.isNotEmpty(importDatum.getSimpleTitle())) continue;
            dataType |= TitleState.SIMPLE_TITLE.getValue();
        }
        if (null != periodEntity) {
            this.periodDao.updateDate(periodEntity);
        }
        this.periodDao.updateDataType(dataTypeEntity, dataType);
        this.periodLanguageDao.deleteByEntity(entity);
        if (!insertLanguageData.isEmpty()) {
            this.periodLanguageDao.insertDates((IPeriodLanguage[])insertLanguageData.stream().toArray(PeriodLanguageImpl[]::new));
        }
    }

    private boolean isCN(String language) {
        return LanguageType.Chinese.getCode().equals(language);
    }

    private List<IPeriodRow> setLanguage(List<IPeriodRow> iPeriodRows, String entity, String language) throws Exception {
        if (this.isCN(language)) {
            return iPeriodRows;
        }
        List<IPeriodLanguage> iPeriodLanguages = this.periodLanguageDao.queryLanguageByEntityAndType(entity, language);
        HashMap<String, IPeriodLanguage> enMap = new HashMap<String, IPeriodLanguage>();
        for (IPeriodLanguage iPeriodLanguage : iPeriodLanguages) {
            if (!StringUtils.isNotEmpty(iPeriodLanguage.getCode())) continue;
            enMap.put(iPeriodLanguage.getCode(), iPeriodLanguage);
        }
        List<IPeriodRow> collect = iPeriodRows.stream().map(e -> {
            I18nPeriodRow i18nPeriodRow = new I18nPeriodRow((IPeriodRow)e);
            if (null != enMap.get(e.getCode())) {
                i18nPeriodRow.setTitle(((IPeriodLanguage)enMap.get(e.getCode())).getTitle());
            }
            return i18nPeriodRow;
        }).collect(Collectors.toList());
        return collect;
    }

    private IPeriodRow setLanguage(IPeriodRow iPeriodRow, String entity, String language) throws Exception {
        if (this.isCN(language)) {
            return iPeriodRow;
        }
        I18nPeriodRow i18PeriodRow = new I18nPeriodRow(iPeriodRow);
        List<IPeriodLanguage> iPeriodLanguages = this.periodLanguageDao.queryLanguageByEntity(entity);
        for (IPeriodLanguage iPeriodLanguage : iPeriodLanguages) {
            if (!iPeriodRow.getCode().equals(iPeriodLanguage.getCode())) continue;
            i18PeriodRow.setTitle(iPeriodLanguage.getTitle());
        }
        return i18PeriodRow;
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    private PeriodDataDefineImpl createImportRowData(ImportRow row) {
        PeriodDataDefineImpl periodDataDefine = PeriodSqlUtil.createPeriodDataDefine();
        periodDataDefine.setCode(row.getCode());
        periodDataDefine.setTitle(row.getTitle());
        periodDataDefine.setStartDate(row.getStart());
        periodDataDefine.setAlias(row.getCode());
        periodDataDefine.setEndDate(row.getEnd());
        if (null != row.getStart() && null != row.getEnd()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(periodDataDefine.getStartDate());
            periodDataDefine.setYear(calendar.get(1));
            periodDataDefine.setQuarter(PeriodUtils.getMonthOfQuarter(calendar.get(2) + 1));
            periodDataDefine.setMonth(calendar.get(2) + 1);
            periodDataDefine.setDay(calendar.get(5));
            periodDataDefine.setDays(PeriodUtils.getNumberOfDaysWeek(periodDataDefine));
            periodDataDefine.setTimeKey(PeriodUtils.dateToString(periodDataDefine.getStartDate()));
        } else {
            periodDataDefine.setYear(0);
            periodDataDefine.setQuarter(0);
            periodDataDefine.setMonth(0);
            periodDataDefine.setDay(0);
            periodDataDefine.setDays(0);
            periodDataDefine.setTimeKey("0");
        }
        periodDataDefine.setCreateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
        periodDataDefine.setSimpleTitle(row.getSimpleTitle());
        return periodDataDefine;
    }

    private boolean checkTime(List<ImportRow> importData, List<ErrorPosInfo> errorInfo) {
        for (int i = 0; i < importData.size(); ++i) {
            ImportRow curr = importData.get(i);
            if (curr.getStart() == null && curr.getEnd() == null) continue;
            Date currStar = curr.getStart();
            Date currEnd = curr.getEnd();
            if (null == currStar) {
                currStar = currEnd;
            }
            if (null == currEnd) {
                currEnd = currStar;
            }
            if (currStar.after(currEnd)) {
                errorInfo.add(new ErrorPosInfo(curr.getRow(), 3, "\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4\uff0c\u8bf7\u4fee\u6539", curr.getStartValue()));
                return false;
            }
            for (int j = 0; j < i; ++j) {
                ImportRow pre = importData.get(j);
                if (pre.getStart() == null && pre.getEnd() == null) continue;
                Date preStar = pre.getStart();
                Date preEnd = pre.getEnd();
                if (null == preStar) {
                    preStar = preEnd;
                }
                if (null == preEnd) {
                    preEnd = preStar;
                }
                if (!currStar.before(preStar) && !currStar.after(preEnd)) {
                    errorInfo.add(new ErrorPosInfo(curr.getRow(), 3, "\u65f6\u95f4\u91cd\u590d\uff0c\u8bf7\u4fee\u6539", curr.getStartValue()));
                    return false;
                }
                if (currEnd.before(preStar) || currEnd.after(preStar)) continue;
                errorInfo.add(new ErrorPosInfo(curr.getRow(), 4, "\u65f6\u95f4\u91cd\u590d\uff0c\u8bf7\u4fee\u6539", curr.getEndValue()));
                return false;
            }
        }
        return true;
    }

    private boolean checkTitle(List<ImportRow> importData, List<ErrorPosInfo> errorInfo) {
        HashSet<String> titleSet = new HashSet<String>();
        for (ImportRow importDatum : importData) {
            if (titleSet.add(importDatum.getTitle())) continue;
            errorInfo.add(new ErrorPosInfo(importDatum.getRow(), 2, "\u65f6\u671f\u6807\u9898\u91cd\u590d", importDatum.getTitle()));
            return false;
        }
        return true;
    }

    private boolean checkCode(List<ImportRow> importData, List<ErrorPosInfo> errorInfo) {
        HashSet<String> codeSet = new HashSet<String>();
        for (ImportRow importDatum : importData) {
            if (codeSet.add(importDatum.getCode())) continue;
            errorInfo.add(new ErrorPosInfo(importDatum.getRow(), 1, "\u65f6\u671f\u7f16\u7801\u91cd\u590d", importDatum.getCode()));
            return false;
        }
        return true;
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        cellValue = cell.getCellType() == CellType.NUMERIC ? String.valueOf(cell.getNumericCellValue()) : (cell.getCellType() == CellType.STRING ? String.valueOf(cell.getStringCellValue()) : (cell.getCellType() == CellType.BOOLEAN ? String.valueOf(cell.getBooleanCellValue()) : (cell.getCellType() == CellType.FORMULA ? String.valueOf(cell.getCellFormula()) : (cell.getCellType() == CellType.BLANK ? "" : (cell.getCellType() == CellType.ERROR ? "\u975e\u6cd5\u5b57\u7b26" : "\u672a\u77e5\u7c7b\u578b")))));
        return cellValue;
    }

    private boolean checkHeader0(Row firstRow) {
        String value0 = firstRow.getCell(0).getStringCellValue();
        String value1 = firstRow.getCell(1).getStringCellValue();
        if (StringUtils.isEmpty(value0) || StringUtils.isEmpty(value1)) {
            return false;
        }
        return value0.equals(ENTITY_CODE) && value1.equals(ENTITY_TITLE);
    }

    private boolean checkHeader3(Row firstRow) {
        String value0 = firstRow.getCell(0).getStringCellValue();
        String value1 = firstRow.getCell(1).getStringCellValue();
        String value2 = firstRow.getCell(2).getStringCellValue();
        String value3 = firstRow.getCell(3).getStringCellValue();
        if (StringUtils.isEmpty(value0) || StringUtils.isEmpty(value1) || StringUtils.isEmpty(value2) || StringUtils.isEmpty(value3)) {
            return false;
        }
        return value0.equals(CODE) && value1.equals(TITLE) && value2.equals(START) && value3.equals(END);
    }

    private void checkFile(MultipartFile file) throws JQException {
        if (null == file) {
            this.logger.error("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01");
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_127);
        }
        String fileName = file.getOriginalFilename().toUpperCase();
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            this.logger.error(fileName + "\u4e0d\u662fexcel\u6587\u4ef6");
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_128, "[" + fileName + "]" + (Object)((Object)PeriodException.PERIOD_EXCEPTION_128));
        }
    }

    private Workbook getWorkBook(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toUpperCase();
        Workbook workbook = null;
        try (InputStream is = file.getInputStream();){
            if (fileName.endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw e;
        }
        return workbook;
    }
}

