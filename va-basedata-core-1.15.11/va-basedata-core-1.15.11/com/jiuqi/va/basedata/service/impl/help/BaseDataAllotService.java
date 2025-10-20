/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataAllotDTO
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataModifyService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataAllotDTO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataAllotService")
public class BaseDataAllotService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataAllotService.class);
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private BaseDataQueryService baseDataQueryService;
    @Autowired
    private BaseDataModifyService baseDataModifyService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private Cache<String, String> caffeine;

    public R allot(BaseDataAllotDTO baseDataAllotDTO) {
        String key = baseDataAllotDTO.getKey();
        try {
            List orgList = baseDataAllotDTO.getOrgList();
            List baseDataList = baseDataAllotDTO.getDataList();
            String tenantName = baseDataAllotDTO.getTenantName();
            String tableName = baseDataAllotDTO.getDefineCode();
            BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
            defineParam.setTenantName(baseDataAllotDTO.getTenantName());
            defineParam.setName(tableName);
            defineParam.setDeepClone(Boolean.valueOf(false));
            BaseDataDefineDO define = this.baseDataDefineService.get(defineParam);
            if (define == null) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefine.not.found", tableName));
            }
            DataModelDTO dataModelDTO = new DataModelDTO();
            dataModelDTO.setName(tableName);
            dataModelDTO.setDeepClone(Boolean.valueOf(false));
            DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
            if (dataModel == null) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.modelDefinition.not.found", tableName));
            }
            Integer structtype = define.getStructtype();
            HashMap<String, String> relateFieldShareFieldMap = new HashMap<String, String>();
            List<DataModelColumn> relateColumns = this.getRelateField(tenantName, dataModel, relateFieldShareFieldMap);
            BaseDataDefineDO groupDefine = null;
            DataModelColumn groupColumn = null;
            if (structtype == 1) {
                List columns;
                if (dataModel.getColumns() != null && !dataModel.getColumns().isEmpty() && (columns = dataModel.getColumns().stream().filter(o -> o.getColumnName().equals(define.getGroupfieldname().toUpperCase())).collect(Collectors.toList())) != null && !columns.isEmpty()) {
                    groupColumn = (DataModelColumn)columns.get(0);
                }
                if (groupColumn != null) {
                    defineParam = new BaseDataDefineDTO();
                    defineParam.setTenantName(baseDataAllotDTO.getTenantName());
                    defineParam.setName(groupColumn.getMapping().split("\\.")[0]);
                    defineParam.setDeepClone(Boolean.valueOf(false));
                    groupDefine = this.baseDataDefineService.get(defineParam);
                }
                if (relateFieldShareFieldMap.containsKey(define.getGroupfieldname().toUpperCase())) {
                    relateFieldShareFieldMap.remove(define.getGroupfieldname().toUpperCase());
                }
                if (relateColumns != null && !relateColumns.isEmpty()) {
                    for (int i = relateColumns.size() - 1; i >= 0; --i) {
                        if (!relateColumns.get(i).getColumnName().equals(define.getGroupfieldname().toUpperCase())) continue;
                        relateColumns.remove(i);
                        break;
                    }
                }
            }
            ArrayList<Map<String, Object>> resultInfo = new ArrayList<Map<String, Object>>();
            for (String orgCode : orgList) {
                OrgDO targetOrg = this.getTargetOrg(tenantName, orgCode);
                for (BaseDataDO data : baseDataList) {
                    String dataCode = data.getCode();
                    String dataName = data.getName();
                    if (targetOrg == null) {
                        resultInfo.add(this.getResultMap(1, "\u672a\u627e\u5230\u7ec4\u7ec7\u673a\u6784" + orgCode, "null/" + dataName + "\uff08" + dataCode + "\uff09", dataCode));
                        continue;
                    }
                    BaseDataDO baseData = this.getCurrBaseData(tenantName, tableName, data);
                    if (baseData == null) {
                        resultInfo.add(this.getResultMap(1, "\u672a\u627e\u5230\u5f53\u524d\u6570\u636e" + dataCode, targetOrg.getName() + "/null\uff08" + dataCode + "\uff09", dataCode));
                        continue;
                    }
                    if (!StringUtils.hasText(baseData.getUnitcode()) || baseData.getUnitcode().equals("-")) {
                        resultInfo.add(this.getResultMap(1, "\u5f53\u524d\u57fa\u7840\u6570\u636e\u975e\u9694\u79bb\u6570\u636e", targetOrg.getName() + "/" + dataName + "\uff08" + dataCode + "\uff09", dataCode));
                        continue;
                    }
                    baseData.setUnitcode(targetOrg.getCode());
                    baseData.setObjectcode(null);
                    baseData.setId(null);
                    this.handleRelateData(tenantName, relateColumns, targetOrg, baseData, relateFieldShareFieldMap);
                    R r = null;
                    if (structtype == 0) {
                        r = this.allotListData(tenantName, tableName, targetOrg, baseData);
                    } else if (structtype == 1) {
                        r = this.allotGroupData(tenantName, tableName, targetOrg, baseData, groupColumn, groupDefine);
                    } else if (structtype == 2 || structtype == 3) {
                        r = this.allotTreeData(tenantName, tableName, targetOrg, baseData);
                    }
                    if (r == null) continue;
                    resultInfo.add(this.getResultMap(r.getCode(), r.getMsg(), targetOrg.getName() + "/" + dataName + "\uff08" + dataCode + "\uff09", dataCode));
                }
            }
            this.setCacheResult(key, 0, BaseDataCoreI18nUtil.getOptSuccessMsg(), resultInfo);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.setCacheResult(key, 1, e.getMessage(), null);
        }
        return R.ok();
    }

    private OrgDO getTargetOrg(String tenantName, String orgCode) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setTenantName(tenantName);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setCode(orgCode);
        PageVO orgs = this.orgDataClient.list(orgDTO);
        if (orgs.getTotal() > 0) {
            return (OrgDO)orgs.getRows().get(0);
        }
        return null;
    }

    private BaseDataDO getCurrBaseData(String tenantName, String tableName, BaseDataDO baseData) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTenantName(tenantName);
        baseDataDTO.setTableName(tableName);
        baseDataDTO.putAll((Map)baseData);
        baseDataDTO.setRecoveryflag(Integer.valueOf(-1));
        baseDataDTO.setStopflag(Integer.valueOf(-1));
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        R rs = this.baseDataQueryService.exist(baseDataDTO);
        if (rs.getCode() == 0 && ((Boolean)rs.get((Object)"exist")).booleanValue()) {
            return new BaseDataDO((Map)((BaseDataDO)rs.get((Object)"data")));
        }
        return null;
    }

    private List<DataModelColumn> getRelateField(String tenantName, DataModelDO dataModel, Map<String, String> relateFieldShareFieldMap) {
        List columns = dataModel.getColumns();
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        ArrayList<DataModelColumn> relateColumns = new ArrayList<DataModelColumn>();
        for (DataModelColumn column : columns) {
            if (!StringUtils.hasText(column.getMapping()) || column.getMappingType() == null || column.getMappingType() != 1) continue;
            String refTablename = column.getMapping().split("\\.")[0];
            BaseDataDefineDTO defineParam = new BaseDataDefineDTO();
            defineParam.setTenantName(tenantName);
            defineParam.setName(refTablename);
            defineParam.setDeepClone(Boolean.valueOf(false));
            BaseDataDefineDO define = this.baseDataDefineService.get(defineParam);
            if (define == null || define.getSharetype() == 0) continue;
            relateColumns.add(column);
            relateFieldShareFieldMap.put(column.getColumnName(), define.getSharefieldname());
        }
        return relateColumns;
    }

    private void handleRelateData(String tenantName, List<DataModelColumn> relateColumns, OrgDO targetOrg, BaseDataDO baseData, Map<String, String> relateFieldShareFieldMap) {
        if (relateColumns == null || relateColumns.isEmpty()) {
            return;
        }
        for (DataModelColumn column : relateColumns) {
            Object value = baseData.get((Object)column.getColumnName().toLowerCase());
            if (value == null || !StringUtils.hasText(value.toString())) continue;
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setTenantName(tenantName);
            baseDataDTO.setTableName(column.getMapping().split("\\.")[0]);
            baseDataDTO.setStopflag(Integer.valueOf(-1));
            baseDataDTO.setObjectcode(value.toString());
            R rs = this.baseDataQueryService.exist(baseDataDTO);
            if (rs.getCode() == 0 && ((Boolean)rs.get((Object)"exist")).booleanValue()) {
                BaseDataDO data = (BaseDataDO)rs.get((Object)"data");
                baseDataDTO.setObjectcode(null);
                baseDataDTO.setUnitcode(targetOrg.getCode());
                baseDataDTO.setCode(data.getCode());
                String shareFieldStr = relateFieldShareFieldMap.get(column.getColumnName());
                if (StringUtils.hasText(shareFieldStr)) {
                    String[] shareFields;
                    for (String shareField : shareFields = shareFieldStr.split("\\,")) {
                        if ("UNITCODE".equals(shareField)) continue;
                        baseDataDTO.put(shareField.toLowerCase(), data.get((Object)shareField.toLowerCase()));
                    }
                }
                if ((rs = this.baseDataQueryService.exist(baseDataDTO)).getCode() == 0 && ((Boolean)rs.get((Object)"exist")).booleanValue()) {
                    data = (BaseDataDO)rs.get((Object)"data");
                    baseData.put(column.getColumnName().toLowerCase(), (Object)data.getObjectcode());
                    continue;
                }
                baseData.put(column.getColumnName().toLowerCase(), null);
                continue;
            }
            baseData.put(column.getColumnName().toLowerCase(), null);
        }
    }

    private R allotListData(String tenantName, String tableName, OrgDO targetOrg, BaseDataDO baseData) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.putAll((Map)baseData);
        baseDataDTO.setTenantName(tenantName);
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setUnitcode(targetOrg.getCode());
        return this.baseDataModifyService.add(baseDataDTO);
    }

    private R allotGroupData(String tenantName, String tableName, OrgDO targetOrg, BaseDataDO baseData, DataModelColumn groupFieldColumn, BaseDataDefineDO groupDefine) {
        String groupFieldName = groupFieldColumn.getColumnName().toLowerCase();
        if (baseData.get((Object)groupFieldName) != null && StringUtils.hasText(baseData.get((Object)groupFieldName).toString())) {
            BaseDataDO data;
            Object value = baseData.get((Object)groupFieldName);
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setTenantName(tenantName);
            baseDataDTO.setTableName(groupFieldColumn.getMapping().split("\\.")[0]);
            baseDataDTO.setStopflag(Integer.valueOf(-1));
            String code = value.toString();
            baseDataDTO.setObjectcode(value.toString());
            R rs = this.baseDataQueryService.exist(baseDataDTO);
            if (rs.getCode() == 0 && ((Boolean)rs.get((Object)"exist")).booleanValue()) {
                data = (BaseDataDO)rs.get((Object)"data");
                baseDataDTO.setObjectcode(null);
                baseDataDTO.setUnitcode(targetOrg.getCode());
                baseDataDTO.setCode(data.getCode());
                String shareFieldStr = groupDefine.getSharefieldname();
                if (StringUtils.hasText(shareFieldStr)) {
                    String[] shareFields;
                    for (String shareField : shareFields = shareFieldStr.split("\\,")) {
                        if ("UNITCODE".equals(shareField)) continue;
                        baseDataDTO.put(shareField.toLowerCase(), data.get((Object)shareField.toLowerCase()));
                    }
                }
                code = data.getCode();
                rs = this.baseDataQueryService.exist(baseDataDTO);
            }
            if (rs.getCode() != 0 || !((Boolean)rs.get((Object)"exist")).booleanValue()) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.allot.check.org.group", code));
            }
            data = (BaseDataDO)rs.get((Object)"data");
            baseData.put(groupFieldColumn.getColumnName().toLowerCase(), (Object)data.getObjectcode());
        }
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.putAll((Map)baseData);
        baseDataDTO.setTenantName(tenantName);
        baseDataDTO.setTableName(tableName);
        return this.baseDataModifyService.add(baseDataDTO);
    }

    private R allotTreeData(String tenantName, String tableName, OrgDO targetOrg, BaseDataDO baseData) {
        BaseDataDTO baseDataDTO;
        String parentCode = baseData.getParentcode();
        if (StringUtils.hasText(parentCode) && !parentCode.equals("-")) {
            baseDataDTO = new BaseDataDTO();
            baseDataDTO.setTenantName(tenantName);
            baseDataDTO.setTableName(tableName);
            baseDataDTO.setStopflag(Integer.valueOf(-1));
            baseDataDTO.setCode(parentCode);
            baseDataDTO.setUnitcode(targetOrg.getCode());
            R rs = this.baseDataQueryService.exist(baseDataDTO);
            if (rs.getCode() != 0 || !((Boolean)rs.get((Object)"exist")).booleanValue()) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.allot.parentcode.check.not.found", baseData.getCode(), parentCode));
            }
        }
        baseDataDTO = new BaseDataDTO();
        baseDataDTO.putAll((Map)baseData);
        baseDataDTO.setTenantName(tenantName);
        baseDataDTO.setTableName(tableName);
        return this.baseDataModifyService.add(baseDataDTO);
    }

    private Map<String, Object> getResultMap(int status, String msg, String path, String code) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("status", status);
        result.put("msg", msg);
        result.put("path", path);
        result.put("code", code);
        return result;
    }

    private void setCacheResult(String key, int status, String msg, List<Map<String, Object>> resultInfo) {
        HashMap<String, Object> jsonObj = new HashMap<String, Object>();
        jsonObj.put("status", status);
        jsonObj.put("msg", msg);
        jsonObj.put("result", resultInfo);
        if (EnvConfig.getRedisEnable()) {
            this.stringRedisTemplate.opsForValue().set((Object)key, (Object)JSONUtil.toJSONString(jsonObj), 60L, TimeUnit.SECONDS);
        } else {
            if (this.caffeine == null) {
                this.caffeine = Caffeine.newBuilder().initialCapacity(1).maximumSize(1L).expireAfterWrite(60L, TimeUnit.SECONDS).build();
            }
            this.caffeine.put((Object)key, (Object)JSONUtil.toJSONString(jsonObj));
        }
    }

    public R getAllotResult(BaseDataAllotDTO baseDataAllotDTO) {
        String key = baseDataAllotDTO.getKey();
        if (!StringUtils.hasText(key)) {
            return R.error((String)BaseDataCoreI18nUtil.getParamMissingMsg());
        }
        String resString = "";
        if (EnvConfig.getRedisEnable()) {
            resString = (String)this.stringRedisTemplate.opsForValue().get((Object)key);
            if (!StringUtils.hasText(resString)) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.allot.results.not.found", new Object[0]));
            }
            this.stringRedisTemplate.delete((Object)key);
        } else {
            if (this.caffeine != null) {
                resString = (String)this.caffeine.getIfPresent((Object)key);
            }
            if (!StringUtils.hasText(resString)) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddata.allot.results.not.found", new Object[0]));
            }
            if (this.caffeine != null) {
                this.caffeine.invalidate((Object)key);
            }
        }
        return R.ok().put("result", (Object)JSONUtil.parseObject((String)resString));
    }
}

