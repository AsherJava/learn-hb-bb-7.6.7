/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.ibatis.session.ExecutorType
 *  org.apache.ibatis.session.SqlSession
 *  org.apache.poi.hssf.usermodel.HSSFDataFormatter
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.mybatis.spring.SqlSessionTemplate
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.organization.common.FormatValidationUtil;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.common.OrgDataCacheUtil;
import com.jiuqi.va.organization.dao.VaOrgDataDao;
import com.jiuqi.va.organization.dao.VaOrgVersionDao;
import com.jiuqi.va.organization.domain.OrgDataImportProcess;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.domain.OrgExcelColumn;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.help.OrgContextService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import com.jiuqi.va.organization.service.impl.help.OrgDataModifyService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class OrgDataImportTemplate {
    private static Logger logger = LoggerFactory.getLogger(OrgDataImportTemplate.class);
    private static final String createRelatedOrgFlag = "createRelatedOrgFlag";
    protected BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
    protected EnumDataClient enumDataClient = (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
    protected AuthUserClient userClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
    protected OrgContextService orgContextService = (OrgContextService)ApplicationContextRegister.getBean(OrgContextService.class);
    protected OrgCategoryService orgCategoryService = (OrgCategoryService)ApplicationContextRegister.getBean(OrgCategoryService.class);
    protected OrgDataService orgDataClient = (OrgDataService)ApplicationContextRegister.getBean(OrgDataService.class);
    protected OrgDataModifyService orgDataModifyService = (OrgDataModifyService)ApplicationContextRegister.getBean(OrgDataModifyService.class);
    protected OrgDataCacheService orgDataCacheService = (OrgDataCacheService)ApplicationContextRegister.getBean(OrgDataCacheService.class);
    protected OrgDataParamService orgDataParamService = (OrgDataParamService)ApplicationContextRegister.getBean(OrgDataParamService.class);
    protected VaOrgVersionDao orgVersionDao = (VaOrgVersionDao)ApplicationContextRegister.getBean(VaOrgVersionDao.class);
    protected SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate)ApplicationContextRegister.getBean(SqlSessionTemplate.class);
    protected Sheet sheet;
    private int firstLine = 2;
    protected String importTableName;
    protected Date importVersionDate;
    public static final String IMPORTSTATE = "importstate";
    protected static final String IMPORTMEMO = "importmemo";
    private static final String IS_CHANGE_PARENTS = "isChangeParents";
    protected OrgDataImportProcess processInfo = null;
    protected List<OrgDTO> importDatas = new ArrayList<OrgDTO>();
    protected List<OrgExcelColumn> importColumns;
    protected Map<String, OrgExcelColumn> importColumnMap = new HashMap<String, OrgExcelColumn>();
    protected List<OrgExcelColumn> importRequiedFieldList = new ArrayList<OrgExcelColumn>();
    protected List<OrgExcelColumn> importUniqueFieldList = new ArrayList<OrgExcelColumn>();
    protected List<OrgExcelColumn> importRelatedFieldList = new ArrayList<OrgExcelColumn>();
    protected Map<String, Map<String, OrgDO>> allOrgMap = new HashMap<String, Map<String, OrgDO>>();
    protected Map<String, Map<String, OrgDO>> manageOrgMap = new HashMap<String, Map<String, OrgDO>>();
    protected Map<String, Map<String, OrgDO>> relOrgMap = new HashMap<String, Map<String, OrgDO>>();
    Map<String, Map<String, BaseDataDO>> relBaseDataMap = new HashMap<String, Map<String, BaseDataDO>>();
    Map<String, UserDO> userMap = new HashMap<String, UserDO>();
    Map<String, Map<String, EnumDataDO>> relEnumMap = new HashMap<String, Map<String, EnumDataDO>>();
    protected Map<String, Map<String, String>> uniqueDataMap = new HashMap<String, Map<String, String>>();
    protected Set<String> exisetOrgCodeSet = new HashSet<String>();

    public OrgDataImportTemplate(Sheet sheet, String tableName, Date versionDate, List<OrgExcelColumn> importColumns, int firstLine) {
        this.sheet = sheet;
        this.importTableName = tableName;
        this.importVersionDate = versionDate;
        this.importColumns = importColumns;
        this.firstLine = firstLine;
        this.initData(sheet);
        this.delNullCol();
        this.initParam();
    }

    public OrgDataImportTemplate(String tableName, Date versionDate, List<OrgExcelColumn> importColumns, List<OrgDTO> dataList) {
        this.importTableName = tableName;
        this.importVersionDate = versionDate;
        this.importColumns = importColumns;
        this.initData(dataList);
        this.delNullCol();
        this.initParam();
    }

    public void executeImportCheck() {
        if (this.importDatas.isEmpty()) {
            return;
        }
        for (OrgDTO data : this.importDatas) {
            this.checkFieldValueValid(data);
            this.checkReg(data);
            this.checkRequird(data);
            this.checkRepeat(data);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeImportSave() {
        if (this.importDatas.isEmpty()) {
            return;
        }
        OrgDTO rqParam = new OrgDTO();
        rqParam.setCategoryname(this.importTableName);
        rqParam.setVersionDate(this.importVersionDate);
        this.initVersionInfo(rqParam);
        String createUserId = "-";
        UserLoginDTO currLoginUser = ShiroUtil.getUser();
        if (currLoginUser != null) {
            createUserId = currLoginUser.getId();
        }
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        orgBatchOptDTO.setQueryParam(rqParam);
        ArrayList<OrgDTO> allDataList = new ArrayList<OrgDTO>();
        allDataList.addAll(this.importDatas);
        orgBatchOptDTO.setDataList(allDataList);
        R r = this.orgDataParamService.loadBatchOptExtendParam(orgBatchOptDTO, OrgDataAction.ImportByExcel);
        if (r.getCode() != 0) {
            throw new RuntimeException(r.getMsg());
        }
        if (this.processInfo != null && allDataList.size() != this.importDatas.size()) {
            this.processInfo.setTotal(this.processInfo.getTotal() + allDataList.size() - this.importDatas.size());
        }
        this.importDatas.clear();
        ArrayList<OrgDTO> checkOrgcodeList = new ArrayList<OrgDTO>();
        HashMap<String, OrgDTO> importRecordMap = new HashMap<String, OrgDTO>();
        OrgDTO data = null;
        for (OrgDO orgDO : allDataList) {
            data = orgDO instanceof OrgDTO ? (OrgDTO)orgDO : new OrgDTO((Map)orgDO);
            data.put("LOAD_EXTEND_PARAM_OVER", (Object)true);
            this.importDatas.add(data);
            data.putAll((Map)rqParam);
            this.checkExist(data);
            this.checkRelationLegal(data);
            this.checkUnique(data);
            this.checkOrgcode(data);
            if (!this.isCurrentLegal(data)) continue;
            if (StringUtils.hasText(data.getOrgcode())) {
                checkOrgcodeList.add(data);
            }
            importRecordMap.put(data.getCode(), data);
        }
        if (!checkOrgcodeList.isEmpty()) {
            orgBatchOptDTO.setDataList(checkOrgcodeList);
            OrgCategoryDO categoryParam = new OrgCategoryDO();
            categoryParam.setName(this.importTableName);
            categoryParam.setDeepClone(false);
            OrgCategoryDO orgCategoryDO = this.orgCategoryService.get(categoryParam);
            HashMap<String, String> errorResult = new HashMap<String, String>();
            this.orgDataParamService.checkOrgCode(orgBatchOptDTO, errorResult, orgCategoryDO, OrgDataAction.ImportByExcel);
            if (!errorResult.isEmpty()) {
                for (Map.Entry rs : errorResult.entrySet()) {
                    this.setImportMemo((OrgDTO)importRecordMap.get(rs.getKey()), (String)rs.getValue(), 9);
                }
            }
        }
        ArrayList<OrgDTO> orderList = new ArrayList<OrgDTO>(this.importDatas);
        Collections.sort(orderList, (o1, o2) -> {
            if (o1.getId() == null && o2.getId() != null) {
                return -1;
            }
            if (o1.getId() != null && o2.getId() != null) {
                if (o1.getRecoveryflag() != null && o2.getRecoveryflag() == null) {
                    return -1;
                }
                return 0;
            }
            return 0;
        });
        SqlSession sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        VaOrgDataDao orgDataDao = (VaOrgDataDao)sqlSession.getMapper(VaOrgDataDao.class);
        int batchSize = this.orgContextService.getBatchSubmitSize();
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        OrgDTO syncParam = new OrgDTO();
        syncParam.setCategoryname(this.importTableName);
        syncParam.setQueryStartVer(startVer);
        int total = orderList.size();
        int startIndex = 0;
        int endIndex = total - 1;
        boolean needSyncCache = false;
        boolean createRelOrg = false;
        OrgDTO endOrg = null;
        try {
            BigDecimal bd1 = startVer;
            BigDecimal bd2 = new BigDecimal(1.0E-4, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
            int cnt = 0;
            for (int i = 0; i < total; ++i) {
                endOrg = (OrgDTO)orderList.get(i);
                if (this.isCurrentLegal(endOrg)) {
                    endOrg.setCacheSyncDisable(Boolean.valueOf(true));
                    endOrg.put("orgVersionData", rqParam.get((Object)"orgVersionData"));
                    endOrg.put("ORG_BATCH_DAO", (Object)orgDataDao);
                    bd1 = bd1.add(bd2);
                    endOrg.put("ORG_FIXED_VER", (Object)bd1);
                    if (endOrg.getId() == null) {
                        endOrg.setCreateuser(createUserId);
                    }
                    this.checkStructLegal(endOrg);
                    this.executeSave(endOrg);
                }
                if (this.isCurrentLegal(endOrg)) {
                    ++cnt;
                    if (!createRelOrg && endOrg.containsKey((Object)"relOrgVer")) {
                        createRelOrg = true;
                    }
                }
                if (this.processInfo != null) {
                    this.processInfo.setCurrIndex(this.processInfo.getCurrIndex() + 1);
                    if (i % 200 == 0 || i == endIndex) {
                        OrgDataCacheUtil.setImportDataResult(this.processInfo.getRsKey(), JSONUtil.toJSONString((Object)this.processInfo));
                    }
                }
                if ((i <= 0 || i % batchSize != 0) && i != endIndex) continue;
                sqlSession.commit();
                sqlSession.clearCache();
                startIndex = i;
                needSyncCache = true;
            }
            LogUtil.add((String)"\u7ec4\u7ec7\u673a\u6784\u7ba1\u7406", (String)"\u8868\u683c\u5bfc\u5165", (String)this.importTableName, (String)"", (String)("\u5bfc\u5165\u4e86" + cnt + "\u6761\u6570\u636e"));
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5bfc\u5165\u5931\u8d25", e);
            sqlSession.rollback();
            for (int i = startIndex; i < total; ++i) {
                endOrg = (OrgDTO)orderList.get(i);
                if (!this.isCurrentLegal(endOrg)) continue;
                this.setImportMemo(endOrg, OrgCoreI18nUtil.getMessage("org.error.template.save", new Object[0]), -1);
            }
        }
        finally {
            sqlSession.close();
            if (needSyncCache) {
                this.updateCache(syncParam);
                if (createRelOrg) {
                    syncParam.clear();
                    syncParam.setCategoryname("MD_ORG");
                    syncParam.setQueryStartVer(startVer);
                    this.updateCache(syncParam);
                }
            }
        }
    }

    private void updateCache(OrgDTO param) {
        OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
        bdsc.setTenantName(param.getTenantName());
        bdsc.setOrgDTO(param);
        this.orgDataCacheService.pushSyncMsg(bdsc);
    }

    protected void initData(Sheet sheet) {
        this.importDatas.clear();
        if (sheet == null) {
            return;
        }
        for (int j = this.firstLine - 1; j < sheet.getLastRowNum() + 1; ++j) {
            Row row = sheet.getRow(j);
            if (this.isBlankRow(row, this.importColumns.size())) continue;
            OrgDTO newData = new OrgDTO();
            newData.put(IMPORTSTATE, (Object)0);
            newData.setCategoryname(this.importTableName);
            for (int k = 0; k < this.importColumns.size(); ++k) {
                OrgExcelColumn field = this.importColumns.get(k);
                if (field == null) continue;
                Cell cell = row.getCell(k);
                this.setFieldValue(field, cell, newData);
            }
            this.importDatas.add(newData);
        }
    }

    protected void initData(List<OrgDTO> dataList) {
        this.importDatas.clear();
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        HashSet<String> columns = new HashSet<String>();
        for (OrgExcelColumn column : this.importColumns) {
            if (column == null) continue;
            columns.add(column.getColumnName().toLowerCase());
        }
        for (int j = 0; j < dataList.size(); ++j) {
            OrgDTO newData = dataList.get(j);
            newData.computeIfAbsent((Object)IMPORTSTATE, key -> 0);
            newData.setCategoryname(this.importTableName);
            for (String col : columns) {
                if (newData.containsKey((Object)col)) continue;
                newData.put(col, null);
            }
            this.importDatas.add(newData);
        }
    }

    protected boolean isBlankRow(Row row, int columnNum) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i < columnNum; ++i) {
            Cell cell = row.getCell(i);
            if (cell == null || cell.getCellType().equals((Object)CellType.BLANK)) continue;
            return false;
        }
        return true;
    }

    private void initVersionInfo(OrgDTO orgParam) {
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(this.importTableName);
        if (this.importVersionDate != null) {
            orgVersionDTO.setVersionDate(this.importVersionDate);
        } else {
            Date loginDate = null;
            UserLoginDTO userLoginDTO = ShiroUtil.getUser();
            if (userLoginDTO != null) {
                loginDate = userLoginDTO.getLoginDate();
            }
            if (loginDate == null) {
                loginDate = new Date();
            }
            orgVersionDTO.setVersionDate(loginDate);
        }
        OrgVersionDO orgVersion = this.orgVersionDao.find(orgVersionDTO);
        if (orgVersion != null) {
            orgParam.put("orgVersionData", (Object)orgVersion);
        }
    }

    public void setFieldValue(OrgExcelColumn field, Cell cell, OrgDTO newData) {
        if (field == null) {
            return;
        }
        DataModelType.ColumnType type = field.getColumnType();
        String key = field.getColumnName().toLowerCase();
        boolean isBoolean = false;
        if (field.getMappingType() != null && field.getMappingType() == 0) {
            isBoolean = true;
        }
        if (cell == null || cell.getCellType().equals((Object)CellType.BLANK)) {
            if (isBoolean) {
                newData.put(key, (Object)0);
                return;
            }
            if ("code".equalsIgnoreCase(key)) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.not.empty", field.getColumnTitle() + "(" + field.getColumnName() + ")"), 1);
            }
            newData.put(key, null);
        } else {
            boolean error = false;
            CellType cellType = cell.getCellType();
            Object value = null;
            switch (type) {
                case UUID: 
                case NVARCHAR: 
                case CLOB: {
                    HSSFDataFormatter dataFormatter;
                    if (cellType.equals((Object)CellType.STRING)) {
                        value = cell.getStringCellValue();
                    } else if (cellType.equals((Object)CellType.NUMERIC)) {
                        dataFormatter = new HSSFDataFormatter();
                        value = dataFormatter.formatCellValue(cell);
                    } else {
                        error = true;
                    }
                    if (value == null || type != DataModelType.ColumnType.NVARCHAR) break;
                    value = value.toString().trim();
                    if (!field.getUppercase().booleanValue() || this.isEmpty(value)) break;
                    value = value.toString().toUpperCase();
                    break;
                }
                case INTEGER: {
                    HSSFDataFormatter dataFormatter;
                    if (cellType.equals((Object)CellType.NUMERIC)) {
                        dataFormatter = new HSSFDataFormatter();
                        value = dataFormatter.formatCellValue(cell);
                        if (!FormatValidationUtil.isInteger(value.toString())) {
                            error = true;
                            break;
                        }
                        try {
                            value = Integer.valueOf(value.toString());
                            value = isBoolean ? Integer.valueOf((Integer)value == 1 ? 1 : 0) : value;
                        }
                        catch (Throwable throwable) {}
                        break;
                    }
                    if (cellType == CellType.STRING && isBoolean) {
                        value = cell.getStringCellValue();
                        value = "\u662f".equals(value) || "yes".equalsIgnoreCase((String)value) || "1".equals(value) ? 1 : 0;
                        break;
                    }
                    error = true;
                    break;
                }
                case NUMERIC: {
                    if ("ordinal".equals(key) && cellType.equals((Object)CellType.STRING)) {
                        value = cell.getStringCellValue();
                        try {
                            new BigDecimal((String)value);
                        }
                        catch (Exception e) {
                            error = true;
                        }
                        break;
                    }
                    if (cellType.equals((Object)CellType.NUMERIC)) {
                        value = cell.getNumericCellValue();
                        break;
                    }
                    error = true;
                    break;
                }
                case DATE: 
                case TIMESTAMP: {
                    if (cellType.equals((Object)CellType.NUMERIC)) {
                        try {
                            value = cell.getDateCellValue();
                        }
                        catch (Exception e) {
                            error = true;
                        }
                        break;
                    }
                    error = true;
                    break;
                }
            }
            Object cellValue = this.getValueOfCell(cell);
            if (error && !this.isEmpty(cellValue.toString())) {
                newData.putVal(key, cellValue);
                String memo = OrgCoreI18nUtil.getMessage("org.error.template.import.column.valueType.not.match", field.getColumnTitle());
                this.setImportMemo(newData, memo, 1);
            } else {
                this.setOrgValue(field, newData, value);
            }
        }
    }

    private void setOrgValue(OrgExcelColumn field, OrgDTO newData, Object value) {
        String key = field.getColumnName().toLowerCase();
        if (value == null) {
            newData.put(key, value);
            return;
        }
        if ("code".equalsIgnoreCase(field.getColumnName()) || "orgcode".equalsIgnoreCase(field.getColumnName()) || "parentcode".equalsIgnoreCase(field.getColumnName())) {
            value = value.toString().toUpperCase();
        }
        if ("parentcode".equalsIgnoreCase(field.getColumnName()) || StringUtils.hasText(field.getMapping())) {
            String val = value.toString();
            if (field.getMultiple() != null && field.getMultiple().booleanValue()) {
                ArrayList<String> vlist = new ArrayList<String>();
                if (StringUtils.hasText(val)) {
                    for (String vcode : val.split("\\,")) {
                        vlist.add(vcode.split(" ")[0]);
                    }
                }
                newData.put(key, vlist);
            } else {
                newData.put(key, (Object)val.split(" ")[0]);
            }
        } else {
            newData.put(key, value);
        }
    }

    protected void setImportMemo(OrgDTO newData, String newMemo, int status) {
        newData.put(IMPORTSTATE, (Object)status);
        String memo = (String)newData.get((Object)IMPORTMEMO);
        memo = !this.isEmpty(memo) ? memo + ";" + newMemo : newMemo;
        newData.put(IMPORTMEMO, (Object)memo);
    }

    private boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }

    protected Object getValueOfCell(Cell cell) {
        if (cell == null || "".equals(cell.toString().trim())) {
            return "";
        }
        Object cellValue = "";
        CellType cellType = cell.getCellType();
        if (cellType == CellType.FORMULA) {
            cellValue = "";
            return cellValue;
        }
        switch (cellType) {
            case STRING: {
                cellValue = cell.getStringCellValue().trim();
                break;
            }
            case BOOLEAN: {
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            }
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted((Cell)cell)) {
                    cellValue = cell.getDateCellValue();
                    break;
                }
                HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                cellValue = dataFormatter.formatCellValue(cell);
                break;
            }
            default: {
                cellValue = "";
            }
        }
        cellValue = this.isEmpty(cellValue) || "".equals(cellValue.toString().trim()) ? "" : cellValue;
        return cellValue;
    }

    private void delNullCol() {
        this.importColumns = this.importColumns.stream().filter(o -> o != null).collect(Collectors.toList());
    }

    protected void initParam() {
        if (this.orgContextService.isAutoCreateAdminOrgFromOther()) {
            this.setAllOrgMap("MD_ORG");
        }
        this.setAllOrgMap(this.importTableName);
        this.setManageOrgMap(this.importTableName);
        for (OrgExcelColumn column : this.importColumns) {
            UserDTO userParam;
            PageVO userPage;
            boolean isDefaultRefField;
            String[] mappingStrs;
            String mapping;
            this.importColumnMap.put(column.getColumnName(), column);
            if (column.isNullable() != null && !column.isNullable().booleanValue()) {
                this.importRequiedFieldList.add(column);
            }
            if (column.getUnique() != null && !column.getUnique().booleanValue()) {
                this.importUniqueFieldList.add(column);
                HashMap<String, String> uniqueMap = new HashMap<String, String>();
                Map<String, OrgDO> map = this.allOrgMap.get(this.importTableName);
                String fieldName = column.getColumnName();
                for (Map.Entry<String, OrgDO> entry : map.entrySet()) {
                    Object value = entry.getValue().getValueOf(fieldName);
                    if (value == null) continue;
                    uniqueMap.put(value.toString(), entry.getKey());
                }
                this.uniqueDataMap.put(fieldName, uniqueMap);
            }
            if ((mapping = column.getMapping()) == null || "PARENTCODE".equals(column.getColumnName()) || (mappingStrs = column.getMapping().split("\\.")).length != 2) continue;
            this.importRelatedFieldList.add(column);
            String refTableName = mappingStrs[0];
            String refFieldName = mappingStrs[1];
            int mappingType = column.getMappingType();
            boolean bl = isDefaultRefField = mappingType == 4 && "CODE".equals(refFieldName) || mappingType == 1 && "OBJECTCODE".equals(refFieldName) || mappingType == 2 && "VAL".equals(refFieldName) || mappingType == 3 && "ID".equals(refFieldName);
            if (!column.getCheckval().booleanValue() && isDefaultRefField) continue;
            if (mappingType == 4) {
                this.setRelationOrgMap(refTableName);
                continue;
            }
            if (mappingType == 1) {
                BaseDataDTO queryParam = new BaseDataDTO();
                queryParam.setTableName(refTableName);
                queryParam.setStopflag(Integer.valueOf(-1));
                queryParam.setRecoveryflag(Integer.valueOf(-1));
                queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                queryParam.setIgnoreShareFields(Boolean.valueOf(true));
                queryParam.setAuthType(BaseDataOption.AuthType.NONE);
                List allData = this.baseDataClient.list(queryParam).getRows();
                HashMap<String, BaseDataDO> tempMap = new HashMap<String, BaseDataDO>();
                for (BaseDataDO data : allData) {
                    tempMap.put((String)data.get((Object)refFieldName.toLowerCase()), data);
                }
                this.relBaseDataMap.put(refTableName, tempMap);
                continue;
            }
            if (mappingType == 2) {
                EnumDataDTO enumQueryParam = new EnumDataDTO();
                enumQueryParam.setBiztype(refTableName);
                List res = this.enumDataClient.list(enumQueryParam);
                HashMap<String, EnumDataDO> temp = new HashMap<String, EnumDataDO>();
                for (EnumDataDO item : res) {
                    if ("TITLE".equals(refFieldName)) {
                        temp.put(item.getTitle(), item);
                        continue;
                    }
                    temp.put(item.getVal(), item);
                }
                this.relEnumMap.put(refTableName, temp);
                continue;
            }
            if (mappingType != 3 || (userPage = this.userClient.list(userParam = new UserDTO())) == null || userPage.getTotal() <= 0) continue;
            for (UserDO userDO : userPage.getRows()) {
                if ("USERNAME".equals(refFieldName)) {
                    this.userMap.put(userDO.getUsername(), userDO);
                    continue;
                }
                if ("EMAIL".equals(refFieldName)) {
                    this.userMap.put(userDO.getEmail(), userDO);
                    continue;
                }
                if ("TELEPHONE".equals(refFieldName)) {
                    this.userMap.put(userDO.getTelephone(), userDO);
                    continue;
                }
                this.userMap.put(userDO.getId(), userDO);
            }
        }
    }

    private void setAllOrgMap(String categoryName) {
        if (this.allOrgMap.get(categoryName) != null) {
            return;
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(categoryName);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setAuthType(OrgDataOption.AuthType.NONE);
        if (this.importTableName.equals(categoryName)) {
            queryParam.setVersionDate(this.importVersionDate);
        }
        HashMap<String, OrgDO> allOrg = new HashMap<String, OrgDO>();
        PageVO<OrgDO> temp = this.orgDataClient.list(queryParam);
        if (temp != null && temp.getRows() != null) {
            for (OrgDO org : temp.getRows()) {
                allOrg.put(org.getCode(), org);
                allOrg.put(org.getName(), org);
            }
        }
        this.allOrgMap.put(categoryName, allOrg);
    }

    private void setManageOrgMap(String categoryName) {
        if (this.manageOrgMap.get(categoryName) != null) {
            return;
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(categoryName);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setAuthType(OrgDataOption.AuthType.MANAGE);
        if (this.importTableName.equals(categoryName)) {
            queryParam.setVersionDate(this.importVersionDate);
        }
        HashMap<String, OrgDO> authOrg = new HashMap<String, OrgDO>();
        PageVO<OrgDO> temp = this.orgDataClient.list(queryParam);
        if (temp != null && temp.getRows() != null) {
            for (OrgDO org : this.orgDataClient.list(queryParam).getRows()) {
                authOrg.put(org.getCode(), org);
            }
        }
        this.manageOrgMap.put(categoryName, authOrg);
    }

    private void setRelationOrgMap(String categoryName) {
        if (this.relOrgMap.get(categoryName) != null) {
            return;
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(categoryName);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setAuthType(OrgDataOption.AuthType.ACCESS);
        if (this.importTableName.equals(categoryName)) {
            queryParam.setVersionDate(this.importVersionDate);
        }
        HashMap<String, OrgDO> relOrg = new HashMap<String, OrgDO>();
        PageVO<OrgDO> temp = this.orgDataClient.list(queryParam);
        if (temp != null && temp.getRows() != null) {
            for (OrgDO org : temp.getRows()) {
                relOrg.put(org.getCode(), org);
                relOrg.put(org.getName(), org);
            }
        }
        this.relOrgMap.put(categoryName, relOrg);
    }

    private void checkFieldValueValid(OrgDTO newData) {
        for (OrgExcelColumn column : this.importColumns) {
            String[] values;
            BigDecimal currValue;
            Object value = newData.get((Object)column.getColumnName().toLowerCase());
            if (this.isEmpty(value)) continue;
            if (column.getColumnType() == DataModelType.ColumnType.NVARCHAR) {
                if (value.toString().length() <= column.getLengths()[0]) continue;
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.value.length.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", value.toString().length(), column.getLengths()[0]), 1);
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.NUMERIC) {
                currValue = new BigDecimal(value.toString());
                values = currValue.toPlainString().split("\\.");
                int decimalCount = 0;
                if (values.length > 1) {
                    if (values[1].length() > column.getLengths()[1]) {
                        this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.maximum.precision.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", values[1].length(), column.getLengths()[1]), 1);
                        continue;
                    }
                    decimalCount = values[1].length();
                }
                if (values.length <= 0 || values[0].length() + decimalCount <= column.getLengths()[0]) continue;
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.value.length.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", values[0].length() + decimalCount, column.getLengths()[0]), 1);
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.INTEGER) {
                currValue = new BigDecimal(value.toString());
                values = currValue.toPlainString().split("\\.");
                int maxLen = Math.min(column.getLengths()[0], 10);
                if (values[0].length() > maxLen) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.value.length.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", values[0].length(), maxLen), 1);
                    continue;
                }
                BigDecimal maxValue = new BigDecimal(Integer.MAX_VALUE);
                if (currValue.compareTo(maxValue) <= 0) continue;
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.maximum.value.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", currValue.toPlainString(), maxValue.toPlainString()), 1);
                continue;
            }
            if (column.getColumnType() != DataModelType.ColumnType.UUID || !this.isEmpty(column.getMapping()) && !"ID".equals(column.getMapping().split("\\.")[1])) continue;
            try {
                UUID.fromString(value.toString());
            }
            catch (Exception e) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.not.match.uuid", column.getColumnTitle() + "(" + column.getColumnName() + ")", value.toString()), 1);
            }
        }
    }

    private void checkRequird(OrgDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        String fieldName = null;
        for (OrgExcelColumn requiedField : this.importRequiedFieldList) {
            fieldName = requiedField.getColumnName().toLowerCase();
            if ("parentcode".equals(fieldName)) continue;
            Map oldValueMap = (Map)newData.get((Object)"oldValueMap");
            if (!this.isEmpty(newData.get((Object)fieldName)) || oldValueMap != null && oldValueMap.get(fieldName) != null) continue;
            String memo = OrgCoreI18nUtil.getMessage("org.error.template.import.not.empty", requiedField.getColumnTitle() + "(" + requiedField.getColumnName()) + ")";
            this.setImportMemo(newData, memo, 2);
        }
    }

    private void checkReg(OrgDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        if (!StringUtils.hasText(newData.getCode()) || !FormatValidationUtil.isOrgCode(newData.getCode())) {
            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.add.code.contains.special.characters", new Object[0]), 3);
        }
    }

    private void checkRepeat(OrgDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        if (this.exisetOrgCodeSet.contains(newData.getCode())) {
            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.add.code.duplicate", new Object[0]), 4);
        } else {
            this.exisetOrgCodeSet.add(newData.getCode());
        }
    }

    private void checkStructLegal(OrgDTO newData) {
        UserLoginDTO user;
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        String categoryname = newData.getCategoryname();
        String newParentcode = newData.getParentcode();
        if (!StringUtils.hasText(newParentcode)) {
            newParentcode = "-";
        }
        if (!"-".equals(newParentcode)) {
            OrgDO parentNode = this.allOrgMap.get(categoryname).get(newParentcode);
            if (parentNode == null) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.parent.not.exist", new Object[0]), 5);
            } else if (parentNode.getRecoveryflag() != null && parentNode.getRecoveryflag() == 1) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.parent.obsolete", new Object[0]), 5);
            } else if (parentNode.getStopflag() == 1) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.parent.inactive", new Object[0]), 5);
            } else if (newData.getCode().equals(parentNode.getCode())) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.parent.not.own", new Object[0]), 5);
            } else {
                newData.setParentcode(parentNode.getCode());
                boolean checkflag = false;
                for (String pcode : parentNode.getParents().split("\\/")) {
                    if (!newData.getCode().equals(pcode)) continue;
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.parents.endless.chain", new Object[0]), 5);
                    checkflag = true;
                    break;
                }
                if (!checkflag) {
                    newData.setParents(parentNode.getParents() + "/" + newData.getCode());
                }
            }
        } else {
            newData.setParentcode("-");
            newData.setParents(newData.getCode());
        }
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        OrgDO currCatOrg = this.allOrgMap.get(categoryname).get(newData.getCode());
        if (currCatOrg != null && !currCatOrg.getParentcode().equals(newParentcode)) {
            newData.put(IS_CHANGE_PARENTS, (Object)currCatOrg.getParents());
        }
        if ((user = ShiroUtil.getUser()) == null || "super".equals(user.getMgrFlag())) {
            return;
        }
        if (currCatOrg == null || !currCatOrg.getParentcode().equals(newParentcode)) {
            if ("-".equals(newParentcode)) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.move.parent.not.management.permission", newData.getCode()), 5);
            } else {
                OrgDO authCurCatOrg = this.manageOrgMap.get(categoryname).get(newParentcode);
                if (authCurCatOrg == null) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.move.parent.not.management.permission", newData.getCode()), 5);
                }
            }
        }
    }

    private void checkExist(OrgDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        if (!(!this.orgContextService.isAutoCreateAdminOrgFromOther() || newData.getCategoryname().equals("MD_ORG") || newData.containsKey((Object)"ignoreCodeRefImp") && ((Boolean)newData.get((Object)"ignoreCodeRefImp")).booleanValue())) {
            Map<String, OrgDO> all = this.allOrgMap.get("MD_ORG");
            OrgDO goalOrg = all.get(newData.getCode());
            if (goalOrg == null) {
                if (this.orgContextService.isAddFromOtherAllow()) {
                    newData.put(createRelatedOrgFlag, (Object)true);
                } else {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.admin.org.not.exist", newData.getCode()), 6);
                }
            } else if (goalOrg.getRecoveryflag() != null && goalOrg.getRecoveryflag() == 1) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.admin.org.obsolete", newData.getCode()), 6);
            }
        }
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        String categoryname = newData.getCategoryname();
        OrgDO currCatOrg = this.allOrgMap.get(categoryname).get(newData.getCode());
        if (currCatOrg == null) {
            return;
        }
        OrgDO authCurCatOrg = this.manageOrgMap.get(categoryname).get(newData.getCode());
        if (authCurCatOrg == null) {
            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.org.no.mgr.permission", newData.getCode()), 6);
        } else {
            newData.setId(authCurCatOrg.getId());
            if (newData.getStopflag() == null) {
                newData.setStopflag(authCurCatOrg.getStopflag());
            }
            if (authCurCatOrg.getRecoveryflag() == 1) {
                newData.setRecoveryflag(Integer.valueOf(0));
            } else {
                newData.remove((Object)"recoveryflag");
            }
        }
    }

    private void checkRelationLegal(OrgDTO newData) {
        for (OrgExcelColumn column : this.importRelatedFieldList) {
            UserDO goal;
            String tipValsInfo;
            String mappingTableName;
            String colName;
            Object valueObj;
            Integer mappingType;
            String mapping;
            if (column.getCheckval() == null || !column.getCheckval().booleanValue() || this.isEmpty(mapping = column.getMapping()) || 1 != (mappingType = column.getMappingType()) && 4 != mappingType && 3 != mappingType && 2 != mappingType || (valueObj = newData.get((Object)(colName = column.getColumnName().toLowerCase()))) == null || this.isEmpty(mappingTableName = mapping.split("\\.")[0])) continue;
            String baseInfo = column.getColumnTitle() + "(" + colName + ")";
            if (mappingType == 4 || mappingTableName.equals("MD_ORG") || mappingTableName.startsWith("MD_ORG_")) {
                tipValsInfo = mappingTableName + "[" + (String)valueObj + "]";
                OrgDO orgDO = this.relOrgMap.get(mappingTableName).get((String)valueObj);
                if (orgDO == null) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.org.not.exist", baseInfo, tipValsInfo), 7);
                    continue;
                }
                if (orgDO.getRecoveryflag() != null && orgDO.getRecoveryflag() == 1) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.obsolete", baseInfo, tipValsInfo), 7);
                    continue;
                }
                if (orgDO.getStopflag() != null && orgDO.getStopflag() == 1) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.inactive", baseInfo, tipValsInfo), 7);
                    continue;
                }
                newData.put(colName, (Object)orgDO.getCode());
                continue;
            }
            if (mappingType == 1) {
                if (this.relBaseDataMap.get(mappingTableName) == null) continue;
                List<String> cbdList = null;
                ArrayList<String> cbdObjList = null;
                if (valueObj instanceof String) {
                    cbdList = new ArrayList<String>();
                    cbdList.add((String)valueObj);
                } else if (valueObj instanceof List) {
                    cbdList = (List)valueObj;
                    cbdObjList = new ArrayList<String>();
                }
                for (String value : cbdList) {
                    tipValsInfo = mappingTableName + "[" + value + "]";
                    BaseDataDO dataDO = this.relBaseDataMap.get(mappingTableName).get(value);
                    if (dataDO == null) {
                        this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.not.exist", baseInfo, tipValsInfo), 7);
                        continue;
                    }
                    if (dataDO.getRecoveryflag() != null && dataDO.getRecoveryflag() == 1) {
                        this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.obsolete", baseInfo, tipValsInfo), 7);
                        continue;
                    }
                    if (dataDO.getStopflag() != null && dataDO.getStopflag() == 1) {
                        this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.inactive", baseInfo, tipValsInfo), 7);
                        continue;
                    }
                    if (cbdObjList != null) {
                        cbdObjList.add(dataDO.getObjectcode());
                        continue;
                    }
                    newData.put(colName, (Object)dataDO.getObjectcode());
                }
                if (valueObj instanceof List) {
                    newData.put(colName, cbdObjList);
                }
                Map<String, String> showTitleMap = null;
                if (newData.containsKey((Object)"showTitleMap")) {
                    showTitleMap = (Map)newData.get((Object)"showTitleMap");
                } else {
                    showTitleMap = new HashMap();
                    newData.put("showTitleMap", showTitleMap);
                }
                if (valueObj instanceof List) {
                    showTitleMap.put(colName, JSONUtil.toJSONString(cbdList).replace("\"", ""));
                    continue;
                }
                showTitleMap.put(colName, (String)valueObj);
                continue;
            }
            if (mappingType == 2) {
                tipValsInfo = mappingTableName + "[" + (String)valueObj + "]";
                goal = this.relEnumMap.get(mappingTableName).get((String)valueObj);
                if (goal == null) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.not.exist", baseInfo, tipValsInfo), 7);
                    continue;
                }
                if (goal.getStatus() != null && goal.getStatus() == 1) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.inactive", baseInfo, tipValsInfo), 7);
                    continue;
                }
                newData.put(colName, (Object)goal.getVal());
                continue;
            }
            if (mappingType != 3) continue;
            tipValsInfo = "USER[" + (String)valueObj + "]";
            goal = this.userMap.get((String)valueObj);
            if (goal == null) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.not.exist", baseInfo, tipValsInfo), 7);
                continue;
            }
            if (goal.getStopflag() != null && goal.getStopflag() == 1) {
                this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.associated.data.inactive", baseInfo, tipValsInfo), 7);
                continue;
            }
            newData.put(colName, (Object)goal.getId());
        }
    }

    private void checkUnique(OrgDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        for (OrgExcelColumn uniqueField : this.importUniqueFieldList) {
            String fieldName = uniqueField.getColumnName();
            Object value = newData.getValueOf(fieldName);
            if (value == null || !StringUtils.hasText(value.toString()) || !this.uniqueDataMap.get(fieldName).keySet().contains(value.toString()) || this.uniqueDataMap.get(fieldName).get(value.toString()).equals(newData.getCode())) continue;
            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.template.import.not.duplicate", uniqueField.getColumnTitle() + "(" + uniqueField.getColumnName() + ")"), 8);
        }
    }

    private void checkOrgcode(OrgDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        if (!this.orgContextService.isModifyOrgcodeAllow() && StringUtils.hasText(newData.getOrgcode()) && !newData.getOrgcode().equals(newData.getCode())) {
            this.setImportMemo(newData, "org.error.orgdata.check.codeAndOrgcode.inconsistent", 9);
        }
    }

    private void executeSave(OrgDTO newData) {
        block19: {
            if (!this.isCurrentLegal(newData)) {
                return;
            }
            newData.setRecoveryflag(Integer.valueOf(0));
            if (newData.getId() == null) {
                try {
                    int relatedFlag = 1;
                    OrgDTO mdOrg = null;
                    if (newData.containsKey((Object)createRelatedOrgFlag) && ((Boolean)newData.get((Object)createRelatedOrgFlag)).booleanValue()) {
                        mdOrg = new OrgDTO();
                        mdOrg.putAll((Map)newData);
                        mdOrg.setCategoryname("MD_ORG");
                        mdOrg.remove((Object)"orgVersionData");
                        Calendar ca = Calendar.getInstance();
                        ca.set(9998, 0, 1);
                        Date versionDate = ca.getTime();
                        mdOrg.setVersionDate(versionDate);
                        R r = this.orgDataParamService.checkOrgCode(mdOrg, OrgDataAction.Add);
                        if (r.getCode() != 0) {
                            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.add.admin.org.code.duplicate", new Object[0]), 10);
                        } else {
                            relatedFlag = this.orgDataModifyService.add(mdOrg);
                        }
                        if (relatedFlag > 0) {
                            newData.put("relOrgVer", (Object)mdOrg.getVer());
                        }
                    }
                    if (relatedFlag > 0) {
                        int flag = this.orgDataModifyService.add(newData);
                        if (flag > 0) {
                            String categoryname = newData.getCategoryname();
                            this.allOrgMap.get(categoryname).put(newData.getCode(), (OrgDO)newData);
                            this.manageOrgMap.get(categoryname).put(newData.getCode(), (OrgDO)newData);
                            if (mdOrg != null) {
                                this.allOrgMap.get("MD_ORG").put(mdOrg.getCode(), (OrgDO)mdOrg);
                            }
                            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.success.orgdata.add", new Object[0]), 0);
                        } else {
                            this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.add", new Object[0]), 10);
                        }
                        break block19;
                    }
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.add.admin.org", new Object[0]), 10);
                }
                catch (Exception e) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.add", new Object[0]), 10);
                }
            } else {
                try {
                    if (!StringUtils.hasText(newData.getParentcode())) {
                        newData.remove((Object)"parentcode");
                    }
                    newData.remove((Object)"createuser");
                    int flag = this.orgDataModifyService.update(newData);
                    if (flag > 0) {
                        this.allOrgMap.get(this.importTableName).put(newData.getCode(), (OrgDO)newData);
                        this.manageOrgMap.get(this.importTableName).put(newData.getCode(), (OrgDO)newData);
                        this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.success.orgdata.update", new Object[0]), 0);
                        if (newData.containsKey((Object)IS_CHANGE_PARENTS)) {
                            this.changeChildrenParents(newData);
                        }
                    } else {
                        this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.update", new Object[0]), 10);
                    }
                }
                catch (Exception e) {
                    this.setImportMemo(newData, OrgCoreI18nUtil.getMessage("org.error.orgdata.update", new Object[0]), 10);
                }
            }
        }
    }

    private void changeChildrenParents(OrgDTO newData) {
        String oldParents = (String)newData.get((Object)IS_CHANGE_PARENTS);
        String newParents = newData.getParents();
        String categoryName = newData.getCategoryname();
        OrgDTO param = new OrgDTO();
        param.setCategoryname(categoryName);
        param.setVersionDate(newData.getVersionDate());
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setStopflag(Integer.valueOf(-1));
        param.setRecoveryflag(Integer.valueOf(-1));
        param.setForceUpdateHistoryVersionData(Boolean.valueOf(newData.isForceUpdateHistoryVersionData()));
        param.setCode(newData.getCode());
        param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
        List<OrgDO> list = this.orgDataCacheService.listBasicCacheData(param);
        if (list == null || list.isEmpty()) {
            return;
        }
        param.remove((Object)"code");
        param.remove((Object)"stopflag");
        param.remove((Object)"recoveryflag");
        param.setQueryChildrenType(null);
        param.setCacheSyncDisable(Boolean.valueOf(true));
        param.put("ORG_BATCH_DAO", newData.get((Object)"ORG_BATCH_DAO"));
        this.orgDataParamService.getOrgVersion(param);
        int point = oldParents.length();
        OrgDTO tempData = new OrgDTO();
        OrgDO subOldData = null;
        for (OrgDO org : list) {
            subOldData = this.allOrgMap.get(categoryName).get(org.getCode());
            if (subOldData.containsKey((Object)IS_CHANGE_PARENTS)) continue;
            tempData.clear();
            tempData.putAll((Map)param);
            tempData.setId(org.getId());
            tempData.setParents(newParents + org.getParents().substring(point));
            this.orgDataModifyService.updateData(tempData);
        }
    }

    protected boolean isCurrentLegal(OrgDTO newData) {
        newData.computeIfAbsent((Object)IMPORTSTATE, key -> 0);
        Object state = newData.get((Object)IMPORTSTATE);
        return (Integer)state == 0;
    }

    public List<OrgDTO> getImportDatas() {
        return this.importDatas;
    }

    public void setProcessInfo(OrgDataImportProcess processInfo) {
        this.processInfo = processInfo;
    }

    public List<OrgDTO> getImportResult() {
        HashSet<String> rsKeys = new HashSet<String>();
        rsKeys.add(IMPORTSTATE);
        rsKeys.add(IMPORTMEMO);
        for (OrgExcelColumn col : this.importColumns) {
            rsKeys.add(col.getColumnName().toLowerCase());
        }
        for (OrgDTO curData : this.importDatas) {
            Iterator it = curData.keySet().iterator();
            while (it.hasNext()) {
                if (rsKeys.contains(it.next())) continue;
                it.remove();
            }
        }
        return this.importDatas;
    }
}

