/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 */
package com.jiuqi.dc.datamapping.impl.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.datamapping.impl.utils.AbstractDataRefChcker;
import com.jiuqi.dc.datamapping.impl.utils.DataRefChkResult;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
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
public class BaseDataDataRefChecker
extends AbstractDataRefChcker {
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private IsolateRefDefineCacheProvider isolateRefDefineCacheProvider;
    @Autowired
    private DataRefConfigureDao dataRefConfigureDao;

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
        HashSet<String> idSet = new HashSet<String>();
        HashMap<IsolationParamContext, HashSet> odsCodeMap = new HashMap<IsolationParamContext, HashSet>();
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
                errorMessage.put(refDto.getIdx(), "\u4e00\u672c\u8d26\u4ee3\u7801\u4e3a\u7a7a");
                continue;
            }
            if (!StringUtils.isEmpty((String)refDto.getCode())) {
                codeSet.add(refDto.getCode());
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
            refDto.setDataSchemeCode(dto.getDataSchemeCode());
            IsolationParamContext isolationParam = IsolationUtil.buildIsolationParam(refDto, define.getIsolationStrategy());
            if (!odsCodeMap.containsKey(isolationParam)) {
                odsCodeMap.put(isolationParam, CollectionUtils.newHashSet());
            }
            if (!((Set)odsCodeMap.get(isolationParam)).contains(refDto.getOdsCode())) {
                ((Set)odsCodeMap.get(isolationParam)).add(refDto.getOdsCode());
                continue;
            }
            refDto.setIgnored(true);
            errorMessage.put(refDto.getIdx(), String.format("\u5bfc\u5165\u6587\u4ef6\u5b58\u5728\u6e90\u7cfb\u7edf\u6570\u636e\u3010%1$s\u3011\u51b2\u7a81", refDto.getOdsCode()));
        }
        boolean checkUnitFlag = false;
        List<Object> refUnitList = CollectionUtils.newArrayList();
        if (IsolationStrategy.getIsolationFieldByCode((String)define.getIsolationStrategy()).contains("DC_UNITCODE")) {
            checkUnitFlag = true;
            List<DataRefDTO> unitCache = this.isolateRefDefineCacheProvider.getBaseMappingCache(new IsolationParamContext(dto.getDataSchemeCode()), "MD_ORG");
            if (!CollectionUtils.isEmpty(unitCache)) {
                refUnitList = unitCache.stream().map(DataRefDTO::getCode).collect(Collectors.toList());
            }
        }
        Map<String, String> schemeNameMap = this.schemeService.listAll().stream().collect(Collectors.toMap(DataSchemeDTO::getCode, DataSchemeDTO::getName, (k1, k2) -> k2));
        Set<String> existsCodeSet = this.queryExistsData((DataMappingDefineDTO)define, codeSet);
        HashMap<IsolationParamContext, Map<String, DataRefDTO>> refDataMap = new HashMap<IsolationParamContext, Map<String, DataRefDTO>>();
        HashMap savedIdMap = new HashMap();
        for (DataRefDTO refDto : origDataList) {
            if (Boolean.TRUE.equals(refDto.getIgnored())) continue;
            if (!StringUtils.isEmpty((String)refDto.getCode()) && !existsCodeSet.contains(refDto.getCode())) {
                errorMessage.put(refDto.getIdx(), String.format("\u4ee3\u7801\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", refDto.getCode()));
                continue;
            }
            if (checkUnitFlag) {
                String dcUnitCode = refDto.getValueStr("DC_UNITCODE");
                if (StringUtils.isEmpty((String)dcUnitCode)) {
                    errorMessage.put(refDto.getIdx(), "\u7ec4\u7ec7\u673a\u6784\u672a\u6620\u5c04");
                    continue;
                }
                if (!refUnitList.contains(dcUnitCode)) {
                    errorMessage.put(refDto.getIdx(), String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5728\u65b9\u6848\u3010%2$s\u3011\u4e0b\u672a\u6620\u5c04", dcUnitCode, dto.getDataSchemeCode()));
                    continue;
                }
            }
            refDto.setDataSchemeCode(dto.getDataSchemeCode());
            IsolationParamContext isolationParam = IsolationUtil.buildIsolationParam(refDto, define.getIsolationStrategy());
            if (!refDataMap.containsKey(isolationParam)) {
                List<DataRefDTO> saveDataList = this.isolateRefDefineCacheProvider.getBaseMappingCache(isolationParam, define.getCode());
                List<DataRefDTO> pendingList = this.dataRefConfigureDao.selectPendingData(define.getCode(), dto.getDataSchemeCode(), isolationParam);
                if (!CollectionUtils.isEmpty(pendingList)) {
                    saveDataList.addAll(pendingList);
                }
                refDataMap.put(isolationParam, saveDataList.stream().collect(Collectors.toMap(DataRefDTO::getOdsCode, item -> item, (o1, o2) -> o2)));
                savedIdMap.put(isolationParam, saveDataList.stream().map(DataRefDTO::getId).collect(Collectors.toSet()));
                result.getSavedDataList().addAll(saveDataList);
            }
            Set savedIdSet = (Set)savedIdMap.get(isolationParam);
            if (StringUtils.isEmpty((String)refDto.getCode())) {
                if (!savedIdSet.contains(refDto.getId())) {
                    errorMessage.put(refDto.getIdx(), "\u6620\u5c04\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664");
                    continue;
                }
                refDto.setCode(((DataRefDTO)((Map)refDataMap.get(isolationParam)).get(refDto.getOdsCode())).getCode());
                deleteData.add(refDto);
                continue;
            }
            if (!StringUtils.isEmpty((String)refDto.getId())) {
                if (!savedIdSet.contains(refDto.getId())) {
                    errorMessage.put(refDto.getIdx(), "\u6620\u5c04\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664");
                    continue;
                }
                modifyData.add(refDto);
                continue;
            }
            if (!StringUtils.isEmpty((String)refDto.getId())) continue;
            refDto.setDataSchemeCode(define.getDataSchemeCode());
            if (Objects.nonNull(dto.getCustomFlag()) && dto.getCustomFlag().booleanValue()) continue;
            if (((Map)refDataMap.get(isolationParam)).containsKey(refDto.getOdsCode())) {
                errorMessage.put(refDto.getIdx(), String.format("\u6620\u5c04\u6570\u636e\u6e90\u7cfb\u7edf\u4ee3\u7801\u3010%1$s\u3011\u6e90\u7cfb\u7edf\u540d\u79f0\u3010%2$s\u3011\u5728\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%3$s\u3011\u4e0b\u5df2\u5b58\u5728\u8bb0\u5f55", refDto.getOdsCode(), refDto.getOdsName(), this.formatSchemeName(schemeNameMap, define.getDataSchemeCode())));
                continue;
            }
            createData.add(refDto);
        }
        return result;
    }

    public Set<String> queryExistsData(DataMappingDefineDTO define, Set<String> codeSet) {
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(define.getRelTableName());
        condi.setCodeScope(new ArrayList<String>(codeSet));
        return this.baseDataService.list(condi).getRows().stream().map(BaseDataDO::getCode).collect(Collectors.toSet());
    }

    private String getRefUniqueKey(DataRefDTO ref) {
        return ref.getDataSchemeCode() + "|" + ref.getOdsId();
    }
}

