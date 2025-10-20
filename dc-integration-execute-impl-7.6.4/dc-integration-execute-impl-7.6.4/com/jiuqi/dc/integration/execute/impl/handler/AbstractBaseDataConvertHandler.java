/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.Pair
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.apache.commons.collections4.MapUtils
 *  org.apache.commons.lang3.ObjectUtils
 */
package com.jiuqi.dc.integration.execute.impl.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.Pair;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil;
import com.jiuqi.dc.integration.execute.impl.dao.BaseDataConvertDao;
import com.jiuqi.dc.integration.execute.impl.dao.OdsMdOrgDao;
import com.jiuqi.dc.integration.execute.impl.intf.IBaseDataConvertHandler;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.enums.SchemeBaseDataRefType;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBaseDataConvertHandler
implements IBaseDataConvertHandler {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataConvertDao dao;
    @Autowired
    private OdsMdOrgDao odsMdOrgDao;

    protected String requiredShowColumnsCheck(BaseDataMappingDefineDTO define) {
        StringJoiner result = new StringJoiner(",");
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setDeepClone(Boolean.FALSE);
        Map<String, String> tableNameNap = AssistDimUtil.listPublished().stream().filter(e -> !StringUtils.isEmpty((String)e.getDictTableName())).collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getDictTableName, (k1, k2) -> k1));
        param.setName(tableNameNap.getOrDefault(define.getCode(), define.getCode()));
        Set keySet = define.getItems().stream().map(FieldMappingDefineDTO::getFieldName).collect(Collectors.toSet());
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(param);
        Map params = (Map)JsonUtils.readValue((String)baseDataDefineDO.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        for (Map column : (List)params.getOrDefault("showFields", params.get("defaultShowFields"))) {
            if (!MapUtils.getBoolean((Map)column, (Object)"required", (Boolean)Boolean.FALSE).booleanValue() || keySet.contains(MapUtils.getString((Map)column, (Object)"columnName"))) continue;
            result.add(MapUtils.getString((Map)column, (Object)"columnTitle"));
        }
        return result.toString();
    }

    public Map<String, BaseDataDO> queryExistBaseData(String tableName, List<String> codes) {
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(tableName);
        condi.setPagination(Boolean.valueOf(false));
        condi.setBaseDataCodes(codes);
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        List baseDatas = this.baseDataClient.list(condi).getRows();
        if (CollectionUtils.isEmpty((Collection)baseDatas)) {
            return CollectionUtils.newHashMap();
        }
        return baseDatas.stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> item, (k1, k2) -> k2));
    }

    protected Pair<Boolean, String> preconditionCheck(BaseDataMappingDefineDTO define) {
        StringJoiner result = new StringJoiner(",");
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setDeepClone(Boolean.FALSE);
        Map<String, String> tableNameNap = AssistDimUtil.listPublished().stream().filter(e -> !StringUtils.isEmpty((String)e.getDictTableName())).collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getDictTableName, (k1, k2) -> k1));
        param.setName(tableNameNap.getOrDefault(define.getCode(), define.getCode()));
        Set keySet = define.getItems().stream().map(FieldMappingDefineDTO::getFieldName).collect(Collectors.toSet());
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(param);
        Map params = (Map)JsonUtils.readValue((String)baseDataDefineDO.getDefine(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        Boolean noDsCode = true;
        Boolean noIsCode = true;
        for (Map column : (List)params.getOrDefault("showFields", params.get("defaultShowFields"))) {
            Object columnName = column.get("columnName");
            if (columnName.equals("DATASCHEMECODE")) {
                noDsCode = false;
                continue;
            }
            if (columnName.equals("ISOLATIONCODE")) {
                noIsCode = false;
                continue;
            }
            if (!MapUtils.getBoolean((Map)column, (Object)"required", (Boolean)Boolean.FALSE).booleanValue() || keySet.contains(MapUtils.getString((Map)column, (Object)"columnName"))) continue;
            result.add(MapUtils.getString((Map)column, (Object)"columnTitle"));
        }
        List mainDim = SchemeBaseDataRefType.getSchemeBaseDataRefList();
        StringBuilder errorInfo = new StringBuilder();
        if (!StringUtils.isEmpty((String)result.toString())) {
            errorInfo.append(mainDim.contains(define.getCode()) ? String.format("\u8bf7\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u7684\u201c%1$s\u201c\u6620\u5c04\u5b9a\u4e49\u4e2d\u914d\u7f6e\u201c%2$s\u201d\u7684\u6e90\u8868\u5b57\u6bb5 \n", define.getName(), result.toString()) : String.format("\u8bf7\u5728\u201c%1$s\u201d\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e2d\u914d\u7f6e\u201c%2$s\u201d\u5b57\u6bb5\u7684\u6e90\u8868\u5b57\u6bb5 \n", define.getName(), result.toString()));
        }
        if (noDsCode.booleanValue()) {
            errorInfo.append(String.format("\u8bf7\u5728\u201c%1$s\u201d\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e2d\u914d\u7f6e\u201cDATASCHEMECODE\u201d\u5b57\u6bb5\u7684\u6e90\u8868\u5b57\u6bb5 \n", define.getName()));
        }
        if (noIsCode.booleanValue()) {
            errorInfo.append(String.format("\u8bf7\u5728\u201c%1$s\u201d\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u4e2d\u914d\u7f6e\u201cISOLATIONCODE\u201d\u5b57\u6bb5\u7684\u6e90\u8868\u5b57\u6bb5 \n", define.getName()));
        }
        if (errorInfo.toString().length() > 0) {
            return new Pair((Object)true, (Object)errorInfo.toString());
        }
        return new Pair((Object)false, null);
    }

    protected List<Map<String, Object>> getIsolationCodes(DataSchemeDTO dataScheme, BaseDataMappingDefineDTO define, String defaultStorageType) {
        if (IsolationStrategy.SHARE.getCode().equals(define.getIsolationStrategy())) {
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            result.add(new HashMap());
            return result;
        }
        if (IsolationStrategy.ISOLATION_SHARE.getCode().equals(define.getIsolationStrategy())) {
            List<Map<String, Object>> orgList = this.dao.selectOrgData(dataScheme, define);
            HashMap<String, Map<String, Object>> orgMap = new HashMap<String, Map<String, Object>>();
            for (Map<String, Object> org : orgList) {
                orgMap.put(org.get("CODE").toString(), org);
            }
            for (Map<String, Object> org : orgList) {
                StringBuilder path = new StringBuilder();
                AbstractBaseDataConvertHandler.buildPathRecursive(org, path, orgMap);
                org.put("PARENTS", path.toString());
            }
            this.saveOdsOrgData(orgList, dataScheme.getCode());
            List<Map<String, Object>> sortedList = orgList.stream().sorted(Comparator.comparing(m -> (String)m.get("PARENTS"))).map(m -> {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("UNITCODE", m.getOrDefault("CODE", ""));
                return map;
            }).collect(Collectors.toList());
            return sortedList;
        }
        return this.dao.selectIsolationCodes(dataScheme, define, defaultStorageType);
    }

    private static void buildPathRecursive(Map<String, Object> org, StringBuilder path, Map<String, Map<String, Object>> orgMap) {
        if (org == null || org.get("PARENTCODE") == null || !orgMap.containsKey(org.get("PARENTCODE"))) {
            path.insert(0, org.get("CODE"));
            return;
        }
        Map<String, Object> parent = orgMap.get(org.get("PARENTCODE"));
        AbstractBaseDataConvertHandler.buildPathRecursive(parent, path, orgMap);
        path.append("/").append(org.get("CODE"));
    }

    private void saveOdsOrgData(List<Map<String, Object>> odsOrgList, String dataSchemeCode) {
        Map<String, Map<String, Object>> saveOrgMap = this.odsMdOrgDao.selectOdsMdOrg(dataSchemeCode);
        ArrayList<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
        block0: for (Map<String, Object> odsOrgMap : odsOrgList) {
            if (ObjectUtils.isEmpty(saveOrgMap.get(odsOrgMap.get("CODE")))) {
                odsOrgMap.put("DATASCHEMECODE", dataSchemeCode);
                addList.add(odsOrgMap);
                continue;
            }
            Map<String, Object> saveDataMap = saveOrgMap.get(odsOrgMap.get("CODE"));
            Set<String> keySet = saveDataMap.keySet();
            for (String key : keySet) {
                if (Objects.equals(odsOrgMap.get(key), saveDataMap.get(key))) continue;
                odsOrgMap.put("DATASCHEMECODE", dataSchemeCode);
                odsOrgMap.put("ID", saveDataMap.get("ID"));
                updateList.add(odsOrgMap);
                continue block0;
            }
        }
        if (addList.size() > 0) {
            this.odsMdOrgDao.insertOdsMdOrg(addList);
        }
        if (updateList.size() > 0) {
            this.odsMdOrgDao.updateOdsMdOrg(updateList);
        }
    }
}

