/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.organization.service.OrgDataService
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.datamapping.impl.utils.AbstractDataRefChcker;
import com.jiuqi.dc.datamapping.impl.utils.DataRefChkResult;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgDataRefChecker
extends AbstractDataRefChcker {
    @Autowired
    private DataRefConfigureDao dao;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private ServiceConfigProperties prop;
    @Autowired
    private IsolateRefDefineCacheProvider cacheProvider;

    @Override
    public DataRefChkResult check(BaseDataMappingDefineDTO define, DataRefSaveDTO dto) {
        List origDataList = dto.getData();
        DataRefChkResult result = new DataRefChkResult();
        HashMap<Integer, String> errorMessage = new HashMap<Integer, String>();
        ArrayList<DataRefDTO> createData = new ArrayList<DataRefDTO>(Math.max(origDataList.size() / 2, 20));
        ArrayList<DataRefDTO> modifyData = new ArrayList<DataRefDTO>(Math.max(origDataList.size() / 2, 20));
        ArrayList<DataRefDTO> deleteData = new ArrayList<DataRefDTO>(Math.max(origDataList.size() / 2, 20));
        ArrayList<DataRefDTO> savedData = new ArrayList<DataRefDTO>(Math.max(origDataList.size() / 2, 20));
        result.setErrorMessage(errorMessage);
        result.setCreateData(createData);
        result.setModifyData(modifyData);
        result.setDeleteData(deleteData);
        result.setSavedDataList(savedData);
        String CODE_LABEL = ("DC".equals(this.prop.getServiceName()) ? "\u4e00\u672c\u8d26" : "\u62a5\u8868\u5355\u4f4d") + "\u4ee3\u7801";
        HashSet<String> idSet = new HashSet<String>();
        HashSet<String> odsCodeSet = new HashSet<String>();
        HashSet<String> codeSet = new HashSet<String>();
        for (DataRefDTO refDto : origDataList) {
            if (StringUtils.isEmpty((String)refDto.getOdsCode())) {
                refDto.setIgnored(true);
                errorMessage.put(refDto.getIdx(), "\u6e90\u7cfb\u7edf\u4ee3\u7801\u4e3a\u7a7a");
                continue;
            }
            if (StringUtils.isEmpty((String)refDto.getOdsName())) {
                refDto.setIgnored(true);
                errorMessage.put(refDto.getIdx(), "\u6e90\u7cfb\u7edf\u540d\u79f0\u4e3a\u7a7a");
                continue;
            }
            if (StringUtils.isEmpty((String)refDto.getId()) && StringUtils.isEmpty((String)refDto.getCode())) {
                refDto.setIgnored(true);
                errorMessage.put(refDto.getIdx(), CODE_LABEL + "\u4e3a\u7a7a");
                continue;
            }
            if (!StringUtils.isEmpty((String)refDto.getId())) {
                if (!idSet.contains(refDto.getId())) {
                    idSet.add(refDto.getId());
                } else {
                    refDto.setIgnored(true);
                    errorMessage.put(refDto.getIdx(), String.format("\u5bfc\u5165\u6587\u4ef6\u5b58\u5728\u6e90\u7cfb\u7edf\u6570\u636e\u3010%1$s\u3011\u51b2\u7a81", refDto.getId()));
                    continue;
                }
            }
            if (odsCodeSet.contains(refDto.getOdsCode())) {
                refDto.setIgnored(true);
                errorMessage.put(refDto.getIdx(), String.format("\u5bfc\u5165\u6587\u4ef6\u5b58\u5728\u6e90\u7cfb\u7edf\u6570\u636e\u3010%1$s\u3011\u51b2\u7a81", refDto.getOdsCode()));
                continue;
            }
            odsCodeSet.add(refDto.getOdsCode());
            if (!StringUtils.isEmpty((String)refDto.getCode()) && codeSet.contains(refDto.getCode())) {
                refDto.setIgnored(true);
                errorMessage.put(refDto.getIdx(), String.format(CODE_LABEL + "\u3010%s\u3011\u4e0d\u5141\u8bb8\u91cd\u590d", refDto.getCode()));
                continue;
            }
            codeSet.add(refDto.getCode());
        }
        IsolationParamContext isolationParamContext = new IsolationParamContext();
        isolationParamContext.setSchemeCode(dto.getDataSchemeCode());
        List<DataRefDTO> refList = this.cacheProvider.getBaseMappingCache(isolationParamContext, define.getCode());
        List savedDataByOdsCodeList = refList.stream().filter(item -> odsCodeSet.contains(item.getOdsCode())).collect(Collectors.toList());
        result.getSavedDataList().addAll(savedDataByOdsCodeList);
        Set savedIdSet = savedDataByOdsCodeList.stream().map(DataRefDTO::getId).collect(Collectors.toSet());
        Map<String, DataRefDTO> savedByOdsCodeMap = savedDataByOdsCodeList.stream().collect(Collectors.toMap(DataRefDTO::getOdsCode, item -> item, (k1, k2) -> k2));
        Map<String, DataRefDTO> savedByCodeMap = this.dao.selectByCodeList(define.getCode(), new ArrayList<String>(codeSet)).stream().collect(Collectors.toMap(DataRefDTO::getCode, item -> item, (k1, k2) -> k2));
        Set<String> existsCodeSet = this.queryExistsData((DataMappingDefineDTO)define, codeSet);
        Map<String, String> schemeNameMap = this.schemeService.listAll().stream().collect(Collectors.toMap(DataSchemeDTO::getCode, DataSchemeDTO::getName, (k1, k2) -> k2));
        for (DataRefDTO refDto : origDataList) {
            if (Boolean.TRUE.equals(refDto.getIgnored())) continue;
            if (!StringUtils.isEmpty((String)refDto.getCode()) && !existsCodeSet.contains(refDto.getCode())) {
                errorMessage.put(refDto.getIdx(), String.format(CODE_LABEL + "\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", refDto.getCode()));
                continue;
            }
            if (StringUtils.isEmpty((String)refDto.getCode())) {
                if (!savedIdSet.contains(refDto.getId())) {
                    errorMessage.put(refDto.getIdx(), "\u6620\u5c04\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664");
                    continue;
                }
                refDto.setCode(savedByOdsCodeMap.get(refDto.getOdsCode()).getCode());
                deleteData.add(refDto);
                continue;
            }
            if (!StringUtils.isEmpty((String)refDto.getId())) {
                if (savedByCodeMap.get(refDto.getCode()) != null && !savedByCodeMap.get(refDto.getCode()).getId().equals(refDto.getId())) {
                    errorMessage.put(refDto.getIdx(), String.format("\u6620\u5c04\u6570\u636e%1$s\u3010%2$s\u3011\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%3$s\u3011\u4e0b\u5df2\u5b58\u5728\u8bb0\u5f55", CODE_LABEL, refDto.getCode(), this.formatSchemeName(schemeNameMap, savedByCodeMap.get(refDto.getCode()).getDataSchemeCode())));
                    continue;
                }
                if (!savedIdSet.contains(refDto.getId())) {
                    errorMessage.put(refDto.getIdx(), "\u6620\u5c04\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664");
                    continue;
                }
                modifyData.add(refDto);
                continue;
            }
            if (!StringUtils.isEmpty((String)refDto.getId())) continue;
            if (savedByCodeMap.containsKey(refDto.getCode())) {
                errorMessage.put(refDto.getIdx(), String.format("\u6620\u5c04\u6570\u636e%1$s\u3010%2$s\u3011\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%3$s\u3011\u4e0b\u5df2\u5b58\u5728\u8bb0\u5f55", CODE_LABEL, refDto.getCode(), this.formatSchemeName(schemeNameMap, savedByCodeMap.get(refDto.getCode()).getDataSchemeCode())));
                continue;
            }
            if (Objects.nonNull(dto.getCustomFlag()) && dto.getCustomFlag().booleanValue()) continue;
            if (savedByOdsCodeMap.containsKey(refDto.getOdsCode())) {
                errorMessage.put(refDto.getIdx(), String.format("\u6620\u5c04\u6570\u636e\u6e90\u7cfb\u7edf\u4ee3\u7801\u3010%1$s\u3011\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%2$s\u3011\u4e0b\u5df2\u5b58\u5728\u8bb0\u5f55", refDto.getOdsCode(), this.formatSchemeName(schemeNameMap, savedByOdsCodeMap.get(refDto.getOdsCode()).getDataSchemeCode())));
                continue;
            }
            createData.add(refDto);
        }
        return result;
    }

    public Set<String> queryExistsData(DataMappingDefineDTO define, Set<String> codeSet) {
        OrgDTO orgCondi = new OrgDTO();
        orgCondi.setCodeScope(new ArrayList<String>(codeSet));
        orgCondi.setAuthType(OrgDataOption.AuthType.NONE);
        return this.orgDataService.list(orgCondi).getRows().stream().map(OrgDO::getCode).collect(Collectors.toSet());
    }
}

