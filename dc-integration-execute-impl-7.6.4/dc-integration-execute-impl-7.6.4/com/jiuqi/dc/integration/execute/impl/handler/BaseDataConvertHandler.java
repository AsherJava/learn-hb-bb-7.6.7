/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.Pair
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.datamapping.client.dto.IsolationParamContext
 *  com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO
 *  com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao
 *  com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus
 *  com.jiuqi.dc.datamapping.impl.gather.impl.AutoMatchServiceGather
 *  com.jiuqi.dc.datamapping.impl.service.AutoMatchService
 *  com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService
 *  com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider
 *  com.jiuqi.dc.mappingscheme.client.common.DataRefUtil
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.dc.integration.execute.impl.handler;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.Pair;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.impl.assistdim.util.AssistDimUtil;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.client.dto.IsolationParamContext;
import com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus;
import com.jiuqi.dc.datamapping.impl.gather.impl.AutoMatchServiceGather;
import com.jiuqi.dc.datamapping.impl.service.AutoMatchService;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.integration.execute.impl.dao.BaseDataConvertDao;
import com.jiuqi.dc.integration.execute.impl.handler.AbstractBaseDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.utils.BaseDataConvertUtil;
import com.jiuqi.dc.integration.execute.impl.utils.UnitRefConvertUtil;
import com.jiuqi.dc.mappingscheme.client.common.DataRefUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class BaseDataConvertHandler
extends AbstractBaseDataConvertHandler {
    @Autowired
    private BaseDataConvertDao dao;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataRefConfigureService dataRefConfigureService;
    @Autowired
    private AutoMatchServiceGather autoMatchServiceGather;
    @Autowired
    private IsolateRefDefineCacheProvider isolateRefDefineCacheProvider;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    @Autowired
    private DataRefConfigureDao dataRefConfigureDao;

    @Override
    public String getCode() {
        return "DEFAULT";
    }

    @Override
    public String doConvert(String dataSchemeCode, BaseDataMappingDefineDTO define) {
        DataSchemeDTO dataSchemeDTO = this.dataSchemeService.getByCode(define.getDataSchemeCode());
        Map<String, String> tableNameMap = AssistDimUtil.listPublished().stream().filter(e -> !StringUtils.isEmpty((String)e.getDictTableName())).collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getDictTableName, (k1, k2) -> k1));
        if (RuleType.ALL.getCode().equals(define.getRuleType())) {
            DataRefAutoMatchDTO dto = new DataRefAutoMatchDTO();
            dto.setTableName(tableNameMap.getOrDefault(define.getCode(), define.getCode()));
            dto.setDataSchemeCode(define.getDataSchemeCode());
            DataRefAutoMatchVO dataRefAutoMatchVO = this.dataRefConfigureService.autoMatch(dto);
            StringBuilder log = new StringBuilder(String.format("\u3010%1$s\u3011\u6620\u5c04\u89c4\u5219\u4e3a\u9010\u6761\u914d\u7f6e", define.getCode()));
            boolean saveFlag = false;
            Map countMap = dataRefAutoMatchVO.getCountMap();
            for (Map.Entry entry : countMap.entrySet()) {
                int refNum = (Integer)entry.getValue();
                AutoMatchService autoMatchService = this.autoMatchServiceGather.getServiceByCode((String)entry.getKey());
                log.append(String.format("\uff0c\u6309\u3010%1$s\u3011\u89c4\u5219\u81ea\u52a8\u6620\u5c04\u3010%2$d\u3011\u6761", autoMatchService.getName(), refNum));
                if (refNum == 0) continue;
                saveFlag = true;
            }
            if (Boolean.TRUE.equals(saveFlag)) {
                this.dataRefConfigureService.save(dataRefAutoMatchVO.getTempData());
            }
            return log.toString();
        }
        Pair<Boolean, String> checkInfo = this.preconditionCheck(define);
        if (((Boolean)checkInfo.getFirst()).booleanValue()) {
            return (String)checkInfo.getSecond();
        }
        int count = this.dao.countUnConvertData(define, dataSchemeDTO.getDataSourceCode());
        if (count < 1) {
            return "\u6ca1\u6709\u8981\u8f6c\u6362\u7684\u6570\u636e";
        }
        IPluginType pluginType = this.pluginTypeGather.getPluginType(define.getPluginType());
        String defaultStorageType = pluginType.storageType();
        List<Map<String, Object>> isolationMaps = this.getIsolationCodes(dataSchemeDTO, define, defaultStorageType);
        Integer addDataCount = 0;
        Integer updateDataCount = 0;
        Integer conflictDataCount = 0;
        Map<String, DataRefDTO> refUnitDataMap = UnitRefConvertUtil.getOdsUnitCodeRefMap(dataSchemeCode);
        for (Map<String, Object> isolationMap : isolationMaps) {
            BaseDataBatchOptDTO batchUpdateDto;
            if (IsolationStrategy.getIsolationFieldByCode((String)define.getIsolationStrategy()).contains("DC_UNITCODE") && ObjectUtils.isEmpty(refUnitDataMap.get(isolationMap.get("UNITCODE"))) && (!IsolationStrategy.SHARE_ISOLATION.getCode().equals(define.getIsolationStrategy()) || !"-".equals(isolationMap.get("UNITCODE")))) continue;
            Map<String, Map<String, Object>> baseDataByIsolationMap = this.dao.selectConvertDataByIsolate(dataSchemeDTO, define, isolationMap, defaultStorageType);
            IsolationParamContext IsolationParamContext2 = new IsolationParamContext(dataSchemeDTO.getCode(), refUnitDataMap.getOrDefault(isolationMap.get("UNITCODE"), new DataRefDTO()).getCode(), MapUtils.getString(isolationMap, (Object)BaseDataConvertUtil.getCodeWithOutODS("ODS_BOOKCODE")), MapUtils.getInteger(isolationMap, (Object)BaseDataConvertUtil.getCodeWithOutODS("ODS_BOOKCODE")));
            Map cache = this.isolateRefDefineCacheProvider.getCache(IsolationParamContext2, define.getCode());
            Set<String> odsCodes = baseDataByIsolationMap.keySet();
            Set mappingCodes = cache.keySet();
            List refConflictCodes = this.dataRefConfigureDao.selectConflictData(DataRefUtil.getRefTableName((String)define.getRelTableName()), dataSchemeCode, IsolationParamContext2);
            List<String> unMappingCode = odsCodes.stream().filter(code -> !mappingCodes.contains(code) && !refConflictCodes.contains(code) && !StringUtils.isEmpty((String)code)).collect(Collectors.toList());
            Map<String, BaseDataDO> existBaseDataMap = this.queryExistBaseData(tableNameMap.getOrDefault(define.getCode(), define.getCode()), unMappingCode);
            ArrayList<Map<String, Object>> addData = new ArrayList<Map<String, Object>>();
            ArrayList<Map<String, Object>> updateData = new ArrayList<Map<String, Object>>();
            ArrayList<Map<String, Object>> conflictData = new ArrayList<Map<String, Object>>();
            block2: for (String odesCode : unMappingCode) {
                Map<String, Object> odsBaseData = baseDataByIsolationMap.get(odesCode);
                StringBuffer isolationCodeString = new StringBuffer("");
                if (!ObjectUtils.isEmpty(isolationMap)) {
                    isolationMap.forEach((key, value) -> isolationCodeString.append(value + "|"));
                    isolationCodeString.delete(isolationCodeString.length() - 1, isolationCodeString.length());
                }
                String string = isolationCodeString.toString();
                BaseDataDO ybzBaseDataDO = existBaseDataMap.get(odsBaseData.get("code"));
                if (ObjectUtils.isEmpty(ybzBaseDataDO)) {
                    odsBaseData.put("DATASCHEMECODE".toLowerCase(), dataSchemeCode);
                    odsBaseData.put("ISOLATIONCODE".toLowerCase(), string);
                    addData.add(odsBaseData);
                    continue;
                }
                Boolean sourceComparison = true;
                sourceComparison = IsolationStrategy.SHARE.getCode().equals(define.getIsolationStrategy()) ? Boolean.valueOf(Objects.equals(ybzBaseDataDO.get((Object)"DATASCHEMECODE".toLowerCase()), dataSchemeDTO.getCode())) : Boolean.valueOf(Objects.equals(ybzBaseDataDO.get((Object)"DATASCHEMECODE".toLowerCase()), dataSchemeDTO.getCode()) && Objects.equals(ybzBaseDataDO.get((Object)"ISOLATIONCODE".toLowerCase()), string));
                if (!sourceComparison.booleanValue()) {
                    conflictData.add(odsBaseData);
                    continue;
                }
                List fieldNameList = define.getItems().stream().filter(e -> !e.getFieldName().equals("ID") && !e.getFieldName().equals("UNITCODE")).map(e -> e.getFieldName().toLowerCase()).collect(Collectors.toList());
                for (String item : fieldNameList) {
                    if (Objects.equals(odsBaseData.get(item), ybzBaseDataDO.get((Object)item))) continue;
                    odsBaseData.put("id", ybzBaseDataDO.getId());
                    updateData.add(odsBaseData);
                    continue block2;
                }
            }
            BaseDataBatchOptDTO batchAddDto = this.getBaseTableInsertData(addData, tableNameMap, define, refUnitDataMap);
            if (batchAddDto.getDataList().size() > 0) {
                String odesCode;
                odesCode = this.baseDataClient.sync(batchAddDto);
            }
            if ((batchUpdateDto = this.getBaseTableInsertData(updateData, tableNameMap, define, refUnitDataMap)).getDataList().size() > 0) {
                R r = this.baseDataClient.sync(batchUpdateDto);
                r.getMsg();
            }
            if (conflictData.size() > 0) {
                ArrayList<DataRefDTO> refDTOList = new ArrayList<DataRefDTO>();
                for (Map map : conflictData) {
                    DataRefDTO refDTO = new DataRefDTO();
                    refDTO.setId(UUIDUtils.newUUIDStr());
                    refDTO.setOdsCode(map.getOrDefault("code", ""));
                    refDTO.setOdsName(map.getOrDefault("name", ""));
                    if (!ObjectUtils.isEmpty(refUnitDataMap.get(isolationMap.get("UNITCODE")))) {
                        refDTO.put((Object)"DC_UNITCODE", (Object)refUnitDataMap.get(isolationMap.get("UNITCODE")).getCode());
                    }
                    refDTO.put((Object)"ODS_BOOKCODE", (Object)map.getOrDefault("bookcode", "").toString());
                    refDTO.put((Object)"ODS_ACCTYEAR", (Object)map.getOrDefault("acctyear", "").toString());
                    refDTO.setDataSchemeCode(dataSchemeCode);
                    refDTO.setHandleStatus(RefHandleStatus.PENDING.getCode());
                    refDTO.setOperator(NpContextHolder.getContext().getUserName());
                    refDTO.setOperateTime(DateUtils.now());
                    refDTOList.add(refDTO);
                }
                this.dataRefConfigureDao.batchInsert(define, refDTOList);
            }
            addDataCount = addDataCount + addData.size();
            updateDataCount = updateDataCount + updateData.size();
            conflictDataCount = conflictDataCount + conflictData.size();
        }
        return String.format("\u6570\u636e\u65b9\u6848\u3010%1$s\u3011\u57fa\u7840\u6570\u636e\u3010%2$s\u3011\u64cd\u4f5c\u5b8c\u6210\u3002\u65b0\u589e\u6570\u636e\uff1a%3$d, \u4fee\u6539\u6570\u636e\uff1a%4$d, \u51b2\u7a81\u6570\u636e\uff1a%5$d", dataSchemeDTO.getName(), define.getName(), addDataCount, updateDataCount, conflictDataCount);
    }

    public BaseDataBatchOptDTO getBaseTableInsertData(List<Map<String, Object>> odsUnConvertDataList, Map<String, String> tableNameMap, BaseDataMappingDefineDTO define, Map<String, DataRefDTO> refUnitDataMap) {
        BaseDataBatchOptDTO batchAddDto = new BaseDataBatchOptDTO();
        batchAddDto.setTenantName("__default_tenant__");
        batchAddDto.setDataList(new ArrayList());
        BaseDataDTO baseData = null;
        for (Map<String, Object> odsUnConvertData : odsUnConvertDataList) {
            baseData = new BaseDataDTO();
            baseData.setTableName(tableNameMap.getOrDefault(define.getCode(), define.getCode()));
            baseData.setShortname(StringUtils.isEmpty((String)baseData.getShortname()) ? baseData.getCode() : baseData.getShortname());
            baseData.putAll(odsUnConvertData);
            if (baseData.containsKey((Object)"UNITCODE".toLowerCase())) {
                baseData.put("UNITCODE".toLowerCase(), (Object)Optional.ofNullable(refUnitDataMap.get(String.valueOf(baseData.get((Object)"UNITCODE".toLowerCase())))).map(DataRefDTO::getCode).orElse("-"));
            }
            batchAddDto.getDataList().add(baseData);
        }
        return batchAddDto;
    }
}

