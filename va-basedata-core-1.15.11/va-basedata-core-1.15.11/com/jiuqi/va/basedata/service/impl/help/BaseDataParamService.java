/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.DataTypeUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.BaseDataAction
 *  com.jiuqi.va.extend.BaseDataParamInterceptor
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.VaEncryptClient
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataContextService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.basedata.storage.BaseDataDefineStorage;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.DataTypeUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.BaseDataAction;
import com.jiuqi.va.extend.BaseDataParamInterceptor;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.VaEncryptClient;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataParamService")
public class BaseDataParamService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataParamService.class);
    public static final String LOAD_EXTEND_PARAM_OVER = "LOAD_EXTEND_PARAM_OVER";
    public static final String UNCHECK_FIELD = "unCheck";
    public static final String CHECK_VALUE_VALID_OVER = "CHECK_VALUE_VALID_OVER";
    public static final String CHECK_FIELD_REQUIRED_OVER = "CHECK_FIELD_REQUIRED_OVER";
    public static final String SHARE_FORCE_CHECK = "shareForceCheck";
    public static final String SHARE_FIELDS = "sharefields";
    public static final String SHARE_UNITCODES = "shareUnitcodes";
    public static final String DEFINE_DO = "baseDataDefineDO";
    public static final String DEFINE_JSONSTR = "defineStr";
    public static final String DEFINE_STRUCTTYPE = "defineStructtype";
    public static final String DEFINE_SHARETYPE = "defineSharetype";
    public static final String DEFINE_VERSION_FLAG = "defineVersionflag";
    public static final String VERSION_DATA = "versionData";
    public static final String HAS_MULTI_VALUES = "hasMultiValues";
    public static final String BASEDATA_DATAMODEL = "hasDataModel";
    public static final String DATA_AUTH_SET = "dataAuthSet";
    public static final String REF_SHOW_CODE = "RefShowCode";
    public static final String VERSION_DATE_LIST = "VERSION_DATE_LIST";
    public static final String COMMONLY_FILTER = "commonlyFilter";
    public static final String SHOW_FIELD_LIST = "showFieldList";
    public static final String SENSITIVE_FIELDS = "sensitiveFields";
    public static final String SECURITY_FIELDS = "securityFields";
    @Autowired
    private BaseDataVersionService baseDataVersionService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private CommonDao commonDao;
    private static boolean paramInterceptorsInted = false;
    private static Collection<BaseDataParamInterceptor> paramInterceptors;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private VaI18nClient i18nResourceClient;
    @Autowired
    private VaEncryptClient encryptClient;
    @Autowired
    private BaseDataContextService baseDataContextService;
    private BaseDataQueryService baseDataQueryService;

    private BaseDataQueryService getBaseDataQueryService() {
        if (this.baseDataQueryService == null) {
            this.baseDataQueryService = (BaseDataQueryService)ApplicationContextRegister.getBean(BaseDataQueryService.class);
        }
        return this.baseDataQueryService;
    }

    public void initParam(BaseDataDTO param) {
        UserLoginDTO userLoginDTO;
        List codes;
        this.initDefineInfo((BaseDataDO)param);
        if (!StringUtils.hasText(param.getShowType())) {
            BaseDataDefineDO define = (BaseDataDefineDO)param.get((Object)DEFINE_DO);
            String showTypeStr = define.getShowtype();
            if (!StringUtils.hasText(showTypeStr)) {
                showTypeStr = "NAME";
            }
            param.setShowType(showTypeStr);
        }
        if (param.getId() != null && param.getQueryChildrenType() == null) {
            return;
        }
        String code = param.getCode();
        if (StringUtils.hasText(code) && code.contains("||")) {
            param.setObjectcode(code);
            param.setCode(code.split("\\|\\|")[0]);
        }
        if ((codes = param.getBaseDataCodes()) != null && !codes.isEmpty() && ((String)codes.get(0)).contains("||")) {
            param.setBaseDataObjectcodes(codes);
            param.remove((Object)"baseDataCodes");
        }
        if (!param.isIgnoreShareFields()) {
            this.initShareFields(param);
        }
        this.initGroupParam(param);
        Date versionDate = param.getVersionDate();
        if (versionDate == null && (userLoginDTO = ShiroUtil.getUser()) != null) {
            versionDate = userLoginDTO.getLoginDate();
        }
        if (versionDate == null) {
            versionDate = new Date();
        }
        param.setVersionDate(versionDate);
    }

    private void initGroupParam(BaseDataDTO param) {
        String groupFieldName = param.getGroupFieldName();
        if (groupFieldName == null) {
            return;
        }
        ArrayList<String> groupNames = param.getGroupNames();
        if (groupNames != null && !groupNames.isEmpty()) {
            return;
        }
        if (param.containsKey((Object)"showSubGroup")) {
            if (groupNames == null) {
                groupNames = new ArrayList<String>();
            }
            String groupObjectCode = (String)param.get((Object)"groupObjectCode");
            boolean showSubGroup = (Boolean)param.get((Object)"showSubGroup");
            if (showSubGroup) {
                PageVO<BaseDataDO> dataPage;
                BaseDataDTO groupParam = new BaseDataDTO((Map)param.get((Object)"groupParam"));
                groupParam.setTenantName(param.getTenantName());
                groupParam.setTableName((String)param.get((Object)"groupTableName"));
                if (StringUtils.hasText(groupObjectCode) && !"-".equals(groupObjectCode)) {
                    groupParam.setObjectcode(groupObjectCode);
                    groupParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
                } else {
                    groupParam.setCode("-");
                    groupParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
                }
                List sharefields = (List)param.get((Object)SHARE_FIELDS);
                if (sharefields != null) {
                    for (String field : sharefields) {
                        groupParam.put(field, param.get((Object)field));
                    }
                }
                if (groupParam.getUnitcode() == null) {
                    groupParam.setUnitcode(param.getUnitcode());
                }
                if ((dataPage = this.getBaseDataQueryService().list(groupParam)) != null && dataPage.getTotal() > 0) {
                    for (BaseDataDO bddo : dataPage.getRows()) {
                        groupNames.add(bddo.getObjectcode());
                    }
                }
            }
        }
        param.setGroupNames((List)groupNames);
    }

    private void initShareFields(BaseDataDTO param) {
        HashSet<String> shareUnitcodes;
        UserLoginDTO currLoginUser;
        BaseDataDefineDO define = this.getBaseDataDefineDO((BaseDataDO)param);
        int sharetype = define.getSharetype();
        if (sharetype == 0) {
            return;
        }
        List objectcodes = param.getBaseDataObjectcodes();
        if (objectcodes != null && !objectcodes.isEmpty()) {
            return;
        }
        if (StringUtils.hasText(param.getObjectcode()) && param.getQueryChildrenType() == null) {
            return;
        }
        List sharefields = (List)param.get((Object)SHARE_FIELDS);
        if (!(param.getId() == null && param.getObjectcode() == null || StringUtils.hasText(param.getUnitcode()) || param.getQueryChildrenType() == null)) {
            BaseDataDTO objParam = new BaseDataDTO();
            objParam.setTenantName(param.getTenantName());
            objParam.setTableName(param.getTableName());
            objParam.setObjectcode(param.getObjectcode());
            objParam.setId(param.getId());
            objParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            objParam.setAuthType(BaseDataOption.AuthType.NONE);
            PageVO<BaseDataDO> dataPage = this.getBaseDataQueryService().list(objParam);
            if (dataPage != null && dataPage.getTotal() > 0) {
                BaseDataDO data = (BaseDataDO)dataPage.getRows().get(0);
                for (String field : sharefields) {
                    if (param.get((Object)field) != null) continue;
                    param.put(field, data.get((Object)field));
                }
                param.setCode(data.getCode());
                return;
            }
        }
        if (!param.containsKey((Object)SHARE_UNITCODES) && sharefields.contains("unitcode") && !StringUtils.hasText(param.getUnitcode()) && (currLoginUser = ShiroUtil.getUser()) != null) {
            param.setUnitcode(currLoginUser.getLoginUnit());
        }
        for (String field : sharefields) {
            if (param.get((Object)field) != null || sharetype == 3 || sharetype == 2 && param.containsKey((Object)SHARE_UNITCODES)) continue;
            throw new RuntimeException("\u9694\u79bb\u7ef4\u5ea6[" + field + "]\u4e3a\u5fc5\u4f20\u53c2\u6570\uff0c\u4e0d\u53ef\u4e3a\u7a7a");
        }
        if (sharetype == 2 && !param.containsKey((Object)SHARE_UNITCODES)) {
            shareUnitcodes = new HashSet<String>();
            shareUnitcodes.add("-");
            OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setTenantName(param.getTenantName());
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setStopflag(Integer.valueOf(-1));
            orgDTO.setRecoveryflag(Integer.valueOf(-1));
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setCode(param.getUnitcode());
            PageVO orgs = orgDataClient.list(orgDTO);
            if (orgs != null && orgs.getTotal() > 0) {
                OrgDO org = (OrgDO)orgs.getRows().get(0);
                Collections.addAll(shareUnitcodes, org.getParents().split("\\/"));
            }
            param.put(SHARE_UNITCODES, shareUnitcodes);
        }
        if (sharetype == 3 && !param.containsKey((Object)SHARE_UNITCODES)) {
            shareUnitcodes = new HashSet();
            shareUnitcodes.add("-");
            shareUnitcodes.add(param.getUnitcode());
            param.put(SHARE_UNITCODES, shareUnitcodes);
        }
    }

    public void initDefineInfo(BaseDataDO param) {
        if (param.containsKey((Object)DEFINE_JSONSTR)) {
            return;
        }
        BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
        defineParam.setTenantName(param.getTenantName());
        defineParam.setName(param.getTableName());
        defineParam.setDeepClone(Boolean.valueOf(false));
        BaseDataDefineDO define = this.baseDataDefineService.get(defineParam);
        if (define == null) {
            throw new RuntimeException("\u672a\u627e\u5230\u57fa\u7840\u6570\u636e\u8868\u5b9a\u4e49\uff1a" + param.getTableName());
        }
        param.put(DEFINE_DO, (Object)define);
        param.put(DEFINE_JSONSTR, (Object)define.getDefine());
        param.put(DEFINE_STRUCTTYPE, (Object)define.getStructtype());
        param.put(DEFINE_SHARETYPE, (Object)define.getSharetype());
        param.put(DEFINE_VERSION_FLAG, (Object)(define.getVersionflag() == null ? 0 : define.getVersionflag()));
        if (define.getSharetype() > 0) {
            String sharefieldname = define.getSharefieldname();
            ArrayList<String> sharefields = new ArrayList<String>();
            if (StringUtils.hasText(sharefieldname)) {
                for (String name : sharefieldname.split("\\,")) {
                    sharefields.add(name.toLowerCase());
                }
            } else {
                sharefields.add("unitcode");
            }
            if (define.getSharetype() == 2 && !sharefields.contains("unitcode")) {
                sharefields.add("unitcode");
            }
            param.put(SHARE_FIELDS, sharefields);
        }
    }

    public void cloneDefineInfo(BaseDataDO source, BaseDataDO target) {
        if (!source.containsKey((Object)DEFINE_JSONSTR)) {
            return;
        }
        target.put(DEFINE_DO, source.get((Object)DEFINE_DO));
        target.put(DEFINE_JSONSTR, source.get((Object)DEFINE_JSONSTR));
        target.put(DEFINE_STRUCTTYPE, source.get((Object)DEFINE_STRUCTTYPE));
        target.put(DEFINE_SHARETYPE, source.get((Object)DEFINE_SHARETYPE));
        target.put(DEFINE_VERSION_FLAG, source.get((Object)DEFINE_VERSION_FLAG));
        if (source.containsKey((Object)SHARE_FIELDS)) {
            target.put(SHARE_FIELDS, source.get((Object)SHARE_FIELDS));
        }
    }

    public List<Map<String, Object>> getShowFieldList(BaseDataDO param) {
        if (param.containsKey((Object)SHOW_FIELD_LIST)) {
            return (List)param.get((Object)SHOW_FIELD_LIST);
        }
        BaseDataDefineDO defineDO = this.getBaseDataDefineDO(param);
        String definestr = defineDO.getDefine();
        ArrayList<Map<String, Object>> showFieldList = new ArrayList<Map<String, Object>>();
        if (StringUtils.hasText(definestr)) {
            List showFields;
            ObjectNode objectNode = JSONUtil.parseObject((String)definestr);
            ArrayNode jsonarray = objectNode.withArray("showFields");
            if (jsonarray == null || jsonarray.isEmpty()) {
                jsonarray = objectNode.withArray("defaultShowFields");
            }
            if (jsonarray != null && (showFields = JSONUtil.parseMapArray((String)jsonarray.toString())) != null) {
                showFieldList.addAll(showFields);
            }
        }
        param.put(SHOW_FIELD_LIST, showFieldList);
        return showFieldList;
    }

    public R checkFieldRepeated(BaseDataDTO param, BaseDataDTO oldData) {
        if (param.containsKey((Object)UNCHECK_FIELD)) {
            return R.ok();
        }
        List<Map<String, Object>> showFieldList = this.getShowFieldList((BaseDataDO)param);
        return this.checkFieldRepeated(param, showFieldList, oldData);
    }

    public R checkField(BaseDataDTO param, BaseDataDTO oldData) {
        List<Map<String, Object>> showFieldList;
        R r;
        if (param.containsKey((Object)UNCHECK_FIELD)) {
            return R.ok();
        }
        if (!param.containsKey((Object)CHECK_VALUE_VALID_OVER)) {
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(param.getTableName());
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = BaseDataDefineStorage.getDataModel(dataModelDTO);
            R r2 = this.checkFieldValueValid(dataModel, param);
            if (r2.getCode() != 0) {
                return r2;
            }
        }
        if (!(param.containsKey((Object)CHECK_FIELD_REQUIRED_OVER) && ((Boolean)param.get((Object)CHECK_FIELD_REQUIRED_OVER)).booleanValue() || (r = this.checkFieldRequired(param, showFieldList = this.getShowFieldList((BaseDataDO)param), oldData)).getCode() == 0)) {
            return r;
        }
        return R.ok();
    }

    public R checkFieldValueValid(DataModelDO dataModel, BaseDataDTO param) {
        List columns = dataModel.getColumns();
        String tableName = param.getTableName();
        String columnName = null;
        Object value = null;
        for (DataModelColumn column : columns) {
            String[] values;
            BigDecimal currValue;
            columnName = column.getColumnName();
            value = param.get((Object)columnName.toLowerCase());
            if (value == null || !StringUtils.hasText(value.toString()) || column.getMappingType() != null && column.getMappingType() == 1 && value instanceof ArrayList) continue;
            if (column.getColumnType() == DataModelType.ColumnType.NVARCHAR) {
                if (value.toString().length() <= column.getLengths()[0]) continue;
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.length.exceed", this.getColumnTitleByDefine(tableName, column), value.toString().length(), column.getLengths()[0]));
            }
            if (column.getColumnType() == DataModelType.ColumnType.NUMERIC) {
                int decimalCount;
                currValue = new BigDecimal(value.toString()).stripTrailingZeros();
                values = currValue.toPlainString().split("\\.");
                if (values.length > 1 && values[1].length() > column.getLengths()[1]) {
                    return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.precision.exceed", this.getColumnTitleByDefine(tableName, column), values[1].length(), column.getLengths()[1]));
                }
                if (values.length <= 0) continue;
                int n = decimalCount = column.getLengths().length > 1 ? column.getLengths()[1] : 0;
                if (values[0].length() + decimalCount <= column.getLengths()[0]) continue;
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.length.exceed", this.getColumnTitleByDefine(tableName, column), values[0].length() + decimalCount, column.getLengths()[0]));
            }
            if (column.getColumnType() != DataModelType.ColumnType.INTEGER) continue;
            currValue = new BigDecimal(value.toString());
            values = currValue.toPlainString().split("\\.");
            int maxLen = Math.min(column.getLengths()[0], 10);
            if (values[0].length() > maxLen) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.length.exceed", this.getColumnTitleByDefine(tableName, column), values[0].length(), maxLen));
            }
            BigDecimal maxValue = new BigDecimal(Integer.MAX_VALUE);
            if (currValue.compareTo(maxValue) <= 0) continue;
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.check.maximum.value.exceed", this.getColumnTitleByDefine(tableName, column), currValue.toPlainString(), maxValue.toPlainString()));
        }
        return R.ok();
    }

    public R checkFieldRequired(BaseDataDTO data, List<Map<String, Object>> showFields, BaseDataDTO oldData) {
        if (showFields == null || showFields.isEmpty()) {
            return R.ok();
        }
        for (Map<String, Object> showfield : showFields) {
            Object value;
            Boolean isRequired;
            if (showfield.get("virtualFlag") != null && Boolean.valueOf(showfield.get("virtualFlag").toString()).booleanValue()) continue;
            String columnName = showfield.get("columnName").toString().toLowerCase();
            if (oldData != null && !data.containsKey((Object)columnName) || (isRequired = (Boolean)showfield.get("required")) == null || !isRequired.booleanValue() || (value = data.get((Object)showfield.get("columnName").toString().toLowerCase())) != null && StringUtils.hasText(value.toString())) continue;
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.empty", showfield.get("columnTitle").toString()));
        }
        return R.ok();
    }

    public R checkFieldRepeated(BaseDataDTO data, List<Map<String, Object>> showFields, BaseDataDTO oldData) {
        if (showFields == null || showFields.isEmpty()) {
            return R.ok();
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(data.getTableName());
        param.setTenantName(data.getTenantName());
        param.setStopflag(Integer.valueOf(-1));
        param.setRecoveryflag(Integer.valueOf(-1));
        if (data.containsKey((Object)DEFINE_DO)) {
            this.cloneDefineInfo((BaseDataDO)data, (BaseDataDO)param);
        } else {
            this.initDefineInfo((BaseDataDO)param);
        }
        BaseDataDefineDO defineDO = this.getBaseDataDefineDO((BaseDataDO)param);
        int sharetype = defineDO.getSharetype();
        if (sharetype > 0) {
            List sharefields = (List)param.get((Object)SHARE_FIELDS);
            if (param.get((Object)SHARE_FIELDS) != null) {
                for (String field : sharefields) {
                    param.put(field, data.get((Object)field));
                }
            }
        }
        param.setVersionDate(data.getVersionDate());
        for (Map<String, Object> showfield : showFields) {
            if (showfield.get("virtualFlag") != null && Boolean.valueOf(showfield.get("virtualFlag").toString()).booleanValue()) continue;
            String columnName = showfield.get("columnName").toString().toLowerCase();
            Boolean unique = (Boolean)showfield.get("unique");
            Object val = data.get((Object)columnName);
            if (unique == null || !unique.booleanValue() || val == null) continue;
            param.put(columnName, val);
            PageVO<BaseDataDO> page = this.getBaseDataQueryService().list(param);
            if (page == null || page.getTotal() == 0) continue;
            for (BaseDataDO row : page.getRows()) {
                if (oldData != null && row.getCode().equals(oldData.getCode())) continue;
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.object.check.repeat", showfield.get("columnTitle").toString()));
            }
            param.remove((Object)columnName);
        }
        return R.ok();
    }

    public String getColumnTitleByDefine(String tableName, DataModelColumn column) {
        ArrayList<String> keys = new ArrayList<String>();
        String currKey = "VA#basedata#defines#" + tableName + "#showcol#" + column.getColumnName();
        keys.add(currKey);
        VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
        dataResourceDTO.setKey(keys);
        List results = this.i18nResourceClient.queryList(dataResourceDTO);
        if (results == null || results.isEmpty()) {
            return column.getColumnTitle();
        }
        String title = (String)results.get(0);
        if (!StringUtils.hasText(title)) {
            return column.getColumnTitle();
        }
        return title;
    }

    public Set<String> loadModelCols(String tenantName, String tableName) {
        return this.loadModelColMap(tenantName, tableName).keySet();
    }

    public Map<String, DataModelColumn> loadModelColMap(String tenantName, String tableName) {
        HashMap<String, DataModelColumn> modelCols = new HashMap<String, DataModelColumn>();
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(tenantName);
        dataModelDTO.setName(tableName);
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = BaseDataDefineStorage.getDataModel(dataModelDTO);
        List cols = dataModel.getColumns();
        for (DataModelColumn column : cols) {
            modelCols.put(column.getColumnName().toLowerCase(), column);
        }
        return modelCols;
    }

    public boolean checkRelated(BaseDataDO param) {
        String curTableName = param.getTableName();
        String sqlTemp = "select count(0) as cnt from %s where %s = '%s'";
        SqlDTO sqlDTO = new SqlDTO(param.getTenantName(), sqlTemp);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        PageVO dataModels = this.dataModelClient.list(dataModelDTO);
        for (DataModelDO dataModel : dataModels.getRows()) {
            if (dataModel.getColumns() == null) continue;
            for (DataModelColumn column : dataModel.getColumns()) {
                if (!StringUtils.hasText(column.getMapping()) || !column.getMapping().split("\\.")[0].equalsIgnoreCase(curTableName)) continue;
                sqlDTO.setSql(String.format(sqlTemp, dataModel.getName(), column.getColumnName(), param.getObjectcode()));
                try {
                    if (this.commonDao.countBySql(sqlDTO) <= 0) continue;
                    param.put("relateInfoCheck", (Object)("\u3010" + dataModel.getTitle() + " - " + dataModel.getName() + "[" + column.getColumnName() + "]\u3011"));
                    return true;
                }
                catch (Exception exception) {
                }
            }
        }
        return false;
    }

    public R modifyVersionCheck(BaseDataDTO basedataParam) {
        this.initDefineInfo((BaseDataDO)basedataParam);
        if (this.isVersionMgrStarted((BaseDataDO)basedataParam)) {
            this.initVersionInfo((BaseDataDO)basedataParam);
            if (basedataParam.isForceUpdateHistoryVersionData()) {
                return R.ok();
            }
            BaseDataVersionDO versionData = (BaseDataVersionDO)basedataParam.get((Object)VERSION_DATA);
            if (versionData != null && !this.baseDataContextService.allowModifyHistoricalData(basedataParam.getTableName())) {
                Calendar ca = Calendar.getInstance();
                ca.setTime(versionData.getInvalidtime());
                if (ca.get(1) != 9999) {
                    return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.historical.not.allow.modify", new Object[0]));
                }
            }
        } else {
            basedataParam.put(VERSION_DATA, null);
        }
        return R.ok();
    }

    private void initVersionInfo(BaseDataDO param) {
        UserLoginDTO userLoginDTO;
        if (param.containsKey((Object)VERSION_DATA)) {
            return;
        }
        Date versionDate = this.getVersionDate(param);
        if (versionDate == null && (userLoginDTO = ShiroUtil.getUser()) != null) {
            versionDate = userLoginDTO.getLoginDate();
        }
        if (versionDate == null) {
            versionDate = new Date();
        }
        BaseDataVersionDTO verParam = new BaseDataVersionDTO();
        verParam.setTablename(param.getTableName());
        verParam.setVersionDate(versionDate);
        param.put(VERSION_DATA, (Object)this.baseDataVersionService.get(verParam));
    }

    private Date getVersionDate(BaseDataDO param) {
        Object value = param.get((Object)"versionDate");
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date)value;
        }
        if (value instanceof Long) {
            return new Date((Long)value);
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(value.toString());
        }
        catch (ParseException e) {
            return null;
        }
    }

    public void setVersionInfo(BaseDataDO basedata) {
        BaseDataVersionDO versionData = null;
        if (this.isVersionMgrStarted(basedata)) {
            Object versionDO = basedata.get((Object)VERSION_DATA);
            if (versionDO instanceof BaseDataVersionDO) {
                versionData = (BaseDataVersionDO)versionDO;
            } else {
                basedata.remove((Object)VERSION_DATA);
                this.initVersionInfo(basedata);
                versionData = (BaseDataVersionDO)basedata.get((Object)VERSION_DATA);
            }
        }
        if (versionData != null) {
            basedata.setValidtime(versionData.getValidtime());
            basedata.setInvalidtime(versionData.getInvalidtime());
        } else {
            basedata.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
            basedata.setInvalidtime(BaseDataConsts.VERSION_MAX_DATE);
        }
    }

    private void initExtendParam() {
        if (paramInterceptorsInted) {
            return;
        }
        Map paramInterceptorMap = ApplicationContextRegister.getBeansOfType(BaseDataParamInterceptor.class);
        if (paramInterceptorMap != null && !paramInterceptorMap.isEmpty()) {
            paramInterceptors = paramInterceptorMap.values();
        }
        paramInterceptorsInted = true;
    }

    public R loadExtendParam(BaseDataDTO param, BaseDataAction action) {
        this.initExtendParam();
        if (paramInterceptors == null) {
            return R.ok();
        }
        if (param.containsKey((Object)LOAD_EXTEND_PARAM_OVER)) {
            return R.ok();
        }
        try {
            for (BaseDataParamInterceptor interceptor : paramInterceptors) {
                interceptor.modify(param, action);
            }
        }
        catch (Exception e) {
            logger.error("\u62e6\u622a\u5f02\u5e38\uff1a" + param.getTableName(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    public void initBatchOptExtendParam(BaseDataBatchOptDTO baseDataDTO, String subBizType) {
        List list;
        BaseDataDTO param = baseDataDTO.getQueryParam();
        if (param == null) {
            param = new BaseDataDTO();
        }
        if (param.getTableName() == null && (list = baseDataDTO.getDataList()) != null && !list.isEmpty()) {
            param.setTableName(((BaseDataDO)list.get(0)).getTableName());
        }
        if (subBizType != null) {
            param.put(subBizType, (Object)true);
        }
        baseDataDTO.setQueryParam(param);
    }

    public R loadBatchOptExtendParam(BaseDataBatchOptDTO baseDataDTO, BaseDataAction action) {
        this.initBatchOptExtendParam(baseDataDTO, null);
        this.initExtendParam();
        if (paramInterceptors == null) {
            return R.ok();
        }
        try {
            for (BaseDataParamInterceptor interceptor : paramInterceptors) {
                interceptor.batchModify(baseDataDTO, action);
            }
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u62e6\u622a\u5f02\u5e38\uff1a" + baseDataDTO.getQueryParam().getTableName(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok();
    }

    public BaseDataVersionDO getBaseDataVersion(BaseDataDTO param) {
        BaseDataVersionDO verDO = null;
        Object vd = param.get((Object)VERSION_DATA);
        if (vd instanceof Map) {
            verDO = (BaseDataVersionDO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)vd), BaseDataVersionDO.class);
        } else if (vd instanceof OrgVersionDO) {
            verDO = (BaseDataVersionDO)vd;
        }
        if (verDO != null && verDO.getTablename().equals(param.getTableName())) {
            return verDO;
        }
        boolean isCacheDisabled = this.isCacheDisabled((BaseDataDO)param);
        if (!this.isVersionMgrStarted((BaseDataDO)param)) {
            BaseDataVersionDO defaultVersionInfo = new BaseDataVersionDO();
            defaultVersionInfo.setId(DataTypeUtil.UUID_EMPTY);
            defaultVersionInfo.setName("-");
            defaultVersionInfo.setTablename(param.getTableName());
            defaultVersionInfo.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
            defaultVersionInfo.setInvalidtime(BaseDataConsts.VERSION_MAX_DATE);
            defaultVersionInfo.setActiveflag(Integer.valueOf(isCacheDisabled ? 0 : 1));
            return defaultVersionInfo;
        }
        Date versionDate = param.getVersionDate();
        BaseDataVersionDTO versionDTO = new BaseDataVersionDTO();
        versionDTO.setTablename(param.getTableName());
        if (versionDate != null) {
            versionDTO.setVersionDate(versionDate);
        } else {
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null && user.getLoginDate() != null) {
                versionDTO.setVersionDate(user.getLoginDate());
            } else {
                versionDTO.setVersionDate(new Date());
            }
        }
        BaseDataVersionDO baseDataVersion = this.baseDataVersionService.get(versionDTO);
        if (isCacheDisabled && baseDataVersion != null && baseDataVersion.isActive()) {
            BaseDataVersionDO cloneVersion = new BaseDataVersionDO();
            cloneVersion.setId(baseDataVersion.getId());
            cloneVersion.setName(baseDataVersion.getName());
            cloneVersion.setTablename(baseDataVersion.getTablename());
            cloneVersion.setValidtime(baseDataVersion.getValidtime());
            cloneVersion.setInvalidtime(baseDataVersion.getInvalidtime());
            cloneVersion.setActiveflag(Integer.valueOf(0));
            baseDataVersion = cloneVersion;
        }
        if (baseDataVersion != null) {
            param.put(VERSION_DATA, (Object)baseDataVersion);
        }
        return baseDataVersion;
    }

    public BaseDataDefineDO getBaseDataDefineDO(BaseDataDO param) {
        Object defineDO = param.get((Object)DEFINE_DO);
        if (defineDO instanceof BaseDataDefineDO) {
            return (BaseDataDefineDO)defineDO;
        }
        param.remove((Object)DEFINE_JSONSTR);
        this.initDefineInfo(param);
        return (BaseDataDefineDO)param.get((Object)DEFINE_DO);
    }

    public boolean isVersionMgrStarted(BaseDataDO param) {
        BaseDataDefineDO define = this.getBaseDataDefineDO(param);
        Integer flag = define.getVersionflag();
        if (flag == null) {
            return false;
        }
        return flag == 1;
    }

    public boolean isAuthStarted(BaseDataDO param) {
        BaseDataDefineDO define = this.getBaseDataDefineDO(param);
        Integer flag = define.getAuthflag();
        if (flag == null) {
            return false;
        }
        return flag == 1;
    }

    public boolean isCacheDisabled(BaseDataDO param) {
        BaseDataDefineDO define = this.getBaseDataDefineDO(param);
        Integer cachedisabledFlag = define.getCachedisabled();
        if (cachedisabledFlag == null) {
            return false;
        }
        return cachedisabledFlag == 1;
    }

    public boolean isDummy(BaseDataDO param) {
        BaseDataDefineDO define = this.getBaseDataDefineDO(param);
        Integer dummyFlag = define.getDummyflag();
        if (dummyFlag == null) {
            return false;
        }
        return dummyFlag == 1;
    }

    public Map<String, String> getSensitiveFields(BaseDataDO param) {
        if (param.containsKey((Object)SENSITIVE_FIELDS)) {
            return (Map)param.get((Object)SENSITIVE_FIELDS);
        }
        BaseDataDefineDO defineDO = this.getBaseDataDefineDO(param);
        Map<String, String> sensitiveFields = this.baseDataContextService.getSensitiveFields(defineDO);
        param.put(SENSITIVE_FIELDS, sensitiveFields);
        return sensitiveFields;
    }

    public void convertRealValue(List<BaseDataCacheDO> datas, BaseDataDO param) {
        if (datas == null || datas.isEmpty()) {
            return;
        }
        Map colIndex = BaseDataContext.getColIndexMap((String)param.getTenantName(), (String)param.getTableName());
        Map<String, String> sensitiveFields = this.getSensitiveFields(param);
        for (BaseDataCacheDO data : datas) {
            this.convertRealValue(data, colIndex, sensitiveFields);
        }
    }

    public void convertRealValue(BaseDataCacheDO data, Map<String, Integer> colIndexMap, Map<String, String> sensitiveFields) {
        if (sensitiveFields == null || sensitiveFields.isEmpty()) {
            return;
        }
        Object val = null;
        String columnName = null;
        String sensitiveType = null;
        Integer colIndex = null;
        for (Map.Entry<String, Integer> entry : colIndexMap.entrySet()) {
            columnName = entry.getKey();
            colIndex = colIndexMap.get(columnName);
            if (colIndex == null || colIndex < 0 || !((val = data.get((Object)columnName)) instanceof String)) continue;
            sensitiveType = sensitiveFields.get(columnName);
            if (!StringUtils.hasText(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), null);
                continue;
            }
            if ("telephoneSensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.{3})(.*?)(.{4})$", "$1****$3");
                continue;
            }
            if ("telephone2Sensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.{3})(.*?)(.{4})$", "*******$3");
                continue;
            }
            if ("idcardSensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.{2})(.*?)(.{2})$", "$1**************$3");
                continue;
            }
            if ("idcard2Sensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.{6})(.*?)(.{4})$", "$1********$3");
                continue;
            }
            if ("idcard3Sensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.{6})(.*?)(.{2})(.{1})(.{1})$", "$1********$3$5");
                continue;
            }
            if ("bankcardSensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.*?)(.{4})$", "************$2");
                continue;
            }
            if ("bankcard2Sensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^(.{6})(.*?)(.{4})$", "$1******$3");
                continue;
            }
            if ("emailSensitive".equals(sensitiveType)) {
                data.convertRealValue((Object)columnName, colIndex.intValue(), "^([^\\s@]{1})([^\\s@]*)(@[\\w.-]+\\.[\\w.-]+)$", "$1*****$3");
                continue;
            }
            if (!"allSensitive".equals(sensitiveType)) continue;
            data.convertRealValue((Object)columnName, colIndex.intValue(), "******");
        }
    }

    public List<String> getSecurityFields(BaseDataDO param) {
        if (param.containsKey((Object)SECURITY_FIELDS)) {
            return (List)param.get((Object)SECURITY_FIELDS);
        }
        BaseDataDefineDO defineDO = this.getBaseDataDefineDO(param);
        String definestr = defineDO.getDefine();
        ArrayList<String> securityFields = null;
        if (!StringUtils.hasText(definestr)) {
            param.put(SECURITY_FIELDS, securityFields);
            return securityFields;
        }
        ObjectNode objectNode = JSONUtil.parseObject((String)definestr);
        ArrayNode jsonArray = objectNode.withArray("fieldProps");
        if (jsonArray == null) {
            param.put(SECURITY_FIELDS, securityFields);
            return securityFields;
        }
        securityFields = new ArrayList<String>();
        boolean flag = false;
        for (JsonNode node : jsonArray) {
            if (!node.has("fieldSecurity") || !(flag = node.get("fieldSecurity").asBoolean())) continue;
            securityFields.add(node.get("columnName").asText().toLowerCase());
        }
        param.put(SECURITY_FIELDS, securityFields);
        return securityFields;
    }

    public void encodeSecurityFields(BaseDataDO data) {
        this.encodeSecurityFields(Collections.singletonList(data), data);
    }

    /*
     * WARNING - void declaration
     */
    public void encodeSecurityFields(List<? extends BaseDataDO> dataList, BaseDataDO param) {
        List<String> securityFields = this.getSecurityFields(param);
        if (securityFields == null || securityFields.isEmpty()) {
            return;
        }
        Object val = null;
        ArrayList<String> plaintexts = new ArrayList<String>();
        for (BaseDataDO baseDataDO : dataList) {
            for (String string : securityFields) {
                val = baseDataDO.get((Object)string);
                if (val == null || val.toString().isEmpty()) continue;
                plaintexts.add((String)val);
            }
        }
        if (plaintexts.isEmpty()) {
            return;
        }
        List values = this.encryptClient.encode(plaintexts);
        boolean bl = false;
        for (BaseDataDO baseDataDO : dataList) {
            for (String field : securityFields) {
                void var7_9;
                val = baseDataDO.get((Object)field);
                if (val == null || val.toString().isEmpty()) continue;
                baseDataDO.put(field, values.get((int)var7_9));
                ++var7_9;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void decodeSecurityFields(List<? extends BaseDataDO> dataList, BaseDataDO param) {
        List<String> securityFields = this.getSecurityFields(param);
        if (securityFields == null || securityFields.isEmpty()) {
            return;
        }
        Object val = null;
        ArrayList<String> ciphertexts = new ArrayList<String>();
        for (BaseDataDO baseDataDO : dataList) {
            for (String string : securityFields) {
                val = baseDataDO.get((Object)string);
                if (val == null || val.toString().isEmpty()) continue;
                ciphertexts.add((String)val);
            }
        }
        if (ciphertexts.isEmpty()) {
            return;
        }
        List values = this.encryptClient.decode(ciphertexts);
        boolean bl = false;
        for (BaseDataDO baseDataDO : dataList) {
            for (String field : securityFields) {
                void var7_9;
                val = baseDataDO.get((Object)field);
                if (val == null || val.toString().isEmpty()) continue;
                baseDataDO.put(field, values.get((int)var7_9));
                ++var7_9;
            }
        }
    }
}

