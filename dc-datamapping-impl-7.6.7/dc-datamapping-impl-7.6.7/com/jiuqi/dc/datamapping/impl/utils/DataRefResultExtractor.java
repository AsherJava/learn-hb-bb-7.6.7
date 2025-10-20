/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DataRefResultExtractor
implements ResultSetExtractor<List<DataRefDTO>> {
    private BaseDataMappingDefineDTO define;
    private boolean convertDcField;
    private int count;
    private List<String> schemeCodeList;
    private Set<String> isolationList;

    public DataRefResultExtractor(BaseDataMappingDefineDTO define, boolean convertDcField) {
        this.define = define;
        this.convertDcField = convertDcField;
    }

    public DataRefResultExtractor(BaseDataMappingDefineDTO define, boolean convertDcField, int count) {
        this.define = define;
        this.convertDcField = convertDcField;
        this.count = count;
    }

    public DataRefResultExtractor(BaseDataMappingDefineDTO define, boolean convertDcField, List<String> schemeCodeList, Set<String> isolationList) {
        this.define = define;
        this.convertDcField = convertDcField;
        this.schemeCodeList = schemeCodeList;
        this.isolationList = isolationList;
    }

    public List<DataRefDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        OrgDTO param;
        ArrayList<DataRefDTO> result = new ArrayList<DataRefDTO>(this.count);
        HashSet unitCodeList = CollectionUtils.newHashSet();
        HashSet baseDataCodeList = CollectionUtils.newHashSet();
        Set<String> dynamicFieldList = IsolationUtil.listDynamicField(this.define, this.isolationList);
        boolean orgIsolationFlag = dynamicFieldList.contains("DC_UNITCODE");
        boolean orgMappingFlag = "MD_ORG".equals(this.define.getCode());
        Map<Object, Object> orgMappingMap = CollectionUtils.newHashMap();
        if (!this.convertDcField && orgIsolationFlag) {
            IsolateRefDefineCacheProvider cacheProvider = (IsolateRefDefineCacheProvider)ApplicationContextRegister.getBean(IsolateRefDefineCacheProvider.class);
            Object orgCacheList = CollectionUtils.newArrayList();
            if (!CollectionUtils.isEmpty(this.schemeCodeList)) {
                for (String schemeCode : this.schemeCodeList) {
                    orgCacheList.addAll(cacheProvider.getBaseMappingCache(new IsolationParamContext(schemeCode), "MD_ORG"));
                }
            } else {
                orgCacheList = cacheProvider.getBaseMappingCache(new IsolationParamContext(this.define.getDataSchemeCode()), "MD_ORG");
            }
            if (!CollectionUtils.isEmpty((Collection)orgCacheList)) {
                orgMappingMap = orgCacheList.stream().collect(Collectors.toMap(DataRefDTO::getOdsCode, DataRefDTO::getCode, (k1, k2) -> k2));
            }
        }
        while (rs.next()) {
            DataRefDTO dataRefDTO = new DataRefDTO();
            dataRefDTO.setDataSchemeCode(this.define.getDataSchemeCode() != null ? this.define.getDataSchemeCode() : rs.getString("DATASCHEMECODE"));
            dataRefDTO.setOdsCode(rs.getString("ODS_CODE"));
            dataRefDTO.setOdsName(rs.getString("ODS_NAME"));
            dataRefDTO.setTableName(this.define.getCode());
            for (String field : dynamicFieldList) {
                String value = rs.getString(field);
                dataRefDTO.put((Object)field, (Object)value);
                if (!"DC_UNITCODE".equals(field)) continue;
                if (!this.convertDcField) {
                    String unitcode = (String)orgMappingMap.get(value);
                    if (!StringUtils.isEmpty((String)unitcode)) {
                        unitCodeList.add(unitcode);
                    }
                    dataRefDTO.put((Object)field, (Object)unitcode);
                    continue;
                }
                if (StringUtils.isEmpty((String)value)) continue;
                unitCodeList.add(value);
            }
            if (this.convertDcField) {
                dataRefDTO.put((Object)"ID", (Object)rs.getString("ID"));
                dataRefDTO.put((Object)"CODE", (Object)rs.getString("CODE"));
                if ("MD_ORG".equals(this.define.getCode())) {
                    unitCodeList.add(rs.getString("CODE"));
                } else {
                    String baseDataCode = rs.getString("CODE");
                    if (!StringUtils.isEmpty((String)baseDataCode)) {
                        baseDataCodeList.add(baseDataCode);
                    }
                }
                dataRefDTO.put((Object)"HANDLESTATUS", (Object)rs.getString("HANDLESTATUS"));
                dataRefDTO.put((Object)"OPERATOR", (Object)rs.getString("OPERATOR"));
                dataRefDTO.put((Object)"OPERATETIME", (Object)rs.getTimestamp("OPERATETIME"));
                dataRefDTO.put((Object)"AUTOMATCHFLAG", (Object)rs.getInt("AUTOMATCHFLAG"));
            }
            result.add(dataRefDTO);
        }
        Map<Object, Object> orgMap = CollectionUtils.newHashMap();
        Map<Object, Object> baseDataMap = CollectionUtils.newHashMap();
        if (orgMappingFlag || orgIsolationFlag) {
            OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
            param = new OrgDTO();
            param.setAuthType(OrgDataOption.AuthType.NONE);
            param.setCategoryname("MD_ORG");
            param.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            param.setOrgCodes(new ArrayList(unitCodeList));
            List orgDOList = orgDataClient.list(param).getRows();
            orgMap = orgDOList.stream().collect(Collectors.toMap(OrgDO::getCode, OrgDO::getName, (k1, k2) -> k2));
        }
        if (this.convertDcField && !orgMappingFlag) {
            BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
            param = new BaseDataDTO();
            param.setTableName(this.define.getRelTableName());
            param.setBaseDataCodes(new ArrayList(baseDataCodeList));
            baseDataMap = baseDataClient.list((BaseDataDTO)param).getRows().stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO::getName, (k1, k2) -> k2));
        }
        for (DataRefDTO dto : result) {
            if (orgIsolationFlag) {
                dto.put((Object)"DC_UNITNAME", orgMap.get(dto.getValueStr("DC_UNITCODE")));
            }
            if (!this.convertDcField) continue;
            if (orgMappingFlag) {
                dto.put((Object)"NAME", orgMap.get(dto.getValueStr("CODE")));
                continue;
            }
            dto.put((Object)"NAME", baseDataMap.get(dto.getValueStr("CODE")));
        }
        return result;
    }
}

