/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.BaseDataAction
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
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
package com.jiuqi.va.basedata.common;

import com.jiuqi.va.basedata.common.BaseDataCacheUtil;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.common.BasedataExcelUtils;
import com.jiuqi.va.basedata.common.FormatValidationUtil;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.domain.BaseDataExcleColumn;
import com.jiuqi.va.basedata.domain.BaseDataImportProcess;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataModifyService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.BaseDataAction;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
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

public class BaseDataImportTemplate {
    private static Logger logger = LoggerFactory.getLogger(BaseDataImportTemplate.class);
    protected BaseDataService baseDataClient = (BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class);
    protected BaseDataDefineService baseDataDefineClient = (BaseDataDefineService)ApplicationContextRegister.getBean(BaseDataDefineService.class);
    protected BaseDataParamService baseDataParamService = (BaseDataParamService)ApplicationContextRegister.getBean(BaseDataParamService.class);
    protected BaseDataModifyService baseDataModifyService = (BaseDataModifyService)ApplicationContextRegister.getBean(BaseDataModifyService.class);
    protected BaseDataCacheService baseDataCacheService = (BaseDataCacheService)ApplicationContextRegister.getBean(BaseDataCacheService.class);
    protected BaseDataContextService baseDataContextService = (BaseDataContextService)ApplicationContextRegister.getBean(BaseDataContextService.class);
    protected EnumDataClient enumDataClient = (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
    protected AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
    protected OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
    protected OrgCategoryClient orgCategoryClient = (OrgCategoryClient)ApplicationContextRegister.getBean(OrgCategoryClient.class);
    protected DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
    protected SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate)ApplicationContextRegister.getBean(SqlSessionTemplate.class);
    protected Sheet sheet = null;
    protected int firstLine = 2;
    protected String importTableName = null;
    protected String importUnitcode = null;
    protected String importState = "importstate";
    protected String importMemo = "importmemo";
    protected BaseDataImportProcess processInfo = null;
    protected List<BaseDataDTO> importDatas = new ArrayList<BaseDataDTO>();
    protected Map<String, List<BaseDataDTO>> importDataMap = new HashMap<String, List<BaseDataDTO>>();
    protected List<BaseDataExcleColumn> importColumns = new ArrayList<BaseDataExcleColumn>();
    protected Map<String, BaseDataExcleColumn> importColumnMap = new HashMap<String, BaseDataExcleColumn>();
    protected List<BaseDataExcleColumn> specialImportColumns = new ArrayList<BaseDataExcleColumn>();
    protected List<BaseDataExcleColumn> importRequiedFieldList = new ArrayList<BaseDataExcleColumn>();
    protected List<BaseDataExcleColumn> importRelatedFieldList = new ArrayList<BaseDataExcleColumn>();
    protected List<String> importShareFields = new ArrayList<String>();
    protected List<String> requiredReviewFieldList = new ArrayList<String>();
    protected Set<String> existDataUniqueCodeSet = new HashSet<String>();
    protected Map<String, List<String>> shareFieldsMap = new HashMap<String, List<String>>();
    protected Map<String, BaseDataDefineDO> defineMap = new HashMap<String, BaseDataDefineDO>();
    protected Map<String, DataModelDO> modelMap = new HashMap<String, DataModelDO>();
    protected Map<String, Map<String, List<BaseDataDO>>> baseDataListMap = new HashMap<String, Map<String, List<BaseDataDO>>>();
    protected Map<String, Map<String, Map<String, BaseDataDO>>> baseDataMap = new HashMap<String, Map<String, Map<String, BaseDataDO>>>();
    protected Map<String, Map<String, Map<String, Map<String, BaseDataDO>>>> specialBaseDataQueryMap = new HashMap<String, Map<String, Map<String, Map<String, BaseDataDO>>>>();
    protected Map<String, OrgCategoryDO> allOrgCategoryMap = new HashMap<String, OrgCategoryDO>();
    protected Map<String, Map<String, OrgDO>> authOrgMap = new HashMap<String, Map<String, OrgDO>>();
    protected Map<String, Map<String, OrgDO>> allOrgMap = new HashMap<String, Map<String, OrgDO>>();
    protected Map<String, Map<String, Map<String, OrgDO>>> specialOrgQueryMap = new HashMap<String, Map<String, Map<String, OrgDO>>>();
    protected Map<String, Map<String, EnumDataDO>> enumDataMap = new HashMap<String, Map<String, EnumDataDO>>();
    protected Map<String, Map<String, Map<String, EnumDataDO>>> specialEnumDataQueryMap = new HashMap<String, Map<String, Map<String, EnumDataDO>>>();
    protected Map<String, UserDO> userMap = new HashMap<String, UserDO>();
    protected Map<String, Map<Object, UserDO>> specialUserDataQueryMap = new HashMap<String, Map<Object, UserDO>>();

    public BaseDataImportTemplate(Sheet sheet, String tableName, List<BaseDataExcleColumn> importColumns) {
        this(sheet, tableName, importColumns, 2, null);
    }

    public BaseDataImportTemplate(Sheet sheet, String tableName, List<BaseDataExcleColumn> importColumns, int firstLine) {
        this(sheet, tableName, importColumns, firstLine, null);
    }

    public BaseDataImportTemplate(Sheet sheet, String tableName, List<BaseDataExcleColumn> importColumns, int firstLine, String unitcode) {
        this.sheet = sheet;
        this.importTableName = tableName;
        this.importColumns = importColumns;
        this.firstLine = firstLine;
        this.initData(sheet);
        this.delNullCol();
        this.initParam();
        this.importUnitcode = unitcode;
        if (this.importUnitcode == null) {
            UserLoginDTO currLoginUser = ShiroUtil.getUser();
            this.importUnitcode = currLoginUser != null ? currLoginUser.getLoginUnit() : "-";
        }
    }

    public BaseDataImportTemplate(String tableName, List<BaseDataExcleColumn> importColumns, List<BaseDataDTO> dataList) {
        this(tableName, importColumns, dataList, null);
    }

    public BaseDataImportTemplate(String tableName, List<BaseDataExcleColumn> importColumns, List<BaseDataDTO> dataList, String unitcode) {
        this.importTableName = tableName;
        this.importColumns = importColumns;
        this.initData(dataList);
        this.delNullCol();
        this.initParam();
        this.importUnitcode = unitcode;
        if (this.importUnitcode == null) {
            UserLoginDTO currLoginUser = ShiroUtil.getUser();
            this.importUnitcode = currLoginUser != null ? currLoginUser.getLoginUnit() : "-";
        }
    }

    public void executeImport() {
        this.executeImportCheck();
        this.executeImportSave();
    }

