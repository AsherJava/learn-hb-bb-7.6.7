/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgBatchOptDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.OrgCodeUniqueCheckInterceptor
 *  com.jiuqi.va.extend.OrgDataAction
 *  com.jiuqi.va.extend.OrgDataParamInterceptor
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgBatchOptDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.OrgCodeUniqueCheckInterceptor;
import com.jiuqi.va.extend.OrgDataAction;
import com.jiuqi.va.extend.OrgDataParamInterceptor;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.dao.VaOrgCategoryDao;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgAuthService;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgVersionService;
import com.jiuqi.va.organization.service.impl.help.OrgContextService;
import com.jiuqi.va.organization.service.impl.help.OrgDataQueryService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgDataParamService {
    private static Logger logger = LoggerFactory.getLogger(OrgDataParamService.class);
    public static final String ORG_VERSION_DATA = "orgVersionData";
    public static final String LOAD_EXTEND_PARAM_OVER = "LOAD_EXTEND_PARAM_OVER";
    public static final String VERSION_DATE_LIST = "VERSION_DATE_LIST";
    @Autowired
    private OrgContextService orgContextService;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    public VaOrgCategoryDao orgCategoryDao;
    @Autowired
    public OrgVersionService orgVersionService;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private CommonDao commonDao;
    @Autowired(required=false)
    private List<OrgDataParamInterceptor> paramInterceptors;
    @Autowired(required=false)
    private List<OrgCodeUniqueCheckInterceptor> orgCodeUniqueCheckInterceptors;
    private static Boolean isNvwaEnv = null;
    private static Boolean isVaEnv = null;
    private OrgDataQueryService orgDataQueryService;
    private OrgAuthService orgAuthService;

    public OrgDataQueryService getOrgDataQueryServcice() {
        if (this.orgDataQueryService == null) {
            this.orgDataQueryService = (OrgDataQueryService)ApplicationContextRegister.getBean(OrgDataQueryService.class);
        }
        return this.orgDataQueryService;
    }

    public OrgAuthService getOrgAuthService() {
        if (this.orgAuthService == null) {
            this.orgAuthService = (OrgAuthService)ApplicationContextRegister.getBean(OrgAuthService.class);
        }
        return this.orgAuthService;
    }

    public OrgVersionDO getOrgVersion(OrgDTO orgDTO) {
        OrgVersionDO verDO = null;
        Object vd = orgDTO.get((Object)ORG_VERSION_DATA);
        if (vd instanceof Map) {
            verDO = (OrgVersionDO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)vd), OrgVersionDO.class);
        } else if (vd instanceof OrgVersionDO) {
            verDO = (OrgVersionDO)vd;
        }
        if (verDO != null && verDO.getCategoryname().equals(orgDTO.getCategoryname())) {
            return verDO;
        }
        Date versionDate = orgDTO.getVersionDate();
        OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
        orgVersionDTO.setCategoryname(orgDTO.getCategoryname());
        if (versionDate != null) {
            orgVersionDTO.setVersionDate(versionDate);
        } else {
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null && user.getLoginDate() != null) {
                orgVersionDTO.setVersionDate(user.getLoginDate());
            } else {
                orgVersionDTO.setVersionDate(new Date());
            }
        }
        OrgVersionDO orgVersion = this.orgVersionService.get(orgVersionDTO);
        if (orgVersion != null) {
            orgDTO.put(ORG_VERSION_DATA, (Object)orgVersion);
        }
        return orgVersion;
    }

    public void setVersionDate(OrgDTO orgDTO) {
        if (orgDTO.getVersionDate() != null) {
            return;
        }
        OrgVersionDO orgVersion = this.getOrgVersion(orgDTO);
        if (orgVersion != null) {
            orgDTO.setVersionDate(orgVersion.getValidtime());
            orgDTO.put(ORG_VERSION_DATA, (Object)orgVersion);
        }
    }

    public boolean isAuthOrg(OrgDTO orgDataDTO) {
        UserLoginDTO currLoginUser = ShiroUtil.getUser();
        if (currLoginUser == null) {
            return false;
        }
        if ("super".equalsIgnoreCase(currLoginUser.getMgrFlag())) {
            return true;
        }
        R rs = this.getOrgAuthService().existDataAuth((UserDO)currLoginUser, orgDataDTO);
        Object exit = rs.get((Object)"exist");
        return exit != null && (Boolean)exit != false;
    }

    public R checkModify(OrgDTO orgDTO, boolean isUpdate) {
        if (!StringUtils.hasText(orgDTO.getCode())) {
            return R.error((String)"\u673a\u6784\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (orgDTO.getVersionDate() == null) {
            return R.error((String)"\u673a\u6784\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        }
        R checkVerRs = this.checkModifyVersionData(orgDTO);
        if (checkVerRs.getCode() != 0) {
            return checkVerRs;
        }
        OrgDTO oldOrgDataDTO = new OrgDTO();
        oldOrgDataDTO.setCategoryname(orgDTO.getCategoryname());
        oldOrgDataDTO.setVersionDate(orgDTO.getVersionDate());
        oldOrgDataDTO.setCode(orgDTO.getCode());
        oldOrgDataDTO.setStopflag(Integer.valueOf(-1));
        oldOrgDataDTO.setRecoveryflag(Integer.valueOf(-1));
        oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
        OrgDO oldOrg = this.getOrgDataQueryServcice().get(oldOrgDataDTO);
        if (isUpdate && oldOrg != null) {
            if (oldOrg.getRecoveryflag() == 1) {
                return R.error((String)"\u8be5\u673a\u6784\u5df2\u88ab\u6807\u8bb0\u4e3a\u56de\u6536\u72b6\u6001");
            }
            oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.MANAGE);
            if (!this.isAuthOrg(oldOrgDataDTO)) {
                return R.error((String)"\u5f53\u524d\u7528\u6237\u7f3a\u5c11\u5bf9\u8be5\u673a\u6784\u7684\u7ba1\u7406\u6743\u9650");
            }
        }
        R rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        rs.put("oldOrg", (Object)oldOrg);
        return rs;
    }

    public R checkModifyVersionData(OrgDTO orgDTO) {
        if (!orgDTO.isForceUpdateHistoryVersionData() && !this.orgContextService.isModifyHistoricalDataAllow()) {
            OrgVersionDO orgVersion = this.getOrgVersion(orgDTO);
            Calendar ca = Calendar.getInstance();
            ca.setTime(orgVersion.getInvalidtime());
            if (ca.get(1) != 9999) {
                return R.error((String)"\u5386\u53f2\u7248\u672c\u6570\u636e\u4e0d\u5141\u8bb8\u4fee\u6539");
            }
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    public R checkRelated(OrgDTO orgDTO) {
        JDialectUtil jDialectUtil;
        String tbname = orgDTO.getCategoryname();
        if (isNvwaEnv == null) {
            jDialectUtil = JDialectUtil.getInstance();
            isNvwaEnv = jDialectUtil.hasTable(new JTableModel(orgDTO.getTenantName(), "NP_USER"));
        }
        if (isVaEnv == null) {
            jDialectUtil = JDialectUtil.getInstance();
            isVaEnv = jDialectUtil.hasTable(new JTableModel(orgDTO.getTenantName(), "AUTH_USER"));
        }
        String sqlTemp = "select count(0) as cnt from %s where %s = '%s'";
        SqlDTO sqlDTO = new SqlDTO(orgDTO.getTenantName(), sqlTemp);
        if ("MD_ORG".equalsIgnoreCase(tbname)) {
            if (isVaEnv != null && isVaEnv.booleanValue()) {
                sqlDTO.setSql(String.format(sqlTemp, "AUTH_USER", "UNITCODE", orgDTO.getCode()));
                if (this.commonDao.countBySql(sqlDTO) > 0) {
                    return R.error((String)"AUTH_USER[UNITCODE]");
                }
            } else if (isNvwaEnv != null && isNvwaEnv.booleanValue()) {
                sqlDTO.setSql(String.format(sqlTemp, "NP_USER", "ORG_CODE", orgDTO.getCode()));
                if (this.commonDao.countBySql(sqlDTO) > 0) {
                    return R.error((String)"NP_USER[ORG_CODE]");
                }
            }
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        PageVO dataModels = this.dataModelClient.list(dataModelDTO);
        String mappingTb = null;
        for (DataModelDO dataModel : dataModels.getRows()) {
            if (dataModel.getColumns() == null) continue;
            for (DataModelColumn column : dataModel.getColumns()) {
                if (!StringUtils.hasText(column.getMapping()) || !(mappingTb = column.getMapping().split("\\.")[0]).equalsIgnoreCase(orgDTO.getCategoryname())) continue;
                sqlDTO.setSql(String.format(sqlTemp, dataModel.getName(), column.getColumnName(), orgDTO.getCode()));
                try {
                    if (this.commonDao.countBySql(sqlDTO) <= 0) continue;
                    return R.error((String)(dataModel.getName() + "[" + column.getColumnName() + "]"));
                }
                catch (Exception exception) {
                }
            }
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    public Map<String, DataModelColumn> loadModelCols(String tenantName, String tableName) {
        HashMap<String, DataModelColumn> modelCols = new HashMap<String, DataModelColumn>();
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setTenantName(tenantName);
        dataModelDTO.setName(tableName.toUpperCase());
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
        List cols = dataModel.getColumns();
        for (DataModelColumn column : cols) {
            modelCols.put(column.getColumnName().toLowerCase(), column);
        }
        return modelCols;
    }

    public R checkOrgCode(OrgDTO orgDTO, OrgDataAction action) {
        if (!StringUtils.hasText(orgDTO.getOrgcode())) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        HashMap<String, String> errorResult = new HashMap<String, String>();
        OrgCategoryDO orgCategoryParam = new OrgCategoryDO();
        orgCategoryParam.setName(orgDTO.getCategoryname());
        orgCategoryParam.setDeepClone(false);
        OrgCategoryDO orgCategory = this.orgCategoryService.get(orgCategoryParam);
        OrgBatchOptDTO orgBatchOptDTO = new OrgBatchOptDTO();
        OrgDTO rqParam = new OrgDTO();
        rqParam.setCategoryname(orgDTO.getCategoryname());
        rqParam.setVersionDate(orgDTO.getVersionDate());
        orgBatchOptDTO.setQueryParam(rqParam);
        ArrayList<OrgDTO> dataList = new ArrayList<OrgDTO>();
        dataList.add(orgDTO);
        orgBatchOptDTO.setDataList(dataList);
        this.checkOrgCode(orgBatchOptDTO, errorResult, orgCategory, action);
        if (!errorResult.isEmpty()) {
            return R.error((String)"\u663e\u793a\u4ee3\u7801\u91cd\u590d");
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    public void checkOrgCode(OrgBatchOptDTO orgBatchOptDTO, Map<String, String> errorResult, OrgCategoryDO orgCategory, OrgDataAction action) {
        List dataList = orgBatchOptDTO.getDataList();
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        OrgDTO param = orgBatchOptDTO.getQueryParam();
        if (this.orgCodeUniqueCheckInterceptors != null && !this.orgCodeUniqueCheckInterceptors.isEmpty()) {
            Integer stopflag = param.getStopflag();
            param.setStopflag(Integer.valueOf(-1));
            for (OrgCodeUniqueCheckInterceptor checkInte : this.orgCodeUniqueCheckInterceptors) {
                checkInte.check(orgBatchOptDTO, errorResult, orgCategory, action);
            }
            if (stopflag != null) {
                param.setStopflag(stopflag);
            } else {
                param.remove((Object)"stopflag");
            }
        } else {
            ArrayList<String> orgcodes = new ArrayList<String>();
            HashMap<String, OrgDO> newDatas = new HashMap<String, OrgDO>();
            for (OrgDO org : dataList) {
                if (!StringUtils.hasText(org.getOrgcode())) continue;
                if (orgcodes.contains(org.getOrgcode())) {
                    errorResult.put(org.getCode(), "\u663e\u793a\u4ee3\u7801\u91cd\u590d");
                    continue;
                }
                orgcodes.add(org.getOrgcode());
                orgcodes.add(org.getCode());
                newDatas.put(org.getOrgcode(), org);
            }
            OrgDTO oldOrgDataDTO = new OrgDTO();
            oldOrgDataDTO.setCategoryname(param.getCategoryname());
            oldOrgDataDTO.setVersionDate(param.getVersionDate());
            oldOrgDataDTO.setAuthType(OrgDataOption.AuthType.NONE);
            oldOrgDataDTO.setStopflag(Integer.valueOf(-1));
            oldOrgDataDTO.setOrgOrgcodes(orgcodes);
            PageVO<OrgDO> oldList = this.getOrgDataQueryServcice().list(oldOrgDataDTO);
            oldOrgDataDTO.setOrgOrgcodes(null);
            oldOrgDataDTO.setOrgCodes(orgcodes);
            PageVO<OrgDO> oldList2 = this.getOrgDataQueryServcice().list(oldOrgDataDTO);
            if (oldList != null && oldList.getTotal() > 0) {
                if (oldList2 != null && oldList2.getTotal() > 0) {
                    oldList.getRows().addAll(oldList2.getRows());
                }
            } else {
                oldList = oldList2;
            }
            if (oldList != null && oldList.getTotal() > 0) {
                for (OrgDO orgDO : oldList.getRows()) {
                    if (action == OrgDataAction.Add) {
                        if (!orgcodes.contains(orgDO.getOrgcode()) && !orgcodes.contains(orgDO.getCode())) continue;
                        errorResult.put(orgDO.getCode(), "\u663e\u793a\u4ee3\u7801\u91cd\u590d");
                        continue;
                    }
                    if (newDatas.containsKey(orgDO.getOrgcode()) && !orgDO.getCode().equals(((OrgDO)newDatas.get(orgDO.getOrgcode())).getCode())) {
                        errorResult.put(orgDO.getCode(), "\u663e\u793a\u4ee3\u7801\u91cd\u590d");
                    }
                    if (!newDatas.containsKey(orgDO.getCode()) || orgDO.getCode().equals(((OrgDO)newDatas.get(orgDO.getCode())).getCode())) continue;
                    errorResult.put(orgDO.getCode(), "\u663e\u793a\u4ee3\u7801\u91cd\u590d");
                }
            }
        }
    }

    public void setDefaultVal(OrgDTO orgDTO) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setTenantName(orgDTO.getTenantName());
        orgCategoryDO.setName(orgDTO.getCategoryname());
        List<ZBDTO> zbdtos = this.orgCategoryService.listZB(orgCategoryDO);
        BaseDataDTO baseDataDTO = null;
        PageVO baseDataPageVO = null;
        List baseDataDOList = null;
        int size = 0;
        for (ZBDTO zbDTO : zbdtos) {
            if (!StringUtils.hasText(zbDTO.getDefaultVal())) continue;
            if (zbDTO.getRelatetype() != null && zbDTO.getRelatetype() == 1) {
                baseDataDTO = new BaseDataDTO();
                baseDataDTO.setTenantName(zbDTO.getTenantName());
                baseDataDTO.setTableName(zbDTO.getReltablename());
                baseDataDTO.setUnitcode(orgDTO.getCode());
                baseDataDTO.setParentcode("-");
                baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                baseDataPageVO = this.baseDataClient.list(baseDataDTO);
                if (baseDataPageVO.getTotal() == 0) {
                    orgDTO.put(zbDTO.getName().toLowerCase(), null);
                    continue;
                }
                baseDataDOList = baseDataPageVO.getRows();
                size = baseDataDOList.stream().filter(baseDataDO -> zbDTO.getDefaultVal().equals(baseDataDO.getObjectcode())).collect(Collectors.toList()).size();
                if (size == 0) {
                    orgDTO.put(zbDTO.getName().toLowerCase(), null);
                    continue;
                }
            }
            orgDTO.put(zbDTO.getName().toLowerCase(), (Object)zbDTO.getDefaultVal());
        }
    }

    public R checkRemove(OrgDTO orgDataDTO) {
        R rs = this.checkModify(orgDataDTO, true);
        if (rs.getCode() != 0) {
            return rs;
        }
        OrgDO oldOrgDO = (OrgDO)rs.get((Object)"oldOrg");
        if (oldOrgDO == null) {
            return R.error((String)"\u8be5\u673a\u6784\u5df2\u4e0d\u5b58\u5728");
        }
        R relRs = this.checkRelated(orgDataDTO);
        if (relRs.getCode() != 0) {
            return R.error((String)("\u8be5\u673a\u6784\u5df2\u88ab " + relRs.getMsg() + " \u5f15\u7528\uff0c\u4e0d\u53ef\u5220\u9664"));
        }
        return rs;
    }

    public R checkFieldValueValid(OrgDTO orgDTO) {
        Map<String, DataModelColumn> columns = this.loadModelCols(orgDTO.getTenantName(), orgDTO.getCategoryname());
        Object value = null;
        DataModelColumn column = null;
        String key = null;
        for (Map.Entry<String, DataModelColumn> entry : columns.entrySet()) {
            String[] values;
            BigDecimal currValue;
            key = entry.getKey();
            column = entry.getValue();
            value = orgDTO.get((Object)key);
            if (value == null || !StringUtils.hasText(value.toString()) || column.getMappingType() != null && column.getMappingType() == 1 && value instanceof ArrayList) continue;
            if (column.getColumnType() == DataModelType.ColumnType.NVARCHAR) {
                if (value.toString().length() <= column.getLengths()[0]) continue;
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.value.length.exceed", column.getColumnTitle(), value.toString().length(), column.getLengths()[0]));
            }
            if (column.getColumnType() == DataModelType.ColumnType.NUMERIC) {
                int decimalCnt;
                currValue = new BigDecimal(value.toString());
                values = currValue.toPlainString().split("\\.");
                if (values.length > 1 && values[1].length() > column.getLengths()[1]) {
                    return R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.maximum.precision.exceed", column.getColumnTitle(), values[1].length(), column.getLengths()[1]));
                }
                if (values.length <= 0) continue;
                int n = decimalCnt = column.getLengths().length > 1 ? column.getLengths()[1] : 0;
                if (values[0].length() + decimalCnt <= column.getLengths()[0]) continue;
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.value.length.exceed", column.getColumnTitle(), values[0].length() + decimalCnt, column.getLengths()[0]));
            }
            if (column.getColumnType() != DataModelType.ColumnType.INTEGER) continue;
            currValue = new BigDecimal(value.toString());
            values = currValue.toPlainString().split("\\.");
            int maxLen = Math.min(column.getLengths()[0], 10);
            if (values[0].length() > maxLen) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.value.length.exceed", column.getColumnTitle(), values[0].length(), maxLen));
            }
            BigDecimal maxValue = new BigDecimal(Integer.MAX_VALUE);
            if (currValue.compareTo(maxValue) <= 0) continue;
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.template.import.maximum.value.exceed", column.getColumnTitle(), currValue.toPlainString(), maxValue.toPlainString()));
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    public R loadExtendParam(OrgDTO param, OrgDataAction action) {
        if (this.paramInterceptors == null || this.paramInterceptors.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        if (param.containsKey((Object)LOAD_EXTEND_PARAM_OVER)) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        try {
            for (OrgDataParamInterceptor interceptor : this.paramInterceptors) {
                interceptor.modify(param, action);
            }
        }
        catch (Exception e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u62e6\u622a\u5f02\u5e38\uff1a" + param.getCategoryname(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    public R loadBatchOptExtendParam(OrgBatchOptDTO orgBatchOptDTO, OrgDataAction action) {
        Object list;
        OrgDTO param = orgBatchOptDTO.getQueryParam();
        if (param == null) {
            param = new OrgDTO();
        }
        if (param.getCategoryname() == null && (list = orgBatchOptDTO.getDataList()) != null && !list.isEmpty()) {
            param.setCategoryname(((OrgDO)list.get(0)).getCategoryname());
        }
        orgBatchOptDTO.setQueryParam(param);
        if (this.paramInterceptors == null || this.paramInterceptors.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        try {
            for (OrgDataParamInterceptor interceptor : this.paramInterceptors) {
                interceptor.batchModify(orgBatchOptDTO, action);
            }
        }
        catch (Exception e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u6279\u91cf\u62e6\u622a\u5f02\u5e38\uff1a" + param.getCategoryname(), e);
            return R.error((String)e.getMessage());
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }
}

