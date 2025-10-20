/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingUtil
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.springframework.data.util.Pair
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.FieldAdaptSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.intf.IBillExtractSchemeUnifiedHandler;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingDesService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchSettingUtil;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillFixedSettingDesServiceImpl
implements BillFixedSettingDesService,
IBillExtractSchemeUnifiedHandler {
    @Autowired
    private BillExtractSchemeService schemeService;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private BizModelClient bizModelClient;

    @Override
    public BillFixedSettingDTO getBillFiexedSetting(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        Assert.isNotEmpty((String)billSettingCondi.getBillType());
        Assert.isNotEmpty((String)billSettingCondi.getSchemeId());
        Assert.isNotEmpty((String)billSettingCondi.getBillTable());
        Assert.isNotEmpty((String)billSettingCondi.getDataField());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(billSettingCondi.getBillType());
        condi.setFetchSchemeId(billSettingCondi.getSchemeId());
        condi.setFormId(billSettingCondi.getBillTable());
        condi.setRegionId(billSettingCondi.getBillTable());
        condi.setDataLinkId(billSettingCondi.getDataField());
        return this.convert2Dto(this.fetchSettingDesDao.listFetchSettingDesByDataLinkId(condi));
    }

    @Override
    public List<BillFixedSettingDTO> getBillFiexedSettingsInTable(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        Assert.isNotEmpty((String)billSettingCondi.getBillType());
        Assert.isNotEmpty((String)billSettingCondi.getSchemeId());
        Assert.isNotEmpty((String)billSettingCondi.getBillTable());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(billSettingCondi.getBillType());
        condi.setFetchSchemeId(billSettingCondi.getSchemeId());
        condi.setFormId(billSettingCondi.getBillTable());
        condi.setRegionId(billSettingCondi.getBillTable());
        ArrayList<BillFixedSettingDTO> billFixedSettingDTOList = new ArrayList<BillFixedSettingDTO>();
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingDesByRegionId(condi);
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesEOS) {
            billFixedSettingDTOList.add(this.convert2Dto(fetchSettingDesEO));
        }
        return billFixedSettingDTOList;
    }

    @Override
    public List<FetchSettingDesEO> getFiexedSettingEOsInTable(BillSettingCondiDTO billSettingCondi) {
        Assert.isNotNull((Object)billSettingCondi);
        Assert.isNotEmpty((String)billSettingCondi.getBillType());
        Assert.isNotEmpty((String)billSettingCondi.getSchemeId());
        Assert.isNotEmpty((String)billSettingCondi.getBillTable());
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(billSettingCondi.getBillType());
        condi.setFetchSchemeId(billSettingCondi.getSchemeId());
        condi.setFormId(billSettingCondi.getBillTable());
        condi.setRegionId(billSettingCondi.getBillTable());
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingDesByRegionId(condi);
        return fetchSettingDesEOS;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String schemeId, BillExtractSettingDTO setting) {
        Map<String, BizModelAllPropsDTO> bizModelPropsMap;
        BillFetchSchemeDTO schemeDto = this.schemeService.getById(schemeId);
        Pair<List<FetchSettingDesEO>, List<List<Object>>> convertResult = this.convertSetting2Eo(schemeDto, bizModelPropsMap = ((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.listBizModelAllProps())).stream().collect(Collectors.toMap(BizModelAllPropsDTO::getCode, item -> item, (k1, k2) -> k2)), setting);
        if (!CollectionUtils.isEmpty((Collection)((Collection)convertResult.getSecond()))) {
            this.fetchSettingDesDao.deleteBatchFetchSettingDesData((List)convertResult.getSecond());
        }
        if (!CollectionUtils.isEmpty((Collection)((Collection)convertResult.getFirst()))) {
            this.fetchSettingDesDao.addBatch((List)convertResult.getFirst());
        }
    }

    @Override
    public int delete(BillFetchSchemeDTO schemeDto) {
        Assert.isNotNull((Object)schemeDto);
        Assert.isNotEmpty((String)schemeDto.getId());
        this.fetchSettingDesDao.deleteByFetchSchemeId(schemeDto.getId());
        return 0;
    }

    @Override
    public int syncCache(BillFetchSchemeDTO schemeDto) {
        return 0;
    }

    @Override
    public void copy(BillFetchSchemeDTO srcScheme, String targetId) {
        Assert.isNotNull((Object)srcScheme);
        Assert.isNotEmpty((String)srcScheme.getBillType());
        Assert.isNotEmpty((String)srcScheme.getId());
        Assert.isNotEmpty((String)targetId);
        FetchSettingCond condi = new FetchSettingCond();
        condi.setFormSchemeId(srcScheme.getBillType());
        condi.setFetchSchemeId(srcScheme.getId());
        List desSettingList = this.fetchSettingDesDao.listFetchSettingDesByFetchSchemeId(condi);
        if (CollectionUtils.isEmpty((Collection)desSettingList)) {
            return;
        }
        ArrayList<FetchSettingDesEO> fetchSettingData = new ArrayList<FetchSettingDesEO>();
        FetchSettingDesEO fetchSetting = null;
        for (FetchSettingDesEO fetchSettingDes : desSettingList) {
            fetchSetting = (FetchSettingDesEO)BeanConvertUtil.convert((Object)fetchSettingDes, FetchSettingDesEO.class, (String[])new String[0]);
            fetchSetting.setFetchSchemeId(targetId);
            fetchSetting.setId(UUIDUtils.newHalfGUIDStr());
            fetchSettingData.add(fetchSetting);
        }
        this.fetchSettingDesDao.addBatch(fetchSettingData);
        BillFetchSchemeDTO targetScheme = (BillFetchSchemeDTO)BeanConvertUtil.convert((Object)srcScheme, BillFetchSchemeDTO.class, (String[])new String[0]);
        targetScheme.setId(targetId);
        this.syncCache(targetScheme);
    }

    private Pair<List<FetchSettingDesEO>, List<List<Object>>> convertSetting2Eo(BillFetchSchemeDTO schemeDto, Map<String, BizModelAllPropsDTO> bizModelPropsMap, BillExtractSettingDTO setting) {
        ArrayList<FetchSettingDesEO> fetchSettingList = new ArrayList<FetchSettingDesEO>();
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        if (setting.getFixedSetting() == null || setting.getFixedSetting().isEmpty()) {
            if (setting.getItemTableSetting() == null || setting.getItemTableSetting().isEmpty()) {
                return Pair.of(fetchSettingList, deleteWhereValues);
            }
            Pair<List<FetchSettingDesEO>, List<List<Object>>> result = null;
            for (Map.Entry itemTableSetting : setting.getItemTableSetting().entrySet()) {
                result = this.convertSetting2Eo(schemeDto, bizModelPropsMap, (BillExtractSettingDTO)itemTableSetting.getValue());
                fetchSettingList.addAll((Collection)result.getFirst());
                deleteWhereValues.addAll((Collection)result.getSecond());
            }
            return Pair.of(fetchSettingList, deleteWhereValues);
        }
        for (Map.Entry fixedSetting : setting.getFixedSetting().entrySet()) {
            List<Object> deleteValue = Arrays.asList(schemeDto.getBillType(), schemeDto.getId(), setting.getBillTable(), setting.getBillTable(), fixedSetting.getKey());
            deleteWhereValues.add(deleteValue);
            boolean errorCheckFlag = false;
            FetchSettingDesEO fetchSettingEo = new FetchSettingDesEO();
            if (StringUtils.isEmpty((String)fetchSettingEo.getId())) {
                fetchSettingEo.setId(UUIDUtils.newHalfGUIDStr());
            }
            fetchSettingEo.setFormSchemeId(schemeDto.getBillType());
            fetchSettingEo.setFetchSchemeId(schemeDto.getId());
            fetchSettingEo.setFormId(setting.getBillTable());
            fetchSettingEo.setRegionId(setting.getBillTable());
            fetchSettingEo.setRegionType(((BillFixedSettingDTO)fixedSetting.getValue()).getRegionType());
            fetchSettingEo.setDataLinkId((String)fixedSetting.getKey());
            fetchSettingEo.setFieldDefineId((String)fixedSetting.getKey());
            List fixedSettingDataDTO = ((BillFixedSettingDTO)fixedSetting.getValue()).getFieldAdaptSettings();
            fetchSettingEo.setSortOrder(Integer.valueOf(0));
            if (CollectionUtils.isEmpty((Collection)fixedSettingDataDTO)) continue;
            LinkedList<FixedAdaptSettingVO> fixedSettingData = new LinkedList<FixedAdaptSettingVO>();
            for (FieldAdaptSettingDTO adaptSetting : ((BillFixedSettingDTO)fixedSetting.getValue()).getFieldAdaptSettings()) {
                FixedAdaptSettingVO adaptSettingVO = new FixedAdaptSettingVO();
                BeanUtils.copyProperties(adaptSetting, adaptSettingVO);
                Map bizModelFormulaDTO = adaptSetting.getBizModelFormula();
                LinkedHashMap bizModelFormula = new LinkedHashMap();
                for (String fetchSourceCode : bizModelFormulaDTO.keySet()) {
                    BizModelAllPropsDTO bizModelDTO = bizModelPropsMap.get(fetchSourceCode);
                    if (null == bizModelDTO) {
                        throw new BdeRuntimeException("\u4e1a\u52a1\u6a21\u578b\u4e0d\u5b58\u5728 fetchSourceCode=" + fetchSourceCode);
                    }
                    BizModelColumnDefineVO bizModelColumnDefineVO = (BizModelColumnDefineVO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.getColumnDefines(fetchSourceCode));
                    List columnDefines = bizModelColumnDefineVO.getColumnDefines();
                    List dimensions = FetchSettingUtil.getDimensions((BizModelAllPropsDTO)bizModelDTO);
                    List fetchSourceRowSettingMapS = (List)bizModelFormulaDTO.get(fetchSourceCode);
                    LinkedList<FixedFetchSourceRowSettingVO> fetchSourceRowSettingVOS = new LinkedList<FixedFetchSourceRowSettingVO>();
                    for (int i = 0; i < fetchSourceRowSettingMapS.size(); ++i) {
                        String rowObjStr;
                        FixedFetchSourceRowSettingVO fetchSourceRowSettingVO;
                        Map dimensionMap = (Map)fetchSourceRowSettingMapS.get(i);
                        this.trimBizModelFetchSettingInputColumn(dimensionMap, bizModelColumnDefineVO);
                        this.buildCustomfetchBizModelDim(dimensionMap);
                        Object rowObj = fetchSourceRowSettingMapS.get(i);
                        if (null == rowObj || null == (fetchSourceRowSettingVO = (FixedFetchSourceRowSettingVO)JSONUtil.parseObject((String)(rowObjStr = JSONUtil.toJSONString(rowObj)), FixedFetchSourceRowSettingVO.class))) continue;
                        LinkedHashMap<String, Object> columnValueGroupByCode = new LinkedHashMap<String, Object>(16);
                        for (ColumnDefineVO columnDefine : columnDefines) {
                            String code = columnDefine.getCode();
                            boolean require = columnDefine.isRequired();
                            Object dimensionObj = dimensionMap.get(code);
                            if (require && null == dimensionObj) {
                                errorCheckFlag = true;
                                continue;
                            }
                            if (null == dimensionObj) continue;
                            if (FetchFixedFieldEnum.DIMTYPE.getCode().equals(code)) {
                                if (dimensionObj instanceof ArrayNode) {
                                    fetchSourceRowSettingVO.setDimType(CollectionUtils.toString((List)JSONUtil.parseArray((String)JSONUtil.toJSONString(dimensionObj), String.class)));
                                } else {
                                    fetchSourceRowSettingVO.setDimType(dimensionObj.toString());
                                }
                            }
                            columnValueGroupByCode.put(code, dimensionObj.toString());
                        }
                        if (errorCheckFlag) continue;
                        List<Object> dimensionSettings = this.listDimensionSetting(columnValueGroupByCode, dimensions);
                        fetchSourceRowSettingVO.setBizModelCode(bizModelDTO.getComputationModelCode());
                        if (StringUtils.isEmpty((String)fetchSourceRowSettingVO.getId())) {
                            fetchSourceRowSettingVO.setId(UUIDUtils.newHalfGUIDStr());
                        }
                        fetchSourceRowSettingVO.setDimComb(dimensions);
                        if (!CollectionUtils.isEmpty(dimensionSettings)) {
                            fetchSourceRowSettingVO.setDimensionSetting(JSONUtil.toJSONString(dimensionSettings));
                        }
                        fetchSourceRowSettingVOS.add(fetchSourceRowSettingVO);
                    }
                    bizModelFormula.put(fetchSourceCode, fetchSourceRowSettingVOS);
                }
                adaptSettingVO.setBizModelFormula(bizModelFormula);
                fixedSettingData.add(adaptSettingVO);
            }
            fetchSettingEo.setFixedSettingData(JSONUtil.toJSONString(fixedSettingData));
            fetchSettingList.add(fetchSettingEo);
        }
        if (setting.getItemTableSetting() == null || setting.getItemTableSetting().isEmpty()) {
            return Pair.of(fetchSettingList, deleteWhereValues);
        }
        Iterator result = null;
        for (Map.Entry itemTableSetting : setting.getItemTableSetting().entrySet()) {
            result = this.convertSetting2Eo(schemeDto, bizModelPropsMap, (BillExtractSettingDTO)itemTableSetting.getValue());
            fetchSettingList.addAll((Collection)result.getFirst());
            deleteWhereValues.addAll((Collection)result.getSecond());
        }
        return Pair.of(fetchSettingList, deleteWhereValues);
    }

    private void buildCustomfetchBizModelDim(Map<String, Object> customFetchBizModelSet) {
        String bizModelCode = (String)customFetchBizModelSet.get("bizModelCode");
        if (StringUtils.isEmpty((String)bizModelCode) || !"CUSTOMFETCH".equals(bizModelCode)) {
            return;
        }
        String formula = (String)customFetchBizModelSet.get("formula");
        if (StringUtils.isEmpty((String)formula)) {
            return;
        }
        Map formulaMap = (Map)JsonUtils.readValue((String)formula, (TypeReference)new TypeReference<Map<String, String>>(){});
        for (String key : formulaMap.keySet()) {
            if (customFetchBizModelSet.containsKey(key)) continue;
            customFetchBizModelSet.put(key, formulaMap.get(key));
        }
    }

    private List<Object> listDimensionSetting(Map<String, Object> columnValueGroupByCode, List<String> dimensions) {
        ArrayList<Object> dimensionSetting = new ArrayList<Object>();
        if (CollectionUtils.isEmpty(dimensions)) {
            return dimensionSetting;
        }
        for (String code : dimensions) {
            LinkedHashMap<String, Object> dimension = new LinkedHashMap<String, Object>(16);
            dimension.put("dimCode", code);
            Object dimObj = columnValueGroupByCode.get(code);
            if (dimObj == null) continue;
            if (dimObj instanceof List) {
                if (CollectionUtils.isEmpty((Collection)((List)dimObj))) continue;
                dimension.put("dimValue", CollectionUtils.toString((List)((List)dimObj)));
            } else {
                if (StringUtils.isEmpty((String)dimObj.toString())) continue;
                dimension.put("dimValue", dimObj);
            }
            Object dimMatchRule = columnValueGroupByCode.get(code + "MatchRule");
            if (dimMatchRule instanceof List) {
                dimension.put("dimRule", CollectionUtils.toString((List)((List)dimMatchRule)));
            } else {
                dimension.put("dimRule", dimMatchRule);
            }
            dimensionSetting.add(dimension);
        }
        return dimensionSetting;
    }

    private void trimBizModelFetchSettingInputColumn(Map<String, Object> bizModelFetchSetting, BizModelColumnDefineVO bizModelColumnDefineVO) {
        List columnDefines = bizModelColumnDefineVO.getColumnDefines();
        List optionItems = bizModelColumnDefineVO.getOptionItems();
        if (CollectionUtils.isEmpty((Collection)columnDefines)) {
            return;
        }
        List inputColumn = columnDefines.stream().filter(column -> "INPUT".equals(column.getType())).collect(Collectors.toList());
        List columnList = inputColumn.stream().map(ColumnDefineVO::getCode).collect(Collectors.toList());
        for (String columnKey : columnList) {
            String value = (String)bizModelFetchSetting.get(columnKey);
            if (StringUtils.isEmpty((String)value)) continue;
            value = value.trim();
            bizModelFetchSetting.put(columnKey, value);
        }
        List optionItemCodes = optionItems.stream().map(SelectOptionVO::getCode).collect(Collectors.toList());
        for (String optionItemCode : optionItemCodes) {
            String value = (String)bizModelFetchSetting.get(optionItemCode);
            if (StringUtils.isEmpty((String)value)) continue;
            value = value.trim();
            bizModelFetchSetting.put(optionItemCode, value);
        }
    }

    public BillFixedSettingDTO convert2Dto(List<FetchSettingDesEO> fetchSettingDesList) {
        if (CollectionUtils.isEmpty(fetchSettingDesList)) {
            return null;
        }
        BillFixedSettingDTO fixedSetting = new BillFixedSettingDTO();
        fixedSetting.setBillType(fetchSettingDesList.get(0).getFormSchemeId());
        fixedSetting.setBillTable(fetchSettingDesList.get(0).getFormId());
        fixedSetting.setDataField(fetchSettingDesList.get(0).getDataLinkId());
        LinkedList<FieldAdaptSettingDTO> fixedSettingData = new LinkedList<FieldAdaptSettingDTO>();
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesList) {
            fixedSettingData.addAll(BillFixedSettingDesServiceImpl.readFixedSettingFromEo(fetchSettingDesEO));
        }
        fixedSetting.setFieldAdaptSettings(fixedSettingData);
        return fixedSetting;
    }

    private BillFixedSettingDTO convert2Dto(FetchSettingDesEO fetchSettingDes) {
        if (Objects.isNull(fetchSettingDes)) {
            return null;
        }
        BillFixedSettingDTO fixedSetting = new BillFixedSettingDTO();
        fixedSetting.setBillType(fetchSettingDes.getFormSchemeId());
        fixedSetting.setBillTable(fetchSettingDes.getFormId());
        fixedSetting.setDataField(fetchSettingDes.getDataLinkId());
        fixedSetting.setRegionType(fetchSettingDes.getRegionType());
        LinkedList<FieldAdaptSettingDTO> fixedSettingData = new LinkedList<FieldAdaptSettingDTO>();
        fixedSettingData.addAll(BillFixedSettingDesServiceImpl.readFixedSettingFromEo(fetchSettingDes));
        fixedSetting.setFieldAdaptSettings(fixedSettingData);
        return fixedSetting;
    }

    public static List<FieldAdaptSettingDTO> readFixedSettingFromEo(FetchSettingDesEO fetchSettingDesEO) {
        if (Objects.isNull(fetchSettingDesEO) || Objects.isNull(fetchSettingDesEO.getFixedSettingData())) {
            return new ArrayList<FieldAdaptSettingDTO>();
        }
        String fixedSettingDataStr = fetchSettingDesEO.getFixedSettingData();
        List fieldAdaptSettingVOS = (List)JsonUtils.readValue((String)fixedSettingDataStr, (TypeReference)new TypeReference<List<FieldAdaptSettingDTO>>(){});
        ArrayList<FieldAdaptSettingDTO> fieldAdaptSettingDTOS = new ArrayList<FieldAdaptSettingDTO>();
        for (FieldAdaptSettingDTO fieldAdaptSettingDTO : fieldAdaptSettingVOS) {
            fieldAdaptSettingDTOS.add(BillFixedSettingDesServiceImpl.convertFixedFieldDefineSettingDTOFormVO(fieldAdaptSettingDTO));
        }
        return fieldAdaptSettingDTOS;
    }

    private static FieldAdaptSettingDTO convertFixedFieldDefineSettingDTOFormVO(FieldAdaptSettingDTO fixedFieldDefineSettingVO) {
        if (Objects.isNull(fixedFieldDefineSettingVO)) {
            return fixedFieldDefineSettingVO;
        }
        Map bizModelFormulaDTO = fixedFieldDefineSettingVO.getBizModelFormula();
        for (String fetchSourceId : bizModelFormulaDTO.keySet()) {
            LinkedList<Map> fetchSourceRowSettingDTOList = new LinkedList<Map>();
            for (Map fetchSourceRowSettingVO : (List)bizModelFormulaDTO.get(fetchSourceId)) {
                Map fetchSourceRowSettingMap = fetchSourceRowSettingVO;
                String dimensionSetting = (String)fetchSourceRowSettingMap.get("dimensionSetting");
                if (!StringUtils.isEmpty((String)dimensionSetting)) {
                    List dimListMap = JSONUtil.parseMapArray((String)dimensionSetting, String.class, String.class);
                    for (Map dimMap : dimListMap) {
                        String dimCode = (String)dimMap.get("dimCode");
                        String dimValue = (String)dimMap.get("dimValue");
                        String dimRule = (String)dimMap.get("dimRule");
                        fetchSourceRowSettingMap.put(dimCode, dimValue);
                        fetchSourceRowSettingMap.put(dimCode + "MatchRule", dimRule);
                    }
                }
                fetchSourceRowSettingDTOList.add(fetchSourceRowSettingMap);
            }
            bizModelFormulaDTO.put(fetchSourceId, fetchSourceRowSettingDTOList);
        }
        return fixedFieldDefineSettingVO;
    }
}