    public void executeImportCheck() {
        if (this.importDatas.isEmpty()) {
            return;
        }
        BaseDataDTO basedataParam = new BaseDataDTO();
        basedataParam.setTableName(this.importTableName);
        R checkRs = this.baseDataParamService.modifyVersionCheck(basedataParam);
        if (checkRs.getCode() != 0) {
            throw new RuntimeException(checkRs.getMsg());
        }
        BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)basedataParam);
        int sharetype = defineDO.getSharetype();
        for (BaseDataDTO data : this.importDatas) {
            if (sharetype == 0) {
                data.setUnitcode("-");
            } else if (data.getUnitcode() == null) {
                data.setUnitcode(this.importUnitcode);
            }
            this.checkFieldValueValid(data);
            this.checkShareLegal(data);
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
        BaseDataDTO basedataParam = new BaseDataDTO();
        basedataParam.setTableName(this.importTableName);
        R checkRs = this.baseDataParamService.modifyVersionCheck(basedataParam);
        if (checkRs.getCode() != 0) {
            throw new RuntimeException(checkRs.getMsg());
        }
        String createUserId = "-";
        UserLoginDTO currLoginUser = ShiroUtil.getUser();
        if (currLoginUser != null) {
            createUserId = currLoginUser.getId();
        }
        BaseDataBatchOptDTO batchOptDTO = new BaseDataBatchOptDTO();
        basedataParam.setUnitcode(this.importUnitcode);
        basedataParam.put("_UpdateDataSync", (Object)true);
        basedataParam.put("_UpdateExcelSave", (Object)true);
        batchOptDTO.setQueryParam(basedataParam);
        ArrayList<BaseDataDTO> allDataList = new ArrayList<BaseDataDTO>();
        allDataList.addAll(this.importDatas);
        batchOptDTO.setDataList(allDataList);
        R rs = this.baseDataParamService.loadBatchOptExtendParam(batchOptDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            throw new RuntimeException(rs.getMsg());
        }
        this.importDatas.clear();
        BaseDataDTO newData = null;
        for (BaseDataDO baseDataDO : allDataList) {
            newData = baseDataDO instanceof BaseDataDTO ? (BaseDataDTO)baseDataDO : new BaseDataDTO((Map)baseDataDO);
            newData.put("LOAD_EXTEND_PARAM_OVER", (Object)true);
            this.importDatas.add(newData);
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(this.importTableName);
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
        basedataParam.put("hasDataModel", (Object)dataModelDO);
        this.baseDataParamService.getShowFieldList((BaseDataDO)basedataParam);
        ArrayList<BaseDataDTO> orderList = new ArrayList<BaseDataDTO>(this.importDatas);
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
        VaBaseDataDao baseDataDao = (VaBaseDataDao)sqlSession.getMapper(VaBaseDataDao.class);
        int batchSize = this.baseDataContextService.getBatchSubmitSize();
        BigDecimal startVer = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        BaseDataDTO syncParam = new BaseDataDTO();
        syncParam.setTableName(this.importTableName);
        syncParam.setQueryStartVer(startVer);
        int total = orderList.size();
        int startIndex = 0;
        int endtIndex = total - 1;
        boolean needSyncCache = false;
        BaseDataDTO endData = null;
        try {
            BaseDataDefineDO defineDO = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)basedataParam);
            int sharetype = defineDO.getSharetype();
            BigDecimal bd1 = startVer;
            BigDecimal bd2 = new BigDecimal(1.0E-4, new MathContext(19)).setScale(6, RoundingMode.HALF_UP);
            String unticode = null;
            for (int i = 0; i < total; ++i) {
                endData = (BaseDataDTO)orderList.get(i);
                if (this.isCurrentLegal(endData)) {
                    unticode = endData.getUnitcode();
                    endData.putAll((Map)basedataParam);
                    if (sharetype == 0) {
                        endData.setUnitcode("-");
                    } else if (unticode == null) {
                        endData.setUnitcode(this.importUnitcode);
                    } else {
                        endData.setUnitcode(unticode);
                    }
                    endData.setCacheSyncDisable(Boolean.valueOf(true));
                    endData.put("CHECK_VALUE_VALID_OVER", (Object)true);
                    endData.put("CHECK_FIELD_REQUIRED_OVER", (Object)true);
                    endData.put("BASEDATA_BATCH_DAO", (Object)baseDataDao);
                    bd1 = bd1.add(bd2);
                    endData.put("BASEDATA_FIXED_VER", (Object)bd1);
                    this.checkStructLegal(endData);
                    this.checkRelationLegal(endData);
                    this.checkExist(endData);
                    if (endData.getId() == null && endData.getCreateuser() == null) {
                        endData.setCreateuser(createUserId);
                    }
                    this.executeSave(endData);
                }
                if (this.processInfo != null) {
                    this.processInfo.setCurrIndex(this.processInfo.getCurrIndex() + 1);
                    if (i % 200 == 0 || i == endtIndex) {
                        BaseDataCacheUtil.setImportDataResult(this.processInfo.getRsKey(), JSONUtil.toJSONString((Object)this.processInfo));
                    }
                }
                if ((i <= 0 || i % batchSize != 0) && i != endtIndex) continue;
                sqlSession.commit();
                sqlSession.clearCache();
                startIndex = i;
                needSyncCache = true;
            }
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u5bfc\u5165\u5931\u8d25", e);
            sqlSession.rollback();
            for (int i = startIndex; i < total; ++i) {
                endData = (BaseDataDTO)orderList.get(i);
                if (!this.isCurrentLegal(endData)) continue;
                this.setImportMemo(endData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.save", new Object[0]), -1);
            }
        }
        finally {
            sqlSession.close();
            if (needSyncCache) {
                this.updateCache(syncParam);
            }
        }
    }

    private void delNullCol() {
        this.importColumns = this.importColumns.stream().filter(o -> o != null).collect(Collectors.toList());
    }

    protected void initParam() {
        this.loadDefine(this.importTableName);
        this.initOrgCategory();
        this.importShareFields = this.getSharefields(this.importTableName);
        for (BaseDataExcleColumn column : this.importColumns) {
            boolean isDefaultRefField;
            String mapping;
            this.importColumnMap.put(column.getColumnName(), column);
            if (column.isNullable() != null && !column.isNullable().booleanValue()) {
                this.importRequiedFieldList.add(column);
            }
            if ((mapping = column.getMapping()) == null || !mapping.contains(".") || "parentcode".equalsIgnoreCase(column.getColumnName())) continue;
            this.importRelatedFieldList.add(column);
            String[] mappingAt = mapping.split("\\.");
            String refTableName = mappingAt[0];
            String refFieldName = mappingAt[1];
            int mappingType = column.getMappingType();
            boolean bl = isDefaultRefField = mappingType == 4 && refFieldName.equals("CODE") || mappingType == 1 && refFieldName.equals("OBJECTCODE") || mappingType == 2 && refFieldName.equals("VAL") || mappingType == 3 && refFieldName.equals("ID");
            if (!column.getCheckval().booleanValue() && isDefaultRefField) continue;
            if (mappingType == 4) {
                this.setOrgMap(refTableName);
                this.specialImportColumns.add(column);
                continue;
            }
            if (mappingType == 1) {
                BaseDataDefineDO define = this.getDefine(refTableName);
                if (define.getSharetype() == 0) {
                    this.loadBaseData(refTableName, this.getShareUniqueCode(refTableName, null));
                }
                this.specialImportColumns.add(column);
                continue;
            }
            if (mappingType == 2) {
                this.loadEnumData(refTableName);
                this.specialImportColumns.add(column);
                continue;
            }
            if (mappingType != 3) continue;
            this.loadUser();
            this.specialImportColumns.add(column);
        }
        List<Map<String, Object>> showFields = BasedataExcelUtils.getBasedataShowFieldsByTableName(this.importTableName);
        for (Map<String, Object> showField : showFields) {
            if (showField.get("virtualFlag") != null && Boolean.valueOf(showField.get("virtualFlag").toString()).booleanValue()) continue;
            Boolean required = (Boolean)showField.get("required");
            required = required == null;
            String columnName = (String)showField.get("columnName");
            if (!required.booleanValue() || this.importColumnMap.containsKey(columnName)) continue;
            this.requiredReviewFieldList.add(columnName);
        }
    }

    private void initOrgCategory() {
        PageVO rs = this.orgCategoryClient.list(new OrgCategoryDO());
        if (rs != null && rs.getRows() != null) {
            for (OrgCategoryDO orgCategoryDO : rs.getRows()) {
                this.allOrgCategoryMap.put(orgCategoryDO.getName(), orgCategoryDO);
            }
        }
    }

    protected void initData(List<BaseDataDTO> dataList) {
        this.importDatas.clear();
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        HashSet<String> columns = new HashSet<String>();
        for (BaseDataExcleColumn column : this.importColumns) {
            if (column == null) continue;
            columns.add(column.getColumnName().toLowerCase());
        }
        for (int j = 0; j < dataList.size(); ++j) {
            BaseDataDTO newData = dataList.get(j);
            newData.setTableName(this.importTableName);
            newData.computeIfAbsent((Object)this.importState, key -> 0);
            for (String col : columns) {
                if (newData.containsKey((Object)col)) continue;
                newData.put(col, null);
            }
            this.importDatas.add(newData);
        }
    }

    protected void initData(Sheet sheet) {
        this.importDatas.clear();
        if (sheet == null) {
            return;
        }
        for (int j = this.firstLine - 1; j < sheet.getLastRowNum() + 1; ++j) {
            Row row = sheet.getRow(j);
            if (this.isBlankRow(row, this.importColumns.size())) continue;
            BaseDataDTO newData = new BaseDataDTO();
            newData.put(this.importState, (Object)0);
            newData.setTableName(this.importTableName);
            for (int k = 0; k < this.importColumns.size(); ++k) {
                BaseDataExcleColumn field = this.importColumns.get(k);
                if (field == null) continue;
                Cell cell = row.getCell(k);
                this.setFieldValue(field, cell, newData);
            }
            this.importDatas.add(newData);
        }
    }

    private void updateCache(BaseDataDTO param) {
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(param.getTenantName());
        bdsc.setBaseDataDTO(param);
        this.baseDataCacheService.pushSyncMsg(bdsc);
    }

    protected void handleSpecialRelationField(BaseDataDTO data) {
        HashMap<String, Object> oldValueMap = new HashMap<String, Object>();
        for (BaseDataExcleColumn column : this.specialImportColumns) {
            boolean isDefaultRefField;
            String columnName = column.getColumnName();
            Object value = data.get((Object)columnName.toLowerCase());
            oldValueMap.put(columnName.toLowerCase(), value);
            if (this.isEmpty(value)) continue;
            String[] mapping = column.getMapping().split("\\.");
            String refTableName = mapping[0];
            String refFieldName = mapping[1];
            Integer mappingType = column.getMappingType();
            boolean bl = isDefaultRefField = mappingType == 4 && refFieldName.equals("CODE") || mappingType == 1 && refFieldName.equals("OBJECTCODE") || mappingType == 2 && refFieldName.equals("VAL") || mappingType == 3 && refFieldName.equals("ID");
            if (!column.getCheckval().booleanValue() && isDefaultRefField) {
                if (!column.getMultiple().booleanValue()) continue;
                ArrayList mulValue = new ArrayList();
                Collections.addAll(mulValue, value.toString().split("\\,"));
                data.put(columnName.toLowerCase(), mulValue);
                continue;
            }
            if (mappingType == 1) {
                String shareUniqueCode = null;
                shareUniqueCode = !this.isEmpty(column.getDriveField()) ? this.getShareUniqueCode(refTableName, (BaseDataDO)data, column.getDriveField().toLowerCase()) : this.getShareUniqueCode(refTableName, (BaseDataDO)data);
                if (column.getMultiple() != null && column.getMultiple().booleanValue()) {
                    String[] refCodes = value.toString().split("\\,");
                    String[] refValues = new String[refCodes.length];
                    for (int i = 0; i < refCodes.length; ++i) {
                        BaseDataDO refData;
                        String refCode = refCodes[i];
                        if ("CODE".equals(refFieldName)) {
                            refCode = refCode.split("\\ ")[0];
                        }
                        refValues[i] = (refData = this.getBaseData(refTableName, shareUniqueCode, refFieldName, refCode)) == null ? refCode : (column.getCheckval() != false ? refData.getCode() : refData.getObjectcode());
                    }
                    data.put(columnName.toLowerCase(), (Object)refValues);
                    continue;
                }
                String specialValue = value.toString();
                if ("CODE".equals(refFieldName)) {
                    specialValue = specialValue.split("\\ ")[0];
                }
                BaseDataDO refData = this.getBaseData(refTableName, shareUniqueCode, refFieldName, specialValue);
                data.put(columnName.toLowerCase(), (Object)(refData == null ? specialValue : (column.getCheckval() != false ? refData.getCode() : refData.getObjectcode())));
                continue;
            }
            if (mappingType == 4) {
                if ("CODE".equals(refFieldName)) {
                    value = value.toString().split("\\ ")[0];
                }
                OrgDO orgDO = this.getOrg(refTableName, refFieldName, (String)value);
                data.put(columnName.toLowerCase(), orgDO == null ? value : orgDO.getCode());
                continue;
            }
            if (mappingType == 2) {
                if ("VAL".equals(refFieldName)) {
                    value = value.toString().split("\\ ")[0];
                }
                EnumDataDO enumDataDO = this.getEnumData(refTableName, refFieldName, (String)value);
                data.put(columnName.toLowerCase(), enumDataDO == null ? value : enumDataDO.getVal());
                continue;
            }
            if (mappingType != 3) continue;
            if ("USERNAME".equals(refFieldName)) {
                value = value.toString().split("\\ ")[0];
            }
            UserDO refUser = this.getUserData(refFieldName, value);
            data.put(columnName.toLowerCase(), refUser == null ? value : refUser.getId());
        }
        data.put("oldValueMap", oldValueMap);
    }

    protected Map<String, EnumDataDO> getEnumMap(String biztype) {
        if (!this.enumDataMap.containsKey(biztype)) {
            this.loadEnumData(biztype);
        }
        return this.enumDataMap.get(biztype);
    }

    protected void loadEnumData(String tableName) {
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setBiztype(tableName);
        List enumDatas = this.enumDataClient.list(enumDataDTO);
        HashMap<String, EnumDataDO> map = new HashMap<String, EnumDataDO>();
        if (enumDatas != null && !enumDatas.isEmpty()) {
            for (EnumDataDO data : enumDatas) {
                map.put(data.getVal(), data);
            }
        }
        this.enumDataMap.put(tableName, map);
    }

    protected EnumDataDO getEnumData(String biztype, String specialKey, String specialValue) {
        if (specialKey.equalsIgnoreCase("VAL")) {
            return this.getEnumMap(biztype).get(specialValue);
        }
        this.specialEnumDataQueryMap.computeIfAbsent(biztype, key -> new HashMap());
        Map<String, Map<String, EnumDataDO>> specialKeyMap = this.specialEnumDataQueryMap.get(biztype);
        if (!specialKeyMap.containsKey(specialKey)) {
            Map<String, EnumDataDO> enumMap = this.getEnumMap(biztype);
            HashMap<String, EnumDataDO> dataMap = new HashMap<String, EnumDataDO>();
            for (Map.Entry<String, EnumDataDO> entry : enumMap.entrySet()) {
                EnumDataDO enumData = entry.getValue();
                String value = null;
                if (specialKey.equalsIgnoreCase("TITLE")) {
                    value = enumData.getTitle();
                }
                if (value == null || dataMap.containsKey(value)) continue;
                dataMap.put(value.toString(), enumData);
            }
            specialKeyMap.put(specialKey, dataMap);
        }
        return specialKeyMap.get(specialKey).get(specialValue);
    }

    protected void loadUser() {
        UserDTO userDTO = new UserDTO();
        PageVO userRes = this.authUserClient.list(userDTO);
        if (userRes != null && userRes.getTotal() > 0) {
            for (UserDO user : userRes.getRows()) {
                this.userMap.put(user.getId(), user);
            }
        }
    }

    protected Map<String, UserDO> getUserMap() {
        if (this.userMap.isEmpty()) {
            this.loadUser();
        }
        return this.userMap;
    }

    private UserDO getUserData(String specialKey, Object specialValue) {
        if (specialKey.equalsIgnoreCase("ID")) {
            return this.getUserMap().get(specialValue);
        }
        if (!this.specialUserDataQueryMap.containsKey(specialKey)) {
            Map<String, UserDO> userMap = this.getUserMap();
            HashMap<String, UserDO> dataMap = new HashMap<String, UserDO>();
            for (Map.Entry<String, UserDO> entry : userMap.entrySet()) {
                UserDO user = entry.getValue();
                String value = null;
                if (specialKey.equalsIgnoreCase("USERNAME")) {
                    value = user.getUsername();
                } else if (specialKey.equalsIgnoreCase("EMAIL")) {
                    value = user.getEmail();
                } else if (specialKey.equalsIgnoreCase("TELEPHONE")) {
                    value = user.getTelephone();
                }
                if (value == null || dataMap.containsKey(value)) continue;
                dataMap.put(value, user);
            }
            this.specialUserDataQueryMap.put(specialKey, dataMap);
        }
        return this.specialUserDataQueryMap.get(specialKey).get(specialValue);
    }

    public Map<String, BaseDataDO> getBaseData(String tableName, String shareUniqueCode) {
        if (!this.baseDataMap.containsKey(tableName) || !this.baseDataMap.get(tableName).containsKey(shareUniqueCode)) {
            this.loadBaseData(tableName, shareUniqueCode);
        }
        return this.baseDataMap.get(tableName).get(shareUniqueCode);
    }

    public List<BaseDataDO> getBaseDataList(String tableName, String shareUniqueCode) {
        Map<String, BaseDataDO> dataMap = this.getBaseData(tableName, shareUniqueCode);
        List<BaseDataDO> dataList = this.baseDataListMap.get(tableName).get(shareUniqueCode);
        if (dataList.isEmpty()) {
            dataList.addAll(dataMap.values());
        }
        return dataList;
    }

    protected void setBaseData(BaseDataDTO data) {
        String shareUniqueCode;
        String tableName = data.getTableName();
        Map<String, BaseDataDO> dataMap = this.getBaseData(tableName, shareUniqueCode = this.getShareUniqueCode(tableName, (BaseDataDO)data));
        BaseDataDO oldData = dataMap.get(data.getCode());
        if (oldData != null) {
            data.setObjectcode(oldData.getObjectcode());
        }
        dataMap.put(data.getCode(), (BaseDataDO)data);
        this.baseDataListMap.get(tableName).get(shareUniqueCode).clear();
    }

    protected void loadBaseData(String tableName, String shareUniqueCode) {
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableName);
        List<String> shareFields = this.getSharefields(tableName);
        if (shareFields != null && shareUniqueCode != null) {
            String[] shareValues = shareUniqueCode.split(";");
            for (int i = 0; i < shareFields.size(); ++i) {
                queryParam.put(shareFields.get(i), (Object)shareValues[i]);
            }
        }
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        queryParam.setAuthType(BaseDataOption.AuthType.NONE);
        queryParam.setOrderBy(new ArrayList());
        PageVO<BaseDataDO> queryRes = this.baseDataClient.list(queryParam);
        HashMap<String, BaseDataDO> dataMap = new HashMap<String, BaseDataDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (BaseDataDO data : queryRes.getRows()) {
                dataMap.put(data.getCode(), data);
            }
        }
        this.baseDataMap.computeIfAbsent(tableName, key -> new HashMap());
        Map<String, Map<String, BaseDataDO>> shareDataMap = this.baseDataMap.get(tableName);
        shareDataMap.put(shareUniqueCode, dataMap);
        this.baseDataListMap.computeIfAbsent(tableName, key -> new HashMap());
        Map<String, List<BaseDataDO>> shareDataListMap = this.baseDataListMap.get(tableName);
        List<BaseDataDO> dataList = shareDataListMap.get(shareUniqueCode);
        if (dataList == null) {
            dataList = new ArrayList<BaseDataDO>();
            shareDataListMap.put(shareUniqueCode, dataList);
        } else {
            dataList.clear();
        }
        dataList.addAll(dataMap.values());
    }

    protected BaseDataDO getBaseData(String tableName, String shareUniquecode, String specialKey, String specialValue) {
        Map<String, Map<String, BaseDataDO>> specialKeyMap;
        Map<String, Map<String, Map<String, BaseDataDO>>> shareMap;
        if (!this.specialBaseDataQueryMap.containsKey(tableName)) {
            this.specialBaseDataQueryMap.put(tableName, new HashMap());
        }
        if (!(shareMap = this.specialBaseDataQueryMap.get(tableName)).containsKey(shareUniquecode)) {
            shareMap.put(shareUniquecode, new HashMap());
        }
        if (!(specialKeyMap = shareMap.get(shareUniquecode)).containsKey(specialKey)) {
            List<BaseDataDO> basedataList = this.getBaseDataList(tableName, shareUniquecode);
            if (basedataList == null) {
                basedataList = new ArrayList<BaseDataDO>();
            }
            HashMap<String, BaseDataDO> dataMap = new HashMap<String, BaseDataDO>();
            for (BaseDataDO data : basedataList) {
                BaseDataDO oldData;
                Object value = data.get((Object)specialKey.toLowerCase());
                if (value != null && !dataMap.containsKey(value)) {
                    dataMap.put(value.toString(), data);
                    continue;
                }
                if (value == null || !dataMap.containsKey(value) || data.getRecoveryflag() == 1 || (oldData = (BaseDataDO)dataMap.get(value.toString())).getRecoveryflag() != 1 && (oldData.getStopflag() != 1 || data.getStopflag() == 1)) continue;
                dataMap.put(value.toString(), data);
            }
            specialKeyMap.put(specialKey, dataMap);
        }
        return specialKeyMap.get(specialKey).get(specialValue);
    }

    public BaseDataDefineDO getDefine(String tableName) {
        if (!this.defineMap.containsKey(tableName)) {
            this.loadDefine(tableName);
        }
        return this.defineMap.get(tableName);
    }

    protected void loadDefine(String tableName) {
        BaseDataDefineDTO refDefineParam = new BaseDataDefineDTO();
        refDefineParam.setName(tableName);
        refDefineParam.setDeepClone(Boolean.valueOf(false));
        PageVO<BaseDataDefineDO> res = this.baseDataDefineClient.list(refDefineParam);
        if (res == null || res.getRows() == null || res.getRows().isEmpty()) {
            throw new RuntimeException("\u83b7\u53d6" + tableName + "\u8868\u5b9a\u4e49\u5931\u8d25");
        }
        this.defineMap.put(tableName, (BaseDataDefineDO)res.getRows().get(0));
    }

    public DataModelDO getModel(String tableName) {
        if (!this.modelMap.containsKey(tableName)) {
            this.loadModel(tableName);
        }
        return this.modelMap.get(tableName);
    }

    protected void loadModel(String tableName) {
        DataModelDO refDefineParam = BasedataExcelUtils.getDataModalDefine(tableName);
        this.modelMap.put(tableName, refDefineParam);
    }

    public void setFieldValue(BaseDataExcleColumn field, Cell cell, BaseDataDTO newData) {
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
            if (key.equalsIgnoreCase("code")) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.empty", field.getColumnTitle() + "(" + field.getColumnName() + ")"), 1);
            }
            newData.put(key, null);
            return;
        }
        boolean error = false;
        CellType cellType = cell.getCellType();
        Object value = null;
        String memo = null;
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
                    value = value.equals("\u662f") || value.equals("yes") || value.equals("true") || value.equals("1") ? 1 : 0;
                    break;
                }
                error = true;
                break;
            }
            case NUMERIC: {
                if ("ordinal".equalsIgnoreCase(key) && cellType.equals((Object)CellType.STRING)) {
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
            memo = BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.column.value.not.match", "[" + field.getColumnTitle() + "]");
            this.setImportMemo(newData, memo, 1);
        } else {
            newData.put(key, value);
        }
    }

    public void checkFieldValueValid(BaseDataDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        this.handleSpecialRelationField(newData);
        for (BaseDataExcleColumn column : this.importColumns) {
            String[] values;
            BigDecimal currValue;
            Object value = newData.get((Object)column.getColumnName().toLowerCase());
            if (this.isEmpty(value)) continue;
            if (column.getColumnType() == DataModelType.ColumnType.NVARCHAR) {
                if (value.toString().length() <= column.getLengths()[0]) continue;
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.length.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", value.toString().length(), column.getLengths()[0]), 1);
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.NUMERIC) {
                currValue = new BigDecimal(value.toString()).stripTrailingZeros();
                values = currValue.toPlainString().split("\\.");
                int decimalCount = 0;
                if (values.length > 1) {
                    if (values[1].length() > column.getLengths()[1]) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.precision.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", values[1].length(), column.getLengths()[1]), 1);
                        continue;
                    }
                    decimalCount = values[1].length();
                }
                if (values.length <= 0 || values[0].length() + decimalCount <= column.getLengths()[0]) continue;
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.length.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", values[0].length() + decimalCount, column.getLengths()[0]), 1);
                continue;
            }
            if (column.getColumnType() == DataModelType.ColumnType.INTEGER) {
                currValue = new BigDecimal(value.toString());
                values = currValue.toPlainString().split("\\.");
                int maxLen = Math.min(column.getLengths()[0], 10);
                if (values[0].length() > maxLen) {
                    this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.length.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", values[0].length(), maxLen), 1);
                    continue;
                }
                BigDecimal maxValue = new BigDecimal(Integer.MAX_VALUE);
                if (currValue.compareTo(maxValue) <= 0) continue;
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.value.exceed", column.getColumnTitle() + "(" + column.getColumnName() + ")", currValue.toPlainString(), maxValue.toPlainString()), 1);
                continue;
            }
            if (column.getColumnType() != DataModelType.ColumnType.UUID || !this.isEmpty(column.getMapping()) && !"ID".equals(column.getMapping().split("\\.")[1])) continue;
            try {
                UUID.fromString(value.toString());
            }
            catch (Exception e) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.not.match.uuid", column.getColumnTitle() + "(" + column.getColumnName() + ")", value.toString()), 1);
            }
        }
    }

    public void checkShareLegal(BaseDataDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        String tableName = newData.getTableName();
        List<String> sharefields = this.getSharefields(tableName);
        if (sharefields == null || sharefields.isEmpty()) {
            newData.setUnitcode("-");
            return;
        }
        Map<String, OrgDO> allOrg = this.getOrgMap("MD_ORG");
        Map<String, OrgDO> authOrg = this.authOrgMap.get("MD_ORG");
        String unitcode = (String)newData.get((Object)"unitcode");
        if (this.isEmpty(unitcode)) {
            if (!this.isEmpty(((Map)newData.get((Object)"oldValueMap")).get("unitcode"))) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.admin.org.not.exist", new Object[0]), 2);
            } else {
                newData.setUnitcode(this.importUnitcode);
            }
        } else if (!unitcode.equals("-")) {
            if (!allOrg.keySet().contains(unitcode)) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.admin.org.not.exist", new Object[0]), 2);
            } else if (!authOrg.keySet().contains(unitcode)) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.admin.org.no.access.right", new Object[0]), 2);
            } else if (authOrg.get(unitcode).getRecoveryflag() == 1) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.admin.org.has.deleted", new Object[0]), 2);
            } else if (authOrg.get(unitcode).getStopflag() == 1) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.admin.org.inactive", new Object[0]), 2);
            }
        }
    }

    protected String getShareUniqueCode(String targetTable, BaseDataDO baseDataDO) {
        return this.getShareUniqueCode(targetTable, baseDataDO, null);
    }

    protected String getShareUniqueCode(String targetTable, BaseDataDO baseDataDO, String driveField) {
        if (this.isEmpty(targetTable) || baseDataDO == null) {
            return null;
        }
        List<String> sharefields = this.getSharefields(targetTable);
        if (sharefields == null || sharefields.isEmpty()) {
            return "-";
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(targetTable);
        param.setUnitcode(this.importUnitcode);
        param.putAll((Map)baseDataDO);
        this.baseDataParamService.loadExtendParam(param, BaseDataAction.Query);
        StringBuilder shareUniqueCode = new StringBuilder();
        String value = null;
        for (String shareField : sharefields) {
            if (baseDataDO != null) {
                value = shareField.equals("unitcode") && !this.isEmpty(driveField) ? (String)param.get((Object)driveField.toLowerCase()) : (String)param.get((Object)shareField);
            }
            if (this.isEmpty(value)) {
                value = shareField.equals("unitcode") ? this.importUnitcode : "";
            } else if (shareField.equals("unitcode") && value.equals("-")) {
                value = this.importUnitcode;
            }
            shareUniqueCode.append(value.split("\\ ")[0]).append(";");
        }
        return shareUniqueCode.toString();
    }

    protected boolean isAccess(BaseDataDTO data, String[] checkNodes, boolean isParallel) {
        boolean isAccess = !isParallel;
        for (String checkNode : checkNodes) {
            if (this.isEmpty(checkNode)) continue;
            Object value = data.get((Object)checkNode);
            if (value != null && FormatValidationUtil.isInteger(value.toString()) && Integer.valueOf(value.toString()) == 1) {
                if (!isParallel) continue;
                isAccess = !isAccess;
                break;
            }
            if (isParallel) continue;
            isAccess = !isAccess;
            break;
        }
        return isAccess;
    }

    protected boolean isUnique(BaseDataDTO data, String checkField) {
        List list;
        Map<String, BaseDataDO> map = this.getBaseData(data.getTableName(), this.getShareUniqueCode(data.getTableName(), (BaseDataDO)data));
        if (!(this.isEmpty(data.get((Object)checkField)) || map == null || map.isEmpty() || (list = map.values().stream().filter(item -> this.isValueSame((BaseDataDO)data, (BaseDataDO)item, checkField)).collect(Collectors.toList())) == null || list.isEmpty())) {
            if (data.getId() == null) {
                return false;
            }
            if (list.stream().filter(item -> !item.getId().equals(data.getId())).count() > 0L) {
                return false;
            }
        }
        return true;
    }

    private boolean isValueSame(BaseDataDO data1, BaseDataDO data2, String key) {
        Object value1 = data1.get((Object)key);
        Object value2 = data2.get((Object)key);
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.toString().equals(value2.toString());
    }

    protected List<String> getSharefields(String tableName) {
        List<String> res = this.shareFieldsMap.get(tableName);
        if (this.shareFieldsMap.containsKey(tableName)) {
            return res;
        }
        res = new ArrayList<String>();
        BaseDataDefineDO define = this.getDefine(tableName);
        String sharefieldString = define.getSharefieldname();
        String unitcode = "unitcode";
        if (define.getSharetype() != 0) {
            if (this.isEmpty(sharefieldString)) {
                res.add(unitcode);
            } else {
                for (String shareField : sharefieldString.split("\\,")) {
                    res.add(shareField.toLowerCase());
                }
                if (!res.contains(unitcode)) {
                    res.add(unitcode);
                }
            }
        }
        this.shareFieldsMap.put(tableName, res);
        return res;
    }

    protected Map<String, OrgDO> getOrgMap(String categoryname) {
        if (!this.allOrgMap.containsKey(categoryname)) {
            this.setOrgMap(categoryname);
        }
        return this.allOrgMap.get(categoryname);
    }

    protected OrgDO getOrg(String categoryname, String code) {
        if (!this.allOrgMap.containsKey(categoryname)) {
            this.setOrgMap(categoryname);
        }
        return this.allOrgMap.get(categoryname).get(code);
    }

    protected OrgDO getOrg(String categoryname, String specialKey, String specialValue) {
        this.specialOrgQueryMap.computeIfAbsent(categoryname, key -> new HashMap());
        Map<String, Map<String, OrgDO>> specialKeyMap = this.specialOrgQueryMap.get(categoryname);
        if (!specialKeyMap.containsKey(specialKey)) {
            Map<String, OrgDO> orgMap = this.getOrgMap(categoryname);
            HashMap<String, OrgDO> dataMap = new HashMap<String, OrgDO>();
            for (Map.Entry<String, OrgDO> entry : orgMap.entrySet()) {
                OrgDO orgDO = entry.getValue();
                Object value = orgDO.get((Object)specialKey.toLowerCase());
                if (value == null || dataMap.containsKey(value)) continue;
                dataMap.put(value.toString().trim(), orgDO);
            }
            specialKeyMap.put(specialKey, dataMap);
        }
        return specialKeyMap.get(specialKey).get(specialValue);
    }

    protected OrgDO getAuthOrg(String categoryname, String code) {
        if (!this.authOrgMap.containsKey(categoryname)) {
            this.setOrgMap(categoryname);
        }
        return this.authOrgMap.get(categoryname).get(code);
    }

    protected void setOrgMap(String categoryname) {
        if (this.allOrgMap.get(categoryname) != null) {
            return;
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setCategoryname(categoryname);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setAuthType(OrgDataOption.AuthType.ACCESS);
        queryParam.setOnlyMarkAuth(Boolean.valueOf(true));
        queryParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        queryParam.setOrderBy(new ArrayList());
        PageVO temp = this.orgDataClient.list(queryParam);
        HashMap<String, OrgDO> allOrg = new HashMap<String, OrgDO>();
        HashMap<String, OrgDO> authOrg = new HashMap<String, OrgDO>();
        this.allOrgMap.put(categoryname, allOrg);
        this.authOrgMap.put(categoryname, authOrg);
        if (temp == null || temp.getTotal() == 0) {
            return;
        }
        for (OrgDO org : temp.getRows()) {
            allOrg.put(org.getCode(), org);
            if (org.get((Object)"authMark") != null && !((Boolean)org.get((Object)"authMark")).booleanValue()) continue;
            authOrg.put(org.getCode(), org);
        }
    }

    protected void checkRepeat(BaseDataDTO newData) {
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        String shareUniqueCode = this.getShareUniqueCode(newData.getTableName(), (BaseDataDO)newData);
        String uniqueSign = shareUniqueCode + ";" + newData.getCode();
        if (this.existDataUniqueCodeSet.contains(uniqueSign)) {
            this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.repeat", this.importColumnMap.get("CODE").getColumnTitle() + "(CODE)"), 3);
        } else {
            this.existDataUniqueCodeSet.add(uniqueSign);
        }
    }

    protected void checkRequird(BaseDataDTO newData) {
        String memo;
        String groupFieldName;
        BaseDataExcleColumn groupField;
        if (!this.isCurrentLegal(newData)) {
            return;
        }
        BaseDataDefineDO define = this.defineMap.get(newData.getTableName());
        int struct = define.getStructtype();
        if (struct == 1 && (groupField = this.importColumnMap.get(groupFieldName = define.getGroupfieldname())) == null) {
            memo = BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.empty", ((DataModelColumn)this.getModel(this.importTableName).getColumns().stream().filter(c -> c.getColumnName().equalsIgnoreCase(groupFieldName)).collect(Collectors.toList()).get(0)).getColumnTitle() + "(" + groupFieldName + ")");
            this.setImportMemo(newData, memo, -2);
        }
        String fieldName = null;
        Map oldValueMap = null;
        memo = null;
        for (BaseDataExcleColumn requiedField : this.importRequiedFieldList) {
            fieldName = requiedField.getColumnName().toLowerCase();
            oldValueMap = (Map)newData.get((Object)"oldValueMap");
            if (!this.isEmpty(newData.get((Object)fieldName)) || oldValueMap != null && oldValueMap.get(fieldName) != null) continue;
            if ("parentcode".equals(fieldName)) {
                newData.setParentcode("-");
                continue;
            }
            memo = BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.empty", requiedField.getColumnTitle() + "(" + requiedField.getColumnName() + ")");
            this.setImportMemo(newData, memo, -2);
        }
    }

    protected void checkStructLegal(BaseDataDTO newData) {
        if (!this.isCurrentLegal(newData) || newData.containsKey((Object)"checkStructDisabled")) {
            return;
        }
        BaseDataDefineDO define = this.getDefine(this.importTableName);
        int struct = define.getStructtype();
        if (struct == 0) {
            return;
        }
        if (struct == 1) {
            String groupFieldName = define.getGroupfieldname();
            BaseDataExcleColumn field = this.importColumnMap.get(groupFieldName);
            if (field != null) {
                String baseInfo = field.getColumnTitle() + "(" + field.getColumnName() + ")";
                Object groupvalue = newData.get((Object)groupFieldName.toLowerCase());
                if (!this.isEmpty(groupvalue)) {
                    String mapping = field.getMapping();
                    if (this.isEmpty(mapping)) {
                        return;
                    }
                    String refTable = mapping.split("\\.")[0];
                    String tipValsInfo = refTable + "[" + (String)groupvalue + "]";
                    BaseDataDO groupData = this.getBaseData(refTable, this.getShareUniqueCode(refTable, (BaseDataDO)newData)).get(groupvalue);
                    if (groupData == null) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.not.exist", baseInfo, tipValsInfo), 4);
                    } else if (groupData.getRecoveryflag() != null && groupData.getRecoveryflag() == 1) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.has.deleted", baseInfo, tipValsInfo), 4);
                    } else if (groupData.getStopflag() == 1) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.inactive", baseInfo, tipValsInfo), 4);
                    } else {
                        newData.put(groupFieldName.toLowerCase(), (Object)groupData.getObjectcode());
                    }
                } else {
                    this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.empty", baseInfo), 4);
                }
            }
            return;
        }
        if (struct == 2) {
            BaseDataExcleColumn field = this.importColumnMap.get("PARENTCODE");
            if (field != null) {
                String baseInfo = field.getColumnTitle() + "(" + field.getColumnName() + ")";
                String parentcode = newData.getParentcode();
                if (!this.isEmpty(parentcode) && !parentcode.equals("-")) {
                    List<Object> basedataList;
                    String refFieldName = field.getMapping().split("\\.")[1];
                    BaseDataDO parentNode = null;
                    if ("CODE".equals(refFieldName)) {
                        parentNode = this.getBaseData(this.importTableName, this.getShareUniqueCode(this.importTableName, (BaseDataDO)newData)).get(parentcode.split(" ")[0]);
                    } else if ("NAME".equals(refFieldName) && (basedataList = this.getBaseDataList(this.importTableName, this.getShareUniqueCode(this.importTableName, (BaseDataDO)newData))) != null && !basedataList.isEmpty()) {
                        parentNode = !(basedataList = basedataList.stream().filter(element -> parentcode.equals(element.getName())).collect(Collectors.toList())).isEmpty() ? (BaseDataDO)basedataList.get(0) : null;
                    }
                    String tipValsInfo = "[" + parentcode + "]";
                    if (parentNode == null) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.not.exist", baseInfo, tipValsInfo), 4);
                    } else if (parentNode.getRecoveryflag() != null && parentNode.getRecoveryflag() == 1) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.has.deleted", baseInfo, tipValsInfo), 4);
                    } else if (parentNode.getStopflag() == 1) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.inactive", baseInfo, tipValsInfo), 4);
                    } else if (newData.getCode().equals(parentNode.getCode())) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.cannot.own", baseInfo), 4);
                    } else {
                        newData.setParentcode(parentNode.getCode());
                        boolean checkflag = false;
                        for (String pcode : parentNode.getParents().split("\\/")) {
                            if (!newData.getCode().equals(pcode)) continue;
                            this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.parents.endless.chain", new Object[0]), 4);
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
            } else {
                newData.setParentcode("-");
                newData.setParents(newData.getCode());
            }
        }
        if (struct == 3) {
            newData.put("checkParentDisable", (Object)true);
            this.baseDataModifyService.setBasedataLevelCode((BaseDataDO)newData);
        }
    }

    protected void checkRelationLegal(BaseDataDTO newData) {
        String colName = null;
        String mapping = null;
        String mappingTableName = null;
        Integer mappingType = null;
        for (BaseDataExcleColumn column : this.importRelatedFieldList) {
            String tipValsInfo;
            Object valueObj;
            if (column.getCheckval() == null || !column.getCheckval().booleanValue() || this.isEmpty(mapping = column.getMapping()) || 1 != (mappingType = column.getMappingType()) && 4 != mappingType && 3 != mappingType && 2 != mappingType || (valueObj = newData.get((Object)(colName = column.getColumnName().toLowerCase()))) == null || colName.equalsIgnoreCase(this.defineMap.get(this.importTableName).getGroupfieldname()) || colName.equalsIgnoreCase("unitcode") || this.isEmpty(mappingTableName = mapping.split("\\.")[0])) continue;
            String baseInfo = column.getColumnTitle() + "(" + colName + ")";
            if (mappingType == 4 || mappingTableName.equals("MD_ORG") || mappingTableName.startsWith("MD_ORG_")) {
                tipValsInfo = mappingTableName + "[" + (String)valueObj + "]";
                if (!this.getOrgMap(mappingTableName).containsKey((String)valueObj)) {
                    this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.not.exist", baseInfo, tipValsInfo), 5);
                    continue;
                }
                if (!this.authOrgMap.get(mappingTableName).containsKey((String)valueObj)) {
                    this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.no.access.right", baseInfo, tipValsInfo), 5);
                    continue;
                }
                if (this.authOrgMap.get(mappingTableName).get((String)valueObj).getStopflag() != 1) continue;
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.inactive", baseInfo, tipValsInfo), 5);
                continue;
            }
            if (mappingType == 1) {
                ArrayList<String> cbdList = null;
                ArrayList<String> cbdObjList = null;
                if (valueObj instanceof List) {
                    cbdList = (ArrayList<String>)valueObj;
                    cbdObjList = new ArrayList<String>();
                } else {
                    cbdList = new ArrayList<String>();
                    cbdList.add((String)valueObj);
                }
                Map<String, BaseDataDO> defineDataMap = this.getBaseData(mappingTableName, this.getShareUniqueCode(mappingTableName, (BaseDataDO)newData, column.getDriveField()));
                for (String value : cbdList) {
                    tipValsInfo = mappingTableName + "[" + value + "]";
                    BaseDataDO dataDO = defineDataMap.get(value);
                    if (dataDO == null) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.not.exist", baseInfo, tipValsInfo), 5);
                        continue;
                    }
                    if (dataDO.getRecoveryflag() != null && dataDO.getRecoveryflag() == 1) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.has.deleted", baseInfo, tipValsInfo), 5);
                        continue;
                    }
                    if (dataDO.getStopflag() != null && dataDO.getStopflag() == 1) {
                        this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.inactive", baseInfo, tipValsInfo), 5);
                        continue;
                    }
                    if (valueObj instanceof List) {
                        cbdObjList.add(dataDO.getObjectcode());
                        continue;
                    }
                    newData.put(colName, (Object)dataDO.getObjectcode());
                }
                if (!this.isCurrentLegal(newData) || !(valueObj instanceof List)) continue;
                newData.put(colName, cbdObjList);
                continue;
            }
            if (mappingType == 2) {
                tipValsInfo = mappingTableName + "[" + (String)valueObj + "]";
                EnumDataDO enumDO = this.enumDataMap.get(mappingTableName).get((String)valueObj);
                if (enumDO == null) {
                    this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.not.exist", baseInfo, tipValsInfo), 5);
                    continue;
                }
                if (enumDO.getStatus() != null && enumDO.getStatus() == 1) {
                    this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.inactive", baseInfo, tipValsInfo), 5);
                    continue;
                }
                newData.put(colName, (Object)enumDO.getVal());
                continue;
            }
            if (mappingType != 3) continue;
            tipValsInfo = "USER[" + (String)valueObj + "]";
            UserDO refUser = this.getUserMap().get((String)valueObj);
            if (refUser == null) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.not.exist", baseInfo, tipValsInfo), 5);
                continue;
            }
            if (refUser.getStopflag() != null && refUser.getStopflag() == 1) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.related.data.inactive", baseInfo, tipValsInfo), 5);
                continue;
            }
            newData.put(colName, (Object)refUser.getId());
        }
    }

    protected void checkExist(BaseDataDTO newData) {
        Map<String, BaseDataDO> existData = this.getBaseData(this.importTableName, this.getShareUniqueCode(this.importTableName, (BaseDataDO)newData));
        if (existData.keySet().contains(newData.getCode())) {
            BaseDataDO old = existData.get(newData.getCode());
            BaseDataDefineDO baseDataDefineDO = this.defineMap.get(this.importTableName);
            if (BaseDataConsts.BASEDATA_SHARETYPE_ISOLATIONANDDOWN.equals(baseDataDefineDO.getSharetype()) && !old.getUnitcode().equals(this.importUnitcode)) {
                this.setImportMemo(newData, BaseDataCoreI18nUtil.getMessage("basedata.error.template.import.prohibit.parent.unitcode", new Object[0]), 6);
                return;
            }
            newData.setId(old.getId());
            newData.setRecoveryflag(old.getRecoveryflag());
            newData.setStopflag(old.getStopflag());
            newData.setVer(old.getVer());
            this.handleRquiredReviewFields(newData, old);
            BaseDataExcleColumn field = this.importColumnMap.get("PARENTCODE");
            if (field == null) {
                newData.setParentcode(old.getParentcode());
                newData.setParents(old.getParents());
            }
        } else {
            newData.setRecoveryflag(Integer.valueOf(0));
            if (newData.getStopflag() == null) {
                newData.setStopflag(Integer.valueOf(0));
            }
        }
    }

    protected void executeSave(BaseDataDTO data) {
        block11: {
            if (!this.isCurrentLegal(data)) {
                return;
            }
            this.handleDateField(data);
            R res = null;
            data.setRecoveryflag(Integer.valueOf(0));
            data.setParents(data.getParentcode() == null ? data.getCode() : data.getParents());
            if (data.getId() == null) {
                try {
                    res = this.baseDataParamService.checkFieldRequired(data, this.baseDataParamService.getShowFieldList((BaseDataDO)data), null);
                    if (res.getCode() != 0) {
                        this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.add", new Object[0]) + ":" + res.getMsg(), -1);
                        return;
                    }
                    res = this.baseDataClient.add(data);
                    if (res.getCode() == 0) {
                        this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.success.bddata.add", new Object[0]), 0);
                        this.setBaseData(data);
                        break block11;
                    }
                    this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.add", new Object[0]) + ":" + res.getMsg(), -1);
                }
                catch (Exception e) {
                    this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.add", new Object[0]), -1);
                    logger.error("\u5bfc\u5165\u65b0\u5efa\u5931\u8d25", e);
                }
            } else {
                try {
                    res = this.baseDataClient.update(data);
                    if (res.getCode() == 0) {
                        this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.success.bddata.update", new Object[0]), 0);
                        this.setBaseData(data);
                    } else {
                        this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.update", new Object[0]) + ":" + res.get((Object)"msg").toString(), -1);
                    }
                }
                catch (Exception e) {
                    this.setImportMemo(data, BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.update", new Object[0]), -1);
                    logger.error("\u5bfc\u5165\u66f4\u65b0\u5931\u8d25", e);
                }
            }
        }
    }

    private void handleDateField(BaseDataDTO basedata) {
        for (BaseDataExcleColumn column : this.importColumns) {
            Object value;
            if (column.getColumnType() != DataModelType.ColumnType.DATE && column.getColumnType() != DataModelType.ColumnType.TIMESTAMP || this.isEmpty(value = basedata.get((Object)column.getColumnName().toLowerCase())) || !(value instanceof Long)) continue;
            basedata.put(column.getColumnName().toLowerCase(), (Object)new Date((Long)value));
        }
    }

    protected void handleRquiredReviewFields(BaseDataDTO data, BaseDataDO old) {
        for (String field : this.requiredReviewFieldList) {
            data.put(field.toLowerCase(), old.get((Object)field.toLowerCase()));
        }
    }

    protected boolean isCurrentLegal(BaseDataDTO newData) {
        newData.computeIfAbsent((Object)this.importState, key -> 0);
        Object state = newData.get((Object)this.importState);
        return (Integer)state == 0;
    }

    protected void setImportMemo(BaseDataDTO newData, String newMemo, int status) {
        newData.put(this.importState, (Object)status);
        Object memo = newData.get((Object)this.importMemo);
        if (memo == null) {
            memo = "";
        }
        if (!this.isEmpty(memo.toString())) {
            memo = memo + ";";
        }
        memo = memo.toString() + newMemo;
        newData.put(this.importMemo, memo);
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

    protected Object getValueOfCell(Cell cell) {
        if (cell == null || cell.toString().trim().equals("")) {
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
        cellValue = this.isEmpty(cellValue) || cellValue.toString().trim().equals("") ? "" : cellValue;
        return cellValue;
    }

    public List<BaseDataDTO> getImportDatas() {
        for (BaseDataDTO curData : this.importDatas) {
            curData.remove((Object)"baseDataDefineDO");
            curData.remove((Object)"defineStr");
        }
        return this.importDatas;
    }

    public void setImportDatas(List<BaseDataDTO> importDatas) {
        this.importDatas = importDatas;
    }

    public List<BaseDataExcleColumn> getImportColumns() {
        return this.importColumns;
    }

    public void setImportColumns(List<BaseDataExcleColumn> importColumns) {
        this.importColumns = importColumns;
    }

    public void setProcessInfo(BaseDataImportProcess processInfo) {
        this.processInfo = processInfo;
    }

    private boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }

    public List<BaseDataDTO> getImportResult() {
        HashSet<String> rsKeys = new HashSet<String>();
        rsKeys.add(this.importState);
        rsKeys.add(this.importMemo);
        for (BaseDataExcleColumn col : this.importColumns) {
            rsKeys.add(col.getColumnName().toLowerCase());
        }
        for (BaseDataDTO curData : this.importDatas) {
            Iterator it = curData.keySet().iterator();
            while (it.hasNext()) {
                if (rsKeys.contains(it.next())) continue;
                it.remove();
            }
        }
        BaseDataDO basedataParam = new BaseDataDO();
        basedataParam.setTableName(this.importTableName);
        this.baseDataParamService.decodeSecurityFields(this.importDatas, basedataParam);
        return this.importDatas;
    }
}

