/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.bde.bizmodel.client.BizModelClient
 *  com.jiuqi.bde.bizmodel.client.FetchDimensionClient
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.DataRegionTypeEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataParam
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.common.vo.FetchSettingVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.MatchRuleEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.bde.bizmodel.client.BizModelClient;
import com.jiuqi.bde.bizmodel.client.FetchDimensionClient;
import com.jiuqi.bde.bizmodel.client.dto.BizModelAllPropsDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.vo.BizModelColumnDefineVO;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.DataRegionTypeEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.FixedFieldDefineSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataParam;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.common.vo.FetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingListLinkCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingSaveDataVO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dao.FetchSettingDesDao;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.MatchRuleEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesBaseDataService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.BizTypeValidator;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class FetchSettingDesServiceImpl
implements FetchSettingDesService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FetchSettingDesDao fetchSettingDesDao;
    @Autowired
    private BizModelClient bizModelClient;
    @Autowired
    private FetchDimensionClient dimensionRestRequest;
    @Autowired
    private FetchSettingDesBaseDataService fetchSettingDesBaseDataService;
    private static final String SUBJECT_DIM_CODE = "subjectCode";
    private static final String EXCLUDESUBJECTCODE = "excludeSubjectCode";
    private static final String CASH_ITEM_DIM_CODE = "cashCode";

    @Override
    public String queryFormTitle(String formId) {
        if (StringUtils.isEmpty((String)formId)) {
            return "";
        }
        FormDefine formDefine = this.runTimeViewController.queryFormById(formId);
        if (ObjectUtils.isEmpty(formDefine)) {
            return "";
        }
        return formDefine.getTitle();
    }

    @Override
    public FixedFieldDefineSettingDTO listDataLinkFixedSettingDesRowRecords(FetchSettingCond fetchSettingCond) {
        List fetchSettings = this.fetchSettingDesDao.listFetchSettingDesByDataLinkId(fetchSettingCond);
        FixedFieldDefineSettingVO fixedFieldDefineSettingVO = this.convertFixedDefineSettingByFetchSettingDesEOList(fetchSettings);
        return this.convertFixedFieldDefineSettingDTOFormVO(fixedFieldDefineSettingVO);
    }

    private FixedFieldDefineSettingDTO convertFixedFieldDefineSettingDTOFormVO(FixedFieldDefineSettingVO fixedFieldDefineSettingVO) {
        Map dimBaseDataTableMap = (Map)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.getBaseDataInputConfig());
        FixedFieldDefineSettingDTO fixedFieldDefineSettingDTO = new FixedFieldDefineSettingDTO();
        if (Objects.isNull(fixedFieldDefineSettingVO)) {
            return null;
        }
        BeanUtils.copyProperties(fixedFieldDefineSettingVO, fixedFieldDefineSettingDTO);
        LinkedList<FixedAdaptSettingDTO> fixedSettingData = new LinkedList<FixedAdaptSettingDTO>();
        for (FixedAdaptSettingVO adaptSettingVO : fixedFieldDefineSettingVO.getFixedSettingData()) {
            FixedAdaptSettingDTO adaptSettingDTO = new FixedAdaptSettingDTO();
            BeanUtils.copyProperties(adaptSettingVO, adaptSettingDTO);
            Map bizModelFormula = adaptSettingVO.getBizModelFormula();
            LinkedHashMap bizModelFormulaDTO = new LinkedHashMap();
            for (Map.Entry fetchSourceIdSettingListEntry : bizModelFormula.entrySet()) {
                String fetchSourceId = (String)fetchSourceIdSettingListEntry.getKey();
                LinkedList<Map> fetchSourceRowSettingDTOList = new LinkedList<Map>();
                for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)fetchSourceIdSettingListEntry.getValue()) {
                    Map<String, List<BaseDataVO>> baseDataInputConfigMap;
                    String fetchSourceRowSettingStr = JSONUtil.toJSONString((Object)fetchSourceRowSettingVO);
                    Map fetchSourceRowSettingMap = JSONUtil.parseMap((String)fetchSourceRowSettingStr);
                    String dimensionSetting = fetchSourceRowSettingVO.getDimensionSetting();
                    if (!StringUtils.isEmpty((String)dimensionSetting)) {
                        List dimListMap = JSONUtil.parseMapArray((String)dimensionSetting, String.class, String.class);
                        for (Map map : dimListMap) {
                            String dimCode = (String)map.get("dimCode");
                            String dimValue = (String)map.get("dimValue");
                            String dimRule = (String)map.get("dimRule");
                            fetchSourceRowSettingMap.put(dimCode, dimValue);
                            fetchSourceRowSettingMap.put(dimCode + "MatchRule", dimRule);
                        }
                    }
                    if (!(baseDataInputConfigMap = this.buildBaseDataConfigByFixedFetchSourceRowSettingVO(fetchSourceRowSettingVO, dimBaseDataTableMap)).isEmpty()) {
                        for (Map.Entry entry : baseDataInputConfigMap.entrySet()) {
                            fetchSourceRowSettingMap.put((String)entry.getKey() + "@basedata", entry.getValue());
                            fetchSourceRowSettingMap.put((String)entry.getKey() + "baseDataTitle", this.buildBaseDataTitle((List)entry.getValue()));
                        }
                    }
                    fetchSourceRowSettingDTOList.add(fetchSourceRowSettingMap);
                }
                bizModelFormulaDTO.put(fetchSourceId, fetchSourceRowSettingDTOList);
            }
            adaptSettingDTO.setBizModelFormula(bizModelFormulaDTO);
            fixedSettingData.add(adaptSettingDTO);
        }
        fixedFieldDefineSettingDTO.setFixedSettingData(fixedSettingData);
        return fixedFieldDefineSettingDTO;
    }

    private String buildBaseDataTitle(List<BaseDataVO> baseDataList) {
        if (CollectionUtils.isEmpty(baseDataList)) {
            return "";
        }
        StringBuilder title = new StringBuilder();
        for (BaseDataVO baseDataVO : baseDataList) {
            if (Objects.isNull(baseDataVO)) continue;
            if (title.length() > 0) {
                title.append(",");
            }
            title.append(baseDataVO.getCode()).append(" ").append(baseDataVO.getTitle());
        }
        return title.toString();
    }

    @Override
    public List<FixedFieldDefineSettingDTO> listDataLinkFixedSettingDes(FetchSettingListLinkCond fetchSettingListLinkCond) {
        ArrayList listLinkFixSettingDes = CollectionUtils.newArrayList();
        List fetchSettingDesEOS = this.fetchSettingDesDao.listDataLinkFixedSettingDes(fetchSettingListLinkCond);
        List<FixedFieldDefineSettingVO> settingVOList = this.getFixedDefineSettingListByEOList(fetchSettingDesEOS);
        for (FixedFieldDefineSettingVO fixedFieldDefineSettingVO : settingVOList) {
            listLinkFixSettingDes.add(this.convertFixedFieldDefineSettingDTOFormVO(fixedFieldDefineSettingVO));
        }
        return listLinkFixSettingDes;
    }

    @Override
    public List<FixedFieldDefineSettingVO> listFixedFieldDefineSettingDesByFormId(FetchSettingCond fetchSettingCond) {
        List fetchSettings = this.fetchSettingDesDao.listFetchSettingDesByCondi(fetchSettingCond);
        return this.listFixedDefineSettingByFetchSettingDesEOList(fetchSettings);
    }

    private List<FixedFieldDefineSettingVO> listFixedDefineSettingByFetchSettingDesEOList(List<FetchSettingDesEO> fetchSettingDesList) {
        if (CollectionUtils.isEmpty(fetchSettingDesList)) {
            return new ArrayList<FixedFieldDefineSettingVO>();
        }
        LinkedList<FixedFieldDefineSettingVO> fixedSettingDataList = new LinkedList<FixedFieldDefineSettingVO>();
        Map<String, List<FetchSettingDesEO>> RegionSettingDesMap = fetchSettingDesList.stream().collect(Collectors.groupingBy(FetchSettingDesEO::getRegionId));
        for (List<FetchSettingDesEO> RegionSettingDesList : RegionSettingDesMap.values()) {
            Map<String, List<FetchSettingDesEO>> fetchSettingDesDataLinkMap = RegionSettingDesList.stream().collect(Collectors.groupingBy(FetchSettingDesEO::getDataLinkId));
            for (List<FetchSettingDesEO> DataLinkSettingDesList : fetchSettingDesDataLinkMap.values()) {
                FixedFieldDefineSettingVO fieldDefineSettingVO = this.convertFixedDefineSettingByFetchSettingDesEOList(DataLinkSettingDesList);
                fixedSettingDataList.add(fieldDefineSettingVO);
            }
        }
        return fixedSettingDataList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String saveFetchFixedSettingDataHandle(FetchSettingSaveDataVO fetchSettingSaveData) {
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        StringBuffer messageInfo = new StringBuffer();
        List<FetchSettingDesEO> fetchSettingDesDatas = this.listConvertFetchSettingSaveData2DesEO(fetchSettingSaveData, deleteWhereValues, messageInfo);
        if (messageInfo.length() <= 0) {
            if (!CollectionUtils.isEmpty(deleteWhereValues)) {
                this.fetchSettingDesDao.deleteBatchFetchSettingDesData(deleteWhereValues);
            }
            if (!CollectionUtils.isEmpty(fetchSettingDesDatas)) {
                this.fetchSettingDesDao.addBatch(fetchSettingDesDatas);
            }
        }
        return messageInfo.toString();
    }

    @Override
    public Map<String, List<FetchSettingVO>> getFetchSettingDesGroupByDataLinkId(FetchSettingCond fetchSettingCond) {
        LinkedHashMap<String, List<FetchSettingVO>> fetchSettingDesGroupByDataLink = new LinkedHashMap<String, List<FetchSettingVO>>();
        List fetchSettingDesData = this.fetchSettingDesDao.listFixedFetchSettingDesByFormId(fetchSettingCond);
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesData) {
            List fetchSettingDesGroup = fetchSettingDesGroupByDataLink.computeIfAbsent(fetchSettingDesEO.getDataLinkId(), key -> new ArrayList());
            fetchSettingDesGroup.add(FetchSettingNrUtil.convertFetchSettingDesEOToVo(fetchSettingDesEO));
        }
        return fetchSettingDesGroupByDataLink;
    }

    @Override
    public Map<String, List<FetchSettingVO>> getFetchFloatSettingDesGroupByDataLinkId(FetchSettingCond fetchSettingCond) {
        LinkedHashMap<String, List<FetchSettingVO>> fetchSettingDesGroupByDataLink = new LinkedHashMap<String, List<FetchSettingVO>>();
        List fetchSettingDesData = this.fetchSettingDesDao.listFixedFetchFloatSettingDesByFormId(fetchSettingCond);
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesData) {
            List fetchSettingDesGroup = fetchSettingDesGroupByDataLink.computeIfAbsent(fetchSettingDesEO.getDataLinkId(), key -> new ArrayList());
            fetchSettingDesGroup.add(FetchSettingNrUtil.convertFetchSettingDesEOToVo(fetchSettingDesEO));
        }
        return fetchSettingDesGroupByDataLink;
    }

    @Override
    public Map<String, Object> listDataLinkByRegionId(String regionId) {
        List dataLinkDefines = this.runTimeViewController.getAllLinksInRegion(regionId);
        if (CollectionUtils.isEmpty((Collection)dataLinkDefines)) {
            return Collections.emptyMap();
        }
        HashMap<String, Object> dataLinks = new HashMap<String, Object>(16);
        for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
            HashMap<String, Integer> dataLinkPos = new HashMap<String, Integer>(16);
            dataLinkPos.put("posX", dataLinkDefine.getPosX());
            dataLinkPos.put("posY", dataLinkDefine.getPosY());
            dataLinks.put(dataLinkDefine.getKey(), dataLinkPos);
        }
        return dataLinks;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveBatchFetchFixedSettingDes(List<FetchSettingDesEO> fetchSettingDesData) {
        if (CollectionUtils.isEmpty(fetchSettingDesData)) {
            return;
        }
        ArrayList deleteWhereValues = new ArrayList();
        fetchSettingDesData.stream().forEach(fetchSettingDes -> deleteWhereValues.add(Arrays.asList(fetchSettingDes.getFormSchemeId(), fetchSettingDes.getFetchSchemeId(), fetchSettingDes.getFormId(), fetchSettingDes.getRegionId(), fetchSettingDes.getDataLinkId())));
        this.fetchSettingDesDao.deleteBatchFetchSettingDesData(deleteWhereValues);
        this.fetchSettingDesDao.addBatch(fetchSettingDesData);
    }

    @Override
    public void deleteBatchFetchFloatSettingDes(FetchSettingCond fetchSettingCond) {
        ArrayList<List<Object>> deleteWhereValues = new ArrayList<List<Object>>();
        deleteWhereValues.add(Arrays.asList(fetchSettingCond.getFormSchemeId(), fetchSettingCond.getFetchSchemeId(), fetchSettingCond.getFormId(), fetchSettingCond.getRegionId()));
        this.fetchSettingDesDao.deleteFloatFetchSettingDesData(deleteWhereValues);
    }

    private List<FetchSettingDesEO> listConvertFetchSettingSaveData2DesEO(FetchSettingSaveDataVO fetchSettingSaveData, List<List<Object>> deleteWhereValues, StringBuffer messageInfo) {
        FetchSchemeNrService fetchSchemeService = (FetchSchemeNrService)ApplicationContextRegister.getBean(FetchSchemeNrService.class);
        FetchSchemeVO fetchScheme = fetchSchemeService.getFetchScheme(fetchSettingSaveData.getFetchSchemeId());
        String bizType = fetchScheme.getBizType();
        LinkedHashMap fixedSettingDataMap = fetchSettingSaveData.getFixedSettingDatas();
        Map<String, BizModelAllPropsDTO> bizModelDTOMap = ((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.listBizModelAllProps())).stream().collect(Collectors.toMap(BizModelAllPropsDTO::getCode, item -> item, (k1, k2) -> k2));
        ArrayList<FetchSettingDesEO> fetchSettingDesDatas = new ArrayList<FetchSettingDesEO>();
        for (Map.Entry regionSettingEntry : fixedSettingDataMap.entrySet()) {
            DataRegionTypeEnum regionType;
            String regionId = (String)regionSettingEntry.getKey();
            DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(regionId);
            if (Objects.nonNull(dataRegionDefine)) {
                regionType = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)dataRegionDefine.getRegionKind()) ? DataRegionTypeEnum.FIXED : DataRegionTypeEnum.FLOAT;
            } else {
                BizTypeValidator.isValidBud((String)bizType, (String)("\u533a\u57df\u5bf9\u8c61\u4e0d\u5b58\u5728 regionId=" + regionId));
                regionType = DataRegionTypeEnum.FIXED;
            }
            Map dataLinkSettingMap = (Map)regionSettingEntry.getValue();
            for (Map.Entry dataLinkSettingEntry : dataLinkSettingMap.entrySet()) {
                String fieldDefineId;
                String dataLinkId = (String)dataLinkSettingEntry.getKey();
                DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkId);
                StringBuilder stringBufferMassage = new StringBuilder();
                if (Objects.nonNull(dataLinkDefine)) {
                    fieldDefineId = dataLinkDefine.getLinkExpression();
                    stringBufferMassage.append("\u5750\u6807\uff1a[").append(dataLinkDefine.getPosX()).append(",").append(dataLinkDefine.getPosY()).append("]\uff1b \r\n");
                } else {
                    BizTypeValidator.isValidBud((String)bizType, (String)("\u6570\u636e\u94fe\u63a5\u5bf9\u8c61\u4e0d\u5b58\u5728 dataLinkId=" + dataLinkId));
                    fieldDefineId = "#";
                }
                List<Object> deleteValue = Arrays.asList(fetchSettingSaveData.getFormSchemeId(), fetchSettingSaveData.getFetchSchemeId(), fetchSettingSaveData.getFormId(), regionId, dataLinkId);
                deleteWhereValues.add(deleteValue);
                FixedFieldDefineSettingDTO fieldDefineSettingDTO = (FixedFieldDefineSettingDTO)dataLinkSettingEntry.getValue();
                boolean errorCheckFlag = false;
                FetchSettingDesEO fetchSettingDesEO = new FetchSettingDesEO();
                fetchSettingDesEO.setId(StringUtils.isEmpty((String)fieldDefineSettingDTO.getId()) ? UUIDUtils.newHalfGUIDStr() : fieldDefineSettingDTO.getId());
                fetchSettingDesEO.setFormSchemeId(fetchSettingSaveData.getFormSchemeId());
                fetchSettingDesEO.setFetchSchemeId(fetchSettingSaveData.getFetchSchemeId());
                fetchSettingDesEO.setFormId(fetchSettingSaveData.getFormId());
                fetchSettingDesEO.setRegionId(regionId);
                fetchSettingDesEO.setRegionType(regionType.getCode());
                fetchSettingDesEO.setDataLinkId(dataLinkId);
                fetchSettingDesEO.setFieldDefineId(fieldDefineId);
                List fixedSettingDataDTO = fieldDefineSettingDTO.getFixedSettingData();
                fetchSettingDesEO.setSortOrder(fieldDefineSettingDTO.getSortOrder());
                if (CollectionUtils.isEmpty((Collection)fixedSettingDataDTO)) continue;
                LinkedList<FixedAdaptSettingVO> fixedSettingData = new LinkedList<FixedAdaptSettingVO>();
                for (FixedAdaptSettingDTO adaptSetting : fixedSettingDataDTO) {
                    FixedAdaptSettingVO adaptSettingVO = new FixedAdaptSettingVO();
                    BeanUtils.copyProperties(adaptSetting, adaptSettingVO);
                    Map bizModelFormulaDTO = adaptSetting.getBizModelFormula();
                    LinkedHashMap bizModelFormula = new LinkedHashMap();
                    for (Map.Entry fetchSourceRowSettingEmtry : bizModelFormulaDTO.entrySet()) {
                        String fetchSourceCode = (String)fetchSourceRowSettingEmtry.getKey();
                        BizModelAllPropsDTO bizModelDTO = bizModelDTOMap.get(fetchSourceCode);
                        if (null == bizModelDTO) {
                            throw new BdeRuntimeException("\u4e1a\u52a1\u6a21\u578b\u4e0d\u5b58\u5728 fetchSourceCode=" + fetchSourceCode);
                        }
                        stringBufferMassage.append("\u3010").append(bizModelDTO.getName()).append("\u3011").append("\u4e1a\u52a1\u6a21\u578b\uff1a");
                        BizModelColumnDefineVO bizModelColumnDefineVO = (BizModelColumnDefineVO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.getColumnDefines(fetchSourceCode));
                        List columnDefines = bizModelColumnDefineVO.getColumnDefines();
                        List<String> dimensions = FetchSettingNrUtil.getDimensions(bizModelDTO);
                        List fetchSourceRowSettingMapS = (List)fetchSourceRowSettingEmtry.getValue();
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
                                    stringBufferMassage.append("\u7b2c").append(i + 1).append("\u884c\u3010").append(columnDefine.getName()).append("\u3011\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff1b");
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
                                if (!StringUtils.isEmpty((String)columnDefine.getDataRange()) && BizTypeEnum.NR.getCode().equals(bizType) && "INPUT".equalsIgnoreCase(columnDefine.getType())) {
                                    String codeValueStr = dimensionObj.toString();
                                    if (codeValueStr.contains(":") && codeValueStr.contains(",")) {
                                        errorCheckFlag = true;
                                        stringBufferMassage.append("\u7b2c").append(i + 1).append("\u884c\u3010").append(columnDefine.getName()).append("\u3011\u4e0d\u5141\u8bb8\u540c\u65f6\u5b58\u5728\u4e24\u79cd\u89c4\u5219\uff1b");
                                    } else {
                                        List<String> dataRange = Arrays.asList(columnDefine.getDataRange().split(","));
                                        if (codeValueStr.contains(",")) {
                                            if (!dataRange.contains("MULTIPLE")) {
                                                errorCheckFlag = true;
                                                stringBufferMassage.append("\u7b2c").append(i + 1).append("\u884c\u3010").append(columnDefine.getName()).append("\u3011\u4e0d\u652f\u6301\u591a\u9009\u89c4\u5219\uff1b");
                                            }
                                        } else if (codeValueStr.contains(":") && !dataRange.contains("RANGE")) {
                                            errorCheckFlag = true;
                                            stringBufferMassage.append("\u7b2c").append(i + 1).append("\u884c\u3010").append(columnDefine.getName()).append("\u3011\u4e0d\u652f\u6301\u8303\u56f4\u89c4\u5219\uff1b");
                                        }
                                    }
                                }
                                columnValueGroupByCode.put(code, dimensionObj.toString());
                            }
                            if (errorCheckFlag) {
                                messageInfo.append((CharSequence)stringBufferMassage);
                                continue;
                            }
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
                fetchSettingDesEO.setFixedSettingData(JSONUtil.toJSONString(fixedSettingData));
                fetchSettingDesDatas.add(fetchSettingDesEO);
            }
        }
        return fetchSettingDesDatas;
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
        Map formulaMap = (Map)JsonUtils.readValue((String)formula, Map.class);
        for (Map.Entry entry : formulaMap.entrySet()) {
            String key = (String)entry.getKey();
            if (customFetchBizModelSet.containsKey(key)) continue;
            customFetchBizModelSet.put(key, entry.getValue());
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

    private Map<String, DataField> getFieldDefinedGroupByKey(Set<String> fieldKeys) {
        Map<String, Object> fieldDefinedByKey = new HashMap<String, DataField>(16);
        if (!CollectionUtils.isEmpty(fieldKeys)) {
            try {
                fieldDefinedByKey = this.runtimeDataSchemeService.getDataFields(new ArrayList<String>(fieldKeys)).stream().collect(Collectors.toMap(Basic::getKey, Function.identity()));
            }
            catch (Exception e) {
                throw new BdeRuntimeException("\u67e5\u8be2\u591a\u4e2a\u6307\u6807\u53d1\u751f\u5f02\u5e38" + e.getMessage());
            }
        }
        return fieldDefinedByKey;
    }

    @Override
    public FixedFieldDefineSettingVO convertFixedDefineSettingByFetchSettingDesEOList(List<FetchSettingDesEO> fetchSettingDesList) {
        if (CollectionUtils.isEmpty(fetchSettingDesList)) {
            return null;
        }
        FixedFieldDefineSettingVO fieldDefineSettingVO = new FixedFieldDefineSettingVO();
        fieldDefineSettingVO.setId(fetchSettingDesList.get(0).getId());
        fieldDefineSettingVO.setFieldDefineId(fetchSettingDesList.get(0).getFieldDefineId());
        fieldDefineSettingVO.setDataLinkId(fetchSettingDesList.get(0).getDataLinkId());
        fieldDefineSettingVO.setRegionId(fetchSettingDesList.get(0).getRegionId());
        LinkedList<FixedAdaptSettingVO> fixedSettingData = new LinkedList<FixedAdaptSettingVO>();
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesList) {
            fixedSettingData.addAll(FetchSettingNrUtil.convertFixedSettingDataDesVOFormEo(fetchSettingDesEO));
        }
        fieldDefineSettingVO.setFixedSettingData(fixedSettingData);
        return fieldDefineSettingVO;
    }

    private Map<String, List<BaseDataVO>> buildBaseDataConfigByFixedFetchSourceRowSettingVO(FixedFetchSourceRowSettingVO rowSettingVO, Map<String, String> dimBaseDataTableMap) {
        String dimensionSetting = rowSettingVO.getDimensionSetting();
        Map<Object, Object> fetchSettingDimCodeValueMap = new HashMap();
        if (!StringUtils.isEmpty((String)dimensionSetting)) {
            List fetchSettingDimListMap = JSONUtil.parseMapArray((String)dimensionSetting, String.class, String.class);
            fetchSettingDimCodeValueMap = fetchSettingDimListMap.stream().collect(Collectors.toMap(item -> (String)item.get("dimCode"), item2 -> (String)item2.get("dimValue")));
        }
        HashMap<String, BaseDataParam> dimBaseDataParamMap = new HashMap<String, BaseDataParam>();
        block10: for (String baseDataDimCode : dimBaseDataTableMap.keySet()) {
            BaseDataParam baseDataParam = new BaseDataParam();
            switch (baseDataDimCode) {
                case "subjectCode": {
                    baseDataParam.setCode(rowSettingVO.getSubjectCode());
                    baseDataParam.setTableName(dimBaseDataTableMap.get(SUBJECT_DIM_CODE));
                    dimBaseDataParamMap.put(SUBJECT_DIM_CODE, baseDataParam);
                    break;
                }
                case "excludeSubjectCode": {
                    baseDataParam.setCode(rowSettingVO.getExcludeSubjectCode());
                    baseDataParam.setTableName(dimBaseDataTableMap.get(SUBJECT_DIM_CODE));
                    dimBaseDataParamMap.put(EXCLUDESUBJECTCODE, baseDataParam);
                    break;
                }
                case "cashCode": {
                    if (StringUtils.isEmpty((String)rowSettingVO.getCashCode())) break;
                    baseDataParam.setCode(rowSettingVO.getCashCode());
                    baseDataParam.setTableName(dimBaseDataTableMap.get(CASH_ITEM_DIM_CODE));
                    dimBaseDataParamMap.put(CASH_ITEM_DIM_CODE, baseDataParam);
                    break;
                }
                default: {
                    if (!fetchSettingDimCodeValueMap.containsKey(baseDataDimCode)) continue block10;
                    baseDataParam.setCode((String)fetchSettingDimCodeValueMap.get(baseDataDimCode));
                    baseDataParam.setTableName(dimBaseDataTableMap.get(baseDataDimCode));
                    dimBaseDataParamMap.put(baseDataDimCode, baseDataParam);
                }
            }
        }
        return this.fetchSettingDesBaseDataService.getBaseDataByTableAndCode(dimBaseDataParamMap);
    }

    public List<FixedFieldDefineSettingVO> getFixedDefineSettingListByEOList(List<FetchSettingDesEO> fetchSettingDesList) {
        if (CollectionUtils.isEmpty(fetchSettingDesList)) {
            return new ArrayList<FixedFieldDefineSettingVO>();
        }
        ArrayList<FixedFieldDefineSettingVO> settingList = new ArrayList<FixedFieldDefineSettingVO>(fetchSettingDesList.size());
        for (FetchSettingDesEO fetchSettingDesEO : fetchSettingDesList) {
            FixedFieldDefineSettingVO fieldDefineSettingVO = new FixedFieldDefineSettingVO();
            fieldDefineSettingVO.setFieldDefineId(fetchSettingDesEO.getFieldDefineId());
            fieldDefineSettingVO.setDataLinkId(fetchSettingDesEO.getDataLinkId());
            fieldDefineSettingVO.setRegionId(fetchSettingDesEO.getRegionId());
            LinkedList<FixedAdaptSettingVO> fixedSettingData = new LinkedList<FixedAdaptSettingVO>(FetchSettingNrUtil.convertFixedSettingDataDesVOFormEo(fetchSettingDesEO));
            fieldDefineSettingVO.setFixedSettingData(fixedSettingData);
            settingList.add(fieldDefineSettingVO);
        }
        return settingList;
    }

    @Override
    public List<Map<String, Object>> listFixedSettingDesByForm(FetchSettingCond fetchSettingCond) {
        FormDefine formDefine = this.runTimeViewController.queryFormById(fetchSettingCond.getFormId());
        if (formDefine == null) {
            return Collections.emptyList();
        }
        if (formDefine.getTitle().startsWith("\u5185\u90e8")) {
            return Collections.emptyList();
        }
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, String> allDimCodeToName = ((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.dimensionRestRequest.listDimension())).stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getTitle));
        allDimCodeToName.put("SUBJECTCODE", "\u79d1\u76ee");
        Map<String, BizModelAllPropsDTO> bizModelDTOMap = ((List)BdeClientUtil.parseResponse((BusinessResponseEntity)this.bizModelClient.listBizModelAllProps())).stream().collect(Collectors.toMap(BizModelAllPropsDTO::getCode, Function.identity(), (o1, o2) -> o2));
        List regions = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
        for (DataRegionDefine region : regions) {
            if (!DataRegionKind.DATA_REGION_SIMPLE.equals((Object)region.getRegionKind())) continue;
            List<DataLinkDefine> dataLinks = this.runTimeViewController.getAllLinksInRegion(region.getKey()).stream().sorted(Comparator.comparingInt(DataLinkDefine::getPosX)).sorted(Comparator.comparingInt(DataLinkDefine::getPosY)).collect(Collectors.toList());
            Set<String> fieldKeys = dataLinks.stream().map(DataLinkDefine::getLinkExpression).filter(Objects::nonNull).collect(Collectors.toSet());
            Map<String, DataField> fieldDefinedGroupByKey = this.getFieldDefinedGroupByKey(fieldKeys);
            Map<String, List<FetchSettingVO>> fixedFetchSettingDesGroupByDataLinkId = this.getFetchSettingDesGroupByDataLinkId(fetchSettingCond);
            if (fixedFetchSettingDesGroupByDataLinkId.isEmpty()) continue;
            result.addAll(this.getDesSettingByRegion(allDimCodeToName, dataLinks, fieldDefinedGroupByKey, bizModelDTOMap, fixedFetchSettingDesGroupByDataLinkId));
        }
        return result;
    }

    @Override
    public List<FetchSettingVO> listFetchSettingDesByFetchSchemeId(FetchSettingCond fetchSettingCond) {
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingDesByCondi(fetchSettingCond);
        return fetchSettingDesEOS.stream().map(item -> FetchSettingNrUtil.convertFetchSettingDesEOToVo(item)).collect(Collectors.toList());
    }

    @Override
    public List<FetchSettingVO> listFetchSettingDesByCondi(FetchSettingCond fetchSettingCond) {
        List fetchSettingDesEOS = this.fetchSettingDesDao.listFetchSettingDesByCondi(fetchSettingCond);
        return fetchSettingDesEOS.stream().map(item -> FetchSettingNrUtil.convertFetchSettingDesEOToVo(item)).collect(Collectors.toList());
    }

    private Collection<? extends Map<String, Object>> getDesSettingByRegion(Map<String, String> allDimCodeToName, List<DataLinkDefine> dataLinks, Map<String, DataField> fieldDefinedGroupByKey, Map<String, BizModelAllPropsDTO> bizModelDTOMap, Map<String, List<FetchSettingVO>> fixedFetchSettingDesGroupByDataLinkId) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (DataLinkDefine link : dataLinks) {
            DataField fieldDefine;
            List<FetchSettingVO> fetchSettingDesListByLinkId = fixedFetchSettingDesGroupByDataLinkId.get(link.getKey());
            if (CollectionUtils.isEmpty(fetchSettingDesListByLinkId) || (fieldDefine = fieldDefinedGroupByKey.get(link.getLinkExpression())) == null) continue;
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(fieldDefine.getDataTableKey());
            String tableNameAndCode = FetchSettingNrUtil.getRpFieldDefineCode(dataTable.getCode(), fieldDefine.getCode());
            String dataLinkTitle = fieldDefine.getTitle();
            Map bizModelFormula = new HashMap();
            for (FetchSettingVO fetchSettingVO : fetchSettingDesListByLinkId) {
                for (FixedAdaptSettingVO adaptSettingVO : fetchSettingVO.getFixedSettingData()) {
                    bizModelFormula = adaptSettingVO.getBizModelFormula();
                }
            }
            for (Map.Entry entry : bizModelFormula.entrySet()) {
                String fetchSourceCode = (String)entry.getKey();
                BizModelAllPropsDTO bizModelDTO = bizModelDTOMap.get(fetchSourceCode);
                if (Objects.isNull(bizModelDTO)) {
                    throw new BdeRuntimeException(String.format("\u6307\u6807\u3010%1$s\u3011\u914d\u7f6e\u89c4\u5219\u4f7f\u7528\u7684\u4e1a\u52a1\u6a21\u578b\u5728\u4e1a\u52a1\u6a21\u578b\u9875\u9762\u5df2\u505c\u7528\u6216\u4e0d\u5b58\u5728", dataLinkTitle));
                }
                if ("BASEDATA".equals(bizModelDTO.getComputationModelCode())) continue;
                for (FixedFetchSourceRowSettingVO fetchSourceRowSettingVO : (List)entry.getValue()) {
                    result.add(this.buildRowData(allDimCodeToName, tableNameAndCode, dataLinkTitle, bizModelDTO, fetchSourceRowSettingVO));
                }
            }
        }
        return result;
    }

    private Map<String, Object> buildRowData(Map<String, String> allDimCodeToName, String tableNameAndCode, String dataLinkTitle, BizModelAllPropsDTO bizModelDTO, FixedFetchSourceRowSettingVO fetchSourceRowSettingVO) {
        String dimSetting;
        HashMap<String, Object> rowData = new HashMap<String, Object>(32);
        rowData.put("fieldDefineCode", tableNameAndCode);
        rowData.put("fieldDefineTitle", dataLinkTitle);
        rowData.put("sign", fetchSourceRowSettingVO.getSign());
        rowData.put("fetchSourceName", bizModelDTO == null ? "" : bizModelDTO.getName());
        if (!StringUtils.isEmpty((String)fetchSourceRowSettingVO.getFetchType())) {
            if (bizModelDTO != null && ComputationModelEnum.CUSTOMFETCH.getCode().equals(bizModelDTO.getComputationModelCode())) {
                CustomBizModelDTO customBizModelDTO = new CustomBizModelDTO();
                BeanUtils.copyProperties(bizModelDTO, customBizModelDTO);
                rowData.put("fetchTypeName", ((SelectField)customBizModelDTO.getSelectFieldMap().get(fetchSourceRowSettingVO.getFetchType())).getFieldName());
            } else {
                rowData.put("fetchTypeName", FetchTypeEnum.getEnumByCode((String)fetchSourceRowSettingVO.getFetchType()).getName());
            }
        }
        rowData.put(SUBJECT_DIM_CODE, fetchSourceRowSettingVO.getSubjectCode());
        rowData.put(EXCLUDESUBJECTCODE, fetchSourceRowSettingVO.getExcludeSubjectCode());
        if (!StringUtils.isEmpty((String)fetchSourceRowSettingVO.getDimType()) && !allDimCodeToName.isEmpty()) {
            String[] dimTypeArray = fetchSourceRowSettingVO.getDimType().split(",");
            StringJoiner dimStrJoiner = new StringJoiner(",");
            for (String item : dimTypeArray) {
                dimStrJoiner.add(allDimCodeToName.get(item));
            }
            rowData.put("dimTypeName", dimStrJoiner.toString());
        }
        if (!StringUtils.isEmpty((String)fetchSourceRowSettingVO.getSumType())) {
            rowData.put("sumTypeName", SumTypeEnum.getSumTypeEnumByCode((String)fetchSourceRowSettingVO.getSumType()).getName());
        }
        rowData.put("reclassSubjCode", fetchSourceRowSettingVO.getReclassSubjCode());
        rowData.put("reclassSrcSubjCode", fetchSourceRowSettingVO.getReclassSrcSubjCode());
        if (!StringUtils.isEmpty((String)fetchSourceRowSettingVO.getAgingRangeType())) {
            rowData.put("agingRangeTypeName", AgingPeriodTypeEnum.fromCode((String)fetchSourceRowSettingVO.getAgingRangeType()).getName());
        }
        rowData.put("agingRangeStart", fetchSourceRowSettingVO.getAgingRangeStart());
        rowData.put("agingRangeEnd", fetchSourceRowSettingVO.getAgingRangeEnd());
        rowData.put("orgCode", fetchSourceRowSettingVO.getOrgCode());
        rowData.put("acctYear", fetchSourceRowSettingVO.getAcctYear());
        rowData.put("acctPeriod", fetchSourceRowSettingVO.getAcctPeriod());
        rowData.put("currencyCode", fetchSourceRowSettingVO.getCurrencyCode());
        rowData.put(CASH_ITEM_DIM_CODE, fetchSourceRowSettingVO.getCashCode());
        if (ComputationModelEnum.TFV.getCode().equals(fetchSourceRowSettingVO.getBizModelCode())) {
            rowData.put("formula", fetchSourceRowSettingVO.getFormula());
        }
        if (!StringUtils.isEmpty((String)(dimSetting = fetchSourceRowSettingVO.getDimensionSetting()))) {
            List dimSettingListMap = JSONUtil.parseMapArray((String)dimSetting, String.class, String.class);
            for (Map dimSettingMap : dimSettingListMap) {
                String dimCode = (String)dimSettingMap.get("dimCode");
                String dimValue = (String)dimSettingMap.get("dimValue");
                String dimRule = (String)dimSettingMap.get("dimRule");
                if (StringUtils.isEmpty((String)dimValue) || !allDimCodeToName.containsKey(dimCode)) continue;
                rowData.put(dimCode, dimValue);
                rowData.put(dimCode + "MatchRule", StringUtils.isEmpty((String)dimRule) ? "" : MatchRuleEnum.getMatchRuleEnumByCode((String)dimRule).getName());
            }
        }
        return rowData;
    }
}

